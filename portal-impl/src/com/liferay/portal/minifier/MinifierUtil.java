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

package com.liferay.portal.minifier;

import com.liferay.portal.kernel.io.unsync.UnsyncStringReader;
import com.liferay.portal.kernel.io.unsync.UnsyncStringWriter;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.InstanceFactory;
import com.liferay.portal.kernel.util.PortalClassLoaderUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.util.PropsValues;

import com.yahoo.platform.yui.compressor.CssCompressor;

/**
 * @author Brian Wing Shun Chan
 * @author Raymond Augé
 * @author Roberto Díaz
 */
public class MinifierUtil {

	public static String minifyCss(String content) {
		if (!PropsValues.MINIFIER_ENABLED) {
			return content;
		}

		return _instance._minifyCss(content);
	}

	public static String minifyJavaScript(String resourceName, String content) {
		if (!PropsValues.MINIFIER_ENABLED) {
			return content;
		}

		return _instance._minifyJavaScript(resourceName, content);
	}

	private static JavaScriptMinifier _getJavaScriptMinifier() {
		try {
			return (JavaScriptMinifier)InstanceFactory.newInstance(
				PortalClassLoaderUtil.getClassLoader(),
				PropsValues.MINIFIER_JAVASCRIPT_IMPL);
		}
		catch (Exception e) {
			_log.error(
				"Unable to instantiate " +
					PropsValues.MINIFIER_JAVASCRIPT_IMPL,
				e);

			return new GoogleJavaScriptMinifier();
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

			return _processMinifiedCss(unsyncStringWriter.toString());
		}
		catch (Exception e) {
			_log.error("Unable to minify CSS:\n" + content, e);

			unsyncStringWriter.append(content);

			return unsyncStringWriter.toString();
		}
	}

	private String _minifyJavaScript(String resourceName, String content) {
		return _javaScriptMinifierInstance.compress(resourceName, content);
	}

	private String _processMinifiedCss(String minifiedCss) {
		int index = 0;

		while ((index = minifiedCss.indexOf("calc(", index)) != -1) {
			index += 5;

			int startIndex = index;

			int parensCount;

			for (parensCount = 1;
				parensCount != 0 && index < minifiedCss.length();
				index++) {

				switch (minifiedCss.charAt(index)) {
					case '(':
						parensCount++;
						break;

					case ')':
						parensCount--;
						break;
				}
			}

			if (parensCount == 0) {
				String replacement = minifiedCss.substring(
					startIndex, index - 1);

				replacement = replacement.replaceAll("\\+", " + ");
				replacement = replacement.replaceAll("-", " - ");
				replacement = replacement.replaceAll("\\*", " * ");
				replacement = replacement.replaceAll("/", " / ");

				StringBundler sb = new StringBundler(3);

				sb.append(minifiedCss.substring(0, startIndex));
				sb.append(replacement);
				sb.append(minifiedCss.substring(index - 1));

				index += replacement.length() - (index - startIndex);

				minifiedCss = sb.toString();
			}
		}

		return minifiedCss;
	}

	private static final Log _log = LogFactoryUtil.getLog(MinifierUtil.class);

	private static final MinifierUtil _instance = new MinifierUtil();

	private final JavaScriptMinifier _javaScriptMinifierInstance;

}