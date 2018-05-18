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

package com.liferay.commerce.user.segment.item.selector.web.internal.util;

import com.liferay.commerce.user.segment.model.CommerceUserSegmentEntry;
import com.liferay.commerce.user.segment.util.comparator.CommerceUserSegmentEntryPriorityComparator;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.SortFactoryUtil;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.util.Objects;

/**
 * @author Alessio Antonio Rendina
 */
public class CommerceUserSegmentItemSelectorViewUtil {

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

		if (Objects.equals(orderByType, "asc")) {
			reverse = false;
		}

		Sort sort = null;

		if (orderByCol.equals("priority")) {
			sort = SortFactoryUtil.create(
				Field.PRIORITY, Sort.INT_TYPE, reverse);
		}

		return sort;
	}

}