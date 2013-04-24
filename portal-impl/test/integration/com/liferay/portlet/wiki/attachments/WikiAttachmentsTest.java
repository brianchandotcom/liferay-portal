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
import com.liferay.portal.model.Group;
import com.liferay.portal.portletfilerepository.PortletFileRepositoryUtil;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.ServiceTestUtil;
import com.liferay.portal.test.EnvironmentExecutionTestListener;
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.test.TransactionalExecutionTestListener;
import com.liferay.portal.util.GroupTestUtil;
import com.liferay.portal.util.TestPropsValues;
import com.liferay.portlet.documentlibrary.model.DLFileEntryConstants;
import com.liferay.portlet.documentlibrary.service.DLFileEntryLocalServiceUtil;
import com.liferay.portlet.documentlibrary.service.DLFolderLocalServiceUtil;
import com.liferay.portlet.trash.model.TrashEntry;
import com.liferay.portlet.trash.service.TrashEntryLocalServiceUtil;
import com.liferay.portlet.trash.service.TrashEntryServiceUtil;
import com.liferay.portlet.wiki.model.WikiNode;
import com.liferay.portlet.wiki.model.WikiPage;
import com.liferay.portlet.wiki.service.WikiNodeLocalServiceUtil;
import com.liferay.portlet.wiki.service.WikiPageLocalServiceUtil;
import com.liferay.portlet.wiki.util.WikiTestUtil;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Eudaldo Alonso
 * @author Roberto Díaz
 * @author Sergio González
 */
@ExecutionTestListeners(
	listeners = {
		EnvironmentExecutionTestListener.class,
		TransactionalExecutionTestListener.class
	})
@RunWith(LiferayIntegrationJUnitTestRunner.class)
public class WikiAttachmentsTest {

	@Before
	public void setUp() throws Exception {
		FinderCacheUtil.clearCache();

		_group = GroupTestUtil.addGroup();
	}

	@After
	public void tearDown() {
		_group = null;
		_node = null;
		_page = null;
	}

	@Test
	@Transactional
	public void testDeleteAttachmentsWhenDeletingWikiNode() throws Exception {
		int initialFileEntriesCount =
			DLFileEntryLocalServiceUtil.getFileEntriesCount();

		addWikiPageAttachment();

		Assert.assertEquals(
			initialFileEntriesCount + 1,
			DLFileEntryLocalServiceUtil.getFileEntriesCount());

		WikiNodeLocalServiceUtil.deleteNode(_page.getNodeId());

		Assert.assertEquals(
			initialFileEntriesCount,
			DLFileEntryLocalServiceUtil.getFileEntriesCount());
	}

	@Test
	@Transactional
	public void testDeleteAttachmentsWhenDeletingWikiPage() throws Exception {
		int initialFileEntriesCount =
			DLFileEntryLocalServiceUtil.getFileEntriesCount();

		addWikiPageAttachment();

		Assert.assertEquals(
			initialFileEntriesCount + 1,
			DLFileEntryLocalServiceUtil.getFileEntriesCount());

		WikiPageLocalServiceUtil.deletePage(
			_page.getNodeId(), _page.getTitle());

		Assert.assertEquals(
			initialFileEntriesCount,
			DLFileEntryLocalServiceUtil.getFileEntriesCount());
	}

	@Test
	@Transactional
	public void testFoldersCountWhenAddingAttachmentsToSameWikiPage()
		throws Exception {

		int initialFoldersCount = DLFolderLocalServiceUtil.getDLFoldersCount();

		addWikiPageAttachment();

		int foldersCount = DLFolderLocalServiceUtil.getDLFoldersCount();

		Assert.assertEquals(initialFoldersCount + 3, foldersCount);

		addWikiPageAttachment();

		foldersCount = DLFolderLocalServiceUtil.getDLFoldersCount();

		Assert.assertEquals(initialFoldersCount + 3, foldersCount);
	}

	@Test
	@Transactional
	public void testFoldersCountWhenAddingWikiNode() throws Exception {
		int initialFoldersCount = DLFolderLocalServiceUtil.getDLFoldersCount();

		addWikiNode();

		Assert.assertEquals(
			initialFoldersCount, DLFolderLocalServiceUtil.getDLFoldersCount());
	}

	@Test
	@Transactional
	public void testFoldersCountWhenAddingWikiPage() throws Exception {
		int initialFoldersCount = DLFolderLocalServiceUtil.getDLFoldersCount();

		addWikiPage();

		Assert.assertEquals(
			initialFoldersCount, DLFolderLocalServiceUtil.getDLFoldersCount());
	}

	@Test
	@Transactional
	public void testFoldersCountWhenAddingWikiPageAttachment()
		throws Exception {

		int initialFoldersCount = DLFolderLocalServiceUtil.getDLFoldersCount();

		addWikiPageAttachment();

		Assert.assertEquals(
			initialFoldersCount + 3,
			DLFolderLocalServiceUtil.getDLFoldersCount());
	}

