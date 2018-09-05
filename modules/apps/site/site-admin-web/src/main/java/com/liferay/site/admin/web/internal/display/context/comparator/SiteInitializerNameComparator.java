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

package com.liferay.site.admin.web.internal.display.context.comparator;

import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.site.admin.web.internal.display.context.SiteInitializerItemDisplayContext;

/**
 * @author Marco Leo
 */
public class SiteInitializerNameComparator
	extends OrderByComparator<SiteInitializerItemDisplayContext> {

	public SiteInitializerNameComparator() {
		this(false);
	}

	public SiteInitializerNameComparator(boolean ascending) {
		_ascending = ascending;
	}

	@Override
	public int compare(
		SiteInitializerItemDisplayContext siteInitializerItemDisplayContext1,
		SiteInitializerItemDisplayContext siteInitializerItemDisplayContext2) {

		String name1 = siteInitializerItemDisplayContext1.getName();
		String name2 = siteInitializerItemDisplayContext2.getName();

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