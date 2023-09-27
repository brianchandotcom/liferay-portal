/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.admin.web.internal.display.context.comparator;

import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.site.admin.web.internal.util.SiteInitializerItem;

/**
 * @author Marco Leo
 */
public class SiteInitializerNameComparator
	extends OrderByComparator<SiteInitializerItem> {

	public SiteInitializerNameComparator() {
		this(false);
	}

	public SiteInitializerNameComparator(boolean ascending) {
		_ascending = ascending;
	}

	@Override
	public int compare(
		SiteInitializerItem siteInitializerItem1,
		SiteInitializerItem siteInitializerItem2) {

		String name1 = siteInitializerItem1.getName();
		String name2 = siteInitializerItem2.getName();

		int value = name1.compareToIgnoreCase(name2);

		if (_ascending) {
			return value;
		}

		return -value;
	}

	@Override
	public boolean isAscending() {
		return _ascending;
	}

	private final boolean _ascending;

}