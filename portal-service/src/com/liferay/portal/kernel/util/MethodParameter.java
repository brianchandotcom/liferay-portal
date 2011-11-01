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
		if (_initialized == false) {

			String[] genericSignatures =
				_extractTopLevelGenericSignatures(_signatures);

			if (genericSignatures == null) {
				_genericTypes = null;
			}
			else {
				_genericTypes = _loadGenericTypes(genericSignatures);
			}
			_initialized = true;
		}
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

	/**
	 * Extracts top level generic signatures for given type signature.
	 * Returns <code>null</code> when generics information is not available.
	 */
	private static String[] _extractTopLevelGenericSignatures(
		String signature) {

		if (signature == null) {
			return null;
		}

		int leftBracketIndex = signature.indexOf('<');

		if (leftBracketIndex == -1) {
			return null;
		}

		int rightBracketIndex = signature.lastIndexOf('>');

		if (rightBracketIndex == -1) {
			return null;
		}

		String generics =
			signature.substring(leftBracketIndex + 1, rightBracketIndex);

		int total = generics.length();
		StringBuilder sb = new StringBuilder(total);

		ArrayList<String> list = new ArrayList<String>();

		int i = 0;
		int innerLevel = 0;
		while (i < total) {
			char c = generics.charAt(i);

			if (c == '<') {
				innerLevel++;
			} else if (c == '>') {
				innerLevel--;
			} else if (innerLevel == 0) {
				sb.append(c);
				if (c == ';') {
					list.add(sb.toString());
					sb.setLength(0);
				}
			}
			i++;
		}

		return list.toArray(new String[list.size()]);
	}

	private static Class<?>[] _loadGenericTypes(String[] signatures)
		throws ClassNotFoundException {

		Thread currentThread = Thread.currentThread();

		ClassLoader contextClassLoader = currentThread.getContextClassLoader();

		Class<?>[] types = new Class<?>[signatures.length];

		int total = signatures.length;
		for (int i = 0; i < total; i++) {
			String className = signatures[i];

			char c = className.charAt(0);
			switch (c) {
				case 'B': types[i] = byte.class; continue;
				case 'C': types[i] = char.class; continue;
				case 'D': types[i] = double.class; continue;
				case 'F': types[i] = float.class; continue;
				case 'I': types[i] = int.class; continue;
				case 'J': types[i] = long.class; continue;
				case 'S': types[i] = short.class; continue;
				case 'Z': types[i] = boolean.class; continue;
				case 'V': types[i] = void.class; continue;
				case 'L': className =
					 className.substring(1, className.length() - 1);
				case '[':
					// uses less-known feature of class loaders for loading
					// array type using bytecode-like signature.
					className = className.replace('/', '.');
			}

			types[i] = contextClassLoader.loadClass(className);
		}

		return types;
	}

	private Class<?>[] _genericTypes;
	private boolean _initialized;
	private String _name;
	private String _signatures;
	private Class<?> _type;

}