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

import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.IndexWriter;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.SearchException;
import com.liferay.portal.kernel.search.generic.MatchQuery;
import com.liferay.portal.search.test.util.indexing.BaseIndexingTestCase;
import com.liferay.portal.search.test.util.indexing.DocumentFixture;
import com.liferay.portal.search.test.util.indexing.IndexingFixture;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Adam Brandizzi
 */
public class ElasticsearchIndexWriterTest extends BaseIndexingTestCase {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_indexWriter = getIndexWriter();
	}

	@After
	@Override
	public void tearDown() throws SearchException {
		Stream<Document> stream = _documents.stream();

		_indexWriter.deleteDocuments(
			_getSearchContext(),
			stream.map(
				document -> document.get(Field.UID)
			).collect(
				Collectors.toList()
			));

		_documents.clear();
	}

	@Test
	public void testAddDocument() {
		addDocument(Field.TITLE, "text");

		_assertOnlyOne(Field.TITLE, "text");
	}

	@Test
	public void testDeleteDocument() throws SearchException {
		Document document = addDocument(Field.TITLE, "text");

		_indexWriter.deleteDocument(
			_getSearchContext(), document.get(Field.UID));

		_assertNone(Field.TITLE, "text");
	}

	@Test
	public void testPartiallyUpdateDocument() throws SearchException {
		Document document = addDocument(
			Field.TITLE, "text", Field.CONTENT, "example");

		document.addText(Field.TITLE, "change");

		_indexWriter.partiallyUpdateDocument(createSearchContext(), document);

		_assertNone(Field.TITLE, "text");

		_assertOnlyOne(Field.CONTENT, "example");
		_assertOnlyOne(Field.TITLE, "change");
	}

	@Test
	public void testPartiallyUpdateDocumentDoesNotRemoveFields()
		throws SearchException {

		Document document = addDocument(
			Field.TITLE, "text", Field.CONTENT, "example");

		document.remove(Field.CONTENT);

		_indexWriter.partiallyUpdateDocument(createSearchContext(), document);

		_assertOnlyOne(Field.CONTENT, "example");
		_assertOnlyOne(Field.TITLE, "text");
	}

	@Test
	public void testUpdateDocument() throws SearchException {
		Document document = addDocument(Field.TITLE, "text");

		document.addText(Field.TITLE, "example");

		_indexWriter.updateDocument(createSearchContext(), document);

		_assertNone(Field.TITLE, "text");

		_assertOnlyOne(Field.TITLE, "example");
	}

	@Test
	public void testUpdateDocumentRemovesFields() throws SearchException {
		Document document = addDocument(
			Field.TITLE, "text", Field.CONTENT, "example");

		document.remove(Field.CONTENT);

		_indexWriter.updateDocument(createSearchContext(), document);

		_assertNone(Field.CONTENT, "example");

		_assertOnlyOne(Field.TITLE, "text");
	}

	protected Document addDocument(String fieldName, String fieldValue) {
		Document document = createDocument(fieldName, fieldValue);

		addDocument(document);

		_documents.add(document);

		return document;
	}

	protected Document addDocument(
		String fieldName1, String fieldValue1, String fieldName2,
		String fieldValue2) {

		Document document = createDocument(
			fieldName1, fieldValue1, fieldName2, fieldValue2);

		addDocument(document);

		_documents.add(document);

		return document;
	}

	protected Document createDocument(String fieldName, String fieldValue) {
		Document document = DocumentFixture.newDocument(
			getCompanyId(), getGroupId(), getEntryClassName());

		document.addText(fieldName, fieldValue);

		return document;
	}

	protected Document createDocument(
		String fieldName1, String fieldValue1, String fieldName2,
		String fieldValue2) {

		Document document = DocumentFixture.newDocument(
			getCompanyId(), getGroupId(), getEntryClassName());

		document.addText(fieldName1, fieldValue1);
		document.addText(fieldName2, fieldValue2);

		return document;
	}

	@Override
	protected IndexingFixture createIndexingFixture() {
		return LiferayElasticsearchIndexingFixtureFactory.getInstance();
	}

	private void _assertNone(String field, String value) {
		assertSearch(
			indexingTestHelper -> {
				indexingTestHelper.setQuery(new MatchQuery(field, value));

				indexingTestHelper.search();

				indexingTestHelper.verify(
					hits -> Assert.assertEquals(0, hits.getLength()));
			});
	}

	private void _assertOnlyOne(String field, String value) {
		assertSearch(
			indexingTestHelper -> {
				indexingTestHelper.setQuery(new MatchQuery(field, value));

				indexingTestHelper.search();

				indexingTestHelper.verify(
					hits -> Assert.assertEquals(1, hits.getLength()));
			});
	}

	private SearchContext _getSearchContext() {
		SearchContext searchContext = new SearchContext();

		searchContext.setCompanyId(getCompanyId());

		return searchContext;
	}

	private final List<Document> _documents = new ArrayList<>();
	private IndexWriter _indexWriter;

}