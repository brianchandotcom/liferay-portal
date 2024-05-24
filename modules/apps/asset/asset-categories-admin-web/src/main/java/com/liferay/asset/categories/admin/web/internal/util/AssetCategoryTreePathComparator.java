/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.asset.categories.admin.web.internal.util;

import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.portal.kernel.util.OrderByComparator;

/**
 * @author Preston Crary
 */
public class AssetCategoryTreePathComparator
	extends OrderByComparator<AssetCategory> {

	public static AssetCategoryTreePathComparator getInstance(
		boolean ascending) {

		if (ascending) {
			return _INSTANCE_ASCENDING;
		}

		return _INSTANCE_DESCENDING;
	}

	@Override
	public int compare(
		AssetCategory assetCategory1, AssetCategory assetCategory2) {

		String treePath1 = assetCategory1.getTreePath();
		String treePath2 = assetCategory2.getTreePath();

		int value = treePath1.compareTo(treePath2);

		if (_ascending) {
			return value;
		}

		return -value;
	}

	@Override
	public String getOrderBy() {
		if (_ascending) {
			return _ORDER_BY_ASC;
		}

		return _ORDER_BY_DESC;
	}

	@Override
	public String[] getOrderByFields() {
		return _ORDER_BY_FIELDS;
	}

	@Override
	public boolean isAscending() {
		return _ascending;
	}

	private AssetCategoryTreePathComparator(boolean ascending) {
		_ascending = ascending;
	}

	private static final AssetCategoryTreePathComparator _INSTANCE_ASCENDING =
		new AssetCategoryTreePathComparator(true);

	private static final AssetCategoryTreePathComparator _INSTANCE_DESCENDING =
		new AssetCategoryTreePathComparator(false);

	private static final String _ORDER_BY_ASC = "treePath ASC";

	private static final String _ORDER_BY_DESC = "treePath DESC";

	private static final String[] _ORDER_BY_FIELDS = {"treePath"};

	private final boolean _ascending;

}