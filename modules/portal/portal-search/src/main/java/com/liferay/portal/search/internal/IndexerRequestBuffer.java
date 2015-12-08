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
import com.liferay.portal.kernel.search.SearchEngineUtil;
import com.liferay.portal.kernel.search.SearchException;
import com.liferay.portal.kernel.util.AutoResetThreadLocal;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

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

	public void add(IndexerRequest indexerRequest) {
		_indexerRequests.put(indexerRequest, indexerRequest);
	}

	public void clear() {
		_indexerRequests.clear();
	}

	public void execute() {
		Set<String> searchEngineIds = new HashSet<>();

		for (IndexerRequest indexerRequest : _indexerRequests.values()) {
			try {
				indexerRequest.execute();

				searchEngineIds.add(indexerRequest.getSearchEngineId());
			}
			catch (Exception e) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"Unable to execute index request: " + indexerRequest,
						e);
				}
			}
		}

		_indexerRequests.clear();

		commit(searchEngineIds);
	}

	public void execute(int numRequests) {
		Set<String> searchEngineIds = new HashSet<>();

		Collection<IndexerRequest> completedIndexerRequests = new ArrayList<>();

		for (IndexerRequest indexerRequest : _indexerRequests.values()) {
			try {
				indexerRequest.execute();

				searchEngineIds.add(indexerRequest.getSearchEngineId());
			}
			catch (Exception e) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"Unable to execute index request: " + indexerRequest,
						e);
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

		commit(searchEngineIds);
	}

	public boolean isEmpty() {
		return _indexerRequests.isEmpty();
	}

	public int size() {
		return _indexerRequests.size();
	}

	protected void commit(Set<String> searchEngineIds) {
		for (String searchEngineId : searchEngineIds) {
			try {
				SearchEngineUtil.commit(searchEngineId);
			}
			catch (SearchException e) {
				if (_log.isWarnEnabled()) {
					_log.warn("Unable to commit search engine: ", e);
				}
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		IndexerRequestBuffer.class);

	private static final ThreadLocal<List<IndexerRequestBuffer>>
		_indexerRequestBuffersThreadLocal =
			new AutoResetThreadLocal<List<IndexerRequestBuffer>>(
				IndexerRequestBuffer.class +
					"._indexerRequestBuffersThreadLocal") {

				@Override
				protected List<IndexerRequestBuffer> initialValue() {
					return new ArrayList<>();
				}

			};

	private final LinkedHashMap<IndexerRequest, IndexerRequest>
		_indexerRequests = new LinkedHashMap<>();

}