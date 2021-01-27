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
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.Base64;

import java.io.PrintStream;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;

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

		TalendProcessOutPrintStream talendProcessOutPrintStream =
			new TalendProcessOutPrintStream(System.out);

		System.setOut(talendProcessOutPrintStream);

		TalendProcessErrPrintStream talendProcessErrPrintStream =
			new TalendProcessErrPrintStream(System.err);

		System.setErr(talendProcessErrPrintStream);

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

			if (causeThrowable == talendProcessException) {
				return _getTalendProcessOutput(
					talendProcessException.getStatus(),
					talendProcessErrPrintStream, talendProcessOutPrintStream);
			}

			throw new ProcessException(causeThrowable);
		}
		catch (Throwable throwable) {
			throw new ProcessException(throwable);
		}

		return _getTalendProcessOutput(
			0, talendProcessErrPrintStream, talendProcessOutPrintStream);
	}

	private String _getTalendProcessOutput(
		int exitCode, TalendProcessErrPrintStream talendProcessErrPrintStream,
		TalendProcessOutPrintStream talendProcessOutPrintStream) {

		return String.format(
			"{\"%s\":\"%s\",\"%s\":%d,\"%s\":\"%s\"}",
			TalendProcessOutputParser.KEY_ERROR,
			Base64.encode(talendProcessErrPrintStream._error.getBytes()),
			TalendProcessOutputParser.KEY_EXIT_CODE, exitCode,
			TalendProcessOutputParser.KEY_OUTPUT,
			Base64.encode(talendProcessOutPrintStream._output.getBytes()));
	}

	private static final long serialVersionUID = 1L;

	private final String _jobMainClassFQN;
	private final String[] _mainMethodArgs;

	private static class TalendProcessErrPrintStream extends PrintStream {

		public TalendProcessErrPrintStream(PrintStream printStream) {
			super(printStream);
		}

		@Override
		public void write(byte[] buf, int off, int len) {
			try {
				_error = _error.concat(
					new String(buf, off, len, StringPool.UTF8));
			}
			catch (UnsupportedEncodingException unsupportedEncodingException) {
				unsupportedEncodingException.printStackTrace();
			}

			super.write(buf, off, len);
		}

		private String _error = "";

	}

	private static class TalendProcessOutPrintStream extends PrintStream {

		public TalendProcessOutPrintStream(PrintStream printStream) {
			super(printStream);
		}

		@Override
		public void write(byte[] buf, int off, int len) {
			try {
				_output = _output.concat(
					new String(buf, off, len, StringPool.UTF8));
			}
			catch (UnsupportedEncodingException unsupportedEncodingException) {
				unsupportedEncodingException.printStackTrace();
			}

			super.write(buf, off, len);
		}

		private String _output = "";

	}

}