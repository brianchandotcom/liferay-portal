/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.navigation.util.comparator;

import com.liferay.portal.kernel.util.DateUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.site.navigation.model.SiteNavigationMenu;

/**
 * @author Pavel Savinov
 */
public class SiteNavigationMenuCreateDateComparator
	extends OrderByComparator<SiteNavigationMenu> {

	public static final String ORDER_BY_ASC =
		"SiteNavigationMenu.createDate ASC";

	public static final String ORDER_BY_DESC =
		"SiteNavigationMenu.createDate DESC";

	public static final String[] ORDER_BY_FIELDS = {"createDate"};

	public static SiteNavigationMenuCreateDateComparator getInstance(
		boolean ascending) {

		if (ascending) {
			return _INSTANCE_ASCENDING;
		}

		return _INSTANCE_DESCENDING;
	}

	@Override
	public int compare(
		SiteNavigationMenu siteNavigationMenu1,
		SiteNavigationMenu siteNavigationMenu2) {

		int value = DateUtil.compareTo(
			siteNavigationMenu1.getCreateDate(),
			siteNavigationMenu2.getCreateDate());

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

	private SiteNavigationMenuCreateDateComparator(boolean ascending) {
		_ascending = ascending;
	}

	private static final SiteNavigationMenuCreateDateComparator
		_INSTANCE_ASCENDING = new SiteNavigationMenuCreateDateComparator(true);

	private static final SiteNavigationMenuCreateDateComparator
		_INSTANCE_DESCENDING = new SiteNavigationMenuCreateDateComparator(
			false);

	private final boolean _ascending;

}