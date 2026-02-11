/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.elasticsearch8.internal.search.engine.adapter.index;

import com.liferay.portal.search.elasticsearch8.internal.connection.ElasticsearchClientResolver;
import com.liferay.portal.search.engine.adapter.index.AnalyzeIndexRequest;
import com.liferay.portal.search.engine.adapter.index.AnalyzeIndexResponse;
import com.liferay.portal.search.engine.adapter.index.CloseIndexRequest;
import com.liferay.portal.search.engine.adapter.index.CloseIndexResponse;
import com.liferay.portal.search.engine.adapter.index.CreateIndexRequest;
import com.liferay.portal.search.engine.adapter.index.CreateIndexResponse;
import com.liferay.portal.search.engine.adapter.index.DeleteIndexRequest;
import com.liferay.portal.search.engine.adapter.index.DeleteIndexResponse;
import com.liferay.portal.search.engine.adapter.index.FlushIndexRequest;
import com.liferay.portal.search.engine.adapter.index.FlushIndexResponse;
import com.liferay.portal.search.engine.adapter.index.GetFieldMappingIndexRequest;
import com.liferay.portal.search.engine.adapter.index.GetFieldMappingIndexResponse;
import com.liferay.portal.search.engine.adapter.index.GetIndexIndexRequest;
import com.liferay.portal.search.engine.adapter.index.GetIndexIndexResponse;
import com.liferay.portal.search.engine.adapter.index.GetMappingIndexRequest;
import com.liferay.portal.search.engine.adapter.index.GetMappingIndexResponse;
import com.liferay.portal.search.engine.adapter.index.IndexRequestExecutor;
import com.liferay.portal.search.engine.adapter.index.IndicesExistsIndexRequest;
import com.liferay.portal.search.engine.adapter.index.IndicesExistsIndexResponse;
import com.liferay.portal.search.engine.adapter.index.OpenIndexRequest;
import com.liferay.portal.search.engine.adapter.index.OpenIndexResponse;
import com.liferay.portal.search.engine.adapter.index.PutMappingIndexRequest;
import com.liferay.portal.search.engine.adapter.index.PutMappingIndexResponse;
import com.liferay.portal.search.engine.adapter.index.RefreshIndexRequest;
import com.liferay.portal.search.engine.adapter.index.RefreshIndexResponse;
import com.liferay.portal.search.engine.adapter.index.StatsIndexRequest;
import com.liferay.portal.search.engine.adapter.index.StatsIndexResponse;
import com.liferay.portal.search.engine.adapter.index.UpdateIndexSettingsIndexRequest;
import com.liferay.portal.search.engine.adapter.index.UpdateIndexSettingsIndexResponse;

/**
 * @author Dylan Rebelak
 */
public class ElasticsearchIndexRequestExecutor implements IndexRequestExecutor {

	public ElasticsearchIndexRequestExecutor(
		ElasticsearchClientResolver elasticsearchClientResolver) {

		_analyzeIndexRequestExecutor = new AnalyzeIndexRequestExecutor(
			elasticsearchClientResolver);
		_closeIndexRequestExecutor = new CloseIndexRequestExecutor(
			elasticsearchClientResolver);
		_createIndexRequestExecutor = new CreateIndexRequestExecutor(
			elasticsearchClientResolver);
		_deleteIndexRequestExecutor = new DeleteIndexRequestExecutor(
			elasticsearchClientResolver);
		_flushIndexRequestExecutor = new FlushIndexRequestExecutor(
			elasticsearchClientResolver);
		_getFieldMappingIndexRequestExecutor =
			new GetFieldMappingIndexRequestExecutor(
				elasticsearchClientResolver);
		_getIndexIndexRequestExecutor = new GetIndexIndexRequestExecutor(
			elasticsearchClientResolver);
		_getMappingIndexRequestExecutor = new GetMappingIndexRequestExecutor(
			elasticsearchClientResolver);
		_indicesExistsIndexRequestExecutor =
			new IndicesExistsIndexRequestExecutor(elasticsearchClientResolver);
		_openIndexRequestExecutor = new OpenIndexRequestExecutor(
			elasticsearchClientResolver);
		_putMappingIndexRequestExecutor = new PutMappingIndexRequestExecutor(
			elasticsearchClientResolver);
		_refreshIndexRequestExecutor = new RefreshIndexRequestExecutor(
			elasticsearchClientResolver);
		_statsIndexRequestExecutor = new StatsIndexRequestExecutor(
			elasticsearchClientResolver);
		_updateIndexSettingsIndexRequestExecutor =
			new UpdateIndexSettingsIndexRequestExecutor(
				elasticsearchClientResolver);
	}

