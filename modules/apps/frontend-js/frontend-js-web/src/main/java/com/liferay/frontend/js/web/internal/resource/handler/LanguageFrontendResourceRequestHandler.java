/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.js.web.internal.resource.handler;

import com.liferay.frontend.js.web.internal.configuration.FrontendCachingConfiguration;
import com.liferay.frontend.js.web.internal.resource.FrontendResource;
import com.liferay.frontend.js.web.internal.resource.LanguageFrontendResource;
import com.liferay.frontend.js.web.internal.util.FrontendJSWebUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.frontend.hashed.files.HashedFilesRegistry;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.Portal;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;

import java.io.IOException;

import java.net.URL;

import java.util.Objects;

/**
 * @author Iván Zaera Avellón
 */
public class LanguageFrontendResourceRequestHandler
	implements FrontendResourceRequestHandler {

	public static final String LANGUAGE_MODULE_PREFIX = "@liferay/language/";

	public static final String LANGUAGE_URI_PREFIX = "/o/js/language/";

	public LanguageFrontendResourceRequestHandler(
		ConfigurationProvider configurationProvider,
		HashedFilesRegistry hashedFilesRegistry, JSONFactory jsonFactory,
		Language language, Portal portal) {

		_configurationProvider = configurationProvider;
		_hashedFilesRegistry = hashedFilesRegistry;
		_jsonFactory = jsonFactory;
		_language = language;
		_portal = portal;
	}

	@Override
	public boolean canHandleRequest(HttpServletRequest httpServletRequest) {
		String requestURI = httpServletRequest.getRequestURI();

		if (requestURI.startsWith(
				FrontendJSWebUtil.getPortalContextPath(_portal) +
					LANGUAGE_URI_PREFIX) &&
			requestURI.endsWith("/all.js")) {

			return true;
		}

		return false;
	}

	@Override
	public FrontendResource handleRequest(HttpServletRequest httpServletRequest)
		throws IOException, ServletException {

		String requestURI = httpServletRequest.getRequestURI();

		String portalContextPath = FrontendJSWebUtil.getPortalContextPath(
			_portal);

		requestURI = requestURI.substring(
			portalContextPath.length() + LANGUAGE_URI_PREFIX.length());

		String[] requestURIParts = requestURI.split(StringPool.SLASH);

		if ((requestURIParts.length != 3) && (requestURIParts.length != 4)) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Invalid request " + httpServletRequest.getRequestURI());
			}

			return null;
		}

		String servletContextPath = requestURIParts[1];

		String allJS = requestURIParts[2];

		// LPD-83084

		if (requestURIParts.length == 4) {
			servletContextPath = requestURIParts[2];
			allJS = requestURIParts[3];

			if (!Objects.equals(requestURIParts[0], requestURIParts[1])) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"Invalid request " +
							httpServletRequest.getRequestURI());
				}

				return null;
			}
		}

		if (!Objects.equals(allJS, "all.js")) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Invalid request " + httpServletRequest.getRequestURI());
			}

			return null;
		}

		URL url = _hashedFilesRegistry.getResource(
			StringBundler.concat(
				portalContextPath, Portal.PATH_MODULE, StringPool.SLASH,
				servletContextPath, "/language.json"));

		if (url == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Missing \"language.json\" for request " + requestURI);
			}

			return null;
		}

		FrontendCachingConfiguration frontendCachingConfiguration =
			FrontendJSWebUtil.getFrontendCachingConfiguration(
				_portal.getCompanyId(httpServletRequest),
				_configurationProvider);

		return new LanguageFrontendResource(
			_jsonFactory, _language, requestURIParts[0],
			frontendCachingConfiguration.labelsModulesMaxAge(),
			frontendCachingConfiguration.sendNoCacheForLabelsModules(), url);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		LanguageFrontendResourceRequestHandler.class);

	private final ConfigurationProvider _configurationProvider;
	private final HashedFilesRegistry _hashedFilesRegistry;
	private final JSONFactory _jsonFactory;
	private final Language _language;
	private final Portal _portal;

}