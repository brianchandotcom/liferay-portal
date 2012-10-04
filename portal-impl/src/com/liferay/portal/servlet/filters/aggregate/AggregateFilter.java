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

package com.liferay.portal.servlet.filters.aggregate;

import com.liferay.portal.kernel.cache.key.CacheKeyGenerator;
import com.liferay.portal.kernel.cache.key.CacheKeyGeneratorUtil;
import com.liferay.portal.kernel.configuration.Filter;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.BrowserSniffer;
import com.liferay.portal.kernel.servlet.BufferCacheServletResponse;
import com.liferay.portal.kernel.servlet.HttpHeaders;
import com.liferay.portal.kernel.servlet.ServletResponseUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.servlet.filters.BasePortalFilter;
import com.liferay.portal.servlet.filters.dynamiccss.DynamicCSSUtil;
import com.liferay.portal.util.JavaScriptBundleUtil;
import com.liferay.portal.util.LimitedFilesCache;
import com.liferay.portal.util.MinifierUtil;
import com.liferay.portal.util.PropsUtil;
import com.liferay.portal.util.PropsValues;

import java.io.File;
import java.io.IOException;

import java.net.URL;
import java.net.URLConnection;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Brian Wing Shun Chan
 * @author Raymond Augé
 */
public class AggregateFilter extends BasePortalFilter {

	/**
	 * @see DynamicCSSUtil#_propagateQueryString(String, String)
	 */
	public static String aggregateCss(
			AggregateContext aggregateContext, String content)
		throws IOException {

		StringBundler sb = new StringBundler();

		int pos = 0;

		while (true) {
			int commentX = content.indexOf(_CSS_COMMENT_BEGIN, pos);
			int commentY = content.indexOf(
				_CSS_COMMENT_END, commentX + _CSS_COMMENT_BEGIN.length());

			int importX = content.indexOf(_CSS_IMPORT_BEGIN, pos);
			int importY = content.indexOf(
				_CSS_IMPORT_END, importX + _CSS_IMPORT_BEGIN.length());

			if ((importX == -1) || (importY == -1)) {
				sb.append(content.substring(pos));

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

				String mediaQuery = StringPool.BLANK;

				int mediaQueryImportX = content.indexOf(
					CharPool.CLOSE_PARENTHESIS,
					importX + _CSS_IMPORT_BEGIN.length());
				int mediaQueryImportY = content.indexOf(
					CharPool.SEMICOLON, importX + _CSS_IMPORT_BEGIN.length());

				String importFileName = null;

				if (importY != mediaQueryImportX) {
					mediaQuery = content.substring(
						mediaQueryImportX + 1, mediaQueryImportY);

					importFileName = content.substring(
						importX + _CSS_IMPORT_BEGIN.length(),
						mediaQueryImportX);
				}
				else {
					importFileName = content.substring(
						importX + _CSS_IMPORT_BEGIN.length(), importY);
				}

				String importContent = aggregateContext.getContent(
					importFileName);

				if (importContent == null) {
					if (_log.isWarnEnabled()) {
						_log.warn(
							"File " +
								aggregateContext.getFullPath(importFileName) +
									" does not exist");
					}

					importContent = StringPool.BLANK;
				}

				String importDir = StringPool.BLANK;

				int slashPos = importFileName.lastIndexOf(CharPool.SLASH);

				if (slashPos != -1) {
					importDir = StringPool.SLASH.concat(
						importFileName.substring(0, slashPos + 1));
				}

				aggregateContext.pushPath(importDir);

				importContent = aggregateCss(aggregateContext, importContent);

				aggregateContext.popPath(importDir);

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

				if (Validator.isNotNull(mediaQuery)) {
					sb.append(_CSS_MEDIA_QUERY);
					sb.append(CharPool.SPACE);
					sb.append(mediaQuery);
					sb.append(CharPool.OPEN_CURLY_BRACE);
					sb.append(importContent);
					sb.append(CharPool.CLOSE_CURLY_BRACE);

					pos = mediaQueryImportY + 1;
				}
				else {
					sb.append(importContent);

					pos = importY + _CSS_IMPORT_END.length();
				}
			}
		}

		return sb.toString();
	}

