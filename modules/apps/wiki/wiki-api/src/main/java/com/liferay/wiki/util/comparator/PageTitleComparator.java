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

import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.wiki.model.WikiPage;

/**
 * @author Samuel Liu
 */
public class PageTitleComparator extends OrderByComparator<WikiPage> {

	public static final String ORDER_BY_ASC = "WikiPage.title ASC";

	public static final String ORDER_BY_DESC = "WikiPage.title DESC";

	public static final String[] ORDER_BY_FIELDS = {"title"};

	public PageTitleComparator() {
		this(false);
	}

	public PageTitleComparator(boolean ascending) {
		_ascending = ascending;
	}

	@Override
	public int compare(WikiPage page1, WikiPage page2) {
		String title1 = new String(page1.getTitle());
		String title2 = new String(page2.getTitle());

		int value = title1.compareTo(title2);

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