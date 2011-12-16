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

package com.liferay.portal.javadoc;

import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.Arrays;

/**
 * @author Igor Spasic
 */
public class JavadocUtil {

	public static Class loadClass(String className, ClassLoader classLoader)
		throws ClassNotFoundException {

		className = _prepareClassnameForLoading(className);

		if ((className.indexOf('.') == -1) || (className.indexOf('[') == -1)) {
			// maybe a primitive
			int primitiveNdx = _getPrimitiveClassName(className);
			if (primitiveNdx >= 0) {
				return PRIMITIVE_TYPES[primitiveNdx];
			}
		}

		if (classLoader != null) {
			try {
				return classLoader.loadClass(className);
			}
			catch (ClassNotFoundException cnfex) {
			}
		}

		Thread currentThread = Thread.currentThread();

		ClassLoader currentThreadClassLoader =
			currentThread.getContextClassLoader();

		if (currentThreadClassLoader != classLoader) {
			try {
				return currentThreadClassLoader.loadClass(className);
			}
			catch (ClassNotFoundException cnfex) {
			}
		}
		
		try {
			Class.forName(className);
		}
		catch (ClassNotFoundException cnfex) {
		}

		throw new ClassNotFoundException("Class not found: " + className);
	}

	private static int _getPrimitiveClassName(String className) {
		int dotIndex = className.indexOf('.');
		if (dotIndex != -1) {
			return -1;
		}
		return Arrays.binarySearch(PRIMITIVE_TYPE_NAMES, className);
	}

	/**
	 * Prepares classname for loading.
	 */
	private static String _prepareClassnameForLoading(String className) {
		int bracketCount = StringUtil.count(className, StringPool.OPEN_BRACKET);
		if (bracketCount == 0) {
			return className;
		}

		char[] bracketsChars = new char[bracketCount];
		for (int i = 0; i < bracketCount; i++) {
			bracketsChars[i] = '[';
		}
		String brackets = new String(bracketsChars);

		int bracketIndex = className.indexOf('[');
		className = className.substring(0, bracketIndex);

		int primitiveNdx = _getPrimitiveClassName(className);
		if (primitiveNdx >= 0) {
			className = String.valueOf(PRIMITIVE_BYTECODE_NAME[primitiveNdx]);

			return brackets + className;
		}
		else {
			return brackets + 'L' + className + ';';
		}
	}
	/**
	 * List of primitive bytecode characters that matches names list.
	 */
	private static final char[] PRIMITIVE_BYTECODE_NAME = new char[] {
		'Z', 'B', 'C', 'D', 'F', 'I', 'J', 'S'
	};
	/**
	 * List of primitive types that matches names list.
	 */
	private static final Class[] PRIMITIVE_TYPES = new Class[] {
		boolean.class, byte.class, char.class, double.class, float.class,
		int.class, long.class, short.class,
	};

	/**
	 * List of primitive type names.
	 */
	private static final String[] PRIMITIVE_TYPE_NAMES = new String[] {
		"boolean", "byte", "char", "double", "float", "int", "long", "short",
	};

}