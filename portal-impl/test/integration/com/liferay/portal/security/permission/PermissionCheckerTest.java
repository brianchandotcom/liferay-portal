/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.security.permission;

import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Organization;
import com.liferay.portal.model.Role;
import com.liferay.portal.model.RoleConstants;
import com.liferay.portal.model.User;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.test.EnvironmentExecutionTestListener;
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.test.TransactionalExecutionTestListener;
import com.liferay.portal.util.GroupTestUtil;
import com.liferay.portal.util.OrganizationTestUtil;
import com.liferay.portal.util.RoleTestUtil;
import com.liferay.portal.util.TestPropsValues;
import com.liferay.portal.util.UserTestUtil;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Roberto Díaz
 */
@ExecutionTestListeners(
	listeners = {
		EnvironmentExecutionTestListener.class,
		TransactionalExecutionTestListener.class
	})
@RunWith(LiferayIntegrationJUnitTestRunner.class)
@Transactional
public class PermissionCheckerTest {

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();
	}

	@Test
	public void testIsCompanyAdminWithCompanyAdmin() throws Exception {
		PermissionChecker permissionChecker = _getPermissionChecker(
			TestPropsValues.getUser());

		Assert.assertTrue(permissionChecker.isCompanyAdmin());
	}

	@Test
	public void testIsCompanyAdminWithRegularUser() throws Exception {
		User user = UserTestUtil.addUser();

		PermissionChecker permissionChecker = _getPermissionChecker(user);

		Assert.assertFalse(permissionChecker.isCompanyAdmin());
	}

	@Test
	public void testIsContentReviewerWithCompanyAdminUser() throws Exception {
		PermissionChecker permissionChecker = _getPermissionChecker(
			TestPropsValues.getUser());

		Assert.assertTrue(
			permissionChecker.isContentReviewer(
				TestPropsValues.getCompanyId(), _group.getGroupId()));
	}

	@Test
	public void testIsContentReviewerWithReviewerUser() throws Exception {
		User user = UserTestUtil.addUser();

		Role portalContentReviewerRole = RoleTestUtil.addRole(
			RoleConstants.PORTAL_CONTENT_REVIEWER, RoleConstants.TYPE_REGULAR);

		UserLocalServiceUtil.setRoleUsers(
			portalContentReviewerRole.getRoleId(),
			new long[] {user.getUserId()});

		PermissionChecker permissionChecker = _getPermissionChecker(user);

		Assert.assertTrue(
			permissionChecker.isContentReviewer(
				user.getCompanyId(), _group.getGroupId()));
	}

	@Test
	public void testIsContentReviewerWithSiteContentReviewer()
		throws Exception {

		RoleTestUtil.addRole(
			RoleConstants.SITE_CONTENT_REVIEWER, RoleConstants.TYPE_SITE);

		User user = UserTestUtil.addGroupUser(
			_group, RoleConstants.SITE_CONTENT_REVIEWER);

		PermissionChecker permissionChecker = _getPermissionChecker(user);

		Assert.assertTrue(
			permissionChecker.isContentReviewer(
				user.getCompanyId(), _group.getGroupId()));
	}

	@Test
	public void testIsGroupAdminWithCompanyAdmin() throws Exception {
		PermissionChecker permissionChecker = _getPermissionChecker(
			TestPropsValues.getUser());

		Assert.assertTrue(permissionChecker.isGroupAdmin(_group.getGroupId()));
	}

	@Test
	public void testIsGroupAdminWithGroupAdmin() throws Exception {
		User user = UserTestUtil.addGroupAdminUser(_group);

		PermissionChecker permissionChecker = _getPermissionChecker(user);

		Assert.assertTrue(permissionChecker.isGroupAdmin(_group.getGroupId()));
	}

	@Test
	public void testIsGroupAdminWithRegularUser() throws Exception {
		User user = UserTestUtil.addUser();

		PermissionChecker permissionChecker = _getPermissionChecker(user);

		Assert.assertFalse(permissionChecker.isGroupAdmin(_group.getGroupId()));
	}

	@Test
	public void testIsGroupMemberWithGroupMember() throws Exception {
		User user = UserTestUtil.addUser();

		UserLocalServiceUtil.addGroupUser(
			_group.getGroupId(), user.getUserId());

		PermissionChecker permissionChecker = _getPermissionChecker(user);

		Assert.assertTrue(permissionChecker.isGroupMember(_group.getGroupId()));
	}

	@Test
	public void testIsGroupMemberWithNonGroupMember() throws Exception {
		User user = UserTestUtil.addUser();

		PermissionChecker permissionChecker = _getPermissionChecker(user);

		Assert.assertFalse(
			permissionChecker.isGroupMember(_group.getGroupId()));
	}

	@Test
	public void testIsGroupOwnerWithCompanyAdmin() throws Exception {
		PermissionChecker permissionChecker = _getPermissionChecker(
			TestPropsValues.getUser());

		Assert.assertTrue(permissionChecker.isGroupOwner(_group.getGroupId()));
	}

	@Test
	public void testIsGroupOwnerWithGroupAdmin() throws Exception {
		User user = UserTestUtil.addGroupAdminUser(_group);

		PermissionChecker permissionChecker = _getPermissionChecker(user);

		Assert.assertFalse(permissionChecker.isGroupOwner(_group.getGroupId()));
	}

	@Test
	public void testIsGroupOwnerWithOwnerUser() throws Exception {
		User user = UserTestUtil.addGroupOwnerUser(_group);

		PermissionChecker permissionChecker = _getPermissionChecker(user);

		Assert.assertTrue(permissionChecker.isGroupOwner(_group.getGroupId()));
	}

	@Test
	public void testIsGroupOwnerWithRegularUser() throws Exception {
		User user = UserTestUtil.addUser(
			_group.getGroupId(), LocaleUtil.getDefault());

		PermissionChecker permissionChecker = _getPermissionChecker(user);

		Assert.assertFalse(permissionChecker.isGroupOwner(_group.getGroupId()));
	}

	@Test
	public void testIsOmniAdminWithAdministratorRoleUser() throws Exception {
		User user = UserTestUtil.addOmniAdmin();

		PermissionChecker permissionChecker = _getPermissionChecker(user);

		Assert.assertFalse(permissionChecker.isOmniadmin());
	}

	@Test
	public void testIsOmniAdminWithCompanyAdmin() throws Exception {
		PermissionChecker permissionChecker = _getPermissionChecker(
			TestPropsValues.getUser());

		Assert.assertFalse(permissionChecker.isOmniadmin());
	}

	@Test
	public void testIsOmniAdminWithGroupAdmin() throws Exception {
		User user = UserTestUtil.addGroupAdminUser(_group);

		PermissionChecker permissionChecker = _getPermissionChecker(user);

		Assert.assertFalse(permissionChecker.isOmniadmin());
	}

	@Test
	public void testIsOmniAdminWithRegularUser() throws Exception {
		User user = UserTestUtil.addUser();

		PermissionChecker permissionChecker = _getPermissionChecker(user);

		Assert.assertFalse(permissionChecker.isOmniadmin());
	}

	@Test
	public void testIsOrganizationAdminWithCompanyAdmin() throws Exception {
		Organization organization = OrganizationTestUtil.addOrganization();

		PermissionChecker permissionChecker = _getPermissionChecker(
			TestPropsValues.getUser());

		Assert.assertTrue(
			permissionChecker.isOrganizationAdmin(
				organization.getOrganizationId()));
	}

	@Test
	public void testIsOrganizationAdminWithGroupAdmin() throws Exception {
		Organization organization = OrganizationTestUtil.addOrganization();

		User user = UserTestUtil.addGroupAdminUser(organization.getGroup());

		PermissionChecker permissionChecker = _getPermissionChecker(user);

		Assert.assertFalse(
			permissionChecker.isOrganizationAdmin(
				organization.getOrganizationId()));
	}

	@Test
	public void testIsOrganizationAdminWithOrganizationAdmin()
		throws Exception {

		Organization organization = OrganizationTestUtil.addOrganization();

		User user = UserTestUtil.addOrganizationAdminUser(organization);

		PermissionChecker permissionChecker = _getPermissionChecker(user);

		Assert.assertTrue(
			permissionChecker.isOrganizationAdmin(
				organization.getOrganizationId()));
	}

	@Test
	public void testIsOrganizationAdminWithRegularUser() throws Exception {
		Organization organization = OrganizationTestUtil.addOrganization();

		User user = UserTestUtil.addUser();

		PermissionChecker permissionChecker = _getPermissionChecker(user);

		Assert.assertFalse(
			permissionChecker.isOrganizationAdmin(
				organization.getOrganizationId()));
	}

	@Test
	public void testIsOrganizationOwnerWithCompanyAdmin() throws Exception {
		Organization organization = OrganizationTestUtil.addOrganization();

		PermissionChecker permissionChecker = _getPermissionChecker(
			TestPropsValues.getUser());

		Assert.assertTrue(
			permissionChecker.isOrganizationOwner(
				organization.getOrganizationId()));
	}

	@Test
	public void testIsOrganizationOwnerWithGroupAdmin() throws Exception {
		Organization organization = OrganizationTestUtil.addOrganization();

		User user = UserTestUtil.addGroupAdminUser(organization.getGroup());

		PermissionChecker permissionChecker = _getPermissionChecker(user);

		Assert.assertFalse(
			permissionChecker.isOrganizationOwner(
				organization.getOrganizationId()));
	}

	@Test
	public void testIsOrganizationOwnerWithOrganizationAdmin()
		throws Exception {

		Organization organization = OrganizationTestUtil.addOrganization();

		User user = UserTestUtil.addOrganizationAdminUser(organization);

		PermissionChecker permissionChecker = _getPermissionChecker(user);

		Assert.assertFalse(
			permissionChecker.isOrganizationOwner(
				organization.getOrganizationId()));
	}

	@Test
	public void testIsOrganizationOwnerWithRegularUser() throws Exception {
		Organization organization = OrganizationTestUtil.addOrganization();

		User user = UserTestUtil.addUser();

		PermissionChecker permissionChecker = _getPermissionChecker(user);

		Assert.assertFalse(
			permissionChecker.isOrganizationOwner(
				organization.getOrganizationId()));
	}

	private PermissionChecker _getPermissionChecker(User user)
		throws Exception {

		return PermissionCheckerFactoryUtil.create(user);
	}

	private Group _group;

}