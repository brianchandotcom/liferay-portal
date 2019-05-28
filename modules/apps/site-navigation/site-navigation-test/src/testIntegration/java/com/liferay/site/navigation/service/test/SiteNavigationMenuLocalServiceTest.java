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
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.TransactionalTestRule;
import com.liferay.site.navigation.constants.SiteNavigationConstants;
import com.liferay.site.navigation.exception.DuplicateSiteNavigationMenuException;
import com.liferay.site.navigation.exception.SiteNavigationMenuNameException;
import com.liferay.site.navigation.model.SiteNavigationMenu;
import com.liferay.site.navigation.service.SiteNavigationMenuLocalService;
import com.liferay.site.navigation.service.persistence.SiteNavigationMenuPersistence;
import com.liferay.site.navigation.util.SiteNavigationMenuTestUtil;
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

	@Test
	public void testAddAutoSiteNavigationMenu() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		SiteNavigationMenu siteNavigationMenu =
			_siteNavigationMenuLocalService.addSiteNavigationMenu(
				TestPropsValues.getUserId(), _group.getGroupId(),
				RandomTestUtil.randomString(),
				SiteNavigationConstants.TYPE_DEFAULT, false, serviceContext);

		SiteNavigationMenu persistedSiteNavigationMenu =
			_siteNavigationMenuPersistence.fetchByPrimaryKey(
				siteNavigationMenu.getSiteNavigationMenuId());

		Assert.assertEquals(siteNavigationMenu, persistedSiteNavigationMenu);
	}

	@Test(expected = DuplicateSiteNavigationMenuException.class)
	public void testAddDuplicateSiteNavigationMenu() throws Exception {
		String navigationMenuName = "Duplicate Name";

		SiteNavigationMenuTestUtil.addSiteNavigationMenu(
			_group, navigationMenuName);
		SiteNavigationMenuTestUtil.addSiteNavigationMenu(
			_group, navigationMenuName);
	}

	@Test
	public void testAddSiteNavigationMenu() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		SiteNavigationMenu siteNavigationMenu =
			_siteNavigationMenuLocalService.addSiteNavigationMenu(
				TestPropsValues.getUserId(), _group.getGroupId(),
				RandomTestUtil.randomString(), serviceContext);

		SiteNavigationMenu persistedSiteNavigationMenu =
			_siteNavigationMenuPersistence.fetchByPrimaryKey(
				siteNavigationMenu.getSiteNavigationMenuId());

		Assert.assertEquals(siteNavigationMenu, persistedSiteNavigationMenu);
	}

	@Test
	public void testAddSiteNavigationMenuByType() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		SiteNavigationMenu siteNavigationMenu =
			_siteNavigationMenuLocalService.addSiteNavigationMenu(
				TestPropsValues.getUserId(), _group.getGroupId(),
				RandomTestUtil.randomString(),
				SiteNavigationConstants.TYPE_DEFAULT, serviceContext);

		SiteNavigationMenu persistedSiteNavigationMenu =
			_siteNavigationMenuPersistence.fetchByPrimaryKey(
				siteNavigationMenu.getSiteNavigationMenuId());

		Assert.assertEquals(siteNavigationMenu, persistedSiteNavigationMenu);
	}

	@Test(expected = SiteNavigationMenuNameException.class)
	public void testAddSiteNavigationMenuWithEmptyName() throws Exception {
		SiteNavigationMenuTestUtil.addSiteNavigationMenu(
			_group, StringPool.BLANK);
	}

	@Test(expected = SiteNavigationMenuNameException.class)
	public void testAddSiteNavigationMenuWithMaxLengthName() throws Exception {
		int nameMaxLength = ModelHintsUtil.getMaxLength(
			SiteNavigationMenu.class.getName(), "name");

		String nameExceedingMaxLength = RandomTestUtil.randomString(
			nameMaxLength + 1);

		SiteNavigationMenuTestUtil.addSiteNavigationMenu(
			_group, nameExceedingMaxLength);
	}

	@Test(expected = SiteNavigationMenuNameException.class)
	public void testAddSiteNavigationMenuWithNullName() throws Exception {
		SiteNavigationMenuTestUtil.addSiteNavigationMenu(_group, null);
	}

	@Test
	public void testDeleteSiteNavigationMenu() throws Exception {
		SiteNavigationMenu siteNavigationMenu =
			SiteNavigationMenuTestUtil.addSiteNavigationMenu(_group);

		_siteNavigationMenuLocalService.deleteSiteNavigationMenu(
			siteNavigationMenu);

		Assert.assertNull(
			_siteNavigationMenuPersistence.fetchByPrimaryKey(
				siteNavigationMenu.getSiteNavigationMenuId()));
	}

	@Test
	public void testDeleteSiteNavigationMenuBySiteNavigationMenuId()
		throws Exception {

		SiteNavigationMenu siteNavigationMenu =
			SiteNavigationMenuTestUtil.addSiteNavigationMenu(_group);

		_siteNavigationMenuLocalService.deleteSiteNavigationMenu(
			siteNavigationMenu.getSiteNavigationMenuId());

		Assert.assertNull(
			_siteNavigationMenuPersistence.fetchByPrimaryKey(
				siteNavigationMenu.getSiteNavigationMenuId()));
	}

	@Test
	public void testDeleteSiteNavigationMenus() throws Exception {
		SiteNavigationMenuTestUtil.addSiteNavigationMenu(_group);

		SiteNavigationMenuTestUtil.addSiteNavigationMenu(_group);

		List<SiteNavigationMenu> originalSiteNavigationMenus =
			_siteNavigationMenuPersistence.findByGroupId(_group.getGroupId());

		_siteNavigationMenuLocalService.deleteSiteNavigationMenus(
			_group.getGroupId());

		List<SiteNavigationMenu> actualSiteNavigationMenus =
			_siteNavigationMenuPersistence.findByGroupId(_group.getGroupId());

		Assert.assertEquals(
			actualSiteNavigationMenus.toString(),
			originalSiteNavigationMenus.size() - 2,
			actualSiteNavigationMenus.size());
	}

	@Test
	public void testFetchPrimarySiteNavigationMenu() throws Exception {
		SiteNavigationMenu siteNavigationMenu =
			_siteNavigationMenuLocalService.fetchPrimarySiteNavigationMenu(
				_group.getGroupId());

		Assert.assertNull(siteNavigationMenu);

		SiteNavigationMenuTestUtil.addSiteNavigationMenu(
			_group, SiteNavigationConstants.TYPE_PRIMARY);

		siteNavigationMenu =
			_siteNavigationMenuLocalService.fetchPrimarySiteNavigationMenu(
				_group.getGroupId());

		Assert.assertNotNull(siteNavigationMenu);
	}

	@Test
	public void testFetchSiteNavigationMenuByTypeSecondary() throws Exception {
		SiteNavigationMenu siteNavigationMenu =
			_siteNavigationMenuLocalService.fetchSiteNavigationMenu(
				_group.getGroupId(), SiteNavigationConstants.TYPE_SECONDARY);

		Assert.assertNull(siteNavigationMenu);

		SiteNavigationMenuTestUtil.addSiteNavigationMenu(
			_group, SiteNavigationConstants.TYPE_SECONDARY);

		siteNavigationMenu =
			_siteNavigationMenuLocalService.fetchSiteNavigationMenu(
				_group.getGroupId(), SiteNavigationConstants.TYPE_SECONDARY);

		Assert.assertNotNull(siteNavigationMenu);
	}

	@Test
	public void testFetchSiteNavigationMenuByTypeSocial() throws Exception {
		SiteNavigationMenu siteNavigationMenu =
			_siteNavigationMenuLocalService.fetchSiteNavigationMenu(
				_group.getGroupId(), SiteNavigationConstants.TYPE_SOCIAL);

		Assert.assertNull(siteNavigationMenu);

		SiteNavigationMenuTestUtil.addSiteNavigationMenu(
			_group, SiteNavigationConstants.TYPE_SOCIAL);

		siteNavigationMenu =
			_siteNavigationMenuLocalService.fetchSiteNavigationMenu(
				_group.getGroupId(), SiteNavigationConstants.TYPE_SOCIAL);

		Assert.assertNotNull(siteNavigationMenu);
	}

	@Test
	public void testGetAutoTrueSiteNavigationMenus() throws Exception {
		List<SiteNavigationMenu> originalSiteNavigationMenus =
			_siteNavigationMenuLocalService.getAutoSiteNavigationMenus(
				_group.getGroupId());

		SiteNavigationMenuTestUtil.addSiteNavigationMenu(_group, true);
		SiteNavigationMenuTestUtil.addSiteNavigationMenu(_group, true);
		SiteNavigationMenuTestUtil.addSiteNavigationMenu(_group, false);

		List<SiteNavigationMenu> actualSiteNavigationMenus =
			_siteNavigationMenuLocalService.getAutoSiteNavigationMenus(
				_group.getGroupId());

		Assert.assertEquals(
			actualSiteNavigationMenus.toString(),
			originalSiteNavigationMenus.size() + 2,
			actualSiteNavigationMenus.size());
	}

	@Test
	public void testGetSiteNavigationMenus() throws Exception {
		List<SiteNavigationMenu> originalSiteNavigationMenus =
			_siteNavigationMenuLocalService.getSiteNavigationMenus(
				_group.getGroupId());

		SiteNavigationMenuTestUtil.addSiteNavigationMenu(_group);
		SiteNavigationMenuTestUtil.addSiteNavigationMenu(_group);

		List<SiteNavigationMenu> actualSiteNavigationMenus =
			_siteNavigationMenuLocalService.getSiteNavigationMenus(
				_group.getGroupId());

		Assert.assertEquals(
			actualSiteNavigationMenus.toString(),
			originalSiteNavigationMenus.size() + 2,
			actualSiteNavigationMenus.size());
	}

	@Test
	public void testGetSiteNavigationMenusCount() throws Exception {
		int originalSiteNavigationMenusCount =
			_siteNavigationMenuLocalService.getSiteNavigationMenusCount(
				_group.getGroupId());

		SiteNavigationMenuTestUtil.addSiteNavigationMenu(_group);
		SiteNavigationMenuTestUtil.addSiteNavigationMenu(_group);

		int actualSiteNavigationMenusCount =
			_siteNavigationMenuLocalService.getSiteNavigationMenusCount(
				_group.getGroupId());

		Assert.assertEquals(
			originalSiteNavigationMenusCount + 2,
			actualSiteNavigationMenusCount);
	}

	@Test
	public void testGetSiteNavigationMenusCountWithKeywords() throws Exception {
		int originalSiteNavigationMenusCount =
			_siteNavigationMenuLocalService.getSiteNavigationMenusCount(
				_group.getGroupId(), "Menu");

		SiteNavigationMenuTestUtil.addSiteNavigationMenu(_group, "Menu 1");
		SiteNavigationMenuTestUtil.addSiteNavigationMenu(_group, "Menu 2");
		SiteNavigationMenuTestUtil.addSiteNavigationMenu(_group, "Test Name");

		int actualSiteNavigationMenusCount =
			_siteNavigationMenuLocalService.getSiteNavigationMenusCount(
				_group.getGroupId(), "Menu");

		Assert.assertEquals(
			originalSiteNavigationMenusCount + 2,
			actualSiteNavigationMenusCount);
	}

	@Test
	public void testGetSiteNavigationMenusWithOrderByComparatorAndKeywordAsc()
		throws Exception {

		SiteNavigationMenu siteNavigationMenu =
			SiteNavigationMenuTestUtil.addSiteNavigationMenu(
				_group, "bb Menu Name");

		SiteNavigationMenuTestUtil.addSiteNavigationMenu(
			_group, "cc Menu Name");
		SiteNavigationMenuTestUtil.addSiteNavigationMenu(_group, "aa");
		SiteNavigationMenuTestUtil.addSiteNavigationMenu(_group, "dd");

		OrderByComparator orderByComparator =
			new SiteNavigationMenuNameComparator(true);

		List<SiteNavigationMenu> siteNavigationMenus =
			_siteNavigationMenuLocalService.getSiteNavigationMenus(
				_group.getGroupId(), "Menu Name", QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, orderByComparator);

		SiteNavigationMenu topSiteNavigationMenu = siteNavigationMenus.get(0);

		Assert.assertEquals(topSiteNavigationMenu, siteNavigationMenu);
	}

	@Test
	public void testGetSiteNavigationMenusWithOrderByComparatorAndKeywordDesc()
		throws Exception {

		SiteNavigationMenu siteNavigationMenu =
			SiteNavigationMenuTestUtil.addSiteNavigationMenu(
				_group, "bb Menu Name");

		SiteNavigationMenuTestUtil.addSiteNavigationMenu(
			_group, "cc Menu Name");

		SiteNavigationMenuTestUtil.addSiteNavigationMenu(_group, "aa");

		SiteNavigationMenuTestUtil.addSiteNavigationMenu(_group, "dd");

		OrderByComparator orderByComparator =
			new SiteNavigationMenuNameComparator(false);

		List<SiteNavigationMenu> siteNavigationMenus =
			_siteNavigationMenuLocalService.getSiteNavigationMenus(
				_group.getGroupId(), "Menu Name", QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, orderByComparator);

		SiteNavigationMenu bottomSiteNavigationMenu = siteNavigationMenus.get(
			siteNavigationMenus.size() - 1);

		Assert.assertEquals(bottomSiteNavigationMenu, siteNavigationMenu);
	}

	@Test
	public void testGetSiteNavigationMenusWithOrderByComparatorAsc()
		throws Exception {

		SiteNavigationMenu siteNavigationMenu =
			SiteNavigationMenuTestUtil.addSiteNavigationMenu(
				_group, "aa Menu Name");

		SiteNavigationMenuTestUtil.addSiteNavigationMenu(
			_group, "bb Menu Name");

		OrderByComparator orderByComparator =
			new SiteNavigationMenuNameComparator(true);

		List<SiteNavigationMenu> siteNavigationMenus =
			_siteNavigationMenuLocalService.getSiteNavigationMenus(
				_group.getGroupId(), QueryUtil.ALL_POS, QueryUtil.ALL_POS,
				orderByComparator);

		SiteNavigationMenu topSiteNavigationMenu = siteNavigationMenus.get(0);

		Assert.assertEquals(topSiteNavigationMenu, siteNavigationMenu);
	}

	@Test
	public void testGetSiteNavigationMenusWithOrderByComparatorDesc()
		throws Exception {

		SiteNavigationMenu siteNavigationMenu =
			SiteNavigationMenuTestUtil.addSiteNavigationMenu(
				_group, "aa Menu Name");

		SiteNavigationMenuTestUtil.addSiteNavigationMenu(
			_group, "bb Menu Name");

		OrderByComparator orderByComparator =
			new SiteNavigationMenuNameComparator(false);

		List<SiteNavigationMenu> descSiteNavigationMenus =
			_siteNavigationMenuLocalService.getSiteNavigationMenus(
				_group.getGroupId(), QueryUtil.ALL_POS, QueryUtil.ALL_POS,
				orderByComparator);

		SiteNavigationMenu bottomSiteNavigationMenu =
			descSiteNavigationMenus.get(descSiteNavigationMenus.size() - 1);

		Assert.assertEquals(bottomSiteNavigationMenu, siteNavigationMenu);
	}

	@Test
	public void testSiteNavigationMenuAutoFalseByDefault() throws Exception {
		SiteNavigationMenu siteNavigationMenu =
			SiteNavigationMenuTestUtil.addSiteNavigationMenu(_group);

		siteNavigationMenu = _siteNavigationMenuPersistence.fetchByPrimaryKey(
			siteNavigationMenu.getSiteNavigationMenuId());

		Assert.assertEquals(siteNavigationMenu.isAuto(), false);
	}

	@Test
	public void testSiteNavigationMenuDefaultType() throws Exception {
		SiteNavigationMenu siteNavigationMenu =
			SiteNavigationMenuTestUtil.addSiteNavigationMenu(_group);

		siteNavigationMenu = _siteNavigationMenuPersistence.fetchByPrimaryKey(
			siteNavigationMenu.getSiteNavigationMenuId());

		Assert.assertEquals(
			siteNavigationMenu.getType(), SiteNavigationConstants.TYPE_DEFAULT);
	}

	@Test
	public void testUpdateOldSiteNavigationMenuType() throws Exception {
		SiteNavigationMenu siteNavigationMenu =
			SiteNavigationMenuTestUtil.addSiteNavigationMenu(
				_group, SiteNavigationConstants.TYPE_PRIMARY);

		SiteNavigationMenuTestUtil.addSiteNavigationMenu(
			_group, SiteNavigationConstants.TYPE_PRIMARY);

		siteNavigationMenu = _siteNavigationMenuPersistence.fetchByPrimaryKey(
			siteNavigationMenu.getSiteNavigationMenuId());

		Assert.assertEquals(
			SiteNavigationConstants.TYPE_DEFAULT, siteNavigationMenu.getType());
	}

	@Test
	public void testUpdateSiteNavigationMenuAuto() throws Exception {
		SiteNavigationMenu originalSiteNavigationMenu =
			SiteNavigationMenuTestUtil.addSiteNavigationMenu(_group, false);

		_siteNavigationMenuLocalService.updateSiteNavigationMenu(
			originalSiteNavigationMenu.getUserId(),
			originalSiteNavigationMenu.getSiteNavigationMenuId(),
			originalSiteNavigationMenu.getGroupId(),
			originalSiteNavigationMenu.getName(),
			originalSiteNavigationMenu.getType(), true);

		SiteNavigationMenu persistedSiteNavigationMenu =
			_siteNavigationMenuPersistence.fetchByPrimaryKey(
				originalSiteNavigationMenu.getSiteNavigationMenuId());

		Assert.assertTrue(persistedSiteNavigationMenu.isAuto());
	}

	@Test
	public void testUpdateSiteNavigationMenuName() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		SiteNavigationMenu originalSiteNavigationMenu =
			SiteNavigationMenuTestUtil.addSiteNavigationMenu(
				_group, "Original Name");

		_siteNavigationMenuLocalService.updateSiteNavigationMenu(
			originalSiteNavigationMenu.getUserId(),
			originalSiteNavigationMenu.getSiteNavigationMenuId(),
			"Updated Name", serviceContext);

		SiteNavigationMenu persistedSiteNavigationMenu =
			_siteNavigationMenuPersistence.fetchByPrimaryKey(
				originalSiteNavigationMenu.getSiteNavigationMenuId());

		Assert.assertEquals(
			"Updated Name", persistedSiteNavigationMenu.getName());
	}

	@Test
	public void testUpdateSiteNavigationMenuType() throws Exception {
		SiteNavigationMenu originalSiteNavigationMenu =
			SiteNavigationMenuTestUtil.addSiteNavigationMenu(
				_group, SiteNavigationConstants.TYPE_DEFAULT);

		_siteNavigationMenuLocalService.updateSiteNavigationMenu(
			originalSiteNavigationMenu.getUserId(),
			originalSiteNavigationMenu.getSiteNavigationMenuId(),
			originalSiteNavigationMenu.getGroupId(),
			originalSiteNavigationMenu.getName(),
			SiteNavigationConstants.TYPE_SECONDARY,
			originalSiteNavigationMenu.isAuto());

		SiteNavigationMenu persistedSiteNavigationMenu =
			_siteNavigationMenuPersistence.fetchByPrimaryKey(
				originalSiteNavigationMenu.getSiteNavigationMenuId());

		Assert.assertEquals(
			SiteNavigationConstants.TYPE_SECONDARY,
			persistedSiteNavigationMenu.getType());
	}

	@DeleteAfterTestRun
	private Group _group;

	@Inject
	private SiteNavigationMenuLocalService _siteNavigationMenuLocalService;

	@Inject
	private SiteNavigationMenuPersistence _siteNavigationMenuPersistence;

}