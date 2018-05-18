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

package com.liferay.commerce.shipment.web.internal.util;

import com.liferay.commerce.model.CommerceShipment;
import com.liferay.commerce.model.CommerceShipmentItem;
import com.liferay.commerce.util.comparator.CommerceShipmentCreateDateComparator;
import com.liferay.commerce.util.comparator.CommerceShipmentItemCreateDateComparator;
import com.liferay.portal.kernel.util.OrderByComparator;

/**
 * @author Alessio Antonio Rendina
 */
public class CommerceShipmentPortletUtil {

	public static OrderByComparator<CommerceShipmentItem>
		getCommerceShipmentItemOrderByComparator(
			String orderByCol, String orderByType) {

		boolean orderByAsc = false;

		if (orderByType.equals("asc")) {
			orderByAsc = true;
		}

		OrderByComparator<CommerceShipmentItem> orderByComparator = null;

		if (orderByCol.equals("create-date")) {
			orderByComparator = new CommerceShipmentItemCreateDateComparator(
				orderByAsc);
		}

		return orderByComparator;
	}

	public static OrderByComparator<CommerceShipment>
		getCommerceShipmentOrderByComparator(
			String orderByCol, String orderByType) {

		boolean orderByAsc = false;

		if (orderByType.equals("asc")) {
			orderByAsc = true;
		}

		OrderByComparator<CommerceShipment> orderByComparator = null;

		if (orderByCol.equals("create-date")) {
			orderByComparator = new CommerceShipmentCreateDateComparator(
				orderByAsc);
		}

		return orderByComparator;
	}

}