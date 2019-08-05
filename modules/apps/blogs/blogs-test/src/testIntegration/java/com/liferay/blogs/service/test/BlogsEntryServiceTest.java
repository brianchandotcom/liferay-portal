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

package com.liferay.blogs.service.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.blogs.model.BlogsEntry;
import com.liferay.blogs.service.BlogsEntryLocalServiceUtil;
import com.liferay.blogs.service.BlogsEntryServiceUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
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
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.service.test.ServiceTestUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Sergio González
 */
@RunWith(Arquillian.class)
public class BlogsEntryServiceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_company = CompanyTestUtil.addCompany();

		_user = UserTestUtil.addCompanyAdminUser(_company);

		_group = GroupTestUtil.addGroup(
			_company.getCompanyId(), _user.getUserId(),
			GroupConstants.DEFAULT_PARENT_GROUP_ID);

		_groupUser = UserTestUtil.addGroupUser(
			_group, RoleConstants.POWER_USER);

		ServiceTestUtil.setUser(_user);
	}

	@Test
	public void testAddEntryWithAddEntryPermission1() throws Exception {
		Role siteMemberRole = RoleLocalServiceUtil.getRole(
			_company.getCompanyId(), RoleConstants.SITE_MEMBER);

		ResourcePermissionLocalServiceUtil.addResourcePermission(
			_company.getCompanyId(), "com.liferay.blogs",
			ResourceConstants.SCOPE_GROUP, String.valueOf(_group.getGroupId()),
			siteMemberRole.getRoleId(), ActionKeys.ADD_ENTRY);

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group, _groupUser.getUserId());

		PermissionChecker permissionChecker =
			PermissionCheckerFactoryUtil.create(_groupUser);

		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				_groupUser, permissionChecker)) {

			BlogsEntryServiceUtil.addEntry(
				RandomTestUtil.randomString(), RandomTestUtil.randomString(),
				RandomTestUtil.randomString(), RandomTestUtil.randomString(), 1,
				1, 1990, 1, 1, true, false, new String[0],
				RandomTestUtil.randomString(), null, null, serviceContext);
		}
	}

	@Test
	public void testAddEntryWithAddEntryPermission2() throws Exception {
		Role siteMemberRole = RoleLocalServiceUtil.getRole(
			_company.getCompanyId(), RoleConstants.SITE_MEMBER);

		ResourcePermissionLocalServiceUtil.addResourcePermission(
			_company.getCompanyId(), "com.liferay.blogs",
			ResourceConstants.SCOPE_GROUP, String.valueOf(_group.getGroupId()),
			siteMemberRole.getRoleId(), ActionKeys.ADD_ENTRY);

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group, _groupUser.getUserId());

		PermissionChecker permissionChecker =
			PermissionCheckerFactoryUtil.create(_groupUser);

		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				_groupUser, permissionChecker)) {

			BlogsEntryServiceUtil.addEntry(
				RandomTestUtil.randomString(), RandomTestUtil.randomString(),
				RandomTestUtil.randomString(), RandomTestUtil.randomString(),
				RandomTestUtil.randomString(), 1, 1, 1990, 1, 1, true, false,
				new String[0], RandomTestUtil.randomString(), null, null,
				serviceContext);
		}
	}

	@Test(expected = PrincipalException.MustHavePermission.class)
	public void testAddEntryWithoutAddEntryPermission1() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group, _groupUser.getUserId());

		PermissionChecker permissionChecker =
			PermissionCheckerFactoryUtil.create(_groupUser);

		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				_groupUser, permissionChecker)) {

			BlogsEntryServiceUtil.addEntry(
				RandomTestUtil.randomString(), RandomTestUtil.randomString(),
				RandomTestUtil.randomString(), RandomTestUtil.randomString(), 1,
				1, 1990, 1, 1, true, false, new String[0],
				RandomTestUtil.randomString(), null, null, serviceContext);
		}
	}

	@Test(expected = PrincipalException.MustHavePermission.class)
	public void testAddEntryWithoutAddEntryPermission2() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group, _groupUser.getUserId());

		PermissionChecker permissionChecker =
			PermissionCheckerFactoryUtil.create(_groupUser);

		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				_groupUser, permissionChecker)) {

			BlogsEntryServiceUtil.addEntry(
				RandomTestUtil.randomString(), RandomTestUtil.randomString(),
				RandomTestUtil.randomString(), RandomTestUtil.randomString(),
				RandomTestUtil.randomString(), 1, 1, 1990, 1, 1, true, false,
				new String[0], RandomTestUtil.randomString(), null, null,
				serviceContext);
		}
	}

	@Test
	public void testDeleteEntryWithDeletePermission() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group, _user.getUserId());

		BlogsEntry entry = BlogsEntryLocalServiceUtil.addEntry(
			_user.getUserId(), "1", RandomTestUtil.randomString(), new Date(),
			serviceContext);

		Role siteMemberRole = RoleLocalServiceUtil.getRole(
			_company.getCompanyId(), RoleConstants.SITE_MEMBER);

		ResourcePermissionLocalServiceUtil.addResourcePermission(
			_group.getCompanyId(), "com.liferay.blogs.model.BlogsEntry",
			ResourceConstants.SCOPE_GROUP, String.valueOf(_group.getGroupId()),
			siteMemberRole.getRoleId(), ActionKeys.DELETE);

		PermissionChecker permissionChecker =
			PermissionCheckerFactoryUtil.create(_groupUser);

		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				_groupUser, permissionChecker)) {

			BlogsEntryServiceUtil.deleteEntry(entry.getEntryId());
		}
	}

	@Test(expected = PrincipalException.MustHavePermission.class)
	public void testDeleteEntryWithoutDeletePermission() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group, _user.getUserId());

		BlogsEntry entry = BlogsEntryLocalServiceUtil.addEntry(
			_user.getUserId(), "1", RandomTestUtil.randomString(), new Date(),
			serviceContext);

		PermissionChecker permissionChecker =
			PermissionCheckerFactoryUtil.create(_groupUser);

		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				_groupUser, permissionChecker)) {

			BlogsEntryServiceUtil.deleteEntry(entry.getEntryId());
		}
	}

	@Test
	public void testDeprecatedAddEntryWithAddEntryPermission()
		throws Exception {

		Role siteMemberRole = RoleLocalServiceUtil.getRole(
			_company.getCompanyId(), RoleConstants.SITE_MEMBER);

		ResourcePermissionLocalServiceUtil.addResourcePermission(
			_company.getCompanyId(), "com.liferay.blogs",
			ResourceConstants.SCOPE_GROUP, String.valueOf(_group.getGroupId()),
			siteMemberRole.getRoleId(), ActionKeys.ADD_ENTRY);

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group, _groupUser.getUserId());

		PermissionChecker permissionChecker =
			PermissionCheckerFactoryUtil.create(_groupUser);

		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				_groupUser, permissionChecker)) {

			BlogsEntryServiceUtil.addEntry(
				RandomTestUtil.randomString(), RandomTestUtil.randomString(),
				RandomTestUtil.randomString(), 1, 1, 1990, 1, 1, true, false,
				new String[0], false, StringPool.BLANK, StringPool.BLANK, null,
				serviceContext);
		}
	}

	@Test(expected = PrincipalException.MustHavePermission.class)
	public void testDeprecatedAddEntryWithoutAddEntryPermission()
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group, _groupUser.getUserId());

		PermissionChecker permissionChecker =
			PermissionCheckerFactoryUtil.create(_groupUser);

		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				_groupUser, permissionChecker)) {

			BlogsEntryServiceUtil.addEntry(
				RandomTestUtil.randomString(), RandomTestUtil.randomString(),
				RandomTestUtil.randomString(), 1, 1, 1990, 1, 1, true, false,
				new String[0], false, StringPool.BLANK, StringPool.BLANK, null,
				serviceContext);
		}
	}

	@Test(expected = PrincipalException.MustHavePermission.class)
	public void testDeprecatedUpdateEntryWithoutUpdatePermission()
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group, _user.getUserId());

		BlogsEntry entry = BlogsEntryLocalServiceUtil.addEntry(
			_user.getUserId(), "1", RandomTestUtil.randomString(), new Date(),
			serviceContext);

		PermissionChecker permissionChecker =
			PermissionCheckerFactoryUtil.create(_groupUser);

		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				_groupUser, permissionChecker)) {

			serviceContext = ServiceContextTestUtil.getServiceContext(
				_group, _groupUser.getUserId());

			BlogsEntryServiceUtil.updateEntry(
				entry.getEntryId(), RandomTestUtil.randomString(),
				RandomTestUtil.randomString(), RandomTestUtil.randomString(), 1,
				1, 1990, 1, 1, true, false, new String[0], false,
				StringPool.BLANK, StringPool.BLANK, null, serviceContext);
		}
	}

	@Test
	public void testDeprecatedUpdateEntryWithUpdatePermission()
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group, _user.getUserId());

		BlogsEntry entry = BlogsEntryLocalServiceUtil.addEntry(
			_user.getUserId(), "1", RandomTestUtil.randomString(), new Date(),
			serviceContext);

		Role siteMemberRole = RoleLocalServiceUtil.getRole(
			_company.getCompanyId(), RoleConstants.SITE_MEMBER);

		ResourcePermissionLocalServiceUtil.addResourcePermission(
			_group.getCompanyId(), "com.liferay.blogs.model.BlogsEntry",
			ResourceConstants.SCOPE_GROUP, String.valueOf(_group.getGroupId()),
			siteMemberRole.getRoleId(), ActionKeys.UPDATE);

		PermissionChecker permissionChecker =
			PermissionCheckerFactoryUtil.create(_groupUser);

		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				_groupUser, permissionChecker)) {

			serviceContext = ServiceContextTestUtil.getServiceContext(
				_group, _groupUser.getUserId());

			BlogsEntryServiceUtil.updateEntry(
				entry.getEntryId(), RandomTestUtil.randomString(),
				RandomTestUtil.randomString(), RandomTestUtil.randomString(), 1,
				1, 1990, 1, 1, true, false, new String[0], false,
				StringPool.BLANK, StringPool.BLANK, null, serviceContext);
		}
	}

	@Test
	public void testGetCompanyEntriesWithoutViewPermission() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group, _user.getUserId());

		BlogsEntry entry1 = BlogsEntryLocalServiceUtil.addEntry(
			_user.getUserId(), "1", RandomTestUtil.randomString(), new Date(),
			serviceContext);

		BlogsEntry entry2 = BlogsEntryLocalServiceUtil.addEntry(
			_user.getUserId(), "2", RandomTestUtil.randomString(), new Date(),
			serviceContext);

		BlogsEntry entry3 = BlogsEntryLocalServiceUtil.addEntry(
			_user.getUserId(), "3", RandomTestUtil.randomString(), new Date(),
			serviceContext);

		List<Role> roles = RoleLocalServiceUtil.getRoles(
			_company.getCompanyId());

		for (Role role : roles) {
			if (RoleConstants.OWNER.equals(role.getName())) {
				continue;
			}

			ResourcePermissionLocalServiceUtil.removeResourcePermission(
				_company.getCompanyId(), "com.liferay.blogs.model.BlogsEntry",
				ResourceConstants.SCOPE_INDIVIDUAL,
				String.valueOf(entry2.getEntryId()), role.getRoleId(),
				ActionKeys.VIEW);
		}

		PermissionChecker permissionChecker =
			PermissionCheckerFactoryUtil.create(_groupUser);

		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				_groupUser, permissionChecker)) {

			List<BlogsEntry> entries = BlogsEntryServiceUtil.getCompanyEntries(
				_company.getCompanyId(), new Date(),
				WorkflowConstants.STATUS_APPROVED, 100);

			Assert.assertEquals(entries.toString(), 2, entries.size());

			Assert.assertTrue(entries.contains(entry3));
			Assert.assertTrue(entries.contains(entry1));
		}
	}

	@Test
	public void testGetCompanyEntriesWithoutViewPermissionAndDisplayDate()
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group, _user.getUserId());

		Calendar calendar = Calendar.getInstance();

		calendar.set(Calendar.YEAR, 2000);

		BlogsEntry entry1 = BlogsEntryLocalServiceUtil.addEntry(
			_user.getUserId(), "1", RandomTestUtil.randomString(),
			calendar.getTime(), serviceContext);

		calendar.add(Calendar.HOUR, 2);

		BlogsEntry entry2 = BlogsEntryLocalServiceUtil.addEntry(
			_user.getUserId(), "2", RandomTestUtil.randomString(),
			calendar.getTime(), serviceContext);

		calendar.add(Calendar.HOUR, 2);

		BlogsEntryLocalServiceUtil.addEntry(
			_user.getUserId(), "3", RandomTestUtil.randomString(),
			calendar.getTime(), serviceContext);

		List<Role> roles = RoleLocalServiceUtil.getRoles(
			_company.getCompanyId());

		for (Role role : roles) {
			if (RoleConstants.OWNER.equals(role.getName())) {
				continue;
			}

			ResourcePermissionLocalServiceUtil.removeResourcePermission(
				_company.getCompanyId(), "com.liferay.blogs.model.BlogsEntry",
				ResourceConstants.SCOPE_INDIVIDUAL,
				String.valueOf(entry2.getEntryId()), role.getRoleId(),
				ActionKeys.VIEW);
		}

		PermissionChecker permissionChecker =
			PermissionCheckerFactoryUtil.create(_groupUser);

		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				_groupUser, permissionChecker)) {

			calendar = Calendar.getInstance();

			calendar.set(Calendar.YEAR, 2000);

			calendar.add(Calendar.HOUR, 3);

			List<BlogsEntry> entries = BlogsEntryServiceUtil.getCompanyEntries(
				_company.getCompanyId(), calendar.getTime(),
				WorkflowConstants.STATUS_APPROVED, 2);

			Assert.assertEquals(entries.toString(), 1, entries.size());

			Assert.assertTrue(entries.contains(entry1));
		}
	}

	@Test
	public void testGetCompanyEntriesWithoutViewPermissionAndMax()
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group, _user.getUserId());

		BlogsEntry entry1 = BlogsEntryLocalServiceUtil.addEntry(
			_user.getUserId(), "1", RandomTestUtil.randomString(), new Date(),
			serviceContext);

		BlogsEntry entry2 = BlogsEntryLocalServiceUtil.addEntry(
			_user.getUserId(), "2", RandomTestUtil.randomString(), new Date(),
			serviceContext);

		BlogsEntry entry3 = BlogsEntryLocalServiceUtil.addEntry(
			_user.getUserId(), "3", RandomTestUtil.randomString(), new Date(),
			serviceContext);

		List<Role> roles = RoleLocalServiceUtil.getRoles(
			_company.getCompanyId());

		for (Role role : roles) {
			if (RoleConstants.OWNER.equals(role.getName())) {
				continue;
			}

			ResourcePermissionLocalServiceUtil.removeResourcePermission(
				_company.getCompanyId(), "com.liferay.blogs.model.BlogsEntry",
				ResourceConstants.SCOPE_INDIVIDUAL,
				String.valueOf(entry2.getEntryId()), role.getRoleId(),
				ActionKeys.VIEW);
		}

		PermissionChecker permissionChecker =
			PermissionCheckerFactoryUtil.create(_groupUser);

		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				_groupUser, permissionChecker)) {

			List<BlogsEntry> entries = BlogsEntryServiceUtil.getCompanyEntries(
				_company.getCompanyId(), new Date(),
				WorkflowConstants.STATUS_APPROVED, 2);

			Assert.assertEquals(entries.toString(), 2, entries.size());

			Assert.assertTrue(entries.contains(entry3));
			Assert.assertTrue(entries.contains(entry1));
		}
	}

	@Test
	public void testGetCompanyEntriesWithViewPermission() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group, _user.getUserId());

		BlogsEntry entry1 = BlogsEntryLocalServiceUtil.addEntry(
			_user.getUserId(), "1", RandomTestUtil.randomString(), new Date(),
			serviceContext);

		BlogsEntry entry2 = BlogsEntryLocalServiceUtil.addEntry(
			_user.getUserId(), "2", RandomTestUtil.randomString(), new Date(),
			serviceContext);

		BlogsEntry entry3 = BlogsEntryLocalServiceUtil.addEntry(
			_user.getUserId(), "3", RandomTestUtil.randomString(), new Date(),
			serviceContext);

		PermissionChecker permissionChecker =
			PermissionCheckerFactoryUtil.create(_groupUser);

		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				_groupUser, permissionChecker)) {

			List<BlogsEntry> entries = BlogsEntryServiceUtil.getCompanyEntries(
				_company.getCompanyId(), new Date(),
				WorkflowConstants.STATUS_APPROVED, 100);

			Assert.assertEquals(entries.toString(), 3, entries.size());

			Assert.assertTrue(entries.contains(entry3));
			Assert.assertTrue(entries.contains(entry2));
			Assert.assertTrue(entries.contains(entry1));
		}
	}

	@Test
	public void testGetCompanyEntriesWithViewPermissionAndDisplayDate()
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group, _user.getUserId());

		Calendar calendar = Calendar.getInstance();

		calendar.set(Calendar.YEAR, 2000);

		BlogsEntry entry1 = BlogsEntryLocalServiceUtil.addEntry(
			_user.getUserId(), "1", RandomTestUtil.randomString(),
			calendar.getTime(), serviceContext);

		calendar.add(Calendar.HOUR, 2);

		BlogsEntry entry2 = BlogsEntryLocalServiceUtil.addEntry(
			_user.getUserId(), "2", RandomTestUtil.randomString(),
			calendar.getTime(), serviceContext);

		calendar.add(Calendar.HOUR, 2);

		BlogsEntryLocalServiceUtil.addEntry(
			_user.getUserId(), "3", RandomTestUtil.randomString(),
			calendar.getTime(), serviceContext);

		PermissionChecker permissionChecker =
			PermissionCheckerFactoryUtil.create(_groupUser);

		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				_groupUser, permissionChecker)) {

			calendar = Calendar.getInstance();

			calendar.set(Calendar.YEAR, 2000);

			calendar.add(Calendar.HOUR, 3);

			List<BlogsEntry> entries = BlogsEntryServiceUtil.getCompanyEntries(
				_company.getCompanyId(), calendar.getTime(),
				WorkflowConstants.STATUS_APPROVED, 2);

			Assert.assertEquals(entries.toString(), 2, entries.size());

			Assert.assertTrue(entries.contains(entry1));
			Assert.assertTrue(entries.contains(entry2));
		}
	}

	@Test
	public void testGetCompanyEntriesWithViewPermissionAndMax()
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group, _user.getUserId());

		BlogsEntryLocalServiceUtil.addEntry(
			_user.getUserId(), "1", RandomTestUtil.randomString(), new Date(),
			serviceContext);

		BlogsEntry entry2 = BlogsEntryLocalServiceUtil.addEntry(
			_user.getUserId(), "2", RandomTestUtil.randomString(), new Date(),
			serviceContext);

		BlogsEntry entry3 = BlogsEntryLocalServiceUtil.addEntry(
			_user.getUserId(), "3", RandomTestUtil.randomString(), new Date(),
			serviceContext);

		PermissionChecker permissionChecker =
			PermissionCheckerFactoryUtil.create(_groupUser);

		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				_groupUser, permissionChecker)) {

			List<BlogsEntry> entries = BlogsEntryServiceUtil.getCompanyEntries(
				_company.getCompanyId(), new Date(),
				WorkflowConstants.STATUS_APPROVED, 2);

			Assert.assertEquals(entries.toString(), 2, entries.size());

			Assert.assertTrue(entries.contains(entry3));
			Assert.assertTrue(entries.contains(entry2));
		}
	}

	@Test
	public void testGetEntriesPrevAndNext() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group, _user.getUserId());

		Calendar calendar = Calendar.getInstance();

		calendar.set(Calendar.YEAR, 2000);

		BlogsEntry entry1 = BlogsEntryLocalServiceUtil.addEntry(
			_user.getUserId(), "1", RandomTestUtil.randomString(),
			calendar.getTime(), serviceContext);

		calendar.add(Calendar.HOUR, 1);

		BlogsEntry entry2 = BlogsEntryLocalServiceUtil.addEntry(
			_user.getUserId(), "2", RandomTestUtil.randomString(),
			calendar.getTime(), serviceContext);

		calendar.add(Calendar.HOUR, 1);

		BlogsEntry entry3 = BlogsEntryLocalServiceUtil.addEntry(
			_user.getUserId(), "3", RandomTestUtil.randomString(),
			serviceContext);

		BlogsEntry[] prevAndNext = BlogsEntryServiceUtil.getEntriesPrevAndNext(
			entry2.getEntryId());

		Assert.assertEquals(
			StringBundler.concat(
				"The previous entry relative to entry ", entry2.getEntryId(),
				" should be ", entry1.getEntryId()),
			entry1, prevAndNext[0]);
		Assert.assertEquals(
			StringBundler.concat(
				"The current entry relative to entry ", entry2.getEntryId(),
				" should be ", entry2.getEntryId()),
			entry2, prevAndNext[1]);
		Assert.assertEquals(
			StringBundler.concat(
				"The next entry relative to entry ", entry2.getEntryId(),
				" should be ", entry3.getEntryId()),
			entry3, prevAndNext[2]);
	}

	@Test
	public void testGetEntriesPrevAndNextWithOnlyNext() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group, _user.getUserId());

		Calendar calendar = Calendar.getInstance();

		calendar.set(Calendar.YEAR, 2000);

		BlogsEntry entry1 = BlogsEntryLocalServiceUtil.addEntry(
			_user.getUserId(), "1", RandomTestUtil.randomString(),
			calendar.getTime(), serviceContext);

		calendar.add(Calendar.HOUR, 1);

		BlogsEntry entry2 = BlogsEntryLocalServiceUtil.addEntry(
			_user.getUserId(), "2", RandomTestUtil.randomString(),
			calendar.getTime(), serviceContext);

		BlogsEntry[] prevAndNext = BlogsEntryServiceUtil.getEntriesPrevAndNext(
			entry1.getEntryId());

		Assert.assertNull(
			"The previous entry relative to entry " + entry1.getEntryId() +
				" should be null",
			prevAndNext[0]);
		Assert.assertEquals(
			StringBundler.concat(
				"The current entry relative to entry ", entry1.getEntryId(),
				" should be ", entry1.getEntryId()),
			entry1, prevAndNext[1]);
		Assert.assertEquals(
			StringBundler.concat(
				"The next entry relative to entry ", entry1.getEntryId(),
				" should be ", entry2.getEntryId()),
			entry2, prevAndNext[2]);
	}

	@Test
	public void testGetEntriesPrevAndNextWithOnlyPrev() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group, _user.getUserId());

		Calendar calendar = Calendar.getInstance();

		calendar.set(Calendar.YEAR, 2000);

		BlogsEntry entry1 = BlogsEntryLocalServiceUtil.addEntry(
			_user.getUserId(), "1", RandomTestUtil.randomString(),
			calendar.getTime(), serviceContext);

		calendar.add(Calendar.HOUR, 1);

		BlogsEntry entry2 = BlogsEntryLocalServiceUtil.addEntry(
			_user.getUserId(), "2", RandomTestUtil.randomString(),
			calendar.getTime(), serviceContext);

		BlogsEntry[] prevAndNext = BlogsEntryServiceUtil.getEntriesPrevAndNext(
			entry2.getEntryId());

		Assert.assertEquals(
			StringBundler.concat(
				"The previous entry relative to entry ", entry2.getEntryId(),
				" should be ", entry1.getEntryId()),
			entry1, prevAndNext[0]);
		Assert.assertEquals(
			StringBundler.concat(
				"The current entry relative to entry ", entry2.getEntryId(),
				" should be ", entry2.getEntryId()),
			entry2, prevAndNext[1]);
		Assert.assertNull(
			"The next entry relative to entry " + entry1.getEntryId() +
				" should be null",
			prevAndNext[2]);
	}

	@Test(expected = PrincipalException.MustHavePermission.class)
	public void testGetEntriesPrevAndNextWithoutEntryViewPermission()
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group, _user.getUserId());

		serviceContext.setAddGroupPermissions(false);
		serviceContext.setAddGuestPermissions(false);

		Calendar calendar = Calendar.getInstance();

		calendar.set(Calendar.YEAR, 2000);

		BlogsEntryLocalServiceUtil.addEntry(
			_user.getUserId(), "1", RandomTestUtil.randomString(),
			calendar.getTime(), serviceContext);

		calendar.add(Calendar.HOUR, 1);

		BlogsEntry entry2 = BlogsEntryLocalServiceUtil.addEntry(
			_user.getUserId(), "2", RandomTestUtil.randomString(),
			calendar.getTime(), serviceContext);

		calendar.add(Calendar.HOUR, 1);

		BlogsEntryLocalServiceUtil.addEntry(
			_user.getUserId(), "3", RandomTestUtil.randomString(),
			calendar.getTime(), serviceContext);

		PermissionChecker permissionChecker =
			PermissionCheckerFactoryUtil.create(_groupUser);

		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				_groupUser, permissionChecker)) {

			BlogsEntryServiceUtil.getEntriesPrevAndNext(entry2.getEntryId());
		}
	}

	@Test
	public void testGetEntriesPrevAndNextWithoutNextViewPermission()
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group, _user.getUserId());

		Calendar calendar = Calendar.getInstance();

		calendar.set(Calendar.YEAR, 2000);

		BlogsEntry entry1 = BlogsEntryLocalServiceUtil.addEntry(
			_user.getUserId(), "1", RandomTestUtil.randomString(),
			calendar.getTime(), serviceContext);

		calendar.add(Calendar.HOUR, 1);

		BlogsEntry entry2 = BlogsEntryLocalServiceUtil.addEntry(
			_user.getUserId(), "2", RandomTestUtil.randomString(),
			calendar.getTime(), serviceContext);

		serviceContext.setAddGroupPermissions(false);
		serviceContext.setAddGuestPermissions(false);

		calendar.add(Calendar.HOUR, 1);

		BlogsEntryLocalServiceUtil.addEntry(
			_user.getUserId(), "3", RandomTestUtil.randomString(),
			calendar.getTime(), serviceContext);

		PermissionChecker permissionChecker =
			PermissionCheckerFactoryUtil.create(_groupUser);

		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				_groupUser, permissionChecker)) {

			BlogsEntry[] prevAndNext =
				BlogsEntryServiceUtil.getEntriesPrevAndNext(
					entry2.getEntryId());

			Assert.assertEquals(
				StringBundler.concat(
					"The previous entry relative to entry ",
					entry2.getEntryId(), " should be ", entry1.getEntryId()),
				entry1, prevAndNext[0]);
			Assert.assertEquals(
				StringBundler.concat(
					"The current entry relative to entry ", entry2.getEntryId(),
					" should be ", entry2.getEntryId()),
				entry2, prevAndNext[1]);
			Assert.assertNull(
				"The next entry relative to entry " + entry2.getEntryId() +
					" should be null",
				prevAndNext[2]);
		}
	}

	@Test
	public void testGetEntriesPrevAndNextWithoutPrevViewPermission()
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group, _user.getUserId());

		serviceContext.setAddGroupPermissions(false);
		serviceContext.setAddGuestPermissions(false);

		Calendar calendar = Calendar.getInstance();

		calendar.set(Calendar.YEAR, 2000);

		BlogsEntryLocalServiceUtil.addEntry(
			_user.getUserId(), "1", RandomTestUtil.randomString(),
			calendar.getTime(), serviceContext);

		serviceContext.setAddGroupPermissions(true);
		serviceContext.setAddGuestPermissions(true);

		calendar.add(Calendar.HOUR, 1);

		BlogsEntry entry2 = BlogsEntryLocalServiceUtil.addEntry(
			_user.getUserId(), "2", RandomTestUtil.randomString(),
			calendar.getTime(), serviceContext);

		calendar.add(Calendar.HOUR, 1);

		BlogsEntry entry3 = BlogsEntryLocalServiceUtil.addEntry(
			_user.getUserId(), "3", RandomTestUtil.randomString(),
			calendar.getTime(), serviceContext);

		PermissionChecker permissionChecker =
			PermissionCheckerFactoryUtil.create(_groupUser);

		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				_groupUser, permissionChecker)) {

			BlogsEntry[] prevAndNext =
				BlogsEntryServiceUtil.getEntriesPrevAndNext(
					entry2.getEntryId());

			Assert.assertNull(
				"The previous entry relative to entry " + entry2.getEntryId() +
					" should be null",
				prevAndNext[0]);
			Assert.assertEquals(
				StringBundler.concat(
					"The current entry relative to entry ", entry2.getEntryId(),
					" should be ", entry2.getEntryId()),
				entry2, prevAndNext[1]);
			Assert.assertEquals(
				StringBundler.concat(
					"The next entry relative to entry ", entry2.getEntryId(),
					" should be ", entry3.getEntryId()),
				entry3, prevAndNext[2]);
		}
	}

	@Test(expected = PrincipalException.MustHavePermission.class)
	public void testGetEntryWithoutViewPermission() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group, _user.getUserId());

		BlogsEntry entry = BlogsEntryLocalServiceUtil.addEntry(
			_user.getUserId(), "1", RandomTestUtil.randomString(), new Date(),
			serviceContext);

		List<Role> roles = RoleLocalServiceUtil.getRoles(
			_company.getCompanyId());

		for (Role role : roles) {
			if (RoleConstants.OWNER.equals(role.getName())) {
				continue;
			}

			ResourcePermissionLocalServiceUtil.removeResourcePermission(
				_company.getCompanyId(), "com.liferay.blogs.model.BlogsEntry",
				ResourceConstants.SCOPE_INDIVIDUAL,
				String.valueOf(entry.getEntryId()), role.getRoleId(),
				ActionKeys.VIEW);
		}

		PermissionChecker permissionChecker =
			PermissionCheckerFactoryUtil.create(_groupUser);

		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				_groupUser, permissionChecker)) {

			BlogsEntryServiceUtil.getEntry(entry.getEntryId());
		}
	}

	@Test(expected = PrincipalException.MustHavePermission.class)
	public void testGetEntryWithoutViewPermission2() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group, _user.getUserId());

		BlogsEntry entry = BlogsEntryLocalServiceUtil.addEntry(
			_user.getUserId(), "1", RandomTestUtil.randomString(), new Date(),
			serviceContext);

		List<Role> roles = RoleLocalServiceUtil.getRoles(
			_company.getCompanyId());

		for (Role role : roles) {
			if (RoleConstants.OWNER.equals(role.getName())) {
				continue;
			}

			ResourcePermissionLocalServiceUtil.removeResourcePermission(
				_company.getCompanyId(), "com.liferay.blogs.model.BlogsEntry",
				ResourceConstants.SCOPE_INDIVIDUAL,
				String.valueOf(entry.getEntryId()), role.getRoleId(),
				ActionKeys.VIEW);
		}

		PermissionChecker permissionChecker =
			PermissionCheckerFactoryUtil.create(_groupUser);

		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				_groupUser, permissionChecker)) {

			BlogsEntryServiceUtil.getEntry(
				entry.getGroupId(), entry.getUrlTitle());
		}
	}

	@Test
	public void testGetEntryWithViewPermission() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group, _user.getUserId());

		BlogsEntry entry = BlogsEntryLocalServiceUtil.addEntry(
			_user.getUserId(), "1", RandomTestUtil.randomString(), new Date(),
			serviceContext);

		PermissionChecker permissionChecker =
			PermissionCheckerFactoryUtil.create(_groupUser);

		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				_groupUser, permissionChecker)) {

			BlogsEntryServiceUtil.getEntry(entry.getEntryId());
		}
	}

	@Test
	public void testGetEntryWithViewPermission2() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group, _user.getUserId());

		BlogsEntry entry = BlogsEntryLocalServiceUtil.addEntry(
			_user.getUserId(), "1", RandomTestUtil.randomString(), new Date(),
			serviceContext);

		PermissionChecker permissionChecker =
			PermissionCheckerFactoryUtil.create(_groupUser);

		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				_groupUser, permissionChecker)) {

			BlogsEntryServiceUtil.getEntry(
				entry.getGroupId(), entry.getUrlTitle());
		}
	}

	@Test
	public void testGetGroupEntriesWithoutViewPermission() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group, _user.getUserId());

		BlogsEntry entry1 = BlogsEntryLocalServiceUtil.addEntry(
			_user.getUserId(), "1", RandomTestUtil.randomString(), new Date(),
			serviceContext);

		BlogsEntry entry2 = BlogsEntryLocalServiceUtil.addEntry(
			_user.getUserId(), "2", RandomTestUtil.randomString(), new Date(),
			serviceContext);

		BlogsEntry entry3 = BlogsEntryLocalServiceUtil.addEntry(
			_user.getUserId(), "3", RandomTestUtil.randomString(), new Date(),
			serviceContext);

		List<Role> roles = RoleLocalServiceUtil.getRoles(
			_company.getCompanyId());

		for (Role role : roles) {
			if (RoleConstants.OWNER.equals(role.getName())) {
				continue;
			}

			ResourcePermissionLocalServiceUtil.removeResourcePermission(
				_company.getCompanyId(), "com.liferay.blogs.model.BlogsEntry",
				ResourceConstants.SCOPE_INDIVIDUAL,
				String.valueOf(entry2.getEntryId()), role.getRoleId(),
				ActionKeys.VIEW);
		}

		PermissionChecker permissionChecker =
			PermissionCheckerFactoryUtil.create(_groupUser);

		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				_groupUser, permissionChecker)) {

			List<BlogsEntry> entries = BlogsEntryServiceUtil.getGroupEntries(
				_group.getGroupId(), new Date(),
				WorkflowConstants.STATUS_APPROVED, 100);

			Assert.assertEquals(entries.toString(), 2, entries.size());

			Assert.assertTrue(entries.contains(entry3));
			Assert.assertTrue(entries.contains(entry1));
		}
	}

	@Test
	public void testGetGroupEntriesWithoutViewPermissionAndDisplayDate()
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group, _user.getUserId());

		Calendar calendar = Calendar.getInstance();

		calendar.set(Calendar.YEAR, 2000);

		BlogsEntry entry1 = BlogsEntryLocalServiceUtil.addEntry(
			_user.getUserId(), "1", RandomTestUtil.randomString(),
			calendar.getTime(), serviceContext);

		calendar.add(Calendar.HOUR, 2);

		BlogsEntry entry2 = BlogsEntryLocalServiceUtil.addEntry(
			_user.getUserId(), "2", RandomTestUtil.randomString(),
			calendar.getTime(), serviceContext);

		calendar.add(Calendar.HOUR, 2);

		BlogsEntryLocalServiceUtil.addEntry(
			_user.getUserId(), "3", RandomTestUtil.randomString(),
			calendar.getTime(), serviceContext);

		List<Role> roles = RoleLocalServiceUtil.getRoles(
			_company.getCompanyId());

		for (Role role : roles) {
			if (RoleConstants.OWNER.equals(role.getName())) {
				continue;
			}

			ResourcePermissionLocalServiceUtil.removeResourcePermission(
				_company.getCompanyId(), "com.liferay.blogs.model.BlogsEntry",
				ResourceConstants.SCOPE_INDIVIDUAL,
				String.valueOf(entry2.getEntryId()), role.getRoleId(),
				ActionKeys.VIEW);
		}

		PermissionChecker permissionChecker =
			PermissionCheckerFactoryUtil.create(_groupUser);

		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				_groupUser, permissionChecker)) {

			calendar = Calendar.getInstance();

			calendar.set(Calendar.YEAR, 2000);

			calendar.add(Calendar.HOUR, 3);

			List<BlogsEntry> entries = BlogsEntryServiceUtil.getGroupEntries(
				_group.getGroupId(), calendar.getTime(),
				WorkflowConstants.STATUS_APPROVED, 2);

			Assert.assertEquals(entries.toString(), 1, entries.size());

			Assert.assertTrue(entries.contains(entry1));
		}
	}

	@Test
	public void testGetGroupEntriesWithoutViewPermissionAndMax()
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group, _user.getUserId());

		BlogsEntry entry1 = BlogsEntryLocalServiceUtil.addEntry(
			_user.getUserId(), "1", RandomTestUtil.randomString(), new Date(),
			serviceContext);

		BlogsEntry entry2 = BlogsEntryLocalServiceUtil.addEntry(
			_user.getUserId(), "2", RandomTestUtil.randomString(), new Date(),
			serviceContext);

		BlogsEntry entry3 = BlogsEntryLocalServiceUtil.addEntry(
			_user.getUserId(), "3", RandomTestUtil.randomString(), new Date(),
			serviceContext);

		List<Role> roles = RoleLocalServiceUtil.getRoles(
			_company.getCompanyId());

		for (Role role : roles) {
			if (RoleConstants.OWNER.equals(role.getName())) {
				continue;
			}

			ResourcePermissionLocalServiceUtil.removeResourcePermission(
				_company.getCompanyId(), "com.liferay.blogs.model.BlogsEntry",
				ResourceConstants.SCOPE_INDIVIDUAL,
				String.valueOf(entry2.getEntryId()), role.getRoleId(),
				ActionKeys.VIEW);
		}

		PermissionChecker permissionChecker =
			PermissionCheckerFactoryUtil.create(_groupUser);

		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				_groupUser, permissionChecker)) {

			List<BlogsEntry> entries = BlogsEntryServiceUtil.getGroupEntries(
				_group.getGroupId(), new Date(),
				WorkflowConstants.STATUS_APPROVED, 2);

			Assert.assertEquals(entries.toString(), 2, entries.size());

			Assert.assertTrue(entries.contains(entry3));
			Assert.assertTrue(entries.contains(entry1));
		}
	}

	@Test
	public void testGetGroupEntriesWithViewPermission() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group, _user.getUserId());

		BlogsEntry entry1 = BlogsEntryLocalServiceUtil.addEntry(
			_user.getUserId(), "1", RandomTestUtil.randomString(), new Date(),
			serviceContext);

		BlogsEntry entry2 = BlogsEntryLocalServiceUtil.addEntry(
			_user.getUserId(), "2", RandomTestUtil.randomString(), new Date(),
			serviceContext);

		BlogsEntry entry3 = BlogsEntryLocalServiceUtil.addEntry(
			_user.getUserId(), "3", RandomTestUtil.randomString(), new Date(),
			serviceContext);

		PermissionChecker permissionChecker =
			PermissionCheckerFactoryUtil.create(_groupUser);

		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				_groupUser, permissionChecker)) {

			List<BlogsEntry> entries = BlogsEntryServiceUtil.getGroupEntries(
				_group.getGroupId(), new Date(),
				WorkflowConstants.STATUS_APPROVED, 100);

			Assert.assertEquals(entries.toString(), 3, entries.size());

			Assert.assertTrue(entries.contains(entry3));
			Assert.assertTrue(entries.contains(entry2));
			Assert.assertTrue(entries.contains(entry1));
		}
	}

	@Test
	public void testGetGroupEntriesWithViewPermissionAndDisplayDate()
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group, _user.getUserId());

		Calendar calendar = Calendar.getInstance();

		calendar.set(Calendar.YEAR, 2000);

		BlogsEntry entry1 = BlogsEntryLocalServiceUtil.addEntry(
			_user.getUserId(), "1", RandomTestUtil.randomString(),
			calendar.getTime(), serviceContext);

		calendar.add(Calendar.HOUR, 2);

		BlogsEntry entry2 = BlogsEntryLocalServiceUtil.addEntry(
			_user.getUserId(), "2", RandomTestUtil.randomString(),
			calendar.getTime(), serviceContext);

		calendar.add(Calendar.HOUR, 2);

		BlogsEntryLocalServiceUtil.addEntry(
			_user.getUserId(), "3", RandomTestUtil.randomString(),
			calendar.getTime(), serviceContext);

		PermissionChecker permissionChecker =
			PermissionCheckerFactoryUtil.create(_groupUser);

		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				_groupUser, permissionChecker)) {

			calendar = Calendar.getInstance();

			calendar.set(Calendar.YEAR, 2000);

			calendar.add(Calendar.HOUR, 3);

			List<BlogsEntry> entries = BlogsEntryServiceUtil.getGroupEntries(
				_group.getGroupId(), calendar.getTime(),
				WorkflowConstants.STATUS_APPROVED, 2);

			Assert.assertEquals(entries.toString(), 2, entries.size());

			Assert.assertTrue(entries.contains(entry1));
			Assert.assertTrue(entries.contains(entry2));
		}
	}

	@Test
	public void testGetGroupEntriesWithViewPermissionAndMax() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group, _user.getUserId());

		BlogsEntryLocalServiceUtil.addEntry(
			_user.getUserId(), "1", RandomTestUtil.randomString(), new Date(),
			serviceContext);

		BlogsEntry entry2 = BlogsEntryLocalServiceUtil.addEntry(
			_user.getUserId(), "2", RandomTestUtil.randomString(), new Date(),
			serviceContext);

		BlogsEntry entry3 = BlogsEntryLocalServiceUtil.addEntry(
			_user.getUserId(), "3", RandomTestUtil.randomString(), new Date(),
			serviceContext);

		PermissionChecker permissionChecker =
			PermissionCheckerFactoryUtil.create(_groupUser);

		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				_groupUser, permissionChecker)) {

			List<BlogsEntry> entries = BlogsEntryServiceUtil.getGroupEntries(
				_group.getGroupId(), new Date(),
				WorkflowConstants.STATUS_APPROVED, 2);

			Assert.assertEquals(entries.toString(), 2, entries.size());

			Assert.assertTrue(entries.contains(entry3));
			Assert.assertTrue(entries.contains(entry2));
		}
	}

	@Test
	public void testMoveEntryToTrashWithDeletePermission() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group, _user.getUserId());

		BlogsEntry entry = BlogsEntryLocalServiceUtil.addEntry(
			_user.getUserId(), "1", RandomTestUtil.randomString(), new Date(),
			serviceContext);

		Role siteMemberRole = RoleLocalServiceUtil.getRole(
			_company.getCompanyId(), RoleConstants.SITE_MEMBER);

		ResourcePermissionLocalServiceUtil.addResourcePermission(
			_group.getCompanyId(), "com.liferay.blogs.model.BlogsEntry",
			ResourceConstants.SCOPE_GROUP, String.valueOf(_group.getGroupId()),
			siteMemberRole.getRoleId(), ActionKeys.DELETE);

		PermissionChecker permissionChecker =
			PermissionCheckerFactoryUtil.create(_groupUser);

		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				_groupUser, permissionChecker)) {

			BlogsEntryServiceUtil.moveEntryToTrash(entry.getEntryId());
		}
	}

	@Test(expected = PrincipalException.MustHavePermission.class)
	public void testMoveEntryToTrashWithoutDeletePermission() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group, _user.getUserId());

		BlogsEntry entry = BlogsEntryLocalServiceUtil.addEntry(
			_user.getUserId(), "1", RandomTestUtil.randomString(), new Date(),
			serviceContext);

		PermissionChecker permissionChecker =
			PermissionCheckerFactoryUtil.create(_groupUser);

		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				_groupUser, permissionChecker)) {

			BlogsEntryServiceUtil.moveEntryToTrash(entry.getEntryId());
		}
	}

	@Test
	public void testOwnerCanDeleteEntry() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group, _groupUser.getUserId());

		BlogsEntry entry = BlogsEntryLocalServiceUtil.addEntry(
			_groupUser.getUserId(), "1", RandomTestUtil.randomString(),
			new Date(), serviceContext);

		PermissionChecker permissionChecker =
			PermissionCheckerFactoryUtil.create(_groupUser);

		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				_groupUser, permissionChecker)) {

			BlogsEntryServiceUtil.deleteEntry(entry.getEntryId());
		}
	}

	@Test
	public void testOwnerCanMoveEntryToTrash() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group, _groupUser.getUserId());

		BlogsEntry entry = BlogsEntryLocalServiceUtil.addEntry(
			_groupUser.getUserId(), "1", RandomTestUtil.randomString(),
			new Date(), serviceContext);

		PermissionChecker permissionChecker =
			PermissionCheckerFactoryUtil.create(_groupUser);

		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				_groupUser, permissionChecker)) {

			BlogsEntryServiceUtil.moveEntryToTrash(entry.getEntryId());
		}
	}

	@Test
	public void testOwnerCanRestoreEntryFromTrash() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group, _groupUser.getUserId());

		BlogsEntry entry = BlogsEntryLocalServiceUtil.addEntry(
			_groupUser.getUserId(), "1", RandomTestUtil.randomString(),
			new Date(), serviceContext);

		BlogsEntryLocalServiceUtil.moveEntryToTrash(
			_groupUser.getUserId(), entry);

		PermissionChecker permissionChecker =
			PermissionCheckerFactoryUtil.create(_groupUser);

		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				_groupUser, permissionChecker)) {

			BlogsEntryServiceUtil.restoreEntryFromTrash(entry.getEntryId());
		}
	}

	@Test
	public void testOwnerCanUpdateEntry() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group, _groupUser.getUserId());

		BlogsEntry entry = BlogsEntryLocalServiceUtil.addEntry(
			_groupUser.getUserId(), "1", RandomTestUtil.randomString(),
			new Date(), serviceContext);

		serviceContext = ServiceContextTestUtil.getServiceContext(
			_group, _groupUser.getUserId());

		PermissionChecker permissionChecker =
			PermissionCheckerFactoryUtil.create(_groupUser);

		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				_groupUser, permissionChecker)) {

			BlogsEntryServiceUtil.updateEntry(
				entry.getEntryId(), RandomTestUtil.randomString(),
				RandomTestUtil.randomString(), RandomTestUtil.randomString(),
				RandomTestUtil.randomString(), 1, 1, 1990, 1, 1, true, false,
				new String[0], RandomTestUtil.randomString(), null, null,
				serviceContext);
		}
	}

	@Test
	public void testRestoreEntryFromTrashWithDeletePermission()
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group, _user.getUserId());

		BlogsEntry entry = BlogsEntryLocalServiceUtil.addEntry(
			_user.getUserId(), "1", RandomTestUtil.randomString(), new Date(),
			serviceContext);

		BlogsEntryLocalServiceUtil.moveEntryToTrash(_user.getUserId(), entry);

		Role siteMemberRole = RoleLocalServiceUtil.getRole(
			_company.getCompanyId(), RoleConstants.SITE_MEMBER);

		ResourcePermissionLocalServiceUtil.addResourcePermission(
			_group.getCompanyId(), "com.liferay.blogs.model.BlogsEntry",
			ResourceConstants.SCOPE_GROUP, String.valueOf(_group.getGroupId()),
			siteMemberRole.getRoleId(), ActionKeys.DELETE);

		PermissionChecker permissionChecker =
			PermissionCheckerFactoryUtil.create(_groupUser);

		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				_groupUser, permissionChecker)) {

			BlogsEntryServiceUtil.restoreEntryFromTrash(entry.getEntryId());
		}
	}

	@Test(expected = PrincipalException.MustHavePermission.class)
	public void testRestoreEntryFromTrashWithoutDeletePermission()
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group, _user.getUserId());

		BlogsEntry entry = BlogsEntryLocalServiceUtil.addEntry(
			_user.getUserId(), "1", RandomTestUtil.randomString(), new Date(),
			serviceContext);

		BlogsEntryLocalServiceUtil.moveEntryToTrash(_user.getUserId(), entry);

		PermissionChecker permissionChecker =
			PermissionCheckerFactoryUtil.create(_groupUser);

		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				_groupUser, permissionChecker)) {

			BlogsEntryServiceUtil.restoreEntryFromTrash(entry.getEntryId());
		}
	}

	@Test(expected = PrincipalException.MustHavePermission.class)
	public void testSubscribeEntryWithoutSubscribePermission()
		throws Exception {

		User user = UserTestUtil.addUser(_company);

		PermissionChecker permissionChecker =
			PermissionCheckerFactoryUtil.create(user);

		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				user, permissionChecker)) {

			BlogsEntryServiceUtil.subscribe(_group.getGroupId());
		}
	}

	@Test
	public void testSubscribeEntryWithSubscribePermission() throws Exception {
		PermissionChecker permissionChecker =
			PermissionCheckerFactoryUtil.create(_groupUser);

		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				_groupUser, permissionChecker)) {

			BlogsEntryServiceUtil.subscribe(_group.getGroupId());
		}
	}

	@Test(expected = PrincipalException.MustHavePermission.class)
	public void testUnsubscribeEntryWithoutSubscribePermission()
		throws Exception {

		User user = UserTestUtil.addUser(_company);

		PermissionChecker permissionChecker =
			PermissionCheckerFactoryUtil.create(user);

		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				user, permissionChecker)) {

			BlogsEntryServiceUtil.unsubscribe(_group.getGroupId());
		}
	}

	@Test
	public void testUnsubscribeEntryWithSubscribePermission() throws Exception {
		PermissionChecker permissionChecker =
			PermissionCheckerFactoryUtil.create(_groupUser);

		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				_groupUser, permissionChecker)) {

			BlogsEntryServiceUtil.unsubscribe(_group.getGroupId());
		}
	}

	@Test(expected = PrincipalException.MustHavePermission.class)
	public void testUpdateEntryWithoutUpdatePermission1() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group, _user.getUserId());

		BlogsEntry entry = BlogsEntryLocalServiceUtil.addEntry(
			_user.getUserId(), "1", RandomTestUtil.randomString(), new Date(),
			serviceContext);

		PermissionChecker permissionChecker =
			PermissionCheckerFactoryUtil.create(_groupUser);

		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				_groupUser, permissionChecker)) {

			serviceContext = ServiceContextTestUtil.getServiceContext(
				_group, _groupUser.getUserId());

			BlogsEntryServiceUtil.updateEntry(
				entry.getEntryId(), RandomTestUtil.randomString(),
				RandomTestUtil.randomString(), RandomTestUtil.randomString(),
				RandomTestUtil.randomString(), 1, 1, 1990, 1, 1, true, false,
				new String[0], RandomTestUtil.randomString(), null, null,
				serviceContext);
		}
	}

	@Test(expected = PrincipalException.MustHavePermission.class)
	public void testUpdateEntryWithoutUpdatePermission2() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group, _user.getUserId());

		BlogsEntry entry = BlogsEntryLocalServiceUtil.addEntry(
			_user.getUserId(), "1", RandomTestUtil.randomString(), new Date(),
			serviceContext);

		PermissionChecker permissionChecker =
			PermissionCheckerFactoryUtil.create(_groupUser);

		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				_groupUser, permissionChecker)) {

			serviceContext = ServiceContextTestUtil.getServiceContext(
				_group, _groupUser.getUserId());

			BlogsEntryServiceUtil.updateEntry(
				entry.getEntryId(), RandomTestUtil.randomString(),
				RandomTestUtil.randomString(), RandomTestUtil.randomString(),
				RandomTestUtil.randomString(), RandomTestUtil.randomString(), 1,
				1, 1990, 1, 1, true, false, new String[0],
				RandomTestUtil.randomString(), null, null, serviceContext);
		}
	}

	@Test
	public void testUpdateEntryWithUpdatePermission1() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group, _user.getUserId());

		BlogsEntry entry = BlogsEntryLocalServiceUtil.addEntry(
			_user.getUserId(), "1", RandomTestUtil.randomString(), new Date(),
			serviceContext);

		Role siteMemberRole = RoleLocalServiceUtil.getRole(
			_company.getCompanyId(), RoleConstants.SITE_MEMBER);

		ResourcePermissionLocalServiceUtil.addResourcePermission(
			_group.getCompanyId(), "com.liferay.blogs.model.BlogsEntry",
			ResourceConstants.SCOPE_GROUP, String.valueOf(_group.getGroupId()),
			siteMemberRole.getRoleId(), ActionKeys.UPDATE);

		PermissionChecker permissionChecker =
			PermissionCheckerFactoryUtil.create(_groupUser);

		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				_groupUser, permissionChecker)) {

			serviceContext = ServiceContextTestUtil.getServiceContext(
				_group, _groupUser.getUserId());

			BlogsEntryServiceUtil.updateEntry(
				entry.getEntryId(), RandomTestUtil.randomString(),
				RandomTestUtil.randomString(), RandomTestUtil.randomString(),
				RandomTestUtil.randomString(), 1, 1, 1990, 1, 1, true, false,
				new String[0], RandomTestUtil.randomString(), null, null,
				serviceContext);
		}
	}

	@Test
	public void testUpdateEntryWithUpdatePermission2() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group, _user.getUserId());

		BlogsEntry entry = BlogsEntryLocalServiceUtil.addEntry(
			_user.getUserId(), "1", RandomTestUtil.randomString(), new Date(),
			serviceContext);

		Role siteMemberRole = RoleLocalServiceUtil.getRole(
			_company.getCompanyId(), RoleConstants.SITE_MEMBER);

		ResourcePermissionLocalServiceUtil.addResourcePermission(
			_group.getCompanyId(), "com.liferay.blogs.model.BlogsEntry",
			ResourceConstants.SCOPE_GROUP, String.valueOf(_group.getGroupId()),
			siteMemberRole.getRoleId(), ActionKeys.UPDATE);

		PermissionChecker permissionChecker =
			PermissionCheckerFactoryUtil.create(_groupUser);

		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				_groupUser, permissionChecker)) {

			serviceContext = ServiceContextTestUtil.getServiceContext(
				_group, _groupUser.getUserId());

			BlogsEntryServiceUtil.updateEntry(
				entry.getEntryId(), RandomTestUtil.randomString(),
				RandomTestUtil.randomString(), RandomTestUtil.randomString(),
				RandomTestUtil.randomString(), RandomTestUtil.randomString(), 1,
				1, 1990, 1, 1, true, false, new String[0],
				RandomTestUtil.randomString(), null, null, serviceContext);
		}
	}

	@DeleteAfterTestRun
	private Company _company;

	private Group _group;
	private User _groupUser;
	private User _user;

}