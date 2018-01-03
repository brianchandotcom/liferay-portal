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

package com.liferay.consumer.talend.avro;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.avro.Schema;
import org.apache.avro.Schema.Field;
import org.apache.avro.Schema.Type;
import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.IndexedRecord;

import org.talend.daikon.avro.AvroUtils;
import org.talend.daikon.avro.LogicalTypeUtils;
import org.talend.daikon.avro.SchemaConstants;
import org.talend.daikon.avro.converter.AvroConverter;
import org.talend.daikon.avro.converter.string.StringBooleanConverter;
import org.talend.daikon.avro.converter.string.StringConverter;
import org.talend.daikon.avro.converter.string.StringDoubleConverter;
import org.talend.daikon.avro.converter.string.StringFloatConverter;
import org.talend.daikon.avro.converter.string.StringIntConverter;
import org.talend.daikon.avro.converter.string.StringLongConverter;
import org.talend.daikon.avro.converter.string.StringStringConverter;
import org.talend.daikon.avro.converter.string.StringTimestampConverter;

/**
 * @author Zoltán Takács
 *
 * Converts delimited string to {@link IndexedRecord} and vice versa
 *
 * Delimited string example (delimiter is ';'): "first name;last name; age"
 *
 * Such converter could be used in {@link Reader} to convert data storage
 * specific object to {@link IndexedRecord} and in writer to convert
 * {@link IndexedRecord} to data storage specific object
 */
public class DelimitedStringConverter
	implements AvroConverter<String, IndexedRecord> {

	/**
	 * Constructor sets schema and default delimiter, which will be used during
	 * conversion
	 *
	 * @param schema
	 *            avro schema
	 */
	public DelimitedStringConverter(Schema schema) {
		this(schema, _DEFAULT_DELIMITER);
	}

	/**
	 * Constructor sets schema and delimiter, which will be used during
	 * conversion
	 *
	 * @param schema
	 *            avro schema
	 */
	public DelimitedStringConverter(Schema schema, String delimiter) {
		_schema = schema;
		_delimiter = delimiter;
		_size = schema.getFields().size();

		_initConverters(schema);
	}

	@Override
	public IndexedRecord convertToAvro(String delimitedString) {
		String[] fields = delimitedString.split(_delimiter);

		if (fields.length != _size) {
			throw new IllegalArgumentException(
				"Input string has wrong number of fields");
		}

		IndexedRecord record = new GenericData.Record(_schema);

		for (int i = 0; i < _size; i++) {
			Object value = _converters[i].convertToAvro(fields[i]);

			record.put(i, value);
		}

		return record;
	}

	@Override
	public String convertToDatum(IndexedRecord record) {
		if (!_schema.equals(record.getSchema())) {
			throw new IllegalArgumentException(
				"Input record has different schema");
		}

		if (_size == 0) {
			return "";
		}

		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < _size; i++) {
			Object value = record.get(i);

			String field = (String)_converters[i].convertToDatum(value);

			sb.append(field);

			sb.append(_delimiter);
		}

		sb.deleteCharAt(sb.length() - 1);

		return sb.toString();
	}

	/**
	 * Returns datum class, which is String
	 *
	 * @return String.class
	 */
	@Override
	public Class<String> getDatumClass() {
		return String.class;
	}

	/**
	 * Returns avro schema
	 *
	 * @return avro schema
	 */
	@Override
	public Schema getSchema() {
		return _schema;
	}

	/**
	 * Initialize converters per each schema field
	 *
	 * @param schema
	 *            design schema
	 */
	private void _initConverters(Schema schema) {
		_converters = new StringConverter[_size];
		List<Field> fields = schema.getFields();

		for (int i = 0; i < _size; i++) {
			Field field = fields.get(i);

			Schema fieldSchema = field.schema();

			fieldSchema = AvroUtils.unwrapIfNullable(fieldSchema);

			if (LogicalTypeUtils.isLogicalTimestampMillis(fieldSchema)) {
				String datePattern = field.getProp(
					SchemaConstants.TALEND_COLUMN_PATTERN);

				_converters[i] = new StringTimestampConverter(datePattern);
			}
			else {
				Type type = fieldSchema.getType();

				_converters[i] = _converterRegistry.get(type);
			}
		}
	}

	private static final String _DEFAULT_DELIMITER = ";";

	/**
	 * Contains available {@link StringConverter}. Avro type is used as a key
	 * However datum class could be also used as key. It depends on what data
	 * mapping is required for particular component family. There might be
	 * situations when several datum classes are mapped to the same avro type.
	 * This is the case to use datum class as a key
	 */
	private static final Map<Type, StringConverter> _converterRegistry;

	/**
	 * Fill in converter registry
	 */
	static {
		_converterRegistry = new HashMap<>();

		_converterRegistry.put(Type.BOOLEAN, new StringBooleanConverter());
		_converterRegistry.put(Type.DOUBLE, new StringDoubleConverter());
		_converterRegistry.put(Type.FLOAT, new StringFloatConverter());
		_converterRegistry.put(Type.INT, new StringIntConverter());
		_converterRegistry.put(Type.LONG, new StringLongConverter());
		_converterRegistry.put(Type.STRING, new StringStringConverter());
	}

	/**
	 * Stores converters. Index in array corresponds to index of field in
	 * schema(?)
	 */
	private StringConverter[] _converters;

	private final String _delimiter;

	/**
	 * Schema of Avro IndexedRecord
	 */
	private final Schema _schema;

	/**
	 * Number of fields in schema
	 */
	private final int _size;

}