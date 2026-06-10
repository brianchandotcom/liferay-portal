/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.subscription.web.internal.portlet.action;

import com.liferay.commerce.model.CommerceSubscriptionEntry;
import com.liferay.commerce.service.CommerceSubscriptionEntryLocalService;
import com.liferay.commerce.subscription.CommerceSubscriptionEntryActionHelper;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.test.portlet.MockActionRequest;
import com.liferay.portal.kernel.test.portlet.MockActionResponse;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import org.springframework.mock.web.MockHttpServletRequest;

/**
 * @author Stefano Motta
 */
public class EditCommerceSubscriptionContentMVCActionCommandTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);

		PortalUtil portalUtil = new PortalUtil();

		Portal portal = Mockito.mock(Portal.class);

		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest();

		Mockito.when(
			portal.getHttpServletRequest(Mockito.any())
		).thenReturn(
			mockHttpServletRequest
		);

		Mockito.when(
			portal.getLiferayPortletRequest(Mockito.any())
		).thenReturn(
			Mockito.mock(LiferayPortletRequest.class)
		);

		Mockito.when(
			portal.getOriginalServletRequest(Mockito.any())
		).thenReturn(
			mockHttpServletRequest
		);

		portalUtil.setPortal(portal);
	}

	@Test
	public void testDoProcessAction() throws Exception {
		CommerceSubscriptionEntry commerceSubscriptionEntry = Mockito.mock(
			CommerceSubscriptionEntry.class);

		Mockito.when(
			commerceSubscriptionEntry.getCompanyId()
		).thenReturn(
			RandomTestUtil.randomLong()
		);

		Mockito.when(
			_commerceSubscriptionEntryLocalService.getCommerceSubscriptionEntry(
				Mockito.anyLong())
		).thenReturn(
			commerceSubscriptionEntry
		);

		MockActionRequest mockActionRequest = new MockActionRequest();

		ThemeDisplay themeDisplay = Mockito.mock(ThemeDisplay.class);

		Mockito.when(
			themeDisplay.getCompanyId()
		).thenReturn(
			RandomTestUtil.randomLong()
		);

		mockActionRequest.setAttribute(WebKeys.THEME_DISPLAY, themeDisplay);

		mockActionRequest.setParameter(Constants.CMD, "cancel");
		mockActionRequest.setParameter(
			"commerceSubscriptionEntryId",
			String.valueOf(RandomTestUtil.randomLong()));

		MockActionResponse mockActionResponse = new MockActionResponse();

		_editCommerceSubscriptionContentMVCActionCommand.doProcessAction(
			mockActionRequest, mockActionResponse);

		Assert.assertTrue(
			SessionErrors.contains(
				mockActionRequest, PrincipalException.class.getName()));
		Assert.assertEquals(
			"/error.jsp", mockActionResponse.getRenderParameter("mvcPath"));
	}

	@Mock
	private CommerceSubscriptionEntryActionHelper
		_commerceSubscriptionEntryActionHelper;

	@Mock
	private CommerceSubscriptionEntryLocalService
		_commerceSubscriptionEntryLocalService;

	@InjectMocks
	private final EditCommerceSubscriptionContentMVCActionCommand
		_editCommerceSubscriptionContentMVCActionCommand =
			new EditCommerceSubscriptionContentMVCActionCommand();

}