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

package com.liferay.registry.internal;

import java.util.Collections;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.Map;

/**
 * @author Raymond Augé
 */
public class MapWrapper extends Dictionary<String, Object> {

	public MapWrapper(Map<String, Object> map) {
		_map = map;
	}

	@Override
	public Enumeration<Object> elements() {
		if (_map == null) {
			return Collections.enumeration(Collections.emptyList());
		}

		return Collections.enumeration(_map.values());
	}

	@Override
	public Object get(Object key) {
		if (_map == null) {
			return null;
		}

		return _map.get(key);
	}

	@Override
	public boolean isEmpty() {
		if (_map == null) {
			return true;
		}

		return _map.isEmpty();
	}

	@Override
	public Enumeration<String> keys() {
		if (_map == null) {
			return Collections.enumeration(Collections.<String>emptyList());
		}

		return Collections.enumeration(_map.keySet());
	}

	@Override
	public Object put(String key, Object value) {
		if (_map == null) {
			return null;
		}

		return _map.put(key, value);
	}

	@Override
	public Object remove(Object key) {
		if (_map == null) {
			return null;
		}

		return _map.remove(key);
	}

	@Override
	public int size() {
		if (_map == null) {
			return 0;
		}

		return _map.size();
	}

	private Map<String, Object> _map;

}