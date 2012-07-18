/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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
 * @author Hugo Huijser
 */
public class NumericalStringComparator
	implements Comparator<String>, Serializable {

	public NumericalStringComparator() {
		this(true, false);
	}

	public NumericalStringComparator(boolean ascending, boolean caseSensitive) {
		_ascending = ascending;
		_caseSensitive = caseSensitive;
	}

	public int compare(String s1, String s2) {
		if (s1 == null) {
			s1 = StringPool.BLANK;
		}

		if (s2 == null) {
			s2 = StringPool.BLANK;
		}

		int value = 0;

		int i1 = GetterUtil.getInteger(StringUtil.extractLeadingDigits(s1), -1);
		int i2 = GetterUtil.getInteger(StringUtil.extractLeadingDigits(s2), -1);

		if ((i1 != -1) && (i2 != -1) && (i1 != i2)) {
			value = i1 - i2;
		}
		else {
			if (_caseSensitive) {
				value = s1.compareTo(s2);
			}
			else {
				value = s1.compareToIgnoreCase(s2);
			}
		}

		if (_ascending) {
			return value;
		}
		else {
			return -value;
		}
	}

	private boolean _ascending;
	private boolean _caseSensitive;

}