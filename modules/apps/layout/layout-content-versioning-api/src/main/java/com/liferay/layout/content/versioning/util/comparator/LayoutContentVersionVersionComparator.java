/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.content.versioning.util.comparator;

import com.liferay.layout.content.versioning.model.LayoutContentVersion;
import com.liferay.portal.kernel.util.OrderByComparator;

/**
 * @author Lourdes Fernández Besada
 */
public class LayoutContentVersionVersionComparator
	extends OrderByComparator<LayoutContentVersion> {

	public static final String ORDER_BY_ASC =
		"LayoutContentVersion.version ASC";

	public static final String ORDER_BY_DESC =
		"LayoutContentVersion.version DESC";

	public static final String[] ORDER_BY_FIELDS = {"version"};

	public static LayoutContentVersionVersionComparator getInstance(
		boolean ascending) {

		if (ascending) {
			return _INSTANCE_ASCENDING;
		}

		return _INSTANCE_DESCENDING;
	}

	@Override
	public int compare(
		LayoutContentVersion layoutContentVersion1,
		LayoutContentVersion layoutContentVersion2) {

		int value = Integer.compare(
			layoutContentVersion1.getVersion(),
			layoutContentVersion2.getVersion());

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

	private LayoutContentVersionVersionComparator(boolean ascending) {
		_ascending = ascending;
	}

	private static final LayoutContentVersionVersionComparator
		_INSTANCE_ASCENDING = new LayoutContentVersionVersionComparator(true);

	private static final LayoutContentVersionVersionComparator
		_INSTANCE_DESCENDING = new LayoutContentVersionVersionComparator(false);

	private final boolean _ascending;

}