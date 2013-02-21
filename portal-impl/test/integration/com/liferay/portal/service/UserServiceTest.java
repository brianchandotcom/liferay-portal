/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.service;

import com.liferay.portal.kernel.dao.orm.FinderCacheUtil;
import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Organization;
import com.liferay.portal.model.User;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.security.permission.PermissionThreadLocal;
import com.liferay.portal.test.EnvironmentExecutionTestListener;
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.test.TransactionalCallbackAwareExecutionTestListener;
import com.liferay.portal.util.GroupTestUtil;
import com.liferay.portal.util.OrganizationTestUtil;
import com.liferay.portal.util.TestPropsValues;
import com.liferay.portal.util.UserTestUtil;

import java.util.Calendar;
import java.util.Locale;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Brian Wing Shun Chan
 */
@ExecutionTestListeners(
	listeners = {
		EnvironmentExecutionTestListener.class,
		TransactionalCallbackAwareExecutionTestListener.class
	})
@RunWith(LiferayIntegrationJUnitTestRunner.class)
@Transactional
public class UserServiceTest {

	@Before
	public void setUp() {
		FinderCacheUtil.clearCache();
	}

	@Test
	public void testAddUser() throws Exception {
		addUser();
	}

	@Test
	public void testDeleteUser() throws Exception {
		User user = addUser();

		UserServiceUtil.deleteUser(user.getUserId());
	}

	@Test
	public void testGetUser() throws Exception {
		User user = addUser();

		UserServiceUtil.getUserByEmailAddress(
			TestPropsValues.getCompanyId(), user.getEmailAddress());
	}

	@Test
	public void testGroupAdminUnsetGroupAdmin() throws Exception {
		Group group = GroupTestUtil.addGroup();

		User subjectUser = UserTestUtil.addGroupAdminUser(group);
		User objectUser = UserTestUtil.addGroupAdminUser(group);

		unsetGroupUsers(group.getGroupId(), subjectUser, objectUser);

		Assert.assertTrue(
			UserLocalServiceUtil.hasGroupUser(
				group.getGroupId(), objectUser.getUserId()));
	}

	@Test
	public void testGroupAdminUnsetGroupOwner() throws Exception {
		Group group = GroupTestUtil.addGroup();

		User subjectUser = UserTestUtil.addGroupAdminUser(group);
		User objectUser = UserTestUtil.addGroupOwnerUser(group);

		unsetGroupUsers(group.getGroupId(), subjectUser, objectUser);

		Assert.assertTrue(
			UserLocalServiceUtil.hasGroupUser(
				group.getGroupId(), objectUser.getUserId()));
	}

	@Test
	public void testGroupOwnerUnsetGroupAdmin() throws Exception {
		Group group = GroupTestUtil.addGroup();

		User subjectUser = UserTestUtil.addGroupOwnerUser(group);
		User objectUser = UserTestUtil.addGroupAdminUser(group);

		unsetGroupUsers(group.getGroupId(), subjectUser, objectUser);

		Assert.assertFalse(
			UserLocalServiceUtil.hasGroupUser(
				group.getGroupId(), objectUser.getUserId()));
	}

	@Test
	public void testGroupOwnerUnsetGroupOwner() throws Exception {
		Group group = GroupTestUtil.addGroup();

		User subjectUser = UserTestUtil.addGroupOwnerUser(group);
		User objectUser = UserTestUtil.addGroupOwnerUser(group);

		unsetGroupUsers(group.getGroupId(), subjectUser, objectUser);

		Assert.assertFalse(
			UserLocalServiceUtil.hasGroupUser(
				group.getGroupId(), objectUser.getUserId()));
	}

	@Test
	public void testOrganizationAdminUnsetOrganizationAdmin() throws Exception {
		Organization organization = OrganizationTestUtil.addOrganization();

		User subjectUser = UserTestUtil.addOrganizationAdminUser(organization);
		User objectUser = UserTestUtil.addOrganizationAdminUser(organization);

		unsetOrganizationUsers(
			organization.getOrganizationId(), subjectUser, objectUser);

		Assert.assertTrue(
			UserLocalServiceUtil.hasOrganizationUser(
				organization.getOrganizationId(), objectUser.getUserId()));
	}

