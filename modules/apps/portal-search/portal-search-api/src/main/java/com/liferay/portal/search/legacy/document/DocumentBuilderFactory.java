/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.legacy.document;

import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.geolocation.GeoLocationPoint;
import com.liferay.portal.search.document.DocumentBuilder;
import com.liferay.portal.search.geolocation.GeoBuilders;

import java.util.Arrays;
import java.util.Map;

/**
 * @author André de Oliveira
 */
public class DocumentBuilderFactory {

	public static DocumentBuilder builder(Document document) {
		Map<String, Field> map = document.getFields();

		DocumentBuilder documentBuilder = new DocumentBuilder();

		map.forEach((key, field) -> _addField(key, field, documentBuilder));

		return documentBuilder;
	}

	private static void _addField(
		String key, Field field, DocumentBuilder documentBuilder) {

		GeoLocationPoint geoLocationPoint = field.getGeoLocationPoint();

		if (geoLocationPoint != null) {
			documentBuilder.setGeoLocationPoint(
				key,
				GeoBuilders.INSTANCE.geoLocationPoint(
					geoLocationPoint.getLatitude(),
					geoLocationPoint.getLongitude()));

			return;
		}

		documentBuilder.setValues(key, Arrays.asList(field.getValues()));
	}

}