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

package com.liferay.commerce.user.segment.web.internal.util;

import com.liferay.commerce.user.segment.model.CommerceUserSegmentCriterion;
import com.liferay.commerce.user.segment.model.CommerceUserSegmentEntry;
import com.liferay.commerce.user.segment.util.comparator.CommerceUserSegmentCriterionPriorityComparator;
import com.liferay.commerce.user.segment.util.comparator.CommerceUserSegmentEntryPriorityComparator;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.SortFactoryUtil;
import com.liferay.portal.kernel.util.OrderByComparator;

/**
 * @author Alessio Antonio Rendina
 */
public class CommerceUserSegmentPortletUtil {

	public static OrderByComparator<CommerceUserSegmentCriterion>
		getCommerceUserSegmentCriterionOrderByComparator(
			String orderByCol, String orderByType) {

		boolean orderByAsc = false;

		if (orderByType.equals("asc")) {
			orderByAsc = true;
		}

		OrderByComparator<CommerceUserSegmentCriterion> orderByComparator =
			null;

		if (orderByCol.equals("priority")) {
			orderByComparator =
				new CommerceUserSegmentCriterionPriorityComparator(orderByAsc);
		}

		return orderByComparator;
	}

	public static OrderByComparator<CommerceUserSegmentEntry>
		getCommerceUserSegmentEntryOrderByComparator(
			String orderByCol, String orderByType) {

		boolean orderByAsc = false;

		if (orderByType.equals("asc")) {
			orderByAsc = true;
		}

		OrderByComparator<CommerceUserSegmentEntry> orderByComparator = null;

		if (orderByCol.equals("priority")) {
			orderByComparator = new CommerceUserSegmentEntryPriorityComparator(
				orderByAsc);
		}

		return orderByComparator;
	}

	public static Sort getCommerceUserSegmentEntrySort(
		String orderByCol, String orderByType) {

		boolean reverse = true;

		if (orderByType.equals("asc")) {
			reverse = false;
		}

		Sort sort = null;

		if (orderByCol.equals("priority")) {
			sort = SortFactoryUtil.create(
				Field.PRIORITY + "_Number_sortable", reverse);
		}

		return sort;
	}

}