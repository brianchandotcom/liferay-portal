/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.httpservice.servlet;

import com.liferay.httpservice.HttpServicePropsKeys;
import com.liferay.portal.kernel.servlet.HttpHeaders;
import com.liferay.portal.kernel.servlet.ServletResponseUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.webserver.WebServerServlet;

import java.io.IOException;

import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.http.HttpContext;

/**
 * @author Raymond Augé
 * @author Miguel Pastor
 */
public class ResourceServlet extends WebServerServlet {

	@Override
	public void init(ServletConfig servletConfig) throws ServletException {
		_bundleServletConfig = (BundleServletConfig)servletConfig;

		_alias = GetterUtil.getString(
			_bundleServletConfig.getInitParameter(
				HttpServicePropsKeys.HTTP_SERVICE_ALIAS));

		_httpContext = _bundleServletConfig.getHttpContext();

		_name = GetterUtil.getString(
			_bundleServletConfig.getInitParameter(
				HttpServicePropsKeys.HTTP_SERVICE_NAME));
	}

	@Override
	public void service(
			HttpServletRequest request, HttpServletResponse response)
		throws IOException, ServletException {

		String requestURI = URLDecoder.decode(request.getRequestURI(), "UTF-8");

		String contextPath = request.getContextPath();

		if (!contextPath.equals(StringPool.SLASH)) {
			requestURI = requestURI.substring(contextPath.length());
		}

		String fileName = requestURI;

		int pos = fileName.lastIndexOf(StringPool.SLASH);

		if (pos != -1) {
			fileName = fileName.substring(pos + 1);
		}

		pos = requestURI.indexOf(_alias);

		if (pos == 0) {
			requestURI = requestURI.substring(_alias.length());
		}

		if (Validator.isNotNull(_name)) {
			requestURI = _name.concat(requestURI);
		}

		try {
			URL resourceURL = _httpContext.getResource(requestURI);

			if (resourceURL == null) {
				response.setStatus(HttpServletResponse.SC_NOT_FOUND);

				return;
			}

			URLConnection connection = resourceURL.openConnection();

			long lastModified = connection.getLastModified();

			if (lastModified > 0) {
				long ifModifiedSince = request.getDateHeader(
					HttpHeaders.IF_MODIFIED_SINCE);

				if ((ifModifiedSince > 0) &&
					(ifModifiedSince == lastModified)) {

					response.setDateHeader(
						HttpHeaders.LAST_MODIFIED, lastModified);

					// If there is an ETag header, re-send it.

					String ifNoneMatch = request.getHeader(
						HttpHeaders.IF_NONE_MATCH);

					if (Validator.isNotNull(ifNoneMatch) &&
						!ifNoneMatch.equals('0')) {

						response.setHeader(HttpHeaders.ETAG, ifNoneMatch);
					}

					response.setStatus(HttpServletResponse.SC_NOT_MODIFIED);
					response.setContentLength(0);

					return;
				}
			}

			if (lastModified > 0) {
				response.setDateHeader(HttpHeaders.LAST_MODIFIED, lastModified);
			}

			String contentType = _httpContext.getMimeType(fileName);

			// Send file

			if (isSupportsRangeHeader(contentType)) {
				sendFileWithRangeHeader(
					request, response, fileName, connection.getInputStream(),
					connection.getContentLength(), contentType);
			}
			else {
				ServletResponseUtil.sendFile(
					request, response, fileName, connection.getInputStream(),
					connection.getContentLength(), contentType);
			}
		}
		catch (Exception e) {
			PortalUtil.sendError(
				HttpServletResponse.SC_NOT_FOUND, e, request, response);
		}
	}

	private String _alias;
	private BundleServletConfig _bundleServletConfig;
	private HttpContext _httpContext;
	private String _name;

}