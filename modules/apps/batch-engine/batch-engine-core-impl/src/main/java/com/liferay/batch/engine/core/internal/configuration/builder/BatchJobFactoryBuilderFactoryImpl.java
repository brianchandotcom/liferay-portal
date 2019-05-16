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

import com.liferay.batch.engine.core.configuration.BatchJobFactoryBuilderFactory;
import com.liferay.batch.engine.core.configuration.builder.BatchJobFactoryBuilder;
import com.liferay.batch.engine.service.BatchJobExecutionLocalService;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Ivica Cardic
 */
@Component(service = BatchJobFactoryBuilderFactory.class)
public class BatchJobFactoryBuilderFactoryImpl
	implements BatchJobFactoryBuilderFactory {

	@Override
	public BatchJobFactoryBuilder get(String jobName) {
		return new BatchJobFactoryBuilderImpl(
			jobName
		).setBatchJobExecutionLocalService(
			_batchJobExecutionLocalService
		);
	}

	@Reference
	private BatchJobExecutionLocalService _batchJobExecutionLocalService;

}