/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.page.template.util.comparator;

import com.liferay.layout.page.template.model.LayoutPageTemplateEntry;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringUtil;

/**
 * @author Jürgen Kappler
 */
public class LayoutPageTemplateEntryNameComparator
	extends OrderByComparator<LayoutPageTemplateEntry> {

	public static final String ORDER_BY_ASC =
		"LayoutPageTemplateEntry.name ASC";

	public static final String ORDER_BY_DESC =
		"LayoutPageTemplateEntry.name DESC";

	public static final String[] ORDER_BY_FIELDS = {"name"};

	public static LayoutPageTemplateEntryNameComparator getInstance(
		boolean ascending) {

		if (ascending) {
			return _INSTANCE_ASCENDING;
		}

		return _INSTANCE_DESCENDING;
	}

	@Override
	public int compare(
		LayoutPageTemplateEntry layoutPageTemplateEntry1,
		LayoutPageTemplateEntry layoutPageTemplateEntry2) {

		String name1 = StringUtil.toLowerCase(
			layoutPageTemplateEntry1.getName());
		String name2 = StringUtil.toLowerCase(
			layoutPageTemplateEntry2.getName());

		int value = name1.compareTo(name2);

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

	private LayoutPageTemplateEntryNameComparator(boolean ascending) {
		_ascending = ascending;
	}

	private static final LayoutPageTemplateEntryNameComparator
		_INSTANCE_ASCENDING = new LayoutPageTemplateEntryNameComparator(true);

	private static final LayoutPageTemplateEntryNameComparator
		_INSTANCE_DESCENDING = new LayoutPageTemplateEntryNameComparator(false);

	private final boolean _ascending;

}