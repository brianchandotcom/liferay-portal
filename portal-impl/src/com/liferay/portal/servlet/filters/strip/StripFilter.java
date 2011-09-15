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

package com.liferay.portal.servlet.filters.strip;

import com.liferay.portal.kernel.concurrent.ConcurrentLRUCache;
import com.liferay.portal.kernel.io.OutputStreamWriter;
import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayOutputStream;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.scripting.ScriptingException;
import com.liferay.portal.kernel.servlet.HttpHeaders;
import com.liferay.portal.kernel.servlet.ServletResponseUtil;
import com.liferay.portal.kernel.servlet.StringServletResponse;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.KMPSearch;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.servlet.filters.BasePortalFilter;
import com.liferay.portal.servlet.filters.dynamiccss.DynamicCSSUtil;
import com.liferay.portal.util.MinifierUtil;
import com.liferay.portal.util.PropsValues;

import java.io.IOException;
import java.io.Writer;

import java.nio.CharBuffer;

import java.util.HashSet;
import java.util.Set;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Brian Wing Shun Chan
 * @author Raymond Augé
 * @author Shuyang Zhou
 */
public class StripFilter extends BasePortalFilter {

	public static final String SKIP_FILTER =
		StripFilter.class.getName() + "SKIP_FILTER";

	public StripFilter() {
		if (PropsValues.MINIFIER_INLINE_CONTENT_CACHE_SIZE > 0) {
			_minifierCache = new ConcurrentLRUCache<String, String>(
				PropsValues.MINIFIER_INLINE_CONTENT_CACHE_SIZE);
		}
	}

	@Override
	public void init(FilterConfig filterConfig) {
		super.init(filterConfig);

		for (String ignorePath : PropsValues.STRIP_IGNORE_PATHS) {
			_ignorePaths.add(ignorePath);
		}
	}

	@Override
	public boolean isFilterEnabled(
		HttpServletRequest request, HttpServletResponse response) {

		if (isStrip(request) && !isInclude(request) &&
			!isAlreadyFiltered(request)) {

			return true;
		}
		else {
			return false;
		}
	}

	protected String extractContent(CharBuffer charBuffer, int length) {

		// See LPS-10545

		/*String content = charBuffer.subSequence(0, length).toString();

		int position = charBuffer.position();

		charBuffer.position(position + length);*/

		CharBuffer duplicateCharBuffer = charBuffer.duplicate();

		int position = duplicateCharBuffer.position() + length;

		String content = duplicateCharBuffer.limit(position).toString();

		charBuffer.position(position);

		return content;
	}

	protected boolean hasMarker(CharBuffer charBuffer, char[] marker) {
		int position = charBuffer.position();

		if ((position + marker.length) >= charBuffer.limit()) {
			return false;
		}

		for (int i = 0; i < marker.length; i++) {
			char c = marker[i];

			char oldC = charBuffer.charAt(i);

			if ((c != oldC) && (Character.toUpperCase(c) != oldC)) {
				return false;
			}
		}

		return true;
	}

	protected boolean isAlreadyFiltered(HttpServletRequest request) {
		if (request.getAttribute(SKIP_FILTER) != null) {
			return true;
		}
		else {
			return false;
		}
	}

	protected boolean isInclude(HttpServletRequest request) {
		String uri = (String)request.getAttribute(
			JavaConstants.JAVAX_SERVLET_INCLUDE_REQUEST_URI);

		if (uri == null) {
			return false;
		}
		else {
			return true;
		}
	}

	protected boolean isStrip(HttpServletRequest request) {
		if (!ParamUtil.getBoolean(request, _STRIP, true)) {
			return false;
		}

		String path = request.getPathInfo();

		if (_ignorePaths.contains(path)) {
			if (_log.isDebugEnabled()) {
				_log.debug("Ignore path " + path);
			}

			return false;
		}

		// Modifying binary content through a servlet filter under certain
		// conditions is bad on performance the user will not start downloading
		// the content until the entire content is modified.

		String lifecycle = ParamUtil.getString(request, "p_p_lifecycle");

		if ((lifecycle.equals("1") &&
			 LiferayWindowState.isExclusive(request)) ||
			lifecycle.equals("2")) {

			return false;
		}
		else {
			return true;
		}
	}

