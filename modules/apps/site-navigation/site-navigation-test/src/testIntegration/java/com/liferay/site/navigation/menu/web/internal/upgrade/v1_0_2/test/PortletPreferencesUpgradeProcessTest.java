/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.navigation.menu.web.internal.upgrade.v1_0_2.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.layout.test.util.LayoutTestUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.TestInfo;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.UnicodePropertiesBuilder;
import com.liferay.portal.kernel.version.Version;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;
import com.liferay.portlet.display.template.test.util.BaseUpgradePortletPreferencesTestCase;
import com.liferay.site.navigation.constants.SiteNavigationConstants;
import com.liferay.site.navigation.constants.SiteNavigationMenuPortletKeys;
import com.liferay.site.navigation.menu.item.layout.constants.SiteNavigationMenuItemTypeConstants;
import com.liferay.site.navigation.model.SiteNavigationMenu;
import com.liferay.site.navigation.model.SiteNavigationMenuItem;
import com.liferay.site.navigation.service.SiteNavigationMenuItemLocalService;
import com.liferay.site.navigation.service.SiteNavigationMenuLocalService;

import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Javier Moral
 */
@RunWith(Arquillian.class)
public class PortletPreferencesUpgradeProcessTest
	extends BaseUpgradePortletPreferencesTestCase {

	@Test
	@TestInfo("LPD-34306")
	public void testUpgrade() throws Exception {
		Layout layout = LayoutTestUtil.addTypePortletLayout(group);

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				group.getGroupId(), TestPropsValues.getUserId());

		SiteNavigationMenu siteNavigationMenu =
			_siteNavigationMenuLocalService.addSiteNavigationMenu(
				null, TestPropsValues.getUserId(), group.getGroupId(), "Menu",
				SiteNavigationConstants.TYPE_DEFAULT, true, serviceContext);

		SiteNavigationMenuItem siteNavigationMenuItem =
			_siteNavigationMenuItemLocalService.addSiteNavigationMenuItem(
				null, TestPropsValues.getUserId(), group.getGroupId(),
				siteNavigationMenu.getSiteNavigationMenuId(), 0,
				SiteNavigationMenuItemTypeConstants.LAYOUT,
				UnicodePropertiesBuilder.create(
					true
				).put(
					"groupId", String.valueOf(group.getGroupId())
				).put(
					"layoutUuid", layout.getUuid()
				).put(
					"privateLayout", false
				).put(
					"title", RandomTestUtil.randomString()
				).buildString(),
				serviceContext);

		testUpgrade(
			HashMapBuilder.put(
				"rootMenuItemExternalReferenceCode",
				siteNavigationMenuItem.getExternalReferenceCode()
			).put(
				"rootMenuItemId",
				String.valueOf(
					siteNavigationMenuItem.getSiteNavigationMenuItemId())
			).put(
				"siteNavigationMenuExternalReferenceCode",
				siteNavigationMenu.getExternalReferenceCode()
			).put(
				"siteNavigationMenuId",
				String.valueOf(siteNavigationMenu.getSiteNavigationMenuId())
			).build(),
			HashMapBuilder.put(
				"rootMenuItemId",
				String.valueOf(
					siteNavigationMenuItem.getSiteNavigationMenuItemId())
			).put(
				"siteNavigationMenuId",
				String.valueOf(siteNavigationMenu.getSiteNavigationMenuId())
			).build());
	}

	@Override
	protected String getPortletId() {
		return SiteNavigationMenuPortletKeys.SITE_NAVIGATION_MENU;
	}

	@Override
	protected UpgradeStepRegistrator getUpgradeStepRegistrator() {
		return _upgradeStepRegistrator;
	}

	@Override
	protected Version getVersion() {
		return _VERSION;
	}

	private static final Version _VERSION = new Version(1, 0, 2);

	@Inject
	private SiteNavigationMenuItemLocalService
		_siteNavigationMenuItemLocalService;

	@Inject
	private SiteNavigationMenuLocalService _siteNavigationMenuLocalService;

	@Inject(
		filter = "(&(component.name=com.liferay.site.navigation.menu.web.internal.upgrade.registry.SiteNavigationMenuWebUpgradeStepRegistrator))"
	)
	private UpgradeStepRegistrator _upgradeStepRegistrator;

}