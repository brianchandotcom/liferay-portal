/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
@Component(service = UserTrackerUADTestHelper.class)
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