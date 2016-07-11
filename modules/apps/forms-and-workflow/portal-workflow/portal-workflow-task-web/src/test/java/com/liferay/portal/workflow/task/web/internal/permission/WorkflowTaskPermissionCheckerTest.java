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

package com.liferay.portal.workflow.task.web.internal.permission;

import com.liferay.asset.kernel.model.AssetRenderer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.workflow.WorkflowHandler;
import com.liferay.portal.kernel.workflow.WorkflowHandlerRegistryUtil;
import com.liferay.portal.kernel.workflow.WorkflowTask;
import com.liferay.portal.kernel.workflow.WorkflowTaskAssignee;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.Matchers;

import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.core.classloader.annotations.SuppressStaticInitializationFor;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 * @author Adam Brandizzi
 */
@PrepareForTest({WorkflowHandlerRegistryUtil.class})
@RunWith(PowerMockRunner.class)
@SuppressStaticInitializationFor(
	{"com.liferay.portal.kernel.workflow.WorkflowHandlerRegistryUtil"}
)
public class WorkflowTaskPermissionCheckerTest extends PowerMockito {

	@Before
	public void setUp() {
		setUpWorkflowHandlerRegistryUtil();
	}

	@Test
	public void testCompanyAdminHasPermission() {
		Assert.assertTrue(
			_workflowTaskPermissionChecker.hasPermission(
				RandomTestUtil.randomLong(), mockWorkflowTask(),
				mockCompanyAdminPermissionChecker()));
	}

	@Test
	public void testIsContentReviewerHasPermission() {
		PermissionChecker permissionChecker =
			mockContentReviewerPermissionChecker(RandomTestUtil.randomLong());

		WorkflowTask workflowTask = mockWorkflowTask(
			User.class.getName(), permissionChecker.getUserId());

		Assert.assertTrue(
			_workflowTaskPermissionChecker.hasPermission(
				RandomTestUtil.randomLong(), workflowTask, permissionChecker));
	}

	@Test
	public void testIsContentReviewerRoleHasPermission() {
		long[] permissionCheckerRoleIds = randomPermissionCheckerRoleIds();

		WorkflowTask workflowTask = mockWorkflowTask(
			Role.class.getName(), permissionCheckerRoleIds[0]);

		PermissionChecker permissionChecker =
			mockContentReviewerPermissionChecker(
				RandomTestUtil.randomLong(), permissionCheckerRoleIds);

		Assert.assertTrue(
			_workflowTaskPermissionChecker.hasPermission(
				RandomTestUtil.randomLong(), workflowTask, permissionChecker));
	}

	@Test
	public void testNotAssigneeHasNoPermission() {
		long assigneeUserId = RandomTestUtil.randomLong();

		WorkflowTask workflowTask = mockWorkflowTask(
			User.class.getName(), assigneeUserId);

		long permissionCheckerUserId = RandomTestUtil.randomLong();

		PermissionChecker permissionChecker =
			mockContentReviewerPermissionChecker(permissionCheckerUserId);

		Assert.assertFalse(
			_workflowTaskPermissionChecker.hasPermission(
				assigneeUserId, workflowTask, permissionChecker));
	}

	@Test
	public void testNotAssigneeRoleHasNoPermission() {
		long assigneeRoleId = RandomTestUtil.randomLong();

		WorkflowTask workflowTask = mockWorkflowTask(
			Role.class.getName(), assigneeRoleId);

		PermissionChecker permissionChecker =
			mockContentReviewerPermissionChecker(RandomTestUtil.randomLong());

		Assert.assertFalse(
			_workflowTaskPermissionChecker.hasPermission(
				assigneeRoleId, workflowTask, permissionChecker));
	}

	@Test
	public void testNotContentReviewerHasNoPermission() {
		PermissionChecker permissionChecker = mockPermissionChecker(
			RandomTestUtil.randomLong(), new long[0], false, false, false);

		Assert.assertFalse(
			_workflowTaskPermissionChecker.hasPermission(
				RandomTestUtil.randomLong(), mockWorkflowTask(),
				permissionChecker));
	}

	@Test
	public void testNotContentReviewerWithAssetViewPermissionHasNoPermisssion()
		throws PortalException {

		PermissionChecker permissionChecker = mockPermissionChecker(
			RandomTestUtil.randomLong(), new long[0], false, false, false);

		mockAssetRendererHasViewPermission(true);

		Assert.assertFalse(
			_workflowTaskPermissionChecker.hasPermission(
				RandomTestUtil.randomLong(), mockWorkflowTask(),
				permissionChecker));
	}

