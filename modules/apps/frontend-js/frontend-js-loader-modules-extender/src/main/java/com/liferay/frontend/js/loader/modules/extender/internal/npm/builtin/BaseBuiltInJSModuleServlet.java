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

package com.liferay.frontend.js.loader.modules.extender.internal.npm.builtin;

import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.MimeTypes;
import com.liferay.portal.kernel.util.StreamUtil;

import java.io.IOException;
import java.io.InputStream;

import java.net.URL;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Provides a base abstract class to implement servlets that return JavaScript
 * modules tracked by the {@link
 * com.liferay.frontend.js.loader.modules.extender.npm.NPMRegistry}.
 *
 * @author Adolfo Pérez
 */
public abstract class BaseBuiltInJSModuleServlet extends HttpServlet {

	protected abstract MimeTypes getMimeTypes();

	/**
	 * Returns the requested resource. This is a template method that must be
	 * implemented by subclasses to lookup the requested resource.
	 *
	 * @param  pathInfo the request's pathInfo
	 * @return the {@link JSModule} object describing the module
	 */
	protected abstract URL getURL(String pathInfo);

	@Override
	protected void service(
			HttpServletRequest request, HttpServletResponse response)
		throws IOException {

		String pathInfo = request.getPathInfo();

		URL url = getURL(pathInfo);

		if (url == null) {
			response.sendError(HttpServletResponse.SC_NOT_FOUND);

			return;
		}

		_setContentType(response, url);

		_sendResource(response, url);
	}

	private void _sendResource(HttpServletResponse response, URL url)
		throws IOException {

		ServletOutputStream servletOutputStream = response.getOutputStream();

		try (InputStream inputStream = url.openStream()) {
			StreamUtil.transfer(inputStream, servletOutputStream, false);
		}
		catch (Exception e) {
			response.sendError(
				HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
				"Unable to read file");
		}
	}

	private void _setContentType(HttpServletResponse response, URL url) {
		String file = url.getFile();

		String extension = FileUtil.getExtension(file);

		if (extension.equals(".js")) {
			response.setContentType(ContentTypes.TEXT_JAVASCRIPT_UTF8);
		}
		else if (extension.equals(".map")) {
			response.setContentType(ContentTypes.APPLICATION_JSON);
		}
		else {
			MimeTypes mimeTypes = getMimeTypes();

			response.setContentType(mimeTypes.getContentType(file));
		}
	}

}