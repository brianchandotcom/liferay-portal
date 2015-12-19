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
import com.liferay.portal.kernel.search.IndexWriterHelperUtil;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.search.SearchEngineHelperUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.util.PropsValues;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang.time.StopWatch;

/**
 * @author Brian Wing Shun Chan
 */
public class SearchEngineInitializer implements Runnable {

	public SearchEngineInitializer(long companyId) {
		_companyId = companyId;
		_usedSearchEngineIds = new HashSet<>();
	}

	public Set<String> getUsedSearchEngineIds() {
		return _usedSearchEngineIds;
	}

	public void halt() {
	}

	public boolean isFinished() {
		return _finished;
	}

	public void reindex() {
		reindex(0);
	}

	public void reindex(int delay) {
		doReIndex(delay);
	}

	@Override
	public void run() {
		reindex(PropsValues.INDEX_ON_STARTUP_DELAY);
	}

	protected void doReIndex(int delay) {
		if (IndexWriterHelperUtil.isIndexReadOnly()) {
			return;
		}

		if (_log.isInfoEnabled()) {
			_log.info("Reindexing Lucene started");
		}

		if (delay < 0) {
			delay = 0;
		}

		try {
			if (delay > 0) {
				Thread.sleep(Time.SECOND * delay);
			}
		}
		catch (InterruptedException ie) {
		}

		StopWatch stopWatch = new StopWatch();

		stopWatch.start();

		try {
			SearchEngineHelperUtil.removeCompany(_companyId);

			SearchEngineHelperUtil.initialize(_companyId);

			Set<String> searchEngineIds = new HashSet<>();

			for (Indexer<?> indexer : IndexerRegistryUtil.getIndexers()) {
				String searchEngineId = indexer.getSearchEngineId();

				if (searchEngineIds.add(searchEngineId)) {
					IndexWriterHelperUtil.deleteEntityDocuments(
						searchEngineId, _companyId, indexer.getClassName(),
						true);
				}

				reindex(indexer);
			}

			if (_log.isInfoEnabled()) {
				_log.info(
					"Reindexing Lucene completed in " +
						(stopWatch.getTime() / Time.SECOND) + " seconds");
			}
		}
		catch (Exception e) {
			_log.error("Error encountered while reindexing", e);

			if (_log.isInfoEnabled()) {
				_log.info("Reindexing Lucene failed");
			}
		}

		_finished = true;
	}

	protected void reindex(Indexer<?> indexer) throws Exception {
		StopWatch stopWatch = new StopWatch();

		stopWatch.start();

		if (_log.isInfoEnabled()) {
			_log.info("Reindexing with " + indexer.getClass() + " started");
		}

		indexer.reindex(new String[] {String.valueOf(_companyId)});

		_usedSearchEngineIds.add(indexer.getSearchEngineId());

		if (_log.isInfoEnabled()) {
			_log.info(
				"Reindexing with " + indexer.getClass() +
					" completed in " + (stopWatch.getTime() / Time.SECOND) +
						" seconds");
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		SearchEngineInitializer.class);

	private final long _companyId;
	private boolean _finished;
	private final Set<String> _usedSearchEngineIds;

}