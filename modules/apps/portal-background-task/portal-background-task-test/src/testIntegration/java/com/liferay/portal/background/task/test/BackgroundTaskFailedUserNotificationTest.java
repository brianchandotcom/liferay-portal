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

package com.liferay.portal.background.task.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.backgroundtask.BackgroundTask;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskExecutor;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskExecutorRegistry;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskManager;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskResult;
import com.liferay.portal.kernel.backgroundtask.BaseBackgroundTaskExecutor;
import com.liferay.portal.kernel.backgroundtask.constants.BackgroundTaskConstants;
import com.liferay.portal.kernel.backgroundtask.display.BackgroundTaskDisplay;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.model.CompanyConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.UserNotificationEvent;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserNotificationEventLocalService;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.HashMap;
import java.util.List;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Dante Wang
 */
@RunWith(Arquillian.class)
public class BackgroundTaskFailedUserNotificationTest {

	@ClassRule
	@Rule
	public static final LiferayIntegrationTestRule liferayIntegrationTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testUserNotificationWhenBackgroundTaskFailed()
		throws Exception {

		_user = UserTestUtil.addUser();

		_backgroundTaskExecutorRegistry.registerBackgroundTaskExecutor(
			TestBackgroundTaskExecutor.class.getName(),
			new TestBackgroundTaskExecutor());

		try {
			_backgroundTaskManager.addBackgroundTask(
				_user.getUserId(), CompanyConstants.SYSTEM,
				BackgroundTaskFailedUserNotificationTest.class.getName(),
				TestBackgroundTaskExecutor.class.getName(), new HashMap<>(),
				new ServiceContext());

			List<UserNotificationEvent> userNotificationEvents =
				_userNotificationEventLocalService.getUserNotificationEvents(
					_user.getUserId());

			Assert.assertEquals(
				userNotificationEvents.toString(), 1,
				userNotificationEvents.size());

			UserNotificationEvent userNotificationEvent =
				userNotificationEvents.get(0);

			Assert.assertEquals(
				"BackgroundTask", userNotificationEvent.getType());

			JSONObject jsonObject = _jsonFactory.createJSONObject(
				userNotificationEvent.getPayload());

			Assert.assertEquals(
				BackgroundTaskFailedUserNotificationTest.class.getName(),
				jsonObject.getString("name"));
			Assert.assertEquals(
				TestBackgroundTaskExecutor.class.getName(),
				jsonObject.getString("taskExecutorClassName"));
		}
		finally {
			_backgroundTaskExecutorRegistry.unregisterBackgroundTaskExecutor(
				TestBackgroundTaskExecutor.class.getName());
		}
	}

	@Inject
	private BackgroundTaskExecutorRegistry _backgroundTaskExecutorRegistry;

	@Inject
	private BackgroundTaskManager _backgroundTaskManager;

	@Inject
	private JSONFactory _jsonFactory;

	@DeleteAfterTestRun
	private User _user;

	@Inject
	private UserNotificationEventLocalService
		_userNotificationEventLocalService;

	private static class TestBackgroundTaskExecutor
		extends BaseBackgroundTaskExecutor {

		@Override
		public BackgroundTaskExecutor clone() {
			return this;
		}

		@Override
		public BackgroundTaskResult execute(BackgroundTask backgroundTask) {
			return new BackgroundTaskResult(
				BackgroundTaskConstants.STATUS_FAILED);
		}

		@Override
		public BackgroundTaskDisplay getBackgroundTaskDisplay(
			BackgroundTask backgroundTask) {

			return null;
		}

	}

}