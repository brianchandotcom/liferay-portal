/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.mcp.server.rest.internal.util;

import com.liferay.mcp.server.rest.dto.v1_0.Tool;
import com.liferay.mcp.server.rest.dto.v1_0.ToolSummary;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.LinkedHashMapBuilder;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.URLCodec;
import com.liferay.portal.kernel.util.Validator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

/**
 * @author Alejandro Tardín
 */
public class OpenAPIUtil {

	public static Http.Options getOptions(
		String baseURL, JSONObject inputJSONObject,
		JSONObject openAPIJSONObject, String toolName) {

		Http.Options options = new Http.Options();

		Operation operation = _getOperation(openAPIJSONObject, toolName);

		String url = baseURL + _getPath(inputJSONObject, operation);

		String queryString = _getQueryString(inputJSONObject, operation);

		if (!queryString.isEmpty()) {
			url = url + StringPool.QUESTION + queryString;
		}

		options.setLocation(url);
		options.setMethod(
			Http.Method.valueOf(StringUtil.toUpperCase(operation._method)));

		if (_isMultipartRequest(operation._operationJSONObject)) {
			_setMultipartBody(
				inputJSONObject, openAPIJSONObject, operation, options);
		}
		else if (operation._operationJSONObject.has("requestBody")) {
			_setBody(inputJSONObject, options, toolName);
		}

		return options;
	}

	public static Tool getTool(JSONObject openAPIJSONObject, String toolName) {
		Operation operation = _getOperation(openAPIJSONObject, toolName);

		return new Tool() {
			{
				setDescription(
					() -> _getDescription(
						operation._method, operation._operationJSONObject,
						operation._path));
				setInputSchema(
					() -> _getInputSchema(
						openAPIJSONObject, operation._operationJSONObject,
						operation._pathParametersJSONArray));

				setName(() -> toolName);
			}
		};
	}

	public static List<ToolSummary> getToolSummaries(
		JSONObject openAPIJSONObject) {

		JSONObject pathsJSONObject = openAPIJSONObject.getJSONObject("paths");

		if (pathsJSONObject == null) {
			throw new IllegalArgumentException(
				"OpenAPI document has no \"paths\" object");
		}

		List<ToolSummary> toolSummaries = new ArrayList<>();

		for (String path : pathsJSONObject.keySet()) {
			JSONObject pathItemJSONObject = pathsJSONObject.getJSONObject(path);

			for (String method : _METHODS) {
				JSONObject operationJSONObject =
					pathItemJSONObject.getJSONObject(method);

				if (operationJSONObject == null) {
					continue;
				}

				toolSummaries.add(
					new ToolSummary() {
						{
							setDescription(
								() -> _getDescription(
									method, operationJSONObject, path));
							setName(
								() -> operationJSONObject.getString(
									"operationId"));
						}
					});
			}
		}

		return toolSummaries;
	}

	private static void _addMultipartParts(
		JSONObject operationJSONObject, Map<String, Object> properties,
		List<String> requiredPropertyNames, JSONObject openAPIJSONObject) {

		Map<String, Object> bodySchemaMap =
			(Map<String, Object>)_getSchemaObject(
				openAPIJSONObject,
				_getBodySchemaJSONObject(operationJSONObject), new HashSet<>());

		Map<String, Object> bodyProperties =
			(Map<String, Object>)bodySchemaMap.get("properties");

		if (bodyProperties == null) {
			return;
		}

		for (Map.Entry<String, Object> entry : bodyProperties.entrySet()) {
			Map<String, Object> propertySchemaMap =
				(Map<String, Object>)entry.getValue();

			properties.put(
				entry.getKey(),
				_isBinary(propertySchemaMap) ?
					_getBinarySchemaMap(propertySchemaMap) : propertySchemaMap);
		}

		requiredPropertyNames.addAll(
			TransformUtil.transform(
				(List<Object>)bodySchemaMap.get("required"), String::valueOf));
	}

