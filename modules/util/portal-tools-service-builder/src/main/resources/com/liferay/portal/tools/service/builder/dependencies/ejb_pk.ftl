package ${apiPackagePath}.service.persistence;

import ${serviceBuilder.getCompatJavaClassName("HashUtil")};
import ${serviceBuilder.getCompatJavaClassName("StringBundler")};

import com.liferay.portal.kernel.util.DateUtil;

import java.io.Serializable;

import java.util.Date;

/**
 * @author ${author}
 * @generated
 */
public class ${entity.PKClassName} implements Comparable<${entity.PKClassName}>, Serializable {

	<#list entity.PKEntityColumns as entityColumn>
		public ${entityColumn.type} ${entityColumn.name};
	</#list>

	public ${entity.PKClassName}() {
	}

	public ${entity.PKClassName}(

	<#list entity.PKEntityColumns as entityColumn>
		${entityColumn.type} ${entityColumn.name}

		<#if entityColumn_has_next>
			,
		</#if>
	</#list>

	) {
		<#list entity.PKEntityColumns as entityColumn>
			this.${entityColumn.name} = ${entityColumn.name};
		</#list>
	}

	<#list entity.PKEntityColumns as entityColumn>
		<#if !entityColumn.isCollection()>
			public ${entityColumn.type} get${entityColumn.methodName}() {
				return ${entityColumn.name};
			}

			<#if entityColumn.type== "boolean">
				public ${entityColumn.type} is${entityColumn.methodName}() {
					return ${entityColumn.name};
				}
			</#if>

			public void set${entityColumn.methodName}(${entityColumn.type} ${entityColumn.name}) {
				this.${entityColumn.name} = ${entityColumn.name};
			}
		</#if>
	</#list>

	@Override
	public int compareTo(${entity.PKClassName} pk) {
		if (pk == null) {
			return -1;
		}

		int value = 0;

		<#list entity.PKEntityColumns as entityColumn>
			<#if entityColumn.isPrimitiveType()>
				<#if stringUtil.equals(entityColumn.type, "boolean")>
					if (!${entityColumn.name} && pk.${entityColumn.name}) {
						value = -1;
					}
					else if (${entityColumn.name} && !pk.${entityColumn.name}) {
						value = 1;
					}
					else {
						value = 0;
					}
				<#else>
					if (${entityColumn.name} < pk.${entityColumn.name}) {
						value = -1;
					}
					else if (${entityColumn.name} > pk.${entityColumn.name}) {
						value = 1;
					}
					else {
						value = 0;
					}
				</#if>
			<#else>
				<#if stringUtil.equals(entityColumn.type, "Date")>
					value = DateUtil.compareTo(${entityColumn.name}, pk.${entityColumn.name});
				<#else>
					value = ${entityColumn.name}.compareTo(pk.${entityColumn.name});
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

		<#list entity.PKEntityColumns as entityColumn>
			<#if entityColumn.isPrimitiveType()>
				(${entityColumn.name} == pk.${entityColumn.name})
			<#else>
				(${entityColumn.name}.equals(pk.${entityColumn.name}))
			</#if>

			<#if entityColumn_has_next> && </#if>
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

		<#list entity.PKEntityColumns as entityColumn>
			hashCode = HashUtil.hash(hashCode, ${entityColumn.name});
		</#list>

		return hashCode;
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(${entity.PKEntityColumns?size * 2 + 2});

		sb.append("{");

		<#list entity.PKEntityColumns as entityColumn>
			<#if entityColumn?is_first>
				sb.append("${entityColumn.name}=");
			<#else>
				sb.append(", ${entityColumn.name}=");
			</#if>

			sb.append(${entityColumn.name});
		</#list>

		sb.append("}");

		return sb.toString();
	}

}