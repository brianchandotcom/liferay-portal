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
import com.liferay.talend.avro.JsonObjectIndexedRecordConverter;
import com.liferay.talend.common.schema.SchemaUtils;
import com.liferay.talend.runtime.LiferaySink;
import com.liferay.talend.tliferayoutput.Action;
import com.liferay.talend.tliferayoutput.TLiferayOutputProperties;

import java.io.IOException;

import java.net.URI;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.json.JsonObject;

import org.apache.avro.generic.IndexedRecord;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.talend.components.api.component.runtime.Result;
import org.talend.components.api.component.runtime.WriteOperation;
import org.talend.components.api.component.runtime.WriterWithFeedback;
import org.talend.components.api.container.RuntimeContainer;
import org.talend.daikon.exception.TalendRuntimeException;

/**
 * @author Zoltán Takács
 * @author Igor Beslic
 */
public class LiferayWriter
	implements WriterWithFeedback<Result, IndexedRecord, IndexedRecord> {

	public LiferayWriter(
		LiferayWriteOperation writeOperation, RuntimeContainer runtimeContainer,
		TLiferayOutputProperties tLiferayOutputProperties) {

		_liferayWriteOperation = writeOperation;
		_runtimeContainer = runtimeContainer;
		_tLiferayOutputProperties = tLiferayOutputProperties;

		_dieOnError = tLiferayOutputProperties.getDieOnError();
		_liferaySink = writeOperation.getSink();
		_result = new Result();
		_successWrites = new ArrayList<>();

		_indexedRecordJsonObjectConverter =
			new IndexedRecordJsonObjectConverter(
				_dieOnError, _tLiferayOutputProperties.getSchema(),
				SchemaUtils.createRejectSchema(
					_tLiferayOutputProperties.getSchema()),
				_result);
		_jsonObjectIndexedRecordConverter =
			new JsonObjectIndexedRecordConverter(
				_tLiferayOutputProperties.getSchema());
	}

	@Override
	public void cleanWrites() {
		_successWrites.clear();
		_indexedRecordJsonObjectConverter.clearFailedIndexedRecords();
	}

	@Override
	public Result close() throws IOException {
		return _result;
	}

	public void doDelete(IndexedRecord indexedRecord) throws IOException {
		URI resourceURI = _tLiferayOutputProperties.resource.getEndpointURI();

		_liferaySink.doDeleteRequest(
			_runtimeContainer, resourceURI.toASCIIString());

		_handleSuccessRecord(indexedRecord);
	}

	public void doInsert(IndexedRecord indexedRecord) throws IOException {
		URI resourceURI = _tLiferayOutputProperties.resource.getEndpointURI();

		JsonObject jsonObject = null;

		try {
			jsonObject = _liferaySink.doPostRequest(
				_runtimeContainer, resourceURI.toASCIIString(),
				_indexedRecordJsonObjectConverter.toJsonObject(indexedRecord));
		}
		catch (Exception e) {
			_indexedRecordJsonObjectConverter.reject(indexedRecord, e);

			return;
		}

		_handleSuccessRecord(
			_jsonObjectIndexedRecordConverter.toIndexedRecord(jsonObject));
	}

	public void doUpdate(IndexedRecord indexedRecord) throws IOException {
		URI resourceURI = _tLiferayOutputProperties.resource.getEndpointURI();

		JsonObject jsonObject = null;

		try {
			jsonObject = _liferaySink.doPatchRequest(
				_runtimeContainer, resourceURI.toASCIIString(),
				_indexedRecordJsonObjectConverter.toJsonObject(indexedRecord));
		}
		catch (Exception e) {
			_indexedRecordJsonObjectConverter.reject(indexedRecord, e);

			return;
		}

		if (jsonObject != null) {
			_handleSuccessRecord(
				_jsonObjectIndexedRecordConverter.toIndexedRecord(jsonObject));
		}
		else {
			_handleSuccessRecord(indexedRecord);
		}
	}

	@Override
	public Iterable<IndexedRecord> getRejectedWrites() {
		return Collections.unmodifiableCollection(
			_indexedRecordJsonObjectConverter.getFailedIndexedRecords());
	}

	@Override
	public Iterable<IndexedRecord> getSuccessfulWrites() {
		return Collections.unmodifiableCollection(_successWrites);
	}

	@Override
	public WriteOperation<Result> getWriteOperation() {
		return _liferayWriteOperation;
	}

	@Override
	public void open(String uId) throws IOException {
	}

	@Override
	public void write(Object object) throws IOException {
		if (!_isIndexedRecord(object)) {
			return;
		}

		IndexedRecord indexedRecord = (IndexedRecord)object;

		cleanWrites();

		Action action = _tLiferayOutputProperties.getConfiguredAction();

		if (Action.Delete == action) {
			doDelete(indexedRecord);
		}
		else if (Action.Insert == action) {
			doInsert(indexedRecord);
		}
		else if (Action.Update == action) {
			doUpdate(indexedRecord);
		}
		else {
			_indexedRecordJsonObjectConverter.reject(
				indexedRecord,
				TalendRuntimeException.createUnexpectedException(
					"Unsupported write action " + action));
		}

		_result.totalCount++;
	}

	private void _handleSuccessRecord(IndexedRecord indexedRecord) {
		_result.successCount++;

		_successWrites.add(indexedRecord);
	}

	private boolean _isIndexedRecord(Object object) throws IOException {
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

		if (_dieOnError) {
			throw new IOException(iae);
		}

		if (_logger.isWarnEnabled()) {
			_logger.warn("Unable to process record", iae);
		}

		return false;
	}

	private static final Logger _logger = LoggerFactory.getLogger(
		LiferayWriter.class);

	private final boolean _dieOnError;
	private final IndexedRecordJsonObjectConverter
		_indexedRecordJsonObjectConverter;
	private final JsonObjectIndexedRecordConverter
		_jsonObjectIndexedRecordConverter;
	private final LiferaySink _liferaySink;
	private final LiferayWriteOperation _liferayWriteOperation;
	private final Result _result;
	private final RuntimeContainer _runtimeContainer;
	private final List<IndexedRecord> _successWrites;
	private final TLiferayOutputProperties _tLiferayOutputProperties;

}