/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.aspectj.elasticsearch.shutdown.thread.interruption;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

/**
 * @author Shuyang Zhou
 */
public class LogUtil {

	public static void log(String message) {
		try (QuietByteArrayOutputStream quietByteArrayOutputStream =
				new QuietByteArrayOutputStream();
			PrintStream printStream = new PrintStream(
				quietByteArrayOutputStream) {

				@Override
				public void println(Object x) {
					if (_counter++ == 0) {
						return;
					}

					super.println(x);
				}

				private int _counter;

			}) {

			Thread currentThread = Thread.currentThread();

			printStream.println(currentThread + ": " + message);

			Exception exception = new Exception();

			exception.printStackTrace(printStream);

			System.out.println(quietByteArrayOutputStream.toString());
		}
	}

	private static class QuietByteArrayOutputStream
		extends ByteArrayOutputStream {

		@Override
		public void close() {
		}

	}

}