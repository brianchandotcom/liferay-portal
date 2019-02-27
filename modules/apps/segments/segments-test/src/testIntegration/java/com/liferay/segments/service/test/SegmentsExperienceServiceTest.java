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

package com.liferay.segments.service.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.RoleConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.service.ResourcePermissionLocalServiceUtil;
import com.liferay.portal.kernel.service.RoleLocalServiceUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.context.ContextUserReplace;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.CompanyTestUtil;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.service.test.ServiceTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerTestRule;
import com.liferay.portal.util.test.LayoutTestUtil;
import com.liferay.segments.constants.SegmentsActionKeys;
import com.liferay.segments.model.SegmentsEntry;
import com.liferay.segments.model.SegmentsExperience;
import com.liferay.segments.service.SegmentsExperienceLocalService;
import com.liferay.segments.service.SegmentsExperienceService;
import com.liferay.segments.test.util.SegmentsTestUtil;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author David Arques
 */
@RunWith(Arquillian.class)
public class SegmentsExperienceServiceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_company = CompanyTestUtil.addCompany();

		_companyAdminUser = UserTestUtil.addCompanyAdminUser(_company);

		_group = GroupTestUtil.addGroup(
			_company.getCompanyId(), _companyAdminUser.getUserId(),
			GroupConstants.DEFAULT_PARENT_GROUP_ID);

		_groupUser = UserTestUtil.addGroupUser(
			_group, RoleConstants.POWER_USER);

		ServiceTestUtil.setUser(_companyAdminUser);

		_classNameId = _classNameLocalService.getClassNameId(Layout.class);

		Layout layout = LayoutTestUtil.addLayout(_group.getGroupId());

		_classPK = layout.getPrimaryKey();
	}

	@Test
	public void testAddSegmentsExperienceWithManageSegmentsEntriesPermission()
		throws Exception {

		Role siteMemberRole = RoleLocalServiceUtil.getRole(
			_company.getCompanyId(), RoleConstants.SITE_MEMBER);

		ResourcePermissionLocalServiceUtil.addResourcePermission(
			_company.getCompanyId(), "com.liferay.segments",
			ResourceConstants.SCOPE_GROUP, String.valueOf(_group.getGroupId()),
			siteMemberRole.getRoleId(),
			SegmentsActionKeys.MANAGE_SEGMENTS_ENTRIES);

		PermissionChecker permissionChecker =
			PermissionCheckerFactoryUtil.create(_groupUser);

		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				_groupUser, permissionChecker)) {

			_addSegmentsExperience(
				ServiceContextTestUtil.getServiceContext(
					_group, _groupUser.getUserId()));
		}
	}

	@Test(expected = PrincipalException.MustHavePermission.class)
	public void testAddSegmentsExperienceWithoutManageSegmentsEntriesPermission()
		throws Exception {

		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				_groupUser, PermissionCheckerFactoryUtil.create(_groupUser))) {

			_addSegmentsExperience(
				ServiceContextTestUtil.getServiceContext(
					_group, _groupUser.getUserId()));
		}
	}

	@Test
	public void testDeleteSegmentsExperienceWithDeletePermission()
		throws Exception {

		SegmentsExperience segmentsEntry = _addSegmentsExperience(
			ServiceContextTestUtil.getServiceContext(
				_group, _companyAdminUser.getUserId()));

		Role siteMemberRole = RoleLocalServiceUtil.getRole(
			_company.getCompanyId(), RoleConstants.SITE_MEMBER);

		ResourcePermissionLocalServiceUtil.addResourcePermission(
			_group.getCompanyId(),
			"com.liferay.segments.model.SegmentsExperience",
			ResourceConstants.SCOPE_GROUP, String.valueOf(_group.getGroupId()),
			siteMemberRole.getRoleId(), ActionKeys.DELETE);

		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				_groupUser, PermissionCheckerFactoryUtil.create(_groupUser))) {

			_segmentsExperienceService.deleteSegmentsExperience(
				segmentsEntry.getSegmentsExperienceId());
		}
	}

	@Test(expected = PrincipalException.MustHavePermission.class)
	public void testDeleteSegmentsExperienceWithoutDeletePermission()
		throws Exception {

		SegmentsExperience segmentsEntry = _addSegmentsExperience(
			ServiceContextTestUtil.getServiceContext(
				_group, _companyAdminUser.getUserId()));

		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				_groupUser, PermissionCheckerFactoryUtil.create(_groupUser))) {

			_segmentsExperienceService.deleteSegmentsExperience(
				segmentsEntry.getSegmentsExperienceId());
		}
	}

	@Test
	public void testGetSegmentsExperiencesCountWithoutViewPermission()
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group, _companyAdminUser.getUserId());

		SegmentsExperience segmentsExperience =
			_segmentsExperienceLocalService.fetchDefaultSegmentsExperience(
				_group.getGroupId(), _classNameId, _classPK, true);

		_addSegmentsExperience(serviceContext);
		_addSegmentsExperience(serviceContext);

		List<Role> roles = RoleLocalServiceUtil.getRoles(
			_company.getCompanyId());

		for (Role role : roles) {
			if (RoleConstants.OWNER.equals(role.getName())) {
				continue;
			}

			ResourcePermissionLocalServiceUtil.removeResourcePermission(
				_company.getCompanyId(),
				"com.liferay.segments.model.SegmentsExperience",
				ResourceConstants.SCOPE_INDIVIDUAL,
				String.valueOf(segmentsExperience.getSegmentsExperienceId()),
				role.getRoleId(), ActionKeys.VIEW);
		}

		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				_groupUser, PermissionCheckerFactoryUtil.create(_groupUser))) {

			Assert.assertEquals(
				2,
				_segmentsExperienceService.getSegmentsExperiencesCount(
					_group.getGroupId(), _classNameId, _classPK, true));
		}
	}

	@Test
	public void testGetSegmentsExperiencesCountWithViewPermission()
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group, _companyAdminUser.getUserId());

		_segmentsExperienceLocalService.fetchDefaultSegmentsExperience(
			_group.getGroupId(), _classNameId, _classPK, true);
		_addSegmentsExperience(serviceContext);
		_addSegmentsExperience(serviceContext);

		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				_groupUser, PermissionCheckerFactoryUtil.create(_groupUser))) {

			Assert.assertEquals(
				3,
				_segmentsExperienceService.getSegmentsExperiencesCount(
					_group.getGroupId(), _classNameId, _classPK, true));
		}
	}

	@Test
	public void testGetSegmentsExperiencesWithoutViewPermission()
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group, _companyAdminUser.getUserId());

		SegmentsExperience segmentsExperience1 =
			_segmentsExperienceLocalService.fetchDefaultSegmentsExperience(
				_group.getGroupId(), _classNameId, _classPK, true);
		SegmentsExperience segmentsExperience2 = _addSegmentsExperience(
			serviceContext);
		SegmentsExperience segmentsExperience3 = _addSegmentsExperience(
			serviceContext);

		for (Role role :
				RoleLocalServiceUtil.getRoles(_company.getCompanyId())) {

			if (RoleConstants.OWNER.equals(role.getName())) {
				continue;
			}

			ResourcePermissionLocalServiceUtil.removeResourcePermission(
				_company.getCompanyId(),
				"com.liferay.segments.model.SegmentsExperience",
				ResourceConstants.SCOPE_INDIVIDUAL,
				String.valueOf(segmentsExperience2.getSegmentsExperienceId()),
				role.getRoleId(), ActionKeys.VIEW);
		}

		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				_groupUser, PermissionCheckerFactoryUtil.create(_groupUser))) {

			List<SegmentsExperience> segmentsEntries =
				_segmentsExperienceService.getSegmentsExperiences(
					_group.getGroupId(), _classNameId, _classPK, true,
					QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);

			Assert.assertEquals(
				segmentsEntries.toString(), 2, segmentsEntries.size());

			Assert.assertTrue(segmentsEntries.contains(segmentsExperience1));
			Assert.assertTrue(segmentsEntries.contains(segmentsExperience3));
		}
	}

	@Test
	public void testGetSegmentsExperiencesWithViewPermission()
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group, _companyAdminUser.getUserId());

		SegmentsExperience segmentsExperience1 =
			_segmentsExperienceLocalService.fetchDefaultSegmentsExperience(
				_group.getGroupId(), _classNameId, _classPK, true);
		SegmentsExperience segmentsExperience2 = _addSegmentsExperience(
			serviceContext);
		SegmentsExperience segmentsExperience3 = _addSegmentsExperience(
			serviceContext);

		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				_groupUser, PermissionCheckerFactoryUtil.create(_groupUser))) {

			List<SegmentsExperience> segmentsEntries =
				_segmentsExperienceService.getSegmentsExperiences(
					_group.getGroupId(), _classNameId, _classPK, true,
					QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);

			Assert.assertEquals(
				segmentsEntries.toString(), 3, segmentsEntries.size());

			Assert.assertTrue(segmentsEntries.contains(segmentsExperience1));
			Assert.assertTrue(segmentsEntries.contains(segmentsExperience2));
			Assert.assertTrue(segmentsEntries.contains(segmentsExperience3));
		}
	}

	@Test(expected = PrincipalException.MustHavePermission.class)
	public void testGetSegmentsExperienceWithoutViewPermission()
		throws Exception {

		SegmentsExperience segmentsExperience = _addSegmentsExperience(
			ServiceContextTestUtil.getServiceContext(
				_group, _companyAdminUser.getUserId()));

		List<Role> roles = RoleLocalServiceUtil.getRoles(
			_company.getCompanyId());

		for (Role role : roles) {
			if (RoleConstants.OWNER.equals(role.getName())) {
				continue;
			}

			ResourcePermissionLocalServiceUtil.removeResourcePermission(
				_company.getCompanyId(),
				"com.liferay.segments.model.SegmentsExperience",
				ResourceConstants.SCOPE_INDIVIDUAL,
				String.valueOf(segmentsExperience.getSegmentsExperienceId()),
				role.getRoleId(), ActionKeys.VIEW);
		}

		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				_groupUser, PermissionCheckerFactoryUtil.create(_groupUser))) {

			_segmentsExperienceService.getSegmentsExperience(
				segmentsExperience.getSegmentsExperienceId());
		}
	}

	@Test
	public void testGetSegmentsExperienceWithViewPermission() throws Exception {
		SegmentsExperience segmentsExperience = _addSegmentsExperience(
			ServiceContextTestUtil.getServiceContext(
				_group, _companyAdminUser.getUserId()));

		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				_groupUser, PermissionCheckerFactoryUtil.create(_groupUser))) {

			_segmentsExperienceService.getSegmentsExperience(
				segmentsExperience.getSegmentsExperienceId());
		}
	}

	@Test(expected = PrincipalException.MustHavePermission.class)
	public void testUpdateSegmentsExperienceWithoutUpdatePermission()
		throws Exception {

		SegmentsExperience segmentsExperience = _addSegmentsExperience(
			ServiceContextTestUtil.getServiceContext(
				_group, _companyAdminUser.getUserId()));

		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				_groupUser, PermissionCheckerFactoryUtil.create(_groupUser))) {

			_segmentsExperienceService.updateSegmentsExperience(
				segmentsExperience.getSegmentsExperienceId(),
				RandomTestUtil.randomLong(),
				RandomTestUtil.randomLocaleStringMap(),
				RandomTestUtil.randomInt(), RandomTestUtil.randomBoolean());
		}
	}

	@Test
	public void testUpdateSegmentsExperienceWithUpdatePermission()
		throws Exception {

		SegmentsExperience segmentsExperience = _addSegmentsExperience(
			ServiceContextTestUtil.getServiceContext(
				_group, _companyAdminUser.getUserId()));

		Role siteMemberRole = RoleLocalServiceUtil.getRole(
			_company.getCompanyId(), RoleConstants.SITE_MEMBER);

		ResourcePermissionLocalServiceUtil.addResourcePermission(
			_group.getCompanyId(),
			"com.liferay.segments.model.SegmentsExperience",
			ResourceConstants.SCOPE_GROUP, String.valueOf(_group.getGroupId()),
			siteMemberRole.getRoleId(), ActionKeys.UPDATE);

		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				_groupUser, PermissionCheckerFactoryUtil.create(_groupUser))) {

			_segmentsExperienceService.updateSegmentsExperience(
				segmentsExperience.getSegmentsExperienceId(),
				RandomTestUtil.randomLong(),
				RandomTestUtil.randomLocaleStringMap(),
				RandomTestUtil.randomInt(), RandomTestUtil.randomBoolean());
		}
	}

	private SegmentsExperience _addSegmentsExperience(
			ServiceContext serviceContext)
		throws Exception {

		SegmentsEntry segmentsEntry = SegmentsTestUtil.addSegmentsEntry(
			_group.getGroupId());

		return _segmentsExperienceLocalService.addSegmentsExperience(
			segmentsEntry.getSegmentsEntryId(), _classNameId, _classPK,
			RandomTestUtil.randomLocaleStringMap(), RandomTestUtil.randomInt(),
			true, serviceContext);
	}

	private long _classNameId;

	@Inject
	private ClassNameLocalService _classNameLocalService;

	private long _classPK;

	@DeleteAfterTestRun
	private Company _company;

	@DeleteAfterTestRun
	private User _companyAdminUser;

	@DeleteAfterTestRun
	private Group _group;

	@DeleteAfterTestRun
	private User _groupUser;

	@Inject
	private SegmentsExperienceLocalService _segmentsExperienceLocalService;

	@Inject
	private SegmentsExperienceService _segmentsExperienceService;

}