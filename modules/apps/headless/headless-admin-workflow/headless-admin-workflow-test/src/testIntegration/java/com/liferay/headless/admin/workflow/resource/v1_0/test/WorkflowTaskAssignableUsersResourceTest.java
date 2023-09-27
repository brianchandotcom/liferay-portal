/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.workflow.resource.v1_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.headless.admin.workflow.client.dto.v1_0.Assignee;
import com.liferay.headless.admin.workflow.client.dto.v1_0.WorkflowInstance;
import com.liferay.headless.admin.workflow.client.dto.v1_0.WorkflowTask;
import com.liferay.headless.admin.workflow.client.dto.v1_0.WorkflowTaskAssignableUser;
import com.liferay.headless.admin.workflow.client.dto.v1_0.WorkflowTaskAssignableUsers;
import com.liferay.headless.admin.workflow.client.dto.v1_0.WorkflowTaskIds;
import com.liferay.headless.admin.workflow.resource.v1_0.test.util.AssigneeTestUtil;
import com.liferay.headless.admin.workflow.resource.v1_0.test.util.ObjectReviewedTestUtil;
import com.liferay.headless.admin.workflow.resource.v1_0.test.util.WorkflowDefinitionTestUtil;
import com.liferay.headless.admin.workflow.resource.v1_0.test.util.WorkflowInstanceTestUtil;
import com.liferay.headless.admin.workflow.resource.v1_0.test.util.WorkflowTaskTestUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.test.rule.DataGuard;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.ArrayUtil;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Javier Gamarra
 */
@DataGuard(scope = DataGuard.Scope.METHOD)
@RunWith(Arquillian.class)
public class WorkflowTaskAssignableUsersResourceTest
	extends BaseWorkflowTaskAssignableUsersResourceTestCase {

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		WorkflowInstance workflowInstance =
			WorkflowInstanceTestUtil.addWorkflowInstance(
				testGroup.getGroupId(),
				ObjectReviewedTestUtil.addObjectReviewed(),
				WorkflowDefinitionTestUtil.addWorkflowDefinition());

		_workflowTask = WorkflowTaskTestUtil.getWorkflowTask(
			workflowInstance.getId());

		_user = TestPropsValues.getUser();
	}

	@Override
	@Test
	public void testPostWorkflowTaskAssignableUser() throws Exception {
		_assertPostWorkflowTaskAssignableUser(
			AssigneeTestUtil.toAssignee(_user));

		_assertPostWorkflowTaskAssignableUser(
			AssigneeTestUtil.addAssignee(testGroup),
			AssigneeTestUtil.addAssignee(testGroup),
			AssigneeTestUtil.toAssignee(_user));
	}

	private void _assertPostWorkflowTaskAssignableUser(
			Assignee... expectedAssignableUsers)
		throws Exception {

		WorkflowTaskAssignableUsers workflowTaskAssignableUsers =
			workflowTaskAssignableUsersResource.postWorkflowTaskAssignableUser(
				new WorkflowTaskIds() {
					{
						workflowTaskIds = new Long[] {_workflowTask.getId()};
					}
				});

		Assert.assertNotNull(
			workflowTaskAssignableUsers.getWorkflowTaskAssignableUsers());

		WorkflowTaskAssignableUser workflowTaskAssignableUser =
			(WorkflowTaskAssignableUser)ArrayUtil.getValue(
				workflowTaskAssignableUsers.getWorkflowTaskAssignableUsers(),
				0);

		Assert.assertEquals(
			_workflowTask.getId(),
			workflowTaskAssignableUser.getWorkflowTaskId());
		Assert.assertEquals(
			expectedAssignableUsers.length,
			ArrayUtil.getLength(
				workflowTaskAssignableUser.getAssignableUsers()));

		for (Assignee expectedAssignableUser : expectedAssignableUsers) {
			Assert.assertTrue(
				ArrayUtil.contains(
					workflowTaskAssignableUser.getAssignableUsers(),
					expectedAssignableUser));
		}
	}

	private User _user;
	private WorkflowTask _workflowTask;

}