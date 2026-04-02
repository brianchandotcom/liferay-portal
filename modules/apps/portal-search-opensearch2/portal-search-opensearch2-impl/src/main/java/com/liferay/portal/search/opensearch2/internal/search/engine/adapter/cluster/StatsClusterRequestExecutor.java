/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.opensearch2.internal.search.engine.adapter.cluster;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.search.engine.adapter.cluster.StatsClusterRequest;
import com.liferay.portal.search.engine.adapter.cluster.StatsClusterResponse;
import com.liferay.portal.search.opensearch2.internal.connection.OpenSearchConnectionManager;

import jakarta.json.JsonNumber;
import jakarta.json.JsonObject;
import jakarta.json.JsonValue;

import java.io.IOException;

import java.util.Collections;

import org.opensearch.client.json.JsonpDeserializer;
import org.opensearch.client.opensearch.OpenSearchClient;
import org.opensearch.client.transport.OpenSearchTransport;
import org.opensearch.client.transport.endpoints.SimpleEndpoint;

/**
 * @author Dylan Rebelak
 */
public class StatsClusterRequestExecutor {

	public StatsClusterRequestExecutor(
		OpenSearchConnectionManager openSearchConnectionManager) {

		_openSearchConnectionManager = openSearchConnectionManager;
	}

	public StatsClusterResponse execute(
		StatsClusterRequest statsClusterRequest) {

		try {
			JsonObject jsonObject = _getClusterStatsJsonObject(
				statsClusterRequest);

			long availableInBytes = 0;
			long totalInBytes = 0;

			JsonObject nodesJsonObject = jsonObject.getJsonObject("nodes");

			if (nodesJsonObject != null) {
				JsonObject fsJsonObject = nodesJsonObject.getJsonObject("fs");

				if (fsJsonObject != null) {
					JsonNumber availableInBytesJsonNumber =
						fsJsonObject.getJsonNumber("available_in_bytes");

					if (availableInBytesJsonNumber != null) {
						availableInBytes =
							availableInBytesJsonNumber.longValue();
					}

					JsonNumber totalInBytesJsonNumber =
						fsJsonObject.getJsonNumber("total_in_bytes");

					if (totalInBytesJsonNumber != null) {
						totalInBytes = totalInBytesJsonNumber.longValue();
					}
				}
			}

			return new StatsClusterResponse(
				availableInBytes,
				ClusterHealthStatusTranslatorUtil.translate(
					jsonObject.getString("status")),
				jsonObject.toString(), totalInBytes - availableInBytes);
		}
		catch (Exception exception) {
			throw new SystemException(exception);
		}
	}

	private JsonObject _getClusterStatsJsonObject(
		StatsClusterRequest statsClusterRequest) {

		OpenSearchClient openSearchClient =
			_openSearchConnectionManager.getOpenSearchClient(
				statsClusterRequest.getConnectionId(),
				statsClusterRequest.isPreferLocalCluster());

		OpenSearchTransport openSearchTransport = openSearchClient._transport();

		try {
			JsonValue jsonValue = openSearchTransport.performRequest(
				statsClusterRequest,
				new SimpleEndpoint<>(
					request -> "GET",
					request -> {
						String[] nodeIds = request.getNodeIds();

						if ((nodeIds != null) && (nodeIds.length > 0)) {
							return "/_cluster/stats/nodes/" +
								StringUtil.merge(nodeIds, StringPool.COMMA);
						}

						return "/_cluster/stats";
					},
					request -> Collections.emptyMap(),
					request -> Collections.emptyMap(), false,
					JsonpDeserializer.jsonValueDeserializer()),
				null);

			return jsonValue.asJsonObject();
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}
	}

	private final OpenSearchConnectionManager _openSearchConnectionManager;

}