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
@Component(immediate = true, service = SearchHitDocumentTranslator.class)
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
		else {
			Map<String, DocumentField> documentFields = searchHit.getFields();

			for (String documentFieldName : documentFields.keySet()) {
				_addField(document, documentFieldName, documentFields);
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

		String geopointIndicatorSuffix = ".geopoint";

		if (fieldName.endsWith(geopointIndicatorSuffix)) {
			return null;
		}

		DocumentField documentField = documentFields.get(fieldName);

		if (documentFields.containsKey(
				fieldName.concat(geopointIndicatorSuffix))) {

			return _translateGeoPoint(documentField);
		}

		return translate(documentField);
	}

	private Field _getFieldFromSource(
		String fieldName, Map<String, Object> documentSourceMap) {

		String geopointIndicatorSuffix = ".geopoint";

		if (fieldName.endsWith(geopointIndicatorSuffix)) {
			return null;
		}

		Object value = documentSourceMap.get(fieldName);

		if (documentSourceMap.containsKey(
				fieldName.concat(geopointIndicatorSuffix))) {

			return _translateGeoPoint(fieldName, value);
		}

		return translate(fieldName, value);
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