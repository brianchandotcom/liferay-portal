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

package com.liferay.batch.engine.internal;

import com.liferay.batch.engine.BatchContentType;
import com.liferay.batch.engine.BatchItemWriter;
import com.liferay.batch.engine.BatchOperation;
import com.liferay.batch.engine.BatchStatus;
import com.liferay.batch.engine.BatchTaskExecutor;
import com.liferay.batch.engine.internal.reader.BatchItemReader;
import com.liferay.batch.engine.internal.reader.BatchItemReaderFactory;
import com.liferay.batch.engine.model.BatchTask;
import com.liferay.batch.engine.service.BatchTaskLocalService;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.TransactionConfig;
import com.liferay.portal.kernel.transaction.TransactionInvokerUtil;

import java.sql.Blob;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Ivica Cardic
 */
public class BatchTaskExecutorImpl<T> implements BatchTaskExecutor {

	public BatchTaskExecutorImpl(
		Class<T> domainClass, BatchItemWriterRegistry batchItemWriterRegistry,
		BatchTaskLocalService batchTaskLocalService) {

		_domainClass = domainClass;
		_batchItemWriterRegistry = batchItemWriterRegistry;
		_batchTaskLocalService = batchTaskLocalService;
	}

	@Override
	public void execute(BatchTask batchTask) {
		try {
			batchTask.setStartTime(new Date());
			batchTask.setStatus(BatchStatus.STARTED.toString());

			_batchTaskLocalService.updateBatchTask(batchTask);

			_execute(batchTask);

			batchTask.setEndTime(new Date());
			batchTask.setStatus(BatchStatus.COMPLETED.toString());

			_batchTaskLocalService.updateBatchTask(batchTask);
		}
		catch (Throwable t) {
			_log.error("Batch job failed : " + batchTask, t);

			batchTask.setErrorMessage(t.getMessage());

			batchTask.setEndTime(new Date());
			batchTask.setStatus(BatchStatus.FAILED.toString());

			_batchTaskLocalService.updateBatchTask(batchTask);
		}
	}

	private Void _commitItems(
			BatchItemWriter<T> batchItemWriter, List<T> items,
			BatchOperation batchOperation)
		throws Exception {

		batchItemWriter.write(items, batchOperation);

		return null;
	}

	private Void _execute(BatchTask batchTask) throws Throwable {
		List<T> items = new ArrayList<>();

		T item = null;

		Blob content = batchTask.getContent();

		BatchItemWriter<T> batchItemWriter = _batchItemWriterRegistry.get(
			batchTask.getClassName(), batchTask.getVersion());

		BatchOperation batchOperation = BatchOperation.valueOf(
			batchTask.getOperation());

		try (BatchItemReader<T> batchItemReader = BatchItemReaderFactory.create(
				_domainClass,
				BatchContentType.valueOf(batchTask.getContentType()),
				content.getBinaryStream())) {

			while ((item = batchItemReader.read()) != null) {
				if (items.size() < batchTask.getBatchSize()) {
					items.add(item);
				}
				else {
					TransactionInvokerUtil.invoke(
						_transactionConfig,
						() -> _commitItems(
							batchItemWriter, items, batchOperation));

					items.clear();
				}
			}
		}

		if (!items.isEmpty()) {
			TransactionInvokerUtil.invoke(
				_transactionConfig,
				() -> _commitItems(batchItemWriter, items, batchOperation));
		}

		return null;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		BatchTaskExecutorImpl.class);

	private static final TransactionConfig _transactionConfig =
		TransactionConfig.Factory.create(
			Propagation.REQUIRES_NEW, new Class<?>[] {Exception.class});

	private final BatchItemWriterRegistry _batchItemWriterRegistry;
	private final BatchTaskLocalService _batchTaskLocalService;
	private final Class<T> _domainClass;

}