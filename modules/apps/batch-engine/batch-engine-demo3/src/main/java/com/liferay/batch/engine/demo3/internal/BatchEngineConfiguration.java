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

package com.liferay.batch.engine.demo3.internal;

import com.liferay.batch.engine.BatchStatus;
import com.liferay.batch.engine.core.BatchJob;
import com.liferay.batch.engine.core.configuration.BatchJobFactory;
import com.liferay.batch.engine.core.configuration.BatchJobFactoryBuilderFactory;
import com.liferay.batch.engine.core.configuration.builder.BatchJobFactoryBuilder;
import com.liferay.batch.engine.core.item.BatchItemWriter;
import com.liferay.batch.engine.core.item.configuration.BatchItemReaderFactory;
import com.liferay.batch.engine.core.item.configuration.BatchItemReaderFactoryBuilderFactory;
import com.liferay.batch.engine.core.item.file.builder.FlatFileBatchItemReaderFactoryBuilder;
import com.liferay.batch.engine.core.item.json.builder.JSONBatchItemReaderFactoryBuilder;
import com.liferay.batch.engine.core.item.xls.builder.XLSBatchItemReaderFactoryBuilder;
import com.liferay.batch.engine.core.launch.BatchJobLauncher;
import com.liferay.batch.engine.demo3.internal.model.Product;
import com.liferay.batch.engine.demo3.internal.model.Sku;
import com.liferay.batch.engine.exception.JobExecutionAlreadyRunningException;
import com.liferay.batch.engine.exception.JobInstanceAlreadyCompleteException;
import com.liferay.batch.engine.exception.JobRestartException;
import com.liferay.batch.engine.model.BatchJobExecution;
import com.liferay.batch.engine.service.BatchJobExecutionLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.UnicodeProperties;

import java.io.InputStream;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Ivica Cardic
 */
@Component(immediate = true, service = {})
public class BatchEngineConfiguration {

	@Activate
	public void activate() throws Exception {
		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setCompanyId(_portal.getDefaultCompanyId());

		ServiceContextThreadLocal.pushServiceContext(serviceContext);

		try {
			_runBatchJob(
				"importProductJSONJob", Product.class,
				_getJSONBatchItemReaderFactory(
					Product.class, "dependencies/json/Product.json"));
			_runBatchJob(
				"importSkuJSONJob", Sku.class,
				_getJSONBatchItemReaderFactory(
					Sku.class, "dependencies/json/Sku.json"));

			_runBatchJob(
				"importProductCSVJob", Product.class,
				_getFlatFileBatchItemReaderFactory(
					Product.class, "dependencies/csv/Product.csv"));

			_runBatchJob(
				"importProductXLSXJob", Product.class,
				_getXLSXBatchItemReaderFactory(
					Product.class, "dependencies/xlsx/Product.xlsx"));
		}
		finally {
			ServiceContextThreadLocal.popServiceContext();
		}
	}

	private BatchItemWriter _getBatchItemWriter(Class domainClass) {
		return items -> items.forEach(
			item -> System.out.println(domainClass + ":" + item));
	}

	private InputStream _getFileResource(String fileName) {
		return BatchEngineConfiguration.class.getResourceAsStream(fileName);
	}

	private BatchItemReaderFactory _getFlatFileBatchItemReaderFactory(
		Class domainClass, String fileName) {

		FlatFileBatchItemReaderFactoryBuilder
			flatFileBatchItemReaderFactoryBuilder =
				_batchItemReaderFactoryBuilderFactory.get(
					FlatFileBatchItemReaderFactoryBuilder.class);

		return flatFileBatchItemReaderFactoryBuilder.setColumnNames(
			"id", "name_en", "name_hr"
		).setItemType(
			domainClass
		).setLinesToSkip(
			1
		).setResource(
			jobSettings -> _getFileResource(fileName)
		).build();
	}

	private BatchItemReaderFactory _getJSONBatchItemReaderFactory(
		Class domainClass, String fileName) {

		JSONBatchItemReaderFactoryBuilder jsonBatchItemReaderFactoryBuilder =
			_batchItemReaderFactoryBuilderFactory.get(
				JSONBatchItemReaderFactoryBuilder.class);

		return jsonBatchItemReaderFactoryBuilder.setItemType(
			domainClass
		).setResource(
			jobSettings -> _getFileResource(fileName)
		).build();
	}

	private BatchItemReaderFactory _getXLSXBatchItemReaderFactory(
		Class domainClass, String fileName) {

		XLSBatchItemReaderFactoryBuilder xlsBatchItemReaderFactoryBuilder =
			_batchItemReaderFactoryBuilderFactory.get(
				XLSBatchItemReaderFactoryBuilder.class);

		return xlsBatchItemReaderFactoryBuilder.setColumnNames(
			"id", "name"
		).setItemType(
			domainClass
		).setLinesToSkip(
			1
		).setResource(
			jobSettings -> _getFileResource(fileName)
		).build();
	}

	private void _runBatchJob(
			String jobName, Class domainClass,
			BatchItemReaderFactory batchItemReaderFactory)
		throws JobExecutionAlreadyRunningException,
			   JobInstanceAlreadyCompleteException, JobRestartException {

		BatchJobExecution lastBatchJobExecution =
			_batchJobExecutionLocalService.fetchLastBatchJobExecution(
				jobName, new UnicodeProperties());

		if (lastBatchJobExecution != null) {
			if (BatchStatus.valueOf(lastBatchJobExecution.getStatus()) ==
					BatchStatus.COMPLETED) {

				return;
			}
		}

		BatchJobFactoryBuilder batchJobFactoryBuilder =
			_batchJobFactoryBuilderFactory.get(jobName);

		BatchJobFactory batchJobFactory =
			batchJobFactoryBuilder.setBatchItemReaderFactory(
				batchItemReaderFactory
			).setBatchItemWriter(
				_getBatchItemWriter(domainClass)
			).build();

		BatchJob batchJob = batchJobFactory.create();

		_batchJobLauncher.run(batchJob, new UnicodeProperties());
	}

	@Reference
	private BatchItemReaderFactoryBuilderFactory
		_batchItemReaderFactoryBuilderFactory;

	@Reference
	private BatchJobExecutionLocalService _batchJobExecutionLocalService;

	@Reference
	private BatchJobFactoryBuilderFactory _batchJobFactoryBuilderFactory;

	@Reference
	private BatchJobLauncher _batchJobLauncher;

	@Reference
	private Portal _portal;

}