package com.study.cae.commons.util.tool;


import com.study.cae.commons.util.tool.util.CreateFile;
import com.study.cae.commons.util.tool.util.MySQLTableComment;

/**
 * @author suicaijiao
 * @Description:
 * @date 2019年4月25日
 */
public class GenerateServiceImpl {

    /**
     * 生成serviceImp
     *
     * @param tableName   数据库表名
     * @param subNum      截取前缀位数
     * @param packageName 报名去掉package的包名称（com.example.demo）
     */
    public static void getServiceImpFile(String tableName, int subNum, String packageName) {
        //获得表名
        String oldName = MySQLTableComment.replaceUnderlineAndfirstToUpper(tableName.substring(subNum), "_");
        String entityClassName = MySQLTableComment.firstCharacterToUpper(oldName);
        String entityClassNameToLower = MySQLTableComment.firstCharacterToLower(oldName);

        // 查询主键名称(数据库名)
        String keyNameDb = MySQLTableComment.getPrimaryKeyName(tableName).split(",")[0];
        String keyNameEntity = MySQLTableComment.replaceUnderlineAndfirstTo(keyNameDb, "_");

        // 查询主键类型(数据库名)
        String keyType = MySQLTableComment.getPrimaryKeyName(tableName).split(",")[1];

        String mapperName = entityClassNameToLower + "Mapper";

        String dirName = "D:/" + entityClassName + "/service/impl";// 创建目录
        CreateFile.createDir(dirName);// 调用方法创建目录
        String fileName = dirName + "/" + entityClassName + "ServiceImpl.java";// 创建文件
        CreateFile.createFile(fileName);// 调用方法创建文件

        StringBuilder builder = new StringBuilder();
        builder.append("package " + packageName + ".service.impl;" + "\r\n\r\n");

        builder.append("import " + packageName + ".mybaits." + entityClassName + "Mapper;\r\n");
        builder.append("import org.springframework.beans.factory.annotation.Autowired;\r\n");
        builder.append("import org.springframework.stereotype.Service;\r\n");
        builder.append("import java.util.List;" + "\r\n");
        builder.append("import " + packageName + ".entity." + entityClassName + ";\r\n");
        builder.append("import " + packageName + ".service." + entityClassName + "Service;\r\n");

        builder.append("@Service(\"" + entityClassNameToLower + "Service\")\r\n");
        builder.append("public class " + entityClassName + "ServiceImpl implements " + entityClassName + "Service  {" + "\r\n\r\n");
        builder.append("	@Autowired\r\n");
        builder.append("	private " + entityClassName + "Mapper " + entityClassNameToLower + "Mapper;\r\n\r\n");

        builder.append("	@Override\r\n");
        builder.append("	public int insert(" + entityClassName + " " + entityClassNameToLower + ") {\r\n");
        builder.append("		return " + mapperName + ".insert(" + entityClassNameToLower + ");\r\n");
        builder.append("	}\r\n\r\n");

        builder.append("	@Override\r\n");
        builder.append("	public int updateByPrimaryKey(" + entityClassName + " " + entityClassNameToLower + ") {\r\n");
        builder.append("		return " + mapperName + ".updateByPrimaryKey(" + entityClassNameToLower + ");\r\n");
        builder.append("	}\r\n\r\n");

        builder.append("	@Override\r\n");
        if ("varchar(32)".equalsIgnoreCase(keyType)) {
            builder.append("	public int deleteByPrimaryKey(String " + keyNameEntity + ") {\r\n");
        } else if ("bigint".equalsIgnoreCase(keyType) || "bigint(20) unsigned".equalsIgnoreCase(keyType)) {
            builder.append("	public int deleteByPrimaryKey(Long " + keyNameEntity + ") {\r\n");
        } else {
            builder.append("	public int deleteByPrimaryKey(Integer " + keyNameEntity + ") {\r\n");
        }
        builder.append("		return " + mapperName + ".deleteByPrimaryKey(" + keyNameEntity + ");\r\n");
        builder.append("	}\r\n\r\n");

        if ("varchar(32)".equalsIgnoreCase(keyType)) {
            builder.append("	public " + entityClassName + " selectByPrimaryKey(String " + keyNameEntity + ") {\r\n");
        } else if ("bigint".equalsIgnoreCase(keyType)) {
            builder.append("	public " + entityClassName + " selectByPrimaryKey(Long " + keyNameEntity + ") {\r\n");
        } else {
            builder.append("	public " + entityClassName + " selectByPrimaryKey(Integer " + keyNameEntity + ") {\r\n");
        }

        builder.append("		return " + mapperName + ".selectByPrimaryKey(" + keyNameEntity + ");\r\n");
        builder.append("	}\r\n\r\n");

        builder.append("	@Override\r\n");
        builder.append("	public List<" + entityClassName + "> selectAll(" + entityClassName + " " + entityClassNameToLower + ") {\r\n");
        builder.append("		return " + mapperName + ".selectAll(" + entityClassNameToLower + ");\r\n");
        builder.append("	}\r\n\r\n");

        builder.append("}");
        String str = CreateFile.fileLinesWrite(fileName, builder.toString(), false);
        if ("write".equals(str)) {
            System.out.println("创建成功：" + fileName);
        }
    }
}
