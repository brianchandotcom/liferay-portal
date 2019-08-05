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
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
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
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.segments.constants.SegmentsActionKeys;
import com.liferay.segments.constants.SegmentsEntryConstants;
import com.liferay.segments.model.SegmentsEntry;
import com.liferay.segments.service.SegmentsEntryService;
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
public class SegmentsEntryServiceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

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
	}

	@Test
	public void testAddSegmentsEntryWithManageSegmentsEntriesPermission()
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

			_segmentsEntryService.addSegmentsEntry(
				RandomTestUtil.randomString(),
				RandomTestUtil.randomLocaleStringMap(),
				RandomTestUtil.randomLocaleStringMap(), true,
				RandomTestUtil.randomString(),
				SegmentsEntryConstants.SOURCE_DEFAULT,
				RandomTestUtil.randomString(),
				ServiceContextTestUtil.getServiceContext(
					_group, _groupUser.getUserId()));
		}
	}

	@Test(expected = PrincipalException.MustHavePermission.class)
	public void testAddSegmentsEntryWithoutManageSegmentsEntriesPermission()
		throws Exception {

		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				_groupUser, PermissionCheckerFactoryUtil.create(_groupUser))) {

			_segmentsEntryService.addSegmentsEntry(
				RandomTestUtil.randomString(),
				RandomTestUtil.randomLocaleStringMap(),
				RandomTestUtil.randomLocaleStringMap(), true,
				RandomTestUtil.randomString(),
				SegmentsEntryConstants.SOURCE_DEFAULT,
				RandomTestUtil.randomString(),
				ServiceContextTestUtil.getServiceContext(
					_group, _groupUser.getUserId()));
		}
	}

	@Test
	public void testDeleteSegmentsEntryWithDeletePermission() throws Exception {
		SegmentsEntry segmentsEntry = SegmentsTestUtil.addSegmentsEntry(
			ServiceContextTestUtil.getServiceContext(
				_group, _companyAdminUser.getUserId()));

		Role siteMemberRole = RoleLocalServiceUtil.getRole(
			_company.getCompanyId(), RoleConstants.SITE_MEMBER);

		ResourcePermissionLocalServiceUtil.addResourcePermission(
			_group.getCompanyId(), "com.liferay.segments.model.SegmentsEntry",
			ResourceConstants.SCOPE_GROUP, String.valueOf(_group.getGroupId()),
			siteMemberRole.getRoleId(), ActionKeys.DELETE);

		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				_groupUser, PermissionCheckerFactoryUtil.create(_groupUser))) {

			_segmentsEntryService.deleteSegmentsEntry(
				segmentsEntry.getSegmentsEntryId());
		}
	}

	@Test(expected = PrincipalException.MustHavePermission.class)
	public void testDeleteSegmentsEntryWithoutDeletePermission()
		throws Exception {

		SegmentsEntry segmentsEntry = SegmentsTestUtil.addSegmentsEntry(
			ServiceContextTestUtil.getServiceContext(
				_group, _companyAdminUser.getUserId()));

		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				_groupUser, PermissionCheckerFactoryUtil.create(_groupUser))) {

			_segmentsEntryService.deleteSegmentsEntry(
				segmentsEntry.getSegmentsEntryId());
		}
	}

	@Test
	public void testGetSegmentsEntriesCountWithoutViewPermission()
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group, _companyAdminUser.getUserId());

		SegmentsEntry segmentsEntry = SegmentsTestUtil.addSegmentsEntry(
			serviceContext);

		SegmentsTestUtil.addSegmentsEntry(serviceContext);
		SegmentsTestUtil.addSegmentsEntry(serviceContext);

		List<Role> roles = RoleLocalServiceUtil.getRoles(
			_company.getCompanyId());

		for (Role role : roles) {
			if (RoleConstants.OWNER.equals(role.getName())) {
				continue;
			}

			ResourcePermissionLocalServiceUtil.removeResourcePermission(
				_company.getCompanyId(),
				"com.liferay.segments.model.SegmentsEntry",
				ResourceConstants.SCOPE_INDIVIDUAL,
				String.valueOf(segmentsEntry.getSegmentsEntryId()),
				role.getRoleId(), ActionKeys.VIEW);
		}

		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				_groupUser, PermissionCheckerFactoryUtil.create(_groupUser))) {

			Assert.assertEquals(
				2,
				_segmentsEntryService.getSegmentsEntriesCount(
					_group.getGroupId(), false));
		}
	}

	@Test
	public void testGetSegmentsEntriesCountWithViewPermission()
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group, _companyAdminUser.getUserId());

		SegmentsTestUtil.addSegmentsEntry(serviceContext);
		SegmentsTestUtil.addSegmentsEntry(serviceContext);
		SegmentsTestUtil.addSegmentsEntry(serviceContext);

		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				_groupUser, PermissionCheckerFactoryUtil.create(_groupUser))) {

			Assert.assertEquals(
				3,
				_segmentsEntryService.getSegmentsEntriesCount(
					_group.getGroupId(), false));
		}
	}

	@Test
	public void testGetSegmentsEntriesWithoutViewPermission() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group, _companyAdminUser.getUserId());

		SegmentsEntry segmentsEntry1 = SegmentsTestUtil.addSegmentsEntry(
			serviceContext);
		SegmentsEntry segmentsEntry2 = SegmentsTestUtil.addSegmentsEntry(
			serviceContext);
		SegmentsEntry segmentsEntry3 = SegmentsTestUtil.addSegmentsEntry(
			serviceContext);

		for (Role role :
				RoleLocalServiceUtil.getRoles(_company.getCompanyId())) {

			if (RoleConstants.OWNER.equals(role.getName())) {
				continue;
			}

			ResourcePermissionLocalServiceUtil.removeResourcePermission(
				_company.getCompanyId(),
				"com.liferay.segments.model.SegmentsEntry",
				ResourceConstants.SCOPE_INDIVIDUAL,
				String.valueOf(segmentsEntry2.getSegmentsEntryId()),
				role.getRoleId(), ActionKeys.VIEW);
		}

		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				_groupUser, PermissionCheckerFactoryUtil.create(_groupUser))) {

			List<SegmentsEntry> segmentsEntries =
				_segmentsEntryService.getSegmentsEntries(
					_group.getGroupId(), false, 0, 100, null);

			Assert.assertEquals(
				segmentsEntries.toString(), 2, segmentsEntries.size());

			Assert.assertTrue(segmentsEntries.contains(segmentsEntry1));
			Assert.assertTrue(segmentsEntries.contains(segmentsEntry3));
		}
	}

	@Test
	public void testGetSegmentsEntriesWithViewPermission() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group, _companyAdminUser.getUserId());

		SegmentsEntry segmentsEntry1 = SegmentsTestUtil.addSegmentsEntry(
			serviceContext);
		SegmentsEntry segmentsEntry2 = SegmentsTestUtil.addSegmentsEntry(
			serviceContext);
		SegmentsEntry segmentsEntry3 = SegmentsTestUtil.addSegmentsEntry(
			serviceContext);

		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				_groupUser, PermissionCheckerFactoryUtil.create(_groupUser))) {

			List<SegmentsEntry> segmentsEntries =
				_segmentsEntryService.getSegmentsEntries(
					_group.getGroupId(), false, 0, 100, null);

			Assert.assertEquals(
				segmentsEntries.toString(), 3, segmentsEntries.size());

			Assert.assertTrue(segmentsEntries.contains(segmentsEntry1));
			Assert.assertTrue(segmentsEntries.contains(segmentsEntry2));
			Assert.assertTrue(segmentsEntries.contains(segmentsEntry3));
		}
	}

	@Test(expected = PrincipalException.MustHavePermission.class)
	public void testGetSegmentsEntryWithoutViewPermission() throws Exception {
		SegmentsEntry segmentsEntry = SegmentsTestUtil.addSegmentsEntry(
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
				"com.liferay.segments.model.SegmentsEntry",
				ResourceConstants.SCOPE_INDIVIDUAL,
				String.valueOf(segmentsEntry.getSegmentsEntryId()),
				role.getRoleId(), ActionKeys.VIEW);
		}

		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				_groupUser, PermissionCheckerFactoryUtil.create(_groupUser))) {

			_segmentsEntryService.getSegmentsEntry(
				segmentsEntry.getSegmentsEntryId());
		}
	}

	@Test
	public void testGetSegmentsEntryWithViewPermission() throws Exception {
		SegmentsEntry segmentsEntry = SegmentsTestUtil.addSegmentsEntry(
			ServiceContextTestUtil.getServiceContext(
				_group, _companyAdminUser.getUserId()));

		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				_groupUser, PermissionCheckerFactoryUtil.create(_groupUser))) {

			_segmentsEntryService.getSegmentsEntry(
				segmentsEntry.getSegmentsEntryId());
		}
	}

	@Test(expected = PrincipalException.MustHavePermission.class)
	public void testUpdateSegmentsEntryFromExternalSource() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group, _companyAdminUser.getUserId());

		SegmentsEntry segmentsEntry = SegmentsTestUtil.addSegmentsEntry(
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			SegmentsEntryConstants.SOURCE_ASAH_FARO_BACKEND,
			RandomTestUtil.randomString(), serviceContext);

		_segmentsEntryService.updateSegmentsEntry(
			segmentsEntry.getSegmentsEntryId(),
			segmentsEntry.getSegmentsEntryKey(), segmentsEntry.getNameMap(),
			segmentsEntry.getDescriptionMap(), segmentsEntry.isActive(),
			segmentsEntry.getCriteria(), serviceContext);
	}

	@Test(expected = PrincipalException.MustHavePermission.class)
	public void testUpdateSegmentsEntryWithoutUpdatePermission()
		throws Exception {

		SegmentsEntry segmentsEntry = SegmentsTestUtil.addSegmentsEntry(
			ServiceContextTestUtil.getServiceContext(
				_group, _companyAdminUser.getUserId()));

		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				_groupUser, PermissionCheckerFactoryUtil.create(_groupUser))) {

			_segmentsEntryService.updateSegmentsEntry(
				segmentsEntry.getSegmentsEntryId(),
				RandomTestUtil.randomString(),
				RandomTestUtil.randomLocaleStringMap(),
				RandomTestUtil.randomLocaleStringMap(), true,
				RandomTestUtil.randomString(),
				ServiceContextTestUtil.getServiceContext(
					_group, _groupUser.getUserId()));
		}
	}

	@Test
	public void testUpdateSegmentsEntryWithUpdatePermission() throws Exception {
		SegmentsEntry segmentsEntry = SegmentsTestUtil.addSegmentsEntry(
			ServiceContextTestUtil.getServiceContext(
				_group, _companyAdminUser.getUserId()));

		Role siteMemberRole = RoleLocalServiceUtil.getRole(
			_company.getCompanyId(), RoleConstants.SITE_MEMBER);

		ResourcePermissionLocalServiceUtil.addResourcePermission(
			_group.getCompanyId(), "com.liferay.segments.model.SegmentsEntry",
			ResourceConstants.SCOPE_GROUP, String.valueOf(_group.getGroupId()),
			siteMemberRole.getRoleId(), ActionKeys.UPDATE);

		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				_groupUser, PermissionCheckerFactoryUtil.create(_groupUser))) {

			_segmentsEntryService.updateSegmentsEntry(
				segmentsEntry.getSegmentsEntryId(),
				RandomTestUtil.randomString(),
				RandomTestUtil.randomLocaleStringMap(),
				RandomTestUtil.randomLocaleStringMap(), true,
				RandomTestUtil.randomString(),
				ServiceContextTestUtil.getServiceContext(
					_group, _groupUser.getUserId()));
		}
	}

	@DeleteAfterTestRun
	private Company _company;

	@DeleteAfterTestRun
	private User _companyAdminUser;

	@DeleteAfterTestRun
	private Group _group;

	@DeleteAfterTestRun
	private User _groupUser;

	@Inject
	private SegmentsEntryService _segmentsEntryService;

}