/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.kernel.process;

import com.liferay.portal.kernel.util.PortalClassLoaderUtil;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectStreamClass;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Shuyang Zhou
 */
public class PortalClassLoaderObjectInputStream extends ObjectInputStream {

	public PortalClassLoaderObjectInputStream(InputStream is)
		throws IOException {

		super(is);

		_portalClassLoader = PortalClassLoaderUtil.getClassLoader();
	}

	@Override
	protected Class<?> resolveClass(ObjectStreamClass desc)
		throws IOException, ClassNotFoundException {

		String name = desc.getName();

		try {
			return Class.forName(name, false, _portalClassLoader);
		}
		catch (ClassNotFoundException cnfe) {
			Class<?> clazz = _primaryClasses.get(name);

			if (clazz != null) {
				return clazz;
			}
			else {
				throw cnfe;
			}
		}
	}

	private static final Map<String, Class<?>> _primaryClasses =
		new HashMap<String, Class<?>>(8, 1.0F);

	private final ClassLoader _portalClassLoader;

	static {
		_primaryClasses.put("boolean", boolean.class);
		_primaryClasses.put("byte", byte.class);
		_primaryClasses.put("char", char.class);
		_primaryClasses.put("short", short.class);
		_primaryClasses.put("int", int.class);
		_primaryClasses.put("long", long.class);
		_primaryClasses.put("float", float.class);
		_primaryClasses.put("double", double.class);
		_primaryClasses.put("void", void.class);
	}

}