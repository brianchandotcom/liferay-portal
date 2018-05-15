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

package com.liferay.user.groups.admin.uad.anonymizer.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.UserGroup;
import com.liferay.portal.kernel.service.UserGroupLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.user.associated.data.anonymizer.UADAnonymizer;
import com.liferay.user.associated.data.test.util.BaseUADAnonymizerTestCase;
import com.liferay.user.groups.admin.uad.test.UserGroupUADTestHelper;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.runner.RunWith;

/**
 * @author Brian Wing Shun Chan
 */
@RunWith(Arquillian.class)
public class UserGroupUADAnonymizerTest
	extends BaseUADAnonymizerTestCase<UserGroup> {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@After
	public void tearDown() throws Exception {
		_userGroupUADTestHelper.cleanUpDependencies(_userGroups);
	}

	@Override
	protected UserGroup addBaseModel(long userId) throws Exception {
		return addBaseModel(userId, true);
	}

	@Override
	protected UserGroup addBaseModel(long userId, boolean deleteAfterTestRun)
		throws Exception {

		UserGroup userGroup = _userGroupUADTestHelper.addUserGroup(userId);

		if (deleteAfterTestRun) {
			_userGroups.add(userGroup);
		}

		return userGroup;
	}

	@Override
	protected void deleteBaseModels(List<UserGroup> baseModels)
		throws Exception {

		_userGroupUADTestHelper.cleanUpDependencies(baseModels);
	}

	@Override
	protected UADAnonymizer getUADAnonymizer() {
		return _uadAnonymizer;
	}

	@Override
	protected boolean isBaseModelAutoAnonymized(long baseModelPK, User user)
		throws Exception {

		UserGroup userGroup = _userGroupLocalService.getUserGroup(baseModelPK);

		String userName = userGroup.getUserName();

		if ((userGroup.getUserId() != user.getUserId()) &&
			!userName.equals(user.getFullName())) {

			return true;
		}

		return false;
	}

	@Override
	protected boolean isBaseModelDeleted(long baseModelPK) {
		if (_userGroupLocalService.fetchUserGroup(baseModelPK) == null) {
			return true;
		}

		return false;
	}

	@Inject(filter = "component.name=*.UserGroupUADAnonymizer")
	private UADAnonymizer _uadAnonymizer;

	@Inject
	private UserGroupLocalService _userGroupLocalService;

	@DeleteAfterTestRun
	private final List<UserGroup> _userGroups = new ArrayList<>();

	@Inject
	private UserGroupUADTestHelper _userGroupUADTestHelper;

}