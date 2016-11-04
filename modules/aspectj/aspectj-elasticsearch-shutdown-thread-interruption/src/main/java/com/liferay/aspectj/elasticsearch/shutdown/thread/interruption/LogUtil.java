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