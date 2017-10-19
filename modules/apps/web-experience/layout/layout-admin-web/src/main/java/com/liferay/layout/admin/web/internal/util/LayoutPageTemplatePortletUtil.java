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

package com.liferay.layout.admin.web.internal.util;

import com.liferay.layout.page.template.model.LayoutPageTemplateCollection;
import com.liferay.layout.page.template.model.LayoutPageTemplateEntry;
import com.liferay.layout.page.template.util.comparator.LayoutPageTemplateCollectionCreateDateComparator;
import com.liferay.layout.page.template.util.comparator.LayoutPageTemplateCollectionNameComparator;
import com.liferay.layout.page.template.util.comparator.LayoutPageTemplateEntryCreateDateComparator;
import com.liferay.layout.page.template.util.comparator.LayoutPageTemplateEntryNameComparator;
import com.liferay.portal.kernel.util.OrderByComparator;

/**
 * @author Jürgen Kappler
 */
public class LayoutPageTemplatePortletUtil {

	public static OrderByComparator<LayoutPageTemplateCollection>
		getLayoutPageTemplateCollectionOrderByComparator(
			String orderByCol, String orderByType) {

		boolean orderByAsc = false;

		if (orderByType.equals("asc")) {
			orderByAsc = true;
		}

		OrderByComparator<LayoutPageTemplateCollection> orderByComparator =
			null;

		if (orderByCol.equals("create-date")) {
			orderByComparator =
				new LayoutPageTemplateCollectionCreateDateComparator(
					orderByAsc);
		}
		else if (orderByCol.equals("name")) {
			orderByComparator = new LayoutPageTemplateCollectionNameComparator(
				orderByAsc);
		}

		return orderByComparator;
	}

	public static OrderByComparator<LayoutPageTemplateEntry>
		getLayoutPageTemplateEntryOrderByComparator(
			String orderByCol, String orderByType) {

		boolean orderByAsc = false;

		if (orderByType.equals("asc")) {
			orderByAsc = true;
		}

		OrderByComparator<LayoutPageTemplateEntry> orderByComparator = null;

		if (orderByCol.equals("create-date")) {
			orderByComparator = new LayoutPageTemplateEntryCreateDateComparator(
				orderByAsc);
		}
		else if (orderByCol.equals("name")) {
			orderByComparator = new LayoutPageTemplateEntryNameComparator(
				orderByAsc);
		}

		return orderByComparator;
	}

}