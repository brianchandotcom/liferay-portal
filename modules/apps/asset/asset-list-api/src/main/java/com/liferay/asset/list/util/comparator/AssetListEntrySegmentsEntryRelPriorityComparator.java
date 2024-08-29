/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.asset.list.util.comparator;

import com.liferay.asset.list.model.AssetListEntrySegmentsEntryRel;
import com.liferay.portal.kernel.util.OrderByComparator;

/**
 * @author Eudaldo Alonso
 */
public class AssetListEntrySegmentsEntryRelPriorityComparator
	extends OrderByComparator<AssetListEntrySegmentsEntryRel> {

	public static final String ORDER_BY_ASC =
		"AssetListEntrySegmentsEntryRel.priority ASC";

	public static final String ORDER_BY_DESC =
		"AssetListEntrySegmentsEntryRel.priority DESC";

	public static final String[] ORDER_BY_FIELDS = {"priority"};

	public static AssetListEntrySegmentsEntryRelPriorityComparator getInstance(
		boolean ascending) {

		if (ascending) {
			return _INSTANCE_ASCENDING;
		}

		return _INSTANCE_DESCENDING;
	}

	@Override
	public int compare(
		AssetListEntrySegmentsEntryRel assetListEntrySegmentsEntryRel1,
		AssetListEntrySegmentsEntryRel assetListEntrySegmentsEntryRel2) {

		int value = Double.compare(
			assetListEntrySegmentsEntryRel1.getPriority(),
			assetListEntrySegmentsEntryRel2.getPriority());

		if (_ascending) {
			return value;
		}

		return Math.negateExact(value);
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

	private AssetListEntrySegmentsEntryRelPriorityComparator(
		boolean ascending) {

		_ascending = ascending;
	}

	private static final AssetListEntrySegmentsEntryRelPriorityComparator
		_INSTANCE_ASCENDING =
			new AssetListEntrySegmentsEntryRelPriorityComparator(true);

	private static final AssetListEntrySegmentsEntryRelPriorityComparator
		_INSTANCE_DESCENDING =
			new AssetListEntrySegmentsEntryRelPriorityComparator(false);

	private final boolean _ascending;

}