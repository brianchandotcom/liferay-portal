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

package com.liferay.portal.search.elasticsearch.internal;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.BaseIndexWriter;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.IndexWriter;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.SearchException;
import com.liferay.portal.kernel.search.suggest.SpellCheckIndexWriter;
import com.liferay.portal.kernel.util.PortalRunMode;
import com.liferay.portal.search.elasticsearch.connection.ElasticsearchConnectionManager;
import com.liferay.portal.search.elasticsearch.document.ElasticsearchUpdateDocumentCommand;
import com.liferay.portal.search.elasticsearch.internal.util.DocumentTypes;
import com.liferay.portal.search.elasticsearch.internal.util.LogUtil;

import java.util.Collection;

import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequestBuilder;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.FilterBuilders;
import org.elasticsearch.index.query.MatchAllQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermFilterBuilder;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Michael C. Han
 * @author Milen Dyankov
 */
@Component(
	immediate = true, property = {"search.engine.impl=Elasticsearch"},
	service = IndexWriter.class
)
public class ElasticsearchIndexWriter extends BaseIndexWriter {

	@Override
	public void addDocument(SearchContext searchContext, Document document)
		throws SearchException {

		_elasticsearchUpdateDocumentCommand.updateDocument(
			DocumentTypes.LIFERAY, searchContext, document, false);
	}

	@Override
	public void addDocuments(
			SearchContext searchContext, Collection<Document> documents)
		throws SearchException {

		_elasticsearchUpdateDocumentCommand.updateDocuments(
			DocumentTypes.LIFERAY, searchContext, documents, false);
	}

	@Override
	public void deleteDocument(SearchContext searchContext, String uid)
		throws SearchException {

		try {
			Client client = _elasticsearchConnectionManager.getClient();

			DeleteRequestBuilder deleteRequestBuilder = client.prepareDelete(
				String.valueOf(searchContext.getCompanyId()),
				DocumentTypes.LIFERAY, uid);

			if (PortalRunMode.isTestMode() ||
				searchContext.isCommitImmediately()) {

				deleteRequestBuilder.setRefresh(true);
			}

			DeleteResponse deleteResponse = deleteRequestBuilder.get();

			LogUtil.logActionResponse(_log, deleteResponse);
		}
		catch (Exception e) {
			throw new SearchException("Unable to delete document " + uid, e);
		}
	}

	@Override
	public void deleteDocuments(
			SearchContext searchContext, Collection<String> uids)
		throws SearchException {

		try {
			Client client = _elasticsearchConnectionManager.getClient();

			BulkRequestBuilder bulkRequestBuilder = client.prepareBulk();

			for (String uid : uids) {
				DeleteRequestBuilder deleteRequestBuilder =
					client.prepareDelete(
						String.valueOf(searchContext.getCompanyId()),
						DocumentTypes.LIFERAY, uid);

				bulkRequestBuilder.add(deleteRequestBuilder);
			}

			if (PortalRunMode.isTestMode()||
				searchContext.isCommitImmediately()) {

				bulkRequestBuilder.setRefresh(true);
			}

			BulkResponse bulkResponse = bulkRequestBuilder.get();

			LogUtil.logActionResponse(_log, bulkResponse);
		}
		catch (Exception e) {
			throw new SearchException("Unable to delete documents " + uids, e);
		}
	}

	@Override
	public void deleteEntityDocuments(
			SearchContext searchContext, String className)
		throws SearchException {

		SearchResponseScroller searchResponseScroller = null;

		try {
			Client client = _elasticsearchConnectionManager.getClient();

			MatchAllQueryBuilder matchAllQueryBuilder =
				QueryBuilders.matchAllQuery();

			TermFilterBuilder termFilterBuilder = FilterBuilders.termFilter(
				Field.ENTRY_CLASS_NAME, className);

			termFilterBuilder.cache(false);

			QueryBuilder queryBuilder = QueryBuilders.filteredQuery(
				matchAllQueryBuilder, termFilterBuilder);

			searchResponseScroller = new SearchResponseScroller(
				client, searchContext, queryBuilder,
				TimeValue.timeValueSeconds(30), DocumentTypes.LIFERAY);

			searchResponseScroller.prepare();

			searchResponseScroller.scroll(_searchHitsProcessor);
		}
		catch (Exception e) {
			throw new SearchException(
				"Unable to delete data for entity " + className, e);
		}
		finally {
			if (searchResponseScroller != null) {
				searchResponseScroller.close();
			}
		}
	}

	@Override
	public void partiallyUpdateDocument(
			SearchContext searchContext, Document document)
		throws SearchException {

		_elasticsearchUpdateDocumentCommand.updateDocument(
			DocumentTypes.LIFERAY, searchContext, document, false);
	}

	@Override
	public void partiallyUpdateDocuments(
			SearchContext searchContext, Collection<Document> documents)
		throws SearchException {

		_elasticsearchUpdateDocumentCommand.updateDocuments(
			DocumentTypes.LIFERAY, searchContext, documents, false);
	}

	@Override
	@Reference(target = "(search.engine.impl=Elasticsearch)", unbind = "-")
	public void setSpellCheckIndexWriter(
		SpellCheckIndexWriter spellCheckIndexWriter) {

		super.setSpellCheckIndexWriter(spellCheckIndexWriter);
	}

	@Override
	public void updateDocument(SearchContext searchContext, Document document)
		throws SearchException {

		_elasticsearchUpdateDocumentCommand.updateDocument(
			DocumentTypes.LIFERAY, searchContext, document, true);
	}

	@Override
	public void updateDocuments(
			SearchContext searchContext, Collection<Document> documents)
		throws SearchException {

		_elasticsearchUpdateDocumentCommand.updateDocuments(
			DocumentTypes.LIFERAY, searchContext, documents, true);
	}

	@Activate
	protected void activate() {
		_searchHitsProcessor = new DeleteDocumentsSearchHitsProcessor(this);
	}

	@Reference(unbind = "-")
	protected void setElasticsearchConnectionManager(
		ElasticsearchConnectionManager elasticsearchConnectionManager) {

		_elasticsearchConnectionManager = elasticsearchConnectionManager;
	}

	@Reference(unbind = "-")
	protected void setElasticsearchUpdateDocumentCommand(
		ElasticsearchUpdateDocumentCommand elasticsearchUpdateDocumentCommand) {

		_elasticsearchUpdateDocumentCommand =
			elasticsearchUpdateDocumentCommand;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ElasticsearchIndexWriter.class);

	private volatile ElasticsearchConnectionManager _elasticsearchConnectionManager;
	private volatile ElasticsearchUpdateDocumentCommand
		_elasticsearchUpdateDocumentCommand;
	private SearchHitsProcessor _searchHitsProcessor;

}