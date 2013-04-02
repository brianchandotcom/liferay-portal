/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.wiki.attachments;

import com.liferay.portal.kernel.dao.orm.FinderCacheUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.MimeTypesUtil;
import com.liferay.portal.model.Group;
import com.liferay.portal.portletfilerepository.PortletFileRepositoryUtil;
import com.liferay.portal.service.ServiceTestUtil;
import com.liferay.portal.test.EnvironmentExecutionTestListener;
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.util.GroupTestUtil;
import com.liferay.portal.util.TestPropsValues;
import com.liferay.portlet.documentlibrary.service.DLFileEntryLocalServiceUtil;
import com.liferay.portlet.documentlibrary.service.DLFolderLocalServiceUtil;
import com.liferay.portlet.wiki.model.WikiNode;
import com.liferay.portlet.wiki.model.WikiPage;
import com.liferay.portlet.wiki.service.WikiNodeLocalServiceUtil;
import com.liferay.portlet.wiki.service.WikiPageLocalServiceUtil;
import com.liferay.portlet.wiki.util.WikiTestUtil;

import java.io.File;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Eudaldo Alonso
 * @author Roberto Díaz
 */
@ExecutionTestListeners(listeners = {EnvironmentExecutionTestListener.class})
@RunWith(LiferayIntegrationJUnitTestRunner.class)
public class WikiAttachmentsTest {

	public static final int NEW_FILE_ENTRY = 1;
	public static final int NEW_NODE_FOLDER = 1;
	public static final int NEW_PAGE_FOLDER = 1;
	public static final int NEW_REPOSITORY_FOLDER = 1;

	@Before
	public void setUp() throws Exception {
		FinderCacheUtil.clearCache();

		_group = GroupTestUtil.addGroup();
	}

	@After
	public void tearDown() {
		_group = null;
		_node  =null;
		_page = null;
	}

	@Test
	@Transactional
	public void testAddSameWikiPageTwoAttachments() throws Exception {
		int initialFolderCount = DLFolderLocalServiceUtil.getDLFoldersCount();

		int newFolders =
			NEW_REPOSITORY_FOLDER + NEW_NODE_FOLDER + NEW_PAGE_FOLDER;

		addWikiPageAttachment();

		int firstFolderCount = DLFolderLocalServiceUtil.getDLFoldersCount();

		addWikiPageAttachment();

		int finalFolderCount = DLFolderLocalServiceUtil.getDLFoldersCount();

		Assert.assertEquals(initialFolderCount + newFolders, firstFolderCount);
		Assert.assertEquals(firstFolderCount, finalFolderCount);
	}

	@Test
	@Transactional
	public void testAddWikiNode() throws Exception {
		int expectedFolderCount = DLFolderLocalServiceUtil.getDLFoldersCount();

		addWikiNode();
		int currentFolderCount = DLFolderLocalServiceUtil.getDLFoldersCount();

		Assert.assertNotNull(_node);
		Assert.assertEquals(expectedFolderCount, currentFolderCount);
	}

	@Test
	@Transactional
	public void testAddWikiPage() throws Exception {
		int expectedFolderCount = DLFolderLocalServiceUtil.getDLFoldersCount();

		addWikiPage();
		int currentFolderCount = DLFolderLocalServiceUtil.getDLFoldersCount();

		Assert.assertNotNull(_page);
		Assert.assertEquals(expectedFolderCount, currentFolderCount);
	}

	@Test
	@Transactional
	public void testAddWikiPageAttachments() throws Exception {
		int initialFolderCount = DLFolderLocalServiceUtil.getDLFoldersCount();

		int newFolders =
			NEW_REPOSITORY_FOLDER + NEW_NODE_FOLDER + NEW_PAGE_FOLDER;

		int firstFolderCount = assertAttachFileToPage1InNode1FromGroup1(
			initialFolderCount + newFolders);

		int totalNewFolders = newFolders;

		int secondFolderCount = assertAttachFileToPage2InNode1FromGroup1(
			firstFolderCount + NEW_PAGE_FOLDER);

		totalNewFolders += NEW_PAGE_FOLDER;

		newFolders = NEW_NODE_FOLDER + NEW_PAGE_FOLDER;

		int thirdFolderCount = assertAttachFileToPage2InNode2FromGroup1(
			secondFolderCount + newFolders);

		totalNewFolders += newFolders;

		newFolders = NEW_REPOSITORY_FOLDER + NEW_NODE_FOLDER + NEW_PAGE_FOLDER;

		int finalFolderCount = assertAttachFileToPage1InNode1FromGroup2(
			thirdFolderCount + newFolders );

		totalNewFolders += newFolders;

		Assert.assertEquals(
			initialFolderCount + totalNewFolders, finalFolderCount);
	}