	@Test
	public void testNotContentReviewerWithAssetViewPermissionHasPermission()
		throws PortalException {

		long[] permissionCheckerRoleIds = randomPermissionCheckerRoleIds();

		WorkflowTask workflowTask = mockWorkflowTask(
			Role.class.getName(), permissionCheckerRoleIds[0]);

		PermissionChecker permissionChecker = mockPermissionChecker(
			RandomTestUtil.randomLong(), permissionCheckerRoleIds, false, false,
			false);

		mockAssetRendererHasViewPermission(true);

		Assert.assertTrue(
			_workflowTaskPermissionChecker.hasPermission(
				RandomTestUtil.randomLong(), workflowTask, permissionChecker));
	}

	@Test
	public void testNotContentReviewerWithNoAssetViewPermissionHasNoPermission()
		throws PortalException {

		long[] permissionCheckerRoleIds = randomPermissionCheckerRoleIds();

		WorkflowTask workflowTask = mockWorkflowTask(
			Role.class.getName(), permissionCheckerRoleIds[0]);

		PermissionChecker permissionChecker = mockPermissionChecker(
			RandomTestUtil.randomLong(), permissionCheckerRoleIds, false, false,
			false);

		mockAssetRendererHasViewPermission(false);

		Assert.assertFalse(
			_workflowTaskPermissionChecker.hasPermission(
				RandomTestUtil.randomLong(), workflowTask, permissionChecker));
	}

	@Test
	public void testOmniadminHasPermission() {
		Assert.assertTrue(
			_workflowTaskPermissionChecker.hasPermission(
				RandomTestUtil.randomLong(), mockWorkflowTask(),
				mockOmniadminPermissionChecker()));
	}

	protected void mockAssetRendererHasViewPermission(
			boolean hasAssetViewPermission)
		throws PortalException {

		WorkflowHandler workflowHandler = mock(WorkflowHandler.class);

		when(
			WorkflowHandlerRegistryUtil.getWorkflowHandler(Matchers.anyString())
		).thenReturn(
			workflowHandler
		);

		AssetRenderer assetRenderer = mock(AssetRenderer.class);

		when(
			workflowHandler.getAssetRenderer(Matchers.anyLong())
		).thenReturn(
			assetRenderer
		);

		when(
			assetRenderer.hasViewPermission(
				Matchers.any(PermissionChecker.class))
		).thenReturn(
			hasAssetViewPermission
		);
	}

	protected PermissionChecker mockCompanyAdminPermissionChecker() {
		return mockPermissionChecker(
			RandomTestUtil.randomLong(), new long[0], true, false, false);
	}

	protected PermissionChecker mockContentReviewerPermissionChecker(
		long userId) {

		return mockPermissionChecker(userId, new long[0], false, true, false);
	}

	protected PermissionChecker mockContentReviewerPermissionChecker(
		long userId, long[] roleIds) {

		return mockPermissionChecker(userId, roleIds, false, true, false);
	}

	protected PermissionChecker mockOmniadminPermissionChecker() {
		return mockPermissionChecker(
			RandomTestUtil.randomLong(), new long[0], false, false, true);
	}

	protected PermissionChecker mockPermissionChecker(
		long userId, long[] roleIds, boolean companyAdmin,
		boolean contentReviewer, boolean omniadmin) {

		PermissionChecker permissionChecker = mock(PermissionChecker.class);

		when(
			permissionChecker.getUserId()
		).thenReturn(
			userId
		);

		when(
			permissionChecker.getRoleIds(Matchers.anyLong(), Matchers.anyLong())
		).thenReturn(
			roleIds
		);

		when(
			permissionChecker.isCompanyAdmin()
		).thenReturn(
			companyAdmin
		);

		when(
			permissionChecker.isContentReviewer(
				Matchers.anyLong(), Matchers.anyLong())
		).thenReturn(
			contentReviewer
		);

		when(
			permissionChecker.isOmniadmin()
		).thenReturn(
			omniadmin
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

		List<WorkflowTaskAssignee> workflowTaskAssignees = new ArrayList<>();

		workflowTaskAssignees.add(workflowTaskAssignee);

		when(
			workflowTask.getOptionalAttributes()
		).thenReturn(
			new HashMap<String, Serializable>()
		);

		when(
			workflowTask.getWorkflowTaskAssignees()
		).thenReturn(
			workflowTaskAssignees
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

	protected long[] randomPermissionCheckerRoleIds() {
		return new long[] {RandomTestUtil.randomLong()};
	}

	protected void setUpWorkflowHandlerRegistryUtil() {
		mockStatic(WorkflowHandlerRegistryUtil.class);
	}

	private final WorkflowTaskPermissionChecker _workflowTaskPermissionChecker =
		new WorkflowTaskPermissionChecker();

}