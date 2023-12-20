/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.opensearch2.internal.search.engine.adapter.search;

import com.liferay.portal.search.engine.adapter.search.ClosePointInTimeRequest;
import com.liferay.portal.search.engine.adapter.search.ClosePointInTimeResponse;
import com.liferay.portal.search.opensearch2.internal.connection.OpenSearchConnectionManager;

import java.io.IOException;

import java.util.Arrays;
import java.util.List;

import org.opensearch.client.opensearch.OpenSearchClient;
import org.opensearch.client.opensearch.core.pit.DeletePitRecord;
import org.opensearch.client.opensearch.core.pit.DeletePitRequest;
import org.opensearch.client.opensearch.core.pit.DeletePitResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Bryan Engler
 * @author Petteri Karttunen
 */
@Component(service = ClosePointInTimeRequestExecutor.class)
public class ClosePointInTimeRequestExecutorImpl
	implements ClosePointInTimeRequestExecutor {

	@Override
	public ClosePointInTimeResponse execute(
		ClosePointInTimeRequest closePointInTimeRequest) {

		DeletePitResponse deletePitResponse = getDeletePitResponse(
			closePointInTimeRequest,
			createDeletePitRequest(closePointInTimeRequest));

		List<DeletePitRecord> deletePitRecords = deletePitResponse.pits();

		return new ClosePointInTimeResponse(deletePitRecords.size());
	}

	protected DeletePitRequest createDeletePitRequest(
		ClosePointInTimeRequest closePointInTimeSearchRequest) {

		return DeletePitRequest.of(
			deletePitRequest -> deletePitRequest.pitId(
				Arrays.asList(
					closePointInTimeSearchRequest.getPointInTimeId())));
	}

	protected DeletePitResponse getDeletePitResponse(
		ClosePointInTimeRequest closePointInTimeRequest,
		DeletePitRequest deletePitRequest) {

		OpenSearchClient openSearchClient =
			_openSearchConnectionManager.getOpenSearchClient(
				closePointInTimeRequest.getConnectionId(),
				closePointInTimeRequest.isPreferLocalCluster());

		try {
			return openSearchClient.deletePit(deletePitRequest);
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}
	}

	@Reference
	private OpenSearchConnectionManager _openSearchConnectionManager;

}