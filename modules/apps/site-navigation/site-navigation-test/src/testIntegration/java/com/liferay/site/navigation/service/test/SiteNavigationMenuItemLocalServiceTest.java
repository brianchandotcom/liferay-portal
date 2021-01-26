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
import com.liferay.expando.kernel.model.ExpandoBridge;
import com.liferay.expando.kernel.model.ExpandoColumnConstants;
import com.liferay.expando.kernel.util.ExpandoBridgeFactoryUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PersistenceTestRule;
import com.liferay.portal.test.rule.TransactionalTestRule;
import com.liferay.site.navigation.exception.InvalidSiteNavigationMenuItemOrderException;
import com.liferay.site.navigation.exception.InvalidSiteNavigationMenuItemTypeException;
import com.liferay.site.navigation.exception.SiteNavigationMenuItemNameException;
import com.liferay.site.navigation.menu.item.layout.constants.SiteNavigationMenuItemTypeConstants;
import com.liferay.site.navigation.model.SiteNavigationMenu;
import com.liferay.site.navigation.model.SiteNavigationMenuItem;
import com.liferay.site.navigation.service.SiteNavigationMenuItemLocalService;
import com.liferay.site.navigation.service.persistence.SiteNavigationMenuItemPersistence;
import com.liferay.site.navigation.util.SiteNavigationMenuItemTestUtil;
import com.liferay.site.navigation.util.SiteNavigationMenuTestUtil;

import java.io.Serializable;

import java.util.List;
import java.util.Map;

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
public class SiteNavigationMenuItemLocalServiceTest {

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

		_expandoBridge = ExpandoBridgeFactoryUtil.getExpandoBridge(
			_group.getCompanyId(), SiteNavigationMenuItem.class.getName());

		if (!_expandoBridge.hasAttribute(StringPool.CONTENT)) {
			_expandoBridge.addAttribute(
				StringPool.CONTENT, ExpandoColumnConstants.STRING,
				StringPool.BLANK);
		}

