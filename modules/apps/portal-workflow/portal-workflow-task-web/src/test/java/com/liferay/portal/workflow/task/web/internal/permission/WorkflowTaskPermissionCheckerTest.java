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
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.service.GroupLocalServiceWrapper;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.ProxyFactory;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.workflow.BaseWorkflowHandler;
import com.liferay.portal.kernel.workflow.DefaultWorkflowTask;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.kernel.workflow.WorkflowHandler;
import com.liferay.portal.kernel.workflow.WorkflowHandlerRegistryUtil;
import com.liferay.portal.kernel.workflow.WorkflowTask;
import com.liferay.portal.kernel.workflow.WorkflowTaskAssignee;
import com.liferay.portal.security.permission.SimplePermissionChecker;
import com.liferay.registry.BasicRegistryImpl;
import com.liferay.registry.RegistryUtil;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author Adam Brandizzi
 */
public class WorkflowTaskPermissionCheckerTest {

	@BeforeClass
	public static void setUpClass() throws PortalException {
		RegistryUtil.setRegistry(new BasicRegistryImpl());

		_setUpGroupLocalServiceUtil();
	}

	@Test
	public void testCompanyAdminHasPermission() {
		Assert.assertTrue(
			_workflowTaskPermissionChecker.hasPermission(
				RandomTestUtil.randomLong(), mockWorkflowTask(),
				mockCompanyAdminPermissionChecker()));
	}

	@Test
	public void testContentReviewerHasPermission() {
		PermissionChecker permissionChecker =
			mockContentReviewerPermissionChecker(RandomTestUtil.randomLong());

		Assert.assertTrue(
			_workflowTaskPermissionChecker.hasPermission(
				RandomTestUtil.randomLong(),
				mockWorkflowTask(
					User.class.getName(), permissionChecker.getUserId()),
				permissionChecker));
	}

	@Test
	public void testContentReviewerRoleHasPermission() {
		long[] permissionCheckerRoleIds = randomPermissionCheckerRoleIds();

		Assert.assertTrue(
			_workflowTaskPermissionChecker.hasPermission(
				RandomTestUtil.randomLong(),
				mockWorkflowTask(
					Role.class.getName(), permissionCheckerRoleIds[0]),
				mockContentReviewerPermissionChecker(
					RandomTestUtil.randomLong(), permissionCheckerRoleIds)));
	}

	@Test
	public void testContentReviewerRoleWithAssetViewPermissionHasPermission()
		throws PortalException {

		mockAssetRendererHasViewPermission(true);

		long[] permissionCheckerRoleIds = randomPermissionCheckerRoleIds();

		Assert.assertTrue(
			_workflowTaskPermissionChecker.hasPermission(
				RandomTestUtil.randomLong(),
				mockWorkflowTask(
					Role.class.getName(), permissionCheckerRoleIds[0]),
				mockPermissionChecker(
					RandomTestUtil.randomLong(), permissionCheckerRoleIds,
					false, false, false)));
	}

	@Test
	public void testNotAssigneeHasNoPermission() {
		long assigneeUserId = RandomTestUtil.randomLong();

		Assert.assertFalse(
			_workflowTaskPermissionChecker.hasPermission(
				assigneeUserId,
				mockWorkflowTask(User.class.getName(), assigneeUserId),
				mockContentReviewerPermissionChecker(
					RandomTestUtil.randomLong())));
	}

	@Test
	public void testNotAssigneeRoleHasNoPermission() {
		long assigneeRoleId = RandomTestUtil.randomLong();

		Assert.assertFalse(
			_workflowTaskPermissionChecker.hasPermission(
				assigneeRoleId,
				mockWorkflowTask(Role.class.getName(), assigneeRoleId),
				mockContentReviewerPermissionChecker(
					RandomTestUtil.randomLong())));
	}

	@Test
	public void testNotContentReviewerHasNoPermission() {
		Assert.assertFalse(
			_workflowTaskPermissionChecker.hasPermission(
				RandomTestUtil.randomLong(), mockWorkflowTask(),
				mockPermissionChecker(
					RandomTestUtil.randomLong(), new long[0], false, false,
					false)));
	}

	@Test
	public void testNotContentReviewerWithAssetViewPermissionHasNoPermission()
		throws PortalException {

		mockAssetRendererHasViewPermission(true);

		Assert.assertFalse(
			_workflowTaskPermissionChecker.hasPermission(
				RandomTestUtil.randomLong(), mockWorkflowTask(),
				mockPermissionChecker(
					RandomTestUtil.randomLong(), new long[0], false, false,
					false)));
	}

	@Test
	public void testNotContentReviewerWithAssetViewPermissionHasPermission()
		throws PortalException {

		// Checks permission on completed workflow task

		mockAssetRendererHasViewPermission(true);

		Assert.assertTrue(
			_workflowTaskPermissionChecker.hasPermission(
				RandomTestUtil.randomLong(), mockCompletedWorkflowTask(),
				mockPermissionChecker(
					RandomTestUtil.randomLong(), new long[0], false, false,
					false)));
	}

	@Test
	public void testNotContentReviewerWithNoAssetViewPermissionHasNoPermission()
		throws PortalException {

		long[] permissionCheckerRoleIds = randomPermissionCheckerRoleIds();

		mockAssetRendererHasViewPermission(false);

		Assert.assertFalse(
			_workflowTaskPermissionChecker.hasPermission(
				RandomTestUtil.randomLong(),
				mockWorkflowTask(
					Role.class.getName(), permissionCheckerRoleIds[0]),
				mockPermissionChecker(
					RandomTestUtil.randomLong(), permissionCheckerRoleIds,
					false, false, false)));
	}

