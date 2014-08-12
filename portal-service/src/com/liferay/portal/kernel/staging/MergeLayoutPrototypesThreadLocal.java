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

package com.liferay.portal.kernel.staging;

import com.liferay.portal.kernel.util.AutoResetThreadLocal;
import com.liferay.portal.kernel.util.HashUtil;
import com.liferay.portal.kernel.util.Validator;

import java.lang.reflect.Method;

import java.util.Arrays;
import java.util.HashSet;
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

	public static boolean isMergeComplete(Method method, Object[] arguments) {
		Set<MethodKey> methodKeys = _mergeComplete.get();

		return methodKeys.contains(new MethodKey(method, arguments));
	}

	public static void setInProgress(boolean inProgress) {
		_inProgress.set(inProgress);
	}

	public static void setMergeComplete(Method method, Object[] arguments) {
		Set<MethodKey> methodKeys = _mergeComplete.get();

		methodKeys.add(new MethodKey(method, arguments));

		setInProgress(false);
	}

	private static final ThreadLocal<Boolean> _inProgress =
		new AutoResetThreadLocal<Boolean>(
			MergeLayoutPrototypesThreadLocal.class + "._inProgress", false);
	private static final ThreadLocal<Set<MethodKey>> _mergeComplete =
		new AutoResetThreadLocal<Set<MethodKey>>(
			MergeLayoutPrototypesThreadLocal.class + "._mergeComplete",
			new HashSet<MethodKey>());

	private static class MethodKey {

		public MethodKey(Method method, Object[] arguments) {
			_method = method;
			_arguments = arguments;
		}

		@Override
		public boolean equals(Object obj) {
			MethodKey methodKey = (MethodKey)obj;

			if (Validator.equals(_method, methodKey._method) &&
				Arrays.equals(_arguments, methodKey._arguments)) {

				return true;
			}

			return false;
		}

		@Override
		public int hashCode() {
			int hashCode = _method.hashCode();

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
		private final Method _method;

	}

}