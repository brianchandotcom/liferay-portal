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

package com.liferay.batch.engine.fileimport;

import com.liferay.batch.engine.BatchFileImportOperation;

import org.osgi.service.component.ComponentFactory;

/**
 * @author Ivica Cardic
 */
public class BatchJobConfiguration {

	public BatchJobConfiguration(
		Class domainClass, String version,
		BatchFileImportOperation batchFileImportOperation,
		BatchFileImportType batchFileImportType,
		ComponentFactory batchItemWriterComponentFactory) {

		_domainClass = domainClass;
		_version = version;
		_batchFileImportOperation = batchFileImportOperation;
		_batchFileImportType = batchFileImportType;
		_batchItemWriterComponentFactory = batchItemWriterComponentFactory;
	}

	public BatchFileImportOperation getBatchFileImportOperation() {
		return _batchFileImportOperation;
	}

	public BatchFileImportType getBatchFileImportType() {
		return _batchFileImportType;
	}

	public ComponentFactory getBatchItemWriterComponentFactory() {
		return _batchItemWriterComponentFactory;
	}

	public Class getDomainClass() {
		return _domainClass;
	}

	public String getTypeName() {
		return _domainClass.getSimpleName();
	}

	public String getVersion() {
		return _version;
	}

	private final BatchFileImportOperation _batchFileImportOperation;
	private final BatchFileImportType _batchFileImportType;
	private final ComponentFactory _batchItemWriterComponentFactory;
	private final Class _domainClass;
	private final String _version;

}