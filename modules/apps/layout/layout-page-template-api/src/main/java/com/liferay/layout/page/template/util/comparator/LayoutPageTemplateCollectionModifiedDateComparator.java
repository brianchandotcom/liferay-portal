/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.page.template.util.comparator;

import com.liferay.layout.page.template.model.LayoutPageTemplateCollection;
import com.liferay.portal.kernel.util.DateUtil;
import com.liferay.portal.kernel.util.OrderByComparator;

/**
 * @author Yurena Cabrera
 */
public class LayoutPageTemplateCollectionModifiedDateComparator
	extends OrderByComparator<LayoutPageTemplateCollection> {

	public static final String ORDER_BY_ASC =
		"LayoutPageTemplateCollection.type DESC, " +
			"LayoutPageTemplateCollection.modifiedDate ASC";

	public static final String ORDER_BY_DESC =
		"LayoutPageTemplateCollection.type DESC, " +
			"LayoutPageTemplateCollection.modified DESC";

	public static final String[] ORDER_BY_FIELDS = {"modifiedDate"};

	public LayoutPageTemplateCollectionModifiedDateComparator() {
		this(true);
	}

	public LayoutPageTemplateCollectionModifiedDateComparator(
		boolean ascending) {

		_ascending = ascending;
	}

	@Override
	public int compare(
		LayoutPageTemplateCollection layoutPageTemplateCollection1,
		LayoutPageTemplateCollection layoutPageTemplateCollection2) {

		int value = DateUtil.compareTo(
			layoutPageTemplateCollection1.getModifiedDate(),
			layoutPageTemplateCollection2.getModifiedDate());

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