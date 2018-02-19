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

import com.liferay.talend.runtime.LiferaySink;
import com.liferay.talend.tliferayoutput.Action;
import com.liferay.talend.tliferayoutput.TLiferayOutputProperties;

import java.io.IOException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.avro.Schema;
import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.IndexedRecord;

import org.talend.components.api.component.runtime.Result;
import org.talend.components.api.component.runtime.WriteOperation;
import org.talend.components.api.component.runtime.WriterWithFeedback;
import org.talend.components.api.container.RuntimeContainer;

/**
 * @author Zoltán Takács
 */
public class LiferayWriter
	implements WriterWithFeedback<Result, IndexedRecord, IndexedRecord> {

	public LiferayWriter(
		LiferayWriteOperation writeOperation, RuntimeContainer runtimeContainer,
		TLiferayOutputProperties tLiferayOutputProperties) {

		_liferayWriteOperation = writeOperation;
		_runtimeContainer = runtimeContainer;
		_tLiferayOutputProperties = tLiferayOutputProperties;

		_rejectWrites = new ArrayList<>();
		_successWrites = new ArrayList<>();
	}

	/**
	 * It will be the part of WriterWithFeedback API in the next version of
	 * daikon dependency. When we migrate to Talend 7, we just need to add the
	 * Override annotation here
	 */
	public void cleanWrites() {
		_successWrites.clear();
		_rejectWrites.clear();
	}

	@Override
	public Result close() throws IOException {
		return _result;
	}

	@Override
	public Iterable<IndexedRecord> getRejectedWrites() {
		return Collections.unmodifiableCollection(_rejectWrites);
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
		_result = new Result(uId);

		LiferaySink liferaySink = _liferayWriteOperation.getSink();
		_rejectSchema =
			_tLiferayOutputProperties.schemaReject.schema.getValue();

		liferaySink.getSchemaNames(_runtimeContainer);
	}

	@Override
	public void write(Object indexedRecordDatum) throws IOException {
		if ((indexedRecordDatum == null) ||
			!(indexedRecordDatum instanceof IndexedRecord)) {

			return;
		}

		IndexedRecord indexedRecord = (IndexedRecord)indexedRecordDatum;
		cleanWrites();

		String id = (String)indexedRecord.get(0);

		Action action = _tLiferayOutputProperties.operations.getValue();

		try {
			if (Action.CREATE == action) {
			}

			if (Action.DELETE == action) {
			}

			if (Action.UPDATE == action) {
			}

			_handleSuccessRecord(indexedRecord);
		}
		catch (Exception e) {
			_handleRejectRecord(indexedRecord, e);
		}

		_result.totalCount++;
	}

	private void _handleRejectRecord(IndexedRecord record, Exception e) {
		_result.rejectCount++;

		IndexedRecord errorIndexedRecord = new GenericData.Record(
			_rejectSchema);

		errorIndexedRecord.put(0, record.get(0) + " " + e.getMessage());

		_rejectWrites.add(errorIndexedRecord);
	}

	private void _handleSuccessRecord(IndexedRecord record) {
		_result.successCount++;
		_successWrites.add(record);
	}

	private final LiferayWriteOperation _liferayWriteOperation;
	private Schema _rejectSchema;
	private final List<IndexedRecord> _rejectWrites;
	private Result _result;
	private final RuntimeContainer _runtimeContainer;
	private final List<IndexedRecord> _successWrites;
	private final TLiferayOutputProperties _tLiferayOutputProperties;

}