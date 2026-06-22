/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.mcp.server.rest.internal.util;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.liferay.mcp.server.rest.dto.v1_0.Tool;
import com.liferay.mcp.server.rest.dto.v1_0.ToolSet;
import com.liferay.mcp.server.rest.dto.v1_0.ToolSummary;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.module.service.Snapshot;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.vulcan.jackson.databind.ObjectMapperProviderUtil;
import com.liferay.portal.vulcan.pagination.Page;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

import jakarta.servlet.http.HttpServletRequest;

import jakarta.ws.rs.core.Response;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceReference;
import org.osgi.service.jaxrs.runtime.JaxrsServiceRuntime;
import org.osgi.service.jaxrs.runtime.dto.ApplicationDTO;
import org.osgi.service.jaxrs.runtime.dto.ResourceDTO;
import org.osgi.service.jaxrs.runtime.dto.ResourceMethodInfoDTO;
import org.osgi.service.jaxrs.runtime.dto.RuntimeDTO;

/**
 * @author Alejandro Tardín
 */
public class ToolSetUtil {

	public static Tool getTool(
		HttpServletRequest httpServletRequest, String toolName,
		String toolSetName) {

		return OpenAPIUtil.getTool(
			!Objects.equals(toolSetName, _TOOL_SET_NAME),
			_getOpenAPIJSONObject(
				_getOpenAPIBrief(toolSetName), httpServletRequest),
			toolName);
	}

