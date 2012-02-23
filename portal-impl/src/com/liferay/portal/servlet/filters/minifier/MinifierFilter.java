/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.servlet.filters.minifier;

import com.liferay.portal.kernel.cache.key.CacheKeyGenerator;
import com.liferay.portal.kernel.cache.key.CacheKeyGeneratorUtil;
import com.liferay.portal.kernel.configuration.Filter;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.BrowserSniffer;
import com.liferay.portal.kernel.servlet.HttpHeaders;
import com.liferay.portal.kernel.servlet.ServletContextUtil;
import com.liferay.portal.kernel.servlet.ServletResponseUtil;
import com.liferay.portal.kernel.servlet.StringServletResponse;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.SystemProperties;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.servlet.filters.BasePortalFilter;
import com.liferay.portal.servlet.filters.dynamiccss.DynamicCSSUtil;
import com.liferay.portal.util.JavaScriptBundleUtil;
import com.liferay.portal.util.MinifierUtil;
import com.liferay.portal.util.PropsUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.util.servlet.filters.CacheResponseUtil;

import java.io.File;
import java.io.IOException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Brian Wing Shun Chan
 */
public class MinifierFilter extends BasePortalFilter {

	/**
	 * @see {@link DynamicCSSUtil#_propagateQueryString(String, String)}
	 */
	public static String aggregateCss(String dir, String content)
		throws IOException {

		StringBuilder sb = new StringBuilder(content.length());

		int pos = 0;

		while (true) {
			int commentX = content.indexOf(_CSS_COMMENT_BEGIN, pos);
			int commentY = content.indexOf(
				_CSS_COMMENT_END, commentX + _CSS_COMMENT_BEGIN.length());

			int importX = content.indexOf(_CSS_IMPORT_BEGIN, pos);
			int importY = content.indexOf(
				_CSS_IMPORT_END, importX + _CSS_IMPORT_BEGIN.length());

			if ((importX == -1) || (importY == -1)) {
				sb.append(content.substring(pos, content.length()));

				break;
			}
			else if ((commentX != -1) && (commentY != -1) &&
					 (commentX < importX) && (commentY > importX)) {

				commentY += _CSS_COMMENT_END.length();

				sb.append(content.substring(pos, commentY));

				pos = commentY;
			}
			else {
				sb.append(content.substring(pos, importX));

				String importFileName = content.substring(
					importX + _CSS_IMPORT_BEGIN.length(), importY);

				String importFullFileName = dir.concat(StringPool.SLASH).concat(
					importFileName);

				String importContent = FileUtil.read(importFullFileName);

				if (importContent == null) {
					if (_log.isWarnEnabled()) {
						_log.warn(
							"File " + importFullFileName + " does not exist");
					}

					importContent = StringPool.BLANK;
				}

				String importDir = StringPool.BLANK;

				int slashPos = importFileName.lastIndexOf(CharPool.SLASH);

				if (slashPos != -1) {
					importDir = StringPool.SLASH.concat(
						importFileName.substring(0, slashPos + 1));
				}

				importContent = aggregateCss(dir + importDir, importContent);

				int importDepth = StringUtil.count(
					importFileName, StringPool.SLASH);

				// LEP-7540

				String relativePath = StringPool.BLANK;

				for (int i = 0; i < importDepth; i++) {
					relativePath += "../";
				}

				importContent = StringUtil.replace(
					importContent,
					new String[] {
						"url('" + relativePath, "url(\"" + relativePath,
						"url(" + relativePath
					},
					new String[] {
						"url('[$TEMP_RELATIVE_PATH$]",
						"url(\"[$TEMP_RELATIVE_PATH$]",
						"url([$TEMP_RELATIVE_PATH$]"
					});

				importContent = StringUtil.replace(
					importContent, "[$TEMP_RELATIVE_PATH$]", StringPool.BLANK);

				sb.append(importContent);

				pos = importY + _CSS_IMPORT_END.length();
			}
		}

		return sb.toString();
	}

	@Override
	public void init(FilterConfig filterConfig) {
		super.init(filterConfig);

		_servletContext = filterConfig.getServletContext();
		_servletContextName = GetterUtil.getString(
			_servletContext.getServletContextName());

		if (Validator.isNull(_servletContextName)) {
			_tempDir += "/portal";
		}
	}

