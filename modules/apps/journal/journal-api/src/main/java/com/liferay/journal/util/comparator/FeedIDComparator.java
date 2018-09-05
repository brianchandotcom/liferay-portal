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

package com.liferay.journal.util.comparator;

import com.liferay.journal.model.JournalFeed;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringUtil;

/**
 * @author Eudaldo Alonso
 */
public class FeedIDComparator extends OrderByComparator<JournalFeed> {

	public static final String ORDER_BY_ASC = "JournalFeed.feedId ASC";

	public static final String ORDER_BY_DESC = "JournalFeed.feedId DESC";

	public static final String[] ORDER_BY_FIELDS = {"feedId"};

	public FeedIDComparator() {
		this(false);
	}

	public FeedIDComparator(boolean ascending) {
		_ascending = ascending;
	}

	@Override
	public int compare(JournalFeed feed1, JournalFeed feed2) {
		String feedId1 = StringUtil.toLowerCase(feed1.getFeedId());
		String feedId2 = StringUtil.toLowerCase(feed2.getFeedId());

		int value = feedId1.compareTo(feedId2);

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