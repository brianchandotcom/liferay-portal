/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.navigation.menu.web.internal.exportimport.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.exportimport.kernel.configuration.ExportImportConfigurationParameterMapFactoryUtil;
import com.liferay.exportimport.kernel.lar.PortletDataHandlerKeys;
import com.liferay.exportimport.kernel.staging.MergeLayoutPrototypesThreadLocal;
import com.liferay.exportimport.kernel.staging.StagingUtil;
import com.liferay.layout.test.util.LayoutTestUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutSet;
import com.liferay.portal.kernel.model.LayoutSetPrototype;
import com.liferay.portal.kernel.portlet.PortletIdCodec;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.PortletPreferencesLocalService;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.PortletKeys;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portlet.display.template.test.util.BaseExportImportTestCase;
import com.liferay.site.navigation.constants.SiteNavigationMenuPortletKeys;
import com.liferay.site.navigation.model.SiteNavigationMenu;
import com.liferay.site.navigation.model.SiteNavigationMenuItem;
import com.liferay.site.navigation.service.SiteNavigationMenuLocalService;
import com.liferay.site.navigation.test.util.SiteNavigationMenuItemTestUtil;
import com.liferay.site.navigation.test.util.SiteNavigationMenuTestUtil;
import com.liferay.sites.kernel.util.Sites;

import java.util.Map;

import javax.portlet.PortletPreferences;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Javier Moral
 */