	protected String getCacheFileName(HttpServletRequest request) {
		CacheKeyGenerator cacheKeyGenerator =
			CacheKeyGeneratorUtil.getCacheKeyGenerator(
				MinifierFilter.class.getName());

		cacheKeyGenerator.append(request.getRequestURI());

		String queryString = request.getQueryString();

		if (queryString != null) {
			cacheKeyGenerator.append(sterilizeQueryString(queryString));
		}

		String cacheKey = String.valueOf(cacheKeyGenerator.finish());

		return _tempDir.concat(StringPool.SLASH).concat(cacheKey);
	}

	protected Object getMinifiedBundleContent(
			HttpServletRequest request, HttpServletResponse response)
		throws IOException {

		String minifierType = ParamUtil.getString(request, "minifierType");
		String minifierBundleId = ParamUtil.getString(
			request, "minifierBundleId");

		if (Validator.isNull(minifierType) ||
			Validator.isNull(minifierBundleId) ||
			!ArrayUtil.contains(
				PropsValues.JAVASCRIPT_BUNDLE_IDS, minifierBundleId)) {

			return null;
		}

		String minifierBundleDir = PropsUtil.get(
			PropsKeys.JAVASCRIPT_BUNDLE_DIR, new Filter(minifierBundleId));

		String bundleDirRealPath = ServletContextUtil.getRealPath(
			_servletContext, minifierBundleDir);

		if (bundleDirRealPath == null) {
			return null;
		}

		String cacheFileName = getCacheFileName(request);

		String[] fileNames = JavaScriptBundleUtil.getFileNames(
			minifierBundleId);

		File cacheFile = new File(cacheFileName);

		if (cacheFile.exists()) {
			boolean staleCache = false;

			for (String fileName : fileNames) {
				File file = new File(
					bundleDirRealPath + StringPool.SLASH + fileName);

				if (file.lastModified() > cacheFile.lastModified()) {
					staleCache = true;

					break;
				}
			}

			if (!staleCache) {
				response.setContentType(ContentTypes.TEXT_JAVASCRIPT);

				return cacheFile;
			}
		}

		if (_log.isInfoEnabled()) {
			_log.info("Minifying JavaScript bundle " + minifierBundleId);
		}

		String minifiedContent = null;

		if (fileNames.length == 0) {
			minifiedContent = StringPool.BLANK;
		}
		else {
			StringBundler sb = new StringBundler(fileNames.length * 2);

			for (String fileName : fileNames) {
				String content = FileUtil.read(
					bundleDirRealPath + StringPool.SLASH + fileName);

				sb.append(content);
				sb.append(StringPool.NEW_LINE);
			}

			minifiedContent = minifyJavaScript(sb.toString());
		}

		response.setContentType(ContentTypes.TEXT_JAVASCRIPT);

		FileUtil.write(cacheFile, minifiedContent);

		return minifiedContent;
	}

	protected Object getMinifiedContent(
			HttpServletRequest request, HttpServletResponse response,
			FilterChain filterChain)
		throws Exception {

		String minifierType = ParamUtil.getString(request, "minifierType");
		String minifierBundleId = ParamUtil.getString(
			request, "minifierBundleId");
		String minifierBundleDir = ParamUtil.getString(
			request, "minifierBundleDir");

		if (Validator.isNull(minifierType) ||
			Validator.isNotNull(minifierBundleId) ||
			Validator.isNotNull(minifierBundleDir)) {

			return null;
		}

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

		String cacheCommonFileName = getCacheFileName(request);

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

		String minifiedContent = null;

		if (realPath.endsWith(_CSS_EXTENSION)) {
			if (_log.isInfoEnabled()) {
				_log.info("Minifying CSS " + file);
			}

			minifiedContent = minifyCss(request, response, file);

			response.setContentType(ContentTypes.TEXT_CSS);

			FileUtil.write(cacheContentTypeFile, ContentTypes.TEXT_CSS);
		}
		else if (realPath.endsWith(_JAVASCRIPT_EXTENSION)) {
			if (_log.isInfoEnabled()) {
				_log.info("Minifying JavaScript " + file);
			}

			minifiedContent = minifyJavaScript(file);

			response.setContentType(ContentTypes.TEXT_JAVASCRIPT);

			FileUtil.write(cacheContentTypeFile, ContentTypes.TEXT_JAVASCRIPT);
		}
		else if (realPath.endsWith(_JSP_EXTENSION)) {
			if (_log.isInfoEnabled()) {
				_log.info("Minifying JSP " + file);
			}

			StringServletResponse stringResponse = new StringServletResponse(
				response);

			processFilter(
				MinifierFilter.class, request, stringResponse, filterChain);

			CacheResponseUtil.setHeaders(response, stringResponse.getHeaders());

			response.setContentType(stringResponse.getContentType());

			minifiedContent = stringResponse.getString();

			if (minifierType.equals("css")) {
				minifiedContent = minifyCss(
					request, response, realPath, minifiedContent);
			}
			else if (minifierType.equals("js")) {
				minifiedContent = minifyJavaScript(minifiedContent);
			}

			FileUtil.write(
				cacheContentTypeFile, stringResponse.getContentType());
		}
		else {
			return null;
		}

		FileUtil.write(cacheDataFile, minifiedContent);

		return minifiedContent;
	}

