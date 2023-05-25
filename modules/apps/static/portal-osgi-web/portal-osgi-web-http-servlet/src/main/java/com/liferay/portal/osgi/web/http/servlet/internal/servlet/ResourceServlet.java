/*******************************************************************************
 * Copyright (c) 2005, 2014 Cognos Incorporated, IBM Corporation and others
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Cognos Incorporated - initial API and implementation
 *     IBM Corporation - bug fixes and enhancements
 *     Raymond Augé <raymond.auge@liferay.com> - Bug 436698
 *******************************************************************************/

package com.liferay.portal.osgi.web.http.servlet.internal.servlet;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.osgi.web.http.servlet.internal.util.Const;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;

import java.net.URL;
import java.net.URLConnection;

import java.security.AccessControlContext;
import java.security.AccessController;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.http.context.ServletContextHelper;

/**
 * @author Cognos Incorporated
 * @author IBM Corporation
 * @author Raymond Augé
 */
public class ResourceServlet extends HttpServlet {

	public ResourceServlet(
		String internalName, ServletContextHelper servletContextHelper,
		AccessControlContext accessControlContext) {

		if (internalName.equals(Const.SLASH)) {
			_internalName = Const.BLANK;
		}
		else {
			_internalName = internalName;
		}

		_servletContextHelper = servletContextHelper;
		_accessControlContext = accessControlContext;
	}

