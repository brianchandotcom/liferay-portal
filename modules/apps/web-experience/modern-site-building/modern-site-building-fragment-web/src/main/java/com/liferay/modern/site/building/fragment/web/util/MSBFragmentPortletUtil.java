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

package com.liferay.modern.site.building.fragment.web.util;

import com.liferay.modern.site.building.fragment.model.MSBFragmentCollection;
import com.liferay.modern.site.building.fragment.util.comparator.MSBFragmentCollectionCreateDateComparator;
import com.liferay.modern.site.building.fragment.util.comparator.MSBFragmentCollectionNameComparator;
import com.liferay.portal.kernel.util.OrderByComparator;

/**
 * @author Jürgen Kappler
 */
public class MSBFragmentPortletUtil {

	public static OrderByComparator<MSBFragmentCollection>
		getMSBFragmentCollectionOrderByComparator(
			String orderByCol, String orderByType) {

		boolean orderByAsc = false;

		if (orderByType.equals("asc")) {
			orderByAsc = true;
		}

		OrderByComparator<MSBFragmentCollection> orderByComparator = null;

		if (orderByCol.equals("create-date")) {
			orderByComparator = new MSBFragmentCollectionCreateDateComparator(
				orderByAsc);
		}
		else if (orderByCol.equals("name")) {
			orderByComparator = new MSBFragmentCollectionNameComparator(
				orderByAsc);
		}

		return orderByComparator;
	}

}