	public static Page<ToolSet> getToolSetsPage() {
		Map<String, OpenAPIBrief> openAPIBriefs = _getOpenAPIBriefs();

		return Page.of(
			TransformUtil.transform(
				openAPIBriefs.entrySet(),
				entry -> new ToolSet() {
					{
						setDescription(
							() -> {
								OpenAPIBrief openAPIBrief = entry.getValue();

								return openAPIBrief._description;
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
					_getOpenAPIBrief(toolSetName), httpServletRequest)));
	}

	public static Response invokeTool(
			HttpServletRequest httpServletRequest, Object inputObject,
			String toolName, String toolSetName)
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

		if (Objects.equals(toolSetName, _TOOL_SET_NAME)) {
			if (Objects.equals(toolName, "getToolSetToolSetNameTool")) {
				return _getResponse(
					getTool(
						httpServletRequest,
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
					httpServletRequest, inputJSONObject.opt("body"),
					inputJSONObject.getString("toolName"),
					inputJSONObject.getString("toolSetName"));
			}
		}

		Http.Options options = _getOptions(
			httpServletRequest, inputJSONObject, toolName, toolSetName);

		String content = _getContent(HttpUtil.URLtoString(options));

		Http.Response response = options.getResponse();

		return Response.status(
			response.getResponseCode()
		).entity(
			content
		).type(
			ContentTypes.TEXT_PLAIN_UTF8
		).build();
	}

	private static String _get(
			HttpServletRequest httpServletRequest, String url)
		throws Exception {

		Http.Options options = new Http.Options();

		Map<String, String> headers = _getHeaders(httpServletRequest);

		if (!headers.isEmpty()) {
			options.setHeaders(headers);
		}

		options.setLocation(url);
		options.setTimeout(60000);

		String content = HttpUtil.URLtoString(options);

		Http.Response response = options.getResponse();

		int responseCode = response.getResponseCode();

		if (responseCode >= 300) {
			throw new Exception(
				StringBundler.concat(
					"HTTP ", responseCode, " for ", url, ": ", content));
		}

		return content;
	}

	private static String _getBaseURL(HttpServletRequest httpServletRequest) {
		return PortalUtil.getPortalURL(httpServletRequest) +
			PortalUtil.getPathContext() + Portal.PATH_MODULE;
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

	private static String _getDescription(Object service) {
		if (service == null) {
			return null;
		}

		Class<?> serviceClass = service.getClass();

		OpenAPIDefinition openAPIDefinition = serviceClass.getAnnotation(
			OpenAPIDefinition.class);

		if (openAPIDefinition == null) {
			return null;
		}

		Info info = openAPIDefinition.info();

		String description = info.description();

		if (description == null) {
			return null;
		}

		return description;
	}

	private static Map<String, String> _getHeaders(
		HttpServletRequest httpServletRequest) {

		String authorization = httpServletRequest.getHeader("Authorization");

		if (authorization != null) {
			return HashMapBuilder.put(
				"Authorization", authorization
			).build();
		}

		Map<String, String> headers = new HashMap<>();

		String cookie = httpServletRequest.getHeader("Cookie");

		if (cookie != null) {
			headers.put("Cookie", cookie);
		}

		String csrfToken = httpServletRequest.getHeader("X-CSRF-Token");

		if (csrfToken != null) {
			headers.put("X-CSRF-Token", csrfToken);
		}

		return headers;
	}

	private static OpenAPIBrief _getOpenAPIBrief(String toolSetName) {
		Map<String, OpenAPIBrief> openAPIBriefs = _getOpenAPIBriefs();

		OpenAPIBrief openAPIBrief = openAPIBriefs.get(toolSetName);

		if (openAPIBrief == null) {
			throw new IllegalArgumentException(
				"No tool-set was found with name \"" + toolSetName + "\"");
		}

		return openAPIBrief;
	}

	private static Map<String, OpenAPIBrief> _getOpenAPIBriefs() {
		Map<String, OpenAPIBrief> openAPIBriefs = new TreeMap<>();

		JaxrsServiceRuntime jaxrsServiceRuntime =
			_jaxrsServiceRuntimeSnapshot.get();

		RuntimeDTO runtimeDTO = jaxrsServiceRuntime.getRuntimeDTO();

		Map<String, String> toolSetDescriptions = _getToolSetDescriptions();

		for (ApplicationDTO applicationDTO : runtimeDTO.applicationDTOs) {
			String base = applicationDTO.base;

			if (Validator.isNull(base)) {
				continue;
			}

			if (!base.startsWith(StringPool.SLASH)) {
				base = StringPool.SLASH + base;
			}

			String openAPIPath = _getOpenAPIPath(applicationDTO);

			if (openAPIPath == null) {
				continue;
			}

			String basePath = base + _getVersionPath(openAPIPath);

			openAPIBriefs.put(
				StringUtil.replace(
					basePath.substring(1), CharPool.SLASH, CharPool.DASH),
				new OpenAPIBrief(
					base, toolSetDescriptions.get(basePath), openAPIPath));
		}

		return openAPIBriefs;
	}

	private static JSONObject _getOpenAPIJSONObject(
		OpenAPIBrief openAPIBrief, HttpServletRequest httpServletRequest) {

		return _openAPIJSONObjectCache.computeIfAbsent(
			_getBaseURL(httpServletRequest) + openAPIBrief._basePath +
				openAPIBrief._openAPIPath,
			url -> {
				String content = null;

				try {
					content = _get(httpServletRequest, url);

					return JSONFactoryUtil.createJSONObject(content);
				}
				catch (Exception exception) {
					throw new RuntimeException(
						StringBundler.concat(
							"Unable to read a valid OpenAPI document from \"",
							url, "\": ",
							StringUtil.shorten(
								GetterUtil.getString(content), 200)),
						exception);
				}
			});
	}

	private static String _getOpenAPIPath(ApplicationDTO applicationDTO) {
		for (ResourceDTO resourceDTO : applicationDTO.resourceDTOs) {
			String openAPIPath = _getOpenAPIPath(resourceDTO.resourceMethods);

			if (openAPIPath != null) {
				return openAPIPath;
			}
		}

		return _getOpenAPIPath(applicationDTO.resourceMethods);
	}

	private static String _getOpenAPIPath(
		ResourceMethodInfoDTO[] resourceMethodInfoDTOs) {

		if (resourceMethodInfoDTOs == null) {
			return null;
		}

		for (ResourceMethodInfoDTO resourceMethodInfoDTO :
				resourceMethodInfoDTOs) {

			String path = resourceMethodInfoDTO.path;

			if ((path != null) && path.contains("/openapi")) {
				return StringUtil.replace(path, "{type:json|yaml}", "json");
			}
		}

		return null;
	}

	private static Http.Options _getOptions(
		HttpServletRequest httpServletRequest, JSONObject inputJSONObject,
		String toolName, String toolSetName) {

		OpenAPIBrief openAPIBrief = _getOpenAPIBrief(toolSetName);

		Http.Options options = OpenAPIUtil.getOptions(
			_getBaseURL(httpServletRequest) + openAPIBrief._basePath,
			inputJSONObject,
			_getOpenAPIJSONObject(openAPIBrief, httpServletRequest), toolName);

		Map<String, String> headers = _getHeaders(httpServletRequest);

		if (options.getHeaders() != null) {
			headers.putAll(options.getHeaders());
		}

		options.setHeaders(headers);
		options.setTimeout(60000);

		return options;
	}

	private static Response _getResponse(Object value) throws Exception {
		ObjectMapper objectMapper = ObjectMapperProviderUtil.getObjectMapper();

		return Response.ok(
			objectMapper.writeValueAsString(value), ContentTypes.TEXT_PLAIN_UTF8
		).build();
	}

	private static Map<String, String> _getToolSetDescriptions() {
		Map<String, String> toolSetDescriptions = new HashMap<>();

		Bundle bundle = FrameworkUtil.getBundle(ToolSetUtil.class);

		BundleContext bundleContext = bundle.getBundleContext();

		ServiceReference<?>[] serviceReferences;

		try {
			serviceReferences = bundleContext.getAllServiceReferences(
				null, "(openapi.resource=true)");
		}
		catch (InvalidSyntaxException invalidSyntaxException) {
			if (_log.isWarnEnabled()) {
				_log.warn(invalidSyntaxException);
			}

			return toolSetDescriptions;
		}

		if (serviceReferences == null) {
			return toolSetDescriptions;
		}

		for (ServiceReference<?> serviceReference : serviceReferences) {
			String path = GetterUtil.getString(
				serviceReference.getProperty("openapi.resource.path"));

			if (Validator.isNull(path)) {
				continue;
			}

			String version = GetterUtil.getString(
				serviceReference.getProperty("api.version"));

			if (Validator.isNotNull(version)) {
				path = path + StringPool.SLASH + version;
			}

			Object service = bundleContext.getService(serviceReference);

			try {
				toolSetDescriptions.putIfAbsent(path, _getDescription(service));
			}
			finally {
				bundleContext.ungetService(serviceReference);
			}
		}

		return toolSetDescriptions;
	}

	private static String _getVersionPath(String openAPIPath) {
		int index = openAPIPath.lastIndexOf("/openapi");

		if (index <= 0) {
			return StringPool.BLANK;
		}

		return openAPIPath.substring(0, index);
	}

	private static final String _TOOL_SET_NAME = "mcp-server-v1.0";

	private static final Log _log = LogFactoryUtil.getLog(ToolSetUtil.class);

	private static final Snapshot<JaxrsServiceRuntime>
		_jaxrsServiceRuntimeSnapshot = new Snapshot<>(
			ToolSetUtil.class, JaxrsServiceRuntime.class);
	private static final Map<String, JSONObject> _openAPIJSONObjectCache =
		new ConcurrentHashMap<>();

	private static class OpenAPIBrief {

		private OpenAPIBrief(
			String basePath, String description, String openAPIPath) {

			_basePath = basePath;
			_description = description;
			_openAPIPath = openAPIPath;
		}

		private final String _basePath;
		private final String _description;
		private final String _openAPIPath;

	}

}