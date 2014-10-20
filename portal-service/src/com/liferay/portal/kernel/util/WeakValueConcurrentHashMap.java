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

import com.liferay.portal.kernel.concurrent.ConcurrentReferenceValueHashMap;
import com.liferay.portal.kernel.memory.FinalizeManager;

import java.util.Map;

/**
 * @author     Shuyang Zhou
 * @deprecated As of 7.0.0, replaced by {@link
 *             com.liferay.portal.kernel.concurrent.ConcurrentReferenceValueHashMap}
 */
@Deprecated
public class WeakValueConcurrentHashMap<K, V>
	extends ConcurrentReferenceValueHashMap<K, V> {

	public WeakValueConcurrentHashMap() {
		super(FinalizeManager.WEAK_REFERENCE_FACTORY);
	}

	public WeakValueConcurrentHashMap(int initialCapacity) {
		super(initialCapacity, FinalizeManager.WEAK_REFERENCE_FACTORY);
	}

	public WeakValueConcurrentHashMap(
		int initialCapacity, float loadFactor, int concurrencyLevel) {

		super(
			initialCapacity, loadFactor, concurrencyLevel,
			FinalizeManager.WEAK_REFERENCE_FACTORY);
	}

	public WeakValueConcurrentHashMap(Map<? extends K, ? extends V> map) {
		super(map, FinalizeManager.WEAK_REFERENCE_FACTORY);
	}

}