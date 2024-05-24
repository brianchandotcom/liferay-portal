/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.asset.categories.internal.util.comparator;

import com.liferay.asset.entry.rel.model.AssetEntryAssetCategoryRel;
import com.liferay.portal.kernel.util.OrderByComparator;

/**
 * @author Eudaldo Alonso
 */
public class AssetEntryAssetCategoryRelAssetCategoryIdComparator
	extends OrderByComparator<AssetEntryAssetCategoryRel> {

	public static final String ORDER_BY_ASC = "assetCategoryId ASC";

	public static final String ORDER_BY_DESC = "assetCategoryId DESC";

	public static final String[] ORDER_BY_FIELDS = {"assetCategoryId"};

	public static AssetEntryAssetCategoryRelAssetCategoryIdComparator
		getInstance(boolean ascending) {

		if (ascending) {
			return _INSTANCE_ASCENDING;
		}

		return _INSTANCE_DESCENDING;
	}

	@Override
	public int compare(
		AssetEntryAssetCategoryRel assetEntryAssetCategoryRel1,
		AssetEntryAssetCategoryRel assetEntryAssetCategoryRel2) {

		long assetCategoryId1 =
			assetEntryAssetCategoryRel1.getAssetCategoryId();
		long assetCategoryId2 =
			assetEntryAssetCategoryRel2.getAssetCategoryId();

		int value = 1;

		if (assetCategoryId1 <= assetCategoryId2) {
			value = -1;
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

	private AssetEntryAssetCategoryRelAssetCategoryIdComparator(
		boolean ascending) {

		_ascending = ascending;
	}

	private static final AssetEntryAssetCategoryRelAssetCategoryIdComparator
		_INSTANCE_ASCENDING =
			new AssetEntryAssetCategoryRelAssetCategoryIdComparator(true);

	private static final AssetEntryAssetCategoryRelAssetCategoryIdComparator
		_INSTANCE_DESCENDING =
			new AssetEntryAssetCategoryRelAssetCategoryIdComparator(false);

	private final boolean _ascending;

}