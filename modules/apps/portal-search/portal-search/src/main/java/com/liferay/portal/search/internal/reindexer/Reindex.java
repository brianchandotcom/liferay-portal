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

package com.liferay.portal.search.internal.reindexer;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistry;
import com.liferay.portal.kernel.search.SearchException;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.search.spi.reindexer.BulkReindexer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ExecutorService;

/**
 * @author Minhchau Dang
 * @author André de Oliveira
 */
public class Reindex {

	public Reindex(
		IndexerRegistry indexerRegistry,
		BulkReindexersHolder bulkReindexersHolder,
		ExecutorService executorService,
		ReindexRequestsHolder reindexRequestsHolder) {

		_indexerRegistry = indexerRegistry;
		_bulkReindexersHolder = bulkReindexersHolder;
		_executorService = executorService;
		_reindexRequestsHolder = reindexRequestsHolder;
	}

	public void reindex(String className, long... classPKs) {
		if (_synchronousExecution) {
			_reindex(className, ListUtil.fromArray(classPKs));

			return;
		}

		_reindexRequestsHolder.addAll(className, classPKs);

		_executorService.submit(
			new Runnable() {

				@Override
				public void run() {
					Collection<Long> classPKs = _reindexRequestsHolder.drain(
						className);

					if (!classPKs.isEmpty()) {
						_reindex(className, classPKs);

						_executorService.submit(this);
					}
				}

			});
	}

	public void setCompanyId(long companyId) {
		_companyId = companyId;
	}

	public void setNonbulkIndexing(boolean nonbulkIndexing) {
		_nonbulkIndexing = nonbulkIndexing;
	}

	public void setSynchronousExecution(boolean synchronousExecution) {
		_synchronousExecution = synchronousExecution;
	}

	protected void addReindexEndListener(
		ReindexEndListener reindexEndListener) {

		_reindexEndListeners.add(reindexEndListener);
	}

	protected Indexer<Object> getIndexer(String className) {
		return _indexerRegistry.getIndexer(className);
	}

	protected void setCustomReindex(CustomReindex customReindex) {
		_customReindex = customReindex;
	}

	protected void setCustomReindexBulk(CustomReindexBulk customReindexBulk) {
		_customReindexBulk = customReindexBulk;
	}

	protected interface CustomReindex {

		public void reindex(long classPK);

	}

	protected interface CustomReindexBulk {

		public void reindex(Collection<Long> classPKs);

	}

	protected interface ReindexEndListener {

		public void onReindexEnd();

	}

	private void _defaultReindex(String className, long classPK) {
		Indexer<?> indexer = getIndexer(className);

		if (!indexer.isIndexerEnabled()) {
			return;
		}

		try {
			indexer.reindex(className, classPK);
		}
		catch (SearchException searchException) {
			_log.error(searchException, searchException);
		}
	}

	private void _defaultReindexBulk(
		String className, Collection<Long> classPKs) {

		Indexer<?> indexer = getIndexer(className);

		if (!indexer.isIndexerEnabled()) {
			return;
		}

		BulkReindexer bulkReindexer = _bulkReindexersHolder.getBulkReindexer(
			className);

		bulkReindexer.reindex(_companyId, classPKs);
	}

	private void _reindex(String className, Collection<Long> classPKs) {
		if (_nonbulkIndexing || (classPKs.size() < 2)) {
			for (long classPK : classPKs) {
				_reindex(className, classPK);
			}
		}
		else {
			_reindexBulk(className, classPKs);
		}

		_reindexEndListeners.forEach(ReindexEndListener::onReindexEnd);
	}

	private void _reindex(String className, long classPK) {
		if (_customReindex != null) {
			_customReindex.reindex(classPK);
		}
		else {
			_defaultReindex(className, classPK);
		}
	}

	private void _reindexBulk(String className, Collection<Long> classPKs) {
		if (_customReindexBulk != null) {
			_customReindexBulk.reindex(classPKs);
		}
		else {
			_defaultReindexBulk(className, classPKs);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(Reindex.class);

	private final BulkReindexersHolder _bulkReindexersHolder;
	private long _companyId;
	private CustomReindex _customReindex;
	private CustomReindexBulk _customReindexBulk;
	private final ExecutorService _executorService;
	private final IndexerRegistry _indexerRegistry;
	private boolean _nonbulkIndexing;
	private final List<ReindexEndListener> _reindexEndListeners =
		new ArrayList<>();
	private final ReindexRequestsHolder _reindexRequestsHolder;
	private boolean _synchronousExecution;

}