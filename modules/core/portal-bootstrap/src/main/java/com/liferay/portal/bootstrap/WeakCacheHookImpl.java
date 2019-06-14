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

package com.liferay.portal.bootstrap;

import com.liferay.petra.concurrent.ConcurrentReferenceValueHashMap;
import com.liferay.petra.memory.FinalizeManager;

import java.util.Map;
import java.util.concurrent.Callable;

import org.eclipse.osgi.framework.util.WeakCacheHook;

/**
 * @author Preston Crary
 */
public class WeakCacheHookImpl implements WeakCacheHook {

	@Override
	@SuppressWarnings("unchecked")
	public <K, V> V computeIfAbsent(K key, Callable<V> valueCallable)
		throws Exception {

		V value = (V)_weakCache.get(key);

		if (value == null) {
			value = valueCallable.call();

			V oldValue = (V)_weakCache.putIfAbsent(key, value);

			if (oldValue != null) {
				value = oldValue;
			}
		}

		return value;
	}

	private static final Map<Object, Object> _weakCache =
		new ConcurrentReferenceValueHashMap<>(
			FinalizeManager.WEAK_REFERENCE_FACTORY);

}