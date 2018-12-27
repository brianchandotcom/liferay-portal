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

package com.liferay.portal.search.elasticsearch6.internal.search.engine.adapter.document;

import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.search.Query;
import com.liferay.portal.kernel.search.query.QueryTranslator;
import com.liferay.portal.search.elasticsearch6.internal.connection.ElasticsearchClientResolver;
import com.liferay.portal.search.engine.adapter.document.UpdateByQueryDocumentRequest;
import com.liferay.portal.search.engine.adapter.document.UpdateByQueryDocumentResponse;

import org.elasticsearch.client.Client;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.reindex.BulkByScrollResponse;
import org.elasticsearch.index.reindex.UpdateByQueryAction;
import org.elasticsearch.index.reindex.UpdateByQueryRequestBuilder;
import org.elasticsearch.script.Script;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Dylan Rebelak
 */
@Component(
	immediate = true, service = UpdateByQueryDocumentRequestExecutor.class
)
public class UpdateByQueryDocumentRequestExecutorImpl
	implements UpdateByQueryDocumentRequestExecutor {

	@Override
	public UpdateByQueryDocumentResponse execute(
		UpdateByQueryDocumentRequest updateByQueryDocumentRequest) {

		UpdateByQueryRequestBuilder updateByQueryRequestBuilder =
			createUpdateByQueryRequestBuilder(updateByQueryDocumentRequest);

		BulkByScrollResponse bulkByScrollResponse =
			updateByQueryRequestBuilder.get();

		TimeValue timeValue = bulkByScrollResponse.getTook();

		UpdateByQueryDocumentResponse updateByQueryDocumentResponse =
			new UpdateByQueryDocumentResponse(
				bulkByScrollResponse.getUpdated(), timeValue.getMillis());

		return updateByQueryDocumentResponse;
	}

	protected UpdateByQueryRequestBuilder createUpdateByQueryRequestBuilder(
		UpdateByQueryDocumentRequest updateByQueryDocumentRequest) {

		Client client = elasticsearchClientResolver.getClient();

		UpdateByQueryRequestBuilder updateByQueryRequestBuilder =
			UpdateByQueryAction.INSTANCE.newRequestBuilder(client);

		Query query = updateByQueryDocumentRequest.getQuery();

		QueryBuilder queryBuilder = queryTranslator.translate(query, null);

		updateByQueryRequestBuilder.filter(queryBuilder);

		updateByQueryRequestBuilder.refresh(
			updateByQueryDocumentRequest.isRefresh());

		JSONObject jsonObject =
			updateByQueryDocumentRequest.getScriptJSONObject();

		if (jsonObject != null) {
			Script script = new Script(jsonObject.toString());

			updateByQueryRequestBuilder.script(script);
		}

		updateByQueryRequestBuilder.source(
			updateByQueryDocumentRequest.getIndexNames());

		return updateByQueryRequestBuilder;
	}

	@Reference
	protected ElasticsearchClientResolver elasticsearchClientResolver;

	@Reference(target = "(search.engine.impl=Elasticsearch)")
	protected QueryTranslator<QueryBuilder> queryTranslator;

}