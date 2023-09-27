/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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