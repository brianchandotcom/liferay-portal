/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.mcp.server.rest.internal.util;

import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.introspect.BeanPropertyDefinition;

import com.liferay.mcp.server.rest.dto.v1_0.Tool;
import com.liferay.mcp.server.rest.dto.v1_0.ToolSet;
import com.liferay.mcp.server.rest.dto.v1_0.ToolSummary;
import com.liferay.object.rest.dto.v1_0.ObjectEntry;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.module.service.Snapshot;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.vulcan.application.HeadlessApplicationProvider;
import com.liferay.portal.vulcan.http.VulcanRequestForwarder;
import com.liferay.portal.vulcan.jackson.databind.ObjectMapperProviderUtil;
import com.liferay.portal.vulcan.pagination.Page;

import jakarta.servlet.http.HttpServletRequest;

import jakarta.ws.rs.core.Response;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Alejandro Tardín
 */
public class ToolSetUtil {

	public static void clearOpenAPIJSONObjectCache(long companyId) {
		Set<String> keys = _openAPIJSONObjects.keySet();

		keys.removeIf(key -> key.startsWith(companyId + StringPool.POUND));
	}

	public static Tool getTool(
		HttpServletRequest httpServletRequest, boolean requiredInputSchemaOnly,
		Map<String, String> restrictFieldsMap, String toolName,
		String toolSetName) {

		JSONObject openAPIJSONObject = _getOpenAPIJSONObject(
			httpServletRequest, _getOpenAPIDocument(toolSetName), toolSetName);

		return OpenAPIUtil.getTool(
			!Objects.equals(toolSetName, _MCP_SERVER_TOOL_SET_NAME),
			inputSchema -> {
				if (!requiredInputSchemaOnly) {
					return inputSchema;
				}

				return _toRequiredInputSchema(
					inputSchema, _isObjectOpenAPI(openAPIJSONObject));
			},
			openAPIJSONObject,
			_getRestrictFields(restrictFieldsMap, toolName, toolSetName),
			toolName);
	}

	public static String getToolKey(String toolName, String toolSetName) {
		return toolSetName + StringPool.POUND + toolName;
	}

	public static Map<String, ?> getToolOutputSchema(
		HttpServletRequest httpServletRequest, String toolName,
		String toolSetName) {

		return OpenAPIUtil.getOutputSchema(
			_getOpenAPIJSONObject(
				httpServletRequest, _getOpenAPIDocument(toolSetName),
				toolSetName),
			toolName);
	}

	public static Page<ToolSet> getToolSetsPage() {
		Map<String, HeadlessApplicationProvider.OpenAPIDocument>
			openAPIDocuments = _getOpenAPIDocuments();

		return Page.of(
			TransformUtil.transform(
				openAPIDocuments.entrySet(),
				entry -> new ToolSet() {
					{
						setDescription(
							() -> {
								HeadlessApplicationProvider.OpenAPIDocument
									openAPIDocument = entry.getValue();

								return openAPIDocument.getDescription();
							});

						setName(entry::getKey);
					}
				}));
	}

	public static Page<ToolSummary> getToolSummariesPage(
		HttpServletRequest httpServletRequest, String toolSetName) {

		return Page.of(
			OpenAPIUtil.getToolSummaries(
				_getOpenAPIJSONObject(
					httpServletRequest, _getOpenAPIDocument(toolSetName),
					toolSetName)));
	}

