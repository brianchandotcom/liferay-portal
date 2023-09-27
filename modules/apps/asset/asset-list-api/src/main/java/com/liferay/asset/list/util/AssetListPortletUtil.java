/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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