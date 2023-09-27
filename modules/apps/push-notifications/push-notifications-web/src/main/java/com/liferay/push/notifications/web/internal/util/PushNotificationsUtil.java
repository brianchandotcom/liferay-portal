/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.push.notifications.web.internal.util;

import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.push.notifications.model.PushNotificationsDevice;
import com.liferay.push.notifications.util.comparator.PushNotificationsDevicePlatformComparator;

/**
 * @author Javier Gamarra
 * @author Salva Tejero
 */
public class PushNotificationsUtil {

	public static OrderByComparator<PushNotificationsDevice>
		getPushNotificationsDeviceOrderByComparator(
			String orderByCol, String orderByType) {

		boolean orderByAsc = false;

		if (orderByType.equals("asc")) {
			orderByAsc = true;
		}

		if (orderByCol.equals("platform")) {
			return new PushNotificationsDevicePlatformComparator(orderByAsc);
		}

		return null;
	}

}