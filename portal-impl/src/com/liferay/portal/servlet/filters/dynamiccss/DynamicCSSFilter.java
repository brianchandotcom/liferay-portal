/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayOutputStream;
import com.liferay.portal.kernel.io.unsync.UnsyncPrintWriter;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.scripting.ScriptingException;
import com.liferay.portal.kernel.servlet.ServletContextUtil;
import com.liferay.portal.kernel.servlet.StringServletResponse;
import com.liferay.portal.kernel.servlet.WebDirDetector;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PortalClassLoaderUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.UnsyncPrintWriterPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.scripting.ruby.RubyExecutor;
import com.liferay.portal.servlet.filters.BasePortalFilter;
import com.liferay.util.SystemProperties;
import com.liferay.util.servlet.ServletResponseUtil;
import com.liferay.util.servlet.filters.CacheResponseUtil;

import java.io.File;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Eduardo Lundgren
 * @author Raymond Augé
 */
public class DynamicCSSFilter extends BasePortalFilter {

	@Override
	public void init(FilterConfig filterConfig) {
		super.init(filterConfig);

		_servletContext = filterConfig.getServletContext();
		_servletContextName = GetterUtil.getString(
			_servletContext.getServletContextName());

		String rootDir = WebDirDetector.getRootDir(
			PortalClassLoaderUtil.getClassLoader());

		_rubyScriptFile = new File(rootDir + "WEB-INF/sass/main.rb");

		try {

			// Ruby executor needs to warm up when requiring Sass. Always breaks
			// the first time without this block.

			_rubyExecutor.eval(
				null, new HashMap<String, Object>(), null,
				"require 'rubygems'\nrequire 'sass'");
		}
		catch (ScriptingException se) {
			_log.error(se, se);
		}

		if (Validator.isNull(_servletContextName)) {
			_tempDir += "/portal";
		}
	}

	protected Object getDynamicContent(
			HttpServletRequest request, HttpServletResponse response,
			FilterChain filterChain)
		throws Exception {

		String requestURI = request.getRequestURI();

		String requestPath = requestURI;

		String contextPath = request.getContextPath();

		if (!contextPath.equals(StringPool.SLASH)) {
			requestPath = requestPath.substring(contextPath.length());
		}

		String realPath = ServletContextUtil.getRealPath(
			_servletContext, requestPath);

		if (realPath == null) {
			return null;
		}

		realPath = StringUtil.replace(
			realPath, CharPool.BACK_SLASH, CharPool.SLASH);

		File file = new File(realPath);

		if (!file.exists()) {
			return null;
		}

		request.setAttribute(WebKeys.CSS_REAL_PATH, realPath);

		StringBundler sb = new StringBundler(4);

		sb.append(_tempDir);
		sb.append(requestURI);

		String queryString = request.getQueryString();

		if (queryString != null) {
			sb.append(_QUESTION_SEPARATOR);
			sb.append(sterilizeQueryString(queryString));
		}

		String cacheCommonFileName = sb.toString();

		File cacheContentTypeFile = new File(
			cacheCommonFileName + "_E_CONTENT_TYPE");
		File cacheDataFile = new File(cacheCommonFileName + "_E_DATA");

		if ((cacheDataFile.exists()) &&
			(cacheDataFile.lastModified() >= file.lastModified())) {

			if (cacheContentTypeFile.exists()) {
				String contentType = FileUtil.read(cacheContentTypeFile);

				response.setContentType(contentType);
			}

			return cacheDataFile;
		}
		else {
			String dynamicContent = null;

			if (realPath.endsWith(_CSS_EXTENSION)) {
				if (_log.isInfoEnabled()) {
					_log.info("Parsing SASS on CSS " + file);
				}

				dynamicContent = parseSass(request, FileUtil.read(file));

				response.setContentType(ContentTypes.TEXT_CSS);

				FileUtil.write(cacheContentTypeFile, ContentTypes.TEXT_CSS);
			}
			else if (realPath.endsWith(_JSP_EXTENSION)) {
				if (_log.isInfoEnabled()) {
					_log.info("Parsing SASS on JSP " + file);
				}

				StringServletResponse stringResponse =
					new StringServletResponse(response);

				processFilter(
					DynamicCSSFilter.class, request, stringResponse,
					filterChain);

				CacheResponseUtil.setHeaders(
					response, stringResponse.getHeaders());

				response.setContentType(stringResponse.getContentType());

				dynamicContent = parseSass(request, stringResponse.getString());

				FileUtil.write(
					cacheContentTypeFile, stringResponse.getContentType());
			}
			else {
				return null;
			}

			FileUtil.write(cacheDataFile, dynamicContent);

			return dynamicContent;
		}
	}

	protected String parseSass(
			HttpServletRequest request, String content)
		throws ScriptingException {

		Map<String, Object> inputObjects = new HashMap<String, Object>();

		inputObjects.put("content", content);

		String cssRealPath = (String)request.getAttribute(
			WebKeys.CSS_REAL_PATH);

		inputObjects.put(
			"cssRealPath", cssRealPath);

		UnsyncByteArrayOutputStream unsyncByteArrayOutputStream =
			new UnsyncByteArrayOutputStream();

		UnsyncPrintWriter unsyncPrintWriter = UnsyncPrintWriterPool.borrow(
			unsyncByteArrayOutputStream);

		inputObjects.put("out", unsyncPrintWriter);

		_rubyExecutor.eval(null, inputObjects, null, _rubyScriptFile);

		unsyncPrintWriter.flush();

		String parsedContent = unsyncByteArrayOutputStream.toString();

		if (Validator.isNotNull(parsedContent)) {
			return parsedContent;
		}

		return content;
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
				DynamicCSSFilter.class, request, response, filterChain);
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

	protected String sterilizeQueryString(String queryString) {
		return StringUtil.replace(
			queryString,
			new String[] {StringPool.SLASH, StringPool.BACK_SLASH},
			new String[] {StringPool.UNDERLINE, StringPool.UNDERLINE});
	}

	private static final String _CSS_EXTENSION = ".css";

	private static final String _JSP_EXTENSION = ".jsp";

	private static final String _QUESTION_SEPARATOR = "_Q_";

	private static final String _TEMP_DIR =
		SystemProperties.get(SystemProperties.TMP_DIR) + "/liferay/css";

	private static Log _log = LogFactoryUtil.getLog(DynamicCSSFilter.class);

	private RubyExecutor _rubyExecutor = new RubyExecutor();
	private File _rubyScriptFile;
	private ServletContext _servletContext;
	private String _servletContextName;
	private String _tempDir = _TEMP_DIR;

}