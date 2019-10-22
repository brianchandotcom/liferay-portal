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

package com.liferay.talend.runtime;

import com.liferay.talend.properties.batch.LiferayBatchFileProperties;
import com.liferay.talend.runtime.writer.LiferayBatchFileWriteOperation;

import java.io.File;

import java.util.List;

import org.apache.avro.Schema;

import org.talend.components.api.component.runtime.Sink;
import org.talend.components.api.component.runtime.WriteOperation;
import org.talend.components.api.container.RuntimeContainer;
import org.talend.components.api.properties.ComponentProperties;
import org.talend.daikon.NamedThing;
import org.talend.daikon.properties.ValidationResult;

/**
 * @author Igor Beslic
 */
public class LiferayBatchFileSink implements Sink {

	@Override
	public WriteOperation<?> createWriteOperation() {
		return new LiferayBatchFileWriteOperation(this);
	}

	@Override
	public Schema getEndpointSchema(
		RuntimeContainer container, String schemaName) {

		return null;
	}

	public LiferayBatchFileProperties getLiferayBatchFileProperties() {
		return _liferayBatchFileProperties;
	}

	@Override
	public List<NamedThing> getSchemaNames(RuntimeContainer container) {
		return null;
	}

	@Override
	public ValidationResult initialize(
		RuntimeContainer container, ComponentProperties properties) {

		_liferayBatchFileProperties = (LiferayBatchFileProperties)properties;

		return ValidationResult.OK;
	}

	@Override
	public ValidationResult validate(RuntimeContainer container) {
		String batchFilePath = _liferayBatchFileProperties.getBatchFilePath();

		if ((batchFilePath == null) || batchFilePath.isEmpty()) {
			return _getErrorValidationResult(
				"Please set a valid value for \"Bulk File Path\" field");
		}

		File file = new File(batchFilePath);

		File parentFile = file.getParentFile();

		if (!parentFile.exists()) {
			try {
				parentFile.mkdirs();
			}
			catch (SecurityException se) {
				return _getErrorValidationResult(
					"Unable to create batch file due security settings for " +
						parentFile.getAbsolutePath());
			}
		}

		if (!parentFile.canWrite()) {
			return _getErrorValidationResult(
				"Please set write permissions for file path " +
					file.getAbsolutePath());
		}

		return ValidationResult.OK;
	}

	private ValidationResult _getErrorValidationResult(String message) {
		return new ValidationResult(ValidationResult.Result.ERROR, message);
	}

	private LiferayBatchFileProperties _liferayBatchFileProperties;

}