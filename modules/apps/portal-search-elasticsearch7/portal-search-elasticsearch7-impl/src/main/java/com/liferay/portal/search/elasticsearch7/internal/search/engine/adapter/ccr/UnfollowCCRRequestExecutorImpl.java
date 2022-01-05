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
import com.liferay.portal.search.engine.adapter.ccr.UnfollowCCRRequest;
import com.liferay.portal.search.engine.adapter.ccr.UnfollowCCRResponse;

import java.io.IOException;

import org.elasticsearch.client.CcrClient;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.ccr.UnfollowRequest;
import org.elasticsearch.client.core.AcknowledgedResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Bryan Engler
 */
@Component(service = UnfollowCCRRequestExecutor.class)
public class UnfollowCCRRequestExecutorImpl
	implements UnfollowCCRRequestExecutor {

	@Override
	public UnfollowCCRResponse execute(UnfollowCCRRequest unfollowCCRRequest) {
		UnfollowRequest unfollowRequest = _createUnfollowRequest(
			unfollowCCRRequest);

		AcknowledgedResponse acknowledgedResponse = getAcknowledgedResponse(
			unfollowRequest, unfollowCCRRequest);

		return new UnfollowCCRResponse(acknowledgedResponse.isAcknowledged());
	}

	protected AcknowledgedResponse getAcknowledgedResponse(
		UnfollowRequest unfollowRequest,
		UnfollowCCRRequest unfollowCCRRequest) {

		RestHighLevelClient restHighLevelClient =
			_elasticsearchClientResolver.getRestHighLevelClient(
				unfollowCCRRequest.getConnectionId(),
				unfollowCCRRequest.isPreferLocalCluster());

		CcrClient ccrClient = restHighLevelClient.ccr();

		try {
			return ccrClient.unfollow(unfollowRequest, RequestOptions.DEFAULT);
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

	private UnfollowRequest _createUnfollowRequest(
		UnfollowCCRRequest unfollowCCRRequest) {

		return new UnfollowRequest(unfollowCCRRequest.getIndexName());
	}

	private ElasticsearchClientResolver _elasticsearchClientResolver;

}