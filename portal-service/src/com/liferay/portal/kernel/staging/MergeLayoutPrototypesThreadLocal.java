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

package com.liferay.portal.kernel.staging;

import com.liferay.portal.kernel.util.AutoResetThreadLocal;
import com.liferay.portal.kernel.util.StringBundler;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Raymond Augé
 */
public class MergeLayoutPrototypesThreadLocal {

	public static boolean isInProgress() {
		return _inProgress.get();
	}

	public static boolean isMergeComplete(
		String methodName, Object[] arguments, Class<?>[] parameterTypes) {

		Set<String> methodKeys = _mergeComplete.get();

		String methodKey = _buildMethodKey(
			methodName, arguments, parameterTypes);

		return methodKeys.contains(methodKey);
	}

	public static void setInProgress(boolean inProgress) {
		_inProgress.set(inProgress);
	}

	public static void setMergeComplete(
		String methodName, Object[] arguments, Class<?>[] parameterTypes) {

		Set<String> methodKeys = _mergeComplete.get();

		String methodKey = _buildMethodKey(
			methodName, arguments, parameterTypes);

		methodKeys.add(methodKey);

		setInProgress(false);
	}

	private static String _buildMethodKey(
		String methodName, Object[] arguments, Class<?>[] parameterTypes) {

		if ((arguments == null) || (arguments.length == 0)) {
			return methodName;
		}

		StringBundler sb = new StringBundler(arguments.length * 2 + 1);

		sb.append(methodName);

		for (int i = 0; i < arguments.length; i++) {
			sb.append(parameterTypes[0].getClass().getName());

			sb.append(arguments.toString());
		}

		return sb.toString();
	}

	private static ThreadLocal<Boolean> _inProgress =
		new AutoResetThreadLocal<Boolean>(
			MergeLayoutPrototypesThreadLocal.class + "._inProgress", false);
	private static ThreadLocal<Set<String>> _mergeComplete =
		new AutoResetThreadLocal<Set<String>>(
			MergeLayoutPrototypesThreadLocal.class + "._mergeComplete",
			new HashSet<String>());

}