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

import com.liferay.poshi.runner.util.StringUtil;

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

	public static void clear() {
		_commandMap.clear();
		_commandMapStack.clear();
		_executeMap.clear();
		_returnMap.clear();
		_staticMap.clear();
	}

	public static boolean containsKeyInCommandMap(String key) throws Exception {
		return _commandMap.containsKey(replaceCommandVars(key));
	}

	public static boolean containsKeyInExecuteMap(String key) throws Exception {
		return _executeMap.containsKey(replaceCommandVars(key));
	}

	public static boolean containsKeyInReturnMap(String key) throws Exception {
		return _returnMap.containsKey(replaceCommandVars(key));
	}

	public static boolean containsKeyInStaticMap(String key) throws Exception {
		return _staticMap.containsKey(replaceCommandVars(key));
	}

	public static String getValueFromCommandMap(String key) throws Exception {
		return _commandMap.get(replaceCommandVars(key));
	}

	public static String getValueFromExecuteMap(String key) throws Exception {
		return _executeMap.get(replaceCommandVars(key));
	}

	public static String getValueFromReturnMap(String key) throws Exception {
		return _returnMap.get(replaceCommandVars(key));
	}

	public static void popCommandMap() {
		_commandMap = _commandMapStack.pop();

		_executeMap = new HashMap<>();
		_returnMap = new HashMap<>();
	}

	public static void pushCommandMap() {
		pushCommandMap(false);
	}

	public static void pushCommandMap(boolean staticMap) {
		_commandMapStack.push(_commandMap);

		_commandMap = _executeMap;

		if (staticMap) {
			_commandMap.putAll(_staticMap);
		}

		_executeMap = new HashMap<>();
		_returnMap = new HashMap<>();
	}

	public static void putIntoCommandMap(String key, String value)
		throws Exception {

		_commandMap.put(replaceCommandVars(key), replaceCommandVars(value));
	}

	public static void putIntoExecuteMap(String key, String value)
		throws Exception {

		_executeMap.put(replaceCommandVars(key), replaceCommandVars(value));
	}

	public static void putIntoReturnMap(String key, String value)
		throws Exception {

		_returnMap.put(replaceCommandVars(key), replaceCommandVars(value));
	}

	public static void putIntoStaticMap(String key, String value)
		throws Exception {

		_staticMap.put(replaceCommandVars(key), replaceCommandVars(value));
	}

	public static String replaceCommandVars(String token) throws Exception {
		Matcher matcher = _pattern.matcher(token);

		while (matcher.find() && _commandMap.containsKey(matcher.group(1))) {
			String varValue = getValueFromCommandMap(matcher.group(1));

			token = StringUtil.replace(token, matcher.group(), varValue);
		}

		return token;
	}

	public static String replaceExecuteVars(String token) throws Exception {
		Matcher matcher = _pattern.matcher(token);

		while (matcher.find() && _executeMap.containsKey(matcher.group(1))) {
			String varValue = getValueFromExecuteMap(matcher.group(1));

			token = StringUtil.replace(token, matcher.group(), varValue);
		}

		return token;
	}

	private static Map<String, String> _commandMap = new HashMap<>();
	private static final Stack<Map<String, String>> _commandMapStack =
		new Stack<>();
	private static Map<String, String> _executeMap = new HashMap<>();
	private static final Pattern _pattern = Pattern.compile("\\$\\{([^}]*)\\}");
	private static Map<String, String> _returnMap = new HashMap<>();
	private static final Map<String, String> _staticMap = new HashMap<>();

}