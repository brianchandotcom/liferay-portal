/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.message.boards.util.comparator;

import com.liferay.message.boards.model.MBCategory;
import com.liferay.message.boards.model.MBThread;
import com.liferay.portal.kernel.util.DateUtil;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.util.Date;

/**
 * @author Adolfo Pérez
 */
public class MBObjectsComparator<T> extends OrderByComparator<T> {

	public static final String ORDER_BY_ASC =
		"modelCategory ASC, priority DESC, modifiedDate DESC, name ASC, " +
			"modelId ASC";

	public static final String ORDER_BY_DESC =
		"modelCategory ASC, priority DESC, modifiedDate ASC, name DESC, " +
			"modelId ASC";

	public static final String[] ORDER_BY_FIELDS = {
		"modelCategory", "priority", "modifiedDate", "name", "modelId"
	};

	public MBObjectsComparator() {
		this(false);
	}

	public MBObjectsComparator(boolean ascending) {
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

	protected Date getMBObjectsModifiedDate(Object object) {
		if (object instanceof MBCategory) {
			MBCategory mbCategory = (MBCategory)object;

			return mbCategory.getModifiedDate();
		}

		if (object instanceof MBThread) {
			MBThread mbThread = (MBThread)object;

			return mbThread.getModifiedDate();
		}

		return null;
	}

	private final boolean _ascending;

}