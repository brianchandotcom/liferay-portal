/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

		_defaultPasswordPolicy =
			_passwordPolicyLocalService.updatePasswordPolicy(
				_defaultPasswordPolicy);
	}

	@Test
	public void testShouldRemovePasswordResetIfPolicyDoesNotAllowChanging()
		throws Exception {

		_user = UserTestUtil.addUser();

		Assert.assertEquals(_defaultPasswordPolicy, _user.getPasswordPolicy());

		Assert.assertTrue(_user.isPasswordReset());

		_userLocalService.addPasswordPolicyUsers(
			_testPasswordPolicy.getPasswordPolicyId(),
			new long[] {_user.getUserId()});

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