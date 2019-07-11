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

package com.liferay.portal.search.elasticsearch7.internal.document;

import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.search.document.Document;
import com.liferay.portal.search.document.Field;
import com.liferay.portal.search.elasticsearch7.internal.geolocation.GeoLocationPointTranslator;
import com.liferay.portal.search.geolocation.GeoLocationPoint;

import java.io.IOException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.lang.time.FastDateFormat;

import org.elasticsearch.common.Strings;
import org.elasticsearch.common.geo.GeoPoint;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;

import org.osgi.service.component.annotations.Component;

/**
 * @author Michael C. Han
 * @author Milen Dyankov
 */
@Component(immediate = true, service = ElasticsearchDocumentFactory.class)
public class DefaultElasticsearchDocumentFactory
	implements ElasticsearchDocumentFactory {

	public static final FastDateFormat DATE_FORMAT = FastDateFormat.getInstance(
		"yyyyMMddHHmmss");

	public static final String DATE_MAX_VALUE = "99950812133000";

	/**
	 * @deprecated As of Mueller (7.2.x)
	 */
	@Deprecated
	@Override
	public String getElasticsearchDocument(
		com.liferay.portal.kernel.search.Document legacyDocument) {

		try {
			return Strings.toString(translate(legacyDocument));
		}
		catch (IOException ioe) {
			throw new RuntimeException(ioe);
		}
	}

	@Override
	public XContentBuilder getElasticsearchDocument(Document document) {
		try {
			return translate(document);
		}
		catch (IOException ioe) {
			throw new RuntimeException(ioe);
		}
	}

	protected void addDates(
			XContentBuilder xContentBuilder,
			com.liferay.portal.kernel.search.Field field)
		throws IOException {

		for (Date date : field.getDates()) {
			String value;

			if (date.getTime() == Long.MAX_VALUE) {
				value = DATE_MAX_VALUE;
			}
			else {
				value = DATE_FORMAT.format(date);
			}

			xContentBuilder.value(value);
		}
	}

	protected void addField(Field field, XContentBuilder xContentBuilder)
		throws IOException {

		List<Object> values = field.getValues();

		if (values.isEmpty()) {
			addFieldValueless(field, xContentBuilder);
		}

		if (values.size() == 1) {
			addFieldValue(field, values.get(0), xContentBuilder);

			return;
		}

		addFieldValues(field, values, xContentBuilder);
	}

	protected void addField(
			XContentBuilder xContentBuilder,
			com.liferay.portal.kernel.search.Field field)
		throws IOException {

		String name = field.getName();

		if (!field.isLocalized()) {
			String[] values = field.getValues();

			if (ArrayUtil.isEmpty(values)) {
				return;
			}

			List<String> valuesList = new ArrayList<>(values.length);

			for (String value : values) {
				if (value == null) {
					continue;
				}

				valuesList.add(value.trim());
			}

			if (valuesList.isEmpty()) {
				return;
			}

			values = valuesList.toArray(new String[0]);

			addField(xContentBuilder, field, name, values);

			if (field.isSortable()) {
				addField(
					xContentBuilder, field, getSortableFieldName(name), values);
			}
		}
		else {
			Map<Locale, String> localizedValues = field.getLocalizedValues();

			for (Map.Entry<Locale, String> entry : localizedValues.entrySet()) {
				String value = entry.getValue();

				if (Validator.isNull(value)) {
					continue;
				}

				Locale locale = entry.getKey();

				String languageId = LocaleUtil.toLanguageId(locale);

				String defaultLanguageId = LocaleUtil.toLanguageId(
					LocaleUtil.getDefault());

				value = value.trim();

				if (languageId.equals(defaultLanguageId)) {
					addField(xContentBuilder, field, name, value);
				}

				String localizedName =
					com.liferay.portal.kernel.search.Field.getLocalizedName(
						languageId, name);

				addField(xContentBuilder, field, localizedName, value);

				if (field.isSortable()) {
					addField(
						xContentBuilder, field,
						getSortableFieldName(localizedName), value);
				}
			}
		}
	}

	protected void addField(
			XContentBuilder xContentBuilder,
			com.liferay.portal.kernel.search.Field field, String fieldName,
			String... values)
		throws IOException {

		xContentBuilder.field(fieldName);

		if (field.isArray() || (values.length > 1)) {
			xContentBuilder.startArray();
		}

		com.liferay.portal.kernel.search.geolocation.GeoLocationPoint
			geoLocationPoint = field.getGeoLocationPoint();

		if (geoLocationPoint != null) {
			GeoPoint geoPoint = new GeoPoint(
				geoLocationPoint.getLatitude(),
				geoLocationPoint.getLongitude());

			xContentBuilder.value(geoPoint);
		}
		else if (field.isDate()) {
			addDates(xContentBuilder, field);
		}
		else {
			for (String value : values) {
				xContentBuilder.value(translateValue(field, value));
			}
		}

		if (field.isArray() || (values.length > 1)) {
			xContentBuilder.endArray();
		}
	}

	protected void addFields(
			Collection<com.liferay.portal.kernel.search.Field> fields,
			XContentBuilder xContentBuilder)
		throws IOException {

		for (com.liferay.portal.kernel.search.Field field : fields) {
			if (!field.hasChildren()) {
				addField(xContentBuilder, field);
			}
			else {
				addNestedField(xContentBuilder, field);
			}
		}
	}

	protected void addFieldValue(
			Field field, Object value, XContentBuilder xContentBuilder)
		throws IOException {

		xContentBuilder.field(field.getName(), toElasticsearchValue(value));
	}

	protected void addFieldValueless(
			Field field, XContentBuilder xContentBuilder)
		throws IOException {

		xContentBuilder.field(field.getName());
	}

	protected void addFieldValues(
			Field field, List<Object> values, XContentBuilder xContentBuilder)
		throws IOException {

		xContentBuilder.array(
			field.getName(),
			values.stream(
			).map(
				this::toElasticsearchValue
			).toArray());
	}

	protected void addNestedField(
			XContentBuilder xContentBuilder,
			com.liferay.portal.kernel.search.Field field)
		throws IOException {

		if (field.isArray()) {
			xContentBuilder.startArray(field.getName());
		}
		else {
			if (Validator.isNull(field.getName())) {
				xContentBuilder.startObject();
			}
			else {
				xContentBuilder.startObject(field.getName());
			}
		}

		addFields(field.getFields(), xContentBuilder);

		if (field.isArray()) {
			xContentBuilder.endArray();
		}
		else {
			xContentBuilder.endObject();
		}
	}

	protected String getSortableFieldName(String localizedName) {
		return com.liferay.portal.kernel.search.Field.getSortableFieldName(
			localizedName);
	}

	protected Object toElasticsearchValue(Object value) {
		if (value instanceof GeoLocationPoint) {
			return GeoLocationPointTranslator.translate(
				(GeoLocationPoint)value);
		}

		return value;
	}

	protected XContentBuilder translate(
			com.liferay.portal.kernel.search.Document legacyDocument)
		throws IOException {

		XContentBuilder xContentBuilder = XContentFactory.jsonBuilder();

		xContentBuilder.startObject();

		Map<String, com.liferay.portal.kernel.search.Field> fields =
			legacyDocument.getFields();

		addFields(fields.values(), xContentBuilder);

		xContentBuilder.endObject();

		return xContentBuilder;
	}

	protected XContentBuilder translate(Document document) throws IOException {
		XContentBuilder xContentBuilder = XContentFactory.jsonBuilder();

		Map<String, Field> fields = document.getFields();

		xContentBuilder.startObject();

		for (Field field : fields.values()) {
			addField(field, xContentBuilder);
		}

		xContentBuilder.endObject();

		return xContentBuilder;
	}

	protected Object translateValue(
		com.liferay.portal.kernel.search.Field field, String value) {

		if (!field.isNumeric()) {
			return value;
		}

		Class<? extends Number> clazz = field.getNumericClass();

		if (clazz.equals(Float.class)) {
			return Float.valueOf(value);
		}
		else if (clazz.equals(Integer.class)) {
			return Integer.valueOf(value);
		}
		else if (clazz.equals(Long.class)) {
			return Long.valueOf(value);
		}
		else if (clazz.equals(Short.class)) {
			return Short.valueOf(value);
		}

		return Double.valueOf(value);
	}

}