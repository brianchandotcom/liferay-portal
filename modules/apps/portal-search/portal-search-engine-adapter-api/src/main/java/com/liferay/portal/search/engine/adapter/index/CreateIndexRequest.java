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
 * @author Michael C. Han
 */
public class CreateIndexRequest implements IndexRequest<CreateIndexResponse> {

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

	/**
	 * @return
	 * @deprecated As of Judson (7.1.x), with no direct replacement. This method
	 *             should not be in the parent interface.  Only certain
	 *             IndexRequests work with mappings.
	 */
	@Deprecated
	@Override
	public String getMappingName() {
		throw new UnsupportedOperationException();
	}

	public String getSource() {
		return _source;
	}

	public void setSource(String source) {
		_source = source;
	}

	private final String _indexName;
	private String _source;

}