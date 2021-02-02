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

import com.liferay.dispatch.talend.web.internal.process.exception.TalendProcessException;
import com.liferay.petra.process.ProcessCallable;
import com.liferay.petra.process.ProcessException;

import java.io.PrintStream;
import java.io.Serializable;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import java.security.Permission;

/**
 * @author Igor Beslic
 */
public class TalendProcessCallable implements ProcessCallable<Serializable> {

	public TalendProcessCallable(
		String[] mainMethodArgs, String jobMainClassFQN) {

		_mainMethodArgs = mainMethodArgs;
		_jobMainClassFQN = jobMainClassFQN;
	}

	@Override
	public Serializable call() throws ProcessException {
		PrintStream errPrintStream = System.err;
		PrintStream outPrintStream = System.out;

		try {
			SniffPrintStream errSniffPrintStream = new SniffPrintStream(
				System.err);

			System.setErr(errSniffPrintStream);

			SniffPrintStream outSniffPrintStream = new SniffPrintStream(
				System.out);

			System.setOut(outSniffPrintStream);

			TalendProcessException talendProcessException =
				new TalendProcessException();

			System.setSecurityManager(
				new SecurityManager() {

					@Override
					public void checkExit(int status) {
						talendProcessException.setStatus(status);

						throw talendProcessException;
					}

					@Override
					public void checkPermission(Permission perm) {
					}

				});

			ClassLoader classLoader =
				TalendProcessCallable.class.getClassLoader();

			try {
				Class<?> talendJobClass = classLoader.loadClass(
					_jobMainClassFQN);

				Method mainMethod = talendJobClass.getMethod(
					"main", String[].class);

				mainMethod.setAccessible(true);

				mainMethod.invoke(null, new Object[] {_mainMethodArgs});

				return new TalendProcessOutput(
					errSniffPrintStream._bytes,
					talendProcessException.getStatus(),
					outSniffPrintStream._bytes);
			}
			catch (InvocationTargetException invocationTargetException) {
				Throwable causeThrowable = invocationTargetException.getCause();

				if (causeThrowable == talendProcessException) {
					return new TalendProcessOutput(
						errSniffPrintStream._bytes,
						talendProcessException.getStatus(),
						outSniffPrintStream._bytes);
				}

				throw new ProcessException(causeThrowable);
			}
			catch (Throwable throwable) {
				throw new ProcessException(throwable);
			}
		}
		finally {
			System.setErr(errPrintStream);
			System.setOut(outPrintStream);
		}
	}

	private static final long serialVersionUID = 1L;

	private final String _jobMainClassFQN;
	private final String[] _mainMethodArgs;

	private class SniffPrintStream extends PrintStream {

		public SniffPrintStream(PrintStream printStream) {
			super(printStream);
		}

		@Override
		public void write(byte[] buf, int off, int len) {
			byte[] tempBytes = _bytes;

			_bytes = new byte[tempBytes.length + len];

			System.arraycopy(tempBytes, 0, _bytes, 0, tempBytes.length);
			System.arraycopy(buf, off, _bytes, tempBytes.length, len);

			super.write(buf, off, len);
		}

		private byte[] _bytes = new byte[0];

	}

}