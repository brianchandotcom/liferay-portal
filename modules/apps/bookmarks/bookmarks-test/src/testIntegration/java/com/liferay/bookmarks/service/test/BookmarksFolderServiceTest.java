/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.bookmarks.service.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.bookmarks.model.BookmarksEntry;
import com.liferay.bookmarks.model.BookmarksFolder;
import com.liferay.bookmarks.service.BookmarksFolderLocalServiceUtil;
import com.liferay.bookmarks.service.BookmarksFolderServiceUtil;
import com.liferay.bookmarks.test.util.BookmarksTestUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.AssertUtils;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.search.test.util.SearchTestRule;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Brian Wing Shun Chan
 */
@RunWith(Arquillian.class)
public class BookmarksFolderServiceTest {

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
	public void testAddFolder() throws Exception {
		BookmarksTestUtil.addFolder(
			_group.getGroupId(), RandomTestUtil.randomString());
	}

	@Test
	public void testAddSubfolder() throws Exception {
		BookmarksFolder folder = BookmarksTestUtil.addFolder(
			_group.getGroupId(), RandomTestUtil.randomString());

		BookmarksTestUtil.addFolder(
			_group.getGroupId(), folder.getFolderId(),
			RandomTestUtil.randomString());
	}

	@Test
	public void testDeleteFolder() throws Exception {
		BookmarksFolder folder = BookmarksTestUtil.addFolder(
			_group.getGroupId(), RandomTestUtil.randomString());

		BookmarksFolderServiceUtil.deleteFolder(folder.getFolderId());
	}

	@Test
	public void testGetFolder() throws Exception {
		BookmarksFolder folder = BookmarksTestUtil.addFolder(
			_group.getGroupId(), RandomTestUtil.randomString());

		BookmarksFolderServiceUtil.getFolder(folder.getFolderId());
	}

	@Test
	public void testSearch() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		BookmarksFolder folder = BookmarksTestUtil.addFolder(
			_group.getGroupId(), RandomTestUtil.randomString());

		BookmarksEntry entry = BookmarksTestUtil.addEntry(
			folder.getFolderId(), true, serviceContext);

		SearchContext searchContext = BookmarksTestUtil.getSearchContext(
			entry.getCompanyId(), entry.getGroupId(), entry.getFolderId(),
			"test");

		Indexer<BookmarksEntry> indexer = IndexerRegistryUtil.getIndexer(
			BookmarksEntry.class);

		Hits hits = indexer.search(searchContext);

		Assert.assertEquals(hits.toString(), 1, hits.getLength());
	}

	@Test
	public void testSearchAndDeleteFolderAndSearch() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		BookmarksFolder folder = BookmarksTestUtil.addFolder(
			_group.getGroupId(), RandomTestUtil.randomString());

		BookmarksEntry entry = BookmarksTestUtil.addEntry(
			folder.getFolderId(), true, serviceContext);

		long companyId = entry.getCompanyId();

		BookmarksFolder entryFolder = entry.getFolder();

		long groupId = entryFolder.getGroupId();

		long folderId = entry.getFolderId();

		String keywords = "test";

		SearchContext searchContext = BookmarksTestUtil.getSearchContext(
			companyId, groupId, folderId, keywords);

		Indexer<BookmarksEntry> indexer = IndexerRegistryUtil.getIndexer(
			BookmarksEntry.class);

		Hits hits = indexer.search(searchContext);

		Assert.assertEquals(hits.toString(), 1, hits.getLength());

		BookmarksFolderLocalServiceUtil.deleteFolder(folderId);

		hits = indexer.search(searchContext);

		Assert.assertEquals(hits.toString(), 0, hits.getLength());
	}

	@Test
	public void testSearchAndVerifyDocs() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		BookmarksFolder folder = BookmarksTestUtil.addFolder(
			_group.getGroupId(), RandomTestUtil.randomString());

		BookmarksEntry entry = BookmarksTestUtil.addEntry(
			folder.getFolderId(), true, serviceContext);

		SearchContext searchContext = BookmarksTestUtil.getSearchContext(
			entry.getCompanyId(), entry.getGroupId(), entry.getFolderId(),
			"test");

		Indexer<BookmarksEntry> indexer = IndexerRegistryUtil.getIndexer(
			BookmarksEntry.class);

		Hits hits = indexer.search(searchContext);

		Assert.assertEquals(hits.toString(), 1, hits.getLength());

		List<Document> results = hits.toList();

		for (Document doc : results) {
			Assert.assertEquals(
				entry.getCompanyId(),
				GetterUtil.getLong(doc.get(Field.COMPANY_ID)));
			Assert.assertEquals(
				BookmarksEntry.class.getName(),
				doc.get(Field.ENTRY_CLASS_NAME));
			Assert.assertEquals(
				entry.getEntryId(),
				GetterUtil.getLong(doc.get(Field.ENTRY_CLASS_PK)));
			AssertUtils.assertEqualsIgnoreCase(
				entry.getName(), doc.get(Field.TITLE));
			Assert.assertEquals(entry.getUrl(), doc.get(Field.URL));
		}
	}

	@Test
	public void testSearchRange() throws Exception {
		BookmarksEntry entry = BookmarksTestUtil.addEntry(
			_group.getGroupId(), true);

		BookmarksTestUtil.addEntry(_group.getGroupId(), true);
		BookmarksTestUtil.addEntry(_group.getGroupId(), true);
		BookmarksTestUtil.addEntry(_group.getGroupId(), true);

		SearchContext searchContext = BookmarksTestUtil.getSearchContext(
			_group.getCompanyId(), _group.getGroupId(), entry.getFolderId(),
			"test");

		Indexer<BookmarksEntry> indexer = IndexerRegistryUtil.getIndexer(
			BookmarksEntry.class);

		searchContext.setEnd(3);
		searchContext.setFolderIds((long[])null);
		searchContext.setStart(1);

		Hits hits = indexer.search(searchContext);

		Assert.assertEquals(hits.toString(), 4, hits.getLength());

		Document[] documents = hits.getDocs();

		Assert.assertEquals(Arrays.toString(documents), 2, documents.length);
	}

	@Rule
	public SearchTestRule searchTestRule = new SearchTestRule();

	@DeleteAfterTestRun
	private Group _group;

}