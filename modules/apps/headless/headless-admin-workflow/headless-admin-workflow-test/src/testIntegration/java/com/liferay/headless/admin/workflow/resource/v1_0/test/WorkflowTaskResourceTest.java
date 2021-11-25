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

package com.liferay.headless.admin.workflow.resource.v1_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.headless.admin.workflow.client.dto.v1_0.Assignee;
import com.liferay.headless.admin.workflow.client.dto.v1_0.ChangeTransition;
import com.liferay.headless.admin.workflow.client.dto.v1_0.ObjectReviewed;
import com.liferay.headless.admin.workflow.client.dto.v1_0.WorkflowDefinition;
import com.liferay.headless.admin.workflow.client.dto.v1_0.WorkflowInstance;
import com.liferay.headless.admin.workflow.client.dto.v1_0.WorkflowTask;
import com.liferay.headless.admin.workflow.client.dto.v1_0.WorkflowTaskAssignToMe;
import com.liferay.headless.admin.workflow.client.dto.v1_0.WorkflowTaskAssignToRole;
import com.liferay.headless.admin.workflow.client.dto.v1_0.WorkflowTaskAssignToUser;
import com.liferay.headless.admin.workflow.client.dto.v1_0.WorkflowTasksBulkSelection;
import com.liferay.headless.admin.workflow.client.pagination.Page;
import com.liferay.headless.admin.workflow.client.pagination.Pagination;
import com.liferay.headless.admin.workflow.resource.v1_0.test.util.AssigneeTestUtil;
import com.liferay.headless.admin.workflow.resource.v1_0.test.util.ObjectReviewedTestUtil;
import com.liferay.headless.admin.workflow.resource.v1_0.test.util.WorkflowDefinitionTestUtil;
import com.liferay.headless.admin.workflow.resource.v1_0.test.util.WorkflowInstanceTestUtil;
import com.liferay.headless.admin.workflow.resource.v1_0.test.util.WorkflowTaskTestUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.messaging.proxy.ProxyMessageListener;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.RoleLocalServiceUtil;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.test.rule.DataGuard;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.workflow.WorkflowTaskManager;
import com.liferay.portal.kernel.workflow.WorkflowTaskManagerUtil;
import com.liferay.portal.test.log.LogCapture;
import com.liferay.portal.test.log.LoggerTestUtil;
import com.liferay.portal.test.rule.Inject;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

import org.apache.commons.lang.time.DateUtils;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Javier Gamarra
 */
@DataGuard(scope = DataGuard.Scope.METHOD)
@RunWith(Arquillian.class)
public class WorkflowTaskResourceTest extends BaseWorkflowTaskResourceTestCase {

	@BeforeClass
	public static void setUpClass() throws Exception {
		BaseWorkflowTaskResourceTestCase.setUpClass();

		_workflowDefinition = WorkflowDefinitionTestUtil.addWorkflowDefinition(
			"fork-workflow-definition.xml");

		PermissionThreadLocal.setPermissionChecker(
			PermissionCheckerFactoryUtil.create(
				UserLocalServiceUtil.getUser(TestPropsValues.getUserId())));
	}

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		ObjectReviewed objectReviewed =
			ObjectReviewedTestUtil.addObjectReviewed();

		_workflowInstance = WorkflowInstanceTestUtil.addWorkflowInstance(
			testGroup.getGroupId(), objectReviewed, _workflowDefinition);

		_workflowTasks = new Stack<>();

