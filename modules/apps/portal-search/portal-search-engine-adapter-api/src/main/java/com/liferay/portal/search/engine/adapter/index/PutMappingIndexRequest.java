/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.engine.adapter.index;

import com.liferay.portal.search.engine.adapter.ccr.CrossClusterRequest;

/**
 * @author Dylan Rebelak
 */
public class PutMappingIndexRequest
	extends CrossClusterRequest
	implements MappingIndexRequest<PutMappingIndexResponse> {

	public PutMappingIndexRequest(String[] indexNames, String mapping) {
		_indexNames = indexNames;
		_mapping = mapping;

		_mappingName = null;
	}

	/**
	 * @deprecated As of Athanasius (7.3.x)
	 */
	@Deprecated
	public PutMappingIndexRequest(
		String[] indexNames, String mappingName, String mapping) {

		_indexNames = indexNames;
		_mappingName = mappingName;
		_mapping = mapping;
	}

	@Override
	public PutMappingIndexResponse accept(
		IndexRequestExecutor indexRequestExecutor) {

		return indexRequestExecutor.executeIndexRequest(this);
	}

	@Override
	public String[] getIndexNames() {
		return _indexNames;
	}

	public String getMapping() {
		return _mapping;
	}

	@Override
	public String getMappingName() {
		return _mappingName;
	}

	private final String[] _indexNames;
	private final String _mapping;
	private final String _mappingName;

}