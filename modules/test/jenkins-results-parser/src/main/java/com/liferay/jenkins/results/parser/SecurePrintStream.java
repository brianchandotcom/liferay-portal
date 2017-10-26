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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;

/**
 * @author Peter Yoo
 */
public class SecurePrintStream extends PrintStream {

	public static SecurePrintStream getInstance() {
		if (_securePrintStream == null) {
			try {
				_securePrintStream = new SecurePrintStream(
					new SecurePrintStreamByteArrayOutputStream());
			}
			catch (UnsupportedEncodingException uee) {
				throw new RuntimeException(uee);
			}
		}

		return _securePrintStream;
	}

	@Override
	public PrintStream append(char c) {
		synchronized(this) {
			return super.append(c);
		}
	}

	@Override
	public PrintStream append(CharSequence charSequence) {
		synchronized(this) {
			return super.append(charSequence);
		}
	}

	@Override
	public PrintStream append(CharSequence charSequence, int start, int end) {
		synchronized(this) {
			return super.append(charSequence, start, end);
		}
	}

	@Override
	public void flush() {
		synchronized (this) {
			try {
				String content =
					_securePrintStreamByteArrayOutputStream.toString();

				content = JenkinsResultsParserUtil.redact(content);

				_systemOutPrintStream.print(content);
			}
			finally {
				_securePrintStreamByteArrayOutputStream.reset();
			}
		}
	}

	@Override
	public void print(boolean b) {
		synchronized(this) {
			super.print(b);
		}
	}

	@Override
	public void print(char c) {
		synchronized(this) {
			super.print(c);
		}
	}

	@Override
	public void print(char[] chars) {
		synchronized(this) {
			super.print(chars);
		}
	}

	@Override
	public void print(double d) {
		synchronized(this) {
			super.print(d);
		}
	}

	@Override
	public void print(float f) {
		synchronized(this) {
			super.print(f);
		}
	}

	@Override
	public void print(int i) {
		synchronized(this) {
			super.print(i);
		}
	}
	@Override
	public void print(long l) {
		synchronized(this) {
			super.print(l);
		}
	}

	@Override
	public void print(Object object) {
		synchronized(this) {
			super.print(object);
		}
	}

	@Override
	public void print(String string) {
		synchronized(this) {
			super.print(string);
		}
	}

	@Override
	public void println() {
		synchronized(this) {
			super.println();
		}
	}

	@Override
	public void println(boolean b) {
		synchronized(this) {
			super.println(b);
		}
	}

	@Override
	public void println(char c) {
		synchronized(this) {
			super.println(c);
		}
	}

	@Override
	public void println(char[] chars) {
		synchronized(this) {
			super.println(chars);
		}
	}

	@Override
	public void println(double d) {
		synchronized(this) {
			super.println(d);
		}
	}

	@Override
	public void println(float f) {
		synchronized(this) {
			super.println(f);
		}
	}

	@Override
	public void println(int i) {
		synchronized(this) {
			super.println(i);
		}
	}

	@Override
	public void println(long l) {
		synchronized(this) {
			super.println(l);
		}
	}

	@Override
	public void println(Object object) {
		synchronized(this) {
			super.println(object);
		}
	}

	@Override
	public void println(String string) {
		synchronized(this) {
			super.println(string);
		}
	}

	@Override
	public void write(byte[] bytes) throws IOException {
		synchronized(this) {
			super.write(bytes);
		}
	}

	@Override
	public void write(byte[] buffer, int offset, int length) {
		synchronized(this) {
			super.write(buffer, offset, length);
		}
	}

	@Override
	public void write(int b) {
		synchronized(this) {
			super.write(b);
		}
	}

	private SecurePrintStream(
			SecurePrintStreamByteArrayOutputStream
				securePrintStreamByteArrayOutputStream)
		throws UnsupportedEncodingException {

		super(securePrintStreamByteArrayOutputStream, true);

		_securePrintStreamByteArrayOutputStream =
			securePrintStreamByteArrayOutputStream;

		_securePrintStreamByteArrayOutputStream.setSecurePrintStream(this);

		_systemOutPrintStream = System.out;
	}

	private static SecurePrintStream _securePrintStream;

	private final SecurePrintStreamByteArrayOutputStream
		_securePrintStreamByteArrayOutputStream;
	private final PrintStream _systemOutPrintStream;

	private static class SecurePrintStreamByteArrayOutputStream
		extends ByteArrayOutputStream {

		@Override
		public void flush() throws IOException {
			super.flush();

			if (_securePrintStream != null) {
				_securePrintStream.flush();
			}
		}

		public void setSecurePrintStream(SecurePrintStream securePrintStream) {
			_securePrintStream = securePrintStream;
		}

		private SecurePrintStream _securePrintStream;

	}

}