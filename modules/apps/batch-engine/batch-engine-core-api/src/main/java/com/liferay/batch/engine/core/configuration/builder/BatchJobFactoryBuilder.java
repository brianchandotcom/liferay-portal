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

package com.liferay.batch.engine.core.configuration.builder;

import com.liferay.batch.engine.core.BatchJobExecutionListener;
import com.liferay.batch.engine.core.configuration.BatchJobFactory;
import com.liferay.batch.engine.core.item.BatchItemReader;
import com.liferay.batch.engine.core.item.BatchItemWriter;
import com.liferay.batch.engine.core.item.configuration.BatchItemReaderFactory;
import com.liferay.batch.engine.core.item.configuration.BatchItemWriterFactory;

/**
 * @author Ivica Cardic
 */
public interface BatchJobFactoryBuilder {

	public BatchJobFactoryBuilder addBatchJobExecutionListener(
		BatchJobExecutionListener batchJobExecutionListener);

	public BatchJobFactory build();

	public BatchJobFactoryBuilder setBatchItemReader(
		BatchItemReader batchItemReader);

	public BatchJobFactoryBuilder setBatchItemReaderFactory(
		BatchItemReaderFactory batchItemReaderFactory);

	public BatchJobFactoryBuilder setBatchItemWriter(
		BatchItemWriter batchItemWriter);

	public BatchJobFactoryBuilder setBatchItemWriterFactory(
		BatchItemWriterFactory batchItemWriterFactory);

	public BatchJobFactoryBuilder setRestartable(boolean restartable);

	public BatchJobFactoryBuilder setWriteInterval(int writeInterval);

}