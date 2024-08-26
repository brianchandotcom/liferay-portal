/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.asset.entry.rel.util.comparator;

import com.liferay.asset.entry.rel.model.AssetEntryAssetCategoryRel;
import com.liferay.portal.kernel.util.OrderByComparator;

/**
 * @author Roberto Díaz
 */
public class AssetEntryAssetCategoryRelAssetEntryAssetCategoryRelIdComparator
	extends OrderByComparator<AssetEntryAssetCategoryRel> {

	public static final String ORDER_BY_ASC =
		"assetEntryAssetCategoryRelId ASC";

	public static final String ORDER_BY_DESC =
		"assetEntryAssetCategoryRelId DESC";

	public static final String[] ORDER_BY_FIELDS = {
		"assetEntryAssetCategoryRelId"
	};

	public static
		AssetEntryAssetCategoryRelAssetEntryAssetCategoryRelIdComparator
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

		long assetEntryAssetCategoryRelId1 =
			assetEntryAssetCategoryRel1.getAssetEntryAssetCategoryRelId();
		long assetEntryAssetCategoryRelId2 =
			assetEntryAssetCategoryRel2.getAssetEntryAssetCategoryRelId();

		int value = 1;

		if (assetEntryAssetCategoryRelId1 <= assetEntryAssetCategoryRelId2) {
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

	private AssetEntryAssetCategoryRelAssetEntryAssetCategoryRelIdComparator(
		boolean ascending) {

		_ascending = ascending;
	}

	private static final
		AssetEntryAssetCategoryRelAssetEntryAssetCategoryRelIdComparator
			_INSTANCE_ASCENDING =
				new AssetEntryAssetCategoryRelAssetEntryAssetCategoryRelIdComparator(
					true);

	private static final
		AssetEntryAssetCategoryRelAssetEntryAssetCategoryRelIdComparator
			_INSTANCE_DESCENDING =
				new AssetEntryAssetCategoryRelAssetEntryAssetCategoryRelIdComparator(
					false);

	private final boolean _ascending;

}