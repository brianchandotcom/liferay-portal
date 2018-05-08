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

package com.liferay.portal.search.internal.buffer;

import com.liferay.portal.kernel.transaction.TransactionAttribute;
import com.liferay.portal.kernel.transaction.TransactionLifecycleListener;
import com.liferay.portal.kernel.transaction.TransactionStatus;
import com.liferay.portal.search.buffer.IndexerRequestBuffer;
import com.liferay.portal.search.buffer.IndexerRequestBufferExecutor;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Michael C. Han
 */
@Component(immediate = true, service = TransactionLifecycleListener.class)
public class IndexerRequestBufferTransactionLifecycleListener
	implements TransactionLifecycleListener {

	@Override
	public void committed(
		TransactionAttribute transactionAttribute,
		TransactionStatus transactionStatus) {

		IndexerRequestBuffer indexerRequestBuffer =
			IndexerRequestBuffer.remove();

		if ((indexerRequestBuffer != null) && !indexerRequestBuffer.isEmpty()) {
			IndexerRequestBufferExecutor indexerRequestBufferExecutor =
				_indexerRequestBufferExecutorWatcher.
					getIndexerRequestBufferExecutor();

			indexerRequestBufferExecutor.execute(indexerRequestBuffer);
		}
	}

	@Override
	public void created(
		TransactionAttribute transactionAttribute,
		TransactionStatus transactionStatus) {

		IndexerRequestBuffer.create();
	}

	@Override
	public void rollbacked(
		TransactionAttribute transactionAttribute,
		TransactionStatus transactionStatus, Throwable throwable) {

		IndexerRequestBuffer indexerRequestBuffer =
			IndexerRequestBuffer.remove();

		if ((indexerRequestBuffer != null) && !indexerRequestBuffer.isEmpty()) {
			indexerRequestBuffer.clear();
		}
	}

	@Reference
	private IndexerRequestBufferExecutorWatcher
		_indexerRequestBufferExecutorWatcher;

}