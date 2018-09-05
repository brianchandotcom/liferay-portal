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

package com.liferay.portlet.asset.util.comparator;

import com.liferay.asset.kernel.model.AssetTag;
import com.liferay.portal.kernel.util.OrderByComparator;

/**
 * @author Eudaldo Alonso
 */
public class AssetTagAssetCountComparator extends OrderByComparator<AssetTag> {

	public static final String ORDER_BY_ASC =
		"AssetTag.assetCount ASC, AssetTag.name ASC";

	public static final String ORDER_BY_DESC =
		"AssetTag.assetCount DESC, AssetTag.name ASC";

	public static final String[] ORDER_BY_FIELDS = {"assetCount"};

	public AssetTagAssetCountComparator() {
		this(true);
	}

	public AssetTagAssetCountComparator(boolean ascending) {
		_ascending = ascending;
	}

	@Override
	public int compare(AssetTag assetTag1, AssetTag assetTag2) {
		int assetCount1 = assetTag1.getAssetCount();
		int assetCount2 = assetTag2.getAssetCount();

		int value = 0;

		if (assetCount1 < assetCount2) {
			value = -1;
		}
		else if (assetCount1 > assetCount2) {
			value = 1;
		}
		else {
			String name1 = assetTag1.getName();
			String name2 = assetTag2.getName();

			value = name1.compareToIgnoreCase(name2);
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