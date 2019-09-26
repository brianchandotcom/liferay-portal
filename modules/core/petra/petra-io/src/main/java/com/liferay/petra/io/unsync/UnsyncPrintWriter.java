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

package com.liferay.petra.io.unsync;

import com.liferay.petra.io.OutputStreamWriter;
import com.liferay.petra.string.StringPool;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Writer;

import java.util.Formatter;
import java.util.Locale;

/**
 * <p>
 * See https://issues.liferay.com/browse/LPS-6648.
 * </p>
 *
 * @author Shuyang Zhou
 */
public class UnsyncPrintWriter extends PrintWriter {

	public UnsyncPrintWriter(File file) throws IOException {
		this(new FileWriter(file));
	}

	public UnsyncPrintWriter(File file, String csn)
		throws FileNotFoundException {

		this(new OutputStreamWriter(new FileOutputStream(file), csn));
	}

	public UnsyncPrintWriter(OutputStream outputStream) {
		this(new OutputStreamWriter(outputStream));
	}

	public UnsyncPrintWriter(String fileName) throws IOException {
		this(new FileWriter(fileName));
	}

	public UnsyncPrintWriter(String fileName, String csn)
		throws FileNotFoundException {

		this(new OutputStreamWriter(new FileOutputStream(fileName), csn));
	}

	public UnsyncPrintWriter(Writer writer) {
		super(writer);

		_writer = writer;
	}

	@Override
	public PrintWriter append(char c) {
		write(c);

		return this;
	}

	@Override
	public PrintWriter append(CharSequence charSequence) {
		if (charSequence == null) {
			write(StringPool.NULL);
		}
		else {
			write(charSequence.toString());
		}

		return this;
	}

	@Override
	public PrintWriter append(CharSequence charSequence, int start, int end) {
		if (charSequence == null) {
			charSequence = StringPool.NULL;
		}

		charSequence = charSequence.subSequence(start, end);

		write(charSequence.toString());

		return this;
	}

	@Override
	public boolean checkError() {
		if (_writer != null) {
			flush();
		}

		return _hasError;
	}

	@Override
	public void close() {
		try {
			if (_writer == null) {
				return;
			}

			_writer.close();

			_writer = null;
		}
		catch (IOException ioe) {
			_hasError = true;
		}
	}

	@Override
	public void flush() {
		if (_writer == null) {
			_hasError = true;
		}
		else {
			try {
				_writer.flush();
			}
			catch (IOException ioe) {
				_hasError = true;
			}
		}
	}

	@Override
	public PrintWriter format(
		Locale locale, String format, Object... arguments) {

		if (_writer == null) {
			_hasError = true;
		}
		else {
			if ((_formatter == null) || (_formatter.locale() != locale)) {
				_formatter = new Formatter(this, locale);
			}

			_formatter.format(locale, format, arguments);
		}

		return this;
	}

	@Override
	public PrintWriter format(String format, Object... arguments) {
		return format(Locale.getDefault(), format, arguments);
	}

	@Override
	public void print(boolean b) {
		if (b) {
			write(StringPool.TRUE);
		}
		else {
			write(StringPool.FALSE);
		}
	}

	@Override
	public void print(char c) {
		write(c);
	}

	@Override
	public void print(char[] chars) {
		write(chars);
	}

	@Override
	public void print(double d) {
		write(String.valueOf(d));
	}

	@Override
	public void print(float f) {
		write(String.valueOf(f));
	}

	@Override
	public void print(int i) {
		write(String.valueOf(i));
	}

	@Override
	public void print(long l) {
		write(String.valueOf(l));
	}

	@Override
	public void print(Object object) {
		write(String.valueOf(object));
	}

	@Override
	public void print(String string) {
		if (string == null) {
			string = StringPool.NULL;
		}

		write(string);
	}

	@Override
	public PrintWriter printf(
		Locale locale, String format, Object... arguments) {

		return format(locale, format, arguments);
	}

	@Override
	public PrintWriter printf(String format, Object... arguments) {
		return format(format, arguments);
	}

	@Override
	public void println() {
		if (_writer == null) {
			_hasError = true;
		}
		else {
			try {
				_writer.write(_LINE_SEPARATOR);
			}
			catch (InterruptedIOException iioe) {
				Thread currentThread = Thread.currentThread();

				currentThread.interrupt();
			}
			catch (IOException ioe) {
				_hasError = true;
			}
		}
	}

	@Override
	public void println(boolean b) {
		print(b);
		println();
	}

	@Override
	public void println(char c) {
		print(c);
		println();
	}

	@Override
	public void println(char[] chars) {
		print(chars);
		println();
	}

	@Override
	public void println(double d) {
		print(d);
		println();
	}

	@Override
	public void println(float f) {
		print(f);
		println();
	}

	@Override
	public void println(int i) {
		print(i);
		println();
	}

	@Override
	public void println(long l) {
		print(l);
		println();
	}

	@Override
	public void println(Object object) {
		print(object);
		println();
	}

	@Override
	public void println(String string) {
		print(string);
		println();
	}

	public void reset(Writer writer) {
		_formatter = null;
		_hasError = false;

		_writer = writer;

		lock = _writer;
		out = _writer;
	}

	@Override
	public void write(char[] chars) {
		write(chars, 0, chars.length);
	}

	@Override
	public void write(char[] chars, int offset, int length) {
		if (_writer == null) {
			_hasError = true;
		}
		else {
			try {
				_writer.write(chars, offset, length);
			}
			catch (InterruptedIOException iioe) {
				Thread currentThread = Thread.currentThread();

				currentThread.interrupt();
			}
			catch (IOException ioe) {
				_hasError = true;
			}
		}
	}

	@Override
	public void write(int c) {
		if (_writer == null) {
			_hasError = true;
		}
		else {
			try {
				_writer.write(c);
			}
			catch (InterruptedIOException iioe) {
				Thread currentThread = Thread.currentThread();

				currentThread.interrupt();
			}
			catch (IOException ioe) {
				_hasError = true;
			}
		}
	}

	@Override
	public void write(String string) {
		if (_writer == null) {
			_hasError = true;
		}
		else {
			try {
				_writer.write(string);
			}
			catch (InterruptedIOException iioe) {
				Thread currentThread = Thread.currentThread();

				currentThread.interrupt();
			}
			catch (IOException ioe) {
				_hasError = true;
			}
		}
	}

	@Override
	public void write(String string, int offset, int length) {
		if (_writer == null) {
			_hasError = true;
		}
		else {
			try {
				_writer.write(string, offset, length);
			}
			catch (InterruptedIOException iioe) {
				Thread currentThread = Thread.currentThread();

				currentThread.interrupt();
			}
			catch (IOException ioe) {
				_hasError = true;
			}
		}
	}

	private static final String _LINE_SEPARATOR = System.getProperty(
		"line.separator");

	private Formatter _formatter;
	private boolean _hasError;
	private Writer _writer;

}