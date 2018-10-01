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

package com.liferay.portal.service.user;

import com.liferay.portal.kernel.model.PasswordPolicy;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.PasswordPolicyLocalServiceUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portlet.passwordpoliciesadmin.util.test.PasswordPolicyTestUtil;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Brian Wing Shun Chan
 * @author José Manuel Navarro
 * @author Drew Brokke
 */
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
			PasswordPolicyLocalServiceUtil.updatePasswordPolicy(
				_defaultPasswordPolicy);

		_testPasswordPolicy = PasswordPolicyTestUtil.addPasswordPolicy(
			serviceContext);

		_testPasswordPolicy.setChangeable(false);
		_testPasswordPolicy.setChangeRequired(false);

		_testPasswordPolicy =
			PasswordPolicyLocalServiceUtil.updatePasswordPolicy(
				_testPasswordPolicy);
	}

	@Test
	public void shouldRemovePasswordResetIfPolicyDoesNotAllowChanging()
		throws Exception {

		_user = UserTestUtil.addUser();

		Assert.assertEquals(_defaultPasswordPolicy, _user.getPasswordPolicy());

		Assert.assertTrue(_user.isPasswordReset());

		long[] users = {_user.getUserId()};

		UserLocalServiceUtil.addPasswordPolicyUsers(
			_testPasswordPolicy.getPasswordPolicyId(), users);

		_user = UserLocalServiceUtil.getUser(_user.getUserId());

		Assert.assertFalse(_user.isPasswordReset());
	}

	@After
	public void tearDown() throws Exception {
		_defaultPasswordPolicy.setDefaultPolicy(false);

		PasswordPolicyLocalServiceUtil.updatePasswordPolicy(
			_defaultPasswordPolicy);
	}

	@DeleteAfterTestRun
	private PasswordPolicy _defaultPasswordPolicy;

	@DeleteAfterTestRun
	private PasswordPolicy _testPasswordPolicy;

	@DeleteAfterTestRun
	private User _user;

}