package ${configYAML.apiPackagePath}.dto.${escapedVersion};

<#list globalEnumSchemas?keys as globalEnumSchemaName>
	import ${configYAML.apiPackagePath}.constant.${escapedVersion}.${globalEnumSchemaName};
</#list>

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonValue;

import com.liferay.petra.function.UnsafeSupplier;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.vulcan.graphql.annotation.GraphQLField;
import com.liferay.portal.vulcan.graphql.annotation.GraphQLName;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import javax.annotation.Generated;

import javax.validation.Valid;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotEmpty;
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
	@JsonTypeInfo(include = JsonTypeInfo.As.PROPERTY, property = "childType", use = JsonTypeInfo.Id.NAME)
</#if>

<#assign dtoParentClassName = freeMarkerTool.getDTOParentClassName(openAPIYAML, schemaName)! />

<#if dtoParentClassName?has_content>
	@JsonTypeInfo(
		defaultImpl = ${schemaName}.class, include = JsonTypeInfo.As.PROPERTY, property = "childType", use = JsonTypeInfo.Id.NAME
	)
</#if>

@Generated("")
@GraphQLName("${schemaName}")
@JsonFilter("Liferay.Vulcan")
<#if schema.requiredPropertySchemaNames?has_content>
	@Schema(requiredProperties =
		{
			<#list schema.requiredPropertySchemaNames as requiredProperty>
				"${requiredProperty}"
				<#if requiredProperty_has_next>
					,
				</#if>
			</#list>
		}
	)
