/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.mcp.server.internal.util;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.URLCodec;

import io.modelcontextprotocol.spec.McpSchema;

import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * @author Alejandro Tardín
 */
public class OpenAPIUtil {

	public static HttpCallArguments getHttpCallArguments(
		Map<String, Object> arguments, String baseURL, String endpoint) {

		int index = endpoint.indexOf(' ');

		String path = endpoint.substring(index + 1);

		StringBundler sb = new StringBundler();

		for (Map.Entry<String, Object> entry : arguments.entrySet()) {
			String name = entry.getKey();

			if (name.equals("body")) {
				continue;
			}

			Object value = entry.getValue();

			if (value == null) {
				continue;
			}

			String placeholder = "{" + name + "}";
			String valueString = String.valueOf(value);

			if (path.contains(placeholder)) {
				path = StringUtil.replace(path, placeholder, valueString);
			}
			else if (!valueString.isEmpty()) {
				sb.append((sb.index() == 0) ? '?' : '&');
				sb.append(URLCodec.encodeURL(name));
				sb.append('=');
				sb.append(URLCodec.encodeURL(valueString));
			}
		}

		String bodyString = null;

		Object body = arguments.get("body");

		if (body instanceof Map bodyMap) {
			bodyString = JSONFactoryUtil.createJSONObject(
				bodyMap
			).toString();
		}
		else if (body != null) {
			bodyString = body.toString();
		}

		return new HttpCallArguments(
			bodyString, endpoint.substring(0, index), baseURL + path + sb);
	}

	public static String getOpenAPIURL(String endpoint) {
		return _getBasePath(endpoint) + "/openapi.json";
	}

	public static McpSchema.Tool getTool(String endpoint, String openAPIJSON)
		throws Exception {

		JSONObject rootJSONObject = JSONFactoryUtil.createJSONObject(
			openAPIJSON);

		JSONObject pathsJSONObject = rootJSONObject.getJSONObject("paths");

		if (pathsJSONObject == null) {
			throw new IllegalArgumentException(
				"OpenAPI document has no 'paths' object");
		}

		String basePath = _getBasePath(endpoint);

		int spaceIndex = endpoint.indexOf(' ');

		String method = endpoint.substring(0, spaceIndex);

		String path = endpoint.substring(spaceIndex + 1);

		String nameSegment = basePath.substring(0, basePath.indexOf('/', 1));

		String pathSuffix = path.substring(nameSegment.length());

		JSONObject pathItemJSONObject = pathsJSONObject.getJSONObject(
			pathSuffix);

		if (pathItemJSONObject == null) {
			pathSuffix = path.substring(basePath.length());

			if (pathSuffix.isEmpty()) {
				pathSuffix = "/";
			}

			pathItemJSONObject = pathsJSONObject.getJSONObject(pathSuffix);
		}

		if (pathItemJSONObject == null) {
			throw new IllegalArgumentException(
				"OpenAPI document has no path item for: " + pathSuffix);
		}

		JSONObject operationJSONObject = pathItemJSONObject.getJSONObject(
			StringUtil.toLowerCase(method));

		if (operationJSONObject == null) {
			throw new IllegalArgumentException(
				"OpenAPI path item has no operation for method: " + method);
		}

		McpSchema.JsonSchema inputJsonSchema = _getInputJsonSchema(
			operationJSONObject, pathItemJSONObject.getJSONArray("parameters"),
			rootJSONObject);

		return McpSchema.Tool.builder(
		).name(
			operationJSONObject.getString("operationId")
		).description(
			_getToolDescription(method, operationJSONObject, path)
		).inputSchema(
			inputJsonSchema
		).build();
	}

	public static class HttpCallArguments {

		public String getBody() {
			return _body;
		}

		public String getMethod() {
			return _method;
		}

		public String getUrl() {
			return _url;
		}

		private HttpCallArguments(String body, String method, String url) {
			_body = body;
			_method = method;
			_url = url;
		}

		private final String _body;
		private final String _method;
		private final String _url;

	}

	private static void _addParameter(
		JSONObject parameterJSONObject, Set<String> processedParameterNames,
		JSONObject propertiesJSONObject, JSONArray requiredJSONArray) {

		String name = parameterJSONObject.getString("name");

		if (!processedParameterNames.add(name)) {
			return;
		}

		JSONObject schemaJSONObject = parameterJSONObject.getJSONObject(
			"schema");

		JSONObject parameterPropertyJSONObject =
			JSONFactoryUtil.createJSONObject();

		for (String key : schemaJSONObject.keySet()) {
			parameterPropertyJSONObject.put(key, schemaJSONObject.get(key));
		}

		propertiesJSONObject.put(name, parameterPropertyJSONObject);

		if (parameterJSONObject.has("description")) {
			parameterPropertyJSONObject.put(
				"description", parameterJSONObject.getString("description"));
		}

		if (Objects.equals(parameterJSONObject.getString("in"), "path")) {
			requiredJSONArray.put(name);
		}
	}

	private static void _addParameters(
		JSONArray parametersJSONArray, Set<String> processedParameterNames,
		JSONObject propertiesJSONObject, JSONArray requiredJSONArray) {

		if (parametersJSONArray == null) {
			return;
		}

		for (int i = 0; i < parametersJSONArray.length(); i++) {
			_addParameter(
				parametersJSONArray.getJSONObject(i), processedParameterNames,
				propertiesJSONObject, requiredJSONArray);
		}
	}

