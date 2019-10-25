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
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.UserEmailAddressException;
import com.liferay.portal.kernel.exception.UserScreenNameException;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.CompanyTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.Calendar;
import java.util.Locale;

import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Jesse Yeh
 */
@RunWith(Arquillian.class)
public class UserSetDigestTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Test(expected = UserEmailAddressException.class)
	public void testAddUserWithWorkflowWithoutEmailAddress() throws Exception {
		_testAddUserWithWorkflowHelper(RandomTestUtil.randomString(), null);
	}

	@Test(expected = PortalException.class)
	public void testAddUserWithWorkflowWithoutPrerequisites() throws Exception {
		_testAddUserWithWorkflowHelper(null, null);
	}

	@Test(expected = UserScreenNameException.class)
	public void testAddUserWithWorkflowWithoutScreenName() throws Exception {
		_testAddUserWithWorkflowHelper(null, _generateRandomEmailAddress());
	}

	@Test
	public void testAddUserWithWorkflowWithPrerequisites() throws Exception {
		_testAddUserWithWorkflowHelper(
			RandomTestUtil.randomString(), _generateRandomEmailAddress());
	}

	@Test
	public void testSetDigestAfterPrerequisites() throws Exception {
		_user = _userLocalService.createUser(RandomTestUtil.nextLong());

		_user.setScreenName(RandomTestUtil.randomString());
		_user.setEmailAddress(_generateRandomEmailAddress());

		_user.setDigest(_user.getDigest(RandomTestUtil.randomString()));
	}

	@Test(expected = IllegalStateException.class)
	public void testSetDigestBeforePrerequisites() throws Exception {
		_user = _userLocalService.createUser(RandomTestUtil.nextLong());

		_user.setDigest(_user.getDigest(RandomTestUtil.randomString()));

		_user.setScreenName(RandomTestUtil.randomString());
		_user.setEmailAddress(_generateRandomEmailAddress());
	}

	@Test(expected = IllegalStateException.class)
	public void testSetEmailAndDigestBeforeScreenName() throws Exception {
		_user = _userLocalService.createUser(RandomTestUtil.nextLong());

		_user.setEmailAddress(_generateRandomEmailAddress());

		_user.setDigest(_user.getDigest(RandomTestUtil.randomString()));

		_user.setScreenName(RandomTestUtil.randomString());
	}

	@Test(expected = IllegalStateException.class)
	public void testSetScreenNameAndDigestBeforeEmailAddress()
		throws Exception {

		_user = _userLocalService.createUser(RandomTestUtil.nextLong());

		_user.setScreenName(RandomTestUtil.randomString());

		_user.setDigest(_user.getDigest(RandomTestUtil.randomString()));

		_user.setEmailAddress(_generateRandomEmailAddress());
	}

	private String _generateRandomEmailAddress() {
		return StringBundler.concat(
			RandomTestUtil.randomString(), RandomTestUtil.nextLong(), "@",
			RandomTestUtil.randomString(), ".com");
	}

	private void _testAddUserWithWorkflowHelper(
			String screenName, String emailAddress)
		throws Exception {

		long creatorUserId = 0;

		Company company = CompanyTestUtil.addCompany();

		long companyId = company.getCompanyId();

		String password = RandomTestUtil.randomString();

		boolean autoPassword = false;
		String password1 = password;
		String password2 = password;

		boolean autoScreenName = false;
		long facebookId = 0;
		String openId = StringPool.BLANK;
		Locale locale = LocaleUtil.getDefault();
		String firstName = RandomTestUtil.randomString();
		String middleName = RandomTestUtil.randomString();
		String lastName = RandomTestUtil.randomString();
		long prefixId = 0;
		long suffixId = 0;
		boolean male = true;
		int birthdayMonth = Calendar.JANUARY;
		int birthdayDay = 1;
		int birthdayYear = 1970;
		String jobTitle = StringPool.BLANK;
		long[] groupIds = null;
		long[] organizationIds = null;
		long[] roleIds = null;
		long[] userGroupIds = null;
		boolean sendEmail = false;
		ServiceContext serviceContext = new ServiceContext();

		_userLocalService.addUserWithWorkflow(
			creatorUserId, companyId, autoPassword, password1, password2,
			autoScreenName, screenName, emailAddress, facebookId, openId,
			locale, firstName, middleName, lastName, prefixId, suffixId, male,
			birthdayMonth, birthdayDay, birthdayYear, jobTitle, groupIds,
			organizationIds, roleIds, userGroupIds, sendEmail, serviceContext);
	}

	@Inject
	private static UserLocalService _userLocalService;

	@DeleteAfterTestRun
	private User _user;

}