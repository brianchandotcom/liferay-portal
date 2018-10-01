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

package com.liferay.asset.list.util;

import com.liferay.asset.list.model.AssetListEntry;
import com.liferay.asset.list.util.comparator.AssetListEntryCreateDateComparator;
import com.liferay.asset.list.util.comparator.AssetListEntryTitleComparator;
import com.liferay.portal.kernel.util.OrderByComparator;

/**
 * @author Jürgen Kappler
 */
public class AssetListPortletUtil {

	public static OrderByComparator<AssetListEntry>
		getAssetListEntryOrderByComparator(
			String orderByCol, String orderByType) {

		boolean orderByAsc = false;

		if (orderByType.equals("asc")) {
			orderByAsc = true;
		}

		OrderByComparator<AssetListEntry> orderByComparator = null;

		if (orderByCol.equals("create-date")) {
			orderByComparator = new AssetListEntryCreateDateComparator(
				orderByAsc);
		}
		else if (orderByCol.equals("title")) {
			orderByComparator = new AssetListEntryTitleComparator(orderByAsc);
		}

		return orderByComparator;
	}

}