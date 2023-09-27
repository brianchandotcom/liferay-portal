/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.engine.adapter.index;

import com.liferay.portal.search.engine.adapter.ccr.CrossClusterRequest;

/**
 * @author Michael C. Han
 * @author Joshua Cords
 * @author Tibor Lipusz
 */
public class CreateIndexRequest
	extends CrossClusterRequest implements IndexRequest<CreateIndexResponse> {

	public CreateIndexRequest(String indexName) {
		_indexName = indexName;
	}

	@Override
	public CreateIndexResponse accept(
		IndexRequestExecutor indexRequestExecutor) {

		return indexRequestExecutor.executeIndexRequest(this);
	}

	public String getIndexName() {
		return _indexName;
	}

	@Override
	public String[] getIndexNames() {
		return new String[] {_indexName};
	}

	public String getMappings() {
		return _mappings;
	}

	public String getSettings() {
		return _settings;
	}

	public String getSource() {
		return _source;
	}

	public void setMappings(String mappings) {
		_mappings = mappings;
	}

	public void setSettings(String settings) {
		_settings = settings;
	}

	public void setSource(String source) {
		_source = source;
	}

	private final String _indexName;
	private String _mappings;
	private String _settings;
	private String _source;

}