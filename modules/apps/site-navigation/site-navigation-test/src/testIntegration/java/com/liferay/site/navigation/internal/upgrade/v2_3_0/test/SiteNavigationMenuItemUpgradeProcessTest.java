/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.navigation.internal.upgrade.v2_3_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.change.tracking.test.util.BaseCTUpgradeProcessTestCase;
import com.liferay.info.item.InfoItemClassDetails;
import com.liferay.info.item.InfoItemServiceRegistry;
import com.liferay.layout.page.template.info.item.capability.DisplayPageInfoItemCapability;
import com.liferay.portal.kernel.dao.orm.EntityCache;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.change.tracking.CTModel;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.change.tracking.CTService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.UnicodePropertiesBuilder;
import com.liferay.portal.kernel.version.Version;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;
import com.liferay.portal.upgrade.test.util.UpgradeTestUtil;
import com.liferay.site.navigation.menu.item.layout.constants.SiteNavigationMenuItemTypeConstants;
import com.liferay.site.navigation.model.SiteNavigationMenu;
import com.liferay.site.navigation.model.SiteNavigationMenuItem;
import com.liferay.site.navigation.service.SiteNavigationMenuItemLocalService;
import com.liferay.site.navigation.test.util.SiteNavigationMenuItemTestUtil;
import com.liferay.site.navigation.test.util.SiteNavigationMenuTestUtil;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Lourdes Fernández Besada
 */
@RunWith(Arquillian.class)
public class SiteNavigationMenuItemUpgradeProcessTest
	extends BaseCTUpgradeProcessTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		_serviceContext = ServiceContextTestUtil.getServiceContext(
			_group.getGroupId());
		_siteNavigationMenu = SiteNavigationMenuTestUtil.addSiteNavigationMenu(
			_group);
	}

	@Test
	public void testUpgrade() throws Exception {
		SiteNavigationMenuItem siteNavigationMenuItem =
			SiteNavigationMenuItemTestUtil.addSiteNavigationMenuItem(
				_siteNavigationMenu);

		Assert.assertEquals(
			SiteNavigationMenuItemTypeConstants.NODE,
			siteNavigationMenuItem.getType());

		Map<Long, String> siteNavigationMenuItemIdsMap = new HashMap<>();

		for (InfoItemClassDetails infoItemClassDetails :
				_infoItemServiceRegistry.getInfoItemClassDetails(
					DisplayPageInfoItemCapability.KEY)) {

			SiteNavigationMenuItem displayPageSiteNavigationMenuItem =
				_siteNavigationMenuItemLocalService.addSiteNavigationMenuItem(
					null, TestPropsValues.getUserId(), _group.getGroupId(),
					_siteNavigationMenu.getSiteNavigationMenuId(), 0,
					infoItemClassDetails.getClassName(),
					UnicodePropertiesBuilder.put(
						"classNameId",
						_portal.getClassNameId(
							infoItemClassDetails.getClassName())
					).buildString(),
					_serviceContext);

			displayPageSiteNavigationMenuItem.setType("display_page");

			displayPageSiteNavigationMenuItem =
				_siteNavigationMenuItemLocalService.
					updateSiteNavigationMenuItem(
						displayPageSiteNavigationMenuItem);

			Assert.assertEquals(
				"display_page", displayPageSiteNavigationMenuItem.getType());

			siteNavigationMenuItemIdsMap.put(
				displayPageSiteNavigationMenuItem.getSiteNavigationMenuItemId(),
				infoItemClassDetails.getClassName());
		}

		Assert.assertFalse(siteNavigationMenuItemIdsMap.isEmpty());

		runUpgrade();

		siteNavigationMenuItem =
			_siteNavigationMenuItemLocalService.getSiteNavigationMenuItem(
				siteNavigationMenuItem.getSiteNavigationMenuItemId());

		Assert.assertEquals(
			SiteNavigationMenuItemTypeConstants.NODE,
			siteNavigationMenuItem.getType());

		for (Map.Entry<Long, String> entry :
				siteNavigationMenuItemIdsMap.entrySet()) {

			SiteNavigationMenuItem displayPageSiteNavigationMenuItem =
				_siteNavigationMenuItemLocalService.getSiteNavigationMenuItem(
					entry.getKey());

			Assert.assertEquals(
				entry.getValue(), displayPageSiteNavigationMenuItem.getType());
			Assert.assertNotEquals(
				"display_page", displayPageSiteNavigationMenuItem.getType());
		}
	}

	@Override
	protected CTModel<?> addCTModel() throws Exception {
		return SiteNavigationMenuItemTestUtil.addSiteNavigationMenuItem(
			_siteNavigationMenu);
	}

	@Override
	protected CTService<?> getCTService() {
		return _siteNavigationMenuItemLocalService;
	}

	@Override
	protected void runUpgrade() throws Exception {
		UpgradeProcess[] upgradeProcesses = UpgradeTestUtil.getUpgradeSteps(
			_upgradeStepRegistrator, new Version(2, 3, 0));

		for (UpgradeProcess upgradeProcess : upgradeProcesses) {
			upgradeProcess.upgrade();
		}

		_entityCache.clearCache();
	}

	@Override
	protected CTModel<?> updateCTModel(CTModel<?> ctModel) throws Exception {
		SiteNavigationMenuItem siteNavigationMenuItem =
			(SiteNavigationMenuItem)ctModel;

		siteNavigationMenuItem.setTypeSettings(
			UnicodePropertiesBuilder.put(
				RandomTestUtil.randomString(), RandomTestUtil.randomString()
			).buildString());

		return _siteNavigationMenuItemLocalService.updateSiteNavigationMenuItem(
			siteNavigationMenuItem);
	}

	@Inject(
		filter = "(&(component.name=com.liferay.site.navigation.internal.upgrade.registry.SiteNavigationServiceUpgradeStepRegistrator))"
	)
	private static UpgradeStepRegistrator _upgradeStepRegistrator;

	@Inject
	private EntityCache _entityCache;

	@DeleteAfterTestRun
	private Group _group;

	@Inject
	private InfoItemServiceRegistry _infoItemServiceRegistry;

	@Inject
	private Portal _portal;

	private ServiceContext _serviceContext;
	private SiteNavigationMenu _siteNavigationMenu;

	@Inject
	private SiteNavigationMenuItemLocalService
		_siteNavigationMenuItemLocalService;

}