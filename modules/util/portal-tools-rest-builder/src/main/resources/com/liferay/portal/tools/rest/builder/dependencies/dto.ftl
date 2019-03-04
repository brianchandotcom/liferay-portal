package ${configYAML.apiPackagePath}.dto.${escapedVersion};

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import com.liferay.petra.function.UnsafeSupplier;
import com.liferay.petra.string.StringBundler;

import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;

import java.util.Date;

import javax.annotation.Generated;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author ${configYAML.author}
 * @generated
 */
@Generated("")
@GraphQLName("${schemaName}")
@JsonFilter("VulcanFilter")
@XmlRootElement(name = "${schemaName}")
public class ${schemaName} {

	<#list freeMarkerTool.getDTOJavaMethodParameters(configYAML, openAPIYAML, schema) as javaMethodParameter>
		<#assign javaDataType = javaMethodParameter.parameterType />

		<#if stringUtil.equals(javaDataType, "[Z")>
			<#assign javaDataType = "boolean[]" />
		<#elseif stringUtil.equals(javaDataType, "[D")>
			<#assign javaDataType = "double[]" />
		<#elseif stringUtil.equals(javaDataType, "[F")>
			<#assign javaDataType = "float[]" />
		<#elseif stringUtil.equals(javaDataType, "[I")>
			<#assign javaDataType = "int[]" />
		<#elseif stringUtil.equals(javaDataType, "[J")>
			<#assign javaDataType = "long[]" />
		<#elseif javaDataType?starts_with("[L")>
			<#assign javaDataType = javaDataType[2..(javaDataType?length - 2)] + "[]" />
		</#if>

		public ${javaDataType} get${javaMethodParameter.parameterName?cap_first}() {
			return ${javaMethodParameter.parameterName};
		}

		public void set${javaMethodParameter.parameterName?cap_first}(${javaDataType} ${javaMethodParameter.parameterName}) {
			this.${javaMethodParameter.parameterName} = ${javaMethodParameter.parameterName};
		}

		@JsonIgnore
		public void set${javaMethodParameter.parameterName?cap_first}(UnsafeSupplier<${javaDataType}, Exception> ${javaMethodParameter.parameterName}UnsafeSupplier) {
			try {
				${javaMethodParameter.parameterName} = ${javaMethodParameter.parameterName}UnsafeSupplier.get();
			}
			catch (Exception e) {
				throw new RuntimeException(e);
			}
		}

		@GraphQLField
		@JsonProperty
		protected ${javaDataType} ${javaMethodParameter.parameterName};
	</#list>

	public String toString() {
		StringBundler sb = new StringBundler();

		sb.append("{");

		<#list freeMarkerTool.getDTOJavaMethodParameters(configYAML, openAPIYAML, schema) as javaMethodParameter>
			<#if !javaMethodParameter?is_first>
				sb.append(", ");
			</#if>

			sb.append("\"${javaMethodParameter.parameterName}\": ");

			<#if javaMethodParameter.parameterType?ends_with("Date") || javaMethodParameter.parameterType?ends_with("String") || javaMethodParameter.parameterType?starts_with("[")>
				sb.append("\"");
				sb.append(${javaMethodParameter.parameterName});
				sb.append("\"");
			<#else>
				sb.append(${javaMethodParameter.parameterName});
			</#if>
		</#list>

		sb.append("}");

		return sb.toString();
	}

}