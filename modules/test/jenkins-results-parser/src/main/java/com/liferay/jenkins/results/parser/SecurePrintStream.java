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

package com.liferay.jenkins.results.parser;

import java.io.IOException;
import java.io.PrintStream;

import java.util.Arrays;

/**
 * @author Peter Yoo
 */
public class SecurePrintStream extends PrintStream {

	public SecurePrintStream(PrintStream printStream) {
		super(printStream, true);

		_printStream = printStream;
	}

	@Override
	public PrintStream append(char c) {
		_printStream.append(c);

		return this;
	}

	@Override
	public PrintStream append(CharSequence charSequence) {
		String redactedString = _redact(charSequence.toString());

		if (redactedString != null) {
			_printStream.append(redactedString);
		}
		else {
			_printStream.append(charSequence);
		}

		return this;
	}

	@Override
	public PrintStream append(CharSequence charSequence, int start, int end) {
		return append(charSequence.subSequence(start, end));
	}

	@Override
	public void flush() {
		_printStream.flush();
	}

	@Override
	public void print(boolean b) {
		_printStream.print(b);
	}

	@Override
	public void print(char c) {
		_printStream.print(c);
	}

	@Override
	public void print(char[] chars) {
		String redactedString = _redact(new String(chars));

		if (redactedString != null) {
			_printStream.print(redactedString);

			return;
		}

		_printStream.print(chars);
	}

	@Override
	public void print(double d) {
		String redactedString = _redact(Double.toString(d));

		if (redactedString != null) {
			_printStream.print(redactedString);

			return;
		}

		_printStream.print(d);
	}

	@Override
	public void print(float f) {
		String redactedString = _redact(Float.toString(f));

		if (redactedString != null) {
			_printStream.print(redactedString);

			return;
		}

		_printStream.print(f);
	}

	@Override
	public void print(int i) {
		String redactedString = _redact(Integer.toString(i));

		if (redactedString != null) {
			_printStream.print(redactedString);

			return;
		}

		_printStream.print(i);
	}

	@Override
	public void print(long l) {
		String redactedString = _redact(Long.toString(l));

		if (redactedString != null) {
			_printStream.println(redactedString);

			return;
		}

		_printStream.print(l);
	}

	@Override
	public void print(Object object) {
		if (object == null) {
			_printStream.print("null");
		}

		String redactedString = _redact(object.toString());

		if (redactedString != null) {
			_printStream.print(redactedString);

			return;
		}

		_printStream.print(object);
	}

	@Override
	public void print(String string) {
		String redactedString = _redact(string);

		if (redactedString != null) {
			_printStream.print(redactedString);

			return;
		}

		_printStream.print(string);
	}

	@Override
	public void println() {
		_printStream.println();
	}

	@Override
	public void println(boolean b) {
		_printStream.println(b);
	}

	@Override
	public void println(char c) {
		_printStream.println(c);
	}

	@Override
	public void println(char[] chars) {
		String redactedString = _redact(new String(chars));

		if (redactedString != null) {
			_printStream.println(redactedString);

			return;
		}

		_printStream.println(chars);
	}

	@Override
	public void println(double d) {
		String redactedString = _redact(Double.toString(d));

		if (redactedString != null) {
			_printStream.println(redactedString);

			return;
		}

		_printStream.println(d);
	}

	@Override
	public void println(float f) {
		String redactedString = _redact(Float.toString(f));

		if (redactedString != null) {
			_printStream.println(redactedString);

			return;
		}

		_printStream.println(f);
	}

	@Override
	public void println(int i) {
		String redactedString = _redact(Integer.toString(i));

		if (redactedString != null) {
			_printStream.println(redactedString);

			return;
		}

		_printStream.println(i);
	}

	@Override
	public void println(long l) {
		String redactedString = _redact(Long.toString(l));

		if (redactedString != null) {
			_printStream.println(redactedString);

			return;
		}

		_printStream.println(l);
	}

	@Override
	public void println(Object object) {
		if (object == null) {
			_printStream.println("null");
		}

		String redactedString = _redact(object.toString());

		if (redactedString != null) {
			_printStream.println(redactedString);

			return;
		}

		_printStream.println(object);
	}

	@Override
	public void println(String string) {
		String redactedString = _redact(string);

		if (redactedString != null) {
			_printStream.println(redactedString);

			return;
		}

		_printStream.println(string);
	}

	@Override
	public void write(byte[] bytes) throws IOException {
		String redactedString = _redact(new String(bytes));

		if (redactedString != null) {
			_printStream.write(redactedString.getBytes());

			return;
		}

		_printStream.write(bytes);
	}

	@Override
	public void write(byte[] buffer, int offset, int length) {
		String redactedString = _redact(
			new String(Arrays.copyOfRange(buffer, offset, offset + length)));

		if (redactedString != null) {
			_printStream.print(redactedString);

			return;
		}

		_printStream.write(buffer, offset, length);
	}

	@Override
	public void write(int b) {
		String redactedString = _redact(Integer.toString(b));

		if (redactedString != null) {
			_printStream.print(redactedString);

			return;
		}

		_printStream.write(b);
	}

	private String _redact(String string) {
		if (string == null) {
			return null;
		}

		String redactedString = JenkinsResultsParserUtil.redact(string);

		if (string.equals(redactedString)) {
			return null;
		}

		return redactedString;
	}

	private final PrintStream _printStream;

}