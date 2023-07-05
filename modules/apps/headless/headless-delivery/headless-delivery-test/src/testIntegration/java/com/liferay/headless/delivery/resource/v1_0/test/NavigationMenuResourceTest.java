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
import com.liferay.blogs.model.BlogsEntry;
import com.liferay.blogs.service.BlogsEntryLocalServiceUtil;
import com.liferay.depot.model.DepotEntry;
import com.liferay.depot.service.DepotEntryGroupRelLocalService;
import com.liferay.depot.service.DepotEntryLocalServiceUtil;
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.model.DLFileEntryTypeConstants;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.test.util.DLAppTestUtil;
import com.liferay.headless.delivery.client.dto.v1_0.NavigationMenu;
import com.liferay.headless.delivery.client.dto.v1_0.NavigationMenuItem;
import com.liferay.headless.delivery.client.pagination.Page;
import com.liferay.headless.delivery.client.pagination.Pagination;
import com.liferay.journal.constants.JournalFolderConstants;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.test.util.JournalTestUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.security.permission.ResourceActionsUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.UnicodePropertiesBuilder;
import com.liferay.portal.test.rule.Inject;
import com.liferay.site.navigation.model.SiteNavigationMenuItem;
import com.liferay.site.navigation.service.SiteNavigationMenuItemLocalService;

import java.util.Collections;
import java.util.Date;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Javier Gamarra
 */
