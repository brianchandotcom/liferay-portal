/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.instances.web.internal.notifications;

import com.liferay.portal.instances.background.task.PortalInstancesOperationType;
import com.liferay.portal.instances.background.task.constants.PortalInstancesBackgroundTaskConstants;
import com.liferay.portal.instances.constants.PortalInstancesPortletKeys;
import com.liferay.portal.json.JSONFactoryImpl;
import com.liferay.portal.kernel.backgroundtask.constants.BackgroundTaskConstants;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.UserNotificationEvent;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.language.LanguageImpl;
import com.liferay.portal.model.impl.UserNotificationEventImpl;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.Locale;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;

import org.springframework.mock.web.MockHttpServletRequest;

/**
 * @author Luis Ortiz
 */
public class PortalInstancesUserNotificationHandlerTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@BeforeClass
	public static void setUpClass() {
		LanguageUtil languageUtil = new LanguageUtil();

		languageUtil.setLanguage(new LanguageImpl());
	}

	@Before
	public void setUp() {
		_serviceContext = new ServiceContext();

		_serviceContext.setLanguageId("en_US");
		_serviceContext.setRequest(new MockHttpServletRequest());

		ReflectionTestUtil.setFieldValue(
			_portalInstancesUserNotificationHandler, "_jsonFactory",
			new JSONFactoryImpl());

		Language language = Mockito.mock(Language.class);

		Mockito.when(
			language.format(
				Mockito.any(Locale.class),
				Mockito.eq("the-virtual-instance-x-was-added-successfully"),
				Mockito.eq(_WEB_ID_MATCHING_LANGUAGE_KEY), Mockito.eq(false))
		).thenReturn(
			_ADDED_MESSAGE
		);

		Mockito.when(
			language.format(
				Mockito.any(Locale.class),
				Mockito.eq("the-virtual-instance-x-could-not-be-added"),
				Mockito.eq(_WEB_ID_MATCHING_LANGUAGE_KEY), Mockito.eq(false))
		).thenReturn(
			_NOT_ADDED_MESSAGE
		);

		Mockito.when(
			language.get(
				Mockito.any(Locale.class),
				Mockito.eq("please-enter-a-valid-web-id"))
		).thenReturn(
			_ERROR_MESSAGE
		);

		ReflectionTestUtil.setFieldValue(
			_portalInstancesUserNotificationHandler, "_language", language);

		Portal portal = Mockito.mock(Portal.class);

		Mockito.when(
			portal.getPortletTitle(
				Mockito.eq(PortalInstancesPortletKeys.PORTAL_INSTANCES),
				Mockito.any(Locale.class))
		).thenReturn(
			_PORTLET_TITLE
		);

		ReflectionTestUtil.setFieldValue(
			_portalInstancesUserNotificationHandler, "_portal", portal);
	}

	@Test
	public void testGetBodyWhenOperationTypeIsAddAndStatusIsFailed()
		throws Exception {

		Assert.assertEquals(
			_getExpectedBody(_NOT_ADDED_MESSAGE + " " + _ERROR_MESSAGE),
			_portalInstancesUserNotificationHandler.getBody(
				_createUserNotificationEvent(
					PortalInstancesOperationType.ADD,
					BackgroundTaskConstants.STATUS_FAILED,
					"please-enter-a-valid-web-id"),
				_serviceContext));
	}

	@Test
	public void testGetBodyWhenOperationTypeIsAddAndStatusIsSuccessful()
		throws Exception {

		Assert.assertEquals(
			_getExpectedBody(_ADDED_MESSAGE),
			_portalInstancesUserNotificationHandler.getBody(
				_createUserNotificationEvent(
					PortalInstancesOperationType.ADD,
					BackgroundTaskConstants.STATUS_SUCCESSFUL, null),
				_serviceContext));
	}

	@Test
	public void testGetBodyWhenOperationTypeIsNotSupported() {
		Assert.assertThrows(
			IllegalArgumentException.class,
			() -> _portalInstancesUserNotificationHandler.getBody(
				_createUserNotificationEvent(
					PortalInstancesOperationType.DELETE,
					BackgroundTaskConstants.STATUS_SUCCESSFUL, null),
				_serviceContext));
	}

	@Test
	public void testGetTitle() throws Exception {
		Assert.assertEquals(
			_PORTLET_TITLE,
			_portalInstancesUserNotificationHandler.getTitle(
				_createUserNotificationEvent(
					PortalInstancesOperationType.ADD,
					BackgroundTaskConstants.STATUS_SUCCESSFUL, null),
				_serviceContext));
	}

	private UserNotificationEvent _createUserNotificationEvent(
		PortalInstancesOperationType portalInstancesOperationType, int status,
		String errorMessageKey) {

		UserNotificationEvent userNotificationEvent =
			new UserNotificationEventImpl();

		userNotificationEvent.setPayload(
			String.valueOf(
				JSONUtil.put(
					PortalInstancesBackgroundTaskConstants.COMPANY_ID, 0
				).put(
					PortalInstancesBackgroundTaskConstants.ERROR_MESSAGE_KEY,
					errorMessageKey
				).put(
					PortalInstancesBackgroundTaskConstants.OPERATION_TYPE,
					portalInstancesOperationType.getValue()
				).put(
					PortalInstancesBackgroundTaskConstants.STATUS,
					BackgroundTaskConstants.getStatusLabel(status)
				).put(
					PortalInstancesBackgroundTaskConstants.WEB_ID,
					_WEB_ID_MATCHING_LANGUAGE_KEY
				)));

		return userNotificationEvent;
	}

	private String _getExpectedBody(String body) {
		return String.format(
			"<div class=\"title\">%s</div><div class=\"body\">%s</div>",
			_PORTLET_TITLE, body);
	}

	private static final String _ADDED_MESSAGE =
		"The virtual instance test was added successfully.";

	private static final String _ERROR_MESSAGE = "Please enter a valid web ID.";

	private static final String _NOT_ADDED_MESSAGE =
		"The virtual instance test could not be added.";

	private static final String _PORTLET_TITLE = "Virtual Instances";

	private static final String _WEB_ID_MATCHING_LANGUAGE_KEY = "test";

	private static final PortalInstancesUserNotificationHandler
		_portalInstancesUserNotificationHandler =
			new PortalInstancesUserNotificationHandler();

	private ServiceContext _serviceContext;

}