	private static void _addParameter(
		JSONObject parameterJSONObject, Map<String, Object> properties,
		List<String> requiredPropertyNames, Set<String> visitedParameterNames) {

		String name = parameterJSONObject.getString("name");

		if (_vulcanFieldSelectionParameterNames.contains(name) ||
			!visitedParameterNames.add(name)) {

			return;
		}

		Map<String, Object> parameterProperties = new LinkedHashMap<>();

		JSONObject parameterSchemaJSONObject =
			parameterJSONObject.getJSONObject("schema");

		for (String key : parameterSchemaJSONObject.keySet()) {
			parameterProperties.put(key, parameterSchemaJSONObject.get(key));
		}

		if (parameterJSONObject.has("description")) {
			parameterProperties.put(
				"description", parameterJSONObject.getString("description"));
		}

		properties.put(name, parameterProperties);

		if (Objects.equals(parameterJSONObject.getString("in"), "path")) {
			requiredPropertyNames.add(name);
		}
	}

	private static void _addParameters(
		JSONArray parametersJSONArray, Map<String, Object> properties,
		List<String> requiredPropertyNames, Set<String> visitedParameterNames) {

		if (parametersJSONArray == null) {
			return;
		}

		for (int i = 0; i < parametersJSONArray.length(); i++) {
			_addParameter(
				parametersJSONArray.getJSONObject(i), properties,
				requiredPropertyNames, visitedParameterNames);
		}
	}

	private static void _appendQueryParameter(
		String name, StringBundler sb, Object value) {

		if (value == null) {
			return;
		}

		String stringValue = String.valueOf(value);

		if (stringValue.isEmpty()) {
			return;
		}

		if (sb.length() > 0) {
			sb.append(CharPool.AMPERSAND);
		}

		sb.append(URLCodec.encodeURL(name));
		sb.append(CharPool.EQUAL);
		sb.append(URLCodec.encodeURL(stringValue));
	}

	private static Map<String, Object> _getAllOfSchemaMap(
		JSONObject jsonObject, JSONObject openAPIJSONObject,
		Set<String> visitedRefs) {

		Map<String, Object> allOfSchemaMap = new LinkedHashMap<>();
		Map<String, Object> properties = new LinkedHashMap<>();
		Set<String> requiredPropertyNames = new HashSet<>();

		for (String key : jsonObject.keySet()) {
			if (key.equals("allOf") || _excludedSchemaKeys.contains(key) ||
				key.startsWith("x-")) {

				continue;
			}

			allOfSchemaMap.put(
				key,
				_getSchemaObject(
					openAPIJSONObject, jsonObject.get(key), visitedRefs));
		}

		JSONArray allOfJSONArray = jsonObject.getJSONArray("allOf");

		for (int i = 0; i < allOfJSONArray.length(); i++) {
			Object schemaObject = _getSchemaObject(
				openAPIJSONObject, allOfJSONArray.getJSONObject(i),
				visitedRefs);

			if (!(schemaObject instanceof Map)) {
				continue;
			}

			Map<String, Object> schemaMap = (Map<String, Object>)schemaObject;

			Map<String, Object> schemaProperties =
				(Map<String, Object>)schemaMap.get("properties");

			if (schemaProperties != null) {
				properties.putAll(schemaProperties);
			}

			requiredPropertyNames.addAll(
				TransformUtil.transform(
					(List<Object>)schemaMap.get("required"), String::valueOf));

			for (Map.Entry<String, Object> entry : schemaMap.entrySet()) {
				String key = entry.getKey();

				if (allOfSchemaMap.containsKey(key) ||
					key.equals("properties") || key.equals("required")) {

					continue;
				}

				allOfSchemaMap.put(key, entry.getValue());
			}
		}

		if (!properties.isEmpty()) {
			allOfSchemaMap.put("properties", properties);
		}

		if (!requiredPropertyNames.isEmpty()) {
			allOfSchemaMap.put("required", List.copyOf(requiredPropertyNames));
		}

		if (!allOfSchemaMap.containsKey("type")) {
			allOfSchemaMap.put("type", "object");
		}

		return allOfSchemaMap;
	}

	private static Map<String, Object> _getBinarySchemaMap(
		Map<String, Object> schemaMap) {

		return HashMapBuilder.<String, Object>put(
			"description",
			() -> {
				String extraDescription =
					"Provide as an object with `data` (base64-encoded bytes)" +
						", and optional `filename` and `contentType`.";

				String description = (String)schemaMap.get("description");

				if (Validator.isNotNull(description)) {
					return description + ". " + extraDescription;
				}

				return extraDescription;
			}
		).put(
			"properties",
			HashMapBuilder.<String, Object>put(
				"contentType",
				HashMapBuilder.<String, Object>put(
					"description", "MIME type of the file content."
				).put(
					"type", "string"
				).build()
			).put(
				"data",
				HashMapBuilder.<String, Object>put(
					"description", "Base64-encoded file bytes."
				).put(
					"type", "string"
				).build()
			).put(
				"filename",
				HashMapBuilder.<String, Object>put(
					"description", "Original file name."
				).put(
					"type", "string"
				).build()
			).build()
		).put(
			"required", Arrays.asList("data")
		).put(
			"type", "object"
		).build();
	}

