/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.js.web.internal.hashed.files.request;

import com.liferay.frontend.js.web.internal.hashed.files.HashedFilesRegistry;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.petra.string.StringPool;
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
public abstract class AbstractStaticFileRequestHelper
	extends AbstractRequestHelper<StaticFileRequestInfo> {

	public AbstractStaticFileRequestHelper(
		String fileContentType, String fileExtension,
		HashedFilesRegistry hashedFilesRegistry,
		ServiceTrackerMap<String, ServletContext> serviceTrackerMap) {

		_fileContentType = fileContentType;
		_fileExtension = fileExtension;
		_hashedFilesRegistry = hashedFilesRegistry;
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

		StaticFileCachingInfo staticFileCachingInfo = getStaticFileCachingInfo(
			httpServletRequest);

		if (realModuleURI == null) {
			return new StaticFileRequestInfo(
				getHash(requestURI), staticFileCachingInfo.getMaxAge(),
				requestURI, staticFileCachingInfo.getSendNoCache(), false);
		}

		return new StaticFileRequestInfo(
			getHash(realModuleURI), staticFileCachingInfo.getMaxAge(),
			realModuleURI, staticFileCachingInfo.getSendNoCache(), true);
	}

	protected abstract StaticFileCachingInfo getStaticFileCachingInfo(
		HttpServletRequest httpServletRequest);

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

	private final String _fileContentType;
	private final String _fileExtension;
	private final HashedFilesRegistry _hashedFilesRegistry;
	private final ServiceTrackerMap<String, ServletContext> _serviceTrackerMap;

}