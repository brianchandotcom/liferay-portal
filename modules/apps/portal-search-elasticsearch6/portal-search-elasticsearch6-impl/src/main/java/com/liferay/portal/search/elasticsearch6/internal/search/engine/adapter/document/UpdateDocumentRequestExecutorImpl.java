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

import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.search.elasticsearch6.internal.connection.ElasticsearchConnectionManager;
import com.liferay.portal.search.elasticsearch6.internal.document.DefaultElasticsearchDocumentFactory;
import com.liferay.portal.search.elasticsearch6.internal.document.ElasticsearchDocumentFactory;
import com.liferay.portal.search.engine.adapter.document.UpdateDocumentRequest;
import com.liferay.portal.search.engine.adapter.document.UpdateDocumentResponse;

import java.io.IOException;

import org.elasticsearch.action.update.UpdateAction;
import org.elasticsearch.action.update.UpdateRequestBuilder;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.rest.RestStatus;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Dylan Rebelak
 */
@Component(immediate = true, service = UpdateDocumentRequestExecutor.class)
public class UpdateDocumentRequestExecutorImpl
	implements UpdateDocumentRequestExecutor {

	@Override
	public UpdateDocumentResponse execute(
		UpdateDocumentRequest updateDocumentRequest) {

		try {
			UpdateRequestBuilder updateRequestBuilder =
				createUpdateRequestBuilder(updateDocumentRequest);

			UpdateResponse updateResponse = updateRequestBuilder.get();

			RestStatus restStatus = updateResponse.status();

			UpdateDocumentResponse updateDocumentResponse =
				new UpdateDocumentResponse(restStatus.getStatus());

			return updateDocumentResponse;
		}
		catch (IOException ioe) {
			throw new SystemException(ioe);
		}
	}

	protected UpdateRequestBuilder createUpdateRequestBuilder(
			UpdateDocumentRequest updateDocumentRequest)
		throws IOException {

		Client client = elasticsearchConnectionManager.getClient();

		UpdateRequestBuilder updateRequestBuilder =
			UpdateAction.INSTANCE.newRequestBuilder(client);

		Document document = updateDocumentRequest.getDocument();

		updateRequestBuilder.setIndex(updateDocumentRequest.getIndexName());
		updateRequestBuilder.setId(document.getUID());
		updateRequestBuilder.setType(document.get(Field.TYPE));

		ElasticsearchDocumentFactory elasticsearchDocumentFactory =
			new DefaultElasticsearchDocumentFactory();

		String elasticsearchDocument =
			elasticsearchDocumentFactory.getElasticsearchDocument(document);

		updateRequestBuilder.setDoc(elasticsearchDocument, XContentType.JSON);

		return updateRequestBuilder;
	}

	@Reference
	protected ElasticsearchConnectionManager elasticsearchConnectionManager;

}