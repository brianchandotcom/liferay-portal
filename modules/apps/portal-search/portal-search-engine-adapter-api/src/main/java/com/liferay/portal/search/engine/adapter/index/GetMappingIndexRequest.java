/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.engine.adapter.index;

import com.liferay.portal.search.engine.adapter.ccr.CrossClusterRequest;

/**
 * @author Dylan Rebelak
 */
public class GetMappingIndexRequest
	extends CrossClusterRequest
	implements MappingIndexRequest<GetMappingIndexResponse> {

	public GetMappingIndexRequest(String[] indexNames) {
		_indexNames = indexNames;

		_mappingName = null;

		setPreferLocalCluster(true);
	}

	/**
	 * @deprecated As of Athanasius (7.3.x)
	 */
	@Deprecated
	public GetMappingIndexRequest(String[] indexNames, String mappingName) {
		_indexNames = indexNames;
		_mappingName = mappingName;

		setPreferLocalCluster(true);
	}

	@Override
	public GetMappingIndexResponse accept(
		IndexRequestExecutor indexRequestExecutor) {

		return indexRequestExecutor.executeIndexRequest(this);
	}

	@Override
	public String[] getIndexNames() {
		return _indexNames;
	}

	@Override
	public String getMappingName() {
		return _mappingName;
	}

	private final String[] _indexNames;
	private final String _mappingName;

}