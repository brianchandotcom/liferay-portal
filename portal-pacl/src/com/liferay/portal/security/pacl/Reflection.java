/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.security.pacl;

import com.liferay.portal.kernel.util.JavaDetector;

import java.lang.reflect.Method;

/**
 * @author Raymond Augé
 */
public class Reflection extends SecurityManager {

	public static Class<?> getCallerClass(int depth) {
		return _instance._getCallerClass(depth);
	}

	public static int getStackIndex(int oracle, int ibm) {
		return _instance._getStackIndex(new int[] {oracle}, new int[] {ibm});
	}

	public static int getStackIndex(int[] oracle, int[] ibm) {
		return _instance._getStackIndex(oracle, ibm);
	}

	Reflection() {
		Method[] methods = sun.reflect.Reflection.class.getMethods();

		for (Method method : methods) {
			if (method.getName().equals("isCallerSensitive")) {
				_useOldReflection = false;

				break;
			}
		}
	}

	@SuppressWarnings("deprecation")
	protected Class<?> _getCallerClass(int depth) {
		if (_useOldReflection) {

			// This operation is faster, so leave it here for legacy versions

			return sun.reflect.Reflection.getCallerClass(depth + 2);
		}

		Class<?> stack[] = getClassContext();

		// [0] Reflection._getCallerClass
		// [1] Reflection.getCallerClass

		return stack[depth + 1];
	}

	protected int _getStackIndex(int[] oracle, int[] ibm) {
		if ((oracle.length != ibm.length) && (oracle.length == 0)) {
			throw new IllegalArgumentException(
				"both arrays must be the same length and at least length 1");
		}

		int index = 0;

		// Case 1: Oracle or IBM (default case)

		if (JavaDetector.isIBM()) {
			index = ibm[0];
		}
		else {
			index = oracle[0];
		}

		if (oracle.length == 1) {
			return index;
		}

		// Case 2: JDK7

		if (JavaDetector.isJDK7()) {
			if (JavaDetector.isIBM()) {
				index = ibm[1];
			}
			else {
				index = oracle[1];
			}
		}

		if (oracle.length == 2) {
			return index;
		}

		// Case 3: JDK7 >= u25

		if (JavaDetector.isJDK7() && (!_useOldReflection)) {
			if (JavaDetector.isIBM()) {
				index = ibm[2];
			}
			else {
				index = oracle[2];
			}
		}

		return index;
	}

	private static final Reflection _instance = new Reflection();

	private boolean _useOldReflection = true;

}