@RunWith(Arquillian.class)
public class NavigationMenuResourceTest
	extends BaseNavigationMenuResourceTestCase {

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_depotEntry = DepotEntryLocalServiceUtil.addDepotEntry(
			Collections.singletonMap(
				LocaleUtil.getDefault(), RandomTestUtil.randomString()),
			null,
			new ServiceContext() {
				{
					setCompanyId(testGroup.getCompanyId());
					setUserId(TestPropsValues.getUserId());
				}
			});

		_depotEntryGroupRelLocalService.addDepotEntryGroupRel(
			_depotEntry.getDepotEntryId(), testGroup.getGroupId());
	}

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
			_addSiteNavigationMenuItem(
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

		assertValid(getNavigationMenu);

		NavigationMenuItem navigationMenuItem =
			getNavigationMenu.getNavigationMenuItems()[0];

		Assert.assertTrue(
			navigationMenuItem.getContentURL(
			).contains(
				"/headless-delivery/v1.0/structured-contents/" +
					journalArticle.getResourcePrimKey()
			));
		Assert.assertEquals(
			journalArticle.getTitle(), navigationMenuItem.getName());
		Assert.assertEquals(
			siteNavigationMenuItem.getSiteNavigationMenuItemId(),
			GetterUtil.getLong(navigationMenuItem.getId()));
		Assert.assertEquals("structuredContent", navigationMenuItem.getType());
		Assert.assertFalse(navigationMenuItem.getUseCustomName());

		postNavigationMenu = testGetNavigationMenu_addNavigationMenu();

		FileEntry fileEntry = DLAppTestUtil.addFileEntryWithWorkflow(
			TestPropsValues.getUserId(), testGroup.getGroupId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			RandomTestUtil.randomString() + ".txt",
			RandomTestUtil.randomString(), true,
			ServiceContextTestUtil.getServiceContext(
				testGroup.getGroupId(), TestPropsValues.getUserId()));

		siteNavigationMenuItem = _addSiteNavigationMenuItem(
			postNavigationMenu.getId(), FileEntry.class.getName(),
			UnicodePropertiesBuilder.create(
				true
			).put(
				"className", DLFileEntry.class.getName()
			).put(
				"classNameId",
				String.valueOf(PortalUtil.getClassNameId(DLFileEntry.class))
			).put(
				"classPK", String.valueOf(fileEntry.getPrimaryKey())
			).put(
				"classTypeId",
				String.valueOf(
					DLFileEntryTypeConstants.FILE_ENTRY_TYPE_ID_BASIC_DOCUMENT)
			).put(
				"title", String.valueOf(fileEntry.getTitle())
			).put(
				"type",
				ResourceActionsUtil.getModelResource(
					LocaleUtil.getDefault(), DLFileEntry.class.getName())
			).put(
				"useCustomName", true
			).buildString());

		getNavigationMenu = navigationMenuResource.getNavigationMenu(
			postNavigationMenu.getId());

		assertValid(getNavigationMenu);

		navigationMenuItem = getNavigationMenu.getNavigationMenuItems()[0];

		Assert.assertTrue(
			navigationMenuItem.getContentURL(
			).contains(
				"/headless-delivery/v1.0/documents/" +
					fileEntry.getFileEntryId()
			));
		Assert.assertEquals(
			siteNavigationMenuItem.getSiteNavigationMenuItemId(),
			GetterUtil.getLong(navigationMenuItem.getId()));
		Assert.assertEquals("document", navigationMenuItem.getType());
		Assert.assertTrue(navigationMenuItem.getUseCustomName());

		postNavigationMenu = testGetNavigationMenu_addNavigationMenu();

		BlogsEntry blogsEntry = BlogsEntryLocalServiceUtil.addEntry(
			TestPropsValues.getUserId(), StringUtil.randomString(),
			StringUtil.randomString(), new Date(),
			ServiceContextTestUtil.getServiceContext(
				testGroup.getGroupId(), TestPropsValues.getUserId()));

		siteNavigationMenuItem = _addSiteNavigationMenuItem(
			postNavigationMenu.getId(), BlogsEntry.class.getName(),
			UnicodePropertiesBuilder.create(
				true
			).put(
				"className", BlogsEntry.class.getName()
			).put(
				"classNameId",
				String.valueOf(PortalUtil.getClassNameId(BlogsEntry.class))
			).put(
				"classPK", String.valueOf(blogsEntry.getPrimaryKey())
			).put(
				"classTypeId", String.valueOf(0)
			).put(
				"title", String.valueOf(blogsEntry.getTitle())
			).put(
				"type",
				ResourceActionsUtil.getModelResource(
					LocaleUtil.getDefault(), BlogsEntry.class.getName())
			).put(
				"useCustomName", true
			).buildString());

		getNavigationMenu = navigationMenuResource.getNavigationMenu(
			postNavigationMenu.getId());

		assertValid(getNavigationMenu);

		navigationMenuItem = getNavigationMenu.getNavigationMenuItems()[0];

		Assert.assertTrue(
			navigationMenuItem.getContentURL(
			).contains(
				"/headless-delivery/v1.0/blog-postings/" +
					blogsEntry.getPrimaryKey()
			));
		Assert.assertEquals(
			siteNavigationMenuItem.getSiteNavigationMenuItemId(),
			GetterUtil.getLong(navigationMenuItem.getId()));
		Assert.assertEquals("blogPosting", navigationMenuItem.getType());
		Assert.assertTrue(navigationMenuItem.getUseCustomName());

		postNavigationMenu = testGetNavigationMenu_addNavigationMenu();

		journalArticle = JournalTestUtil.addArticle(
			_depotEntry.getGroupId(),
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID);

		siteNavigationMenuItem = _addSiteNavigationMenuItem(
			postNavigationMenu.getId(), JournalArticle.class.getName(),
			UnicodePropertiesBuilder.create(
				true
			).put(
				"className", JournalArticle.class.getName()
			).put(
				"classNameId", String.valueOf(journalArticle.getClassNameId())
			).put(
				"classPK", String.valueOf(journalArticle.getResourcePrimKey())
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

		getNavigationMenu = navigationMenuResource.getNavigationMenu(
			postNavigationMenu.getId());

		assertValid(getNavigationMenu);

		navigationMenuItem = getNavigationMenu.getNavigationMenuItems()[0];

		Assert.assertTrue(
			navigationMenuItem.getContentURL(
			).contains(
				"/headless-delivery/v1.0/structured-contents/" +
					journalArticle.getResourcePrimKey()
			));
		Assert.assertEquals(
			journalArticle.getTitle(), navigationMenuItem.getName());
		Assert.assertEquals(
			siteNavigationMenuItem.getSiteNavigationMenuItemId(),
			GetterUtil.getLong(navigationMenuItem.getId()));
		Assert.assertEquals("structuredContent", navigationMenuItem.getType());
		Assert.assertFalse(navigationMenuItem.getUseCustomName());

		postNavigationMenu = testGetNavigationMenu_addNavigationMenu();

		fileEntry = DLAppTestUtil.addFileEntryWithWorkflow(
			TestPropsValues.getUserId(), _depotEntry.getGroupId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			RandomTestUtil.randomString() + ".txt",
			RandomTestUtil.randomString(), true,
			ServiceContextTestUtil.getServiceContext(
				_depotEntry.getGroupId(), TestPropsValues.getUserId()));

		siteNavigationMenuItem = _addSiteNavigationMenuItem(
			postNavigationMenu.getId(), FileEntry.class.getName(),
			UnicodePropertiesBuilder.create(
				true
			).put(
				"className", DLFileEntry.class.getName()
			).put(
				"classNameId",
				String.valueOf(PortalUtil.getClassNameId(DLFileEntry.class))
			).put(
				"classPK", String.valueOf(fileEntry.getPrimaryKey())
			).put(
				"classTypeId",
				String.valueOf(
					DLFileEntryTypeConstants.FILE_ENTRY_TYPE_ID_BASIC_DOCUMENT)
			).put(
				"title", String.valueOf(fileEntry.getTitle())
			).put(
				"type",
				ResourceActionsUtil.getModelResource(
					LocaleUtil.getDefault(), DLFileEntry.class.getName())
			).put(
				"useCustomName", true
			).buildString());

		getNavigationMenu = navigationMenuResource.getNavigationMenu(
			postNavigationMenu.getId());

		assertValid(getNavigationMenu);

		navigationMenuItem = getNavigationMenu.getNavigationMenuItems()[0];

		Assert.assertTrue(
			navigationMenuItem.getContentURL(
			).contains(
				"/headless-delivery/v1.0/documents/" +
					fileEntry.getFileEntryId()
			));
		Assert.assertEquals(
			siteNavigationMenuItem.getSiteNavigationMenuItemId(),
			GetterUtil.getLong(navigationMenuItem.getId()));
		Assert.assertEquals("document", navigationMenuItem.getType());
		Assert.assertTrue(navigationMenuItem.getUseCustomName());
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
			_addSiteNavigationMenuItem(
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

		NavigationMenuItem navigationMenuItem = page.fetchFirstItem(
		).getNavigationMenuItems()[0];

		Assert.assertEquals(1, page.getTotalCount());
		assertEquals(postNavigationMenu, page.fetchFirstItem());
		assertValid(page);

		Assert.assertTrue(
			navigationMenuItem.getContentURL(
			).contains(
				"/headless-delivery/v1.0/structured-contents/" +
					journalArticle.getResourcePrimKey()
			));
		Assert.assertEquals(
			siteNavigationMenuItem.getSiteNavigationMenuItemId(),
			GetterUtil.getLong(navigationMenuItem.getId()));
		Assert.assertEquals("structuredContent", navigationMenuItem.getType());
		Assert.assertTrue(navigationMenuItem.getUseCustomName());

		navigationMenuResource.deleteNavigationMenu(postNavigationMenu.getId());

		postNavigationMenu = testGetNavigationMenu_addNavigationMenu();

		FileEntry fileEntry = DLAppTestUtil.addFileEntryWithWorkflow(
			TestPropsValues.getUserId(), testGroup.getGroupId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			RandomTestUtil.randomString() + ".txt",
			RandomTestUtil.randomString(), true,
			ServiceContextTestUtil.getServiceContext(
				testGroup.getGroupId(), TestPropsValues.getUserId()));

		siteNavigationMenuItem = _addSiteNavigationMenuItem(
			postNavigationMenu.getId(), FileEntry.class.getName(),
			UnicodePropertiesBuilder.create(
				true
			).put(
				"className", DLFileEntry.class.getName()
			).put(
				"classNameId",
				String.valueOf(PortalUtil.getClassNameId(DLFileEntry.class))
			).put(
				"classPK", String.valueOf(fileEntry.getPrimaryKey())
			).put(
				"classTypeId",
				String.valueOf(
					DLFileEntryTypeConstants.FILE_ENTRY_TYPE_ID_BASIC_DOCUMENT)
			).put(
				"title", String.valueOf(fileEntry.getTitle())
			).put(
				"type",
				ResourceActionsUtil.getModelResource(
					LocaleUtil.getDefault(), DLFileEntry.class.getName())
			).buildString());

		page = navigationMenuResource.getSiteNavigationMenusPage(
			testGroup.getGroupId(), Pagination.of(1, 10));

		navigationMenuItem = page.fetchFirstItem(
		).getNavigationMenuItems()[0];

		Assert.assertEquals(1, page.getTotalCount());
		assertEquals(postNavigationMenu, page.fetchFirstItem());
		assertValid(page);

		Assert.assertTrue(
			navigationMenuItem.getContentURL(
			).contains(
				"/headless-delivery/v1.0/documents/" +
					fileEntry.getFileEntryId()
			));
		Assert.assertEquals(
			siteNavigationMenuItem.getSiteNavigationMenuItemId(),
			GetterUtil.getLong(navigationMenuItem.getId()));
		Assert.assertEquals(fileEntry.getTitle(), navigationMenuItem.getName());
		Assert.assertEquals("document", navigationMenuItem.getType());
		Assert.assertFalse(navigationMenuItem.getUseCustomName());

		navigationMenuResource.deleteNavigationMenu(postNavigationMenu.getId());

		postNavigationMenu = testGetNavigationMenu_addNavigationMenu();

		BlogsEntry blogsEntry = BlogsEntryLocalServiceUtil.addEntry(
			TestPropsValues.getUserId(), StringUtil.randomString(),
			StringUtil.randomString(), new Date(),
			ServiceContextTestUtil.getServiceContext(
				testGroup.getGroupId(), TestPropsValues.getUserId()));

		siteNavigationMenuItem = _addSiteNavigationMenuItem(
			postNavigationMenu.getId(), BlogsEntry.class.getName(),
			UnicodePropertiesBuilder.create(
				true
			).put(
				"className", BlogsEntry.class.getName()
			).put(
				"classNameId",
				String.valueOf(PortalUtil.getClassNameId(BlogsEntry.class))
			).put(
				"classPK", String.valueOf(blogsEntry.getPrimaryKey())
			).put(
				"classTypeId", String.valueOf(0)
			).put(
				"title", String.valueOf(blogsEntry.getTitle())
			).put(
				"type",
				ResourceActionsUtil.getModelResource(
					LocaleUtil.getDefault(), BlogsEntry.class.getName())
			).buildString());

		page = navigationMenuResource.getSiteNavigationMenusPage(
			testGroup.getGroupId(), Pagination.of(1, 10));

		navigationMenuItem = page.fetchFirstItem(
		).getNavigationMenuItems()[0];

		Assert.assertEquals(1, page.getTotalCount());
		assertEquals(postNavigationMenu, page.fetchFirstItem());
		assertValid(page);

		Assert.assertTrue(
			navigationMenuItem.getContentURL(
			).contains(
				"/headless-delivery/v1.0/blog-postings/" +
					blogsEntry.getPrimaryKey()
			));
		Assert.assertEquals(
			siteNavigationMenuItem.getSiteNavigationMenuItemId(),
			GetterUtil.getLong(navigationMenuItem.getId()));
		Assert.assertEquals(
			blogsEntry.getTitle(), navigationMenuItem.getName());
		Assert.assertEquals("blogPosting", navigationMenuItem.getType());
		Assert.assertFalse(navigationMenuItem.getUseCustomName());

		navigationMenuResource.deleteNavigationMenu(postNavigationMenu.getId());

		postNavigationMenu = testGetNavigationMenu_addNavigationMenu();

		journalArticle = JournalTestUtil.addArticle(
			_depotEntry.getGroupId(),
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID);

		siteNavigationMenuItem = _addSiteNavigationMenuItem(
			postNavigationMenu.getId(), JournalArticle.class.getName(),
			UnicodePropertiesBuilder.create(
				true
			).put(
				"className", JournalArticle.class.getName()
			).put(
				"classNameId", String.valueOf(journalArticle.getClassNameId())
			).put(
				"classPK", String.valueOf(journalArticle.getResourcePrimKey())
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

		page = navigationMenuResource.getSiteNavigationMenusPage(
			testGroup.getGroupId(), Pagination.of(1, 10));

		navigationMenuItem = page.fetchFirstItem(
		).getNavigationMenuItems()[0];

		Assert.assertEquals(1, page.getTotalCount());
		assertEquals(postNavigationMenu, page.fetchFirstItem());
		assertValid(page);

		Assert.assertTrue(
			navigationMenuItem.getContentURL(
			).contains(
				"/headless-delivery/v1.0/structured-contents/" +
					journalArticle.getResourcePrimKey()
			));
		Assert.assertEquals(
			siteNavigationMenuItem.getSiteNavigationMenuItemId(),
			GetterUtil.getLong(navigationMenuItem.getId()));
		Assert.assertEquals("structuredContent", navigationMenuItem.getType());
		Assert.assertTrue(navigationMenuItem.getUseCustomName());

		navigationMenuResource.deleteNavigationMenu(postNavigationMenu.getId());

		postNavigationMenu = testGetNavigationMenu_addNavigationMenu();

		fileEntry = DLAppTestUtil.addFileEntryWithWorkflow(
			TestPropsValues.getUserId(), _depotEntry.getGroupId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			RandomTestUtil.randomString() + ".txt",
			RandomTestUtil.randomString(), true,
			ServiceContextTestUtil.getServiceContext(
				_depotEntry.getGroupId(), TestPropsValues.getUserId()));

		siteNavigationMenuItem = _addSiteNavigationMenuItem(
			postNavigationMenu.getId(), FileEntry.class.getName(),
			UnicodePropertiesBuilder.create(
				true
			).put(
				"className", DLFileEntry.class.getName()
			).put(
				"classNameId",
				String.valueOf(PortalUtil.getClassNameId(DLFileEntry.class))
			).put(
				"classPK", String.valueOf(fileEntry.getPrimaryKey())
			).put(
				"classTypeId",
				String.valueOf(
					DLFileEntryTypeConstants.FILE_ENTRY_TYPE_ID_BASIC_DOCUMENT)
			).put(
				"title", String.valueOf(fileEntry.getTitle())
			).put(
				"type",
				ResourceActionsUtil.getModelResource(
					LocaleUtil.getDefault(), DLFileEntry.class.getName())
			).buildString());

		page = navigationMenuResource.getSiteNavigationMenusPage(
			testGroup.getGroupId(), Pagination.of(1, 10));

		navigationMenuItem = page.fetchFirstItem(
		).getNavigationMenuItems()[0];

		Assert.assertEquals(1, page.getTotalCount());
		assertEquals(postNavigationMenu, page.fetchFirstItem());
		assertValid(page);

		Assert.assertTrue(
			navigationMenuItem.getContentURL(
			).contains(
				"/headless-delivery/v1.0/documents/" +
					fileEntry.getFileEntryId()
			));
		Assert.assertEquals(
			siteNavigationMenuItem.getSiteNavigationMenuItemId(),
			GetterUtil.getLong(navigationMenuItem.getId()));
		Assert.assertEquals(fileEntry.getTitle(), navigationMenuItem.getName());
		Assert.assertEquals("document", navigationMenuItem.getType());
		Assert.assertFalse(navigationMenuItem.getUseCustomName());
	}

	@Override
	protected String[] getAdditionalAssertFieldNames() {
		return new String[] {"name"};
	}

	private SiteNavigationMenuItem _addSiteNavigationMenuItem(
			long siteNavigationMenuId, String type, String typeSettings)
		throws Exception {

		return _siteNavigationMenuItemLocalService.addSiteNavigationMenuItem(
			TestPropsValues.getUserId(), testGroup.getGroupId(),
			siteNavigationMenuId, 0, type, typeSettings,
			ServiceContextTestUtil.getServiceContext(
				testGroup.getGroupId(), TestPropsValues.getUserId()));
	}

	private DepotEntry _depotEntry;

	@Inject
	private DepotEntryGroupRelLocalService _depotEntryGroupRelLocalService;

	@Inject
	private SiteNavigationMenuItemLocalService
		_siteNavigationMenuItemLocalService;

}