/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.js.web.internal.servlet;

import com.liferay.frontend.js.web.internal.language.LanguageState;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.URLUtil;

import java.net.URL;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

/**
 * @author Iván Zaera Avellón
 */
public class ServletContextServiceTrackerCustomizer
	implements ServiceTrackerCustomizer<ServletContext, String> {

	public ServletContextServiceTrackerCustomizer(
		BundleContext bundleContext, JSONFactory jsonFactory,
		Language language) {

		_bundleContext = bundleContext;
		_jsonFactory = jsonFactory;
		_language = language;
	}

	@Override
	public String addingService(
		ServiceReference<ServletContext> serviceReference) {

		ServletContext servletContext = _bundleContext.getService(
			serviceReference);

		try {
			String contextPath = servletContext.getContextPath();

			if (!contextPath.startsWith(_WEB_CONTEXT_PATH_PREFIX)) {
				return null;
			}

			String webContextPath = contextPath.substring(
				_WEB_CONTEXT_PATH_PREFIX.length());

			List<String> keys = _getLanguageKeys(servletContext);

			if (keys != null) {
				if (_log.isDebugEnabled()) {
					_log.debug(
						StringBundler.concat(
							"Web context path '", webContextPath,
							"' added, contains ", keys.size(), " keys"));
				}

				synchronized (this) {
					_webContextPathKeysMap.put(webContextPath, keys);

					LanguageState.set(
						new LanguageState(_webContextPathKeysMap, _language));
				}
			}

			return webContextPath;
		}
		finally {
			_bundleContext.ungetService(serviceReference);
		}
	}

	@Override
	public void modifiedService(
		ServiceReference<ServletContext> serviceReference,
		String webContextPath) {
	}

	@Override
	public void removedService(
		ServiceReference<ServletContext> serviceReference,
		String webContextPath) {

		if (_log.isDebugEnabled()) {
			_log.debug(
				StringBundler.concat(
					"Web context path '", webContextPath, "' removed"));
		}

		synchronized (this) {
			_webContextPathKeysMap.remove(webContextPath);

			LanguageState.set(
				new LanguageState(_webContextPathKeysMap, _language));
		}
	}

	private List<String> _getLanguageKeys(ServletContext servletContext) {
		try {
			URL url = servletContext.getResource("/language.json");

			if (url == null) {
				return null;
			}

			JSONObject jsonObject = _jsonFactory.createJSONObject(
				URLUtil.toString(url));

			return JSONUtil.toStringList(jsonObject.getJSONArray("keys"));
		}
		catch (Exception exception) {
			_log.error(
				"Unable to get language.json keys from servlet context " +
					servletContext.getContextPath(),
				exception);

			return null;
		}
	}

	private static final String _WEB_CONTEXT_PATH_PREFIX =
		Portal.PATH_MODULE + StringPool.SLASH;

	private static final Log _log = LogFactoryUtil.getLog(
		ServletContextServiceTrackerCustomizer.class);

	private final BundleContext _bundleContext;
	private final JSONFactory _jsonFactory;
	private final Language _language;
	private final Map<String, List<String>> _webContextPathKeysMap =
		new HashMap<>();

}