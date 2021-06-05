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

import com.liferay.poshi.core.util.ListUtil;
import com.liferay.poshi.core.util.StringUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Karen Dang
 * @author Michael Hashimoto
 */
public class PoshiVariablesUtil {

	public static void clear() {
		_commandMap.clear();
		_commandMapStack.clear();
		_executeMap.clear();
		_staticMap.clear();
	}

	public static boolean containsKeyInCommandMap(String key) {
		return _commandMap.containsKey(replaceCommandVars(key));
	}

	public static boolean containsKeyInExecuteMap(String key) {
		return _executeMap.containsKey(replaceCommandVars(key));
	}

	public static boolean containsKeyInStaticMap(String key) {
		return _staticMap.containsKey(replaceCommandVars(key));
	}

	public static String getReplacedCommandVarsString(String token) {
		if (token == null) {
			return null;
		}

		Object tokenObject = replaceCommandVars(token);

		if (tokenObject instanceof List) {
			return ListUtil.toString((List)tokenObject);
		}

		return tokenObject.toString();
	}

	public static String getStringFromCommandMap(String key) {
		if (containsKeyInCommandMap((String)replaceCommandVars(key))) {
			Object object = getValueFromCommandMap(key);

			return object.toString();
		}

		return null;
	}

	public static String getStringFromExecuteMap(String key) {
		if (containsKeyInExecuteMap((String)replaceCommandVars(key))) {
			Object object = getValueFromExecuteMap(key);

			return object.toString();
		}

		return null;
	}

	public static String getStringFromStaticMap(String key) {
		if (containsKeyInStaticMap((String)replaceStaticVars(key))) {
			Object object = getValueFromExecuteMap(key);

			return object.toString();
		}

		return null;
	}

	public static Object getValueFromCommandMap(String key) {
		return _commandMap.get(replaceCommandVars(key));
	}

	public static Object getValueFromExecuteMap(String key) {
		return _executeMap.get(replaceCommandVars(key));
	}

	public static Object getValueFromStaticMap(String key) {
		return _staticMap.get(replaceCommandVars(key));
	}

	public static void popCommandMap() {
		_commandMap = _commandMapStack.pop();

		_commandMap.putAll(_staticMap);

		_executeMap = new HashMap<>();
	}

	public static void pushCommandMap() {
		_commandMapStack.push(_commandMap);

		_commandMap = _executeMap;

		_commandMap.putAll(_staticMap);

		_executeMap = new HashMap<>();
	}

	public static void putIntoCommandMap(String key, Object value) {
		if (value instanceof String) {
			_commandMap.put(
				(String)replaceCommandVars(key),
				replaceCommandVars((String)value));
		}
		else {
			_commandMap.put((String)replaceCommandVars(key), value);
		}

		if (containsKeyInStaticMap(key)) {
			putIntoStaticMap(key, value);
		}
	}

	public static void putIntoExecuteMap(String key, Object value) {
		if (value instanceof String) {
			_executeMap.put(
				(String)replaceCommandVars(key),
				replaceCommandVars((String)value));
		}
		else {
			_executeMap.put((String)replaceCommandVars(key), value);
		}
	}

	public static void putIntoStaticMap(String key, Object value) {
		if (value instanceof String) {
			_staticMap.put(
				(String)replaceCommandVars(key),
				replaceCommandVars((String)value));
		}
		else {
			_staticMap.put((String)replaceCommandVars(key), value);
		}
	}

	public static Object replaceCommandVars(String token) {
		Matcher matcher = _pattern.matcher(token);

		if (matcher.matches() && _commandMap.containsKey(matcher.group(1))) {
			return getValueFromCommandMap(matcher.group(1));
		}

		matcher.reset();

		while (matcher.find() && _commandMap.containsKey(matcher.group(1))) {
			String varValue = getStringFromCommandMap(matcher.group(1));

			token = StringUtil.replace(token, matcher.group(), varValue);
		}

		return token;
	}

	public static Object replaceExecuteVars(String token) {
		Matcher matcher = _pattern.matcher(token);

		if (matcher.matches() && _executeMap.containsKey(matcher.group(1))) {
			return getValueFromExecuteMap(matcher.group(1));
		}

		matcher.reset();

		while (matcher.find() && _executeMap.containsKey(matcher.group(1))) {
			String varValue = getStringFromExecuteMap(matcher.group(1));

			token = StringUtil.replace(token, matcher.group(), varValue);
		}

		return token;
	}

	public static Object replaceStaticVars(String token) {
		Matcher matcher = _pattern.matcher(token);

		if (matcher.matches() && _staticMap.containsKey(matcher.group(1))) {
			return getValueFromStaticMap(matcher.group(1));
		}

		matcher.reset();

		while (matcher.find() && _staticMap.containsKey(matcher.group(1))) {
			String varValue = getStringFromStaticMap(matcher.group(1));

			token = StringUtil.replace(token, matcher.group(), varValue);
		}

		return token;
	}

	private static Map<String, Object> _commandMap = new HashMap<>();
	private static final Stack<Map<String, Object>> _commandMapStack =
		new Stack<>();
	private static Map<String, Object> _executeMap = new HashMap<>();
	private static final Pattern _pattern = Pattern.compile("\\$\\{([^}]*)\\}");
	private static final Map<String, Object> _staticMap = new HashMap<>();

}