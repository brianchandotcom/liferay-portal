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

package com.liferay.portlet.wiki.service;

import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.test.AssertUtils;
import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.model.Group;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.test.DeleteAfterTestRun;
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.test.MainServletExecutionTestListener;
import com.liferay.portal.test.Sync;
import com.liferay.portal.test.SynchronousDestinationExecutionTestListener;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.test.GroupTestUtil;
import com.liferay.portal.util.test.RandomTestUtil;
import com.liferay.portal.util.test.ServiceContextTestUtil;
import com.liferay.portal.util.test.TestPropsValues;
import com.liferay.portlet.asset.model.AssetEntry;
import com.liferay.portlet.asset.model.AssetLink;
import com.liferay.portlet.asset.service.AssetCategoryLocalServiceUtil;
import com.liferay.portlet.asset.service.AssetEntryLocalServiceUtil;
import com.liferay.portlet.asset.service.AssetLinkLocalServiceUtil;
import com.liferay.portlet.asset.service.AssetTagLocalServiceUtil;
import com.liferay.portlet.expando.model.ExpandoBridge;
import com.liferay.portlet.expando.model.ExpandoColumn;
import com.liferay.portlet.expando.model.ExpandoColumnConstants;
import com.liferay.portlet.expando.model.ExpandoValue;
import com.liferay.portlet.expando.util.test.ExpandoTestUtil;
import com.liferay.portlet.wiki.DuplicatePageException;
import com.liferay.portlet.wiki.NoSuchPageResourceException;
import com.liferay.portlet.wiki.model.WikiNode;
import com.liferay.portlet.wiki.model.WikiPage;
import com.liferay.portlet.wiki.util.test.WikiTestUtil;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.testng.Assert;

/**
 * @author Manuel de la Peña
 * @author Roberto Díaz
 */
@ExecutionTestListeners(
	listeners = {
		MainServletExecutionTestListener.class,
		SynchronousDestinationExecutionTestListener.class
	})
