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

	public static void main(String[] args) throws Exception {
		JenkinsResultsParserUtil.debug = true;

		System.setOut(getInstance());

		/*JenkinsResultsParserUtil.setBuildProperties(
			JenkinsResultsParserUtil.getBuildProperties());*/

		String content = JenkinsResultsParserUtil.toString(
			"http://mirrors-no-cache.lax.liferay.com/github.com/liferay" +
				"/liferay-jenkins-ee/commands/build.properties");

		System.out.println(content);
	}

	@Override
	public PrintStream append(char c) {
		if (_suspendFlush) {
			return _tempPrintStream.append(c);
		}

		return super.append(c);
	}

	@Override
	public PrintStream append(CharSequence csq) {
		if (_suspendFlush) {
			return _tempPrintStream.append(csq);
		}

		return super.append(csq);
	}

	@Override
	public PrintStream append(CharSequence csq, int start, int end) {
		if (_suspendFlush) {
			return _tempPrintStream.append(csq, start, end);
		}

		return super.append(csq, start, end);
	}

	@Override
	public void flush() {
		if (!_suspendFlush) {
			synchronized (this) {
				try {
					_tempByteArrayOutputStream = new ByteArrayOutputStream();

					_tempPrintStream = new PrintStream(
						_tempByteArrayOutputStream);

					_suspendFlush = true;

					String content = _byteArrayOutputStream.toString();

					content = JenkinsResultsParserUtil.redact(content);

					_standardOut.print(content);
				}
				finally {
					_byteArrayOutputStream.reset();

					_suspendFlush = false;

					try {
						_byteArrayOutputStream.write(
							_tempByteArrayOutputStream.toByteArray());
					}
					catch (IOException ioe) {
						ioe.printStackTrace();
					}
					finally {
						_tempByteArrayOutputStream.reset();
					}

					_tempPrintStream.close();
				}
			}
		}
	}

	@Override
	public void print(boolean b) {
		if (_suspendFlush) {
			_tempPrintStream.print(b);

			return;
		}

		super.print(b);
	}

	@Override
	public void print(char c) {
		if (_suspendFlush) {
			_tempPrintStream.print(c);

			return;
		}

		super.print(c);
	}

	@Override
	public void print(char[] s) {
		if (_suspendFlush) {
			_tempPrintStream.print(s);

			return;
		}

		super.print(s);
	}

	@Override
	public void print(double d) {
		if (_suspendFlush) {
			_tempPrintStream.print(d);

			return;
		}

		super.print(d);
	}

	@Override
	public void print(float f) {
		if (_suspendFlush) {
			_tempPrintStream.print(f);

			return;
		}

		super.print(f);
	}

	@Override
	public void print(int i) {
		if (_suspendFlush) {
			_tempPrintStream.print(i);

			return;
		}

		super.print(i);
	}

	@Override
	public void print(long l) {
		if (_suspendFlush) {
			_tempPrintStream.print(l);

			return;
		}

		super.print(l);
	}

	@Override
	public void print(Object obj) {
		if (_suspendFlush) {
			_tempPrintStream.print(obj);

			return;
		}

		super.print(obj);
	}

	@Override
	public void print(String s) {
		if (_suspendFlush) {
			_tempPrintStream.print(s);

			return;
		}

		super.print(s);
	}

	@Override
	public void println() {
		if (_suspendFlush) {
			_tempPrintStream.println();

			return;
		}

		super.println();
	}

	@Override
	public void println(boolean x) {
		if (_suspendFlush) {
			_tempPrintStream.println(x);

			return;
		}

		super.println(x);
	}

	@Override
	public void println(char x) {
		if (_suspendFlush) {
			_tempPrintStream.println(x);

			return;
		}

		super.println(x);
	}

	@Override
	public void println(char[] x) {
		if (_suspendFlush) {
			_tempPrintStream.println(x);

			return;
		}

		super.println(x);
	}

	@Override
	public void println(double x) {
		if (_suspendFlush) {
			_tempPrintStream.println(x);

			return;
		}

		super.println(x);
	}

	@Override
	public void println(float x) {
		if (_suspendFlush) {
			_tempPrintStream.println(x);

			return;
		}

		super.println(x);
	}

	@Override
	public void println(int x) {
		if (_suspendFlush) {
			_tempPrintStream.println(x);

			return;
		}

		super.println(x);
	}

	@Override
	public void println(long x) {
		if (_suspendFlush) {
			_tempPrintStream.println(x);

			return;
		}

		super.println(x);
	}

	@Override
	public void println(Object x) {
		if (_suspendFlush) {
			_tempPrintStream.println(x);

			return;
		}

		super.println(x);
	}

	@Override
	public void println(String string) {
		if (_suspendFlush) {
			_tempPrintStream.println(string);

			return;
		}

		super.println(string);
	}

	@Override
	public void write(byte[] b) throws IOException {
		if (_suspendFlush) {
			_tempPrintStream.write(b);

			return;
		}

		super.write(b);
	}

	@Override
	public void write(byte[] buf, int off, int len) {
		if (_suspendFlush) {
			_tempPrintStream.write(buf, off, len);

			return;
		}

		super.write(buf, off, len);
	}

	@Override
	public void write(int b) {
		if (_suspendFlush) {
			_tempPrintStream.write(b);

			return;
		}

		super.write(b);
	}

	private SecurePrintStream(
			SecurePrintStreamByteArrayOutputStream byteArrayOutputStream)
		throws UnsupportedEncodingException {

		super(byteArrayOutputStream, true);

		_byteArrayOutputStream = byteArrayOutputStream;

		_byteArrayOutputStream.setSecurePrintStream(this);

		_standardOut = System.out;
	}

	private static SecurePrintStream _securePrintStream;

	private final SecurePrintStreamByteArrayOutputStream _byteArrayOutputStream;
	private final PrintStream _standardOut;
	private boolean _suspendFlush;
	private ByteArrayOutputStream _tempByteArrayOutputStream;
	private PrintStream _tempPrintStream;

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