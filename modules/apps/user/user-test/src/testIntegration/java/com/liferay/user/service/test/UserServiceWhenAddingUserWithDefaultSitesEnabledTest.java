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
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.UserGroupRole;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.UserGroupRoleLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.OrganizationTestUtil;
import com.liferay.portal.kernel.test.util.RoleTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.List;

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
public class UserServiceWhenAddingUserWithDefaultSitesEnabledTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		UnicodeProperties properties = new UnicodeProperties();

		properties.put(
			PropsKeys.ADMIN_DEFAULT_GROUP_NAMES, _group.getDescriptiveName());

		_organization = OrganizationTestUtil.addOrganization(true);

		Group organizationGroup = _organization.getGroup();

		properties.put(
			PropsKeys.ADMIN_DEFAULT_ORGANIZATION_GROUP_NAMES,
			organizationGroup.getDescriptiveName());

		_companyLocalService.updatePreferences(
			_group.getCompanyId(), properties);

		UnicodeProperties typeSettingsProperties =
			_group.getTypeSettingsProperties();

		_siteRole = RoleTestUtil.addRole(RoleConstants.TYPE_SITE);

		typeSettingsProperties.put(
			"defaultSiteRoleIds", String.valueOf(_siteRole.getRoleId()));

		_groupLocalService.updateGroup(
			_group.getGroupId(), typeSettingsProperties.toString());

		_user = UserTestUtil.addUser();
	}

	@Test
	public void testShouldInheritDefaultOrganizationSiteMembership() {
		Group organizationGroup = _organization.getGroup();

		long organizationGroupId = organizationGroup.getGroupId();

		Assert.assertTrue(
			ArrayUtil.contains(_user.getGroupIds(), organizationGroupId));
	}

	@Test
	public void testShouldInheritDefaultSiteRolesFromDefaultSite()
		throws Exception {

		Assert.assertTrue(
			ArrayUtil.contains(_user.getGroupIds(), _group.getGroupId()));

		List<UserGroupRole> userGroupRoles =
			_userGroupRoleLocalService.getUserGroupRoles(
				_user.getUserId(), _group.getGroupId());

		Assert.assertEquals(
			userGroupRoles.toString(), 1, userGroupRoles.size());

		UserGroupRole userGroupRole = userGroupRoles.get(0);

		Assert.assertEquals(_siteRole.getRoleId(), userGroupRole.getRoleId());
	}

	@Inject
	private CompanyLocalService _companyLocalService;

	@DeleteAfterTestRun
	private Group _group;

	@Inject
	private GroupLocalService _groupLocalService;

	@DeleteAfterTestRun
	private Organization _organization;

	@DeleteAfterTestRun
	private Role _siteRole;

	@DeleteAfterTestRun
	private User _user;

	@Inject
	private UserGroupRoleLocalService _userGroupRoleLocalService;

}