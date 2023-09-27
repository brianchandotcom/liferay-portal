/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.engine.adapter.index;

import com.liferay.portal.search.engine.adapter.ccr.CrossClusterRequest;

/**
 * @author Michael C. Han
 */
public class CloseIndexRequest
	extends CrossClusterRequest implements IndexRequest<CloseIndexResponse> {

	public CloseIndexRequest(String... indexNames) {
		_indexNames = indexNames;
	}

	@Override
	public CloseIndexResponse accept(
		IndexRequestExecutor indexRequestExecutor) {

		return indexRequestExecutor.executeIndexRequest(this);
	}

	@Override
	public String[] getIndexNames() {
		return _indexNames;
	}

	public IndicesOptions getIndicesOptions() {
		return _indicesOptions;
	}

	public long getTimeout() {
		return _timeout;
	}

	public void setIndicesOptions(IndicesOptions indicesOptions) {
		_indicesOptions = indicesOptions;
	}

	public void setTimeout(long timeout) {
		_timeout = timeout;
	}

	private final String[] _indexNames;
	private IndicesOptions _indicesOptions;
	private long _timeout;

}