	private static JSONObject _getBodySchemaJSONObject(
		JSONObject operationJSONObject) {

		JSONObject requestBodyJSONObject = operationJSONObject.getJSONObject(
			"requestBody");

		JSONObject contentJSONObject = requestBodyJSONObject.getJSONObject(
			"content");

		if (contentJSONObject == null) {
			throw new IllegalArgumentException(
				"Request body has no \"content\"");
		}

		JSONObject mediaTypeJSONObject = contentJSONObject.getJSONObject(
			"application/json");

		if (mediaTypeJSONObject == null) {
			for (String mediaType : contentJSONObject.keySet()) {
				mediaTypeJSONObject = contentJSONObject.getJSONObject(
					mediaType);

				if (mediaTypeJSONObject != null) {
					break;
				}
			}
		}

		if (mediaTypeJSONObject == null) {
			throw new IllegalArgumentException("Request body has no content");
		}

		JSONObject bodySchemaJSONObject = mediaTypeJSONObject.getJSONObject(
			"schema");

		if (bodySchemaJSONObject == null) {
			throw new IllegalArgumentException(
				"Request body content has no \"schema\"");
		}

		return bodySchemaJSONObject;
	}

	private static String _getDescription(
		String method, JSONObject operationJSONObject, String path) {

		boolean hasDescription = operationJSONObject.has("description");
		boolean hasSummary = operationJSONObject.has("summary");

		if (hasDescription && hasSummary) {
			return operationJSONObject.getString("summary") + ". " +
				operationJSONObject.getString("description");
		}

		if (hasDescription) {
			return operationJSONObject.getString("description");
		}

		if (hasSummary) {
			return operationJSONObject.getString("summary");
		}

		return StringUtil.toUpperCase(method) + StringPool.SPACE + path;
	}

	private static Http.FilePart _getFilePart(String name, Object value) {
		String fileName = name;
		String contentType = ContentTypes.APPLICATION_OCTET_STREAM;
		String data;

		JSONObject jsonObject = null;

		if (value instanceof JSONObject) {
			jsonObject = (JSONObject)value;
		}
		else if (value instanceof Map) {
			jsonObject = JSONFactoryUtil.createJSONObject(
				(Map<String, ?>)value);
		}

		if (jsonObject != null) {
			if (jsonObject.has("filename")) {
				fileName = jsonObject.getString("filename");
			}

			if (jsonObject.has("contentType")) {
				contentType = jsonObject.getString("contentType");
			}

			data = jsonObject.getString("data");
		}
		else {
			data = String.valueOf(value);
		}

		if (Validator.isNull(data)) {
			throw new IllegalArgumentException(
				"Multipart part \"" + name + "\" has no \"data\"");
		}

		Base64.Decoder decoder = Base64.getDecoder();

		byte[] bytes = decoder.decode(data);

		return new Http.FilePart(
			name, fileName, bytes, contentType, StringPool.UTF8);
	}

