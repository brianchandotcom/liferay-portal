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

import com.liferay.batch.engine.BatchEngineTaskContentType;
import com.liferay.batch.engine.BatchEngineTaskExecuteStatus;
import com.liferay.batch.engine.BatchEngineTaskExecutor;
import com.liferay.batch.engine.BatchEngineTaskItemWriter;
import com.liferay.batch.engine.BatchEngineTaskOperation;
import com.liferay.batch.engine.internal.reader.BatchEngineTaskItemReader;
import com.liferay.batch.engine.internal.reader.BatchEngineTaskItemReaderFactory;
import com.liferay.batch.engine.model.BatchEngineTask;
import com.liferay.batch.engine.service.BatchEngineTaskLocalService;
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
public class BatchEngineTaskExecutorImpl<T> implements BatchEngineTaskExecutor {

	public BatchEngineTaskExecutorImpl(
		BatchEngineTaskItemWriterRegistry batchEngineTaskItemWriterRegistry,
		BatchEngineTaskLocalService batchEngineTaskLocalService,
		Class<T> itemClass) {

		_batchEngineTaskItemWriterRegistry = batchEngineTaskItemWriterRegistry;
		_batchEngineTaskLocalService = batchEngineTaskLocalService;
		_itemClass = itemClass;
	}

	@Override
	public void execute(BatchEngineTask batchEngineTask) {
		try {
			batchEngineTask.setExecuteStatus(
				BatchEngineTaskExecuteStatus.STARTED.toString());
			batchEngineTask.setStartTime(new Date());

			_batchEngineTaskLocalService.updateBatchEngineTask(batchEngineTask);

			_execute(batchEngineTask);

			batchEngineTask.setEndTime(new Date());
			batchEngineTask.setExecuteStatus(
				BatchEngineTaskExecuteStatus.COMPLETED.toString());

			_batchEngineTaskLocalService.updateBatchEngineTask(batchEngineTask);
		}
		catch (Throwable t) {
			_log.error(
				"Unable to update batch engine task " + batchEngineTask, t);

			batchEngineTask.setEndTime(new Date());
			batchEngineTask.setErrorMessage(t.getMessage());
			batchEngineTask.setExecuteStatus(
				BatchEngineTaskExecuteStatus.FAILED.toString());

			_batchEngineTaskLocalService.updateBatchEngineTask(batchEngineTask);
		}
	}

	private void _commitItems(
			BatchEngineTaskItemWriter<T> batchEngineTaskItemWriter,
			List<T> items, BatchEngineTaskOperation batchEngineTaskOperation)
		throws Throwable {

		batchEngineTaskItemWriter.write(items, batchEngineTaskOperation);

		TransactionInvokerUtil.invoke(
			_transactionConfig,
			() -> {
				batchEngineTaskItemWriter.write(
					items, batchEngineTaskOperation);

				return null;
			});
	}

	private void _execute(BatchEngineTask batchEngineTask) throws Throwable {
		List<T> items = new ArrayList<>();

		T item = null;

		Blob content = batchEngineTask.getContent();

		BatchEngineTaskItemWriter<T> batchEngineTaskItemWriter =
			_batchEngineTaskItemWriterRegistry.get(
				batchEngineTask.getClassName(), batchEngineTask.getVersion());

		BatchEngineTaskOperation batchEngineTaskOperation =
			BatchEngineTaskOperation.valueOf(batchEngineTask.getOperation());

		try (BatchEngineTaskItemReader<T> batchEngineTaskItemReader =
				BatchEngineTaskItemReaderFactory.create(
					BatchEngineTaskContentType.valueOf(
						batchEngineTask.getContentType()),
					content.getBinaryStream(), _itemClass)) {

			while ((item = batchEngineTaskItemReader.read()) != null) {
				if (items.size() < batchEngineTask.getBatchSize()) {
					items.add(item);
				}

				if (items.size() == batchEngineTask.getBatchSize()) {
					_commitItems(
						batchEngineTaskItemWriter, items,
						batchEngineTaskOperation);

					items.clear();
				}
			}

			if (!items.isEmpty()) {
				_commitItems(
					batchEngineTaskItemWriter, items, batchEngineTaskOperation);
			}
		}

		if (!items.isEmpty()) {
			_commitItems(
				batchEngineTaskItemWriter, items, batchEngineTaskOperation);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		BatchEngineTaskExecutorImpl.class);

	private static final TransactionConfig _transactionConfig =
		TransactionConfig.Factory.create(
			Propagation.REQUIRES_NEW, new Class<?>[] {Exception.class});

	private final BatchEngineTaskItemWriterRegistry
		_batchEngineTaskItemWriterRegistry;
	private final BatchEngineTaskLocalService _batchEngineTaskLocalService;
	private final Class<T> _itemClass;

}