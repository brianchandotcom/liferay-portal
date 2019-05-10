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

package com.liferay.portal.search.engine.adapter.index;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Dylan Rebelak
 */
@ProviderType
public interface IndexRequestExecutor {

	public AnalyzeIndexResponse executeIndexRequest(
		AnalyzeIndexRequest analyzeIndexRequest);

	public CloseIndexResponse executeIndexRequest(
		CloseIndexRequest closeIndexRequest);

	public CreateIndexResponse executeIndexRequest(
		CreateIndexRequest createIndexRequest);

	public DeleteIndexResponse executeIndexRequest(
		DeleteIndexRequest deleteIndexRequest);

	public FlushIndexResponse executeIndexRequest(
		FlushIndexRequest flushIndexRequest);

	public GetFieldMappingIndexResponse executeIndexRequest(
		GetFieldMappingIndexRequest getFieldMappingIndexRequest);

	public GetIndexIndexResponse executeIndexRequest(
		GetIndexIndexRequest getIndexIndexRequest);

	public GetMappingIndexResponse executeIndexRequest(
		GetMappingIndexRequest getMappingIndexRequest);

	public IndicesExistsIndexResponse executeIndexRequest(
		IndicesExistsIndexRequest indicesExistsIndexRequest);

	public OpenIndexResponse executeIndexRequest(
		OpenIndexRequest openIndexRequest);

	public PutMappingIndexResponse executeIndexRequest(
		PutMappingIndexRequest putMappingIndexRequest);

	public RefreshIndexResponse executeIndexRequest(
		RefreshIndexRequest refreshIndexRequest);

	public UpdateIndexSettingsIndexResponse executeIndexRequest(
		UpdateIndexSettingsIndexRequest updateIndexSettingsIndexRequest);

}