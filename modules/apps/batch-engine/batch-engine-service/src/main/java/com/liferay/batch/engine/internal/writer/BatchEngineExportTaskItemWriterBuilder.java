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
import com.liferay.batch.engine.unit.BatchEngineUnitConfiguration;

import java.io.OutputStream;
import java.io.Serializable;

import java.lang.reflect.Field;

import java.util.HashMap;
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
				_fieldNames, _outputStream);
		}

		if (_batchEngineTaskContentType == BatchEngineTaskContentType.JSONL) {
			return new JSONLBatchEngineExportTaskItemWriterImpl(
				_fieldNames, _outputStream);
		}

		if ((_batchEngineTaskContentType == BatchEngineTaskContentType.XLS) ||
			(_batchEngineTaskContentType == BatchEngineTaskContentType.XLSX)) {

			return new XLSBatchEngineExportTaskItemWriterImpl(
				fieldsMap, _fieldNames, _outputStream);
		}

		if (_batchEngineTaskContentType == BatchEngineTaskContentType.JSONT) {
			BatchEngineUnitConfiguration batchEngineUnitConfiguration =
				new BatchEngineUnitConfiguration();

			batchEngineUnitConfiguration.setClassName(_itemClass.getName());
			batchEngineUnitConfiguration.setCompanyId(_companyId);

			if (_parameters == null) {
				_parameters = new HashMap<>();
			}

			_parameters.computeIfAbsent("createStrategy", key -> "INSERT");
			_parameters.computeIfAbsent(
				"importStrategy", key -> "ON_ERROR_FAIL");
			_parameters.computeIfAbsent("updateStrategy", key -> "UPDATE");

			batchEngineUnitConfiguration.setParameters(_parameters);
			batchEngineUnitConfiguration.setUserId(_userId);
			batchEngineUnitConfiguration.setVersion("v1.0");

			return new JSONTBatchEngineExportTaskItemWriterImpl(
				batchEngineUnitConfiguration, _fieldNames, _outputStream);
		}

		throw new IllegalArgumentException(
			"Unknown batch engine task content type " +
				_batchEngineTaskContentType);
	}

	public BatchEngineExportTaskItemWriterBuilder companyId(long companyId) {
		_companyId = companyId;

		return this;
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

	public BatchEngineExportTaskItemWriterBuilder userId(long userId) {
		_userId = userId;

		return this;
	}

	private BatchEngineTaskContentType _batchEngineTaskContentType;
	private long _companyId;
	private String _csvFileColumnDelimiter;
	private List<String> _fieldNames;
	private Class<?> _itemClass;
	private OutputStream _outputStream;
	private Map<String, Serializable> _parameters;
	private long _userId;

}