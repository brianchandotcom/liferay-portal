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

import com.liferay.petra.string.StringPool;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Michael Young
 * @author Connor McKay
 * @author Shuyang Zhou
 */
public class InheritableMap<K, V> extends HashMap<K, V> {

	public InheritableMap() {
	}

	public InheritableMap(Map<? extends K, ? extends V> map) {
		super(map);
	}

	@Override
	public void clear() {
		super.clear();

		_parentMap = null;
	}

	@Override
	public boolean containsKey(Object key) {
		if ((_parentMap != null) && _parentMap.containsKey(key)) {
			return true;
		}

		return super.containsKey(key);
	}

	@Override
	public boolean containsValue(Object value) {
		if ((_parentMap != null) && _parentMap.containsValue(value)) {
			return true;
		}

		return super.containsValue(value);
	}

	@Override
	public V get(Object key) {
		V value = super.get(key);

		if (value != null) {
			return value;
		}
		else if (_parentMap != null) {
			return _parentMap.get(key);
		}

		return null;
	}

	public Map<K, V> getParentMap() {
		return _parentMap;
	}

	@Override
	public V remove(Object key) {
		V value = super.remove(key);

		if (value != null) {
			return value;
		}
		else if (_parentMap != null) {
			return _parentMap.remove(key);
		}

		return null;
	}

	public void setParentMap(Map<? extends K, ? extends V> parentMap) {
		_parentMap = (Map<K, V>)parentMap;
	}

	@Override
	public String toString() {
		String string = super.toString();

		String parentString = "{}";

		if (_parentMap != null) {
			parentString = _parentMap.toString();
		}

		if (string.length() <= 2) {
			return parentString;
		}

		StringBundler sb = new StringBundler(3);

		sb.append(string.substring(0, string.length() - 1));
		sb.append(StringPool.COMMA_AND_SPACE);
		sb.append(parentString.substring(1));

		return sb.toString();
	}

	private Map<K, V> _parentMap;

}