/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.notifications.web.internal.portlet;

import com.liferay.bulk.selection.BulkSelection;
import com.liferay.bulk.selection.BulkSelectionFactory;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.UserNotificationDelivery;
import com.liferay.portal.kernel.model.UserNotificationEvent;
import com.liferay.portal.kernel.notifications.UserNotificationDefinition;
import com.liferay.portal.kernel.notifications.UserNotificationFeedEntry;
import com.liferay.portal.kernel.notifications.UserNotificationManagerUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.service.UserNotificationDeliveryLocalService;
import com.liferay.portal.kernel.service.UserNotificationEventLocalService;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.portlet.MockLiferayPortletActionRequest;
import com.liferay.portal.kernel.test.portlet.MockLiferayPortletActionResponse;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.impl.UserImpl;
import com.liferay.portal.model.impl.UserNotificationDeliveryImpl;
import com.liferay.portal.model.impl.UserNotificationEventImpl;
import com.liferay.portal.test.log.LogCapture;
import com.liferay.portal.test.log.LogEntry;
import com.liferay.portal.test.log.LoggerTestUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;
import com.liferay.portal.util.PortalImpl;

import jakarta.portlet.ActionRequest;
import jakarta.portlet.ActionResponse;
import jakarta.portlet.PortletRequest;

import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.ArgumentMatchers;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

/**
 * @author Marco Galluzzi
 * @author Alessio Antonio Rendina
 */
public class NotificationsPortletTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() {
		ReflectionTestUtil.setFieldValue(
			_notificationsPortlet, "_language", Mockito.mock(Language.class));
		ReflectionTestUtil.setFieldValue(
			_notificationsPortlet, "_portal", _portal);
		ReflectionTestUtil.setFieldValue(
			_notificationsPortlet, "_userNotificationDeliveryLocalService",
			_userNotificationDeliveryLocalService);
		ReflectionTestUtil.setFieldValue(
			_notificationsPortlet, "_userNotificationEventBulkSelectionFactory",
			_userNotificationEventBulkSelectionFactory);
		ReflectionTestUtil.setFieldValue(
			_notificationsPortlet, "_userNotificationEventLocalService",
			_userNotificationEventLocalService);

		_user.setUserId(RandomTestUtil.randomLong());

		PortalUtil portalUtil = new PortalUtil();

