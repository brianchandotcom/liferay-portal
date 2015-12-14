/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portal.servlet;

import com.liferay.portal.kernel.cache.PortalCache;
import com.liferay.portal.kernel.cache.SingleVMPoolUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.HttpHeaders;
import com.liferay.portal.kernel.servlet.RequestDispatcherUtil;
import com.liferay.portal.kernel.servlet.ServletResponseUtil;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.ObjectValuePair;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.minifier.MinifierUtil;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.model.PortletApp;
import com.liferay.portal.service.PortletLocalServiceUtil;
import com.liferay.portal.servlet.filters.dynamiccss.DynamicCSSUtil;
import com.liferay.portal.util.AggregateUtil;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portal.util.PrefsPropsUtil;
import com.liferay.portal.util.PropsValues;

import java.io.IOException;
import java.io.Serializable;

import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Eduardo Lundgren
 * @author Edward Han
 * @author Zsigmond Rab
 * @author Raymond Augé
 */
public class ComboServlet extends HttpServlet {

	public static void clearCache() {
		_bytesArrayPortalCache.removeAll();
		_fileContentBagPortalCache.removeAll();
	}

	@Override
	public void service(
			HttpServletRequest request, HttpServletResponse response)
		throws IOException, ServletException {

		try {
			doService(request, response);
		}
		catch (Exception e) {
			_log.error(e, e);

			PortalUtil.sendError(
				HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e, request,
				response);
		}
	}

	protected static String getModulePortletId(String modulePath) {
		int index = modulePath.indexOf(CharPool.COLON);

		if (index > 0) {
			return modulePath.substring(0, index);
		}

		return PortletKeys.PORTAL;
	}

	protected static String getResourcePath(String modulePath) {
		int index = modulePath.indexOf(CharPool.COLON);

		if (index > 0) {
			return modulePath.substring(index + 1);
		}

		return modulePath;
	}

	protected void doService(
			HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		Set<String> modulePathsSet = new LinkedHashSet<>();

		Map<String, String[]> parameterMap = HttpUtil.getParameterMap(
			request.getQueryString());

		Enumeration<String> enu = Collections.enumeration(
			parameterMap.keySet());

		while (enu.hasMoreElements()) {
			String name = enu.nextElement();

			if (_protectedParameters.contains(name)) {
				continue;
			}

			name = HttpUtil.decodePath(name);

			ServletContext servletContext = getServletContext();

			String contextPath = servletContext.getContextPath();

			if (name.startsWith(contextPath)) {
				name = name.replaceFirst(contextPath, StringPool.BLANK);
			}

			modulePathsSet.add(name);
		}

		if (modulePathsSet.isEmpty()) {
			throw new IllegalArgumentException(
				"Query string translates to an empty module paths set");
		}

		String[] modulePaths = modulePathsSet.toArray(
			new String[modulePathsSet.size()]);

		String firstModulePath = modulePaths[0];

		String extension = FileUtil.getExtension(firstModulePath);

		String minifierType = ParamUtil.getString(request, "minifierType");

		if (Validator.isNull(minifierType)) {
			minifierType = "js";

			if (StringUtil.equalsIgnoreCase(extension, _CSS_EXTENSION)) {
				minifierType = "css";
			}
		}

		if (!minifierType.equals("css") && !minifierType.equals("js")) {
			minifierType = "js";
		}

		String modulePathsString = null;

		byte[][] bytesArray = null;

		if (!PropsValues.COMBO_CHECK_TIMESTAMP) {
			modulePathsString = Arrays.toString(modulePaths);

			modulePathsString +=
				StringPool.POUND + LanguageUtil.getLanguageId(request);

			bytesArray = _bytesArrayPortalCache.get(modulePathsString);
		}

		if (bytesArray == null) {
			bytesArray = new byte[modulePaths.length][];

			for (int i = 0; i < modulePaths.length; i++) {
				String modulePath = modulePaths[i];

				if (!validateModuleExtension(modulePath)) {
					response.setHeader(
						HttpHeaders.CACHE_CONTROL,
						HttpHeaders.CACHE_CONTROL_NO_CACHE_VALUE);
					response.setStatus(HttpServletResponse.SC_NOT_FOUND);

					return;
				}

				byte[] bytes = new byte[0];

				if (Validator.isNotNull(modulePath)) {
					RequestDispatcher requestDispatcher =
						getResourceRequestDispatcher(
							request, response, modulePath);

					if (requestDispatcher == null) {
						response.setHeader(
							HttpHeaders.CACHE_CONTROL,
							HttpHeaders.CACHE_CONTROL_NO_CACHE_VALUE);
						response.setStatus(HttpServletResponse.SC_NOT_FOUND);

						return;
					}

					bytes = getResourceContent(
						requestDispatcher, request, response, modulePath,
						minifierType);
				}

				bytesArray[i] = bytes;
			}

			if ((modulePathsString != null) &&
				!PropsValues.COMBO_CHECK_TIMESTAMP) {

				_bytesArrayPortalCache.put(modulePathsString, bytesArray);
			}
		}

		String contentType = ContentTypes.TEXT_JAVASCRIPT;

		if (StringUtil.equalsIgnoreCase(extension, _CSS_EXTENSION)) {
			contentType = ContentTypes.TEXT_CSS;
		}

		response.setContentType(contentType);

		ServletResponseUtil.write(response, bytesArray);
	}

