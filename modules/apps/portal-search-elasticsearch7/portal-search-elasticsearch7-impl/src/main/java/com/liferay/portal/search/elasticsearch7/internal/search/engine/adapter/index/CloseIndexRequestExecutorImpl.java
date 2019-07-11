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

package com.liferay.portal.search.elasticsearch7.internal.search.engine.adapter.index;

import com.liferay.portal.search.elasticsearch7.internal.connection.ElasticsearchClientResolver;
import com.liferay.portal.search.engine.adapter.index.CloseIndexRequest;
import com.liferay.portal.search.engine.adapter.index.CloseIndexResponse;
import com.liferay.portal.search.engine.adapter.index.IndicesOptions;

import org.elasticsearch.action.admin.indices.close.CloseIndexAction;
import org.elasticsearch.action.admin.indices.close.CloseIndexRequestBuilder;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.common.unit.TimeValue;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Michael C. Han
 */
@Component(service = CloseIndexRequestExecutor.class)
public class CloseIndexRequestExecutorImpl
	implements CloseIndexRequestExecutor {

	@Override
	public CloseIndexResponse execute(CloseIndexRequest closeIndexRequest) {
		CloseIndexRequestBuilder closeIndexRequestBuilder =
			createCloseIndexRequestBuilder(closeIndexRequest);

		AcknowledgedResponse acknowledgedResponse =
			closeIndexRequestBuilder.get();

		return new CloseIndexResponse(acknowledgedResponse.isAcknowledged());
	}

	protected CloseIndexRequestBuilder createCloseIndexRequestBuilder(
		CloseIndexRequest closeIndexRequest) {

		CloseIndexRequestBuilder closeIndexRequestBuilder =
			new CloseIndexRequestBuilder(
				_elasticsearchClientResolver.getClient(),
				CloseIndexAction.INSTANCE);

		closeIndexRequestBuilder.setIndices(closeIndexRequest.getIndexNames());

		IndicesOptions indicesOptions = closeIndexRequest.getIndicesOptions();

		if (indicesOptions != null) {
			closeIndexRequestBuilder.setIndicesOptions(
				_indicesOptionsTranslator.translate(indicesOptions));
		}

		if (closeIndexRequest.getTimeout() > 0) {
			TimeValue timeValue = TimeValue.timeValueMillis(
				closeIndexRequest.getTimeout());

			closeIndexRequestBuilder.setMasterNodeTimeout(timeValue);
			closeIndexRequestBuilder.setTimeout(timeValue);
		}

		return closeIndexRequestBuilder;
	}

	@Reference(unbind = "-")
	protected void setElasticsearchClientResolver(
		ElasticsearchClientResolver elasticsearchClientResolver) {

		_elasticsearchClientResolver = elasticsearchClientResolver;
	}

	@Reference(unbind = "-")
	protected void setIndicesOptionsTranslator(
		IndicesOptionsTranslator indicesOptionsTranslator) {

		_indicesOptionsTranslator = indicesOptionsTranslator;
	}

	private ElasticsearchClientResolver _elasticsearchClientResolver;
	private IndicesOptionsTranslator _indicesOptionsTranslator;

}