	private static Map<String, Object> _getInputSchema(
		JSONObject openAPIJSONObject, JSONObject operationJSONObject,
		JSONArray pathParametersJSONArray) {

		Map<String, Object> properties = new LinkedHashMap<>();
		List<String> requiredPropertyNames = new ArrayList<>();

		if (operationJSONObject.has("requestBody")) {
			if (_isMultipartRequest(operationJSONObject)) {
				_addMultipartParts(
					operationJSONObject, properties, requiredPropertyNames,
					openAPIJSONObject);
			}
			else {
				JSONObject requestBodyJSONObject =
					operationJSONObject.getJSONObject("requestBody");

				Map<String, Object> bodySchemaMap = null;

				if (requestBodyJSONObject.getJSONObject("content") != null) {
					Object bodySchemaObject = _getSchemaObject(
						openAPIJSONObject,
						_getBodySchemaJSONObject(operationJSONObject),
						new HashSet<>());

					if (bodySchemaObject instanceof Map) {
						bodySchemaMap = (Map<String, Object>)bodySchemaObject;
					}
				}

				if (bodySchemaMap == null) {
					bodySchemaMap = LinkedHashMapBuilder.<String, Object>put(
						"type", "object"
					).build();
				}

				String requestBodyDescription = requestBodyJSONObject.getString(
					"description");

				if (Validator.isNotNull(requestBodyDescription)) {
					Object bodyDescription = bodySchemaMap.get("description");

					if (Validator.isNull(bodyDescription)) {
						bodySchemaMap.put(
							"description", requestBodyDescription);
					}
					else if (!Objects.equals(
								bodyDescription, requestBodyDescription)) {

						bodySchemaMap.put(
							"description",
							StringBundler.concat(
								requestBodyDescription, StringPool.SPACE,
								bodyDescription));
					}
				}

				properties.put("body", bodySchemaMap);

				requiredPropertyNames.add("body");
			}
		}

		Set<String> visitedParameterNames = new HashSet<>();

		_addParameters(
			operationJSONObject.getJSONArray("parameters"), properties,
			requiredPropertyNames, visitedParameterNames);
		_addParameters(
			pathParametersJSONArray, properties, requiredPropertyNames,
			visitedParameterNames);

		return LinkedHashMapBuilder.<String, Object>put(
			"properties", properties
		).put(
			"required", requiredPropertyNames
		).put(
			"type", "object"
		).build();
	}

	private static Map<String, Object> _getOneOfSchemaMap(
		JSONObject jsonObject, JSONObject openAPIJSONObject, String ref,
		Set<String> visitedRefs) {

		List<Object> oneOfSchemaObjects = new ArrayList<>();

		JSONObject schemasJSONObject = JSONUtil.getValueAsJSONObject(
			openAPIJSONObject, "JSONObject/components", "JSONObject/schemas");

		if (schemasJSONObject != null) {
			for (String schemaName : schemasJSONObject.keySet()) {
				JSONObject schemaJSONObject = schemasJSONObject.getJSONObject(
					schemaName);

				JSONArray allOfJSONArray = schemaJSONObject.getJSONArray(
					"allOf");

				if (allOfJSONArray == null) {
					continue;
				}

				boolean extendsBase = false;

				for (int i = 0; i < allOfJSONArray.length(); i++) {
					JSONObject memberJSONObject = allOfJSONArray.getJSONObject(
						i);

					if (memberJSONObject == null) {
						continue;
					}

					if (ref.equals(memberJSONObject.getString("$ref"))) {
						extendsBase = true;

						break;
					}
				}

				if (!extendsBase) {
					continue;
				}

				String subtypeRef = "#/components/schemas/" + schemaName;

				if (visitedRefs.contains(subtypeRef)) {
					continue;
				}

				Set<String> subtypeVisitedRefs = new HashSet<>(visitedRefs);

				subtypeVisitedRefs.add(subtypeRef);

				oneOfSchemaObjects.add(
					_getSchemaObject(
						openAPIJSONObject, schemaJSONObject,
						subtypeVisitedRefs));
			}
		}

		if (oneOfSchemaObjects.isEmpty()) {
			Map<String, Object> schemaMap = new LinkedHashMap<>();

			for (String key : jsonObject.keySet()) {
				if (_excludedSchemaKeys.contains(key) || key.startsWith("x-")) {
					continue;
				}

				schemaMap.put(
					key,
					_getSchemaObject(
						openAPIJSONObject, jsonObject.get(key), visitedRefs));
			}

			return schemaMap;
		}

		return LinkedHashMapBuilder.<String, Object>put(
			"oneOf", oneOfSchemaObjects
		).build();
	}

	private static Operation _getOperation(
		JSONObject openAPIJSONObject, String toolName) {

		JSONObject pathsJSONObject = openAPIJSONObject.getJSONObject("paths");

		if (pathsJSONObject == null) {
			throw new IllegalArgumentException(
				"OpenAPI document has no \"paths\" object");
		}

		for (String path : pathsJSONObject.keySet()) {
			JSONObject pathJSONObject = pathsJSONObject.getJSONObject(path);

			for (String method : _METHODS) {
				JSONObject operationJSONObject = pathJSONObject.getJSONObject(
					method);

				if ((operationJSONObject == null) ||
					!toolName.equals(
						operationJSONObject.getString("operationId"))) {

					continue;
				}

				return new Operation(
					method, operationJSONObject, path,
					pathJSONObject.getJSONArray("parameters"));
			}
		}

		throw new IllegalArgumentException(
			"OpenAPI document has no tool with name \"" + toolName + "\"");
	}