	protected String minifyCss(
			HttpServletRequest request, HttpServletResponse response, File file)
		throws IOException {

		String content = FileUtil.read(file);

		content = aggregateCss(file.getParent(), content);

		return minifyCss(request, response, file.getAbsolutePath(), content);
	}

	protected String minifyCss(
		HttpServletRequest request, HttpServletResponse response,
		String cssRealPath, String content) {

		try {
			content = DynamicCSSUtil.parseSass(request, cssRealPath, content);
		}
		catch (Exception e) {
			_log.error("Unable to parse SASS on CSS " + cssRealPath, e);

			if (_log.isDebugEnabled()) {
				_log.debug(content);
			}

			response.setHeader(
				HttpHeaders.CACHE_CONTROL,
				HttpHeaders.CACHE_CONTROL_NO_CACHE_VALUE);
		}

		String browserId = ParamUtil.getString(request, "browserId");

		if (!browserId.equals(BrowserSniffer.BROWSER_ID_IE)) {
			Matcher matcher = _pattern.matcher(content);

			content = matcher.replaceAll(StringPool.BLANK);
		}

		return MinifierUtil.minifyCss(content);
	}

	protected String minifyJavaScript(File file) throws IOException {
		String content = FileUtil.read(file);

		return minifyJavaScript(content);
	}

	protected String minifyJavaScript(String content) {
		return MinifierUtil.minifyJavaScript(content);
	}

	@Override
	protected void processFilter(
			HttpServletRequest request, HttpServletResponse response,
			FilterChain filterChain)
		throws Exception {

		Object minifiedContent = getMinifiedContent(
			request, response, filterChain);

		if (minifiedContent == null) {
			minifiedContent = getMinifiedBundleContent(request, response);
		}

		if (minifiedContent == null) {
			processFilter(MinifierFilter.class, request, response, filterChain);
		}
		else {
			if (minifiedContent instanceof File) {
				ServletResponseUtil.write(response, (File)minifiedContent);
			}
			else if (minifiedContent instanceof String) {
				ServletResponseUtil.write(response, (String)minifiedContent);
			}
		}
	}

	protected String sterilizeQueryString(String queryString) {
		return StringUtil.replace(
			queryString, new String[] {StringPool.SLASH, StringPool.BACK_SLASH},
			new String[] {StringPool.UNDERLINE, StringPool.UNDERLINE});
	}

	private static final String _CSS_COMMENT_BEGIN = "/*";

	private static final String _CSS_COMMENT_END = "*/";

	private static final String _CSS_EXTENSION = ".css";

	private static final String _CSS_IMPORT_BEGIN = "@import url(";

	private static final String _CSS_IMPORT_END = ");";

	private static final String _JAVASCRIPT_EXTENSION = ".js";

	private static final String _JSP_EXTENSION = ".jsp";

	private static final String _TEMP_DIR =
		SystemProperties.get(SystemProperties.TMP_DIR) + "/liferay/minifier";

	private static Log _log = LogFactoryUtil.getLog(MinifierFilter.class);

	private static Pattern _pattern = Pattern.compile(
		"^(\\.ie|\\.js\\.ie)([^}]*)}", Pattern.MULTILINE);

	private ServletContext _servletContext;
	private String _servletContextName;
	private String _tempDir = _TEMP_DIR;

}