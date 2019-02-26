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

package com.liferay.portal.search.elasticsearch6.internal;

import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.search.BaseIndexWriter;
import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.BooleanQuery;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.IndexWriter;
import com.liferay.portal.kernel.search.ParseException;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.filter.BooleanFilter;
import com.liferay.portal.kernel.search.filter.TermFilter;
import com.liferay.portal.kernel.search.generic.BooleanQueryImpl;
import com.liferay.portal.kernel.search.generic.MatchAllQuery;
import com.liferay.portal.kernel.search.suggest.SpellCheckIndexWriter;
import com.liferay.portal.kernel.util.PortalRunMode;
import com.liferay.portal.search.elasticsearch6.internal.index.IndexNameBuilder;
import com.liferay.portal.search.elasticsearch6.internal.util.DocumentTypes;
import com.liferay.portal.search.engine.adapter.SearchEngineAdapter;
import com.liferay.portal.search.engine.adapter.document.BulkDocumentRequest;
import com.liferay.portal.search.engine.adapter.document.DeleteByQueryDocumentRequest;
import com.liferay.portal.search.engine.adapter.document.DeleteDocumentRequest;
import com.liferay.portal.search.engine.adapter.document.IndexDocumentRequest;
import com.liferay.portal.search.engine.adapter.document.UpdateDocumentRequest;
import com.liferay.portal.search.engine.adapter.index.RefreshIndexRequest;

import java.util.Collection;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Michael C. Han
 * @author Milen Dyankov
 */
@Component(
	immediate = true, property = "search.engine.impl=Elasticsearch",
	service = IndexWriter.class
)
public class ElasticsearchIndexWriter extends BaseIndexWriter {

	@Override
	public void addDocument(SearchContext searchContext, Document document) {
		String indexName = _indexNameBuilder.getIndexName(
			searchContext.getCompanyId());

		IndexDocumentRequest indexDocumentRequest = new IndexDocumentRequest(
			indexName, document);

		indexDocumentRequest.setType(DocumentTypes.LIFERAY);

		if (PortalRunMode.isTestMode() || searchContext.isCommitImmediately()) {
			indexDocumentRequest.setRefresh(true);
		}

		_searchEngineAdapter.execute(indexDocumentRequest);
	}

	@Override
	public void addDocuments(
		SearchContext searchContext, Collection<Document> documents) {

		String indexName = _indexNameBuilder.getIndexName(
			searchContext.getCompanyId());

		BulkDocumentRequest bulkDocumentRequest = new BulkDocumentRequest();

		if (PortalRunMode.isTestMode() || searchContext.isCommitImmediately()) {
			bulkDocumentRequest.setRefresh(true);
		}

		documents.forEach(
			document -> {
				IndexDocumentRequest indexDocumentRequest =
					new IndexDocumentRequest(indexName, document);

				indexDocumentRequest.setType(DocumentTypes.LIFERAY);

				bulkDocumentRequest.addBulkableDocumentRequest(
					indexDocumentRequest);
			});

		_searchEngineAdapter.execute(bulkDocumentRequest);
	}

	@Override
	public void commit(SearchContext searchContext) {
		String indexName = _indexNameBuilder.getIndexName(
			searchContext.getCompanyId());

		RefreshIndexRequest refreshIndexRequest = new RefreshIndexRequest(
			indexName);

		_searchEngineAdapter.execute(refreshIndexRequest);
	}

	@Override
	public void deleteDocument(SearchContext searchContext, String uid) {
		String indexName = _indexNameBuilder.getIndexName(
			searchContext.getCompanyId());

		DeleteDocumentRequest deleteDocumentRequest = new DeleteDocumentRequest(
			indexName, uid);

		if (PortalRunMode.isTestMode() || searchContext.isCommitImmediately()) {
			deleteDocumentRequest.setRefresh(true);
		}

		deleteDocumentRequest.setType(DocumentTypes.LIFERAY);

		_searchEngineAdapter.execute(deleteDocumentRequest);
	}

	@Override
	public void deleteDocuments(
		SearchContext searchContext, Collection<String> uids) {

		String indexName = _indexNameBuilder.getIndexName(
			searchContext.getCompanyId());

		BulkDocumentRequest bulkDocumentRequest = new BulkDocumentRequest();

		if (PortalRunMode.isTestMode() || searchContext.isCommitImmediately()) {
			bulkDocumentRequest.setRefresh(true);
		}

		uids.forEach(
			uid -> {
				DeleteDocumentRequest deleteDocumentRequest =
					new DeleteDocumentRequest(indexName, uid);

				deleteDocumentRequest.setType(DocumentTypes.LIFERAY);

				bulkDocumentRequest.addBulkableDocumentRequest(
					deleteDocumentRequest);
			});

		_searchEngineAdapter.execute(bulkDocumentRequest);
	}

