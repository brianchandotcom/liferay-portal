package ${configYAML.apiPackagePath}.dto.${escapedVersion};

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import com.liferay.petra.function.UnsafeSupplier;
import com.liferay.petra.string.StringBundler;

import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Date;

import javax.annotation.Generated;

import javax.validation.constraints.NotNull;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author ${configYAML.author}
 * @generated
 */
<#if schema.oneOfSchemas?has_content>
	@JsonSubTypes(
		{
			<#list schema.oneOfSchemas as oneOfSchema>
				<#assign propertySchemaName = oneOfSchema.propertySchemas?keys[0] />

				@JsonSubTypes.Type(name = "${propertySchemaName}", value=${propertySchemaName?cap_first}.class)

				<#if oneOfSchema_has_next>
					,
				</#if>
			</#list>
		}
	)
	@JsonTypeInfo(include = JsonTypeInfo.As.PROPERTY, property = "type", use = JsonTypeInfo.Id.NAME)
</#if>
@Generated("")
@GraphQLName("${schemaName}")
@JsonFilter("Liferay.Vulcan")
@XmlRootElement(name = "${schemaName}")
public class ${schemaName} <#if freeMarkerTool.getDTOParentClassName(openAPIYAML, schemaName)??>extends ${freeMarkerTool.getParentClass(openAPIYAML, schemaName)}</#if> {

	<#list freeMarkerTool.getDTOJavaMethodParameters(configYAML, openAPIYAML, schema) as javaMethodParameter>
		<#assign
			javaDataType = javaMethodParameter.parameterType
			propertySchema = freeMarkerTool.getDTOPropertySchema(javaMethodParameter, schema)
		/>

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

		<#if propertySchema.description??>
			@Schema(description = "${propertySchema.description}")
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
		@JsonProperty(
			<#if propertySchema.readOnly>
				access = JsonProperty.Access.READ_ONLY
			<#elseif propertySchema.writeOnly>
				access = JsonProperty.Access.WRITE_ONLY
			<#else>
				access = JsonProperty.Access.READ_WRITE
			</#if>
		)
		<#if schema.requiredPropertySchemaNames?? && schema.requiredPropertySchemaNames?seq_contains(javaMethodParameter.parameterName)>
			@NotNull
		</#if>
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

			<#if javaMethodParameter.parameterType?starts_with("[")>
				if (${javaMethodParameter.parameterName} == null) {
					sb.append("null");
				}
				else {
					sb.append("[");

					for (int i = 0; i < ${javaMethodParameter.parameterName}.length; i++){
						<#if javaMethodParameter.parameterType?ends_with("Date;") || javaMethodParameter.parameterType?ends_with("String;")>
							sb.append("\"");
							sb.append(${javaMethodParameter.parameterName}[i]);
							sb.append("\"");
						<#else>
							sb.append(${javaMethodParameter.parameterName}[i]);
						</#if>

						if ((i + 1) < ${javaMethodParameter.parameterName}.length) {
							sb.append(", ");
						}
					}

					sb.append("]");
				}
			<#else>
				<#if javaMethodParameter.parameterType?ends_with("Date") || javaMethodParameter.parameterType?ends_with("String")>
					sb.append("\"");
					sb.append(${javaMethodParameter.parameterName});
					sb.append("\"");
				<#else>
					sb.append(${javaMethodParameter.parameterName});
				</#if>
			</#if>
		</#list>

		sb.append("}");

		return sb.toString();
	}

}