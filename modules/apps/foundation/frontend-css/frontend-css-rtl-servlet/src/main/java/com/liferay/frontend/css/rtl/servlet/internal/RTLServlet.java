/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
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

package com.liferay.frontend.css.rtl.servlet.internal;

import com.liferay.frontend.css.rtl.converter.CSSRTLConverter;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.RequestDispatcherUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StreamUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.framework.Bundle;
import org.osgi.service.http.context.ServletContextHelper;

/**
 * @author Carlos Sierra Andrés
 */
public class RTLServlet extends HttpServlet {

	public RTLServlet(
		Bundle bundle, ServletContextHelper servletContextHelper) {

		_bundle = bundle;
		_servletContextHelper = servletContextHelper;
	}

	@Override
	protected void doGet(
			HttpServletRequest request, HttpServletResponse response)
		throws IOException, ServletException {

		URL url = getResourceURL(request);

		if (url == null) {
			response.sendError(HttpServletResponse.SC_NOT_FOUND, "Not Found");
		}
		else {
			transfer(url, response);
		}
	}

	@Override
	protected long getLastModified(HttpServletRequest request) {
		try {
			URL url = getResourceURL(request);

			if (url != null) {
				URLConnection urlConnection = url.openConnection();

				return urlConnection.getLastModified();
			}

			return super.getLastModified(request);
		}
		catch (IOException ioe) {
			return super.getLastModified(request);
		}
	}

	protected URL getResourceURL(HttpServletRequest request)
		throws IOException {

		String path = URLDecoder.decode(
			RequestDispatcherUtil.getEffectivePath(request), StringPool.UTF8);

		URL url = _servletContextHelper.getResource(path);

		if (url == null) {
			return null;
		}

		String languageId = request.getParameter("languageId");

		if ((languageId == null) || !PortalUtil.isRightToLeft(request)) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"Skip because specified language " + languageId +
						" is not right to left");
			}

			return url;
		}

		String rtlPath = FileUtil.appendSuffix(path, "_rtl");

		URL rtlURL = _servletContextHelper.getResource(rtlPath);

		if (rtlURL != null) {
			return rtlURL;
		}

		File dataFile = _bundle.getDataFile(rtlPath);

		if (dataFile.exists() &&
			(dataFile.lastModified() >
				url.openConnection().getLastModified())) {

			URI uri = dataFile.toURI();

			return uri.toURL();
		}

		CSSRTLConverter cssRTLConverter = new CSSRTLConverter(false);

		String rtl = cssRTLConverter.process(StringUtil.read(url.openStream()));

		InputStream inputStream = new ByteArrayInputStream(
			rtl.getBytes(StringPool.UTF8));

		OutputStream outputStream = null;

		try {
			dataFile.getParentFile().mkdirs();

			dataFile.createNewFile();

			outputStream = new FileOutputStream(dataFile);

			StreamUtil.transfer(inputStream, outputStream, false);
		}
		catch (IOException ioe) {
			if (_log.isWarnEnabled()) {
				_log.warn("Unable to cache RTL CSS", ioe);
			}
		}
		finally {
			if (outputStream != null) {
				outputStream.close();
			}
		}

		inputStream.reset();

		URI uri = dataFile.toURI();

		return uri.toURL();
	}

	protected void transfer(URL url, HttpServletResponse response)
		throws IOException {

		URLConnection urlConnection = url.openConnection();

		response.setContentLength(urlConnection.getContentLength());

		response.setStatus(HttpServletResponse.SC_OK);

		StreamUtil.transfer(url.openStream(), response.getOutputStream());
	}

	private static final Log _log = LogFactoryUtil.getLog(RTLServlet.class);

	private final Bundle _bundle;
	private final ServletContextHelper _servletContextHelper;

}