	private static String _getBasePath(String endpoint) {
		int spaceIndex = endpoint.indexOf(' ');

		if (spaceIndex < 0) {
			throw new IllegalArgumentException(
				"Endpoint has no method/path separator: " + endpoint);
		}

		String path = endpoint.substring(spaceIndex + 1);

		int firstSlashIndex = path.indexOf('/', 1);

		if (firstSlashIndex < 0) {
			throw new IllegalArgumentException(
				"Endpoint has no base path: " + endpoint);
		}

		int secondSlashIndex = path.indexOf('/', firstSlashIndex + 1);

		if (secondSlashIndex > 0) {
			return path.substring(0, secondSlashIndex);
		}

		return path;
	}

	private static JSONObject _getBodySchemaJSONObject(
		JSONObject operationJSONObject) {

		JSONObject jsonObject = operationJSONObject.getJSONObject(
			"requestBody"
		).getJSONObject(
			"content"
		).getJSONObject(
			"application/json"
		);

		if (jsonObject == null) {
			throw new IllegalArgumentException(
				"Request body has no application/json content");
		}

		JSONObject schemaJSONObject = jsonObject.getJSONObject("schema");

		if (schemaJSONObject == null) {
			throw new IllegalArgumentException(
				"Request body application/json has no schema");
		}

		return schemaJSONObject;
	}

	private static McpSchema.JsonSchema _getInputJsonSchema(
			JSONObject operationJSONObject, JSONArray pathParametersJSONArray,
			JSONObject rootJSONObject)
		throws Exception {

		JSONObject propertiesJSONObject = JSONFactoryUtil.createJSONObject();
		JSONArray requiredJSONArray = JSONFactoryUtil.createJSONArray();

		if (operationJSONObject.has("requestBody")) {
			propertiesJSONObject.put(
				"body",
				(JSONObject)_resolveRefs(
					rootJSONObject,
					_getBodySchemaJSONObject(operationJSONObject),
					new HashSet<>()));

			requiredJSONArray.put("body");
		}

		Set<String> processedParameterNames = new HashSet<>();

		_addParameters(
			operationJSONObject.getJSONArray("parameters"),
			processedParameterNames, propertiesJSONObject, requiredJSONArray);
		_addParameters(
			pathParametersJSONArray, processedParameterNames,
			propertiesJSONObject, requiredJSONArray);

		return _objectMapper.readValue(
			JSONUtil.put(
				"properties", propertiesJSONObject
			).put(
				"required", requiredJSONArray
			).put(
				"type", "object"
			).toString(),
			McpSchema.JsonSchema.class);
	}

	private static String _getToolDescription(
		String method, JSONObject operationJSONObject, String pathTemplate) {

		boolean hasDescription = operationJSONObject.has("description");
		boolean hasSummary = operationJSONObject.has("summary");

		if (hasDescription && hasSummary) {
			return operationJSONObject.getString("summary") + ". " +
				operationJSONObject.getString("description");
		}
		else if (hasDescription) {
			return operationJSONObject.getString("description");
		}
		else if (hasSummary) {
			return operationJSONObject.getString("summary");
		}

		return method + " " + pathTemplate;
	}

	private static JSONObject _resolveRef(
		String ref, JSONObject rootJSONObject) {

		JSONObject currentJSONObject = rootJSONObject;

		for (String part : StringUtil.split(ref.substring(2), CharPool.SLASH)) {
			currentJSONObject = currentJSONObject.getJSONObject(part);
		}

		return currentJSONObject;
	}

	private static Object _resolveRefs(
		JSONObject rootJSONObject, Object value, Set<String> visitedRefs) {

		if (value instanceof JSONObject) {
			JSONObject jsonObject = (JSONObject)value;

			if (jsonObject.has("$ref")) {
				String ref = jsonObject.getString("$ref");

				if (visitedRefs.contains(ref)) {
					return JSONUtil.put("type", "object");
				}

				Set<String> currentVisitedRefs = new HashSet<>(visitedRefs);

				currentVisitedRefs.add(ref);

				return _resolveRefs(
					rootJSONObject, _resolveRef(ref, rootJSONObject),
					currentVisitedRefs);
			}

			JSONObject resultJSONObject = JSONFactoryUtil.createJSONObject();

			for (String key : jsonObject.keySet()) {
				if (_excludedSchemaKeys.contains(key) || key.startsWith("x-")) {
					continue;
				}

				resultJSONObject.put(
					key,
					_resolveRefs(
						rootJSONObject, jsonObject.get(key), visitedRefs));
			}

			return resultJSONObject;
		}

		if (value instanceof JSONArray) {
			JSONArray jsonArray = (JSONArray)value;

			JSONArray resultJSONArray = JSONFactoryUtil.createJSONArray();

			for (int i = 0; i < jsonArray.length(); i++) {
				resultJSONArray.put(
					_resolveRefs(
						rootJSONObject, jsonArray.get(i), visitedRefs));
			}

			return resultJSONArray;
		}

		return value;
	}

	private static final Set<String> _excludedSchemaKeys = Set.of(
		"example", "exclusiveMaximum", "exclusiveMinimum", "xml");
	private static final ObjectMapper _objectMapper = new ObjectMapper();

}