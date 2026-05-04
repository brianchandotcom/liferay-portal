/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.sharing.internal.model.listener.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.UserGroup;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.service.UserGroupLocalService;
import com.liferay.portal.kernel.test.TestInfo;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserGroupTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.sharing.model.SharingEntry;
import com.liferay.sharing.security.permission.SharingEntryAction;
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
 * @author Alicia García
 */
@RunWith(Arquillian.class)
public class UserGroupModelListenerTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();
	}

	@Test
	@TestInfo("LPD-48130")
	public void testDeleteUserGroup() throws Exception {
		UserGroup userGroup1 = UserGroupTestUtil.addUserGroup();
		UserGroup userGroup2 = UserGroupTestUtil.addUserGroup();

		_addUserGroupSharingEntry(userGroup1.getUserGroupId());
		_addUserGroupSharingEntry(userGroup2.getUserGroupId());

		List<SharingEntry> toUserGroupSharingEntries =
			_sharingEntryLocalService.getToUserGroupSharingEntries(
				userGroup1.getUserGroupId());

		Assert.assertEquals(
			toUserGroupSharingEntries.toString(), 1,
			toUserGroupSharingEntries.size());

		toUserGroupSharingEntries =
			_sharingEntryLocalService.getToUserGroupSharingEntries(
				userGroup2.getUserGroupId());

		Assert.assertEquals(
			toUserGroupSharingEntries.toString(), 1,
			toUserGroupSharingEntries.size());

		_userGroupLocalService.deleteUserGroup(userGroup1);

		toUserGroupSharingEntries =
			_sharingEntryLocalService.getToUserGroupSharingEntries(
				userGroup1.getUserGroupId());

		Assert.assertEquals(
			toUserGroupSharingEntries.toString(), 0,
			toUserGroupSharingEntries.size());

		toUserGroupSharingEntries =
			_sharingEntryLocalService.getToUserGroupSharingEntries(
				userGroup2.getUserGroupId());

		Assert.assertEquals(
			toUserGroupSharingEntries.toString(), 1,
			toUserGroupSharingEntries.size());
	}

	private void _addUserGroupSharingEntry(long toUserGroupId)
		throws Exception {

		_sharingEntryLocalService.addSharingEntry(
			null, TestPropsValues.getUserId(), 0, toUserGroupId, 0,
			_classNameLocalService.getClassNameId(Group.class.getName()),
			_group.getGroupId(), _group.getGroupId(), true,
			Arrays.asList(SharingEntryAction.VIEW), null,
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId()));
	}

	@Inject
	private ClassNameLocalService _classNameLocalService;

	@DeleteAfterTestRun
	private Group _group;

	@Inject
	private SharingEntryLocalService _sharingEntryLocalService;

	@Inject
	private UserGroupLocalService _userGroupLocalService;

}