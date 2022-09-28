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

package com.liferay.poshi.core;

import com.liferay.poshi.core.util.Validator;

import java.util.Stack;

import org.dom4j.Element;

/**
 * @author Karen Dang
 * @author Michael Hashimoto
 */
public final class PoshiStackTraceUtil {

	public static void emptyStackTrace() {
		while (!_stackTrace.isEmpty()) {
			_stackTrace.pop();
		}
	}

	public static String getCurrentFilePath() {
		return _filePaths.peek();
	}

	public static String getCurrentNamespace() {
		if (_filePaths.isEmpty()) {
			return PoshiContext.getDefaultNamespace();
		}

		String filePath = getCurrentFilePath();

		int x = filePath.indexOf("[");

		return PoshiContext.getNamespaceFromFilePath(filePath.substring(0, x));
	}

	public static String getCurrentNamespace(
		String namespacedClassCommandName) {

		String defaultNamespace = PoshiContext.getDefaultNamespace();

		String namespace =
			PoshiGetterUtil.getNamespaceFromNamespacedClassCommandName(
				namespacedClassCommandName);

		if (Validator.isNull(namespace) || namespace.equals(defaultNamespace)) {
			namespace = getCurrentNamespace();
		}

		return namespace;
	}

	public static String getSimpleStackTrace() {
		StringBuilder sb = new StringBuilder();

		for (String filePath : _stackTrace) {
			if (filePath.contains(".function")) {
				continue;
			}

			sb.append(PoshiGetterUtil.getFileNameFromFilePath(filePath));
		}

		String currentFilePath = _filePaths.peek();

		if (!currentFilePath.contains(".function")) {
			sb.append(PoshiGetterUtil.getFileNameFromFilePath(currentFilePath));
			sb.append(":");
			sb.append(PoshiGetterUtil.getLineNumber(_currentElement));
		}

		return sb.toString();
	}

	public static String getStackTrace(String msg) {
		StringBuilder sb = new StringBuilder();

		if (Validator.isNotNull(msg)) {
			sb.append(msg);
		}

		Stack<String> stackTrace = (Stack<String>)_stackTrace.clone();

		sb.append("\n");
		sb.append(_filePaths.peek());
		sb.append(":");
		sb.append(PoshiGetterUtil.getLineNumber(_currentElement));

		while (!stackTrace.isEmpty()) {
			sb.append("\n");
			sb.append(stackTrace.pop());
		}

		return sb.toString();
	}

	public static void popStackTrace() {
		_filePaths.pop();
		_stackTrace.pop();
	}

	public static void printStackTrace() {
		printStackTrace(null);
	}

	public static void printStackTrace(String msg) {
		System.out.println(getStackTrace(msg));
	}

	public static void pushStackTrace(Element element) throws Exception {
		_stackTrace.push(
			_filePaths.peek() + ":" + PoshiGetterUtil.getLineNumber(element));

		String namespacedClassCommandName = null;
		String classType = null;

		if (element.attributeValue("function") != null) {
			namespacedClassCommandName = element.attributeValue("function");
			classType = "function";
		}
		else if (element.attributeValue("macro") != null) {
			namespacedClassCommandName = element.attributeValue("macro");
			classType = "macro";
		}
		else if (element.attributeValue("test-case") != null) {
			namespacedClassCommandName = element.attributeValue("test-case");

			String className =
				PoshiGetterUtil.getClassNameFromNamespacedClassCommandName(
					namespacedClassCommandName);

			if (className.equals("super")) {
				className = PoshiGetterUtil.getExtendedTestCaseName();

				namespacedClassCommandName =
					namespacedClassCommandName.replaceFirst("super", className);
			}

			classType = "test-case";
		}
		else {
			printStackTrace();

			throw new Exception("Missing (function|macro|test-case) attribute");
		}

		_pushFilePath(namespacedClassCommandName, classType);
	}

	public static void setCurrentElement(Element currentElement) {
		_currentElement = currentElement;
	}

	public static void startStackTrace(
		String classCommandName, String classType) {

		_pushFilePath(classCommandName, classType);
	}

	private static void _pushFilePath(
		String namespacedClassCommandName, String classType) {

		String classCommandName =
			PoshiGetterUtil.getClassCommandNameFromNamespacedClassCommandName(
				namespacedClassCommandName);

		String className =
			PoshiGetterUtil.getClassNameFromNamespacedClassCommandName(
				classCommandName);

		String fileExtension = PoshiGetterUtil.getFileExtensionFromClassType(
			classType);

		String filePath = PoshiContext.getFilePathFromFileName(
			className + "." + fileExtension,
			getCurrentNamespace(namespacedClassCommandName));

		if (classType.equals("test-case") && (filePath == null)) {
			filePath = PoshiContext.getFilePathFromFileName(
				className + ".prose",
				getCurrentNamespace(namespacedClassCommandName));
		}

		String commandName =
			PoshiGetterUtil.getCommandNameFromNamespacedClassCommandName(
				namespacedClassCommandName);

		_filePaths.push(filePath + "[" + commandName + "]");
	}

	private static Element _currentElement;
	private static final Stack<String> _filePaths = new Stack<>();
	private static final Stack<String> _stackTrace = new Stack<>();

}