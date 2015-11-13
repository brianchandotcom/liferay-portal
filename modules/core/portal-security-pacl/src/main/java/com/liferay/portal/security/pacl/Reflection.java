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

package com.liferay.portal.security.pacl;

import com.liferay.portal.kernel.util.JavaDetector;

/**
 * <p>
 * See https://issues.liferay.com/browse/LPS-38327.
 * </p>
 *
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

	private Reflection() {
		boolean useOldReflection = true;

		try {
			sun.reflect.Reflection.getCallerClass(1);
		}
		catch (UnsupportedOperationException uoe) {
			useOldReflection = false;
		}

		_useOldReflection = useOldReflection;
	}

	@SuppressWarnings("deprecation")
	private Class<?> _getCallerClass(int depth) {
		if (_useOldReflection) {

			// This operation is faster, so leave it here for legacy versions

			return sun.reflect.Reflection.getCallerClass(depth + 2);
		}

		Class<?>[] callerClasses = getClassContext();

		// [0] Reflection._getCallerClass
		// [1] Reflection.getCallerClass

		return callerClasses[depth + 1];
	}

	private int _getStackIndex(int[] oracle, int[] ibm) {
		if ((oracle.length != ibm.length) && (oracle.length == 0)) {
			throw new IllegalArgumentException(
				"Both arrays must not be empty and have the same length");
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
			return index + _STACK_OFFSET;
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
			return index + _STACK_OFFSET;
		}

		// Case 3: JDK7 >= u25

		if (JavaDetector.isJDK7() && !_useOldReflection) {
			if (JavaDetector.isIBM()) {
				index = ibm[2];
			}
			else {
				index = oracle[2];
			}
		}

		return index + _STACK_OFFSET;
	}

	private static final int _STACK_OFFSET = 10;

	private static final Reflection _instance = new Reflection();

	private final boolean _useOldReflection;

}