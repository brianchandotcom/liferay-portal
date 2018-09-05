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

package com.liferay.sharing.internal.model.listener.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.model.RoleConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.CompanyTestUtil;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerTestRule;
import com.liferay.sharing.constants.SharingEntryActionKey;
import com.liferay.sharing.model.SharingEntry;
import com.liferay.sharing.service.SharingEntryLocalService;

import java.util.Arrays;
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
public class GroupModelListenerTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_company = CompanyTestUtil.addCompany();

		_user = UserTestUtil.addCompanyAdminUser(_company);

		_group = GroupTestUtil.addGroup(
			_company.getCompanyId(), _user.getUserId(),
			GroupConstants.DEFAULT_PARENT_GROUP_ID);

		_groupUser = UserTestUtil.addGroupUser(
			_group, RoleConstants.POWER_USER);
	}

	@Test
	public void testDeletingGroupDeletesSharingEntries() throws Exception {
		long classNameId = _classNameLocalService.getClassNameId(
			Group.class.getName());
		long classPK = RandomTestUtil.randomLong();

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), _user.getUserId());

		_sharingEntryLocalService.addSharingEntry(
			_user.getUserId(), _groupUser.getUserId(), classNameId, classPK,
			_group.getGroupId(), true,
			Arrays.asList(SharingEntryActionKey.VIEW), serviceContext);

		List<SharingEntry> groupSharingEntries =
			_sharingEntryLocalService.getGroupSharingEntries(
				_group.getGroupId());

		Assert.assertEquals(
			groupSharingEntries.toString(), 1, groupSharingEntries.size());

		_groupLocalService.deleteGroup(_group.getGroupId());

		groupSharingEntries = _sharingEntryLocalService.getGroupSharingEntries(
			_group.getGroupId());

		Assert.assertEquals(
			groupSharingEntries.toString(), 0, groupSharingEntries.size());
	}

	@Test
	public void testDeletingGroupDoesNotDeleteOtherGroupSharingEntries()
		throws Exception {

		Group group2 = GroupTestUtil.addGroup();

		try {
			long classNameId = _classNameLocalService.getClassNameId(
				Group.class.getName());

			ServiceContext serviceContext =
				ServiceContextTestUtil.getServiceContext(
					_group.getGroupId(), _user.getUserId());

			_sharingEntryLocalService.addSharingEntry(
				_user.getUserId(), _groupUser.getUserId(), classNameId,
				RandomTestUtil.randomLong(), _group.getGroupId(), true,
				Arrays.asList(SharingEntryActionKey.VIEW), serviceContext);

			_sharingEntryLocalService.addSharingEntry(
				_user.getUserId(), _groupUser.getUserId(), classNameId,
				RandomTestUtil.randomLong(), group2.getGroupId(), true,
				Arrays.asList(SharingEntryActionKey.VIEW), serviceContext);

			List<SharingEntry> groupSharingEntries =
				_sharingEntryLocalService.getGroupSharingEntries(
					_group.getGroupId());

			Assert.assertEquals(
				groupSharingEntries.toString(), 1, groupSharingEntries.size());

			List<SharingEntry> group2SharingEntries =
				_sharingEntryLocalService.getGroupSharingEntries(
					group2.getGroupId());

			Assert.assertEquals(
				group2SharingEntries.toString(), 1,
				group2SharingEntries.size());

			_groupLocalService.deleteGroup(_group.getGroupId());

			groupSharingEntries =
				_sharingEntryLocalService.getGroupSharingEntries(
					_group.getGroupId());

			Assert.assertEquals(
				groupSharingEntries.toString(), 0, groupSharingEntries.size());

			group2SharingEntries =
				_sharingEntryLocalService.getGroupSharingEntries(
					group2.getGroupId());

			Assert.assertEquals(
				group2SharingEntries.toString(), 1,
				group2SharingEntries.size());
		}
		finally {
			_groupLocalService.deleteGroup(group2);
		}
	}

	@Inject
	private ClassNameLocalService _classNameLocalService;

	@DeleteAfterTestRun
	private Company _company;

	private Group _group;

	@Inject
	private GroupLocalService _groupLocalService;

	private User _groupUser;

	@Inject
	private SharingEntryLocalService _sharingEntryLocalService;

	private User _user;

}