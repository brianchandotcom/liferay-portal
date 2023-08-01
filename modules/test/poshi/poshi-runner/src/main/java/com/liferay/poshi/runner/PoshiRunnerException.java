/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.poshi.runner;

import com.liferay.poshi.core.PoshiProperties;
import com.liferay.poshi.core.PoshiStackTrace;

import java.io.PrintStream;
import java.io.PrintWriter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Kenji Heigel
 */
public class PoshiRunnerException extends Exception {

	public PoshiRunnerException(
		Exception exception, PoshiStackTrace poshiStackTrace) {

		super(exception);

		_poshiStackTraceElements = poshiStackTrace.getStackTrace();
	}

	@Override
	public String getLocalizedMessage() {
		Throwable throwable = getCause();

		return throwable.getLocalizedMessage();
	}

	@Override
	public String getMessage() {
		Throwable throwable = getCause();

		return throwable.getMessage();
	}

	@Override
	public StackTraceElement[] getStackTrace() {
		Throwable throwable = getCause();

		List<StackTraceElement> stackTraceElements = new ArrayList<>();

		Collections.addAll(stackTraceElements, _poshiStackTraceElements);

		Collections.addAll(stackTraceElements, throwable.getStackTrace());

		return stackTraceElements.toArray(new StackTraceElement[0]);
	}

	@Override
	public void printStackTrace(PrintStream printStream) {
		_printStackTrace(new WrappedPrintStream(printStream));
	}

	@Override
	public void printStackTrace(PrintWriter printWriter) {
		_printStackTrace(new WrappedPrintWriter(printWriter));
	}

	@Override
	public String toString() {
		Throwable throwable = getCause();

		return throwable.toString();
	}

	private void _printStackTrace(PrintStreamOrWriter printStreamOrWriter) {
		Throwable throwable = getCause();

		printStreamOrWriter.println(throwable);

		for (StackTraceElement stackTraceElement : getStackTrace()) {
			printStreamOrWriter.println("\tat " + stackTraceElement);
		}

		PoshiProperties poshiProperties = PoshiProperties.getPoshiProperties();

		if (poshiProperties.debugStacktrace) {
			super.printStackTrace();
		}
	}

	private final StackTraceElement[] _poshiStackTraceElements;

	private abstract static class PrintStreamOrWriter {

		public abstract void println(Object object);

	}

	private static class WrappedPrintStream extends PrintStreamOrWriter {

		public WrappedPrintStream(PrintStream printStream) {
			_printStream = printStream;
		}

		public void println(Object object) {
			_printStream.println(object);
		}

		private final PrintStream _printStream;

	}

	private static class WrappedPrintWriter extends PrintStreamOrWriter {

		public WrappedPrintWriter(PrintWriter printWriter) {
			_printWriter = printWriter;
		}

		public void println(Object object) {
			_printWriter.println(object);
		}

		private final PrintWriter _printWriter;

	}

}