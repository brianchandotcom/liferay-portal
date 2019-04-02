package ${configYAML.apiPackagePath}.client.serdes.${escapedVersion};

<#list allSchemas?keys as schemaName>
	import ${configYAML.apiPackagePath}.client.dto.${escapedVersion}.${schemaName};
</#list>

import ${configYAML.apiPackagePath}.client.json.BaseJSONParser;

import java.math.BigDecimal;

import java.util.Collection;
import java.util.Date;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Stream;

import javax.annotation.Generated;

/**
 * @author ${configYAML.author}
 * @generated
 */
@Generated("")
public class ${schemaName}SerDes {

	public static ${schemaName} toDTO(String json) {
		${schemaName}JSONParser ${schemaVarName}JSONParser = new ${schemaName}JSONParser();

		return ${schemaVarName}JSONParser.parseToDTO(json);
	}

	public static ${schemaName}[] toDTOs(String json) {
		${schemaName}JSONParser ${schemaVarName}JSONParser = new ${schemaName}JSONParser();

		return ${schemaVarName}JSONParser.parseToDTOs(json);
	}

	public static String toJSON(${schemaName} ${schemaVarName}) {
		if (${schemaVarName} == null) {
			return "{}";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		<#assign
			enumSchemas = freeMarkerTool.getDTOEnumSchemas(schema)
			properties = freeMarkerTool.getDTOProperties(configYAML, openAPIYAML, schema)
		/>

		<#list properties?keys as propertyName>
			<#if !propertyName?is_first>
				sb.append(", ");
			</#if>

			sb.append("\"${propertyName}\": ");

			<#if properties[propertyName]?contains("[]")>
				if (${schemaVarName}.get${propertyName?cap_first}() == null) {
					sb.append("null");
				}
				else {
					sb.append("[");

					for (int i = 0; i < ${schemaVarName}.get${propertyName?cap_first}().length; i++) {
						<#if stringUtil.equals(properties[propertyName], "Date[]") || stringUtil.equals(properties[propertyName], "String[]") || enumSchemas?keys?seq_contains(properties[propertyName])>
							sb.append("\"");
							sb.append(${schemaVarName}.get${propertyName?cap_first}()[i]);
							sb.append("\"");
						<#else>
							sb.append(${schemaVarName}.get${propertyName?cap_first}()[i]);
						</#if>

						if ((i + 1) < ${schemaVarName}.get${propertyName?cap_first}().length) {
							sb.append(", ");
						}
					}

					sb.append("]");
				}
			<#else>
				<#if properties[propertyName]?ends_with("Date") || properties[propertyName]?ends_with("String") || enumSchemas?keys?seq_contains(properties[propertyName])>
					sb.append("\"");
					sb.append(${schemaVarName}.get${propertyName?cap_first}());
					sb.append("\"");
				<#else>
					sb.append(${schemaVarName}.get${propertyName?cap_first}());
				</#if>
			</#if>
		</#list>

		sb.append("}");

		return sb.toString();
	}

	public static String toJSON(Collection<${schemaName}> ${schemaVarNames}) {
		if (${schemaVarNames} == null) {
			return "[]";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("[");

		for (${schemaName} ${schemaVarName} : ${schemaVarNames}) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append(toJSON(${schemaVarName}));
		}

		sb.append("]");

		return sb.toString();
	}

	private static class ${schemaName}JSONParser extends BaseJSONParser<${schemaName}> {

		protected ${schemaName} createDTO() {
			return new ${schemaName}();
		}

		protected ${schemaName}[] createDTOArray(int size) {
			return new ${schemaName}[size];
		}

		protected void setField(${schemaName} ${schemaVarName}, String jsonParserFieldName, Object jsonParserFieldValue) {
			<#list properties?keys as propertyName>
				<#if !propertyName?is_first>
					else
				</#if>

				if (Objects.equals(jsonParserFieldName, "${propertyName}")) {
					if (jsonParserFieldValue != null) {
						${schemaVarName}.set${propertyName?cap_first}(

						<#assign
							propertyType = properties[propertyName]
						/>

						<#if stringUtil.equals(propertyType, "Date[]")>
							toDates((Object[])jsonParserFieldValue)
						<#elseif stringUtil.equals(propertyType, "Integer[]")>
							toIntegers((Object[])jsonParserFieldValue)
						<#elseif stringUtil.equals(propertyType, "Long[]")>
							toLongs((Object[])jsonParserFieldValue)
						<#elseif stringUtil.equals(propertyType, "String[]")>
							toStrings((Object[])jsonParserFieldValue)
						<#elseif allSchemas?keys?seq_contains(propertyType)>
							${propertyType}SerDes.toDTO((String)jsonParserFieldValue)
						<#elseif propertyType?ends_with("[]") && allSchemas?keys?seq_contains(propertyType?remove_ending("[]"))>
							Stream.of(
								toStrings((Object[])jsonParserFieldValue)
							).map(
								object -> ${propertyType?remove_ending("[]")}SerDes.toDTO((String)object)
							).toArray(
								size -> new ${propertyType?remove_ending("[]")}[size]
							)
						<#elseif enumSchemas?keys?seq_contains(properties[propertyName])>
							${schemaName}.${propertyType}.create((String)jsonParserFieldValue)
						<#else>
							(${propertyType})jsonParserFieldValue
						</#if>

						);
					}
				}
			</#list>

			else {
				throw new IllegalArgumentException("Unsupported field name " + jsonParserFieldName);
			}
		}

	}

}