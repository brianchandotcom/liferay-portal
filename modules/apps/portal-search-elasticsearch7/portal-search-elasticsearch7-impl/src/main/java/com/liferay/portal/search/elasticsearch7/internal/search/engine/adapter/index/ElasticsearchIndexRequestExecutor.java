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

package com.liferay.portal.search.elasticsearch7.internal.search.engine.adapter.index;

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
import com.liferay.portal.search.engine.adapter.index.UpdateIndexSettingsIndexRequest;
import com.liferay.portal.search.engine.adapter.index.UpdateIndexSettingsIndexResponse;

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
	public UpdateIndexSettingsIndexResponse executeIndexRequest(
		UpdateIndexSettingsIndexRequest updateIndexSettingsIndexRequest) {

		return _updateIndexSettingsIndexRequestExecutor.execute(
			updateIndexSettingsIndexRequest);
	}

	@Reference(unbind = "-")
	protected void setAnalyzeIndexRequestExecutor(
		AnalyzeIndexRequestExecutor analyzeIndexRequestExecutor) {

		_analyzeIndexRequestExecutor = analyzeIndexRequestExecutor;
	}

	@Reference(unbind = "-")
	protected void setCloseIndexRequestExecutor(
		CloseIndexRequestExecutor closeIndexRequestExecutor) {

		_closeIndexRequestExecutor = closeIndexRequestExecutor;
	}

	@Reference(unbind = "-")
	protected void setCreateIndexRequestExecutor(
		CreateIndexRequestExecutor createIndexRequestExecutor) {

		_createIndexRequestExecutor = createIndexRequestExecutor;
	}

	@Reference(unbind = "-")
	protected void setDeleteIndexRequestExecutor(
		DeleteIndexRequestExecutor deleteIndexRequestExecutor) {

		_deleteIndexRequestExecutor = deleteIndexRequestExecutor;
	}

	@Reference(unbind = "-")
	protected void setFlushIndexRequestExecutor(
		FlushIndexRequestExecutor flushIndexRequestExecutor) {

		_flushIndexRequestExecutor = flushIndexRequestExecutor;
	}

	@Reference(unbind = "-")
	protected void setGetFieldMappingIndexRequestExecutor(
		GetFieldMappingIndexRequestExecutor
			getFieldMappingIndexRequestExecutor) {

		_getFieldMappingIndexRequestExecutor =
			getFieldMappingIndexRequestExecutor;
	}

	@Reference(unbind = "-")
	protected void setGetIndexIndexRequestExecutor(
		GetIndexIndexRequestExecutor getIndexIndexRequestExecutor) {

		_getIndexIndexRequestExecutor = getIndexIndexRequestExecutor;
	}

	@Reference(unbind = "-")
	protected void setGetMappingIndexRequestExecutor(
		GetMappingIndexRequestExecutor getMappingIndexRequestExecutor) {

		_getMappingIndexRequestExecutor = getMappingIndexRequestExecutor;
	}

	@Reference(unbind = "-")
	protected void setIndicesExistsIndexRequestExecutor(
		IndicesExistsIndexRequestExecutor indicesExistsIndexRequestExecutor) {

		_indicesExistsIndexRequestExecutor = indicesExistsIndexRequestExecutor;
	}

	@Reference(unbind = "-")
	protected void setOpenIndexRequestExecutor(
		OpenIndexRequestExecutor openIndexRequestExecutor) {

		_openIndexRequestExecutor = openIndexRequestExecutor;
	}

	@Reference(unbind = "-")
	protected void setPutMappingIndexRequestExecutor(
		PutMappingIndexRequestExecutor putMappingIndexRequestExecutor) {

		_putMappingIndexRequestExecutor = putMappingIndexRequestExecutor;
	}

	@Reference(unbind = "-")
	protected void setRefreshIndexRequestExecutor(
		RefreshIndexRequestExecutor refreshIndexRequestExecutor) {

		_refreshIndexRequestExecutor = refreshIndexRequestExecutor;
	}

	@Reference(unbind = "-")
	protected void setUpdateIndexSettingsIndexRequestExecutor(
		UpdateIndexSettingsIndexRequestExecutor
			updateIndexSettingsIndexRequestExecutor) {

		_updateIndexSettingsIndexRequestExecutor =
			updateIndexSettingsIndexRequestExecutor;
	}

	private AnalyzeIndexRequestExecutor _analyzeIndexRequestExecutor;
	private CloseIndexRequestExecutor _closeIndexRequestExecutor;
	private CreateIndexRequestExecutor _createIndexRequestExecutor;
	private DeleteIndexRequestExecutor _deleteIndexRequestExecutor;
	private FlushIndexRequestExecutor _flushIndexRequestExecutor;
	private GetFieldMappingIndexRequestExecutor
		_getFieldMappingIndexRequestExecutor;
	private GetIndexIndexRequestExecutor _getIndexIndexRequestExecutor;
	private GetMappingIndexRequestExecutor _getMappingIndexRequestExecutor;
	private IndicesExistsIndexRequestExecutor
		_indicesExistsIndexRequestExecutor;
	private OpenIndexRequestExecutor _openIndexRequestExecutor;
	private PutMappingIndexRequestExecutor _putMappingIndexRequestExecutor;
	private RefreshIndexRequestExecutor _refreshIndexRequestExecutor;
	private UpdateIndexSettingsIndexRequestExecutor
		_updateIndexSettingsIndexRequestExecutor;

}