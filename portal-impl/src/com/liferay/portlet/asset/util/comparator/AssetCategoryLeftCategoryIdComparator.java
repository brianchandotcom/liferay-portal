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

import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portlet.asset.model.AssetCategory;

/**
 * @author Shuyang Zhou
 */
public class AssetCategoryLeftCategoryIdComparator
	extends OrderByComparator<AssetCategory> {

	public static final String ORDER_BY_ASC = "leftCategoryId ASC";

	public static final String ORDER_BY_DESC = "leftCategoryId DESC";

	public static final String[] ORDER_BY_FIELDS = {"leftCategoryId"};

	public AssetCategoryLeftCategoryIdComparator() {
		this(false);
	}

	public AssetCategoryLeftCategoryIdComparator(boolean ascending) {
		_ascending = ascending;
	}

	@Override
	public int compare(
		AssetCategory assetCategory1, AssetCategory assetCategory2) {

		long leftCategoryId1 = assetCategory1.getLeftCategoryId();
		long leftCategoryId2 = assetCategory2.getLeftCategoryId();

		int value = 0;

		if (leftCategoryId1 < leftCategoryId2) {
			value = -1;
		}
		else if (leftCategoryId1 > leftCategoryId2) {
			value = 1;
		}

		if (_ascending) {
			return value;
		}
		else {
			return -value;
		}
	}

	@Override
	public String getOrderBy() {
		if (_ascending) {
			return ORDER_BY_ASC;
		}
		else {
			return ORDER_BY_DESC;
		}
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