	@Test
	public void testNotContentReviewerWithoutAssetViewPermissionHasNoPermission()
		throws PortalException {

		// Checks permission on completed workflow task

		mockAssetRendererHasViewPermission(false);

		Assert.assertFalse(
			_workflowTaskPermissionChecker.hasPermission(
				RandomTestUtil.randomLong(), mockCompletedWorkflowTask(),
				mockPermissionChecker(
					RandomTestUtil.randomLong(), new long[0], false, false,
					false)));
	}

	@Test
	public void testOmniadminHasPermission() {
		Assert.assertTrue(
			_workflowTaskPermissionChecker.hasPermission(
				RandomTestUtil.randomLong(), mockWorkflowTask(),
				mockOmniadminPermissionChecker()));
	}

	protected void mockAssetRendererHasViewPermission(
		boolean hasAssetViewPermission) {

		Map<String, WorkflowHandler<?>> workflowHandlerMap =
			ReflectionTestUtil.getFieldValue(
				WorkflowHandlerRegistryUtil.class, "_workflowHandlerMap");

		workflowHandlerMap.put(
			_TEST_CONTEXT_ENTRY_CLASS_NAME,
			new BaseWorkflowHandler<Object>() {

				@Override
				public AssetRenderer<Object> getAssetRenderer(long classPK) {
					return (AssetRenderer<Object>)ProxyUtil.newProxyInstance(
						AssetRenderer.class.getClassLoader(),
						new Class<?>[] {AssetRenderer.class},
						(proxy, method, args) -> {
							if (Objects.equals(
									method.getName(), "hasViewPermission")) {

								return hasAssetViewPermission;
							}

							return method.getDefaultValue();
						});
				}

				@Override
				public String getClassName() {
					return _TEST_CONTEXT_ENTRY_CLASS_NAME;
				}

				@Override
				public String getType(Locale locale) {
					return null;
				}

				@Override
				public Object updateStatus(
					int status, Map<String, Serializable> workflowContext) {

					return null;
				}

			});
	}

	protected PermissionChecker mockCompanyAdminPermissionChecker() {
		return mockPermissionChecker(
			RandomTestUtil.randomLong(), new long[0], true, false, false);
	}

	protected WorkflowTask mockCompletedWorkflowTask() {
		return mockWorkflowTask(
			Role.class.getName(), RandomTestUtil.randomLong(), true);
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
		boolean contentReviewer, boolean paraOmniadmin) {

		return new SimplePermissionChecker() {

			@Override
			public long getCompanyId() {
				return 0;
			}

			@Override
			public long[] getRoleIds(long userId, long groupId) {
				return roleIds;
			}

			@Override
			public long getUserId() {
				return userId;
			}

			@Override
			public boolean isCompanyAdmin() {
				return companyAdmin;
			}

			@Override
			public boolean isContentReviewer(long companyId, long groupId) {
				return contentReviewer;
			}

			@Override
			public boolean isOmniadmin() {
				return paraOmniadmin;
			}

		};
	}

	protected WorkflowTask mockWorkflowTask() {
		return mockWorkflowTask(
			Role.class.getName(), RandomTestUtil.randomLong());
	}

	protected WorkflowTask mockWorkflowTask(
		String assigneeClassName, long assigneeClassPK) {

		return mockWorkflowTask(assigneeClassName, assigneeClassPK, false);
	}

	protected WorkflowTask mockWorkflowTask(
		String assigneeClassName, long assigneeClassPK, boolean completed) {

		WorkflowTaskAssignee workflowTaskAssignee = new WorkflowTaskAssignee(
			assigneeClassName, assigneeClassPK);

		List<WorkflowTaskAssignee> workflowTaskAssignees = new ArrayList<>();

		workflowTaskAssignees.add(workflowTaskAssignee);

		return new DefaultWorkflowTask() {

			@Override
			public Map<String, Serializable> getOptionalAttributes() {
				return Collections.singletonMap(
					WorkflowConstants.CONTEXT_ENTRY_CLASS_NAME,
					_TEST_CONTEXT_ENTRY_CLASS_NAME);
			}

			@Override
			public List<WorkflowTaskAssignee> getWorkflowTaskAssignees() {
				return workflowTaskAssignees;
			}

			@Override
			public boolean isCompleted() {
				return completed;
			}

		};
	}

	protected long[] randomPermissionCheckerRoleIds() {
		return new long[] {RandomTestUtil.randomLong()};
	}

	private static void _setUpGroupLocalServiceUtil() throws PortalException {
		ReflectionTestUtil.setFieldValue(
			GroupLocalServiceUtil.class, "_service",
			new GroupLocalServiceWrapper(null) {

				@Override
				public Group getGroup(long groupId) throws PortalException {
					return ProxyFactory.newDummyInstance(Group.class);
				}

			});
	}

	private static final String _TEST_CONTEXT_ENTRY_CLASS_NAME =
		"TEST_CONTEXT_ENTRY_CLASS_NAME";

	private final WorkflowTaskPermissionChecker _workflowTaskPermissionChecker =
		new WorkflowTaskPermissionChecker();

}