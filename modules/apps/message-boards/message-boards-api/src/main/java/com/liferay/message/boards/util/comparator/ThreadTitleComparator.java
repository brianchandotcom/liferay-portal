/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.message.boards.util.comparator;

import com.liferay.message.boards.model.MBThread;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringUtil;

/**
 * @author Sergio González
 */
public class ThreadTitleComparator<T> extends OrderByComparator<T> {

	public static final String ORDER_BY_ASC =
		"priority DESC, title ASC, modifiedDate DESC";

	public static final String ORDER_BY_DESC =
		"priority DESC, title DESC, modifiedDate DESC";

	public static final String[] ORDER_BY_FIELDS = {
		"priority", "title", "modifiedDate"
	};

	public ThreadTitleComparator() {
		this(false);
	}

	public ThreadTitleComparator(boolean ascending) {
		_ascending = ascending;
	}

	@Override
	public int compare(T t1, T t2) {
		String name1 = StringUtil.toLowerCase(getThreadTitle(t1));
		String name2 = StringUtil.toLowerCase(getThreadTitle(t2));

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

	protected String getThreadTitle(Object object) {
		if (object instanceof MBThread) {
			MBThread mbThread = (MBThread)object;

			return mbThread.getTitle();
		}

		return null;
	}

	private final boolean _ascending;

}