</#if>
@XmlRootElement(name = "${schemaName}")
public class ${schemaName} <#if dtoParentClassName?has_content>extends ${dtoParentClassName}</#if> {
	<#assign enumSchemas = freeMarkerTool.getDTOEnumSchemas(openAPIYAML, schema) />

	<#list enumSchemas?keys as enumName>
		@GraphQLName("${enumName}")
		public static enum ${enumName} {

			<#list enumSchemas[enumName].enumValues as enumValue>
				${freeMarkerTool.getEnumFieldName(enumValue)}("${enumValue}")

				<#if enumValue_has_next>
					,
				</#if>
			</#list>;

			@JsonCreator
			public static ${enumName} create(String value) {
				for (${enumName} ${freeMarkerTool.getSchemaVarName(enumName)} : values()) {
					if (Objects.equals(${freeMarkerTool.getSchemaVarName(enumName)}.getValue(), value)) {
						return ${freeMarkerTool.getSchemaVarName(enumName)};
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

			private ${enumName}(String value) {
				_value = value;
			}

			private final String _value;

		}
	</#list>

	<#assign properties = freeMarkerTool.getDTOProperties(configYAML, openAPIYAML, schema) />

	<#list properties?keys as propertyName>
		<#assign
			propertySchema = freeMarkerTool.getDTOPropertySchema(propertyName, schema)
			propertyType = properties[propertyName]
		/>

		<#if propertySchema.maximum??>
			@DecimalMax("${propertySchema.maximum}")
		</#if>
		<#if propertySchema.minimum??>
			@DecimalMin("${propertySchema.minimum}")
		</#if>

		@Schema(
			<#if propertySchema.description??>
				description = "${propertySchema.description}"
			</#if>

			<#if propertySchema.example??>
				<#if propertySchema.description??>
					,
				</#if>

				example = "${propertySchema.example}"
			</#if>
		)

		<#if !["Boolean", "Boolean[]", "Date", "Date[]", "Double", "Double[]", "Integer", "Integer[]", "Long", "Long[]", "String", "String[]"]?seq_contains(propertyType)>
			@Valid
		</#if>

		<#assign capitalizedPropertyName = propertyName?cap_first />

		<#if enumSchemas?keys?seq_contains(propertyType)>
			<#assign capitalizedPropertyName = propertyType />
		</#if>

		public ${propertyType} get${capitalizedPropertyName}() {
			return ${propertyName};
		}

		<#if enumSchemas?keys?seq_contains(propertyType)>
			@JsonIgnore
			public String get${capitalizedPropertyName}AsString() {
				if (${propertyName} == null) {
					return null;
				}

				return ${propertyName}.toString();
			}
		</#if>

		public void set${capitalizedPropertyName}(${propertyType} ${propertyName}) {
			this.${propertyName} = ${propertyName};
		}

		@JsonIgnore
		public void set${capitalizedPropertyName}(UnsafeSupplier<${propertyType}, Exception> ${propertyName}UnsafeSupplier) {
			try {
				${propertyName} = ${propertyName}UnsafeSupplier.get();
			}
			catch (RuntimeException re) {
				throw re;
			}
			catch (Exception e) {
				throw new RuntimeException(e);
			}
		}

		<#if propertySchema.deprecated>
			@Deprecated
		</#if>
		@GraphQLField(
			<#if propertySchema.description??>
				description = "${propertySchema.description}"
			</#if>
		)
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
			<#if stringUtil.equals(propertyType, "String")>
				@NotEmpty
			<#else>
				@NotNull
			</#if>
		</#if>
		protected ${propertyType} ${propertyName};
	</#list>

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof ${schemaName})) {
			return false;
		}

		${schemaName} ${schemaVarName} = (${schemaName})object;

		return Objects.equals(toString(), ${schemaVarName}.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		StringBundler sb = new StringBundler();

		sb.append("{");

		<#assign
			enumSchemas = freeMarkerTool.getDTOEnumSchemas(openAPIYAML, schema)
			properties = freeMarkerTool.getDTOProperties(configYAML, openAPIYAML, schema)
		/>

		<#list properties?keys as propertyName>
			<#assign propertyType = properties[propertyName] />

			<#if stringUtil.equals(propertyType, "Date") || stringUtil.equals(propertyType, "Date[]")>
				DateFormat liferayToJSONDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");

				<#break>
			</#if>
		</#list>

		<#list properties?keys as propertyName>
			<#assign propertyType = properties[propertyName] />

			if (${propertyName} != null) {
				if (sb.length() > 1) {
					sb.append(", ");
				}

				sb.append("\"${propertyName}\": ");

				<#if allSchemas[propertyType]??>
					sb.append(String.valueOf(${propertyName}));
				<#else>
					<#if propertyType?contains("[]")>
						sb.append("[");

						for (int i = 0; i < ${propertyName}.length; i++) {
							<#if stringUtil.equals(propertyType, "Date[]") || stringUtil.equals(propertyType, "Object[]") || stringUtil.equals(propertyType, "String[]") || enumSchemas?keys?seq_contains(propertyType)>
								sb.append("\"");

								<#if stringUtil.equals(propertyType, "Date[]")>
									sb.append(liferayToJSONDateFormat.format(${propertyName}[i]));
								<#elseif stringUtil.equals(propertyType, "Object[]") || stringUtil.equals(propertyType, "String[]")>
									sb.append(_escape(${propertyName}[i]));
								<#else>
									sb.append(${propertyName}[i]);
								</#if>

								sb.append("\"");
							<#elseif stringUtil.startsWith(propertyType, "Map<")>
								sb.append(_toJSON(${propertyName}[i]));
							<#elseif allSchemas[propertyType?remove_ending("[]")]??>
								sb.append(String.valueOf(${propertyName}[i]));
							<#else>
								sb.append(${propertyName}[i]);
							</#if>

							if ((i + 1) < ${propertyName}.length) {
								sb.append(", ");
							}
						}

						sb.append("]");
					<#else>
						<#if stringUtil.equals(propertyType, "Date") || stringUtil.equals(propertyType, "Object") || stringUtil.equals(propertyType, "String") || enumSchemas?keys?seq_contains(propertyType)>
							sb.append("\"");

							<#if stringUtil.equals(propertyType, "Date")>
								sb.append(liferayToJSONDateFormat.format(${propertyName}));
							<#elseif stringUtil.equals(propertyType, "Object") || stringUtil.equals(propertyType, "String")>
								sb.append(_escape(${propertyName}));
							<#else>
								sb.append(${propertyName});
							</#if>

							sb.append("\"");
						<#elseif stringUtil.startsWith(propertyType, "Map<")>
							sb.append(_toJSON(${propertyName}));
						<#else>
							sb.append(${propertyName});
						</#if>
					</#if>
				</#if>
			}
		</#list>

		sb.append("}");

		return sb.toString();
	}

	@Schema(defaultValue = "${configYAML.apiPackagePath}.dto.${escapedVersion}.${schemaName}", name = "x-class-name")
	public String xClassName;

	private static String _escape(Object object) {
		String string = String.valueOf(object);

		return string.replaceAll("\"", "\\\\\"");
	}

	private static String _toJSON(Map<String, ?> map) {
		StringBuilder sb = new StringBuilder("{");

		@SuppressWarnings("unchecked")
		Set set = map.entrySet();

		@SuppressWarnings("unchecked")
		Iterator<Map.Entry<String, ?>> iterator = set.iterator();

		while (iterator.hasNext()) {
			Map.Entry<String, ?> entry = iterator.next();

			sb.append("\"");
			sb.append(entry.getKey());
			sb.append("\":");
			sb.append("\"");
			sb.append(entry.getValue());
			sb.append("\"");

			if (iterator.hasNext()) {
				sb.append(",");
			}
		}

		sb.append("}");

		return sb.toString();
	}

}