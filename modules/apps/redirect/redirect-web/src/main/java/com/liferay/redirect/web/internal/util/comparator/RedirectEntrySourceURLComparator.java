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

package com.liferay.redirect.web.internal.util.comparator;

import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.redirect.model.RedirectEntry;

/**
 * @author Alejandro Tardín
 */
public class RedirectEntrySourceURLComparator
	extends OrderByComparator<RedirectEntry> {

	public static final String ORDER_BY_ASC = "RedirectEntry.sourceURL ASC";

	public static final String ORDER_BY_DESC = "RedirectEntry.sourceURL DESC";

	public static final String[] ORDER_BY_FIELDS = {"sourceURL"};

	public RedirectEntrySourceURLComparator(boolean ascending) {
		_ascending = ascending;
	}

	@Override
	public int compare(
		RedirectEntry redirectEntry1, RedirectEntry redirectEntry2) {

		String name1 = redirectEntry1.getSourceURL();
		String name2 = redirectEntry2.getSourceURL();

		int value = name1.compareTo(name2);

		if (_ascending) {
			return value;
		}

		return -value;
	}

	@Override
	public String getOrderBy() {
		if (_ascending) {
			return ORDER_BY_ASC;
		}

		return ORDER_BY_DESC;
	}

	@Override
	public String[] getOrderByFields() {
		return ORDER_BY_FIELDS;
	}

	@Override
	public boolean isAscending() {
		return _ascending;
	}

	private final boolean _ascending;

}