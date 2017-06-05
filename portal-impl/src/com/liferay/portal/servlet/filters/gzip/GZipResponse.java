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

package com.liferay.portal.servlet.filters.gzip;

import com.google.common.base.Objects;

import com.liferay.portal.kernel.io.unsync.UnsyncBufferedOutputStream;
import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayOutputStream;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.HttpHeaders;
import com.liferay.portal.kernel.servlet.ServletOutputStreamAdapter;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.UnsyncPrintWriterPool;
import com.liferay.portal.util.PropsValues;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

import java.util.zip.GZIPOutputStream;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

/**
 * @author Jayson Falkner
 * @author Brian Wing Shun Chan
 * @author Shuyang Zhou
 */
public class GZipResponse extends HttpServletResponseWrapper {

	public GZipResponse(HttpServletResponse response) {
		super(response);

		// Clear previous content length setting. GZip response does not buffer
		// output to get final content length. The response will be chunked
		// unless an outer filter calculates the content length.

		response.setContentLength(-1);

		// Setting the header after finishResponse is too late

		response.addHeader(HttpHeaders.CONTENT_ENCODING, _GZIP);
	}

	public void finishResponse() throws IOException {
		if (_printWriter != null) {
			_printWriter.close();
		}
		else if (_servletOutputStream != null) {
			_servletOutputStream.close();
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
			_servletOutputStream = _createGZipServletOutputStream(
				super.getOutputStream());
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
			_servletOutputStream, getCharacterEncoding());

		return _printWriter;
	}

	@Override
	public void setContentLength(int contentLength) {
		if (contentLength == 0) {
			super.setContentLength(0);
		}
	}

	@Override
	public void setHeader(String name, String value) {
		if (HttpHeaders.CONTENT_LENGTH.equals(name)) {
			if (Objects.equal("0", value)) {
				super.setContentLength(0);
			}

			return;
		}

		super.setHeader(name, value);
	}

	private ServletOutputStream _createGZipServletOutputStream(
			ServletOutputStream servletOutputStream)
		throws IOException {

		if (_isGZipContentType()) {
			return servletOutputStream;
		}

		EmptyGZipBufferedOutputStream emptyGZipBufferedOutputStream =
			new EmptyGZipBufferedOutputStream(servletOutputStream);

		GZIPOutputStream gzipOutputStream = new GZIPOutputStream(
			emptyGZipBufferedOutputStream) {

			{
				def.setLevel(PropsValues.GZIP_COMPRESSION_LEVEL);
			}

		};

		return new ServletOutputStreamAdapter(gzipOutputStream) {

			@Override
			public void write(byte[] bytes) throws IOException {
				write(bytes, 0, bytes.length);
			}

			@Override
			public void write(byte[] bytes, int offset, int length)
				throws IOException {

				if (length > 0) {
					emptyGZipBufferedOutputStream.setFlush(true);
				}

				super.write(bytes, offset, length);
			}

			@Override
			public void write(int b) throws IOException {
				emptyGZipBufferedOutputStream.setFlush(true);

				super.write(b);
			}

		};
	}

	private boolean _isGZipContentType() {
		String contentType = getContentType();

		if (contentType != null) {
			if (contentType.equals(ContentTypes.APPLICATION_GZIP) ||
				contentType.equals(ContentTypes.APPLICATION_X_GZIP)) {

				return true;
			}
		}

		return false;
	}

	private static final int _EMPTY_GZIP_OUTPUT_SIZE;

	private static final String _GZIP = "gzip";

	private static final Log _log = LogFactoryUtil.getLog(GZipResponse.class);

	static {
		try {
			UnsyncByteArrayOutputStream ubaos =
				new UnsyncByteArrayOutputStream();

			GZIPOutputStream gzipOutputStream = new GZIPOutputStream(ubaos) {

				{
					def.setLevel(PropsValues.GZIP_COMPRESSION_LEVEL);
				}

			};

			gzipOutputStream.close();

			_EMPTY_GZIP_OUTPUT_SIZE = ubaos.size();
		}
		catch (IOException ioe) {
			throw new ExceptionInInitializerError(ioe);
		}
	}

	private PrintWriter _printWriter;
	private ServletOutputStream _servletOutputStream;

	private static class EmptyGZipBufferedOutputStream
		extends UnsyncBufferedOutputStream {

		@Override
		public void flush() throws IOException {
			if (_flush) {
				super.flush();
			}
		}

		public void setFlush(boolean flush) {
			_flush = flush;
		}

		private EmptyGZipBufferedOutputStream(OutputStream outputStream) {
			super(outputStream, _EMPTY_GZIP_OUTPUT_SIZE);
		}

		private boolean _flush;

	}

}