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

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * @author     Shuyang Zhou
 * @deprecated As of Judson (7.1.x), replaced by {@link
 *             com.liferay.petra.lang.CentralizedThreadLocal}
 */
@Deprecated
public class CentralizedThreadLocal<T> extends ThreadLocal<T> {

	public static void clearLongLivedThreadLocals() {
		com.liferay.petra.lang.CentralizedThreadLocal.
			clearLongLivedThreadLocals();
	}

	public static void clearShortLivedThreadLocals() {
		com.liferay.petra.lang.CentralizedThreadLocal.
			clearShortLivedThreadLocals();
	}

	public static Map<CentralizedThreadLocal<?>, Object>
		getLongLivedThreadLocals() {

		return _toKernelMap(
			com.liferay.petra.lang.CentralizedThreadLocal.
				getLongLivedThreadLocals());
	}

	public static Map<CentralizedThreadLocal<?>, Object>
		getShortLivedThreadLocals() {

		return _toKernelMap(
			com.liferay.petra.lang.CentralizedThreadLocal.
				getShortLivedThreadLocals());
	}

	public static void setThreadLocals(
		Map<CentralizedThreadLocal<?>, Object> longLivedThreadLocals,
		Map<CentralizedThreadLocal<?>, Object> shortLivedThreadLocals) {

		com.liferay.petra.lang.CentralizedThreadLocal.setThreadLocals(
			_toPetraMap(longLivedThreadLocals),
			_toPetraMap(shortLivedThreadLocals));
	}

	public CentralizedThreadLocal(boolean shortLived) {
		Function<T, T> copyFunction = null;

		Class<?> clazz = getClass();

		while (clazz != CentralizedThreadLocal.class) {
			try {
				clazz.getDeclaredMethod("copy", Object.class);

				copyFunction = this::copy;

				break;
			}
			catch (ReflectiveOperationException roe) {
				clazz = clazz.getSuperclass();
			}
		}

		_centralizedThreadLocal =
			new com.liferay.petra.lang.CentralizedThreadLocal<>(
				null, CentralizedThreadLocal.this::initialValue, copyFunction,
				shortLived);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof CentralizedThreadLocal)) {
			return false;
		}

		CentralizedThreadLocal<?> centralizedThreadLocal =
			(CentralizedThreadLocal<?>)obj;

		if (_centralizedThreadLocal ==
				centralizedThreadLocal._centralizedThreadLocal) {

			return true;
		}

		return false;
	}

	@Override
	public T get() {
		return _centralizedThreadLocal.get();
	}

	@Override
	public int hashCode() {
		return _centralizedThreadLocal.hashCode();
	}

	@Override
	public void remove() {
		_centralizedThreadLocal.remove();
	}

	@Override
	public void set(T value) {
		_centralizedThreadLocal.set(value);
	}

	protected T copy(T value) {
		throw new UnsupportedOperationException();
	}

	private static Map<CentralizedThreadLocal<?>, Object> _toKernelMap(
		Map<com.liferay.petra.lang.CentralizedThreadLocal<?>, Object>
			petraMap) {

		Map<CentralizedThreadLocal<?>, Object> kernelMap = new HashMap<>();

		for (Map.Entry<com.liferay.petra.lang.CentralizedThreadLocal<?>, Object>
				entry : petraMap.entrySet()) {

			kernelMap.put(
				new CentralizedThreadLocal<>(entry.getKey()), entry.getValue());
		}

		return kernelMap;
	}

	private static Map<com.liferay.petra.lang.CentralizedThreadLocal<?>, Object>
		_toPetraMap(Map<CentralizedThreadLocal<?>, Object> kernelMap) {

		Map<com.liferay.petra.lang.CentralizedThreadLocal<?>, Object> petraMap =
			new HashMap<>();

		for (Map.Entry<CentralizedThreadLocal<?>, Object> entry :
				kernelMap.entrySet()) {

			CentralizedThreadLocal<?> centralizedThreadLocal = entry.getKey();

			petraMap.put(
				centralizedThreadLocal._centralizedThreadLocal,
				entry.getValue());
		}

		return petraMap;
	}

	private CentralizedThreadLocal(
		com.liferay.petra.lang.CentralizedThreadLocal<T>
			centralizedThreadLocal) {

		_centralizedThreadLocal = centralizedThreadLocal;
	}

	private final com.liferay.petra.lang.CentralizedThreadLocal<T>
		_centralizedThreadLocal;

}