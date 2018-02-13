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

package com.liferay.portal.kernel.test;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayOutputStream;

import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;

/**
 * @author Shuyang Zhou
 */
public class ConsoleTestUtil {

	public static UnsyncByteArrayOutputStream hijackStdErr() {
		System.err.flush();

		UnsyncByteArrayOutputStream unsyncByteArrayOutputStream =
			new UnsyncByteArrayOutputStream();

		PrintStream printStream = new PrintStream(unsyncByteArrayOutputStream);

		System.setErr(printStream);

		return unsyncByteArrayOutputStream;
	}

	public static UnsyncByteArrayOutputStream hijackStdOut() {
		System.out.flush();

		UnsyncByteArrayOutputStream unsyncByteArrayOutputStream =
			new UnsyncByteArrayOutputStream();

		PrintStream printStream = new PrintStream(unsyncByteArrayOutputStream);

		System.setOut(printStream);

		return unsyncByteArrayOutputStream;
	}

	public static String restoreStdErr(
			UnsyncByteArrayOutputStream unsyncByteArrayOutputStream)
		throws UnsupportedEncodingException {

		System.out.flush();

		FileOutputStream fileOutputStream = new FileOutputStream(
			FileDescriptor.err);

		PrintStream printStream = new PrintStream(fileOutputStream);

		System.setErr(printStream);

		return unsyncByteArrayOutputStream.toString(
			StringPool.DEFAULT_CHARSET_NAME);
	}

	public static String restoreStdOut(
			UnsyncByteArrayOutputStream unsyncByteArrayOutputStream)
		throws UnsupportedEncodingException {

		System.out.flush();

		FileOutputStream fileOutputStream = new FileOutputStream(
			FileDescriptor.out);

		PrintStream printStream = new PrintStream(fileOutputStream);

		System.setOut(printStream);

		return unsyncByteArrayOutputStream.toString(
			StringPool.DEFAULT_CHARSET_NAME);
	}

}