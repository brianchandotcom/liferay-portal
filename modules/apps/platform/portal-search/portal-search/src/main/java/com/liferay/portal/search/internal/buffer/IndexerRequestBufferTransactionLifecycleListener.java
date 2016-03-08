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

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.TransactionAttribute;
import com.liferay.portal.kernel.transaction.TransactionConfig;
import com.liferay.portal.kernel.transaction.TransactionInvokerUtil;
import com.liferay.portal.kernel.transaction.TransactionLifecycleListener;
import com.liferay.portal.kernel.transaction.TransactionStatus;
import com.liferay.portal.kernel.util.ReflectionUtil;
import com.liferay.portal.search.buffer.IndexerRequestBuffer;
import com.liferay.portal.search.buffer.IndexerRequestBufferExecutor;

import java.util.concurrent.Callable;

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

		final IndexerRequestBuffer indexerRequestBuffer =
			IndexerRequestBuffer.remove();

		if ((indexerRequestBuffer != null) && !indexerRequestBuffer.isEmpty()) {
			final IndexerRequestBufferExecutor indexerRequestBufferExecutor =
				_indexerRequestBufferExecutorWatcher.
					getIndexerRequestBufferExecutor();

			try {
				TransactionInvokerUtil.invoke(
					_NEW_TRANSACTION_CONFIG,
					new Callable<Void>() {

						@Override
						public Void call() throws Exception {
							indexerRequestBufferExecutor.execute(
								indexerRequestBuffer);

							return null;
						}

					});
			}
			catch (Throwable t) {
				ReflectionUtil.throwException(t);
			}
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

	private static final TransactionConfig _NEW_TRANSACTION_CONFIG;

	static {
		TransactionConfig.Builder builder = new TransactionConfig.Builder();

		builder.setPropagation(Propagation.REQUIRES_NEW);
		builder.setRollbackForClasses(
			PortalException.class, SystemException.class);

		_NEW_TRANSACTION_CONFIG = builder.build();
	}

	@Reference
	private IndexerRequestBufferExecutorWatcher
		_indexerRequestBufferExecutorWatcher;

}