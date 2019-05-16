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

import com.liferay.batch.engine.core.BatchJob;
import com.liferay.batch.engine.core.configuration.BatchJobFactory;
import com.liferay.batch.engine.core.configuration.BatchJobRegistry;
import com.liferay.batch.engine.core.configuration.NoSuchJobException;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

import org.osgi.service.component.annotations.Component;

/**
 * @author Ivica Cardic
 */
@Component(service = BatchJobRegistry.class)
public class BatchJobRegistryImpl implements BatchJobRegistry {

	@Override
	public BatchJob getBatchJob(String jobName) throws NoSuchJobException {
		Objects.requireNonNull(jobName);

		BatchJobFactory batchJobFactory = _batchJobFactoriesMap.get(
			StringUtil.toUpperCase(jobName));

		if (batchJobFactory == null) {
			throw new NoSuchJobException(
				String.format("Batch job %s is not registered", jobName));
		}

		return batchJobFactory.create();
	}

	@Override
	public void register(BatchJobFactory batchJobFactory) {
		_batchJobFactoriesMap.put(
			StringUtil.toUpperCase(batchJobFactory.getJobName()),
			batchJobFactory);
	}

	@Override
	public void unregister(String jobName) {
		_batchJobFactoriesMap.remove(StringUtil.toUpperCase(jobName));
	}

	private final Map<String, BatchJobFactory> _batchJobFactoriesMap =
		new ConcurrentHashMap<>();

}