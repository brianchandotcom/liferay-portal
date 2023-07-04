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
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.model.DLFileEntryTypeConstants;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.test.util.DLAppTestUtil;
import com.liferay.headless.delivery.client.dto.v1_0.NavigationMenu;
import com.liferay.headless.delivery.client.pagination.Page;
import com.liferay.headless.delivery.client.pagination.Pagination;
import com.liferay.journal.constants.JournalFolderConstants;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.test.util.JournalTestUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.security.permission.ResourceActionsUtil;
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

import java.util.Date;

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

		NavigationMenu postNavigationMenu1 =
			testGetNavigationMenu_addNavigationMenu();

		JournalArticle journalArticle = JournalTestUtil.addArticle(
			testGroup.getGroupId(),
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID);

		SiteNavigationMenuItem siteNavigationMenuItem1 =
			_createSiteNavigationMenuItem(
				postNavigationMenu1.getId(), JournalArticle.class.getName(),
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

		NavigationMenu getNavigationMenu1 =
			navigationMenuResource.getNavigationMenu(
				postNavigationMenu1.getId());

		Assert.assertEquals(
			"structuredContent",
			getNavigationMenu1.getNavigationMenuItems()[0].getType());
		Assert.assertFalse(
			getNavigationMenu1.getNavigationMenuItems()[0].getUseCustomName());
		Assert.assertEquals(
			journalArticle.getTitle(),
			getNavigationMenu1.getNavigationMenuItems()[0].getName());
		Assert.assertEquals(
			siteNavigationMenuItem1.getSiteNavigationMenuItemId(),
			GetterUtil.getLong(
				getNavigationMenu1.getNavigationMenuItems()[0].getId()));
		Assert.assertTrue(
			getNavigationMenu1.getNavigationMenuItems()[0].getContentURL(
			).contains(
				"/headless-delivery/v1.0/structured-contents/" +
					journalArticle.getResourcePrimKey()
			));
		assertValid(getNavigationMenu1);

		NavigationMenu postNavigationMenu2 =
			testGetNavigationMenu_addNavigationMenu();

		FileEntry fileEntry = DLAppTestUtil.addFileEntryWithWorkflow(
			TestPropsValues.getUserId(), testGroup.getGroupId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			RandomTestUtil.randomString() + ".txt",
			RandomTestUtil.randomString(), true,
			ServiceContextTestUtil.getServiceContext(
				testGroup.getGroupId(), TestPropsValues.getUserId()));

		SiteNavigationMenuItem siteNavigationMenuItem2 =
			_createSiteNavigationMenuItem(
				postNavigationMenu2.getId(), FileEntry.class.getName(),
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
						DLFileEntryTypeConstants.
							FILE_ENTRY_TYPE_ID_BASIC_DOCUMENT)
				).put(
					"title", String.valueOf(fileEntry.getTitle())
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

		Assert.assertEquals(
			"document",
			getNavigationMenu2.getNavigationMenuItems()[0].getType());
		Assert.assertTrue(
			getNavigationMenu2.getNavigationMenuItems()[0].getUseCustomName());
		Assert.assertEquals(
			siteNavigationMenuItem2.getSiteNavigationMenuItemId(),
			GetterUtil.getLong(
				getNavigationMenu2.getNavigationMenuItems()[0].getId()));
		Assert.assertTrue(
			getNavigationMenu2.getNavigationMenuItems()[0].getContentURL(
			).contains(
				"/headless-delivery/v1.0/documents/" +
					fileEntry.getFileEntryId()
			));
		assertValid(getNavigationMenu2);

		NavigationMenu postNavigationMenu3 =
			testGetNavigationMenu_addNavigationMenu();

		BlogsEntry blogsEntry = BlogsEntryLocalServiceUtil.addEntry(
			TestPropsValues.getUserId(), StringUtil.randomString(),
			StringUtil.randomString(), new Date(),
			ServiceContextTestUtil.getServiceContext(
				testGroup.getGroupId(), TestPropsValues.getUserId()));

		SiteNavigationMenuItem siteNavigationMenuItem3 =
			_createSiteNavigationMenuItem(
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

		Assert.assertEquals(
			"blogPosting",
			getNavigationMenu3.getNavigationMenuItems()[0].getType());
		Assert.assertTrue(
			getNavigationMenu3.getNavigationMenuItems()[0].getUseCustomName());
		Assert.assertEquals(
			siteNavigationMenuItem3.getSiteNavigationMenuItemId(),
			GetterUtil.getLong(
				getNavigationMenu3.getNavigationMenuItems()[0].getId()));
		Assert.assertTrue(
			getNavigationMenu3.getNavigationMenuItems()[0].getContentURL(
			).contains(
				"/headless-delivery/v1.0/blog-postings/" +
					blogsEntry.getPrimaryKey()
			));
		assertValid(getNavigationMenu3);
	}

	@Override
	@Test
	public void testGetSiteNavigationMenusPage() throws Exception {
		super.testGetSiteNavigationMenusPage();

		NavigationMenu postNavigationMenu1 =
			testGetNavigationMenu_addNavigationMenu();

		JournalArticle journalArticle = JournalTestUtil.addArticle(
			testGroup.getGroupId(),
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID);

		SiteNavigationMenuItem siteNavigationMenuItem1 =
			_createSiteNavigationMenuItem(
				postNavigationMenu1.getId(), JournalArticle.class.getName(),
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
			siteNavigationMenuItem1.getSiteNavigationMenuItemId(),
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
		assertEquals(postNavigationMenu1, page.fetchFirstItem());
		assertValid(page);

		navigationMenuResource.deleteNavigationMenu(
			postNavigationMenu1.getId());

		NavigationMenu postNavigationMenu2 =
			testGetNavigationMenu_addNavigationMenu();

		FileEntry fileEntry = DLAppTestUtil.addFileEntryWithWorkflow(
			TestPropsValues.getUserId(), testGroup.getGroupId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			RandomTestUtil.randomString() + ".txt",
			RandomTestUtil.randomString(), true,
			ServiceContextTestUtil.getServiceContext(
				testGroup.getGroupId(), TestPropsValues.getUserId()));

		SiteNavigationMenuItem siteNavigationMenuItem2 =
			_createSiteNavigationMenuItem(
				postNavigationMenu2.getId(), FileEntry.class.getName(),
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
						DLFileEntryTypeConstants.
							FILE_ENTRY_TYPE_ID_BASIC_DOCUMENT)
				).put(
					"title", String.valueOf(fileEntry.getTitle())
				).put(
					"type",
					ResourceActionsUtil.getModelResource(
						LocaleUtil.getDefault(), DLFileEntry.class.getName())
				).buildString());

		page = navigationMenuResource.getSiteNavigationMenusPage(
			testGroup.getGroupId(), Pagination.of(1, 10));

		Assert.assertEquals(1, page.getTotalCount());
		Assert.assertEquals(
			"document",
			page.fetchFirstItem(
			).getNavigationMenuItems()[0].getType());
		Assert.assertFalse(
			page.fetchFirstItem(
			).getNavigationMenuItems()[0].getUseCustomName());
		Assert.assertEquals(
			siteNavigationMenuItem2.getSiteNavigationMenuItemId(),
			GetterUtil.getLong(
				page.fetchFirstItem(
				).getNavigationMenuItems()[0].getId()));
		Assert.assertEquals(
			fileEntry.getTitle(),
			page.fetchFirstItem(
			).getNavigationMenuItems()[0].getName());
		Assert.assertTrue(
			page.fetchFirstItem(
			).getNavigationMenuItems()[0].getContentURL(
			).contains(
				"/headless-delivery/v1.0/documents/" +
					fileEntry.getFileEntryId()
			));
		assertEquals(postNavigationMenu2, page.fetchFirstItem());
		assertValid(page);

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
			_createSiteNavigationMenuItem(
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

		Assert.assertEquals(1, page.getTotalCount());
		Assert.assertEquals(
			"blogPosting",
			page.fetchFirstItem(
			).getNavigationMenuItems()[0].getType());
		Assert.assertFalse(
			page.fetchFirstItem(
			).getNavigationMenuItems()[0].getUseCustomName());
		Assert.assertEquals(
			siteNavigationMenuItem3.getSiteNavigationMenuItemId(),
			GetterUtil.getLong(
				page.fetchFirstItem(
				).getNavigationMenuItems()[0].getId()));
		Assert.assertEquals(
			blogsEntry.getTitle(),
			page.fetchFirstItem(
			).getNavigationMenuItems()[0].getName());
		Assert.assertTrue(
			page.fetchFirstItem(
			).getNavigationMenuItems()[0].getContentURL(
			).contains(
				"/headless-delivery/v1.0/blog-postings/" +
					blogsEntry.getPrimaryKey()
			));
		assertEquals(postNavigationMenu3, page.fetchFirstItem());
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