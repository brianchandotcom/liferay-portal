/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.js.web.internal.resource.handler;

import com.liferay.frontend.js.web.internal.configuration.FrontendCachingConfiguration;
import com.liferay.frontend.js.web.internal.resource.FrontendResource;
import com.liferay.frontend.js.web.internal.resource.JavaScriptFrontendResource;
import com.liferay.frontend.js.web.internal.util.FrontendJsWebUtil;
import com.liferay.petra.io.StreamUtil;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.frontend.hashed.files.HashedFilesRegistry;
import com.liferay.portal.kernel.frontend.hashed.files.HashedFilesUtil;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.PortletConfigFactory;
import com.liferay.portal.kernel.util.AggregateResourceBundle;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.language.LanguageResources;

import jakarta.portlet.PortletConfig;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;

import java.io.IOException;

import java.net.URL;

import java.util.Enumeration;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Iván Zaera Avellón
 */
public class JavaScriptFrontendResourceRequestHandler
	implements FrontendResourceRequestHandler {

	public JavaScriptFrontendResourceRequestHandler(
		ConfigurationProvider configurationProvider,
		HashedFilesRegistry hashedFilesRegistry, Language language,
		Portal portal, PortletConfigFactory portletConfigFactory) {

		_configurationProvider = configurationProvider;
		_hashedFilesRegistry = hashedFilesRegistry;
		_language = language;
		_portal = portal;
		_portletConfigFactory = portletConfigFactory;
	}

	@Override
	public boolean canHandleRequest(HttpServletRequest httpServletRequest) {
		String requestURI = httpServletRequest.getRequestURI();

		if (requestURI.endsWith(".js") &&
			(_hashedFilesRegistry.getResource(requestURI) != null)) {

			return true;
		}

		return false;
	}

	@Override
	public FrontendResource handleRequest(HttpServletRequest httpServletRequest)
		throws IOException, ServletException {

		String requestURI = httpServletRequest.getRequestURI();

		URL url = _hashedFilesRegistry.getResource(requestURI);

		if (url == null) {
			return null;
		}

		ResourceBundle resourceBundle = _getResourceBundle(httpServletRequest);

		String eTag =
			(resourceBundle == null) ? HashedFilesUtil.getHash(url.getFile()) :
				null;

		boolean immutable = false;

		if ((HashedFilesUtil.getHash(requestURI) != null) &&
			(resourceBundle == null)) {

			immutable = true;
		}

		FrontendCachingConfiguration frontendCachingConfiguration =
			FrontendJsWebUtil.getFrontendCachingConfiguration(
				_portal.getCompanyId(httpServletRequest),
				_configurationProvider);

		long maxAge = frontendCachingConfiguration.jsFilesMaxAge();
		boolean sendNoCache =
			frontendCachingConfiguration.sendNoCacheForJSFiles();

		if (immutable) {
			maxAge = 31536000;
			sendNoCache = false;
		}
		else if (resourceBundle != null) {
			maxAge = frontendCachingConfiguration.translatedJSFilesMaxAge();
			sendNoCache =
				frontendCachingConfiguration.sendNoCacheForTranslatedJSFiles();
		}

		return new JavaScriptFrontendResource(
			eTag, immutable, _language, maxAge, resourceBundle != null,
			resourceBundle, sendNoCache, url);
	}

	private ResourceBundle _getResourceBundle(
		HttpServletRequest httpServletRequest) {

		if (!_isTranslatable(httpServletRequest.getRequestURI())) {
			return null;
		}

		Locale locale = LocaleUtil.fromLanguageId(
			_language.getLanguageId(httpServletRequest));

		ResourceBundle resourceBundle = LanguageResources.getResourceBundle(
			locale);

		PortletConfig portletConfig = null;

		Enumeration<String> enumeration =
			httpServletRequest.getParameterNames();

		while (enumeration.hasMoreElements()) {
			String parameterName = enumeration.nextElement();

			int index = parameterName.indexOf(CharPool.COLON);

			if (index > 0) {
				portletConfig = _portletConfigFactory.get(
					parameterName.substring(0, index));
			}
		}

		if (portletConfig != null) {
			resourceBundle = new AggregateResourceBundle(
				portletConfig.getResourceBundle(locale), resourceBundle);
		}

		final ResourceBundle finalResourceBundle = resourceBundle;

		return new ResourceBundle() {

			@Override
			public Enumeration<String> getKeys() {
				return finalResourceBundle.getKeys();
			}

			@Override
			public Locale getLocale() {
				return locale;
			}

			@Override
			protected Object handleGetObject(String s) {
				return finalResourceBundle.getObject(s);
			}

		};
	}

	private boolean _isTranslatable(String requestURI) {
		if (!_translatable.containsKey(requestURI)) {
			URL url = _hashedFilesRegistry.getResource(requestURI);

			try {

				// Check content

				String content = StreamUtil.toString(url.openStream());

				if (!content.contains("Liferay.Language.get(")) {
					return false;
				}

				// Check language.json

				String portalContextPath =
					FrontendJsWebUtil.getPortalContextPath(_portal);

				String prefix = requestURI.substring(
					0,
					requestURI.indexOf(
						StringPool.SLASH,
						portalContextPath.length() +
							Portal.PATH_MODULE.length() + 1));

				URL languageJsonURL = _hashedFilesRegistry.getResource(
					prefix + "/language.json");

				if (languageJsonURL == null) {
					_translatable.putIfAbsent(requestURI, true);
				}
				else {
					_translatable.putIfAbsent(requestURI, false);
				}
			}
			catch (IOException ioException) {
				_log.error(
					"Unable to read translatable resource " + url, ioException);

				_translatable.putIfAbsent(requestURI, false);
			}
		}

		return _translatable.get(requestURI);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		JavaScriptFrontendResourceRequestHandler.class);

	private final ConfigurationProvider _configurationProvider;
	private final HashedFilesRegistry _hashedFilesRegistry;
	private final Language _language;
	private final Portal _portal;
	private final PortletConfigFactory _portletConfigFactory;
	private final Map<String, Boolean> _translatable =
		new ConcurrentHashMap<>();

}