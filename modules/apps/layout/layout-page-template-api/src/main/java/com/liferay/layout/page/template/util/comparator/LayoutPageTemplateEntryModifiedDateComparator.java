/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.page.template.util.comparator;

import com.liferay.layout.page.template.model.LayoutPageTemplateEntry;
import com.liferay.portal.kernel.util.DateUtil;
import com.liferay.portal.kernel.util.OrderByComparator;

/**
 * @author Lourdes Fernández Besada
 */
public class LayoutPageTemplateEntryModifiedDateComparator
	extends OrderByComparator<LayoutPageTemplateEntry> {

	public static final String ORDER_BY_ASC =
		"LayoutPageTemplateEntry.modifiedDate ASC";

	public static final String ORDER_BY_DESC =
		"LayoutPageTemplateEntry.modifiedDate DESC";

	public static final String[] ORDER_BY_FIELDS = {"modifiedDate"};

	public LayoutPageTemplateEntryModifiedDateComparator() {
		this(true);
	}

	public LayoutPageTemplateEntryModifiedDateComparator(boolean ascending) {
		_ascending = ascending;
	}

	@Override
	public int compare(
		LayoutPageTemplateEntry layoutPageTemplateEntry1,
		LayoutPageTemplateEntry layoutPageTemplateEntry2) {

		int value = DateUtil.compareTo(
			layoutPageTemplateEntry1.getModifiedDate(),
			layoutPageTemplateEntry2.getModifiedDate());

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