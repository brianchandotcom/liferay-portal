/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.util;

import java.util.Comparator;

/**
 * @author Brian Wing Shun Chan
 */
public class KeyValuePairComparator implements Comparator<KeyValuePair> {

	public KeyValuePairComparator() {
		this(true);
	}

	public KeyValuePairComparator(boolean ascending) {
		this(true, ascending);
	}

	public KeyValuePairComparator(boolean byKey, boolean ascending) {
		_byKey = byKey;
		_ascending = ascending;
	}

	@Override
	public int compare(KeyValuePair kvp1, KeyValuePair kvp2) {
		if (_byKey) {
			String key1 = kvp1.getKey();
			String key2 = kvp2.getKey();

			if (_ascending) {
				return key1.compareTo(key2);
			}

			return -key1.compareTo(key2);
		}

		String value1 = kvp1.getValue();
		String value2 = kvp2.getValue();

		if (_ascending) {
			return value1.compareTo(value2);
		}

		return -value1.compareTo(value2);
	}

	private final boolean _ascending;
	private final boolean _byKey;

}