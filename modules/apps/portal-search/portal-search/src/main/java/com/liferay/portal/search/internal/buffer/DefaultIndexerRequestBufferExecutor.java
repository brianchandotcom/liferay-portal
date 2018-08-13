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

package com.liferay.portal.search.internal.buffer;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.IndexWriterHelper;
import com.liferay.portal.search.buffer.IndexerRequest;
import com.liferay.portal.search.buffer.IndexerRequestBuffer;
import com.liferay.portal.search.buffer.IndexerRequestBufferExecutor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.util.tracker.ServiceTracker;

/**
 * @author Michael C. Han
 */
@Component(
	immediate = true, property = "buffered.execution.mode=DEFAULT",
	service = IndexerRequestBufferExecutor.class
)
public class DefaultIndexerRequestBufferExecutor
	extends BaseIndexerRequestBufferExecutor
	implements IndexerRequestBufferExecutor {

	@Override
	public void execute(
		IndexerRequestBuffer indexerRequestBuffer, int numRequests) {

		Set<String> searchEngineIds = new HashSet<>();

		Collection<IndexerRequest> completedIndexerRequests = new ArrayList<>();

		if (_log.isDebugEnabled()) {
			Collection<IndexerRequest> indexerRequests =
				indexerRequestBuffer.getIndexerRequests();

			_log.debug(
				StringBundler.concat(
					"Indexer request buffer size ", indexerRequests.size(),
					" to execute ", numRequests, " requests"));
		}

		int i = 0;

		for (IndexerRequest indexerRequest :
				indexerRequestBuffer.getIndexerRequests()) {

			if (_log.isDebugEnabled()) {
				_log.debug(
					StringBundler.concat(
						"Executing indexer request ", i++, ": ",
						indexerRequest));
			}

			executeIndexerRequest(searchEngineIds, indexerRequest);

			completedIndexerRequests.add(indexerRequest);

			if (completedIndexerRequests.size() == numRequests) {
				break;
			}
		}

		for (IndexerRequest indexerRequest : completedIndexerRequests) {
			indexerRequestBuffer.remove(indexerRequest);
		}

		if (!BufferOverflowThreadLocal.isOverflowMode()) {
			commit(searchEngineIds);
		}
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_indexWriterHelperServiceTracker = new ServiceTracker<>(
			bundleContext, IndexWriterHelper.class, null);

		_indexWriterHelperServiceTracker.open();
	}

	@Deactivate
	protected void deactivate() {
		_indexWriterHelperServiceTracker.close();
	}

	@Override
	protected IndexWriterHelper getIndexWriterHelper() {
		return _indexWriterHelperServiceTracker.getService();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DefaultIndexerRequestBufferExecutor.class);

	private ServiceTracker<IndexWriterHelper, IndexWriterHelper>
		_indexWriterHelperServiceTracker;

}