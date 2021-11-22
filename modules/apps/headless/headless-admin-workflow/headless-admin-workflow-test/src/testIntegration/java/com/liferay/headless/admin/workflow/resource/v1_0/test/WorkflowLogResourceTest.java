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
import com.liferay.headless.admin.workflow.client.dto.v1_0.WorkflowDefinition;
import com.liferay.headless.admin.workflow.client.dto.v1_0.WorkflowInstance;
import com.liferay.headless.admin.workflow.client.dto.v1_0.WorkflowLog;
import com.liferay.headless.admin.workflow.client.dto.v1_0.WorkflowTask;
import com.liferay.headless.admin.workflow.client.pagination.Page;
import com.liferay.headless.admin.workflow.client.pagination.Pagination;
import com.liferay.headless.admin.workflow.resource.v1_0.test.util.ObjectReviewedTestUtil;
import com.liferay.headless.admin.workflow.resource.v1_0.test.util.WorkflowDefinitionTestUtil;
import com.liferay.headless.admin.workflow.resource.v1_0.test.util.WorkflowInstanceTestUtil;
import com.liferay.headless.admin.workflow.resource.v1_0.test.util.WorkflowTaskTestUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.workflow.WorkflowTaskManager;
import com.liferay.portal.test.rule.Inject;

import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Javier Gamarra
 */
@RunWith(Arquillian.class)
public class WorkflowLogResourceTest extends BaseWorkflowLogResourceTestCase {

	@BeforeClass
	public static void setUpClass() throws Exception {
		BaseWorkflowLogResourceTestCase.setUpClass();

		_workflowDefinition =
			WorkflowDefinitionTestUtil.addWorkflowDefinition();

		PermissionThreadLocal.setPermissionChecker(
			PermissionCheckerFactoryUtil.create(
				UserLocalServiceUtil.getUser(TestPropsValues.getUserId())));
	}

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_siteContentReviewerRole = _roleLocalService.getRole(
			testGroup.getCompanyId(), RoleConstants.SITE_CONTENT_REVIEWER);

