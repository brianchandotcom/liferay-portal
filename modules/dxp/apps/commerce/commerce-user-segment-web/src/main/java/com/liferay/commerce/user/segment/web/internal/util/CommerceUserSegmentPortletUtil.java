/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
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