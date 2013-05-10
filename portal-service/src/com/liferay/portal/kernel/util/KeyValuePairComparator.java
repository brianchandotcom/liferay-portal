/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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
			else {
				return -(key1.compareTo(key2));
			}
		}
		else {
			String value1 = kvp1.getValue();
			String value2 = kvp2.getValue();

			if (_ascending) {
				return value1.compareTo(value2);
			}
			else {
				return -(value1.compareTo(value2));
			}
		}
	}

	private boolean _ascending;
	private boolean _byKey;

}