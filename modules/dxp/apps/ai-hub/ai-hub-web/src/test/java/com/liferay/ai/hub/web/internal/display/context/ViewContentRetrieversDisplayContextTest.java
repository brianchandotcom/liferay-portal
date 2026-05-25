/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.ai.hub.web.internal.display.context;

import com.liferay.frontend.data.set.model.FDSActionDropdownItem;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.portlet.LiferayPortletURL;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import jakarta.portlet.ActionRequest;

import jakarta.servlet.http.HttpServletRequest;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

import org.mockito.Mockito;

import org.springframework.mock.web.MockHttpServletRequest;

/**
 * @author Mario Gomes
 */
public class ViewContentRetrieversDisplayContextTest {

	@ClassRule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() throws Exception {
		_setUpGroupLocalService();
		_setUpLanguageUtil();
		_setUpMockHttpServletRequest();
		_setUpObjectDefinitionLocalService();
		_setUpPortalUtil();
	}

	@Test
	public void testGetFDSActionDropdownItems() throws Exception {
		ViewContentRetrieversDisplayContext
			viewContentRetrieversDisplayContext =
				new ViewContentRetrieversDisplayContext(
					_groupLocalService, _mockHttpServletRequest,
					_objectDefinitionLocalService, _portal);

		List<FDSActionDropdownItem> fdsActionDropdownItems =
			viewContentRetrieversDisplayContext.getFDSActionDropdownItems();

		Assert.assertEquals(
			fdsActionDropdownItems.toString(), 4,
			fdsActionDropdownItems.size());

		_assertFDSActionDropdownItem(
			fdsActionDropdownItems.get(0),
			StringBundler.concat(
				_PORTAL_URL, "/web", _GROUP_FRIENDLY_URL, "/content-retriever",
				"?externalReferenceCode=%7BexternalReferenceCode%7D"),
			"view", "view", "view", "get", null);
		_assertFDSActionDropdownItem(
			fdsActionDropdownItems.get(1),
			"/o/ai-hub/content-retrievers/by-external-reference-code" +
				"/{externalReferenceCode}/object-actions/crawler",
			"reload", "put", "sync-now", "put", "async");
		_assertFDSActionDropdownItem(
			fdsActionDropdownItems.get(2),
			"/o/ai-hub/content-retrievers/by-external-reference-code" +
				"/{externalReferenceCode}",
			"trash", "delete", "delete", "delete", "async");
		_assertFDSActionDropdownItem(
			fdsActionDropdownItems.get(3), _PERMISSIONS_URL,
			"password-policies", "permissions", "permissions", "get",
			"modal-permissions");
	}

	private void _assertFDSActionDropdownItem(
		FDSActionDropdownItem fdsActionDropdownItem, String href, String icon,
		String id, String label, String method, String target) {

		Assert.assertNotNull(fdsActionDropdownItem);

		Map<String, String> data =
			(Map<String, String>)fdsActionDropdownItem.get("data");

		Assert.assertEquals(id, data.get("id"));
		Assert.assertEquals(method, data.get("method"));

		Assert.assertEquals(href, fdsActionDropdownItem.get("href"));
		Assert.assertEquals(icon, fdsActionDropdownItem.get("icon"));
		Assert.assertEquals(label, fdsActionDropdownItem.get("label"));
		Assert.assertEquals(target, fdsActionDropdownItem.get("target"));
	}

	private void _setUpGroupLocalService() throws Exception {
		Group group = Mockito.mock(Group.class);

		Mockito.when(
			group.getFriendlyURL()
		).thenReturn(
			_GROUP_FRIENDLY_URL
		);

		Mockito.when(
			_groupLocalService.getGroup(Mockito.anyLong())
		).thenReturn(
			group
		);
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
			company.getPortalURL(Mockito.anyLong())
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

		_mockHttpServletRequest.setAttribute(
			WebKeys.THEME_DISPLAY, themeDisplay);
	}

	private void _setUpObjectDefinitionLocalService() throws Exception {
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

		Mockito.when(
			_objectDefinitionLocalService.
				getObjectDefinitionByExternalReferenceCode(
					"L_AI_HUB_CONTENT_RETRIEVER", _COMPANY_ID)
		).thenReturn(
			objectDefinition
		);
	}

	private void _setUpPortalUtil() {
		PortalUtil portalUtil = new PortalUtil();

		LiferayPortletURL liferayPortletURL = Mockito.mock(
			LiferayPortletURL.class);

		Mockito.when(
			liferayPortletURL.toString()
		).thenReturn(
			_PERMISSIONS_URL
		);

		Mockito.when(
			_portal.getControlPanelPortletURL(
				_mockHttpServletRequest,
				"com_liferay_portlet_configuration_web_portlet_" +
					"PortletConfigurationPortlet",
				ActionRequest.RENDER_PHASE)
		).thenReturn(
			liferayPortletURL
		);

		Mockito.doAnswer(
			invocation -> new String[] {
				invocation.getArgument(0, String.class), StringPool.BLANK
			}
		).when(
			_portal
		).stripURLAnchor(
			Mockito.anyString(), Mockito.anyString()
		);

		portalUtil.setPortal(_portal);
	}

	private static final long _COMPANY_ID = RandomTestUtil.randomLong();

	private static final String _GROUP_FRIENDLY_URL =
		"/" + RandomTestUtil.randomString();

	private static final String _PERMISSIONS_URL =
		"http://" + RandomTestUtil.randomString() + "/edit_permissions";

	private static final String _PORTAL_URL =
		"http://" + RandomTestUtil.randomString() + ".com";

	private final GroupLocalService _groupLocalService = Mockito.mock(
		GroupLocalService.class);
	private final MockHttpServletRequest _mockHttpServletRequest =
		new MockHttpServletRequest();
	private final ObjectDefinitionLocalService _objectDefinitionLocalService =
		Mockito.mock(ObjectDefinitionLocalService.class);
	private final Portal _portal = Mockito.mock(Portal.class);

}