	@Override
	public void service(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws IOException {

		String method = httpServletRequest.getMethod();

		if (method.equals("GET") || method.equals("POST") ||
			method.equals("HEAD")) {

			String pathInfo = HttpServletRequestWrapperImpl.getDispatchPathInfo(
				httpServletRequest);

			if (pathInfo == null) {
				pathInfo = Const.BLANK;
			}

			String resourcePath = _internalName + pathInfo;

			URL resourceURL = _servletContextHelper.getResource(resourcePath);

			if (resourceURL != null) {
				_writeResource(
					httpServletRequest, httpServletResponse, resourcePath,
					resourceURL);
			}
			else {
				httpServletResponse.sendError(
					HttpServletResponse.SC_NOT_FOUND,
					"ProxyServlet: " + httpServletRequest.getRequestURI());
			}
		}
		else {
			httpServletResponse.setStatus(
				HttpServletResponse.SC_METHOD_NOT_ALLOWED);
		}
	}

	private void _sendError(HttpServletResponse httpServletResponse)
		throws IOException {

		try {

			// we need to reset headers for 302 and 403

			httpServletResponse.reset();
			httpServletResponse.sendError(HttpServletResponse.SC_FORBIDDEN);
		}
		catch (IllegalStateException illegalStateException) {

			// this could happen if the response has already been committed

			if (_log.isDebugEnabled()) {
				_log.debug(illegalStateException);
			}
		}
	}

	private void _writeResource(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, String resourcePath,
			URL resourceURL)
		throws IOException {

		try {
			AccessController.doPrivileged(
				(PrivilegedExceptionAction<Boolean>)() -> {
					URLConnection connection = resourceURL.openConnection();

					long lastModified = connection.getLastModified();
					int contentLength = connection.getContentLength();

					String etag = null;

					if ((lastModified != -1) && (contentLength != -1)) {
						etag = StringBundler.concat(
							"W/\"", contentLength, "-", lastModified, "\"");
					}

					// Check for cache revalidation.
					// We should prefer ETag validation as the guarantees are
					// stronger and all HTTP 1.1 clients should be using it

					String ifNoneMatch = httpServletRequest.getHeader(
						_IF_NONE_MATCH);

					if ((ifNoneMatch != null) && (etag != null) &&
						ifNoneMatch.contains(etag)) {

						httpServletResponse.setStatus(
							HttpServletResponse.SC_NOT_MODIFIED);

						return Boolean.TRUE;
					}

					long ifModifiedSince = httpServletRequest.getDateHeader(
						_IF_MODIFIED_SINCE);

					// for purposes of comparison we add 999 to ifModifiedSince
					// since the fidelity of the IMS header generally doesn't
					// include milliseconds

					if ((ifModifiedSince > -1) && (lastModified > 0) &&
						(lastModified <= (ifModifiedSince + 999))) {

						httpServletResponse.setStatus(
							HttpServletResponse.SC_NOT_MODIFIED);

						return Boolean.TRUE;
					}

					if (contentLength != -1) {
						httpServletResponse.setContentLength(contentLength);
					}

					String contentType = _servletContextHelper.getMimeType(
						resourcePath);

					if (contentType == null) {
						ServletConfig servletConfig = getServletConfig();

						ServletContext servletContext =
							servletConfig.getServletContext();

						contentType = servletContext.getMimeType(resourcePath);
					}

					if (contentType != null) {
						httpServletResponse.setContentType(contentType);
					}

					if (lastModified > 0) {
						httpServletResponse.setDateHeader(
							_LAST_MODIFIED, lastModified);
					}

					if (etag != null) {
						httpServletResponse.setHeader(_ETAG, etag);
					}

					if (contentLength == 0) {
						return Boolean.TRUE;
					}

					try (InputStream inputStream =
							connection.getInputStream()) {

						// write the resource

						try {
							int writtenContentLength =
								_writeResourceToOutputStream(
									inputStream,
									httpServletResponse.getOutputStream());

							if ((contentLength == -1) ||
								(contentLength != writtenContentLength)) {

								httpServletResponse.setContentLength(
									writtenContentLength);
							}
						}
						catch (IllegalStateException illegalStateException) {
							if (_log.isDebugEnabled()) {
								_log.debug(illegalStateException);
							}

							// can occur if the response output is already open
							// as a Writer

							_writeResourceToWriter(
								inputStream, httpServletResponse.getWriter());

							// Since ContentLength is a measure of the number of
							// bytes contained in the body of a message when we
							// use a Writer we lose control of the exact byte
							// count and defer the problem to the Servlet
							// Engine's Writer implementation.

						}
					}
					catch (FileNotFoundException fileNotFoundException) {
						if (_log.isDebugEnabled()) {
							_log.debug(fileNotFoundException);
						}

						// FileNotFoundException may indicate the following
						// scenarios
						// - url is a directory
						// - url is not accessible

						_sendError(httpServletResponse);
					}
					catch (SecurityException securityException) {
						if (_log.isDebugEnabled()) {
							_log.debug(securityException);
						}

						// SecurityException may indicate the following
						// scenarios
						// - url is not accessible

						_sendError(httpServletResponse);
					}

					return Boolean.TRUE;
				},
				_accessControlContext);
		}
		catch (PrivilegedActionException privilegedActionException) {
			throw (IOException)privilegedActionException.getException();
		}
	}

	private int _writeResourceToOutputStream(
			InputStream inputStream, OutputStream outputStream)
		throws IOException {

		byte[] buffer = new byte[8192];

		int bytesRead = inputStream.read(buffer);

		int writtenContentLength = 0;

		while (bytesRead != -1) {
			outputStream.write(buffer, 0, bytesRead);

			writtenContentLength += bytesRead;

			bytesRead = inputStream.read(buffer);
		}

		return writtenContentLength;
	}

	private void _writeResourceToWriter(InputStream inputStream, Writer writer)
		throws IOException {

		try (Reader reader = new InputStreamReader(inputStream)) {
			char[] buffer = new char[8192];

			int charsRead = reader.read(buffer);

			while (charsRead != -1) {
				writer.write(buffer, 0, charsRead);

				charsRead = reader.read(buffer);
			}
		}
	}

	private static final String _ETAG = "ETag";

	private static final String _IF_MODIFIED_SINCE = "If-Modified-Since";

	private static final String _IF_NONE_MATCH = "If-None-Match";

	private static final String _LAST_MODIFIED = "Last-Modified";

	private static final Log _log = LogFactoryUtil.getLog(
		ResourceServlet.class.getName());

	private static final long serialVersionUID = 3586876493076122102L;

	private final AccessControlContext _accessControlContext;
	private final String _internalName;
	private final ServletContextHelper _servletContextHelper;

}