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

package com.liferay.commerce.notification.web.internal.util;

import com.liferay.commerce.notification.model.CommerceNotificationQueueEntry;
import com.liferay.commerce.notification.model.CommerceNotificationTemplate;
import com.liferay.commerce.notification.util.comparator.CommerceNotificationQueueEntryPriorityComparator;
import com.liferay.commerce.notification.util.comparator.CommerceNotificationTemplateModifiedDateComparator;
import com.liferay.commerce.notification.util.comparator.CommerceNotificationTemplateNameComparator;
import com.liferay.portal.kernel.util.OrderByComparator;

/**
 * @author Alessio Antonio Rendina
 */
public class CommerceNotificationsUtil {

	public static OrderByComparator<CommerceNotificationQueueEntry>
		getCommerceNotificationQueueEntryOrderByComparator(
			String orderByCol, String orderByType) {

		boolean orderByAsc = false;

		if (orderByType.equals("asc")) {
			orderByAsc = true;
		}

		OrderByComparator<CommerceNotificationQueueEntry> orderByComparator =
			null;

		if (orderByCol.equals("priority")) {
			orderByComparator =
				new CommerceNotificationQueueEntryPriorityComparator(
					orderByAsc);
		}

		return orderByComparator;
	}

	public static OrderByComparator<CommerceNotificationTemplate>
		getCommerceNotificationTemplateOrderByComparator(
			String orderByCol, String orderByType) {

		boolean orderByAsc = false;

		if (orderByType.equals("asc")) {
			orderByAsc = true;
		}

		OrderByComparator<CommerceNotificationTemplate> orderByComparator =
			null;

		if (orderByCol.equals("modified-date")) {
			orderByComparator =
				new CommerceNotificationTemplateModifiedDateComparator(
					orderByAsc);
		}
		else if (orderByCol.equals("name")) {
			orderByComparator = new CommerceNotificationTemplateNameComparator(
				orderByAsc);
		}

		return orderByComparator;
	}

}