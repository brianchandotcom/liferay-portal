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

import com.liferay.poshi.runner.util.Validator;

import java.util.Stack;

/**
 * @author Karen Dang
 * @author Michael Hashimoto
 */
public final class PoshiRunnerStackTraceUtil {

	public static void emptyStackTrace() {
		while (!_stackTrace.isEmpty()) {
			_stackTrace.pop();
		}
	}

	public static String getStackTrace() {
		return getStackTrace(null);
	}

	public static String getStackTrace(String msg) {
		StringBuilder sb = new StringBuilder();

		sb.append("\nBUILD FAILED:");

		if (Validator.isNotNull(msg)) {
			sb.append(" ");
			sb.append(msg);
		}

		Stack<String> stackTrace = (Stack<String>)_stackTrace.clone();

		while (!stackTrace.isEmpty()) {
			sb.append("\n");
			sb.append(stackTrace.pop());
		}

		return sb.toString();
	}

	public static String popFilePath() {
		return _filePaths.pop();
	}

	public static String popStackTrace() {
		return _stackTrace.pop();
	}

	public static void printStackTrace() {
		printStackTrace(null);
	}

	public static void printStackTrace(String msg) {
		System.out.println(getStackTrace(msg));
	}

	public static void pushFilePath(String className, String classType) {
		if (className.contains("#")) {
			className = PoshiRunnerGetterUtil.getClassNameFromClassCommandName(
				className);
		}

		String fileExtension =
			PoshiRunnerGetterUtil.getFileExtensionFromClassType(classType);

		_filePaths.push(
			PoshiRunnerContext.getFilePathFromFileName(
				className + "." + fileExtension));
	}

	public static void pushStackTrace(String lineNumber) {
		_stackTrace.push(_filePaths.peek() + ":" + lineNumber);
	}

	private static final Stack<String> _filePaths = new Stack<>();
	private static final Stack<String> _stackTrace = new Stack<>();

}