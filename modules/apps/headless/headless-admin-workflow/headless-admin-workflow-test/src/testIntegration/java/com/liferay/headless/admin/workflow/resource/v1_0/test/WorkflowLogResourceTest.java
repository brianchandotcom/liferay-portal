/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

		_administratorRole = _roleLocalService.getRole(
			testGroup.getCompanyId(), RoleConstants.ADMINISTRATOR);
		_siteContentReviewerRole = _roleLocalService.getRole(
			testGroup.getCompanyId(), RoleConstants.SITE_CONTENT_REVIEWER);

		_workflowInstance = WorkflowInstanceTestUtil.addWorkflowInstance(
			testGroup.getGroupId(), ObjectReviewedTestUtil.addObjectReviewed(),
			_workflowDefinition);

		_workflowTask = WorkflowTaskTestUtil.getWorkflowTask(
			_workflowInstance.getId());
	}

	@Override
	@Test
	public void testGetWorkflowInstanceWorkflowLogsPage() throws Exception {
		_workflowTaskManager.assignWorkflowTaskToUser(
			TestPropsValues.getCompanyId(), TestPropsValues.getUserId(),
			_workflowTask.getId(), TestPropsValues.getUserId(),
			StringPool.BLANK, null, null);

		Page<WorkflowLog> page =
			workflowLogResource.getWorkflowInstanceWorkflowLogsPage(
				_workflowInstance.getId(),
				new String[] {WorkflowLog.Type.TRANSITION.getValue()},
				Pagination.of(1, 2));

		Assert.assertEquals(0, page.getTotalCount());

		page = workflowLogResource.getWorkflowInstanceWorkflowLogsPage(
			_workflowInstance.getId(),
			new String[] {WorkflowLog.Type.TASK_ASSIGN.getValue()},
			Pagination.of(1, 3));

		Assert.assertEquals(3, page.getTotalCount());

		assertEquals(
			Arrays.asList(
				_toAssignToHimselfWorkflowLog(),
				_toAssignToRoleWorkflowLog(_siteContentReviewerRole),
				_toAssignToRoleWorkflowLog(_administratorRole)),
			(List<WorkflowLog>)page.getItems());

		assertValid(page);
	}

	@Override
	@Test
	public void testGetWorkflowInstanceWorkflowLogsPageWithPagination()
		throws Exception {

		_workflowTaskManager.assignWorkflowTaskToUser(
			TestPropsValues.getCompanyId(), TestPropsValues.getUserId(),
			_workflowTask.getId(), TestPropsValues.getUserId(),
			StringPool.BLANK, null, null);

		Page<WorkflowLog> page1 =
			workflowLogResource.getWorkflowInstanceWorkflowLogsPage(
				_workflowInstance.getId(),
				new String[] {WorkflowLog.Type.TASK_ASSIGN.getValue()},
				Pagination.of(1, 2));

		Assert.assertEquals(3, page1.getTotalCount());

		List<WorkflowLog> workflowLogs1 = (List<WorkflowLog>)page1.getItems();

		Assert.assertEquals(workflowLogs1.toString(), 2, workflowLogs1.size());

		assertEquals(
			Arrays.asList(
				_toAssignToHimselfWorkflowLog(),
				_toAssignToRoleWorkflowLog(_siteContentReviewerRole)),
			workflowLogs1);

		Page<WorkflowLog> page3 =
			workflowLogResource.getWorkflowInstanceWorkflowLogsPage(
				_workflowInstance.getId(),
				new String[] {WorkflowLog.Type.TASK_ASSIGN.getValue()},
				Pagination.of(3, 1));

		Assert.assertEquals(3, page3.getTotalCount());

		List<WorkflowLog> workflowLogs2 = (List<WorkflowLog>)page3.getItems();

		Assert.assertEquals(workflowLogs2.toString(), 1, workflowLogs2.size());

		assertEquals(
			Arrays.asList(_toAssignToRoleWorkflowLog(_administratorRole)),
			workflowLogs2);
	}

	@Override
	@Test
	public void testGetWorkflowTaskWorkflowLogsPage() throws Exception {
		Page<WorkflowLog> page =
			workflowLogResource.getWorkflowTaskWorkflowLogsPage(
				_workflowTask.getId(),
				new String[] {WorkflowLog.Type.TASK_ASSIGN.getValue()},
				Pagination.of(1, 2));

		Assert.assertEquals(2, page.getTotalCount());

		assertEquals(
			Arrays.asList(
				_toAssignToRoleWorkflowLog(_siteContentReviewerRole),
				_toAssignToRoleWorkflowLog(_administratorRole)),
			(List<WorkflowLog>)page.getItems());

		assertValid(page);
	}

	@Override
	@Test
	public void testGetWorkflowTaskWorkflowLogsPageWithPagination()
		throws Exception {

		_workflowTaskManager.assignWorkflowTaskToUser(
			TestPropsValues.getCompanyId(), TestPropsValues.getUserId(),
			_workflowTask.getId(), TestPropsValues.getUserId(),
			StringPool.BLANK, null, null);

		Page<WorkflowLog> page1 =
			workflowLogResource.getWorkflowTaskWorkflowLogsPage(
				_workflowTask.getId(),
				new String[] {WorkflowLog.Type.TASK_ASSIGN.getValue()},
				Pagination.of(1, 2));

		List<WorkflowLog> workflowLogs1 = (List<WorkflowLog>)page1.getItems();

		Assert.assertEquals(workflowLogs1.toString(), 2, workflowLogs1.size());

		assertEquals(
			Arrays.asList(
				_toAssignToHimselfWorkflowLog(),
				_toAssignToRoleWorkflowLog(_siteContentReviewerRole)),
			workflowLogs1);

		Page<WorkflowLog> page3 =
			workflowLogResource.getWorkflowTaskWorkflowLogsPage(
				_workflowTask.getId(),
				new String[] {WorkflowLog.Type.TASK_ASSIGN.getValue()},
				Pagination.of(3, 1));

		Assert.assertEquals(3, page3.getTotalCount());

		List<WorkflowLog> workflowLogs2 = (List<WorkflowLog>)page3.getItems();

		Assert.assertEquals(workflowLogs2.toString(), 1, workflowLogs2.size());

		assertEquals(
			Arrays.asList(_toAssignToRoleWorkflowLog(_administratorRole)),
			workflowLogs2);
	}

	@Override
	protected String[] getAdditionalAssertFieldNames() {
		return new String[] {
			"commentLog", "description", "state", "stateLabel", "type",
			"workflowTaskId"
		};
	}

	@Override
	protected WorkflowLog testGetWorkflowLog_addWorkflowLog() throws Exception {
		Page<WorkflowLog> page =
			workflowLogResource.getWorkflowInstanceWorkflowLogsPage(
				_workflowInstance.getId(),
				new String[] {WorkflowLog.Type.TASK_ASSIGN.getValue()},
				Pagination.of(1, 2));

		List<WorkflowLog> workflowLogs = (List<WorkflowLog>)page.getItems();

		return workflowLogs.get(0);
	}

	@Override
	protected WorkflowLog testGraphQLWorkflowLog_addWorkflowLog()
		throws Exception {

		return testGetWorkflowLog_addWorkflowLog();
	}

	private WorkflowLog _toAssignToHimselfWorkflowLog() throws Exception {
		return new WorkflowLog() {
			{
				commentLog = StringPool.BLANK;
				description = _language.format(
					LocaleUtil.getDefault(), "x-assigned-the-task-to-himself",
					_portal.getUserName(
						TestPropsValues.getUserId(), StringPool.BLANK),
					false);
				state = "review";
				stateLabel = "Review";
				type = Type.TASK_ASSIGN;
				workflowTaskId = _workflowTask.getId();
			}
		};
	}

	private WorkflowLog _toAssignToRoleWorkflowLog(Role role) {
		String roleTitle = role.getTitle(LocaleUtil.getDefault());

		return new WorkflowLog() {
			{
				commentLog = _language.get(
					LocaleUtil.getDefault(), "assigned-initial-task");
				description = _language.format(
					LocaleUtil.getDefault(),
					"task-initially-assigned-to-the-x-role", roleTitle, false);
				state = "review";
				stateLabel = "Review";
				type = Type.TASK_ASSIGN;
				workflowTaskId = _workflowTask.getId();
			}
		};
	}

	private static WorkflowDefinition _workflowDefinition;
	private static WorkflowInstance _workflowInstance;

	private Role _administratorRole;

	@Inject
	private Language _language;

	@Inject
	private Portal _portal;

	@Inject
	private RoleLocalService _roleLocalService;

	private Role _siteContentReviewerRole;
	private WorkflowTask _workflowTask;

	@Inject
	private WorkflowTaskManager _workflowTaskManager;

}