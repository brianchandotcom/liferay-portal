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

import com.liferay.portal.internal.minifier.MinifierThreadLocal;
import com.liferay.portal.kernel.io.unsync.UnsyncStringReader;
import com.liferay.portal.kernel.io.unsync.UnsyncStringWriter;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.util.PropsValues;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.ServiceTracker;
import org.apache.commons.lang.time.StopWatch;

/**
 * @author Brian Wing Shun Chan
 * @author Raymond Augé
 * @author Roberto Díaz
 */
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
		catch (Exception e) {
			_log.error("Unable to minify CSS:\n" + content, e);

			unsyncStringWriter.append(content);

			return unsyncStringWriter.toString();
		}
		finally {
			if (_log.isDebugEnabled()) {
				int lenght = 0;

				if (content != null) {
					byte[] bytes = content.getBytes();

					lenght = bytes.length;
				}

				_log.debug(
					StringBundler.concat(
						"_minifyCss for size", String.valueOf(lenght),
						"B takes ", String.valueOf(stopWatch.getTime()),
						" ms"));
			}
		}
	}

	private static String _minifyJavaScript(
		String resourceName, String content) {

		JavaScriptMinifier javaScriptMinifier =
			_javaScriptMinifierServiceTracker.getService();

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
				int lenght = 0;

				if (content != null) {
					byte[] bytes = content.getBytes();

					lenght = bytes.length;
				}

				_log.debug(
					StringBundler.concat(
						"minifyJavaScript for ", resourceName, " with size ",
						String.valueOf(lenght), "B takes ",
						String.valueOf(stopWatch.getTime()), " ms"));
			}
		}
	}

	private MinifierUtil() {
	}

	private static final Log _log = LogFactoryUtil.getLog(MinifierUtil.class);

	private static final ServiceTracker<JavaScriptMinifier, JavaScriptMinifier>
		_javaScriptMinifierServiceTracker;

	static {
		Registry registry = RegistryUtil.getRegistry();

		_javaScriptMinifierServiceTracker = registry.trackServices(
			JavaScriptMinifier.class);

		_javaScriptMinifierServiceTracker.open();
	}

}