@RunWith(LiferayIntegrationJUnitTestRunner.class)
@Sync
public class WikiPageLocalServiceTest {

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		_node = WikiTestUtil.addNode(_group.getGroupId());
	}

	@Test
	public void testChangeParent() throws Exception {
		testChangeParent(false);
	}

	@Test
	public void testChangeParentWithExpando() throws Exception {
		testChangeParent(true);
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
			attachmentsFileEntries.size(), copyAttachmentsFileEntries.size());

		FileEntry fileEntry = attachmentsFileEntries.get(0);
		FileEntry copyFileEntry = copyAttachmentsFileEntries.get(0);

		Assert.assertEquals(
			fileEntry.getExtension(), copyFileEntry.getExtension());
		Assert.assertEquals(
			fileEntry.getMimeType(), copyFileEntry.getMimeType());
		Assert.assertEquals(fileEntry.getTitle(), copyFileEntry.getTitle());
		Assert.assertEquals(fileEntry.getSize(), copyFileEntry.getSize());
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
	public void testDeleteTrashedPageWithExplicitTrashedRedirectPage()
		throws Exception {

		WikiPage[] pages = WikiTestUtil.addTrashedPageWithRedirectPage(
			_group.getGroupId(), _node.getNodeId(), true);

		WikiPage page = pages[0];
		WikiPage redirectPage = pages[1];

		WikiPageLocalServiceUtil.deletePage(page);

		try {
			WikiPageLocalServiceUtil.getPage(page.getResourcePrimKey());

			Assert.fail("Page should be deleted");
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

		WikiPage[] pages = WikiTestUtil.addTrashedPageWithRedirectPage(
			_group.getGroupId(), _node.getNodeId(), false);

		WikiPage page = pages[0];
		WikiPage redirectPage = pages[1];

		WikiPageLocalServiceUtil.deletePage(page);

		try {
			WikiPageLocalServiceUtil.getPage(page.getResourcePrimKey());

			Assert.fail("Page should be deleted");
		}
		catch (NoSuchPageResourceException nsrpe) {
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

			Assert.fail("Parent page should be deleted");
		}
		catch (NoSuchPageResourceException nspre) {
			childPage = WikiPageLocalServiceUtil.getPage(
				childPage.getResourcePrimKey());

			Assert.assertNull(childPage.fetchParentPage());
			Assert.assertEquals(
				childPage.getStatus(), WorkflowConstants.STATUS_APPROVED);
		}
	}

	@Test
	public void testDeleteTrashedPageWithRestoredRedirectPage()
		throws Exception {

		WikiPage[] pages = WikiTestUtil.addTrashedPageWithRedirectPage(
			_group.getGroupId(), _node.getNodeId(), true);

		WikiPage page = pages[0];
		WikiPage redirectPage = pages[1];

		WikiPageLocalServiceUtil.restorePageFromTrash(
			TestPropsValues.getUserId(), redirectPage);

		WikiPageLocalServiceUtil.deletePage(page);

		try {
			WikiPageLocalServiceUtil.getPage(page.getResourcePrimKey());

			Assert.fail("Page should be deleted");
		}
		catch (NoSuchPageResourceException nspre) {
			redirectPage = WikiPageLocalServiceUtil.getPageByPageId(
				redirectPage.getPageId());

			Assert.assertNull(redirectPage.fetchRedirectPage());
			Assert.assertEquals(
				redirectPage.getStatus(), WorkflowConstants.STATUS_APPROVED);
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

			Assert.fail("Parent page should be deleted");
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

			Assert.fail("Parent page should be deleted");
		}
		catch (NoSuchPageResourceException nspre) {
			WikiPageLocalServiceUtil.getPage(childPage.getResourcePrimKey());
		}
	}

	@Test
	public void testGetPage() throws Exception {
		WikiPage page = WikiTestUtil.addPage(
			_group.getGroupId(), _node.getNodeId(), true);

		WikiPage retrievedPage = WikiPageLocalServiceUtil.getPage(
			page.getResourcePrimKey());

		Assert.assertEquals(page.getPageId(), retrievedPage.getPageId());
	}

	@Test
	public void testMoveMovedPage() throws Exception {
		WikiTestUtil.addPage(
			TestPropsValues.getUserId(), _group.getGroupId(), _node.getNodeId(),
			"A", true);

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		WikiPageLocalServiceUtil.movePage(
			TestPropsValues.getUserId(), _node.getNodeId(), "A", "B", true,
			serviceContext);

		WikiPageLocalServiceUtil.movePage(
			TestPropsValues.getUserId(), _node.getNodeId(), "A", "C", true,
			serviceContext);

		WikiPage pageA = WikiPageLocalServiceUtil.getPage(
			_node.getNodeId(), "A");
		WikiPage pageB = WikiPageLocalServiceUtil.getPage(
			_node.getNodeId(), "B");
		WikiPage pageC = WikiPageLocalServiceUtil.getPage(
			_node.getNodeId(), "C");

		Assert.assertEquals(pageA.getRedirectTitle(), "C");
		Assert.assertEquals(pageB.getRedirectTitle(), StringPool.BLANK);
		Assert.assertEquals(pageC.getRedirectTitle(), StringPool.BLANK);
		Assert.assertEquals(pageA.getSummary(), "Moved to C");
		Assert.assertEquals(pageB.getSummary(), "Summary");
		Assert.assertEquals(pageC.getSummary(), StringPool.BLANK);
		Assert.assertEquals(pageA.getContent(), "[[C]]");
		Assert.assertEquals(pageC.getContent(), "[[B]]");
	}

	@Test
	public void testMovePage() throws Exception {
		testMovePage(false);
	}

	@Test(expected = DuplicatePageException.class)
	public void testMovePageSameName() throws Exception {
		WikiPage page = WikiTestUtil.addPage(
			_group.getGroupId(), _node.getNodeId(), true);

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		WikiPageLocalServiceUtil.movePage(
			TestPropsValues.getUserId(), _node.getNodeId(), page.getTitle(),
			page.getTitle(), true, serviceContext);
	}

	@Test
	public void testMovePageWithExpando() throws Exception {
		testMovePage(true);
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

		Assert.assertEquals(
			assetCategoryIds, serviceContext.getAssetCategoryIds());

		AssetEntry assetEntry = AssetEntryLocalServiceUtil.getEntry(
			WikiPage.class.getName(), page.getResourcePrimKey());

		List<AssetLink> assetLinks = AssetLinkLocalServiceUtil.getLinks(
			assetEntry.getEntryId());

		long[] assetLinkEntryIds = StringUtil.split(
			ListUtil.toString(assetLinks, AssetLink.ENTRY_ID2_ACCESSOR), 0L);

		Assert.assertEquals(
			assetLinkEntryIds, serviceContext.getAssetLinkEntryIds());

		String[] assetTagNames = AssetTagLocalServiceUtil.getTagNames(
			WikiPage.class.getName(), page.getResourcePrimKey());

		Assert.assertEquals(assetTagNames, serviceContext.getAssetTagNames());

		if (hasExpandoValues) {
			ExpandoBridge expandoBridge = page.getExpandoBridge();

			AssertUtils.assertEquals(
				serviceContext.getExpandoBridgeAttributes(),
				expandoBridge.getAttributes());
		}
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

	protected void testMovePage(boolean hasExpandoValues) throws Exception {
		WikiPage page = WikiTestUtil.addPage(
			_group.getGroupId(), _node.getNodeId(), true);

		if (hasExpandoValues) {
			addExpandoValueToPage(page);
		}

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		WikiPageLocalServiceUtil.movePage(
			TestPropsValues.getUserId(), _node.getNodeId(), page.getTitle(),
			"New Title", true, serviceContext);

		WikiPage movedPage = WikiPageLocalServiceUtil.getPage(
			_node.getNodeId(), "New Title");

		Assert.assertNotNull(movedPage);

		checkPopulatedServiceContext(
			serviceContext, movedPage, hasExpandoValues);
	}

	protected void testRestorePageFromTrash(boolean hasExpandoValues)
		throws Exception {

		WikiPage page = WikiTestUtil.addPage(
			_group.getGroupId(), _node.getNodeId(), true);

		if (hasExpandoValues) {
			addExpandoValueToPage(page);
		}

		WikiPageLocalServiceUtil.movePageToTrash(
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
				expandoBridge.getAttributes(),
				restoredExpandoBridge.getAttributes());
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

		Assert.assertEquals(originalContent, revertedPage.getContent());

		checkPopulatedServiceContext(
			serviceContext, revertedPage, hasExpandoValues);
	}

	@DeleteAfterTestRun
	private Group _group;

	private WikiNode _node;

}