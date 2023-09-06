/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.elasticsearch7.internal.search.engine.adapter.index;

import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.search.elasticsearch7.internal.connection.ElasticsearchClientResolver;
import com.liferay.portal.search.engine.adapter.index.StatsIndexRequest;
import com.liferay.portal.search.engine.adapter.index.StatsIndexResponse;

import java.util.HashMap;
import java.util.Map;

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

		String indexes = "_all";

		if (ArrayUtil.isNotEmpty(statsIndexRequest.getIndexNames())) {
			indexes = StringUtil.merge(statsIndexRequest.getIndexNames());
		}

		String endpoint = "/" + indexes + "/_stats";

		Request request = new Request("GET", endpoint);

		try {
			Response response = restClient.performRequest(request);

			String responseBody = EntityUtils.toString(response.getEntity());

			JSONObject responseJSONObject = _jsonFactory.createJSONObject(
				responseBody);

			JSONObject indicesJSONObject = responseJSONObject.getJSONObject(
				"indices");

			Map<String, Long> indexSizes = new HashMap<>();

			for (String indexName : statsIndexRequest.getIndexNames()) {
				JSONObject indexJSONObject = indicesJSONObject.getJSONObject(
					indexName);

				JSONObject totalJSONObject = indexJSONObject.getJSONObject(
					"total");

				JSONObject storeJSONObject = totalJSONObject.getJSONObject(
					"store");

				long size = storeJSONObject.getLong("size_in_bytes");

				indexSizes.put(indexName, size);
			}

			return new StatsIndexResponse(indexSizes);
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