/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.navigation.menu.item.layout.internal.type.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.service.LayoutService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.UnicodePropertiesBuilder;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.site.navigation.constants.SiteNavigationConstants;
import com.liferay.site.navigation.model.SiteNavigationMenu;
import com.liferay.site.navigation.service.SiteNavigationMenuItemLocalService;
import com.liferay.site.navigation.service.SiteNavigationMenuLocalService;

import java.util.HashMap;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Eudaldo Alonso
 */
@RunWith(Arquillian.class)
public class LayoutSiteNavigationMenuItemTypeTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();
	}

	@Test
	public void testAddToAutoMenuFalseToMenu() throws PortalException {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		_siteNavigationMenuLocalService.addSiteNavigationMenu(
			null, TestPropsValues.getUserId(), _group.getGroupId(), "Auto Menu",
			SiteNavigationConstants.TYPE_DEFAULT, true, serviceContext);

		_layoutService.addLayout(
			_group.getGroupId(), false, 0,
			HashMapBuilder.put(
				LocaleUtil.getSiteDefault(), "welcome"
			).build(),
			new HashMap<>(), new HashMap<>(), new HashMap<>(), new HashMap<>(),
			LayoutConstants.TYPE_PORTLET,
			UnicodePropertiesBuilder.put(
				"addToAutoMenus", Boolean.FALSE.toString()
			).buildString(),
			false, new HashMap<>(), serviceContext);

		Assert.assertEquals(
			0,
			_siteNavigationMenuItemLocalService.
				getSiteNavigationMenuItemsCount());
	}

	@Test
	public void testAddToAutoMenuFalseToPrimaryMenu() throws PortalException {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		_siteNavigationMenuLocalService.addSiteNavigationMenu(
			null, TestPropsValues.getUserId(), _group.getGroupId(),
			"Primary Menu", SiteNavigationConstants.TYPE_PRIMARY, true,
			serviceContext);

		_layoutService.addLayout(
			_group.getGroupId(), false, 0,
			HashMapBuilder.put(
				LocaleUtil.getSiteDefault(), "welcome"
			).build(),
			new HashMap<>(), new HashMap<>(), new HashMap<>(), new HashMap<>(),
			LayoutConstants.TYPE_PORTLET,
			UnicodePropertiesBuilder.put(
				"addToAutoMenus", Boolean.FALSE.toString()
			).buildString(),
			false, new HashMap<>(), serviceContext);

		Assert.assertEquals(
			0,
			_siteNavigationMenuItemLocalService.
				getSiteNavigationMenuItemsCount());
	}

	@Test
	public void testAddToAutoMenuTrueToMenu() throws PortalException {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		SiteNavigationMenu autoSiteNavigationMenu =
			_siteNavigationMenuLocalService.addSiteNavigationMenu(
				null, TestPropsValues.getUserId(), _group.getGroupId(),
				"Auto Menu", SiteNavigationConstants.TYPE_DEFAULT, true,
				serviceContext);

		SiteNavigationMenu primarySiteNavigationMenu =
			_siteNavigationMenuLocalService.addSiteNavigationMenu(
				null, TestPropsValues.getUserId(), _group.getGroupId(),
				"Primary Menu", SiteNavigationConstants.TYPE_PRIMARY, true,
				serviceContext);

		_layoutService.addLayout(
			_group.getGroupId(), false, 0,
			HashMapBuilder.put(
				LocaleUtil.getSiteDefault(), "welcome"
			).build(),
			new HashMap<>(), new HashMap<>(), new HashMap<>(), new HashMap<>(),
			LayoutConstants.TYPE_PORTLET,
			UnicodePropertiesBuilder.put(
				"siteNavigationMenuId",
				StringUtil.merge(
					new long[] {
						autoSiteNavigationMenu.getSiteNavigationMenuId(),
						primarySiteNavigationMenu.getSiteNavigationMenuId()
					})
			).buildString(),
			false, new HashMap<>(), serviceContext);

		Assert.assertEquals(
			2,
			_siteNavigationMenuItemLocalService.
				getSiteNavigationMenuItemsCount());
	}

	@Test
	public void testAddToAutoMenuTrueToPrimaryMenu() throws PortalException {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		SiteNavigationMenu siteNavigationMenu =
			_siteNavigationMenuLocalService.addSiteNavigationMenu(
				null, TestPropsValues.getUserId(), _group.getGroupId(),
				"Primary Menu", SiteNavigationConstants.TYPE_PRIMARY, true,
				serviceContext);

		_layoutService.addLayout(
			_group.getGroupId(), false, 0,
			HashMapBuilder.put(
				LocaleUtil.getSiteDefault(), "welcome"
			).build(),
			new HashMap<>(), new HashMap<>(), new HashMap<>(), new HashMap<>(),
			LayoutConstants.TYPE_PORTLET,
			UnicodePropertiesBuilder.put(
				"siteNavigationMenuId",
				StringUtil.merge(
					new long[] {siteNavigationMenu.getSiteNavigationMenuId()})
			).buildString(),
			false, new HashMap<>(), serviceContext);

		Assert.assertEquals(
			1,
			_siteNavigationMenuItemLocalService.
				getSiteNavigationMenuItemsCount());
	}

	@DeleteAfterTestRun
	private Group _group;

	@Inject
	private LayoutService _layoutService;

	@Inject
	private SiteNavigationMenuItemLocalService
		_siteNavigationMenuItemLocalService;

	@Inject
	private SiteNavigationMenuLocalService _siteNavigationMenuLocalService;

}