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
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.site.navigation.exception.InvalidSiteNavigationMenuItemOrderException;
import com.liferay.site.navigation.model.SiteNavigationMenu;
import com.liferay.site.navigation.model.SiteNavigationMenuItem;
import com.liferay.site.navigation.service.SiteNavigationMenuItemLocalServiceUtil;
import com.liferay.site.navigation.service.SiteNavigationMenuLocalServiceUtil;

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
public class SiteNavigationMenuItemServiceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		_siteNavigationMenu =
			SiteNavigationMenuLocalServiceUtil.addSiteNavigationMenu(
				TestPropsValues.getUserId(), _group.getGroupId(), "Menu",
				serviceContext);
	}

	@Test(expected = InvalidSiteNavigationMenuItemOrderException.class)
	public void testInvalidParentSiteNavigationMenuItem()
		throws PortalException {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		SiteNavigationMenuItem siteNavigationMenuItem =
			SiteNavigationMenuItemLocalServiceUtil.addSiteNavigationMenuItem(
				TestPropsValues.getUserId(), _group.getGroupId(),
				_siteNavigationMenu.getSiteNavigationMenuId(), 0, "test",
				StringPool.BLANK, serviceContext);

		SiteNavigationMenuItem childSiteNavigationMenuItem =
			SiteNavigationMenuItemLocalServiceUtil.addSiteNavigationMenuItem(
				TestPropsValues.getUserId(), _group.getGroupId(),
				_siteNavigationMenu.getSiteNavigationMenuId(),
				siteNavigationMenuItem.getSiteNavigationMenuItemId(), "test",
				StringPool.BLANK, serviceContext);

		SiteNavigationMenuItemLocalServiceUtil.updateSiteNavigationMenuItem(
			siteNavigationMenuItem.getSiteNavigationMenuItemId(),
			childSiteNavigationMenuItem.getSiteNavigationMenuItemId(),
			siteNavigationMenuItem.getOrder());
	}

	@Test
	public void testSiteNavigationMenuItemOrder() throws PortalException {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		SiteNavigationMenuItemLocalServiceUtil.addSiteNavigationMenuItem(
			TestPropsValues.getUserId(), _group.getGroupId(),
			_siteNavigationMenu.getSiteNavigationMenuId(), 0, "test",
			StringPool.BLANK, serviceContext);

		SiteNavigationMenuItem siteNavigationMenuItem =
			SiteNavigationMenuItemLocalServiceUtil.addSiteNavigationMenuItem(
				TestPropsValues.getUserId(), _group.getGroupId(),
				_siteNavigationMenu.getSiteNavigationMenuId(), 0, "test",
				StringPool.BLANK, serviceContext);

		Assert.assertEquals(1, siteNavigationMenuItem.getOrder());
	}

	@Test
	public void testUpdateSiteNavigationMenuItemOrder() throws PortalException {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		SiteNavigationMenuItem siteNavigationMenuItem =
			SiteNavigationMenuItemLocalServiceUtil.addSiteNavigationMenuItem(
				TestPropsValues.getUserId(), _group.getGroupId(),
				_siteNavigationMenu.getSiteNavigationMenuId(), 0, "test",
				StringPool.BLANK, serviceContext);

		SiteNavigationMenuItem childSiteNavigationMenuItem1 =
			SiteNavigationMenuItemLocalServiceUtil.addSiteNavigationMenuItem(
				TestPropsValues.getUserId(), _group.getGroupId(),
				_siteNavigationMenu.getSiteNavigationMenuId(),
				siteNavigationMenuItem.getSiteNavigationMenuItemId(), "test",
				StringPool.BLANK, serviceContext);

		SiteNavigationMenuItem childSiteNavigationMenuItem2 =
			SiteNavigationMenuItemLocalServiceUtil.addSiteNavigationMenuItem(
				TestPropsValues.getUserId(), _group.getGroupId(),
				_siteNavigationMenu.getSiteNavigationMenuId(),
				siteNavigationMenuItem.getSiteNavigationMenuItemId(), "test",
				StringPool.BLANK, serviceContext);

		SiteNavigationMenuItemLocalServiceUtil.updateSiteNavigationMenuItem(
			childSiteNavigationMenuItem2.getSiteNavigationMenuItemId(),
			siteNavigationMenuItem.getSiteNavigationMenuItemId(), 0);

		childSiteNavigationMenuItem1 =
			SiteNavigationMenuItemLocalServiceUtil.fetchSiteNavigationMenuItem(
				childSiteNavigationMenuItem1.getSiteNavigationMenuItemId());

		Assert.assertEquals(1, childSiteNavigationMenuItem1.getOrder());
	}

	@Test
	public void testUpdateSiteNavigationMenuItemParent()
		throws PortalException {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		SiteNavigationMenuItem siteNavigationMenuItem1 =
			SiteNavigationMenuItemLocalServiceUtil.addSiteNavigationMenuItem(
				TestPropsValues.getUserId(), _group.getGroupId(),
				_siteNavigationMenu.getSiteNavigationMenuId(), 0, "test",
				StringPool.BLANK, serviceContext);

		SiteNavigationMenuItem childSiteNavigationMenuItem11 =
			SiteNavigationMenuItemLocalServiceUtil.addSiteNavigationMenuItem(
				TestPropsValues.getUserId(), _group.getGroupId(),
				_siteNavigationMenu.getSiteNavigationMenuId(),
				siteNavigationMenuItem1.getSiteNavigationMenuItemId(), "test",
				StringPool.BLANK, serviceContext);

		SiteNavigationMenuItem childSiteNavigationMenuItem12 =
			SiteNavigationMenuItemLocalServiceUtil.addSiteNavigationMenuItem(
				TestPropsValues.getUserId(), _group.getGroupId(),
				_siteNavigationMenu.getSiteNavigationMenuId(),
				siteNavigationMenuItem1.getSiteNavigationMenuItemId(), "test",
				StringPool.BLANK, serviceContext);

		SiteNavigationMenuItem siteNavigationMenuItem2 =
			SiteNavigationMenuItemLocalServiceUtil.addSiteNavigationMenuItem(
				TestPropsValues.getUserId(), _group.getGroupId(),
				_siteNavigationMenu.getSiteNavigationMenuId(), 0, "test",
				StringPool.BLANK, serviceContext);

		SiteNavigationMenuItem childSiteNavigationMenuItem21 =
			SiteNavigationMenuItemLocalServiceUtil.addSiteNavigationMenuItem(
				TestPropsValues.getUserId(), _group.getGroupId(),
				_siteNavigationMenu.getSiteNavigationMenuId(),
				siteNavigationMenuItem2.getSiteNavigationMenuItemId(), "test",
				StringPool.BLANK, serviceContext);

		SiteNavigationMenuItemLocalServiceUtil.updateSiteNavigationMenuItem(
			childSiteNavigationMenuItem11.getSiteNavigationMenuItemId(),
			siteNavigationMenuItem2.getSiteNavigationMenuItemId(), 0);

		childSiteNavigationMenuItem21 =
			SiteNavigationMenuItemLocalServiceUtil.fetchSiteNavigationMenuItem(
				childSiteNavigationMenuItem21.getSiteNavigationMenuItemId());

		Assert.assertEquals(1, childSiteNavigationMenuItem21.getOrder());

		childSiteNavigationMenuItem12 =
			SiteNavigationMenuItemLocalServiceUtil.fetchSiteNavigationMenuItem(
				childSiteNavigationMenuItem12.getSiteNavigationMenuItemId());

		Assert.assertEquals(0, childSiteNavigationMenuItem12.getOrder());
	}

	@DeleteAfterTestRun
	private Group _group;

	private SiteNavigationMenu _siteNavigationMenu;

}