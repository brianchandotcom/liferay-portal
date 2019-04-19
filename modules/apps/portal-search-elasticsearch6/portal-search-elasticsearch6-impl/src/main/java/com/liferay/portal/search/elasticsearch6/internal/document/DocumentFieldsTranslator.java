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

import java.util.Map;

import org.elasticsearch.common.document.DocumentField;

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
		Map<String, DocumentField> documentFieldsMap,
		DocumentBuilder documentBuilder) {

		if (MapUtil.isEmpty(documentFieldsMap)) {
			return;
		}

		documentFieldsMap.forEach(
			(name, documentField) -> translate(documentField, documentBuilder));
	}

	protected void translate(
		DocumentField documentField, DocumentBuilder documentBuilder) {

		String fieldName = documentField.getName();

		if (fieldName.endsWith(_GEOPOINT_SUFFIX)) {
			documentBuilder.setGeoLocationPoint(
				fieldName,
				_geoBuilders.geoLocationPoint(documentField.getValue()));
		}
		else {
			documentBuilder.setValues(fieldName, documentField.getValues());
		}
	}

	private static final String _GEOPOINT_SUFFIX = ".geopoint";

	private static final String _UID_FIELD_NAME = "uid";

	private final GeoBuilders _geoBuilders;

}