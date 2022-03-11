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

package com.liferay.site.service.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.layout.test.util.LayoutTestUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.GroupService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.OrganizationTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import java.util.LinkedList;
import java.util.List;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Preston Crary
 */
@RunWith(Arquillian.class)
public class GroupServiceUserSitesGroupsTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Test
	public void testCompanyGroup() throws Exception {
		_user = UserTestUtil.addUser();

		List<Group> groups = _groupService.getUserSitesGroups(
			_user.getUserId(), null, QueryUtil.ALL_POS);

		Group group = _groupLocalService.getCompanyGroup(_user.getCompanyId());

		Assert.assertTrue(
			groups + " does not contain " + group, groups.contains(group));
	}

	@Test
	public void testGroupsWithoutLayouts() throws Exception {
		_group = GroupTestUtil.addGroup();

		_user = UserTestUtil.addGroupUser(_group, RoleConstants.USER);

		List<Group> groups = _groupService.getUserSitesGroups(
			_user.getUserId(), null, QueryUtil.ALL_POS);

		Assert.assertTrue(
			groups + " does not contain " + _group, groups.contains(_group));
	}

	@Test
	public void testInactiveGroups() throws Exception {
		_group = GroupTestUtil.addGroup();

		LayoutTestUtil.addTypePortletLayout(_group);

		_user = UserTestUtil.addGroupUser(_group, RoleConstants.USER);

		_group.setActive(false);

		_groupLocalService.updateGroup(_group);

		List<Group> groups = _groupService.getUserSitesGroups(
			_user.getUserId(), null, QueryUtil.ALL_POS);

		Assert.assertFalse(
			groups + " contains " + _group, groups.contains(_group));
	}

	@Test
	public void testInactiveOrganizationGroup() throws Exception {
		_user = UserTestUtil.addUser();

		Organization organization = OrganizationTestUtil.addOrganization(true);

		_organizations.addFirst(organization);

		_userLocalService.addOrganizationUsers(
			organization.getOrganizationId(), new long[] {_user.getUserId()});

		Group group = organization.getGroup();

		LayoutTestUtil.addTypePortletLayout(group);

		group = _groupLocalService.getGroup(group.getGroupId());

		group.setActive(false);

		_groupLocalService.updateGroup(group);

		List<Group> groups = _groupService.getUserSitesGroups(
			_user.getUserId(), null, QueryUtil.ALL_POS);

		Assert.assertFalse(
			groups + " contains " + group, groups.contains(group));
	}

	@Test
	public void testOrganizationAncestorsGroups() throws Exception {
		_user = UserTestUtil.addUser();

		Organization parentOrganization = OrganizationTestUtil.addOrganization(
			true);

		Group parentOrganizationGroup = parentOrganization.getGroup();

		_organizations.addFirst(parentOrganization);

		Organization organization = OrganizationTestUtil.addOrganization(
			parentOrganization.getOrganizationId(),
			RandomTestUtil.randomString(), false);

		_organizations.addFirst(organization);

		_userLocalService.addOrganizationUsers(
			organization.getOrganizationId(), new long[] {_user.getUserId()});

		try {
			List<Group> groups = _groupService.getUserSitesGroups(
				_user.getUserId(), null, QueryUtil.ALL_POS);

			Group organizationGroup = organization.getGroup();

			Assert.assertTrue(
				groups + " does not contain " + parentOrganizationGroup,
				groups.contains(parentOrganizationGroup));
			Assert.assertFalse(
				groups + " contains " + organizationGroup,
				groups.contains(organizationGroup));
		}
		finally {
			_userLocalService.unsetOrganizationUsers(
				organization.getOrganizationId(),
				new long[] {_user.getUserId()});
		}
	}

	@Test
	public void testOrganizationGroups() throws Exception {
		_user = UserTestUtil.addUser();

		Organization organization = OrganizationTestUtil.addOrganization(true);

		_organizations.addFirst(organization);

		_userLocalService.addGroupUser(organization.getGroupId(), _user);

		Group organizationGroup = organization.getGroup();

		LayoutTestUtil.addTypePortletLayout(organizationGroup);

		List<Group> groups = _groupService.getUserSitesGroups(
			_user.getUserId(), null, QueryUtil.ALL_POS);

		Assert.assertTrue(
			groups + " does not contain " + organizationGroup,
			groups.contains(organizationGroup));
	}

	@Test
	public void testOrganizationUser() throws Exception {
		_user = UserTestUtil.addUser();

		Organization organization = OrganizationTestUtil.addOrganization(true);

		_organizations.addFirst(organization);

		_userLocalService.addOrganizationUser(
			organization.getOrganizationId(), _user);

		Group organizationGroup = organization.getGroup();

		LayoutTestUtil.addTypePortletLayout(organizationGroup);

		List<Group> groups = _groupService.getUserSitesGroups(
			_user.getUserId(), null, QueryUtil.ALL_POS);

		Assert.assertTrue(
			groups + " does not contain " + organizationGroup,
			groups.contains(organizationGroup));
	}

	@Test
	public void testOrganizationWithoutLayouts() throws Exception {
		_user = UserTestUtil.addUser();

		Organization organization = OrganizationTestUtil.addOrganization(true);

		_organizations.addFirst(organization);

		_userLocalService.addGroupUser(organization.getGroupId(), _user);

		List<Group> groups = _groupService.getUserSitesGroups(
			_user.getUserId(), null, QueryUtil.ALL_POS);

		Group organizationGroup = organization.getGroup();

		Assert.assertTrue(
			groups + " does not contain " + organizationGroup,
			groups.contains(organizationGroup));
	}

	@Test
	public void testOrganizationWithoutSite() throws Exception {
		_user = UserTestUtil.addUser();

		Organization organization = OrganizationTestUtil.addOrganization(false);

		_organizations.addFirst(organization);

		_userLocalService.addGroupUser(organization.getGroupId(), _user);

		List<Group> groups = _groupService.getUserSitesGroups(
			_user.getUserId(), null, QueryUtil.ALL_POS);

		Group organizationGroup = organization.getGroup();

		Assert.assertFalse(
			groups + " contains " + organizationGroup,
			groups.contains(organizationGroup));
	}

	@Test
	public void testUserPersonalSite() throws Exception {
		_user = UserTestUtil.addUser();

		List<Group> groups = _groupService.getUserSitesGroups(
			_user.getUserId(), null, QueryUtil.ALL_POS);

		Group userGroup = _user.getGroup();

		Assert.assertTrue(
			groups + " does not contain " + userGroup,
			groups.contains(userGroup));
	}

	@Test
	public void testUsersGroups() throws Exception {
		_group = GroupTestUtil.addGroup();

		LayoutTestUtil.addTypePortletLayout(_group);

		_user = UserTestUtil.addGroupUser(_group, RoleConstants.USER);

		List<Group> groups = _groupService.getUserSitesGroups(
			_user.getUserId(), null, QueryUtil.ALL_POS);

		Assert.assertTrue(
			groups + " does not contain " + _group, groups.contains(_group));
	}

	@DeleteAfterTestRun
	private Group _group;

	@Inject
	private GroupLocalService _groupLocalService;

	@Inject
	private GroupService _groupService;

	@DeleteAfterTestRun
	private final LinkedList<Organization> _organizations = new LinkedList<>();

	@DeleteAfterTestRun
	private User _user;

	@Inject
	private UserLocalService _userLocalService;

}