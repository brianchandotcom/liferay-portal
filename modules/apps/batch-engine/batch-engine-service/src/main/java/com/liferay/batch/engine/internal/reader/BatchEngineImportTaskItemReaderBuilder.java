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

package com.liferay.batch.engine.internal.reader;

import com.liferay.batch.engine.BatchEngineTaskContentType;
import com.liferay.batch.engine.internal.util.ZipInputStreamUtil;

import java.io.InputStream;
import java.io.Serializable;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @author Shuyang Zhou
 * @author Ivica Cardic
 * @author Igor Beslic
 */
public class BatchEngineImportTaskItemReaderBuilder {

	public BatchEngineImportTaskItemReaderBuilder batchEngineTaskContentType(
		BatchEngineTaskContentType batchEngineTaskContentType) {

		_batchEngineTaskContentType = batchEngineTaskContentType;

		return this;
	}

	public BatchEngineImportTaskItemReader build() throws Exception {
		if (_fieldNames == null) {
			_fieldNames = Collections.emptyList();
		}

		InputStream inputStream = ZipInputStreamUtil.asZipInputStream(
			_inputStream);

		if (_batchEngineTaskContentType == BatchEngineTaskContentType.CSV) {
			return new CSVBatchEngineImportTaskItemReaderImpl(
				_csvFileColumnDelimiter, inputStream, _parameters);
		}

		if ((_batchEngineTaskContentType == BatchEngineTaskContentType.JSON) ||
			(_batchEngineTaskContentType == BatchEngineTaskContentType.JSONT)) {

			return new JSONBatchEngineImportTaskItemReaderImpl(
				_fieldNames, inputStream);
		}

		if (_batchEngineTaskContentType == BatchEngineTaskContentType.JSONL) {
			return new JSONLBatchEngineImportTaskItemReaderImpl(
				_fieldNames, inputStream);
		}

		if ((_batchEngineTaskContentType == BatchEngineTaskContentType.XLS) ||
			(_batchEngineTaskContentType == BatchEngineTaskContentType.XLSX)) {

			return new XLSBatchEngineImportTaskItemReaderImpl(
				_fieldNames, inputStream);
		}

		throw new IllegalArgumentException(
			"Unknown batch engine task content type " +
				_batchEngineTaskContentType);
	}

	public BatchEngineImportTaskItemReaderBuilder csvFileColumnDelimiter(
		String csvFileColumnDelimiter) {

		_csvFileColumnDelimiter = csvFileColumnDelimiter;

		return this;
	}

	public BatchEngineImportTaskItemReaderBuilder fieldNames(
		List<String> fieldNames) {

		_fieldNames = fieldNames;

		return this;
	}

	public BatchEngineImportTaskItemReaderBuilder inputStream(
		InputStream inputStream) {

		_inputStream = inputStream;

		return this;
	}

	public BatchEngineImportTaskItemReaderBuilder parameters(
		Map<String, Serializable> parameters) {

		_parameters = parameters;

		return this;
	}

	private BatchEngineTaskContentType _batchEngineTaskContentType;
	private String _csvFileColumnDelimiter;
	private List<String> _fieldNames;
	private InputStream _inputStream;
	private Map<String, Serializable> _parameters;

}