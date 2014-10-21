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

package com.liferay.portal.service.http;

import com.liferay.client.soap.portal.model.UserSoap;
import com.liferay.client.soap.portal.service.ServiceContext;
import com.liferay.client.soap.portal.service.http.UserServiceSoap;
import com.liferay.client.soap.portal.service.http.UserServiceSoapServiceLocator;
import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.test.listeners.MainServletExecutionTestListener;
import com.liferay.portal.test.runners.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.util.test.RandomTestUtil;
import com.liferay.portal.util.test.TestPropsValues;

import java.util.Calendar;

import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Brian Wing Shun Chan
 */
@ExecutionTestListeners(listeners = {MainServletExecutionTestListener.class})
@RunWith(LiferayIntegrationJUnitTestRunner.class)
public class UserServiceSoapTest {

	@Test
	public void testAddUser() throws Exception {
		addUser();
	}

	@Test
	public void testDeleteUser() throws Exception {
		UserSoap user = addUser();

		getUserServiceSoap().deleteUser(user.getUserId());
	}

	@Test
	public void testGetUser() throws Exception {
		UserSoap user = addUser();

		getUserServiceSoap().getUserByEmailAddress(
			TestPropsValues.getCompanyId(), user.getEmailAddress());
	}

	protected UserSoap addUser() throws Exception {
		boolean autoPassword = true;
		String password1 = null;
		String password2 = null;
		boolean autoScreenName = true;
		String screenName = StringPool.BLANK;
		String emailAddress =
			"UserServiceSoapTest." + RandomTestUtil.nextLong() + "@liferay.com";
		long facebookId = 0;
		String openId = StringPool.BLANK;
		String locale = LocaleUtil.getDefault().toString();
		String firstName = "UserServiceSoapTest";
		String middleName = StringPool.BLANK;
		String lastName = "UserServiceSoapTest";
		int prefixId = 0;
		int suffixId = 0;
		boolean male = true;
		int birthdayMonth = Calendar.JANUARY;
		int birthdayDay = 1;
		int birthdayYear = 1970;
		String jobTitle = null;
		long[] groupIds = null;
		long[] organizationIds = null;
		long[] roleIds = null;
		long[] userGroupIds = null;
		boolean sendMail = false;
		ServiceContext serviceContext = new ServiceContext();

		return getUserServiceSoap().addUser(
			TestPropsValues.getCompanyId(), autoPassword, password1, password2,
			autoScreenName, screenName, emailAddress, facebookId, openId,
			locale, firstName, middleName, lastName, prefixId, suffixId, male,
			birthdayMonth, birthdayDay, birthdayYear, jobTitle, groupIds,
			organizationIds, roleIds, userGroupIds, sendMail, serviceContext);
	}

	protected UserServiceSoap getUserServiceSoap() throws Exception {
		UserServiceSoapServiceLocator userServiceSoapServiceLocator =
			new UserServiceSoapServiceLocator();

		return userServiceSoapServiceLocator.getPortal_UserService(
			HttpPrincipalTestUtil.getSoapURL(
				userServiceSoapServiceLocator.
					getPortal_UserServiceWSDDServiceName()));
	}

}