		portalUtil.setPortal(new PortalImpl());
	}

	@After
	public void tearDown() {
		_serviceContextFactoryMockedStatic.close();
		_userNotificationManagerUtilMockedStatic.close();
	}

	@Test
	public void testMarkNotificationAsReadWithNullLink() throws Exception {
		Mockito.when(
			_userNotificationEventBulkSelectionFactory.create(
				ArgumentMatchers.anyMap())
		).thenReturn(
			Mockito.mock(BulkSelection.class)
		);

		UserNotificationFeedEntry userNotificationFeedEntry = Mockito.mock(
			UserNotificationFeedEntry.class);

		Mockito.when(
			userNotificationFeedEntry.getLink()
		).thenReturn(
			null
		);

		UserNotificationEvent userNotificationEvent =
			_addUserNotificationEvent();

		_userNotificationManagerUtilMockedStatic.when(
			() -> UserNotificationManagerUtil.interpret(
				ArgumentMatchers.anyString(),
				ArgumentMatchers.eq(userNotificationEvent),
				ArgumentMatchers.any())
		).thenReturn(
			userNotificationFeedEntry
		);

		_serviceContextFactoryMockedStatic.when(
			() -> ServiceContextFactory.getInstance(
				ArgumentMatchers.any(PortletRequest.class))
		).thenReturn(
			new ServiceContext()
		);

		String escapedRedirect = "https://localhost/escaped";
		String redirect = "https://localhost/redirect";

		Mockito.when(
			_portal.escapeRedirect(redirect)
		).thenReturn(
			escapedRedirect
		);

		MockLiferayPortletActionRequest mockLiferayPortletActionRequest =
			_getMockLiferayPortletActionRequest();

		mockLiferayPortletActionRequest.setParameter(
			"userNotificationEventId",
			String.valueOf(userNotificationEvent.getUserNotificationEventId()));
		mockLiferayPortletActionRequest.setParameter("redirect", redirect);

		ActionResponse actionResponse = Mockito.mock(ActionResponse.class);

		ReflectionTestUtil.invoke(
			_notificationsPortlet, "_markAsRead",
			new Class<?>[] {
				String.class, ActionRequest.class, ActionResponse.class,
				ThemeDisplay.class
			},
			"markNotificationAsRead", mockLiferayPortletActionRequest,
			actionResponse,
			mockLiferayPortletActionRequest.getAttribute(
				WebKeys.THEME_DISPLAY));

		Mockito.verify(
			actionResponse
		).sendRedirect(
			escapedRedirect
		);

		Mockito.verifyNoMoreInteractions(actionResponse);
	}

	@Test
	public void testMarkNotificationAsReadWithNullUserNotificationEvent()
		throws Exception {

		Mockito.when(
			_userNotificationEventBulkSelectionFactory.create(
				ArgumentMatchers.anyMap())
		).thenReturn(
			Mockito.mock(BulkSelection.class)
		);

		String escapedRedirect = "https://localhost/escaped";
		String redirect = "https://localhost/redirect";

		Mockito.when(
			_portal.escapeRedirect(redirect)
		).thenReturn(
			escapedRedirect
		);

		MockLiferayPortletActionRequest mockLiferayPortletActionRequest =
			_getMockLiferayPortletActionRequest();

		mockLiferayPortletActionRequest.setParameter(
			"userNotificationEventId",
			String.valueOf(RandomTestUtil.randomLong()));
		mockLiferayPortletActionRequest.setParameter("redirect", redirect);

		ActionResponse actionResponse = Mockito.mock(ActionResponse.class);

		ReflectionTestUtil.invoke(
			_notificationsPortlet, "_markAsRead",
			new Class<?>[] {
				String.class, ActionRequest.class, ActionResponse.class,
				ThemeDisplay.class
			},
			"markNotificationAsRead", mockLiferayPortletActionRequest,
			actionResponse,
			mockLiferayPortletActionRequest.getAttribute(
				WebKeys.THEME_DISPLAY));

		Mockito.verify(
			actionResponse
		).sendRedirect(
			escapedRedirect
		);

		Mockito.verifyNoMoreInteractions(actionResponse);
	}

	@Test
	public void testMarkNotificationAsReadWithNullUserNotificationFeedEntry()
		throws Exception {

		Mockito.when(
			_userNotificationEventBulkSelectionFactory.create(
				ArgumentMatchers.anyMap())
		).thenReturn(
			Mockito.mock(BulkSelection.class)
		);

		UserNotificationEvent userNotificationEvent =
			_addUserNotificationEvent();

		_userNotificationManagerUtilMockedStatic.when(
			() -> UserNotificationManagerUtil.interpret(
				ArgumentMatchers.anyString(),
				ArgumentMatchers.eq(userNotificationEvent),
				ArgumentMatchers.any())
		).thenReturn(
			null
		);

		_serviceContextFactoryMockedStatic.when(
			() -> ServiceContextFactory.getInstance(
				ArgumentMatchers.any(PortletRequest.class))
		).thenReturn(
			new ServiceContext()
		);

		String escapedRedirect = "https://localhost/escaped";
		String redirect = "https://localhost/redirect";

		Mockito.when(
			_portal.escapeRedirect(redirect)
		).thenReturn(
			escapedRedirect
		);

		MockLiferayPortletActionRequest mockLiferayPortletActionRequest =
			_getMockLiferayPortletActionRequest();

		mockLiferayPortletActionRequest.setParameter(
			"userNotificationEventId",
			String.valueOf(userNotificationEvent.getUserNotificationEventId()));
		mockLiferayPortletActionRequest.setParameter("redirect", redirect);

		ActionResponse actionResponse = Mockito.mock(ActionResponse.class);

		ReflectionTestUtil.invoke(
			_notificationsPortlet, "_markAsRead",
			new Class<?>[] {
				String.class, ActionRequest.class, ActionResponse.class,
				ThemeDisplay.class
			},
			"markNotificationAsRead", mockLiferayPortletActionRequest,
			actionResponse,
			mockLiferayPortletActionRequest.getAttribute(
				WebKeys.THEME_DISPLAY));

		Mockito.verify(
			actionResponse
		).sendRedirect(
			escapedRedirect
		);

		Mockito.verifyNoMoreInteractions(actionResponse);
	}

	@Test
	public void testUpdateUserNotificationDeliveryWithoutUserNotificationDefinition()
		throws Exception {

		UserNotificationDelivery userNotificationDelivery =
			_addUserNotificationDelivery();

		_setUserNotificationDefinition(null, userNotificationDelivery);

		MockLiferayPortletActionRequest mockLiferayPortletActionRequest =
			_getMockLiferayPortletActionRequest();

		mockLiferayPortletActionRequest.setParameter(
			"userNotificationDeliveryIds",
			String.valueOf(
				userNotificationDelivery.getUserNotificationDeliveryId()));

		try (LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
				NotificationsPortlet.class.getName(), LoggerTestUtil.WARN)) {

			_notificationsPortlet.updateUserNotificationDelivery(
				mockLiferayPortletActionRequest,
				new MockLiferayPortletActionResponse());

			List<LogEntry> logEntries = logCapture.getLogEntries();

			Assert.assertEquals(logEntries.toString(), 1, logEntries.size());

			LogEntry logEntry = logEntries.get(0);

			Assert.assertEquals(
				_getExpectedWarningMessage(userNotificationDelivery),
				logEntry.getMessage());
		}
	}

	private UserNotificationDelivery _addUserNotificationDelivery() {
		UserNotificationDelivery userNotificationDelivery =
			new UserNotificationDeliveryImpl();

		userNotificationDelivery.setUserNotificationDeliveryId(
			RandomTestUtil.randomLong());
		userNotificationDelivery.setUserId(_user.getUserId());
		userNotificationDelivery.setPortletId(RandomTestUtil.randomString());
		userNotificationDelivery.setClassNameId(RandomTestUtil.randomLong());
		userNotificationDelivery.setNotificationType(
			RandomTestUtil.randomInt());

		Mockito.when(
			_userNotificationDeliveryLocalService.fetchUserNotificationDelivery(
				userNotificationDelivery.getUserNotificationDeliveryId())
		).thenReturn(
			userNotificationDelivery
		);

		return userNotificationDelivery;
	}

	private UserNotificationEvent _addUserNotificationEvent() {
		UserNotificationEvent userNotificationEvent =
			new UserNotificationEventImpl();

		userNotificationEvent.setUserNotificationEventId(
			RandomTestUtil.randomLong());
		userNotificationEvent.setUserId(_user.getUserId());

		Mockito.when(
			_userNotificationEventLocalService.fetchUserNotificationEvent(
				userNotificationEvent.getUserNotificationEventId())
		).thenReturn(
			userNotificationEvent
		);

		return userNotificationEvent;
	}

	private String _getExpectedWarningMessage(
		UserNotificationDelivery userNotificationDelivery) {

		return String.format(
			"No user notification definition found for class name ID %d, " +
				"notification type %d, and portlet %s",
			userNotificationDelivery.getClassNameId(),
			userNotificationDelivery.getNotificationType(),
			userNotificationDelivery.getPortletId());
	}

	private MockLiferayPortletActionRequest
		_getMockLiferayPortletActionRequest() {

		MockLiferayPortletActionRequest mockLiferayPortletActionRequest =
			new MockLiferayPortletActionRequest();

		ThemeDisplay themeDisplay = new ThemeDisplay();

		themeDisplay.setUser(_user);

		mockLiferayPortletActionRequest.setAttribute(
			WebKeys.THEME_DISPLAY, themeDisplay);

		return mockLiferayPortletActionRequest;
	}

	private void _setUserNotificationDefinition(
		UserNotificationDefinition userNotificationDefinition,
		UserNotificationDelivery userNotificationDelivery) {

		_userNotificationManagerUtilMockedStatic.when(
			() -> UserNotificationManagerUtil.fetchUserNotificationDefinition(
				userNotificationDelivery.getPortletId(),
				userNotificationDelivery.getClassNameId(),
				userNotificationDelivery.getNotificationType())
		).thenReturn(
			userNotificationDefinition
		);
	}

	private final NotificationsPortlet _notificationsPortlet =
		new NotificationsPortlet();
	private final Portal _portal = Mockito.mock(Portal.class);
	private final MockedStatic<ServiceContextFactory>
		_serviceContextFactoryMockedStatic = Mockito.mockStatic(
			ServiceContextFactory.class);
	private final User _user = new UserImpl();
	private final UserNotificationDeliveryLocalService
		_userNotificationDeliveryLocalService = Mockito.mock(
			UserNotificationDeliveryLocalService.class);
	private final BulkSelectionFactory<UserNotificationEvent>
		_userNotificationEventBulkSelectionFactory = Mockito.mock(
			BulkSelectionFactory.class);
	private final UserNotificationEventLocalService
		_userNotificationEventLocalService = Mockito.mock(
			UserNotificationEventLocalService.class);
	private final MockedStatic<UserNotificationManagerUtil>
		_userNotificationManagerUtilMockedStatic = Mockito.mockStatic(
			UserNotificationManagerUtil.class);

}