@RunWith(Arquillian.class)
public class SiteNavigationMenuExportImportTest
	extends BaseExportImportTestCase {

	@Override
	public String getPortletId() throws Exception {
		return PortletIdCodec.encode(
			SiteNavigationMenuPortletKeys.SITE_NAVIGATION_MENU,
			RandomTestUtil.randomString());
	}

	@Before
	public void setUp() throws Exception {
		super.setUp();

		_liveGroup = GroupTestUtil.addGroup();

		GroupTestUtil.enableLocalStaging(
			_liveGroup, TestPropsValues.getUserId());

		_stagingGroup = _liveGroup.getStagingGroup();

		_layout = LayoutTestUtil.addTypePortletLayout(_stagingGroup);

		_siteNavigationMenu = SiteNavigationMenuTestUtil.addSiteNavigationMenu(
			_stagingGroup);

		_siteNavigationMenuItem =
			SiteNavigationMenuItemTestUtil.addSiteNavigationMenuItem(
				_siteNavigationMenu);
	}

	@Test
	public void testExportImport() throws Exception {
		String portletId = LayoutTestUtil.addPortletToLayout(
			_layout, SiteNavigationMenuPortletKeys.SITE_NAVIGATION_MENU,
			HashMapBuilder.put(
				"rootMenuItemExternalReferenceCode",
				new String[] {
					_siteNavigationMenuItem.getExternalReferenceCode()
				}
			).put(
				"siteNavigationMenuExternalReferenceCode",
				new String[] {_siteNavigationMenu.getExternalReferenceCode()}
			).build());

		_publishLayouts();

		_siteNavigationMenuLocalService.
			getSiteNavigationMenuByExternalReferenceCode(
				_siteNavigationMenu.getExternalReferenceCode(),
				_liveGroup.getGroupId());

		Layout layout = _layoutLocalService.getLayoutByUuidAndGroupId(
			_layout.getUuid(), _liveGroup.getGroupId(),
			_layout.isPrivateLayout());

		PortletPreferences portletPreferences =
			_portletPreferencesLocalService.getPreferences(
				_liveGroup.getCompanyId(), PortletKeys.PREFS_OWNER_ID_DEFAULT,
				PortletKeys.PREFS_OWNER_TYPE_LAYOUT, layout.getPlid(),
				portletId);

		Assert.assertEquals(
			_siteNavigationMenu.getExternalReferenceCode(),
			portletPreferences.getValue(
				"siteNavigationMenuExternalReferenceCode", StringPool.BLANK));
		Assert.assertEquals(
			_siteNavigationMenuItem.getExternalReferenceCode(),
			portletPreferences.getValue(
				"rootMenuItemExternalReferenceCode", StringPool.BLANK));
	}

	@Override
	@Test
	public void testExportImportAssetLinks() throws Exception {
	}

	@Test
	public void testExportImportEmptyPortletPreferences() throws Exception {
		String portletId = LayoutTestUtil.addPortletToLayout(
			_layout, SiteNavigationMenuPortletKeys.SITE_NAVIGATION_MENU,
			HashMapBuilder.put(
				"siteNavigationMenuType", new String[] {"1"}
			).build());

		_publishLayouts();

		Layout layout = _layoutLocalService.getLayoutByUuidAndGroupId(
			_layout.getUuid(), _liveGroup.getGroupId(),
			_layout.isPrivateLayout());

		PortletPreferences portletPreferences =
			_portletPreferencesLocalService.getPreferences(
				_liveGroup.getCompanyId(), PortletKeys.PREFS_OWNER_ID_DEFAULT,
				PortletKeys.PREFS_OWNER_TYPE_LAYOUT, layout.getPlid(),
				portletId);

		Assert.assertEquals(
			"1",
			portletPreferences.getValue(
				"siteNavigationMenuType", StringPool.BLANK));
	}

	@Test
	public void testSiteTemplatePropagationWhenDuplicateSiteNavigationMenusExist()
		throws Exception {

		Group group = GroupTestUtil.addGroup();

		LayoutSetPrototype layoutSetPrototype =
			LayoutTestUtil.addLayoutSetPrototype(RandomTestUtil.randomString());

		Layout prototypeLayout = LayoutTestUtil.addTypePortletLayout(
			layoutSetPrototype.getGroup(), true);

		MergeLayoutPrototypesThreadLocal.clearMergeComplete();

		_sites.updateLayoutSetPrototypesLinks(
			group, layoutSetPrototype.getLayoutSetPrototypeId(), 0, true, true);

		Layout layout = _layoutLocalService.getFriendlyURLLayout(
			group.getGroupId(), false, prototypeLayout.getFriendlyURL());

		layout.setLayoutPrototypeUuid(prototypeLayout.getUuid());
		layout.setLayoutPrototypeLinkEnabled(true);

		_layoutLocalService.updateLayout(layout);

		String name = RandomTestUtil.randomString();

		SiteNavigationMenuTestUtil.addSiteNavigationMenu(group, name);

		LayoutTestUtil.addPortletToLayout(
			prototypeLayout, SiteNavigationMenuPortletKeys.SITE_NAVIGATION_MENU,
			HashMapBuilder.put(
				"siteNavigationMenuExternalReferenceCode",
				() -> {
					SiteNavigationMenu siteNavigationMenu =
						SiteNavigationMenuTestUtil.addSiteNavigationMenu(
							layoutSetPrototype.getGroup(), name);

					return new String[] {
						siteNavigationMenu.getExternalReferenceCode()
					};
				}
			).build());

		MergeLayoutPrototypesThreadLocal.clearMergeComplete();

		MergeLayoutPrototypesThreadLocal.setSkipMerge(false);

		_sites.mergeLayoutSetPrototypeLayouts(
			group, group.getPublicLayoutSet());

		LayoutSet layoutSet = layoutSetPrototype.getLayoutSet();

		UnicodeProperties layoutSetPrototypeSettingsUnicodeProperties =
			layoutSet.getSettingsProperties();

		Assert.assertEquals(
			0,
			GetterUtil.getInteger(
				layoutSetPrototypeSettingsUnicodeProperties.getProperty(
					Sites.MERGE_FAIL_COUNT)));
	}

	private void _publishLayouts() throws Exception {
		Map<String, String[]> parameterMap =
			ExportImportConfigurationParameterMapFactoryUtil.
				buildParameterMap();

		parameterMap.put(
			PortletDataHandlerKeys.PORTLET_DATA,
			new String[] {Boolean.FALSE.toString()});
		parameterMap.put(
			PortletDataHandlerKeys.PORTLET_DATA_ALL,
			new String[] {Boolean.FALSE.toString()});

		StagingUtil.publishLayouts(
			TestPropsValues.getUserId(), _stagingGroup.getGroupId(),
			_liveGroup.getGroupId(), false, parameterMap);
	}

	private Layout _layout;

	@Inject
	private LayoutLocalService _layoutLocalService;

	@DeleteAfterTestRun
	private Group _liveGroup;

	@Inject
	private PortletPreferencesLocalService _portletPreferencesLocalService;

	private SiteNavigationMenu _siteNavigationMenu;
	private SiteNavigationMenuItem _siteNavigationMenuItem;

	@Inject
	private SiteNavigationMenuLocalService _siteNavigationMenuLocalService;

	@Inject
	private Sites _sites;

	private Group _stagingGroup;

}