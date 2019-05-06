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

package com.liferay.message.boards.util.comparator;

import com.liferay.message.boards.model.MBCategory;
import com.liferay.message.boards.model.MBThread;
import com.liferay.portal.kernel.util.DateUtil;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.util.Date;

/**
 * @author     Adolfo Pérez
 * @deprecated As of Mueller (7.2.x), replaced by {@link MBObjectsComparator}
 * @review
 */
@Deprecated
public class MBObjectsModifiedDateComparator<T> extends OrderByComparator<T> {

	public static final String ORDER_BY_ASC =
		"modelCategory ASC, priority DESC, modifiedDate ASC, name ASC, " +
			"modelId ASC";

	public static final String ORDER_BY_DESC =
		"modelCategory ASC, priority DESC, modifiedDate DESC, name ASC, " +
			"modelId ASC";

	public static final String[] ORDER_BY_FIELDS = {
		"modelCategory", "priority", "modifiedDate", "name", "modelId"
	};

	public MBObjectsModifiedDateComparator() {
		this(false);
	}

	public MBObjectsModifiedDateComparator(boolean ascending) {
		_ascending = ascending;
	}

	@Override
	public int compare(T t1, T t2) {
		Date modifiedDate1 = getMBObjectsModifiedDate(t1);
		Date modifiedDate2 = getMBObjectsModifiedDate(t2);

		int value = DateUtil.compareTo(modifiedDate1, modifiedDate2);

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

	protected Date getMBObjectsModifiedDate(Object obj) {
		if (obj instanceof MBCategory) {
			MBCategory mbCategory = (MBCategory)obj;

			return mbCategory.getModifiedDate();
		}

		if (obj instanceof MBThread) {
			MBThread mbThread = (MBThread)obj;

			return mbThread.getModifiedDate();
		}

		return null;
	}

	private final boolean _ascending;

}