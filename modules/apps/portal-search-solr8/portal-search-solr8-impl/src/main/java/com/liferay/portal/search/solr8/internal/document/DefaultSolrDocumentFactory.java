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

import org.osgi.service.component.annotations.Component;

/**
 * @author Michael C. Han
 */
@Component(service = SolrDocumentFactory.class)
public class DefaultSolrDocumentFactory implements SolrDocumentFactory {

	@Override
	public SolrInputDocument getSolrInputDocument(
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

					addField(solrInputDocument, field, value, name);
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

					addField(
						solrInputDocument, field, value,
						com.liferay.portal.kernel.search.Field.getLocalizedName(
							locale, name));
				}
			}
		}

		return solrInputDocument;
	}

	@Override
	public SolrInputDocument getSolrInputDocument(Document document) {
		SolrInputDocument solrInputDocument = new SolrInputDocument();

		Map<String, Field> fields = document.getFields();

		for (Field field : fields.values()) {
			addField(field, solrInputDocument);
		}

		return solrInputDocument;
	}

	protected void addField(Field field, SolrInputDocument solrInputDocument) {
		for (Object value : field.getValues()) {
			solrInputDocument.addField(field.getName(), toSolrValue(value));
		}
	}

	protected void addField(
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

	protected Object toSolrValue(Object value) {
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