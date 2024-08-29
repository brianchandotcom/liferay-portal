/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.navigation.menu.web.internal.display.context;

import com.liferay.layout.page.template.constants.LayoutPageTemplateEntryTypeConstants;
import com.liferay.layout.page.template.model.LayoutPageTemplateEntry;
import com.liferay.layout.page.template.service.LayoutPageTemplateEntryLocalService;
import com.liferay.layout.page.template.service.LayoutPageTemplateEntryLocalServiceUtil;
import com.liferay.portal.configuration.module.configuration.ConfigurationProviderUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.module.service.Snapshot;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.test.rule.LiferayUnitTestRule;
import com.liferay.site.navigation.constants.SiteNavigationConstants;
import com.liferay.site.navigation.menu.web.internal.configuration.SiteNavigationMenuPortletInstanceConfiguration;
import com.liferay.site.navigation.model.SiteNavigationMenu;
import com.liferay.site.navigation.model.SiteNavigationMenuItem;
import com.liferay.site.navigation.service.SiteNavigationMenuItemLocalServiceUtil;
import com.liferay.site.navigation.service.SiteNavigationMenuLocalServiceUtil;

import javax.servlet.http.HttpServletRequest;

import org.junit.AfterClass;
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
public class SiteNavigationMenuDisplayContextTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@AfterClass
	public static void tearDownClass() {
		_configurationProviderUtilMockedStatic.close();
		_groupLocalServiceUtilMockedStatic.close();
		_siteNavigationMenuItemLocalServiceUtilMockedStatic.close();
		_siteNavigationMenuLocalServiceUtilMockedStatic.close();
	}

	@Before
	public void setUp() {
		_setUpConfigurationProviderUtil();
		_setUpHttpServletRequest();
		_setUpLayoutPageTemplateEntryLocalServiceUtil();
		_setUpThemeDisplay();
	}

	@Test
	public void testGetAlertKeyWithoutPrivateLayoutsEnabled() throws Exception {
		_setUpGroup(false);
		_setUpLayout(false);

		SiteNavigationMenuDisplayContext siteNavigationMenuDisplayContext =
			new SiteNavigationMenuDisplayContext(_httpServletRequest);

		Assert.assertEquals("", siteNavigationMenuDisplayContext.getAlertKey());
	}

	@Test
	public void testGetAlertKeyWithPrivateLayoutsEnabledAndDisplayPageInEditMode()
		throws Exception {

		_setUpGroup(true);
		_setUpLayout(true);
		_setUpLayoutPageTemplateEntry(
			LayoutPageTemplateEntryTypeConstants.DISPLAY_PAGE);
		_setUpSiteNavigationMenuPortletInstanceConfigurationSiteNavigationMenuType(
			SiteNavigationConstants.TYPE_PRIVATE_PAGES_HIERARCHY);

		SiteNavigationMenuDisplayContext siteNavigationMenuDisplayContext =
			new SiteNavigationMenuDisplayContext(_httpServletRequest);

		Assert.assertEquals(
			"the-navigation-being-displayed-here-is-the-private-pages-" +
				"hierarchy",
			siteNavigationMenuDisplayContext.getAlertKey());
	}

	@Test
	public void testGetAlertKeyWithPrivateLayoutsEnabledAndDisplayPageInViewMode()
		throws Exception {

		_setUpGroup(true);
		_setUpLayout(false);
		_setUpLayoutPageTemplateEntry(
			LayoutPageTemplateEntryTypeConstants.DISPLAY_PAGE);
		_setUpSiteNavigationMenuPortletInstanceConfigurationSiteNavigationMenuType(
			SiteNavigationConstants.TYPE_PRIVATE_PAGES_HIERARCHY);

		SiteNavigationMenuDisplayContext siteNavigationMenuDisplayContext =
			new SiteNavigationMenuDisplayContext(_httpServletRequest);

		Assert.assertEquals("", siteNavigationMenuDisplayContext.getAlertKey());
	}

	@Test
	public void testGetAlertKeyWithPrivateLayoutsEnabledAndPrivatePagesHierarchy()
		throws Exception {

		_setUpGroup(true);
		_setUpLayout(false);
		_setUpSiteNavigationMenuPortletInstanceConfigurationSiteNavigationMenuType(
			SiteNavigationConstants.TYPE_PRIVATE_PAGES_HIERARCHY);

		SiteNavigationMenuDisplayContext siteNavigationMenuDisplayContext =
			new SiteNavigationMenuDisplayContext(_httpServletRequest);

		Assert.assertEquals(
			"the-navigation-being-displayed-here-is-the-private-pages-" +
				"hierarchy",
			siteNavigationMenuDisplayContext.getAlertKey());
	}

	@Test
	public void testGetAlertKeyWithPrivateLayoutsEnabledAndPublicPagesHierarchy()
		throws Exception {

		_setUpGroup(true);
		_setUpLayout(false);
		_setUpSiteNavigationMenuPortletInstanceConfigurationSiteNavigationMenuType(
			SiteNavigationConstants.TYPE_PUBLIC_PAGES_HIERARCHY);

		SiteNavigationMenuDisplayContext siteNavigationMenuDisplayContext =
			new SiteNavigationMenuDisplayContext(_httpServletRequest);

		Assert.assertEquals(
			"the-navigation-being-displayed-here-is-the-public-pages-hierarchy",
			siteNavigationMenuDisplayContext.getAlertKey());
	}

	@Test
	public void testGetDisplayStyleGroupId() throws ConfigurationException {
		long groupId = RandomTestUtil.randomLong();

		_setUpGroupLocalServiceUtil(groupId);

		SiteNavigationMenuDisplayContext siteNavigationMenuDisplayContext =
			new SiteNavigationMenuDisplayContext(_httpServletRequest);

		_setUpSiteNavigationMenuPortletInstanceConfigurationDisplayStyleGroup(
			RandomTestUtil.randomLong());

		Assert.assertEquals(
			groupId, siteNavigationMenuDisplayContext.getDisplayStyleGroupId());
	}

	@Test
	public void testGetRootMenuItemId() throws ConfigurationException {
		long rootMenuItemId = RandomTestUtil.randomLong();

		_setUpSiteNavigationMenuItemLocalServiceUtil(rootMenuItemId);

		SiteNavigationMenuDisplayContext siteNavigationMenuDisplayContext =
			new SiteNavigationMenuDisplayContext(_httpServletRequest);

		_setUpSiteNavigationMenuPortletInstanceConfigurationRootMenuItem(
			String.valueOf(RandomTestUtil.randomLong()));

		Assert.assertEquals(
			String.valueOf(rootMenuItemId),
			siteNavigationMenuDisplayContext.getRootMenuItemId());
	}

	@Test
	public void testGetSiteNavigationMenuId() throws ConfigurationException {
		long siteNavigationMenuId = RandomTestUtil.randomLong();

		_setUpSiteNavigationMenuLocalServiceUtil(siteNavigationMenuId);

		SiteNavigationMenuDisplayContext siteNavigationMenuDisplayContext =
			new SiteNavigationMenuDisplayContext(_httpServletRequest);

		_setUpSiteNavigationMenuPortletInstanceConfigurationSiteNavigationMenu(
			RandomTestUtil.randomLong());

		Assert.assertEquals(
			siteNavigationMenuId,
			siteNavigationMenuDisplayContext.getSiteNavigationMenuId());
	}

	private void _setUpConfigurationProviderUtil() {
		_configurationProviderUtilMockedStatic.when(
			() -> ConfigurationProviderUtil.getPortletInstanceConfiguration(
				Mockito.any(), Mockito.any())
		).thenReturn(
			_siteNavigationMenuPortletInstanceConfiguration
		);
	}

	private void _setUpGroup(boolean privateLayoutsEnabled) {
		Mockito.when(
			_group.isPrivateLayoutsEnabled()
		).thenReturn(
			privateLayoutsEnabled
		);
	}

	private void _setUpGroupLocalServiceUtil(long groupId) {
		Group group = Mockito.mock(Group.class);

		Mockito.when(
			group.getGroupId()
		).thenReturn(
			groupId
		);

		_groupLocalServiceUtilMockedStatic.when(
			() -> GroupLocalServiceUtil.fetchGroupByExternalReferenceCode(
				Mockito.anyString(), Mockito.anyLong())
		).thenReturn(
			group
		);
	}

	private void _setUpHttpServletRequest() {
		Mockito.when(
			(ThemeDisplay)_httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY)
		).thenReturn(
			_themeDisplay
		);
	}

	private void _setUpLayout(boolean draftLayout) {
		Mockito.when(
			_layout.isDraftLayout()
		).thenReturn(
			draftLayout
		);
	}

	private void _setUpLayoutPageTemplateEntry(int type) {
		Mockito.when(
			_layoutPageTemplateEntry.getType()
		).thenReturn(
			type
		);
	}

	private void _setUpLayoutPageTemplateEntryLocalServiceUtil() {
		LayoutPageTemplateEntryLocalService
			layoutPageTemplateEntryLocalService = Mockito.mock(
				LayoutPageTemplateEntryLocalService.class);

		Mockito.when(
			layoutPageTemplateEntryLocalService.
				fetchLayoutPageTemplateEntryByPlid(Mockito.anyLong())
		).thenReturn(
			_layoutPageTemplateEntry
		);

		ReflectionTestUtil.setFieldValue(
			LayoutPageTemplateEntryLocalServiceUtil.class, "_serviceSnapshot",
			new Snapshot<LayoutPageTemplateEntryLocalService>(
				LayoutPageTemplateEntryLocalServiceUtil.class,
				LayoutPageTemplateEntryLocalService.class) {

				@Override
				public LayoutPageTemplateEntryLocalService get() {
					return layoutPageTemplateEntryLocalService;
				}

			});
	}

	private void _setUpSiteNavigationMenuItemLocalServiceUtil(
		long rootMenuItemId) {

		SiteNavigationMenuItem siteNavigationMenuItem = Mockito.mock(
			SiteNavigationMenuItem.class);

		Mockito.when(
			siteNavigationMenuItem.getSiteNavigationMenuItemId()
		).thenReturn(
			rootMenuItemId
		);

		_siteNavigationMenuItemLocalServiceUtilMockedStatic.when(
			() ->
				SiteNavigationMenuItemLocalServiceUtil.
					fetchSiteNavigationMenuItemByExternalReferenceCode(
						Mockito.anyString(), Mockito.anyLong())
		).thenReturn(
			siteNavigationMenuItem
		);
	}

	private void _setUpSiteNavigationMenuLocalServiceUtil(
		long siteNavigationMenuId) {

		SiteNavigationMenu siteNavigationMenu = Mockito.mock(
			SiteNavigationMenu.class);

		Mockito.when(
			siteNavigationMenu.getSiteNavigationMenuId()
		).thenReturn(
			siteNavigationMenuId
		);

		_siteNavigationMenuLocalServiceUtilMockedStatic.when(
			() ->
				SiteNavigationMenuLocalServiceUtil.
					fetchSiteNavigationMenuByExternalReferenceCode(
						Mockito.anyString(), Mockito.anyLong())
		).thenReturn(
			siteNavigationMenu
		);
	}

	private void
		_setUpSiteNavigationMenuPortletInstanceConfigurationDisplayStyleGroup(
			long displayStyleGroupId) {

		Mockito.when(
			_siteNavigationMenuPortletInstanceConfiguration.
				displayStyleGroupExternalReferenceCode()
		).thenReturn(
			RandomTestUtil.randomString()
		);

		Mockito.when(
			_siteNavigationMenuPortletInstanceConfiguration.
				displayStyleGroupId()
		).thenReturn(
			displayStyleGroupId
		);
	}

	private void
		_setUpSiteNavigationMenuPortletInstanceConfigurationRootMenuItem(
			String rootMenuItemId) {

		Mockito.when(
			_siteNavigationMenuPortletInstanceConfiguration.
				rootMenuItemExternalReferenceCode()
		).thenReturn(
			RandomTestUtil.randomString()
		);

		Mockito.when(
			_siteNavigationMenuPortletInstanceConfiguration.rootMenuItemId()
		).thenReturn(
			rootMenuItemId
		);
	}

	private void
		_setUpSiteNavigationMenuPortletInstanceConfigurationSiteNavigationMenu(
			long siteNavigationMenuId) {

		Mockito.when(
			_siteNavigationMenuPortletInstanceConfiguration.
				siteNavigationMenuExternalReferenceCode()
		).thenReturn(
			RandomTestUtil.randomString()
		);

		Mockito.when(
			_siteNavigationMenuPortletInstanceConfiguration.
				siteNavigationMenuId()
		).thenReturn(
			siteNavigationMenuId
		);
	}

	private void
		_setUpSiteNavigationMenuPortletInstanceConfigurationSiteNavigationMenuType(
			int siteNavigationMenuType) {

		Mockito.when(
			_siteNavigationMenuPortletInstanceConfiguration.
				siteNavigationMenuType()
		).thenReturn(
			siteNavigationMenuType
		);
	}

	private void _setUpThemeDisplay() {
		Mockito.when(
			_themeDisplay.getScopeGroup()
		).thenReturn(
			_group
		);

		Mockito.when(
			_themeDisplay.getLayout()
		).thenReturn(
			_layout
		);
	}

	private static final MockedStatic<ConfigurationProviderUtil>
		_configurationProviderUtilMockedStatic = Mockito.mockStatic(
			ConfigurationProviderUtil.class);
	private static final MockedStatic<GroupLocalServiceUtil>
		_groupLocalServiceUtilMockedStatic = Mockito.mockStatic(
			GroupLocalServiceUtil.class);
	private static final MockedStatic<SiteNavigationMenuItemLocalServiceUtil>
		_siteNavigationMenuItemLocalServiceUtilMockedStatic =
			Mockito.mockStatic(SiteNavigationMenuItemLocalServiceUtil.class);
	private static final MockedStatic<SiteNavigationMenuLocalServiceUtil>
		_siteNavigationMenuLocalServiceUtilMockedStatic = Mockito.mockStatic(
			SiteNavigationMenuLocalServiceUtil.class);

	private final Group _group = Mockito.mock(Group.class);
	private final HttpServletRequest _httpServletRequest = Mockito.mock(
		HttpServletRequest.class);
	private final Layout _layout = Mockito.mock(Layout.class);
	private final LayoutPageTemplateEntry _layoutPageTemplateEntry =
		Mockito.mock(LayoutPageTemplateEntry.class);
	private final SiteNavigationMenuPortletInstanceConfiguration
		_siteNavigationMenuPortletInstanceConfiguration = Mockito.mock(
			SiteNavigationMenuPortletInstanceConfiguration.class);
	private final ThemeDisplay _themeDisplay = Mockito.mock(ThemeDisplay.class);

}