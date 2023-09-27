/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.minifier;

import com.liferay.osgi.service.tracker.collections.map.ServiceReferenceMapper;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.petra.io.unsync.UnsyncStringReader;
import com.liferay.petra.io.unsync.UnsyncStringWriter;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.internal.minifier.MinifierThreadLocal;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.module.util.SystemBundleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.ServiceProxyFactory;
import com.liferay.portal.util.PropsValues;

import javax.servlet.ServletContext;

import org.apache.commons.lang.time.StopWatch;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

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

	private static String _getServletContextPath(String resourceName) {
		String pathModule = Portal.PATH_MODULE;

		int i = resourceName.indexOf(StringPool.SLASH, pathModule.length() + 1);

		if (i == -1) {
			return StringPool.BLANK;
		}

		return resourceName.substring(0, i);
	}

	private static boolean _isLiferayResource(String resourceName) {
		if (resourceName.startsWith(Portal.PATH_MODULE + "/admin-theme/") ||
			resourceName.startsWith(Portal.PATH_MODULE + "/classic-theme/") ||
			resourceName.startsWith(
				Portal.PATH_MODULE + "/js/resolved-module/") ||
			_liferayServletContextsMap.containsKey(
				_getServletContextPath(resourceName))) {

			return true;
		}

		return false;
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
		catch (Throwable throwable) {
			String failingContent = content;

			if (content.length() > 1048576) {
				failingContent = failingContent.substring(0, 1048575);
			}

			_log.error("Unable to minify CSS:\n" + failingContent, throwable);

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

		if ((javaScriptMinifier == null) || _isLiferayResource(resourceName)) {
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
	private static final ServiceTrackerMap<String, ServletContext>
		_liferayServletContextsMap;

	static {
		_liferayServletContextsMap =
			ServiceTrackerMapFactory.openSingleValueMap(
				SystemBundleUtil.getBundleContext(), ServletContext.class, null,
				new ServiceReferenceMapper<String, ServletContext>() {

					@Override
					public void map(
						ServiceReference<ServletContext> serviceReference,
						Emitter<String> emitter) {

						Bundle bundle = serviceReference.getBundle();

						String symbolicName = bundle.getSymbolicName();

						if (!symbolicName.startsWith("com.liferay.")) {
							return;
						}

						BundleContext bundleContext =
							SystemBundleUtil.getBundleContext();

						ServletContext servletContext =
							bundleContext.getService(serviceReference);

						try {
							emitter.emit(servletContext.getContextPath());
						}
						finally {
							bundleContext.ungetService(serviceReference);
						}
					}

				});
	}

}