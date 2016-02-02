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

package com.liferay.portal.template.velocity;

import java.io.IOException;

import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.collections.ExtendedProperties;

/**
 * @author Shuyang Zhou
 */
public class FastExtendedProperties extends ExtendedProperties {

	public FastExtendedProperties() {
	}

	public FastExtendedProperties(ExtendedProperties extendedProperties)
		throws IOException {

		// Do not call putAll. See LPS-61927.

		//putAll(extendedProperties);

		Enumeration keys = extendedProperties.keys();

		while (keys.hasMoreElements()) {
			String key = (String)keys.nextElement();
			Object value = extendedProperties.get(key);

			addProperty(key, value);
		}
	}

	@Override
	public void clear() {
		_map.clear();
	}

	@Override
	public Object clone() {
		FastExtendedProperties fastExtendedProperties =
			(FastExtendedProperties)super.clone();

		fastExtendedProperties._map = new ConcurrentHashMap<>(_map);

		return fastExtendedProperties;
	}

	@Override
	public boolean contains(Object value) {
		return _map.containsKey(value);
	}

	@Override
	public boolean containsKey(Object key) {
		return _map.containsKey(key);
	}

	@Override
	public boolean containsValue(Object value) {
		return _map.containsValue(value);
	}

	@Override
	@SuppressWarnings("rawtypes")
	public Enumeration elements() {
		return Collections.enumeration(_map.values());
	}

	@Override
	@SuppressWarnings("rawtypes")
	public Set entrySet() {
		return _map.entrySet();
	}

	@Override
	public boolean equals(Object o) {
		return _map.equals(o);
	}

	@Override
	public Object get(Object key) {
		return _map.get(key);
	}

	@Override
	public int hashCode() {
		return _map.hashCode();
	}

	@Override
	public boolean isEmpty() {
		return _map.isEmpty();
	}

	@Override
	@SuppressWarnings("rawtypes")
	public Enumeration keys() {
		return Collections.enumeration(_map.keySet());
	}

	@Override
	@SuppressWarnings("rawtypes")
	public Set keySet() {
		return _map.keySet();
	}

	@Override
	public Object put(Object key, Object value) {
		return _map.put(key, value);
	}

	@Override
	@SuppressWarnings("rawtypes")
	public void putAll(Map t) {
		_map.putAll(t);
	}

	@Override
	public Object remove(Object key) {
		return _map.remove(key);
	}

	@Override
	public int size() {
		return _map.size();
	}

	@Override
	public String toString() {
		return _map.toString();
	}

	@Override
	@SuppressWarnings("rawtypes")
	public Collection values() {
		return _map.values();
	}

	private Map<Object, Object> _map = new ConcurrentHashMap<>();

}