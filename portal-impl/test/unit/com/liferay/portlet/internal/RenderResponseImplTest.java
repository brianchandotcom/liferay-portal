/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portlet.internal;

import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.theme.PortletDisplay;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.test.rule.LiferayUnitTestRule;
import com.liferay.portal.util.PortalImpl;
import com.liferay.portlet.configuration.kernel.util.PortletConfigurationUtil;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.MockedStatic;
import org.mockito.Mockito;

/**
 * @author Jorge Avalos
 */
public class RenderResponseImplTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@AfterClass
	public static void tearDownClass() {
		_portletConfigurationUtilMockedStatic.close();
	}

	@Before
	public void setUp() throws Exception {
		PortalUtil portalUtil = new PortalUtil();

		portalUtil.setPortal(new PortalImpl());

		LanguageUtil languageUtil = new LanguageUtil();

		Language language = Mockito.mock(Language.class);

		languageUtil.setLanguage(language);

		Mockito.when(
			language.isAvailableLocale(LocaleUtil.US)
		).thenReturn(
			true
		);

		_portletConfigurationUtilMockedStatic.reset();
	}

	@Test
	public void testSetTitle() throws Exception {
		PortletDisplay portletDisplay = Mockito.mock(PortletDisplay.class);

		Mockito.when(
			portletDisplay.getId()
		).thenReturn(
			_PORTLET_ID
		);

		ThemeDisplay themeDisplay = Mockito.mock(ThemeDisplay.class);

		Mockito.when(
			themeDisplay.getLanguageId()
		).thenReturn(
			"en_US"
		);

		PortletRequestImpl portletRequestImpl = Mockito.mock(
			PortletRequestImpl.class);

		Mockito.when(
			portletRequestImpl.getAttribute(WebKeys.THEME_DISPLAY)
		).thenReturn(
			themeDisplay
		);

		Mockito.when(
			themeDisplay.getPortletDisplay()
		).thenReturn(
			portletDisplay
		);

		RenderResponseImpl renderResponseImpl = new RenderResponseImpl();

		ReflectionTestUtil.setFieldValue(
			renderResponseImpl, "portletRequestImpl", portletRequestImpl);

		_portletConfigurationUtilMockedStatic.when(
			() -> PortletConfigurationUtil.getPortletTitle(
				portletDisplay.getId(), portletDisplay.getPortletPreferences(),
				themeDisplay.getLanguageId())
		).thenReturn(
			_PORTLET_TITLE
		);

		renderResponseImpl.setTitle("");

		Assert.assertEquals(_PORTLET_TITLE, renderResponseImpl.getTitle());
	}

	private static final String _PORTLET_ID = RandomTestUtil.randomString();

	private static final String _PORTLET_TITLE = RandomTestUtil.randomString();

	private static final MockedStatic<PortletConfigurationUtil>
		_portletConfigurationUtilMockedStatic = Mockito.mockStatic(
			PortletConfigurationUtil.class);

}