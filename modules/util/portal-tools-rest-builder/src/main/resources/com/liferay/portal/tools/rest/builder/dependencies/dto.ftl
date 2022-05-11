package ${configYAML.apiPackagePath}.dto.${escapedVersion};

<#list globalEnumSchemas?keys as globalEnumSchemaName>
	import ${configYAML.apiPackagePath}.constant.${escapedVersion}.${globalEnumSchemaName};
</#list>

<#list allExternalSchemas?keys as externalSchemaName>
	<#if javaDataTypeMap?keys?seq_contains(externalSchemaName)>
		import ${javaDataTypeMap[externalSchemaName]};
	</#if>
</#list>

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonValue;

import com.liferay.petra.function.UnsafeSupplier;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.vulcan.graphql.annotation.GraphQLField;
import com.liferay.portal.vulcan.graphql.annotation.GraphQLName;
import com.liferay.portal.vulcan.util.ObjectMapperUtil;

import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serializable;

import java.math.BigDecimal;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.HashMap;
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

import javax.xml.bind.annotation.XmlElement;
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
@GraphQLName(
	<#if schema.description?has_content>
		description = "${schema.description?j_string}", value = "${schemaName}"
	<#else>
		"${schemaName}"
	</#if>
)
@JsonFilter("Liferay.Vulcan")
<#if schema.requiredPropertySchemaNames?has_content>
	@Schema(
		<#if schema.deprecated>
			deprecated = ${schema.deprecated?c},
		</#if>
		requiredProperties =
			{
				<#list schema.requiredPropertySchemaNames as requiredProperty>
					"${requiredProperty}"
					<#if requiredProperty_has_next>
						,
					</#if>
				</#list>
			}
		<#if schema.description??>
			, description = "${schema.description?j_string}"
		</#if>
	)
