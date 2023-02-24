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

package com.liferay.batch.engine.internal.writer;

import com.liferay.batch.engine.BatchEngineTaskContentType;
import com.liferay.batch.engine.internal.auto.deploy.BatchEngineAutoDeployListener;

import java.io.OutputStream;
import java.io.Serializable;

import java.lang.reflect.Field;

import java.util.List;
import java.util.Map;

/**
 * @author Ivica Cardic
 * @author Igor Beslic
 */
public class BatchEngineExportTaskItemWriterBuilder {

	public BatchEngineExportTaskItemWriterBuilder batchEngineTaskContentType(
		BatchEngineTaskContentType batchEngineTaskContentType) {

		_batchEngineTaskContentType = batchEngineTaskContentType;

		return this;
	}

	public BatchEngineExportTaskItemWriter build() throws Exception {
		Map<String, Field> fieldsMap = ItemClassIndexUtil.index(_itemClass);

		if (_batchEngineTaskContentType == BatchEngineTaskContentType.CSV) {
			return new CSVBatchEngineExportTaskItemWriterImpl(
				_csvFileColumnDelimiter, fieldsMap, _fieldNames, _outputStream,
				_parameters);
		}

		if (_batchEngineTaskContentType == BatchEngineTaskContentType.JSON) {
			return new JSONBatchEngineExportTaskItemWriterImpl(
				fieldsMap.keySet(), _fieldNames, _outputStream);
		}

		if (_batchEngineTaskContentType == BatchEngineTaskContentType.JSONL) {
			return new JSONLBatchEngineExportTaskItemWriterImpl(
				fieldsMap.keySet(), _fieldNames, _outputStream);
		}

		if ((_batchEngineTaskContentType == BatchEngineTaskContentType.XLS) ||
			(_batchEngineTaskContentType == BatchEngineTaskContentType.XLSX)) {

			return new XLSBatchEngineExportTaskItemWriterImpl(
				fieldsMap, _fieldNames, _outputStream);
		}

		if (_batchEngineTaskContentType == BatchEngineTaskContentType.JSONT) {
			BatchEngineAutoDeployListener.BatchEngineImportConfiguration
				batchEngineImportConfiguration =
					new BatchEngineAutoDeployListener.
						BatchEngineImportConfiguration();

			batchEngineImportConfiguration.setClassName(_itemClass.getName());
			batchEngineImportConfiguration.setVersion("v1.0");
			batchEngineImportConfiguration.setParameters(_parameters);

			return new JSONTBatchEngineExportTaskItemWriterImpl(
				fieldsMap.keySet(), batchEngineImportConfiguration, _fieldNames,
				_outputStream);
		}

		throw new IllegalArgumentException(
			"Unknown batch engine task content type " +
				_batchEngineTaskContentType);
	}

	public BatchEngineExportTaskItemWriterBuilder csvFileColumnDelimiter(
		String csvFileColumnDelimiter) {

		_csvFileColumnDelimiter = csvFileColumnDelimiter;

		return this;
	}

	public BatchEngineExportTaskItemWriterBuilder fieldNames(
		List<String> fieldNames) {

		_fieldNames = fieldNames;

		return this;
	}

	public BatchEngineExportTaskItemWriterBuilder itemClass(
		Class<?> itemClass) {

		_itemClass = itemClass;

		return this;
	}

	public BatchEngineExportTaskItemWriterBuilder outputStream(
		OutputStream outputStream) {

		_outputStream = outputStream;

		return this;
	}

	public BatchEngineExportTaskItemWriterBuilder parameters(
		Map<String, Serializable> parameters) {

		_parameters = parameters;

		return this;
	}

	private BatchEngineTaskContentType _batchEngineTaskContentType;
	private String _csvFileColumnDelimiter;
	private List<String> _fieldNames;
	private Class<?> _itemClass;
	private OutputStream _outputStream;
	private Map<String, Serializable> _parameters;

}