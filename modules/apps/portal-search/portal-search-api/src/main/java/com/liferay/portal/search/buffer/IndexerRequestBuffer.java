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

import com.liferay.petra.lang.CentralizedThreadLocal;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * @author Michael C. Han
 */
public class IndexerRequestBuffer {

	public static IndexerRequestBuffer create() {
		List<IndexerRequestBuffer> indexerRequestBuffers =
			_indexerRequestBuffersThreadLocal.get();

		IndexerRequestBuffer indexerRequestBuffer = new IndexerRequestBuffer();

		indexerRequestBuffers.add(indexerRequestBuffer);

		return indexerRequestBuffer;
	}

	public static IndexerRequestBuffer get() {
		List<IndexerRequestBuffer> indexerRequestBuffers =
			_indexerRequestBuffersThreadLocal.get();

		if (indexerRequestBuffers.isEmpty()) {
			return null;
		}

		return indexerRequestBuffers.get(indexerRequestBuffers.size() - 1);
	}

	public static IndexerRequestBuffer remove() {
		List<IndexerRequestBuffer> indexerRequestBuffers =
			_indexerRequestBuffersThreadLocal.get();

		if (indexerRequestBuffers.isEmpty()) {
			return null;
		}

		return indexerRequestBuffers.remove(indexerRequestBuffers.size() - 1);
	}

	/**
	 * @param      indexerRequest
	 * @deprecated As of Judson (7.1.x), replaced by {@link #add(IndexerRequest,
	 *             IndexerRequestBufferOverflowHandler, int)}
	 */
	@Deprecated
	public void add(IndexerRequest indexerRequest) {
		_indexerRequests.put(indexerRequest, indexerRequest);
	}

	public void add(
		IndexerRequest indexerRequest,
		IndexerRequestBufferOverflowHandler indexerRequestBufferOverflowHandler,
		int maxBufferSize) {

		_indexerRequests.put(indexerRequest, indexerRequest);

		indexerRequestBufferOverflowHandler.bufferOverflowed(
			this, maxBufferSize);
	}

	public void clear() {
		_indexerRequests.clear();
	}

	public Collection<IndexerRequest> getIndexerRequests() {
		return _indexerRequests.values();
	}

	public boolean isEmpty() {
		return _indexerRequests.isEmpty();
	}

	public void remove(IndexerRequest indexerRequest) {
		_indexerRequests.remove(indexerRequest);
	}

	public int size() {
		return _indexerRequests.size();
	}

	private static final ThreadLocal<List<IndexerRequestBuffer>>
		_indexerRequestBuffersThreadLocal = new CentralizedThreadLocal<>(
			IndexerRequestBuffer.class + "._indexerRequestBuffersThreadLocal",
			ArrayList::new);

	private final LinkedHashMap<IndexerRequest, IndexerRequest>
		_indexerRequests = new LinkedHashMap<>();

}