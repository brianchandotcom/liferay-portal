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

package com.liferay.talend.avro;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.avro.Schema;

import org.talend.daikon.avro.AvroUtils;
import org.talend.daikon.avro.LogicalTypeUtils;
import org.talend.daikon.avro.SchemaConstants;
import org.talend.daikon.avro.converter.AvroConverter;
import org.talend.daikon.avro.converter.string.StringBooleanConverter;
import org.talend.daikon.avro.converter.string.StringIntConverter;
import org.talend.daikon.avro.converter.string.StringLongConverter;
import org.talend.daikon.avro.converter.string.StringStringConverter;
import org.talend.daikon.avro.converter.string.StringTimestampConverter;

/**
 * @author Zoltán Takács
 */
public abstract class BaseConverter<DatumT, AvroT>
	implements AvroConverter<DatumT, AvroT> {

	/**
	 * Sets Avro {@link Schema} and DI {@link Class} of data
	 *
	 * @param clazz type of DI data
	 * @param schema schema of a Avro data
	 */
	public BaseConverter(Class<DatumT> clazz, Schema schema) {
		_clazz = clazz;
		_schema = schema;
	}

	/**
	 * Returns {@link Class} of DI data
	 */
	@Override
	public Class<DatumT> getDatumClass() {
		return _clazz;
	}

	/**
	 * Returns {@link Schema} of Avro data
	 */
	@Override
	public Schema getSchema() {
		return _schema;
	}

	/**
	 * Initialize converters per each schema field
	 *
	 * @param schema design schema
	 */
	protected void initConverters(Schema schema) {
		schemaFields = schema.getFields();

		avroConverters = new AvroConverter[schemaFields.size()];

		for (int i = 0; i < schemaFields.size(); i++) {
			Schema.Field field = schemaFields.get(i);

			Schema fieldSchema = AvroUtils.unwrapIfNullable(field.schema());

			if (LogicalTypeUtils.isLogicalTimestampMillis(fieldSchema)) {
				String datePattern = field.getProp(
					SchemaConstants.TALEND_COLUMN_PATTERN);

				avroConverters[i] = new StringTimestampConverter(datePattern);
			}
			else {
				Schema.Type type = fieldSchema.getType();

				avroConverters[i] = _converterRegistry.get(type);
			}
		}
	}

	/**
	 * Stores converters. Array index corresponds to field index
	 */
	protected AvroConverter[] avroConverters;

	protected List<Schema.Field> schemaFields;

	private static final Map<Schema.Type, AvroConverter> _converterRegistry;

	static {
		_converterRegistry = new HashMap<>();

		_converterRegistry.put(
			Schema.Type.BOOLEAN, new StringBooleanConverter());
		_converterRegistry.put(Schema.Type.INT, new StringIntConverter());
		_converterRegistry.put(Schema.Type.LONG, new StringLongConverter());
		_converterRegistry.put(Schema.Type.STRING, new StringStringConverter());
	}

	/**
	 * Class of DI data
	 */
	private final Class<DatumT> _clazz;

	/**
	 * Schema of Avro data
	 */
	private final Schema _schema;

}