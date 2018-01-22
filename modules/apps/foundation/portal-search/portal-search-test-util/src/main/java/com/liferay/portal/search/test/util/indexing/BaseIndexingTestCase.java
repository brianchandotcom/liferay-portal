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

package com.liferay.portal.search.test.util.indexing;

import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.BooleanQuery;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.IndexSearcher;
import com.liferay.portal.kernel.search.IndexWriter;
import com.liferay.portal.kernel.search.Query;
import com.liferay.portal.kernel.search.QueryConfig;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.SearchException;
import com.liferay.portal.kernel.search.generic.BooleanQueryImpl;
import com.liferay.portal.kernel.search.generic.TermQueryImpl;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.search.test.util.SearchMapUtil;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.function.Function;

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

		try {
			_indexWriter.deleteEntityDocuments(
				createSearchContext(), _entryClassName);
		}
		catch (SearchException se) {
		}

		_documentFixture.tearDown();

		_indexingFixture.tearDown();
	}

	protected static SearchContext createSearchContext() {
		SearchContext searchContext = new SearchContext();

		searchContext.setCompanyId(COMPANY_ID);
		searchContext.setGroupIds(new long[] {GROUP_ID});

		QueryConfig queryConfig = searchContext.getQueryConfig();

		queryConfig.setHighlightEnabled(false);
		queryConfig.setHitsProcessingEnabled(true);
		queryConfig.setScoreEnabled(false);

		searchContext.setStart(QueryUtil.ALL_POS);

		return searchContext;
	}

	protected static <K, V> Map<K, V> toMap(K key, V value) {
		return Collections.singletonMap(key, value);
	}

	protected void addDocument(DocumentCreationHelper documentCreationHelper)
		throws Exception {

		Document document = DocumentFixture.newDocument(
			COMPANY_ID, GROUP_ID, _entryClassName);

		documentCreationHelper.populate(document);

		_indexWriter.addDocument(createSearchContext(), document);
	}

	protected void addDocuments(
			Function<String, DocumentCreationHelper> function,
			Collection<String> values)
		throws Exception {

		for (String value : values) {
			addDocument(function.apply(value));
		}
	}

	protected abstract IndexingFixture createIndexingFixture() throws Exception;

	protected Query getDefaultQuery() throws Exception {
		Map<String, String> map = SearchMapUtil.join(
			toMap(Field.COMPANY_ID, String.valueOf(COMPANY_ID)),
			toMap(Field.ENTRY_CLASS_NAME, _entryClassName));

		BooleanQueryImpl booleanQueryImpl = new BooleanQueryImpl();

		map.forEach(
			(key, value) -> booleanQueryImpl.add(
				new TermQueryImpl(key, value), BooleanClauseOccur.MUST));

		return booleanQueryImpl;
	}

	protected IndexSearcher getIndexSearcher() {
		return _indexSearcher;
	}

	protected IndexWriter getIndexWriter() {
		return _indexWriter;
	}

	protected Hits search(SearchContext searchContext) throws Exception {
		return search(searchContext, getDefaultQuery());
	}

	protected Hits search(SearchContext searchContext, Query query)
		throws Exception {

		return _indexSearcher.search(searchContext, query);
	}

	protected Hits search(
			SearchContext searchContext, QueryContributor queryContributor)
		throws Exception {

		return search(searchContext, _getQuery(queryContributor));
	}

	protected static final long COMPANY_ID = RandomTestUtil.randomLong();

	protected static final long GROUP_ID = RandomTestUtil.randomLong();

	private Query _getQuery(QueryContributor queryContributor)
		throws Exception {

		Query query = getDefaultQuery();

		if (queryContributor == null) {
			return query;
		}

		BooleanQuery booleanQuery = new BooleanQueryImpl();

		booleanQuery.add(query, BooleanClauseOccur.MUST);

		queryContributor.contribute(booleanQuery);

		return booleanQuery;
	}

	private final DocumentFixture _documentFixture = new DocumentFixture();
	private final String _entryClassName;
	private IndexingFixture _indexingFixture;
	private IndexSearcher _indexSearcher;
	private IndexWriter _indexWriter;

}