	@Test
	@Transactional
	public void testFoldersCountWhenAddingWikiPageAttachments()
		throws Exception {

		int foldersCount = DLFolderLocalServiceUtil.getDLFoldersCount();

		addWikiPageAttachment();

		Assert.assertEquals(
			foldersCount + 3, DLFolderLocalServiceUtil.getDLFoldersCount());

		foldersCount = DLFolderLocalServiceUtil.getDLFoldersCount();

		_page = null;

		addWikiPageAttachment();

		Assert.assertEquals(
			foldersCount + 1, DLFolderLocalServiceUtil.getDLFoldersCount());

		foldersCount = DLFolderLocalServiceUtil.getDLFoldersCount();

		_node = null;
		_page = null;

		addWikiPageAttachment();

		Assert.assertEquals(
			foldersCount + 2, DLFolderLocalServiceUtil.getDLFoldersCount());

		foldersCount = DLFolderLocalServiceUtil.getDLFoldersCount();

		_group = null;
		_node = null;
		_page = null;

		addWikiPageAttachment();

		Assert.assertEquals(
			foldersCount + 3, DLFolderLocalServiceUtil.getDLFoldersCount());
	}

	@Test
	@Transactional
	public void testFoldersCountWhenDeletingWikiNodeWithAttachments()
		throws Exception {

		int initialFoldersCount = DLFolderLocalServiceUtil.getDLFoldersCount();

		addWikiPageAttachment();

		Assert.assertEquals(
			initialFoldersCount + 3,
			DLFolderLocalServiceUtil.getDLFoldersCount());

		WikiNodeLocalServiceUtil.deleteNode(_page.getNodeId());

		Assert.assertEquals(
			initialFoldersCount + 1,
			DLFolderLocalServiceUtil.getDLFoldersCount());
	}

	@Test
	@Transactional
	public void testFoldersCountWhenDeletingWikiNodeWithoutAttachments()
		throws Exception {

		int initialFoldersCount = DLFolderLocalServiceUtil.getDLFoldersCount();

		addWikiNode();

		Assert.assertEquals(
			initialFoldersCount, DLFolderLocalServiceUtil.getDLFoldersCount());

		WikiNodeLocalServiceUtil.deleteNode(_node.getNodeId());

		Assert.assertEquals(
			initialFoldersCount, DLFolderLocalServiceUtil.getDLFoldersCount());
	}

	@Test
	@Transactional
	public void testFoldersCountWhenDeletingWikiPageWithAttachments()
		throws Exception {

		int initialFoldersCount = DLFolderLocalServiceUtil.getDLFoldersCount();

		addWikiPageAttachment();

		Assert.assertEquals(
			initialFoldersCount + 3,
			DLFolderLocalServiceUtil.getDLFoldersCount());

		WikiPageLocalServiceUtil.deletePage(
			_page.getNodeId(), _page.getTitle());

		Assert.assertEquals(
			initialFoldersCount + 2,
			DLFolderLocalServiceUtil.getDLFoldersCount());
	}

	@Test
	@Transactional
	public void testFoldersCountWhenDeletingWikiPageWithoutAttachments()
		throws Exception {

		int initialFoldersCount = DLFolderLocalServiceUtil.getDLFoldersCount();

		addWikiPage();

		Assert.assertEquals(
			initialFoldersCount, DLFolderLocalServiceUtil.getDLFoldersCount());

		WikiPageLocalServiceUtil.deletePage(
			_page.getNodeId(), _page.getTitle());

		Assert.assertEquals(
			initialFoldersCount, DLFolderLocalServiceUtil.getDLFoldersCount());
	}

	@Test
	public void testMoveToTrashAndDeleteWikiPageAttachment() throws Exception {
		addWikiPage();

		_trashWikiAttachments(false);

		GroupLocalServiceUtil.deleteGroup(_group);
	}

	@Test
	public void testMoveToTrashAndRestoreWikiPageAttachment() throws Exception {
		addWikiPage();

		_trashWikiAttachments(true);

		GroupLocalServiceUtil.deleteGroup(_group);
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

		_page = WikiTestUtil.addPage(
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

	private void _trashWikiAttachments(boolean restore) throws Exception {
		int initialNotInTrashCount = _page.getAttachmentsFileEntriesCount();
		int initialTrashEntriesCount =
			_page.getDeletedAttachmentsFileEntriesCount();

		String fileName = ServiceTestUtil.randomString() + ".docx";

		WikiTestUtil.addWikiAttachment(
			TestPropsValues.getUserId(), _node.getNodeId(), _page.getTitle(),
			fileName, getClass());

		Assert.assertEquals(
			initialNotInTrashCount + 1, _page.getAttachmentsFileEntriesCount());
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
			initialTrashEntriesCount + 1,
			_page.getDeletedAttachmentsFileEntriesCount());

		if (restore) {
			TrashEntry trashEntry = TrashEntryLocalServiceUtil.getEntry(
				DLFileEntryConstants.getClassName(), fileEntryId);

			TrashEntryServiceUtil.restoreEntry(trashEntry.getEntryId());

			Assert.assertEquals(
				initialNotInTrashCount + 1,
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