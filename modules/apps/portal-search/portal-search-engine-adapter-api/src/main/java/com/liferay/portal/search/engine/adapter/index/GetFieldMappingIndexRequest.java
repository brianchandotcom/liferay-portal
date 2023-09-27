/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.engine.adapter.index;

import com.liferay.portal.search.engine.adapter.ccr.CrossClusterRequest;

/**
 * @author Dylan Rebelak
 */
public class GetFieldMappingIndexRequest
	extends CrossClusterRequest
	implements MappingIndexRequest<GetFieldMappingIndexResponse> {

	/**
	 * @deprecated As of Athanasius (7.3.x)
	 */
	@Deprecated
	public GetFieldMappingIndexRequest(
		String[] indexNames, String mappingName, String[] fields) {

		_indexNames = indexNames;
		_mappingName = mappingName;
		_fields = fields;

		setPreferLocalCluster(true);
	}

	public GetFieldMappingIndexRequest(String[] indexNames, String[] fields) {
		_indexNames = indexNames;
		_fields = fields;

		_mappingName = null;

		setPreferLocalCluster(true);
	}

	@Override
	public GetFieldMappingIndexResponse accept(
		IndexRequestExecutor indexRequestExecutor) {

		return indexRequestExecutor.executeIndexRequest(this);
	}

	public String[] getFields() {
		return _fields;
	}

	@Override
	public String[] getIndexNames() {
		return _indexNames;
	}

	@Override
	public String getMappingName() {
		return _mappingName;
	}

	private final String[] _fields;
	private final String[] _indexNames;
	private final String _mappingName;

}