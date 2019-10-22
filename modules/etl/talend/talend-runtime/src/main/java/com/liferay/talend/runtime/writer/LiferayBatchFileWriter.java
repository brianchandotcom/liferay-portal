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

package com.liferay.talend.runtime.writer;

import com.liferay.talend.avro.IndexedRecordJsonObjectConverter;
import com.liferay.talend.avro.exception.ConverterException;
import com.liferay.talend.common.schema.SchemaUtils;
import com.liferay.talend.properties.batch.LiferayBatchFileProperties;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

import javax.json.JsonObject;

import org.apache.avro.Schema;
import org.apache.avro.generic.IndexedRecord;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.talend.components.api.component.runtime.Result;
import org.talend.components.api.component.runtime.WriteOperation;
import org.talend.components.api.component.runtime.Writer;
import org.talend.components.api.container.RuntimeContainer;

/**
 * @author Igor Beslic
 */
public class LiferayBatchFileWriter implements Writer<Result> {

	public LiferayBatchFileWriter(
		WriteOperation<Result> writeOperation,
		LiferayBatchFileProperties liferayBatchFileProperties,
		RuntimeContainer runtimeContainer) {

		_writeOperation = writeOperation;
		_liferayBatchFileProperties = liferayBatchFileProperties;
	}

	@Override
	public Result close() throws IOException {
		if (_outputStreamWriter != null) {
			_outputStreamWriter.flush();

			_outputStreamWriter.close();
		}

		return _result;
	}

	@Override
	public WriteOperation<Result> getWriteOperation() {
		return _writeOperation;
	}

	@Override
	public void open(String uId) throws IOException {
		_uId = uId;

		_result = new Result(_uId);

		Schema entitySchema = _liferayBatchFileProperties.getEntitySchema();

		_indexedRecordJsonObjectConverter =
			new IndexedRecordJsonObjectConverter(
				false, entitySchema,
				SchemaUtils.createRejectSchema(entitySchema), _result);

		_outputStreamWriter = new OutputStreamWriter(
			new FileOutputStream(
				new File(_liferayBatchFileProperties.getBatchFilePath())));
	}

	@Override
	public void write(Object object) throws IOException {
		if (!_isIndexedRecord(object)) {
			_result.rejectCount++;

			return;
		}

		IndexedRecord indexedRecord = (IndexedRecord)object;

		try {
			JsonObject jsonObject =
				_indexedRecordJsonObjectConverter.toJsonObject(indexedRecord);

			_outputStreamWriter.write(jsonObject.toString());

			_outputStreamWriter.write(System.lineSeparator());

			_result.successCount++;

			if ((_result.successCount % _FLUSH_TRIGGER_WRITES) == 0) {
				_outputStreamWriter.flush();
			}
		}
		catch (ConverterException ce) {
			_indexedRecordJsonObjectConverter.reject(indexedRecord, ce);
		}

		_result.totalCount++;
	}

	private boolean _isIndexedRecord(Object object) {
		if (object instanceof IndexedRecord) {
			return true;
		}

		IllegalArgumentException iae = new IllegalArgumentException(
			"Indexed record is null");

		if (object != null) {
			iae = new IllegalArgumentException(
				String.format(
					"Expected record instance of %s but actual instance " +
						"passed was %s",
					IndexedRecord.class, object.getClass()));
		}

		if (_logger.isWarnEnabled()) {
			_logger.warn("Unable to process record", iae);
		}

		return false;
	}

	private static final int _FLUSH_TRIGGER_WRITES = 20;

	private static final Logger _logger = LoggerFactory.getLogger(
		LiferayBatchFileWriter.class);

	private IndexedRecordJsonObjectConverter _indexedRecordJsonObjectConverter;
	private final LiferayBatchFileProperties _liferayBatchFileProperties;
	private OutputStreamWriter _outputStreamWriter;
	private Result _result;
	private String _uId;
	private final WriteOperation<Result> _writeOperation;

}