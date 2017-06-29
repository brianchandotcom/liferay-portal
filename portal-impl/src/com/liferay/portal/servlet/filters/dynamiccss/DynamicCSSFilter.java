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

package com.liferay.portal.servlet.filters.dynamiccss;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.BufferCacheServletResponse;
import com.liferay.portal.kernel.servlet.DynamicResourceIncludeUtil;
import com.liferay.portal.kernel.servlet.HttpHeaders;
import com.liferay.portal.kernel.servlet.PortalWebResourcesUtil;
import com.liferay.portal.kernel.servlet.PortletResourcesUtil;
import com.liferay.portal.kernel.servlet.ServletResponseUtil;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.URLUtil;
import com.liferay.portal.servlet.filters.IgnoreModuleRequestFilter;
import com.liferay.portal.servlet.filters.util.CacheFileNameGenerator;
import com.liferay.portal.util.PropsUtil;

import java.io.File;

import java.net.URL;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Eduardo Lundgren
 * @author Raymond Augé
 */
public class DynamicCSSFilter extends IgnoreModuleRequestFilter {

	public static final boolean ENABLED = GetterUtil.getBoolean(
		PropsUtil.get(DynamicCSSFilter.class.getName()));

	@Override
	public void init(FilterConfig filterConfig) {
		super.init(filterConfig);

		_servletContext = filterConfig.getServletContext();

		File tempDir = (File)_servletContext.getAttribute(
			JavaConstants.JAVAX_SERVLET_CONTEXT_TEMPDIR);

		_tempDir = new File(tempDir, _TEMP_DIR);

		_tempDir.mkdirs();
	}

	protected String getCacheFileName(HttpServletRequest request) {
		String cacheFileName = CacheFileNameGenerator.getCacheFileName(
			request, DynamicCSSFilter.class.getName());

		if (PortalUtil.isRightToLeft(request)) {
			return cacheFileName + _CACHE_FILE_NAME_RTL;
		}

		return cacheFileName;
	}

