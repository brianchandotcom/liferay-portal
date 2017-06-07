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

package com.liferay.wiki.service.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.asset.kernel.exception.AssetCategoryException;
import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.model.AssetLink;
import com.liferay.asset.kernel.model.AssetTag;
import com.liferay.asset.kernel.model.AssetVocabulary;
import com.liferay.asset.kernel.service.AssetCategoryLocalServiceUtil;
import com.liferay.asset.kernel.service.AssetEntryLocalServiceUtil;
import com.liferay.asset.kernel.service.AssetLinkLocalServiceUtil;
import com.liferay.asset.kernel.service.AssetTagLocalServiceUtil;
import com.liferay.asset.kernel.service.AssetVocabularyLocalServiceUtil;
import com.liferay.expando.kernel.model.ExpandoBridge;
import com.liferay.expando.kernel.model.ExpandoColumn;
import com.liferay.expando.kernel.model.ExpandoColumnConstants;
import com.liferay.expando.kernel.model.ExpandoValue;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.AssertUtils;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.rule.Sync;
import com.liferay.portal.kernel.test.rule.SynchronousDestinationTestRule;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.kernel.workflow.WorkflowThreadLocal;
import com.liferay.portal.service.test.ServiceTestUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portlet.asset.util.test.AssetTestUtil;
import com.liferay.portlet.expando.util.test.ExpandoTestUtil;
import com.liferay.wiki.exception.DuplicatePageException;
import com.liferay.wiki.exception.NoSuchPageResourceException;
import com.liferay.wiki.exception.PageTitleException;
import com.liferay.wiki.model.WikiNode;
import com.liferay.wiki.model.WikiPage;
import com.liferay.wiki.service.WikiPageLocalServiceUtil;
import com.liferay.wiki.util.test.WikiTestUtil;

import java.io.Serializable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Manuel de la Peña
 * @author Roberto Díaz
 */
