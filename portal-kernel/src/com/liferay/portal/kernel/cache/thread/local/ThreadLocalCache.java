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

package com.liferay.portal.kernel.cache.thread.local;

import com.liferay.petra.string.StringBundler;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Shuyang Zhou
 */
public class ThreadLocalCache<T> {

	public ThreadLocalCache(Object id, Lifecycle lifecycle) {
		_id = id;
		_lifecycle = lifecycle;
	}

	public T get(String key) {
		if (_cache == null) {
			return null;
		}

		return _cache.get(key);
	}

	public Object getId() {
		return _id;
	}

	public Lifecycle getLifecycle() {
		return _lifecycle;
	}

	public void put(String key, T object) {
		if (_cache == null) {
			_cache = new HashMap<>();
		}

		_cache.put(key, object);
	}

	public void remove(String key) {
		if (_cache != null) {
			_cache.remove(key);
		}
	}

	public void removeAll() {
		if (_cache != null) {
			_cache.clear();
		}
	}

	@Override
	public String toString() {
		return StringBundler.concat(
			"{cache=", _cache, ", id=", _id, ", lifecycle=", _lifecycle, "}");
	}

	private Map<String, T> _cache;
	private final Object _id;
	private final Lifecycle _lifecycle;

}