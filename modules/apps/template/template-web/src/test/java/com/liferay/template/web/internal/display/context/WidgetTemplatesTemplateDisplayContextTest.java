/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.template.web.internal.display.context;

import com.liferay.dynamic.data.mapping.configuration.DDMWebConfiguration;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.test.TestInfo;
import com.liferay.portal.kernel.test.portlet.MockLiferayPortletRenderRequest;
import com.liferay.portal.kernel.test.portlet.MockLiferayPortletRenderResponse;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Eudaldo Alonso
 */
public class WidgetTemplatesTemplateDisplayContextTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() {
		_setUpThemeDisplay();
	}

	@Test
	@TestInfo("LPS-116076")
	public void testIsAddButtonEnabledWithEnableTemplateCreationDisabled() {
		Mockito.when(
			_ddmWebConfiguration.enableTemplateCreation()
		).thenReturn(
			false
		);

		WidgetTemplatesTemplateDisplayContext
			widgetTemplatesTemplateDisplayContext =
				new WidgetTemplatesTemplateDisplayContext(
					_getMockLiferayPortletRenderRequest(),
					new MockLiferayPortletRenderResponse());

		Assert.assertFalse(
			widgetTemplatesTemplateDisplayContext.isAddButtonEnabled());
	}

	@Test
	@TestInfo("LPS-116076")
	public void testIsAddButtonEnabledWithEnableTemplateCreationEnabled() {
		Mockito.when(
			_ddmWebConfiguration.enableTemplateCreation()
		).thenReturn(
			true
		);

		WidgetTemplatesTemplateDisplayContext
			widgetTemplatesTemplateDisplayContext =
				new WidgetTemplatesTemplateDisplayContext(
					_getMockLiferayPortletRenderRequest(),
					new MockLiferayPortletRenderResponse());

		Assert.assertTrue(
			widgetTemplatesTemplateDisplayContext.isAddButtonEnabled());
	}

	private MockLiferayPortletRenderRequest
		_getMockLiferayPortletRenderRequest() {

		MockLiferayPortletRenderRequest mockLiferayPortletRenderRequest =
			new MockLiferayPortletRenderRequest();

		mockLiferayPortletRenderRequest.setAttribute(
			DDMWebConfiguration.class.getName(), _ddmWebConfiguration);
		mockLiferayPortletRenderRequest.setAttribute(
			WebKeys.THEME_DISPLAY, _themeDisplay);

		return mockLiferayPortletRenderRequest;
	}

	private void _setUpThemeDisplay() {
		Mockito.when(
			_themeDisplay.getScopeGroup()
		).thenReturn(
			_group
		);
	}

	private final DDMWebConfiguration _ddmWebConfiguration = Mockito.mock(
		DDMWebConfiguration.class);
	private final Group _group = Mockito.mock(Group.class);
	private final ThemeDisplay _themeDisplay = Mockito.mock(ThemeDisplay.class);

}