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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import com.liferay.talend.runtime.LiferaySink;
import com.liferay.talend.tliferayoutput.Action;
import com.liferay.talend.tliferayoutput.TLiferayOutputProperties;

import java.io.IOException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.avro.Schema;
import org.apache.avro.Schema.Type;
import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.IndexedRecord;

import org.talend.components.api.component.runtime.Result;
import org.talend.components.api.component.runtime.WriteOperation;
import org.talend.components.api.component.runtime.WriterWithFeedback;
import org.talend.components.api.container.RuntimeContainer;
import org.talend.daikon.avro.AvroUtils;
import org.talend.daikon.i18n.GlobalI18N;
import org.talend.daikon.i18n.I18nMessages;

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

		_liferaySink = writeOperation.getSink();

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

	public void doInsert(IndexedRecord indexedRecord) throws IOException {
		Schema indexRecordSchema = indexedRecord.getSchema();

		List<Schema.Field> indexRecordFields = indexRecordSchema.getFields();

		ObjectNode apioForm = _mapper.createObjectNode();

		for (Schema.Field field : indexRecordFields) {
			Schema fieldSchema = field.schema();

			Schema unwrappedSchema = AvroUtils.unwrapIfNullable(fieldSchema);

			Type fieldType = unwrappedSchema.getType();

			if (fieldType == Schema.Type.STRING) {
				apioForm.put(
					field.name(), (String)indexedRecord.get(field.pos()));
			}
			else if (fieldType == Schema.Type.NULL) {
				apioForm.put(field.name(), "");
			}
			else {
				throw new IOException(
					i18nMessages.getMessage(
						"error.unsupported.field.schema", field.name(),
						fieldType.getName()));
			}
		}

		String resourceURL =
			_tLiferayOutputProperties.resource.resourceURL.getValue();

		_liferaySink.doApioPostRequest(
			_runtimeContainer, resourceURL, apioForm);
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
				doInsert(indexedRecord);
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

	protected static final I18nMessages i18nMessages =
		GlobalI18N.getI18nMessageProvider().getI18nMessages(
			LiferayWriter.class);

	private void _handleRejectRecord(
		IndexedRecord indexedRecord, Exception exception) {

		_result.rejectCount++;

		IndexedRecord errorIndexedRecord = new GenericData.Record(
			_rejectSchema);

		errorIndexedRecord.put(
			0, indexedRecord.get(0) + " " + exception.getMessage());

		_rejectWrites.add(errorIndexedRecord);
	}

	private void _handleSuccessRecord(IndexedRecord indexedRecord) {
		_result.successCount++;
		_successWrites.add(indexedRecord);
	}

	private final LiferaySink _liferaySink;
	private final LiferayWriteOperation _liferayWriteOperation;
	private final ObjectMapper _mapper = new ObjectMapper();
	private Schema _rejectSchema;
	private final List<IndexedRecord> _rejectWrites;
	private Result _result;
	private final RuntimeContainer _runtimeContainer;
	private final List<IndexedRecord> _successWrites;
	private final TLiferayOutputProperties _tLiferayOutputProperties;

}