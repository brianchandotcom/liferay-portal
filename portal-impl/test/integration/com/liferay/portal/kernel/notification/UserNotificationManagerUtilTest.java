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

package com.liferay.portal.kernel.notification;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.UserNotificationEvent;
import com.liferay.portal.kernel.notification.bundle.usernotificationmanagerutil.TestUserNotificationHandler;
import com.liferay.portal.kernel.notifications.UserNotificationFeedEntry;
import com.liferay.portal.kernel.notifications.UserNotificationHandler;
import com.liferay.portal.kernel.notifications.UserNotificationManagerUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.model.impl.UserNotificationEventImpl;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.SyntheticBundleClassTestRule;

import java.util.Map;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Peter Fellwock
 */
public class UserNotificationManagerUtilTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			new SyntheticBundleClassTestRule(
				"bundle.usernotificationmanagerutil"));

	@Test
	public void testGetUserNotificationHandlers() {
		Map<String, Map<String, UserNotificationHandler>>
			userNotificationHandlersMap =
				UserNotificationManagerUtil.getUserNotificationHandlers();

		Assert.assertNotNull(userNotificationHandlersMap);

		Map<String, UserNotificationHandler> userNotificationHandlers =
			userNotificationHandlersMap.get(
				TestUserNotificationHandler.SELECTOR);

		Assert.assertNotNull(userNotificationHandlers);

		UserNotificationHandler userNotificationHandler =
			userNotificationHandlers.get(
				TestUserNotificationHandler.PORTLET_ID);

		Assert.assertNotNull(userNotificationHandler);

		Class<? extends UserNotificationHandler> clazz =
			userNotificationHandler.getClass();

		Assert.assertEquals(
			TestUserNotificationHandler.class.getName(), clazz.getName());
	}

	@Test
	public void testInterpret() throws PortalException {
		UserNotificationEvent userNotificationEvent =
			new UserNotificationEventImpl();

		userNotificationEvent.setType(TestUserNotificationHandler.PORTLET_ID);

		UserNotificationFeedEntry userNotificationFeedEntry =
			UserNotificationManagerUtil.interpret(
				TestUserNotificationHandler.SELECTOR, userNotificationEvent,
				null);

		Assert.assertEquals(
			TestUserNotificationHandler.LINK,
			userNotificationFeedEntry.getLink());
	}

	@Test
	public void testIsDeliver() {
		try {
			boolean deliver = UserNotificationManagerUtil.isDeliver(
				1, TestUserNotificationHandler.SELECTOR,
				TestUserNotificationHandler.PORTLET_ID, 1, 1, 1, null);

			Assert.assertTrue(deliver);
		}
		catch (Exception e) {
			throw new Error(e);
		}
	}

}