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

package com.liferay.commerce.wish.list.web.internal.util;

import com.liferay.commerce.wish.list.model.CommerceWishList;
import com.liferay.commerce.wish.list.model.CommerceWishListItem;
import com.liferay.commerce.wish.list.util.comparator.CommerceWishListCreateDateComparator;
import com.liferay.commerce.wish.list.util.comparator.CommerceWishListItemCreateDateComparator;
import com.liferay.commerce.wish.list.util.comparator.CommerceWishListNameComparator;
import com.liferay.portal.kernel.util.OrderByComparator;

/**
 * @author Andrea Di Giorgi
 */
public class CommerceWishListPortletUtil {

	public static OrderByComparator<CommerceWishListItem>
		getCommerceWishListItemOrderByComparator(
			String orderByCol, String orderByType) {

		boolean orderByAsc = false;

		if (orderByType.equals("asc")) {
			orderByAsc = true;
		}

		OrderByComparator<CommerceWishListItem> orderByComparator = null;

		if (orderByCol.equals("create-date")) {
			orderByComparator = new CommerceWishListItemCreateDateComparator(
				orderByAsc);
		}

		return orderByComparator;
	}

	public static OrderByComparator<CommerceWishList>
		getCommerceWishListOrderByComparator(
			String orderByCol, String orderByType) {

		boolean orderByAsc = false;

		if (orderByType.equals("asc")) {
			orderByAsc = true;
		}

		OrderByComparator<CommerceWishList> orderByComparator = null;

		if (orderByCol.equals("create-date")) {
			orderByComparator = new CommerceWishListCreateDateComparator(
				orderByAsc);
		}
		else if (orderByCol.equals("name")) {
			orderByComparator = new CommerceWishListNameComparator(orderByAsc);
		}

		return orderByComparator;
	}

}