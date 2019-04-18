package ${configYAML.apiPackagePath}.client.serdes.${escapedVersion};

<#list allSchemas?keys as schemaName>
	import ${configYAML.apiPackagePath}.client.dto.${escapedVersion}.${schemaName};
</#list>

import ${configYAML.apiPackagePath}.client.json.BaseJSONParser;

import java.math.BigDecimal;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
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
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		<#assign
			enumSchemas = freeMarkerTool.getDTOEnumSchemas(schema)
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

			if (${schemaVarName}.get${propertyName?cap_first}() != null) {
				if (sb.length() > 1) {
					sb.append(", ");
				}

				sb.append("\"${propertyName}\":");

				<#if allSchemas[propertyType]??>
					sb.append(${propertyType}SerDes.toJSON(${schemaVarName}.get${propertyName?cap_first}()));
				<#else>
					<#if propertyType?contains("[]")>
						sb.append("[");

						for (int i = 0; i < ${schemaVarName}.get${propertyName?cap_first}().length; i++) {
							<#if stringUtil.equals(propertyType, "Date[]") || stringUtil.equals(propertyType, "Object[]") || stringUtil.equals(propertyType, "String[]") || enumSchemas?keys?seq_contains(propertyType)>
								sb.append("\"");

								<#if stringUtil.equals(propertyType, "Date[]")>
									sb.append(liferayToJSONDateFormat.format(${schemaVarName}.get${propertyName?cap_first}()[i]));
								<#elseif stringUtil.equals(propertyType, "Object[]") || stringUtil.equals(propertyType, "String[]")>
									sb.append(_escape(${schemaVarName}.get${propertyName?cap_first}()[i]));
								<#else>
									sb.append(${schemaVarName}.get${propertyName?cap_first}()[i]);
								</#if>

								sb.append("\"");
							<#elseif allSchemas[propertyType?remove_ending("[]")]??>
								sb.append(${propertyType?remove_ending("[]")}SerDes.toJSON(${schemaVarName}.get${propertyName?cap_first}()[i]));
							<#else>
								sb.append(${schemaVarName}.get${propertyName?cap_first}()[i]);
							</#if>

							if ((i + 1) < ${schemaVarName}.get${propertyName?cap_first}().length) {
								sb.append(", ");
							}
						}

						sb.append("]");
					<#else>
						<#if stringUtil.equals(propertyType, "Date") || stringUtil.equals(propertyType, "Object") || stringUtil.equals(propertyType, "String") || enumSchemas?keys?seq_contains(propertyType)>
							sb.append("\"");

							<#if stringUtil.equals(propertyType, "Date")>
								sb.append(liferayToJSONDateFormat.format(${schemaVarName}.get${propertyName?cap_first}()));
							<#elseif stringUtil.equals(propertyType, "Object") || stringUtil.equals(propertyType, "String")>
								sb.append(_escape(${schemaVarName}.get${propertyName?cap_first}()));
							<#else>
								sb.append(${schemaVarName}.get${propertyName?cap_first}());
							</#if>

							sb.append("\"");
						<#else>
							sb.append(${schemaVarName}.get${propertyName?cap_first}());
						</#if>
					</#if>
				</#if>
			}
		</#list>

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, String> toMap(${schemaName} ${schemaVarName}) {
		if (${schemaVarName} == null) {
			return null;
		}

		Map<String, String> map = new HashMap<>();

		<#assign
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

			<#if allSchemas[propertyType]??>
				if (${schemaVarName}.get${propertyName?cap_first}() == null) {
					map.put("${propertyName}", null);
				}
				else {
					map.put("${propertyName}", ${propertyType}SerDes.toJSON(${schemaVarName}.get${propertyName?cap_first}()));
				}
			<#elseif stringUtil.equals(propertyType, "Date")>
				map.put("${propertyName}", liferayToJSONDateFormat.format(${schemaVarName}.get${propertyName?cap_first}()));
			<#else>
				if (${schemaVarName}.get${propertyName?cap_first}() == null) {
					map.put("${propertyName}", null);
				}
				else {
					map.put("${propertyName}", String.valueOf(${schemaVarName}.get${propertyName?cap_first}()));
				}
			</#if>
		</#list>

		return map;
	}

	private static String _escape(Object object) {
		String string = String.valueOf(object);

		return string.replaceAll("\"", "\\\\\"");
	}

	private static class ${schemaName}JSONParser extends BaseJSONParser<${schemaName}> {

		@Override
		protected ${schemaName} createDTO() {
			return new ${schemaName}();
		}

		@Override
		protected ${schemaName}[] createDTOArray(int size) {
			return new ${schemaName}[size];
		}

		@Override
		protected void setField(${schemaName} ${schemaVarName}, String jsonParserFieldName, Object jsonParserFieldValue) {
			<#list properties?keys as propertyName>
				<#if !propertyName?is_first>
					else
				</#if>

				if (Objects.equals(jsonParserFieldName, "${propertyName}")) {
					if (jsonParserFieldValue != null) {
						${schemaVarName}.set${propertyName?cap_first}(

						<#assign propertyType = properties[propertyName] />

						<#if stringUtil.equals(propertyType, "Date")>
							toDate((String)jsonParserFieldValue)
						<#elseif stringUtil.equals(propertyType, "Date[]")>
							toDates((Object[])jsonParserFieldValue)
						<#elseif stringUtil.equals(propertyType, "Integer")>
							Integer.valueOf((String)jsonParserFieldValue)
						<#elseif stringUtil.equals(propertyType, "Integer[]")>
							toIntegers((Object[])jsonParserFieldValue)
						<#elseif stringUtil.equals(propertyType, "Long")>
							Long.valueOf((String)jsonParserFieldValue)
						<#elseif stringUtil.equals(propertyType, "Long[]")>
							toLongs((Object[])jsonParserFieldValue)
						<#elseif stringUtil.equals(propertyType, "Number")>
							Integer.valueOf((String)jsonParserFieldValue)
						<#elseif stringUtil.equals(propertyType, "Number[]")>
							toIntegers((Object[])jsonParserFieldValue)
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