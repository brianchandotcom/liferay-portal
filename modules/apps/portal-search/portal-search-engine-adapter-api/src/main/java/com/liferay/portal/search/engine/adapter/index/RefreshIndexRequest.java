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

import aQute.bnd.annotation.ProviderType;

import com.liferay.petra.string.StringPool;

/**
 * @author Michael C. Han
 */
@ProviderType
public class RefreshIndexRequest implements IndexRequest<RefreshIndexResponse> {

	public RefreshIndexRequest() {
		_indexNames = StringPool.EMPTY_ARRAY;
	}

	public RefreshIndexRequest(String... indexNames) {
		_indexNames = indexNames;
	}

	@Override
	public RefreshIndexResponse accept(
		IndexRequestExecutor indexRequestExecutor) {

		return indexRequestExecutor.executeIndexRequest(this);
	}

	@Override
	public String[] getIndexNames() {
		return _indexNames;
	}

	/**
	 * @deprecated As of Judson (7.1.x), since 1.1.0.  This method should not be
	 *             in the parent interface.  Only certain IndexRequests work
	 *             with mappings
	 * @return
	 */
	@Deprecated
	@Override
	public String getMappingName() {
		throw new UnsupportedOperationException();
	}

	private final String[] _indexNames;

}