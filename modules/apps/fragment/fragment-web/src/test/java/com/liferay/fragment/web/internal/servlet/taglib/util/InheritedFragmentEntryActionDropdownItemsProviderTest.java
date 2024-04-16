/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.fragment.web.internal.servlet.taglib.util;

import com.liferay.fragment.model.FragmentEntry;
import com.liferay.fragment.web.internal.security.permission.resource.FragmentPermission;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletURL;
import com.liferay.portal.kernel.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.test.portlet.MockLiferayPortletURL;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.List;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

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
public class InheritedFragmentEntryActionDropdownItemsProviderTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() {
		_setUpFragmentPermission();
		_setUpLanguageUtil();
		_setUpPortalUtil();
		_setUpPortletURLBuilder();
		_setUpRenderRequest();
		_setUpRenderResponse();
	}

	@After
	public void tearDown() {
		_fragmentPermissionMockedStatic.close();
		_portletURLBuilderMockedStatic.close();
	}

	@Test
	public void testGetActionDropdowns() throws Exception {
		InheritedFragmentEntryActionDropdownItemsProvider
			inheritedFragmentEntryActionDropdownItemsProvider =
				new InheritedFragmentEntryActionDropdownItemsProvider(
					Mockito.mock(FragmentEntry.class), _renderRequest,
					_renderResponse);

		List<DropdownItem> dropdownItems =
			inheritedFragmentEntryActionDropdownItemsProvider.
				getActionDropdownItems();

		Assert.assertEquals(dropdownItems.toString(), 2, dropdownItems.size());

		DropdownItem copyDropdownItem = dropdownItems.get(0);

		Assert.assertEquals("copy-to", copyDropdownItem.get("label"));

		DropdownItem exportDropdownItem = dropdownItems.get(1);

		Assert.assertEquals("export", exportDropdownItem.get("label"));
	}

	private void _setUpFragmentPermission() {
		Mockito.when(
			FragmentPermission.contains(
				Mockito.any(), Mockito.anyLong(), Mockito.anyString())
		).thenReturn(
			true
		);
	}

	private void _setUpLanguageUtil() {
		LanguageUtil languageUtil = new LanguageUtil();

		languageUtil.setLanguage(Mockito.mock(Language.class));

		Mockito.when(
			languageUtil.get(_httpServletRequest, "copy-to")
		).thenReturn(
			"copy-to"
		);

		Mockito.when(
			languageUtil.get(_httpServletRequest, "export")
		).thenReturn(
			"export"
		);
	}

	private void _setUpPortalUtil() {
		PortalUtil portalUtil = new PortalUtil();

		portalUtil.setPortal(Mockito.mock(Portal.class));

		Mockito.when(
			portalUtil.getHttpServletRequest(_renderRequest)
		).thenReturn(
			_httpServletRequest
		);
	}

	private void _setUpPortletURLBuilder() {
		Mockito.when(
			PortletURLBuilder.createActionURL(_renderResponse)
		).thenReturn(
			new PortletURLBuilder.PortletURLStep(new MockLiferayPortletURL())
		);

		Mockito.when(
			PortletURLBuilder.createRenderURL(_renderResponse)
		).thenReturn(
			new PortletURLBuilder.PortletURLStep(new MockLiferayPortletURL())
		);
	}

	private void _setUpRenderRequest() {
		Mockito.when(
			_httpServletRequest.getAttribute(WebKeys.THEME_DISPLAY)
		).thenReturn(
			_themeDisplay
		);
	}

	private void _setUpRenderResponse() {
		Mockito.when(
			_renderResponse.createResourceURL()
		).thenReturn(
			Mockito.mock(LiferayPortletURL.class)
		);
	}

	private final MockedStatic<FragmentPermission>
		_fragmentPermissionMockedStatic = Mockito.mockStatic(
			FragmentPermission.class);
	private final HttpServletRequest _httpServletRequest = Mockito.mock(
		HttpServletRequest.class);
	private final MockedStatic<PortletURLBuilder>
		_portletURLBuilderMockedStatic = Mockito.mockStatic(
			PortletURLBuilder.class);
	private final RenderRequest _renderRequest = Mockito.mock(
		RenderRequest.class);
	private final RenderResponse _renderResponse = Mockito.mock(
		RenderResponse.class);
	private final ThemeDisplay _themeDisplay = Mockito.mock(ThemeDisplay.class);

}