	@Override
	public AnalyzeIndexResponse executeIndexRequest(
		AnalyzeIndexRequest analyzeIndexRequest) {

		return _analyzeIndexRequestExecutor.execute(analyzeIndexRequest);
	}

	@Override
	public CloseIndexResponse executeIndexRequest(
		CloseIndexRequest closeIndexRequest) {

		return _closeIndexRequestExecutor.execute(closeIndexRequest);
	}

	@Override
	public CreateIndexResponse executeIndexRequest(
		CreateIndexRequest createIndexRequest) {

		return _createIndexRequestExecutor.execute(createIndexRequest);
	}

	@Override
	public DeleteIndexResponse executeIndexRequest(
		DeleteIndexRequest deleteIndexRequest) {

		return _deleteIndexRequestExecutor.execute(deleteIndexRequest);
	}

	@Override
	public FlushIndexResponse executeIndexRequest(
		FlushIndexRequest flushIndexRequest) {

		return _flushIndexRequestExecutor.execute(flushIndexRequest);
	}

	@Override
	public GetFieldMappingIndexResponse executeIndexRequest(
		GetFieldMappingIndexRequest getFieldMappingIndexRequest) {

		return _getFieldMappingIndexRequestExecutor.execute(
			getFieldMappingIndexRequest);
	}

	@Override
	public GetIndexIndexResponse executeIndexRequest(
		GetIndexIndexRequest getIndexIndexRequest) {

		return _getIndexIndexRequestExecutor.execute(getIndexIndexRequest);
	}

	@Override
	public GetMappingIndexResponse executeIndexRequest(
		GetMappingIndexRequest getMappingIndexRequest) {

		return _getMappingIndexRequestExecutor.execute(getMappingIndexRequest);
	}

	@Override
	public IndicesExistsIndexResponse executeIndexRequest(
		IndicesExistsIndexRequest indicesExistsIndexRequest) {

		return _indicesExistsIndexRequestExecutor.execute(
			indicesExistsIndexRequest);
	}

	@Override
	public OpenIndexResponse executeIndexRequest(
		OpenIndexRequest openIndexRequest) {

		return _openIndexRequestExecutor.execute(openIndexRequest);
	}

	@Override
	public PutMappingIndexResponse executeIndexRequest(
		PutMappingIndexRequest putMappingIndexRequest) {

		return _putMappingIndexRequestExecutor.execute(putMappingIndexRequest);
	}

	@Override
	public RefreshIndexResponse executeIndexRequest(
		RefreshIndexRequest refreshIndexRequest) {

		return _refreshIndexRequestExecutor.execute(refreshIndexRequest);
	}

	@Override
	public StatsIndexResponse executeIndexRequest(
		StatsIndexRequest statsIndexRequest) {

		return _statsIndexRequestExecutor.execute(statsIndexRequest);
	}

	@Override
	public UpdateIndexSettingsIndexResponse executeIndexRequest(
		UpdateIndexSettingsIndexRequest updateIndexSettingsIndexRequest) {

		return _updateIndexSettingsIndexRequestExecutor.execute(
			updateIndexSettingsIndexRequest);
	}

	private final AnalyzeIndexRequestExecutor _analyzeIndexRequestExecutor;
	private final CloseIndexRequestExecutor _closeIndexRequestExecutor;
	private final CreateIndexRequestExecutor _createIndexRequestExecutor;
	private final DeleteIndexRequestExecutor _deleteIndexRequestExecutor;
	private final FlushIndexRequestExecutor _flushIndexRequestExecutor;
	private final GetFieldMappingIndexRequestExecutor
		_getFieldMappingIndexRequestExecutor;
	private final GetIndexIndexRequestExecutor _getIndexIndexRequestExecutor;
	private final GetMappingIndexRequestExecutor
		_getMappingIndexRequestExecutor;
	private final IndicesExistsIndexRequestExecutor
		_indicesExistsIndexRequestExecutor;
	private final OpenIndexRequestExecutor _openIndexRequestExecutor;
	private final PutMappingIndexRequestExecutor
		_putMappingIndexRequestExecutor;
	private final RefreshIndexRequestExecutor _refreshIndexRequestExecutor;
	private final StatsIndexRequestExecutor _statsIndexRequestExecutor;
	private final UpdateIndexSettingsIndexRequestExecutor
		_updateIndexSettingsIndexRequestExecutor;

}