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

package com.liferay.portal.search.elasticsearch6.internal.document;

import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.search.document.DocumentBuilder;
import com.liferay.portal.search.geolocation.GeoBuilders;

import java.util.Collection;
import java.util.Map;

import org.elasticsearch.common.document.DocumentField;
import org.elasticsearch.common.geo.GeoPoint;

/**
 * @author Bryan Engler
 */
public class DocumentFieldsTranslator {

	public DocumentFieldsTranslator(GeoBuilders geoBuilders) {
		_geoBuilders = geoBuilders;
	}

	public void populateAlternateUID(
		Map<String, DocumentField> documentFieldsMap,
		DocumentBuilder documentBuilder, String alternateUidFieldName) {

		if (MapUtil.isEmpty(documentFieldsMap)) {
			return;
		}

		if (documentFieldsMap.containsKey(_UID_FIELD_NAME)) {
			return;
		}

		if (Validator.isBlank(alternateUidFieldName)) {
			return;
		}

		DocumentField documentField = documentFieldsMap.get(
			alternateUidFieldName);

		if (documentField != null) {
			documentBuilder.setValues(
				_UID_FIELD_NAME, documentField.getValues());
		}
	}

	public void translate(
		DocumentBuilder documentBuilder,
		Map<String, Object> documentSourceMap) {

		if (MapUtil.isEmpty(documentSourceMap)) {
			return;
		}

		documentSourceMap.forEach(
			(name, value) -> translate(name, value, documentBuilder));
	}

	public void translate(
		Map<String, DocumentField> documentFieldsMap,
		DocumentBuilder documentBuilder) {

		if (MapUtil.isEmpty(documentFieldsMap)) {
			return;
		}

		documentFieldsMap.forEach(
			(name, documentField) -> translate(
				documentField, documentBuilder, documentFieldsMap));
	}

	protected void translate(
		DocumentField documentField, DocumentBuilder documentBuilder,
		Map<String, DocumentField> documentFieldsMap) {

		if (translateGeoLocationPoint(
				documentField, documentBuilder, documentFieldsMap)) {

			return;
		}

		documentBuilder.setValues(
			documentField.getName(), documentField.getValues());
	}

	protected void translate(
		String name, Object value, DocumentBuilder documentBuilder) {

		if (name.endsWith(_GEOPOINT_SUFFIX)) {
			documentBuilder.setGeoLocationPoint(
				name, _geoBuilders.geoLocationPoint((String)value));
		}
		else {
			if (value instanceof Collection) {
				documentBuilder.setValues(name, (Collection)value);
			}
			else {
				documentBuilder.setValue(name, value);
			}
		}
	}

	protected boolean translateGeoLocationPoint(
		DocumentField documentField1, DocumentBuilder documentBuilder,
		Map<String, DocumentField> documentFieldsMap) {

		String fieldName1 = documentField1.getName();

		if (fieldName1.endsWith(_GEOPOINT_SUFFIX)) {
			return true;
		}

		String fieldName2 = fieldName1.concat(_GEOPOINT_SUFFIX);

		DocumentField documentField2 = documentFieldsMap.get(fieldName2);

		if (documentField2 == null) {
			return false;
		}

		GeoPoint geoPoint = GeoPoint.fromGeohash(documentField2.getValue());

		documentBuilder.setGeoLocationPoint(
			fieldName1,
			_geoBuilders.geoLocationPoint(
				geoPoint.getLat(), geoPoint.getLon()));

		return true;
	}

	private static final String _GEOPOINT_SUFFIX = ".geopoint";

	private static final String _UID_FIELD_NAME = "uid";

	private final GeoBuilders _geoBuilders;

}