		_workflowTasks.addAll(
			WorkflowTaskTestUtil.getWorkflowTasks(_workflowInstance.getId()));
	}

	@Override
	@Test
	public void testGetWorkflowInstanceWorkflowTasksAssignedToUserPage()
		throws Exception {

		Page<WorkflowTask> page =
			workflowTaskResource.
				getWorkflowInstanceWorkflowTasksAssignedToUserPage(
					_workflowInstance.getId(), TestPropsValues.getUserId(),
					null, Pagination.of(1, 2));

		Assert.assertEquals(0, page.getTotalCount());

		WorkflowTask workflowTask1 =
			testGetWorkflowInstanceWorkflowTasksAssignedToUserPage_addWorkflowTask(
				_workflowInstance.getId(), randomWorkflowTask());

		WorkflowTask workflowTask2 =
			testGetWorkflowInstanceWorkflowTasksAssignedToUserPage_addWorkflowTask(
				_workflowInstance.getId(), randomWorkflowTask());

		page =
			workflowTaskResource.
				getWorkflowInstanceWorkflowTasksAssignedToUserPage(
					_workflowInstance.getId(), TestPropsValues.getUserId(),
					null, Pagination.of(1, 2));

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(workflowTask1, workflowTask2),
			(List<WorkflowTask>)page.getItems());
		assertValid(page);
	}

	@Override
	@Test
	public void testGetWorkflowInstanceWorkflowTasksPage() throws Exception {
		Page<WorkflowTask> page =
			workflowTaskResource.getWorkflowInstanceWorkflowTasksPage(
				_workflowInstance.getId(), null, Pagination.of(1, 3));

		Assert.assertEquals(3, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(
				new WorkflowTask() {
					{
						completed = false;
						label = "task1";
						name = "task1";
					}
				},
				new WorkflowTask() {
					{
						completed = false;
						label = "task2";
						name = "task2";
					}
				},
				new WorkflowTask() {
					{
						completed = false;
						label = "task3";
						name = "task3";
					}
				}),
			(List<WorkflowTask>)page.getItems());
		assertValid(page);
	}

	@Override
	@Test
	public void testGetWorkflowTaskHasAssignableUsers() throws Exception {
		WorkflowTask workflowTask = testGetWorkflowTask_addWorkflowTask();

		Assert.assertFalse(
			workflowTaskResource.getWorkflowTaskHasAssignableUsers(
				workflowTask.getId()));

		Assert.assertFalse(
			workflowTaskResource.getWorkflowTaskHasAssignableUsers(
				workflowTask.getId()));

		_workflowTaskManager.assignWorkflowTaskToUser(
			TestPropsValues.getCompanyId(), TestPropsValues.getUserId(),
			workflowTask.getId(), TestPropsValues.getUserId(), StringPool.BLANK,
			null, null);

		Assert.assertFalse(
			workflowTaskResource.getWorkflowTaskHasAssignableUsers(
				workflowTask.getId()));
	}

	@Override
	@Test
	public void testGetWorkflowTasksAssignedToMyRolesPage() throws Exception {
		Page<WorkflowTask> page =
			workflowTaskResource.getWorkflowTasksAssignedToMyRolesPage(
				Pagination.of(1, 3));

		Assert.assertEquals(3, page.getTotalCount());

		WorkflowTask workflowTask1 = testGetWorkflowTask_addWorkflowTask();

		_assignWorkflowTaskToMyRolesPage(
			TestPropsValues.getUser(), workflowTask1.getId());

		WorkflowTask workflowTask2 = testGetWorkflowTask_addWorkflowTask();

		_assignWorkflowTaskToMyRolesPage(
			TestPropsValues.getUser(), workflowTask2.getId());

		WorkflowTask workflowTask3 = testGetWorkflowTask_addWorkflowTask();

		_assignWorkflowTaskToMyRolesPage(
			TestPropsValues.getUser(), workflowTask3.getId());

		page = workflowTaskResource.getWorkflowTasksAssignedToMyRolesPage(
			Pagination.of(1, 3));

		Assert.assertEquals(3, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(workflowTask1, workflowTask2, workflowTask3),
			(List<WorkflowTask>)page.getItems());

		assertValid(page);
	}

	@Override
	@Test
	public void testGetWorkflowTasksAssignedToMyRolesPageWithPagination()
		throws Exception {

		WorkflowTask workflowTask1 = testGetWorkflowTask_addWorkflowTask();

		_assignWorkflowTaskToMyRolesPage(
			TestPropsValues.getUser(), workflowTask1.getId());

		WorkflowTask workflowTask2 = testGetWorkflowTask_addWorkflowTask();

		_assignWorkflowTaskToMyRolesPage(
			TestPropsValues.getUser(), workflowTask2.getId());

		WorkflowTask workflowTask3 = testGetWorkflowTask_addWorkflowTask();

		_assignWorkflowTaskToMyRolesPage(
			TestPropsValues.getUser(), workflowTask3.getId());

		Page<WorkflowTask> page1 =
			workflowTaskResource.getWorkflowTasksAssignedToMyRolesPage(
				Pagination.of(1, 2));

		List<WorkflowTask> workflowTasks1 =
			(List<WorkflowTask>)page1.getItems();

		Assert.assertEquals(
			workflowTasks1.toString(), 2, workflowTasks1.size());

		Page<WorkflowTask> page2 =
			workflowTaskResource.getWorkflowTasksAssignedToMyRolesPage(
				Pagination.of(2, 2));

		List<WorkflowTask> workflowTasks2 =
			(List<WorkflowTask>)page2.getItems();

		Assert.assertEquals(
			workflowTasks2.toString(), 1, workflowTasks2.size());

		Page<WorkflowTask> page3 =
			workflowTaskResource.getWorkflowTasksAssignedToMyRolesPage(
				Pagination.of(1, 3));

		assertEqualsIgnoringOrder(
			Arrays.asList(workflowTask1, workflowTask2, workflowTask3),
			(List<WorkflowTask>)page3.getItems());
	}

	@Override
	@Test
	public void testGetWorkflowTasksAssignedToRolePage() throws Exception {
		Role siteContentReviewerRole = _roleLocalService.getRole(
			testGroup.getCompanyId(), RoleConstants.SITE_CONTENT_REVIEWER);

		Page<WorkflowTask> page =
			workflowTaskResource.getWorkflowTasksAssignedToRolePage(
				siteContentReviewerRole.getRoleId(), Pagination.of(1, 2));

		Assert.assertEquals(0, page.getTotalCount());

		WorkflowTask workflowTask1 =
			testGetWorkflowTasksAssignedToRolePage_addWorkflowTask(
				randomWorkflowTask());

		WorkflowTaskManagerUtil.assignWorkflowTaskToRole(
			TestPropsValues.getCompanyId(), TestPropsValues.getUserId(),
			workflowTask1.getId(), siteContentReviewerRole.getRoleId(),
			StringPool.BLANK, null, null);

		page = workflowTaskResource.getWorkflowTasksAssignedToRolePage(
			siteContentReviewerRole.getRoleId(), Pagination.of(1, 2));

		Assert.assertEquals(1, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Collections.singletonList(workflowTask1),
			(List<WorkflowTask>)page.getItems());
		assertValid(page);
	}

	@Override
	@Test
	public void testGetWorkflowTasksAssignedToRolePageWithPagination()
		throws Exception {

		Role siteContentReviewerRole = _roleLocalService.getRole(
			testGroup.getCompanyId(), RoleConstants.SITE_CONTENT_REVIEWER);

		WorkflowTask workflowTask1 =
			testGetWorkflowTasksAssignedToRolePage_addWorkflowTask(
				randomWorkflowTask());

		WorkflowTaskManagerUtil.assignWorkflowTaskToRole(
			TestPropsValues.getCompanyId(), TestPropsValues.getUserId(),
			workflowTask1.getId(), siteContentReviewerRole.getRoleId(),
			StringPool.BLANK, null, null);

		WorkflowTask workflowTask2 =
			testGetWorkflowTasksAssignedToRolePage_addWorkflowTask(
				randomWorkflowTask());

		WorkflowTaskManagerUtil.assignWorkflowTaskToRole(
			TestPropsValues.getCompanyId(), TestPropsValues.getUserId(),
			workflowTask2.getId(), siteContentReviewerRole.getRoleId(),
			StringPool.BLANK, null, null);

		WorkflowTask workflowTask3 =
			testGetWorkflowTasksAssignedToRolePage_addWorkflowTask(
				randomWorkflowTask());

		WorkflowTaskManagerUtil.assignWorkflowTaskToRole(
			TestPropsValues.getCompanyId(), TestPropsValues.getUserId(),
			workflowTask3.getId(), siteContentReviewerRole.getRoleId(),
			StringPool.BLANK, null, null);

		Page<WorkflowTask> page1 =
			workflowTaskResource.getWorkflowTasksAssignedToRolePage(
				siteContentReviewerRole.getRoleId(), Pagination.of(1, 2));

		List<WorkflowTask> workflowTasks1 =
			(List<WorkflowTask>)page1.getItems();

		Assert.assertEquals(
			workflowTasks1.toString(), 2, workflowTasks1.size());

		Page<WorkflowTask> page2 =
			workflowTaskResource.getWorkflowTasksAssignedToRolePage(
				siteContentReviewerRole.getRoleId(), Pagination.of(2, 2));

		Assert.assertEquals(3, page2.getTotalCount());

		List<WorkflowTask> workflowTasks2 =
			(List<WorkflowTask>)page2.getItems();

		Assert.assertEquals(
			workflowTasks2.toString(), 1, workflowTasks2.size());

		Page<WorkflowTask> page3 =
			workflowTaskResource.getWorkflowTasksAssignedToRolePage(
				siteContentReviewerRole.getRoleId(), Pagination.of(1, 3));

		assertEqualsIgnoringOrder(
			Arrays.asList(workflowTask1, workflowTask2, workflowTask3),
			(List<WorkflowTask>)page3.getItems());
	}

	@Override
	@Test
	public void testGetWorkflowTasksAssignedToUserPage() throws Exception {
		Page<WorkflowTask> page =
			workflowTaskResource.getWorkflowTasksAssignedToUserPage(
				TestPropsValues.getUserId(), Pagination.of(1, 2));

		Assert.assertEquals(0, page.getTotalCount());

		WorkflowTask workflowTask1 =
			testGetWorkflowTasksAssignedToUserPage_addWorkflowTask(
				randomWorkflowTask());

		WorkflowTask workflowTask2 =
			testGetWorkflowTasksAssignedToUserPage_addWorkflowTask(
				randomWorkflowTask());

		page = workflowTaskResource.getWorkflowTasksAssignedToUserPage(
			TestPropsValues.getUserId(), Pagination.of(1, 2));

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(workflowTask1, workflowTask2),
			(List<WorkflowTask>)page.getItems());
		assertValid(page);
	}

	@Override
	@Test
	public void testGetWorkflowTasksAssignedToUserPageWithPagination()
		throws Exception {

		WorkflowTask workflowTask1 =
			testGetWorkflowTasksAssignedToUserPage_addWorkflowTask(
				randomWorkflowTask());

		WorkflowTask workflowTask2 =
			testGetWorkflowTasksAssignedToUserPage_addWorkflowTask(
				randomWorkflowTask());

		WorkflowTask workflowTask3 =
			testGetWorkflowTasksAssignedToUserPage_addWorkflowTask(
				randomWorkflowTask());

		Page<WorkflowTask> page1 =
			workflowTaskResource.getWorkflowTasksAssignedToUserPage(
				TestPropsValues.getUserId(), Pagination.of(1, 2));

		List<WorkflowTask> workflowTasks1 =
			(List<WorkflowTask>)page1.getItems();

		Assert.assertEquals(
			workflowTasks1.toString(), 2, workflowTasks1.size());

		Page<WorkflowTask> page2 =
			workflowTaskResource.getWorkflowTasksAssignedToUserPage(
				TestPropsValues.getUserId(), Pagination.of(2, 2));

		Assert.assertEquals(3, page2.getTotalCount());

		List<WorkflowTask> workflowTasks2 =
			(List<WorkflowTask>)page2.getItems();

		Assert.assertEquals(
			workflowTasks2.toString(), 1, workflowTasks2.size());

		Page<WorkflowTask> page3 =
			workflowTaskResource.getWorkflowTasksAssignedToUserPage(
				TestPropsValues.getUserId(), Pagination.of(1, 3));

		assertEqualsIgnoringOrder(
			Arrays.asList(workflowTask1, workflowTask2, workflowTask3),
			(List<WorkflowTask>)page3.getItems());
	}

	@Override
	@Test
	public void testGetWorkflowTasksAssignedToUserRolesPage() throws Exception {
		Assignee assignee = AssigneeTestUtil.addAssignee(testGroup);

		Page<WorkflowTask> page =
			workflowTaskResource.getWorkflowTasksAssignedToUserRolesPage(
				assignee.getId(), Pagination.of(1, 2));

		Assert.assertEquals(0, page.getTotalCount());

		WorkflowTask workflowTask1 =
			testGetWorkflowTasksAssignedToUserRolesPage_addWorkflowTask(
				randomWorkflowTask());

		_assignWorkflowTaskToMyRolesPage(
			TestPropsValues.getUser(), workflowTask1.getId());

		WorkflowTask workflowTask2 =
			testGetWorkflowTasksAssignedToUserRolesPage_addWorkflowTask(
				randomWorkflowTask());

		_assignWorkflowTaskToMyRolesPage(
			TestPropsValues.getUser(), workflowTask2.getId());

		page = workflowTaskResource.getWorkflowTasksAssignedToUserRolesPage(
			assignee.getId(), Pagination.of(1, 2));

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(workflowTask1, workflowTask2),
			(List<WorkflowTask>)page.getItems());
		assertValid(page);
	}

	@Override
	@Test
	public void testGetWorkflowTasksAssignedToUserRolesPageWithPagination()
		throws Exception {

		WorkflowTask workflowTask1 =
			testGetWorkflowTasksAssignedToUserRolesPage_addWorkflowTask(
				randomWorkflowTask());

		_assignWorkflowTaskToMyRolesPage(
			TestPropsValues.getUser(), workflowTask1.getId());

		WorkflowTask workflowTask2 =
			testGetWorkflowTasksAssignedToUserRolesPage_addWorkflowTask(
				randomWorkflowTask());

		_assignWorkflowTaskToMyRolesPage(
			TestPropsValues.getUser(), workflowTask2.getId());

		WorkflowTask workflowTask3 =
			testGetWorkflowTasksAssignedToUserRolesPage_addWorkflowTask(
				randomWorkflowTask());

		_assignWorkflowTaskToMyRolesPage(
			TestPropsValues.getUser(), workflowTask3.getId());

		Assignee assignee = AssigneeTestUtil.addAssignee(testGroup);

		Page<WorkflowTask> page1 =
			workflowTaskResource.getWorkflowTasksAssignedToUserRolesPage(
				assignee.getId(), Pagination.of(1, 2));

		List<WorkflowTask> workflowTasks1 =
			(List<WorkflowTask>)page1.getItems();

		Assert.assertEquals(
			workflowTasks1.toString(), 2, workflowTasks1.size());

		Page<WorkflowTask> page2 =
			workflowTaskResource.getWorkflowTasksAssignedToUserRolesPage(
				assignee.getId(), Pagination.of(2, 2));

		Assert.assertEquals(3, page2.getTotalCount());

		List<WorkflowTask> workflowTasks2 =
			(List<WorkflowTask>)page2.getItems();

		Assert.assertEquals(
			workflowTasks2.toString(), 1, workflowTasks2.size());

		Page<WorkflowTask> page3 =
			workflowTaskResource.getWorkflowTasksAssignedToUserRolesPage(
				assignee.getId(), Pagination.of(1, 3));

		assertEqualsIgnoringOrder(
			Arrays.asList(workflowTask1, workflowTask2, workflowTask3),
			(List<WorkflowTask>)page3.getItems());
	}

	@Override
	@Test
	public void testGetWorkflowTasksSubmittingUserPage() throws Exception {
		Page<WorkflowTask> page =
			workflowTaskResource.getWorkflowTasksSubmittingUserPage(
				TestPropsValues.getUserId(), Pagination.of(1, 2));

		Assert.assertEquals(3, page.getTotalCount());

		WorkflowTask workflowTask1 = testGetWorkflowTask_addWorkflowTask();

		WorkflowTask workflowTask2 = testGetWorkflowTask_addWorkflowTask();

		WorkflowTask workflowTask3 = testGetWorkflowTask_addWorkflowTask();

		page = workflowTaskResource.getWorkflowTasksSubmittingUserPage(
			TestPropsValues.getUserId(), Pagination.of(1, 3));

		Assert.assertEquals(3, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(workflowTask1, workflowTask2, workflowTask3),
			(List<WorkflowTask>)page.getItems());
		assertValid(page);
	}

	@Override
	@Test
	public void testGetWorkflowTasksSubmittingUserPageWithPagination()
		throws Exception {

		WorkflowTask workflowTask1 = testGetWorkflowTask_addWorkflowTask();

		WorkflowTask workflowTask2 = testGetWorkflowTask_addWorkflowTask();

		WorkflowTask workflowTask3 = testGetWorkflowTask_addWorkflowTask();

		Page<WorkflowTask> page1 =
			workflowTaskResource.getWorkflowTasksSubmittingUserPage(
				TestPropsValues.getUserId(), Pagination.of(1, 2));

		List<WorkflowTask> workflowTasks1 =
			(List<WorkflowTask>)page1.getItems();

		Assert.assertEquals(
			workflowTasks1.toString(), 2, workflowTasks1.size());

		Page<WorkflowTask> page2 =
			workflowTaskResource.getWorkflowTasksSubmittingUserPage(
				TestPropsValues.getUserId(), Pagination.of(2, 2));

		Assert.assertEquals(3, page2.getTotalCount());

		List<WorkflowTask> workflowTasks2 =
			(List<WorkflowTask>)page2.getItems();

		Assert.assertEquals(
			workflowTasks2.toString(), 1, workflowTasks2.size());

		Page<WorkflowTask> page3 =
			workflowTaskResource.getWorkflowTasksSubmittingUserPage(
				TestPropsValues.getUserId(), Pagination.of(1, 3));

		assertEqualsIgnoringOrder(
			Arrays.asList(workflowTask1, workflowTask2, workflowTask3),
			(List<WorkflowTask>)page3.getItems());
	}

	@Override
	@Test
	public void testGraphQLGetWorkflowTaskNotFound() throws Exception {
		try (LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
				ProxyMessageListener.class.getName(), LoggerTestUtil.OFF)) {

			super.testGraphQLGetWorkflowTaskNotFound();
		}
	}

	@Override
	@Test
	public void testPatchWorkflowTaskAssignToUser() throws Exception {
		WorkflowTask workflowTask = testGetWorkflowTask_addWorkflowTask();

		assertHttpResponseStatusCode(
			204,
			workflowTaskResource.patchWorkflowTaskAssignToUserHttpResponse(
				new WorkflowTaskAssignToUser[] {
					new WorkflowTaskAssignToUser() {
						{
							assigneeId = TestPropsValues.getUserId();
							workflowTaskId = workflowTask.getId();
						}
					}
				}));

		try (LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
				ProxyMessageListener.class.getName(), LoggerTestUtil.OFF)) {

			assertHttpResponseStatusCode(
				404,
				workflowTaskResource.patchWorkflowTaskAssignToUserHttpResponse(
					new WorkflowTaskAssignToUser[] {
						new WorkflowTaskAssignToUser() {
							{
								assigneeId = TestPropsValues.getUserId();
								workflowTaskId = 0L;
							}
						}
					}));
		}
	}

	@Override
	@Test
	public void testPatchWorkflowTaskChangeTransition() throws Exception {
		WorkflowTask workflowTask1 =
			testGetWorkflowInstanceWorkflowTasksAssignedToMePage_addWorkflowTask(
				null, null);

		WorkflowTask workflowTask2 =
			testGetWorkflowInstanceWorkflowTasksAssignedToMePage_addWorkflowTask(
				null, null);

		assertHttpResponseStatusCode(
			204,
			workflowTaskResource.patchWorkflowTaskChangeTransitionHttpResponse(
				new ChangeTransition[] {
					new ChangeTransition() {
						{
							transitionName = "join";
							workflowTaskId = workflowTask1.getId();
						}
					},
					new ChangeTransition() {
						{
							transitionName = "join";
							workflowTaskId = workflowTask2.getId();
						}
					}
				}));

		try (LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
				ProxyMessageListener.class.getName(), LoggerTestUtil.OFF)) {

			assertHttpResponseStatusCode(
				404,
				workflowTaskResource.
					patchWorkflowTaskChangeTransitionHttpResponse(
						new ChangeTransition[] {
							new ChangeTransition() {
								{
									transitionName = "approve";
									workflowTaskId = 0L;
								}
							}
						}));
		}
	}

	@Override
	@Test
	public void testPatchWorkflowTaskUpdateDueDate() throws Exception {
		WorkflowTask workflowTask1 =
			testGetWorkflowInstanceWorkflowTasksAssignedToMePage_addWorkflowTask(
				null, null);

		WorkflowTask workflowTask2 =
			testGetWorkflowInstanceWorkflowTasksAssignedToMePage_addWorkflowTask(
				null, null);

		assertHttpResponseStatusCode(
			204,
			workflowTaskResource.patchWorkflowTaskUpdateDueDateHttpResponse(
				new WorkflowTaskAssignToMe[] {
					new WorkflowTaskAssignToMe() {
						{
							dueDate = DateUtils.addDays(
								RandomTestUtil.nextDate(), 1);
							workflowTaskId = workflowTask1.getId();
						}
					},
					new WorkflowTaskAssignToMe() {
						{
							dueDate = DateUtils.addDays(
								RandomTestUtil.nextDate(), 2);
							workflowTaskId = workflowTask2.getId();
						}
					}
				}));

		try (LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
				ProxyMessageListener.class.getName(), LoggerTestUtil.OFF)) {

			assertHttpResponseStatusCode(
				404,
				workflowTaskResource.patchWorkflowTaskUpdateDueDateHttpResponse(
					new WorkflowTaskAssignToMe[] {
						new WorkflowTaskAssignToMe() {
							{
								dueDate = RandomTestUtil.nextDate();
								workflowTaskId = 0L;
							}
						},
						new WorkflowTaskAssignToMe() {
							{
								dueDate = RandomTestUtil.nextDate();
								workflowTaskId = workflowTask2.getId();
							}
						}
					}));
		}
	}

	@Override
	@Test
	public void testPostWorkflowTaskAssignToMe() throws Exception {
		WorkflowTask workflowTask = testGetWorkflowTask_addWorkflowTask();

		WorkflowTask postWorkflowTask =
			workflowTaskResource.postWorkflowTaskAssignToMe(
				workflowTask.getId(), new WorkflowTaskAssignToMe());

		Page<WorkflowTask> page =
			workflowTaskResource.getWorkflowTasksAssignedToMePage(
				Pagination.of(1, 1));

		Assert.assertEquals(1, page.getTotalCount());

		List<WorkflowTask> items = (List<WorkflowTask>)page.getItems();

		equals(postWorkflowTask, items.get(0));

		assertValid(page);
	}

	@Override
	@Test
	public void testPostWorkflowTaskAssignToRole() throws Exception {
		WorkflowTask workflowTask = testGetWorkflowTask_addWorkflowTask();

		workflowTaskResource.postWorkflowTaskAssignToMe(
			workflowTask.getId(), new WorkflowTaskAssignToMe());

		Role administratorRole = _roleLocalService.getRole(
			testGroup.getCompanyId(), RoleConstants.ADMINISTRATOR);

		WorkflowTask postWorkflowTask =
			workflowTaskResource.postWorkflowTaskAssignToRole(
				workflowTask.getId(),
				new WorkflowTaskAssignToRole() {
					{
						roleId = administratorRole.getRoleId();
					}
				});

		Page<WorkflowTask> page =
			workflowTaskResource.getWorkflowTasksAssignedToRolePage(
				administratorRole.getRoleId(), Pagination.of(1, 3));

		Assert.assertEquals(3, page.getTotalCount());

		List<WorkflowTask> items = (List<WorkflowTask>)page.getItems();

		equals(postWorkflowTask, items.get(0));

		assertValid(page);
	}

	@Override
	@Test
	public void testPostWorkflowTaskAssignToUser() throws Exception {
		WorkflowTask workflowTask = testGetWorkflowTask_addWorkflowTask();

		WorkflowTask postWorkflowTask =
			workflowTaskResource.postWorkflowTaskAssignToUser(
				workflowTask.getId(),
				new WorkflowTaskAssignToUser() {
					{
						assigneeId = TestPropsValues.getUserId();
						workflowTaskId = workflowTask.getId();
					}
				});

		Page<WorkflowTask> page =
			workflowTaskResource.getWorkflowTasksAssignedToUserPage(
				TestPropsValues.getUserId(), Pagination.of(1, 2));

		Assert.assertEquals(1, page.getTotalCount());

		List<WorkflowTask> items = (List<WorkflowTask>)page.getItems();

		equals(postWorkflowTask, items.get(0));

		assertValid(page);
	}

	@Override
	@Test
	public void testPostWorkflowTaskChangeTransition() throws Exception {
		WorkflowTask workflowTask =
			testGetWorkflowInstanceWorkflowTasksAssignedToMePage_addWorkflowTask(
				null, null);

		workflowTaskResource.postWorkflowTaskChangeTransition(
			workflowTask.getId(),
			new ChangeTransition() {
				{
					transitionName = "join";
					workflowTaskId = workflowTask.getId();
				}
			});

		Page<WorkflowTask> page =
			workflowTaskResource.getWorkflowInstanceWorkflowTasksPage(
				_workflowInstance.getId(), null, Pagination.of(1, 3));

		Assert.assertEquals(3, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(
				new WorkflowTask() {
					{
						completed = false;
						label = "task1";
						name = "task1";
					}
				},
				new WorkflowTask() {
					{
						completed = false;
						label = "task2";
						name = "task2";
					}
				},
				new WorkflowTask() {
					{
						completed = true;
						label = "task3";
						name = "task3";
					}
				}),
			(List<WorkflowTask>)page.getItems());
		assertValid(page);
	}

	@Override
	@Test
	public void testPostWorkflowTasksPage() throws Exception {
		WorkflowTask workflowTask1 = testGetWorkflowTask_addWorkflowTask();

		WorkflowTask workflowTask2 = testGetWorkflowTask_addWorkflowTask();

		Page<WorkflowTask> page = workflowTaskResource.postWorkflowTasksPage(
			Pagination.of(1, 2), null,
			new WorkflowTasksBulkSelection() {
				{
					assigneeIds = new Long[] {TestPropsValues.getUserId()};
				}
			});

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(workflowTask1, workflowTask2),
			(List<WorkflowTask>)page.getItems());
		assertValid(page);

		page = workflowTaskResource.postWorkflowTasksPage(
			Pagination.of(1, 2), null,
			new WorkflowTasksBulkSelection() {
				{
					workflowTaskNames = new String[] {"task2"};
				}
			});

		Assert.assertEquals(1, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(workflowTask2), (List<WorkflowTask>)page.getItems());
		assertValid(page);
	}

	@Override
	@Test
	public void testPostWorkflowTaskUpdateDueDate() throws Exception {
		WorkflowTask workflowTask = testGetWorkflowTask_addWorkflowTask();

		Assert.assertNull(workflowTask.getDateDue());

		WorkflowTask postWorkflowTask =
			workflowTaskResource.postWorkflowTaskUpdateDueDate(
				workflowTask.getId(),
				new WorkflowTaskAssignToMe() {
					{
						dueDate = DateUtils.addDays(
							RandomTestUtil.nextDate(), 2);
					}
				});

		assertEquals(workflowTask, postWorkflowTask);
		assertValid(postWorkflowTask);
		Assert.assertNotNull(postWorkflowTask.getDateDue());
	}

	@Override
	protected String[] getAdditionalAssertFieldNames() {
		return new String[] {"completed", "label", "name"};
	}

	@Override
	protected WorkflowTask
			testGetWorkflowInstanceWorkflowTasksAssignedToMePage_addWorkflowTask(
				Long workflowInstanceId, WorkflowTask workflowTask)
		throws Exception {

		return testGetWorkflowTasksAssignedToMePage_addWorkflowTask(
			workflowTask);
	}

	@Override
	protected Long
			testGetWorkflowInstanceWorkflowTasksAssignedToMePage_getWorkflowInstanceId()
		throws Exception {

		return testGetWorkflowInstanceWorkflowTasksPage_getWorkflowInstanceId();
	}

	@Override
	protected WorkflowTask
			testGetWorkflowInstanceWorkflowTasksAssignedToUserPage_addWorkflowTask(
				Long workflowInstanceId, WorkflowTask workflowTask)
		throws Exception {

		workflowTask = testGetWorkflowTask_addWorkflowTask();

		workflowTaskResource.postWorkflowTaskAssignToUser(
			workflowTask.getId(),
			new WorkflowTaskAssignToUser() {
				{
					assigneeId = TestPropsValues.getUserId();
				}
			});

		return workflowTask;
	}

	@Override
	protected Long
			testGetWorkflowInstanceWorkflowTasksAssignedToUserPage_getWorkflowInstanceId()
		throws Exception {

		return testGetWorkflowInstanceWorkflowTasksPage_getWorkflowInstanceId();
	}

	@Override
	protected WorkflowTask
			testGetWorkflowInstanceWorkflowTasksPage_addWorkflowTask(
				Long workflowInstanceId, WorkflowTask workflowTask)
		throws Exception {

		return testGetWorkflowTask_addWorkflowTask();
	}

	@Override
	protected Long
			testGetWorkflowInstanceWorkflowTasksPage_getWorkflowInstanceId()
		throws Exception {

		return _workflowInstance.getId();
	}

	@Override
	protected WorkflowTask testGetWorkflowTask_addWorkflowTask()
		throws Exception {

		WorkflowTask workflowTask1 = _workflowTasks.pop();

		workflowTaskResource.postWorkflowTaskAssignToMe(
			workflowTask1.getId(), new WorkflowTaskAssignToMe());

		return workflowTask1;
	}

	@Override
	protected WorkflowTask testGetWorkflowTasksAssignedToMePage_addWorkflowTask(
			WorkflowTask workflowTask)
		throws Exception {

		return testGetWorkflowTask_addWorkflowTask();
	}

	@Override
	protected WorkflowTask
			testGetWorkflowTasksAssignedToRolePage_addWorkflowTask(
				WorkflowTask workflowTask)
		throws Exception {

		return testGetWorkflowTask_addWorkflowTask();
	}

	@Override
	protected WorkflowTask
			testGetWorkflowTasksAssignedToUserPage_addWorkflowTask(
				WorkflowTask workflowTask)
		throws Exception {

		return testGetWorkflowInstanceWorkflowTasksAssignedToUserPage_addWorkflowTask(
			workflowTask.getWorkflowInstanceId(), workflowTask);
	}

	@Override
	protected WorkflowTask
			testGetWorkflowTasksAssignedToUserRolesPage_addWorkflowTask(
				WorkflowTask workflowTask)
		throws Exception {

		return testGetWorkflowTask_addWorkflowTask();
	}

	@Override
	protected WorkflowTask testGraphQLWorkflowTask_addWorkflowTask()
		throws Exception {

		return testGetWorkflowTask_addWorkflowTask();
	}

	private void _assignWorkflowTaskToMyRolesPage(
			User user, long workflowTaskId)
		throws Exception {

		for (Role role : RoleLocalServiceUtil.getUserRoles(user.getUserId())) {
			WorkflowTaskManagerUtil.assignWorkflowTaskToRole(
				user.getCompanyId(), user.getUserId(), workflowTaskId,
				role.getRoleId(), StringPool.BLANK, null, null);
		}
	}

	private static WorkflowDefinition _workflowDefinition;

	@Inject
	private RoleLocalService _roleLocalService;

	@Inject
	private UserLocalService _userLocalService;

	private WorkflowInstance _workflowInstance;

	@Inject
	private WorkflowTaskManager _workflowTaskManager;

	private Stack<WorkflowTask> _workflowTasks;

}