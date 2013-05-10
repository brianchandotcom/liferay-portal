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

	@Override
	public int compare(String s1, String s2) {
		if (s1 == null) {
			s1 = StringPool.BLANK;
		}

		if (s2 == null) {
			s2 = StringPool.BLANK;
		}

		int startsWithWeight = StringUtil.startsWithWeight(s1, s2);

		boolean lastMatchingCharIsDigit = false;

		if ((startsWithWeight > 0) &&
			Validator.isDigit(s1.charAt(startsWithWeight - 1))) {

			lastMatchingCharIsDigit = true;
		}

		s1 = s1.substring(startsWithWeight);
		s2 = s2.substring(startsWithWeight);

		String leadingDigits1 = StringUtil.extractLeadingDigits(s1);
		String leadingDigits2 = StringUtil.extractLeadingDigits(s2);

		int value = 0;

		if ((lastMatchingCharIsDigit &&
			 (Validator.isNotNull(leadingDigits1) ||
			  Validator.isNotNull(leadingDigits2))) ||
			(Validator.isNotNull(leadingDigits1) &&
			 Validator.isNotNull(leadingDigits2))) {

			if (leadingDigits1.length() != leadingDigits2.length()) {
				value = leadingDigits1.length() - leadingDigits2.length();
			}
			else {
				int i1 = GetterUtil.getInteger(leadingDigits1);
				int i2 = GetterUtil.getInteger(leadingDigits2);

				value = i1 - i2;
			}
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