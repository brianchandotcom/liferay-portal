/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
import com.liferay.expando.kernel.model.ExpandoColumn;
import com.liferay.expando.kernel.model.ExpandoColumnConstants;
import com.liferay.expando.kernel.model.ExpandoTable;
import com.liferay.expando.kernel.model.ExpandoTableConstants;
import com.liferay.expando.kernel.service.ExpandoColumnLocalService;
import com.liferay.expando.kernel.service.ExpandoTableLocalService;
import com.liferay.headless.delivery.client.dto.v1_0.NavigationMenu;
import com.liferay.headless.delivery.client.dto.v1_0.NavigationMenuItem;
import com.liferay.headless.delivery.client.pagination.Page;
import com.liferay.headless.delivery.client.pagination.Pagination;
import com.liferay.headless.delivery.client.resource.v1_0.NavigationMenuResource;
import com.liferay.journal.constants.JournalFolderConstants;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.test.util.JournalTestUtil;
import com.liferay.petra.string.StringPool;
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
import com.liferay.portal.util.PropsValues;
import com.liferay.site.navigation.menu.item.layout.constants.SiteNavigationMenuItemTypeConstants;
import com.liferay.site.navigation.model.SiteNavigationMenuItem;
import com.liferay.site.navigation.service.SiteNavigationMenuItemLocalService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Javier Gamarra
 */
