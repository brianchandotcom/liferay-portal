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

package com.liferay.portal.servlet.filters.gzip;

import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayOutputStream;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.BrowserSnifferUtil;
import com.liferay.portal.kernel.servlet.HttpHeaders;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.UnsyncPrintWriterPool;
import com.liferay.util.RSSThreadLocal;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

/**
 * @author Jayson Falkner
 * @author Brian Wing Shun Chan
 * @author Shuyang Zhou
 */
public class GZipResponse extends HttpServletResponseWrapper {

	public GZipResponse(
		HttpServletRequest request, HttpServletResponse response) {

		super(response);

		_response = response;

		// Clear previous content length setting. GZip response does not buffer
		// output to get final content length. The response will be chunked
		// unless an outer filter calculates the content length.

		_response.setContentLength(-1);

		_response.addHeader(HttpHeaders.CONTENT_ENCODING, _GZIP);

		_firefox = BrowserSnifferUtil.isFirefox(request);
	}

	public void finishResponse() throws IOException {
		try {
			if (_printWriter != null) {
				_printWriter.close();
			}
			else if (_servletOutputStream != null) {
				_servletOutputStream.close();
			}
		}
		catch (IOException e) {
		}

		if (_unsyncByteArrayOutputStream != null) {
			_response.setContentLength(_unsyncByteArrayOutputStream.size());

			_unsyncByteArrayOutputStream.writeTo(_response.getOutputStream());
		}
	}

	@Override
	public void flushBuffer() throws IOException {
		if (_servletOutputStream != null) {
			_servletOutputStream.flush();
		}
	}

	@Override
	public ServletOutputStream getOutputStream() throws IOException {
		if (_printWriter != null) {
			throw new IllegalStateException();
		}

		if (_servletOutputStream == null) {
			if (_gZipContentType) {
				_servletOutputStream = _response.getOutputStream();
			}
			else {
				if (_firefox && RSSThreadLocal.isExportRSS()) {
					_unsyncByteArrayOutputStream =
						new UnsyncByteArrayOutputStream();

					_servletOutputStream = new GZipServletOutputStream(
						_unsyncByteArrayOutputStream);
				}
				else {
					_servletOutputStream = new GZipServletOutputStream(
						_response.getOutputStream());
				}
			}
		}

		return _servletOutputStream;
	}

	@Override
	public PrintWriter getWriter() throws IOException {
		if (_printWriter != null) {
			return _printWriter;
		}

		if (_servletOutputStream != null) {
			throw new IllegalStateException();
		}

		if (_log.isWarnEnabled()) {
			_log.warn("Use getOutputStream for optimum performance");
		}

		_servletOutputStream = getOutputStream();

		_printWriter = UnsyncPrintWriterPool.borrow(
			new OutputStreamWriter(//_stream, _res.getCharacterEncoding()));
				_servletOutputStream, StringPool.UTF8));

		return _printWriter;
	}

	@Override
	public void setContentLength(int contentLength) {
	}

	@Override
	public void setContentType(String contentType) {
		super.setContentType(contentType);

		if (contentType != null) {
			if (contentType.equals(ContentTypes.APPLICATION_GZIP) ||
				contentType.equals(ContentTypes.APPLICATION_X_GZIP)) {

				_gZipContentType = true;
			}
		}
	}

	private static final String _GZIP = "gzip";

	private static Log _log = LogFactoryUtil.getLog(GZipResponse.class);

	private boolean _firefox;
	private boolean _gZipContentType;
	private PrintWriter _printWriter;
	private HttpServletResponse _response;
	private ServletOutputStream _servletOutputStream;
	private UnsyncByteArrayOutputStream _unsyncByteArrayOutputStream;

}