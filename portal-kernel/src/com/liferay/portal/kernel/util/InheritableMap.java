/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.util;

import com.liferay.petra.string.StringBundler;
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

		return StringBundler.concat(
			string.substring(0, string.length() - 1),
			StringPool.COMMA_AND_SPACE, parentString.substring(1));
	}

	private Map<K, V> _parentMap;

}