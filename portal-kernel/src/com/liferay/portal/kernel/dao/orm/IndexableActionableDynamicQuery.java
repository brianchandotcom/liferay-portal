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

package com.liferay.portal.kernel.dao.orm;

import com.liferay.portal.kernel.backgroundtask.BackgroundTaskThreadLocal;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.IndexWriterHelper;
import com.liferay.portal.kernel.search.SearchEngineHelperUtil;
import com.liferay.portal.kernel.search.background.task.ReindexStatusMessageSenderUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ServiceProxyFactory;
import com.liferay.portal.kernel.util.Validator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.ConcurrentLinkedDeque;

/**
 * @author Andrew Betts
 */
public class IndexableActionableDynamicQuery
	extends DefaultActionableDynamicQuery {

	public void addDocuments(Document... documents) throws PortalException {
		if (ArrayUtil.isEmpty(documents)) {
			return;
		}

		for (Document document : documents) {
			if (document != null) {
				_documents.add(document);
			}
		}

		long size = _documents.size();

		if (size >= getInterval()) {
			indexInterval();
		}
		else if ((size % _STATUS_INTERVAL) == 0) {
			sendStatusMessage(size);
		}
	}

	@Override
	public void performActions() throws PortalException {
		if (BackgroundTaskThreadLocal.hasBackgroundTask()) {
			_total = super.performCount();
		}

		try {
			super.performActions();
		}
		finally {
			_count = _total;

			sendStatusMessage();
		}
	}

	public void setIndexWriterHelper(IndexWriterHelper indexWriterHelper) {
		_indexWriterHelper = indexWriterHelper;
	}

	@Override
	public void setParallel(boolean parallel) {
		if (isParallel() == parallel) {
			return;
		}

		super.setParallel(parallel);

		if (parallel) {
			_documents = new ConcurrentLinkedDeque<>();
		}
	}

	public void setSearchEngineId(String searchEngineId) {
		_searchEngineId = searchEngineId;
	}

	@Override
	protected void actionsCompleted() throws PortalException {
		if (Validator.isNotNull(_searchEngineId)) {
			_indexWriterHelper.commit(_searchEngineId, getCompanyId());
		}
	}

	@Override
	protected long doPerformActions(long previousPrimaryKey)
		throws PortalException {

		try {
			return super.doPerformActions(previousPrimaryKey);
		}
		finally {
			indexInterval();
		}
	}

	protected String getSearchEngineId() {
		return _searchEngineId;
	}

	protected void indexInterval() throws PortalException {
		if ((_documents == null) || _documents.isEmpty()) {
			return;
		}

		if (Validator.isNull(_searchEngineId)) {
			_searchEngineId = SearchEngineHelperUtil.getSearchEngineId(
				_documents);
		}

		_indexWriterHelper.updateDocuments(
			_searchEngineId, getCompanyId(), new ArrayList<>(_documents),
			false);

		_count += _documents.size();

		_documents.clear();

		sendStatusMessage();
	}

	protected void sendStatusMessage() {
		sendStatusMessage(0);
	}

	protected void sendStatusMessage(long documentIntervalCount) {
		if (!BackgroundTaskThreadLocal.hasBackgroundTask()) {
			return;
		}

		Class<?> modelClass = getModelClass();

		ReindexStatusMessageSenderUtil.sendStatusMessage(
			modelClass.getName(), _count + documentIntervalCount, _total);
	}

	private static final long _STATUS_INTERVAL = 1000;

	private static volatile IndexWriterHelper _indexWriterHelperProxy =
		ServiceProxyFactory.newServiceTrackedInstance(
			IndexWriterHelper.class, IndexableActionableDynamicQuery.class,
			"_indexWriterHelperProxy", false);

	private long _count;
	private Collection<Document> _documents = new ArrayList<>();
	private IndexWriterHelper _indexWriterHelper = _indexWriterHelperProxy;
	private String _searchEngineId;
	private long _total;

}