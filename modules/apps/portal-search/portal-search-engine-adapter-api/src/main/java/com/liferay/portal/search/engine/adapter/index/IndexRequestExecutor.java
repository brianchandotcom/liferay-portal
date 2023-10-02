/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

	public StatsIndexResponse executeIndexRequest(
		StatsIndexRequest statsIndexRequest);

	public UpdateIndexSettingsIndexResponse executeIndexRequest(
		UpdateIndexSettingsIndexRequest updateIndexSettingsIndexRequest);

}