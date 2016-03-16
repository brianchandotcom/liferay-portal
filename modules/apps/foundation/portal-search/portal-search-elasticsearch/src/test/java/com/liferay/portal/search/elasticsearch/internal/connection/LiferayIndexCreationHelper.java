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

package com.liferay.portal.search.elasticsearch.internal.connection;

import com.liferay.portal.search.elasticsearch.internal.index.LiferayDocumentTypeFactory;

import org.elasticsearch.action.admin.indices.create.CreateIndexRequestBuilder;
import org.elasticsearch.client.IndicesAdminClient;
import org.elasticsearch.common.settings.Settings;

/**
 * @author André de Oliveira
 */
public class LiferayIndexCreationHelper implements IndexCreationHelper {

	public LiferayIndexCreationHelper(IndicesAdminClient indicesAdminClient) {
		_liferayDocumentTypeFactory = new LiferayDocumentTypeFactory(
			indicesAdminClient);
	}

	@Override
	public void contribute(
		CreateIndexRequestBuilder createIndexRequestBuilder) {

		_liferayDocumentTypeFactory.createRequiredDefaultTypeMappings(
			createIndexRequestBuilder);
	}

	@Override
	public void contributeIndexSettings(Settings.Builder builder) {
		_liferayDocumentTypeFactory.createRequiredDefaultAnalyzers(builder);
	}

	@Override
	public void whenIndexCreated(String indexName) {
		_liferayDocumentTypeFactory.createOptionalDefaultTypeMappings(
			indexName);
	}

	private final LiferayDocumentTypeFactory _liferayDocumentTypeFactory;

}