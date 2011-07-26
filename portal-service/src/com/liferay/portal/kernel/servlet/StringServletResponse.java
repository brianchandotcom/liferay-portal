/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.kernel.servlet;

import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayOutputStream;
import com.liferay.portal.kernel.io.unsync.UnsyncStringWriter;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.UnsyncPrintWriterPool;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;

import java.util.Locale;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Brian Wing Shun Chan
 * @author Shuyang Zhou
 * @author Zsolt Balogh
 */
public class StringServletResponse extends HeaderCacheServletResponse {

	public StringServletResponse(HttpServletResponse response) {
		super(response);
	}

	@Override
	public int getBufferSize() {
		return _bufferSize;
	}

	@Override
	public ServletOutputStream getOutputStream() {
		if (_calledGetWriter) {
			throw new IllegalStateException(
				"Cannot obtain OutputStream because Writer is already in use");
		}

		if (_servletOutputStream == null) {
			_unsyncByteArrayOutputStream = new UnsyncByteArrayOutputStream();
			_servletOutputStream = new PipingServletOutputStream(
				_unsyncByteArrayOutputStream);
		}

		_calledGetOutputStream = true;

		return _servletOutputStream;
	}

	public String getString() {
		if (_string != null) {
			return _string;
		}

		if (_calledGetOutputStream) {
			try {
				_string = _unsyncByteArrayOutputStream.toString(
					StringPool.UTF8);
			}
			catch (UnsupportedEncodingException uee) {
				_log.error(uee, uee);

				_string = StringPool.BLANK;
			}
		}
		else if (_calledGetWriter) {
			_string = _unsyncStringWriter.toString();
		}
		else {
			_string = StringPool.BLANK;
		}

		return _string;
	}

	public UnsyncByteArrayOutputStream getUnsyncByteArrayOutputStream() {
		return _unsyncByteArrayOutputStream;
	}

	@Override
	public PrintWriter getWriter() {
		if (_calledGetOutputStream) {
			throw new IllegalStateException(
				"Cannot obtain Writer because OutputStream is already in use");
		}

		if (_printWriter == null) {
			_unsyncStringWriter = new UnsyncStringWriter();
			_printWriter = UnsyncPrintWriterPool.borrow(_unsyncStringWriter);
		}

		_calledGetWriter = true;

		return _printWriter;
	}

	public boolean isCalledGetOutputStream() {
		return _calledGetOutputStream;
	}

	public boolean isCalledGetWriter() {
		return _calledGetWriter;
	}

	public void recycle() {
		_string = null;

		setStatus(SC_OK);

		resetBuffer();
	}

	@Override
	public void resetBuffer() {
		if (_calledGetOutputStream) {
			_calledGetOutputStream = false;

			_unsyncByteArrayOutputStream.reset();
		}
		else if (_calledGetWriter) {
			_calledGetWriter = false;

			_unsyncStringWriter.reset();
		}
	}

	@Override
	public void setBufferSize(int bufferSize) {
		_bufferSize = bufferSize;
	}

	@Override
	public void setLocale(Locale locale) {
	}

	public void setString(String string) {
		_string = string;
	}

	public void writeTo(Writer writer) throws IOException {
		if (_string != null) {
			writer.write(_string);
		}
		else if (_calledGetWriter) {
			StringBundler sb = _unsyncStringWriter.getStringBundler();

			sb.writeTo(writer);
		}
		else {
			writer.write(getString());
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		StringServletResponse.class);

	private int _bufferSize;
	private boolean _calledGetOutputStream;
	private boolean _calledGetWriter;
	private PrintWriter _printWriter;
	private ServletOutputStream _servletOutputStream;
	private String _string;
	private UnsyncByteArrayOutputStream _unsyncByteArrayOutputStream;
	private UnsyncStringWriter _unsyncStringWriter;

}