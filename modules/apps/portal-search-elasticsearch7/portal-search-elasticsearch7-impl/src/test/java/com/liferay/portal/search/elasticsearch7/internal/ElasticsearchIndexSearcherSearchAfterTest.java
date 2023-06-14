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

package com.liferay.portal.search.elasticsearch7.internal;

import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.petra.string.CharPool;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.IndexSearcher;
import com.liferay.portal.kernel.search.IndexWriter;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.SearchException;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.generic.MatchAllQuery;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.util.PropsTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.Props;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.search.elasticsearch7.internal.connection.ElasticsearchConnectionFixture;
import com.liferay.portal.search.elasticsearch7.internal.connection.ElasticsearchFixture;
import com.liferay.portal.search.elasticsearch7.internal.deep.pagination.configuration.DeepPaginationConfigurationWrapper;
import com.liferay.portal.search.internal.legacy.searcher.SearchRequestBuilderFactoryImpl;
import com.liferay.portal.search.internal.sort.FieldSortImpl;
import com.liferay.portal.search.internal.sort.ScoreSortImpl;
import com.liferay.portal.search.legacy.searcher.SearchRequestBuilderFactory;
import com.liferay.portal.search.sort.Sorts;
import com.liferay.portal.search.test.util.IdempotentRetryAssert;
import com.liferay.portal.search.test.util.indexing.DocumentFixture;
import com.liferay.portal.search.test.util.indexing.IndexingFixture;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Assert;
import org.junit.Assume;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;

import org.mockito.Mockito;

/**
 * @author Joshua Cords
 */