</#if>
@XmlRootElement(name = "${schemaName}")
public class ${schemaName} <#if dtoParentClassName?has_content>extends ${dtoParentClassName}</#if> implements Serializable {

	public static ${schemaName} toDTO(String json) {
		return ObjectMapperUtil.readValue(${schemaName}.class, json);
	}

	public static ${schemaName} unsafeToDTO(String json) {
		return ObjectMapperUtil.unsafeReadValue(${schemaName}.class, json);
	}

	<#assign
		enumSchemas = freeMarkerTool.getDTOEnumSchemas(openAPIYAML, schema)
		properties = freeMarkerTool.getDTOProperties(configYAML, openAPIYAML, schema)
	/>

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

		<#if propertySchema.jsonMap>
			@JsonAnyGetter
		</#if>

		@Schema(
			<#if propertySchema.deprecated>
				deprecated = ${propertySchema.deprecated?c}
			</#if>

			<#if propertySchema.description??>
				<#if propertySchema.deprecated>
					,
				</#if>
				description = "${propertySchema.description?j_string}"
			</#if>

			<#if propertySchema.example??>
				<#if propertySchema.deprecated || propertySchema.description??>
					,
				</#if>

				example = "${freeMarkerTool.getObjectFieldStringValue(propertyType, propertySchema.example)}"
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
				description = "${propertySchema.description?j_string}"
			</#if>
		)
		<#if propertySchema.jsonMap>
			@JsonAnySetter
		</#if>
		@JsonProperty(
			<#if propertySchema.readOnly>
				access = JsonProperty.Access.READ_ONLY
			<#elseif propertySchema.writeOnly>
				access = JsonProperty.Access.WRITE_ONLY
			<#else>
				access = JsonProperty.Access.READ_WRITE
			</#if>

			<#if propertySchema.name?? && !stringUtil.equals(propertyName, propertySchema.name)>
				, value = "${propertySchema.name}"
			</#if>
		)
		<#if propertySchema.xml??>
			@XmlElement(name = "${propertySchema.xml.name}")
		</#if>
		<#if schema.requiredPropertySchemaNames?? && schema.requiredPropertySchemaNames?seq_contains(propertyName)>
			<#if stringUtil.equals(propertyType, "String")>
				@NotEmpty
			<#else>
				@NotNull
			</#if>
		</#if>
		protected ${propertyType} ${propertyName}<#if propertySchema.jsonMap> = new HashMap<>()</#if>;
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

		<#list properties?keys as propertyName>
			<#assign propertyType = properties[propertyName] />

			<#if stringUtil.equals(propertyType, "Date") || stringUtil.equals(propertyType, "Date[]")>
				DateFormat liferayToJSONDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");

				<#break>
			</#if>
		</#list>

		<#list properties?keys as propertyName>
			<#assign
				propertySchema = freeMarkerTool.getDTOPropertySchema(propertyName, schema)
				propertyType = properties[propertyName]
			/>

			if (${propertyName} != null) {
				if (sb.length() > 1) {
					sb.append(", ");
				}

				<#if propertySchema.name?? && !stringUtil.equals(propertyName, propertySchema.name)>
					<#assign key = propertySchema.name />
				<#else>
					<#assign key = propertyName />
				</#if>

				sb.append("\"${key}\": ");

				<#if allSchemas[propertyType]??>
					sb.append(String.valueOf(${propertyName}));
				<#elseif stringUtil.equals(propertyType, "Object")>
					if (${propertyName} instanceof Map) {
						sb.append(JSONFactoryUtil.createJSONObject((Map<?, ?>)${propertyName}));
					}
					else if (${propertyName} instanceof String) {
						sb.append("\"");
						sb.append(_escape((String)${propertyName}));
						sb.append("\"");
					}
					else {
						sb.append(${propertyName});
					}
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
						<#if stringUtil.equals(propertyType, "Date") || stringUtil.equals(propertyType, "String") || enumSchemas?keys?seq_contains(propertyType)>
							sb.append("\"");

							<#if stringUtil.equals(propertyType, "Date")>
								sb.append(liferayToJSONDateFormat.format(${propertyName}));
							<#elseif stringUtil.equals(propertyType, "String")>
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

	@Schema(accessMode = Schema.AccessMode.READ_ONLY, defaultValue = "${configYAML.apiPackagePath}.dto.${escapedVersion}.${schemaName}", name = "x-class-name")
	public String xClassName;

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
			if ((value == null) || value.equals("")) {
				return null;
			}

			for (${enumName} ${freeMarkerTool.getSchemaVarName(enumName)} : values()) {
				if (Objects.equals(${freeMarkerTool.getSchemaVarName(enumName)}.getValue(), value)) {
					return ${freeMarkerTool.getSchemaVarName(enumName)};
				}
			}

			throw new IllegalArgumentException("Invalid enum value: " + value);
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

	private static String _escape(Object object) {
		return StringUtil.replace(String.valueOf(object), _JSON_ESCAPE_STRINGS[0], _JSON_ESCAPE_STRINGS[1]);
	}

	private static boolean _isArray(Object value) {
		if (value == null) {
			return false;
		}

		Class<?> clazz = value.getClass();

		return clazz.isArray();
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
			sb.append(_escape(entry.getKey()));
			sb.append("\": ");

			Object value = entry.getValue();

			if (_isArray(value)) {
				sb.append("[");

				Object[] valueArray = (Object[]) value;

				for (int i = 0; i < valueArray.length; i++) {
					if (valueArray[i] instanceof String) {
						sb.append("\"");
						sb.append(valueArray[i]);
						sb.append("\"");
					}
					else {
						sb.append(valueArray[i]);
					}

					if ((i + 1) < valueArray.length) {
						sb.append(", ");
					}
				}

				sb.append("]");
			}
			else if (value instanceof Map) {
				sb.append(_toJSON((Map<String, ?>) value));
			}
			else if (value instanceof String) {
				sb.append("\"");
				sb.append(_escape(value));
				sb.append("\"");
			}
			else {
				sb.append(value);
			}

			if (iterator.hasNext()) {
				sb.append(", ");
			}
		}

		sb.append("}");

		return sb.toString();
	}

	private static final String[][] _JSON_ESCAPE_STRINGS = {
		{"\\", "\"", "\b", "\f", "\n", "\r", "\t"},
		{"\\\\", "\\\"", "\\b", "\\f", "\\n", "\\r", "\\t"}
	};

}