	private static Map<String, Object> _getParameterSchemaObjects(
		String in, JSONObject inputJSONObject, Operation operation) {

		Map<String, Object> parameterSchemaObjects = new LinkedHashMap<>();

		for (JSONArray parametersJSONArray :
				Arrays.asList(
					operation._operationJSONObject.getJSONArray("parameters"),
					operation._pathParametersJSONArray)) {

			if (parametersJSONArray == null) {
				continue;
			}

			for (int i = 0; i < parametersJSONArray.length(); i++) {
				JSONObject parameterJSONObject =
					parametersJSONArray.getJSONObject(i);

				if (!Objects.equals(parameterJSONObject.getString("in"), in)) {
					continue;
				}

				String name = parameterJSONObject.getString("name");

				if (parameterSchemaObjects.containsKey(name) ||
					!inputJSONObject.has(name)) {

					continue;
				}

				Object value = inputJSONObject.get(name);

				if (value == null) {
					continue;
				}

				parameterSchemaObjects.put(name, value);
			}
		}

		return parameterSchemaObjects;
	}

	private static String _getPart(Object value) {
		if (value instanceof JSONArray || value instanceof JSONObject) {
			return value.toString();
		}

		if (value instanceof Map) {
			return JSONFactoryUtil.createJSONObject(
				(Map<String, ?>)value
			).toString();
		}

		if (value instanceof List) {
			return JSONFactoryUtil.createJSONArray(
				(List<?>)value
			).toString();
		}

		return String.valueOf(value);
	}

	private static String _getPath(
		JSONObject inputJSONObject, Operation operation) {

		Map<String, Object> parameterSchemaObjects = _getParameterSchemaObjects(
			"path", inputJSONObject, operation);

		String path = operation._path;

		for (Map.Entry<String, Object> entry :
				parameterSchemaObjects.entrySet()) {

			path = StringUtil.replace(
				path, "{" + entry.getKey() + "}",
				URLCodec.encodeURL(String.valueOf(entry.getValue())));
		}

		return path;
	}

	private static String _getQueryString(
		JSONObject inputJSONObject, Operation operation) {

		StringBundler sb = new StringBundler();

		Map<String, Object> parameterSchemaObjects = _getParameterSchemaObjects(
			"query", inputJSONObject, operation);

		for (Map.Entry<String, Object> entry :
				parameterSchemaObjects.entrySet()) {

			String name = entry.getKey();

			if (_vulcanFieldSelectionParameterNames.contains(name)) {
				continue;
			}

			_appendQueryParameter(name, sb, entry.getValue());
		}

		if (Objects.equals(operation._method, "get")) {
			_appendQueryParameter("restrictFields", sb, "actions");
		}

		return sb.toString();
	}

	private static JSONObject _getRefJSONObject(
		String ref, JSONObject openAPIJSONObject) {

		JSONObject refJSONObject = openAPIJSONObject;

		for (String part : StringUtil.split(ref.substring(2), CharPool.SLASH)) {
			refJSONObject = refJSONObject.getJSONObject(part);
		}

		return refJSONObject;
	}

	private static Object _getSchemaObject(
		JSONObject openAPIJSONObject, Object value, Set<String> visitedRefs) {

		if (value instanceof JSONObject) {
			JSONObject jsonObject = (JSONObject)value;

			if (jsonObject.has("$ref")) {
				String ref = jsonObject.getString("$ref");

				if (visitedRefs.contains(ref)) {
					return HashMapBuilder.<String, Object>put(
						"type", "object"
					).build();
				}

				JSONObject refJSONObject = _getRefJSONObject(
					ref, openAPIJSONObject);

				Set<String> currentVisitedRefs = new HashSet<>(visitedRefs);

				currentVisitedRefs.add(ref);

				if (refJSONObject.has("discriminator")) {
					return _getOneOfSchemaMap(
						refJSONObject, openAPIJSONObject, ref,
						currentVisitedRefs);
				}

				return _getSchemaObject(
					openAPIJSONObject, refJSONObject, currentVisitedRefs);
			}

			if (jsonObject.has("allOf")) {
				return _getAllOfSchemaMap(
					jsonObject, openAPIJSONObject, visitedRefs);
			}

			Map<String, Object> schemaMap = new LinkedHashMap<>();

			for (String key : jsonObject.keySet()) {
				if (_excludedSchemaKeys.contains(key) || key.startsWith("x-")) {
					continue;
				}

				schemaMap.put(
					key,
					_getSchemaObject(
						openAPIJSONObject, jsonObject.get(key), visitedRefs));
			}

			return schemaMap;
		}

		if (value instanceof JSONArray jsonArray) {
			return TransformUtil.transform(
				JSONUtil.toObjectList(jsonArray),
				object -> _getSchemaObject(
					openAPIJSONObject, object, visitedRefs));
		}

		return value;
	}

