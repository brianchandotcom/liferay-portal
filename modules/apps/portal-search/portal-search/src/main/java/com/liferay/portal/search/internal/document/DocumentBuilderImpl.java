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

package com.liferay.portal.search.internal.document;

import com.liferay.portal.search.document.Document;
import com.liferay.portal.search.document.DocumentBuilder;
import com.liferay.portal.search.geolocation.GeoLocationPoint;

import java.util.Collection;

/**
 * @author Wade Cao
 */
public class DocumentBuilderImpl implements DocumentBuilder {

	@Override
	public Document build() {
		return new DocumentImpl(_documentImpl);
	}

	@Override
	public DocumentBuilder setBoolean(String name, Boolean value) {
		if (value == Boolean.TRUE) {
			setFieldValue(name, value);
		}
		else {
			unsetValue(name);
		}

		return this;
	}

	@Override
	public DocumentBuilder setBooleans(String name, Boolean... values) {
		setFieldValues(name, values);

		return this;
	}

	@Override
	public DocumentBuilder setDate(String name, String value) {
		setFieldValue(name, value);

		return this;
	}

	@Override
	public DocumentBuilder setDates(String name, String... values) {
		setFieldValues(name, values);

		return this;
	}

	@Override
	public DocumentBuilder setDouble(String name, Double value) {
		setFieldValue(name, value);

		return this;
	}

	@Override
	public DocumentBuilder setDoubles(String name, Double... values) {
		setFieldValues(name, values);

		return this;
	}

	@Override
	public DocumentBuilder setFloat(String name, Float value) {
		setFieldValue(name, value);

		return this;
	}

	@Override
	public DocumentBuilder setFloats(String name, Float... values) {
		setFieldValues(name, values);

		return this;
	}

	@Override
	public DocumentBuilder setGeoLocationPoint(
		String name, GeoLocationPoint value) {

		setFieldValue(name, value);

		return this;
	}

	@Override
	public DocumentBuilder setGeoLocationPoints(
		String name, GeoLocationPoint... values) {

		setFieldValues(name, values);

		return this;
	}

	@Override
	public DocumentBuilder setInteger(String name, Integer value) {
		setFieldValue(name, value);

		return this;
	}

	@Override
	public DocumentBuilder setIntegers(String name, Integer... values) {
		setFieldValues(name, values);

		return this;
	}

	@Override
	public DocumentBuilder setLong(String name, Long value) {
		setFieldValue(name, value);

		return this;
	}

	@Override
	public DocumentBuilder setLongs(String name, Long... values) {
		setFieldValues(name, values);

		return this;
	}

	@Override
	public DocumentBuilder setString(String name, String value) {
		setFieldValue(name, value);

		return this;
	}

	@Override
	public DocumentBuilder setStrings(String name, String... values) {
		setFieldValues(name, values);

		return this;
	}

	@Override
	public DocumentBuilder setValue(String name, Object value) {
		setFieldValue(name, value);

		return this;
	}

	@Override
	public DocumentBuilder setValues(String name, Collection<Object> values) {
		_documentImpl.setFieldValues(name, values);

		return this;
	}

	@Override
	public DocumentBuilder unsetValue(String name) {
		_documentImpl.unsetField(name);

		return this;
	}

	protected void setFieldValue(String name, Object value) {
		_documentImpl.setFieldValue(name, value);
	}

	protected void setFieldValues(String name, Object[] values) {
		_documentImpl.setFieldValues(name, values);
	}

	private final DocumentImpl _documentImpl = new DocumentImpl();

}