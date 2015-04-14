package ${packagePath}.service.persistence;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.util.DateUtil;
import com.liferay.portal.kernel.util.HashUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;

import java.io.Serializable;

import java.util.Date;

/**
 * @author ${author}
 * @generated
 */
@ProviderType
public class ${entity.PKClassName} implements Comparable<${entity.PKClassName}>, Serializable {

	<#list entity.PKList as column>
		public ${column.type} ${column.name};
	</#list>

	public ${entity.PKClassName}() {
	}

	public ${entity.PKClassName}(

	<#list entity.PKList as column>
		${column.type} ${column.name}

		<#if column_has_next>
			,
		</#if>
	</#list>

	) {
		<#list entity.PKList as column>
			this.${column.name} = ${column.name};
		</#list>
	}

	<#list entity.PKList as column>
		<#if !column.isCollection()>
			public ${column.type} get${column.methodName}() {
				return ${column.name};
			}

			<#if column.type== "boolean">
				public ${column.type} is${column.methodName}() {
					return ${column.name};
				}
			</#if>

			public void set${column.methodName}(${column.type} ${column.name}) {
				this.${column.name} = ${column.name};
			}
		</#if>
	</#list>

	@Override
	public int compareTo(${entity.PKClassName} pk) {
		if (pk == null) {
			return -1;
		}

		int value = 0;

		<#list entity.PKList as column>
			<#if column.isPrimitiveType()>
				<#if column.type == "boolean">
					if (!${column.name} && pk.${column.name}) {
						value = -1;
					}
					else if (${column.name} && !pk.${column.name}) {
						value = 1;
					}
					else {
						value = 0;
					}
				<#else>
					if (${column.name} < pk.${column.name}) {
						value = -1;
					}
					else if (${column.name} > pk.${column.name}) {
						value = 1;
					}
					else {
						value = 0;
					}
				</#if>
			<#else>
				<#if column.type == "Date">
					value = DateUtil.compareTo(${column.name}, pk.${column.name});
				<#else>
					value = ${column.name}.compareTo(pk.${column.name});
				</#if>
			</#if>

			if (value != 0) {
				return value;
			}
		</#list>

		return 0;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof ${entity.PKClassName})) {
			return false;
		}

		${entity.PKClassName} pk = (${entity.PKClassName})obj;

		if (

		<#list entity.PKList as column>
			<#if column.isPrimitiveType()>
				(${column.name} == pk.${column.name})
			<#else>
				(${column.name}.equals(pk.${column.name}))
			</#if>

			<#if column_has_next> && </#if>
		</#list>

		) {
			return true;
		}
		else{
			return false;
		}
	}

	@Override
	public int hashCode() {
		int hashCode = 0;

		<#list entity.PKList as column>
			hashCode = HashUtil.hash(hashCode, ${column.name});
		</#list>

		return hashCode;
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(${entity.PKList?size * 5});

		sb.append(StringPool.OPEN_CURLY_BRACE);

		<#list entity.PKList as column>
			sb.append("${column.name}");
			sb.append(StringPool.EQUAL);
			sb.append(${column.name});

			<#if column_has_next>
				sb.append(StringPool.COMMA);
				sb.append(StringPool.SPACE);
			</#if>
		</#list>

		sb.append(StringPool.CLOSE_CURLY_BRACE);

		return sb.toString();
	}

}