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

import com.liferay.batch.engine.BatchEngineTaskExecutor;
import com.liferay.batch.engine.BatchEngineTaskExecutorFactory;
import com.liferay.batch.engine.service.BatchEngineTaskLocalService;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Shuyang Zhou
 */
@Component(service = BatchEngineTaskExecutorFactory.class)
public class BatchEngineTaskExecutorFactoryImpl
	implements BatchEngineTaskExecutorFactory {

	@Override
	public BatchEngineTaskExecutor create(Class<?> domainClass) {
		return new BatchEngineTaskExecutorImpl<>(
			domainClass, _batchEngineTaskItemWriterRegistry,
			_batchEngineTaskLocalService);
	}

	@Reference
	private BatchEngineTaskItemWriterRegistry
		_batchEngineTaskItemWriterRegistry;

	@Reference
	private BatchEngineTaskLocalService _batchEngineTaskLocalService;

}