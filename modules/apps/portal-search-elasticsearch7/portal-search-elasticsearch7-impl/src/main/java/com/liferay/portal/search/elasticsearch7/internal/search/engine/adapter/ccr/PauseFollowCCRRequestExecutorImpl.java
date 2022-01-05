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

package com.liferay.portal.search.elasticsearch7.internal.search.engine.adapter.ccr;

import com.liferay.portal.search.elasticsearch7.internal.connection.ElasticsearchClientResolver;
import com.liferay.portal.search.engine.adapter.ccr.PauseFollowCCRRequest;
import com.liferay.portal.search.engine.adapter.ccr.PauseFollowCCRResponse;

import java.io.IOException;

import org.elasticsearch.client.CcrClient;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.ccr.PauseFollowRequest;
import org.elasticsearch.client.core.AcknowledgedResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Bryan Engler
 */
@Component(service = PauseFollowCCRRequestExecutor.class)
public class PauseFollowCCRRequestExecutorImpl
	implements PauseFollowCCRRequestExecutor {

	@Override
	public PauseFollowCCRResponse execute(
		PauseFollowCCRRequest pauseFollowCCRRequest) {

		PauseFollowRequest pauseFollowRequest = _createPauseFollowRequest(
			pauseFollowCCRRequest);

		AcknowledgedResponse acknowledgedResponse = getAcknowledgedResponse(
			pauseFollowRequest, pauseFollowCCRRequest);

		return new PauseFollowCCRResponse(
			acknowledgedResponse.isAcknowledged());
	}

	protected AcknowledgedResponse getAcknowledgedResponse(
		PauseFollowRequest pauseFollowRequest,
		PauseFollowCCRRequest pauseFollowCCRRequest) {

		RestHighLevelClient restHighLevelClient =
			_elasticsearchClientResolver.getRestHighLevelClient(
				pauseFollowCCRRequest.getConnectionId(),
				pauseFollowCCRRequest.isPreferLocalCluster());

		CcrClient ccrClient = restHighLevelClient.ccr();

		try {
			return ccrClient.pauseFollow(
				pauseFollowRequest, RequestOptions.DEFAULT);
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}
	}

	@Reference(unbind = "-")
	protected void setElasticsearchClientResolver(
		ElasticsearchClientResolver elasticsearchClientResolver) {

		_elasticsearchClientResolver = elasticsearchClientResolver;
	}

	private PauseFollowRequest _createPauseFollowRequest(
		PauseFollowCCRRequest pauseFollowCCRRequest) {

		return new PauseFollowRequest(pauseFollowCCRRequest.getIndexName());
	}

	private ElasticsearchClientResolver _elasticsearchClientResolver;

}