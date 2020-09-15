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

package com.liferay.portal.search.buffer;

import java.util.Collection;

/**
 * @author Michael C. Han
 * @deprecated As of Athanasius (7.3.x)
 */
@Deprecated
public class IndexerRequestBuffer {

	public static IndexerRequestBuffer create() {
		throw new UnsupportedOperationException();
	}

	public static IndexerRequestBuffer get() {
		throw new UnsupportedOperationException();
	}

	public static IndexerRequestBuffer remove() {
		throw new UnsupportedOperationException();
	}

	public void add(
		IndexerRequest indexerRequest,
		IndexerRequestBufferOverflowHandler indexerRequestBufferOverflowHandler,
		int maxBufferSize) {

		throw new UnsupportedOperationException();
	}

	public void clear() {
		throw new UnsupportedOperationException();
	}

	public Collection<IndexerRequest> getIndexerRequests() {
		throw new UnsupportedOperationException();
	}

	public boolean isEmpty() {
		throw new UnsupportedOperationException();
	}

	public void remove(IndexerRequest indexerRequest) {
		throw new UnsupportedOperationException();
	}

	public int size() {
		throw new UnsupportedOperationException();
	}

}