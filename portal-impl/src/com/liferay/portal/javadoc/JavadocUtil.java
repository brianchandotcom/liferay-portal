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

package com.liferay.portal.javadoc;

import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.ClassLoaderUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.Arrays;

/**
 * @author Igor Spasic
 */
public class JavadocUtil {

	public static Class<?> loadClass(ClassLoader classLoader, String className)
		throws ClassNotFoundException {

		className = _getLoadableClassName(className);

		if ((className.indexOf('.') == -1) || (className.indexOf('[') == -1)) {
			int primitiveIndex = _getPrimitiveIndex(className);

			if (primitiveIndex >= 0) {
				return _PRIMITIVE_TYPES[primitiveIndex];
			}
		}

		if (classLoader != null) {
			try {
				return classLoader.loadClass(className);
			}
			catch (ClassNotFoundException cnfe) {
			}
		}

		ClassLoader contextClassLoader =
			ClassLoaderUtil.getContextClassLoader();

		if (classLoader != contextClassLoader) {
			try {
				return contextClassLoader.loadClass(className);
			}
			catch (ClassNotFoundException cnfe) {
			}
		}

		return Class.forName(className);
	}

	private static String _getLoadableClassName(String className) {
		int bracketCount = StringUtil.count(className, CharPool.OPEN_BRACKET);

		if (bracketCount == 0) {
			return className;
		}

		StringBuilder sb = new StringBuilder(bracketCount);

		for (int i = 0; i < bracketCount; i++) {
			sb.append('[');
		}

		int bracketIndex = className.indexOf('[');

		className = className.substring(0, bracketIndex);

		int primitiveIndex = _getPrimitiveIndex(className);

		if (primitiveIndex >= 0) {
			className = String.valueOf(
				_PRIMITIVE_BYTECODE_NAME[primitiveIndex]);

			return sb.toString() + className;
		}
		else {
			return sb.toString() + 'L' + className + ';';
		}
	}

	private static int _getPrimitiveIndex(String className) {
		if (className.indexOf('.') != -1) {
			return -1;
		}

		return Arrays.binarySearch(_PRIMITIVE_TYPE_NAMES, className);
	}

	private static final char[] _PRIMITIVE_BYTECODE_NAME =
		{'Z', 'B', 'C', 'D', 'F', 'I', 'J', 'S'};

	private static final String[] _PRIMITIVE_TYPE_NAMES =
		{"boolean", "byte", "char", "double", "float", "int", "long", "short"};

	private static final Class<?>[] _PRIMITIVE_TYPES = new Class<?>[] {
		boolean.class, byte.class, char.class, double.class, float.class,
		int.class, long.class, short.class
	};

}