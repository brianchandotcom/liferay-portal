/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.internal.buffer.util;

import com.liferay.petra.lang.SafeCloseable;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.concurrent.SystemExecutorServiceUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.mass.delete.MassDeleteCacheThreadLocal;
import com.liferay.portal.kernel.module.service.Snapshot;
import com.liferay.portal.kernel.search.IndexWriterHelper;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.SearchException;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.search.internal.buffer.BufferOverflowThreadLocal;
import com.liferay.portal.search.internal.buffer.IndexerRequest;
import com.liferay.portal.search.internal.buffer.IndexerRequestBuffer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author Michael C. Han
 */
public class IndexerRequestBufferExecutorUtil {

	public static void execute(IndexerRequestBuffer indexerRequestBuffer) {
		execute(indexerRequestBuffer, indexerRequestBuffer.size());
	}

	public static void execute(
		IndexerRequestBuffer indexerRequestBuffer, int numRequests) {

		if (!SearchContext.isBatchMode()) {
			_execute(indexerRequestBuffer, numRequests, true);

			return;
		}

		if (MassDeleteCacheThreadLocal.isMassDeleteMode()) {
			_execute(indexerRequestBuffer, numRequests, false);

			return;
		}

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		if (serviceContext != null) {
			serviceContext = (ServiceContext)serviceContext.clone();
		}

		ServiceContext finalServiceContext = serviceContext;

		ExecutorService executorService =
			SystemExecutorServiceUtil.getExecutorService();

		IndexerRequestBuffer transferCopyIndexerRequestBuffer =
			indexerRequestBuffer.transferCopy();

		AtomicReference<Future<?>> futureReference = new AtomicReference<>();

		FutureTask<?> futureTask = new FutureTask<Void>(
			() -> {
				ServiceContextThreadLocal.pushServiceContext(
					finalServiceContext);

				try (SafeCloseable safeCloseable = SearchContext.openBatchMode(
						false)) {

					_execute(
						transferCopyIndexerRequestBuffer,
						transferCopyIndexerRequestBuffer.size(), false);
				}
				catch (Exception exception) {
					_log.error(exception);
				}
				finally {
					ServiceContextThreadLocal.popServiceContext();

					SearchContext.unregisterBatchModeSyncFuture(
						futureReference.get());
				}

				return null;
			});

		futureReference.set(futureTask);

		SearchContext.registerBatchModeSyncFuture(futureTask);

		executorService.execute(futureTask);
	}

	private static void _execute(
		IndexerRequestBuffer indexerRequestBuffer, int numRequests,
		boolean commit) {

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

			completedIndexerRequests.add(indexerRequest);

			if (completedIndexerRequests.size() == numRequests) {
				break;
			}
		}

		for (IndexerRequest indexerRequest : completedIndexerRequests) {
			indexerRequestBuffer.remove(indexerRequest);
		}

		if (!BufferOverflowThreadLocal.isOverflowMode() && commit) {
			IndexWriterHelper indexWriterHelper =
				_indexWriterHelperSnapshot.get();

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
					_log.warn(
						"Unable to commit search engine", searchException);
				}
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		IndexerRequestBufferExecutorUtil.class);

	private static final Snapshot<IndexWriterHelper>
		_indexWriterHelperSnapshot = new Snapshot<>(
			IndexerRequestBufferExecutorUtil.class, IndexWriterHelper.class,
			null, true);

}