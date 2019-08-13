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

import com.liferay.batch.engine.BatchTaskExecutor;
import com.liferay.batch.engine.BatchTaskExecutorFactory;
import com.liferay.batch.engine.service.BatchTaskLocalService;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Shuyang Zhou
 */
@Component(service = BatchTaskExecutorFactory.class)
public class BatchTaskExecutorFactoryImpl implements BatchTaskExecutorFactory {

	@Override
	public BatchTaskExecutor create(Class<?> domainClass) {
		return new BatchTaskExecutorImpl<>(
			domainClass, _batchItemWriterRegistry, _batchTaskLocalService);
	}

	@Reference
	private BatchItemWriterRegistry _batchItemWriterRegistry;

	@Reference
	private BatchTaskLocalService _batchTaskLocalService;

}