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

package com.liferay.dispatch.talend.web.internal.process;

import com.liferay.petra.io.unsync.UnsyncByteArrayOutputStream;
import com.liferay.petra.process.ProcessCallable;
import com.liferay.petra.process.ProcessException;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import java.security.Permission;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Igor Beslic
 */
public class TalendProcessCallable
	implements ProcessCallable<TalendProcessOutput> {

	public TalendProcessCallable(
		String[] mainMethodArgs, String jobMainClassFQN) {

		_mainMethodArgs = mainMethodArgs;
		_jobMainClassFQN = jobMainClassFQN;
	}

	@Override
	public TalendProcessOutput call() throws ProcessException {
		PrintStream errPrintStream = System.err;
		PrintStream outPrintStream = System.out;

		UnsyncByteArrayOutputStream errUnsyncByteArrayOutputStream =
			new UnsyncByteArrayOutputStream();

		System.setErr(
			new TeePrintStream(errUnsyncByteArrayOutputStream, errPrintStream));

		UnsyncByteArrayOutputStream outUnsyncByteArrayOutputStream =
			new UnsyncByteArrayOutputStream();

		System.setOut(
			new TeePrintStream(outUnsyncByteArrayOutputStream, outPrintStream));

		RuntimeException runtimeException = new RuntimeException();

		AtomicInteger exitStatusAtomicInteger = new AtomicInteger();

		System.setSecurityManager(
			new SecurityManager() {

				@Override
				public void checkExit(int status) {
					exitStatusAtomicInteger.set(status);

					throw runtimeException;
				}

				@Override
				public void checkPermission(Permission perm) {
				}

			});

		ClassLoader classLoader = TalendProcessCallable.class.getClassLoader();

		try {
			Class<?> talendJobClass = classLoader.loadClass(_jobMainClassFQN);

			Method mainMethod = talendJobClass.getMethod(
				"main", String[].class);

			mainMethod.setAccessible(true);

			mainMethod.invoke(null, new Object[] {_mainMethodArgs});
		}
		catch (InvocationTargetException invocationTargetException) {
			Throwable causeThrowable = invocationTargetException.getCause();

			if (causeThrowable == runtimeException) {
				return new TalendProcessOutput(
					exitStatusAtomicInteger.get(),
					outUnsyncByteArrayOutputStream.toString(),
					errUnsyncByteArrayOutputStream.toString());
			}

			throw new ProcessException(causeThrowable);
		}
		catch (Throwable throwable) {
			throw new ProcessException(throwable);
		}
		finally {
			System.setErr(errPrintStream);
			System.setOut(outPrintStream);
		}

		throw new ProcessException("Talend process did not trigger jvm exit");
	}

	private static final long serialVersionUID = 1L;

	private final String _jobMainClassFQN;
	private final String[] _mainMethodArgs;

	private class TeePrintStream extends PrintStream {

		@Override
		public void close() {
			super.close();

			_printStream.flush();
		}

		@Override
		public void flush() {
			super.flush();

			_printStream.flush();
		}

		@Override
		public void write(byte[] bytes) throws IOException {
			super.write(bytes);

			_printStream.write(bytes);
		}

		@Override
		public void write(byte[] bytes, int offset, int length) {
			super.write(bytes, offset, length);

			_printStream.write(bytes, offset, length);
		}

		@Override
		public void write(int integer) {
			super.write(integer);

			_printStream.write(integer);
		}

		private TeePrintStream(
			OutputStream outputStream, PrintStream printStream) {

			super(outputStream);

			_printStream = printStream;
		}

		private final PrintStream _printStream;

	}

}