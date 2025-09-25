/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.elasticsearch7.internal.document;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.search.document.DocumentBuilder;
import com.liferay.portal.search.geolocation.GeoBuilders;
import com.liferay.portal.search.geolocation.GeoLocationPoint;

import java.util.Collection;
import java.util.List;
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

		if (MapUtil.isEmpty(documentFieldsMap) ||
			documentFieldsMap.containsKey(_UID_FIELD_NAME) ||
			Validator.isBlank(alternateUidFieldName)) {

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

		if (_translateGeoLocationPoint(
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

	private GeoLocationPoint _getGeoLocationPoint(
		DocumentField documentField1, DocumentField documentField2) {

		Object value1 = documentField1.getValue();
		String value2 = documentField2.getValue();

		if (StringUtil.startsWith(value2, StringPool.OPEN_CURLY_BRACE) &&
			(value1 instanceof Map)) {

			return _getGeoLocationPoint((Map<String, Object>)value1);
		}

		GeoPoint geoPoint = GeoPoint.fromGeohash(value2);

		return _geoBuilders.geoLocationPoint(
			geoPoint.getLat(), geoPoint.getLon());
	}

	private GeoLocationPoint _getGeoLocationPoint(Map<String, Object> map) {
		if (MapUtil.isEmpty(map) || !map.containsKey("coordinates")) {
			return null;
		}

		List<Double> list = (List<Double>)map.get("coordinates");

		return _geoBuilders.geoLocationPoint(list.get(1), list.get(0));
	}

	private boolean _translateGeoLocationPoint(
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

		documentBuilder.setGeoLocationPoint(
			fieldName1, _getGeoLocationPoint(documentField1, documentField2));

		return true;
	}

	private static final String _GEOPOINT_SUFFIX = ".geopoint";

	private static final String _UID_FIELD_NAME = "uid";

	private final GeoBuilders _geoBuilders;

}