@RunWith(Arquillian.class)
public class NavigationMenuResourceTest
	extends BaseNavigationMenuResourceTestCase {

	@BeforeClass
	public static void setUpClass() throws Exception {
		BaseNavigationMenuResourceTestCase.setUpClass();

		ExpandoTable expandoTable = _expandoTableLocalService.addDefaultTable(
			PortalUtil.getDefaultCompanyId(),
			SiteNavigationMenuItem.class.getName());

		ExpandoColumn expandoColumn1 = _expandoColumnLocalService.addColumn(
			expandoTable.getTableId(), RandomTestUtil.randomString(),
			ExpandoColumnConstants.STRING, StringPool.BLANK);

		_expandoColumnNames.add(expandoColumn1.getName());

		ExpandoColumn expandoColumn2 = _expandoColumnLocalService.addColumn(
			expandoTable.getTableId(), RandomTestUtil.randomString(),
			ExpandoColumnConstants.STRING, StringPool.BLANK);

		_expandoColumnNames.add(expandoColumn2.getName());
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
		_expandoTableLocalService.deleteTable(
			PortalUtil.getDefaultCompanyId(),
			SiteNavigationMenuItem.class.getName(),
			ExpandoTableConstants.DEFAULT_TABLE_NAME);
	}

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

		BlogsEntry blogsEntry = BlogsEntryLocalServiceUtil.addEntry(
			TestPropsValues.getUserId(), StringUtil.randomString(),
			StringUtil.randomString(), new Date(),
			ServiceContextTestUtil.getServiceContext(
				testGroup.getGroupId(), TestPropsValues.getUserId()));

		_testGetNavigationMenu(
			blogsEntry.getPrimaryKey(), 0, BlogsEntry.class,
			"blog-postings/" + blogsEntry.getPrimaryKey(),
			BlogsEntry.class.getName(), blogsEntry.getTitle(), "blogPosting",
			true);

		FileEntry fileEntry = DLAppTestUtil.addFileEntryWithWorkflow(
			TestPropsValues.getUserId(), _depotEntry.getGroupId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			RandomTestUtil.randomString() + ".txt",
			RandomTestUtil.randomString(), true,
			ServiceContextTestUtil.getServiceContext(
				_depotEntry.getGroupId(), TestPropsValues.getUserId()));

		_testGetNavigationMenu(
			fileEntry.getPrimaryKey(),
			DLFileEntryTypeConstants.FILE_ENTRY_TYPE_ID_BASIC_DOCUMENT,
			DLFileEntry.class, "documents/" + fileEntry.getFileEntryId(),
			FileEntry.class.getName(), fileEntry.getTitle(), "document", true);

		fileEntry = DLAppTestUtil.addFileEntryWithWorkflow(
			TestPropsValues.getUserId(), testGroup.getGroupId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			RandomTestUtil.randomString() + ".txt",
			RandomTestUtil.randomString(), true,
			ServiceContextTestUtil.getServiceContext(
				testGroup.getGroupId(), TestPropsValues.getUserId()));

		_testGetNavigationMenu(
			fileEntry.getPrimaryKey(),
			DLFileEntryTypeConstants.FILE_ENTRY_TYPE_ID_BASIC_DOCUMENT,
			DLFileEntry.class, "documents/" + fileEntry.getFileEntryId(),
			FileEntry.class.getName(), fileEntry.getTitle(), "document", true);

		JournalArticle journalArticle = JournalTestUtil.addArticle(
			_depotEntry.getGroupId(),
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID);

		_testGetNavigationMenu(
			journalArticle.getResourcePrimKey(),
			journalArticle.getDDMStructureId(), JournalArticle.class,
			"structured-contents/" + journalArticle.getResourcePrimKey(),
			JournalArticle.class.getName(), journalArticle.getTitle(),
			"structuredContent", false);

		journalArticle = JournalTestUtil.addArticle(
			testGroup.getGroupId(),
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID);

		_testGetNavigationMenu(
			journalArticle.getResourcePrimKey(),
			journalArticle.getDDMStructureId(), JournalArticle.class,
			"structured-contents/" + journalArticle.getResourcePrimKey(),
			JournalArticle.class.getName(), journalArticle.getTitle(),
			"structuredContent", false);

		_testGetNavigationMenuWithSubmenu();
	}

	@Override
	@Test
	public void testGetSiteNavigationMenusPage() throws Exception {
		super.testGetSiteNavigationMenusPage();

		BlogsEntry blogsEntry = BlogsEntryLocalServiceUtil.addEntry(
			TestPropsValues.getUserId(), StringUtil.randomString(),
			StringUtil.randomString(), new Date(),
			ServiceContextTestUtil.getServiceContext(
				testGroup.getGroupId(), TestPropsValues.getUserId()));

		_testGetSiteNavigationMenusPage(
			blogsEntry.getPrimaryKey(), 0, BlogsEntry.class,
			"blog-postings/" + blogsEntry.getPrimaryKey(),
			BlogsEntry.class.getName(), blogsEntry.getTitle(), "blogPosting",
			false);

		FileEntry fileEntry = DLAppTestUtil.addFileEntryWithWorkflow(
			TestPropsValues.getUserId(), _depotEntry.getGroupId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			RandomTestUtil.randomString() + ".txt",
			RandomTestUtil.randomString(), true,
			ServiceContextTestUtil.getServiceContext(
				_depotEntry.getGroupId(), TestPropsValues.getUserId()));

		_testGetSiteNavigationMenusPage(
			fileEntry.getPrimaryKey(),
			DLFileEntryTypeConstants.FILE_ENTRY_TYPE_ID_BASIC_DOCUMENT,
			DLFileEntry.class, "documents/" + fileEntry.getFileEntryId(),
			FileEntry.class.getName(), fileEntry.getTitle(), "document", false);

		fileEntry = DLAppTestUtil.addFileEntryWithWorkflow(
			TestPropsValues.getUserId(), testGroup.getGroupId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			RandomTestUtil.randomString() + ".txt",
			RandomTestUtil.randomString(), true,
			ServiceContextTestUtil.getServiceContext(
				testGroup.getGroupId(), TestPropsValues.getUserId()));

		_testGetSiteNavigationMenusPage(
			fileEntry.getPrimaryKey(),
			DLFileEntryTypeConstants.FILE_ENTRY_TYPE_ID_BASIC_DOCUMENT,
			DLFileEntry.class, "documents/" + fileEntry.getFileEntryId(),
			FileEntry.class.getName(), fileEntry.getTitle(), "document", false);

		JournalArticle journalArticle = JournalTestUtil.addArticle(
			_depotEntry.getGroupId(),
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID);

		_testGetSiteNavigationMenusPage(
			journalArticle.getResourcePrimKey(),
			journalArticle.getDDMStructureId(), JournalArticle.class,
			"structured-contents/" + journalArticle.getResourcePrimKey(),
			JournalArticle.class.getName(), journalArticle.getTitle(),
			"structuredContent", true);

		journalArticle = JournalTestUtil.addArticle(
			testGroup.getGroupId(),
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID);

		_testGetSiteNavigationMenusPage(
			journalArticle.getResourcePrimKey(),
			journalArticle.getDDMStructureId(), JournalArticle.class,
			"structured-contents/" + journalArticle.getResourcePrimKey(),
			JournalArticle.class.getName(), journalArticle.getTitle(),
			"structuredContent", true);
	}

	@Override
	protected String[] getAdditionalAssertFieldNames() {
		return new String[] {"name", "navigationMenuItems"};
	}

	private NavigationMenuResource _buildNavigationMenuResource() {
		NavigationMenuResource.Builder builder =
			NavigationMenuResource.builder();

		return builder.authentication(
			"test@liferay.com", PropsValues.DEFAULT_ADMIN_PASSWORD
		).locale(
			LocaleUtil.getDefault()
		).header(
			"X-Accept-All-Languages", "true"
		).build();
	}

	private void _testGetNavigationMenu(
			long classPK, long classTypeId, Class<?> clazz, String contentURL,
			String displayPageType, String title, String type,
			Boolean useCustomName)
		throws Exception {

		NavigationMenu postNavigationMenu =
			testGetNavigationMenu_addNavigationMenu();

		SiteNavigationMenuItem siteNavigationMenuItem =
			_siteNavigationMenuItemLocalService.addSiteNavigationMenuItem(
				null, TestPropsValues.getUserId(), testGroup.getGroupId(),
				postNavigationMenu.getId(), 0, displayPageType,
				UnicodePropertiesBuilder.create(
					true
				).put(
					"className", clazz.getName()
				).put(
					"classNameId",
					String.valueOf(PortalUtil.getClassNameId(clazz))
				).put(
					"classPK", String.valueOf(classPK)
				).put(
					"classTypeId", String.valueOf(classTypeId)
				).put(
					"title", String.valueOf(title)
				).put(
					"type",
					ResourceActionsUtil.getModelResource(
						LocaleUtil.getDefault(), clazz.getName())
				).put(
					"useCustomName",
					() -> {
						if (useCustomName) {
							return "true";
						}

						return null;
					}
				).buildString(),
				ServiceContextTestUtil.getServiceContext(
					testGroup.getGroupId(), TestPropsValues.getUserId()));

		NavigationMenu getNavigationMenu =
			navigationMenuResource.getNavigationMenu(
				postNavigationMenu.getId());

		assertValid(getNavigationMenu);

		NavigationMenuItem navigationMenuItem =
			getNavigationMenu.getNavigationMenuItems()[0];

		Assert.assertTrue(
			navigationMenuItem.getContentURL(
			).contains(
				"/headless-delivery/v1.0/" + contentURL
			));
		Assert.assertEquals(
			siteNavigationMenuItem.getSiteNavigationMenuItemId(),
			GetterUtil.getLong(navigationMenuItem.getId()));
		Assert.assertEquals(type, navigationMenuItem.getType());

		if (useCustomName) {
			Assert.assertTrue(navigationMenuItem.getUseCustomName());
		}
		else {
			Assert.assertEquals(title, navigationMenuItem.getName());
			Assert.assertFalse(navigationMenuItem.getUseCustomName());
		}
	}

	private void _testGetNavigationMenuWithSubmenu() throws Exception {
		NavigationMenu postNavigationMenu =
			testGetNavigationMenu_addNavigationMenu();

		String nameEnUS = RandomTestUtil.randomString();
		String nameEsES = RandomTestUtil.randomString();

		SiteNavigationMenuItem siteNavigationMenuItem =
			_siteNavigationMenuItemLocalService.addSiteNavigationMenuItem(
				null, TestPropsValues.getUserId(), testGroup.getGroupId(),
				postNavigationMenu.getId(), 0,
				SiteNavigationMenuItemTypeConstants.NODE,
				UnicodePropertiesBuilder.create(
					true
				).put(
					"defaultLanguageId", "en_US"
				).put(
					"name_en_US", nameEnUS
				).put(
					"name_es_ES", nameEsES
				).buildString(),
				ServiceContextTestUtil.getServiceContext(
					testGroup.getGroupId(), TestPropsValues.getUserId()));

		NavigationMenuResource navigationMenuResource =
			_buildNavigationMenuResource();

		NavigationMenu getNavigationMenu =
			navigationMenuResource.getNavigationMenu(
				postNavigationMenu.getId());

		assertValid(getNavigationMenu);

		NavigationMenuItem navigationMenuItem =
			getNavigationMenu.getNavigationMenuItems()[0];

		Assert.assertEquals(
			siteNavigationMenuItem.getSiteNavigationMenuItemId(),
			GetterUtil.getLong(navigationMenuItem.getId()));
		Assert.assertEquals(nameEnUS, navigationMenuItem.getName());

		Map<String, String> nameI18nMap = navigationMenuItem.getName_i18n();

		Assert.assertEquals(nameEnUS, nameI18nMap.get("en-US"));
		Assert.assertEquals(nameEsES, nameI18nMap.get("es-ES"));

		Assert.assertEquals("navigationMenu", navigationMenuItem.getType());
		Assert.assertFalse(navigationMenuItem.getUseCustomName());
	}

	private void _testGetSiteNavigationMenusPage(
			long classPK, long classTypeId, Class<?> clazz, String contentURL,
			String displayPageType, String title, String type,
			Boolean useCustomName)
		throws Exception {

		NavigationMenu postNavigationMenu =
			testGetNavigationMenu_addNavigationMenu();

		SiteNavigationMenuItem siteNavigationMenuItem =
			_siteNavigationMenuItemLocalService.addSiteNavigationMenuItem(
				null, TestPropsValues.getUserId(), testGroup.getGroupId(),
				postNavigationMenu.getId(), 0, displayPageType,
				UnicodePropertiesBuilder.create(
					true
				).put(
					"className", clazz.getName()
				).put(
					"classNameId",
					String.valueOf(PortalUtil.getClassNameId(clazz))
				).put(
					"classPK", String.valueOf(classPK)
				).put(
					"classTypeId", String.valueOf(classTypeId)
				).put(
					"title", String.valueOf(title)
				).put(
					"type",
					ResourceActionsUtil.getModelResource(
						LocaleUtil.getDefault(), clazz.getName())
				).put(
					"useCustomName",
					() -> {
						if (useCustomName) {
							return "true";
						}

						return null;
					}
				).buildString(),
				ServiceContextTestUtil.getServiceContext(
					testGroup.getGroupId(), TestPropsValues.getUserId()));

		Page<NavigationMenu> page =
			navigationMenuResource.getSiteNavigationMenusPage(
				testGroup.getGroupId(), Pagination.of(1, 10));

		Assert.assertEquals(1, page.getTotalCount());
		assertValid(page);

		NavigationMenu getNavigationMenu = page.fetchFirstItem();

		Assert.assertEquals(
			postNavigationMenu.getName(), getNavigationMenu.getName());
		Assert.assertEquals(
			postNavigationMenu.getSiteId(), getNavigationMenu.getSiteId());

		NavigationMenuItem navigationMenuItem =
			getNavigationMenu.getNavigationMenuItems()[0];

		Assert.assertTrue(
			navigationMenuItem.getContentURL(
			).contains(
				"/headless-delivery/v1.0/" + contentURL
			));
		Assert.assertEquals(
			siteNavigationMenuItem.getSiteNavigationMenuItemId(),
			GetterUtil.getLong(navigationMenuItem.getId()));
		Assert.assertEquals(type, navigationMenuItem.getType());

		if (useCustomName) {
			Assert.assertTrue(navigationMenuItem.getUseCustomName());
		}
		else {
			Assert.assertEquals(title, navigationMenuItem.getName());
			Assert.assertFalse(navigationMenuItem.getUseCustomName());
		}

		navigationMenuResource.deleteNavigationMenu(postNavigationMenu.getId());
	}

	@Inject
	private static ExpandoColumnLocalService _expandoColumnLocalService;

	private static final List<String> _expandoColumnNames = new ArrayList<>();

	@Inject
	private static ExpandoTableLocalService _expandoTableLocalService;

	private DepotEntry _depotEntry;

	@Inject
	private DepotEntryGroupRelLocalService _depotEntryGroupRelLocalService;

	@Inject
	private SiteNavigationMenuItemLocalService
		_siteNavigationMenuItemLocalService;

}