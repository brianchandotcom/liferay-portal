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

import org.apache.avro.Schema;

import org.talend.daikon.avro.converter.AvroConverter;

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
	 * Class of DI data
	 */
	private final Class<DatumT> _clazz;

	/**
	 * Schema of Avro data
	 */
	private final Schema _schema;

}