/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.engine.adapter.index;

import com.liferay.portal.search.engine.adapter.ccr.CrossClusterRequest;

/**
 * @author Michael C. Han
 */
public class IndicesExistsIndexRequest
	extends CrossClusterRequest
	implements IndexRequest<IndicesExistsIndexResponse> {

	public IndicesExistsIndexRequest(String... indexNames) {
		_indexNames = indexNames;

		setPreferLocalCluster(true);
	}

	@Override
	public IndicesExistsIndexResponse accept(
		IndexRequestExecutor indexRequestExecutor) {

		return indexRequestExecutor.executeIndexRequest(this);
	}

	@Override
	public String[] getIndexNames() {
		return _indexNames;
	}

	private final String[] _indexNames;

}