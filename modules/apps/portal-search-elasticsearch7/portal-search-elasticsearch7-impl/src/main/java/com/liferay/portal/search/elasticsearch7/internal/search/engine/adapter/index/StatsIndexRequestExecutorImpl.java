/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.elasticsearch7.internal.search.engine.adapter.index;

import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.search.elasticsearch7.internal.connection.ElasticsearchClientResolver;
import com.liferay.portal.search.engine.adapter.index.StatsIndexRequest;
import com.liferay.portal.search.engine.adapter.index.StatsIndexResponse;

import org.apache.http.util.EntityUtils;

import org.elasticsearch.client.Request;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Felipe Lorenz
 */
@Component(service = StatsIndexRequestExecutor.class)
public class StatsIndexRequestExecutorImpl
	implements StatsIndexRequestExecutor {

	@Override
	public StatsIndexResponse execute(StatsIndexRequest statsIndexRequest) {
		RestHighLevelClient restHighLevelClient =
			_elasticsearchClientResolver.getRestHighLevelClient(
				statsIndexRequest.getConnectionId(),
				statsIndexRequest.isPreferLocalCluster());

		RestClient restClient = restHighLevelClient.getLowLevelClient();

		String endpoint = "/liferay-20096/_stats";

		Request request = new Request("GET", endpoint);

		try {
			Response response = restClient.performRequest(request);

			String responseBody = EntityUtils.toString(response.getEntity());

			JSONObject responseJSONObject = _jsonFactory.createJSONObject(
				responseBody);

			JSONObject indicesJSONObject = responseJSONObject.getJSONObject(
				"indices");

			JSONObject indexJSONObject = indicesJSONObject.getJSONObject(
				"liferay-20096");

			JSONObject totalJSONObject = indexJSONObject.getJSONObject("total");

			JSONObject storeJSONObject = totalJSONObject.getJSONObject("store");

			Long size = storeJSONObject.getLong("size_in_bytes");

			return new StatsIndexResponse(size);
		}
		catch (Exception exception) {
			throw new SystemException(exception);
		}
	}

	@Reference
	private ElasticsearchClientResolver _elasticsearchClientResolver;

	@Reference
	private JSONFactory _jsonFactory;

}