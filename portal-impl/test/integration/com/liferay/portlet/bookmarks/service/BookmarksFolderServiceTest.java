/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.bookmarks.service;

import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.search.Query;
import com.liferay.portal.kernel.search.QueryConfig;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.model.Group;
import com.liferay.portal.service.ServiceTestUtil;
import com.liferay.portal.test.AssertUtils;
import com.liferay.portal.test.EnvironmentExecutionTestListener;
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.test.Sync;
import com.liferay.portal.test.SynchronousDestinationExecutionTestListener;
import com.liferay.portlet.bookmarks.model.BookmarksEntry;
import com.liferay.portlet.bookmarks.model.BookmarksFolder;
import com.liferay.portlet.bookmarks.util.BookmarksTestUtil;

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
		EnvironmentExecutionTestListener.class,
		SynchronousDestinationExecutionTestListener.class
	})
@RunWith(LiferayIntegrationJUnitTestRunner.class)
@Sync
@Transactional
public class BookmarksFolderServiceTest {

	@Before
	public void setUp() throws Exception {
		_group = ServiceTestUtil.addGroup();
	}

	@Test
	public void testAddFolder() throws Exception {
		BookmarksTestUtil.addFolder(_group.getGroupId());
	}

	@Test
	public void testAddSubfolder() throws Exception {
		BookmarksFolder folder = BookmarksTestUtil.addFolder(
			_group.getGroupId());

		BookmarksTestUtil.addFolder(_group.getGroupId(), folder.getFolderId());
	}

	@Test
	public void testCountRangeSearch() throws Exception {
		BookmarksEntry entry = BookmarksTestUtil.addEntry(_group.getGroupId());
		BookmarksTestUtil.addEntry(_group.getGroupId());
		BookmarksTestUtil.addEntry(_group.getGroupId());
		BookmarksTestUtil.addEntry(_group.getGroupId());

		long companyId = _group.getCompanyId();
		long groupId = _group.getGroupId();
		long folderId = entry.getFolderId();
		String keywords = "test";

		SearchContext searchContext = new SearchContext();

		searchContext.setCompanyId(companyId);
		searchContext.setFolderIds(new long[] {folderId});
		searchContext.setGroupIds(new long[] {groupId});
		searchContext.setKeywords(keywords);

		QueryConfig queryConfig = new QueryConfig();

		queryConfig.setHighlightEnabled(false);
		queryConfig.setScoreEnabled(false);

		searchContext.setQueryConfig(queryConfig);

		Indexer indexer = IndexerRegistryUtil.getIndexer(BookmarksEntry.class);

		searchContext.setEnd(3);
		searchContext.setFolderIds((long[])null);
		searchContext.setStart(1);

		Hits hits = indexer.search(searchContext);

		Assert.assertEquals(4, hits.getLength());
		Assert.assertEquals(2, hits.getDocs().length);
	}

	@Test
	public void testDeleteFolder() throws Exception {
		BookmarksFolder folder = BookmarksTestUtil.addFolder(
			_group.getGroupId());

		BookmarksFolderServiceUtil.deleteFolder(folder.getFolderId());
	}

	@Test
	public void testGetFolder() throws Exception {
		BookmarksFolder folder = BookmarksTestUtil.addFolder(
			_group.getGroupId());

		BookmarksFolderServiceUtil.getFolder(folder.getFolderId());
	}

	@Test
	public void testSearch() throws Exception {
		BookmarksEntry entry = BookmarksTestUtil.addEntry(_group.getGroupId());

		long companyId = entry.getCompanyId();
		long groupId = entry.getFolder().getGroupId();
		long folderId = entry.getFolderId();
		String keywords = "test";

		SearchContext searchContext = new SearchContext();

		searchContext.setCompanyId(companyId);
		searchContext.setFolderIds(new long[] {folderId});
		searchContext.setGroupIds(new long[] {groupId});
		searchContext.setKeywords(keywords);

		QueryConfig queryConfig = new QueryConfig();

		queryConfig.setHighlightEnabled(false);
		queryConfig.setScoreEnabled(false);

		searchContext.setQueryConfig(queryConfig);

		Indexer indexer = IndexerRegistryUtil.getIndexer(BookmarksEntry.class);

		Hits hits = indexer.search(searchContext);

		Assert.assertEquals(1, hits.getLength());
	}

	@Test
	public void testSearchAndDeleteFolderAndSearch() throws Exception {
		BookmarksEntry entry = BookmarksTestUtil.addEntry(_group.getGroupId());

		long companyId = entry.getCompanyId();
		long groupId = entry.getFolder().getGroupId();
		long folderId = entry.getFolderId();
		String keywords = "test";

		SearchContext searchContext = new SearchContext();

		searchContext.setCompanyId(companyId);
		searchContext.setFolderIds(new long[] {folderId});
		searchContext.setGroupIds(new long[] {groupId});
		searchContext.setKeywords(keywords);

		QueryConfig queryConfig = new QueryConfig();

		queryConfig.setHighlightEnabled(false);
		queryConfig.setScoreEnabled(false);

		searchContext.setQueryConfig(queryConfig);

		Indexer indexer = IndexerRegistryUtil.getIndexer(BookmarksEntry.class);

		Hits hits = indexer.search(searchContext);

		Assert.assertEquals(1, hits.getLength());

		List<Document> results =  hits.toList();

		for (Document doc : results) {
			Assert.assertEquals(
				companyId, GetterUtil.getLong(doc.get(Field.COMPANY_ID)));

			Assert.assertEquals(
				groupId, GetterUtil.getLong(doc.get(Field.GROUP_ID)));

			AssertUtils.assertEqualsIgnoreCase(
				entry.getName(), doc.get(Field.TITLE));
			Assert.assertEquals(entry.getUrl(), doc.get(Field.URL));
			AssertUtils.assertEqualsIgnoreCase(
				entry.getDescription(), doc.get(Field.DESCRIPTION));

			Assert.assertEquals(
				folderId, GetterUtil.getLong(doc.get("folderId")));
			Assert.assertEquals(
				entry.getEntryId(),
				GetterUtil.getLong(doc.get(Field.ENTRY_CLASS_PK)));
		}

		BookmarksFolderLocalServiceUtil.deleteFolder(folderId);

		hits = indexer.search(searchContext);

		Query query = hits.getQuery();

		Assert.assertEquals(query.toString(), 0, hits.getLength());
	}

	private Group _group;

}