	protected void outputCloseTag(
			CharBuffer charBuffer, Writer writer, String closeTag)
		throws IOException {

		writer.write(closeTag);

		charBuffer.position(charBuffer.position() + closeTag.length());

		skipWhiteSpace(charBuffer, writer, true);
	}

	protected void outputOpenTag(
		CharBuffer charBuffer, Writer writer, char[] openTag)
		throws IOException {

		writer.write(openTag);

		charBuffer.position(charBuffer.position() + openTag.length);
	}

	protected void processCSS(
			HttpServletRequest request, HttpServletResponse response,
			CharBuffer charBuffer, Writer writer)
		throws IOException {

		outputOpenTag(charBuffer, writer, _MARKER_STYLE_OPEN);

		int length = KMPSearch.search(
			charBuffer, _MARKER_STYLE_CLOSE, _MARKER_STYLE_CLOSE_NEXTS);

		if (length == -1) {
			if (_log.isWarnEnabled()) {
				_log.warn("Missing </style>");
			}

			return;
		}

		if (length == 0) {
			outputCloseTag(charBuffer, writer, _MARKER_STYLE_CLOSE);

			return;
		}

		String content = extractContent(charBuffer, length);

		String minifiedContent = content;

		if (PropsValues.MINIFIER_INLINE_CONTENT_CACHE_SIZE > 0) {
			String key = String.valueOf(content.hashCode());

			minifiedContent = _minifierCache.get(key);

			if (minifiedContent == null) {
				try {
					content = DynamicCSSUtil.parseSass(request, key, content);
				}
				catch (ScriptingException se) {
					_log.error("Unable to parse SASS on CSS " + key, se);

					if (_log.isDebugEnabled()) {
						_log.debug(content);
					}

					if (response != null) {
						response.setHeader(
							HttpHeaders.CACHE_CONTROL,
							HttpHeaders.CACHE_CONTROL_NO_CACHE_VALUE);
					}
				}

				minifiedContent = MinifierUtil.minifyCss(content);

				boolean skipCache = false;

				for (String skipCss :
						PropsValues.MINIFIER_INLINE_CONTENT_CACHE_SKIP_CSS) {

					if (minifiedContent.contains(skipCss)) {
						skipCache = true;

						break;
					}
				}

				if (!skipCache) {
					_minifierCache.put(key, minifiedContent);
				}
			}
		}

		if (!Validator.isNull(minifiedContent)) {
			writer.write(minifiedContent);
		}

		outputCloseTag(charBuffer, writer, _MARKER_STYLE_CLOSE);
	}

	@Override
	protected void processFilter(
			HttpServletRequest request, HttpServletResponse response,
			FilterChain filterChain)
		throws Exception {

		if (_log.isDebugEnabled()) {
			String completeURL = HttpUtil.getCompleteURL(request);

			_log.debug("Stripping " + completeURL);
		}

		request.setAttribute(SKIP_FILTER, Boolean.TRUE);

		StringServletResponse stringResponse = new StringServletResponse(
			response);

		processFilter(StripFilter.class, request, stringResponse, filterChain);

		String contentType = GetterUtil.getString(
			stringResponse.getContentType()).toLowerCase();

		if (_log.isDebugEnabled()) {
			_log.debug("Stripping content of type " + contentType);
		}

		response.setContentType(contentType);

		if (contentType.startsWith(ContentTypes.TEXT_HTML) &&
			(stringResponse.getStatus() == HttpServletResponse.SC_OK)) {

			CharBuffer oldCharBuffer = CharBuffer.wrap(
				stringResponse.getString());

			boolean ensureContentLength = ParamUtil.getBoolean(
				request, _ENSURE_CONTENT_LENGTH);

			if (ensureContentLength) {
				UnsyncByteArrayOutputStream unsyncByteArrayOutputStream =
					new UnsyncByteArrayOutputStream();

				strip(
					request, response, oldCharBuffer,
					new OutputStreamWriter(unsyncByteArrayOutputStream));

				response.setContentLength(unsyncByteArrayOutputStream.size());

				unsyncByteArrayOutputStream.writeTo(response.getOutputStream());
			}
			else {
				strip(request, response, oldCharBuffer, response.getWriter());
			}
		}
		else {
			ServletResponseUtil.write(response, stringResponse);
		}
	}

