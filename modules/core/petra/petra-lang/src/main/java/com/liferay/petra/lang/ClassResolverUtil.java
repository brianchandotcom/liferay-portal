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

package com.liferay.petra.lang;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Shuyang Zhou
 */
public class ClassResolverUtil {

	public static Class<?> resolve(String className, ClassLoader classLoader)
		throws ClassNotFoundException {

		try {
			return Class.forName(className, false, classLoader);
		}
		catch (ClassNotFoundException cnfe) {
			Class<?> clazz = _primitiveClasses.get(className);

			if (clazz != null) {
				return clazz;
			}
			else {
				throw cnfe;
			}
		}
	}

	private static final Map<String, Class<?>> _primitiveClasses =
		new HashMap<String, Class<?>>(9, 1.0F) {
			{
				put("boolean", boolean.class);
				put("byte", byte.class);
				put("char", char.class);
				put("double", double.class);
				put("float", float.class);
				put("int", int.class);
				put("long", long.class);
				put("short", short.class);
				put("void", void.class);
			}
		};

}