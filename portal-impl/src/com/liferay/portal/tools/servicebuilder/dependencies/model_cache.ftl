package ${packagePath}.model.impl;

import ${packagePath}.model.${entity.name};

import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.CacheModel;

import java.io.Serializable;

import java.util.Date;

/**
 * The cache model class for representing ${entity.name} in entity cache.
 *
 * @author ${author}
 * @see ${entity.name}
 * @generated
 */
public class ${entity.name}CacheModel implements CacheModel<${entity.name}>, Serializable {

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(${(entity.regularColList?size - entity.blobList?size) * 2 + 1});

		<#list entity.regularColList as column>
			<#if column.type != "Blob">
				<#if column_index == 0>
					sb.append("{${column.name}=");
					sb.append(${column.name});
				<#elseif column_has_next>
					sb.append(", ${column.name}=");
					sb.append(${column.name});
				<#else>
					sb.append(", ${column.name}=");
					sb.append(${column.name});
					sb.append("}");
				</#if>
			</#if>
		</#list>

		return sb.toString();
	}

	public ${entity.name} toEntityModel() {
		${entity.name}Impl ${entity.varName}Impl = new ${entity.name}Impl();

		<#list entity.regularColList as column>
			<#if column.type != "Blob">
				<#if column.type == "Date">
					if (${column.name} == Long.MIN_VALUE) {
						${entity.varName}Impl.set${column.methodName}(null);
					}
					else {
						${entity.varName}Impl.set${column.methodName}(new Date(${column.name}));
					}
				<#else>
					<#if column.type == "String">
						if (${column.name} == null) {
							${entity.varName}Impl.set${column.methodName}(StringPool.BLANK);
						}
						else {
							${entity.varName}Impl.set${column.methodName}(${column.name});
						}
					<#else>
						${entity.varName}Impl.set${column.methodName}(${column.name});
					</#if>
				</#if>
			</#if>
		</#list>

		${entity.varName}Impl.resetOriginalValues();

		<#list cacheFields as cacheField>
			<#assign methodName = textFormatter.format(serviceBuilder.getVariableName(cacheField), 6)>

			${entity.varName}Impl.set${methodName}(${cacheField.name});
		</#list>

		return ${entity.varName}Impl;
	}

	<#list entity.regularColList as column>
		<#if column.type != "Blob">
			<#if column.type == "Date">
				public long ${column.name};
			<#else>
				public ${column.type} ${column.name};
			</#if>
		</#if>
	</#list>

	<#list cacheFields as cacheField>
		public ${cacheField.type.fullyQualifiedName} ${cacheField.name};
	</#list>

}