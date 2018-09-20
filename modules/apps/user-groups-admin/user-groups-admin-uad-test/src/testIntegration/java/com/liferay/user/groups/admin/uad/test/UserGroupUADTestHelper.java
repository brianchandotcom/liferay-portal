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

package com.liferay.user.groups.admin.uad.test;

import com.liferay.portal.kernel.model.UserGroup;
import com.liferay.portal.kernel.service.UserGroupLocalService;
import com.liferay.portal.kernel.test.randomizerbumpers.NumericStringRandomizerBumper;
import com.liferay.portal.kernel.test.randomizerbumpers.UniqueStringRandomizerBumper;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;

/**
 * @author Brian Wing Shun Chan
 */
public class UserGroupUADTestHelper {

	public static UserGroup addUserGroup(
			UserGroupLocalService userGroupLocalService, long userId)
		throws Exception {

		String name = RandomTestUtil.randomString(
			NumericStringRandomizerBumper.INSTANCE,
			UniqueStringRandomizerBumper.INSTANCE);

		return userGroupLocalService.addUserGroup(
			userId, TestPropsValues.getCompanyId(), name,
			RandomTestUtil.randomString(),
			ServiceContextTestUtil.getServiceContext());
	}

}