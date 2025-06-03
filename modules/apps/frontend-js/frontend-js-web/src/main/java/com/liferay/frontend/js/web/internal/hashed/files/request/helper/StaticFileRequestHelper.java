/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.js.web.internal.hashed.files.request.helper;

import com.liferay.frontend.js.web.internal.hashed.files.HashedFileURIsRegistry;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.settings.CompanyServiceSettingsLocator;
import com.liferay.portal.kernel.settings.FallbackKeysSettingsUtil;
import com.liferay.portal.kernel.settings.Settings;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.URLUtil;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

import java.net.URL;

import java.util.Arrays;
import java.util.List;

/**
 * @author Iván Zaera Avellón
 */
public class StaticFileRequestHelper
	extends BaseRequestHelper<StaticFileRequestHelperInfo> {

	public StaticFileRequestHelper(
		String contentType, String fileExtension,
		HashedFileURIsRegistry hashedFileURIsRegistry, long maxAgeDefaultValue,
		String maxAgeKey, Portal portal, boolean sendNoCacheDefaultValue,
		String sendNoCacheKey,
		ServiceTrackerMap<String, ServletContext> serviceTrackerMap) {

		_contentType = contentType;
		_fileExtension = fileExtension;
		_hashedFileURIsRegistry = hashedFileURIsRegistry;
		_maxAgeDefaultValue = maxAgeDefaultValue;
		_maxAgeKey = maxAgeKey;
		_portal = portal;
		_sendNoCacheDefaultValue = sendNoCacheDefaultValue;
		_sendNoCacheKey = sendNoCacheKey;
		_serviceTrackerMap = serviceTrackerMap;
	}

	@Override
	public boolean isAcceptableRequest(HttpServletRequest httpServletRequest) {
		String requestURI = httpServletRequest.getRequestURI();

		if (requestURI.contains("/__liferay__/") &&
			requestURI.endsWith(_fileExtension)) {

			return true;
		}

		return false;
	}

	@Override
	protected StaticFileRequestHelperInfo getRequestHelperInfo(
		HttpServletRequest httpServletRequest) {

		String requestURI = httpServletRequest.getRequestURI();

		String hashedFileURI = _hashedFileURIsRegistry.get(requestURI);

		if (hashedFileURI == null) {
			return new StaticFileRequestHelperInfo(
				getHash(requestURI), true, 31536000, requestURI, false);
		}

		long maxAge = _maxAgeDefaultValue;
		boolean sendNoCache = _sendNoCacheDefaultValue;

		try {
			Settings settings = FallbackKeysSettingsUtil.getSettings(
				new CompanyServiceSettingsLocator(
					_portal.getCompanyId(httpServletRequest),
					"com.liferay.frontend.js.web.internal.configuration." +
						"FrontendCachingConfiguration",
					"com.liferay.frontend.js.web.internal.configuration." +
						"FrontendCachingConfiguration"));

			maxAge = Long.valueOf(
				settings.getValue(
					_maxAgeKey, String.valueOf(_maxAgeDefaultValue)));
			sendNoCache = Boolean.valueOf(
				settings.getValue(
					_sendNoCacheKey, String.valueOf(_sendNoCacheDefaultValue)));
		}
		catch (Exception exception) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Unable to get frontend caching configuration: will use " +
						"reasonable defaults instead",
					exception);
			}
		}

		return new StaticFileRequestHelperInfo(
			getHash(hashedFileURI), false, maxAge, hashedFileURI, sendNoCache);
	}

	@Override
	protected void sendContent(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse,
			StaticFileRequestHelperInfo requestHelperInfo)
		throws IOException, ServletException {

		String resourceURI = requestHelperInfo.getResourceURI();

		List<String> resourceURIParts = Arrays.asList(
			resourceURI.split(StringPool.SLASH));

		ServletContext servletContext = _serviceTrackerMap.getService(
			StringUtil.merge(resourceURIParts.subList(0, 3), StringPool.SLASH));

		if (servletContext == null) {
			httpServletResponse.sendError(HttpServletResponse.SC_NOT_FOUND);

			return;
		}

		String resourcePath = StringUtil.merge(
			resourceURIParts.subList(3, resourceURIParts.size()),
			StringPool.SLASH);

		resourcePath = StringPool.SLASH + resourcePath;

		URL url = servletContext.getResource(resourcePath);

		if (url == null) {
			httpServletResponse.sendError(HttpServletResponse.SC_NOT_FOUND);

			return;
		}

		httpServletResponse.setCharacterEncoding(StringPool.UTF8);
		httpServletResponse.setContentType(_contentType);

		PrintWriter printWriter = httpServletResponse.getWriter();

		printWriter.write(URLUtil.toString(url));
	}

	private static final Log _log = LogFactoryUtil.getLog(
		StaticFileRequestHelper.class);

	private final String _contentType;
	private final String _fileExtension;
	private final HashedFileURIsRegistry _hashedFileURIsRegistry;
	private final long _maxAgeDefaultValue;
	private final String _maxAgeKey;
	private final Portal _portal;
	private final boolean _sendNoCacheDefaultValue;
	private final String _sendNoCacheKey;
	private final ServiceTrackerMap<String, ServletContext> _serviceTrackerMap;

}