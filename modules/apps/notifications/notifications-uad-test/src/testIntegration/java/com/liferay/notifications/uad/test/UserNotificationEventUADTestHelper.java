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

import com.liferay.portal.kernel.model.UserNotificationDeliveryConstants;
import com.liferay.portal.kernel.model.UserNotificationEvent;
import com.liferay.portal.kernel.service.UserNotificationEventLocalService;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Brian Wing Shun Chan
 */
@Component(immediate = true, service = UserNotificationEventUADTestHelper.class)
public class UserNotificationEventUADTestHelper {

	public UserNotificationEvent addUserNotificationEvent(long userId)
		throws Exception {

		return _userNotificationEventLocalService.addUserNotificationEvent(
			userId, RandomTestUtil.randomString(), 0,
			UserNotificationDeliveryConstants.TYPE_WEBSITE, 0,
			RandomTestUtil.randomString(), false,
			ServiceContextTestUtil.getServiceContext());
	}

	public void cleanUpDependencies(
			List<UserNotificationEvent> userNotificationEvents)
		throws Exception {
	}

	@Reference
	private UserNotificationEventLocalService
		_userNotificationEventLocalService;

}