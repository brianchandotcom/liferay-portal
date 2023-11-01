/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.dao.orm;

import com.liferay.petra.lang.SafeCloseable;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskThreadLocal;
import com.liferay.portal.kernel.change.tracking.CTCollectionThreadLocal;
import com.liferay.portal.kernel.change.tracking.sql.CTSQLModeThreadLocal;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.change.tracking.CTModel;
import com.liferay.portal.kernel.module.service.Snapshot;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.IndexWriterHelper;
import com.liferay.portal.kernel.search.background.task.ReindexStatusMessageSenderUtil;
import com.liferay.portal.kernel.util.ArrayUtil;

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

	@Override
	protected void actionsCompleted() throws PortalException {
		IndexWriterHelper indexWriterHelper =
			_indexWriterHelperProxySnapshot.get();

		indexWriterHelper.commit(getCompanyId());
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

	protected void indexInterval() throws PortalException {
		if ((_documents == null) || _documents.isEmpty()) {
			return;
		}

		IndexWriterHelper indexWriterHelper =
			_indexWriterHelperProxySnapshot.get();

		indexWriterHelper.updateDocuments(
			getCompanyId(), new ArrayList<>(_documents), false);

		_count += _documents.size();

		_documents.clear();

		sendStatusMessage();
	}

	@Override
	protected void performAction(Object object) throws PortalException {
		long ctCollectionId = 0;

		if (object instanceof CTModel) {
			CTModel<?> ctModel = (CTModel<?>)object;

			ctCollectionId = ctModel.getCtCollectionId();
		}

		try (SafeCloseable safeCloseable1 =
				CTSQLModeThreadLocal.setCTSQLModeWithSafeCloseable(
					CTSQLModeThreadLocal.CTSQLMode.DEFAULT);
			SafeCloseable safeCloseable2 =
				CTCollectionThreadLocal.setCTCollectionIdWithSafeCloseable(
					ctCollectionId)) {

			super.performAction(object);
		}
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

	private static final long _STATUS_INTERVAL = 100;

	private static final Snapshot<IndexWriterHelper>
		_indexWriterHelperProxySnapshot = new Snapshot<>(
			IndexableActionableDynamicQuery.class, IndexWriterHelper.class);

	private long _count;
	private Collection<Document> _documents = new ArrayList<>();
	private long _total;

}