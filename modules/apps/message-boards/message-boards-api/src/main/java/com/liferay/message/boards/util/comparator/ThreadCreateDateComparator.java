/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.message.boards.util.comparator;

import com.liferay.message.boards.model.MBThread;
import com.liferay.portal.kernel.util.DateUtil;
import com.liferay.portal.kernel.util.OrderByComparator;

/**
 * @author Javier de Arcos
 */
public class ThreadCreateDateComparator extends OrderByComparator<MBThread> {

	public static final String ORDER_BY_ASC = "MBThread.createDate ASC";

	public static final String[] ORDER_BY_CONDITION_FIELDS = {"createDate"};

	public static final String ORDER_BY_DESC = "MBThread.createDate DESC";

	public static final String[] ORDER_BY_FIELDS = {"createDate"};

	public ThreadCreateDateComparator() {
		this(false);
	}

	public ThreadCreateDateComparator(boolean ascending) {
		_ascending = ascending;
	}

	@Override
	public int compare(MBThread thread1, MBThread thread2) {
		int value = DateUtil.compareTo(
			thread1.getCreateDate(), thread2.getCreateDate());

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