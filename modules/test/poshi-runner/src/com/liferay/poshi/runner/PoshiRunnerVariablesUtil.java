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

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Karen Dang
 * @author Michael Hashimoto
 */
public class PoshiRunnerVariablesUtil {

	public static boolean containsKey(String key) {
		Map<String, String> map = _variablesStack.peek();

		if (map.containsKey(key)) {
			return true;
		}

		return false;
	}

	public static String getVarValue(String var) {
		Map<String, String> map = _variablesStack.peek();

		return map.get(var);
	}

	public static Map<String, String> popStack() {
		return _variablesStack.pop();
	}

	public static void pushTempMapToStack() {
		_variablesStack.push(_tempMap);
	}

	public static String replaceVariables(String variable) {
		Pattern pattern = Pattern.compile(_REGEX);
		Matcher matcher = pattern.matcher(variable);

		while (matcher.find()) {
			String token = getVarValue(matcher.group(1));

			String replacement = Matcher.quoteReplacement(token);

			variable = variable.replaceFirst(_REGEX, replacement);
		}

		return variable;
	}

	public static void resetTempMap() {
		_tempMap = new HashMap<>();
	}

	public static void setVarInTempMap(String var, String value) {
		_tempMap.put(var, value);
	}

	public static void setVarToParentMap(String var, String value) {
		_tempMap = popStack();

		setVarInTempMap(var, value);

		pushTempMapToStack();
	}

	private static final String _REGEX = "\\$\\{([^}]*)\\}";

	private static Map<String, String> _tempMap = new HashMap<>();
	private static final Stack<Map<String, String>> _variablesStack =
		new Stack<Map<String, String>>();

}