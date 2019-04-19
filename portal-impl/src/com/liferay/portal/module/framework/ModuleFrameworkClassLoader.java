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

package com.liferay.portal.module.framework;

import com.liferay.petra.string.CharPool;

import java.net.URL;
import java.net.URLClassLoader;

import java.util.Arrays;

/**
 * @author Miguel Pastor
 */
public class ModuleFrameworkClassLoader extends URLClassLoader {

	public ModuleFrameworkClassLoader(
		URL[] urls, ClassLoader parent, String[] packageNames) {

		super(urls, parent);

		_packageNames = packageNames;
	}

	@Override
	protected Class<?> loadClass(String name, boolean resolve)
		throws ClassNotFoundException {

		Object lock = getClassLoadingLock(name);

		synchronized (lock) {
			Class<?> clazz = findLoadedClass(name);

			if (clazz == null) {
				if (_hasPackageName(name)) {
					try {
						clazz = findClass(name);
					}
					catch (ClassNotFoundException cnfe) {
						clazz = super.loadClass(name, resolve);
					}
				}
				else {
					try {
						clazz = super.loadClass(name, resolve);
					}
					catch (ClassNotFoundException cnfe) {
						clazz = findClass(name);
					}
				}
			}

			if (resolve) {
				resolveClass(clazz);
			}

			return clazz;
		}
	}

	private boolean _hasPackageName(String name) {
		String packageName = name;

		int index = name.lastIndexOf(CharPool.PERIOD);

		if (index != -1) {
			packageName = name.substring(0, index);
		}

		index = Arrays.binarySearch(_packageNames, packageName);

		if (index >= 0) {
			return true;
		}

		index = -index - 1;

		if ((index == 0) || !packageName.startsWith(_packageNames[index - 1])) {
			return false;
		}

		return true;
	}

	private final String[] _packageNames;

	static {
		ClassLoader.registerAsParallelCapable();
	}

}