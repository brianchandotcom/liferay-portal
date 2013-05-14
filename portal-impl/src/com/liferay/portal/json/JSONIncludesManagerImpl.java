/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.json;

import com.liferay.portal.kernel.json.JSON;
import com.liferay.portal.kernel.json.JSONIncludesManager;
import com.liferay.portal.kernel.util.StringPool;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Igor Spasic
 */
public class JSONIncludesManagerImpl implements JSONIncludesManager {

	@Override
	public String[] lookupExcludes(Class<?> type) {
		String[] excludes = _excludesMap.get(type);

		if (excludes != null) {
			return excludes;
		}

		List<String> list = new ArrayList<String>();

		while (type != null) {
			JSON jsonAnnotation = type.getAnnotation(JSON.class);

			if ((jsonAnnotation != null) && jsonAnnotation.strict()) {
				list.add(_EXCLUDE_ALL);

				break;
			}
			else {
				_scanFieldsAndMethods(list, type, false);
			}

			type = type.getSuperclass();
		}

		excludes = _listToArray(list);

		_excludesMap.put(type, excludes);

		return excludes;
	}

	@Override
	public String[] lookupIncludes(Class<?> type) {
		String[] includes = _includesMap.get(type);

		if (includes != null) {
			return includes;
		}

		List<String> list = new ArrayList<String>();

		while (type != null) {
			_scanFieldsAndMethods(list, type, true);

			type = type.getSuperclass();
		}

		includes = _listToArray(list);

		_includesMap.put(type, includes);

		return includes;
	}

	private String _getPropertyName(Method method) {
		Class<?>[] parameterTypes = method.getParameterTypes();

		if (parameterTypes.length != 0) {
			return null;
		}

		String propertyName = null;

		String methodName = method.getName();

		if (methodName.startsWith("get")) {
			propertyName = methodName.substring(3);
		}
		else if (methodName.startsWith("is")) {
			propertyName = methodName.substring(2);
		}
		else {
			return null;
		}

		if ((propertyName.length() > 2) &&
			Character.isUpperCase(propertyName.charAt(1))) {

			return propertyName;
		}

		return Character.toLowerCase(propertyName.charAt(0)) +
			propertyName.substring(1);
	}

	private String[] _listToArray(List<String> list) {
		if (list.isEmpty()) {
			return _EMPTY_LIST;
		}
		else {
			return list.toArray(new String[list.size()]);
		}
	}

	private void _scanFieldsAndMethods(
		List<String> list, Class<?> type, boolean include) {

		Field[] fields = type.getDeclaredFields();

		for (Field field : fields) {
			JSON jsonAnnotation = field.getAnnotation(JSON.class);

			if ((jsonAnnotation != null) &&
				(jsonAnnotation.include() == include)) {

				String name = field.getName();

				if (name.startsWith(StringPool.UNDERLINE)) {
					name = name.substring(1);
				}

				if (!list.contains(name)) {
					list.add(name);
				}
			}
		}

		Method[] methods = type.getDeclaredMethods();

		for (Method method : methods) {
			JSON jsonAnnotation = method.getAnnotation(JSON.class);

			if ((jsonAnnotation != null) &&
				(jsonAnnotation.include() == include)) {

				String name = _getPropertyName(method);

				if (name != null) {
					if (!list.contains(name)) {
						list.add(name);
					}
				}
			}
		}
	}

	private static final String[] _EMPTY_LIST = new String[0];

	private static final String _EXCLUDE_ALL = "*";

	private Map<Class<?>, String[]> _excludesMap =
		new HashMap<Class<?>, String[]>();
	private Map<Class<?>, String[]> _includesMap =
		new HashMap<Class<?>, String[]>();

}