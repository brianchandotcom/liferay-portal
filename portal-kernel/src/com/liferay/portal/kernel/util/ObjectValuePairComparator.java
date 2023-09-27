/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

			return -key1.compareTo((K)key2);
		}

		Comparable<V> value1 = (Comparable<V>)ovp1.getValue();
		Comparable<V> value2 = (Comparable<V>)ovp2.getValue();

		if (_ascending) {
			return value1.compareTo((V)value2);
		}

		return -value1.compareTo((V)value2);
	}

	private final boolean _ascending;
	private final boolean _byKey;

}