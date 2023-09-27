/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.internal.buffer.util;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.module.service.Snapshot;
import com.liferay.portal.kernel.search.IndexWriterHelper;
import com.liferay.portal.kernel.search.SearchException;
import com.liferay.portal.search.internal.buffer.BufferOverflowThreadLocal;
import com.liferay.portal.search.internal.buffer.IndexerRequest;
import com.liferay.portal.search.internal.buffer.IndexerRequestBuffer;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @author Michael C. Han
 */
public class IndexerRequestBufferExecutorUtil {

	public static void execute(IndexerRequestBuffer indexerRequestBuffer) {
		execute(indexerRequestBuffer, indexerRequestBuffer.size());
	}

	public static void execute(
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

		if (!BufferOverflowThreadLocal.isOverflowMode()) {
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