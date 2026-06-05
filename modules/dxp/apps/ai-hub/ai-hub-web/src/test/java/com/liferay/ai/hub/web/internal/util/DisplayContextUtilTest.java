/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.ai.hub.web.internal.util;

import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.service.ObjectDefinitionLocalServiceUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.petra.string.StringUtil;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import jakarta.portlet.PortletURL;

import jakarta.servlet.http.HttpServletRequest;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.MockedStatic;
import org.mockito.Mockito;

/**
 * @author Carolina Barbosa
 */
public class DisplayContextUtilTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@AfterClass
	public static void tearDownClass() {
		_objectDefinitionLocalServiceUtilMockedStatic.close();
		_portalUtilMockedStatic.close();
	}

	@Before
	public void setUp() {
		_setUpHttpServletRequest();
		_setUpObjectDefinitionLocalServiceUtilMockedStatic();
		_setUpPortalUtilMockedStatic();
		_setUpThemeDisplay();
	}

	@Test
	public void testGetPermissionsURL() throws Exception {
		DisplayContextUtil.getPermissionsURL(
			RandomTestUtil.randomString(), _httpServletRequest);

		Mockito.verify(
			_portletURL
		).setParameter(
			"roleTypes",
			StringUtil.merge(
				new int[] {
					RoleConstants.TYPE_ACCOUNT, RoleConstants.TYPE_REGULAR,
					RoleConstants.TYPE_SITE
				},
				StringPool.COMMA)
		);
	}

	private void _setUpHttpServletRequest() {
		Mockito.when(
			_httpServletRequest.getAttribute(WebKeys.THEME_DISPLAY)
		).thenReturn(
			_themeDisplay
		);
	}

	private void _setUpObjectDefinitionLocalServiceUtilMockedStatic() {
		_objectDefinitionLocalServiceUtilMockedStatic.when(
			() ->
				ObjectDefinitionLocalServiceUtil.
					getObjectDefinitionByExternalReferenceCode(
						Mockito.anyString(), Mockito.anyLong())
		).thenReturn(
			Mockito.mock(ObjectDefinition.class)
		);
	}

	private void _setUpPortalUtilMockedStatic() {
		_portalUtilMockedStatic.when(
			() -> PortalUtil.getControlPanelPortletURL(
				Mockito.eq(_httpServletRequest), Mockito.anyString(),
				Mockito.anyString())
		).thenReturn(
			_portletURL
		);
	}

	private void _setUpThemeDisplay() {
		Mockito.when(
			_themeDisplay.getCompanyId()
		).thenReturn(
			RandomTestUtil.randomLong()
		);
	}

	private static final MockedStatic<ObjectDefinitionLocalServiceUtil>
		_objectDefinitionLocalServiceUtilMockedStatic = Mockito.mockStatic(
			ObjectDefinitionLocalServiceUtil.class);
	private static final MockedStatic<PortalUtil> _portalUtilMockedStatic =
		Mockito.mockStatic(PortalUtil.class);

	private final HttpServletRequest _httpServletRequest = Mockito.mock(
		HttpServletRequest.class);
	private final PortletURL _portletURL = Mockito.mock(PortletURL.class);
	private final ThemeDisplay _themeDisplay = Mockito.mock(ThemeDisplay.class);

}