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
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.RoleConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.context.ContextUserReplace;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.service.test.ServiceTestUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerTestRule;

import java.util.Calendar;

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
			PermissionCheckerTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();
		_user = TestPropsValues.getUser();

		_groupUser = UserTestUtil.addGroupUser(
			_group, RoleConstants.POWER_USER);

		ServiceTestUtil.setUser(TestPropsValues.getUser());
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
			"The previous entry relative to entry " + entry2.getEntryId() +
				" should be " + entry1.getEntryId(),
			entry1, prevAndNext[0]);
		Assert.assertEquals(
			"The current entry relative to entry " + entry2.getEntryId() +
				" should be " + entry2.getEntryId(),
			entry2, prevAndNext[1]);
		Assert.assertEquals(
			"The next entry relative to entry " + entry2.getEntryId() +
				" should be " + entry3.getEntryId(),
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
			"The current entry relative to entry " + entry1.getEntryId() +
				" should be " + entry1.getEntryId(),
			entry1, prevAndNext[1]);
		Assert.assertEquals(
			"The next entry relative to entry " + entry1.getEntryId() +
				" should be " + entry2.getEntryId(),
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
			"The previous entry relative to entry " + entry2.getEntryId() +
				" should be " + entry1.getEntryId(),
			entry1, prevAndNext[0]);
		Assert.assertEquals(
			"The current entry relative to entry " + entry2.getEntryId() +
				" should be " + entry2.getEntryId(),
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

		try (ContextUserReplace contextUserReplace =
				new ContextUserReplace(_groupUser, permissionChecker)) {

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

		try (ContextUserReplace contextUserReplace =
				new ContextUserReplace(_groupUser, permissionChecker)) {

			BlogsEntry[] prevAndNext =
				BlogsEntryServiceUtil.getEntriesPrevAndNext(
					entry2.getEntryId());

			Assert.assertEquals(
				"The previous entry relative to entry " + entry2.getEntryId() +
					" should be " + entry1.getEntryId(),
				entry1, prevAndNext[0]);
			Assert.assertEquals(
				"The current entry relative to entry " + entry2.getEntryId() +
					" should be " + entry2.getEntryId(),
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

		try (ContextUserReplace contextUserReplace =
				new ContextUserReplace(_groupUser, permissionChecker)) {

			BlogsEntry[] prevAndNext =
				BlogsEntryServiceUtil.getEntriesPrevAndNext(
					entry2.getEntryId());

			Assert.assertNull(
				"The previous entry relative to entry " + entry2.getEntryId() +
					" should be null",
				prevAndNext[0]);
			Assert.assertEquals(
				"The current entry relative to entry " + entry2.getEntryId() +
					" should be " + entry2.getEntryId(),
				entry2, prevAndNext[1]);
			Assert.assertEquals(
				"The next entry relative to entry " + entry2.getEntryId() +
					" should be " + entry3.getEntryId(),
				entry3, prevAndNext[2]);
		}
	}

	@DeleteAfterTestRun
	private Group _group;

	@DeleteAfterTestRun
	private User _groupUser;

	private User _user;

}