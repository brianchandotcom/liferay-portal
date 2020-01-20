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
 * The table class for the ${entity.name}.
 *
 * @author ${author}
<#if classDeprecated>
 * @deprecated ${classDeprecatedComment}
</#if>
 * @generated
 */
public class ${entity.name}Table extends Table<${entity.name}Table> {

	public static final ${entity.name}Table INSTANCE = new ${entity.name}Table();

	<#list entity.databaseRegularEntityColumns as entityColumn>
		<#assign sqlType = serviceBuilder.getSqlType(entity.getName(), entityColumn.getName(), entityColumn.getType()) />

		<#if entityColumn.isPrimitiveType()>
			<#assign entityColumnType = serviceBuilder.getPrimitiveObj(entityColumn.type) />
		<#elseif stringUtil.equals(sqlType, "CLOB")>
			<#assign entityColumnType = "Clob" />
		<#else>
			<#assign entityColumnType = entityColumn.genericizedType />
		</#if>

		public final Column<${entity.name}Table, ${entityColumnType}> ${entityColumn.name} = createColumn("${entityColumn.DBName}", ${entityColumnType}.class, Types.${sqlType});
	</#list>

	private ${entity.name}Table() {
		super("${entity.table}", ${entity.name}Table::new);
	}

}