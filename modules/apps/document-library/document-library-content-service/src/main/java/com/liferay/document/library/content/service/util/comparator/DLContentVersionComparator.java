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

package com.liferay.document.library.content.service.util.comparator;

import com.liferay.document.library.content.model.DLContent;
import com.liferay.portal.kernel.util.OrderByComparator;

/**
 * @author Shuyang Zhou
 */
public class DLContentVersionComparator extends OrderByComparator<DLContent> {

	public static final String ORDER_BY_ASC = "DLContent.version ASC";

	public static final String ORDER_BY_DESC = "DLContent.version DESC";

	public static final String[] ORDER_BY_FIELDS = {"version"};

	public DLContentVersionComparator() {
		this(false);
	}

	public DLContentVersionComparator(boolean ascending) {
		_ascending = ascending;
	}

	@Override
	public int compare(DLContent content1, DLContent content2) {
		String version1 = content1.getVersion();
		String version2 = content2.getVersion();

		int value = version1.compareTo(version2);

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