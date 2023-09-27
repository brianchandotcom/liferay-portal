/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.asset.util.comparator;

import com.liferay.asset.kernel.model.AssetTag;
import com.liferay.portal.kernel.util.OrderByComparator;

/**
 * @author Miguel Pastor
 */
public class AssetTagCountComparator extends OrderByComparator<AssetTag> {

	public static final String ORDER_BY_ASC = "AssetTag.assetCount ASC";

	public static final String ORDER_BY_DESC = "AssetTag.assetCount DESC";

	public static final String[] ORDER_BY_FIELDS = {"assetCount"};

	public AssetTagCountComparator() {
		this(false);
	}

	public AssetTagCountComparator(boolean ascending) {
		_ascending = ascending;
	}

	@Override
	public int compare(AssetTag assetTag1, AssetTag assetTag2) {
		int value = 0;

		if (assetTag1.getAssetCount() < assetTag2.getAssetCount()) {
			value = -1;
		}
		else if (assetTag1.getAssetCount() > assetTag2.getAssetCount()) {
			value = 1;
		}

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