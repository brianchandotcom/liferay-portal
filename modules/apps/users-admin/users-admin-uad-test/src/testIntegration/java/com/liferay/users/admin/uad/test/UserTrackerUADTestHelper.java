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

package com.liferay.users.admin.uad.test;

import com.liferay.portal.kernel.model.UserTracker;
import com.liferay.portal.kernel.service.UserTrackerLocalService;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Brian Wing Shun Chan
 */
@Component(immediate = true, service = UserTrackerUADTestHelper.class)
public class UserTrackerUADTestHelper {

	public UserTracker addUserTracker(long userId) throws Exception {
		UserTracker userTracker = _userTrackerLocalService.createUserTracker(
			RandomTestUtil.nextLong());

		userTracker.setCompanyId(TestPropsValues.getCompanyId());
		userTracker.setUserId(userId);
		userTracker.setModifiedDate(RandomTestUtil.nextDate());
		userTracker.setSessionId(RandomTestUtil.randomString());
		userTracker.setRemoteAddr(RandomTestUtil.randomString());
		userTracker.setRemoteHost(RandomTestUtil.randomString());
		userTracker.setUserAgent(RandomTestUtil.randomString());

		return _userTrackerLocalService.updateUserTracker(userTracker);
	}

	public void cleanUpDependencies(List<UserTracker> userTrackers)
		throws Exception {
	}

	@Reference
	private UserTrackerLocalService _userTrackerLocalService;

}