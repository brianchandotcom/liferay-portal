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

package com.liferay.portal.search.elasticsearch6.internal.search.engine.adapter.index;

import com.liferay.portal.search.engine.adapter.index.AnalyzeIndexRequest;
import com.liferay.portal.search.engine.adapter.index.AnalyzeIndexResponse;
import com.liferay.portal.search.engine.adapter.index.FlushIndexRequest;
import com.liferay.portal.search.engine.adapter.index.FlushIndexResponse;
import com.liferay.portal.search.engine.adapter.index.GetFieldMappingIndexRequest;
import com.liferay.portal.search.engine.adapter.index.GetFieldMappingIndexResponse;
import com.liferay.portal.search.engine.adapter.index.GetMappingIndexRequest;
import com.liferay.portal.search.engine.adapter.index.GetMappingIndexResponse;
import com.liferay.portal.search.engine.adapter.index.IndexRequestExecutor;
import com.liferay.portal.search.engine.adapter.index.PutMappingIndexRequest;
import com.liferay.portal.search.engine.adapter.index.PutMappingIndexResponse;
import com.liferay.portal.search.engine.adapter.index.RefreshIndexRequest;
import com.liferay.portal.search.engine.adapter.index.RefreshIndexResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Dylan Rebelak
 */
@Component(
	immediate = true, property = "search.engine.impl=Elasticsearch",
	service = IndexRequestExecutor.class
)
public class ElasticsearchIndexRequestExecutor implements IndexRequestExecutor {

	@Override
	public AnalyzeIndexResponse executeIndexRequest(
		AnalyzeIndexRequest analyzeIndexRequest) {

		return analyzeIndexRequestExecutor.execute(analyzeIndexRequest);
	}

	@Override
	public FlushIndexResponse executeIndexRequest(
		FlushIndexRequest flushIndexRequest) {

		return flushIndexRequestExecutor.execute(flushIndexRequest);
	}

	@Override
	public GetFieldMappingIndexResponse executeIndexRequest(
		GetFieldMappingIndexRequest getFieldMappingIndexRequest) {

		return getFieldMappingIndexRequestExecutor.execute(
			getFieldMappingIndexRequest);
	}

	@Override
	public GetMappingIndexResponse executeIndexRequest(
		GetMappingIndexRequest getMappingIndexRequest) {

		return getMappingIndexRequestExecutor.execute(getMappingIndexRequest);
	}

	@Override
	public PutMappingIndexResponse executeIndexRequest(
		PutMappingIndexRequest putMappingIndexRequest) {

		return putMappingIndexRequestExecutor.execute(putMappingIndexRequest);
	}

	@Override
	public RefreshIndexResponse executeIndexRequest(
		RefreshIndexRequest refreshIndexRequest) {

		return refreshIndexRequestExecutor.execute(refreshIndexRequest);
	}

	@Reference
	protected AnalyzeIndexRequestExecutor analyzeIndexRequestExecutor;

	@Reference
	protected FlushIndexRequestExecutor flushIndexRequestExecutor;

	@Reference
	protected GetFieldMappingIndexRequestExecutor
		getFieldMappingIndexRequestExecutor;

	@Reference
	protected GetMappingIndexRequestExecutor getMappingIndexRequestExecutor;

	@Reference
	protected PutMappingIndexRequestExecutor putMappingIndexRequestExecutor;

	@Reference
	protected RefreshIndexRequestExecutor refreshIndexRequestExecutor;

}