		_workflowInstance = WorkflowInstanceTestUtil.addWorkflowInstance(
			testGroup.getGroupId(), ObjectReviewedTestUtil.addObjectReviewed(),
			_workflowDefinition);
	}

	@Override
	@Test
	public void testGetWorkflowInstanceWorkflowLogsPage() throws Exception {
		WorkflowTask workflowTask = WorkflowTaskTestUtil.getWorkflowTask(
			_workflowInstance.getId());

		_workflowTaskManager.assignWorkflowTaskToUser(
			TestPropsValues.getCompanyId(), TestPropsValues.getUserId(),
			workflowTask.getId(), TestPropsValues.getUserId(), StringPool.BLANK,
			null, null);

		Page<WorkflowLog> page =
			workflowLogResource.getWorkflowInstanceWorkflowLogsPage(
				_workflowInstance.getId(),
				new String[] {WorkflowLog.Type.TRANSITION.getValue()},
				Pagination.of(1, 2));

		Assert.assertEquals(0, page.getTotalCount());

		page = workflowLogResource.getWorkflowInstanceWorkflowLogsPage(
			_workflowInstance.getId(),
			new String[] {WorkflowLog.Type.TASK_ASSIGN.getValue()},
			Pagination.of(1, 2));

		Assert.assertEquals(2, page.getTotalCount());

		assertEquals(
			Arrays.asList(
				new WorkflowLog() {
					{
						commentLog = StringPool.BLANK;
						description = _language.format(
							LocaleUtil.getDefault(),
							"x-assigned-the-task-to-himself",
							_portal.getUserName(
								TestPropsValues.getUserId(), StringPool.BLANK),
							false);
						state = "review";
						type = Type.TASK_ASSIGN;
						workflowTaskId = workflowTask.getId();
					}
				},
				new WorkflowLog() {
					{
						commentLog = _language.get(
							LocaleUtil.getDefault(), "assigned-initial-task");
						description = _language.format(
							LocaleUtil.getDefault(),
							"task-initially-assigned-to-the-x-role",
							_siteContentReviewerRole.getTitle(
								LocaleUtil.getDefault()),
							false);
						state = "review";
						type = Type.TASK_ASSIGN;
						workflowTaskId = workflowTask.getId();
					}
				}),
			(List<WorkflowLog>)page.getItems());

		assertValid(page);
	}

	@Override
	@Test
	public void testGetWorkflowInstanceWorkflowLogsPageWithPagination()
		throws Exception {

		WorkflowTask workflowTask = WorkflowTaskTestUtil.getWorkflowTask(
			_workflowInstance.getId());

		_workflowTaskManager.assignWorkflowTaskToUser(
			TestPropsValues.getCompanyId(), TestPropsValues.getUserId(),
			workflowTask.getId(), TestPropsValues.getUserId(), StringPool.BLANK,
			null, null);

		Page<WorkflowLog> page1 =
			workflowLogResource.getWorkflowInstanceWorkflowLogsPage(
				_workflowInstance.getId(),
				new String[] {WorkflowLog.Type.TASK_ASSIGN.getValue()},
				Pagination.of(1, 1));

		List<WorkflowLog> workflowLogs1 = (List<WorkflowLog>)page1.getItems();

		Assert.assertEquals(workflowLogs1.toString(), 1, workflowLogs1.size());

		assertEquals(
			Arrays.asList(
				new WorkflowLog() {
					{
						commentLog = StringPool.BLANK;
						description = _language.format(
							LocaleUtil.getDefault(),
							"x-assigned-the-task-to-himself",
							_portal.getUserName(
								TestPropsValues.getUserId(), StringPool.BLANK),
							false);
						state = "review";
						type = Type.TASK_ASSIGN;
						workflowTaskId = workflowTask.getId();
					}
				}),
			workflowLogs1);

		Page<WorkflowLog> page2 =
			workflowLogResource.getWorkflowInstanceWorkflowLogsPage(
				_workflowInstance.getId(),
				new String[] {WorkflowLog.Type.TASK_ASSIGN.getValue()},
				Pagination.of(2, 1));

		Assert.assertEquals(2, page2.getTotalCount());

		List<WorkflowLog> workflowLogs2 = (List<WorkflowLog>)page2.getItems();

		Assert.assertEquals(workflowLogs2.toString(), 1, workflowLogs2.size());

		assertEquals(
			Arrays.asList(
				new WorkflowLog() {
					{
						commentLog = _language.get(
							LocaleUtil.getDefault(), "assigned-initial-task");
						description = _language.format(
							LocaleUtil.getDefault(),
							"task-initially-assigned-to-the-x-role",
							_siteContentReviewerRole.getTitle(
								LocaleUtil.getDefault()),
							false);
						state = "review";
						type = Type.TASK_ASSIGN;
						workflowTaskId = workflowTask.getId();
					}
				}),
			workflowLogs2);
	}

	@Override
	@Test
	public void testGetWorkflowTaskWorkflowLogsPage() throws Exception {
		WorkflowTask workflowTask = WorkflowTaskTestUtil.getWorkflowTask(
			_workflowInstance.getId());

		Page<WorkflowLog> page =
			workflowLogResource.getWorkflowTaskWorkflowLogsPage(
				workflowTask.getId(),
				new String[] {WorkflowLog.Type.TASK_ASSIGN.getValue()},
				Pagination.of(1, 2));

		Assert.assertEquals(1, page.getTotalCount());

		assertEquals(
			Arrays.asList(
				new WorkflowLog() {
					{
						commentLog = _language.get(
							LocaleUtil.getDefault(), "assigned-initial-task");
						description = _language.format(
							LocaleUtil.getDefault(),
							"task-initially-assigned-to-the-x-role",
							_siteContentReviewerRole.getTitle(
								LocaleUtil.getDefault()),
							false);
						state = "review";
						type = Type.TASK_ASSIGN;
						workflowTaskId = workflowTask.getId();
					}
				}),
			(List<WorkflowLog>)page.getItems());

		assertValid(page);
	}

	@Override
	@Test
	public void testGetWorkflowTaskWorkflowLogsPageWithPagination()
		throws Exception {

		WorkflowTask workflowTask = WorkflowTaskTestUtil.getWorkflowTask(
			_workflowInstance.getId());

		_workflowTaskManager.assignWorkflowTaskToUser(
			TestPropsValues.getCompanyId(), TestPropsValues.getUserId(),
			workflowTask.getId(), TestPropsValues.getUserId(), StringPool.BLANK,
			null, null);

		Page<WorkflowLog> page1 =
			workflowLogResource.getWorkflowTaskWorkflowLogsPage(
				workflowTask.getId(),
				new String[] {WorkflowLog.Type.TASK_ASSIGN.getValue()},
				Pagination.of(1, 1));

		List<WorkflowLog> workflowLogs1 = (List<WorkflowLog>)page1.getItems();

		Assert.assertEquals(workflowLogs1.toString(), 1, workflowLogs1.size());

		assertEquals(
			Arrays.asList(
				new WorkflowLog() {
					{
						commentLog = StringPool.BLANK;
						description = _language.format(
							LocaleUtil.getDefault(),
							"x-assigned-the-task-to-himself",
							_portal.getUserName(
								TestPropsValues.getUserId(), StringPool.BLANK),
							false);
						state = "review";
						type = Type.TASK_ASSIGN;
						workflowTaskId = workflowTask.getId();
					}
				}),
			workflowLogs1);

		Page<WorkflowLog> page2 =
			workflowLogResource.getWorkflowTaskWorkflowLogsPage(
				workflowTask.getId(),
				new String[] {WorkflowLog.Type.TASK_ASSIGN.getValue()},
				Pagination.of(2, 1));

		Assert.assertEquals(2, page2.getTotalCount());

		List<WorkflowLog> workflowLogs2 = (List<WorkflowLog>)page2.getItems();

		Assert.assertEquals(workflowLogs2.toString(), 1, workflowLogs2.size());

		assertEquals(
			Arrays.asList(
				new WorkflowLog() {
					{
						commentLog = _language.get(
							LocaleUtil.getDefault(), "assigned-initial-task");
						description = _language.format(
							LocaleUtil.getDefault(),
							"task-initially-assigned-to-the-x-role",
							_siteContentReviewerRole.getTitle(
								LocaleUtil.getDefault()),
							false);
						state = "review";
						type = Type.TASK_ASSIGN;
						workflowTaskId = workflowTask.getId();
					}
				}),
			workflowLogs2);
	}

	@Override
	protected String[] getAdditionalAssertFieldNames() {
		return new String[] {
			"commentLog", "description", "state", "type", "workflowTaskId"
		};
	}

	@Override
	protected WorkflowLog testGetWorkflowLog_addWorkflowLog() throws Exception {
		Page<WorkflowLog> page =
			workflowLogResource.getWorkflowInstanceWorkflowLogsPage(
				_workflowInstance.getId(),
				new String[] {WorkflowLog.Type.TASK_ASSIGN.getValue()},
				Pagination.of(1, 2));

		List<WorkflowLog> items = (List<WorkflowLog>)page.getItems();

		return items.get(0);
	}

	@Override
	protected WorkflowLog testGraphQLWorkflowLog_addWorkflowLog()
		throws Exception {

		return testGetWorkflowLog_addWorkflowLog();
	}

	private static WorkflowDefinition _workflowDefinition;
	private static WorkflowInstance _workflowInstance;

	@Inject
	private Language _language;

	@Inject
	private Portal _portal;

	@Inject
	private RoleLocalService _roleLocalService;

	private Role _siteContentReviewerRole;

	@Inject
	private WorkflowTaskManager _workflowTaskManager;

}