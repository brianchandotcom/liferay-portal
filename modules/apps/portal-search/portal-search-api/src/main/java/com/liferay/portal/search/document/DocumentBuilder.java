/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.document;

import com.liferay.portal.search.geolocation.GeoLocationPoint;

import java.util.Collection;

/**
 * @author Wade Cao
 * @author André de Oliveira
 */
public class DocumentBuilder {

	public Document build() {
		return new Document(_document);
	}

	public DocumentBuilder setBoolean(String name, Boolean value) {
		if (value == Boolean.TRUE) {
			setFieldValue(name, value);
		}
		else {
			unsetValue(name);
		}

		return this;
	}

	public DocumentBuilder setBooleans(String name, Boolean... values) {
		setFieldValues(name, values);

		return this;
	}

	public DocumentBuilder setDate(String name, String value) {
		setFieldValue(name, value);

		return this;
	}

	public DocumentBuilder setDates(String name, String... values) {
		setFieldValues(name, values);

		return this;
	}

	public DocumentBuilder setDouble(String name, Double value) {
		setFieldValue(name, value);

		return this;
	}

	public DocumentBuilder setDoubles(String name, Double... values) {
		setFieldValues(name, values);

		return this;
	}

	public DocumentBuilder setFloat(String name, Float value) {
		setFieldValue(name, value);

		return this;
	}

	public DocumentBuilder setFloats(String name, Float... values) {
		setFieldValues(name, values);

		return this;
	}

	public DocumentBuilder setGeoLocationPoint(
		String name, GeoLocationPoint value) {

		setFieldValue(name, value);

		return this;
	}

	public DocumentBuilder setGeoLocationPoints(
		String name, GeoLocationPoint... values) {

		setFieldValues(name, values);

		return this;
	}

	public DocumentBuilder setInteger(String name, Integer value) {
		setFieldValue(name, value);

		return this;
	}

	public DocumentBuilder setIntegers(String name, Integer... values) {
		setFieldValues(name, values);

		return this;
	}

	public DocumentBuilder setLong(String name, Long value) {
		setFieldValue(name, value);

		return this;
	}

	public DocumentBuilder setLongs(String name, Long... values) {
		setFieldValues(name, values);

		return this;
	}

	public DocumentBuilder setString(String name, String value) {
		setFieldValue(name, value);

		return this;
	}

	public DocumentBuilder setStrings(String name, String... values) {
		setFieldValues(name, values);

		return this;
	}

	public DocumentBuilder setValue(String name, Object value) {
		setFieldValue(name, value);

		return this;
	}

	public DocumentBuilder setValues(String name, Collection<Object> values) {
		_document.setFieldValues(name, values);

		return this;
	}

	public DocumentBuilder unsetValue(String name) {
		_document.unsetField(name);

		return this;
	}

	protected void setFieldValue(String name, Object value) {
		_document.setFieldValue(name, value);
	}

	protected void setFieldValues(String name, Object[] values) {
		_document.setFieldValues(name, values);
	}

	private final Document _document = new Document();

}