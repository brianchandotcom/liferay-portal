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
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.search.SearchEngineUtil;
import com.liferay.portal.kernel.search.background.task.ReindexStatusMessageSenderUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @author Andrew Betts
 */
public class IndexableActionableDynamicQuery
	extends DefaultActionableDynamicQuery {

	public void addDocument(Document document) throws PortalException {
		if (_documents == null) {
			_documents = new ArrayList<>();
		}

		_documents.add(document);

		if (_documents.size() >= getInterval()) {
			indexInterval();
		}
	}

	public void addDocuments(Collection<Document> documents)
		throws PortalException {

		if (_documents == null) {
			_documents = new ArrayList<>();
		}

		_documents.addAll(documents);

		if (_documents.size() >= getInterval()) {
			indexInterval();
		}
	}

	@Override
	public void performActions() throws PortalException {
		if (BackgroundTaskThreadLocal.hasBackgroundTask()) {
			_count = super.performCount();
		}

		try {
			super.performActions();
		}
		finally {
			_progress = _count;

			sendStatusMessage();
		}
	}

	public void setCommitImmediately(boolean commitImmediately) {
		_commitImmediately = commitImmediately;
	}

	public void setSearchEngineId(String searchEngineId) {
		_searchEngineId = searchEngineId;
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
			_searchEngineId = SearchEngineUtil.getSearchEngineId(_documents);
		}

		SearchEngineUtil.updateDocuments(
			_searchEngineId, getCompanyId(), new ArrayList<>(_documents),
			_commitImmediately);

		_progress += _documents.size();

		_documents.clear();
	}

	@Override
	protected void intervalCompleted(long startPrimaryKey, long endPrimaryKey)
		throws PortalException {

		sendStatusMessage();
	}

	protected void sendStatusMessage() {
		if (!BackgroundTaskThreadLocal.hasBackgroundTask()) {
			return;
		}

		Class<?> clazz = getModelClass();

		Indexer indexer = IndexerRegistryUtil.getIndexer(clazz);

		String className = indexer.getClassName();

		ReindexStatusMessageSenderUtil.sendStatusMessage(
			className, _progress, _count);
	}

	private boolean _commitImmediately;
	private long _count;
	private Collection<Document> _documents;
	private long _progress;
	private String _searchEngineId;

}