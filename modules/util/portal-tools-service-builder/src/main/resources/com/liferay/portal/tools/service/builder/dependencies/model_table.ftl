package ${apiPackagePath}.model;

import com.liferay.portal.kernel.dao.model.Column;
import com.liferay.portal.kernel.dao.model.Table;

import java.io.Serializable;

import java.math.BigDecimal;

import java.sql.Blob;
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
		<#if entityColumn.isPrimitiveType()>
			<#assign entityColumnType = serviceBuilder.getPrimitiveObj(entityColumn.type) />
		<#elseif stringUtil.equals(entityColumn.type, "Map")>
			<#assign entityColumnType = "String" />
		<#else>
			<#assign entityColumnType = entityColumn.genericizedType />
		</#if>

		public final Column<${entity.name}Table, ${entityColumnType}> ${entityColumn.name};
	</#list>

	private ${entity.name}Table() {
		super("${entity.table}", ${entity.name}Table::new);

		<#list entity.databaseRegularEntityColumns as entityColumn>
			<#if entityColumn.isPrimitiveType()>
				<#assign entityColumnType = serviceBuilder.getPrimitiveObj(entityColumn.type) />
			<#elseif stringUtil.equals(entityColumn.type, "Map")>
				<#assign entityColumnType = "String" />
			<#else>
				<#assign entityColumnType = entityColumn.genericizedType />
			</#if>

			<#assign sqlType = serviceBuilder.getSqlType(entity.getName(), entityColumn.getName(), entityColumn.getType()) />

			${entityColumn.name} = new Column<>(this, "${entityColumn.DBName}", ${entityColumnType}.class, Types.${sqlType});
		</#list>

		setColumns(
			<#list entity.databaseRegularEntityColumns as entityColumn>
				${entityColumn.name}

				<#if entityColumn_has_next>
					,
				</#if>
			</#list>
			);
	}

}