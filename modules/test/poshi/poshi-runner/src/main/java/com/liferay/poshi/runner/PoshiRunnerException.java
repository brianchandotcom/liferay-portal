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

package com.liferay.poshi.runner;

import com.liferay.poshi.core.PoshiStackTrace;
import com.liferay.poshi.core.util.PoshiProperties;

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