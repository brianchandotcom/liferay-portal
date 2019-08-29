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
public class CreateIndexResponse implements IndexResponse {

	public CreateIndexResponse(boolean acknowledged) {
		_acknowledged = acknowledged;
		_indexName = null;
	}

	public CreateIndexResponse(boolean acknowledged, String indexName) {
		_acknowledged = acknowledged;
		_indexName = indexName;
	}

	public String getIndexName() {
		return _indexName;
	}

	public boolean isAcknowledged() {
		return _acknowledged;
	}

	private final boolean _acknowledged;
	private final String _indexName;

}