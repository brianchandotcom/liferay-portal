/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.engine.adapter.index;

import com.liferay.portal.search.engine.adapter.ccr.CrossClusterRequest;

/**
 * @author Michael C. Han
 */
public class UpdateIndexSettingsIndexRequest
	extends CrossClusterRequest
	implements IndexRequest<UpdateIndexSettingsIndexResponse> {

	public UpdateIndexSettingsIndexRequest(String indexName) {
		_indexName = indexName;
	}

	@Override
	public UpdateIndexSettingsIndexResponse accept(
		IndexRequestExecutor indexRequestExecutor) {

		return indexRequestExecutor.executeIndexRequest(this);
	}

	@Override
	public String[] getIndexNames() {
		return new String[] {_indexName};
	}

	public IndicesOptions getIndicesOptions() {
		return _indicesOptions;
	}

	public String getSettings() {
		return _settings;
	}

	public void setIndicesOptions(IndicesOptions indicesOptions) {
		_indicesOptions = indicesOptions;
	}

	public void setSettings(String settings) {
		_settings = settings;
	}

	private final String _indexName;
	private IndicesOptions _indicesOptions;
	private String _settings;

}