/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.asset.list.util.comparator;

import com.liferay.asset.list.model.AssetListEntry;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringUtil;

/**
 * @author Kyle Miho
 */
public class AssetListEntryTitleComparator
	extends OrderByComparator<AssetListEntry> {

	public static final String ORDER_BY_ASC = "AssetListEntry.title ASC";

	public static final String ORDER_BY_DESC = "AssetListEntry.title DESC";

	public static final String[] ORDER_BY_FIELDS = {"title"};

	public AssetListEntryTitleComparator() {
		this(false);
	}

	public AssetListEntryTitleComparator(boolean ascending) {
		_ascending = ascending;
	}

	@Override
	public int compare(
		AssetListEntry assetListEntry1, AssetListEntry assetListEntry2) {

		String title1 = StringUtil.toLowerCase(assetListEntry1.getTitle());
		String title2 = StringUtil.toLowerCase(assetListEntry2.getTitle());

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