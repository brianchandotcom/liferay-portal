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

package com.liferay.portal.workflow.task.web.internal.notifications;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.UserNotificationEvent;
import com.liferay.portal.kernel.model.UserNotificationEventWrapper;
import com.liferay.portal.kernel.module.util.SystemBundleUtil;
import com.liferay.portal.kernel.notifications.UserNotificationFeedEntry;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserNotificationEventLocalService;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ProxyFactory;
import com.liferay.portal.kernel.workflow.BaseWorkflowHandler;
import com.liferay.portal.kernel.workflow.DefaultWorkflowTask;
import com.liferay.portal.kernel.workflow.WorkflowHandler;
import com.liferay.portal.kernel.workflow.WorkflowTask;
import com.liferay.portal.kernel.workflow.WorkflowTaskManager;
import com.liferay.portal.kernel.workflow.WorkflowTaskManagerUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;
import com.liferay.portal.workflow.task.web.internal.permission.WorkflowTaskPermissionChecker;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;

import org.osgi.framework.BundleContext;

/**
 * @author Inácio Nery
 */
public class WorkflowTaskUserNotificationHandlerTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@BeforeClass
	public static void setUpClass() throws Exception {
		_setUpUserNotificationEventLocalService();
		_setUpWorkflowTaskManagerUtil();
		_setUpWorkflowTaskPermissionChecker();
	}

	@Before
	public void setUp() {
		_allowedUsers.clear();
		_setUpWorkflowHandlerRegistryUtil();
	}

	@Test
	public void testInterpret() throws Exception {
		Language language = Mockito.mock(Language.class);

		ReflectionTestUtil.setFieldValue(
			_workflowTaskUserNotificationHandler, "_language", language);

		Mockito.when(
			language.format(
				Mockito.any(Locale.class),
				Mockito.eq("notification-for-x-was-deactivated"),
				Mockito.anyString(), Mockito.eq(false))
		).thenReturn(
			"Notification for Sample Object was deactivated."
		);

		Mockito.when(
			language.get(
				Mockito.any(Locale.class),
				Mockito.eq("notification-no-longer-applies"))
		).thenReturn(
			"Notification no longer applies."
		);

		UserNotificationFeedEntry userNotificationFeedEntry =
			_workflowTaskUserNotificationHandler.interpret(
				mockUserNotificationEvent(null, "Sample Object", 0),
				_serviceContext);

		Assert.assertEquals(
			StringBundler.concat(
				"<div class=\"title\">Notification no longer applies.</div>",
				"<div class=\"body\">Notification for Sample Object was ",
				"deactivated.</div>"),
			userNotificationFeedEntry.getBody());
	}

	@Test
	public void testInvalidWorkflowTaskIdShouldReturnBlankBody()
		throws Exception {

		Assert.assertEquals(
			StringPool.BLANK,
			_workflowTaskUserNotificationHandler.getBody(
				mockUserNotificationEvent(
					null, null, _INVALID_WORKFLOW_TASK_ID),
				_serviceContext));
	}

	@Test
	public void testInvalidWorkflowTaskIdShouldReturnBlankLink()
		throws Exception {

		Assert.assertEquals(
			StringPool.BLANK,
			_workflowTaskUserNotificationHandler.getLink(
				mockUserNotificationEvent(
					_VALID_ENTRY_CLASS_NAME, null, _INVALID_WORKFLOW_TASK_ID),
				_serviceContext));
	}

	@Test
	public void testIsApplicable() {
		User user1 = Mockito.mock(User.class);

		Mockito.when(
			user1.getUserId()
		).thenReturn(
			_USER_ID
		);

		_allowedUsers.add(user1);

		User user2 = Mockito.mock(User.class);

		Mockito.when(
			user2.getUserId()
		).thenReturn(
			RandomTestUtil.randomLong()
		);

		_allowedUsers.add(user2);

		Assert.assertTrue(
			_workflowTaskUserNotificationHandler.isApplicable(
				mockUserNotificationEvent(
					_VALID_ENTRY_CLASS_NAME, null, _VALID_WORKFLOW_TASK_ID),
				_serviceContext));

		_allowedUsers.remove(user1);

		Assert.assertFalse(
			_workflowTaskUserNotificationHandler.isApplicable(
				mockUserNotificationEvent(
					_VALID_ENTRY_CLASS_NAME, null, _VALID_WORKFLOW_TASK_ID),
				_serviceContext));
	}

	@Test
	public void testNullWorkflowTaskIdShouldReturnBlankLink() throws Exception {
		Assert.assertEquals(
			StringPool.BLANK,
			_workflowTaskUserNotificationHandler.getLink(
				mockUserNotificationEvent(_VALID_ENTRY_CLASS_NAME, null, 0),
				_serviceContext));
	}

	@Test
	public void testNullWorkflowTaskIdShouldReturnBody() throws Exception {
		Assert.assertEquals(
			_NOTIFICATION_MESSAGE,
			_workflowTaskUserNotificationHandler.getBody(
				mockUserNotificationEvent(null, null, 0), _serviceContext));
	}

	@Test
	public void testValidWorkflowTaskIdShouldReturnBody() throws Exception {
		Assert.assertEquals(
			_NOTIFICATION_MESSAGE,
			_workflowTaskUserNotificationHandler.getBody(
				mockUserNotificationEvent(null, null, _VALID_WORKFLOW_TASK_ID),
				_serviceContext));
	}

	@Test
	public void testValidWorkflowTaskIdShouldReturnLink() throws Exception {
		Assert.assertEquals(
			_VALID_LINK,
			_workflowTaskUserNotificationHandler.getLink(
				mockUserNotificationEvent(
					_VALID_ENTRY_CLASS_NAME, null, _VALID_WORKFLOW_TASK_ID),
				_serviceContext));
	}

	protected UserNotificationEvent mockUserNotificationEvent(
		String entryClassName, String entryType, long workflowTaskId) {

		JSONObject jsonObject = JSONUtil.put(
			"entryClassName", entryClassName
		).put(
			"entryType", entryType
		).put(
			"notificationMessage", _NOTIFICATION_MESSAGE
		).put(
			"workflowTaskId", workflowTaskId
		);

		return new UserNotificationEventWrapper(null) {

			@Override
			public String getPayload() {
				return jsonObject.toString();
			}

			@Override
			public long getUserNotificationEventId() {
				return 0;
			}

		};
	}

	private static void _setUpUserNotificationEventLocalService()
		throws Exception {

		ReflectionTestUtil.setFieldValue(
			_workflowTaskUserNotificationHandler,
			"_userNotificationEventLocalService",
			ProxyFactory.newDummyInstance(
				UserNotificationEventLocalService.class));
	}

	private static void _setUpWorkflowTaskManagerUtil() throws Exception {
		WorkflowTaskManager workflowTaskManager = Mockito.spy(
			WorkflowTaskManager.class);

		WorkflowTask workflowTask = new DefaultWorkflowTask() {

			@Override
			public Map<String, Serializable> getOptionalAttributes() {
				return Collections.emptyMap();
			}

		};

		Mockito.doReturn(
			workflowTask
		).when(
			workflowTaskManager
		).fetchWorkflowTask(
			_VALID_WORKFLOW_TASK_ID
		);

		Mockito.doReturn(
			_allowedUsers
		).when(
			workflowTaskManager
		).getNotifiableUsers(
			Mockito.anyLong()
		);

		ReflectionTestUtil.setFieldValue(
			WorkflowTaskManagerUtil.class, "_workflowTaskManager",
			workflowTaskManager);
	}

	private static void _setUpWorkflowTaskPermissionChecker() throws Exception {
		ReflectionTestUtil.setFieldValue(
			_workflowTaskUserNotificationHandler,
			"_workflowTaskPermissionChecker",
			new WorkflowTaskPermissionChecker() {

				@Override
				public boolean hasPermission(
					long groupId, WorkflowTask workflowTask,
					PermissionChecker permissionChecker) {

					return true;
				}

			});
	}

	private void _setUpWorkflowHandlerRegistryUtil() {
		BundleContext bundleContext = SystemBundleUtil.getBundleContext();

		bundleContext.registerService(
			WorkflowHandler.class,
			new BaseWorkflowHandler<Object>() {

				@Override
				public String getClassName() {
					return _VALID_ENTRY_CLASS_NAME;
				}

				@Override
				public String getNotificationLink(
					long workflowTaskId, ServiceContext serviceContext) {

					if (_serviceContext == serviceContext) {
						return _VALID_LINK;
					}

					return null;
				}

				@Override
				public String getType(Locale locale) {
					return null;
				}

				@Override
				public Object updateStatus(int status, Map workflowContext) {
					return null;
				}

			},
			null);
	}

	private static final Long _INVALID_WORKFLOW_TASK_ID =
		RandomTestUtil.randomLong();

	private static final String _NOTIFICATION_MESSAGE =
		RandomTestUtil.randomString();

	private static final Long _USER_ID = RandomTestUtil.randomLong();

	private static final String _VALID_ENTRY_CLASS_NAME =
		RandomTestUtil.randomString();

	private static final String _VALID_LINK = RandomTestUtil.randomString();

	private static final Long _VALID_WORKFLOW_TASK_ID =
		RandomTestUtil.randomLong();

	private static final List<User> _allowedUsers = new ArrayList<>();

	private static final ServiceContext _serviceContext = new ServiceContext() {

		@Override
		public ThemeDisplay getThemeDisplay() {
			return new ThemeDisplay() {
				{
					setSiteGroupId(RandomTestUtil.randomLong());
				}
			};
		}

		@Override
		public long getUserId() {
			return _USER_ID;
		}

	};

	private static final WorkflowTaskUserNotificationHandler
		_workflowTaskUserNotificationHandler =
			new WorkflowTaskUserNotificationHandler();

}