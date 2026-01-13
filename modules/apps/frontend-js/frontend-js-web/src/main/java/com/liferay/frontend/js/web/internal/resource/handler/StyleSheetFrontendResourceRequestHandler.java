/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.js.web.internal.resource.handler;

import com.liferay.frontend.js.web.internal.configuration.FrontendCachingConfiguration;
import com.liferay.frontend.js.web.internal.resource.FrontendResource;
import com.liferay.frontend.js.web.internal.resource.StyleSheetFrontendResource;
import com.liferay.frontend.js.web.internal.util.FrontendJsWebUtil;
import com.liferay.petra.io.StreamUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.frontend.hashed.files.HashedFilesRegistry;
import com.liferay.portal.kernel.frontend.hashed.files.HashedFilesUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Theme;
import com.liferay.portal.kernel.service.ThemeLocalService;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.TreeMapBuilder;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;

import java.io.IOException;

import java.net.URL;

import java.nio.charset.StandardCharsets;

import java.util.Map;
import java.util.SortedMap;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Iván Zaera Avellón
 */
public class StyleSheetFrontendResourceRequestHandler
	implements FrontendResourceRequestHandler {

	public StyleSheetFrontendResourceRequestHandler(
		ConfigurationProvider configurationProvider,
		HashedFilesRegistry hashedFilesRegistry, Portal portal,
		ThemeLocalService themeLocalService) {

		_configurationProvider = configurationProvider;
		_hashedFilesRegistry = hashedFilesRegistry;
		_portal = portal;
		_themeLocalService = themeLocalService;
	}

	@Override
	public boolean canHandleRequest(HttpServletRequest httpServletRequest) {
		String requestURI = httpServletRequest.getRequestURI();

		if (requestURI.endsWith(".css") &&
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

		SortedMap<String, String> tokensSortedMap = _getTokensSortedMap(
			httpServletRequest, url);

		if ((HashedFilesUtil.getHash(requestURI) != null) &&
			(tokensSortedMap == null)) {

			immutable = true;
		}

		FrontendCachingConfiguration frontendCachingConfiguration =
			FrontendJsWebUtil.getFrontendCachingConfiguration(
				_portal.getCompanyId(httpServletRequest),
				_configurationProvider);

		long maxAge = frontendCachingConfiguration.cssStyleSheetsMaxAge();
		boolean sendNoCache =
			frontendCachingConfiguration.sendNoCacheForCSSStyleSheets();

		if (immutable) {
			maxAge = 31536000;
			sendNoCache = false;
		}
		else if (tokensSortedMap != null) {
			maxAge =
				frontendCachingConfiguration.tokenizedCSSStyleSheetsMaxAge();
			sendNoCache =
				frontendCachingConfiguration.
					sendNoCacheForTokenizedCSSStyleSheets();
		}

		return new StyleSheetFrontendResource(
			_getETag(url, tokensSortedMap), immutable, maxAge, sendNoCache,
			tokensSortedMap, url);
	}

	private String _getETag(
		URL url, SortedMap<String, String> tokensSortedMap) {

		String eTag = HashedFilesUtil.getHash(url.getFile());

		if (eTag == null) {
			return null;
		}

		if (tokensSortedMap == null) {
			return eTag;
		}

		StringBuilder sb = new StringBuilder();

		for (Map.Entry<String, String> entry : tokensSortedMap.entrySet()) {
			sb.append(entry.getKey());
			sb.append(StringPool.EQUAL);
			sb.append(entry.getValue());
			sb.append(StringPool.POUND);
		}

		int hash = 0;

		String tokensString = sb.toString();

		byte[] data = tokensString.getBytes(StandardCharsets.UTF_8);

		for (byte b : data) {
			hash = (31 * hash) + b;
		}

		return eTag + StringPool.DASH + StringUtil.toHexString(hash);
	}

	private SortedMap<String, String> _getTokensSortedMap(
		HttpServletRequest httpServletRequest, URL url) {

		if (!_isTokenizable(url)) {
			return null;
		}

		String themeId = httpServletRequest.getParameter("themeId");

		if (themeId == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Unable to get themeId parameter when serving resource " +
						url);
			}

			return null;
		}

		String proxyPath = _portal.getPathProxy();

		ServletContext servletContext = httpServletRequest.getServletContext();

		String baseURL = proxyPath.concat(servletContext.getContextPath());

		if (baseURL.endsWith(StringPool.SLASH)) {
			baseURL = baseURL.substring(0, baseURL.length() - 1);
		}

		String themeImagePath;

		try {
			Theme theme = _themeLocalService.getTheme(
				_portal.getCompanyId(httpServletRequest), themeId);

			themeImagePath =
				_portal.getCDNHost(httpServletRequest) +
					theme.getStaticResourcePath() + theme.getImagesPath();
		}
		catch (PortalException portalException) {
			throw new RuntimeException(portalException);
		}

		return TreeMapBuilder.put(
			"base_url", baseURL
		).put(
			"portal_ctx", _portal.getPathContext()
		).put(
			"theme_image_path", themeImagePath
		).build();
	}

	private boolean _isTokenizable(URL url) {
		String key = url.toString();

		if (!_tokenizable.containsKey(key)) {
			try {
				String content = StreamUtil.toString(url.openStream());

				if (content.contains("@base_url@") ||
					content.contains("@portal_ctx@") ||
					content.contains("@theme_image_path@")) {

					_tokenizable.putIfAbsent(key, true);
				}
				else {
					_tokenizable.putIfAbsent(key, false);
				}
			}
			catch (IOException ioException) {
				_log.error(
					"Unable to read tokenizable resource " + url, ioException);

				_tokenizable.putIfAbsent(key, false);
			}
		}

		return _tokenizable.get(key);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		StyleSheetFrontendResourceRequestHandler.class);

	private final ConfigurationProvider _configurationProvider;
	private final HashedFilesRegistry _hashedFilesRegistry;
	private final Portal _portal;
	private final ThemeLocalService _themeLocalService;
	private final Map<String, Boolean> _tokenizable = new ConcurrentHashMap<>();

}