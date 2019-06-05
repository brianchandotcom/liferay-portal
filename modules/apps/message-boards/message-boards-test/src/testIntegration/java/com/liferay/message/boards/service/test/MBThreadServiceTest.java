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

package com.liferay.message.boards.service.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.message.boards.constants.MBCategoryConstants;
import com.liferay.message.boards.model.MBCategory;
import com.liferay.message.boards.model.MBMessage;
import com.liferay.message.boards.model.MBThread;
import com.liferay.message.boards.service.MBCategoryLocalServiceUtil;
import com.liferay.message.boards.service.MBThreadServiceUtil;
import com.liferay.message.boards.test.util.MBTestUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.orm.QueryDefinition;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.role.RoleConstants;
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
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

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
public class MBThreadServiceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		MBTestUtil.populateNotificationsServiceContext(
			serviceContext, Constants.ADD);

		_category = MBCategoryLocalServiceUtil.addCategory(
			TestPropsValues.getUserId(),
			MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID,
			RandomTestUtil.randomString(), StringPool.BLANK, serviceContext);
	}

	@Test
	public void testGetThreadsCountWithAnyStatus() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		MBTestUtil.addMessageWithWorkflow(
			_group.getGroupId(), _category.getCategoryId(),
			StringUtil.randomString(), StringUtil.randomString(), false,
			serviceContext);

		MBTestUtil.addMessageWithWorkflow(
			_group.getGroupId(), _category.getCategoryId(),
			StringUtil.randomString(), StringUtil.randomString(), true,
			serviceContext);

		QueryDefinition<MBThread> queryDefinition = new QueryDefinition<>(
			WorkflowConstants.STATUS_ANY);

		Assert.assertEquals(
			2,
			MBThreadServiceUtil.getThreadsCount(
				_group.getGroupId(), _category.getCategoryId(),
				queryDefinition));
	}

	@Test
	public void testGetThreadsCountWithApprovedStatus() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		MBTestUtil.addMessageWithWorkflow(
			_group.getGroupId(), _category.getCategoryId(),
			StringUtil.randomString(), StringUtil.randomString(), false,
			serviceContext);

		MBTestUtil.addMessageWithWorkflow(
			_group.getGroupId(), _category.getCategoryId(),
			StringUtil.randomString(), StringUtil.randomString(), true,
			serviceContext);

		QueryDefinition<MBThread> queryDefinition = new QueryDefinition<>(
			WorkflowConstants.STATUS_APPROVED);

		Assert.assertEquals(
			1,
			MBThreadServiceUtil.getThreadsCount(
				_group.getGroupId(), _category.getCategoryId(),
				queryDefinition));
	}

	@Test
	public void testGetThreadsCountWithApprovedStatusAndOwnerReturnsDraft()
		throws Exception {

		User user = UserTestUtil.addGroupUser(_group, RoleConstants.POWER_USER);

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), user.getUserId());

		MBTestUtil.addMessageWithWorkflow(
			_group.getGroupId(), _category.getCategoryId(),
			StringUtil.randomString(), StringUtil.randomString(), false,
			serviceContext);

		PermissionChecker permissionChecker =
			PermissionCheckerFactoryUtil.create(user);

		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				user, permissionChecker)) {

			QueryDefinition<MBThread> queryDefinition = new QueryDefinition<>(
				WorkflowConstants.STATUS_APPROVED, user.getUserId(), true);

			Assert.assertEquals(
				1,
				MBThreadServiceUtil.getThreadsCount(
					_group.getGroupId(), _category.getCategoryId(),
					queryDefinition));
		}
	}

	@Test
	public void testGetThreadsCountWithApprovedStatusAndWithoutOwnerDoesNotReturnDraft()
		throws Exception {

		User user = UserTestUtil.addGroupUser(_group, RoleConstants.POWER_USER);

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), user.getUserId());

		MBTestUtil.addMessageWithWorkflow(
			_group.getGroupId(), _category.getCategoryId(),
			StringUtil.randomString(), StringUtil.randomString(), false,
			serviceContext);

		PermissionChecker permissionChecker =
			PermissionCheckerFactoryUtil.create(user);

		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				user, permissionChecker)) {

			QueryDefinition<MBThread> queryDefinition = new QueryDefinition<>(
				WorkflowConstants.STATUS_APPROVED, user.getUserId(), false);

			Assert.assertEquals(
				0,
				MBThreadServiceUtil.getThreadsCount(
					_group.getGroupId(), _category.getCategoryId(),
					queryDefinition));
		}
	}

	@Test
	public void testGetThreadsCountWithDraftStatus() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		MBTestUtil.addMessageWithWorkflow(
			_group.getGroupId(), _category.getCategoryId(),
			StringUtil.randomString(), StringUtil.randomString(), false,
			serviceContext);

		MBTestUtil.addMessageWithWorkflow(
			_group.getGroupId(), _category.getCategoryId(),
			StringUtil.randomString(), StringUtil.randomString(), true,
			serviceContext);

		QueryDefinition<MBThread> queryDefinition = new QueryDefinition<>(
			WorkflowConstants.STATUS_DRAFT);

		Assert.assertEquals(
			1,
			MBThreadServiceUtil.getThreadsCount(
				_group.getGroupId(), _category.getCategoryId(),
				queryDefinition));
	}

	@Test
	public void testGetThreadsCountWithoutPermissions() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		serviceContext.setAddGroupPermissions(false);
		serviceContext.setAddGuestPermissions(false);

		MBTestUtil.addMessageWithWorkflow(
			_group.getGroupId(), _category.getCategoryId(),
			StringUtil.randomString(), StringUtil.randomString(), true,
			serviceContext);

		User user = UserTestUtil.addGroupUser(_group, RoleConstants.POWER_USER);

		PermissionChecker permissionChecker =
			PermissionCheckerFactoryUtil.create(user);

		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				user, permissionChecker)) {

			QueryDefinition<MBThread> queryDefinition = new QueryDefinition<>(
				WorkflowConstants.STATUS_ANY);

			Assert.assertEquals(
				0,
				MBThreadServiceUtil.getThreadsCount(
					_group.getGroupId(), _category.getCategoryId(),
					queryDefinition));
		}
	}

	@Test
	public void testGetThreadsWithAnyStatus() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		MBMessage draftMessage = MBTestUtil.addMessageWithWorkflow(
			_group.getGroupId(), _category.getCategoryId(),
			StringUtil.randomString(), StringUtil.randomString(), false,
			serviceContext);

		MBMessage approvedMessage = MBTestUtil.addMessageWithWorkflow(
			_group.getGroupId(), _category.getCategoryId(),
			StringUtil.randomString(), StringUtil.randomString(), true,
			serviceContext);

		QueryDefinition<MBThread> queryDefinition = new QueryDefinition<>(
			WorkflowConstants.STATUS_ANY);

		List<MBThread> threads = MBThreadServiceUtil.getThreads(
			_group.getGroupId(), _category.getCategoryId(), queryDefinition);

		Assert.assertEquals(threads.toString(), 2, threads.size());

		Assert.assertTrue(threads.contains(draftMessage.getThread()));
		Assert.assertTrue(threads.contains(approvedMessage.getThread()));
	}

	@Test
	public void testGetThreadsWithApprovedStatus() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		MBTestUtil.addMessageWithWorkflow(
			_group.getGroupId(), _category.getCategoryId(),
			StringUtil.randomString(), StringUtil.randomString(), false,
			serviceContext);

		MBMessage approvedMessage = MBTestUtil.addMessageWithWorkflow(
			_group.getGroupId(), _category.getCategoryId(),
			StringUtil.randomString(), StringUtil.randomString(), true,
			serviceContext);

		QueryDefinition<MBThread> queryDefinition = new QueryDefinition<>(
			WorkflowConstants.STATUS_APPROVED);

		List<MBThread> threads = MBThreadServiceUtil.getThreads(
			_group.getGroupId(), _category.getCategoryId(), queryDefinition);

		Assert.assertEquals(threads.toString(), 1, threads.size());

		Assert.assertEquals(approvedMessage.getThread(), threads.get(0));
	}

	@Test
	public void testGetThreadsWithApprovedStatusAndOwnerReturnsDraft()
		throws Exception {

		User user = UserTestUtil.addGroupUser(_group, RoleConstants.POWER_USER);

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), user.getUserId());

		MBMessage draftMessage = MBTestUtil.addMessageWithWorkflow(
			_group.getGroupId(), _category.getCategoryId(),
			StringUtil.randomString(), StringUtil.randomString(), false,
			serviceContext);

		PermissionChecker permissionChecker =
			PermissionCheckerFactoryUtil.create(user);

		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				user, permissionChecker)) {

			QueryDefinition<MBThread> queryDefinition = new QueryDefinition<>(
				WorkflowConstants.STATUS_APPROVED, user.getUserId(), true);

			List<MBThread> threads = MBThreadServiceUtil.getThreads(
				_group.getGroupId(), _category.getCategoryId(),
				queryDefinition);

			Assert.assertEquals(threads.toString(), 1, threads.size());
			Assert.assertEquals(draftMessage.getThread(), threads.get(0));
		}
	}

	@Test
	public void testGetThreadsWithApprovedStatusAndWithoutOwnerDoesNotReturnDraft()
		throws Exception {

		User user = UserTestUtil.addGroupUser(_group, RoleConstants.POWER_USER);

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), user.getUserId());

		MBTestUtil.addMessageWithWorkflow(
			_group.getGroupId(), _category.getCategoryId(),
			StringUtil.randomString(), StringUtil.randomString(), false,
			serviceContext);

		PermissionChecker permissionChecker =
			PermissionCheckerFactoryUtil.create(user);

		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				user, permissionChecker)) {

			QueryDefinition<MBThread> queryDefinition = new QueryDefinition<>(
				WorkflowConstants.STATUS_APPROVED, user.getUserId(), false);

			List<MBThread> threads = MBThreadServiceUtil.getThreads(
				_group.getGroupId(), _category.getCategoryId(),
				queryDefinition);

			Assert.assertEquals(threads.toString(), 0, threads.size());
		}
	}

	@Test
	public void testGetThreadsWithDraftStatus() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		MBMessage draftMessage = MBTestUtil.addMessageWithWorkflow(
			_group.getGroupId(), _category.getCategoryId(),
			StringUtil.randomString(), StringUtil.randomString(), false,
			serviceContext);

		MBTestUtil.addMessageWithWorkflow(
			_group.getGroupId(), _category.getCategoryId(),
			StringUtil.randomString(), StringUtil.randomString(), true,
			serviceContext);

		QueryDefinition<MBThread> queryDefinition = new QueryDefinition<>(
			WorkflowConstants.STATUS_DRAFT);

		List<MBThread> threads = MBThreadServiceUtil.getThreads(
			_group.getGroupId(), _category.getCategoryId(), queryDefinition);

		Assert.assertEquals(threads.toString(), 1, threads.size());

		Assert.assertEquals(draftMessage.getThread(), threads.get(0));
	}

	private MBCategory _category;

	@DeleteAfterTestRun
	private Group _group;

}