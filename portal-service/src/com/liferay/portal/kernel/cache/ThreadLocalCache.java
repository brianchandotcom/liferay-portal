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

import com.liferay.portal.kernel.util.StringBundler;

import java.io.Serializable;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Shuyang Zhou
 */
public class ThreadLocalCache<T> {

	public ThreadLocalCache(Serializable name, Lifecycle lifecycle) {
		_name = name;
		_lifecycle = lifecycle;
	}

	public T get(String key) {
		if (_cache == null) {
			return null;
		}
		else {
			return _cache.get(key);
		}
	}

	public Lifecycle getLifecycle() {
		return _lifecycle;
	}

	public Serializable getName() {
		return _name;
	}

	public void put(String key, T obj) {
		if (_cache == null) {
			_cache = new HashMap<String, T>();
		}

		_cache.put(key, obj);
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
		StringBundler sb = new StringBundler(7);

		sb.append("{cache=");
		sb.append(_cache.toString());
		sb.append(", lifecycle=");
		sb.append(_lifecycle);
		sb.append(", name=");
		sb.append(_name);
		sb.append("}");

		return sb.toString();
	}

	private Map<String, T> _cache;
	private final Lifecycle _lifecycle;
	private final Serializable _name;

}