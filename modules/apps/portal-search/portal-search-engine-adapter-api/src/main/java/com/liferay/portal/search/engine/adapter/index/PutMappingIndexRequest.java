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

/**
 * @author Dylan Rebelak
 */
public class PutMappingIndexRequest
	implements IndexRequest<PutMappingIndexResponse> {

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