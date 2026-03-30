/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.exportimport.web.internal.portlet.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.portlet.bridges.mvc.constants.MVCRenderConstants;
import com.liferay.portal.kernel.test.portlet.MockLiferayPortletRenderRequest;
import com.liferay.portal.kernel.test.portlet.MockLiferayPortletRenderResponse;
import com.liferay.portal.kernel.theme.PortletDisplay;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portlet.test.MockLiferayPortletContext;

import jakarta.portlet.Portlet;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Mikel Lorza
 */
@RunWith(Arquillian.class)
public class ImportPortletTest {

	@ClassRule
	@Rule
	public static final LiferayIntegrationTestRule liferayIntegrationTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testRender() throws Exception {
		MockLiferayPortletRenderRequest mockLiferayPortletRenderRequest =
			_getMockLiferayPortletRenderRequest("http://localhost/test");

		_portlet.render(
			mockLiferayPortletRenderRequest,
			new MockLiferayPortletRenderResponse());

		ThemeDisplay themeDisplay =
			(ThemeDisplay)mockLiferayPortletRenderRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();

		Assert.assertTrue(portletDisplay.isShowBackIcon());
		Assert.assertEquals(
			"http://localhost/test", portletDisplay.getURLBack());

		mockLiferayPortletRenderRequest = _getMockLiferayPortletRenderRequest(
			null);

		_portlet.render(
			mockLiferayPortletRenderRequest,
			new MockLiferayPortletRenderResponse());

		themeDisplay =
			(ThemeDisplay)mockLiferayPortletRenderRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		portletDisplay = themeDisplay.getPortletDisplay();

		Assert.assertFalse(portletDisplay.isShowBackIcon());
		Assert.assertEquals(StringPool.BLANK, portletDisplay.getURLBack());
	}

	private MockLiferayPortletRenderRequest _getMockLiferayPortletRenderRequest(
		String backURL) {

		MockLiferayPortletRenderRequest mockLiferayPortletRenderRequest =
			new MockLiferayPortletRenderRequest();

		String path = "/import/view_import_layouts.jsp";

		mockLiferayPortletRenderRequest.setAttribute(
			MVCRenderConstants.
				PORTLET_CONTEXT_OVERRIDE_REQUEST_ATTIBUTE_NAME_PREFIX + path,
			new MockLiferayPortletContext(path));

		mockLiferayPortletRenderRequest.setParameter("backURL", backURL);

		ThemeDisplay themeDisplay = new ThemeDisplay();

		mockLiferayPortletRenderRequest.setAttribute(
			WebKeys.THEME_DISPLAY, themeDisplay);

		PortletDisplay portletDisplay = new PortletDisplay();

		portletDisplay.setThemeDisplay(themeDisplay);

		return mockLiferayPortletRenderRequest;
	}

	@Inject(
		filter = "component.name=com.liferay.exportimport.web.internal.portlet.ImportPortlet"
	)
	private Portlet _portlet;

}