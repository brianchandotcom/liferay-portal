/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.marketplace.util.comparator;

import com.liferay.marketplace.model.App;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringUtil;

/**
 * @author Ryan Park
 */
public class AppTitleComparator extends OrderByComparator<App> {

	public static final String ORDER_BY_ASC = "title ASC";

	public static final String ORDER_BY_DESC = "title DESC";

	public static final String[] ORDER_BY_FIELDS = {"title"};

	public AppTitleComparator() {
		this(true);
	}

	public AppTitleComparator(boolean ascending) {
		_ascending = ascending;
	}

	@Override
	public int compare(App app1, App app2) {
		String lowerCaseTitle1 = StringUtil.toLowerCase(app1.getTitle());
		String lowerCaseTitle2 = StringUtil.toLowerCase(app2.getTitle());

		int value = lowerCaseTitle1.compareTo(lowerCaseTitle2);

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