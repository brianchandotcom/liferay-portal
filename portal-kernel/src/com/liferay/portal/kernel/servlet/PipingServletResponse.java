/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.servlet;

import com.liferay.portal.kernel.io.WriterOutputStream;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.UnsyncPrintWriterPool;

import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Writer;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

/**
 * @author Shuyang Zhou
 */
public class PipingServletResponse extends HttpServletResponseWrapper {

	public PipingServletResponse(
		HttpServletResponse httpServletResponse, OutputStream outputStream) {

		super(httpServletResponse);

		if (outputStream == null) {
			throw new NullPointerException("Output stream is null");
		}

		_servletOutputStream = new ServletOutputStreamAdapter(outputStream);
	}

	public PipingServletResponse(
		HttpServletResponse httpServletResponse, PrintWriter printWriter) {

		super(httpServletResponse);

		if (printWriter == null) {
			throw new NullPointerException("Print writer is null");
		}

		_printWriter = printWriter;
	}

	public PipingServletResponse(
		HttpServletResponse httpServletResponse,
		ServletOutputStream servletOutputStream) {

		super(httpServletResponse);

		if (servletOutputStream == null) {
			throw new NullPointerException("Servlet output stream is null");
		}

		_servletOutputStream = servletOutputStream;
	}

	public PipingServletResponse(
		HttpServletResponse httpServletResponse, Writer writer) {

		super(httpServletResponse);

		if (writer == null) {
			throw new NullPointerException("Writer is null");
		}

		_printWriter = UnsyncPrintWriterPool.borrow(writer);
	}

	@Override
	public ServletOutputStream getOutputStream() {
		if (_servletOutputStream == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"Getting an output stream when a writer is available is " +
						"not recommended because it is slow");
			}

			_servletOutputStream = new ServletOutputStreamAdapter(
				new WriterOutputStream(
					_printWriter, getCharacterEncoding(), getBufferSize(),
					true));
		}

		return _servletOutputStream;
	}

	@Override
	public PrintWriter getWriter() {
		if (_printWriter == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"Getting a writer when an output stream is available is " +
						"not recommended because it is slow");
			}

			_printWriter = UnsyncPrintWriterPool.borrow(
				_servletOutputStream, getCharacterEncoding());
		}

		return _printWriter;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		PipingServletResponse.class);

	private PrintWriter _printWriter;
	private ServletOutputStream _servletOutputStream;

}