		_siteNavigationMenu = SiteNavigationMenuTestUtil.addSiteNavigationMenu(
			_group);
	}

	@Test
	public void testAddSiteNavigationMenuItem() throws PortalException {
		SiteNavigationMenuItem siteNavigationMenuItem =
			SiteNavigationMenuItemTestUtil.addSiteNavigationMenuItem(
				_siteNavigationMenu);

		SiteNavigationMenuItem persistedSiteNavigationMenuItem =
			_siteNavigationMenuItemPersistence.fetchByPrimaryKey(
				siteNavigationMenuItem.getSiteNavigationMenuItemId());

		Assert.assertEquals(
			siteNavigationMenuItem, persistedSiteNavigationMenuItem);
	}

	@Test
	public void testAddSiteNavigationMenuItemWithOrder()
		throws PortalException {

		int siteNavigationMenuItemPosition = 1;

		SiteNavigationMenuItem siteNavigationMenuItem =
			SiteNavigationMenuItemTestUtil.addSiteNavigationMenuItem(
				_siteNavigationMenu, siteNavigationMenuItemPosition);

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

		_siteNavigationMenuItemLocalService.deleteSiteNavigationMenuItem(
			siteNavigationMenuItem);

		Assert.assertNull(
			_siteNavigationMenuItemPersistence.fetchByPrimaryKey(
				siteNavigationMenuItem.getSiteNavigationMenuItemId()));
	}

	@Test
	public void testDeleteSiteNavigationMenuItemBySiteNavigationMenuItemId()
		throws PortalException {

		SiteNavigationMenuItem siteNavigationMenuItem =
			SiteNavigationMenuItemTestUtil.addSiteNavigationMenuItem(
				_siteNavigationMenu);

		_siteNavigationMenuItemLocalService.deleteSiteNavigationMenuItem(
			siteNavigationMenuItem.getSiteNavigationMenuItemId());

		Assert.assertNull(
			_siteNavigationMenuItemPersistence.fetchByPrimaryKey(
				siteNavigationMenuItem.getSiteNavigationMenuItemId()));
	}

	@Test
	public void testDeleteSiteNavigationMenuItemsAndMerge()
		throws PortalException {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		_siteNavigationMenuItemLocalService.addSiteNavigationMenuItem(
			TestPropsValues.getUserId(), _group.getGroupId(),
			_siteNavigationMenu.getSiteNavigationMenuId(), 0,
			SiteNavigationMenuItemTypeConstants.LAYOUT, StringPool.BLANK,
			serviceContext);

		SiteNavigationMenuItem siteNavigationMenuItem =
			_siteNavigationMenuItemLocalService.addSiteNavigationMenuItem(
				TestPropsValues.getUserId(), _group.getGroupId(),
				_siteNavigationMenu.getSiteNavigationMenuId(), 0,
				SiteNavigationMenuItemTypeConstants.LAYOUT, StringPool.BLANK,
				serviceContext);

		SiteNavigationMenuItem childSiteNavigationMenuItem1 =
			_siteNavigationMenuItemLocalService.addSiteNavigationMenuItem(
				TestPropsValues.getUserId(), _group.getGroupId(),
				_siteNavigationMenu.getSiteNavigationMenuId(),
				siteNavigationMenuItem.getSiteNavigationMenuItemId(),
				SiteNavigationMenuItemTypeConstants.LAYOUT, StringPool.BLANK,
				serviceContext);

		_siteNavigationMenuItemLocalService.addSiteNavigationMenuItem(
			TestPropsValues.getUserId(), _group.getGroupId(),
			_siteNavigationMenu.getSiteNavigationMenuId(), 0,
			SiteNavigationMenuItemTypeConstants.LAYOUT, StringPool.BLANK,
			serviceContext);

		_siteNavigationMenuItemLocalService.deleteSiteNavigationMenuItem(
			siteNavigationMenuItem.getSiteNavigationMenuItemId());

		childSiteNavigationMenuItem1 =
			_siteNavigationMenuItemPersistence.fetchByPrimaryKey(
				childSiteNavigationMenuItem1.getSiteNavigationMenuItemId());

		Assert.assertEquals(1, childSiteNavigationMenuItem1.getOrder());
	}

	@Test
	public void testDeleteSiteNavigationMenuItemsByGroupId() throws Exception {
		Group group = GroupTestUtil.addGroup();

		SiteNavigationMenu siteNavigationMenu =
			SiteNavigationMenuTestUtil.addSiteNavigationMenu(group);

		try {
			SiteNavigationMenuItemTestUtil.addSiteNavigationMenuItem(
				siteNavigationMenu);

			SiteNavigationMenuItemTestUtil.addSiteNavigationMenuItem(
				siteNavigationMenu);

			int originalSiteNavigationMenuItemsCount =
				_siteNavigationMenuItemPersistence.countBySiteNavigationMenuId(
					siteNavigationMenu.getSiteNavigationMenuId());

			_siteNavigationMenuItemLocalService.
				deleteSiteNavigationMenuItemsByGroupId(group.getGroupId());

			int actualSiteNavigationMenuItemsCount =
				_siteNavigationMenuItemPersistence.countBySiteNavigationMenuId(
					siteNavigationMenu.getSiteNavigationMenuId());

			Assert.assertEquals(
				originalSiteNavigationMenuItemsCount - 2,
				actualSiteNavigationMenuItemsCount);
		}
		finally {
			GroupLocalServiceUtil.deleteGroup(group);
		}
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

		_siteNavigationMenuItemLocalService.deleteSiteNavigationMenuItems(
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
			_siteNavigationMenuItemLocalService.getSiteNavigationMenuItems(
				_siteNavigationMenu.getSiteNavigationMenuId());

		SiteNavigationMenuItemTestUtil.addSiteNavigationMenuItem(
			_siteNavigationMenu);

		SiteNavigationMenuItemTestUtil.addSiteNavigationMenuItem(
			_siteNavigationMenu);

		List<SiteNavigationMenuItem> actualSiteNavigationMenuItems =
			_siteNavigationMenuItemLocalService.getSiteNavigationMenuItems(
				_siteNavigationMenu.getSiteNavigationMenuId());

		Assert.assertEquals(
			actualSiteNavigationMenuItems.toString(),
			originalSiteNavigationMenuItems.size() + 2,
			actualSiteNavigationMenuItems.size());
	}

	@Test
	public void testGetSiteNavigationMenuItemsByParentSiteNavigationMenuItemId()
		throws PortalException {

		SiteNavigationMenuItem parentSiteNavigationMenuItem =
			SiteNavigationMenuItemTestUtil.addSiteNavigationMenuItem(
				_siteNavigationMenu);

		List<SiteNavigationMenuItem> originalSiteNavigationMenuItems =
			_siteNavigationMenuItemLocalService.getSiteNavigationMenuItems(
				_siteNavigationMenu.getSiteNavigationMenuId(),
				parentSiteNavigationMenuItem.getSiteNavigationMenuItemId());

		SiteNavigationMenuItem childSiteNavigationMenuItem =
			SiteNavigationMenuItemTestUtil.addSiteNavigationMenuItem(
				_siteNavigationMenu,
				parentSiteNavigationMenuItem.getSiteNavigationMenuItemId());

		SiteNavigationMenuItemTestUtil.addSiteNavigationMenuItem(
			_siteNavigationMenu);

		List<SiteNavigationMenuItem> actualSiteNavigationMenuItems =
			_siteNavigationMenuItemLocalService.getSiteNavigationMenuItems(
				_siteNavigationMenu.getSiteNavigationMenuId(),
				parentSiteNavigationMenuItem.getSiteNavigationMenuItemId());

		Assert.assertEquals(
			actualSiteNavigationMenuItems.toString(),
			originalSiteNavigationMenuItems.size() + 1,
			actualSiteNavigationMenuItems.size());

		Assert.assertEquals(
			childSiteNavigationMenuItem, actualSiteNavigationMenuItems.get(0));
	}

	@Test
	public void testGetSiteNavigationMenuItemsCount() throws PortalException {
		SiteNavigationMenuItemTestUtil.addSiteNavigationMenuItem(
			_siteNavigationMenu);

		SiteNavigationMenuItemTestUtil.addSiteNavigationMenuItem(
			_siteNavigationMenu);

		Assert.assertEquals(
			2,
			_siteNavigationMenuItemPersistence.countBySiteNavigationMenuId(
				_siteNavigationMenu.getSiteNavigationMenuId()));
	}

	@Test(expected = InvalidSiteNavigationMenuItemOrderException.class)
	public void testInvalidParentSiteNavigationMenuItem()
		throws PortalException {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		SiteNavigationMenuItem siteNavigationMenuItem =
			_siteNavigationMenuItemLocalService.addSiteNavigationMenuItem(
				TestPropsValues.getUserId(), _group.getGroupId(),
				_siteNavigationMenu.getSiteNavigationMenuId(), 0,
				SiteNavigationMenuItemTypeConstants.LAYOUT, StringPool.BLANK,
				serviceContext);

		SiteNavigationMenuItem childSiteNavigationMenuItem =
			_siteNavigationMenuItemLocalService.addSiteNavigationMenuItem(
				TestPropsValues.getUserId(), _group.getGroupId(),
				_siteNavigationMenu.getSiteNavigationMenuId(),
				siteNavigationMenuItem.getSiteNavigationMenuItemId(),
				SiteNavigationMenuItemTypeConstants.LAYOUT, StringPool.BLANK,
				serviceContext);

		_siteNavigationMenuItemLocalService.updateSiteNavigationMenuItem(
			siteNavigationMenuItem.getSiteNavigationMenuItemId(),
			childSiteNavigationMenuItem.getSiteNavigationMenuItemId(),
			siteNavigationMenuItem.getOrder());
	}

	@Test(expected = SiteNavigationMenuItemNameException.class)
	public void testInvalidSiteNavigationMenuItemName() throws PortalException {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		UnicodeProperties typeSettingsUnicodeProperties =
			new UnicodeProperties();

		typeSettingsUnicodeProperties.put(
			"name", StringUtil.randomString(1000));

		_siteNavigationMenuItemLocalService.addSiteNavigationMenuItem(
			TestPropsValues.getUserId(), _group.getGroupId(),
			_siteNavigationMenu.getSiteNavigationMenuId(), 0,
			SiteNavigationMenuItemTypeConstants.LAYOUT,
			typeSettingsUnicodeProperties.toString(), serviceContext);
	}

	@Test(expected = InvalidSiteNavigationMenuItemTypeException.class)
	public void testInvalidSiteNavigationMenuItemType() throws PortalException {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		_siteNavigationMenuItemLocalService.addSiteNavigationMenuItem(
			TestPropsValues.getUserId(), _group.getGroupId(),
			_siteNavigationMenu.getSiteNavigationMenuId(), 0,
			"invalidMenuItemType", StringPool.BLANK, serviceContext);
	}

	@Test(expected = RuntimeException.class)
	public void testSiteNavigationMenuItemInvalidCustomAttribute()
		throws PortalException {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		SiteNavigationMenuItem siteNavigationMenuItem =
			_siteNavigationMenuItemLocalService.addSiteNavigationMenuItem(
				TestPropsValues.getUserId(), _group.getGroupId(),
				_siteNavigationMenu.getSiteNavigationMenuId(), 0,
				SiteNavigationMenuItemTypeConstants.LAYOUT, StringPool.BLANK,
				serviceContext);

		_expandoBridge.setClassPK(
			siteNavigationMenuItem.getSiteNavigationMenuItemId());

		_expandoBridge.getAttribute("invalid");
	}

	@Test
	public void testSiteNavigationMenuItemOrder() throws PortalException {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		_siteNavigationMenuItemLocalService.addSiteNavigationMenuItem(
			TestPropsValues.getUserId(), _group.getGroupId(),
			_siteNavigationMenu.getSiteNavigationMenuId(), 0,
			SiteNavigationMenuItemTypeConstants.LAYOUT, StringPool.BLANK,
			serviceContext);

		SiteNavigationMenuItem siteNavigationMenuItem =
			_siteNavigationMenuItemLocalService.addSiteNavigationMenuItem(
				TestPropsValues.getUserId(), _group.getGroupId(),
				_siteNavigationMenu.getSiteNavigationMenuId(), 0,
				SiteNavigationMenuItemTypeConstants.LAYOUT, StringPool.BLANK,
				serviceContext);

		Assert.assertEquals(1, siteNavigationMenuItem.getOrder());
	}

	@Test
	public void testSiteNavigationMenuItemValidCustomAttribute()
		throws PortalException {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		SiteNavigationMenuItem siteNavigationMenuItem =
			_siteNavigationMenuItemLocalService.addSiteNavigationMenuItem(
				TestPropsValues.getUserId(), _group.getGroupId(),
				_siteNavigationMenu.getSiteNavigationMenuId(), 0,
				SiteNavigationMenuItemTypeConstants.LAYOUT, StringPool.BLANK,
				serviceContext);

		_expandoBridge.setClassPK(
			siteNavigationMenuItem.getSiteNavigationMenuItemId());

		Serializable attributeValue = _expandoBridge.getAttribute(
			StringPool.CONTENT);

		Assert.assertEquals(StringPool.BLANK, attributeValue);

		Map<String, Serializable> expandoAttributes =
			serviceContext.getExpandoBridgeAttributes();

		expandoAttributes.put(StringPool.CONTENT, StringPool.CONTENT);

		_siteNavigationMenuItemLocalService.updateSiteNavigationMenuItem(
			TestPropsValues.getUserId(),
			siteNavigationMenuItem.getSiteNavigationMenuItemId(),
			siteNavigationMenuItem.getTypeSettings(), serviceContext);

		attributeValue = _expandoBridge.getAttribute(StringPool.CONTENT);

		Assert.assertEquals(StringPool.CONTENT, attributeValue);
	}

	@Test
	public void testUpdateSiteNavigationMenuItemOrder() throws PortalException {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		SiteNavigationMenuItem siteNavigationMenuItem =
			_siteNavigationMenuItemLocalService.addSiteNavigationMenuItem(
				TestPropsValues.getUserId(), _group.getGroupId(),
				_siteNavigationMenu.getSiteNavigationMenuId(), 0,
				SiteNavigationMenuItemTypeConstants.LAYOUT, StringPool.BLANK,
				serviceContext);

		SiteNavigationMenuItem childSiteNavigationMenuItem1 =
			_siteNavigationMenuItemLocalService.addSiteNavigationMenuItem(
				TestPropsValues.getUserId(), _group.getGroupId(),
				_siteNavigationMenu.getSiteNavigationMenuId(),
				siteNavigationMenuItem.getSiteNavigationMenuItemId(),
				SiteNavigationMenuItemTypeConstants.LAYOUT, StringPool.BLANK,
				serviceContext);

		SiteNavigationMenuItem childSiteNavigationMenuItem2 =
			_siteNavigationMenuItemLocalService.addSiteNavigationMenuItem(
				TestPropsValues.getUserId(), _group.getGroupId(),
				_siteNavigationMenu.getSiteNavigationMenuId(),
				siteNavigationMenuItem.getSiteNavigationMenuItemId(),
				SiteNavigationMenuItemTypeConstants.LAYOUT, StringPool.BLANK,
				serviceContext);

		_siteNavigationMenuItemLocalService.updateSiteNavigationMenuItem(
			childSiteNavigationMenuItem2.getSiteNavigationMenuItemId(),
			siteNavigationMenuItem.getSiteNavigationMenuItemId(), 0);

		childSiteNavigationMenuItem1 =
			_siteNavigationMenuItemPersistence.fetchByPrimaryKey(
				childSiteNavigationMenuItem1.getSiteNavigationMenuItemId());

		Assert.assertEquals(1, childSiteNavigationMenuItem1.getOrder());
	}

	@Test
	public void testUpdateSiteNavigationMenuItemOrderWith3Levels()
		throws PortalException {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		SiteNavigationMenuItem siteNavigationMenuItem1 =
			_siteNavigationMenuItemLocalService.addSiteNavigationMenuItem(
				TestPropsValues.getUserId(), _group.getGroupId(),
				_siteNavigationMenu.getSiteNavigationMenuId(), 0,
				SiteNavigationMenuItemTypeConstants.LAYOUT, StringPool.BLANK,
				serviceContext);

		SiteNavigationMenuItem siteNavigationMenuItem2 =
			_siteNavigationMenuItemLocalService.addSiteNavigationMenuItem(
				TestPropsValues.getUserId(), _group.getGroupId(),
				_siteNavigationMenu.getSiteNavigationMenuId(), 0,
				SiteNavigationMenuItemTypeConstants.LAYOUT, StringPool.BLANK,
				serviceContext);

		SiteNavigationMenuItem siteNavigationMenuItem3 =
			_siteNavigationMenuItemLocalService.addSiteNavigationMenuItem(
				TestPropsValues.getUserId(), _group.getGroupId(),
				_siteNavigationMenu.getSiteNavigationMenuId(), 0,
				SiteNavigationMenuItemTypeConstants.LAYOUT, StringPool.BLANK,
				serviceContext);

		SiteNavigationMenuItem siteNavigationMenuItem3Child1 =
			_siteNavigationMenuItemLocalService.addSiteNavigationMenuItem(
				TestPropsValues.getUserId(), _group.getGroupId(),
				_siteNavigationMenu.getSiteNavigationMenuId(), 0,
				SiteNavigationMenuItemTypeConstants.LAYOUT, StringPool.BLANK,
				serviceContext);

		SiteNavigationMenuItem siteNavigationMenuItem3Child1Child1 =
			_siteNavigationMenuItemLocalService.addSiteNavigationMenuItem(
				TestPropsValues.getUserId(), _group.getGroupId(),
				_siteNavigationMenu.getSiteNavigationMenuId(), 0,
				SiteNavigationMenuItemTypeConstants.LAYOUT, StringPool.BLANK,
				serviceContext);

		Assert.assertEquals(
			0, siteNavigationMenuItem1.getParentSiteNavigationMenuItemId());
		Assert.assertEquals(0, siteNavigationMenuItem1.getOrder());

		Assert.assertEquals(
			0, siteNavigationMenuItem2.getParentSiteNavigationMenuItemId());
		Assert.assertEquals(1, siteNavigationMenuItem2.getOrder());

		Assert.assertEquals(
			0, siteNavigationMenuItem3.getParentSiteNavigationMenuItemId());
		Assert.assertEquals(2, siteNavigationMenuItem3.getOrder());

		Assert.assertEquals(
			0,
			siteNavigationMenuItem3Child1.getParentSiteNavigationMenuItemId());
		Assert.assertEquals(3, siteNavigationMenuItem3Child1.getOrder());

		Assert.assertEquals(
			0,
			siteNavigationMenuItem3Child1Child1.
				getParentSiteNavigationMenuItemId());
		Assert.assertEquals(4, siteNavigationMenuItem3Child1Child1.getOrder());

		_siteNavigationMenuItemLocalService.updateSiteNavigationMenuItem(
			siteNavigationMenuItem3Child1.getSiteNavigationMenuItemId(),
			siteNavigationMenuItem3.getSiteNavigationMenuItemId(), 0);

		Assert.assertEquals(
			0, siteNavigationMenuItem1.getParentSiteNavigationMenuItemId());
		Assert.assertEquals(0, siteNavigationMenuItem1.getOrder());

		Assert.assertEquals(
			0, siteNavigationMenuItem2.getParentSiteNavigationMenuItemId());
		Assert.assertEquals(1, siteNavigationMenuItem2.getOrder());

		Assert.assertEquals(
			0, siteNavigationMenuItem3.getParentSiteNavigationMenuItemId());
		Assert.assertEquals(2, siteNavigationMenuItem3.getOrder());

		Assert.assertEquals(
			siteNavigationMenuItem3.getSiteNavigationMenuItemId(),
			siteNavigationMenuItem3Child1.getParentSiteNavigationMenuItemId());
		Assert.assertEquals(0, siteNavigationMenuItem3Child1.getOrder());

		Assert.assertEquals(
			0,
			siteNavigationMenuItem3Child1Child1.
				getParentSiteNavigationMenuItemId());
		Assert.assertEquals(3, siteNavigationMenuItem3Child1Child1.getOrder());

		_siteNavigationMenuItemLocalService.updateSiteNavigationMenuItem(
			siteNavigationMenuItem3Child1Child1.getSiteNavigationMenuItemId(),
			siteNavigationMenuItem3Child1.getSiteNavigationMenuItemId(), 0);

		Assert.assertEquals(
			0, siteNavigationMenuItem1.getParentSiteNavigationMenuItemId());
		Assert.assertEquals(0, siteNavigationMenuItem1.getOrder());

		Assert.assertEquals(
			0, siteNavigationMenuItem2.getParentSiteNavigationMenuItemId());
		Assert.assertEquals(1, siteNavigationMenuItem2.getOrder());

		Assert.assertEquals(
			0, siteNavigationMenuItem3.getParentSiteNavigationMenuItemId());
		Assert.assertEquals(2, siteNavigationMenuItem3.getOrder());

		Assert.assertEquals(
			siteNavigationMenuItem3.getSiteNavigationMenuItemId(),
			siteNavigationMenuItem3Child1.getParentSiteNavigationMenuItemId());
		Assert.assertEquals(0, siteNavigationMenuItem3Child1.getOrder());

		Assert.assertEquals(
			siteNavigationMenuItem3Child1.getSiteNavigationMenuItemId(),
			siteNavigationMenuItem3Child1Child1.
				getParentSiteNavigationMenuItemId());
		Assert.assertEquals(0, siteNavigationMenuItem3Child1Child1.getOrder());
	}

	@Test
	public void testUpdateSiteNavigationMenuItemParent()
		throws PortalException {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		SiteNavigationMenuItem siteNavigationMenuItem1 =
			_siteNavigationMenuItemLocalService.addSiteNavigationMenuItem(
				TestPropsValues.getUserId(), _group.getGroupId(),
				_siteNavigationMenu.getSiteNavigationMenuId(), 0,
				SiteNavigationMenuItemTypeConstants.LAYOUT, StringPool.BLANK,
				serviceContext);

		SiteNavigationMenuItem childSiteNavigationMenuItem11 =
			_siteNavigationMenuItemLocalService.addSiteNavigationMenuItem(
				TestPropsValues.getUserId(), _group.getGroupId(),
				_siteNavigationMenu.getSiteNavigationMenuId(),
				siteNavigationMenuItem1.getSiteNavigationMenuItemId(),
				SiteNavigationMenuItemTypeConstants.LAYOUT, StringPool.BLANK,
				serviceContext);

		SiteNavigationMenuItem childSiteNavigationMenuItem12 =
			_siteNavigationMenuItemLocalService.addSiteNavigationMenuItem(
				TestPropsValues.getUserId(), _group.getGroupId(),
				_siteNavigationMenu.getSiteNavigationMenuId(),
				siteNavigationMenuItem1.getSiteNavigationMenuItemId(),
				SiteNavigationMenuItemTypeConstants.LAYOUT, StringPool.BLANK,
				serviceContext);

		SiteNavigationMenuItem siteNavigationMenuItem2 =
			_siteNavigationMenuItemLocalService.addSiteNavigationMenuItem(
				TestPropsValues.getUserId(), _group.getGroupId(),
				_siteNavigationMenu.getSiteNavigationMenuId(), 0,
				SiteNavigationMenuItemTypeConstants.LAYOUT, StringPool.BLANK,
				serviceContext);

		SiteNavigationMenuItem childSiteNavigationMenuItem21 =
			_siteNavigationMenuItemLocalService.addSiteNavigationMenuItem(
				TestPropsValues.getUserId(), _group.getGroupId(),
				_siteNavigationMenu.getSiteNavigationMenuId(),
				siteNavigationMenuItem2.getSiteNavigationMenuItemId(),
				SiteNavigationMenuItemTypeConstants.LAYOUT, StringPool.BLANK,
				serviceContext);

		_siteNavigationMenuItemLocalService.updateSiteNavigationMenuItem(
			childSiteNavigationMenuItem11.getSiteNavigationMenuItemId(),
			siteNavigationMenuItem2.getSiteNavigationMenuItemId(), 0);

		childSiteNavigationMenuItem21 =
			_siteNavigationMenuItemPersistence.fetchByPrimaryKey(
				childSiteNavigationMenuItem21.getSiteNavigationMenuItemId());

		Assert.assertEquals(1, childSiteNavigationMenuItem21.getOrder());

		childSiteNavigationMenuItem12 =
			_siteNavigationMenuItemPersistence.fetchByPrimaryKey(
				childSiteNavigationMenuItem12.getSiteNavigationMenuItemId());

		Assert.assertEquals(0, childSiteNavigationMenuItem12.getOrder());
	}

	private ExpandoBridge _expandoBridge;

	@DeleteAfterTestRun
	private Group _group;

	private SiteNavigationMenu _siteNavigationMenu;

	@Inject
	private SiteNavigationMenuItemLocalService
		_siteNavigationMenuItemLocalService;

	@Inject
	private SiteNavigationMenuItemPersistence
		_siteNavigationMenuItemPersistence;

}