	public static String aggregateJavaScript(
		AggregateContext aggregateContext, String[] fileNames) {

		StringBundler sb = new StringBundler(fileNames.length * 2);

		for (String fileName : fileNames) {
			String content = aggregateContext.getContent(fileName);

			if (Validator.isNull(content)) {
				continue;
			}

			sb.append(content);
			sb.append(StringPool.NEW_LINE);
		}

		return getJavaScriptContent(sb.toString());
	}

	@Override
	public void init(FilterConfig filterConfig) {
		super.init(filterConfig);

		_servletContext = filterConfig.getServletContext();

		File tempDir = (File)_servletContext.getAttribute(
			JavaConstants.JAVAX_SERVLET_CONTEXT_TEMPDIR);

		_tempDir = new File(tempDir, _TEMP_DIR);

		_tempDir.mkdirs();

		if (PropsValues.MINIFIER_FILES_LIMIT > 0) {
			_limitedFilesCache = new LimitedFilesCache<String>(
				PropsValues.MINIFIER_FILES_LIMIT);

			if (_log.isDebugEnabled()) {
				_log.debug(
					"Aggregate files limit " +
						PropsValues.MINIFIER_FILES_LIMIT);
			}
		}
	}

	protected static String getJavaScriptContent(String content) {
		return MinifierUtil.minifyJavaScript(content);
	}

