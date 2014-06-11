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

package com.liferay.portal.search.elasticsearch.index;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.search.elasticsearch.io.StringOutputStream;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Future;

import org.elasticsearch.action.admin.indices.create.CreateIndexRequestBuilder;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsRequestBuilder;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsResponse;
import org.elasticsearch.client.AdminClient;
import org.elasticsearch.client.IndicesAdminClient;
import org.elasticsearch.common.io.stream.OutputStreamStreamOutput;

/**
 * @author Michael C. Han
 */
public class CompanyIndexFactory implements IndexFactory {

	@Override
	public void createIndices(AdminClient adminClient, long companyId)
		throws Exception {

		IndicesAdminClient indicesAdminClient = adminClient.indices();

		IndicesExistsRequestBuilder indicesExistsRequestBuilder =
			indicesAdminClient.prepareExists(String.valueOf(companyId));

		Future<IndicesExistsResponse> indicesExistsRequestFuture =
			indicesExistsRequestBuilder.execute();

		IndicesExistsResponse indicesExistsResponse =
			indicesExistsRequestFuture.get();

		if (indicesExistsResponse.isExists()) {
			return;
		}

		CreateIndexRequestBuilder createIndexRequestBuilder =
			indicesAdminClient.prepareCreate(String.valueOf(companyId));

		for (Map.Entry<String, String> entry : _typeMappings.entrySet()) {
			Class<?> clazz = getClass();

			String typeMapping = StringUtil.read(
				clazz.getClassLoader(), entry.getValue());

			createIndexRequestBuilder.addMapping(entry.getKey(), typeMapping);
		}

		Future<CreateIndexResponse> createIndexFuture =
			createIndexRequestBuilder.execute();

		CreateIndexResponse createIndexResponse = createIndexFuture.get();

		if (_log.isInfoEnabled()) {
			StringOutputStream stringOutputStream = new StringOutputStream();

			createIndexResponse.writeTo(
				new OutputStreamStreamOutput(stringOutputStream));

			_log.info(stringOutputStream);
		}
	}

	public void setTypeMappings(Map<String, String> typeMappings) {
		_typeMappings = typeMappings;
	}

	private static Log _log = LogFactoryUtil.getLog(CompanyIndexFactory.class);

	private Map<String, String> _typeMappings = new HashMap<String, String>();

}