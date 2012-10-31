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

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectStreamClass;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Brian Wing Shun Chan
 * @author Shuyang Zhou
 */
public class ClassLoaderObjectInputStream extends ObjectInputStream {

	public ClassLoaderObjectInputStream(InputStream is, ClassLoader classLoader)
		throws IOException {

		super(is);

		_classLoader = classLoader;
	}

	@Override
	protected Class<?> resolveClass(ObjectStreamClass osc)
		throws ClassNotFoundException {

		String name = osc.getName();

		try {
			return Class.forName(name, false, _classLoader);
		}
		catch (ClassNotFoundException cnfe) {
			Class<?> clazz = _primitiveClasses.get(name);

			if (clazz != null) {
				return clazz;
			}
			else {
				throw cnfe;
			}
		}
	}

	private static final Map<String, Class<?>> _primitiveClasses =
		new HashMap<String, Class<?>>(9, 1.0F);

	private ClassLoader _classLoader;

	static {
		_primitiveClasses.put("boolean", boolean.class);
		_primitiveClasses.put("byte", byte.class);
		_primitiveClasses.put("char", char.class);
		_primitiveClasses.put("short", short.class);
		_primitiveClasses.put("int", int.class);
		_primitiveClasses.put("long", long.class);
		_primitiveClasses.put("float", float.class);
		_primitiveClasses.put("double", double.class);
		_primitiveClasses.put("void", void.class);
	}

}