/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.util;

import com.liferay.petra.string.StringPool;

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

		return s1.compareToIgnoreCase(s2);
	}

	private final boolean _ascending;
	private final boolean _caseSensitive;

}