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

package com.liferay.bookmarks.service;

import com.liferay.bookmarks.model.BookmarksEntry;
import com.liferay.bookmarks.model.BookmarksFolder;
import com.liferay.bookmarks.util.BookmarksTestUtil;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.search.Query;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.test.AssertUtils;
import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.model.Group;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.test.DeleteAfterTestRun;
import com.liferay.portal.test.Sync;
import com.liferay.portal.test.SynchronousDestinationExecutionTestListener;
import com.liferay.portal.test.listeners.MainServletExecutionTestListener;
import com.liferay.portal.test.runners.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.util.test.GroupTestUtil;
import com.liferay.portal.util.test.RandomTestUtil;
import com.liferay.portal.util.test.ServiceContextTestUtil;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Brian Wing Shun Chan
 */
@ExecutionTestListeners(
	listeners = {
		MainServletExecutionTestListener.class,
		SynchronousDestinationExecutionTestListener.class
	})
@RunWith(LiferayIntegrationJUnitTestRunner.class)
@Sync
public class BookmarksFolderServiceTest {

	@Before
	public void setUp() throws Exception {
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

		Indexer indexer = IndexerRegistryUtil.getIndexer(BookmarksEntry.class);

		Hits hits = indexer.search(searchContext);

		Assert.assertEquals(1, hits.getLength());
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
		long groupId = entry.getFolder().getGroupId();
		long folderId = entry.getFolderId();
		String keywords = "test";

		SearchContext searchContext = BookmarksTestUtil.getSearchContext(
			companyId, groupId, folderId, keywords);

		Indexer indexer = IndexerRegistryUtil.getIndexer(BookmarksEntry.class);

		Hits hits = indexer.search(searchContext);

		Assert.assertEquals(1, hits.getLength());

		BookmarksFolderLocalServiceUtil.deleteFolder(folderId);

		hits = indexer.search(searchContext);

		Query query = hits.getQuery();

		Assert.assertEquals(query.toString(), 0, hits.getLength());
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

		Indexer indexer = IndexerRegistryUtil.getIndexer(BookmarksEntry.class);

		Hits hits = indexer.search(searchContext);

		Assert.assertEquals(1, hits.getLength());

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

		Indexer indexer = IndexerRegistryUtil.getIndexer(BookmarksEntry.class);

		searchContext.setEnd(3);
		searchContext.setFolderIds((long[])null);
		searchContext.setStart(1);

		Hits hits = indexer.search(searchContext);

		Assert.assertEquals(4, hits.getLength());

		Document[] documents = hits.getDocs();

		Assert.assertEquals(2, documents.length);
	}

	@DeleteAfterTestRun
	private Group _group;

}