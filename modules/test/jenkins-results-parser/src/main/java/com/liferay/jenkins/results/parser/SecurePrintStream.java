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

		JenkinsResultsParserUtil.setBuildProperties(
			JenkinsResultsParserUtil.getBuildProperties());

		String content = JenkinsResultsParserUtil.toString(
			"http://mirrors-no-cache.lax.liferay.com/github.com/liferay" +
				"/liferay-jenkins-ee/commands/build.properties");

		System.out.println(content);
	}

	@Override
	public void flush() {
		synchronized (_byteArrayOutputStream) {
			try {
				String content = _byteArrayOutputStream.toString();

				content = JenkinsResultsParserUtil.redact(content);

				_standardOut.print(content);
			}
			finally {
				_byteArrayOutputStream.reset();
			}
		}
	}

	@Override
	public void println(String string) {
		super.println(string);
		//flush();
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