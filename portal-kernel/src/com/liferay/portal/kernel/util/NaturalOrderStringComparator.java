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

import java.io.Serializable;

import java.util.Comparator;

/**
 * @author Hugo Huijser
 */
public class NaturalOrderStringComparator
	implements Comparator<String>, Serializable {

	public NaturalOrderStringComparator() {
		this(true, false);
	}

	public NaturalOrderStringComparator(
		boolean ascending, boolean caseSensitive) {

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

		int value = 0;

		int i1 = 0;
		int i2 = 0;

		int length1 = s1.length();
		int length2 = s2.length();

		while ((i1 < length1) && (i2 < length2)) {
			char c1 = s1.charAt(i1);
			char c2 = s2.charAt(i2);

			if (Validator.isDigit(c1) && Validator.isDigit(c2)) {
				String leadingDigitsAsString1 = StringUtil.extractLeadingDigits(
					s1.substring(i1));
				String leadingDigitsAsString2 = StringUtil.extractLeadingDigits(
					s2.substring(i2));

				int leadingNumber1 = GetterUtil.getInteger(
					leadingDigitsAsString1);
				int leadingNumber2 = GetterUtil.getInteger(
					leadingDigitsAsString2);

				if (leadingNumber1 != leadingNumber2) {
					value = leadingNumber1 - leadingNumber2;

					break;
				}

				i1 += leadingDigitsAsString1.length();
				i2 += leadingDigitsAsString2.length();

				continue;
			}

			if (isCheckSpecialCharacters() && Validator.isAscii(c1) &&
				Validator.isAscii(c2)) {

				boolean digitOrLetter1 = _isDigitOrLetter(c1);
				boolean digitOrLetter2 = _isDigitOrLetter(c2);

				if (digitOrLetter1 ^ digitOrLetter2) {
					if (digitOrLetter1) {
						value = 1;
					}
					else {
						value = -1;
					}

					break;
				}
			}

			if (c1 == c2) {
				i1++;
				i2++;

				continue;
			}

			if (_caseSensitive) {
				value = c1 - c2;

				break;
			}
			else {
				char c1UpperCase = Character.toUpperCase(c1);
				char c2UpperCase = Character.toUpperCase(c2);

				if (c1UpperCase == c2UpperCase) {
					i1++;
					i2++;

					continue;
				}

				value = c1UpperCase - c2UpperCase;

				break;
			}
		}

		if ((value == 0) && (length1 != length2)) {
			if ((length1 == i1) && (length2 == i2)) {
				value = length2 - length1;
			}
			else {
				value = length1 - length2;
			}
		}

		if (_ascending) {
			return value;
		}

		return -value;
	}

	protected boolean isCheckSpecialCharacters() {
		return true;
	}

	private boolean _isDigitOrLetter(char c) {
		if (Validator.isChar(c) || Validator.isDigit(c)) {
			return true;
		}

		return false;
	}

	private final boolean _ascending;
	private final boolean _caseSensitive;

}