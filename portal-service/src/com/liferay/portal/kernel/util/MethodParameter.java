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

package com.liferay.portal.kernel.util;

import java.util.ArrayList;

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

		String[] genericSignatures = _extractTopLevelGenericSignatures(
			_signatures);

		if (genericSignatures == null) {
			_genericTypes = null;
		}
		else {
			_genericTypes = _loadGenericTypes(genericSignatures);
		}

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

	private static String[] _extractTopLevelGenericSignatures(
		String signature) {

		if (signature == null) {
			return null;
		}

		int leftBracketIndex = signature.indexOf(CharPool.LESS_THAN);

		if (leftBracketIndex == -1) {
			return null;
		}

		int rightBracketIndex = signature.lastIndexOf(CharPool.GREATER_THAN);

		if (rightBracketIndex == -1) {
			return null;
		}

		String generics = signature.substring(
			leftBracketIndex + 1, rightBracketIndex);

		StringBuilder sb = new StringBuilder(generics.length());

		ArrayList<String> list = new ArrayList<String>();

		int level = 0;

		for (int i = 0; i < generics.length(); i++) {
			char c = generics.charAt(i);

			if (c == '<') {
				level++;
			}
			else if (c == '>') {
				level--;
			}
			else if (level == 0) {
				sb.append(c);

				if (c == ';') {
					list.add(sb.toString());

					sb.setLength(0);
				}
			}
		}

		return list.toArray(new String[list.size()]);
	}

	private static Class<?>[] _loadGenericTypes(String[] signatures)
		throws ClassNotFoundException {

		Thread currentThread = Thread.currentThread();

		ClassLoader contextClassLoader = currentThread.getContextClassLoader();

		Class<?>[] types = new Class<?>[signatures.length];

		for (int i = 0; i < signatures.length; i++) {
			String className = signatures[i];

			char c = className.charAt(0);

			if (c == 'B') {
				types[i] = byte.class;
			}
			else if (c == 'C') {
				types[i] = char.class;
			}
			else if (c == 'D') {
				types[i] = double.class;
			}
			else if (c == 'F') {
				types[i] = float.class;
			}
			else if (c == 'I') {
				types[i] = int.class;
			}
			else if (c == 'J') {
				types[i] = long.class;
			}
			else if (c == 'L') {
				className = className.substring(1, className.length() - 1);
				className = className.replace(CharPool.SLASH, CharPool.PERIOD);

				types[i] = contextClassLoader.loadClass(className);
			}
			else if (c == 'S') {
				types[i] = short.class;
			}
			else if (c == 'Z') {
				types[i] = boolean.class;
			}
			else if (c == 'V') {
				types[i] = void.class;
			}
			else if (c == CharPool.OPEN_BRACKET) {
				className = className.replace(CharPool.SLASH, CharPool.PERIOD);

				types[i] = contextClassLoader.loadClass(className);
			}
			else {
				throw new ClassNotFoundException(className);
			}
		}

		return types;
	}

	private Class<?>[] _genericTypes;
	private boolean _initialized;
	private String _name;
	private String _signatures;
	private Class<?> _type;

}