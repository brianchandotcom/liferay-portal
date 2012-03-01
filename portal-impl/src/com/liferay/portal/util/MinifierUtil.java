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

package com.liferay.portal.util;

import com.liferay.portal.kernel.io.unsync.UnsyncStringReader;
import com.liferay.portal.kernel.io.unsync.UnsyncStringWriter;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import com.yahoo.platform.yui.compressor.CssCompressor;
import com.yahoo.platform.yui.compressor.JavaScriptCompressor;

import com.yahoo.platform.yui.mozilla.javascript.ErrorReporter;
import com.yahoo.platform.yui.mozilla.javascript.EvaluatorException;

/**
 * @author Brian Wing Shun Chan
 */
public class MinifierUtil {

	public static String minifyCss(String content) {
		return _instance._minifyCss(content);
	}

	public static String minifyJavaScript(String content) {
		return _instance._minifyJavaScript(content);
	}

	private MinifierUtil() {
	}

	private String _minifyCss(String content) {
		UnsyncStringWriter unsyncStringWriter = new UnsyncStringWriter();

		try {
			CssCompressor cssCompressor = new CssCompressor(
				new UnsyncStringReader(content));

			cssCompressor.compress(unsyncStringWriter, _CSS_LINE_BREAK);
		}
		catch (Exception e) {
			_log.error("CSS Minifier failed for\n" + content);

			unsyncStringWriter.append(content);
		}

		return unsyncStringWriter.toString();
	}

	private String _minifyJavaScript(String content) {
		UnsyncStringWriter unsyncStringWriter = new UnsyncStringWriter();

		try {
			JavaScriptCompressor javaScriptCompressor =
				new JavaScriptCompressor(
					new UnsyncStringReader(content),
					new JavaScriptErrorReporter());

			javaScriptCompressor.compress(
					unsyncStringWriter, _JS_LINE_BREAK, _JS_MUNGE, _JS_VERBOSE,
					_JS_PRESERVE_ALL_SEMICOLONS, _JS_DISABLE_OPTIMIZATIONS);
		}
		catch (Exception e) {
			_log.error("JavaScript Minifier failed for\n" + content);

			unsyncStringWriter.append(content);
		}

		return unsyncStringWriter.toString();
	}

	private static final int _CSS_LINE_BREAK = -1;

	private static final boolean _JS_DISABLE_OPTIMIZATIONS = false;

	private static final int _JS_LINE_BREAK = -1;

	private static final boolean _JS_MUNGE = true;

	private static final boolean _JS_PRESERVE_ALL_SEMICOLONS = false;

	private static final boolean _JS_VERBOSE = false;

	private static Log _log = LogFactoryUtil.getLog(MinifierUtil.class);

	private static MinifierUtil _instance = new MinifierUtil();

	private class JavaScriptErrorReporter implements ErrorReporter {

		public void error(
			String message, String sourceName, int line, String lineSource,
			int lineOffset) {

			if (line < 0) {
				_log.error(message);
			}
			else {
				_log.error(line + ": " + lineOffset + ": " + message);
			}
		}

		public EvaluatorException runtimeError(
			String message, String sourceName, int line, String lineSource,
			int lineOffset) {

			error(message, sourceName, line, lineSource, lineOffset);

			return new EvaluatorException(message);
		}

		public void warning(
			String message, String sourceName, int line, String lineSource,
			int lineOffset) {

			if (!_log.isWarnEnabled()) {
				return;
			}

			if (line < 0) {
				_log.warn(message);
			}
			else {
				_log.warn(line + ": " + lineOffset + ": " + message);
			}
		}

	}

}