/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.opensearch2.internal.search.engine.adapter.search;

import com.liferay.portal.search.engine.adapter.search.OpenPointInTimeRequest;
import com.liferay.portal.search.engine.adapter.search.OpenPointInTimeResponse;
import com.liferay.portal.search.opensearch2.internal.connection.OpenSearchConnectionManager;

import java.io.IOException;

import java.util.Arrays;

import org.opensearch.client.opensearch.OpenSearchClient;
import org.opensearch.client.opensearch._types.Time;
import org.opensearch.client.opensearch._types.TimeUnit;
import org.opensearch.client.opensearch.core.pit.CreatePitRequest;
import org.opensearch.client.opensearch.core.pit.CreatePitResponse;

/**
 * @author Bryan Engler
 */
public class OpenPointInTimeRequestExecutor {

	public OpenPointInTimeRequestExecutor(
		OpenSearchConnectionManager openSearchConnectionManager) {

		_openSearchConnectionManager = openSearchConnectionManager;
	}

	public OpenPointInTimeResponse execute(
		OpenPointInTimeRequest openPointInTimeRequest) {

		CreatePitResponse createPitResponse = getCreatePitResponse(
			createPitRequest(openPointInTimeRequest), openPointInTimeRequest);

		return new OpenPointInTimeResponse(createPitResponse.pitId());
	}

	protected CreatePitRequest createPitRequest(
		OpenPointInTimeRequest openPointInTimeRequest) {

		CreatePitRequest.Builder builder = new CreatePitRequest.Builder();

		builder.keepAlive(
			Time.of(
				time -> time.time(
					openPointInTimeRequest.getKeepAliveMinutes() +
						TimeUnit.Minutes.jsonValue())));

		if (openPointInTimeRequest.getIndices() != null) {
			builder.targetIndexes(
				Arrays.asList(openPointInTimeRequest.getIndices()));
		}

		return builder.build();
	}

	protected CreatePitResponse getCreatePitResponse(
		CreatePitRequest createPitRequest,
		OpenPointInTimeRequest openPointInTimeRequest) {

		OpenSearchClient openSearchClient =
			_openSearchConnectionManager.getOpenSearchClient(
				openPointInTimeRequest.getConnectionId(),
				openPointInTimeRequest.isPreferLocalCluster());

		try {
			return openSearchClient.createPit(createPitRequest);
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}
	}

	private final OpenSearchConnectionManager _openSearchConnectionManager;

}