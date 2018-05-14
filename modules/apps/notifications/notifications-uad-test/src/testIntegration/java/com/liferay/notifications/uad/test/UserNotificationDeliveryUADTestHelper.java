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

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Brian Wing Shun Chan
 */
@Component(
	immediate = true, service = UserNotificationDeliveryUADTestHelper.class
)
public class UserNotificationDeliveryUADTestHelper {

	/**
	 * Implement addUserNotificationDelivery() to enable some UAD tests.
	 *
	 * <p>
	 * Several UAD tests depend on creating one or more valid UserNotificationDeliveries with a specified user ID in order to execute correctly. Implement addUserNotificationDelivery() such that it creates a valid UserNotificationDelivery with the specified user ID value and returns it in order to enable the UAD tests that depend on it.
	 * </p>
	 */
	public UserNotificationDelivery addUserNotificationDelivery(long userId)
		throws Exception {

		return _userNotificationDeliveryLocalService.
			addUserNotificationDelivery(
				userId, RandomTestUtil.randomString(), 0,
				UserNotificationDeliveryConstants.TYPE_WEBSITE,
				UserNotificationDeliveryConstants.TYPE_WEBSITE, false);
	}

	/**
	 * Implement cleanUpDependencies(List<UserNotificationDelivery> userNotificationDeliveries) if tests require additional tear down logic.
	 *
	 * <p>
	 * Several UAD tests depend on creating one or more valid UserNotificationDeliveries with specified user ID and status by user ID in order to execute correctly. Implement cleanUpDependencies(List<UserNotificationDelivery> userNotificationDeliveries) such that any additional objects created during the construction of userNotificationDeliveries are safely removed.
	 * </p>
	 */
	public void cleanUpDependencies(
			List<UserNotificationDelivery> userNotificationDeliveries)
		throws Exception {
	}

	@Reference
	private UserNotificationDeliveryLocalService
		_userNotificationDeliveryLocalService;

}