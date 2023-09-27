/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.workflow.resource.v1_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.headless.admin.workflow.client.dto.v1_0.Assignee;
import com.liferay.headless.admin.workflow.client.dto.v1_0.WorkflowInstance;
import com.liferay.headless.admin.workflow.client.dto.v1_0.WorkflowTask;
import com.liferay.headless.admin.workflow.client.pagination.Page;
import com.liferay.headless.admin.workflow.client.pagination.Pagination;
import com.liferay.headless.admin.workflow.resource.v1_0.test.util.AssigneeTestUtil;
import com.liferay.headless.admin.workflow.resource.v1_0.test.util.ObjectReviewedTestUtil;
import com.liferay.headless.admin.workflow.resource.v1_0.test.util.WorkflowDefinitionTestUtil;
import com.liferay.headless.admin.workflow.resource.v1_0.test.util.WorkflowInstanceTestUtil;
import com.liferay.headless.admin.workflow.resource.v1_0.test.util.WorkflowTaskTestUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.test.rule.DataGuard;
import com.liferay.portal.kernel.test.util.TestPropsValues;

import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Javier Gamarra
 */
@DataGuard(scope = DataGuard.Scope.METHOD)
@RunWith(Arquillian.class)
public class AssigneeResourceTest extends BaseAssigneeResourceTestCase {

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_workflowInstance = WorkflowInstanceTestUtil.addWorkflowInstance(
			testGroup.getGroupId(), ObjectReviewedTestUtil.addObjectReviewed(),
			WorkflowDefinitionTestUtil.addWorkflowDefinition());

		_user = TestPropsValues.getUser();
	}

	@Override
	@Test
	public void testGetWorkflowTaskAssignableUsersPage() throws Exception {
		Long workflowTaskId =
			testGetWorkflowTaskAssignableUsersPage_getWorkflowTaskId();

		Page<Assignee> page =
			assigneeResource.getWorkflowTaskAssignableUsersPage(
				workflowTaskId, Pagination.of(1, 10));

		Assert.assertEquals(1, page.getTotalCount());

		Assignee assignee1 = testGetWorkflowTaskAssignableUsersPage_addAssignee(
			workflowTaskId, randomAssignee());
		Assignee assignee2 = testGetWorkflowTaskAssignableUsersPage_addAssignee(
			workflowTaskId, randomAssignee());

		page = assigneeResource.getWorkflowTaskAssignableUsersPage(
			workflowTaskId, Pagination.of(1, 10));

		Assert.assertEquals(3, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(
				assignee1, assignee2, AssigneeTestUtil.toAssignee(_user)),
			(List<Assignee>)page.getItems());
		assertValid(page);
	}

	@Override
	@Test
	public void testGetWorkflowTaskAssignableUsersPageWithPagination()
		throws Exception {

		Long workflowTaskId =
			testGetWorkflowTaskAssignableUsersPage_getWorkflowTaskId();

		Assignee assignee1 = testGetWorkflowTaskAssignableUsersPage_addAssignee(
			workflowTaskId, randomAssignee());
		Assignee assignee2 = testGetWorkflowTaskAssignableUsersPage_addAssignee(
			workflowTaskId, randomAssignee());

		Page<Assignee> page1 =
			assigneeResource.getWorkflowTaskAssignableUsersPage(
				workflowTaskId, Pagination.of(1, 2));

		List<Assignee> assignees1 = (List<Assignee>)page1.getItems();

		Assert.assertEquals(assignees1.toString(), 2, assignees1.size());

		Page<Assignee> page2 =
			assigneeResource.getWorkflowTaskAssignableUsersPage(
				workflowTaskId, Pagination.of(2, 2));

		Assert.assertEquals(3, page2.getTotalCount());

		List<Assignee> assignees2 = (List<Assignee>)page2.getItems();

		Assert.assertEquals(assignees2.toString(), 1, assignees2.size());

		Page<Assignee> page3 =
			assigneeResource.getWorkflowTaskAssignableUsersPage(
				workflowTaskId, Pagination.of(1, 3));

		assertEqualsIgnoringOrder(
			Arrays.asList(
				assignee1, assignee2, AssigneeTestUtil.toAssignee(_user)),
			(List<Assignee>)page3.getItems());
	}

	@Override
	protected String[] getAdditionalAssertFieldNames() {
		return new String[] {"name"};
	}

	@Override
	protected Assignee testGetWorkflowTaskAssignableUsersPage_addAssignee(
			Long workflowTaskId, Assignee assignee)
		throws Exception {

		return AssigneeTestUtil.addAssignee(testGroup);
	}

	@Override
	protected Long testGetWorkflowTaskAssignableUsersPage_getWorkflowTaskId()
		throws Exception {

		WorkflowTask workflowTask = WorkflowTaskTestUtil.getWorkflowTask(
			_workflowInstance.getId());

		return workflowTask.getId();
	}

	private User _user;
	private WorkflowInstance _workflowInstance;

}