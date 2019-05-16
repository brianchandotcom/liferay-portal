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

package com.liferay.batch.engine.core.internal.configuration.builder;

import com.liferay.batch.engine.core.BatchJob;
import com.liferay.batch.engine.core.BatchJobExecutionListener;
import com.liferay.batch.engine.core.configuration.BatchJobFactory;
import com.liferay.batch.engine.core.configuration.builder.BatchJobFactoryBuilder;
import com.liferay.batch.engine.core.internal.BatchJobImpl;
import com.liferay.batch.engine.core.item.BatchItemReader;
import com.liferay.batch.engine.core.item.BatchItemWriter;
import com.liferay.batch.engine.core.item.configuration.BatchItemReaderFactory;
import com.liferay.batch.engine.core.item.configuration.BatchItemWriterFactory;
import com.liferay.batch.engine.service.BatchJobExecutionLocalService;
import com.liferay.portal.kernel.util.UnicodeProperties;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author Ivica Cardic
 */
public class BatchJobFactoryBuilderImpl implements BatchJobFactoryBuilder {

	public BatchJobFactoryBuilderImpl(String jobName) {
		_jobName = jobName;
	}

	@Override
	public BatchJobFactoryBuilder addBatchJobExecutionListener(
		BatchJobExecutionListener batchJobExecutionListener) {

		_batchJobExecutionListeners.add(batchJobExecutionListener);

		return this;
	}

	@Override
	public BatchJobFactory build() {
		Objects.requireNonNull(_jobName);
		Objects.requireNonNull(_batchItemReaderFactory);
		Objects.requireNonNull(_batchItemWriterFactory);

		return new BatchJobFactoryImpl();
	}

	@Override
	public BatchJobFactoryBuilder setBatchItemReader(
		BatchItemReader batchItemReader) {

		_batchItemReaderFactory = new ReferenceBatchItemReaderFactory(
			batchItemReader);

		return this;
	}

	@Override
	public BatchJobFactoryBuilder setBatchItemReaderFactory(
		BatchItemReaderFactory batchItemReaderFactory) {

		_batchItemReaderFactory = batchItemReaderFactory;

		return this;
	}

	@Override
	public BatchJobFactoryBuilder setBatchItemWriter(
		BatchItemWriter batchItemWriter) {

		_batchItemWriterFactory = new ReferenceBatchItemWriterFactory(
			batchItemWriter);

		return this;
	}

	@Override
	public BatchJobFactoryBuilder setBatchItemWriterFactory(
		BatchItemWriterFactory batchItemWriterFactory) {

		_batchItemWriterFactory = batchItemWriterFactory;

		return this;
	}

	public BatchJobFactoryBuilder setBatchJobExecutionLocalService(
		BatchJobExecutionLocalService batchJobExecutionLocalService) {

		_batchJobExecutionLocalService = batchJobExecutionLocalService;

		return this;
	}

	@Override
	public BatchJobFactoryBuilder setRestartable(boolean restartable) {
		_restartable = restartable;

		return this;
	}

	@Override
	public BatchJobFactoryBuilder setWriteInterval(int writeInterval) {
		_writeInterval = writeInterval;

		return this;
	}

	private BatchItemReaderFactory _batchItemReaderFactory;
	private BatchItemWriterFactory _batchItemWriterFactory;
	private final List<BatchJobExecutionListener> _batchJobExecutionListeners =
		new ArrayList<>();
	private BatchJobExecutionLocalService _batchJobExecutionLocalService;
	private final String _jobName;
	private boolean _restartable = true;
	private int _writeInterval = 100;

	private class BatchJobFactoryImpl implements BatchJobFactory {

		@Override
		@SuppressWarnings("unchecked")
		public BatchJob create() {
			BatchJobImpl batchJobImpl = new BatchJobImpl<>(
				_batchJobExecutionLocalService);

			batchJobImpl.setBatchItemReaderFactory(_batchItemReaderFactory);
			batchJobImpl.setBatchItemWriterFactory(_batchItemWriterFactory);
			batchJobImpl.setName(_jobName);
			batchJobImpl.setJobExecutionListeners(_batchJobExecutionListeners);
			batchJobImpl.setRestartable(_restartable);
			batchJobImpl.setWriteInterval(_writeInterval);

			return batchJobImpl;
		}

		@Override
		public String getJobName() {
			return _jobName;
		}

	}

	private class ReferenceBatchItemReaderFactory
		implements BatchItemReaderFactory {

		public ReferenceBatchItemReaderFactory(
			BatchItemReader batchItemReader) {

			_batchItemReader = batchItemReader;
		}

		@Override
		public BatchItemReader create(UnicodeProperties jobSettingsProperties) {
			return _batchItemReader;
		}

		private final BatchItemReader _batchItemReader;

	}

	private class ReferenceBatchItemWriterFactory
		implements BatchItemWriterFactory {

		public ReferenceBatchItemWriterFactory(
			BatchItemWriter batchItemWriter) {

			_batchItemWriter = batchItemWriter;
		}

		@Override
		public BatchItemWriter create(UnicodeProperties jobSettingsProperties) {
			return _batchItemWriter;
		}

		private final BatchItemWriter _batchItemWriter;

	}

}