	public static Response invokeTool(
			List<String> dataMaskExternalReferenceCodes,
			HttpServletRequest httpServletRequest, Object inputObject,
			Map<String, String> restrictFieldsMap, String toolName,
			String toolSetName)
		throws Exception {

		JSONObject inputJSONObject = null;

		if (inputObject instanceof JSONObject) {
			inputJSONObject = (JSONObject)inputObject;
		}
		else if (inputObject instanceof Map) {
			inputJSONObject = JSONFactoryUtil.createJSONObject(
				(Map<String, ?>)inputObject);
		}
		else {
			inputJSONObject = JSONFactoryUtil.createJSONObject();
		}

		if (Objects.equals(toolSetName, _MCP_SERVER_TOOL_SET_NAME)) {
			if (Objects.equals(toolName, "getToolSetToolSetNameTool")) {
				return _getResponse(
					getTool(
						httpServletRequest,
						inputJSONObject.getBoolean("requiredInputSchemaOnly"),
						restrictFieldsMap,
						inputJSONObject.getString("toolName"),
						inputJSONObject.getString("toolSetName")));
			}

			if (Objects.equals(
					toolName, "getToolSetToolSetNameToolSummariesPage")) {

				return _getResponse(
					getToolSummariesPage(
						httpServletRequest,
						inputJSONObject.getString("toolSetName")));
			}

			if (Objects.equals(toolName, "getToolSetsPage")) {
				return _getResponse(getToolSetsPage());
			}

			if (Objects.equals(toolName, "postToolSetToolSetNameToolInvoke")) {
				return invokeTool(
					dataMaskExternalReferenceCodes, httpServletRequest,
					inputJSONObject.opt("body"), restrictFieldsMap,
					inputJSONObject.getString("toolName"),
					inputJSONObject.getString("toolSetName"));
			}
		}

		VulcanRequestForwarder vulcanRequestForwarder =
			_vulcanRequestForwarderSnapshot.get();

		HeadlessApplicationProvider.OpenAPIDocument openAPIDocument =
			_getOpenAPIDocument(toolSetName);

		HeadlessApplicationProvider.Application application =
			openAPIDocument.getApplication();

		VulcanRequestForwarder.Response response =
			vulcanRequestForwarder.forward(
				httpServletRequest,
				OpenAPIUtil.getRequest(
					application.getBasePath(),
					HashMapBuilder.put(
						"X-Liferay-Data-Masks",
						() -> StringUtil.merge(
							dataMaskExternalReferenceCodes, StringPool.COMMA)
					).build(),
					inputJSONObject,
					_getOpenAPIJSONObject(
						httpServletRequest, openAPIDocument, toolSetName),
					_getRestrictFields(
						restrictFieldsMap, toolName, toolSetName),
					toolName,
					UserLocalServiceUtil.fetchUser(
						GetterUtil.getLong(
							httpServletRequest.getAttribute(
								WebKeys.USER_ID)))));

		String content = response.getContent();

		return Response.status(
			response.getStatusCode()
		).entity(
			Validator.isNull(content) ? null : _getContent(content)
		).type(
			ContentTypes.TEXT_PLAIN_UTF8
		).build();
	}

	private static Map<String, Object> _collapseNestedProperties(
		Map<String, Object> schema) {

		Map<String, Object> properties = (Map<String, Object>)schema.get(
			"properties");

		if (properties == null) {
			return schema;
		}

		Map<String, Object> collapsedProperties = new LinkedHashMap<>();

		for (Map.Entry<String, Object> entry : properties.entrySet()) {
			Object value = entry.getValue();

			if ((value instanceof Map<?, ?> valueMap) &&
				(valueMap.containsKey("items") ||
				 valueMap.containsKey("properties"))) {

				collapsedProperties.put(
					entry.getKey(),
					HashMapBuilder.<String, Object>put(
						"type", valueMap.get("type")
					).build());
			}
			else {
				collapsedProperties.put(entry.getKey(), value);
			}
		}

		return HashMapBuilder.<String, Object>putAll(
			schema
		).put(
			"properties", collapsedProperties
		).build();
	}

	private static String _getContent(String content) {
		if (Validator.isNull(content) || (content.charAt(0) != '{') ||
			!content.contains("\"actions\"")) {

			return content;
		}

		try {
			JSONObject jsonObject = JSONFactoryUtil.createJSONObject(content);

			if (!jsonObject.has("actions")) {
				return content;
			}

			jsonObject.remove("actions");

			return jsonObject.toString();
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception);
			}

