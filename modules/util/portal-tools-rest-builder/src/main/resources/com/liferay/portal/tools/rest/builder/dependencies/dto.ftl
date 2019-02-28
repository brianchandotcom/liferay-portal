package ${configYAML.apiPackagePath}.dto.${escapedVersion};

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
@XmlRootElement(name = "${schemaName}")
public class ${schemaName} {

	<#list freeMarkerTool.getDTOJavaMethodParameters(configYAML, openAPIYAML, schema) as javaMethodParameter>
		public ${javaMethodParameter.parameterType} get${javaMethodParameter.parameterName?cap_first}() {
			return ${javaMethodParameter.parameterName};
		}

		public void set${javaMethodParameter.parameterName?cap_first}(${javaMethodParameter.parameterType} ${javaMethodParameter.parameterName}) {
			this.${javaMethodParameter.parameterName} = ${javaMethodParameter.parameterName};
		}

		@JsonIgnore
		public void set${javaMethodParameter.parameterName?cap_first}(UnsafeSupplier<${javaMethodParameter.parameterType}, Exception> ${javaMethodParameter.parameterName}UnsafeSupplier) {
			try {
				${javaMethodParameter.parameterName} = ${javaMethodParameter.parameterName}UnsafeSupplier.get();
			}
			catch (Exception e) {
				throw new RuntimeException(e);
			}
		}

		@GraphQLField
		@JsonProperty
		protected ${javaMethodParameter.parameterType} ${javaMethodParameter.parameterName};
	</#list>

	public String toString() {
		StringBundler sb = new StringBundler();

		sb.append("{");

		<#list freeMarkerTool.getDTOJavaMethodParameters(configYAML, openAPIYAML, schema) as javaMethodParameter>
			<#if !javaMethodParameter?is_first>
				sb.append(", ");
			</#if>

			sb.append("\"${javaMethodParameter.parameterName}\": ");

			<#if stringUtil.equals(javaMethodParameter.parameterType, "Date") || stringUtil.equals(javaMethodParameter.parameterType, "String") || javaMethodParameter.parameterType?contains("[]")>
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