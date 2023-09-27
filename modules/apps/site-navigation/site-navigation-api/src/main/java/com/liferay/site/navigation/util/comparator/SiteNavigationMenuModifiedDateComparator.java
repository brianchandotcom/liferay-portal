/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.navigation.util.comparator;

import com.liferay.portal.kernel.util.DateUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.site.navigation.model.SiteNavigationMenu;

/**
 * @author Eudaldo Alonso
 */
public class SiteNavigationMenuModifiedDateComparator
	extends OrderByComparator<SiteNavigationMenu> {

	public static final String ORDER_BY_ASC =
		"SiteNavigationMenu.modifiedDate ASC";

	public static final String ORDER_BY_DESC =
		"SiteNavigationMenu.modifiedDate DESC";

	public static final String[] ORDER_BY_FIELDS = {"modifiedDate"};

	public SiteNavigationMenuModifiedDateComparator() {
		this(false);
	}

	public SiteNavigationMenuModifiedDateComparator(boolean ascending) {
		_ascending = ascending;
	}

	@Override
	public int compare(
		SiteNavigationMenu siteNavigationMenu1,
		SiteNavigationMenu siteNavigationMenu2) {

		int value = DateUtil.compareTo(
			siteNavigationMenu1.getModifiedDate(),
			siteNavigationMenu2.getModifiedDate());

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