	@Test
	public void testOrganizationAdminUnsetOrganizationOwner() throws Exception {
		Organization organization = OrganizationTestUtil.addOrganization();

		User subjectUser = UserTestUtil.addOrganizationAdminUser(organization);
		User objectUser = UserTestUtil.addOrganizationOwnerUser(organization);

		unsetOrganizationUsers(
			organization.getOrganizationId(), subjectUser, objectUser);

		Assert.assertTrue(
			UserLocalServiceUtil.hasOrganizationUser(
				organization.getOrganizationId(), objectUser.getUserId()));
	}

	@Test
	public void testOrganizationOwnerUnsetOrganizationAdmin() throws Exception {
		Organization organization = OrganizationTestUtil.addOrganization();

		User subjectUser = UserTestUtil.addOrganizationOwnerUser(organization);
		User objectUser = UserTestUtil.addOrganizationAdminUser(organization);

		unsetOrganizationUsers(
			organization.getOrganizationId(), subjectUser, objectUser);

		Assert.assertFalse(
			UserLocalServiceUtil.hasOrganizationUser(
				organization.getOrganizationId(), objectUser.getUserId()));
	}

	@Test
	public void testOrganizationOwnerUnsetOrganizationOwner() throws Exception {
		Organization organization = OrganizationTestUtil.addOrganization();

		User subjectUser = UserTestUtil.addOrganizationOwnerUser(organization);
		User objectUser = UserTestUtil.addOrganizationOwnerUser(organization);

		unsetOrganizationUsers(
			organization.getOrganizationId(), subjectUser, objectUser);

		Assert.assertFalse(
			UserLocalServiceUtil.hasOrganizationUser(
				organization.getOrganizationId(), objectUser.getUserId()));
	}

	protected User addUser() throws Exception {
		boolean autoPassword = true;
		String password1 = StringPool.BLANK;
		String password2 = StringPool.BLANK;
		boolean autoScreenName = true;
		String screenName = StringPool.BLANK;
		String emailAddress =
			"UserServiceTest." + ServiceTestUtil.nextLong() + "@liferay.com";
		long facebookId = 0;
		String openId = StringPool.BLANK;
		Locale locale = LocaleUtil.getDefault();
		String firstName = "UserServiceTest";
		String middleName = StringPool.BLANK;
		String lastName = "UserServiceTest";
		int prefixId = 0;
		int suffixId = 0;
		boolean male = true;
		int birthdayMonth = Calendar.JANUARY;
		int birthdayDay = 1;
		int birthdayYear = 1970;
		String jobTitle = StringPool.BLANK;
		long[] groupIds = null;
		long[] organizationIds = null;
		long[] roleIds = null;
		long[] userGroupIds = null;
		boolean sendMail = false;

		ServiceContext serviceContext = new ServiceContext();

		return UserServiceUtil.addUser(
			TestPropsValues.getCompanyId(), autoPassword, password1, password2,
			autoScreenName, screenName, emailAddress, facebookId, openId,
			locale, firstName, middleName, lastName, prefixId, suffixId, male,
			birthdayMonth, birthdayDay, birthdayYear, jobTitle, groupIds,
			organizationIds, roleIds, userGroupIds, sendMail, serviceContext);
	}

	protected void unsetGroupUsers(
				long groupId, User subjectUser, User objectUser)
		throws Exception {

		PermissionChecker permissionChecker =
			PermissionCheckerFactoryUtil.create(subjectUser);

		PermissionThreadLocal.setPermissionChecker(permissionChecker);

		ServiceContext serviceContext = new ServiceContext();

		UserServiceUtil.unsetGroupUsers(
			groupId, new long[] {objectUser.getUserId()}, serviceContext);
	}

	protected void unsetOrganizationUsers(
			long organizationId, User subjectUser, User objectUser)
		throws Exception {

		PermissionChecker permissionChecker =
			PermissionCheckerFactoryUtil.create(subjectUser);

		PermissionThreadLocal.setPermissionChecker(permissionChecker);

		UserServiceUtil.unsetOrganizationUsers(
			organizationId, new long[] {objectUser.getUserId()});
	}

}