/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.opensearch2.internal.filter;

import com.liferay.portal.kernel.search.query.QueryTranslator;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.search.opensearch2.internal.geolocation.GeoTranslator;

import org.opensearch.client.opensearch._types.query_dsl.QueryVariant;

/**
 * @author Michael C. Han
 */
public class OpenSearchFilterTranslatorFixture {

	public OpenSearchFilterTranslatorFixture(
		QueryTranslator<QueryVariant> queryTranslator) {

		OpenSearchFilterTranslator openSearchFilterTranslator =
			new OpenSearchFilterTranslator();

		ReflectionTestUtil.setFieldValue(
			openSearchFilterTranslator, "_geoTranslator", new GeoTranslator());
		ReflectionTestUtil.setFieldValue(
			openSearchFilterTranslator, "_queryTranslator", queryTranslator);

		_openSearchFilterTranslator = openSearchFilterTranslator;
	}

	public OpenSearchFilterTranslator getOpenSearchFilterTranslator() {
		return _openSearchFilterTranslator;
	}

	private final OpenSearchFilterTranslator _openSearchFilterTranslator;

}