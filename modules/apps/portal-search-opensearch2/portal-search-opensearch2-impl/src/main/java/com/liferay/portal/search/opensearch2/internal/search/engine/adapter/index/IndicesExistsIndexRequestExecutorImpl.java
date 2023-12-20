/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.opensearch2.internal.search.engine.adapter.index;

import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.search.engine.adapter.index.IndicesExistsIndexRequest;
import com.liferay.portal.search.engine.adapter.index.IndicesExistsIndexResponse;
import com.liferay.portal.search.opensearch2.internal.connection.OpenSearchConnectionManager;

import java.io.IOException;

import org.opensearch.client.opensearch.OpenSearchClient;
import org.opensearch.client.opensearch.indices.ExistsRequest;
import org.opensearch.client.opensearch.indices.OpenSearchIndicesClient;
import org.opensearch.client.transport.endpoints.BooleanResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Michael C. Han
 * @author Petteri Karttunen
 */
@Component(service = IndicesExistsIndexRequestExecutor.class)
public class IndicesExistsIndexRequestExecutorImpl
	implements IndicesExistsIndexRequestExecutor {

	@Override
	public IndicesExistsIndexResponse execute(
		IndicesExistsIndexRequest indicesExistsIndexRequest) {

		BooleanResponse booleanResponse = _indicesExists(
			createExistsRequest(indicesExistsIndexRequest),
			indicesExistsIndexRequest);

		return new IndicesExistsIndexResponse(booleanResponse.value());
	}

	protected ExistsRequest createExistsRequest(
		IndicesExistsIndexRequest indicesExistsIndexRequest) {

		return ExistsRequest.of(
			existsRequest -> existsRequest.index(
				ListUtil.fromArray(indicesExistsIndexRequest.getIndexNames())));
	}

	private BooleanResponse _indicesExists(
		ExistsRequest existsRequest,
		IndicesExistsIndexRequest indicesExistsIndexRequest) {

		OpenSearchClient openSearchClient =
			_openSearchConnectionManager.getOpenSearchClient(
				indicesExistsIndexRequest.getConnectionId(),
				indicesExistsIndexRequest.isPreferLocalCluster());

		OpenSearchIndicesClient openSearchIndicesClient =
			openSearchClient.indices();

		try {
			return openSearchIndicesClient.exists(existsRequest);
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}
	}

	@Reference
	private OpenSearchConnectionManager _openSearchConnectionManager;

}