/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.wiki.util.comparator;

import com.liferay.portal.kernel.util.DateUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.wiki.model.WikiNode;

/**
 * @author Brian Wing Shun Chan
 */
public class NodeLastPostDateComparator extends OrderByComparator<WikiNode> {

	public static final String ORDER_BY_ASC = "WikiNode.lastPostDate ASC";

	public static final String ORDER_BY_DESC = "WikiNode.lastPostDate DESC";

	public static final String[] ORDER_BY_FIELDS = {"lastPostDate"};

	public static NodeLastPostDateComparator getInstance(boolean ascending) {
		if (ascending) {
			return _INSTANCE_ASCENDING;
		}

		return _INSTANCE_DESCENDING;
	}

	@Override
	public int compare(WikiNode wikiNode1, WikiNode wikiNode2) {
		int value = DateUtil.compareTo(
			wikiNode1.getLastPostDate(), wikiNode2.getLastPostDate());

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

	private NodeLastPostDateComparator(boolean ascending) {
		_ascending = ascending;
	}

	private static final NodeLastPostDateComparator _INSTANCE_ASCENDING =
		new NodeLastPostDateComparator(true);

	private static final NodeLastPostDateComparator _INSTANCE_DESCENDING =
		new NodeLastPostDateComparator(false);

	private final boolean _ascending;

}