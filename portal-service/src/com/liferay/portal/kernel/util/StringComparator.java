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

import java.io.Serializable;

import java.util.Comparator;

/**
 * @author Brian Wing Shun Chan
 */
public class StringComparator implements Comparator<String>, Serializable {

	public StringComparator() {
		this(true, false);
	}

	public StringComparator(boolean ascending, boolean caseSensitive) {
		_ascending = ascending;
		_caseSensitive = caseSensitive;
	}

	@Override
	public int compare(String s1, String s2) {
		if (s1 == null) {
			s1 = StringPool.BLANK;
		}

		if (s2 == null) {
			s2 = StringPool.BLANK;
		}

		if (!_ascending) {
			String temp = s1;

			s1 = s2;
			s2 = temp;
		}

		if (_caseSensitive) {
			return s1.compareTo(s2);
		}
		else {
			return s1.compareToIgnoreCase(s2);
		}
	}

	private final boolean _ascending;
	private final boolean _caseSensitive;

}