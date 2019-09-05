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

package com.liferay.exportimport.kernel.staging;

import com.liferay.petra.lang.CentralizedThreadLocal;
import com.liferay.petra.lang.HashUtil;

import java.lang.reflect.Method;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * @author Raymond Augé
 * @author Shuyang Zhou
 */
public class MergeLayoutPrototypesThreadLocal {

	public static void clearMergeComplete() {
		_mergeComplete.remove();
	}

	public static boolean isInProgress() {
		return _inProgress.get();
	}

	/**
	 * @deprecated As of Judson (7.1.x), replaced by {@link
	 *             #isMergeComplete(String, Object...)}
	 */
	@Deprecated
	public static boolean isMergeComplete(Method method, Object[] arguments) {
		return isMergeComplete(method.getName(), arguments);
	}

	public static boolean isMergeComplete(
		String methodName, Object... arguments) {

		Set<MethodKey> methodKeys = _mergeComplete.get();

		return methodKeys.contains(new MethodKey(methodName, arguments));
	}

	public static void setInProgress(boolean inProgress) {
		_inProgress.set(inProgress);
	}

	/**
	 * @deprecated As of Judson (7.1.x), replaced by {@link
	 *             #setMergeComplete(String, Object...)}
	 */
	@Deprecated
	public static void setMergeComplete(Method method, Object[] arguments) {
		setMergeComplete(method.getName(), arguments);
	}

	public static void setMergeComplete(
		String methodName, Object... arguments) {

		Set<MethodKey> methodKeys = _mergeComplete.get();

		methodKeys.add(new MethodKey(methodName, arguments));

		setInProgress(false);
	}

	private static final ThreadLocal<Boolean> _inProgress =
		new CentralizedThreadLocal<>(
			MergeLayoutPrototypesThreadLocal.class + "._inProgress",
			() -> Boolean.FALSE);
	private static final ThreadLocal<Set<MethodKey>> _mergeComplete =
		new CentralizedThreadLocal<>(
			MergeLayoutPrototypesThreadLocal.class + "._mergeComplete",
			HashSet::new);

	private static class MethodKey {

		public MethodKey(String methodName, Object[] arguments) {
			_methodName = methodName;
			_arguments = arguments;
		}

		@Override
		public boolean equals(Object obj) {
			MethodKey methodKey = (MethodKey)obj;

			if (Objects.equals(_methodName, methodKey._methodName) &&
				Arrays.equals(_arguments, methodKey._arguments)) {

				return true;
			}

			return false;
		}

		@Override
		public int hashCode() {
			int hashCode = _methodName.hashCode();

			if (_arguments != null) {
				for (Object obj : _arguments) {
					if (obj == null) {
						hashCode = HashUtil.hash(hashCode, 0);
					}
					else {
						hashCode = HashUtil.hash(hashCode, obj.hashCode());
					}
				}
			}

			return hashCode;
		}

		private final Object[] _arguments;
		private final String _methodName;

	}

}