	protected byte[] getResourceContent(
			RequestDispatcher requestDispatcher, HttpServletRequest request,
			HttpServletResponse response, String modulePath,
			String minifierType)
		throws Exception {

		String resourcePath = getResourcePath(modulePath);

		String portletId = getModulePortletId(modulePath);

		Portlet portlet = PortletLocalServiceUtil.getPortletById(portletId);

		if (!resourcePath.startsWith(portlet.getContextPath())) {
			resourcePath = portlet.getContextPath() + resourcePath;
		}

		String fileContentKey = resourcePath.concat(StringPool.QUESTION).concat(
			minifierType).concat("&languageId=").concat(
				request.getParameter("languageId"));

		FileContentBag fileContentBag = _fileContentBagPortalCache.get(
			fileContentKey);

		if ((fileContentBag != null) && !PropsValues.COMBO_CHECK_TIMESTAMP) {
			return fileContentBag._fileContent;
		}

		if ((fileContentBag != null) && PropsValues.COMBO_CHECK_TIMESTAMP) {
			long elapsedTime =
				System.currentTimeMillis() - fileContentBag._lastModified;

			if ((requestDispatcher != null) &&
				(elapsedTime <= PropsValues.COMBO_CHECK_TIMESTAMP_INTERVAL) &&
				(RequestDispatcherUtil.getLastModifiedTime(
					requestDispatcher, request, response) ==
						fileContentBag._lastModified)) {

				return fileContentBag._fileContent;
			}

			_fileContentBagPortalCache.remove(fileContentKey);
		}

		if (requestDispatcher == null) {
			fileContentBag = _EMPTY_FILE_CONTENT_BAG;
		}
		else {
			ObjectValuePair<String, Long> objectValuePair =
				RequestDispatcherUtil.getContentAndLastModifiedTime(
					requestDispatcher, request, response);

			String stringFileContent = objectValuePair.getKey();

			if (!StringUtil.endsWith(resourcePath, _CSS_MINIFIED_SUFFIX) &&
				!StringUtil.endsWith(
					resourcePath, _JAVASCRIPT_MINIFIED_SUFFIX)) {

				if (minifierType.equals("css")) {
					try {
						stringFileContent = DynamicCSSUtil.replaceToken(
							getServletContext(), request, stringFileContent);
					}
					catch (Exception e) {
						_log.error(
							"Unable to replace tokens in CSS " + resourcePath,
							e);

						if (_log.isDebugEnabled()) {
							_log.debug(stringFileContent);
						}

						response.setHeader(
							HttpHeaders.CACHE_CONTROL,
							HttpHeaders.CACHE_CONTROL_NO_CACHE_VALUE);
					}

					String baseURL = StringPool.BLANK;

					int slashIndex = resourcePath.lastIndexOf(CharPool.SLASH);

					if (slashIndex != -1) {
						baseURL = resourcePath.substring(0, slashIndex + 1);
					}

					stringFileContent = AggregateUtil.updateRelativeURLs(
						stringFileContent, baseURL);

					stringFileContent = MinifierUtil.minifyCss(
						stringFileContent);
				}
				else if (minifierType.equals("js")) {
					stringFileContent = MinifierUtil.minifyJavaScript(
						resourcePath, stringFileContent);
				}
			}

			fileContentBag = new FileContentBag(
				stringFileContent.getBytes(StringPool.UTF8),
				objectValuePair.getValue());
		}

		if (PropsValues.COMBO_CHECK_TIMESTAMP) {
			int timeToLive =
				(int)(PropsValues.COMBO_CHECK_TIMESTAMP_INTERVAL / Time.SECOND);

			_fileContentBagPortalCache.put(
				fileContentKey, fileContentBag, timeToLive);
		}

		return fileContentBag._fileContent;
	}

