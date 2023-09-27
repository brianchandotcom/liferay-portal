/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.elasticsearch7.internal;

import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.DocumentImpl;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.geolocation.GeoLocationPoint;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.Collection;
import java.util.Map;

import org.elasticsearch.common.document.DocumentField;
import org.elasticsearch.search.SearchHit;

import org.osgi.service.component.annotations.Component;

/**
 * @author André de Oliveira
 */
@Component(service = SearchHitDocumentTranslator.class)
public class SearchHitDocumentTranslatorImpl
	implements SearchHitDocumentTranslator {

	@Override
	public Document translate(SearchHit searchHit) {
		Document document = new DocumentImpl();

		Map<String, Object> documentSourceMap = searchHit.getSourceAsMap();

		if (MapUtil.isNotEmpty(documentSourceMap)) {
			for (String fieldName : documentSourceMap.keySet()) {
				_addFieldFromSource(document, fieldName, documentSourceMap);
			}
		}

		Map<String, DocumentField> documentFields = searchHit.getFields();

		if (MapUtil.isNotEmpty(documentFields)) {
			for (String fieldName : documentFields.keySet()) {
				if (document.getField(fieldName) == null) {
					_addField(document, fieldName, documentFields);
				}
			}
		}

		return document;
	}

	protected Field translate(DocumentField documentField) {
		return translate(documentField.getName(), documentField.getValues());
	}

	protected Field translate(String fieldName, Object value) {
		if (value instanceof Collection) {
			Collection<Object> values = (Collection)value;

			return new Field(
				fieldName,
				ArrayUtil.toStringArray(values.toArray(new Object[0])));
		}

		return new Field(fieldName, String.valueOf(value));
	}

	private void _addField(
		Document document, String fieldName,
		Map<String, DocumentField> documentFields) {

		Field field = _getField(fieldName, documentFields);

		if (field != null) {
			document.add(field);
		}
	}

	private void _addFieldFromSource(
		Document document, String fieldName,
		Map<String, Object> documentSourceMap) {

		Field field = _getFieldFromSource(fieldName, documentSourceMap);

		if (field != null) {
			document.add(field);
		}
	}

	private Field _getField(
		String fieldName, Map<String, DocumentField> documentFields) {

		if (_isInvalidFieldName(fieldName)) {
			return null;
		}

		DocumentField documentField = documentFields.get(fieldName);

		if (documentFields.containsKey(fieldName.concat(".geopoint"))) {
			return _translateGeoPoint(documentField);
		}

		return translate(documentField);
	}

	private Field _getFieldFromSource(
		String fieldName, Map<String, Object> documentSourceMap) {

		if (_isInvalidFieldName(fieldName)) {
			return null;
		}

		Object value = documentSourceMap.get(fieldName);

		if (documentSourceMap.containsKey(fieldName.concat(".geopoint"))) {
			return _translateGeoPoint(fieldName, value);
		}

		return translate(fieldName, value);
	}

	private boolean _isInvalidFieldName(String fieldName) {
		if (fieldName.endsWith(".geopoint") || fieldName.equals("_ignored")) {
			return true;
		}

		return false;
	}

	private Field _translateGeoPoint(DocumentField documentField) {
		return _translateGeoPoint(
			documentField.getName(), documentField.getValue());
	}

	private Field _translateGeoPoint(String fieldName, Object value) {
		Field field = new Field(fieldName);

		String[] values = StringUtil.split(String.valueOf(value));

		field.setGeoLocationPoint(
			new GeoLocationPoint(
				Double.valueOf(values[0]), Double.valueOf(values[1])));

		return field;
	}

}