	protected void processInput(CharBuffer oldCharBuffer, Writer writer)
		throws IOException {

		int length = KMPSearch.search(
			oldCharBuffer, _MARKER_INPUT_OPEN.length + 1, _MARKER_INPUT_CLOSE,
			_MARKER_INPUT_CLOSE_NEXTS);

		if (length == -1) {
			if (_log.isWarnEnabled()) {
				_log.warn("Missing />");
			}

			outputOpenTag(oldCharBuffer, writer, _MARKER_INPUT_OPEN);

			return;
		}

		length += _MARKER_INPUT_CLOSE.length();

		String content = extractContent(oldCharBuffer, length);

		writer.write(content);

		skipWhiteSpace(oldCharBuffer, writer, true);
	}

	protected void processJavaScript(
			CharBuffer charBuffer, Writer writer, char[] openTag)
		throws IOException {

		StringBundler sb = new StringBundler();

		sb.append(openTag);

		for (int i = openTag.length; i < charBuffer.length(); i++) {
			char c = charBuffer.charAt(i);

			sb.append(c);

			if (c == CharPool.GREATER_THAN) {
				break;
			}
			else if (c == CharPool.LESS_THAN) {
				return;
			}
		}

		openTag = sb.toString().toCharArray();

		outputOpenTag(charBuffer, writer, openTag);

		int length = KMPSearch.search(
			charBuffer, _MARKER_SCRIPT_CLOSE, _MARKER_SCRIPT_CLOSE_NEXTS);

		if (length == -1) {
			if (_log.isWarnEnabled()) {
				_log.warn("Missing </script>");
			}

			return;
		}

		if (length == 0) {
			outputCloseTag(charBuffer, writer, _MARKER_SCRIPT_CLOSE);

			return;
		}

		String content = extractContent(charBuffer, length);

		String minifiedContent = content;

		if (PropsValues.MINIFIER_INLINE_CONTENT_CACHE_SIZE > 0) {
			String key = String.valueOf(content.hashCode());

			minifiedContent = _minifierCache.get(key);

			if (minifiedContent == null) {
				minifiedContent = MinifierUtil.minifyJavaScript(content);

				boolean skipCache = false;

				for (String skipJavaScript :
						PropsValues.
							MINIFIER_INLINE_CONTENT_CACHE_SKIP_JAVASCRIPT) {

					if (minifiedContent.contains(skipJavaScript)) {
						skipCache = true;

						break;
					}
				}

				if (!skipCache) {
					_minifierCache.put(key, minifiedContent);
				}
			}
		}

		if (!Validator.isNull(minifiedContent)) {
			writer.write(_CDATA_OPEN);
			writer.write(minifiedContent);
			writer.write(_CDATA_CLOSE);
		}

		outputCloseTag(charBuffer, writer, _MARKER_SCRIPT_CLOSE);
	}

	protected void processPre(CharBuffer oldCharBuffer, Writer writer)
		throws IOException {

		int length = KMPSearch.search(
			oldCharBuffer, _MARKER_PRE_OPEN.length + 1, _MARKER_PRE_CLOSE,
			_MARKER_PRE_CLOSE_NEXTS);

		if (length == -1) {
			if (_log.isWarnEnabled()) {
				_log.warn("Missing </pre>");
			}

			outputOpenTag(oldCharBuffer, writer, _MARKER_PRE_OPEN);

			return;
		}

		length += _MARKER_PRE_CLOSE.length();

		String content = extractContent(oldCharBuffer, length);

		writer.write(content);

		skipWhiteSpace(oldCharBuffer, writer, true);
	}

	protected void processTextArea(CharBuffer oldCharBuffer, Writer writer)
		throws IOException {

		int length = KMPSearch.search(
			oldCharBuffer, _MARKER_TEXTAREA_OPEN.length + 1,
			_MARKER_TEXTAREA_CLOSE, _MARKER_TEXTAREA_CLOSE_NEXTS);

		if (length == -1) {
			if (_log.isWarnEnabled()) {
				_log.warn("Missing </textArea>");
			}

			outputOpenTag(oldCharBuffer, writer, _MARKER_TEXTAREA_OPEN);
			return;
		}

		length += _MARKER_TEXTAREA_CLOSE.length();

		String content = extractContent(oldCharBuffer, length);

		writer.write(content);

		skipWhiteSpace(oldCharBuffer, writer, true);
	}

