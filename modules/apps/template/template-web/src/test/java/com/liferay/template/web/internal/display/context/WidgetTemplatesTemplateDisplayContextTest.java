/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.template.web.internal.display.context;

import com.liferay.dynamic.data.mapping.configuration.DDMWebConfiguration;
import com.liferay.dynamic.data.mapping.constants.DDMActionKeys;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.service.PortletLocalService;
import com.liferay.portal.kernel.service.PortletLocalServiceUtil;
import com.liferay.portal.kernel.service.permission.PortletPermissionUtil;
import com.liferay.portal.kernel.test.TestInfo;
import com.liferay.portal.kernel.test.portlet.MockLiferayPortletRenderRequest;
import com.liferay.portal.kernel.test.portlet.MockLiferayPortletRenderResponse;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.test.rule.LiferayUnitTestRule;
import com.liferay.template.constants.TemplatePortletKeys;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.MockedStatic;
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
		_setUpPortletLocalService();
		_setUpThemeDisplay();
	}

	@After
	public void tearDown() {
		if (_portletLocalServiceUtilMockedStatic != null) {
			_portletLocalServiceUtilMockedStatic.close();
		}

		if (_portletPermissionUtilMockedStatic != null) {
			_portletPermissionUtilMockedStatic.close();
		}
	}

	@Test
	@TestInfo({"LDP-69505", "LPS-116076"})
	public void testIsAddButtonEnabledWithEnableTemplateCreationDisabled() {
		Mockito.when(
			_ddmWebConfiguration.enableTemplateCreation()
		).thenReturn(
			false
		);

		_portletPermissionUtilMockedStatic.when(
			() -> PortletPermissionUtil.contains(
				Mockito.any(PermissionChecker.class), Mockito.anyLong(),
				Mockito.any(), Mockito.eq(TemplatePortletKeys.TEMPLATE),
				Mockito.eq(DDMActionKeys.ADD_TEMPLATE), Mockito.anyBoolean(),
				Mockito.anyBoolean())
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
	@TestInfo({"LDP-69505", "LPS-116076"})
	public void testIsAddButtonEnabledWithEnableTemplateCreationEnabled() {
		Mockito.when(
			_ddmWebConfiguration.enableTemplateCreation()
		).thenReturn(
			true
		);

		_portletPermissionUtilMockedStatic.when(
			() -> PortletPermissionUtil.contains(
				Mockito.any(PermissionChecker.class), Mockito.anyLong(),
				Mockito.any(), Mockito.eq(TemplatePortletKeys.TEMPLATE),
				Mockito.eq(DDMActionKeys.ADD_TEMPLATE), Mockito.anyBoolean(),
				Mockito.anyBoolean())
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

	private void _setUpPortletLocalService() {
		_portletLocalServiceUtilMockedStatic = Mockito.mockStatic(
			PortletLocalServiceUtil.class);

		_portletLocalServiceUtilMockedStatic.when(
			PortletLocalServiceUtil::getService
		).thenReturn(
			_portletLocalService
		);

		Mockito.when(
			_portletLocalService.getPortletById(
				Mockito.anyLong(), Mockito.anyString())
		).thenReturn(
			_portlet
		);

		Mockito.when(
			_portlet.getPortletId()
		).thenReturn(
			"com_liferay_asset_publisher_web_portlet_AssetPublisherPortlet"
		);
	}

	private void _setUpThemeDisplay() {
		_portletPermissionUtilMockedStatic = Mockito.mockStatic(
			PortletPermissionUtil.class);

		Mockito.when(
			_themeDisplay.getPermissionChecker()
		).thenReturn(
			_permissionChecker
		);

		Mockito.when(
			_themeDisplay.getScopeGroup()
		).thenReturn(
			_group
		);

		Mockito.when(
			_group.getGroupId()
		).thenReturn(
			RandomTestUtil.randomLong()
		);
	}

	private final DDMWebConfiguration _ddmWebConfiguration = Mockito.mock(
		DDMWebConfiguration.class);
	private final Group _group = Mockito.mock(Group.class);
	private final PermissionChecker _permissionChecker = Mockito.mock(
		PermissionChecker.class);
	private final Portlet _portlet = Mockito.mock(Portlet.class);
	private final PortletLocalService _portletLocalService = Mockito.mock(
		PortletLocalService.class);
	private MockedStatic<PortletLocalServiceUtil>
		_portletLocalServiceUtilMockedStatic;
	private MockedStatic<PortletPermissionUtil>
		_portletPermissionUtilMockedStatic;
	private final ThemeDisplay _themeDisplay = Mockito.mock(ThemeDisplay.class);

}