	@Test
	@Transactional
	public void testDeleteAttachmentWhenDeletingWikiNode() throws Exception {
		int initialFileEntryCount =
			DLFileEntryLocalServiceUtil.getFileEntriesCount();

		addWikiPageAttachment();

		int firstFileEntryCount =
			DLFileEntryLocalServiceUtil.getFileEntriesCount();

		Assert.assertEquals(
			initialFileEntryCount + NEW_FILE_ENTRY, firstFileEntryCount);

		WikiNodeLocalServiceUtil.deleteNode(_page.getNodeId());

		int finalFileEntryCount =
			DLFileEntryLocalServiceUtil.getFileEntriesCount();

		Assert.assertEquals(
			firstFileEntryCount - NEW_FILE_ENTRY, finalFileEntryCount);
	}

	@Test
	@Transactional
	public void testDeleteAttachmentWhenDeletingWikiPage() throws Exception {
		int initialFileEntryCount =
			DLFileEntryLocalServiceUtil.getFileEntriesCount();

		addWikiPageAttachment();

		int firstFileEntryCount =
			DLFileEntryLocalServiceUtil.getFileEntriesCount();

		Assert.assertEquals(
			initialFileEntryCount + NEW_FILE_ENTRY, firstFileEntryCount);

		WikiPageLocalServiceUtil.deletePage(
			_page.getNodeId(), _page.getTitle());

		int finalFileEntryCount =
			DLFileEntryLocalServiceUtil.getFileEntriesCount();

		Assert.assertEquals(
			firstFileEntryCount - NEW_FILE_ENTRY, finalFileEntryCount);
	}

	@Test
	@Transactional
	public void testDeleteWikiNodeWithAttachments() throws Exception {
		int initialFolderCount = DLFolderLocalServiceUtil.getDLFoldersCount();

		int initialWikiNodeCount = WikiNodeLocalServiceUtil.getNodesCount(
			_group.getGroupId());

		addWikiPageAttachment();

		int firstFolderCount = DLFolderLocalServiceUtil.getDLFoldersCount();

		int newFolders =
			NEW_REPOSITORY_FOLDER + NEW_NODE_FOLDER + NEW_PAGE_FOLDER;

		Assert.assertEquals(initialFolderCount + newFolders, firstFolderCount);

		WikiNodeLocalServiceUtil.deleteNode(_page.getNodeId());

		int finalFolderCount = DLFolderLocalServiceUtil.getDLFoldersCount();
		int finalWikiNodeCount = WikiNodeLocalServiceUtil.getNodesCount(
			_group.getGroupId());

		int expectedFolderCount =
			firstFolderCount - (NEW_PAGE_FOLDER + NEW_NODE_FOLDER);

		Assert.assertEquals(expectedFolderCount, finalFolderCount);
		Assert.assertEquals(initialWikiNodeCount, finalWikiNodeCount);
	}

	@Test
	@Transactional
	public void testDeleteWikiPageWithAttachments() throws Exception {
		int initialFolderCount = DLFolderLocalServiceUtil.getDLFoldersCount();

		addWikiNode();

		int initialWikiPageCount = WikiPageLocalServiceUtil.getPagesCount(
			_node.getNodeId());

		addWikiPageAttachment();

		int firstFolderCount = DLFolderLocalServiceUtil.getDLFoldersCount();

		int newFolders =
			NEW_REPOSITORY_FOLDER + NEW_NODE_FOLDER + NEW_PAGE_FOLDER;

		Assert.assertEquals(initialFolderCount + newFolders, firstFolderCount);

		WikiPageLocalServiceUtil.deletePage(
			_page.getNodeId(), _page.getTitle());

		int finalFolderCount = DLFolderLocalServiceUtil.getDLFoldersCount();
		int finalWikiPageCount = WikiPageLocalServiceUtil.getPagesCount(
			_node.getNodeId());

		Assert.assertEquals(
			firstFolderCount - NEW_PAGE_FOLDER, finalFolderCount);
		Assert.assertEquals(initialWikiPageCount, finalWikiPageCount);
	}

	@Test
	@Transactional
	public void testDeleteWikiPageWithoutAttachments() throws Exception {
		int initialFolderCount = DLFolderLocalServiceUtil.getDLFoldersCount();

		addWikiNode();

		int initialWikiPageCount = WikiPageLocalServiceUtil.getPagesCount(
			_node.getNodeId());

		addWikiPage();

		int firstFolderCount = DLFolderLocalServiceUtil.getDLFoldersCount();

		Assert.assertEquals(initialFolderCount, firstFolderCount);

		WikiPageLocalServiceUtil.deletePage(
			_page.getNodeId(), _page.getTitle());

		int finalFolderCount = DLFolderLocalServiceUtil.getDLFoldersCount();
		int finalWikiPageCount = WikiPageLocalServiceUtil.getPagesCount(
			_node.getNodeId());

		Assert.assertEquals(initialFolderCount, finalFolderCount);
		Assert.assertEquals(initialWikiPageCount, finalWikiPageCount);
	}

	@Test
	public void testTrashAndDelete() throws Exception {
		addWikiPage();

		_trashWikiAttachments(false);
	}

