/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.user.groups.admin.uad.test;

import com.liferay.portal.kernel.model.UserGroup;
import com.liferay.portal.kernel.service.UserGroupLocalService;
import com.liferay.portal.kernel.test.randomizerbumpers.NumericStringRandomizerBumper;
import com.liferay.portal.kernel.test.randomizerbumpers.UniqueStringRandomizerBumper;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Brian Wing Shun Chan
 */
@Component(service = UserGroupUADTestHelper.class)
public class UserGroupUADTestHelper {

	public UserGroup addUserGroup(long userId) throws Exception {
		String name = RandomTestUtil.randomString(
			NumericStringRandomizerBumper.INSTANCE,
			UniqueStringRandomizerBumper.INSTANCE);

		return _userGroupLocalService.addUserGroup(
			userId, TestPropsValues.getCompanyId(), name,
			RandomTestUtil.randomString(),
			ServiceContextTestUtil.getServiceContext());
	}

	public void cleanUpDependencies(List<UserGroup> userGroups)
		throws Exception {
	}

	@Reference
	private UserGroupLocalService _userGroupLocalService;

}