	private static boolean _isBinary(Map<String, Object> schemaMap) {
		if (schemaMap == null) {
			return false;
		}

		return Objects.equals(schemaMap.get("format"), "binary");
	}

	private static boolean _isMultipartRequest(JSONObject operationJSONObject) {
		JSONObject contentJSONObject = JSONUtil.getValueAsJSONObject(
			operationJSONObject, "JSONObject/requestBody",
			"JSONObject/content");

		if ((contentJSONObject == null) ||
			contentJSONObject.has("application/json")) {

			return false;
		}

		return contentJSONObject.has("multipart/form-data");
	}

	private static void _setBody(
		JSONObject inputJSONObject, Http.Options options, String toolName) {

		if (!inputJSONObject.has("body")) {
			throw new IllegalArgumentException(
				StringBundler.concat(
					"The \"", toolName,
					"\" tool requires the request payload nested under a ",
					"\"body\" property. Pass any path or query parameters as ",
					"siblings of \"body\" rather than flattening the payload ",
					"into the input map."));
		}

		options.addHeader("Content-Type", ContentTypes.APPLICATION_JSON);

		String body = StringPool.BLANK;

		Object bodyObject = inputJSONObject.get("body");

		if (bodyObject instanceof JSONObject) {
			body = bodyObject.toString();
		}
		else if (bodyObject != null) {
			body = String.valueOf(bodyObject);
		}

		options.setBody(body, ContentTypes.APPLICATION_JSON, StringPool.UTF8);
	}

	private static void _setMultipartBody(
		JSONObject inputJSONObject, JSONObject openAPIJSONObject,
		Operation operation, Http.Options options) {

		Map<String, Object> bodySchemaMap =
			(Map<String, Object>)_getSchemaObject(
				openAPIJSONObject,
				_getBodySchemaJSONObject(operation._operationJSONObject),
				new HashSet<>());

		Map<String, Object> partProperties =
			(Map<String, Object>)bodySchemaMap.get("properties");

		if (partProperties == null) {
			return;
		}

		List<Http.FilePart> fileParts = new ArrayList<>();
		Map<String, String> parts = new LinkedHashMap<>();

		for (Map.Entry<String, Object> entry : partProperties.entrySet()) {
			String partName = entry.getKey();

			if (!inputJSONObject.has(partName)) {
				continue;
			}

			Object value = inputJSONObject.get(partName);

			if (value == null) {
				continue;
			}

			Map<String, Object> partSchema =
				(Map<String, Object>)entry.getValue();

			if (Objects.equals(partSchema.get("format"), "binary")) {
				fileParts.add(_getFilePart(partName, value));
			}
			else {
				parts.put(partName, _getPart(value));
			}
		}

		if (!fileParts.isEmpty()) {
			options.setFileParts(fileParts);
		}

		if (!parts.isEmpty()) {
			options.setParts(parts);
		}

		if (!fileParts.isEmpty() || !parts.isEmpty()) {
			options.addHeader(
				"Content-Type",
				"multipart/form-data; boundary=" + UUID.randomUUID());
		}
	}

	private static final String[] _METHODS = {
		"delete", "get", "head", "options", "patch", "post", "put"
	};

	private static final Set<String> _excludedSchemaKeys = Set.of(
		"actions", "example", "exclusiveMaximum", "exclusiveMinimum", "xml");
	private static final Set<String> _vulcanFieldSelectionParameterNames =
		Set.of("fields", "restrictFields");

	private static class Operation {

		private Operation(
			String method, JSONObject operationJSONObject, String path,
			JSONArray pathParametersJSONArray) {

			_method = method;
			_operationJSONObject = operationJSONObject;
			_path = path;
			_pathParametersJSONArray = pathParametersJSONArray;
		}

		private final String _method;
		private final JSONObject _operationJSONObject;
		private final String _path;
		private final JSONArray _pathParametersJSONArray;

	}

}