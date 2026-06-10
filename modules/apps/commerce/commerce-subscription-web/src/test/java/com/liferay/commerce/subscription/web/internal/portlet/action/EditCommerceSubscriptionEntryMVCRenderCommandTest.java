/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.subscription.web.internal.portlet.action;

import com.liferay.commerce.model.CommerceSubscriptionEntry;
import com.liferay.commerce.subscription.web.internal.display.context.CommerceSubscriptionEntryDisplayContext;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.test.portlet.MockRenderRequest;
import com.liferay.portal.kernel.test.portlet.MockRenderResponse;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;

import org.springframework.mock.web.MockHttpServletRequest;

/**
 * @author Stefano Motta
 */
public class EditCommerceSubscriptionEntryMVCRenderCommandTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() {
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
	public void testRender() throws Exception {
		MockRenderRequest mockRenderRequest = new MockRenderRequest();

		CommerceSubscriptionEntryDisplayContext
			commerceSubscriptionEntryDisplayContext = Mockito.mock(
				CommerceSubscriptionEntryDisplayContext.class);

		Mockito.when(
			commerceSubscriptionEntryDisplayContext.
				getCommerceSubscriptionEntry()
		).thenReturn(
			Mockito.mock(CommerceSubscriptionEntry.class)
		).thenThrow(
			new PrincipalException()
		);

		mockRenderRequest.setAttribute(
			WebKeys.PORTLET_DISPLAY_CONTEXT,
			commerceSubscriptionEntryDisplayContext);

		Assert.assertEquals(
			"/edit_commerce_subscription_entry.jsp",
			_editCommerceSubscriptionEntryMVCRenderCommand.render(
				mockRenderRequest, new MockRenderResponse()));
		Assert.assertEquals(
			"/error.jsp",
			_editCommerceSubscriptionEntryMVCRenderCommand.render(
				mockRenderRequest, new MockRenderResponse()));
		Assert.assertTrue(
			SessionErrors.contains(
				mockRenderRequest, PrincipalException.class.getName()));
	}

	private final EditCommerceSubscriptionEntryMVCRenderCommand
		_editCommerceSubscriptionEntryMVCRenderCommand =
			new EditCommerceSubscriptionEntryMVCRenderCommand();

}