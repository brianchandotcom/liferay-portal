/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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
		ClassLoader parent, Map<String, Class<?>> preloadClasses) {

		super(parent);

		_preloadClasses.putAll(preloadClasses);
	}

	@Override
	public Class<?> loadClass(String name) throws ClassNotFoundException {
		Class<?> preloadClass = _preloadClasses.get(name);

		if (preloadClass == null) {
			preloadClass = super.loadClass(name);
		}

		return preloadClass;
	}

	private Map<String, Class<?>> _preloadClasses =
		new HashMap<String, Class<?>>();

}