/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.js.web.internal.hashed.files.request.helper;

import com.liferay.frontend.js.web.internal.hashed.files.HashedFilesRegistry;
import com.liferay.frontend.js.web.internal.hashed.files.request.AbstractRequestHelper;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.settings.CompanyServiceSettingsLocator;
import com.liferay.portal.kernel.settings.FallbackKeysSettingsUtil;
import com.liferay.portal.kernel.settings.Settings;
import com.liferay.portal.kernel.settings.SettingsException;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.URLUtil;

import java.io.IOException;
import java.io.PrintWriter;

import java.net.URL;

import java.util.Arrays;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Iván Zaera Avellón
 */
public class StaticFileRequestHelper
	extends AbstractRequestHelper<StaticFileRequestInfo> {

	public StaticFileRequestHelper(
		String fileContentType, String fileExtension,
		HashedFilesRegistry hashedFilesRegistry, long maxAgeDefaultValue,
		String maxAgeKey, boolean sendNoCacheDefaultValue,
		String sendNoCacheKey, Portal portal,
		ServiceTrackerMap<String, ServletContext> serviceTrackerMap) {

		_fileContentType = fileContentType;
		_fileExtension = fileExtension;
		_hashedFilesRegistry = hashedFilesRegistry;
		_maxAgeDefaultValue = maxAgeDefaultValue;
		_maxAgeKey = maxAgeKey;
		_sendNoCacheDefaultValue = sendNoCacheDefaultValue;
		_sendNoCacheKey = sendNoCacheKey;
		_portal = portal;
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
	protected StaticFileRequestInfo getRequestInfo(
		HttpServletRequest httpServletRequest) {

		String requestURI = httpServletRequest.getRequestURI();

		String realModuleURI = _hashedFilesRegistry.getHashedFile(requestURI);

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
		catch (SettingsException settingsException) {
			_log.error(
				"Unable to get frontend caching configuration: will use " +
					"reasonable defaults instead",
				settingsException);
		}

		if (realModuleURI == null) {
			return new StaticFileRequestInfo(
				getHash(requestURI), maxAge, requestURI, sendNoCache, false);
		}

		return new StaticFileRequestInfo(
			getHash(realModuleURI), maxAge, realModuleURI, sendNoCache, true);
	}

	@Override
	protected void sendContent(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse,
			StaticFileRequestInfo staticFileRequestInfo)
		throws IOException, ServletException {

		String realModuleURI = staticFileRequestInfo.getRealModuleURI();

		List<String> parts = Arrays.asList(
			realModuleURI.split(StringPool.SLASH));

		ServletContext servletContext = _serviceTrackerMap.getService(
			StringUtil.merge(parts.subList(0, 3), StringPool.SLASH));

		if (servletContext == null) {
			httpServletResponse.sendError(HttpServletResponse.SC_NOT_FOUND);

			return;
		}

		String resourcePath = StringUtil.merge(
			parts.subList(3, parts.size()), StringPool.SLASH);

		URL url = servletContext.getResource(StringPool.SLASH + resourcePath);

		if (url == null) {
			httpServletResponse.sendError(HttpServletResponse.SC_NOT_FOUND);

			return;
		}

		httpServletResponse.setCharacterEncoding(StringPool.UTF8);
		httpServletResponse.setContentType(_fileContentType);

		PrintWriter printWriter = httpServletResponse.getWriter();

		printWriter.write(URLUtil.toString(url));
	}

	private static final Log _log = LogFactoryUtil.getLog(
		StaticFileRequestHelper.class);

	private final String _fileContentType;
	private final String _fileExtension;
	private final HashedFilesRegistry _hashedFilesRegistry;
	private final long _maxAgeDefaultValue;
	private final String _maxAgeKey;
	private final Portal _portal;
	private final boolean _sendNoCacheDefaultValue;
	private final String _sendNoCacheKey;
	private final ServiceTrackerMap<String, ServletContext> _serviceTrackerMap;

}