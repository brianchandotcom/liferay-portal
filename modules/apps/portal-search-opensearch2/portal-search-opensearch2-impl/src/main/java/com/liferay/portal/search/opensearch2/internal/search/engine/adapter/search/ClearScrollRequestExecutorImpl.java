/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.opensearch2.internal.search.engine.adapter.search;

import com.liferay.portal.search.engine.adapter.search.ClearScrollRequest;
import com.liferay.portal.search.engine.adapter.search.ClearScrollResponse;
import com.liferay.portal.search.opensearch2.internal.connection.OpenSearchConnectionManager;

import org.opensearch.client.opensearch.OpenSearchClient;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Gustavo Lima
 * @author Petteri Karttunen
 */
@Component(service = ClearScrollRequestExecutor.class)
public class ClearScrollRequestExecutorImpl
	implements ClearScrollRequestExecutor {

	@Override
	public ClearScrollResponse execute(ClearScrollRequest clearScrollRequest) {
		org.opensearch.client.opensearch.core.ClearScrollResponse
			clearScrollResponse = getClearScrollResponse(
				clearScrollRequest,
				createClearScrollRequest(clearScrollRequest));

		return new ClearScrollResponse(clearScrollResponse.numFreed());
	}

	protected org.opensearch.client.opensearch.core.ClearScrollRequest
		createClearScrollRequest(ClearScrollRequest clearScrollRequest) {

		return org.opensearch.client.opensearch.core.ClearScrollRequest.of(
			openSearchClearScrollRequest ->
				openSearchClearScrollRequest.scrollId(
					clearScrollRequest.getScrollId()));
	}

	protected org.opensearch.client.opensearch.core.ClearScrollResponse
		getClearScrollResponse(
			ClearScrollRequest clearScrollRequest,
			org.opensearch.client.opensearch.core.ClearScrollRequest
				openSearchClearScrollRequest) {

		OpenSearchClient openSearchClient =
			_openSearchConnectionManager.getOpenSearchClient(
				clearScrollRequest.getConnectionId(),
				clearScrollRequest.isPreferLocalCluster());

		try {
			return openSearchClient.clearScroll(openSearchClearScrollRequest);
		}
		catch (Exception exception) {
			throw new RuntimeException(exception.getMessage(), exception);
		}
	}

	@Reference
	private OpenSearchConnectionManager _openSearchConnectionManager;

}