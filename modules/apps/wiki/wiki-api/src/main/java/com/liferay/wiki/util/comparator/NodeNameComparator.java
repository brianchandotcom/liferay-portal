/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.wiki.util.comparator;

import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.wiki.model.WikiNode;

/**
 * @author Sergio González
 */
public class NodeNameComparator extends OrderByComparator<WikiNode> {

	public static final String ORDER_BY_ASC = "WikiNode.name ASC";

	public static final String ORDER_BY_DESC = "WikiNode.name DESC";

	public static final String[] ORDER_BY_FIELDS = {"name"};

	public NodeNameComparator() {
		this(false);
	}

	public NodeNameComparator(boolean ascending) {
		_ascending = ascending;
	}

	@Override
	public int compare(WikiNode node1, WikiNode node2) {
		String name1 = new String(node1.getName());
		String name2 = new String(node2.getName());

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