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

	public static final AssetCategoryTreePathComparator INSTANCE_ASCENDING =
		new AssetCategoryTreePathComparator(true);

	public static final AssetCategoryTreePathComparator INSTANCE_DESCENDING =
		new AssetCategoryTreePathComparator(false);

	public static final String ORDER_BY_ASC = "treePath ASC";

	public static final String ORDER_BY_DESC = "treePath DESC";

	public static final String[] ORDER_BY_FIELDS = {"treePath"};

	public static AssetCategoryTreePathComparator getInstance(
		boolean ascending) {

		if (ascending) {
			return INSTANCE_ASCENDING;
		}

		return INSTANCE_DESCENDING;
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

	private AssetCategoryTreePathComparator(boolean ascending) {
		_ascending = ascending;
	}

	private final boolean _ascending;

}