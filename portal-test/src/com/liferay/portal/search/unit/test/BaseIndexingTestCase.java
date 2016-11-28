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

package com.liferay.portal.search.unit.test;

import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.IndexSearcher;
import com.liferay.portal.kernel.search.IndexWriter;
import com.liferay.portal.kernel.search.Query;
import com.liferay.portal.kernel.search.QueryConfig;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.generic.TermQueryImpl;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.StringUtil;

import org.junit.After;
import org.junit.Assume;
import org.junit.Before;

/**
 * @author Miguel Angelo Caldas Gallindo
 */
public abstract class BaseIndexingTestCase {

	public BaseIndexingTestCase() {
		Class<?> clazz = getClass();

		_entryClassName = StringUtil.toLowerCase(clazz.getSimpleName());
	}

	@Before
	public void setUp() throws Exception {
		_documentFixture.setUp();

		_indexingFixture = createIndexingFixture();

		Assume.assumeTrue(_indexingFixture.isSearchEngineAvailable());

		_indexingFixture.setUp();

		_indexSearcher = _indexingFixture.getIndexSearcher();
		_indexWriter = _indexingFixture.getIndexWriter();
	}

	@After
	public void tearDown() throws Exception {
		if (!_indexingFixture.isSearchEngineAvailable()) {
			return;
		}

		_documentFixture.tearDown();

		_indexWriter.deleteEntityDocuments(
			createSearchContext(), _entryClassName);

		_indexingFixture.tearDown();
	}

	public interface DocumentCreationHelper {

		public void populate(Document document);

	}

	protected static SearchContext createSearchContext() {
		SearchContext searchContext = new SearchContext();

		searchContext.setCompanyId(COMPANY_ID);
		searchContext.setEnd(QueryUtil.ALL_POS);
		searchContext.setGroupIds(new long[] {GROUP_ID});

		QueryConfig queryConfig = new QueryConfig();

		queryConfig.setHighlightEnabled(false);
		queryConfig.setHitsProcessingEnabled(true);
		queryConfig.setScoreEnabled(false);

		searchContext.setQueryConfig(queryConfig);

		searchContext.setStart(QueryUtil.ALL_POS);

		return searchContext;
	}

	protected void addDocument(DocumentCreationHelper documentCreationHelper)
		throws Exception {

		Document document = DocumentFixture.newDocument(
			COMPANY_ID, GROUP_ID, _entryClassName);

		documentCreationHelper.populate(document);

		_indexWriter.addDocument(createSearchContext(), document);
	}

	protected abstract IndexingFixture createIndexingFixture() throws Exception;

	protected Hits search(SearchContext searchContext) throws Exception {
		Query query = new TermQueryImpl(
			Field.ENTRY_CLASS_NAME, _entryClassName);

		return _indexSearcher.search(searchContext, query);
	}

	protected static final long COMPANY_ID = RandomTestUtil.randomLong();

	protected static final long GROUP_ID = RandomTestUtil.randomLong();

	private final DocumentFixture _documentFixture = new DocumentFixture();
	private final String _entryClassName;
	private IndexingFixture _indexingFixture;
	private IndexSearcher _indexSearcher;
	private IndexWriter _indexWriter;

}