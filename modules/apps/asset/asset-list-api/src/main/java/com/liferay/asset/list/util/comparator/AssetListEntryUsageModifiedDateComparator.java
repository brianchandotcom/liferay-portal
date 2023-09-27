/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.asset.list.util.comparator;

import com.liferay.asset.list.model.AssetListEntryUsage;
import com.liferay.portal.kernel.util.DateUtil;
import com.liferay.portal.kernel.util.OrderByComparator;

/**
 * @author Pavel Savinov
 */
public class AssetListEntryUsageModifiedDateComparator
	extends OrderByComparator<AssetListEntryUsage> {

	public static final String ORDER_BY_ASC =
		"AssetListEntryUsage.modifiedDate ASC";

	public static final String ORDER_BY_DESC =
		"AssetListEntryUsage.modifiedDate DESC";

	public static final String[] ORDER_BY_FIELDS = {"modifiedDate"};

	public AssetListEntryUsageModifiedDateComparator() {
		this(true);
	}

	public AssetListEntryUsageModifiedDateComparator(boolean ascending) {
		_ascending = ascending;
	}

	@Override
	public int compare(
		AssetListEntryUsage assetListEntryUsage1,
		AssetListEntryUsage assetListEntryUsage2) {

		int value = DateUtil.compareTo(
			assetListEntryUsage1.getModifiedDate(),
			assetListEntryUsage2.getModifiedDate());

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