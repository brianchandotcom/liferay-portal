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

		NavigationMenu postNavigationMenu1 =
			testGetNavigationMenu_addNavigationMenu();

		JournalArticle journalArticle1 = JournalTestUtil.addArticle(
			testGroup.getGroupId(),
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID);

		SiteNavigationMenuItem siteNavigationMenuItem1 =
			_addSiteNavigationMenuItem(
				postNavigationMenu1.getId(), JournalArticle.class.getName(),
				UnicodePropertiesBuilder.create(
					true
				).put(
					"className", JournalArticle.class.getName()
				).put(
					"classNameId",
					String.valueOf(journalArticle1.getClassNameId())
				).put(
					"classPK",
					String.valueOf(journalArticle1.getResourcePrimKey())
				).put(
					"classTypeId",
					String.valueOf(journalArticle1.getDDMStructureId())
				).put(
					"title", String.valueOf(journalArticle1.getTitle())
				).put(
					"type",
					ResourceActionsUtil.getModelResource(
						LocaleUtil.getDefault(), JournalArticle.class.getName())
				).buildString());

		NavigationMenu getNavigationMenu1 =
			navigationMenuResource.getNavigationMenu(
				postNavigationMenu1.getId());

		assertValid(getNavigationMenu1);

		NavigationMenuItem navigationMenuItem1 =
			getNavigationMenu1.getNavigationMenuItems()[0];

		Assert.assertTrue(
			navigationMenuItem1.getContentURL(
			).contains(
				"/headless-delivery/v1.0/structured-contents/" +
					journalArticle1.getResourcePrimKey()
			));
		Assert.assertEquals(
			journalArticle1.getTitle(), navigationMenuItem1.getName());
		Assert.assertEquals(
			siteNavigationMenuItem1.getSiteNavigationMenuItemId(),
			GetterUtil.getLong(navigationMenuItem1.getId()));
		Assert.assertEquals("structuredContent", navigationMenuItem1.getType());
		Assert.assertFalse(navigationMenuItem1.getUseCustomName());

		NavigationMenu postNavigationMenu2 =
			testGetNavigationMenu_addNavigationMenu();

		FileEntry fileEntry1 = DLAppTestUtil.addFileEntryWithWorkflow(
			TestPropsValues.getUserId(), testGroup.getGroupId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			RandomTestUtil.randomString() + ".txt",
			RandomTestUtil.randomString(), true,
			ServiceContextTestUtil.getServiceContext(
				testGroup.getGroupId(), TestPropsValues.getUserId()));

		SiteNavigationMenuItem siteNavigationMenuItem2 =
			_addSiteNavigationMenuItem(
				postNavigationMenu2.getId(), FileEntry.class.getName(),
				UnicodePropertiesBuilder.create(
					true
				).put(
					"className", DLFileEntry.class.getName()
				).put(
					"classNameId",
					String.valueOf(PortalUtil.getClassNameId(DLFileEntry.class))
				).put(
					"classPK", String.valueOf(fileEntry1.getPrimaryKey())
				).put(
					"classTypeId",
					String.valueOf(
						DLFileEntryTypeConstants.
							FILE_ENTRY_TYPE_ID_BASIC_DOCUMENT)
				).put(
					"title", String.valueOf(fileEntry1.getTitle())
				).put(
					"type",
					ResourceActionsUtil.getModelResource(
						LocaleUtil.getDefault(), DLFileEntry.class.getName())
				).put(
					"useCustomName", true
				).buildString());

		NavigationMenu getNavigationMenu2 =
			navigationMenuResource.getNavigationMenu(
				postNavigationMenu2.getId());

		assertValid(getNavigationMenu2);

		NavigationMenuItem navigationMenuItem2 =
			getNavigationMenu2.getNavigationMenuItems()[0];

		Assert.assertTrue(
			navigationMenuItem2.getContentURL(
			).contains(
				"/headless-delivery/v1.0/documents/" +
					fileEntry1.getFileEntryId()
			));
		Assert.assertEquals(
			siteNavigationMenuItem2.getSiteNavigationMenuItemId(),
			GetterUtil.getLong(navigationMenuItem2.getId()));
		Assert.assertEquals("document", navigationMenuItem2.getType());
		Assert.assertTrue(navigationMenuItem2.getUseCustomName());

		NavigationMenu postNavigationMenu3 =
			testGetNavigationMenu_addNavigationMenu();

		BlogsEntry blogsEntry = BlogsEntryLocalServiceUtil.addEntry(
			TestPropsValues.getUserId(), StringUtil.randomString(),
			StringUtil.randomString(), new Date(),
			ServiceContextTestUtil.getServiceContext(
				testGroup.getGroupId(), TestPropsValues.getUserId()));

		SiteNavigationMenuItem siteNavigationMenuItem3 =
			_addSiteNavigationMenuItem(
				postNavigationMenu3.getId(), BlogsEntry.class.getName(),
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

		NavigationMenu getNavigationMenu3 =
			navigationMenuResource.getNavigationMenu(
				postNavigationMenu3.getId());

		assertValid(getNavigationMenu3);

		NavigationMenuItem navigationMenuItem3 =
			getNavigationMenu3.getNavigationMenuItems()[0];

		Assert.assertTrue(
			navigationMenuItem3.getContentURL(
			).contains(
				"/headless-delivery/v1.0/blog-postings/" +
					blogsEntry.getPrimaryKey()
			));
		Assert.assertEquals(
			siteNavigationMenuItem3.getSiteNavigationMenuItemId(),
			GetterUtil.getLong(navigationMenuItem3.getId()));
		Assert.assertEquals("blogPosting", navigationMenuItem3.getType());
		Assert.assertTrue(navigationMenuItem3.getUseCustomName());

		NavigationMenu postNavigationMenu4 =
			testGetNavigationMenu_addNavigationMenu();

		JournalArticle journalArticle2 = JournalTestUtil.addArticle(
			_depotEntry.getGroupId(),
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID);

		SiteNavigationMenuItem siteNavigationMenuItem4 =
			_addSiteNavigationMenuItem(
				postNavigationMenu4.getId(), JournalArticle.class.getName(),
				UnicodePropertiesBuilder.create(
					true
				).put(
					"className", JournalArticle.class.getName()
				).put(
					"classNameId",
					String.valueOf(journalArticle2.getClassNameId())
				).put(
					"classPK",
					String.valueOf(journalArticle2.getResourcePrimKey())
				).put(
					"classTypeId",
					String.valueOf(journalArticle2.getDDMStructureId())
				).put(
					"title", String.valueOf(journalArticle2.getTitle())
				).put(
					"type",
					ResourceActionsUtil.getModelResource(
						LocaleUtil.getDefault(), JournalArticle.class.getName())
				).buildString());

		NavigationMenu getNavigationMenu4 =
			navigationMenuResource.getNavigationMenu(
				postNavigationMenu4.getId());

		assertValid(getNavigationMenu4);

		NavigationMenuItem navigationMenuItem4 =
			getNavigationMenu4.getNavigationMenuItems()[0];

		Assert.assertTrue(
			navigationMenuItem4.getContentURL(
			).contains(
				"/headless-delivery/v1.0/structured-contents/" +
					journalArticle2.getResourcePrimKey()
			));
		Assert.assertEquals(
			journalArticle2.getTitle(), navigationMenuItem4.getName());
		Assert.assertEquals(
			siteNavigationMenuItem4.getSiteNavigationMenuItemId(),
			GetterUtil.getLong(navigationMenuItem4.getId()));
		Assert.assertEquals("structuredContent", navigationMenuItem4.getType());
		Assert.assertFalse(navigationMenuItem4.getUseCustomName());

		NavigationMenu postNavigationMenu5 =
			testGetNavigationMenu_addNavigationMenu();

		FileEntry fileEntry2 = DLAppTestUtil.addFileEntryWithWorkflow(
			TestPropsValues.getUserId(), _depotEntry.getGroupId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			RandomTestUtil.randomString() + ".txt",
			RandomTestUtil.randomString(), true,
			ServiceContextTestUtil.getServiceContext(
				_depotEntry.getGroupId(), TestPropsValues.getUserId()));

		SiteNavigationMenuItem siteNavigationMenuItem5 =
			_addSiteNavigationMenuItem(
				postNavigationMenu5.getId(), FileEntry.class.getName(),
				UnicodePropertiesBuilder.create(
					true
				).put(
					"className", DLFileEntry.class.getName()
				).put(
					"classNameId",
					String.valueOf(PortalUtil.getClassNameId(DLFileEntry.class))
				).put(
					"classPK", String.valueOf(fileEntry2.getPrimaryKey())
				).put(
					"classTypeId",
					String.valueOf(
						DLFileEntryTypeConstants.
							FILE_ENTRY_TYPE_ID_BASIC_DOCUMENT)
				).put(
					"title", String.valueOf(fileEntry2.getTitle())
				).put(
					"type",
					ResourceActionsUtil.getModelResource(
						LocaleUtil.getDefault(), DLFileEntry.class.getName())
				).put(
					"useCustomName", true
				).buildString());

		NavigationMenu getNavigationMenu5 =
			navigationMenuResource.getNavigationMenu(
				postNavigationMenu5.getId());

		assertValid(getNavigationMenu5);

		NavigationMenuItem navigationMenuItem5 =
			getNavigationMenu5.getNavigationMenuItems()[0];

		Assert.assertTrue(
			navigationMenuItem5.getContentURL(
			).contains(
				"/headless-delivery/v1.0/documents/" +
					fileEntry2.getFileEntryId()
			));
		Assert.assertEquals(
			siteNavigationMenuItem5.getSiteNavigationMenuItemId(),
			GetterUtil.getLong(navigationMenuItem5.getId()));
		Assert.assertEquals("document", navigationMenuItem5.getType());
		Assert.assertTrue(navigationMenuItem5.getUseCustomName());
	}

	@Override
	@Test
	public void testGetSiteNavigationMenusPage() throws Exception {
		super.testGetSiteNavigationMenusPage();

		NavigationMenu postNavigationMenu1 =
			testGetNavigationMenu_addNavigationMenu();

		JournalArticle journalArticle1 = JournalTestUtil.addArticle(
			testGroup.getGroupId(),
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID);

		SiteNavigationMenuItem siteNavigationMenuItem1 =
			_addSiteNavigationMenuItem(
				postNavigationMenu1.getId(), JournalArticle.class.getName(),
				UnicodePropertiesBuilder.create(
					true
				).put(
					"className", JournalArticle.class.getName()
				).put(
					"classNameId",
					String.valueOf(journalArticle1.getClassNameId())
				).put(
					"classPK",
					String.valueOf(journalArticle1.getResourcePrimKey())
				).put(
					"classTypeId",
					String.valueOf(journalArticle1.getDDMStructureId())
				).put(
					"title", String.valueOf(journalArticle1.getTitle())
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

		NavigationMenuItem navigationMenuItem1 = page.fetchFirstItem(
		).getNavigationMenuItems()[0];

		Assert.assertEquals(1, page.getTotalCount());
		assertEquals(postNavigationMenu1, page.fetchFirstItem());
		assertValid(page);

		Assert.assertTrue(
			navigationMenuItem1.getContentURL(
			).contains(
				"/headless-delivery/v1.0/structured-contents/" +
					journalArticle1.getResourcePrimKey()
			));
		Assert.assertEquals(
			siteNavigationMenuItem1.getSiteNavigationMenuItemId(),
			GetterUtil.getLong(navigationMenuItem1.getId()));
		Assert.assertEquals("structuredContent", navigationMenuItem1.getType());
		Assert.assertTrue(navigationMenuItem1.getUseCustomName());

		navigationMenuResource.deleteNavigationMenu(
			postNavigationMenu1.getId());

		NavigationMenu postNavigationMenu2 =
			testGetNavigationMenu_addNavigationMenu();

		FileEntry fileEntry1 = DLAppTestUtil.addFileEntryWithWorkflow(
			TestPropsValues.getUserId(), testGroup.getGroupId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			RandomTestUtil.randomString() + ".txt",
			RandomTestUtil.randomString(), true,
			ServiceContextTestUtil.getServiceContext(
				testGroup.getGroupId(), TestPropsValues.getUserId()));

		SiteNavigationMenuItem siteNavigationMenuItem2 =
			_addSiteNavigationMenuItem(
				postNavigationMenu2.getId(), FileEntry.class.getName(),
				UnicodePropertiesBuilder.create(
					true
				).put(
					"className", DLFileEntry.class.getName()
				).put(
					"classNameId",
					String.valueOf(PortalUtil.getClassNameId(DLFileEntry.class))
				).put(
					"classPK", String.valueOf(fileEntry1.getPrimaryKey())
				).put(
					"classTypeId",
					String.valueOf(
						DLFileEntryTypeConstants.
							FILE_ENTRY_TYPE_ID_BASIC_DOCUMENT)
				).put(
					"title", String.valueOf(fileEntry1.getTitle())
				).put(
					"type",
					ResourceActionsUtil.getModelResource(
						LocaleUtil.getDefault(), DLFileEntry.class.getName())
				).buildString());

		page = navigationMenuResource.getSiteNavigationMenusPage(
			testGroup.getGroupId(), Pagination.of(1, 10));

		NavigationMenuItem navigationMenuItem2 = page.fetchFirstItem(
		).getNavigationMenuItems()[0];

		Assert.assertEquals(1, page.getTotalCount());
		assertEquals(postNavigationMenu2, page.fetchFirstItem());
		assertValid(page);

		Assert.assertTrue(
			navigationMenuItem2.getContentURL(
			).contains(
				"/headless-delivery/v1.0/documents/" +
					fileEntry1.getFileEntryId()
			));
		Assert.assertEquals(
			siteNavigationMenuItem2.getSiteNavigationMenuItemId(),
			GetterUtil.getLong(navigationMenuItem2.getId()));
		Assert.assertEquals(
			fileEntry1.getTitle(), navigationMenuItem2.getName());
		Assert.assertEquals("document", navigationMenuItem2.getType());
		Assert.assertFalse(navigationMenuItem2.getUseCustomName());

		navigationMenuResource.deleteNavigationMenu(
			postNavigationMenu2.getId());

		NavigationMenu postNavigationMenu3 =
			testGetNavigationMenu_addNavigationMenu();

		BlogsEntry blogsEntry = BlogsEntryLocalServiceUtil.addEntry(
			TestPropsValues.getUserId(), StringUtil.randomString(),
			StringUtil.randomString(), new Date(),
			ServiceContextTestUtil.getServiceContext(
				testGroup.getGroupId(), TestPropsValues.getUserId()));

		SiteNavigationMenuItem siteNavigationMenuItem3 =
			_addSiteNavigationMenuItem(
				postNavigationMenu3.getId(), BlogsEntry.class.getName(),
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

		NavigationMenuItem navigationMenuItem3 = page.fetchFirstItem(
		).getNavigationMenuItems()[0];

		Assert.assertEquals(1, page.getTotalCount());
		assertEquals(postNavigationMenu3, page.fetchFirstItem());
		assertValid(page);

		Assert.assertTrue(
			navigationMenuItem3.getContentURL(
			).contains(
				"/headless-delivery/v1.0/blog-postings/" +
					blogsEntry.getPrimaryKey()
			));
		Assert.assertEquals(
			siteNavigationMenuItem3.getSiteNavigationMenuItemId(),
			GetterUtil.getLong(navigationMenuItem3.getId()));
		Assert.assertEquals(
			blogsEntry.getTitle(), navigationMenuItem3.getName());
		Assert.assertEquals("blogPosting", navigationMenuItem3.getType());
		Assert.assertFalse(navigationMenuItem3.getUseCustomName());

		navigationMenuResource.deleteNavigationMenu(
			postNavigationMenu3.getId());

		NavigationMenu postNavigationMenu4 =
			testGetNavigationMenu_addNavigationMenu();

		JournalArticle journalArticle2 = JournalTestUtil.addArticle(
			_depotEntry.getGroupId(),
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID);

		SiteNavigationMenuItem siteNavigationMenuItem4 =
			_addSiteNavigationMenuItem(
				postNavigationMenu4.getId(), JournalArticle.class.getName(),
				UnicodePropertiesBuilder.create(
					true
				).put(
					"className", JournalArticle.class.getName()
				).put(
					"classNameId",
					String.valueOf(journalArticle2.getClassNameId())
				).put(
					"classPK",
					String.valueOf(journalArticle2.getResourcePrimKey())
				).put(
					"classTypeId",
					String.valueOf(journalArticle2.getDDMStructureId())
				).put(
					"title", String.valueOf(journalArticle2.getTitle())
				).put(
					"type",
					ResourceActionsUtil.getModelResource(
						LocaleUtil.getDefault(), JournalArticle.class.getName())
				).put(
					"useCustomName", true
				).buildString());

		page = navigationMenuResource.getSiteNavigationMenusPage(
			testGroup.getGroupId(), Pagination.of(1, 10));

		NavigationMenuItem navigationMenuItem4 = page.fetchFirstItem(
		).getNavigationMenuItems()[0];

		Assert.assertEquals(1, page.getTotalCount());
		assertEquals(postNavigationMenu4, page.fetchFirstItem());
		assertValid(page);

		Assert.assertTrue(
			navigationMenuItem4.getContentURL(
			).contains(
				"/headless-delivery/v1.0/structured-contents/" +
					journalArticle2.getResourcePrimKey()
			));
		Assert.assertEquals(
			siteNavigationMenuItem4.getSiteNavigationMenuItemId(),
			GetterUtil.getLong(navigationMenuItem4.getId()));
		Assert.assertEquals("structuredContent", navigationMenuItem4.getType());
		Assert.assertTrue(navigationMenuItem4.getUseCustomName());

		navigationMenuResource.deleteNavigationMenu(
			postNavigationMenu4.getId());

		NavigationMenu postNavigationMenu5 =
			testGetNavigationMenu_addNavigationMenu();

		FileEntry fileEntry2 = DLAppTestUtil.addFileEntryWithWorkflow(
			TestPropsValues.getUserId(), _depotEntry.getGroupId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			RandomTestUtil.randomString() + ".txt",
			RandomTestUtil.randomString(), true,
			ServiceContextTestUtil.getServiceContext(
				_depotEntry.getGroupId(), TestPropsValues.getUserId()));

		SiteNavigationMenuItem siteNavigationMenuItem5 =
			_addSiteNavigationMenuItem(
				postNavigationMenu5.getId(), FileEntry.class.getName(),
				UnicodePropertiesBuilder.create(
					true
				).put(
					"className", DLFileEntry.class.getName()
				).put(
					"classNameId",
					String.valueOf(PortalUtil.getClassNameId(DLFileEntry.class))
				).put(
					"classPK", String.valueOf(fileEntry2.getPrimaryKey())
				).put(
					"classTypeId",
					String.valueOf(
						DLFileEntryTypeConstants.
							FILE_ENTRY_TYPE_ID_BASIC_DOCUMENT)
				).put(
					"title", String.valueOf(fileEntry2.getTitle())
				).put(
					"type",
					ResourceActionsUtil.getModelResource(
						LocaleUtil.getDefault(), DLFileEntry.class.getName())
				).buildString());

		page = navigationMenuResource.getSiteNavigationMenusPage(
			testGroup.getGroupId(), Pagination.of(1, 10));

		NavigationMenuItem navigationMenuItem5 = page.fetchFirstItem(
		).getNavigationMenuItems()[0];

		Assert.assertEquals(1, page.getTotalCount());
		assertEquals(postNavigationMenu5, page.fetchFirstItem());
		assertValid(page);

		Assert.assertTrue(
			navigationMenuItem5.getContentURL(
			).contains(
				"/headless-delivery/v1.0/documents/" +
					fileEntry2.getFileEntryId()
			));
		Assert.assertEquals(
			siteNavigationMenuItem5.getSiteNavigationMenuItemId(),
			GetterUtil.getLong(navigationMenuItem5.getId()));
		Assert.assertEquals(
			fileEntry2.getTitle(), navigationMenuItem5.getName());
		Assert.assertEquals("document", navigationMenuItem5.getType());
		Assert.assertFalse(navigationMenuItem5.getUseCustomName());
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

	@Inject
	private DepotEntryGroupRelLocalService _depotEntryGroupRelLocalService;

	@Inject
	private SiteNavigationMenuItemLocalService
		_siteNavigationMenuItemLocalService;

	private DepotEntry _depotEntry;

}