			return content;
		}
	}

	private static Set<String> _getObjectEntrySystemPropertyNames() {
		if (_objectEntrySystemPropertyNames != null) {
			return _objectEntrySystemPropertyNames;
		}

		ObjectMapper objectMapper = ObjectMapperProviderUtil.getObjectMapper();

		SerializationConfig serializationConfig =
			objectMapper.getSerializationConfig();

		BeanDescription beanDescription = serializationConfig.introspect(
			serializationConfig.constructType(ObjectEntry.class));

		Set<String> objectEntrySystemPropertyNames = new HashSet<>();

		for (BeanPropertyDefinition beanPropertyDefinition :
				beanDescription.findProperties()) {

			objectEntrySystemPropertyNames.add(
				beanPropertyDefinition.getName());
		}

		_objectEntrySystemPropertyNames = Collections.unmodifiableSet(
			objectEntrySystemPropertyNames);

		return _objectEntrySystemPropertyNames;
	}

	private static HeadlessApplicationProvider.OpenAPIDocument
		_getOpenAPIDocument(String toolSetName) {

		Map<String, HeadlessApplicationProvider.OpenAPIDocument>
			openAPIDocuments = _getOpenAPIDocuments();

		HeadlessApplicationProvider.OpenAPIDocument openAPIDocument =
			openAPIDocuments.get(toolSetName);

		if (openAPIDocument == null) {
			throw new IllegalArgumentException(
				"No tool set was found with name \"" + toolSetName + "\"");
		}

		return openAPIDocument;
	}

	private static Map<String, HeadlessApplicationProvider.OpenAPIDocument>
		_getOpenAPIDocuments() {

		Map<String, HeadlessApplicationProvider.OpenAPIDocument>
			openAPIDocuments = new TreeMap<>();

		HeadlessApplicationProvider headlessApplicationProvider =
			_headlessApplicationProviderSnapshot.get();

		for (HeadlessApplicationProvider.Application application :
				headlessApplicationProvider.getApplications()) {

			if (Validator.isNull(application.getBasePath()) ||
				Objects.equals(application.getBasePath(), "/openapi")) {

				continue;
			}

			for (HeadlessApplicationProvider.OpenAPIDocument openAPIDocument :
					application.getOpenAPIDocuments()) {

				String apiPath = application.getBasePath();

				String version = openAPIDocument.getVersion();

				if (version != null) {
					apiPath += StringPool.SLASH + version;
				}

				openAPIDocuments.putIfAbsent(
					StringUtil.replace(
						apiPath.substring(1), CharPool.SLASH, CharPool.DASH),
					openAPIDocument);
			}
		}

		return openAPIDocuments;
	}

	private static JSONObject _getOpenAPIJSONObject(
		HttpServletRequest httpServletRequest,
		HeadlessApplicationProvider.OpenAPIDocument openAPIDocument,
		String toolSetName) {

		return _openAPIJSONObjects.computeIfAbsent(
			StringBundler.concat(
				PortalUtil.getCompanyId(httpServletRequest), StringPool.POUND,
				openAPIDocument.getPath(
					HeadlessApplicationProvider.OpenAPIDocument.Type.JSON)),
			key -> {
				String content = openAPIDocument.getContentString(
					PortalUtil.getPortalURL(httpServletRequest) +
						PortalUtil.getPathContext() + Portal.PATH_MODULE,
					HeadlessApplicationProvider.OpenAPIDocument.Type.JSON);

				if (Validator.isNull(content)) {
					throw new IllegalStateException(
						"Unable to read the OpenAPI document of the \"" +
							toolSetName + "\" tool set");
				}

				try {
					return JSONFactoryUtil.createJSONObject(content);
				}
				catch (JSONException jsonException) {
					throw new IllegalStateException(
						StringBundler.concat(
							"Unable to parse the OpenAPI document of the \"",
							toolSetName, "\" tool set"),
						jsonException);
				}
			});
	}

	private static Response _getResponse(Object value) throws Exception {
		ObjectMapper objectMapper = ObjectMapperProviderUtil.getObjectMapper();

		return Response.ok(
			objectMapper.writeValueAsString(value), ContentTypes.TEXT_PLAIN_UTF8
		).build();
	}

	private static String _getRestrictFields(
		Map<String, String> restrictFieldsMap, String toolName,
		String toolSetName) {

		if (restrictFieldsMap == null) {
			return null;
		}

		return restrictFieldsMap.get(getToolKey(toolName, toolSetName));
	}

	private static boolean _isObjectOpenAPI(JSONObject openAPIJSONObject) {
		JSONObject infoJSONObject = openAPIJSONObject.getJSONObject("info");

		if (infoJSONObject == null) {
			return false;
		}

		return Objects.equals(infoJSONObject.getString("title"), "Object");
	}

	private static Map<String, Object> _removeObjectEntrySystemProperties(
		Map<String, Object> schema) {

		Map<String, Object> properties = (Map<String, Object>)schema.get(
			"properties");

		Map<String, Object> customProperties = new LinkedHashMap<>(properties);

		Set<String> keys = customProperties.keySet();

		keys.removeAll(_getObjectEntrySystemPropertyNames());

		return HashMapBuilder.<String, Object>putAll(
			schema
		).put(
			"properties", customProperties
		).build();
	}

	private static Map<String, Object> _toRequiredInputSchema(
		Map<String, ?> inputSchema, boolean objectToolSet) {

		Map<String, Object> requiredInputSchema = _toRequiredSchema(
			0, objectToolSet, inputSchema);

		if (requiredInputSchema == null) {
			requiredInputSchema = HashMapBuilder.<String, Object>put(
				"properties", new HashMap<String, Object>()
			).put(
				"type", "object"
			).build();
		}

		Map<String, ?> properties = (Map<String, ?>)inputSchema.get(
			"properties");

		if (properties.containsKey("fields")) {
			Map<String, Object> requiredProperties =
				(Map<String, Object>)requiredInputSchema.get("properties");

			requiredProperties.put("fields", properties.get("fields"));
		}

		return requiredInputSchema;
	}

	private static Map<String, Object> _toRequiredPropertySchema(
		int depth, boolean objectToolSet, Map<String, Object> propertySchema) {

		Map<String, Object> requiredSchema = _toRequiredSchema(
			depth + 1, objectToolSet, propertySchema);

		if (requiredSchema != null) {
			Object description = propertySchema.get("description");

			if (description != null) {
				requiredSchema.put("description", description);
			}

			return requiredSchema;
		}

		if (!propertySchema.containsKey("properties")) {
			return propertySchema;
		}

		if (objectToolSet && (depth == 0)) {
			propertySchema = _removeObjectEntrySystemProperties(propertySchema);
		}

		return _collapseNestedProperties(propertySchema);
	}

	private static Map<String, Object> _toRequiredSchema(
		int depth, boolean objectToolSet, Map<String, ?> schema) {

		if ((schema == null) || (depth > _MAX_SCHEMA_DEPTH)) {
			return null;
		}

		List<String> requiredPropertyNames = (List<String>)schema.get(
			"required");

		Map<String, Object> properties = (Map<String, Object>)schema.get(
			"properties");

		if (ListUtil.isEmpty(requiredPropertyNames) || (properties == null)) {
			return null;
		}

		Map<String, Object> requiredPropertySchemas = new LinkedHashMap<>();

		for (String requiredPropertyName : requiredPropertyNames) {
			if (properties.get(requiredPropertyName) instanceof
					Map<?, ?> propertySchema) {

				requiredPropertySchemas.put(
					requiredPropertyName,
					_toRequiredPropertySchema(
						depth, objectToolSet,
						(Map<String, Object>)propertySchema));
			}
		}

		if (requiredPropertySchemas.isEmpty()) {
			return null;
		}

		return HashMapBuilder.<String, Object>put(
			"properties", requiredPropertySchemas
		).put(
			"required", new ArrayList<>(requiredPropertySchemas.keySet())
		).put(
			"type", schema.get("type")
		).build();
	}

	private static final int _MAX_SCHEMA_DEPTH = 4;

	private static final String _MCP_SERVER_TOOL_SET_NAME = "mcp-server-v1.0";

	private static final Log _log = LogFactoryUtil.getLog(ToolSetUtil.class);

	private static final Snapshot<HeadlessApplicationProvider>
		_headlessApplicationProviderSnapshot = new Snapshot<>(
			ToolSetUtil.class, HeadlessApplicationProvider.class);
	private static volatile Set<String> _objectEntrySystemPropertyNames;
	private static final Map<String, JSONObject> _openAPIJSONObjects =
		new ConcurrentHashMap<>();
	private static final Snapshot<VulcanRequestForwarder>
		_vulcanRequestForwarderSnapshot = new Snapshot<>(
			ToolSetUtil.class, VulcanRequestForwarder.class);

}