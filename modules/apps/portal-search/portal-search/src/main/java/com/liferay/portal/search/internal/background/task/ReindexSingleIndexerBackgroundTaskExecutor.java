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

import com.liferay.portal.kernel.backgroundtask.BackgroundTask;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskConstants;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskExecutor;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.IndexWriterHelper;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistry;
import com.liferay.portal.kernel.search.SearchEngine;
import com.liferay.portal.kernel.search.SearchEngineHelper;
import com.liferay.portal.kernel.search.background.task.ReindexBackgroundTaskConstants;
import com.liferay.portal.kernel.search.background.task.ReindexStatusMessageSender;
import com.liferay.portal.kernel.util.Validator;

import java.io.Serializable;

import java.util.Collection;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Andrew Betts
 */
@Component(
	immediate = true,
	property = "background.task.executor.class.name=com.liferay.portal.search.internal.background.task.ReindexSingleIndexerBackgroundTaskExecutor",
	service = {
		BackgroundTaskExecutor.class,
		ReindexSingleIndexerBackgroundTaskExecutor.class
	}
)
public class ReindexSingleIndexerBackgroundTaskExecutor
	extends ReindexBackgroundTaskExecutor {

	public ReindexSingleIndexerBackgroundTaskExecutor() {
		setIsolationLevel(BackgroundTaskConstants.ISOLATION_LEVEL_TASK_NAME);
	}

	@Override
	public BackgroundTaskExecutor clone() {
		return this;
	}

	@Override
	public String generateLockKey(BackgroundTask backgroundTask) {
		Map<String, Serializable> taskContextMap =
			backgroundTask.getTaskContextMap();

		String className = (String)taskContextMap.get("className");

		if (Validator.isNotNull(className)) {
			return className;
		}

		return super.generateLockKey(backgroundTask);
	}

	@Override
	protected void reindex(String className, long[] companyIds)
		throws Exception {

		Indexer<?> indexer = indexerRegistry.getIndexer(className);

		if (indexer == null) {
			return;
		}

		Collection<SearchEngine> searchEngines =
			searchEngineHelper.getSearchEngines();

		for (long companyId : companyIds) {
			reindexStatusMessageSender.sendStatusMessage(
				ReindexBackgroundTaskConstants.SINGLE_START, companyId,
				companyIds);

			try {
				for (SearchEngine searchEngine : searchEngines) {
					searchEngine.initialize(companyId);
				}

				indexWriterHelper.deleteEntityDocuments(
					indexer.getSearchEngineId(), companyId, className, true);

				indexer.reindex(new String[] {String.valueOf(companyId)});
			}
			catch (Exception exception) {
				_log.error(exception, exception);
			}
			finally {
				reindexStatusMessageSender.sendStatusMessage(
					ReindexBackgroundTaskConstants.SINGLE_END, companyId,
					companyIds);
			}
		}
	}

	@Reference
	protected IndexerRegistry indexerRegistry;

	@Reference
	protected IndexWriterHelper indexWriterHelper;

	@Reference
	protected ReindexStatusMessageSender reindexStatusMessageSender;

	@Reference
	protected SearchEngineHelper searchEngineHelper;

	private static final Log _log = LogFactoryUtil.getLog(
		ReindexSingleIndexerBackgroundTaskExecutor.class);

}