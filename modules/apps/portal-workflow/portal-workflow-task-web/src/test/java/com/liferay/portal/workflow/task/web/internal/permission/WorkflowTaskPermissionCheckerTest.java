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
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.module.util.SystemBundleUtil;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.service.GroupLocalServiceWrapper;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserNotificationEventLocalServiceUtil;
import com.liferay.portal.kernel.service.UserNotificationEventLocalServiceWrapper;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.ProxyFactory;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.workflow.BaseWorkflowHandler;
import com.liferay.portal.kernel.workflow.DefaultWorkflowTask;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.kernel.workflow.WorkflowHandler;
import com.liferay.portal.kernel.workflow.WorkflowTask;
import com.liferay.portal.kernel.workflow.WorkflowTaskAssignee;
import com.liferay.portal.security.permission.SimplePermissionChecker;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.osgi.framework.BundleContext;

import org.powermock.api.mockito.PowerMockito;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 * @author Adam Brandizzi
 */
@RunWith(PowerMockRunner.class)
public class WorkflowTaskPermissionCheckerTest extends PowerMockito {

	@BeforeClass
	public static void setUpClass() {
		_setUpGroupLocalServiceUtil();
	}

	@Before
	public void setUp() {
		_setUpWorkflowHandlerRegistryUtil();

		mockUserNotificationEventLocalServiceUtil(0);
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
	public void testContentReviewerRoleWithAssetViewPermissionHasPermission() {
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
	public void testContentReviewerWithoutAssetViewPermissionHasPermissionOnCompletedTask() {
		long[] permissionCheckerRoleIds = randomPermissionCheckerRoleIds();

		Assert.assertTrue(
			_workflowTaskPermissionChecker.hasPermission(
				RandomTestUtil.randomLong(),
				mockCompletedWorkflowTask(
					Role.class.getName(), permissionCheckerRoleIds[0]),
				mockPermissionChecker(
					RandomTestUtil.randomLong(), permissionCheckerRoleIds,
					false, false, false)));
	}

	@Test
	public void testContentReviewerWithoutAssetViewPermissionHasPermissionOnPendingTask() {
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
	public void testNotContentReviewerWithAssetViewPermissionHasNoPermissionOnCompletedTask() {
		mockAssetRendererHasViewPermission(true);

		Assert.assertFalse(
			_workflowTaskPermissionChecker.hasPermission(
				RandomTestUtil.randomLong(), mockCompletedWorkflowTask(),
				mockPermissionChecker(
					RandomTestUtil.randomLong(), new long[0], false, false,
					false)));
	}

	@Test
	public void testNotContentReviewerWithAssetViewPermissionHasNoPermissionOnPendingTask() {
		mockAssetRendererHasViewPermission(true);

		Assert.assertFalse(
			_workflowTaskPermissionChecker.hasPermission(
				RandomTestUtil.randomLong(), mockWorkflowTask(),
				mockPermissionChecker(
					RandomTestUtil.randomLong(), new long[0], false, false,
					false)));
	}

	@Test
	public void testNotContentReviewerWithAssetViewPermissionHasPermissionOnPendingTaskWithNotification() {
		mockAssetRendererHasViewPermission(true);
		mockUserNotificationEventLocalServiceUtil(1);

		Assert.assertTrue(
			_workflowTaskPermissionChecker.hasPermission(
				RandomTestUtil.randomLong(), mockWorkflowTask(),
				mockPermissionChecker(
					RandomTestUtil.randomLong(), new long[0], false, false,
					false)));
	}

	@Test
	public void testNotContentReviewerWithoutAssetViewPermissionHasNoPermissionOnCompletedTask() {
		mockAssetRendererHasViewPermission(false);

		Assert.assertFalse(
			_workflowTaskPermissionChecker.hasPermission(
				RandomTestUtil.randomLong(), mockCompletedWorkflowTask(),
				mockPermissionChecker(
					RandomTestUtil.randomLong(), new long[0], false, false,
					false)));
	}

	@Test
	public void testNotContentReviewerWithoutAssetViewPermissionHasNoPermissionOnPendingTask() {
		mockAssetRendererHasViewPermission(false);

		Assert.assertFalse(
			_workflowTaskPermissionChecker.hasPermission(
				RandomTestUtil.randomLong(), mockWorkflowTask(),
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

		BundleContext bundleContext = SystemBundleUtil.getBundleContext();

		bundleContext.registerService(
			WorkflowHandler.class,
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

			},
			null);
	}

	protected PermissionChecker mockCompanyAdminPermissionChecker() {
		return mockPermissionChecker(
			RandomTestUtil.randomLong(), new long[0], true, false, false);
	}

	protected WorkflowTask mockCompletedWorkflowTask() {
		return mockCompletedWorkflowTask(
			Role.class.getName(), RandomTestUtil.randomLong());
	}

	protected WorkflowTask mockCompletedWorkflowTask(
		String assigneeClassName, long assigneeClassPK) {

		return mockWorkflowTask(assigneeClassName, assigneeClassPK, true);
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

	protected void mockUserNotificationEventLocalServiceUtil(int count) {
		ReflectionTestUtil.setFieldValue(
			UserNotificationEventLocalServiceUtil.class, "_service",
			new UserNotificationEventLocalServiceWrapper(null) {

				@Override
				public int getUserNotificationEventsCount(
					long userId, String type,
					Map<String, String> payloadParameter) {

					return count;
				}

			});
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

	private static void _setUpGroupLocalServiceUtil() {
		ReflectionTestUtil.setFieldValue(
			GroupLocalServiceUtil.class, "_service",
			new GroupLocalServiceWrapper(null) {

				@Override
				public Group getGroup(long groupId) {
					return ProxyFactory.newDummyInstance(Group.class);
				}

			});
	}

	private void _setUpWorkflowHandlerRegistryUtil() {
		BundleContext bundleContext = SystemBundleUtil.getBundleContext();

		bundleContext.registerService(
			WorkflowHandler.class,
			new BaseWorkflowHandler<Object>() {

				@Override
				public AssetRenderer<Object> getAssetRenderer(long classPK) {
					return (AssetRenderer<Object>)ProxyUtil.newProxyInstance(
						AssetRenderer.class.getClassLoader(),
						new Class<?>[] {AssetRenderer.class},
						(proxy, method, args) -> {
							if (Objects.equals(
									method.getName(), "hasViewPermission")) {

								return true;
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
				public String getURLEditWorkflowTask(
					long workflowTaskId, ServiceContext serviceContext) {

					return null;
				}

				@Override
				public Object updateStatus(
					int status, Map<String, Serializable> workflowContext) {

					return null;
				}

			},
			null);
	}

	private static final String _TEST_CONTEXT_ENTRY_CLASS_NAME =
		"TEST_CONTEXT_ENTRY_CLASS_NAME";

	private final WorkflowTaskPermissionChecker _workflowTaskPermissionChecker =
		new WorkflowTaskPermissionChecker();

}