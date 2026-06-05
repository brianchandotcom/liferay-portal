/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.ai.hub.web.internal.display.context;

import com.liferay.ai.hub.web.internal.test.util.DisplayContextTestUtil;
import com.liferay.frontend.data.set.model.FDSActionDropdownItem;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.service.ObjectDefinitionLocalServiceUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.test.portlet.MockLiferayPortletURL;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import jakarta.portlet.ActionRequest;

import jakarta.servlet.http.HttpServletRequest;

import java.util.List;
import java.util.Locale;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

import org.mockito.MockedStatic;
import org.mockito.Mockito;

import org.springframework.mock.web.MockHttpServletRequest;

/**
 * @author Mario Gomes
 */
public class ViewInstructionDefinitionsDisplayContextTest {

	@ClassRule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@AfterClass
	public static void tearDownClass() {
		_objectDefinitionLocalServiceUtilMockedStatic.close();
		_portalUtilMockedStatic.close();
	}

	@Before
	public void setUp() throws Exception {
		_setUpLanguageUtil();
		_setUpMockHttpServletRequest();
		_setUpObjectDefinition();
		_setUpPortalUtil();
	}

	@Test
	public void testGetFDSActionDropdownItems() throws Exception {
		ViewInstructionDefinitionsDisplayContext
			viewInstructionDefinitionsDisplayContext =
				new ViewInstructionDefinitionsDisplayContext(
					_mockHttpServletRequest);

		List<FDSActionDropdownItem> fdsActionDropdownItems =
			viewInstructionDefinitionsDisplayContext.
				getFDSActionDropdownItems();

		Assert.assertEquals(
			fdsActionDropdownItems.toString(), 3,
			fdsActionDropdownItems.size());

		DisplayContextTestUtil.assertFDSActionDropdownItem(
			fdsActionDropdownItems.get(0),
			StringBundler.concat(
				_PORTAL_URL, "/web", _GROUP_FRIENDLY_URL, "/instruction",
				"?externalReferenceCode={externalReferenceCode}"),
			"view", "view", "view", "get", null);
		DisplayContextTestUtil.assertFDSActionDropdownItem(
			fdsActionDropdownItems.get(1),
			"/o/ai-hub/instruction-definitions/by-external-reference-code" +
				"/{externalReferenceCode}",
			"trash", "delete", "delete", "delete", "async");
		DisplayContextTestUtil.assertFDSActionDropdownItem(
			fdsActionDropdownItems.get(2), _PERMISSIONS_URL,
			"password-policies", "permissions", "permissions", "get",
			"modal-permissions");
	}

	private void _setUpLanguageUtil() {
		LanguageUtil languageUtil = new LanguageUtil();

		languageUtil.setLanguage(Mockito.mock(Language.class));

		Mockito.when(
			LanguageUtil.get(
				Mockito.any(HttpServletRequest.class), Mockito.anyString())
		).thenAnswer(
			invocation -> invocation.getArgument(1)
		);
	}

	private void _setUpMockHttpServletRequest() throws Exception {
		ThemeDisplay themeDisplay = Mockito.mock(ThemeDisplay.class);

		Company company = Mockito.mock(Company.class);

		Mockito.when(
			company.getPortalURL(GroupConstants.DEFAULT_PARENT_GROUP_ID)
		).thenReturn(
			_PORTAL_URL
		);

		Mockito.when(
			themeDisplay.getCompany()
		).thenReturn(
			company
		);

		Mockito.when(
			themeDisplay.getCompanyId()
		).thenReturn(
			_COMPANY_ID
		);

		Group group = Mockito.mock(Group.class);

		Mockito.when(
			group.getFriendlyURL()
		).thenReturn(
			_GROUP_FRIENDLY_URL
		);

		Mockito.when(
			themeDisplay.getScopeGroup()
		).thenReturn(
			group
		);

		_mockHttpServletRequest.setAttribute(
			WebKeys.THEME_DISPLAY, themeDisplay);
	}

	private void _setUpObjectDefinition() {
		ObjectDefinition objectDefinition = Mockito.mock(
			ObjectDefinition.class);

		Mockito.when(
			objectDefinition.getClassName()
		).thenReturn(
			RandomTestUtil.randomString()
		);

		Mockito.when(
			objectDefinition.getLabel(Mockito.any(Locale.class))
		).thenReturn(
			RandomTestUtil.randomString()
		);

		_objectDefinitionLocalServiceUtilMockedStatic.when(
			() ->
				ObjectDefinitionLocalServiceUtil.
					getObjectDefinitionByExternalReferenceCode(
						"L_AI_HUB_INSTRUCTION_DEFINITION", _COMPANY_ID)
		).thenReturn(
			objectDefinition
		);
	}

	private void _setUpPortalUtil() {
		Mockito.when(
			_mockLiferayPortletURL.toString()
		).thenReturn(
			_PERMISSIONS_URL
		);

		_portalUtilMockedStatic.when(
			() -> PortalUtil.getControlPanelPortletURL(
				_mockHttpServletRequest,
				"com_liferay_portlet_configuration_web_portlet_" +
					"PortletConfigurationPortlet",
				ActionRequest.RENDER_PHASE)
		).thenReturn(
			_mockLiferayPortletURL
		);
	}

	private static final long _COMPANY_ID = RandomTestUtil.randomLong();

	private static final String _GROUP_FRIENDLY_URL =
		"/" + RandomTestUtil.randomString();

	private static final String _PERMISSIONS_URL =
		RandomTestUtil.randomString();

	private static final String _PORTAL_URL = RandomTestUtil.randomString();

	private static final MockedStatic<ObjectDefinitionLocalServiceUtil>
		_objectDefinitionLocalServiceUtilMockedStatic = Mockito.mockStatic(
			ObjectDefinitionLocalServiceUtil.class);
	private static final MockedStatic<PortalUtil> _portalUtilMockedStatic =
		Mockito.mockStatic(PortalUtil.class);

	private final MockHttpServletRequest _mockHttpServletRequest =
		new MockHttpServletRequest();
	private final MockLiferayPortletURL _mockLiferayPortletURL = Mockito.spy(
		new MockLiferayPortletURL());

}