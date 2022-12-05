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
import com.liferay.portal.kernel.search.SearchException;

import java.util.ArrayList;
import java.util.Collection;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.util.tracker.ServiceTracker;

/**
 * @author Michael C. Han
 */
@Component(immediate = true, service = IndexerRequestBufferExecutor.class)
public class IndexerRequestBufferExecutor {

	public void execute(IndexerRequestBuffer indexerRequestBuffer) {
		execute(indexerRequestBuffer, indexerRequestBuffer.size());
	}

	public void execute(
		IndexerRequestBuffer indexerRequestBuffer, int numRequests) {

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

			_executeIndexerRequest(indexerRequest);

			completedIndexerRequests.add(indexerRequest);

			if (completedIndexerRequests.size() == numRequests) {
				break;
			}
		}

		for (IndexerRequest indexerRequest : completedIndexerRequests) {
			indexerRequestBuffer.remove(indexerRequest);
		}

		if (!BufferOverflowThreadLocal.isOverflowMode()) {
			_commit();
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

	private void _commit() {
		IndexWriterHelper indexWriterHelper = _getIndexWriterHelper();

		if (indexWriterHelper == null) {
			if (_log.isWarnEnabled()) {
				_log.warn("Index writer helper is null");
			}

			return;
		}

		try {
			indexWriterHelper.commit();
		}
		catch (SearchException searchException) {
			if (_log.isWarnEnabled()) {
				_log.warn("Unable to commit search engine", searchException);
			}
		}
	}

	private void _executeIndexerRequest(IndexerRequest indexerRequest) {
		try {
			indexerRequest.execute();
		}
		catch (Exception exception) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Unable to execute index request " + indexerRequest,
					exception);
			}
		}
	}

	private IndexWriterHelper _getIndexWriterHelper() {
		return _indexWriterHelperServiceTracker.getService();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		IndexerRequestBufferExecutor.class);

	private ServiceTracker<IndexWriterHelper, IndexWriterHelper>
		_indexWriterHelperServiceTracker;

}