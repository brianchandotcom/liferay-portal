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

package com.liferay.headless.delivery.resource.v1_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.headless.delivery.client.dto.v1_0.NavigationMenu;
import com.liferay.headless.delivery.client.pagination.Page;
import com.liferay.headless.delivery.client.pagination.Pagination;
import com.liferay.journal.constants.JournalFolderConstants;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.test.util.JournalTestUtil;
import com.liferay.portal.kernel.security.permission.ResourceActionsUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.UnicodePropertiesBuilder;
import com.liferay.portal.test.rule.Inject;
import com.liferay.site.navigation.model.SiteNavigationMenuItem;
import com.liferay.site.navigation.service.SiteNavigationMenuItemLocalService;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Javier Gamarra
 */
@RunWith(Arquillian.class)
public class NavigationMenuResourceTest
	extends BaseNavigationMenuResourceTestCase {

	@Override
	@Test
	public void testGetNavigationMenu() throws Exception {
		super.testGetNavigationMenu();

		NavigationMenu postNavigationMenu =
			testGetNavigationMenu_addNavigationMenu();

		JournalArticle journalArticle = JournalTestUtil.addArticle(
			testGroup.getGroupId(),
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID);

		SiteNavigationMenuItem siteNavigationMenuItem =
			_createSiteNavigationMenuItem(
				postNavigationMenu.getId(), JournalArticle.class.getName(),
				UnicodePropertiesBuilder.create(
					true
				).put(
					"className", JournalArticle.class.getName()
				).put(
					"classNameId",
					String.valueOf(journalArticle.getClassNameId())
				).put(
					"classPK",
					String.valueOf(journalArticle.getResourcePrimKey())
				).put(
					"classTypeId",
					String.valueOf(journalArticle.getDDMStructureId())
				).put(
					"title", String.valueOf(journalArticle.getTitle())
				).put(
					"type",
					ResourceActionsUtil.getModelResource(
						LocaleUtil.getDefault(), JournalArticle.class.getName())
				).buildString());

		NavigationMenu getNavigationMenu =
			navigationMenuResource.getNavigationMenu(
				postNavigationMenu.getId());

		Assert.assertEquals(
			"structuredContent",
			getNavigationMenu.getNavigationMenuItems()[0].getType());
		Assert.assertFalse(
			getNavigationMenu.getNavigationMenuItems()[0].getUseCustomName());
		Assert.assertEquals(
			journalArticle.getTitle(),
			getNavigationMenu.getNavigationMenuItems()[0].getName());
		Assert.assertEquals(
			siteNavigationMenuItem.getSiteNavigationMenuItemId(),
			GetterUtil.getLong(
				getNavigationMenu.getNavigationMenuItems()[0].getId()));
		Assert.assertTrue(
			getNavigationMenu.getNavigationMenuItems()[0].getContentURL(
			).contains(
				"/headless-delivery/v1.0/structured-contents/" +
					journalArticle.getResourcePrimKey()
			));
		assertValid(getNavigationMenu);
	}

	@Override
	@Test
	public void testGetSiteNavigationMenusPage() throws Exception {
		super.testGetSiteNavigationMenusPage();

		NavigationMenu postNavigationMenu =
			testGetNavigationMenu_addNavigationMenu();

		JournalArticle journalArticle = JournalTestUtil.addArticle(
			testGroup.getGroupId(),
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID);

		SiteNavigationMenuItem siteNavigationMenuItem =
			_createSiteNavigationMenuItem(
				postNavigationMenu.getId(), JournalArticle.class.getName(),
				UnicodePropertiesBuilder.create(
					true
				).put(
					"className", JournalArticle.class.getName()
				).put(
					"classNameId",
					String.valueOf(journalArticle.getClassNameId())
				).put(
					"classPK",
					String.valueOf(journalArticle.getResourcePrimKey())
				).put(
					"classTypeId",
					String.valueOf(journalArticle.getDDMStructureId())
				).put(
					"title", String.valueOf(journalArticle.getTitle())
				).put(
					"type",
					ResourceActionsUtil.getModelResource(
						LocaleUtil.getDefault(), JournalArticle.class.getName())
				).put(
					"useCustomName", true
				).buildString());

		Page<NavigationMenu> page =
			navigationMenuResource.getSiteNavigationMenusPage(
				testGroup.getGroupId(), Pagination.of(1, 10));

		Assert.assertEquals(1, page.getTotalCount());
		Assert.assertEquals(
			"structuredContent",
			page.fetchFirstItem(
			).getNavigationMenuItems()[0].getType());
		Assert.assertTrue(
			page.fetchFirstItem(
			).getNavigationMenuItems()[0].getUseCustomName());
		Assert.assertEquals(
			siteNavigationMenuItem.getSiteNavigationMenuItemId(),
			GetterUtil.getLong(
				page.fetchFirstItem(
				).getNavigationMenuItems()[0].getId()));
		Assert.assertTrue(
			page.fetchFirstItem(
			).getNavigationMenuItems()[0].getContentURL(
			).contains(
				"/headless-delivery/v1.0/structured-contents/" +
					journalArticle.getResourcePrimKey()
			));
		assertEquals(postNavigationMenu, page.fetchFirstItem());
		assertValid(page);
	}

	@Override
	protected String[] getAdditionalAssertFieldNames() {
		return new String[] {"name"};
	}

	private SiteNavigationMenuItem _createSiteNavigationMenuItem(
			long siteNavigationMenuId, String type, String typeSettings)
		throws Exception {

		return _siteNavigationMenuItemLocalService.addSiteNavigationMenuItem(
			TestPropsValues.getUserId(), testGroup.getGroupId(),
			siteNavigationMenuId, 0, type, typeSettings,
			ServiceContextTestUtil.getServiceContext(
				testGroup.getGroupId(), TestPropsValues.getUserId()));
	}

	@Inject
	private SiteNavigationMenuItemLocalService
		_siteNavigationMenuItemLocalService;

}