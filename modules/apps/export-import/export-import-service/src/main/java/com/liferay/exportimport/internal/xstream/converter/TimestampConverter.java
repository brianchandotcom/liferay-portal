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

package com.liferay.exportimport.internal.xstream.converter;

import com.liferay.exportimport.kernel.xstream.BaseXStreamConverter;
import com.liferay.exportimport.kernel.xstream.XStreamHierarchicalStreamReader;
import com.liferay.exportimport.kernel.xstream.XStreamHierarchicalStreamWriter;
import com.liferay.exportimport.kernel.xstream.XStreamMarshallingContext;
import com.liferay.exportimport.kernel.xstream.XStreamUnmarshallingContext;

import com.thoughtworks.xstream.converters.extended.SqlTimestampConverter;

import java.sql.Timestamp;

import java.util.List;

/**
 * @author Rodrigo Paulino
 */
public class TimestampConverter extends BaseXStreamConverter {

	public TimestampConverter() {
		_converter = new SqlTimestampConverter();
	}

	@Override
	public boolean canConvert(Class<?> clazz) {
		Class<?> superClass = clazz.getSuperclass();

		if (clazz.equals(Timestamp.class) ||
			((superClass != null) && superClass.equals(Timestamp.class))) {

			return true;
		}

		return false;
	}

	@Override
	public void marshal(
			Object object, XStreamHierarchicalStreamWriter writer,
			XStreamMarshallingContext xStreamMarshallingContext)
		throws Exception {

		writer.setValue(_converter.toString(object));
	}

	@Override
	public Object unmarshal(
			XStreamHierarchicalStreamReader xStreamHierarchicalStreamReader,
			XStreamUnmarshallingContext xStreamUnmarshallingContext)
		throws Exception {

		return _converter.fromString(
			xStreamHierarchicalStreamReader.getValue());
	}

	@Override
	protected List<String> getFields() {
		return null;
	}

	private final SqlTimestampConverter _converter;

}