public class ElasticsearchIndexSearcherSearchAfterTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@BeforeClass
	public static void setUpClass() throws Exception {
		_indexingFixture = null;
	}

	@Before
	public void setUp() throws Exception {
		_documentFixture.setUp();

		Class<?> clazz = getClass();

		_entryClassName = StringUtil.toLowerCase(
			clazz.getSimpleName() + CharPool.PERIOD + testName.getMethodName());

		_searchRequestBuilderFactory = new SearchRequestBuilderFactoryImpl();

		_setUpIndexingFixture();

		_addDocuments();

		PropsTestUtil.setProps("feature.flag.LPS-172416", "true");
	}

	@After
	public void tearDown() throws Exception {
		if (!_documents.isEmpty()) {
			_indexWriter.deleteDocuments(
				_getSearchContext(),
				TransformUtil.transform(
					_documents, document -> document.get(Field.UID)));

			_documents.clear();
		}

		_documentFixture.tearDown();

		if (_indexingFixture == null) {
			return;
		}

		if (_indexingFixture.isSearchEngineAvailable()) {
			_indexingFixture.tearDown();
		}

		_indexingFixture = null;
	}

	@Test
	public void testElasticsearchIndexSearcher() throws Exception {
		_assertHits(_INDEX_MAX_RESULT_WINDOW, 0, _INDEX_MAX_RESULT_WINDOW);
	}

	@Test
	public void testElasticsearchIndexSearcherAcrossIndexMaxResultWindow()
		throws Exception {

		_assertHits(_INDEX_MAX_RESULT_WINDOW, 1, _INDEX_MAX_RESULT_WINDOW + 1);
	}

	@Test
	public void testElasticsearchIndexSearcherAcrossMultiplesOfIndexMaxResultWindow()
		throws Exception {

		_assertHits(
			_INDEX_MAX_RESULT_WINDOW, _INDEX_MAX_RESULT_WINDOW + 1,
			_NUMBER_INDEXED_DOCUMENTS);
	}

	@Test
	public void testElasticsearchIndexSearcherIndexSearchLimit()
		throws Exception {

		_assertHits(_INDEX_SEARCH_LIMIT, QueryUtil.ALL_POS, QueryUtil.ALL_POS);
	}

	@Rule
	public TestName testName = new TestName();

	protected static final long GROUP_ID = RandomTestUtil.randomLong();

	private void _addDocument(
		String fieldName1, String fieldValue1, String fieldName2,
		String fieldValue2) {

		Document document = _createDocument(
			fieldName1, fieldValue1, fieldName2, fieldValue2);

		try {
			_indexWriter.addDocument(_getSearchContext(), document);
		}
		catch (SearchException searchException) {
			Throwable throwable = searchException.getCause();

			if (throwable instanceof RuntimeException) {
				throw (RuntimeException)throwable;
			}

			if (throwable != null) {
				throw new RuntimeException(throwable);
			}

			throw new RuntimeException(searchException);
		}

		_documents.add(document);
	}

	private void _addDocuments() {
		for (int i = 1; i <= _NUMBER_INDEXED_DOCUMENTS; i++) {
			_addDocument(Field.TITLE, "Title " + i, Field.CONTENT, "example");
		}
	}

	private void _assertDocumentOrder(
		Document[] documents, int start, int end) {

		for (int i = 0; (i < documents.length) && (start < end); i++) {
			Document expectedDocument = _documents.get(start++);

			Assert.assertEquals(
				expectedDocument.get(Field.TITLE),
				documents[i].get(Field.TITLE));
		}
	}

	private void _assertHits(int expectedHitsReturned, int start, int end)
		throws Exception {

		IdempotentRetryAssert.retryAssert(
			5, TimeUnit.SECONDS,
			() -> {
				SearchContext searchContext = _getSearchContext();

				searchContext.setEnd(end);
				searchContext.setSorts(new Sort(Field.MODIFIED_DATE, true));
				searchContext.setStart(start);

				try {
					Hits hits = _indexSearcher.search(
						searchContext, new MatchAllQuery());

					Document[] documents = hits.getDocs();
					float[] scores = hits.getScores();

					printHits(documents);

					Assert.assertEquals(
						hits.toString(), expectedHitsReturned,
						documents.length);
					Assert.assertEquals(
						hits.toString(), _NUMBER_INDEXED_DOCUMENTS,
						hits.getLength());

					Assert.assertEquals(
						scores.toString(), expectedHitsReturned, scores.length);

					_assertDocumentOrder(documents, start, end);
				}
				catch (Exception exception) {
				}
			});
	}

	private Document _createDocument(
		String fieldName1, String fieldValue1, String fieldName2,
		String fieldValue2) {

		Document document = DocumentFixture.newDocument(
			_indexingFixture.getCompanyId(), GROUP_ID, _entryClassName);

		document.addText(fieldName1, fieldValue1);
		document.addText(fieldName2, fieldValue2);

		return document;
	}

	private IndexingFixture _createIndexingFixture() {
		ElasticsearchConnectionFixture elasticsearchConnectionFixture =
			ElasticsearchConnectionFixture.builder(
			).clusterName(
				ElasticsearchIndexSearcherSearchAfterTest.class.getSimpleName()
			).elasticsearchConfigurationProperties(
				Collections.singletonMap(
					"indexMaxResultWindow", _INDEX_MAX_RESULT_WINDOW)
			).build();

		return new ElasticsearchIndexingFixture() {
			{
				setElasticsearchFixture(
					new ElasticsearchFixture(elasticsearchConnectionFixture));
				setLiferayMappingsAddedToIndex(true);
			}
		};
	}

	private SearchContext _getSearchContext() {
		SearchContext searchContext = new SearchContext();

		searchContext.setCompanyId(_indexingFixture.getCompanyId());

		return searchContext;
	}

	private void _setUpDeepPagination() {
		DeepPaginationConfigurationWrapper deepPaginationConfigurationWrapper =
			Mockito.mock(DeepPaginationConfigurationWrapper.class);

		Mockito.doReturn(
			true
		).when(
			deepPaginationConfigurationWrapper
		).getEnableDeepPagination();

		Mockito.doReturn(
			60
		).when(
			deepPaginationConfigurationWrapper
		).getPointInTimeKeepAliveSeconds();

		ReflectionTestUtil.setFieldValue(
			_indexSearcher, "_deepPaginationConfigurationWrapper",
			deepPaginationConfigurationWrapper);
	}

	private void _setUpIndexingFixture() throws Exception {
		if (_indexingFixture != null) {
			Assume.assumeTrue(_indexingFixture.isSearchEngineAvailable());

			return;
		}

		_indexingFixture = _createIndexingFixture();

		Assume.assumeTrue(_indexingFixture.isSearchEngineAvailable());

		_indexingFixture.setUp();

		_indexSearcher = _indexingFixture.getIndexSearcher();
		_indexWriter = _indexingFixture.getIndexWriter();

		_setUpIndexSearchLimit();
		_setUpDeepPagination();
		_setUpSorts();
	}

	private void _setUpIndexSearchLimit() {
		Props props = Mockito.mock(Props.class);

		Mockito.doReturn(
			String.valueOf(_INDEX_SEARCH_LIMIT)
		).when(
			props
		).get(
			PropsKeys.INDEX_SEARCH_LIMIT
		);

		ReflectionTestUtil.setFieldValue(_indexSearcher, "_props", props);
	}

	private void _setUpSorts() {
		Sorts sorts = Mockito.mock(Sorts.class);

		Mockito.doReturn(
			new ScoreSortImpl()
		).when(
			sorts
		).score();

		Mockito.doReturn(
			new FieldSortImpl("_shard_doc")
		).when(
			sorts
		).field(
			"_shard_doc"
		);

		ReflectionTestUtil.setFieldValue(_indexSearcher, "_sorts", sorts);
	}

	private void printHits(Document[] documents) {
		for (Document document : documents) {
			System.out.println("Title: " + document.get(Field.TITLE));
		}
	}

	private static final int _INDEX_MAX_RESULT_WINDOW = 3;

	private static final int _INDEX_SEARCH_LIMIT = 2;

	private static final int _NUMBER_INDEXED_DOCUMENTS = 7;

	private static IndexingFixture _indexingFixture;

	private final DocumentFixture _documentFixture = new DocumentFixture();
	private final List<Document> _documents = new ArrayList<>();
	private String _entryClassName;
	private IndexSearcher _indexSearcher;
	private IndexWriter _indexWriter;
	private SearchRequestBuilderFactory _searchRequestBuilderFactory;

}