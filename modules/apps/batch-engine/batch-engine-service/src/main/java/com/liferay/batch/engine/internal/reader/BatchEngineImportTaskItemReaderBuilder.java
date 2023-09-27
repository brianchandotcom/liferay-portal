/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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