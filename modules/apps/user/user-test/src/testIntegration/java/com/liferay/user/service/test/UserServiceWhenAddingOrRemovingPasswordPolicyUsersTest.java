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

package com.liferay.user.service.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.model.PasswordPolicy;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.PasswordPolicyLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portlet.passwordpoliciesadmin.util.test.PasswordPolicyTestUtil;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Brian Wing Shun Chan
 * @author José Manuel Navarro
 * @author Drew Brokke
 */
@RunWith(Arquillian.class)
public class UserServiceWhenAddingOrRemovingPasswordPolicyUsersTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setUserId(TestPropsValues.getUserId());

		_defaultPasswordPolicy = PasswordPolicyTestUtil.addPasswordPolicy(
			serviceContext, true);

		_defaultPasswordPolicy.setChangeable(true);
		_defaultPasswordPolicy.setChangeRequired(true);

		_defaultPasswordPolicy =
			_passwordPolicyLocalService.updatePasswordPolicy(
				_defaultPasswordPolicy);

		_testPasswordPolicy = PasswordPolicyTestUtil.addPasswordPolicy(
			serviceContext);

		_testPasswordPolicy.setChangeable(false);
		_testPasswordPolicy.setChangeRequired(false);

		_testPasswordPolicy = _passwordPolicyLocalService.updatePasswordPolicy(
			_testPasswordPolicy);
	}

	@After
	public void tearDown() {
		_defaultPasswordPolicy.setDefaultPolicy(false);

		_passwordPolicyLocalService.updatePasswordPolicy(
			_defaultPasswordPolicy);
	}

	@Test
	public void testShouldRemovePasswordResetIfPolicyDoesNotAllowChanging()
		throws Exception {

		_user = UserTestUtil.addUser();

		Assert.assertEquals(_defaultPasswordPolicy, _user.getPasswordPolicy());

		Assert.assertTrue(_user.isPasswordReset());

		long[] users = {_user.getUserId()};

		_userLocalService.addPasswordPolicyUsers(
			_testPasswordPolicy.getPasswordPolicyId(), users);

		_user = _userLocalService.getUser(_user.getUserId());

		Assert.assertFalse(_user.isPasswordReset());
	}

	@DeleteAfterTestRun
	private PasswordPolicy _defaultPasswordPolicy;

	@Inject
	private PasswordPolicyLocalService _passwordPolicyLocalService;

	@DeleteAfterTestRun
	private PasswordPolicy _testPasswordPolicy;

	@DeleteAfterTestRun
	private User _user;

	@Inject
	private UserLocalService _userLocalService;

}