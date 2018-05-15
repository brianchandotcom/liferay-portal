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

package com.liferay.roles.uad.anonymizer.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.roles.uad.test.RoleUADTestHelper;
import com.liferay.user.associated.data.anonymizer.UADAnonymizer;
import com.liferay.user.associated.data.test.util.BaseUADAnonymizerTestCase;

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
public class RoleUADAnonymizerTest extends BaseUADAnonymizerTestCase<Role> {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@After
	public void tearDown() throws Exception {
		_roleUADTestHelper.cleanUpDependencies(_roles);
	}

	@Override
	protected Role addBaseModel(long userId) throws Exception {
		return addBaseModel(userId, true);
	}

	@Override
	protected Role addBaseModel(long userId, boolean deleteAfterTestRun)
		throws Exception {

		Role role = _roleUADTestHelper.addRole(userId);

		if (deleteAfterTestRun) {
			_roles.add(role);
		}

		return role;
	}

	@Override
	protected void deleteBaseModels(List<Role> baseModels) throws Exception {
		_roleUADTestHelper.cleanUpDependencies(baseModels);
	}

	@Override
	protected UADAnonymizer getUADAnonymizer() {
		return _uadAnonymizer;
	}

	@Override
	protected boolean isBaseModelAutoAnonymized(long baseModelPK, User user)
		throws Exception {

		Role role = _roleLocalService.getRole(baseModelPK);

		String userName = role.getUserName();

		if ((role.getUserId() != user.getUserId()) &&
			!userName.equals(user.getFullName())) {

			return true;
		}

		return false;
	}

	@Override
	protected boolean isBaseModelDeleted(long baseModelPK) {
		if (_roleLocalService.fetchRole(baseModelPK) == null) {
			return true;
		}

		return false;
	}

	@Inject
	private RoleLocalService _roleLocalService;

	@DeleteAfterTestRun
	private final List<Role> _roles = new ArrayList<>();

	@Inject
	private RoleUADTestHelper _roleUADTestHelper;

	@Inject(filter = "component.name=*.RoleUADAnonymizer")
	private UADAnonymizer _uadAnonymizer;

}