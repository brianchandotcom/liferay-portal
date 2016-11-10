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

package com.liferay.portal.search.elasticsearch.internal;

import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.DocumentImpl;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.geolocation.GeoLocationPoint;
import com.liferay.portal.kernel.util.ArrayUtil;

import java.util.Collection;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHitField;

import org.osgi.service.component.annotations.Component;

/**
 * @author André de Oliveira
 */
@Component(immediate = true, service = SearchHitDocumentTranslator.class)
public class SearchHitDocumentTranslatorImpl
	implements SearchHitDocumentTranslator {

	public Document translate(SearchHit searchHit) {
		Document document = new DocumentImpl();

		Map<String, SearchHitField> searchHitFields = searchHit.getFields();

		for (String searchHitFieldName : searchHitFields.keySet()) {
			addField(document, searchHitFieldName, searchHitFields);
		}

		return document;
	}

	protected void addField(
		Document document, String fieldName,
		Map<String, SearchHitField> searchHitFields) {

		String baseFieldName = removeSuffixes(fieldName, ".lat", ".lon");

		if (document.hasField(baseFieldName)) {
			return;
		}

		SearchHitField searchHitField = searchHitFields.get(baseFieldName);

		Field field = translateGeoPoint(
			searchHitField, searchHitFields.get(baseFieldName + ".lat"),
			searchHitFields.get(baseFieldName + ".lon"));

		if (field == null) {
			field = translate(searchHitField);
		}

		document.add(field);
	}

	protected String removeSuffixes(String fieldName, String... suffixes) {
		for (String suffix : suffixes) {
			fieldName = StringUtils.removeEnd(fieldName, suffix);
		}

		return fieldName;
	}

	protected Field translate(SearchHitField searchHitField) {
		String name = searchHitField.getName();

		Collection<Object> values = searchHitField.getValues();

		Field field = new Field(
			name, ArrayUtil.toStringArray(
				values.toArray(new Object[values.size()])));

		return field;
	}

	protected Field translateGeoPoint(
		SearchHitField searchHitField, SearchHitField latSearchHitField,
		SearchHitField lonSearchHitField) {

		if ((latSearchHitField == null) || (lonSearchHitField == null)) {
			return null;
		}

		Field field = new Field(searchHitField.getName());

		field.setGeoLocationPoint(
			new GeoLocationPoint(
				(Double)latSearchHitField.getValue(),
				(Double)lonSearchHitField.getValue()));

		return field;
	}

}