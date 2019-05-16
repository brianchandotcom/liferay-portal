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

package com.liferay.batch.engine.core.internal;

import com.liferay.batch.engine.BatchStatus;
import com.liferay.batch.engine.core.BatchJob;
import com.liferay.batch.engine.core.BatchJobExecutionListener;
import com.liferay.batch.engine.core.item.BatchItemReader;
import com.liferay.batch.engine.core.item.BatchItemStream;
import com.liferay.batch.engine.core.item.BatchItemWriter;
import com.liferay.batch.engine.core.item.configuration.BatchItemReaderFactory;
import com.liferay.batch.engine.core.item.configuration.BatchItemWriterFactory;
import com.liferay.batch.engine.model.BatchJobExecution;
import com.liferay.batch.engine.service.BatchJobExecutionLocalService;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.TransactionConfig;
import com.liferay.portal.kernel.transaction.TransactionInvokerUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Callable;

/**
 * @author Ivica Cardic
 */
public class BatchJobImpl<T> implements BatchJob {

	public BatchJobImpl(
		BatchJobExecutionLocalService batchJobExecutionLocalService) {

		_batchJobExecutionLocalService = batchJobExecutionLocalService;
	}

	@Override
	public void execute(BatchJobExecution batchJobExecution) {
		Objects.requireNonNull(batchJobExecution);

		if (_log.isDebugEnabled()) {
			_log.debug(
				String.format(
					"Batch Job execution %s starting", batchJobExecution));
		}

		try {
			_invokeTransaction(() -> _beforeExecution(batchJobExecution));

			_execute(batchJobExecution);

			_invokeTransaction(() -> _afterExecution(batchJobExecution));

			if (_log.isDebugEnabled()) {
				_log.debug(
					String.format(
						"Batch Job execution %s complete", batchJobExecution));
			}
		}
		catch (Exception e) {
			batchJobExecution.setError(e.getMessage());

			try {
				_invokeTransaction(() -> _handleFailure(batchJobExecution, e));
			}
			catch (Exception e1) {
				_log.error(e1.getMessage(), e1);
			}
		}
	}

	@Override
	public String getName() {
		return _name;
	}

	@Override
	public int getWriteInterval() {
		return _writeInterval;
	}

	@Override
	public boolean isRestartable() {
		return _restartable;
	}

	public void setBatchItemReaderFactory(
		BatchItemReaderFactory<T> batchItemReaderFactory) {

		_batchItemReaderFactory = batchItemReaderFactory;
	}

	public void setBatchItemWriterFactory(
		BatchItemWriterFactory<T> batchItemWriterFactory) {

		_batchItemWriterFactory = batchItemWriterFactory;
	}

	public void setJobExecutionListeners(
		List<BatchJobExecutionListener> batchJobExecutionListeners) {

		_batchJobExecutionListeners.addAll(batchJobExecutionListeners);
	}

	public void setName(String name) {
		_name = name;
	}

	public void setRestartable(boolean restartable) {
		_restartable = restartable;
	}

	public void setWriteInterval(int writeInterval) {
		_writeInterval = writeInterval;
	}

	private Void _afterExecution(BatchJobExecution batchJobExecution) {
		_finishBatchJobExecution(batchJobExecution, BatchStatus.COMPLETED);

		_executeAfterJobListeners(batchJobExecution);

		return null;
	}

	private Void _beforeExecution(BatchJobExecution batchJobExecution) {
		_executeBeforeJobListeners(batchJobExecution);

		_startBatchJobExecution(batchJobExecution);

		return null;
	}

	private Void _commitItems(BatchItemWriter<T> batchItemWriter, List<T> items)
		throws Exception {

		batchItemWriter.write(items);

		return null;
	}

	private Void _execute(BatchJobExecution batchJobExecution)
		throws Exception {

		Objects.requireNonNull(batchJobExecution);

		final List<T> items = new ArrayList<>();

		T item = null;

		BatchItemReader<T> batchItemReader = _batchItemReaderFactory.create(
			batchJobExecution.getJobSettingsProperties());
		BatchItemWriter<T> batchItemWriter = _batchItemWriterFactory.create(
			batchJobExecution.getJobSettingsProperties());

		if (batchItemReader instanceof BatchItemStream) {
			((BatchItemStream)batchItemReader).open(
				batchJobExecution.getJobSettingsProperties());
		}

		while ((item = batchItemReader.read()) != null) {
			if (items.size() < _writeInterval) {
				items.add(item);
			}
			else {
				_invokeTransaction(() -> _commitItems(batchItemWriter, items));

				items.clear();
			}
		}

		if (batchItemReader instanceof BatchItemStream) {
			((BatchItemStream)batchItemReader).close();
		}

		if (!items.isEmpty()) {
			_invokeTransaction(() -> _commitItems(batchItemWriter, items));
		}

		return null;
	}

	private void _executeAfterJobListeners(
		BatchJobExecution batchJobExecution) {

		for (BatchJobExecutionListener batchJobExecutionListener :
				_batchJobExecutionListeners) {

			batchJobExecutionListener.afterJob(batchJobExecution);
		}
	}

	private void _executeBeforeJobListeners(
		BatchJobExecution batchJobExecution) {

		for (BatchJobExecutionListener batchJobExecutionListener :
				_batchJobExecutionListeners) {

			batchJobExecutionListener.beforeJob(batchJobExecution);
		}
	}

	private void _finishBatchJobExecution(
		BatchJobExecution batchJobExecution, BatchStatus batchStatus) {

		batchJobExecution.setStatus(batchStatus.toString());
		batchJobExecution.setEndTime(new Date());

		_batchJobExecutionLocalService.updateBatchJobExecution(
			batchJobExecution);
	}

	private Void _handleFailure(
		BatchJobExecution batchJobExecution, Exception e) {

		_finishBatchJobExecution(batchJobExecution, BatchStatus.FAILED);

		_log.error(
			String.format("Batch Job execution %s failed", batchJobExecution),
			e);

		_executeAfterJobListeners(batchJobExecution);

		return null;
	}

	private void _invokeTransaction(Callable<Void> callable) throws Exception {
		try {
			TransactionInvokerUtil.invoke(_transactionConfig, callable);
		}
		catch (Throwable t) {
			throw new Exception(t);
		}
	}

	private void _startBatchJobExecution(BatchJobExecution batchJobExecution) {
		batchJobExecution.setStatus(BatchStatus.STARTED.toString());
		batchJobExecution.setStartTime(new Date());

		_batchJobExecutionLocalService.updateBatchJobExecution(
			batchJobExecution);
	}

	private static final Log _log = LogFactoryUtil.getLog(BatchJobImpl.class);

	private static final TransactionConfig _transactionConfig =
		TransactionConfig.Factory.create(
			Propagation.REQUIRES_NEW, new Class<?>[] {Exception.class});

	private BatchItemReaderFactory<T> _batchItemReaderFactory;
	private BatchItemWriterFactory<T> _batchItemWriterFactory;
	private final List<BatchJobExecutionListener> _batchJobExecutionListeners =
		new ArrayList<>();
	private final BatchJobExecutionLocalService _batchJobExecutionLocalService;
	private String _name;
	private boolean _restartable;
	private int _writeInterval;

}