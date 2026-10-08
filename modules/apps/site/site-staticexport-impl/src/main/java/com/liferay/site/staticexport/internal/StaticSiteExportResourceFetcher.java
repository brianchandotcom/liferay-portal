/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.staticexport.internal;

import com.liferay.petra.io.StreamUtil;
import com.liferay.portal.kernel.servlet.DirectRequestDispatcherFactoryUtil;
import com.liferay.portal.kernel.servlet.DynamicServletRequest;
import com.liferay.portal.kernel.servlet.MetaInfoCacheServletResponse;
import com.liferay.portal.kernel.servlet.PipingServletResponse;
import com.liferay.portal.kernel.servlet.ServletContextPool;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.Validator;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import jakarta.servlet.http.HttpServletResponse;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * @author Víctor Galán
 */
public class StaticSiteExportResourceFetcher {

	public StaticSiteExportResourceFetcher(
		HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse, String portalURL,
		ServletContext servletContext,
		StaticSiteExportBundleResourceResolver
			staticSiteExportBundleResourceResolver) {

		_httpServletRequest = httpServletRequest;
		_httpServletResponse = httpServletResponse;
		_portalURL = portalURL;
		_servletContext = servletContext;
		_staticSiteExportBundleResourceResolver =
			staticSiteExportBundleResourceResolver;
	}

	public File fetch(String url) throws Exception {
		StaticSiteExportURL staticSiteExportURL = new StaticSiteExportURL(url);

		File file = null;

		String moduleName = staticSiteExportURL.getModuleName();

		if (moduleName == null) {
			file = _getServletFile(_servletContext, staticSiteExportURL);
		}
		else {
			file = _staticSiteExportBundleResourceResolver.resolve(
				moduleName, staticSiteExportURL.getDispatchPath());

			if (file == null) {
				file = _getServletFile(
					ServletContextPool.get(moduleName), staticSiteExportURL);
			}
		}

		if (file != null) {
			return file;
		}

		return _fetchFile(staticSiteExportURL);
	}

	private File _fetchFile(StaticSiteExportURL staticSiteExportURL)
		throws Exception {

		Http.Options options = new Http.Options();

		options.setFollowRedirects(true);
		options.setLocation(_getLocation(staticSiteExportURL));

		File file = FileUtil.createTempFile();

		try (InputStream inputStream = HttpUtil.URLtoInputStream(options);
			OutputStream outputStream = new FileOutputStream(file)) {

			StreamUtil.transfer(inputStream, outputStream);
		}

		Http.Response response = options.getResponse();

		if ((file.length() == 0) ||
			(response.getResponseCode() != HttpServletResponse.SC_OK)) {

			FileUtil.delete(file);

			return null;
		}

		return file;
	}

	private String _getLocation(StaticSiteExportURL staticSiteExportURL) {
		if (staticSiteExportURL.isExternal()) {
			return staticSiteExportURL.getURL();
		}

		return _portalURL + staticSiteExportURL.getURL();
	}

	private File _getServletFile(
			ServletContext servletContext,
			StaticSiteExportURL staticSiteExportURL)
		throws Exception {

		if (servletContext == null) {
			return null;
		}

		RequestDispatcher requestDispatcher =
			DirectRequestDispatcherFactoryUtil.getRequestDispatcher(
				servletContext, staticSiteExportURL.getDispatchPath());

		if (requestDispatcher == null) {
			return null;
		}

		HttpServletRequest httpServletRequest = _httpServletRequest;

		String queryString = staticSiteExportURL.getQueryString();

		if (Validator.isNotNull(queryString)) {
			httpServletRequest = DynamicServletRequest.addQueryString(
				httpServletRequest, queryString, false);
		}

		File file = FileUtil.createTempFile();

		MetaInfoCacheServletResponse metaInfoCacheServletResponse =
			new MetaInfoCacheServletResponse(_httpServletResponse);

		try (OutputStream outputStream = new FileOutputStream(file)) {
			PipingServletResponse pipingServletResponse =
				new PipingServletResponse(
					metaInfoCacheServletResponse, outputStream);

			requestDispatcher.include(
				new PathHttpServletRequestWrapper(
					httpServletRequest, staticSiteExportURL),
				pipingServletResponse);

			PrintWriter printWriter = pipingServletResponse.getWriter();

			printWriter.flush();
		}

		if ((file.length() == 0) ||
			(metaInfoCacheServletResponse.getStatus() !=
				HttpServletResponse.SC_OK)) {

			FileUtil.delete(file);

			return null;
		}

		return file;
	}

	private final HttpServletRequest _httpServletRequest;
	private final HttpServletResponse _httpServletResponse;
	private final String _portalURL;
	private final ServletContext _servletContext;
	private final StaticSiteExportBundleResourceResolver
		_staticSiteExportBundleResourceResolver;

	private static class PathHttpServletRequestWrapper
		extends HttpServletRequestWrapper {

		public PathHttpServletRequestWrapper(
			HttpServletRequest httpServletRequest,
			StaticSiteExportURL staticSiteExportURL) {

			super(httpServletRequest);

			_staticSiteExportURL = staticSiteExportURL;
		}

		@Override
		public String getPathInfo() {
			return _staticSiteExportURL.getPathInfo();
		}

		@Override
		public String getRequestURI() {
			return _staticSiteExportURL.getDispatchPath();
		}

		@Override
		public String getServletPath() {
			return _staticSiteExportURL.getServletPath();
		}

		private final StaticSiteExportURL _staticSiteExportURL;

	}

}