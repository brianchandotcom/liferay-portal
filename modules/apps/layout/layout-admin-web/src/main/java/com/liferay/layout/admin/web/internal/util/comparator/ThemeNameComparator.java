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

package com.liferay.layout.admin.web.internal.util.comparator;

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