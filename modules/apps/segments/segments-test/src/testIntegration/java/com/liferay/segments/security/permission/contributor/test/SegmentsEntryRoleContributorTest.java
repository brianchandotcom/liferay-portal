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

package com.liferay.segments.security.permission.contributor.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.ResourcePermissionLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.OrganizationTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.RoleTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.segments.criteria.Criteria;
import com.liferay.segments.criteria.CriteriaSerializer;
import com.liferay.segments.criteria.contributor.SegmentsCriteriaContributor;
import com.liferay.segments.model.SegmentsEntry;
import com.liferay.segments.service.SegmentsEntryLocalService;
import com.liferay.segments.service.SegmentsEntryRoleLocalService;
import com.liferay.segments.test.util.SegmentsTestUtil;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.mock.web.MockHttpServletRequest;

/**
 * @author Drew Brokke
 */
@RunWith(Arquillian.class)
public class SegmentsEntryRoleContributorTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext();

		serviceContext.setRequest(new MockHttpServletRequest());

		ServiceContextThreadLocal.pushServiceContext(serviceContext);
	}

	@After
	public void tearDown() {
		ServiceContextThreadLocal.popServiceContext();
	}

	@Test
	public void testHasGroupPermissionWhenDeletingSegmentsEntry()
		throws Exception {

		_setUpHasGroupPermissionTest();

		_segmentsEntryLocalService.deleteSegmentsEntry(
			_segmentsEntry.getSegmentsEntryId());

		PermissionChecker permissionChecker =
			PermissionCheckerFactoryUtil.create(_user);

		Assert.assertFalse(
			permissionChecker.hasPermission(
				TestPropsValues.getGroupId(), Group.class.getName(),
				TestPropsValues.getGroupId(), _ACTION_KEY));
	}

	@Test
	public void testHasGroupPermissionWhenDeletingSegmentsEntryRole()
		throws Exception {

		_setUpHasGroupPermissionTest();

		_segmentsEntryRoleLocalService.setSegmentsEntrySiteRoles(
			_segmentsEntry.getSegmentsEntryId(), new long[0],
			ServiceContextTestUtil.getServiceContext());

		PermissionChecker permissionChecker =
			PermissionCheckerFactoryUtil.create(_user);

		Assert.assertFalse(
			permissionChecker.hasPermission(
				TestPropsValues.getGroupId(), Group.class.getName(),
				TestPropsValues.getGroupId(), _ACTION_KEY));
	}

	@Test
	public void testHasGroupPermissionWhenDeletingSiteRole() throws Exception {
		_setUpHasGroupPermissionTest();

		_roleLocalService.deleteRole(_role.getRoleId());

		PermissionChecker permissionChecker =
			PermissionCheckerFactoryUtil.create(_user);

		Assert.assertFalse(
			permissionChecker.hasPermission(
				TestPropsValues.getGroupId(), Group.class.getName(),
				TestPropsValues.getGroupId(), _ACTION_KEY));
	}

	@Test
	public void testHasPermission() throws Exception {
		_role = RoleTestUtil.addRole(RoleConstants.TYPE_REGULAR);

		_organization = OrganizationTestUtil.addOrganization();

		String actionKey = ActionKeys.DELETE;

		_resourcePermissionLocalService.addResourcePermission(
			TestPropsValues.getCompanyId(), Organization.class.getName(),
			ResourceConstants.SCOPE_COMPANY,
			String.valueOf(TestPropsValues.getCompanyId()), _role.getRoleId(),
			actionKey);

		_user = UserTestUtil.addUser();

		_user.setLastName(RandomTestUtil.randomString());

		_user = _userLocalService.updateUser(_user);

		_segmentsEntry = _addSegmentsEntry(_user.getLastName());

		PermissionChecker permissionChecker =
			PermissionCheckerFactoryUtil.create(_user);

		Assert.assertFalse(
			permissionChecker.hasPermission(
				TestPropsValues.getGroupId(), Organization.class.getName(),
				_organization.getOrganizationId(), actionKey));

		_segmentsEntryRoleLocalService.addSegmentsEntryRole(
			_segmentsEntry.getSegmentsEntryId(), _role.getRoleId(),
			ServiceContextTestUtil.getServiceContext());

		permissionChecker = PermissionCheckerFactoryUtil.create(_user);

		Assert.assertTrue(
			permissionChecker.hasPermission(
				TestPropsValues.getGroupId(), Organization.class.getName(),
				_organization.getOrganizationId(), actionKey));
	}

	@Test
	public void testHasPermissionWhenUserInOrganizationSegmentEntry()
		throws Exception {

		PermissionChecker permissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		try {
			_role = RoleTestUtil.addRole(RoleConstants.TYPE_REGULAR);

			_organization = OrganizationTestUtil.addOrganization();

			String actionKey = ActionKeys.DELETE;

			_resourcePermissionLocalService.addResourcePermission(
				TestPropsValues.getCompanyId(), Organization.class.getName(),
				ResourceConstants.SCOPE_COMPANY,
				String.valueOf(TestPropsValues.getCompanyId()),
				_role.getRoleId(), actionKey);

			_user = UserTestUtil.addOrganizationUser(
				_organization, RoleConstants.ORGANIZATION_USER);

			_userLocalService.addOrganizationUsers(
				_organization.getOrganizationId(),
				new long[] {_user.getUserId()});

			_segmentsEntry = _addSegmentEntry(_organization);

			_groupLocalService.addOrganizationGroup(
				_organization.getOrganizationId(),
				TestPropsValues.getGroupId());

			_segmentsEntryRoleLocalService.addSegmentsEntryRole(
				_segmentsEntry.getSegmentsEntryId(), _role.getRoleId(),
				ServiceContextTestUtil.getServiceContext());

			PermissionChecker userPermissionChecker =
				PermissionCheckerFactoryUtil.create(_user);

			PermissionThreadLocal.setPermissionChecker(userPermissionChecker);

			Assert.assertTrue(
				userPermissionChecker.hasPermission(
					TestPropsValues.getGroupId(), Organization.class.getName(),
					_organization.getOrganizationId(), actionKey));
		}
		finally {
			PermissionThreadLocal.setPermissionChecker(permissionChecker);
		}
	}

	private SegmentsEntry _addSegmentEntry(Organization organization)
		throws Exception {

		Criteria criteria = new Criteria();

		_userOrganizationSegmentsCriteriaContributor.contribute(
			criteria,
			String.format(
				"(organizationId eq '%s')", organization.getOrganizationId()),
			Criteria.Conjunction.AND);

		return SegmentsTestUtil.addSegmentsEntry(
			TestPropsValues.getGroupId(),
			CriteriaSerializer.serialize(criteria), User.class.getName());
	}

	private SegmentsEntry _addSegmentsEntry(String userLastName)
		throws Exception {

		return SegmentsTestUtil.addSegmentsEntry(
			TestPropsValues.getGroupId(),
			JSONUtil.put(
				"criteria",
				JSONUtil.put(
					"user",
					JSONUtil.put(
						"conjunction", "and"
					).put(
						"filterString",
						String.format("(lastName eq '%s')", userLastName)
					).put(
						"typeValue", "model"
					))
			).toString(),
			User.class.getName());
	}

	private void _setUpHasGroupPermissionTest() throws Exception {
		_role = RoleTestUtil.addRole(RoleConstants.TYPE_SITE);

		_resourcePermissionLocalService.addResourcePermission(
			TestPropsValues.getCompanyId(), Group.class.getName(),
			ResourceConstants.SCOPE_GROUP,
			String.valueOf(TestPropsValues.getGroupId()), _role.getRoleId(),
			_ACTION_KEY);

		_user = UserTestUtil.addUser(TestPropsValues.getGroupId());

		_user.setLastName(RandomTestUtil.randomString());

		_user = _userLocalService.updateUser(_user);

		_segmentsEntry = _addSegmentsEntry(_user.getLastName());

		PermissionChecker permissionChecker =
			PermissionCheckerFactoryUtil.create(_user);

		Assert.assertFalse(
			permissionChecker.hasPermission(
				TestPropsValues.getGroupId(), Group.class.getName(),
				TestPropsValues.getGroupId(), _ACTION_KEY));

		_segmentsEntryRoleLocalService.setSegmentsEntrySiteRoles(
			_segmentsEntry.getSegmentsEntryId(), new long[] {_role.getRoleId()},
			ServiceContextTestUtil.getServiceContext());

		permissionChecker = PermissionCheckerFactoryUtil.create(_user);

		Assert.assertTrue(
			permissionChecker.hasPermission(
				TestPropsValues.getGroupId(), Group.class.getName(),
				TestPropsValues.getGroupId(), _ACTION_KEY));
	}

	private static final String _ACTION_KEY = ActionKeys.UPDATE;

	@Inject
	private GroupLocalService _groupLocalService;

	@DeleteAfterTestRun
	private Organization _organization;

	@Inject
	private ResourcePermissionLocalService _resourcePermissionLocalService;

	@DeleteAfterTestRun
	private Role _role;

	@Inject
	private RoleLocalService _roleLocalService;

	@DeleteAfterTestRun
	private SegmentsEntry _segmentsEntry;

	@Inject
	private SegmentsEntryLocalService _segmentsEntryLocalService;

	@Inject
	private SegmentsEntryRoleLocalService _segmentsEntryRoleLocalService;

	@DeleteAfterTestRun
	private User _user;

	@Inject
	private UserLocalService _userLocalService;

	@Inject(
		filter = "segments.criteria.contributor.key=user-organization",
		type = SegmentsCriteriaContributor.class
	)
	private SegmentsCriteriaContributor
		_userOrganizationSegmentsCriteriaContributor;

}