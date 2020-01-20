package ${apiPackagePath}.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.Table;

import java.io.Serializable;

import java.math.BigDecimal;

import java.sql.Blob;
import java.sql.Clob;
import java.sql.Types;

import java.util.Date;
import java.util.Locale;
import java.util.Map;

/**
 * The table class for the ${name}.
 *
 * @author ${author}
 * @generated
 */
public class ${name}Table extends Table<${name}Table> {

	public static final ${name}Table INSTANCE = new ${name}Table();

	<#list columns as column>
		public final Column<${name}Table, ${column.javaType}> ${column.name} = createColumn("${column.dbName}", ${column.javaType}.class, Types.${column.sqlType});
	</#list>

	private ${name}Table() {
		super("${table}", ${name}Table::new);
	}

}