/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.notifications.web.internal.util;

import com.liferay.notifications.web.internal.util.comparator.UserNotificationEventTimestampComparator;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.UserNotificationDeliveryConstants;
import com.liferay.portal.kernel.model.UserNotificationEvent;
import com.liferay.portal.kernel.service.UserNotificationEventLocalServiceUtil;

/**
 * @author Alejandro Tardín
 */
public class NotificationsUtil {

	public static long getAllNotificationsCount(
			long userId, boolean actionRequired)
		throws PortalException {

		if (actionRequired) {
			return UserNotificationEventLocalServiceUtil.
				getArchivedUserNotificationEventsCount(
					userId, _DELIVERY_TYPE, true, true, false);
		}

		return UserNotificationEventLocalServiceUtil.
			getDeliveredUserNotificationEventsCount(
				userId, _DELIVERY_TYPE, true, false);
	}

	public static void populateResults(
			long userId, boolean actionRequired, String navigation,
			String orderByType,
			SearchContainer<UserNotificationEvent> searchContainer)
		throws PortalException {

		if (navigation.equals("all")) {
			searchContainer.setResultsAndTotal(
				() ->
					UserNotificationEventLocalServiceUtil.
						getDeliveredUserNotificationEvents(
							userId, _DELIVERY_TYPE, true, actionRequired,
							searchContainer.getStart(),
							searchContainer.getEnd(),
							new UserNotificationEventTimestampComparator(
								orderByType.equals("asc"))),
				UserNotificationEventLocalServiceUtil.
					getDeliveredUserNotificationEventsCount(
						userId, _DELIVERY_TYPE, true, actionRequired));
		}
		else {
			boolean readNavigation = false;

			if (navigation.equals("read")) {
				readNavigation = true;
			}

			boolean archived = readNavigation;

			searchContainer.setResultsAndTotal(
				() ->
					UserNotificationEventLocalServiceUtil.
						getArchivedUserNotificationEvents(
							userId, _DELIVERY_TYPE, true, actionRequired,
							archived, searchContainer.getStart(),
							searchContainer.getEnd(),
							new UserNotificationEventTimestampComparator(
								orderByType.equals("asc"))),
				UserNotificationEventLocalServiceUtil.
					getArchivedUserNotificationEventsCount(
						userId, _DELIVERY_TYPE, true, actionRequired,
						archived));
		}
	}

	private static final int _DELIVERY_TYPE =
		UserNotificationDeliveryConstants.TYPE_WEBSITE;

}