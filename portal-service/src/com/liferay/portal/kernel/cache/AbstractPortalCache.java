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

package com.liferay.portal.kernel.cache;

import java.io.Serializable;

/**
 * @author Tina Tian
 */
public abstract class AbstractPortalCache<K extends Serializable, V>
	implements PortalCache<K, V> {

	@Override
	public V get(K key) {
		if (key == null) {
			throw new NullPointerException("Key is null");
		}

		return doGet(key);
	}

	@Override
	public void put(K key, V value) {
		put(key, value, DEFAULT_TIME_TO_LIVE, false);
	}

	@Override
	public void put(K key, V value, int timeToLive) {
		put(key, value, timeToLive, false);
	}

	@Override
	public void putQuiet(K key, V value) {
		put(key, value, DEFAULT_TIME_TO_LIVE, true);
	}

	@Override
	public void putQuiet(K key, V value, int timeToLive) {
		put(key, value, timeToLive, true);
	}

	@Override
	public void remove(K key) {
		if (key == null) {
			throw new NullPointerException("Key is null");
		}

		doRemove(key);
	}

	protected abstract V doGet(K key);

	protected abstract void doPut(
		K key, V value, int timeToLive, boolean quiet);

	protected abstract void doRemove(K key);

	protected void put(K key, V value, int timeToLive, boolean quiet) {
		if (key == null) {
			throw new NullPointerException("Key is null");
		}

		if (value == null) {
			throw new NullPointerException("Value is null");
		}

		if ((timeToLive != DEFAULT_TIME_TO_LIVE) && (timeToLive < 0)) {
			throw new IllegalArgumentException("Time to live is negative");
		}

		doPut(key, value, timeToLive, quiet);
	}

}