	protected RequestDispatcher getResourceRequestDispatcher(
			HttpServletRequest request, HttpServletResponse response,
			String modulePath)
		throws Exception {

		String portletId = getModulePortletId(modulePath);

		Portlet portlet = PortletLocalServiceUtil.getPortletById(portletId);

		if (portlet.isUndeployedPortlet()) {
			return null;
		}

		PortletApp portletApp = portlet.getPortletApp();

		ServletContext servletContext = portletApp.getServletContext();

		String resourcePath = getResourcePath(modulePath);

		if (!PortalUtil.isValidResourceId(resourcePath)) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Invalid resource " + request.getRequestURL() + "?" +
						request.getQueryString());
			}

			return null;
		}

		return servletContext.getRequestDispatcher(resourcePath);
	}

	protected boolean validateModuleExtension(String moduleName)
		throws Exception {

		int index = moduleName.indexOf(CharPool.QUESTION);

		if (index != -1) {
			moduleName = moduleName.substring(0, index);
		}

		boolean validModuleExtension = false;

		String[] fileExtensions = PrefsPropsUtil.getStringArray(
			PropsKeys.COMBO_ALLOWED_FILE_EXTENSIONS, StringPool.COMMA);

		for (String fileExtension : fileExtensions) {
			if (StringPool.STAR.equals(fileExtension) ||
				StringUtil.endsWith(moduleName, fileExtension)) {

				validModuleExtension = true;

				break;
			}
		}

		return validModuleExtension;
	}

	private static final String _CSS_EXTENSION = "css";

	private static final String _CSS_MINIFIED_SUFFIX = "-min.css";

	private static final FileContentBag _EMPTY_FILE_CONTENT_BAG =
		new FileContentBag(new byte[0], 0);

	private static final String _JAVASCRIPT_MINIFIED_SUFFIX = "-min.js";

	private static final Log _log = LogFactoryUtil.getLog(ComboServlet.class);

	private static final PortalCache<String, byte[][]> _bytesArrayPortalCache =
		SingleVMPoolUtil.getPortalCache(ComboServlet.class.getName());
	private static final PortalCache<String, FileContentBag>
		_fileContentBagPortalCache = SingleVMPoolUtil.getPortalCache(
			FileContentBag.class.getName());

	private final Set<String> _protectedParameters = SetUtil.fromArray(
		new String[] {
			"b", "browserId", "minifierType", "languageId", "t", "themeId"
		});

	private static class FileContentBag implements Serializable {

		public FileContentBag(byte[] fileContent, long lastModifiedTime) {
			_fileContent = fileContent;
			_lastModified = lastModifiedTime;
		}

		private final byte[] _fileContent;
		private final long _lastModified;

	}

}