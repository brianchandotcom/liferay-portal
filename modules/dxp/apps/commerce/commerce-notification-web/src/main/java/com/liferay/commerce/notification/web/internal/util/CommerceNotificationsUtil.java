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

package com.liferay.commerce.notification.web.internal.util;

import com.liferay.commerce.notification.model.CommerceNotificationTemplate;
import com.liferay.commerce.notification.util.comparator.CommerceNotificationTemplateCreateDateComparator;
import com.liferay.portal.kernel.util.OrderByComparator;

/**
 * @author Alessio Antonio Rendina
 */
public class CommerceNotificationsUtil {

	public static OrderByComparator<CommerceNotificationTemplate>
		getCommerceNotificationTemplateOrderByComparator(
			String orderByCol, String orderByType) {

		boolean orderByAsc = false;

		if (orderByType.equals("asc")) {
			orderByAsc = true;
		}

		OrderByComparator<CommerceNotificationTemplate> orderByComparator =
			null;

		if (orderByCol.equals("create-date")) {
			orderByComparator =
				new CommerceNotificationTemplateCreateDateComparator(
					orderByAsc);
		}

		return orderByComparator;
	}

}