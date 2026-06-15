/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.exportimport.kernel.staging;

import com.liferay.petra.lang.CentralizedThreadLocal;
import com.liferay.petra.lang.HashUtil;

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

	public static boolean isMergeComplete(
		String methodName, Object... arguments) {

		Set<MethodKey> methodKeys = _mergeComplete.get();

		return methodKeys.contains(new MethodKey(methodName, arguments));
	}

	public static void setInProgress(boolean inProgress) {
		_inProgress.set(inProgress);
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

		@Override
		public boolean equals(Object object) {
			if (this == object) {
				return true;
			}

			if (!(object instanceof MethodKey)) {
				return false;
			}

			MethodKey methodKey = (MethodKey)object;

			if (Objects.equals(_methodName, methodKey._methodName) &&
				Arrays.equals(_arguments, methodKey._arguments)) {

				return true;
			}

			return false;
		}

		@Override
		public int hashCode() {
			int hashCode = HashUtil.hash(0, _methodName);

			return HashUtil.hash(hashCode, Arrays.hashCode(_arguments));
		}

		private MethodKey(String methodName, Object... arguments) {
			_methodName = methodName;
			_arguments = arguments;
		}

		private final Object[] _arguments;
		private final String _methodName;

	}

}