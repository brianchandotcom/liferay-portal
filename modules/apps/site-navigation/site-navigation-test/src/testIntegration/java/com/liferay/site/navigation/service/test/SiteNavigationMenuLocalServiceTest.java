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
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.ModelHintsUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.TransactionalTestRule;
import com.liferay.site.navigation.constants.SiteNavigationConstants;
import com.liferay.site.navigation.exception.DuplicateSiteNavigationMenuException;
import com.liferay.site.navigation.exception.SiteNavigationMenuNameException;
import com.liferay.site.navigation.model.SiteNavigationMenu;
import com.liferay.site.navigation.service.SiteNavigationMenuLocalServiceUtil;
import com.liferay.site.navigation.service.persistence.SiteNavigationMenuUtil;
import com.liferay.site.navigation.util.comparator.SiteNavigationMenuNameComparator;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Pavel Savinov
 */
@RunWith(Arquillian.class)
public class SiteNavigationMenuLocalServiceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			new TransactionalTestRule(
				Propagation.REQUIRED, "com.liferay.site.navigation.service"));

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();
	}

	@Test(expected = DuplicateSiteNavigationMenuException.class)
	public void testAddDuplicateSiteNavigationMenu() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		SiteNavigationMenuLocalServiceUtil.addSiteNavigationMenu(
			TestPropsValues.getUserId(), _group.getGroupId(), "Duplicate Name",
			SiteNavigationConstants.TYPE_DEFAULT, false, serviceContext);

		SiteNavigationMenuLocalServiceUtil.addSiteNavigationMenu(
			TestPropsValues.getUserId(), _group.getGroupId(), "Duplicate Name",
			SiteNavigationConstants.TYPE_DEFAULT, false, serviceContext);
	}

	@Test
	public void testAddSiteNavigationMenu() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		SiteNavigationMenu siteNavigationMenu =
			SiteNavigationMenuLocalServiceUtil.addSiteNavigationMenu(
				TestPropsValues.getUserId(), _group.getGroupId(),
				RandomTestUtil.randomString(),
				SiteNavigationConstants.TYPE_DEFAULT, false, serviceContext);

		SiteNavigationMenu existingSiteNavigationMenu = _getSiteNavigationMenu(
			siteNavigationMenu.getSiteNavigationMenuId());

		Assert.assertEquals(siteNavigationMenu, existingSiteNavigationMenu);
	}

	@Test(expected = SiteNavigationMenuNameException.class)
	public void testAddSiteNavigationMenuWithMaxLengthName() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		int nameMaxLength = ModelHintsUtil.getMaxLength(
			SiteNavigationMenu.class.getName(), "name");

		String nameExceedingMaxLength = RandomTestUtil.randomString(
			nameMaxLength + 1);

		SiteNavigationMenuLocalServiceUtil.addSiteNavigationMenu(
			TestPropsValues.getUserId(), _group.getGroupId(),
			nameExceedingMaxLength, SiteNavigationConstants.TYPE_DEFAULT, false,
			serviceContext);
	}

	@Test(expected = SiteNavigationMenuNameException.class)
	public void testAddSiteNavigationMenuWithEmptyName() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		String navigationMenuName = StringPool.BLANK;

		SiteNavigationMenuLocalServiceUtil.addSiteNavigationMenu(
			TestPropsValues.getUserId(), _group.getGroupId(),
			navigationMenuName, SiteNavigationConstants.TYPE_DEFAULT, false,
			serviceContext);
	}

	@Test
	public void testDeleteSiteNavigationMenu() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		SiteNavigationMenu siteNavigationMenu =
			SiteNavigationMenuLocalServiceUtil.addSiteNavigationMenu(
				TestPropsValues.getUserId(), _group.getGroupId(),
				RandomTestUtil.randomString(), serviceContext);

		SiteNavigationMenuLocalServiceUtil.deleteSiteNavigationMenu(
			siteNavigationMenu);

		Assert.assertNull(
			_getSiteNavigationMenu(
				siteNavigationMenu.getSiteNavigationMenuId()));
	}

	@Test
	public void testDeleteSiteNavigationMenuBySiteNavigationMenuId()
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		SiteNavigationMenu siteNavigationMenu =
			SiteNavigationMenuLocalServiceUtil.addSiteNavigationMenu(
				TestPropsValues.getUserId(), _group.getGroupId(),
				RandomTestUtil.randomString(), serviceContext);

		SiteNavigationMenuLocalServiceUtil.deleteSiteNavigationMenu(
			siteNavigationMenu.getSiteNavigationMenuId());

		Assert.assertNull(
			_getSiteNavigationMenu(
				siteNavigationMenu.getSiteNavigationMenuId()));
	}

	@Test
	public void testDeleteSiteNavigationMenus() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		SiteNavigationMenuLocalServiceUtil.addSiteNavigationMenu(
			TestPropsValues.getUserId(), _group.getGroupId(),
			RandomTestUtil.randomString(), serviceContext);

		SiteNavigationMenuLocalServiceUtil.addSiteNavigationMenu(
			TestPropsValues.getUserId(), _group.getGroupId(),
			RandomTestUtil.randomString(), serviceContext);

		List<SiteNavigationMenu> siteNavigationMenusOriginal =
			_getSiteNavigationMenus(_group.getGroupId());

		int siteNavigationMenusOriginalSize =
			siteNavigationMenusOriginal.size();

		SiteNavigationMenuLocalServiceUtil.deleteSiteNavigationMenus(
			_group.getGroupId());

		List<SiteNavigationMenu> siteNavigationMenusAfter =
			_getSiteNavigationMenus(_group.getGroupId());

		int siteNavigationMenusAfterSize = siteNavigationMenusAfter.size();

		Assert.assertEquals(
			siteNavigationMenusOriginalSize - 2, siteNavigationMenusAfterSize);
	}

	@Test
	public void testFetchPrimarySiteNavigationMenu() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		SiteNavigationMenu siteNavigationMenuPrimary =
			SiteNavigationMenuLocalServiceUtil.fetchPrimarySiteNavigationMenu(
				_group.getGroupId());

		Assert.assertNull(siteNavigationMenuPrimary);

		SiteNavigationMenuLocalServiceUtil.addSiteNavigationMenu(
			TestPropsValues.getUserId(), _group.getGroupId(),
			RandomTestUtil.randomString(), SiteNavigationConstants.TYPE_PRIMARY,
			false, serviceContext);

		siteNavigationMenuPrimary =
			SiteNavigationMenuLocalServiceUtil.fetchPrimarySiteNavigationMenu(
				_group.getGroupId());

		Assert.assertNotNull(siteNavigationMenuPrimary);
	}

	@Test
	public void testFetchSiteNavigationMenuByTypeSecondary() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		SiteNavigationMenu siteNavigationMenuSecondary =
			SiteNavigationMenuLocalServiceUtil.fetchSiteNavigationMenu(
				_group.getGroupId(), SiteNavigationConstants.TYPE_SECONDARY);

		Assert.assertNull(siteNavigationMenuSecondary);

		SiteNavigationMenuLocalServiceUtil.addSiteNavigationMenu(
			TestPropsValues.getUserId(), _group.getGroupId(),
			RandomTestUtil.randomString(),
			SiteNavigationConstants.TYPE_SECONDARY, false, serviceContext);

		siteNavigationMenuSecondary =
			SiteNavigationMenuLocalServiceUtil.fetchSiteNavigationMenu(
				_group.getGroupId(), SiteNavigationConstants.TYPE_SECONDARY);

		Assert.assertNotNull(siteNavigationMenuSecondary);
	}

	@Test
	public void testFetchSiteNavigationMenuByTypeSocial() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		SiteNavigationMenu siteNavigationMenuSocial =
			SiteNavigationMenuLocalServiceUtil.fetchSiteNavigationMenu(
				_group.getGroupId(), SiteNavigationConstants.TYPE_SOCIAL);

		Assert.assertNull(siteNavigationMenuSocial);

		SiteNavigationMenuLocalServiceUtil.addSiteNavigationMenu(
			TestPropsValues.getUserId(), _group.getGroupId(),
			RandomTestUtil.randomString(), SiteNavigationConstants.TYPE_SOCIAL,
			false, serviceContext);

		siteNavigationMenuSocial =
			SiteNavigationMenuLocalServiceUtil.fetchSiteNavigationMenu(
				_group.getGroupId(), SiteNavigationConstants.TYPE_SOCIAL);

		Assert.assertNotNull(siteNavigationMenuSocial);
	}

	@Test
	public void testGetAutoTrueSiteNavigationMenus() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		List<SiteNavigationMenu> siteNavigationMenusOriginal =
			SiteNavigationMenuLocalServiceUtil.getSiteNavigationMenus(
				_group.getGroupId());

		int siteNavigationMenusOriginalSize =
			siteNavigationMenusOriginal.size();

		SiteNavigationMenuLocalServiceUtil.addSiteNavigationMenu(
			TestPropsValues.getUserId(), _group.getGroupId(),
			RandomTestUtil.randomString(), SiteNavigationConstants.TYPE_DEFAULT,
			true, serviceContext);

		SiteNavigationMenuLocalServiceUtil.addSiteNavigationMenu(
			TestPropsValues.getUserId(), _group.getGroupId(),
			RandomTestUtil.randomString(), SiteNavigationConstants.TYPE_DEFAULT,
			true, serviceContext);

		SiteNavigationMenuLocalServiceUtil.addSiteNavigationMenu(
			TestPropsValues.getUserId(), _group.getGroupId(),
			RandomTestUtil.randomString(), SiteNavigationConstants.TYPE_DEFAULT,
			false, serviceContext);

		List<SiteNavigationMenu> siteNavigationMenusAfter =
			SiteNavigationMenuLocalServiceUtil.getAutoSiteNavigationMenus(
				_group.getGroupId());

		int siteNavigationMenusAfterSize = siteNavigationMenusAfter.size();

		Assert.assertEquals(
			siteNavigationMenusOriginalSize + 2, siteNavigationMenusAfterSize);
	}

	@Test
	public void testGetSiteNavigationMenus() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		List<SiteNavigationMenu> siteNavigationMenusOriginal =
			SiteNavigationMenuLocalServiceUtil.getSiteNavigationMenus(
				_group.getGroupId());

		int siteNavigationMenusOriginalSize =
			siteNavigationMenusOriginal.size();

		SiteNavigationMenuLocalServiceUtil.addSiteNavigationMenu(
			TestPropsValues.getUserId(), _group.getGroupId(),
			RandomTestUtil.randomString(), serviceContext);

		SiteNavigationMenuLocalServiceUtil.addSiteNavigationMenu(
			TestPropsValues.getUserId(), _group.getGroupId(),
			RandomTestUtil.randomString(), serviceContext);

		List<SiteNavigationMenu> siteNavigationMenusAfter =
			SiteNavigationMenuLocalServiceUtil.getSiteNavigationMenus(
				_group.getGroupId());

		int siteNavigationMenusAfterSize = siteNavigationMenusAfter.size();

		Assert.assertEquals(
			siteNavigationMenusOriginalSize + 2, siteNavigationMenusAfterSize);
	}

	@Test
	public void testGetSiteNavigationMenusCount() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		int startCount =
			SiteNavigationMenuLocalServiceUtil.getSiteNavigationMenusCount(
				_group.getGroupId());

		SiteNavigationMenuLocalServiceUtil.addSiteNavigationMenu(
			TestPropsValues.getUserId(), _group.getGroupId(),
			RandomTestUtil.randomString(), serviceContext);

		SiteNavigationMenuLocalServiceUtil.addSiteNavigationMenu(
			TestPropsValues.getUserId(), _group.getGroupId(),
			RandomTestUtil.randomString(), serviceContext);

		int endCount =
			SiteNavigationMenuLocalServiceUtil.getSiteNavigationMenusCount(
				_group.getGroupId());

		Assert.assertEquals(startCount + 2, endCount);
	}

	@Test
	public void testGetSiteNavigationMenusCountWithKeywords() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		int startCount =
			SiteNavigationMenuLocalServiceUtil.getSiteNavigationMenusCount(
				_group.getGroupId(), "Menu");

		SiteNavigationMenuLocalServiceUtil.addSiteNavigationMenu(
			TestPropsValues.getUserId(), _group.getGroupId(), "Menu 1",
			serviceContext);

		SiteNavigationMenuLocalServiceUtil.addSiteNavigationMenu(
			TestPropsValues.getUserId(), _group.getGroupId(), "Menu 2",
			serviceContext);

		SiteNavigationMenuLocalServiceUtil.addSiteNavigationMenu(
			TestPropsValues.getUserId(), _group.getGroupId(), "Test Name",
			serviceContext);

		int endCount =
			SiteNavigationMenuLocalServiceUtil.getSiteNavigationMenusCount(
				_group.getGroupId(), "Menu");

		Assert.assertEquals(startCount + 2, endCount);
	}

	@Test
	public void testGetSiteNavigationMenusWithOrderByComparator()
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		SiteNavigationMenu siteNavigationMenuAA =
			SiteNavigationMenuLocalServiceUtil.addSiteNavigationMenu(
				TestPropsValues.getUserId(), _group.getGroupId(),
				"AA Menu Name", serviceContext);

		SiteNavigationMenuLocalServiceUtil.addSiteNavigationMenu(
			TestPropsValues.getUserId(), _group.getGroupId(), "BB Menu Name",
			serviceContext);

		OrderByComparator ascendingComparator =
			new SiteNavigationMenuNameComparator(true);

		List<SiteNavigationMenu> siteNavigationMenusAscending =
			SiteNavigationMenuLocalServiceUtil.getSiteNavigationMenus(
				_group.getGroupId(), QueryUtil.ALL_POS, QueryUtil.ALL_POS,
				ascendingComparator);

		SiteNavigationMenu topSiteNavigationMenu =
			siteNavigationMenusAscending.get(0);

		Assert.assertEquals(topSiteNavigationMenu, siteNavigationMenuAA);

		OrderByComparator descendingComparator =
			new SiteNavigationMenuNameComparator(false);

		List<SiteNavigationMenu> siteNavigationMenusDescending =
			SiteNavigationMenuLocalServiceUtil.getSiteNavigationMenus(
				_group.getGroupId(), QueryUtil.ALL_POS, QueryUtil.ALL_POS,
				descendingComparator);

		SiteNavigationMenu bottomSiteNavigationMenu =
			siteNavigationMenusDescending.get(
				siteNavigationMenusDescending.size() - 1);

		Assert.assertEquals(bottomSiteNavigationMenu, siteNavigationMenuAA);
	}

	@Test
	public void testGetSiteNavigationMenusWithOrderByComparatorAndKeyword()
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		SiteNavigationMenu siteNavigationMenuBB =
			SiteNavigationMenuLocalServiceUtil.addSiteNavigationMenu(
				TestPropsValues.getUserId(), _group.getGroupId(),
				"BB Menu Name", serviceContext);

		SiteNavigationMenuLocalServiceUtil.addSiteNavigationMenu(
			TestPropsValues.getUserId(), _group.getGroupId(), "CC Menu Name",
			serviceContext);

		SiteNavigationMenuLocalServiceUtil.addSiteNavigationMenu(
			TestPropsValues.getUserId(), _group.getGroupId(), "AA",
			serviceContext);

		SiteNavigationMenuLocalServiceUtil.addSiteNavigationMenu(
			TestPropsValues.getUserId(), _group.getGroupId(), "DD",
			serviceContext);

		OrderByComparator ascendingComparator =
			new SiteNavigationMenuNameComparator(true);

		List<SiteNavigationMenu> siteNavigationMenusAscending =
			SiteNavigationMenuLocalServiceUtil.getSiteNavigationMenus(
				_group.getGroupId(), "Menu Name", QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, ascendingComparator);

		SiteNavigationMenu topSiteNavigationMenu =
			siteNavigationMenusAscending.get(0);

		Assert.assertEquals(topSiteNavigationMenu, siteNavigationMenuBB);

		OrderByComparator descendingComparator =
			new SiteNavigationMenuNameComparator(false);

		List<SiteNavigationMenu> siteNavigationMenusDescending =
			SiteNavigationMenuLocalServiceUtil.getSiteNavigationMenus(
				_group.getGroupId(), "Menu Name", QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, descendingComparator);

		SiteNavigationMenu bottomSiteNavigationMenu =
			siteNavigationMenusDescending.get(
				siteNavigationMenusDescending.size() - 1);

		Assert.assertEquals(bottomSiteNavigationMenu, siteNavigationMenuBB);
	}

	@Test
	public void testSiteNavigationMenuAutoFalseByDefault() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		SiteNavigationMenu siteNavigationMenu =
			SiteNavigationMenuLocalServiceUtil.addSiteNavigationMenu(
				TestPropsValues.getUserId(), _group.getGroupId(),
				RandomTestUtil.randomString(),
				SiteNavigationConstants.TYPE_DEFAULT, serviceContext);

		siteNavigationMenu =
			_getSiteNavigationMenu(
				siteNavigationMenu.getSiteNavigationMenuId());

		Assert.assertEquals(siteNavigationMenu.isAuto(), false);
	}

	@Test
	public void testSiteNavigationMenuDefaultType() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		SiteNavigationMenu siteNavigationMenu =
			SiteNavigationMenuLocalServiceUtil.addSiteNavigationMenu(
				TestPropsValues.getUserId(), _group.getGroupId(),
				RandomTestUtil.randomString(), serviceContext);

		siteNavigationMenu =
			_getSiteNavigationMenu(
				siteNavigationMenu.getSiteNavigationMenuId());

		Assert.assertEquals(
			siteNavigationMenu.getType(), SiteNavigationConstants.TYPE_DEFAULT);
	}

	@Test
	public void testUpdateOldSiteNavigationMenuType() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		SiteNavigationMenu siteNavigationMenu =
			SiteNavigationMenuLocalServiceUtil.addSiteNavigationMenu(
				TestPropsValues.getUserId(), _group.getGroupId(),
				RandomTestUtil.randomString(),
				SiteNavigationConstants.TYPE_PRIMARY, false, serviceContext);

		SiteNavigationMenuLocalServiceUtil.addSiteNavigationMenu(
			TestPropsValues.getUserId(), _group.getGroupId(),
			RandomTestUtil.randomString(), SiteNavigationConstants.TYPE_PRIMARY,
			false, serviceContext);

		siteNavigationMenu = _getSiteNavigationMenu(
			siteNavigationMenu.getSiteNavigationMenuId());

		Assert.assertEquals(
			SiteNavigationConstants.TYPE_DEFAULT, siteNavigationMenu.getType());
	}

	@Test
	public void testUpdateSiteNavigationMenuAuto() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		SiteNavigationMenu originalSiteNavigationMenu =
			SiteNavigationMenuLocalServiceUtil.addSiteNavigationMenu(
				TestPropsValues.getUserId(), _group.getGroupId(),
				RandomTestUtil.randomString(),
				SiteNavigationConstants.TYPE_DEFAULT, false, serviceContext);

		SiteNavigationMenuLocalServiceUtil.updateSiteNavigationMenu(
			originalSiteNavigationMenu.getUserId(),
			originalSiteNavigationMenu.getSiteNavigationMenuId(),
			originalSiteNavigationMenu.getGroupId(),
			originalSiteNavigationMenu.getName(),
			originalSiteNavigationMenu.getType(), true);

		SiteNavigationMenu updatedSiteNavigationMenu = _getSiteNavigationMenu(
			originalSiteNavigationMenu.getSiteNavigationMenuId());

		Assert.assertTrue(updatedSiteNavigationMenu.isAuto());
	}

	@Test
	public void testUpdateSiteNavigationMenuName() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		SiteNavigationMenu originalSiteNavigationMenu =
			SiteNavigationMenuLocalServiceUtil.addSiteNavigationMenu(
				TestPropsValues.getUserId(), _group.getGroupId(),
				"Original Name", serviceContext);

		SiteNavigationMenuLocalServiceUtil.updateSiteNavigationMenu(
			originalSiteNavigationMenu.getUserId(),
			originalSiteNavigationMenu.getSiteNavigationMenuId(),
			"Updated Name", serviceContext);

		SiteNavigationMenu updatedSiteNavigationMenu = _getSiteNavigationMenu(
			originalSiteNavigationMenu.getSiteNavigationMenuId());

		Assert.assertEquals(
			"Updated Name", updatedSiteNavigationMenu.getName());
	}

	@Test
	public void testUpdateSiteNavigationMenuType() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		SiteNavigationMenu originalSiteNavigationMenu =
			SiteNavigationMenuLocalServiceUtil.addSiteNavigationMenu(
				TestPropsValues.getUserId(), _group.getGroupId(),
				RandomTestUtil.randomString(),
				SiteNavigationConstants.TYPE_DEFAULT, false, serviceContext);

		SiteNavigationMenuLocalServiceUtil.updateSiteNavigationMenu(
			originalSiteNavigationMenu.getUserId(),
			originalSiteNavigationMenu.getSiteNavigationMenuId(),
			originalSiteNavigationMenu.getGroupId(),
			originalSiteNavigationMenu.getName(),
			SiteNavigationConstants.TYPE_SECONDARY,
			originalSiteNavigationMenu.isAuto());

		SiteNavigationMenu updatedSiteNavigationMenu = _getSiteNavigationMenu(
			originalSiteNavigationMenu.getSiteNavigationMenuId());

		Assert.assertEquals(
			SiteNavigationConstants.TYPE_SECONDARY,
			updatedSiteNavigationMenu.getType());
	}

	private SiteNavigationMenu _getSiteNavigationMenu(
		long siteNavigationMenuId) {

		return SiteNavigationMenuUtil.fetchByPrimaryKey(siteNavigationMenuId);
	}

	private List<SiteNavigationMenu> _getSiteNavigationMenus(long groupId) {
		return SiteNavigationMenuUtil.findByGroupId(groupId);
	}

	@DeleteAfterTestRun
	private Group _group;

}