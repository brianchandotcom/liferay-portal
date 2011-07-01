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

	public static final int DEFAULT_IDLE_SIZE = 8;

	public SoftReferencePool(PoolAction<V, P> poolAction) {
		this(poolAction, DEFAULT_IDLE_SIZE);
	}

	public SoftReferencePool(PoolAction<V, P> poolAction, int maxIdleSize) {
		_poolAction = poolAction;
		_maxIdleSize = maxIdleSize;
	}

	public V borrowObject(P parameter) {
		while (true) {
			SoftReference<? extends V> softReference = _softReferences.poll();

			if (softReference == null) {
				return _poolAction.onCreate(parameter);
			}

			V value = softReference.get();

			if (value != null) {
				return _poolAction.onBorrow(value, parameter);
			}
		}
	}

	public void returnObject(V value) {
		if (_softReferences.size() < _maxIdleSize) {
			SoftReference<V> softReference = new SoftReference<V>(
				value, _referenceQueue);

			_poolAction.onReturn(value);

			_softReferences.offer(softReference);
		}
		else {
			while (_softReferences.size() > _maxIdleSize) {
				_softReferences.poll();
			}
		}

		SoftReference<? extends V> softReference = null;

		while (true) {
			softReference = (SoftReference<? extends V>)_referenceQueue.poll();

			if (softReference == null) {
				break;
			}

			_softReferences.remove(softReference);
		}
	}

	private int _maxIdleSize;
	private PoolAction<V, P> _poolAction;
	private ReferenceQueue<V> _referenceQueue = new ReferenceQueue<V>();
	private Queue<SoftReference<? extends V>> _softReferences =
		new ConcurrentLinkedQueue<SoftReference<? extends V>>();

}