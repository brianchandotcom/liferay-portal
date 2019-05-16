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

package com.liferay.batch.engine.demo1.internal.v1_0;

import com.liferay.batch.engine.BatchFileImportOperation;
import com.liferay.batch.engine.demo1.internal.v1_0.model.Product;
import com.liferay.batch.engine.demo1.internal.v1_0.model.Sku;
import com.liferay.batch.engine.fileimport.BatchFileImportType;
import com.liferay.batch.engine.fileimport.BatchJobConfiguration;
import com.liferay.batch.engine.fileimport.configuration.BatchFileImportConfigurator;

import java.util.ArrayList;
import java.util.List;

import org.osgi.service.component.ComponentFactory;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Ivica Cardic
 */
@Component(immediate = true, service = {})
public class BatchFileImportConfiguration {

	@Activate
	public void activate() {
		_initBatchJobConfigurations();

		_batchFileImportConfigurator.register(_batchJobConfigurations);
	}

	@Deactivate
	public void deactivate() {
		_batchFileImportConfigurator.unregister(_batchJobConfigurations);
	}

	private void _initBatchJobConfigurations() {
		for (Class domainClass : _domainClasses) {
			for (BatchFileImportOperation batchFileImportOperation :
					BatchFileImportOperation.values()) {

				for (BatchFileImportType batchFileImportType :
						BatchFileImportType.values()) {

					_batchJobConfigurations.add(
						new BatchJobConfiguration(
							domainClass, _VERSION, batchFileImportOperation,
							batchFileImportType,
							_batchItemWriterComponentFactory));
				}
			}
		}
	}

	private static final String _VERSION = "v1.0";

	@Reference
	private BatchFileImportConfigurator _batchFileImportConfigurator;

	@Reference(target = "(component.factory=Demo1BatchFileImportWriter_v1_0)")
	private ComponentFactory _batchItemWriterComponentFactory;

	private final List<BatchJobConfiguration> _batchJobConfigurations =
		new ArrayList<>();
	private final List<Class> _domainClasses = new ArrayList<Class>() {
		{
			add(Product.class);
			add(Sku.class);
		}
	};

}