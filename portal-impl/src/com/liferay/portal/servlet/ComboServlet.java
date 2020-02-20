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

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.cache.PortalCache;
import com.liferay.portal.kernel.cache.PortalCacheHelperUtil;
import com.liferay.portal.kernel.cache.PortalCacheManagerNames;
import com.liferay.portal.kernel.exception.NoSuchLayoutException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.model.PortletApp;
import com.liferay.portal.kernel.service.PortletLocalServiceUtil;
import com.liferay.portal.kernel.servlet.HttpHeaders;
import com.liferay.portal.kernel.servlet.RequestDispatcherUtil;
import com.liferay.portal.kernel.servlet.ServletResponseUtil;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.ObjectValuePair;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.PortletKeys;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.minifier.MinifierUtil;
import com.liferay.portal.servlet.filters.dynamiccss.DynamicCSSUtil;
import com.liferay.portal.util.AggregateUtil;
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
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws IOException, ServletException {

		try {
			doService(httpServletRequest, httpServletResponse);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			PortalUtil.sendError(
				HttpServletResponse.SC_INTERNAL_SERVER_ERROR, exception,
				httpServletRequest, httpServletResponse);
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
			return HttpUtil.removePathParameters(
				modulePath.substring(index + 1));
		}

		return HttpUtil.removePathParameters(modulePath);
	}

	protected void doService(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws Exception {

		Set<String> modulePathsSet = new LinkedHashSet<>();

		Map<String, String[]> parameterMap = HttpUtil.getParameterMap(
			httpServletRequest.getQueryString());

		Enumeration<String> enu = Collections.enumeration(
			parameterMap.keySet());

		while (enu.hasMoreElements()) {
			String name = enu.nextElement();

			if (_protectedParameters.contains(name)) {
				continue;
			}

			name = HttpUtil.decodePath(name);

			ServletContext servletContext = getServletContext();

			String pathProxy = PortalUtil.getPathProxy();

			if (name.startsWith(pathProxy)) {
				name = name.replaceFirst(pathProxy, StringPool.BLANK);
			}

			String contextPath = servletContext.getContextPath();

			if (name.startsWith(contextPath)) {
				name = name.replaceFirst(contextPath, StringPool.BLANK);
			}

			modulePathsSet.add(name);
		}

		if (modulePathsSet.isEmpty()) {
			PortalUtil.sendError(
				HttpServletResponse.SC_NOT_FOUND,
				new NoSuchLayoutException(
					"Query string translates to an empty module paths set"),
				httpServletRequest, httpServletResponse);

			return;
		}

		String[] modulePaths = modulePathsSet.toArray(new String[0]);

		String extension = StringPool.BLANK;

		for (String modulePath : modulePaths) {
			String pathExtension = _getModulePathExtension(modulePath);

			if (Validator.isNull(pathExtension)) {
				continue;
			}

			if (Validator.isNull(extension)) {
				extension = pathExtension;
			}

			if (!extension.equals(pathExtension)) {
				httpServletResponse.setHeader(
					HttpHeaders.CACHE_CONTROL,
					HttpHeaders.CACHE_CONTROL_NO_CACHE_VALUE);
				httpServletResponse.setStatus(
					HttpServletResponse.SC_BAD_REQUEST);

				return;
			}
		}

		boolean cacheEnabled = true;

		if (PropsValues.WORK_DIR_OVERRIDE_ENABLED) {
			cacheEnabled = false;

			httpServletResponse.setHeader(
				HttpHeaders.CACHE_CONTROL,
				HttpHeaders.CACHE_CONTROL_NO_CACHE_VALUE);
		}

		String minifierType = ParamUtil.getString(
			httpServletRequest, "minifierType");

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
				StringPool.POUND +
					LanguageUtil.getLanguageId(httpServletRequest);

			bytesArray = _bytesArrayPortalCache.get(modulePathsString);
		}

		if (bytesArray == null) {
			bytesArray = new byte[modulePaths.length][];

			for (int i = 0; i < modulePaths.length; i++) {
				String modulePath = modulePaths[i];

				if (!validateModuleExtension(modulePath)) {
					httpServletResponse.setHeader(
						HttpHeaders.CACHE_CONTROL,
						HttpHeaders.CACHE_CONTROL_NO_CACHE_VALUE);
					httpServletResponse.setStatus(
						HttpServletResponse.SC_BAD_REQUEST);

					return;
				}

				byte[] bytes = new byte[0];

				if (Validator.isNotNull(modulePath)) {
					RequestDispatcher requestDispatcher =
						getResourceRequestDispatcher(
							httpServletRequest, httpServletResponse,
							modulePath);

					if (requestDispatcher == null) {
						httpServletResponse.setHeader(
							HttpHeaders.CACHE_CONTROL,
							HttpHeaders.CACHE_CONTROL_NO_CACHE_VALUE);
						httpServletResponse.setStatus(
							HttpServletResponse.SC_NOT_FOUND);

						return;
					}

					bytes = getResourceContent(
						requestDispatcher, httpServletRequest,
						httpServletResponse, modulePath, minifierType);
				}

				bytesArray[i] = bytes;
			}

			if (cacheEnabled && (modulePathsString != null) &&
				!PropsValues.COMBO_CHECK_TIMESTAMP) {

				_bytesArrayPortalCache.put(modulePathsString, bytesArray);
			}
		}

		String contentType = ContentTypes.TEXT_JAVASCRIPT;

		if (StringUtil.equalsIgnoreCase(extension, _CSS_EXTENSION)) {
			contentType = ContentTypes.TEXT_CSS;
		}

		httpServletResponse.setContentType(contentType);

		ServletResponseUtil.write(httpServletResponse, bytesArray);
	}

	protected byte[] getResourceContent(
			RequestDispatcher requestDispatcher,
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, String modulePath,
			String minifierType)
		throws Exception {

		String resourcePath = getResourcePath(modulePath);

		String portletId = getModulePortletId(modulePath);

		Portlet portlet = PortletLocalServiceUtil.getPortletById(portletId);

		if (!resourcePath.startsWith(portlet.getContextPath())) {
			resourcePath = portlet.getContextPath() + resourcePath;
		}

		StringBundler sb = new StringBundler(5);

		sb.append(resourcePath);
		sb.append(StringPool.QUESTION);
		sb.append(minifierType);
		sb.append("&languageId=");
		sb.append(ParamUtil.getString(httpServletRequest, "languageId"));

		String fileContentKey = sb.toString();

		FileContentBag fileContentBag = _fileContentBagPortalCache.get(
			fileContentKey);

		if ((fileContentBag != null) && !PropsValues.COMBO_CHECK_TIMESTAMP) {
			return fileContentBag._fileContent;
		}

		if ((fileContentBag != null) && PropsValues.COMBO_CHECK_TIMESTAMP) {
			long elapsedTime =
				System.currentTimeMillis() - fileContentBag._lastModified;

			if ((requestDispatcher != null) &&
				(elapsedTime <= PropsValues.COMBO_CHECK_TIMESTAMP_INTERVAL)) {

				long lastModified = RequestDispatcherUtil.getLastModifiedTime(
					requestDispatcher, httpServletRequest, httpServletResponse);

				if (lastModified == fileContentBag._lastModified) {
					return fileContentBag._fileContent;
				}
			}

			_fileContentBagPortalCache.remove(fileContentKey);
		}

		if (requestDispatcher == null) {
			fileContentBag = _EMPTY_FILE_CONTENT_BAG;
		}
		else {
			ObjectValuePair<String, Long> objectValuePair =
				RequestDispatcherUtil.getContentAndLastModifiedTime(
					requestDispatcher, httpServletRequest, httpServletResponse);

			String stringFileContent = objectValuePair.getKey();

			if (!StringUtil.endsWith(resourcePath, _CSS_MINIFIED_DASH_SUFFIX) &&
				!StringUtil.endsWith(resourcePath, _CSS_MINIFIED_DOT_SUFFIX) &&
				!StringUtil.endsWith(
					resourcePath, _JAVASCRIPT_MINIFIED_DASH_SUFFIX) &&
				!StringUtil.endsWith(
					resourcePath, _JAVASCRIPT_MINIFIED_DOT_SUFFIX)) {

				if (minifierType.equals("css")) {
					try {
						stringFileContent = DynamicCSSUtil.replaceToken(
							getServletContext(), httpServletRequest,
							stringFileContent);
					}
					catch (Exception exception) {
						_log.error(
							"Unable to replace tokens in CSS " + resourcePath,
							exception);

						if (_log.isDebugEnabled()) {
							_log.debug(stringFileContent);
						}

						httpServletResponse.setHeader(
							HttpHeaders.CACHE_CONTROL,
							HttpHeaders.CACHE_CONTROL_NO_CACHE_VALUE);
					}

					String baseURL = StringPool.BLANK;

					int slashIndex = resourcePath.lastIndexOf(CharPool.SLASH);

					if (slashIndex != -1) {
						baseURL = resourcePath.substring(0, slashIndex + 1);
					}

					baseURL = PortalUtil.getPathProxy() + baseURL;

					stringFileContent = AggregateUtil.updateRelativeURLs(
						stringFileContent, baseURL);

					stringFileContent = MinifierUtil.minifyCss(
						stringFileContent);
				}
				else if (minifierType.equals("js")) {
					stringFileContent = MinifierUtil.minifyJavaScript(
						resourcePath, stringFileContent);

					stringFileContent = stringFileContent.concat(
						StringPool.NEW_LINE);
				}
			}
			else if (StringUtil.endsWith(
						resourcePath, _JAVASCRIPT_MINIFIED_DASH_SUFFIX) ||
					 StringUtil.endsWith(
						 resourcePath, _JAVASCRIPT_MINIFIED_DOT_SUFFIX)) {

				stringFileContent = stringFileContent.concat(
					StringPool.NEW_LINE);
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
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, String modulePath)
		throws Exception {

		String portletId = getModulePortletId(modulePath);

		Portlet portlet = PortletLocalServiceUtil.getPortletById(portletId);

		if ((portlet == null) || portlet.isUndeployedPortlet()) {
			return null;
		}

		String resourcePath = getResourcePath(modulePath);

		if (!PortalUtil.isValidResourceId(resourcePath)) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					StringBundler.concat(
						"Invalid resource ", httpServletRequest.getRequestURL(),
						"?", httpServletRequest.getQueryString()));
			}

			return null;
		}

		PortletApp portletApp = portlet.getPortletApp();

		ServletContext servletContext = portletApp.getServletContext();

		return servletContext.getRequestDispatcher(resourcePath);
	}

	protected boolean validateModuleExtension(String moduleName)
		throws Exception {

		moduleName = getResourcePath(moduleName);

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

	private String _getModulePathExtension(String modulePath) {
		String resourcePath = getResourcePath(modulePath);

		int index = resourcePath.indexOf(CharPool.QUESTION);

		if (index != -1) {
			resourcePath = resourcePath.substring(0, index);
		}

		return FileUtil.getExtension(resourcePath);
	}

	private static final String _CSS_EXTENSION = "css";

	private static final String _CSS_MINIFIED_DASH_SUFFIX = "-min.css";

	private static final String _CSS_MINIFIED_DOT_SUFFIX = ".min.css";

	private static final FileContentBag _EMPTY_FILE_CONTENT_BAG =
		new FileContentBag(new byte[0], 0);

	private static final String _JAVASCRIPT_MINIFIED_DASH_SUFFIX = "-min.js";

	private static final String _JAVASCRIPT_MINIFIED_DOT_SUFFIX = ".min.js";

	private static final Log _log = LogFactoryUtil.getLog(ComboServlet.class);

	private static final PortalCache<String, byte[][]> _bytesArrayPortalCache =
		PortalCacheHelperUtil.getPortalCache(
			PortalCacheManagerNames.SINGLE_VM, ComboServlet.class.getName());
	private static final PortalCache<String, FileContentBag>
		_fileContentBagPortalCache = PortalCacheHelperUtil.getPortalCache(
			PortalCacheManagerNames.SINGLE_VM, FileContentBag.class.getName());

	private final Set<String> _protectedParameters = SetUtil.fromArray(
		new String[] {
			"b", "browserId", "minifierType", "languageId", "t", "themeId", "zx"
		});

	private static class FileContentBag implements Serializable {

		public FileContentBag(byte[] fileContent, long lastModified) {
			_fileContent = fileContent;
			_lastModified = lastModified;
		}

		private final byte[] _fileContent;
		private final long _lastModified;

	}

}