	protected Object getBundleContent(
			HttpServletRequest request, HttpServletResponse response)
		throws IOException {

		String minifierType = ParamUtil.getString(request, "minifierType");
		String bundleId = ParamUtil.getString(
			request, "bundleId",
			ParamUtil.getString(request, "minifierBundleId"));

		if (Validator.isNull(minifierType) ||
			Validator.isNull(bundleId) ||
			!ArrayUtil.contains(PropsValues.JAVASCRIPT_BUNDLE_IDS, bundleId)) {

			return null;
		}

		String bundleDir = PropsUtil.get(
			PropsKeys.JAVASCRIPT_BUNDLE_DIR, new Filter(bundleId));

		URL bundleDirURL = _servletContext.getResource(bundleDir);

		if (bundleDirURL == null) {
			return null;
		}

		String cacheFileName = getCacheFileName(request);

		String[] fileNames = JavaScriptBundleUtil.getFileNames(bundleId);

		File cacheFile = new File(_tempDir, cacheFileName);

		if (_limitedFilesCache != null) {
			_limitedFilesCache.put(cacheFileName);
		}

		if (cacheFile.exists()) {
			boolean staleCache = false;

			for (String fileName : fileNames) {
				URL resourceURL = _servletContext.getResource(
					bundleDir.concat(StringPool.SLASH).concat(fileName));

				if (resourceURL == null) {
					continue;
				}

				URLConnection urlConnection = resourceURL.openConnection();

				if (urlConnection.getLastModified() >
						cacheFile.lastModified()) {

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
			_log.info("Aggregating JavaScript bundle " + bundleId);
		}

		String content = null;

		if (fileNames.length == 0) {
			content = StringPool.BLANK;
		}
		else {
			AggregateContext aggregateContext = new ServletAggregateContext(
				_servletContext, StringPool.SLASH);

			aggregateContext.pushPath(bundleDir);

			content = aggregateJavaScript(aggregateContext, fileNames);
		}

		response.setContentType(ContentTypes.TEXT_JAVASCRIPT);

		FileUtil.write(cacheFile, content);

		return content;
	}

	protected String getCacheFileName(HttpServletRequest request) {
		CacheKeyGenerator cacheKeyGenerator =
			CacheKeyGeneratorUtil.getCacheKeyGenerator(
				AggregateFilter.class.getName());

		cacheKeyGenerator.append(request.getRequestURI());

		String queryString = request.getQueryString();

		if (queryString != null) {
			cacheKeyGenerator.append(sterilizeQueryString(queryString));
		}

		return String.valueOf(cacheKeyGenerator.finish());
	}

	protected Object getContent(
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

		String resourcePath = requestURI;

		String contextPath = request.getContextPath();

		if (!contextPath.equals(StringPool.SLASH)) {
			resourcePath = resourcePath.substring(contextPath.length());
		}

		URL resourceURL = _servletContext.getResource(resourcePath);

		if (resourceURL == null) {
			return null;
		}

		URLConnection urlConnection = resourceURL.openConnection();

		String cacheCommonFileName = getCacheFileName(request);

		File cacheContentTypeFile = new File(
			_tempDir, cacheCommonFileName + "_E_CONTENT_TYPE");
		File cacheDataFile = new File(
			_tempDir, cacheCommonFileName + "_E_DATA");

		if (cacheDataFile.exists() &&
			(cacheDataFile.lastModified() >= urlConnection.getLastModified())) {

			if (cacheContentTypeFile.exists()) {
				String contentType = FileUtil.read(cacheContentTypeFile);

				response.setContentType(contentType);
			}

			return cacheDataFile;
		}

		String content = null;

		if (resourcePath.endsWith(_CSS_EXTENSION)) {
			if (_log.isInfoEnabled()) {
				_log.info("Minifying CSS " + resourcePath);
			}

			content = getCssContent(
				request, response, resourceURL, resourcePath);

			response.setContentType(ContentTypes.TEXT_CSS);

			FileUtil.write(cacheContentTypeFile, ContentTypes.TEXT_CSS);
		}
		else if (resourcePath.endsWith(_JAVASCRIPT_EXTENSION)) {
			if (_log.isInfoEnabled()) {
				_log.info("Minifying JavaScript " + resourcePath);
			}

			content = getJavaScriptContent(resourceURL);

			response.setContentType(ContentTypes.TEXT_JAVASCRIPT);

			FileUtil.write(cacheContentTypeFile, ContentTypes.TEXT_JAVASCRIPT);
		}
		else if (resourcePath.endsWith(_JSP_EXTENSION)) {
			if (_log.isInfoEnabled()) {
				_log.info("Minifying JSP " + resourcePath);
			}

			BufferCacheServletResponse bufferCacheServletResponse =
				new BufferCacheServletResponse(response);

			processFilter(
				AggregateFilter.class, request, bufferCacheServletResponse,
				filterChain);

			bufferCacheServletResponse.finishResponse();

			content = bufferCacheServletResponse.getString();

			if (minifierType.equals("css")) {
				content = getCssContent(
					request, response, resourcePath, content);
			}
			else if (minifierType.equals("js")) {
				content = getJavaScriptContent(content);
			}

			FileUtil.write(
				cacheContentTypeFile,
				bufferCacheServletResponse.getContentType());
		}
		else {
			return null;
		}

		FileUtil.write(cacheDataFile, content);

		return content;
	}

	protected String getCssContent(
		HttpServletRequest request, HttpServletResponse response,
		String resourcePath, String content) {

		try {
			content = DynamicCSSUtil.parseSass(
				_servletContext, request, resourcePath, content);
		}
		catch (Exception e) {
			_log.error("Unable to parse SASS on CSS " + resourcePath, e);

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

	protected String getCssContent(
			HttpServletRequest request, HttpServletResponse response,
			URL resourceURL, String resourcePath)
		throws IOException {

		URLConnection urlConnection = resourceURL.openConnection();

		String content = StringUtil.read(urlConnection.getInputStream());

		content = aggregateCss(
			new ServletAggregateContext(_servletContext, resourcePath),
			content);

		return getCssContent(request, response, resourcePath, content);
	}

	protected String getJavaScriptContent(URL resourceURL) throws IOException {
		URLConnection urlConnection = resourceURL.openConnection();

		String content = StringUtil.read(urlConnection.getInputStream());

		return getJavaScriptContent(content);
	}

	@Override
	protected void processFilter(
			HttpServletRequest request, HttpServletResponse response,
			FilterChain filterChain)
		throws Exception {

		Object minifiedContent = getContent(request, response, filterChain);

		if (minifiedContent == null) {
			minifiedContent = getBundleContent(request, response);
		}

		if (minifiedContent == null) {
			processFilter(
				AggregateFilter.class, request, response, filterChain);
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

	private static final String _CSS_MEDIA_QUERY = "@media";

	private static final String _JAVASCRIPT_EXTENSION = ".js";

	private static final String _JSP_EXTENSION = ".jsp";

	private static final String _TEMP_DIR = "aggregate";

	private static Log _log = LogFactoryUtil.getLog(AggregateFilter.class);

	private static Pattern _pattern = Pattern.compile(
		"^(\\.ie|\\.js\\.ie)([^}]*)}", Pattern.MULTILINE);

	private LimitedFilesCache<String> _limitedFilesCache;
	private ServletContext _servletContext;
	private File _tempDir;

}