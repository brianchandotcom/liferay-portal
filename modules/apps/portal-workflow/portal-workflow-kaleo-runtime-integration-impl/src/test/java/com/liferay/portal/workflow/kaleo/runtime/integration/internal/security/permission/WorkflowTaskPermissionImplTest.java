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

package com.liferay.portal.workflow.kaleo.runtime.integration.internal.security.permission;

import com.liferay.asset.kernel.model.AssetRenderer;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.module.util.SystemBundleUtil;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.service.GroupLocalServiceWrapper;
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
import com.liferay.portal.test.rule.LiferayUnitTestRule;
import com.liferay.portal.workflow.security.permission.WorkflowTaskPermission;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.osgi.framework.BundleContext;

/**
 * @author Adam Brandizzi
 */
public class WorkflowTaskPermissionImplTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() {
		_setUpGroupLocalService();
		_setUpWorkflowHandlerRegistryUtil();

		_mockUserNotificationEventLocalService(0);
	}

	@Test
	public void testCompanyAdminHasPermission() {
		Assert.assertTrue(
			_workflowTaskPermissionChecker.contains(
				_mockCompanyAdminPermissionChecker(), _mockWorkflowTask(),
				RandomTestUtil.randomLong()));
	}

	@Test
	public void testContentReviewerHasPermission() {
		PermissionChecker permissionChecker =
			_mockContentReviewerPermissionChecker(RandomTestUtil.randomLong());

		Assert.assertTrue(
			_workflowTaskPermissionChecker.contains(
				permissionChecker,
				_mockWorkflowTask(
					User.class.getName(), permissionChecker.getUserId()),
				RandomTestUtil.randomLong()));
	}

	@Test
	public void testContentReviewerRoleHasPermission() {
		long[] permissionCheckerRoleIds = _randomPermissionCheckerRoleIds();

		Assert.assertTrue(
			_workflowTaskPermissionChecker.contains(
				_mockContentReviewerPermissionChecker(
					RandomTestUtil.randomLong(), permissionCheckerRoleIds),
				_mockWorkflowTask(
					Role.class.getName(), permissionCheckerRoleIds[0]),
				RandomTestUtil.randomLong()));
	}

	@Test
	public void testContentReviewerRoleWithAssetViewPermissionHasPermission() {
		_mockAssetRendererHasViewPermission(true);

		long[] permissionCheckerRoleIds = _randomPermissionCheckerRoleIds();

		Assert.assertTrue(
			_workflowTaskPermissionChecker.contains(
				_mockPermissionChecker(
					RandomTestUtil.randomLong(), permissionCheckerRoleIds,
					false, false, false),
				_mockWorkflowTask(
					Role.class.getName(), permissionCheckerRoleIds[0]),
				RandomTestUtil.randomLong()));
	}

	@Test
	public void testContentReviewerWithoutAssetViewPermissionHasPermissionOnCompletedTask() {
		long[] permissionCheckerRoleIds = _randomPermissionCheckerRoleIds();

		Assert.assertTrue(
			_workflowTaskPermissionChecker.contains(
				_mockPermissionChecker(
					RandomTestUtil.randomLong(), permissionCheckerRoleIds,
					false, false, false),
				_mockCompletedWorkflowTask(
					Role.class.getName(), permissionCheckerRoleIds[0]),
				RandomTestUtil.randomLong()));
	}

	@Test
	public void testContentReviewerWithoutAssetViewPermissionHasPermissionOnPendingTask() {
		long[] permissionCheckerRoleIds = _randomPermissionCheckerRoleIds();

		Assert.assertTrue(
			_workflowTaskPermissionChecker.contains(
				_mockPermissionChecker(
					RandomTestUtil.randomLong(), permissionCheckerRoleIds,
					false, false, false),
				_mockWorkflowTask(
					Role.class.getName(), permissionCheckerRoleIds[0]),
				RandomTestUtil.randomLong()));
	}

	@Test
	public void testNotAssigneeHasNoPermission() {
		long assigneeUserId = RandomTestUtil.randomLong();

		Assert.assertFalse(
			_workflowTaskPermissionChecker.contains(
				_mockContentReviewerPermissionChecker(
					RandomTestUtil.randomLong()),
				_mockWorkflowTask(User.class.getName(), assigneeUserId),
				assigneeUserId));
	}

	@Test
	public void testNotAssigneeRoleHasNoPermission() {
		long assigneeRoleId = RandomTestUtil.randomLong();

		Assert.assertFalse(
			_workflowTaskPermissionChecker.contains(
				_mockContentReviewerPermissionChecker(
					RandomTestUtil.randomLong()),
				_mockWorkflowTask(Role.class.getName(), assigneeRoleId),
				assigneeRoleId));
	}

	@Test
	public void testNotContentReviewerHasNoPermission() {
		Assert.assertFalse(
			_workflowTaskPermissionChecker.contains(
				_mockPermissionChecker(
					RandomTestUtil.randomLong(), new long[0], false, false,
					false),
				_mockWorkflowTask(), RandomTestUtil.randomLong()));
	}

	@Test
	public void testNotContentReviewerWithAssetViewPermissionHasNoPermissionOnCompletedTask() {
		_mockAssetRendererHasViewPermission(true);

		Assert.assertFalse(
			_workflowTaskPermissionChecker.contains(
				_mockPermissionChecker(
					RandomTestUtil.randomLong(), new long[0], false, false,
					false),
				_mockCompletedWorkflowTask(), RandomTestUtil.randomLong()));
	}

	@Test
	public void testNotContentReviewerWithAssetViewPermissionHasNoPermissionOnPendingTask() {
		_mockAssetRendererHasViewPermission(true);

		Assert.assertFalse(
			_workflowTaskPermissionChecker.contains(
				_mockPermissionChecker(
					RandomTestUtil.randomLong(), new long[0], false, false,
					false),
				_mockWorkflowTask(), RandomTestUtil.randomLong()));
	}

	@Test
	public void testNotContentReviewerWithAssetViewPermissionHasPermissionOnPendingTaskWithNotification() {
		_mockAssetRendererHasViewPermission(true);
		_mockUserNotificationEventLocalService(1);

		Assert.assertTrue(
			_workflowTaskPermissionChecker.contains(
				_mockPermissionChecker(
					RandomTestUtil.randomLong(), new long[0], false, false,
					false),
				_mockWorkflowTask(), RandomTestUtil.randomLong()));
	}

	@Test
	public void testNotContentReviewerWithoutAssetViewPermissionHasNoPermissionOnCompletedTask() {
		_mockAssetRendererHasViewPermission(false);

		Assert.assertFalse(
			_workflowTaskPermissionChecker.contains(
				_mockPermissionChecker(
					RandomTestUtil.randomLong(), new long[0], false, false,
					false),
				_mockCompletedWorkflowTask(), RandomTestUtil.randomLong()));
	}

	@Test
	public void testNotContentReviewerWithoutAssetViewPermissionHasNoPermissionOnPendingTask() {
		_mockAssetRendererHasViewPermission(false);

		Assert.assertFalse(
			_workflowTaskPermissionChecker.contains(
				_mockPermissionChecker(
					RandomTestUtil.randomLong(), new long[0], false, false,
					false),
				_mockWorkflowTask(), RandomTestUtil.randomLong()));
	}

	@Test
	public void testOmniadminHasPermission() {
		Assert.assertTrue(
			_workflowTaskPermissionChecker.contains(
				_mockOmniadminPermissionChecker(), _mockWorkflowTask(),
				RandomTestUtil.randomLong()));
	}

	private void _mockAssetRendererHasViewPermission(
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

	private PermissionChecker _mockCompanyAdminPermissionChecker() {
		return _mockPermissionChecker(
			RandomTestUtil.randomLong(), new long[0], true, false, false);
	}

	private WorkflowTask _mockCompletedWorkflowTask() {
		return _mockCompletedWorkflowTask(
			Role.class.getName(), RandomTestUtil.randomLong());
	}

	private WorkflowTask _mockCompletedWorkflowTask(
		String assigneeClassName, long assigneeClassPK) {

		return _mockWorkflowTask(assigneeClassName, assigneeClassPK, true);
	}

	private PermissionChecker _mockContentReviewerPermissionChecker(
		long userId) {

		return _mockPermissionChecker(userId, new long[0], false, true, false);
	}

	private PermissionChecker _mockContentReviewerPermissionChecker(
		long userId, long[] roleIds) {

		return _mockPermissionChecker(userId, roleIds, false, true, false);
	}

	private PermissionChecker _mockOmniadminPermissionChecker() {
		return _mockPermissionChecker(
			RandomTestUtil.randomLong(), new long[0], false, false, true);
	}

	private PermissionChecker _mockPermissionChecker(
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

	private void _mockUserNotificationEventLocalService(int count) {
		ReflectionTestUtil.setFieldValue(
			_workflowTaskPermissionChecker,
			"_userNotificationEventLocalService",
			new UserNotificationEventLocalServiceWrapper() {

				@Override
				public int getUserNotificationEventsCount(
					long userId, String type,
					Map<String, String> payloadParameter) {

					return count;
				}

			});
	}

	private WorkflowTask _mockWorkflowTask() {
		return _mockWorkflowTask(
			Role.class.getName(), RandomTestUtil.randomLong());
	}

	private WorkflowTask _mockWorkflowTask(
		String assigneeClassName, long assigneeClassPK) {

		return _mockWorkflowTask(assigneeClassName, assigneeClassPK, false);
	}

	private WorkflowTask _mockWorkflowTask(
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

	private long[] _randomPermissionCheckerRoleIds() {
		return new long[] {RandomTestUtil.randomLong()};
	}

	private void _setUpGroupLocalService() {
		ReflectionTestUtil.setFieldValue(
			_workflowTaskPermissionChecker, "_groupLocalService",
			new GroupLocalServiceWrapper() {

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
				public Object updateStatus(
					int status, Map<String, Serializable> workflowContext) {

					return null;
				}

			},
			null);
	}

	private static final String _TEST_CONTEXT_ENTRY_CLASS_NAME =
		"TEST_CONTEXT_ENTRY_CLASS_NAME";

	private final WorkflowTaskPermission _workflowTaskPermissionChecker =
		new WorkflowTaskPermissionImpl();

}