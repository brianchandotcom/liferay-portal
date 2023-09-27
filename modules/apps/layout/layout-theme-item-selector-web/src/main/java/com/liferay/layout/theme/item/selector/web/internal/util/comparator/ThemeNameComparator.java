/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.theme.item.selector.web.internal.util.comparator;

import com.liferay.portal.kernel.model.Theme;

import java.io.Serializable;

import java.util.Comparator;

/**
 * @author Eudaldo Alonso
 */
public class ThemeNameComparator implements Comparator<Theme>, Serializable {

	public ThemeNameComparator(boolean ascending) {
		_ascending = ascending;
	}

	@Override
	public int compare(Theme theme1, Theme theme2) {
		String name1 = theme1.getName();
		String name2 = theme2.getName();

		int value = name1.compareTo(name2);

		if (_ascending) {
			return value;
		}

		return -value;
	}

	private final boolean _ascending;

}