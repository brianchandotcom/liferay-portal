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

import java.io.OutputStream;
import java.io.Serializable;

import java.lang.reflect.Field;

import java.util.List;
import java.util.Map;

/**
 * @author Ivica Cardic
 */
public class BatchEngineExportTaskItemWriterFactory {

	public BatchEngineExportTaskItemWriterFactory(
		String csvFileColumnDelimiter) {

		_csvFileColumnDelimiter = csvFileColumnDelimiter;
	}

	public BatchEngineExportTaskItemWriter create(
			BatchEngineTaskContentType batchEngineTaskContentType,
			List<String> fieldNames, Class<?> itemClass,
			OutputStream outputStream, Map<String, Serializable> parameters)
		throws Exception {

		Map<String, Field> fieldMap = ItemClassIndexUtil.index(itemClass);

		if (batchEngineTaskContentType == BatchEngineTaskContentType.CSV) {
			return new CSVBatchEngineExportTaskItemWriterImpl(
				_csvFileColumnDelimiter, fieldMap, fieldNames, outputStream,
				parameters);
		}

		if (batchEngineTaskContentType == BatchEngineTaskContentType.JSON) {
			return new JSONBatchEngineExportTaskItemWriterImpl(
				fieldMap.keySet(), fieldNames, outputStream);
		}

		if (batchEngineTaskContentType == BatchEngineTaskContentType.JSONL) {
			return new JSONLBatchEngineExportTaskItemWriterImpl(
				fieldMap.keySet(), fieldNames, outputStream);
		}

		if ((batchEngineTaskContentType == BatchEngineTaskContentType.XLS) ||
			(batchEngineTaskContentType == BatchEngineTaskContentType.XLSX)) {

			return new XLSBatchEngineExportTaskItemWriterImpl(
				fieldMap, fieldNames, outputStream);
		}

		throw new IllegalArgumentException(
			"Unknown batch engine task content type " +
				batchEngineTaskContentType);
	}

	private final String _csvFileColumnDelimiter;

}