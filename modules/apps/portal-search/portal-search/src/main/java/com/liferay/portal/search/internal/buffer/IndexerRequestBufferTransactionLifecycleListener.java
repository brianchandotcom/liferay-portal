/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.internal.buffer;

import com.liferay.portal.kernel.transaction.TransactionAttribute;
import com.liferay.portal.kernel.transaction.TransactionLifecycleListener;
import com.liferay.portal.kernel.transaction.TransactionStatus;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Michael C. Han
 */
@Component(service = TransactionLifecycleListener.class)
public class IndexerRequestBufferTransactionLifecycleListener
	implements TransactionLifecycleListener {

	@Override
	public void committed(
		TransactionAttribute transactionAttribute,
		TransactionStatus transactionStatus) {

		IndexerRequestBuffer indexerRequestBuffer =
			IndexerRequestBuffer.remove();

		if ((indexerRequestBuffer != null) && !indexerRequestBuffer.isEmpty()) {
			_indexerRequestBufferExecutor.execute(indexerRequestBuffer);
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
	private IndexerRequestBufferExecutor _indexerRequestBufferExecutor;

}