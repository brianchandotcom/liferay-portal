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

import java.util.Comparator;

/**
 * @author Brian Wing Shun Chan
 */
public class ObjectValuePairComparator<K, V>
	implements Comparator<ObjectValuePair<K, V>> {

	public ObjectValuePairComparator() {
		this(true);
	}

	public ObjectValuePairComparator(boolean ascending) {
		this(true, ascending);
	}

	public ObjectValuePairComparator(boolean byKey, boolean ascending) {
		_byKey = byKey;
		_ascending = ascending;
	}

	@Override
	public int compare(ObjectValuePair<K, V> ovp1, ObjectValuePair<K, V> ovp2) {
		if (_byKey) {
			Comparable<K> key1 = (Comparable<K>)ovp1.getKey();
			Comparable<K> key2 = (Comparable<K>)ovp2.getKey();

			if (_ascending) {
				return key1.compareTo((K)key2);
			}

			return -(key1.compareTo((K)key2));
		}
		else {
			Comparable<V> value1 = (Comparable<V>)ovp1.getValue();
			Comparable<V> value2 = (Comparable<V>)ovp2.getValue();

			if (_ascending) {
				return value1.compareTo((V)value2);
			}

			return -(value1.compareTo((V)value2));
		}
	}

	private final boolean _ascending;
	private final boolean _byKey;

}