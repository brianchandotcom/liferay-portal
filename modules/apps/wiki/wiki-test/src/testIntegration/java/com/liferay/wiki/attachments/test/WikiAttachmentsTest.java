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
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.service.DLAppLocalService;
import com.liferay.document.library.kernel.service.DLFileEntryLocalService;
import com.liferay.document.library.kernel.service.DLFolderLocalService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.facet.faceted.searcher.FacetedSearcher;
import com.liferay.portal.kernel.search.facet.faceted.searcher.FacetedSearcherManager;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.SearchContextTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.search.test.util.SearchTestRule;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.wiki.model.WikiNode;
import com.liferay.wiki.model.WikiPage;
import com.liferay.wiki.service.WikiNodeLocalService;
import com.liferay.wiki.service.WikiPageLocalService;
import com.liferay.wiki.test.util.WikiTestUtil;

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
public class WikiAttachmentsTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		UserTestUtil.setUser(TestPropsValues.getUser());

		_group = GroupTestUtil.addGroup();
	}

	@Test
	public void testDeleteAttachmentsWhenDeletingWikiNode() throws Exception {
		int initialFileEntriesCount =
			_dlFileEntryLocalService.getFileEntriesCount();

		addWikiPageAttachment();

		Assert.assertEquals(
			initialFileEntriesCount + 1,
			_dlFileEntryLocalService.getFileEntriesCount());

		_wikiNodeLocalService.deleteNode(_page.getNodeId());

		Assert.assertEquals(
			initialFileEntriesCount,
			_dlFileEntryLocalService.getFileEntriesCount());
	}

	@Test
	public void testDeleteAttachmentsWhenDeletingWikiPage() throws Exception {
		int initialFileEntriesCount =
			_dlFileEntryLocalService.getFileEntriesCount();

		addWikiPageAttachment();

		Assert.assertEquals(
			initialFileEntriesCount + 1,
			_dlFileEntryLocalService.getFileEntriesCount());

		_wikiPageLocalService.deletePage(_page.getNodeId(), _page.getTitle());

		Assert.assertEquals(
			initialFileEntriesCount,
			_dlFileEntryLocalService.getFileEntriesCount());
	}

	@Test
	public void testFoldersCountWhenAddingAttachmentsToSameWikiPage()
		throws Exception {

		int initialFoldersCount = _dlFolderLocalService.getDLFoldersCount();

		addWikiPageAttachment();

		int foldersCount = _dlFolderLocalService.getDLFoldersCount();

		Assert.assertEquals(initialFoldersCount + 3, foldersCount);

		addWikiPageAttachment();

		foldersCount = _dlFolderLocalService.getDLFoldersCount();

		Assert.assertEquals(initialFoldersCount + 3, foldersCount);
	}

	@Test
	public void testFoldersCountWhenAddingWikiNode() throws Exception {
		int initialFoldersCount = _dlFolderLocalService.getDLFoldersCount();

		addWikiNode();

		Assert.assertEquals(
			initialFoldersCount, _dlFolderLocalService.getDLFoldersCount());
	}

	@Test
	public void testFoldersCountWhenAddingWikiPage() throws Exception {
		int initialFoldersCount = _dlFolderLocalService.getDLFoldersCount();

		addWikiPage();

		Assert.assertEquals(
			initialFoldersCount, _dlFolderLocalService.getDLFoldersCount());
	}

	@Test
	public void testFoldersCountWhenAddingWikiPageAttachment()
		throws Exception {

		int initialFoldersCount = _dlFolderLocalService.getDLFoldersCount();

		addWikiPageAttachment();

		Assert.assertEquals(
			initialFoldersCount + 3, _dlFolderLocalService.getDLFoldersCount());
	}

	@Test
	public void testFoldersCountWhenAddingWikiPageAttachments()
		throws Exception {

		int foldersCount = _dlFolderLocalService.getDLFoldersCount();

		addWikiPageAttachment();

		Assert.assertEquals(
			foldersCount + 3, _dlFolderLocalService.getDLFoldersCount());

		foldersCount = _dlFolderLocalService.getDLFoldersCount();

		_page = null;

		addWikiPageAttachment();

		Assert.assertEquals(
			foldersCount + 1, _dlFolderLocalService.getDLFoldersCount());

		foldersCount = _dlFolderLocalService.getDLFoldersCount();

		_node = null;
		_page = null;

		addWikiPageAttachment();

		Assert.assertEquals(
			foldersCount + 2, _dlFolderLocalService.getDLFoldersCount());

		foldersCount = _dlFolderLocalService.getDLFoldersCount();

		Group group = _group;

		_group = null;
		_node = null;
		_page = null;

		try {
			addWikiPageAttachment();

			Assert.assertEquals(
				foldersCount + 3, _dlFolderLocalService.getDLFoldersCount());
		}
		finally {
			_groupLocalService.deleteGroup(group);
		}
	}

	@Test
	public void testFoldersCountWhenDeletingWikiNodeWithAttachments()
		throws Exception {

		int initialFoldersCount = _dlFolderLocalService.getDLFoldersCount();

		addWikiPageAttachment();

		Assert.assertEquals(
			initialFoldersCount + 3, _dlFolderLocalService.getDLFoldersCount());

		_wikiNodeLocalService.deleteNode(_page.getNodeId());

		Assert.assertEquals(
			initialFoldersCount + 1, _dlFolderLocalService.getDLFoldersCount());
	}

	@Test
	public void testFoldersCountWhenDeletingWikiNodeWithoutAttachments()
		throws Exception {

		int initialFoldersCount = _dlFolderLocalService.getDLFoldersCount();

		addWikiNode();

		Assert.assertEquals(
			initialFoldersCount, _dlFolderLocalService.getDLFoldersCount());

		_wikiNodeLocalService.deleteNode(_node.getNodeId());

		Assert.assertEquals(
			initialFoldersCount, _dlFolderLocalService.getDLFoldersCount());
	}

	@Test
	public void testFoldersCountWhenDeletingWikiPageWithAttachments()
		throws Exception {

		int initialFoldersCount = _dlFolderLocalService.getDLFoldersCount();

		addWikiPageAttachment();

		Assert.assertEquals(
			initialFoldersCount + 3, _dlFolderLocalService.getDLFoldersCount());

		_wikiPageLocalService.deletePage(_page.getNodeId(), _page.getTitle());

		Assert.assertEquals(
			initialFoldersCount + 2, _dlFolderLocalService.getDLFoldersCount());
	}

	@Test
	public void testFoldersCountWhenDeletingWikiPageWithoutAttachments()
		throws Exception {

		int initialFoldersCount = _dlFolderLocalService.getDLFoldersCount();

		addWikiPage();

		Assert.assertEquals(
			initialFoldersCount, _dlFolderLocalService.getDLFoldersCount());

		_wikiPageLocalService.deletePage(_page.getNodeId(), _page.getTitle());

		Assert.assertEquals(
			initialFoldersCount, _dlFolderLocalService.getDLFoldersCount());
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

	@Test
	public void testSearchIncludeAttachment() throws Exception {
		String title = "Title";

		_addFileEntry(title);
		_addWikiPageWithAttachmentFileName(title);

		Assert.assertEquals(1, _searchCount(title, false));
		Assert.assertEquals(2, _searchCount(title, true));
	}

	@Rule
	public SearchTestRule searchTestRule = new SearchTestRule();

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

	private void _addFileEntry(String title) throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		_dlAppLocalService.addFileEntry(
			null, serviceContext.getUserId(), _group.getGroupId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			StringUtil.randomString(), ContentTypes.TEXT_PLAIN, title,
			StringPool.BLANK, StringPool.BLANK, StringPool.BLANK,
			_CONTENT.getBytes(), null, null, serviceContext);
	}

	private void _addWikiPageWithAttachmentFileName(String fileName)
		throws Exception {

		if (_page == null) {
			addWikiPage();
		}

		WikiTestUtil.addWikiAttachment(
			_page.getUserId(), _page.getNodeId(), _page.getTitle(), fileName,
			getClass());
	}

	private int _searchCount(String keywords, boolean includeAttachments)
		throws Exception {

		SearchContext searchContext = SearchContextTestUtil.getSearchContext(
			_group.getGroupId());

		searchContext.setIncludeAttachments(includeAttachments);
		searchContext.setKeywords(keywords);

		FacetedSearcher facetedSearcher =
			_facetedSearcherManager.createFacetedSearcher();

		Hits hits = facetedSearcher.search(searchContext);

		return hits.getLength();
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

		FileEntry fileEntry = _wikiPageLocalService.movePageAttachmentToTrash(
			TestPropsValues.getUserId(), _page.getNodeId(), _page.getTitle(),
			fileName);

		Assert.assertEquals(
			initialNotInTrashCount, _page.getAttachmentsFileEntriesCount());
		Assert.assertEquals(
			initialTrashEntriesCount + 1,
			_page.getDeletedAttachmentsFileEntriesCount());

		if (restore) {
			_wikiPageLocalService.restorePageAttachmentFromTrash(
				TestPropsValues.getUserId(), _page.getNodeId(),
				_page.getTitle(), fileEntry.getFileName());

			Assert.assertEquals(
				initialNotInTrashCount + 1,
				_page.getAttachmentsFileEntriesCount());
			Assert.assertEquals(
				initialTrashEntriesCount,
				_page.getDeletedAttachmentsFileEntriesCount());

			_wikiPageLocalService.deletePageAttachment(
				_page.getNodeId(), _page.getTitle(), fileName);
		}
		else {
			_wikiPageLocalService.deletePageAttachment(
				_page.getNodeId(), _page.getTitle(), fileEntry.getTitle());

			Assert.assertEquals(
				initialNotInTrashCount, _page.getAttachmentsFileEntriesCount());
			Assert.assertEquals(
				initialTrashEntriesCount,
				_page.getDeletedAttachmentsFileEntriesCount());
		}
	}

	private static final String _CONTENT =
		"Content: Enterprise. Open Source. For Life.";

	@Inject
	private static DLAppLocalService _dlAppLocalService;

	@Inject
	private static DLFileEntryLocalService _dlFileEntryLocalService;

	@Inject
	private static DLFolderLocalService _dlFolderLocalService;

	@Inject
	private static FacetedSearcherManager _facetedSearcherManager;

	@Inject
	private static GroupLocalService _groupLocalService;

	@Inject
	private static WikiNodeLocalService _wikiNodeLocalService;

	@Inject
	private static WikiPageLocalService _wikiPageLocalService;

	@DeleteAfterTestRun
	private Group _group;

	private WikiNode _node;
	private WikiPage _page;

}