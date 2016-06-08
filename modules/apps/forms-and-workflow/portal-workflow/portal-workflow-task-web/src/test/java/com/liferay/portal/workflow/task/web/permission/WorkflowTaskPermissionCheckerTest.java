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

package com.liferay.portal.workflow.task.web.permission;

import static org.mockito.Mockito.mock;

import static org.powermock.api.mockito.PowerMockito.when;

import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.workflow.WorkflowTask;
import com.liferay.portal.kernel.workflow.WorkflowTaskAssignee;

import java.util.ArrayList;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.Matchers;

import org.powermock.modules.junit4.PowerMockRunner;

/**
 * @author Adam Brandizzi
 */
@RunWith(PowerMockRunner.class)
public class WorkflowTaskPermissionCheckerTest {

	@Test
	public void testAssigneeIsContentReviewerHasPermission() {
		PermissionChecker permissionChecker =
			mockContentReviewerPermissionChecker();

		WorkflowTask workflowTask = mockWorkflowTask(
			User.class.getName(), permissionChecker.getUserId());

		Assert.assertTrue(
			_workflowTaskPermissionChecker.hasPermission(
				RandomTestUtil.randomLong(), workflowTask, permissionChecker));
	}

	@Test
	public void testAssigneeIsContentReviewerRoleHasPermission() {
		PermissionChecker permissionChecker =
			mockContentReviewerPermissionChecker();

		long[] roleIds = permissionChecker.getRoleIds(
			RandomTestUtil.randomLong(), RandomTestUtil.randomLong());

		WorkflowTask workflowTask = mockWorkflowTask(
			Role.class.getName(), roleIds[0]);

		Assert.assertTrue(
			_workflowTaskPermissionChecker.hasPermission(
				RandomTestUtil.randomLong(), workflowTask, permissionChecker));
	}

	@Test
	public void testAssigneeIsNonContentReviewerRoleHasNoPermission() {
		WorkflowTask workflowTask = mockWorkflowTask(
			Role.class.getName(), RandomTestUtil.randomLong());

		Assert.assertFalse(
			_workflowTaskPermissionChecker.hasPermission(
				RandomTestUtil.randomLong(), workflowTask,
				mockContentReviewerPermissionChecker()));
	}

	@Test
	public void testAssigneeIsNotContentReviewerHasNoPermission() {
		WorkflowTask workflowTask = mockWorkflowTask(
			User.class.getName(), RandomTestUtil.randomLong());

		Assert.assertFalse(
			_workflowTaskPermissionChecker.hasPermission(
				RandomTestUtil.randomLong(), workflowTask,
				mockContentReviewerPermissionChecker()));
	}

	@Test
	public void testCompanyAdminHasPermission() {
		Assert.assertTrue(
			_workflowTaskPermissionChecker.hasPermission(
				RandomTestUtil.randomLong(), mockWorkflowTask(),
				mockCompanyAdminPermissionChecker()));
	}

	@Test
	public void testNonContentReviewerHasNoPermission() {
		PermissionChecker permissionChecker = mockPermissionChecker(
			false, false, false);

		Assert.assertFalse(
			_workflowTaskPermissionChecker.hasPermission(
				RandomTestUtil.randomLong(), mockWorkflowTask(),
				permissionChecker));
	}

	@Test
	public void testOmniadminHasPermission() {
		Assert.assertTrue(
			_workflowTaskPermissionChecker.hasPermission(
				RandomTestUtil.randomLong(), mockWorkflowTask(),
				mockOmniadminPermissionChecker()));
	}

	protected PermissionChecker mockCompanyAdminPermissionChecker() {
		return mockPermissionChecker(false, true, false);
	}

	protected PermissionChecker mockContentReviewerPermissionChecker() {
		return mockPermissionChecker(false, false, true);
	}

	protected PermissionChecker mockOmniadminPermissionChecker() {
		return mockPermissionChecker(true, false, false);
	}

	protected PermissionChecker mockPermissionChecker(
		boolean isOmniadmin, boolean isCompanyAdmin,
		boolean isContentReviewer) {

		PermissionChecker permissionChecker = mock(PermissionChecker.class);

		when(
			permissionChecker.isOmniadmin()
		).thenReturn(
			isOmniadmin
		);

		when(
			permissionChecker.isCompanyAdmin()
		).thenReturn(
			isCompanyAdmin
		);

		when(
			permissionChecker.isContentReviewer(
				Matchers.anyLong(), Matchers.anyLong())
		).thenReturn(
			isContentReviewer
		);

		when(
			permissionChecker.getUserId()
		).thenReturn(
			RandomTestUtil.randomLong()
		);

		long[] roleIds = new long[] {
			RandomTestUtil.randomLong(), RandomTestUtil.randomLong(),
			RandomTestUtil.randomLong()
		};

		when(
			permissionChecker.getRoleIds(Matchers.anyLong(), Matchers.anyLong())
		).thenReturn(
			roleIds
		);

		return permissionChecker;
	}

	protected WorkflowTask mockWorkflowTask() {
		return mockWorkflowTask(
			Role.class.getName(), RandomTestUtil.randomLong());
	}

	protected WorkflowTask mockWorkflowTask(
		String assigneeClassName, long assigneeClassPK) {

		WorkflowTaskAssignee workflowTaskAssignee = mockWorkflowTaskAssignee(
			assigneeClassName, assigneeClassPK);

		WorkflowTask workflowTask = mock(WorkflowTask.class);

		ArrayList<WorkflowTaskAssignee> assignees = new ArrayList<>();

		assignees.add(workflowTaskAssignee);

		when(
			workflowTask.getWorkflowTaskAssignees()
		).thenReturn(
			assignees
		);

		return workflowTask;
	}

	protected WorkflowTaskAssignee mockWorkflowTaskAssignee(
		String className, long classPK) {

		WorkflowTaskAssignee workflowTaskAssignee = mock(
			WorkflowTaskAssignee.class);

		when(
			workflowTaskAssignee.getAssigneeClassName()
		).thenReturn(
			className
		);

		when(
			workflowTaskAssignee.getAssigneeClassPK()
		).thenReturn(
			classPK
		);

		return workflowTaskAssignee;
	}

	private final WorkflowTaskPermissionChecker _workflowTaskPermissionChecker =
		new WorkflowTaskPermissionChecker();

}