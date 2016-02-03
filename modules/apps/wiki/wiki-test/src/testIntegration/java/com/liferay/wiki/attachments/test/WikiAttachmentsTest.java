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

package com.liferay.wiki.attachments.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.rule.Sync;
import com.liferay.portal.kernel.test.rule.SynchronousDestinationTestRule;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.model.Group;
import com.liferay.portal.service.test.ServiceTestUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portlet.documentlibrary.model.DLFileEntryConstants;
import com.liferay.portlet.documentlibrary.service.DLFileEntryLocalServiceUtil;
import com.liferay.portlet.documentlibrary.service.DLFolderLocalServiceUtil;
import com.liferay.trash.kernel.service.TrashEntryServiceUtil;
import com.liferay.wiki.model.WikiNode;
import com.liferay.wiki.model.WikiPage;
import com.liferay.wiki.service.WikiNodeLocalServiceUtil;
import com.liferay.wiki.service.WikiPageLocalServiceUtil;
import com.liferay.wiki.util.test.WikiTestUtil;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Eudaldo Alonso
 * @author Roberto Díaz
 * @author Sergio González
 */
@RunWith(Arquillian.class)
@Sync
public class WikiAttachmentsTest {

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
	}

	@Test
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
	public void testFoldersCountWhenAddingWikiNode() throws Exception {
		int initialFoldersCount = DLFolderLocalServiceUtil.getDLFoldersCount();

		addWikiNode();

		Assert.assertEquals(
			initialFoldersCount, DLFolderLocalServiceUtil.getDLFoldersCount());
	}

	@Test
	public void testFoldersCountWhenAddingWikiPage() throws Exception {
		int initialFoldersCount = DLFolderLocalServiceUtil.getDLFoldersCount();

		addWikiPage();

		Assert.assertEquals(
			initialFoldersCount, DLFolderLocalServiceUtil.getDLFoldersCount());
	}

	@Test
	public void testFoldersCountWhenAddingWikiPageAttachment()
		throws Exception {

		int initialFoldersCount = DLFolderLocalServiceUtil.getDLFoldersCount();

		addWikiPageAttachment();

		Assert.assertEquals(
			initialFoldersCount + 3,
			DLFolderLocalServiceUtil.getDLFoldersCount());
	}

	@Test
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
	}

	@Test
	public void testMoveToTrashAndRestoreWikiPageAttachment() throws Exception {
		addWikiPage();

		_trashWikiAttachments(true);
	}

	protected void addWikiNode() throws Exception {
		if (_group == null) {
			_group = GroupTestUtil.addGroup();
		}

		_node = WikiTestUtil.addNode(_group.getGroupId());
	}

	protected void addWikiPage() throws Exception {
		if (_node == null) {
			addWikiNode();
		}

		_page = WikiTestUtil.addPage(
			_group.getGroupId(), _node.getNodeId(), true);
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

		String fileName = RandomTestUtil.randomString() + ".docx";

		WikiTestUtil.addWikiAttachment(
			TestPropsValues.getUserId(), _node.getNodeId(), _page.getTitle(),
			fileName, getClass());

		Assert.assertEquals(
			initialNotInTrashCount + 1, _page.getAttachmentsFileEntriesCount());
		Assert.assertEquals(
			initialTrashEntriesCount,
			_page.getDeletedAttachmentsFileEntriesCount());

		FileEntry fileEntry =
			WikiPageLocalServiceUtil.movePageAttachmentToTrash(
				TestPropsValues.getUserId(), _page.getNodeId(),
				_page.getTitle(), fileName);

		Assert.assertEquals(
			initialNotInTrashCount, _page.getAttachmentsFileEntriesCount());
		Assert.assertEquals(
			initialTrashEntriesCount + 1,
			_page.getDeletedAttachmentsFileEntriesCount());

		if (restore) {
			TrashEntryServiceUtil.restoreEntry(
				DLFileEntryConstants.getClassName(),
				fileEntry.getFileEntryId());

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

	@DeleteAfterTestRun
	private Group _group;

	private WikiNode _node;
	private WikiPage _page;

}