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

package com.liferay.portal.search.elasticsearch;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.SearchException;
import com.liferay.portal.kernel.util.PortalRunMode;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.search.elasticsearch.connection.ElasticsearchConnectionManager;
import com.liferay.portal.search.elasticsearch.document.ElasticsearchDocumentFactory;
import com.liferay.portal.search.elasticsearch.util.DocumentTypes;
import com.liferay.portal.search.elasticsearch.util.LogUtil;

import java.io.IOException;

import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.Future;

import org.elasticsearch.action.ActionResponse;
import org.elasticsearch.action.bulk.BulkItemResponse;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequestBuilder;
import org.elasticsearch.action.update.UpdateRequestBuilder;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.Client;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Michael C. Han
 */
@Component(immediate = true)
public class ElasticsearchUpdateDocumentCommandImpl
	implements ElasticsearchUpdateDocumentCommand {

	@Reference
	public void setElasticsearchConnectionManager(
		ElasticsearchConnectionManager elasticsearchConnectionManager) {

		_elasticsearchConnectionManager = elasticsearchConnectionManager;
	}

	@Reference
	public void setElasticsearchDocumentFactory(
		ElasticsearchDocumentFactory elasticsearchDocumentFactory) {

		_elasticsearchDocumentFactory = elasticsearchDocumentFactory;
	}

	@Override
	public String updateDocument(
			String documentType, SearchContext searchContext, Document document,
			boolean deleteFirst)
		throws SearchException {

		BulkResponse bulkResponse = doUpdateDocuments(
			documentType, searchContext, Arrays.asList(document), deleteFirst);

		BulkItemResponse[] bulkItemResponses = bulkResponse.getItems();

		for (BulkItemResponse bulkItemResponse : bulkItemResponses) {
			ActionResponse actionResponse = bulkItemResponse.getResponse();

			if (actionResponse instanceof UpdateResponse) {
				UpdateResponse updateResponse = (UpdateResponse)actionResponse;

				return updateResponse.getId();
			}
		}

		return StringPool.BLANK;
	}

	@Override
	public void updateDocuments(
			String documentType, SearchContext searchContext,
			Collection<Document> documents, boolean deleteFirst)
		throws SearchException {

		try {
			doUpdateDocuments(
				documentType, searchContext, documents, deleteFirst);
		}
		catch (Exception e) {
			throw new SearchException(
				"Unable to update documents " + documents, e);
		}
	}

	protected UpdateRequestBuilder buildUpdateRequestBuilder(
			String documentType, SearchContext searchContext, Document document)
		throws IOException {

		Client client = _elasticsearchConnectionManager.getClient();

		UpdateRequestBuilder updateRequestBuilder = client.prepareUpdate(
			String.valueOf(searchContext.getCompanyId()), documentType,
			document.getUID());

		String elasticSearchDocument =
			_elasticsearchDocumentFactory.getElasticsearchDocument(document);

		updateRequestBuilder.setDoc(elasticSearchDocument);
		updateRequestBuilder.setDocAsUpsert(true);

		return updateRequestBuilder;
	}

	protected BulkResponse doUpdateDocuments(
			String documentType, SearchContext searchContext,
			Collection<Document> documents, boolean deleteFirst)
		throws SearchException {

		try {
			Client client = _elasticsearchConnectionManager.getClient();

			BulkRequestBuilder bulkRequestBuilder = client.prepareBulk();

			for (Document document : documents) {
				if (deleteFirst) {
					DeleteRequestBuilder deleteRequestBuilder =
						client.prepareDelete(
							String.valueOf(searchContext.getCompanyId()),
							DocumentTypes.LIFERAY, document.getUID());

					bulkRequestBuilder.add(deleteRequestBuilder);
				}

				UpdateRequestBuilder updateRequestBuilder =
					buildUpdateRequestBuilder(
						documentType, searchContext, document);

				bulkRequestBuilder.add(updateRequestBuilder);
			}

			if (PortalRunMode.isTestMode()||
				searchContext.isCommitImmediately()) {

				bulkRequestBuilder.setRefresh(true);
			}

			Future<BulkResponse> future = bulkRequestBuilder.execute();

			BulkResponse bulkResponse = future.get();

			LogUtil.logActionResponse(_log, bulkResponse);

			return bulkResponse;
		}
		catch (Exception e) {
			throw new SearchException(
				"Unable to update documents " + documents, e);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ElasticsearchUpdateDocumentCommandImpl.class);

	private ElasticsearchConnectionManager _elasticsearchConnectionManager;
	private ElasticsearchDocumentFactory _elasticsearchDocumentFactory;

}