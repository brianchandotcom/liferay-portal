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

package com.liferay.batch.engine.fileimport.internal.configuration;

import com.liferay.batch.engine.core.BatchJobExecutionListener;
import com.liferay.batch.engine.core.configuration.BatchJobFactoryBuilderFactory;
import com.liferay.batch.engine.core.configuration.BatchJobRegistry;
import com.liferay.batch.engine.core.configuration.builder.BatchJobFactoryBuilder;
import com.liferay.batch.engine.core.item.BatchItemWriter;
import com.liferay.batch.engine.core.item.configuration.BatchItemReaderFactory;
import com.liferay.batch.engine.core.item.configuration.BatchItemReaderFactoryBuilderFactory;
import com.liferay.batch.engine.core.item.file.builder.FlatFileBatchItemReaderFactoryBuilder;
import com.liferay.batch.engine.core.item.io.Resource;
import com.liferay.batch.engine.core.item.json.builder.JSONBatchItemReaderFactoryBuilder;
import com.liferay.batch.engine.core.item.xls.builder.XLSBatchItemReaderFactoryBuilder;
import com.liferay.batch.engine.fileimport.BatchFileImportType;
import com.liferay.batch.engine.fileimport.BatchJobConfiguration;
import com.liferay.batch.engine.fileimport.configuration.BatchFileImportConfigurator;
import com.liferay.batch.engine.fileimport.configuration.BatchFileImportJobNameMapper;
import com.liferay.batch.engine.fileimport.writer.BaseBatchFileImportWriter;

import java.util.List;

import org.osgi.service.component.ComponentFactory;
import org.osgi.service.component.ComponentInstance;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Ivica Cardic
 */
@Component(service = BatchFileImportConfigurator.class)
public class BatchFileImportConfiguratorImpl
	implements BatchFileImportConfigurator {

	@Override
	public void register(List<BatchJobConfiguration> batchJobConfigurations) {
		for (BatchJobConfiguration batchJobConfiguration :
				batchJobConfigurations) {

			BatchJobFactoryBuilder batchJobFactoryBuilder =
				_batchJobFactoryBuilderFactory.get(
					_getJobName(batchJobConfiguration));

			_batchJobRegistry.register(
				batchJobFactoryBuilder.setBatchItemReaderFactory(
					_getBatchItemReaderFactory(
						batchJobConfiguration.getDomainClass(),
						batchJobConfiguration.getBatchFileImportType())
				).addBatchJobExecutionListener(
					_callbackURLBatchJobExecutionListener
				).addBatchJobExecutionListener(
					_updateStatusBatchJobExecutionListener
				).setBatchItemWriterFactory(
					jobSettings -> _getBatchItemWriterFactory(
						batchJobConfiguration)
				).build());
		}
	}

	@Override
	public void unregister(List<BatchJobConfiguration> batchJobConfigurations) {
		for (BatchJobConfiguration batchJobConfiguration :
				batchJobConfigurations) {

			_batchJobRegistry.unregister(_getJobName(batchJobConfiguration));
		}
	}

	private BatchItemReaderFactory _getBatchItemReaderFactory(
		Class domainClass, BatchFileImportType type) {

		if (type == BatchFileImportType.CSV) {
			FlatFileBatchItemReaderFactoryBuilder
				flatFileBatchItemReaderFactoryBuilder =
					_batchItemReaderFactoryBuilderFactory.get(
						FlatFileBatchItemReaderFactoryBuilder.class);

			return flatFileBatchItemReaderFactoryBuilder.setItemType(
				domainClass
			).setLinesToSkip(
				1
			).setResource(
				_dlResource
			).build();
		}
		else if (type == BatchFileImportType.JSON) {
			JSONBatchItemReaderFactoryBuilder
				jsonBatchItemReaderFactoryBuilder =
					_batchItemReaderFactoryBuilderFactory.get(
						JSONBatchItemReaderFactoryBuilder.class);

			return jsonBatchItemReaderFactoryBuilder.setItemType(
				domainClass
			).setResource(
				_dlResource
			).build();
		}
		else {
			XLSBatchItemReaderFactoryBuilder xlsBatchItemReaderFactoryBuilder =
				_batchItemReaderFactoryBuilderFactory.get(
					XLSBatchItemReaderFactoryBuilder.class);

			return xlsBatchItemReaderFactoryBuilder.setItemType(
				domainClass
			).setLinesToSkip(
				1
			).setResource(
				_dlResource
			).build();
		}
	}

	@SuppressWarnings("unchecked")
	private BatchItemWriter _getBatchItemWriterFactory(
		BatchJobConfiguration batchJobConfiguration) {

		ComponentFactory componentFactory =
			batchJobConfiguration.getBatchItemWriterComponentFactory();

		ComponentInstance componentInstance = componentFactory.newInstance(
			null);

		BaseBatchFileImportWriter batchFileImportWriter =
			(BaseBatchFileImportWriter)componentInstance.getInstance();

		batchFileImportWriter.setBatchFileImportOperation(
			batchJobConfiguration.getBatchFileImportOperation());
		batchFileImportWriter.setDomainClass(
			batchJobConfiguration.getDomainClass());
		batchFileImportWriter.setVersion(batchJobConfiguration.getVersion());

		return items -> {
			try {
				batchFileImportWriter.write(items);
			}
			finally {
				componentInstance.dispose();
			}
		};
	}

	private String _getJobName(BatchJobConfiguration batchJobConfiguration) {
		return _batchFileImportJobNameMapper.getJobName(
			batchJobConfiguration.getTypeName(),
			batchJobConfiguration.getVersion(),
			batchJobConfiguration.getBatchFileImportType(),
			batchJobConfiguration.getBatchFileImportOperation());
	}

	@Reference
	private BatchFileImportJobNameMapper _batchFileImportJobNameMapper;

	@Reference
	private BatchItemReaderFactoryBuilderFactory
		_batchItemReaderFactoryBuilderFactory;

	@Reference
	private BatchJobFactoryBuilderFactory _batchJobFactoryBuilderFactory;

	@Reference
	private BatchJobRegistry _batchJobRegistry;

	@Reference(target = "(component.name=CallbackURLBatchJobExecutionListener)")
	private BatchJobExecutionListener _callbackURLBatchJobExecutionListener;

	@Reference(target = "(component.name=DLResource)")
	private Resource _dlResource;

	@Reference(
		target = "(component.name=UpdateBatchFileImportStatusBatchJobExecutionListener)"
	)
	private BatchJobExecutionListener _updateStatusBatchJobExecutionListener;

}