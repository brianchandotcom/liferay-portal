/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.page.template.admin.web.internal.display.context;

import com.liferay.frontend.taglib.clay.servlet.taglib.util.NavigationItem;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.test.portlet.MockLiferayPortletURL;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.test.rule.LiferayUnitTestRule;
import com.liferay.staging.StagingGroupHelper;
import com.liferay.staging.StagingGroupHelperUtil;

import java.util.List;

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
public class LayoutPageTemplatesAdminDisplayContextTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() {
		_setUpPortalUtil();
		_setUpLanguageUtil();
	}

	@After
	public void tearDown() {
		_portletURLBuilderMockedStatic.close();
		_stagingGroupHelperUtilMockedStatic.close();
	}

	@Test
	public void testGetNavigationItems() {
		_setUpContext(false, false, false);

		LayoutPageTemplatesAdminDisplayContext
			layoutPageTemplatesAdminDisplayContext =
				new LayoutPageTemplatesAdminDisplayContext(
					_liferayPortletRequest, _liferayPortletResponse);

		List<NavigationItem> navigationItems =
			layoutPageTemplatesAdminDisplayContext.getNavigationItems();

		Assert.assertEquals(
			navigationItems.toString(), 3, navigationItems.size());

		NavigationItem navigationItem = navigationItems.get(0);

		Assert.assertEquals("Masters", navigationItem.get("label"));

		navigationItem = navigationItems.get(1);

		Assert.assertEquals("Page Templates", navigationItem.get("label"));

		navigationItem = navigationItems.get(2);

		Assert.assertEquals(
			"Display Page Templates", navigationItem.get("label"));
	}

	@Test
	public void testGetNavigationItemsInCompanyGroup() {
		_setUpContext(true, false, false);

		LayoutPageTemplatesAdminDisplayContext
			layoutPageTemplatesAdminDisplayContext =
				new LayoutPageTemplatesAdminDisplayContext(
					_liferayPortletRequest, _liferayPortletResponse);

		Assert.assertTrue(
			ListUtil.isEmpty(
				layoutPageTemplatesAdminDisplayContext.getNavigationItems()));
	}

	@Test
	public void testGetNavigationItemsInLocalLiveStagingGroup() {
		_setUpContext(false, true, false);

		LayoutPageTemplatesAdminDisplayContext
			layoutPageTemplatesAdminDisplayContext =
				new LayoutPageTemplatesAdminDisplayContext(
					_liferayPortletRequest, _liferayPortletResponse);

		List<NavigationItem> navigationItems =
			layoutPageTemplatesAdminDisplayContext.getNavigationItems();

		Assert.assertEquals(
			navigationItems.toString(), 0, navigationItems.size());
	}

	@Test
	public void testGetNavigationItemsInRemoteLiveStagingGroup() {
		_setUpContext(false, false, true);

		LayoutPageTemplatesAdminDisplayContext
			layoutPageTemplatesAdminDisplayContext =
				new LayoutPageTemplatesAdminDisplayContext(
					_liferayPortletRequest, _liferayPortletResponse);

		List<NavigationItem> navigationItems =
			layoutPageTemplatesAdminDisplayContext.getNavigationItems();

		Assert.assertEquals(
			navigationItems.toString(), 0, navigationItems.size());
	}

	private void _setUpContext(
		boolean company, boolean localLiveGroup, boolean removeLiveGroup) {

		Mockito.when(
			_group.isCompany()
		).thenReturn(
			company
		);

		Mockito.when(
			_liferayPortletRequest.getAttribute(WebKeys.THEME_DISPLAY)
		).thenReturn(
			_themeDisplay
		);

		Mockito.when(
			_themeDisplay.getScopeGroup()
		).thenReturn(
			_group
		);

		Mockito.when(
			LanguageUtil.get(_httpServletRequest, "masters")
		).thenReturn(
			"Masters"
		);

		Mockito.when(
			LanguageUtil.get(_httpServletRequest, "page-templates")
		).thenReturn(
			"Page Templates"
		);

		Mockito.when(
			LanguageUtil.get(_httpServletRequest, "display-page-templates")
		).thenReturn(
			"Display Page Templates"
		);

		Mockito.when(
			PortalUtil.getHttpServletRequest(_liferayPortletRequest)
		).thenReturn(
			_httpServletRequest
		);

		Mockito.when(
			PortletURLBuilder.createRenderURL(_liferayPortletResponse)
		).thenReturn(
			new PortletURLBuilder.PortletURLStep(new MockLiferayPortletURL())
		);

		StagingGroupHelper stagingGroupHelper = Mockito.mock(
			StagingGroupHelper.class);

		Mockito.when(
			StagingGroupHelperUtil.getStagingGroupHelper()
		).thenReturn(
			stagingGroupHelper
		);

		Mockito.when(
			stagingGroupHelper.isLocalLiveGroup(_group)
		).thenReturn(
			localLiveGroup
		);

		Mockito.when(
			stagingGroupHelper.isRemoteLiveGroup(_group)
		).thenReturn(
			removeLiveGroup
		);
	}

	private void _setUpLanguageUtil() {
		LanguageUtil languageUtil = new LanguageUtil();

		languageUtil.setLanguage(Mockito.mock(Language.class));
	}

	private void _setUpPortalUtil() {
		PortalUtil portalUtil = new PortalUtil();

		portalUtil.setPortal(_portal);
	}

	private final Group _group = Mockito.mock(Group.class);
	private final HttpServletRequest _httpServletRequest = Mockito.mock(
		HttpServletRequest.class);
	private final LiferayPortletRequest _liferayPortletRequest = Mockito.mock(
		LiferayPortletRequest.class);
	private final LiferayPortletResponse _liferayPortletResponse = Mockito.mock(
		LiferayPortletResponse.class);
	private final Portal _portal = Mockito.mock(Portal.class);
	private final MockedStatic<PortletURLBuilder>
		_portletURLBuilderMockedStatic = Mockito.mockStatic(
			PortletURLBuilder.class);
	private final MockedStatic<StagingGroupHelperUtil>
		_stagingGroupHelperUtilMockedStatic = Mockito.mockStatic(
			StagingGroupHelperUtil.class);
	private final ThemeDisplay _themeDisplay = Mockito.mock(ThemeDisplay.class);

}