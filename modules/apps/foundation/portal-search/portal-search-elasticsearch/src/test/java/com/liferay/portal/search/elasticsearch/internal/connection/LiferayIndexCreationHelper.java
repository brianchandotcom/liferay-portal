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
import org.elasticsearch.common.settings.Settings;

/**
 * @author André de Oliveira
 */
public class LiferayIndexCreationHelper implements IndexCreationHelper {

	public LiferayIndexCreationHelper(
		IndicesAdminClientSupplier indicesAdminClientSupplier) {

		_indicesAdminClientSupplier = indicesAdminClientSupplier;
	}

	@Override
	public void contribute(
		CreateIndexRequestBuilder createIndexRequestBuilder) {

		LiferayDocumentTypeFactory liferayDocumentTypeFactory =
			getLiferayDocumentTypeFactory();

		liferayDocumentTypeFactory.createRequiredDefaultTypeMappings(
			createIndexRequestBuilder);
	}

	@Override
	public void contributeIndexSettings(Settings.Builder builder) {
		LiferayDocumentTypeFactory liferayDocumentTypeFactory =
			getLiferayDocumentTypeFactory();

		liferayDocumentTypeFactory.createRequiredDefaultAnalyzers(builder);
	}

	@Override
	public void whenIndexCreated(String indexName) {
		LiferayDocumentTypeFactory liferayDocumentTypeFactory =
			getLiferayDocumentTypeFactory();

		liferayDocumentTypeFactory.createOptionalDefaultTypeMappings(indexName);
	}

	protected LiferayDocumentTypeFactory getLiferayDocumentTypeFactory() {
		return new LiferayDocumentTypeFactory(
			_indicesAdminClientSupplier.getIndicesAdminClient());
	}

	private final IndicesAdminClientSupplier _indicesAdminClientSupplier;

}