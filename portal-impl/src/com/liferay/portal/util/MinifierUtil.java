/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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
import com.liferay.portal.kernel.util.StringPool;

import com.yahoo.platform.yui.compressor.CssCompressor;

/**
 * @author Brian Wing Shun Chan
 * @author Raymond Augé
 */
public class MinifierUtil {

	public static String minifyCss(String content) {
		if (!PropsValues.MINIFIER_ENABLED) {
			return content;
		}

		return _instance._minifyCss(content);
	}

	@Deprecated
	public static String minifyJavaScript(String content) {
		if (!PropsValues.MINIFIER_ENABLED) {
			return content;
		}

		return _instance._minifyJavaScript(content);
	}

	public static String minifyJavaScript(String resource, String content) {
		if (!PropsValues.MINIFIER_ENABLED) {
			return content;
		}

		return _instance._minifyJavaScript(resource, content);
	}

	private static JavaScriptMinifier _getJavaScriptMinifier() {
		try {
			Class<JavaScriptMinifier> javaScriptMinifierClass =
				(Class<JavaScriptMinifier>)Class.forName(
					PropsValues.JAVASCRIPT_MINIFIER);

			return javaScriptMinifierClass.newInstance();
		}
		catch (Exception e) {
			if (_log.isErrorEnabled()) {
				_log.error(
					"Could not instantiate "+ PropsValues.JAVASCRIPT_MINIFIER +
						". Returning YUIJavaScriptMinifier as default");
			}

			return new YUIJavaScriptMinifier();
		}
	}

	private MinifierUtil() {
		_javaScriptMinifierInstance = _getJavaScriptMinifier();
	}

	private String _minifyCss(String content) {
		UnsyncStringWriter unsyncStringWriter = new UnsyncStringWriter();

		try {
			CssCompressor cssCompressor = new CssCompressor(
				new UnsyncStringReader(content));

			cssCompressor.compress(
				unsyncStringWriter, PropsValues.YUI_COMPRESSOR_CSS_LINE_BREAK);
		}
		catch (Exception e) {
			_log.error("CSS Minifier failed for\n" + content);

			unsyncStringWriter.append(content);
		}

		return unsyncStringWriter.toString();
	}

	@Deprecated
	private String _minifyJavaScript(String content) {
		return _javaScriptMinifierInstance.compress(StringPool.BLANK, content);
	}

	private String _minifyJavaScript(String resource, String content) {
		return _javaScriptMinifierInstance.compress(resource, content);
	}

	private static Log _log = LogFactoryUtil.getLog(MinifierUtil.class);

	private static MinifierUtil _instance = new MinifierUtil();

	private JavaScriptMinifier _javaScriptMinifierInstance;

}