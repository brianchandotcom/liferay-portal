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

import java.util.ArrayList;
import java.util.List;

/**
 * @author Igor Spasic
 */
public class MethodParameter {

	public MethodParameter(String name, String signatures, Class<?> type) {
		_name = name;
		_signatures = signatures;
		_type = type;
	}

	public Class<?>[] getGenericTypes() throws ClassNotFoundException {
		if (_initialized) {
			return _genericTypes;
		}

		_genericTypes = _getGenericTypes(_signatures);

		_initialized = true;

		return _genericTypes;
	}

	public String getName() {
		return _name;
	}

	public String getSignature() {
		return _signatures;
	}

	public Class<?> getType() {
		return _type;
	}

	private static String _getClassName(String signature) {
		String className = signature;

		char c = signature.charAt(0);

		if (_isPrimitive(c)) {
			if (signature.length() != 1) {
				throw new IllegalArgumentException("Invalid: " + signature);
			}
		}
		else if (c == 'L') {
			className = className.substring(1, className.length() - 1);
			className = className.replace('/', '.');
		}
		else if (c == '[') {
			className = className.replace('/', '.');
		}
		else {
			throw new IllegalArgumentException(
				"Invalid signature " + signature);
		}

		return className;
	}

	private static String _getGenericName(String typeName) {
		if (typeName.equals(StringPool.STAR)) {
			return null;
		}

		if (typeName.startsWith(StringPool.MINUS) ||
			typeName.startsWith(StringPool.PLUS)) {

			typeName = typeName.substring(1);
		}

		return typeName;
	}

	private ClassLoader _getContextClassLoader() {
		if (_contextClassLoader != null) {
			return _contextClassLoader;
		}

		Thread currentThread = Thread.currentThread();

		_contextClassLoader = currentThread.getContextClassLoader();

		return _contextClassLoader;
	}

	private Class<?> _getGenericType(String signature)
		throws ClassNotFoundException {

		ClassLoader contextClassLoader = _getContextClassLoader();

		String className = _getClassName(signature);

		if (className.startsWith(StringPool.OPEN_BRACKET)) {
			try {
				return Class.forName(className, true, contextClassLoader);
			}
			catch (ClassNotFoundException cnfe) {
			}
		}

		return contextClassLoader.loadClass(className);
	}

	private Class<?>[] _getGenericTypes(String signatures)
		throws ClassNotFoundException {

		if (signatures == null) {
			return null;
		}

		int leftBracketIndex = signatures.indexOf(CharPool.LESS_THAN);

		if (leftBracketIndex == -1) {
			return null;
		}

		int rightBracketIndex = signatures.lastIndexOf(CharPool.GREATER_THAN);

		if (rightBracketIndex == -1) {
			return null;
		}

		String generics = signatures.substring(
			leftBracketIndex + 1, rightBracketIndex);

		List<Class<?>> genericTypeslist = new ArrayList<>();

		int level = 0;
		int index = 0;

		while (index < generics.length()) {
			char c = generics.charAt(index);

			index++;

			if (c == CharPool.LESS_THAN) {
				level++;
			}
			else if (c == CharPool.GREATER_THAN) {
				level--;
			}
			else if (level == 0) {
				String extractedTopLevelGenericName = null;

				if (c == 'L') {
					int endIndex = generics.indexOf(';', index) + 1;

					extractedTopLevelGenericName = _getGenericName(
						generics.substring(index - 1, endIndex));

					index = endIndex;
				}
				else if (c == '[') {
					char nextChar = generics.charAt(index);

					if (_isPrimitive(nextChar)) {
						extractedTopLevelGenericName = _getGenericName(
							generics.substring(index - 1, index + 1));

						index++;
					}
					else if (nextChar == 'L') {
						int endIndex = generics.indexOf(';', index) + 1;

						extractedTopLevelGenericName = _getGenericName(
							generics.substring(index - 1, endIndex));

						index = endIndex;
					}
				}

				if (extractedTopLevelGenericName != null) {
					if (Validator.isNotNull(extractedTopLevelGenericName)) {
						genericTypeslist.add(
							_getGenericType(extractedTopLevelGenericName));
					}
				}
			}
		}

		if (genericTypeslist.isEmpty()) {
			return null;
		}

		return genericTypeslist.toArray(new Class<?>[genericTypeslist.size()]);
	}

	private static boolean _isPrimitive(char c) {
		if ((c == 'B') || (c == 'C') || (c == 'D') || (c == 'F') ||
			(c == 'I') || (c == 'J') || (c == 'S') || (c == 'V') ||
			(c == 'Z')) {
			return true;
		}
		else {
			return false;
		}
	}

	private ClassLoader _contextClassLoader;
	private Class<?>[] _genericTypes;
	private boolean _initialized;
	private String _name;
	private String _signatures;
	private Class<?> _type;

}