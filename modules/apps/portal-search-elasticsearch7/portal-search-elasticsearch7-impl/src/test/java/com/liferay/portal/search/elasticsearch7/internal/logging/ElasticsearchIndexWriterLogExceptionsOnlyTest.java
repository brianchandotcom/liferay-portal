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

package com.liferay.portal.search.elasticsearch7.internal.logging;

import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.DocumentImpl;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.IndexWriter;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.SearchException;
import com.liferay.portal.search.elasticsearch7.internal.LiferayElasticsearchIndexingFixtureFactory;
import com.liferay.portal.search.elasticsearch7.internal.connection.ElasticsearchFixture;
import com.liferay.portal.search.elasticsearch7.internal.search.engine.adapter.document.BulkDocumentRequestExecutorImpl;
import com.liferay.portal.search.test.util.indexing.BaseIndexingTestCase;
import com.liferay.portal.search.test.util.indexing.DocumentCreationHelpers;
import com.liferay.portal.search.test.util.indexing.IndexingFixture;
import com.liferay.portal.search.test.util.logging.ExpectedLogTestRule;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

import org.junit.Rule;
import org.junit.Test;

/**
 * @author Bryan Engler
 */
public class ElasticsearchIndexWriterLogExceptionsOnlyTest
	extends BaseIndexingTestCase {

	@Test
	public void testAddDocument() throws Exception {
		expectedLogTestRule.expectMessage(
			"failed to parse field [expirationDate] of type [date]");

		addDocument(
			DocumentCreationHelpers.singleKeyword(
				Field.EXPIRATION_DATE, "text"));
	}

	@Test
	public void testAddDocuments() {
		expectedLogTestRule.expectMessage("Bulk add failed");

		List<Document> documents = new ArrayList<>();

		Document document = new DocumentImpl();

		document.addKeyword(Field.EXPIRATION_DATE, "text");

		documents.add(document);

		IndexWriter indexWriter = getIndexWriter();

		try {
			indexWriter.addDocuments(createSearchContext(), documents);
		}
		catch (SearchException se) {
		}
	}

	@Test
	public void testAddDocumentsBulkExecutor() {
		expectedLogTestRule.configure(
			BulkDocumentRequestExecutorImpl.class, Level.WARNING);
		expectedLogTestRule.expectMessage(
			"failed to parse field [expirationDate] of type [date]");

		List<Document> documents = new ArrayList<>();

		Document document = new DocumentImpl();

		document.addKeyword(Field.EXPIRATION_DATE, "text");

		documents.add(document);

		IndexWriter indexWriter = getIndexWriter();

		try {
			indexWriter.addDocuments(createSearchContext(), documents);
		}
		catch (SearchException se) {
		}
	}

	@Test
	public void testCommit() {
		expectedLogTestRule.expectMessage("no such index");

		SearchContext searchContext = new SearchContext();

		searchContext.setCompanyId(1);

		IndexWriter indexWriter = getIndexWriter();

		try {
			indexWriter.commit(searchContext);
		}
		catch (SearchException se) {
		}
	}

	@Test
	public void testDeleteDocument() {
		expectedLogTestRule.expectMessage("no such index");

		SearchContext searchContext = new SearchContext();

		searchContext.setCompanyId(1);

		IndexWriter indexWriter = getIndexWriter();

		try {
			indexWriter.deleteDocument(searchContext, "1");
		}
		catch (SearchException se) {
		}
	}

	@Test
	public void testDeleteDocuments() {
		expectedLogTestRule.expectMessage("Bulk delete failed");

		SearchContext searchContext = new SearchContext();

		searchContext.setCompanyId(1);

		List<String> uids = new ArrayList<>();

		uids.add("1");

		IndexWriter indexWriter = getIndexWriter();

		try {
			indexWriter.deleteDocuments(searchContext, uids);
		}
		catch (SearchException se) {
		}
	}

	@Test
	public void testDeleteDocumentsBulkExecutor() {
		expectedLogTestRule.configure(
			BulkDocumentRequestExecutorImpl.class, Level.WARNING);
		expectedLogTestRule.expectMessage("no such index");

		SearchContext searchContext = new SearchContext();

		searchContext.setCompanyId(1);

		List<String> uids = new ArrayList<>();

		uids.add("1");

		IndexWriter indexWriter = getIndexWriter();

		try {
			indexWriter.deleteDocuments(searchContext, uids);
		}
		catch (SearchException se) {
		}
	}

	@Test
	public void testDeleteEntityDocuments() {
		expectedLogTestRule.expectMessage("no such index");

		SearchContext searchContext = new SearchContext();

		searchContext.setCompanyId(1);

		IndexWriter indexWriter = getIndexWriter();

		try {
			indexWriter.deleteEntityDocuments(searchContext, "test");
		}
		catch (SearchException se) {
		}
	}

	@Test
	public void testPartiallyUpdateDocument() {
		expectedLogTestRule.expectMessage("document missing");

		Document document = new DocumentImpl();

		document.addKeyword(Field.UID, "1");

		IndexWriter indexWriter = getIndexWriter();

		try {
			indexWriter.partiallyUpdateDocument(
				createSearchContext(), document);
		}
		catch (SearchException se) {
		}
	}

	@Test
	public void testPartiallyUpdateDocuments() {
		expectedLogTestRule.expectMessage("Bulk partial update failed");

		Document document = new DocumentImpl();

		List<Document> documents = new ArrayList<>();

		document.addKeyword(Field.UID, "1");

		documents.add(document);

		IndexWriter indexWriter = getIndexWriter();

		try {
			indexWriter.partiallyUpdateDocuments(
				createSearchContext(), documents);
		}
		catch (SearchException se) {
		}
	}

	@Test
	public void testPartiallyUpdateDocumentsBulkExecutor() {
		expectedLogTestRule.configure(
			BulkDocumentRequestExecutorImpl.class, Level.WARNING);
		expectedLogTestRule.expectMessage("document missing");

		Document document = new DocumentImpl();

		List<Document> documents = new ArrayList<>();

		document.addKeyword(Field.UID, "1");

		documents.add(document);

		IndexWriter indexWriter = getIndexWriter();

		try {
			indexWriter.partiallyUpdateDocuments(
				createSearchContext(), documents);
		}
		catch (SearchException se) {
		}
	}

	@Test
	public void testUpdateDocument() {
		expectedLogTestRule.expectMessage("Update failed");

		Document document = new DocumentImpl();

		document.addKeyword(Field.UID, "1");
		document.addKeyword(Field.EXPIRATION_DATE, "text");

		IndexWriter indexWriter = getIndexWriter();

		try {
			indexWriter.updateDocument(createSearchContext(), document);
		}
		catch (SearchException se) {
		}
	}

	@Test
	public void testUpdateDocumentBulkExecutor() {
		expectedLogTestRule.configure(
			BulkDocumentRequestExecutorImpl.class, Level.WARNING);
		expectedLogTestRule.expectMessage(
			"failed to parse field [expirationDate] of type [date]");

		Document document = new DocumentImpl();

		document.addKeyword(Field.UID, "1");
		document.addKeyword(Field.EXPIRATION_DATE, "text");

		IndexWriter indexWriter = getIndexWriter();

		try {
			indexWriter.updateDocument(createSearchContext(), document);
		}
		catch (SearchException se) {
		}
	}

	@Test
	public void testUpdateDocuments() {
		expectedLogTestRule.expectMessage("Bulk update failed");

		List<Document> documents = new ArrayList<>();

		Document document = new DocumentImpl();

		document.addKeyword(Field.UID, "1");
		document.addKeyword(Field.EXPIRATION_DATE, "text");

		documents.add(document);

		IndexWriter indexWriter = getIndexWriter();

		try {
			indexWriter.updateDocuments(createSearchContext(), documents);
		}
		catch (SearchException se) {
		}
	}

	@Test
	public void testUpdateDocumentsBulkExecutor() {
		expectedLogTestRule.configure(
			BulkDocumentRequestExecutorImpl.class, Level.WARNING);
		expectedLogTestRule.expectMessage(
			"failed to parse field [expirationDate] of type [date]");

		List<Document> documents = new ArrayList<>();

		Document document = new DocumentImpl();

		document.addKeyword(Field.UID, "1");
		document.addKeyword(Field.EXPIRATION_DATE, "text");

		documents.add(document);

		IndexWriter indexWriter = getIndexWriter();

		try {
			indexWriter.updateDocuments(createSearchContext(), documents);
		}
		catch (SearchException se) {
		}
	}

	@Rule
	public ExpectedLogTestRule expectedLogTestRule = ExpectedLogTestRule.none();

	protected ElasticsearchFixture createElasticsearchFixture() {
		Map<String, Object> elasticsearchConfigurationProperties =
			new HashMap<>();

		elasticsearchConfigurationProperties.put("logExceptionsOnly", true);

		return new ElasticsearchFixture(
			ElasticsearchIndexWriterLogExceptionsOnlyTest.class.getSimpleName(),
			elasticsearchConfigurationProperties);
	}

	@Override
	protected IndexingFixture createIndexingFixture() {
		return LiferayElasticsearchIndexingFixtureFactory.builder(
		).elasticsearchFixture(
			createElasticsearchFixture()
		).build();
	}

}