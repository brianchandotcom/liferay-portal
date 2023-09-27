/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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