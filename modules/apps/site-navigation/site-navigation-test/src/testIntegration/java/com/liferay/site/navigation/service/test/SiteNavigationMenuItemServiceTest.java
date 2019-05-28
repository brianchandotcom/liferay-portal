/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.site.navigation.service.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PersistenceTestRule;
import com.liferay.portal.test.rule.TransactionalTestRule;
import com.liferay.site.navigation.menu.item.layout.constants.SiteNavigationMenuItemTypeConstants;
import com.liferay.site.navigation.model.SiteNavigationMenu;
import com.liferay.site.navigation.model.SiteNavigationMenuItem;
import com.liferay.site.navigation.service.SiteNavigationMenuItemService;
import com.liferay.site.navigation.service.persistence.SiteNavigationMenuItemPersistence;
import com.liferay.site.navigation.util.SiteNavigationMenuItemTestUtil;
import com.liferay.site.navigation.util.SiteNavigationMenuTestUtil;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Kyle Miho
 */
@RunWith(Arquillian.class)
public class SiteNavigationMenuItemServiceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(
				Propagation.REQUIRED, "com.liferay.site.navigation.service"));

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		_siteNavigationMenu = SiteNavigationMenuTestUtil.addSiteNavigationMenu(
			_group);
	}

	@Test
	public void testAddSiteNavigationMenuItem() throws PortalException {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		SiteNavigationMenuItem siteNavigationMenuItem =
			_siteNavigationMenuItemService.addSiteNavigationMenuItem(
				_group.getGroupId(),
				_siteNavigationMenu.getSiteNavigationMenuId(), 0,
				SiteNavigationMenuItemTypeConstants.LAYOUT, StringPool.BLANK,
				serviceContext);

		SiteNavigationMenuItem persistedSiteNavigationMenuItem =
			_siteNavigationMenuItemPersistence.fetchByPrimaryKey(
				siteNavigationMenuItem.getSiteNavigationMenuItemId());

		Assert.assertEquals(
			siteNavigationMenuItem, persistedSiteNavigationMenuItem);
	}

	@Test
	public void testDeleteSiteNavigationMenuItem() throws PortalException {
		SiteNavigationMenuItem siteNavigationMenuItem =
			SiteNavigationMenuItemTestUtil.addSiteNavigationMenuItem(
				_siteNavigationMenu);

		_siteNavigationMenuItemService.deleteSiteNavigationMenuItem(
			siteNavigationMenuItem.getSiteNavigationMenuItemId());

		Assert.assertNull(
			_siteNavigationMenuItemPersistence.fetchByPrimaryKey(
				siteNavigationMenuItem.getSiteNavigationMenuItemId()));
	}

	@Test
	public void testDeleteSiteNavigationMenuItemsBySiteNavigationMenuId()
		throws PortalException {

		SiteNavigationMenuItemTestUtil.addSiteNavigationMenuItem(
			_siteNavigationMenu);

		SiteNavigationMenuItemTestUtil.addSiteNavigationMenuItem(
			_siteNavigationMenu);

		int originalSiteNavigationMenuItemsCount =
			_siteNavigationMenuItemPersistence.countBySiteNavigationMenuId(
				_siteNavigationMenu.getSiteNavigationMenuId());

		_siteNavigationMenuItemService.deleteSiteNavigationMenuItems(
			_siteNavigationMenu.getSiteNavigationMenuId());

		int actualSiteNavigationMenuItemsCount =
			_siteNavigationMenuItemPersistence.countBySiteNavigationMenuId(
				_siteNavigationMenu.getSiteNavigationMenuId());

		Assert.assertEquals(
			originalSiteNavigationMenuItemsCount - 2,
			actualSiteNavigationMenuItemsCount);
	}

	@Test
	public void testGetSiteNavigationMenuItems() throws PortalException {
		List<SiteNavigationMenuItem> originalSiteNavigationMenuItems =
			_siteNavigationMenuItemService.getSiteNavigationMenuItems(
				_siteNavigationMenu.getSiteNavigationMenuId());

		SiteNavigationMenuItemTestUtil.addSiteNavigationMenuItem(
			_siteNavigationMenu);

		SiteNavigationMenuItemTestUtil.addSiteNavigationMenuItem(
			_siteNavigationMenu);

		List<SiteNavigationMenuItem> actualSiteNavigationMenuItems =
			_siteNavigationMenuItemService.getSiteNavigationMenuItems(
				_siteNavigationMenu.getSiteNavigationMenuId());

		Assert.assertEquals(
			actualSiteNavigationMenuItems.toString(),
			originalSiteNavigationMenuItems.size() + 2,
			actualSiteNavigationMenuItems.size());
	}

	@Test
	public void testUpdateSiteNavigationMenuItemOrder() throws PortalException {
		SiteNavigationMenuItem siteNavigationMenuItem =
			SiteNavigationMenuItemTestUtil.addSiteNavigationMenuItem(
				_siteNavigationMenu, 0);

		SiteNavigationMenuItem childSiteNavigationMenuItem1 =
			SiteNavigationMenuItemTestUtil.addSiteNavigationMenuItem(
				_siteNavigationMenu,
				siteNavigationMenuItem.getSiteNavigationMenuItemId());

		SiteNavigationMenuItem childSiteNavigationMenuItem2 =
			SiteNavigationMenuItemTestUtil.addSiteNavigationMenuItem(
				_siteNavigationMenu,
				siteNavigationMenuItem.getSiteNavigationMenuItemId());

		_siteNavigationMenuItemService.updateSiteNavigationMenuItem(
			childSiteNavigationMenuItem2.getSiteNavigationMenuItemId(),
			siteNavigationMenuItem.getSiteNavigationMenuItemId(), 0);

		childSiteNavigationMenuItem1 =
			_siteNavigationMenuItemPersistence.fetchByPrimaryKey(
				childSiteNavigationMenuItem1.getSiteNavigationMenuItemId());

		Assert.assertEquals(1, childSiteNavigationMenuItem1.getOrder());
	}

	@Test
	public void testUpdateSiteNavigationMenuItemParent()
		throws PortalException {

		SiteNavigationMenuItem siteNavigationMenuItem1 =
			SiteNavigationMenuItemTestUtil.addSiteNavigationMenuItem(
				_siteNavigationMenu);

		SiteNavigationMenuItem childSiteNavigationMenuItem11 =
			SiteNavigationMenuItemTestUtil.addSiteNavigationMenuItem(
				_siteNavigationMenu,
				siteNavigationMenuItem1.getSiteNavigationMenuItemId());

		SiteNavigationMenuItem siteNavigationMenuItem2 =
			SiteNavigationMenuItemTestUtil.addSiteNavigationMenuItem(
				_siteNavigationMenu);

		_siteNavigationMenuItemService.updateSiteNavigationMenuItem(
			childSiteNavigationMenuItem11.getSiteNavigationMenuItemId(),
			siteNavigationMenuItem2.getSiteNavigationMenuItemId(), 0);

		childSiteNavigationMenuItem11 =
			_siteNavigationMenuItemPersistence.fetchByPrimaryKey(
				childSiteNavigationMenuItem11.getSiteNavigationMenuItemId());

		Assert.assertEquals(
			siteNavigationMenuItem2.getSiteNavigationMenuItemId(),
			childSiteNavigationMenuItem11.getParentSiteNavigationMenuItemId());
	}

	@DeleteAfterTestRun
	private Group _group;

	private SiteNavigationMenu _siteNavigationMenu;

	@Inject
	private SiteNavigationMenuItemPersistence
		_siteNavigationMenuItemPersistence;

	@Inject
	private SiteNavigationMenuItemService _siteNavigationMenuItemService;

}