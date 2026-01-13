/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.js.web.internal.resource.handler;

import com.liferay.frontend.js.web.internal.configuration.FrontendCachingConfiguration;
import com.liferay.frontend.js.web.internal.resource.FrontendResource;
import com.liferay.frontend.js.web.internal.resource.HashedFileFrontendResource;
import com.liferay.portal.kernel.frontend.hashed.files.HashedFilesRegistry;
import com.liferay.portal.kernel.frontend.hashed.files.HashedFilesUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.settings.CompanyServiceSettingsLocator;
import com.liferay.portal.kernel.settings.FallbackKeysSettingsUtil;
import com.liferay.portal.kernel.settings.Settings;
import com.liferay.portal.kernel.util.Portal;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;

import java.io.IOException;

import java.net.URL;

/**
 * @author Iván Zaera Avellón
 */
public class HashedFileFrontendResourceRequestHandler
	implements FrontendResourceRequestHandler {

	public HashedFileFrontendResourceRequestHandler(
		String contentType, long defaultMaxAge, boolean defaultSendNoCache,
		String fileExtension, HashedFilesRegistry hashedFilesRegistry,
		String maxAgeKey, Portal portal, String sendNoCacheKey) {

		_contentType = contentType;
		_defaultMaxAge = defaultMaxAge;
		_defaultSendNoCache = defaultSendNoCache;
		_fileExtension = fileExtension;
		_hashedFilesRegistry = hashedFilesRegistry;
		_maxAgeKey = maxAgeKey;
		_portal = portal;
		_sendNoCacheKey = sendNoCacheKey;
	}

	@Override
	public boolean canHandleRequest(HttpServletRequest httpServletRequest) {
		String requestURI = httpServletRequest.getRequestURI();

		if (requestURI.endsWith(_fileExtension) &&
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

		boolean immutable = false;

		if (HashedFilesUtil.getHash(requestURI) != null) {
			immutable = true;
		}

		long maxAge = 31536000;
		boolean sendNoCache = false;

		if (!immutable) {
			maxAge = _defaultMaxAge;
			sendNoCache = _defaultSendNoCache;

			long companyId = _portal.getCompanyId(httpServletRequest);

			try {
				Class<FrontendCachingConfiguration>
					frontendCachingConfigurationClass =
						FrontendCachingConfiguration.class;

				Settings settings = FallbackKeysSettingsUtil.getSettings(
					new CompanyServiceSettingsLocator(
						companyId, frontendCachingConfigurationClass.getName(),
						frontendCachingConfigurationClass.getName()));

				maxAge = Long.valueOf(
					settings.getValue(
						_maxAgeKey, String.valueOf(_defaultMaxAge)));
				sendNoCache = Boolean.valueOf(
					settings.getValue(
						_sendNoCacheKey, String.valueOf(_defaultSendNoCache)));
			}
			catch (Exception exception) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"Unable to get frontend caching configuration for " +
							"company " + companyId,
						exception);
				}
			}
		}

		return new HashedFileFrontendResource(
			_contentType, HashedFilesUtil.getHash(url.getFile()), immutable,
			maxAge, sendNoCache, url);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		HashedFileFrontendResourceRequestHandler.class);

	private final String _contentType;
	private final long _defaultMaxAge;
	private final boolean _defaultSendNoCache;
	private final String _fileExtension;
	private final HashedFilesRegistry _hashedFilesRegistry;
	private final String _maxAgeKey;
	private final Portal _portal;
	private final String _sendNoCacheKey;

}