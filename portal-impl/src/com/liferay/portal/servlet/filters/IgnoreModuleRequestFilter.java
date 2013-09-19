/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.servlet.filters;

import com.liferay.portal.kernel.cache.key.CacheKeyGenerator;
import com.liferay.portal.kernel.cache.key.CacheKeyGeneratorUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.util.PortalUtil;

import java.io.File;

import java.net.URLConnection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Raymond Augé
 */
public abstract class IgnoreModuleRequestFilter extends BasePortalFilter {

	@Override
	public boolean isFilterEnabled(
		HttpServletRequest request, HttpServletResponse response) {

		if (isModuleRequest(request)) {
			return false;
		}

		return super.isFilterEnabled(request, response);
	}

	protected boolean cacheDataFileExists(
			HttpServletResponse response, File cacheDataFile,
			URLConnection urlConnection, File cacheContentTypeFile)
		throws Exception {

		if (cacheDataFile.exists() &&
			(cacheDataFile.lastModified() >= urlConnection.getLastModified())) {

			if (cacheContentTypeFile.exists()) {
				String contentType = FileUtil.read(cacheContentTypeFile);

				response.setContentType(contentType);
			}

			return true;
		}

		return false;
	}

	protected String getCacheCDNHost(
			HttpServletRequest request, URLConnection urlConnection)
		throws Exception {

		String cacheCDNHost = StringPool.BLANK;

		String cdnHost = PortalUtil.getCDNHost(request);

		if (Validator.isNotNull(cdnHost)) {
			String content = StringUtil.read(urlConnection.getInputStream());

			if (content.contains("@theme_image_path@")) {
				if (request.isSecure()) {
					cacheCDNHost = "_CDN_HTTPS";
				}
				else {
					cacheCDNHost = "_CDN_HTTP";
				}
			}
		}

		return cacheCDNHost;
	}

	protected String getCacheFileName(HttpServletRequest request) {
		Class<?> clazz = getClass();

		CacheKeyGenerator cacheKeyGenerator =
			CacheKeyGeneratorUtil.getCacheKeyGenerator(clazz.getName());

		cacheKeyGenerator.append(request.getRequestURI());

		String queryString = request.getQueryString();

		if (queryString != null) {
			cacheKeyGenerator.append(sterilizeQueryString(queryString));
		}

		return String.valueOf(cacheKeyGenerator.finish());
	}

	protected boolean isModuleRequest(HttpServletRequest request) {
		String contextPath = request.getContextPath();
		String requestURI = request.getRequestURI();

		String resourcePath = requestURI;

		int index = requestURI.indexOf(contextPath);

		if (index == 0) {
			resourcePath = resourcePath.substring(contextPath.length());
		}

		if (resourcePath.startsWith(PortalUtil.getPathModule())) {
			return true;
		}

		return false;
	}

	protected String sterilizeQueryString(String queryString) {
		return StringUtil.replace(
			queryString, new String[] {StringPool.SLASH, StringPool.BACK_SLASH},
			new String[] {StringPool.UNDERLINE, StringPool.UNDERLINE});
	}

}