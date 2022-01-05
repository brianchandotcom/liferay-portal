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
import com.liferay.portal.search.engine.adapter.ccr.FollowInfoCCRRequest;
import com.liferay.portal.search.engine.adapter.ccr.FollowInfoCCRResponse;
import com.liferay.portal.search.engine.adapter.ccr.FollowInfoStatus;

import java.io.IOException;

import java.util.List;

import org.elasticsearch.client.CcrClient;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.ccr.FollowInfoRequest;
import org.elasticsearch.client.ccr.FollowInfoResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Bryan Engler
 */
@Component(service = FollowInfoCCRRequestExecutor.class)
public class FollowInfoCCRRequestExecutorImpl
	implements FollowInfoCCRRequestExecutor {

	@Override
	public FollowInfoCCRResponse execute(
		FollowInfoCCRRequest followInfoCCRRequest) {

		FollowInfoRequest followInfoRequest = _createFollowInfoRequest(
			followInfoCCRRequest);

		FollowInfoResponse followInfoResponse = _getFollowInfoResponse(
			followInfoRequest, followInfoCCRRequest);

		List<FollowInfoResponse.FollowerInfo> followerInfos =
			followInfoResponse.getInfos();

		FollowInfoResponse.FollowerInfo followerInfo = followerInfos.get(0);

		FollowInfoResponse.Status status = followerInfo.getStatus();

		if (status == FollowInfoResponse.Status.ACTIVE) {
			return new FollowInfoCCRResponse(FollowInfoStatus.ACTIVE);
		}

		return new FollowInfoCCRResponse(FollowInfoStatus.PAUSED);
	}

	@Reference(unbind = "-")
	protected void setElasticsearchClientResolver(
		ElasticsearchClientResolver elasticsearchClientResolver) {

		_elasticsearchClientResolver = elasticsearchClientResolver;
	}

	private FollowInfoRequest _createFollowInfoRequest(
		FollowInfoCCRRequest followInfoCCRRequest) {

		return new FollowInfoRequest(followInfoCCRRequest.getIndexName());
	}

	private FollowInfoResponse _getFollowInfoResponse(
		FollowInfoRequest followInfoRequest,
		FollowInfoCCRRequest followInfoCCRRequest) {

		RestHighLevelClient restHighLevelClient =
			_elasticsearchClientResolver.getRestHighLevelClient(
				followInfoCCRRequest.getConnectionId(),
				followInfoCCRRequest.isPreferLocalCluster());

		CcrClient ccrClient = restHighLevelClient.ccr();

		try {
			return ccrClient.getFollowInfo(
				followInfoRequest, RequestOptions.DEFAULT);
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}
	}

	private ElasticsearchClientResolver _elasticsearchClientResolver;

}