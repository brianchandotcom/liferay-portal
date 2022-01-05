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

package com.liferay.portal.search.internal.legacy.document;

import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.geolocation.GeoLocationPoint;
import com.liferay.portal.search.document.DocumentBuilder;
import com.liferay.portal.search.geolocation.GeoBuilders;
import com.liferay.portal.search.internal.document.DocumentBuilderImpl;
import com.liferay.portal.search.legacy.document.DocumentBuilderFactory;

import java.util.Arrays;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Wade Cao
 */
@Component(service = DocumentBuilderFactory.class)
public class DocumentBuilderFactoryImpl implements DocumentBuilderFactory {

	@Override
	public DocumentBuilder builder(Document document) {
		Map<String, Field> map = document.getFields();

		DocumentBuilder documentBuilder = new DocumentBuilderImpl();

		map.forEach((key, field) -> _addField(key, field, documentBuilder));

		return documentBuilder;
	}

	private void _addField(
		String key, Field field, DocumentBuilder documentBuilder) {

		GeoLocationPoint geoLocationPoint = field.getGeoLocationPoint();

		if (geoLocationPoint != null) {
			documentBuilder.setGeoLocationPoint(
				key,
				_geoBuilders.geoLocationPoint(
					geoLocationPoint.getLatitude(),
					geoLocationPoint.getLongitude()));

			return;
		}

		documentBuilder.setValues(key, Arrays.asList(field.getValues()));
	}

	@Reference
	private GeoBuilders _geoBuilders;

}