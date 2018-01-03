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

package com.liferay.consumer.talend.runtime.reader;

import com.liferay.consumer.talend.tliferayinput.TLiferayInputProperties;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import java.util.Map;
import java.util.NoSuchElementException;

import org.apache.avro.Schema;
import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.IndexedRecord;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.talend.components.api.component.runtime.AbstractBoundedReader;
import org.talend.components.api.component.runtime.Result;
import org.talend.components.common.avro.RootSchemaUtils;
import org.talend.components.common.component.runtime.RootRecordUtils;
import org.talend.daikon.avro.converter.AvroConverter;

/**
 * @author Zoltán Takács
 */
public class LiferayInputReader extends AbstractBoundedReader<IndexedRecord> {

	public LiferayInputReader(LiferayInputSource source) {
		super(source);
	}

	@Override
	public boolean advance() throws IOException {
		if (!_started) {
			throw new IllegalStateException("Reader wasn't started");
		}

		_hasMore = _reader.ready();

		if (_hasMore) {
			String line = _reader.readLine();

			// create the data schema

			Schema dataSchema = _getRuntimeSchema(line);

			// create the data IndexRecord

			IndexedRecord dataRecord = _getConverter(
				dataSchema).convertToAvro(line);

			// create the outOfBand record (since the schema is static)

			IndexedRecord outOfBandRecord = new GenericData.Record(
				TLiferayInputProperties.outOfBandSchema);

			outOfBandRecord.put(0, _result.totalCount);

			// create the root record

			_current = RootRecordUtils.createRootRecord(
				_getRootSchema(), dataRecord, outOfBandRecord);

			_result.totalCount++;
		}

		return _hasMore;
	}

	@Override
	public void close() throws IOException {
		if (!_started) {
			throw new IllegalStateException("Reader wasn't started");
		}

		_reader.close();

		if (_log.isDebugEnabled()) {
			_log.debug("close: " + getCurrentSource().getFilePath());
		}

		_reader = null;
		_started = false;
		_hasMore = false;
	}

	@Override
	public IndexedRecord getCurrent() throws NoSuchElementException {
		if (!_started) {
			throw new NoSuchElementException("Reader wasn't started");
		}

		if (!_hasMore) {
			throw new NoSuchElementException("Has no more elements");
		}

		return _current;
	}

	@Override
	public LiferayInputSource getCurrentSource() {
		return (LiferayInputSource)super.getCurrentSource();
	}

	/**
	 * Returns values of Return properties. It is called after component
	 * finished his work (after {@link this#close()} method)
	 */
	@Override
	public Map<String, Object> getReturnValues() {
		return _result.toMap();
	}

	@Override
	public boolean start() throws IOException {
		_reader = new BufferedReader(
			new FileReader(getCurrentSource().getFilePath()));
		_result = new Result();

		if (_log.isDebugEnabled()) {
			_log.debug(
				"open: " + getCurrentSource().getFilePath()); //$NON-NLS-1$
		}

		_started = true;

		return advance();
	}

	/**
	 * Returns implementation of {@link AvroConverter}, creates it if it doesn't
	 * exist.
	 *
	 * @param runtimeSchema
	 * Schema of data record
	 * @return converter
	 */
	private AvroConverter<String, IndexedRecord> _getConverter(
		Schema runtimeSchema) {

		if (_converter == null) {
			_converter = getCurrentSource().createConverter(runtimeSchema);
		}

		return _converter;
	}

	/**
	 * Returns Root schema, which is used during IndexedRecord creation <br>
	 * This should be called only after {@link this#getRuntimeSchema(String)} is
	 * called
	 *
	 * @return avro Root schema
	 */
	private Schema _getRootSchema() {
		if (_rootSchema == null) {
			if (_runtimeSchema == null) {
				throw new IllegalStateException(
					"Runtime schema should be created before Root schema");
			}
			else {
				_rootSchema = RootSchemaUtils.createRootSchema(
					_runtimeSchema, TLiferayInputProperties.outOfBandSchema);
			}
		}

		return _rootSchema;
	}

	/**
	 * Returns Runtime schema, which is used during data IndexedRecord creation
	 * Creates the schema only once for all records
	 *
	 * @param delimitedString delimited line, which was read from file
	 * @return avro Runtime schema
	 */
	private Schema _getRuntimeSchema(String delimitedString) {
		if (_runtimeSchema == null) {
			_runtimeSchema = null;

			delimitedString = delimitedString;
			//getCurrentSource().provideRuntimeSchema(delimitedString);
		}

		return _runtimeSchema;
	}

	private static final Logger _log = LoggerFactory.getLogger(
		LiferayInputReader.class);

	/**
	 * Converts datum field values to avro format
	 */
	private AvroConverter<String, IndexedRecord> _converter;

	private IndexedRecord _current;
	private boolean _hasMore;
	private BufferedReader _reader;

	/**
	 * Holds values for return properties
	 */
	private Result _result;

	/**
	 * Root schema includes Runtime schema and schema of out of band data
	 * (a.k.a flow variables)
	 */
	private Schema _rootSchema;

	/**
	 * Runtime schema - schema of data record
	 */
	private Schema _runtimeSchema;

	private boolean _started;

}