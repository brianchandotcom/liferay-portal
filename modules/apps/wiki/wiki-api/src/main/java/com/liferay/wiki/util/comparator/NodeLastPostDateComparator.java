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

	public NodeLastPostDateComparator() {
		this(false);
	}

	public NodeLastPostDateComparator(boolean ascending) {
		_ascending = ascending;
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

	private final boolean _ascending;

}