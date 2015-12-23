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

package com.liferay.portal.search.internal.background.task;

import com.liferay.portal.kernel.backgroundtask.BackgroundTaskExecutor;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.IndexWriterHelperUtil;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.search.background.task.ReindexBackgroundTaskConstants;
import com.liferay.portal.kernel.search.background.task.ReindexStatusMessageSenderUtil;

/**
 * @author Andrew Betts
 */
public class ReindexSingleIndexerBackgroundTaskExecutor
	extends ReindexBackgroundTaskExecutor {

	@Override
	public BackgroundTaskExecutor clone() {
		return new ReindexSingleIndexerBackgroundTaskExecutor();
	}

	@Override
	protected void reindex(String className, long[] companyIds)
		throws Exception {

		Indexer<?> indexer = IndexerRegistryUtil.getIndexer(className);

		if (indexer == null) {
			return;
		}

		for (long companyId : companyIds) {
			ReindexStatusMessageSenderUtil.sendStatusMessage(
				ReindexBackgroundTaskConstants.SINGLE_START, companyId,
				companyIds);

			try {
				IndexWriterHelperUtil.deleteEntityDocuments(
					indexer.getSearchEngineId(), companyId, className, true);

				indexer.reindex(new String[] {String.valueOf(companyId)});
			}
			catch (Exception e) {
				_log.error(e, e);
			}
			finally {
				ReindexStatusMessageSenderUtil.sendStatusMessage(
					ReindexBackgroundTaskConstants.SINGLE_END, companyId,
					companyIds);
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ReindexSingleIndexerBackgroundTaskExecutor.class);

}