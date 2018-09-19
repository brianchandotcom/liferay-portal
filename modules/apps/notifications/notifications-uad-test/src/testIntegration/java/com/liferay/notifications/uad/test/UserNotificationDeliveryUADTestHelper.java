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

package com.liferay.notifications.uad.test;

import com.liferay.portal.kernel.model.UserNotificationDelivery;
import com.liferay.portal.kernel.model.UserNotificationDeliveryConstants;
import com.liferay.portal.kernel.service.UserNotificationDeliveryLocalService;
import com.liferay.portal.kernel.test.util.RandomTestUtil;

/**
 * @author Brian Wing Shun Chan
 */
public class UserNotificationDeliveryUADTestHelper {

	public static UserNotificationDelivery addUserNotificationDelivery(
			UserNotificationDeliveryLocalService
				userNotificationDeliveryLocalService,
			long userId)
		throws Exception {

		return userNotificationDeliveryLocalService.
			addUserNotificationDelivery(
				userId, RandomTestUtil.randomString(), 0,
				UserNotificationDeliveryConstants.TYPE_WEBSITE,
				UserNotificationDeliveryConstants.TYPE_WEBSITE, false);
	}

}