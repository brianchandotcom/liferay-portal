/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

	private final Map<String, Class<?>> _classes = new HashMap<>();

}