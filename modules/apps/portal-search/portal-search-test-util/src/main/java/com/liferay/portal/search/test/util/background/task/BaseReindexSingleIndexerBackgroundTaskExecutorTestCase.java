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

package com.liferay.portal.search.test.util.background.task;

import com.liferay.osgi.service.tracker.collections.list.ServiceTrackerList;
import com.liferay.portal.kernel.backgroundtask.BackgroundTask;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.IndexWriterHelper;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistry;
import com.liferay.portal.kernel.search.SearchEngineHelper;
import com.liferay.portal.kernel.search.background.task.ReindexBackgroundTaskConstants;
import com.liferay.portal.kernel.search.background.task.ReindexStatusMessageSender;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.search.index.IndexNameBuilder;
import com.liferay.portal.search.internal.SearchEngineHelperImpl;
import com.liferay.portal.search.internal.background.task.ReindexSingleIndexerBackgroundTaskExecutor;
import com.liferay.portal.search.test.util.search.engine.SearchEngineFixture;
import com.liferay.portal.util.PropsImpl;

import java.io.Serializable;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

/**
 * @author Adam Brandizzi
 */
public abstract class BaseReindexSingleIndexerBackgroundTaskExecutorTestCase {

	@BeforeClass
	public static void setUpClass() {
		PropsUtil.setProps(new PropsImpl());
	}

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);

		long companyId = RandomTestUtil.randomLong();

		setUpBackgroundTask(companyId);

		setUpIndexerRegistry();

		SearchEngineFixture searchEngineFixture = getSearchEngineFixture();

		searchEngineFixture.setUp();

		SearchEngineHelper searchEngineHelper = new SearchEngineHelperImpl();

		searchEngineHelper.setSearchEngine(
			"test", searchEngineFixture.getSearchEngine());

		_companyId = companyId;
		_searchEngineFixture = searchEngineFixture;
		_searchEngineHelper = searchEngineHelper;
	}

	@After
	public void tearDown() throws Exception {
		if (_searchEngineFixture != null) {
			_searchEngineFixture.tearDown();
		}
	}

	@Test
	public void testFieldMappings() throws Exception {
		ReindexSingleIndexerBackgroundTaskExecutor
			reindexSingleIndexerBackgroundTaskExecutor =
				getReindexSingleIndexerBackgroundTaskExecutor();

		reindexSingleIndexerBackgroundTaskExecutor.execute(_backgroundTask);

		assertFieldType(Field.ENTRY_CLASS_NAME, "keyword");
	}

	protected abstract void assertFieldType(String fieldName, String fieldType)
		throws Exception;

	protected String getIndexName() {
		IndexNameBuilder indexNameBuilder =
			_searchEngineFixture.getIndexNameBuilder();

		return indexNameBuilder.getIndexName(_companyId);
	}

	protected ReindexSingleIndexerBackgroundTaskExecutor
		getReindexSingleIndexerBackgroundTaskExecutor() {

		return new ReindexSingleIndexerBackgroundTaskExecutor() {
			{
				indexerRegistry = _indexerRegistry;
				indexWriterHelper = _indexWriterHelper;
				reindexStatusMessageSender = _reindexStatusMessageSender;
				searchEngineHelper = _searchEngineHelper;
				systemIndexers = _systemIndexers;
			}
		};
	}

	protected abstract SearchEngineFixture getSearchEngineFixture();

	protected void setUpBackgroundTask(long companyId) {
		Mockito.when(
			_backgroundTask.getTaskContextMap()
		).thenReturn(
			HashMapBuilder.<String, Serializable>put(
				ReindexBackgroundTaskConstants.CLASS_NAME,
				RandomTestUtil.randomString()
			).put(
				ReindexBackgroundTaskConstants.COMPANY_IDS,
				new long[] {companyId}
			).build()
		);
	}

	protected void setUpIndexerRegistry() {
		Mockito.when(
			_indexerRegistry.getIndexer(Matchers.anyString())
		).thenReturn(
			_indexer
		);
	}

	@Mock
	private BackgroundTask _backgroundTask;

	private long _companyId;

	@Mock
	private Indexer<Object> _indexer;

	@Mock
	private IndexerRegistry _indexerRegistry;

	@Mock
	private IndexWriterHelper _indexWriterHelper;

	@Mock
	private ReindexStatusMessageSender _reindexStatusMessageSender;

	private SearchEngineFixture _searchEngineFixture;
	private SearchEngineHelper _searchEngineHelper;

	@Mock
	private ServiceTrackerList<Indexer<?>> _systemIndexers;

}