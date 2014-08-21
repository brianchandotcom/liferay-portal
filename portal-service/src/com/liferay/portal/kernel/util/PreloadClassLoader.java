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

package com.liferay.portal.kernel.util;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Shuyang Zhou
 */
public class PreloadClassLoader extends ClassLoader {

	public PreloadClassLoader(
		ClassLoader classLoader, Map<String, Class<?>> classes) {

		super(classLoader);

		_classes.putAll(classes);
	}

	@Override
	public Class<?> loadClass(String name) throws ClassNotFoundException {
		Class<?> clazz = _classes.get(name);

		if (clazz != null) {
			return clazz;
		}

		return super.loadClass(name);
	}

	private final Map<String, Class<?>> _classes =
		new HashMap<String, Class<?>>();

}