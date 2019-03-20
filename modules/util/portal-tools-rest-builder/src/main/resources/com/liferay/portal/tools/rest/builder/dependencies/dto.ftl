package ${configYAML.apiPackagePath}.dto.${escapedVersion};

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonValue;

import com.liferay.petra.function.UnsafeSupplier;
import com.liferay.petra.string.StringBundler;

import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Date;
import java.util.Map;
import java.util.Objects;

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
public class ${schemaName} <#if freeMarkerTool.getDTOParentClassName(openAPIYAML, schemaName)??>extends ${freeMarkerTool.getDTOParentClassName(openAPIYAML, schemaName)}</#if> {
	<#assign enumSimpleClassNames = [] />

	<#if schema.propertySchemas??>
		<#list schema.propertySchemas?keys as propertySchemaName>
			<#assign propertySchema = schema.propertySchemas[propertySchemaName] />

			<#if propertySchema.enumValues?? && (propertySchema.enumValues?size > 0)>
				<#assign enumSimpleClassNames = enumSimpleClassNames + [propertySchemaName?cap_first] />

				public static enum ${propertySchemaName?cap_first} {

					<#list propertySchema.enumValues as enumValue>
						${freeMarkerTool.getEnumFieldName(enumValue)}("${enumValue}")

						<#if enumValue_has_next>
							,
						</#if>
					</#list>;

					@JsonCreator
					public static ${propertySchemaName?cap_first} create(String value) {
						for (${propertySchemaName?cap_first} ${propertySchemaName} : values()) {
							if (Objects.equals(${propertySchemaName}.getValue(), value)) {
								return ${propertySchemaName};
							}
						}

						return null;
					}

					@JsonValue
					public String getValue() {
						return _value;
					}

					@Override
					public String toString() {
						return _value;
					}

					private ${propertySchemaName?cap_first}(String value) {
						_value = value;
					}

					private final String _value;

				}
			</#if>
		</#list>
	</#if>

	<#assign properties = freeMarkerTool.getDTOProperties(configYAML, openAPIYAML, schema) />

	<#list properties?keys as propertyName>
		<#assign
			javaDataType = properties[propertyName]
			propertySchema = freeMarkerTool.getDTOPropertySchema(propertyName, schema)
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
		<#elseif stringUtil.equals(javaDataType, "java.util.Map")>
			<#assign javaDataType = "Map<String, " + freeMarkerTool.getJavaDataType(configYAML, openAPIYAML, propertySchema.additionalPropertySchema) + ">" />
		</#if>

		<#if propertySchema.description??>
			@Schema(description = "${propertySchema.description}")
		</#if>
		public ${javaDataType} get${propertyName?cap_first}() {
			return ${propertyName};
		}

		<#if enumSimpleClassNames?seq_contains(javaDataType)>
			@JsonIgnore
			public String get${propertyName?cap_first}AsString() {
				if (${propertyName} == null) {
					return null;
				}

				return ${propertyName}.toString();
			}
		</#if>

		public void set${propertyName?cap_first}(${javaDataType} ${propertyName}) {
			this.${propertyName} = ${propertyName};
		}

		@JsonIgnore
		public void set${propertyName?cap_first}(UnsafeSupplier<${javaDataType}, Exception> ${propertyName}UnsafeSupplier) {
			try {
				${propertyName} = ${propertyName}UnsafeSupplier.get();
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
		<#if schema.requiredPropertySchemaNames?? && schema.requiredPropertySchemaNames?seq_contains(propertyName)>
			@NotNull
		</#if>
		protected ${javaDataType} ${propertyName};
	</#list>

	public String toString() {
		StringBundler sb = new StringBundler();

		sb.append("{");

		<#list properties?keys as propertyName>
			<#if !propertyName?is_first>
				sb.append(", ");
			</#if>

			sb.append("\"${propertyName}\": ");

			<#if properties[propertyName]?starts_with("[")>
				if (${propertyName} == null) {
					sb.append("null");
				}
				else {
					sb.append("[");

					for (int i = 0; i < ${propertyName}.length; i++) {
						<#if properties[propertyName]?ends_with("Date;") || properties[propertyName]?ends_with("String;") || enumSimpleClassNames?seq_contains(properties[propertyName])>
							sb.append("\"");
							sb.append(${propertyName}[i]);
							sb.append("\"");
						<#else>
							sb.append(${propertyName}[i]);
						</#if>

						if ((i + 1) < ${propertyName}.length) {
							sb.append(", ");
						}
					}

					sb.append("]");
				}
			<#else>
				<#if properties[propertyName]?ends_with("Date") || properties[propertyName]?ends_with("String") || enumSimpleClassNames?seq_contains(properties[propertyName])>
					sb.append("\"");
					sb.append(${propertyName});
					sb.append("\"");
				<#else>
					sb.append(${propertyName});
				</#if>
			</#if>
		</#list>

		sb.append("}");

		return sb.toString();
	}

}