@RunWith(Arquillian.class)
@Sync
public class WikiPageLocalServiceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			SynchronousDestinationTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		ServiceTestUtil.setUser(TestPropsValues.getUser());

		_group = GroupTestUtil.addGroup();

		_node = WikiTestUtil.addNode(_group.getGroupId());
	}

	@Test
	public void testAddFrontPageWithoutRequiredCategory() throws Exception {
		AssetVocabulary assetVocabulary = getRequiredAssetVocabulary();

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		AssetCategoryLocalServiceUtil.addCategory(
			TestPropsValues.getUserId(), _group.getGroupId(), "category 1",
			assetVocabulary.getVocabularyId(), serviceContext);

		WikiPage frontPage = WikiTestUtil.addPage(
			TestPropsValues.getUserId(), _node.getNodeId(), "FrontPage",
			RandomTestUtil.randomString(), true, serviceContext);

		List<AssetCategory> categories =
			AssetCategoryLocalServiceUtil.getCategories(
				WikiPage.class.getName(), frontPage.getResourcePrimKey());

		Assert.assertTrue(ListUtil.isNull(categories));
	}

	@Test
	public void testAddPageWithInvalidTitle() throws Exception {
		char[] invalidCharacters = "\\[]|:;%<>".toCharArray();

		for (char invalidCharacter : invalidCharacters) {
			try {
				ServiceContext serviceContext =
					ServiceContextTestUtil.getServiceContext(
						_group.getGroupId());

				WikiTestUtil.addPage(
					TestPropsValues.getUserId(), _node.getNodeId(),
					"ChildPage" + invalidCharacter,
					RandomTestUtil.randomString(), true, serviceContext);

				Assert.fail(
					"Created a page with invalid character " +
						invalidCharacter);
			}
			catch (PageTitleException pte) {
			}
		}
	}

	@Test
	public void testAddPageWithNbspTitle() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		WikiPage page = WikiTestUtil.addPage(
			TestPropsValues.getUserId(), _node.getNodeId(),
			"ChildPage" + CharPool.NO_BREAK_SPACE + "1",
			RandomTestUtil.randomString(), true, serviceContext);

		Assert.assertEquals("ChildPage 1", page.getTitle());
	}

	@Test(expected = PortalException.class)
	public void testAddPageWithoutRequiredCategory() throws Exception {
		AssetVocabulary assetVocabulary = getRequiredAssetVocabulary();

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		AssetCategoryLocalServiceUtil.addCategory(
			TestPropsValues.getUserId(), _group.getGroupId(), "category 1",
			assetVocabulary.getVocabularyId(), serviceContext);

		try {
			WikiTestUtil.addPage(
				TestPropsValues.getUserId(), _node.getNodeId(),
				RandomTestUtil.randomString(), RandomTestUtil.randomString(),
				true, serviceContext);
		}
		catch (AssetCategoryException ace) {
			throw new PortalException();
		}
	}

	@Test
	public void testAddPageWithRequiredCategory() throws Exception {
		AssetVocabulary assetVocabulary = getRequiredAssetVocabulary();

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		AssetCategory assetCategory = AssetCategoryLocalServiceUtil.addCategory(
			TestPropsValues.getUserId(), _group.getGroupId(), "category 1",
			assetVocabulary.getVocabularyId(), serviceContext);

		long categoryId = assetCategory.getCategoryId();

		serviceContext.setAssetCategoryIds(new long[] {categoryId});

		WikiPage page = WikiTestUtil.addPage(
			TestPropsValues.getUserId(), _node.getNodeId(),
			RandomTestUtil.randomString(), RandomTestUtil.randomString(), true,
			serviceContext);

		List<AssetCategory> assetCategories =
			AssetCategoryLocalServiceUtil.getCategories(
				WikiPage.class.getName(), page.getResourcePrimKey());

		Assert.assertEquals(
			assetCategories.toString(), 1, assetCategories.size());

		AssetCategory persistedAssetCategory = assetCategories.get(0);

		Assert.assertEquals(categoryId, persistedAssetCategory.getCategoryId());
	}

	@Test
	public void testChangeParent() throws Exception {
		testChangeParent(false);
	}

	@Test
	public void testChangeParentChangesAllWikiPageVersionsParentTitle()
		throws Exception {

		WikiTestUtil.addPage(
			TestPropsValues.getUserId(), _group.getGroupId(), _node.getNodeId(),
			"ParentPage1", true);
		WikiTestUtil.addPage(
			TestPropsValues.getUserId(), _group.getGroupId(), _node.getNodeId(),
			"ParentPage2", true);

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		WikiPage childPage = WikiTestUtil.addPage(
			TestPropsValues.getUserId(), _node.getNodeId(), "ChildPage",
			RandomTestUtil.randomString(), "ParentPage1", true, serviceContext);

		WikiTestUtil.updatePage(
			childPage, TestPropsValues.getUserId(), StringUtil.randomString(),
			serviceContext);

		WikiPageLocalServiceUtil.changeParent(
			TestPropsValues.getUserId(), _node.getNodeId(),
			childPage.getTitle(), "ParentPage2", serviceContext);

		List<WikiPage> pages = WikiPageLocalServiceUtil.getPages(
			childPage.getNodeId(), childPage.getTitle(), QueryUtil.ALL_POS,
			QueryUtil.ALL_POS);

		Assert.assertEquals(pages.toString(), 3, pages.size());

		for (WikiPage curWikiPage : pages) {
			Assert.assertEquals("ParentPage2", curWikiPage.getParentTitle());
		}
	}

	@Test
	public void testChangeParentWithExpando() throws Exception {
		testChangeParent(true);
	}

	@Test
	public void testChangeParentWithWorkflowChangesParentAfterUpdateStatus()
		throws Exception {

		WikiTestUtil.addPage(
			TestPropsValues.getUserId(), _group.getGroupId(), _node.getNodeId(),
			"ParentPage1", true);
		WikiTestUtil.addPage(
			TestPropsValues.getUserId(), _group.getGroupId(), _node.getNodeId(),
			"ParentPage2", true);

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		WikiPage childPage = WikiTestUtil.addPage(
			TestPropsValues.getUserId(), _node.getNodeId(), "ChildPage",
			RandomTestUtil.randomString(), "ParentPage1", true, serviceContext);

		boolean workflowEnabled = WorkflowThreadLocal.isEnabled();

		try {
			WorkflowThreadLocal.setEnabled(true);

			serviceContext = (ServiceContext)serviceContext.clone();

			serviceContext.setWorkflowAction(
				WorkflowConstants.ACTION_SAVE_DRAFT);

			WikiPage pendingChildPage = WikiPageLocalServiceUtil.changeParent(
				TestPropsValues.getUserId(), _node.getNodeId(),
				childPage.getTitle(), "ParentPage2", serviceContext);

			childPage = WikiPageLocalServiceUtil.getPage(
				_node.getNodeId(), childPage.getTitle(), true);

			Assert.assertEquals("ParentPage1", childPage.getParentTitle());

			Map<String, Serializable> workflowContext = new HashMap<>();

			workflowContext.put(
				WorkflowConstants.CONTEXT_COMMAND, serviceContext.getCommand());

			WikiPageLocalServiceUtil.updateStatus(
				TestPropsValues.getUserId(), pendingChildPage,
				WorkflowConstants.STATUS_APPROVED, serviceContext,
				workflowContext);

			List<WikiPage> pages = WikiPageLocalServiceUtil.getPages(
				childPage.getNodeId(), childPage.getTitle(), QueryUtil.ALL_POS,
				QueryUtil.ALL_POS);

			Assert.assertEquals(pages.toString(), 2, pages.size());

			for (WikiPage curWikiPage : pages) {
				Assert.assertEquals(
					"ParentPage2", curWikiPage.getParentTitle());
			}
		}
		finally {
			WorkflowThreadLocal.setEnabled(workflowEnabled);
		}
	}

	@Test
	public void testCopyPage() throws Exception {
		WikiPage page = WikiTestUtil.addPage(
			_group.getGroupId(), _node.getNodeId(), true);

		WikiTestUtil.addWikiAttachment(
			page.getUserId(), page.getNodeId(), page.getTitle(), getClass());

		List<FileEntry> attachmentsFileEntries =
			page.getAttachmentsFileEntries();

		WikiPage copyPage = WikiTestUtil.copyPage(
			page, true,
			ServiceContextTestUtil.getServiceContext(_group.getGroupId()));

		List<FileEntry> copyAttachmentsFileEntries =
			copyPage.getAttachmentsFileEntries();

		Assert.assertEquals(
			attachmentsFileEntries.toString(),
			copyAttachmentsFileEntries.size(), attachmentsFileEntries.size());

		FileEntry fileEntry = attachmentsFileEntries.get(0);
		FileEntry copyFileEntry = copyAttachmentsFileEntries.get(0);

		Assert.assertEquals(
			copyFileEntry.getExtension(), fileEntry.getExtension());
		Assert.assertEquals(
			copyFileEntry.getMimeType(), fileEntry.getMimeType());
		Assert.assertEquals(copyFileEntry.getTitle(), fileEntry.getTitle());
		Assert.assertEquals(copyFileEntry.getSize(), fileEntry.getSize());
	}

	@Test(expected = NoSuchPageResourceException.class)
	public void testDeletePage() throws Exception {
		WikiPage page = WikiTestUtil.addPage(
			TestPropsValues.getUserId(), _group.getGroupId(), _node.getNodeId(),
			"TestPage", true);

		WikiPageLocalServiceUtil.deletePage(page);

		WikiPageLocalServiceUtil.getPage(page.getResourcePrimKey());
	}

	@Test
	public void testDeleteParentPageWithChangedParentChild() throws Exception {
		WikiTestUtil.addPage(
			TestPropsValues.getUserId(), _group.getGroupId(), _node.getNodeId(),
			"ParentPage1", true);
		WikiTestUtil.addPage(
			TestPropsValues.getUserId(), _group.getGroupId(), _node.getNodeId(),
			"ParentPage2", true);

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		WikiPage childPage = WikiTestUtil.addPage(
			TestPropsValues.getUserId(), _node.getNodeId(), "ChildPage",
			RandomTestUtil.randomString(), "ParentPage1", true, serviceContext);

		WikiPageLocalServiceUtil.changeParent(
			TestPropsValues.getUserId(), _node.getNodeId(),
			childPage.getTitle(), "ParentPage2", serviceContext);

		WikiPageLocalServiceUtil.deletePage(_node.getNodeId(), "ParentPage1");

		childPage = WikiPageLocalServiceUtil.getPage(
			_node.getNodeId(), childPage.getTitle());

		Assert.assertEquals("ChildPage", childPage.getTitle());
	}

	@Test
	public void testDeleteTrashedPageWithExplicitTrashedRedirectPage()
		throws Exception {

		WikiPage[] pages = WikiTestUtil.addRenamedTrashedPage(
			_group.getGroupId(), _node.getNodeId(), true);

		WikiPage page = pages[0];
		WikiPage redirectPage = pages[1];

		WikiPageLocalServiceUtil.deletePage(page);

		try {
			WikiPageLocalServiceUtil.getPage(page.getResourcePrimKey());

			Assert.fail();
		}
		catch (NoSuchPageResourceException nspre) {
			redirectPage = WikiPageLocalServiceUtil.getPage(
				redirectPage.getResourcePrimKey());

			Assert.assertNull(redirectPage.fetchRedirectPage());
		}
	}

	@Test(expected = NoSuchPageResourceException.class)
	public void testDeleteTrashedPageWithImplicitTrashedRedirectPage()
		throws Exception {

		WikiPage[] pages = WikiTestUtil.addRenamedTrashedPage(
			_group.getGroupId(), _node.getNodeId(), false);

		WikiPage page = pages[0];
		WikiPage redirectPage = pages[1];

		WikiPageLocalServiceUtil.deletePage(page);

		try {
			WikiPageLocalServiceUtil.getPage(page.getResourcePrimKey());

			Assert.fail();
		}
		catch (NoSuchPageResourceException nspre) {
			WikiPageLocalServiceUtil.getPage(redirectPage.getResourcePrimKey());
		}
	}

	@Test
	public void testDeleteTrashedPageWithRestoredChildPage() throws Exception {
		WikiPage[] pages = WikiTestUtil.addTrashedPageWithChildPage(
			_group.getGroupId(), _node.getNodeId(), true);

		WikiPage parentPage = pages[0];
		WikiPage childPage = pages[1];

		WikiPageLocalServiceUtil.restorePageFromTrash(
			TestPropsValues.getUserId(), childPage);

		WikiPageLocalServiceUtil.deletePage(parentPage);

		try {
			WikiPageLocalServiceUtil.getPage(parentPage.getResourcePrimKey());

			Assert.fail();
		}
		catch (NoSuchPageResourceException nspre) {
			childPage = WikiPageLocalServiceUtil.getPage(
				childPage.getResourcePrimKey());

			Assert.assertNull(childPage.fetchParentPage());
			Assert.assertEquals(
				WorkflowConstants.STATUS_APPROVED, childPage.getStatus());
		}
	}

	@Test
	public void testDeleteTrashedPageWithRestoredRedirectPage()
		throws Exception {

		WikiPage[] pages = WikiTestUtil.addRenamedTrashedPage(
			_group.getGroupId(), _node.getNodeId(), true);

		WikiPage page = pages[0];
		WikiPage redirectPage = pages[1];

		WikiPageLocalServiceUtil.restorePageFromTrash(
			TestPropsValues.getUserId(), redirectPage);

		WikiPageLocalServiceUtil.deletePage(page);

		try {
			WikiPageLocalServiceUtil.getPage(page.getResourcePrimKey());

			Assert.fail();
		}
		catch (NoSuchPageResourceException nspre) {
			redirectPage = WikiPageLocalServiceUtil.getPageByPageId(
				redirectPage.getPageId());

			Assert.assertNull(redirectPage.fetchRedirectPage());
			Assert.assertEquals(
				WorkflowConstants.STATUS_APPROVED, redirectPage.getStatus());
		}
	}

	@Test
	public void testDeleteTrashedParentPageWithExplicitTrashedChildPage()
		throws Exception {

		WikiPage[] pages = WikiTestUtil.addTrashedPageWithChildPage(
			_group.getGroupId(), _node.getNodeId(), true);

		WikiPage parentPage = pages[0];
		WikiPage childPage = pages[1];

		WikiPageLocalServiceUtil.deletePage(parentPage);

		try {
			WikiPageLocalServiceUtil.getPage(parentPage.getResourcePrimKey());

			Assert.fail();
		}
		catch (NoSuchPageResourceException nspre) {
			childPage = WikiPageLocalServiceUtil.getPageByPageId(
				childPage.getPageId());

			Assert.assertNull(childPage.fetchParentPage());
		}
	}

	@Test(expected = NoSuchPageResourceException.class)
	public void testDeleteTrashedParentPageWithImplicitTrashedChildPage()
		throws Exception {

		WikiPage[] pages = WikiTestUtil.addTrashedPageWithChildPage(
			_group.getGroupId(), _node.getNodeId(), false);

		WikiPage parentPage = pages[0];
		WikiPage childPage = pages[1];

		WikiPageLocalServiceUtil.deletePage(parentPage);

		try {
			WikiPageLocalServiceUtil.getPage(parentPage.getResourcePrimKey());

			Assert.fail();
		}
		catch (NoSuchPageResourceException nspre) {
			WikiPageLocalServiceUtil.getPage(childPage.getResourcePrimKey());
		}
	}

	@Test
	public void testGetNoAssetPages() throws Exception {
		List<WikiPage> initialPages =
			WikiPageLocalServiceUtil.getNoAssetPages();

		WikiTestUtil.addPage(_group.getGroupId(), _node.getNodeId(), true);

		WikiPage page = WikiTestUtil.addPage(
			_group.getGroupId(), _node.getNodeId(), true);

		AssetEntry assetEntry = AssetEntryLocalServiceUtil.fetchEntry(
			WikiPage.class.getName(), page.getResourcePrimKey());

		Assert.assertNotNull(assetEntry);

		AssetEntryLocalServiceUtil.deleteAssetEntry(assetEntry);

		List<WikiPage> pages = WikiPageLocalServiceUtil.getNoAssetPages();

		Assert.assertEquals(
			pages.toString(), initialPages.size() + 1, pages.size());
		Assert.assertEquals(page, pages.get(pages.size() - 1));
	}

	@Test
	public void testGetPage() throws Exception {
		WikiPage page = WikiTestUtil.addPage(
			_group.getGroupId(), _node.getNodeId(), true);

		WikiPage retrievedPage = WikiPageLocalServiceUtil.getPage(
			page.getResourcePrimKey());

		Assert.assertEquals(retrievedPage.getPageId(), page.getPageId());
	}

	@Test
	public void testRenameDefaultVersionPageWithAssetCategories()
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		AssetVocabulary assetVocabulary = AssetTestUtil.addVocabulary(
			_group.getGroupId());

		AssetCategory assetCategory1 = AssetTestUtil.addCategory(
			_group.getGroupId(), assetVocabulary.getVocabularyId());
		AssetCategory assetCategory2 = AssetTestUtil.addCategory(
			_group.getGroupId(), assetVocabulary.getVocabularyId());

		long[] assetCategoryIds = new long[2];

		assetCategoryIds[0] = assetCategory1.getCategoryId();
		assetCategoryIds[1] = assetCategory2.getCategoryId();

		serviceContext.setAssetCategoryIds(assetCategoryIds);

		WikiPage page = WikiTestUtil.addPage(
			TestPropsValues.getUserId(), _node.getNodeId(),
			RandomTestUtil.randomString(), RandomTestUtil.randomString(), true,
			serviceContext);

		serviceContext = ServiceContextTestUtil.getServiceContext(
			_group.getGroupId());

		WikiPageLocalServiceUtil.renamePage(
			TestPropsValues.getUserId(), _node.getNodeId(), page.getTitle(),
			"New Title", true, serviceContext);

		WikiPage renamedPage = WikiPageLocalServiceUtil.getPage(
			_node.getNodeId(), "New Title");

		long[] finalAssetCategoryIds =
			AssetCategoryLocalServiceUtil.getCategoryIds(
				WikiPage.class.getName(), renamedPage.getResourcePrimKey());

		Assert.assertArrayEquals(assetCategoryIds, finalAssetCategoryIds);
	}

	@Test
	public void testRenameDefaultVersionPageWithAssetTags() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		AssetTag assetTag1 = AssetTestUtil.addTag(_group.getGroupId());
		AssetTag assetTag2 = AssetTestUtil.addTag(_group.getGroupId());

		String[] assetTagNames = new String[2];

		assetTagNames[0] = assetTag1.getName();
		assetTagNames[1] = assetTag2.getName();

		serviceContext.setAssetTagNames(assetTagNames);

		WikiPage page = WikiTestUtil.addPage(
			TestPropsValues.getUserId(), _node.getNodeId(),
			RandomTestUtil.randomString(), RandomTestUtil.randomString(), true,
			serviceContext);

		serviceContext = ServiceContextTestUtil.getServiceContext(
			_group.getGroupId());

		WikiPageLocalServiceUtil.renamePage(
			TestPropsValues.getUserId(), _node.getNodeId(), page.getTitle(),
			"New Title", true, serviceContext);

		WikiPage renamedPage = WikiPageLocalServiceUtil.getPage(
			_node.getNodeId(), "New Title");

		Assert.assertNotNull(renamedPage);

		String[] finalAssetTagNames = AssetTagLocalServiceUtil.getTagNames(
			WikiPage.class.getName(), renamedPage.getResourcePrimKey());

		Assert.assertArrayEquals(finalAssetTagNames, assetTagNames);
	}

	@Test
	public void testRenameNonDefaultVersionPageWithAssetCategories()
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		AssetVocabulary assetVocabulary = AssetTestUtil.addVocabulary(
			_group.getGroupId());

		AssetCategory assetCategory1 = AssetTestUtil.addCategory(
			_group.getGroupId(), assetVocabulary.getVocabularyId());
		AssetCategory assetCategory2 = AssetTestUtil.addCategory(
			_group.getGroupId(), assetVocabulary.getVocabularyId());

		long[] assetCategoryIds = new long[2];

		assetCategoryIds[0] = assetCategory1.getCategoryId();
		assetCategoryIds[1] = assetCategory2.getCategoryId();

		serviceContext.setAssetCategoryIds(assetCategoryIds);

		WikiPage page = WikiTestUtil.addPage(
			TestPropsValues.getUserId(), _node.getNodeId(),
			RandomTestUtil.randomString(), RandomTestUtil.randomString(), true,
			serviceContext);

		WikiTestUtil.updatePage(page);

		serviceContext = ServiceContextTestUtil.getServiceContext(
			_group.getGroupId());

		WikiPageLocalServiceUtil.renamePage(
			TestPropsValues.getUserId(), _node.getNodeId(), page.getTitle(),
			"New Title", true, serviceContext);

		WikiPage renamedPage = WikiPageLocalServiceUtil.getPage(
			_node.getNodeId(), "New Title");

		long[] finalAssetCategoryIds =
			AssetCategoryLocalServiceUtil.getCategoryIds(
				WikiPage.class.getName(), renamedPage.getResourcePrimKey());

		Assert.assertArrayEquals(assetCategoryIds, finalAssetCategoryIds);
	}

	@Test
	public void testRenameNonDefaultVersionPageWithAssetTags()
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		AssetTag assetTag1 = AssetTestUtil.addTag(_group.getGroupId());
		AssetTag assetTag2 = AssetTestUtil.addTag(_group.getGroupId());

		String[] assetTagNames = new String[2];

		assetTagNames[0] = assetTag1.getName();
		assetTagNames[1] = assetTag2.getName();

		serviceContext.setAssetTagNames(assetTagNames);

		WikiPage page = WikiTestUtil.addPage(
			TestPropsValues.getUserId(), _node.getNodeId(),
			RandomTestUtil.randomString(), RandomTestUtil.randomString(), true,
			serviceContext);

		WikiTestUtil.updatePage(page);

		serviceContext = ServiceContextTestUtil.getServiceContext(
			_group.getGroupId());

		WikiPageLocalServiceUtil.renamePage(
			TestPropsValues.getUserId(), _node.getNodeId(), page.getTitle(),
			"New Title", true, serviceContext);

		WikiPage renamedPage = WikiPageLocalServiceUtil.getPage(
			_node.getNodeId(), "New Title");

		String[] finalAssetTagNames = AssetTagLocalServiceUtil.getTagNames(
			WikiPage.class.getName(), renamedPage.getResourcePrimKey());

		Assert.assertArrayEquals(finalAssetTagNames, assetTagNames);
	}

	@Test
	public void testRenamePage() throws Exception {
		WikiPage page = WikiTestUtil.addPage(
			_group.getGroupId(), _node.getNodeId(), true);

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		WikiPageLocalServiceUtil.renamePage(
			TestPropsValues.getUserId(), _node.getNodeId(), page.getTitle(),
			"New Title", true, serviceContext);

		WikiPage renamedPage = WikiPageLocalServiceUtil.getPage(
			_node.getNodeId(), "New Title");

		Assert.assertNotNull(renamedPage);

		checkPopulatedServiceContext(serviceContext, renamedPage, false);
	}

	@Test(expected = DuplicatePageException.class)
	public void testRenamePageSameName() throws Exception {
		WikiPage page = WikiTestUtil.addPage(
			_group.getGroupId(), _node.getNodeId(), true);

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		WikiPageLocalServiceUtil.renamePage(
			TestPropsValues.getUserId(), _node.getNodeId(), page.getTitle(),
			page.getTitle(), true, serviceContext);
	}

	@Test
	public void testRenamePageWithExpando() throws Exception {
		WikiPage page = WikiTestUtil.addPage(
			_group.getGroupId(), _node.getNodeId(), true);

		addExpandoValueToPage(page);

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		WikiPageLocalServiceUtil.renamePage(
			TestPropsValues.getUserId(), _node.getNodeId(), page.getTitle(),
			"New Title", true, serviceContext);

		WikiPage renamedPage = WikiPageLocalServiceUtil.getPage(
			_node.getNodeId(), "New Title");

		Assert.assertNotNull(renamedPage);

		checkPopulatedServiceContext(serviceContext, renamedPage, true);
	}

	@Test
	public void testRenamePageWithNbspTitle() throws Exception {
		WikiPage page = WikiTestUtil.addPage(
			_group.getGroupId(), _node.getNodeId(), true);

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		WikiPageLocalServiceUtil.renamePage(
			TestPropsValues.getUserId(), _node.getNodeId(), page.getTitle(),
			"New" + CharPool.NO_BREAK_SPACE + "Title", true, serviceContext);

		WikiPageLocalServiceUtil.getPage(_node.getNodeId(), "New Title");
	}

	@Test
	public void testRenameRenamedPage() throws Exception {
		WikiTestUtil.addPage(
			TestPropsValues.getUserId(), _group.getGroupId(), _node.getNodeId(),
			"A", true);

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		WikiPageLocalServiceUtil.renamePage(
			TestPropsValues.getUserId(), _node.getNodeId(), "A", "B", true,
			serviceContext);

		WikiPageLocalServiceUtil.renamePage(
			TestPropsValues.getUserId(), _node.getNodeId(), "A", "C", true,
			serviceContext);

		WikiPage pageA = WikiPageLocalServiceUtil.getPage(
			_node.getNodeId(), "A");
		WikiPage pageB = WikiPageLocalServiceUtil.getPage(
			_node.getNodeId(), "B");
		WikiPage pageC = WikiPageLocalServiceUtil.getPage(
			_node.getNodeId(), "C");

		Assert.assertEquals("C", pageA.getRedirectTitle());
		Assert.assertEquals(StringPool.BLANK, pageB.getRedirectTitle());
		Assert.assertEquals(StringPool.BLANK, pageC.getRedirectTitle());
		Assert.assertEquals("Renamed as C", pageA.getSummary());
		Assert.assertEquals("Summary", pageB.getSummary());
		Assert.assertEquals(StringPool.BLANK, pageC.getSummary());
		Assert.assertEquals("[[C]]", pageA.getContent());
		Assert.assertEquals("[[B]]", pageC.getContent());
	}

	@Test
	public void testRestorePageFromTrash() throws Exception {
		testRestorePageFromTrash(false);
	}

	@Test
	public void testRestorePageFromTrashWithExpando() throws Exception {
		testRestorePageFromTrash(true);
	}

	@Test
	public void testRevertPage() throws Exception {
		testRevertPage(false);
	}

	@Test
	public void testRevertPageWithExpando() throws Exception {
		testRevertPage(true);
	}

	protected void addExpandoValueToPage(WikiPage page) throws Exception {
		ExpandoValue value = ExpandoTestUtil.addValue(
			PortalUtil.getClassNameId(WikiPage.class), page.getPrimaryKey(),
			RandomTestUtil.randomString());

		ExpandoBridge expandoBridge = page.getExpandoBridge();

		ExpandoColumn column = value.getColumn();

		expandoBridge.addAttribute(
			column.getName(), ExpandoColumnConstants.STRING, value.getString());
	}

	protected void checkPopulatedServiceContext(
			ServiceContext serviceContext, WikiPage page,
			boolean hasExpandoValues)
		throws Exception {

		long[] assetCategoryIds = AssetCategoryLocalServiceUtil.getCategoryIds(
			WikiPage.class.getName(), page.getResourcePrimKey());

		Assert.assertArrayEquals(
			serviceContext.getAssetCategoryIds(), assetCategoryIds);

		AssetEntry assetEntry = AssetEntryLocalServiceUtil.getEntry(
			WikiPage.class.getName(), page.getResourcePrimKey());

		List<AssetLink> assetLinks = AssetLinkLocalServiceUtil.getLinks(
			assetEntry.getEntryId());

		long[] assetLinkEntryIds = ListUtil.toLongArray(
			assetLinks, AssetLink.ENTRY_ID2_ACCESSOR);

		Assert.assertArrayEquals(
			serviceContext.getAssetLinkEntryIds(), assetLinkEntryIds);

		String[] assetTagNames = AssetTagLocalServiceUtil.getTagNames(
			WikiPage.class.getName(), page.getResourcePrimKey());

		Assert.assertArrayEquals(
			serviceContext.getAssetTagNames(), assetTagNames);

		if (hasExpandoValues) {
			ExpandoBridge expandoBridge = page.getExpandoBridge();

			AssertUtils.assertEquals(
				expandoBridge.getAttributes(),
				serviceContext.getExpandoBridgeAttributes());
		}
	}

	protected AssetVocabulary getRequiredAssetVocabulary()
		throws PortalException {

		AssetVocabulary assetVocabulary =
			AssetVocabularyLocalServiceUtil.addDefaultVocabulary(
				_group.getGroupId());

		long classNameId = PortalUtil.getClassNameId(WikiPage.class.getName());

		StringBundler sb = new StringBundler(5);

		sb.append("multiValued=true\nrequiredClassNameIds=");
		sb.append(classNameId);
		sb.append(":-1\nselectedClassNameIds=");
		sb.append(classNameId);
		sb.append(":-1");

		assetVocabulary.setSettings(sb.toString());

		AssetVocabularyLocalServiceUtil.updateAssetVocabulary(assetVocabulary);

		return assetVocabulary;
	}

	protected void testChangeParent(boolean hasExpandoValues) throws Exception {
		WikiPage page = WikiTestUtil.addPage(
			_group.getGroupId(), _node.getNodeId(), true);

		if (hasExpandoValues) {
			addExpandoValueToPage(page);
		}

		WikiPage parentPage = WikiTestUtil.addPage(
			_group.getGroupId(), _node.getNodeId(), true);

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		WikiPageLocalServiceUtil.changeParent(
			TestPropsValues.getUserId(), _node.getNodeId(), page.getTitle(),
			parentPage.getTitle(), serviceContext);

		WikiPage retrievedPage = WikiPageLocalServiceUtil.getPage(
			page.getResourcePrimKey());

		checkPopulatedServiceContext(
			serviceContext, retrievedPage, hasExpandoValues);
	}

	protected void testRestorePageFromTrash(boolean hasExpandoValues)
		throws Exception {

		WikiPage page = WikiTestUtil.addPage(
			_group.getGroupId(), _node.getNodeId(), true);

		if (hasExpandoValues) {
			addExpandoValueToPage(page);
		}

		page = WikiPageLocalServiceUtil.movePageToTrash(
			TestPropsValues.getUserId(), _node.getNodeId(), page.getTitle());

		WikiPageLocalServiceUtil.restorePageFromTrash(
			TestPropsValues.getUserId(), page);

		WikiPage restoredPage = WikiPageLocalServiceUtil.getPage(
			page.getResourcePrimKey());

		Assert.assertNotNull(restoredPage);

		if (hasExpandoValues) {
			ExpandoBridge expandoBridge = page.getExpandoBridge();

			ExpandoBridge restoredExpandoBridge =
				restoredPage.getExpandoBridge();

			AssertUtils.assertEquals(
				restoredExpandoBridge.getAttributes(),
				expandoBridge.getAttributes());
		}
	}

	protected void testRevertPage(boolean hasExpandoValues) throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		String originalContent = RandomTestUtil.randomString();

		WikiPage originalPage = WikiTestUtil.addPage(
			TestPropsValues.getUserId(), _node.getNodeId(),
			RandomTestUtil.randomString(), originalContent, true,
			serviceContext);

		if (hasExpandoValues) {
			addExpandoValueToPage(originalPage);
		}

		WikiPage updatedPage1 = WikiTestUtil.updatePage(
			originalPage, TestPropsValues.getUserId(),
			originalContent + "\nAdded second line.", serviceContext);

		Assert.assertNotEquals(originalContent, updatedPage1.getContent());

		WikiPage updatedPage2 = WikiTestUtil.updatePage(
			updatedPage1, TestPropsValues.getUserId(),
			updatedPage1.getContent() + "\nAdded third line.", serviceContext);

		Assert.assertNotEquals(originalContent, updatedPage2.getContent());

		WikiPage revertedPage = WikiPageLocalServiceUtil.revertPage(
			TestPropsValues.getUserId(), _node.getNodeId(),
			updatedPage2.getTitle(), originalPage.getVersion(), serviceContext);

		Assert.assertEquals(revertedPage.getContent(), originalContent);

		checkPopulatedServiceContext(
			serviceContext, revertedPage, hasExpandoValues);
	}

	@DeleteAfterTestRun
	private Group _group;

	private WikiNode _node;

}