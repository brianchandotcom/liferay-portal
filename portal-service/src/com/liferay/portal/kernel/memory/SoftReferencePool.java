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

package com.liferay.portal.kernel.memory;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * @author Shuyang Zhou
 */
public class SoftReferencePool<V, P> {

	public SoftReferencePool(PoolAction<V, P> poolAction) {
		this(poolAction, DEFAULT_IDLE_SIZE);
	}

	public SoftReferencePool(PoolAction<V, P> poolAction, int maxIdleSize) {
		_poolAction = poolAction;
		_maxIdleSize = maxIdleSize;
	}

	public V borrowObject(P parameter) {
		while (true) {
			SoftReference<? extends V> valueReference = _pool.poll();

			if (valueReference == null) {
				return _poolAction.onCreate(parameter);
			}

			V value = valueReference.get();

			if (value != null) {
				return _poolAction.onBorrow(value, parameter);
			}
		}
	}

	public void returnObject(V value) {
		if (_pool.size() < _maxIdleSize) {
			SoftReference<V> valueReference = new SoftReference<V>(value,
				_referenceQueue);

			_poolAction.onReturn(value);

			_pool.offer(valueReference);
		}
		else {
			while (_pool.size() > _maxIdleSize) {
				_pool.poll();
			}
		}

		SoftReference<? extends V> reference = null;

		while ((reference = (SoftReference<? extends V>)_referenceQueue.poll())
			!= null) {
			_pool.remove(reference);
		}
	}

	public static final int DEFAULT_IDLE_SIZE = 8;

	private final int _maxIdleSize;
	private final Queue<SoftReference<? extends V>> _pool =
		new ConcurrentLinkedQueue<SoftReference<? extends V>>();
	private final PoolAction<V, P> _poolAction;
	private final ReferenceQueue<V> _referenceQueue = new ReferenceQueue<V>();

}