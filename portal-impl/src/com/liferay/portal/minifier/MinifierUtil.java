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

import com.liferay.petra.io.unsync.UnsyncStringReader;
import com.liferay.petra.io.unsync.UnsyncStringWriter;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.internal.minifier.MinifierThreadLocal;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ServiceProxyFactory;
import com.liferay.portal.util.PropsValues;

import org.apache.commons.lang.time.StopWatch;

/**
 * @author Brian Wing Shun Chan
 * @author Raymond Augé
 * @author Roberto Díaz
 * @deprecated As of Cavanaugh (7.4.x), with no direct replacement
 */
@Deprecated
public class MinifierUtil {

	public static String minifyCss(String content) {
		if (PropsValues.MINIFIER_ENABLED && MinifierThreadLocal.isEnabled()) {
			return _minifyCss(content);
		}

		return content;
	}

	public static String minifyJavaScript(String resourceName, String content) {
		if (PropsValues.MINIFIER_ENABLED && MinifierThreadLocal.isEnabled()) {
			return _minifyJavaScript(resourceName, content);
		}

		return content;
	}

	private static String _minifyCss(String content) {
		StopWatch stopWatch = new StopWatch();

		stopWatch.start();

		UnsyncStringWriter unsyncStringWriter = new UnsyncStringWriter();

		try {
			CSSCompressor cssCompressor = new CSSCompressor(
				new UnsyncStringReader(content));

			cssCompressor.compress(
				unsyncStringWriter, PropsValues.YUI_COMPRESSOR_CSS_LINE_BREAK);

			return unsyncStringWriter.toString();
		}
		catch (Exception exception) {
			_log.error("Unable to minify CSS:\n" + content, exception);

			unsyncStringWriter.append(content);

			return unsyncStringWriter.toString();
		}
		finally {
			if (_log.isDebugEnabled()) {
				int length = 0;

				if (content != null) {
					byte[] bytes = content.getBytes();

					length = bytes.length;
				}

				_log.debug(
					StringBundler.concat(
						"Minification for ", length, " bytes of CSS took ",
						stopWatch.getTime(), " ms"));
			}
		}
	}

	private static String _minifyJavaScript(
		String resourceName, String content) {

		JavaScriptMinifier javaScriptMinifier = _javaScriptMinifier;

		if (javaScriptMinifier == null) {
			return content;
		}

		StopWatch stopWatch = new StopWatch();

		stopWatch.start();

		try {
			return javaScriptMinifier.compress(resourceName, content);
		}
		finally {
			if (_log.isDebugEnabled()) {
				int length = 0;

				if (content != null) {
					byte[] bytes = content.getBytes();

					length = bytes.length;
				}

				_log.debug(
					StringBundler.concat(
						"Minification for ", length,
						" bytes of JavaScript in resource ", resourceName,
						" took ", stopWatch.getTime(), " ms"));
			}
		}
	}

	private MinifierUtil() {
	}

	private static final Log _log = LogFactoryUtil.getLog(MinifierUtil.class);

	private static volatile JavaScriptMinifier _javaScriptMinifier =
		ServiceProxyFactory.newServiceTrackedInstance(
			JavaScriptMinifier.class, MinifierUtil.class, "_javaScriptMinifier",
			false, true);

}