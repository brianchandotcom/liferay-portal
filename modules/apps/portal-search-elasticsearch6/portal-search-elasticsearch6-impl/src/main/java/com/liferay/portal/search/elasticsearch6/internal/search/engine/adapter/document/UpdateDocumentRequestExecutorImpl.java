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

import com.liferay.portal.search.engine.adapter.document.BulkableDocumentRequestTranslator;
import com.liferay.portal.search.engine.adapter.document.UpdateDocumentRequest;
import com.liferay.portal.search.engine.adapter.document.UpdateDocumentResponse;

import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.delete.DeleteRequestBuilder;
import org.elasticsearch.action.index.IndexRequestBuilder;
import org.elasticsearch.action.update.UpdateRequestBuilder;
import org.elasticsearch.action.update.UpdateResponse;
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

		UpdateRequestBuilder updateRequestBuilder =
			bulkableDocumentRequestTranslator.translate(
				updateDocumentRequest, null);

		UpdateResponse updateResponse = updateRequestBuilder.get();

		RestStatus restStatus = updateResponse.status();

		UpdateDocumentResponse updateDocumentResponse =
			new UpdateDocumentResponse(restStatus.getStatus());

		return updateDocumentResponse;
	}

	@Reference(target = "(search.engine.impl=Elasticsearch)")
	protected BulkableDocumentRequestTranslator
		<DeleteRequestBuilder, IndexRequestBuilder, UpdateRequestBuilder,
		 BulkRequestBuilder> bulkableDocumentRequestTranslator;

}