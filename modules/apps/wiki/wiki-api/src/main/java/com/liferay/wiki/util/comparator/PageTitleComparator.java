/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

	public static PageTitleComparator getInstance(boolean ascending) {
		if (ascending) {
			return _INSTANCE_ASCENDING;
		}

		return _INSTANCE_DESCENDING;
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

	private PageTitleComparator(boolean ascending) {
		_ascending = ascending;
	}

	private static final PageTitleComparator _INSTANCE_ASCENDING =
		new PageTitleComparator(true);

	private static final PageTitleComparator _INSTANCE_DESCENDING =
		new PageTitleComparator(false);

	private final boolean _ascending;

}