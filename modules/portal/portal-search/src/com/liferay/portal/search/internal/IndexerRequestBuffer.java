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

package com.liferay.portal.search.internal;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.InitialThreadLocal;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;

/**
 * @author Michael C. Han
 */
public class IndexerRequestBuffer {

	public static IndexerRequestBuffer get() {
		return _indexerRequestBufferThreadLocal.get();
	}

	public static IndexerRequestBuffer getOrCreate() {
		IndexerRequestBuffer indexerRequestBuffer = IndexerRequestBuffer.get();

		if (indexerRequestBuffer == null) {
			indexerRequestBuffer = new IndexerRequestBuffer();

			_indexerRequestBufferThreadLocal.set(indexerRequestBuffer);
		}

		return indexerRequestBuffer;
	}

	public static IndexerRequestBuffer remove() {
		IndexerRequestBuffer indexerRequestBuffer =
			_indexerRequestBufferThreadLocal.get();

		_indexerRequestBufferThreadLocal.remove();

		return indexerRequestBuffer;
	}

	public void add(IndexerRequest indexerRequest) {
		_indexerRequests.put(indexerRequest, indexerRequest);
	}

	public void clear() {
		_indexerRequests.clear();
	}

	public void execute() {
		for (IndexerRequest indexerRequest : _indexerRequests.values()) {
			try {
				indexerRequest.execute();
			}
			catch (Exception e) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"Unable to execute index request: " + indexerRequest);
				}
			}
		}

		_indexerRequests.clear();
	}

	public void execute(int numRequests) {
		Collection<IndexerRequest> completedIndexerRequests = new ArrayList<>();

		for (IndexerRequest indexerRequest : _indexerRequests.values()) {
			try {
				indexerRequest.execute();
			}
			catch (Exception e) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"Unable to execute index request: " + indexerRequest);
				}
			}

			completedIndexerRequests.add(indexerRequest);

			if (completedIndexerRequests.size() == numRequests) {
				break;
			}
		}

		for (IndexerRequest indexerRequest : completedIndexerRequests) {
			_indexerRequests.remove(indexerRequest);
		}
	}

	public int size() {
		return _indexerRequests.size();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		IndexerRequestBuffer.class);

	private static final ThreadLocal<IndexerRequestBuffer>
		_indexerRequestBufferThreadLocal =
			new InitialThreadLocal<>(
				IndexerRequestBuffer.class.getName() +
					"._indexerRequestBufferThreadLocal",
				null);

	private final LinkedHashMap<IndexerRequest, IndexerRequest>
		_indexerRequests = new LinkedHashMap<>();

}