/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.solr8.internal.document;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.search.geolocation.GeoLocationPoint;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.search.document.Document;
import com.liferay.portal.search.document.Field;

import java.util.Locale;
import java.util.Map;

import org.apache.solr.common.SolrInputDocument;

/**
 * @author Michael C. Han
 */
public class SolrDocumentFactoryUtil {

	/**
	 * @deprecated As of Mueller (7.2.x)
	 */
	@Deprecated
	public static SolrInputDocument getSolrInputDocument(
		com.liferay.portal.kernel.search.Document document) {

		SolrInputDocument solrInputDocument = new SolrInputDocument();

		Map<String, com.liferay.portal.kernel.search.Field> fields =
			document.getFields();

		for (com.liferay.portal.kernel.search.Field field : fields.values()) {
			String name = field.getName();

			if (!field.isLocalized()) {
				String[] values = field.getValues();

				if (ArrayUtil.isEmpty(values)) {
					continue;
				}

				for (String value : values) {
					if (value == null) {
						continue;
					}

					value = value.trim();

					_addField(solrInputDocument, field, value, name);
				}
			}
			else {
				Map<Locale, String> localizedValues =
					field.getLocalizedValues();

				for (Map.Entry<Locale, String> entry :
						localizedValues.entrySet()) {

					String value = entry.getValue();

					if (Validator.isNull(value)) {
						continue;
					}

					value = value.trim();

					Locale locale = entry.getKey();

					String languageId = LocaleUtil.toLanguageId(locale);

					String defaultLanguageId = LocaleUtil.toLanguageId(
						LocaleUtil.getDefault());

					if (languageId.equals(defaultLanguageId)) {
						solrInputDocument.addField(name, value);
					}

					_addField(
						solrInputDocument, field, value,
						com.liferay.portal.kernel.search.Field.getLocalizedName(
							locale, name));
				}
			}
		}

		return solrInputDocument;
	}

	public static SolrInputDocument getSolrInputDocument(Document document) {
		SolrInputDocument solrInputDocument = new SolrInputDocument();

		Map<String, Field> fields = document.getFields();

		for (Field field : fields.values()) {
			_addField(field, solrInputDocument);
		}

		return solrInputDocument;
	}

	private static void _addField(
		Field field, SolrInputDocument solrInputDocument) {

		for (Object value : field.getValues()) {
			solrInputDocument.addField(field.getName(), _toSolrValue(value));
		}
	}

	private static void _addField(
		SolrInputDocument solrInputDocument,
		com.liferay.portal.kernel.search.Field field, String value,
		String localizedName) {

		GeoLocationPoint geoLocationPoint = field.getGeoLocationPoint();

		if (geoLocationPoint != null) {
			value =
				geoLocationPoint.getLatitude() + StringPool.COMMA +
					geoLocationPoint.getLongitude();
		}

		solrInputDocument.addField(localizedName, value);

		if (field.isSortable()) {
			solrInputDocument.addField(
				com.liferay.portal.kernel.search.Field.getSortableFieldName(
					localizedName),
				value);
		}
	}

	private static Object _toSolrValue(Object value) {
		if (value instanceof GeoLocationPoint) {
			GeoLocationPoint geoLocationPoint = (GeoLocationPoint)value;

			if (geoLocationPoint != null) {
				value =
					geoLocationPoint.getLatitude() + StringPool.COMMA +
						geoLocationPoint.getLongitude();
			}
		}

		return value;
	}

}