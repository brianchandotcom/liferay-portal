/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.staticexport.internal;

import com.liferay.petra.string.CharPool;
import com.liferay.portal.kernel.servlet.BufferCacheServletResponse;
import com.liferay.portal.kernel.servlet.DirectRequestDispatcherFactoryUtil;
import com.liferay.portal.kernel.servlet.DynamicServletRequest;
import com.liferay.portal.kernel.servlet.ServletContextPool;
import com.liferay.portal.kernel.util.Validator;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import jakarta.servlet.http.HttpServletResponse;

import java.nio.ByteBuffer;

/**
 * @author Víctor Galán
 */
public class StaticSiteExportResourceFetcher {

	public StaticSiteExportResourceFetcher(
		HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse,
		ServletContext servletContext) {

		_httpServletRequest = httpServletRequest;
		_httpServletResponse = httpServletResponse;
		_servletContext = servletContext;
	}

	public byte[] fetch(String url) throws Exception {
		String path = url;
		String queryString = null;

		int index = url.indexOf(CharPool.QUESTION);

		if (index != -1) {
			path = url.substring(0, index);
			queryString = url.substring(index + 1);
		}

		ServletContext servletContext = _servletContext;

		if (path.startsWith(_MODULE_PATH_PREFIX)) {
			int slashIndex = path.indexOf(
				CharPool.SLASH, _MODULE_PATH_PREFIX.length());

			if (slashIndex != -1) {
				ServletContext moduleServletContext = ServletContextPool.get(
					path.substring(_MODULE_PATH_PREFIX.length(), slashIndex));

				if (moduleServletContext != null) {
					servletContext = moduleServletContext;

					path = path.substring(slashIndex);
				}
			}
		}

		RequestDispatcher requestDispatcher =
			DirectRequestDispatcherFactoryUtil.getRequestDispatcher(
				servletContext, path);

		if (requestDispatcher == null) {
			return null;
		}

		HttpServletRequest httpServletRequest = _httpServletRequest;

		if (Validator.isNotNull(queryString)) {
			httpServletRequest = DynamicServletRequest.addQueryString(
				httpServletRequest, queryString, false);
		}

		BufferCacheServletResponse bufferCacheServletResponse =
			new BufferCacheServletResponse(_httpServletResponse);

		requestDispatcher.include(
			new PathHttpServletRequestWrapper(httpServletRequest, path),
			bufferCacheServletResponse);

		ByteBuffer byteBuffer = bufferCacheServletResponse.getByteBuffer();

		byte[] bytes = new byte[byteBuffer.remaining()];

		byteBuffer.get(bytes);

		return bytes;
	}

	private static final String _MODULE_PATH_PREFIX = "/o/";

	private final HttpServletRequest _httpServletRequest;
	private final HttpServletResponse _httpServletResponse;
	private final ServletContext _servletContext;

	private static class PathHttpServletRequestWrapper
		extends HttpServletRequestWrapper {

		public PathHttpServletRequestWrapper(
			HttpServletRequest httpServletRequest, String path) {

			super(httpServletRequest);

			_path = path;
		}

		@Override
		public String getPathInfo() {
			int index = _path.indexOf(CharPool.SLASH, 1);

			if (index == -1) {
				return null;
			}

			return _path.substring(index);
		}

		@Override
		public String getRequestURI() {
			return _path;
		}

		@Override
		public String getServletPath() {
			int index = _path.indexOf(CharPool.SLASH, 1);

			if (index == -1) {
				return _path;
			}

			return _path.substring(0, index);
		}

		private final String _path;

	}

}