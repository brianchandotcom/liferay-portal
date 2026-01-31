/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.elasticsearch8.internal.search.engine.adapter.document;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch._types.query_dsl.QueryVariant;
import co.elastic.clients.elasticsearch.core.DeleteByQueryRequest;
import co.elastic.clients.elasticsearch.core.DeleteByQueryResponse;

import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.search.elasticsearch8.internal.connection.ElasticsearchClientResolver;
import com.liferay.portal.search.elasticsearch8.internal.legacy.query.ElasticsearchQueryTranslator;
import com.liferay.portal.search.engine.adapter.document.DeleteByQueryDocumentRequest;
import com.liferay.portal.search.engine.adapter.document.DeleteByQueryDocumentResponse;
import com.liferay.portal.search.query.QueryTranslator;

import java.io.IOException;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Dylan Rebelak
 */
@Component(service = DeleteByQueryDocumentRequestExecutor.class)
public class DeleteByQueryDocumentRequestExecutorImpl
	implements DeleteByQueryDocumentRequestExecutor {

	@Override
	public DeleteByQueryDocumentResponse execute(
		DeleteByQueryDocumentRequest deleteByQueryDocumentRequest) {

		DeleteByQueryResponse deleteByQueryResponse = _getDeleteByQueryResponse(
			deleteByQueryDocumentRequest,
			createDeleteByQueryRequest(deleteByQueryDocumentRequest));

		return new DeleteByQueryDocumentResponse(
			deleteByQueryResponse.total(), deleteByQueryResponse.took());
	}

	protected DeleteByQueryRequest createDeleteByQueryRequest(
		DeleteByQueryDocumentRequest deleteByQueryDocumentRequest) {

		DeleteByQueryRequest.Builder builder =
			new DeleteByQueryRequest.Builder();

		builder.index(
			ListUtil.fromArray(deleteByQueryDocumentRequest.getIndexNames()));

		if (deleteByQueryDocumentRequest.getPortalSearchQuery() != null) {
			builder.query(
				new Query(
					_queryTranslator.translate(
						deleteByQueryDocumentRequest.getPortalSearchQuery())));
		}
		else {
			builder.query(
				new Query(
					_legacyQueryTranslator.translate(
						deleteByQueryDocumentRequest.getQuery(), null)));
		}

		builder.refresh(deleteByQueryDocumentRequest.isRefresh());

		return builder.build();
	}

	private DeleteByQueryResponse _getDeleteByQueryResponse(
		DeleteByQueryDocumentRequest deleteByQueryDocumentRequest,
		DeleteByQueryRequest deleteByQueryRequest) {

		ElasticsearchClient elasticsearchClient =
			_elasticsearchClientResolver.getElasticsearchClient(
				deleteByQueryDocumentRequest.getConnectionId(),
				deleteByQueryDocumentRequest.isPreferLocalCluster());

		try {
			return elasticsearchClient.deleteByQuery(deleteByQueryRequest);
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}
	}

	@Reference
	private ElasticsearchClientResolver _elasticsearchClientResolver;

	private final com.liferay.portal.kernel.search.query.QueryTranslator
		<QueryVariant> _legacyQueryTranslator =
			new ElasticsearchQueryTranslator();
	private final QueryTranslator<QueryVariant> _queryTranslator =
		new com.liferay.portal.search.elasticsearch8.internal.query.
			ElasticsearchQueryTranslator();

}