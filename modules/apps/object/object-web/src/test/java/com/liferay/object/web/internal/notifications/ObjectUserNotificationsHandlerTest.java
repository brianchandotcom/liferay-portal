/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.web.internal.notifications;

import com.liferay.asset.display.page.portlet.AssetDisplayPageFriendlyURLProvider;
import com.liferay.info.item.InfoItemReference;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.UserNotificationEvent;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactory;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactoryUtil;
import com.liferay.portal.kernel.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

import org.mockito.MockedStatic;
import org.mockito.Mockito;

/**
 * @author Thalles Montenegro
 */
public class ObjectUserNotificationsHandlerTest {

	@ClassRule
	public static LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() throws PortalException {
		_setUpServiceContext();
	}

	@Test
	public void testGetLinkWhenNotificationHasFriendlyURL() throws Exception {
		Mockito.when(
			_userNotificationEvent.getPayload()
		).thenReturn(
			StringPool.BLANK
		);

		Mockito.when(
			_serviceContext.getThemeDisplay()
		).thenReturn(
			Mockito.mock(ThemeDisplay.class)
		);

		Mockito.when(
			_assetDisplayPageFriendlyURLProvider.getFriendlyURL(
				Mockito.any(InfoItemReference.class),
				Mockito.any(ThemeDisplay.class))
		).thenReturn(
			"http://localhost:8080/web/l/54321"
		);

		Assert.assertEquals(
			"http://localhost:8080/web/l/54321",
			_objectUserNotificationsHandler.getLink(
				_userNotificationEvent, _serviceContext));
	}

	@Test
	public void testGetLinkWhenGuestUserObjectEntrySubmissionsExceedsLimits()
		throws Exception {

		Mockito.when(
			_userNotificationEvent.getPayload()
		).thenReturn(
			"{ \"exceedsObjectEntryLimit\": true }"
		);

		try (MockedStatic<RequestBackedPortletURLFactoryUtil>
				 requestBackedPortletURLFactoryUtilMockedStatic = Mockito.mockStatic(
					RequestBackedPortletURLFactoryUtil.class);
			 MockedStatic<PortletURLBuilder> portletURLBuilderMockedStatic =
				 Mockito.mockStatic(PortletURLBuilder.class)) {

			 requestBackedPortletURLFactoryUtilMockedStatic.when(
				() -> RequestBackedPortletURLFactoryUtil.create(
					Mockito.any(HttpServletRequest.class))
			).thenReturn(
				_requestBackedPortletURLFactory
			);

			portletURLBuilderMockedStatic.when(
				() -> PortletURLBuilder.create(Mockito.any())
			).thenReturn(
				_portletURLStep
			);

			_objectUserNotificationsHandler.getLink(
				_userNotificationEvent, _serviceContext);

			portletURLBuilderMockedStatic.verify(
				() -> PortletURLBuilder.create(Mockito.any()));
			Assert.assertTrue(true);
		}
	}

	private void _setUpServiceContext() throws PortalException {
		Mockito.when(
			_serviceContext.getThemeDisplay()
		).thenReturn(
			null
		);

		Mockito.when(
			_serviceContext.getRequest()
		).thenReturn(
			Mockito.mock(HttpServletRequest.class)
		);

		Mockito.when(
			_serviceContext.getCurrentURL()
		).thenReturn(
			RandomTestUtil.randomString()
		);

		Mockito.when(
			_serviceContext.getScopeGroup()
		).thenReturn(
			Mockito.mock(Group.class)
		);
	}

	@Test
	public void testGetLinkWhenObjectEntryHasNotDisplayPageFriendlyURL()
		throws Exception {

		Mockito.when(
			_userNotificationEvent.getPayload()
		).thenReturn(
			StringPool.BLANK
		);

		try (MockedStatic<PortletURLBuilder> portletURLBuilderMockedStatic =
				Mockito.mockStatic(PortletURLBuilder.class);
			 MockedStatic<PortalUtil> portalUtilMockedStatic =
				 Mockito.mockStatic(PortalUtil.class)) {

			portletURLBuilderMockedStatic.when(
				() -> PortletURLBuilder.create(Mockito.any())
			).thenReturn(
				_portletURLStep
			);

			portalUtilMockedStatic.when(
				() -> PortalUtil.getControlPanelPortletURL(
					Mockito.any(HttpServletRequest.class),
					Mockito.any(Group.class), Mockito.anyString(),
					Mockito.anyLong(), Mockito.anyLong(),
					Mockito.anyString())
			).thenReturn(
				Mockito.mock(PortletURL.class)
			);

			_objectUserNotificationsHandler.getLink(
				_userNotificationEvent, _serviceContext);

			portletURLBuilderMockedStatic.verify(
				() -> PortletURLBuilder.create(Mockito.any()));
			Assert.assertTrue(true);
		}
	}

	private final AssetDisplayPageFriendlyURLProvider
		_assetDisplayPageFriendlyURLProvider = Mockito.mock(
			AssetDisplayPageFriendlyURLProvider.class);
	private final ObjectUserNotificationsHandler _objectUserNotificationsHandler =
		new ObjectUserNotificationsHandler(
			_assetDisplayPageFriendlyURLProvider,
			Mockito.mock(ObjectDefinition.class));

	private final PortletURLBuilder.PortletURLStep _portletURLStep = new PortletURLBuilder.PortletURLStep(
		Mockito.mock(PortletURL.class));
	private final RequestBackedPortletURLFactory
		_requestBackedPortletURLFactory = Mockito.mock(
			RequestBackedPortletURLFactory.class);
	private final ServiceContext _serviceContext = Mockito.mock(
		ServiceContext.class);
	private final UserNotificationEvent _userNotificationEvent = Mockito.mock(
		UserNotificationEvent.class);

}