	@Override
	public void deleteEntityDocuments(
		SearchContext searchContext, String className) {

		String indexName = _indexNameBuilder.getIndexName(
			searchContext.getCompanyId());

		try {
			BooleanQuery booleanQuery = new BooleanQueryImpl();

			booleanQuery.add(new MatchAllQuery(), BooleanClauseOccur.MUST);

			BooleanFilter booleanFilter = new BooleanFilter();

			booleanFilter.add(
				new TermFilter(Field.ENTRY_CLASS_NAME, className),
				BooleanClauseOccur.MUST);

			booleanQuery.setPreBooleanFilter(booleanFilter);

			DeleteByQueryDocumentRequest deleteByQueryDocumentRequest =
				new DeleteByQueryDocumentRequest(booleanQuery, indexName);

			if (PortalRunMode.isTestMode() ||
				searchContext.isCommitImmediately()) {

				deleteByQueryDocumentRequest.setRefresh(true);
			}

			_searchEngineAdapter.execute(deleteByQueryDocumentRequest);
		}
		catch (ParseException pe) {
			throw new SystemException(pe);
		}
	}

	@Override
	public void partiallyUpdateDocument(
		SearchContext searchContext, Document document) {

		String indexName = _indexNameBuilder.getIndexName(
			searchContext.getCompanyId());

		UpdateDocumentRequest updateDocumentRequest = new UpdateDocumentRequest(
			indexName, document.getUID(), document);

		updateDocumentRequest.setType(DocumentTypes.LIFERAY);

		if (PortalRunMode.isTestMode() || searchContext.isCommitImmediately()) {
			updateDocumentRequest.setRefresh(true);
		}

		_searchEngineAdapter.execute(updateDocumentRequest);
	}

	@Override
	public void partiallyUpdateDocuments(
		SearchContext searchContext, Collection<Document> documents) {

		String indexName = _indexNameBuilder.getIndexName(
			searchContext.getCompanyId());

		BulkDocumentRequest bulkDocumentRequest = new BulkDocumentRequest();

		if (PortalRunMode.isTestMode() || searchContext.isCommitImmediately()) {
			bulkDocumentRequest.setRefresh(true);
		}

		documents.forEach(
			document -> {
				UpdateDocumentRequest updateDocumentRequest =
					new UpdateDocumentRequest(
						indexName, document.getUID(), document);

				updateDocumentRequest.setType(DocumentTypes.LIFERAY);

				bulkDocumentRequest.addBulkableDocumentRequest(
					updateDocumentRequest);
			});

		_searchEngineAdapter.execute(bulkDocumentRequest);
	}

	@Override
	@Reference(target = "(search.engine.impl=Elasticsearch)", unbind = "-")
	public void setSpellCheckIndexWriter(
		SpellCheckIndexWriter spellCheckIndexWriter) {

		super.setSpellCheckIndexWriter(spellCheckIndexWriter);
	}

	@Override
	public void updateDocument(SearchContext searchContext, Document document) {
		String indexName = _indexNameBuilder.getIndexName(
			searchContext.getCompanyId());

		BulkDocumentRequest bulkDocumentRequest = new BulkDocumentRequest();

		if (PortalRunMode.isTestMode() || searchContext.isCommitImmediately()) {
			bulkDocumentRequest.setRefresh(true);
		}

		DeleteDocumentRequest deleteDocumentRequest = new DeleteDocumentRequest(
			indexName, document.getUID());

		deleteDocumentRequest.setType(DocumentTypes.LIFERAY);

		bulkDocumentRequest.addBulkableDocumentRequest(deleteDocumentRequest);

		IndexDocumentRequest indexDocumentRequest = new IndexDocumentRequest(
			indexName, document);

		indexDocumentRequest.setType(DocumentTypes.LIFERAY);

		bulkDocumentRequest.addBulkableDocumentRequest(indexDocumentRequest);

		_searchEngineAdapter.execute(bulkDocumentRequest);
	}

	@Override
	public void updateDocuments(
		SearchContext searchContext, Collection<Document> documents) {

		String indexName = _indexNameBuilder.getIndexName(
			searchContext.getCompanyId());

		BulkDocumentRequest bulkDocumentRequest = new BulkDocumentRequest();

		if (PortalRunMode.isTestMode() || searchContext.isCommitImmediately()) {
			bulkDocumentRequest.setRefresh(true);
		}

		documents.forEach(
			document -> {
				DeleteDocumentRequest deleteDocumentRequest =
					new DeleteDocumentRequest(indexName, document.getUID());

				deleteDocumentRequest.setType(DocumentTypes.LIFERAY);

				bulkDocumentRequest.addBulkableDocumentRequest(
					deleteDocumentRequest);

				IndexDocumentRequest indexDocumentRequest =
					new IndexDocumentRequest(indexName, document);

				indexDocumentRequest.setType(DocumentTypes.LIFERAY);

				bulkDocumentRequest.addBulkableDocumentRequest(
					indexDocumentRequest);
			});

		_searchEngineAdapter.execute(bulkDocumentRequest);
	}

	@Reference(unbind = "-")
	protected void setIndexNameBuilder(IndexNameBuilder indexNameBuilder) {
		_indexNameBuilder = indexNameBuilder;
	}

	@Reference(target = "(search.engine.impl=Elasticsearch)", unbind = "-")
	protected void setSearchEngineAdapter(
		SearchEngineAdapter searchEngineAdapter) {

		_searchEngineAdapter = searchEngineAdapter;
	}

	private IndexNameBuilder _indexNameBuilder;
	private SearchEngineAdapter _searchEngineAdapter;

}