/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.message.boards.service.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.message.boards.constants.MBConstants;
import com.liferay.message.boards.model.MBMessage;
import com.liferay.message.boards.model.MBSuspiciousActivity;
import com.liferay.message.boards.service.MBSuspiciousActivityLocalServiceUtil;
import com.liferay.message.boards.service.MBSuspiciousActivityServiceUtil;
import com.liferay.message.boards.test.util.MBTestUtil;
import com.liferay.petra.function.UnsafeRunnable;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.RoleTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Shakir Shamim
 */
@RunWith(Arquillian.class)
public class MBSuspiciousActivityServiceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		_message = MBTestUtil.addMessage(
			_group.getGroupId(), TestPropsValues.getUserId(),
			RandomTestUtil.randomString(), RandomTestUtil.randomString());

		_role = RoleTestUtil.addRole(
			RandomTestUtil.randomString(), RoleConstants.TYPE_REGULAR,
			MBConstants.RESOURCE_NAME, ResourceConstants.SCOPE_GROUP,
			String.valueOf(_group.getGroupId()), ActionKeys.BAN_USER);

		_suspiciousActivity =
			MBSuspiciousActivityLocalServiceUtil.
				addOrUpdateMessageSuspiciousActivity(
					TestPropsValues.getUserId(), _message.getMessageId(),
					RandomTestUtil.randomString());

		_user = UserTestUtil.addGroupUser(_group, RoleConstants.POWER_USER);
	}

	@Test
	public void testDeleteSuspiciousActivity() throws Exception {
		_assertRoleGrantsPermission(
			() -> MBSuspiciousActivityServiceUtil.deleteSuspiciousActivity(
				_suspiciousActivity.getSuspiciousActivityId()));

		Assert.assertNull(
			MBSuspiciousActivityLocalServiceUtil.fetchMBSuspiciousActivity(
				_suspiciousActivity.getSuspiciousActivityId()));
	}

	@Test
	public void testGetMessageSuspiciousActivities() throws Exception {
		_assertRoleGrantsPermission(
			() ->
				MBSuspiciousActivityServiceUtil.getMessageSuspiciousActivities(
					_message.getMessageId()));
	}

	@Test
	public void testGetSuspiciousActivity() throws Exception {
		_assertRoleGrantsPermission(
			() -> MBSuspiciousActivityServiceUtil.getSuspiciousActivity(
				_suspiciousActivity.getSuspiciousActivityId()));
	}

	@Test
	public void testGetThreadSuspiciousActivities() throws Exception {
		_assertRoleGrantsPermission(
			() -> MBSuspiciousActivityServiceUtil.getThreadSuspiciousActivities(
				_message.getThreadId()));
	}

	@Test
	public void testUpdateValidated() throws Exception {
		_assertRoleGrantsPermission(
			() -> MBSuspiciousActivityServiceUtil.updateValidated(
				_suspiciousActivity.getSuspiciousActivityId()));

		MBSuspiciousActivity suspiciousActivity =
			MBSuspiciousActivityLocalServiceUtil.getMBSuspiciousActivity(
				_suspiciousActivity.getSuspiciousActivityId());

		Assert.assertTrue(suspiciousActivity.isValidated());
	}

	private void _assertRoleGrantsPermission(
			UnsafeRunnable<Exception> unsafeRunnable)
		throws Exception {

		UserTestUtil.setUser(_user);

		Assert.assertThrows(
			PrincipalException.MustHavePermission.class, unsafeRunnable::run);

		UserLocalServiceUtil.addRoleUser(_role.getRoleId(), _user.getUserId());

		unsafeRunnable.run();
	}

	@DeleteAfterTestRun
	private Group _group;

	private MBMessage _message;

	@DeleteAfterTestRun
	private Role _role;

	private MBSuspiciousActivity _suspiciousActivity;

	@DeleteAfterTestRun
	private User _user;

}