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

import com.liferay.talend.avro.constants.AvroConstants;
import com.liferay.talend.runtime.LiferaySink;
import com.liferay.talend.tliferayoutput.Action;
import com.liferay.talend.tliferayoutput.TLiferayOutputProperties;

import java.io.IOException;

import java.net.URI;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import org.apache.avro.Schema;
import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.IndexedRecord;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.talend.components.api.component.runtime.Result;
import org.talend.components.api.component.runtime.WriteOperation;
import org.talend.components.api.component.runtime.WriterWithFeedback;
import org.talend.components.api.container.RuntimeContainer;
import org.talend.daikon.avro.AvroUtils;
import org.talend.daikon.avro.converter.AvroConverter;
import org.talend.daikon.avro.converter.string.StringStringConverter;
import org.talend.daikon.exception.TalendRuntimeException;
import org.talend.daikon.i18n.GlobalI18N;
import org.talend.daikon.i18n.I18nMessageProvider;
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

		_dieOnError = tLiferayOutputProperties.dieOnError.getValue();
		_liferaySink = writeOperation.getSink();
		_rejectWrites = new ArrayList<>();
		_rejectSchema = TLiferayOutputProperties.createRejectSchema(
			tLiferayOutputProperties.resource.main.schema.getValue());
		_successWrites = new ArrayList<>();
	}

	@Override
	public void cleanWrites() {
		_successWrites.clear();
		_rejectWrites.clear();
	}

	@Override
	public Result close() throws IOException {
		return _result;
	}

	public void doDelete(IndexedRecord indexedRecord) throws IOException {
		URI resourceURI = _tLiferayOutputProperties.resource.getEndpointURI();

		_liferaySink.doDeleteRequest(
			_runtimeContainer, resourceURI.toASCIIString());
	}

	public void doInsert(IndexedRecord indexedRecord) throws IOException {
		ObjectNode objectNode = _createEndpointRequestPayload(indexedRecord);

		URI resourceURI = _tLiferayOutputProperties.resource.getEndpointURI();

		_liferaySink.doPostRequest(
			_runtimeContainer, resourceURI.toASCIIString(), objectNode);
	}

	public void doUpdate(IndexedRecord indexedRecord) throws IOException {
		ObjectNode objectNode = _createEndpointRequestPayload(indexedRecord);

		URI resourceURI = _tLiferayOutputProperties.resource.getEndpointURI();

		_liferaySink.doPatchRequest(
			_runtimeContainer, resourceURI.toASCIIString(), objectNode);
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
	}

	@Override
	public void write(Object indexedRecordDatum) throws IOException {
		if ((indexedRecordDatum == null) ||
			!(indexedRecordDatum instanceof IndexedRecord)) {

			if (_log.isDebugEnabled()) {
				if (indexedRecordDatum != null) {
					_log.debug(
						"Unable to process incoming data row: " +
							indexedRecordDatum.toString());
				}
				else {
					_log.debug("Skipping NULL data row");
				}
			}

			return;
		}

		IndexedRecord indexedRecord = (IndexedRecord)indexedRecordDatum;
		cleanWrites();

		Action action = _tLiferayOutputProperties.operations.getValue();

		try {
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
				throw TalendRuntimeException.createUnexpectedException(
					"Unexpected Operation in Output component");
			}

			_handleSuccessRecord(indexedRecord);
		}
		catch (Exception e) {
			if (_log.isDebugEnabled()) {
				_log.debug(e.getMessage(), e);
			}

			_handleRejectRecord(indexedRecord, e);
		}

		_result.totalCount++;
	}

	protected String getIndexedRecordId(IndexedRecord indexedRecord)
		throws IOException {

		Schema indexRecordSchema = indexedRecord.getSchema();

		List<Schema.Field> indexRecordFields = indexRecordSchema.getFields();

		Stream<Schema.Field> stream = indexRecordFields.stream();

		Schema.Field idField = stream.filter(
			field -> AvroConstants.ID.equals(field.name())
		).findFirst(
		).orElseThrow(
			() -> new IOException(
				String.format(
					"Unable to find '%s' field in the incoming indexed record",
					AvroConstants.ID))
		);

		Schema fieldSchema = idField.schema();

		Schema unwrappedSchema = AvroUtils.unwrapIfNullable(fieldSchema);

		Schema.Type fieldType = unwrappedSchema.getType();

		if (fieldType == Schema.Type.STRING) {
			return (String)indexedRecord.get(idField.pos());
		}

		throw new IOException(
			i18nMessages.getMessage(
				"error.unsupported.field.schema", idField.name(),
				fieldType.getName()));
	}

	protected static final I18nMessages i18nMessages;

	static {
		I18nMessageProvider i18nMessageProvider =
			GlobalI18N.getI18nMessageProvider();

		i18nMessages = i18nMessageProvider.getI18nMessages(LiferayWriter.class);
	}

	private ObjectNode _createEndpointRequestPayload(
			IndexedRecord indexedRecord)
		throws IOException {

		Schema indexRecordSchema = indexedRecord.getSchema();

		List<Schema.Field> indexRecordFields = indexRecordSchema.getFields();

		ObjectNode objectNode = _mapper.createObjectNode();

		for (Schema.Field field : indexRecordFields) {
			String fieldName = field.name();

			Schema fieldSchema = field.schema();

			Schema unwrappedSchema = AvroUtils.unwrapIfNullable(fieldSchema);

			Schema.Type fieldType = unwrappedSchema.getType();

			if (fieldType == Schema.Type.NULL) {
				continue;
			}

			if (AvroUtils.isSameType(unwrappedSchema, AvroUtils._boolean())) {
				if (fieldName.contains("_")) {
					String[] nameParts = fieldName.split("_");

					objectNode.with(
						nameParts[0]
					).put(
						nameParts[1], (boolean)indexedRecord.get(field.pos())
					);
				}
				else {
					objectNode.put(
						fieldName, (boolean)indexedRecord.get(field.pos()));
				}
			}
			else if (AvroUtils.isSameType(
						unwrappedSchema, AvroUtils._bytes())) {

				if (fieldName.contains("_")) {
					String[] nameParts = fieldName.split("_");

					objectNode.with(
						nameParts[0]
					).put(
						nameParts[1], (byte[])indexedRecord.get(field.pos())
					);
				}
				else {
					objectNode.put(
						fieldName, (byte[])indexedRecord.get(field.pos()));
				}
			}
			else if (AvroUtils.isSameType(
						unwrappedSchema, AvroUtils._logicalTimestamp()) ||
					 AvroUtils.isSameType(unwrappedSchema, AvroUtils._date()) ||
					 AvroUtils.isSameType(
						 unwrappedSchema, AvroUtils._string())) {

				if (fieldName.contains("_")) {
					String[] nameParts = fieldName.split("_");

					objectNode.with(
						nameParts[0]
					).put(
						nameParts[1], (String)indexedRecord.get(field.pos())
					);
				}
				else {
					objectNode.put(
						fieldName, (String)indexedRecord.get(field.pos()));
				}
			}
			else if (AvroUtils.isSameType(
						unwrappedSchema, AvroUtils._double())) {

				if (fieldName.contains("_")) {
					String[] nameParts = fieldName.split("_");

					objectNode.with(
						nameParts[0]
					).put(
						nameParts[1], (double)indexedRecord.get(field.pos())
					);
				}
				else {
					objectNode.put(
						fieldName, (double)indexedRecord.get(field.pos()));
				}
			}
			else if (AvroUtils.isSameType(
						unwrappedSchema, AvroUtils._float())) {

				if (fieldName.contains("_")) {
					String[] nameParts = fieldName.split("_");

					objectNode.with(
						nameParts[0]
					).put(
						nameParts[1], (float)indexedRecord.get(field.pos())
					);
				}
				else {
					objectNode.put(
						fieldName, (float)indexedRecord.get(field.pos()));
				}
			}
			else if (AvroUtils.isSameType(unwrappedSchema, AvroUtils._int())) {
				if (fieldName.contains("_")) {
					String[] nameParts = fieldName.split("_");

					objectNode.with(
						nameParts[0]
					).put(
						nameParts[1], (int)indexedRecord.get(field.pos())
					);
				}
				else {
					objectNode.put(
						fieldName, (int)indexedRecord.get(field.pos()));
				}
			}
			else if (AvroUtils.isSameType(unwrappedSchema, AvroUtils._long())) {
				if (fieldName.contains("_")) {
					String[] nameParts = fieldName.split("_");

					objectNode.with(
						nameParts[0]
					).put(
						nameParts[1], (long)indexedRecord.get(field.pos())
					);
				}
				else {
					objectNode.put(
						fieldName, (long)indexedRecord.get(field.pos()));
				}
			}
			else {
				throw new IOException(
					i18nMessages.getMessage(
						"error.unsupported.field.schema", fieldName,
						fieldType.getName()));
			}
		}

		return objectNode;
	}

	private void _handleRejectRecord(
			IndexedRecord indexedRecord, Exception exception)
		throws IOException {

		if (_dieOnError) {
			throw new IOException(exception);
		}

		_result.rejectCount++;

		Schema currentRecordSchema = indexedRecord.getSchema();

		List<Schema.Field> currentRecordSchemaFields =
			currentRecordSchema.getFields();

		List<Schema.Field> rejectSchemaFields = _rejectSchema.getFields();

		int additionRejectSchemaFieldsSize =
			TLiferayOutputProperties.rejectSchemaFieldNames.size();

		if (rejectSchemaFields.isEmpty() ||
			((currentRecordSchemaFields.size() +
				additionRejectSchemaFieldsSize) != rejectSchemaFields.size())) {

			_log.error("Reject schema was not setup properly");

			return;
		}

		IndexedRecord errorIndexedRecord = new GenericData.Record(
			_rejectSchema);

		for (Schema.Field field : currentRecordSchemaFields) {
			Schema.Field rejectField = _rejectSchema.getField(field.name());

			if (rejectField != null) {
				int pos = rejectField.pos();

				errorIndexedRecord.put(pos, indexedRecord.get(field.pos()));
			}
		}

		Schema.Field errorField = _rejectSchema.getField(
			TLiferayOutputProperties.FIELD_ERROR_MESSAGE);

		errorIndexedRecord.put(
			errorField.pos(),
			_stringStringConverter.convertToAvro(exception.getMessage()));

		_rejectWrites.add(errorIndexedRecord);
	}

	private void _handleSuccessRecord(IndexedRecord indexedRecord) {
		_result.successCount++;
		_successWrites.add(indexedRecord);
	}

	private static final Logger _log = LoggerFactory.getLogger(
		LiferayWriter.class);

	private static final AvroConverter<String, String> _stringStringConverter =
		new StringStringConverter();

	private final boolean _dieOnError;
	private final LiferaySink _liferaySink;
	private final LiferayWriteOperation _liferayWriteOperation;
	private final ObjectMapper _mapper = new ObjectMapper();
	private final Schema _rejectSchema;
	private final List<IndexedRecord> _rejectWrites;
	private Result _result;
	private final RuntimeContainer _runtimeContainer;
	private final List<IndexedRecord> _successWrites;
	private final TLiferayOutputProperties _tLiferayOutputProperties;

}