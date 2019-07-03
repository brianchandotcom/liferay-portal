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

import com.liferay.talend.common.schema.SchemaUtils;
import com.liferay.talend.common.schema.constants.SchemaConstants;
import com.liferay.talend.runtime.LiferaySink;
import com.liferay.talend.tliferayoutput.Action;
import com.liferay.talend.tliferayoutput.TLiferayOutputProperties;

import java.io.IOException;
import java.io.StringReader;

import java.net.URI;

import java.util.ArrayList;
import java.util.Base64;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonReader;

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

		_dieOnError = tLiferayOutputProperties.dieOnError.getValue();
		_liferaySink = writeOperation.getSink();
		_rejectWrites = new ArrayList<>();
		_rejectSchema = SchemaUtils.createRejectSchema(
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
		URI resourceURI = _tLiferayOutputProperties.resource.getEndpointURI();

		_liferaySink.doPostRequest(
			_runtimeContainer, resourceURI.toASCIIString(),
			_createEndpointRequestPayload(indexedRecord));
	}

	public void doUpdate(IndexedRecord indexedRecord) throws IOException {
		URI resourceURI = _tLiferayOutputProperties.resource.getEndpointURI();

		_liferaySink.doPatchRequest(
			_runtimeContainer, resourceURI.toASCIIString(),
			_createEndpointRequestPayload(indexedRecord));
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

		Action action =
			_tLiferayOutputProperties.resource.operations.getValue();

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

	protected static final I18nMessages i18nMessages;

	static {
		I18nMessageProvider i18nMessageProvider =
			GlobalI18N.getI18nMessageProvider();

		i18nMessages = i18nMessageProvider.getI18nMessages(LiferayWriter.class);
	}

	private JsonObject _createEndpointRequestPayload(
			IndexedRecord indexedRecord)
		throws IOException {

		Schema indexRecordSchema = indexedRecord.getSchema();

		List<Schema.Field> indexRecordFields = indexRecordSchema.getFields();

		JsonObjectBuilder objectBuilder = Json.createObjectBuilder();

		Map<String, JsonObjectBuilder> nestedJsonObjectBuilders =
			new HashMap<>();

		for (Schema.Field field : indexRecordFields) {
			if (indexedRecord.get(field.pos()) == null) {
				continue;
			}

			String fieldName = field.name();

			Schema unwrappedSchema = AvroUtils.unwrapIfNullable(field.schema());

			if (unwrappedSchema.getType() == Schema.Type.NULL) {
				continue;
			}

			JsonObjectBuilder currentJsonObjectBuilder = objectBuilder;

			if (_isNestedFieldName(fieldName)) {
				String[] nameParts = fieldName.split("_");

				if (!nestedJsonObjectBuilders.containsKey(nameParts[0])) {
					nestedJsonObjectBuilders.put(
						nameParts[0], Json.createObjectBuilder());
				}

				currentJsonObjectBuilder = nestedJsonObjectBuilders.get(
					nameParts[0]);

				fieldName = nameParts[1];
			}

			if (AvroUtils.isSameType(unwrappedSchema, AvroUtils._boolean())) {
				currentJsonObjectBuilder.add(
					fieldName, (boolean)indexedRecord.get(field.pos()));
			}
			else if (AvroUtils.isSameType(
						unwrappedSchema, AvroUtils._bytes())) {

				Base64.Encoder encoder = Base64.getEncoder();

				currentJsonObjectBuilder.add(
					fieldName,
					encoder.encodeToString(
						(byte[])indexedRecord.get(field.pos())));
			}
			else if (AvroUtils.isSameType(
						unwrappedSchema, AvroUtils._logicalTimestamp()) ||
					 AvroUtils.isSameType(unwrappedSchema, AvroUtils._date())) {

				currentJsonObjectBuilder.add(
					fieldName, (Long)indexedRecord.get(field.pos()));
			}
			else if (AvroUtils.isSameType(
						unwrappedSchema, AvroUtils._double())) {

				currentJsonObjectBuilder.add(
					fieldName, (double)indexedRecord.get(field.pos()));
			}
			else if (AvroUtils.isSameType(
						unwrappedSchema, AvroUtils._float())) {

				currentJsonObjectBuilder.add(
					fieldName, (float)indexedRecord.get(field.pos()));
			}
			else if (AvroUtils.isSameType(unwrappedSchema, AvroUtils._int())) {
				currentJsonObjectBuilder.add(
					fieldName, (int)indexedRecord.get(field.pos()));
			}
			else if (AvroUtils.isSameType(unwrappedSchema, AvroUtils._long())) {
				currentJsonObjectBuilder.add(
					fieldName, (long)indexedRecord.get(field.pos()));
			}
			else if (AvroUtils.isSameType(
						unwrappedSchema, AvroUtils._string())) {

				String stringFieldValue = (String)indexedRecord.get(
					field.pos());

				if (Objects.equals("true", field.getProp("oas.dictionary")) ||
					Objects.equals("Dictionary", unwrappedSchema.getName())) {

					StringReader stringReader = new StringReader(
						stringFieldValue);

					JsonReader jsonReader = Json.createReader(stringReader);

					currentJsonObjectBuilder.add(
						fieldName, jsonReader.readValue());

					jsonReader.close();

					continue;
				}

				currentJsonObjectBuilder.add(fieldName, stringFieldValue);
			}
			else {
				throw new IOException(
					i18nMessages.getMessage(
						"error.unsupported.field.schema", fieldName,
						unwrappedSchema.getType()));
			}
		}

		for (Map.Entry<String, JsonObjectBuilder> nestedJsonObjectBuilder :
				nestedJsonObjectBuilders.entrySet()) {

			objectBuilder.add(
				nestedJsonObjectBuilder.getKey(),
				nestedJsonObjectBuilder.getValue());
		}

		return objectBuilder.build();
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
			SchemaConstants.FIELD_ERROR_MESSAGE);

		errorIndexedRecord.put(
			errorField.pos(),
			_stringStringConverter.convertToAvro(exception.getMessage()));

		_rejectWrites.add(errorIndexedRecord);
	}

	private void _handleSuccessRecord(IndexedRecord indexedRecord) {
		_result.successCount++;
		_successWrites.add(indexedRecord);
	}

	private boolean _isNestedFieldName(String fieldName) {
		if (fieldName.contains("_")) {
			return true;
		}

		return false;
	}

	private static final Logger _log = LoggerFactory.getLogger(
		LiferayWriter.class);

	private static final AvroConverter<String, String> _stringStringConverter =
		new StringStringConverter();

	private final boolean _dieOnError;
	private final LiferaySink _liferaySink;
	private final LiferayWriteOperation _liferayWriteOperation;
	private final Schema _rejectSchema;
	private final List<IndexedRecord> _rejectWrites;
	private Result _result;
	private final RuntimeContainer _runtimeContainer;
	private final List<IndexedRecord> _successWrites;
	private final TLiferayOutputProperties _tLiferayOutputProperties;

}