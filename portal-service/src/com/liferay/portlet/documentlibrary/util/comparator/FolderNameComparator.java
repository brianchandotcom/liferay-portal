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

package com.liferay.portlet.documentlibrary.util.comparator;

import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.util.OrderByComparator;

/**
 * @author Brian Wing Shun Chan
 * @author Alexander Chow
 */
public class FolderNameComparator extends OrderByComparator {

	public static final String ORDER_BY_ASC = "name ASC";

	public static final String ORDER_BY_DESC = "name DESC";

	public static final String[] ORDER_BY_FIELDS = {"name"};

	public FolderNameComparator() {
		this(false);
	}

	public FolderNameComparator(boolean ascending) {
		_ascending = ascending;
	}

	@Override
	public int compare(Object folder1, Object folder2) {
		String name1 = ((Folder)folder1).getName();
		String name2 = ((Folder)folder2).getName();

		int value = name1.compareToIgnoreCase(name2);

		if (_ascending) {
			return value;
		}
		else {
			return -value;
		}
	}

	@Override
	public String getOrderBy() {
		if (_ascending) {
			return ORDER_BY_ASC;
		}
		else {
			return ORDER_BY_DESC;
		}
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