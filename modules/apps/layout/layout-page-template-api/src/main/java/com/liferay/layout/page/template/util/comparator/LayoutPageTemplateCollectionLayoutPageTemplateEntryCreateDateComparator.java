/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.page.template.util.comparator;

import com.liferay.layout.page.template.model.LayoutPageTemplateCollection;
import com.liferay.layout.page.template.model.LayoutPageTemplateEntry;
import com.liferay.portal.kernel.util.OrderByComparator;

/**
 * @author Yurena Cabrera
 */
public class
	LayoutPageTemplateCollectionLayoutPageTemplateEntryCreateDateComparator
		extends OrderByComparator<Object> {

	public static final String ORDER_BY_ASC = "createDate ASC";

	public static final String ORDER_BY_DESC = "createDate DESC";

	public static final String[] ORDER_BY_FIELDS = {"createDate"};

	public LayoutPageTemplateCollectionLayoutPageTemplateEntryCreateDateComparator() {
		this(false);
	}

	public LayoutPageTemplateCollectionLayoutPageTemplateEntryCreateDateComparator(
		boolean ascending) {

		_ascending = ascending;
	}

	@Override
	public int compare(Object object1, Object object2) {
		if ((object1 instanceof LayoutPageTemplateCollection) &&
			(object2 instanceof LayoutPageTemplateCollection)) {

			LayoutPageTemplateCollectionCreateDateComparator
				layoutPageTemplateCollectionCreateDateComparator =
					new LayoutPageTemplateCollectionCreateDateComparator();

			return layoutPageTemplateCollectionCreateDateComparator.compare(
				(LayoutPageTemplateCollection)object1,
				(LayoutPageTemplateCollection)object2);
		}

		if ((object1 instanceof LayoutPageTemplateEntry) &&
			(object2 instanceof LayoutPageTemplateEntry)) {

			LayoutPageTemplateEntryCreateDateComparator
				layoutPageTemplateEntryCreateDateComparator =
					new LayoutPageTemplateEntryCreateDateComparator();

			return layoutPageTemplateEntryCreateDateComparator.compare(
				(LayoutPageTemplateEntry)object1,
				(LayoutPageTemplateEntry)object2);
		}

		int value = 0;

		if (object1 instanceof LayoutPageTemplateEntry) {
			value = -1;
		}
		else if (object2 instanceof LayoutPageTemplateEntry) {
			value = 1;
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

	private final boolean _ascending;

}