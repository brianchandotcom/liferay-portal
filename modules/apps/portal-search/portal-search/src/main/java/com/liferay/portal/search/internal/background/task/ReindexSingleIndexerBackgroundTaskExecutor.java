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

import com.liferay.osgi.service.tracker.collections.list.ServiceTrackerList;
import com.liferay.osgi.service.tracker.collections.list.ServiceTrackerListFactory;
import com.liferay.portal.kernel.backgroundtask.BackgroundTask;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskExecutor;
import com.liferay.portal.kernel.backgroundtask.constants.BackgroundTaskConstants;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.CompanyConstants;
import com.liferay.portal.kernel.search.IndexWriterHelper;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistry;
import com.liferay.portal.kernel.search.SearchEngine;
import com.liferay.portal.kernel.search.SearchEngineHelper;
import com.liferay.portal.kernel.search.background.task.ReindexBackgroundTaskConstants;
import com.liferay.portal.kernel.search.background.task.ReindexStatusMessageSender;
import com.liferay.portal.kernel.util.ServiceProxyFactory;
import com.liferay.portal.kernel.util.Validator;

import java.io.Serializable;

import java.util.Collection;
import java.util.Map;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
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
	extends BaseReindexBackgroundTaskExecutor {

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

	@Activate
	protected void activate(BundleContext bundleContext) {
		systemIndexers = ServiceTrackerListFactory.open(
			bundleContext, (Class<Indexer<?>>)(Class<?>)Indexer.class,
			"(system.index=true)");
	}

	@Deactivate
	protected void deactivate() {
		if (systemIndexers != null) {
			systemIndexers.close();
		}
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

		boolean systemIndexer = _isSystemIndexer(indexer);

		for (long companyId : companyIds) {
			if (((companyId == CompanyConstants.SYSTEM) && !systemIndexer) ||
				((companyId != CompanyConstants.SYSTEM) && systemIndexer)) {

				continue;
			}

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

	protected static volatile IndexWriterHelper indexWriterHelper =
		ServiceProxyFactory.newServiceTrackedInstance(
			IndexWriterHelper.class,
			ReindexSingleIndexerBackgroundTaskExecutor.class,
			"indexWriterHelper", true);

	@Reference
	protected IndexerRegistry indexerRegistry;

	@Reference
	protected ReindexStatusMessageSender reindexStatusMessageSender;

	@Reference
	protected SearchEngineHelper searchEngineHelper;

	protected ServiceTrackerList<Indexer<?>> systemIndexers;

	private boolean _isSystemIndexer(Indexer<?> indexer) {
		if (systemIndexers.size() > 0) {
			for (Indexer<?> systemIndexer : systemIndexers) {
				if (indexer.equals(systemIndexer)) {
					return true;
				}
			}
		}

		return false;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ReindexSingleIndexerBackgroundTaskExecutor.class);

}