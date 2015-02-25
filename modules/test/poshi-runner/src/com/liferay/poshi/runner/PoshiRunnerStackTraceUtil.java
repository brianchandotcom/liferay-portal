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

import java.util.Stack;

/**
 * @author Karen Dang
 * @author Michael Hashimoto
 */
public final class PoshiRunnerStackTraceUtil {

	public static Stack<String> getStackTrace() {
		return _stackTrace;
	}

	public static String popFilePath() {
		return _filePaths.pop();
	}

	public static String popStackTrace() {
		return _stackTrace.pop();
	}

	public static void pushFilePath(
		String className, String classType, String lineNumber) {

		if (className.contains("#")) {
			className = PoshiRunnerGetterUtil.getClassNameFromClassCommandName(
				className);
		}

		_filePaths.push(
			PoshiRunnerContext.getFilePath(className + "." + classType));
	}

	public static void pushStackTrace(String lineNumber) {
		_stackTrace.push(_filePaths.peek() + ":" + lineNumber + "\n");
	}

	private static final Stack<String> _filePaths = new Stack<>();
	private static final Stack<String> _stackTrace = new Stack<>();

}