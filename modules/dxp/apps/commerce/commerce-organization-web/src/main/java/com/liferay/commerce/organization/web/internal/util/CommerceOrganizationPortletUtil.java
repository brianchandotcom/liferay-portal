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

package com.liferay.commerce.organization.web.internal.util;

import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.SortFactoryUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.comparator.UserLastNameComparator;

/**
 * @author Marco Leo
 * @author Alessio Antonio Rendina
 */
public class CommerceOrganizationPortletUtil {

	public static Sort getOrganizationSort(
		String orderByCol, String orderByType) {

		boolean reverse = true;

		if (orderByType.equals("asc")) {
			reverse = false;
		}

		Sort sort = null;

		if (orderByCol.equals("nameTreePath")) {
			sort = SortFactoryUtil.create(
				"nameTreePath_String_sortable", reverse);
		}
		else if (orderByCol.equals("name")) {
			sort = SortFactoryUtil.create("name_sortable", reverse);
		}

		return sort;
	}

	public static OrderByComparator<User> getUserOrderByComparator(
		String orderByCol, String orderByType) {

		boolean orderByAsc = false;

		if (orderByType.equals("asc")) {
			orderByAsc = true;
		}

		OrderByComparator<User> orderByComparator = null;

		if (orderByCol.equals("name")) {
			orderByComparator = new UserLastNameComparator(orderByAsc);
		}

		return orderByComparator;
	}

	public static Sort getUserSort(String orderByCol, String orderByType) {
		boolean reverse = true;

		if (orderByType.equals("asc")) {
			reverse = false;
		}

		Sort sort = null;

		if (orderByCol.equals("name")) {
			sort = SortFactoryUtil.create("lastName_sortable", reverse);
		}

		return sort;
	}

}