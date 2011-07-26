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

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Shuyang Zhou
 */
public class CentralizedThreadLocal<T> extends ThreadLocal<T> {

	public static void clearLongLivedThreadLocals() {
		_longLivedThreadLocals.remove();
	}

	public static void clearShortLivedThreadLocals() {
		_shortLivedThreadLocals.remove();
	}

	public static Map<ThreadLocal<?>, Object> getLongLivedThreadLocals() {
		return _longLivedThreadLocals.get();
	}

	public static Map<ThreadLocal<?>, Object> getShortLivedThreadLocals() {
		return _shortLivedThreadLocals.get();
	}

	public static void setThreadLocals(
		Map<ThreadLocal<?>, Object> longLivedThreadLocals,
		Map<ThreadLocal<?>, Object> shortLivedThreadLocals) {

		_longLivedThreadLocals.set(longLivedThreadLocals);
		_shortLivedThreadLocals.set(shortLivedThreadLocals);
	}

	public CentralizedThreadLocal(boolean shortLived) {
		_shortLived = shortLived;

		if (shortLived) {
			_hashCode = _shortLivedNextHasCode.getAndAdd(_HASH_INCREMENT);
		}
		else {
			_hashCode = _longLivedNextHasCode.getAndAdd(_HASH_INCREMENT);
		}
	}

	@Override
	public T get() {
		Map<ThreadLocal<?>, Object> threadLocals = _getThreadLocals();

		T value = (T)threadLocals.get(this);

		if (value == _nullHolder) {
			return null;
		}

		if (value == null) {
			value = initialValue();

			if (value == null) {
				threadLocals.put(this, _nullHolder);
			}
			else {
				threadLocals.put(this, value);
			}
		}

		return value;
	}

	@Override
	public int hashCode() {
		return _hashCode;
	}

	@Override
	public void remove() {
		Map<ThreadLocal<?>, Object> threadLocals = _getThreadLocals();

		threadLocals.remove(this);
	}

	@Override
	public void set(T value) {
		Map<ThreadLocal<?>, Object> threadLocals = _getThreadLocals();

		if (value == null) {
			threadLocals.put(this, _nullHolder);
		}
		else {
			threadLocals.put(this, value);
		}
	}

	private Map<ThreadLocal<?>, Object> _getThreadLocals() {
		if (_shortLived) {
			return _shortLivedThreadLocals.get();
		}
		else {
			return _longLivedThreadLocals.get();
		}
	}

	private static final int _HASH_INCREMENT = 0x61c88647;

	private static final AtomicInteger _longLivedNextHasCode =
		new AtomicInteger();
	private static final ThreadLocal<Map<ThreadLocal<?>, Object>>
		_longLivedThreadLocals = new MapThreadLocal();
	private static final Object _nullHolder = new Object();
	private static final AtomicInteger _shortLivedNextHasCode =
		new AtomicInteger();
	private static final ThreadLocal<Map<ThreadLocal<?>, Object>>
		_shortLivedThreadLocals = new MapThreadLocal();

	private static class MapThreadLocal
		extends ThreadLocal<Map<ThreadLocal<?>, Object>> {

		@Override
		protected Map<ThreadLocal<?>, Object> initialValue() {
			return new HashMap<ThreadLocal<?>, Object>();
		}

	}

	private final int _hashCode;
	private final boolean _shortLived;

}