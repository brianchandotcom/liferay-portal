/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.opensearch2.internal.search.engine.adapter.index;

import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.search.engine.adapter.index.GetFieldMappingIndexRequest;
import com.liferay.portal.search.engine.adapter.index.GetFieldMappingIndexResponse;
import com.liferay.portal.search.opensearch2.internal.connection.OpenSearchConnectionManager;
import com.liferay.portal.search.opensearch2.internal.util.JsonpUtil;

import java.io.IOException;

import java.util.HashMap;
import java.util.Map;

import org.opensearch.client.opensearch.OpenSearchClient;
import org.opensearch.client.opensearch._types.mapping.FieldMapping;
import org.opensearch.client.opensearch.indices.GetFieldMappingRequest;
import org.opensearch.client.opensearch.indices.GetFieldMappingResponse;
import org.opensearch.client.opensearch.indices.OpenSearchIndicesClient;
import org.opensearch.client.opensearch.indices.get_field_mapping.TypeFieldMappings;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Dylan Rebelak
 * @author Petteri Karttunen
 */
@Component(service = GetFieldMappingIndexRequestExecutor.class)
public class GetFieldMappingIndexRequestExecutorImpl
	implements GetFieldMappingIndexRequestExecutor {

	@Override
	public GetFieldMappingIndexResponse execute(
		GetFieldMappingIndexRequest getFieldMappingIndexRequest) {

		GetFieldMappingResponse getFieldMappingResponse =
			_getGetFieldMappingsResponse(
				getFieldMappingIndexRequest,
				createGetFieldMappingRequest(getFieldMappingIndexRequest));

		Map<String, String> fieldMappingsMap = new HashMap<>();

		Map<String, TypeFieldMappings> typeFieldMappingsMap =
			getFieldMappingResponse.result();

		for (Map.Entry<String, TypeFieldMappings> entry1 :
				typeFieldMappingsMap.entrySet()) {

			TypeFieldMappings typeFieldMappings = entry1.getValue();

			Map<String, FieldMapping> fieldMappings =
				typeFieldMappings.mappings();

			JSONObject jsonObject = _jsonFactory.createJSONObject();

			for (Map.Entry<String, FieldMapping> entry2 :
					fieldMappings.entrySet()) {

				jsonObject.put(
					entry2.getKey(), JsonpUtil.toString(entry2.getValue()));
			}

			fieldMappingsMap.put(entry1.getKey(), jsonObject.toString());
		}

		return new GetFieldMappingIndexResponse(fieldMappingsMap);
	}

	protected GetFieldMappingRequest createGetFieldMappingRequest(
		GetFieldMappingIndexRequest getFieldMappingIndexRequest) {

		return GetFieldMappingRequest.of(
			getFieldMappingRequest -> getFieldMappingRequest.fields(
				ListUtil.fromArray(getFieldMappingIndexRequest.getFields())
			).index(
				ListUtil.fromArray(getFieldMappingIndexRequest.getIndexNames())
			));
	}

	private GetFieldMappingResponse _getGetFieldMappingsResponse(
		GetFieldMappingIndexRequest getFieldMappingIndexRequest,
		GetFieldMappingRequest getFieldMappingRequest) {

		OpenSearchClient openSearchClient =
			_openSearchConnectionManager.getOpenSearchClient(
				getFieldMappingIndexRequest.getConnectionId(),
				getFieldMappingIndexRequest.isPreferLocalCluster());

		OpenSearchIndicesClient openSearchIndicesClient =
			openSearchClient.indices();

		try {
			return openSearchIndicesClient.getFieldMapping(
				getFieldMappingRequest);
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}
	}

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private OpenSearchConnectionManager _openSearchConnectionManager;

}