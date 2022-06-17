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

package com.liferay.client.extension.type.internal.util;

import com.liferay.client.extension.type.CET;
import com.liferay.client.extension.type.annotation.CETProperty;

import java.lang.reflect.Method;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author Iván Zaera Avellón
 */
public class URLFieldUtil {

	public static boolean isURLField(
		Class<? extends CET> cetClass, String name) {

		Set<String> urlFields = _urlFieldsMap.get(cetClass);

		if (urlFields == null) {
			urlFields = new HashSet<>();

			for (Method method : cetClass.getDeclaredMethods()) {
				CETProperty cetProperty = method.getAnnotation(
					CETProperty.class);

				if (cetProperty.isURL()) {
					urlFields.add(cetProperty.name());
				}
			}

			_urlFieldsMap.putIfAbsent(cetClass, urlFields);
		}

		return urlFields.contains(name);
	}

	private static final ConcurrentMap<Class<? extends CET>, Set<String>>
		_urlFieldsMap = new ConcurrentHashMap<>();

}