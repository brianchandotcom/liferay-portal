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

import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
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
import com.liferay.portal.search.elasticsearch7.internal.configuration.ElasticsearchConfigurationWrapper;
import com.liferay.portal.search.elasticsearch7.internal.logging.ElasticsearchExceptionHandler;
import com.liferay.portal.search.elasticsearch7.internal.util.DocumentTypes;
import com.liferay.portal.search.engine.adapter.SearchEngineAdapter;
import com.liferay.portal.search.engine.adapter.document.BulkDocumentRequest;
import com.liferay.portal.search.engine.adapter.document.BulkDocumentResponse;
import com.liferay.portal.search.engine.adapter.document.DeleteByQueryDocumentRequest;
import com.liferay.portal.search.engine.adapter.document.DeleteDocumentRequest;
import com.liferay.portal.search.engine.adapter.document.IndexDocumentRequest;
import com.liferay.portal.search.engine.adapter.document.UpdateDocumentRequest;
import com.liferay.portal.search.engine.adapter.index.RefreshIndexRequest;
import com.liferay.portal.search.index.IndexNameBuilder;

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

		try {
			_searchEngineAdapter.execute(indexDocumentRequest);
		}
		catch (RuntimeException runtimeException) {
			if (_elasticsearchConfigurationWrapper.logExceptionsOnly()) {
				_log.error(runtimeException);
			}
			else {
				throw runtimeException;
			}
		}
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

		BulkDocumentResponse bulkDocumentResponse =
			_searchEngineAdapter.execute(bulkDocumentRequest);

		if (bulkDocumentResponse.hasErrors()) {
			if (_elasticsearchConfigurationWrapper.logExceptionsOnly()) {
				_log.error("Bulk add failed");
			}
			else {
				throw new SystemException("Bulk add failed");
			}
		}
	}

	@Override
	public void commit(SearchContext searchContext) {
		String indexName = _indexNameBuilder.getIndexName(
			searchContext.getCompanyId());

		RefreshIndexRequest refreshIndexRequest = new RefreshIndexRequest(
			indexName);

		try {
			_searchEngineAdapter.execute(refreshIndexRequest);
		}
		catch (RuntimeException runtimeException) {
			if (_elasticsearchConfigurationWrapper.logExceptionsOnly()) {
				_log.error(runtimeException);
			}
			else {
				throw runtimeException;
			}
		}
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

		try {
			_searchEngineAdapter.execute(deleteDocumentRequest);
		}
		catch (RuntimeException runtimeException) {
			ElasticsearchExceptionHandler elasticsearchExceptionHandler =
				new ElasticsearchExceptionHandler(
					_log,
					_elasticsearchConfigurationWrapper.logExceptionsOnly());

			elasticsearchExceptionHandler.handleDeleteDocumentException(
				runtimeException);
		}
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

		BulkDocumentResponse bulkDocumentResponse =
			_searchEngineAdapter.execute(bulkDocumentRequest);

		if (bulkDocumentResponse.hasErrors()) {
			if (_elasticsearchConfigurationWrapper.logExceptionsOnly()) {
				_log.error("Bulk delete failed");
			}
			else {
				throw new SystemException("Bulk delete failed");
			}
		}
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
		catch (ParseException parseException) {
			throw new SystemException(parseException);
		}
		catch (RuntimeException runtimeException) {
			if (_elasticsearchConfigurationWrapper.logExceptionsOnly()) {
				_log.error(runtimeException);
			}
			else {
				throw runtimeException;
			}
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

		try {
			_searchEngineAdapter.execute(updateDocumentRequest);
		}
		catch (RuntimeException runtimeException) {
			if (_elasticsearchConfigurationWrapper.logExceptionsOnly()) {
				_log.error(runtimeException);
			}
			else {
				throw runtimeException;
			}
		}
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

		BulkDocumentResponse bulkDocumentResponse =
			_searchEngineAdapter.execute(bulkDocumentRequest);

		if (bulkDocumentResponse.hasErrors()) {
			if (_elasticsearchConfigurationWrapper.logExceptionsOnly()) {
				_log.error("Bulk partial update failed");
			}
			else {
				throw new SystemException("Bulk partial update failed");
			}
		}
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

		BulkDocumentResponse bulkDocumentResponse =
			_searchEngineAdapter.execute(bulkDocumentRequest);

		if (bulkDocumentResponse.hasErrors()) {
			if (_elasticsearchConfigurationWrapper.logExceptionsOnly()) {
				_log.error("Update failed");
			}
			else {
				throw new SystemException("Update failed");
			}
		}
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

		BulkDocumentResponse bulkDocumentResponse =
			_searchEngineAdapter.execute(bulkDocumentRequest);

		if (bulkDocumentResponse.hasErrors()) {
			if (_elasticsearchConfigurationWrapper.logExceptionsOnly()) {
				_log.error("Bulk update failed");
			}
			else {
				throw new SystemException("Bulk update failed");
			}
		}
	}

	@Override
	protected SpellCheckIndexWriter getSpellCheckIndexWriter() {
		return _spellCheckIndexWriter;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ElasticsearchIndexWriter.class);

	@Reference
	private volatile ElasticsearchConfigurationWrapper
		_elasticsearchConfigurationWrapper;

	@Reference
	private IndexNameBuilder _indexNameBuilder;

	@Reference(target = "(search.engine.impl=Elasticsearch)")
	private SearchEngineAdapter _searchEngineAdapter;

	@Reference(target = "(search.engine.impl=Elasticsearch)")
	private SpellCheckIndexWriter _spellCheckIndexWriter;

}