	protected Object getDynamicContent(
			HttpServletRequest request, HttpServletResponse response,
			FilterChain filterChain)
		throws Exception {

		ServletContext servletContext = _servletContext;

		String requestPath = getRequestPath(request);

		if (requestPath.endsWith(_CSS_EXTENSION) &&
			PortalUtil.isRightToLeft(request)) {

			int pos = requestPath.lastIndexOf(StringPool.PERIOD);

			requestPath =
				requestPath.substring(0, pos) + "_rtl" +
					requestPath.substring(pos);
		}

		URL resourceURL = _servletContext.getResource(requestPath);

		String originalRequestPath = request.getRequestURI();

		if (resourceURL == null) {
			ServletContext resourceServletContext =
				PortalWebResourcesUtil.getPathServletContext(
					originalRequestPath);

			if (resourceServletContext != null) {
				resourceURL = PortalWebResourcesUtil.getResource(
					resourceServletContext, originalRequestPath);
			}

			if (resourceURL == null) {
				resourceServletContext =
					PortletResourcesUtil.getPathServletContext(
						originalRequestPath);

				if (resourceServletContext != null) {
					resourceURL = PortletResourcesUtil.getResource(
						resourceServletContext, originalRequestPath);
				}
			}

			if (resourceURL == null) {
				resourceServletContext =
					DynamicResourceIncludeUtil.getPathServletContext(
						originalRequestPath);

				if (resourceServletContext != null) {
					resourceURL = DynamicResourceIncludeUtil.getResource(
						resourceServletContext, originalRequestPath);
				}
			}

			if (resourceURL == null) {
				return null;
			}

			servletContext = resourceServletContext;
		}

		String cacheCommonFileName = getCacheFileName(request);

		File cacheContentTypeFile = new File(
			_tempDir, cacheCommonFileName + "_E_CTYPE");
		File cacheDataFile = new File(
			_tempDir, cacheCommonFileName + "_E_DATA");

		if (cacheDataFile.exists() &&
			(cacheDataFile.lastModified() >=
				getLastModified(request, resourceURL))) {

			if (cacheContentTypeFile.exists()) {
				String contentType = FileUtil.read(cacheContentTypeFile);

				response.setContentType(contentType);
			}

			return cacheDataFile;
		}

		String dynamicContent = null;

		String content = null;

		try {
			if (originalRequestPath.endsWith(_CSS_EXTENSION)) {
				if (_log.isInfoEnabled()) {
					_log.info("Replacing tokens on CSS " + originalRequestPath);
				}

				content = StringUtil.read(resourceURL.openStream());

				dynamicContent = DynamicCSSUtil.replaceToken(
					servletContext, request, content);

				response.setContentType(ContentTypes.TEXT_CSS);

				FileUtil.write(cacheContentTypeFile, ContentTypes.TEXT_CSS);
			}
			else if (originalRequestPath.endsWith(_JSP_EXTENSION)) {
				if (_log.isInfoEnabled()) {
					_log.info(
						"Replacing tokens on JSP or servlet " +
							originalRequestPath);
				}

				BufferCacheServletResponse bufferCacheServletResponse =
					new BufferCacheServletResponse(response);

				processFilter(
					DynamicCSSFilter.class.getName(), request,
					bufferCacheServletResponse, filterChain);

				content = bufferCacheServletResponse.getString();

				dynamicContent = DynamicCSSUtil.replaceToken(
					servletContext, request, content);

				FileUtil.write(
					cacheContentTypeFile,
					bufferCacheServletResponse.getContentType());
			}
			else {
				return null;
			}
		}
		catch (Exception e) {
			_log.error(
				"Unable to replace tokens in CSS " + originalRequestPath, e);

			if (_log.isDebugEnabled()) {
				_log.debug(content);
			}

			response.setHeader(
				HttpHeaders.CACHE_CONTROL,
				HttpHeaders.CACHE_CONTROL_NO_CACHE_VALUE);
		}

		if (dynamicContent != null) {
			FileUtil.write(cacheDataFile, dynamicContent);
		}
		else {
			dynamicContent = content;
		}

		return dynamicContent;
	}

	protected long getLastModified(HttpServletRequest request, URL resourceURL)
		throws Exception {

		long resourceLastModified = URLUtil.getLastModifiedTime(resourceURL);

		long requestLastModified = ParamUtil.getLong(request, "t", -1);

		return Math.max(resourceLastModified, requestLastModified);
	}

	protected String getRequestPath(HttpServletRequest request) {
		String requestPath = request.getRequestURI();

		String contextPath = request.getContextPath();

		if (!contextPath.equals(StringPool.SLASH)) {
			requestPath = requestPath.substring(contextPath.length());
		}

		return requestPath;
	}

	@Override
	protected boolean isModuleRequest(HttpServletRequest request) {
		String requestURI = request.getRequestURI();

		if (PortalWebResourcesUtil.hasContextPath(requestURI)) {
			return false;
		}

		return super.isModuleRequest(request);
	}

	@Override
	protected void processFilter(
			HttpServletRequest request, HttpServletResponse response,
			FilterChain filterChain)
		throws Exception {

		Object parsedContent = getDynamicContent(
			request, response, filterChain);

		if (parsedContent == null) {
			processFilter(
				DynamicCSSFilter.class.getName(), request, response,
				filterChain);
		}
		else {
			if (parsedContent instanceof File) {
				ServletResponseUtil.write(response, (File)parsedContent);
			}
			else if (parsedContent instanceof String) {
				ServletResponseUtil.write(response, (String)parsedContent);
			}
		}
	}

	private static final String _CACHE_FILE_NAME_RTL = "_rtl";

	private static final String _CSS_EXTENSION = ".css";

	private static final String _JSP_EXTENSION = ".jsp";

	private static final String _TEMP_DIR = "css";

	private static final Log _log = LogFactoryUtil.getLog(
		DynamicCSSFilter.class);

	private ServletContext _servletContext;
	private File _tempDir;

}