	@Test
	public void testTrashAndRestore() throws Exception {
		addWikiPage();

		_trashWikiAttachments(true);
	}

	protected void addWikiNode() throws Exception {
		if (_group == null) {
			_group = GroupTestUtil.addGroup();
		}

		_node = WikiTestUtil.addNode(
			TestPropsValues.getUserId(), _group.getGroupId(),
			ServiceTestUtil.randomString(), ServiceTestUtil.randomString(50));
	}

	protected void addWikiPage() throws Exception {
		if (_node == null) {
			addWikiNode();
		}

		_page =  WikiTestUtil.addPage(
			_node.getUserId(), _group.getGroupId(), _node.getNodeId(),
			ServiceTestUtil.randomString(), true);
	}

	protected void addWikiPageAttachment() throws Exception {
		if (_page == null) {
			addWikiPage();
		}

		WikiTestUtil.addWikiAttachment(
			_page.getUserId(), _page.getNodeId(), _page.getTitle(), getClass());
	}

	protected int assertAttachFile(int expectedFolderCount) throws Exception {
		addWikiPageAttachment();

		int currentFolderCount = DLFolderLocalServiceUtil.getDLFoldersCount();

		Assert.assertEquals(expectedFolderCount, currentFolderCount );

		return currentFolderCount;
	}

	protected int assertAttachFileToPage1InNode1FromGroup1(
			int expectedFolderCount)
		throws Exception {

		return assertAttachFile(expectedFolderCount);
	}

	protected int assertAttachFileToPage1InNode1FromGroup2(
			int expectedFolderCount)
		throws Exception {

		_group = null;
		_page = null;
		_node = null;

		return assertAttachFile(expectedFolderCount);
	}

	protected int assertAttachFileToPage2InNode1FromGroup1(
			int expectedFolderCount)
		throws Exception {

		_page = null;

		return assertAttachFile(expectedFolderCount);
	}

	protected int assertAttachFileToPage2InNode2FromGroup1(
			int expectedFolderCount)
		throws Exception {

		_page = null;
		_node = null;

		return assertAttachFile(expectedFolderCount);
	}

	private void _trashWikiAttachments(boolean restore) throws Exception {
		int initialNotInTrashCount = _page.getAttachmentsFileEntriesCount();
		int initialTrashEntriesCount =
			_page.getDeletedAttachmentsFileEntriesCount();

		Class<?> clazz = getClass();

		String fileName = ServiceTestUtil.randomString() + ".docx";

		byte[] fileBytes = FileUtil.getBytes(
			clazz.getResourceAsStream("dependencies/OSX_Test.docx"));

		File file = null;

		if ((fileBytes != null) && (fileBytes.length > 0)) {
			file = FileUtil.createTempFile(fileBytes);
		}

		String mimeType = MimeTypesUtil.getExtensionContentType("docx");

		WikiPageLocalServiceUtil.addPageAttachment(
			TestPropsValues.getUserId(), _node.getNodeId(), _page.getTitle(),
			fileName, file, mimeType);

		Assert.assertEquals(
			initialNotInTrashCount + NEW_FILE_ENTRY,
			_page.getAttachmentsFileEntriesCount());
		Assert.assertEquals(
			initialTrashEntriesCount,
			_page.getDeletedAttachmentsFileEntriesCount());

		long fileEntryId = WikiPageLocalServiceUtil.movePageAttachmentToTrash(
			TestPropsValues.getUserId(), _page.getNodeId(), _page.getTitle(),
			fileName);

		FileEntry fileEntry = PortletFileRepositoryUtil.getPortletFileEntry(
			fileEntryId);

		Assert.assertEquals(
			initialNotInTrashCount, _page.getAttachmentsFileEntriesCount());
		Assert.assertEquals(
			initialTrashEntriesCount + NEW_FILE_ENTRY,
			_page.getDeletedAttachmentsFileEntriesCount());

		if (restore) {
			WikiPageLocalServiceUtil.restorePageAttachmentFromTrash(
				TestPropsValues.getUserId(), _page.getNodeId(),
				_page.getTitle(), fileEntry.getTitle());

			Assert.assertEquals(
				initialNotInTrashCount + NEW_FILE_ENTRY,
				_page.getAttachmentsFileEntriesCount());
			Assert.assertEquals(
				initialTrashEntriesCount,
				_page.getDeletedAttachmentsFileEntriesCount());

			WikiPageLocalServiceUtil.deletePageAttachment(
				_page.getNodeId(), _page.getTitle(), fileName);
		}
		else {
			WikiPageLocalServiceUtil.deletePageAttachment(
				_page.getNodeId(), _page.getTitle(), fileEntry.getTitle());

			Assert.assertEquals(
				initialNotInTrashCount, _page.getAttachmentsFileEntriesCount());
			Assert.assertEquals(
				initialTrashEntriesCount,
				_page.getDeletedAttachmentsFileEntriesCount());
		}
	}

	private Group _group;
	private WikiNode _node;
	private WikiPage _page;

}