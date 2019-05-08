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

package com.liferay.portal.notification.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.UserNotificationEvent;
import com.liferay.portal.kernel.model.UserNotificationEventWrapper;
import com.liferay.portal.kernel.notifications.UserNotificationFeedEntry;
import com.liferay.portal.kernel.notifications.UserNotificationHandler;
import com.liferay.portal.kernel.notifications.UserNotificationManagerUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.Map;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceRegistration;

/**
 * @author Leon Chi
 */
@RunWith(Arquillian.class)
public class UserNotificationManagerUtilTest {

	@ClassRule
	@Rule
	public static final LiferayIntegrationTestRule liferayIntegrationTestRule =
		new LiferayIntegrationTestRule();

	@BeforeClass
	public static void setUpClass() {
		Bundle bundle = FrameworkUtil.getBundle(
			UserNotificationManagerUtilTest.class);

		BundleContext bundleContext = bundle.getBundleContext();

		_userNotificationHandler = new UserNotificationHandler() {

			@Override
			public String getPortletId() {
				return _PORTLET_ID;
			}

			@Override
			public String getSelector() {
				return _SELECTOR;
			}

			@Override
			public UserNotificationFeedEntry interpret(
				UserNotificationEvent userNotificationEvent,
				ServiceContext serviceContext) {

				return _userNotificationFeedEntry;
			}

			@Override
			public boolean isDeliver(
				long userId, long classNameId, int notificationType,
				int deliveryType, ServiceContext serviceContext) {

				if (userId == 1) {
					return true;
				}

				return false;
			}

			@Override
			public boolean isOpenDialog() {
				return false;
			}

		};

		_serviceRegistration = bundleContext.registerService(
			UserNotificationHandler.class, _userNotificationHandler,
			MapUtil.singletonDictionary("service.ranking", Integer.MAX_VALUE));
	}

	@AfterClass
	public static void tearDownClass() {
		_serviceRegistration.unregister();
	}

	@Test
	public void testGetUserNotificationHandlers() {
		Map<String, Map<String, UserNotificationHandler>>
			userNotificationHandlersMap =
				UserNotificationManagerUtil.getUserNotificationHandlers();

		Assert.assertNotNull(userNotificationHandlersMap);

		Map<String, UserNotificationHandler> userNotificationHandlers =
			userNotificationHandlersMap.get(_SELECTOR);

		Assert.assertNotNull(userNotificationHandlers);

		UserNotificationHandler userNotificationHandler =
			userNotificationHandlers.get(_PORTLET_ID);

		Assert.assertSame(_userNotificationHandler, userNotificationHandler);
	}

	@Test
	public void testInterpret() throws PortalException {
		UserNotificationEvent userNotificationEvent =
			new UserNotificationEventWrapper(null) {

				@Override
				public String getType() {
					return _PORTLET_ID;
				}

			};

		Assert.assertSame(
			_userNotificationFeedEntry,
			UserNotificationManagerUtil.interpret(
				_SELECTOR, userNotificationEvent, null));
	}

	@Test
	public void testIsDeliver() throws PortalException {
		Assert.assertTrue(
			UserNotificationManagerUtil.isDeliver(
				1, _SELECTOR, _PORTLET_ID, 1, 1, 1, null));

		Assert.assertFalse(
			UserNotificationManagerUtil.isDeliver(
				0, _SELECTOR, _PORTLET_ID, 1, 1, 1, null));
	}

	private static final String _PORTLET_ID = "PORTLET_ID";

	private static final String _SELECTOR = "SELECTOR";

	private static ServiceRegistration<UserNotificationHandler>
		_serviceRegistration;
	private static final UserNotificationFeedEntry _userNotificationFeedEntry =
		new UserNotificationFeedEntry(
			false, StringPool.BLANK, StringPool.BLANK, false);
	private static UserNotificationHandler _userNotificationHandler;

}