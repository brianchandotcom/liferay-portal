/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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