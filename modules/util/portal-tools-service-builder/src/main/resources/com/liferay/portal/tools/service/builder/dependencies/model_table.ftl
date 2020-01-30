package ${apiPackagePath}.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.io.Serializable;

import java.math.BigDecimal;

import java.sql.Blob;
import java.sql.Clob;
import java.sql.Types;

import java.util.Date;
import java.util.Locale;
import java.util.Map;

/**
 * The table class for the &quot;${table}&quot; database table.
 *
 * @author ${author}
<#list modelNames as modelName>
 * @see ${modelName}
</#list>
<#if classDeprecated>
 * @deprecated ${classDeprecatedComment}
</#if>
 * @generated
 */

<#if classDeprecated>
	@Deprecated
</#if>

public class ${name}Table extends BaseTable<${name}Table> {

	public static final ${name}Table INSTANCE = new ${name}Table();

	<#list columns as column>
		public final Column<${name}Table, ${column.javaType}> ${column.name} = createColumn("${column.dbName}", ${column.javaType}.class, Types.${column.sqlType}, Column.${column.flag});
	</#list>

	private ${name}Table() {
		super("${table}", ${name}Table::new);
	}

}