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