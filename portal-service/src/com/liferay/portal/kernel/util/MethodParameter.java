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

		List<String> list = new ArrayList<String>();

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

		if (sb.length() > 0) {
			list.add(sb.toString());

			sb.setLength(0);
		}

		return list.toArray(new String[list.size()]);
	}

	private static Class<?>[] _loadGenericTypes(String[] signatures)
		throws ClassNotFoundException {

		Thread currentThread = Thread.currentThread();

		ClassLoader contextClassLoader = currentThread.getContextClassLoader();

		Class<?>[] types = new Class<?>[signatures.length];

		for (int i = 0; i < signatures.length; i++) {
			String className = _getClassName(signatures[i]);

			types[i] = contextClassLoader.loadClass(className);
		}

		return types;
	}

	private static String _getClassName(String signature) {
		String className = signature;
		
		char c = signature.charAt(0);
		
		if ((c == 'B') || (c == 'C') || (c == 'D') || (c == 'F') ||
			(c == 'I') || (c == 'J') || (c == 'S') || (c == 'Z')) {
		}
		else if (c == 'L') {
			className = className.substring(1, className.length() - 1);
		}
		else if (c == 'V') {
			if (signature.length() != 1) {
				throw new IllegalArgumentException("Invalid: " + signature);
			}
		}
		else if (c == '[') {
			className = className.replace('/', '.');
		}
		else {
			throw new IllegalArgumentException("Invalid: " + signature);
		}
		
		return className;
	}

	private Class<?>[] _genericTypes;
	private boolean _initialized;
	private String _name;
	private String _signatures;
	private Class<?> _type;

}