	protected boolean skipWhiteSpace(
			CharBuffer charBuffer, Writer writer, boolean appendSeparator)
		throws IOException {

		boolean skipped = false;

		for (int i = charBuffer.position(); i < charBuffer.limit(); i++) {
			char c = charBuffer.get();

			if ((c == CharPool.SPACE) || (c == CharPool.TAB) ||
				(c == CharPool.RETURN) || (c == CharPool.NEW_LINE)) {

				skipped = true;

				continue;
			}
			else {
				charBuffer.position(i);

				break;
			}
		}

		if (skipped && appendSeparator) {
			writer.write(CharPool.SPACE);
		}

		return skipped;
	}

	protected void strip(
			HttpServletRequest request, HttpServletResponse response,
			CharBuffer charBuffer, Writer writer)
		throws IOException {

		skipWhiteSpace(charBuffer, writer, false);

		while (charBuffer.hasRemaining()) {
			char c = charBuffer.get();

			writer.write(c);

			if (c == CharPool.LESS_THAN) {
				if (hasMarker(charBuffer, _MARKER_INPUT_OPEN)) {
					processInput(charBuffer, writer);

					continue;
				}
				else if (hasMarker(charBuffer, _MARKER_PRE_OPEN)) {
					processPre(charBuffer, writer);

					continue;
				}
				else if (hasMarker(charBuffer, _MARKER_TEXTAREA_OPEN)) {
					processTextArea(charBuffer, writer);

					continue;
				}
				else if (hasMarker(charBuffer, _MARKER_SCRIPT_OPEN)) {
					processJavaScript(charBuffer, writer, _MARKER_SCRIPT_OPEN);

					continue;
				}
				else if (hasMarker(charBuffer, _MARKER_STYLE_OPEN)) {
					processCSS(request, response, charBuffer, writer);

					continue;
				}
			}
			else if (c == CharPool.GREATER_THAN) {
				skipWhiteSpace(charBuffer, writer, true);
			}

			skipWhiteSpace(charBuffer, writer, true);
		}

		writer.flush();
	}

	private static final String _CDATA_CLOSE = "/*]]>*/";

	private static final String _CDATA_OPEN = "/*<![CDATA[*/";

	private static final String _ENSURE_CONTENT_LENGTH = "ensureContentLength";

	private static final String _MARKER_INPUT_CLOSE = "/>";

	private static final int [] _MARKER_INPUT_CLOSE_NEXTS =
		KMPSearch.generateNexts(_MARKER_INPUT_CLOSE);

	private static final char[] _MARKER_INPUT_OPEN = "input".toCharArray();

	private static final String _MARKER_PRE_CLOSE = "/pre>";

	private static final int[] _MARKER_PRE_CLOSE_NEXTS =
		KMPSearch.generateNexts(_MARKER_PRE_CLOSE);

	private static final char[] _MARKER_PRE_OPEN = "pre>".toCharArray();

	private static final String _MARKER_SCRIPT_CLOSE = "</script>";

	private static final int[] _MARKER_SCRIPT_CLOSE_NEXTS =
		KMPSearch.generateNexts(_MARKER_SCRIPT_CLOSE);

	private static final char[] _MARKER_SCRIPT_OPEN = "script".toCharArray();

	private static final String _MARKER_STYLE_CLOSE = "</style>";

	private static final int[] _MARKER_STYLE_CLOSE_NEXTS =
		KMPSearch.generateNexts(_MARKER_STYLE_CLOSE);

	private static final char[] _MARKER_STYLE_OPEN =
		"style type=\"text/css\">".toCharArray();

	private static final String _MARKER_TEXTAREA_CLOSE = "/textarea>";

	private static final int[] _MARKER_TEXTAREA_CLOSE_NEXTS =
		KMPSearch.generateNexts(_MARKER_TEXTAREA_CLOSE);

	private static final char[] _MARKER_TEXTAREA_OPEN =
		"textarea ".toCharArray();

	private static final String _STRIP = "strip";

	private static Log _log = LogFactoryUtil.getLog(StripFilter.class);

	private Set<String> _ignorePaths = new HashSet<String>();
	private ConcurrentLRUCache<String, String> _minifierCache;

}