/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.knowledge.base.util.comparator;

import com.liferay.knowledge.base.model.KBComment;
import com.liferay.portal.kernel.util.OrderByComparator;

/**
 * @author Roberto Díaz
 */
public class KBCommentUserNameComparator extends OrderByComparator<KBComment> {

	public static final String ORDER_BY_ASC = "KBComment.userName ASC";

	public static final String ORDER_BY_DESC = "KBComment.userName DESC";

	public static final String[] ORDER_BY_FIELDS = {"userName"};

	public KBCommentUserNameComparator() {
		this(false);
	}

	public KBCommentUserNameComparator(boolean ascending) {
		_ascending = ascending;
	}

	@Override
	public int compare(KBComment kbComment1, KBComment kbComment2) {
		String userName1 = kbComment1.getUserName();
		String userName2 = kbComment2.getUserName();

		int value = userName1.compareToIgnoreCase(userName2);

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