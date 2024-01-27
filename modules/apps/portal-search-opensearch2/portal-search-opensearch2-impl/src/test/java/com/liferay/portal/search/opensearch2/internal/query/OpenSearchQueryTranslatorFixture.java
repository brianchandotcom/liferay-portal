/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.opensearch2.internal.query;

import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.search.opensearch2.internal.geolocation.GeoTranslator;
import com.liferay.portal.search.opensearch2.internal.script.ScriptTranslator;

/**
 * @author Michael C. Han
 */
public class OpenSearchQueryTranslatorFixture {

	public OpenSearchQueryTranslatorFixture() {
		ReflectionTestUtil.setFieldValue(
			_openSearchQueryTranslator, "_geoTranslator", new GeoTranslator());
		ReflectionTestUtil.setFieldValue(
			_openSearchQueryTranslator, "_scriptTranslator",
			new ScriptTranslator());
	}

	public OpenSearchQueryTranslator getOpenSearchQueryTranslator() {
		return _openSearchQueryTranslator;
	}

	private final OpenSearchQueryTranslator _openSearchQueryTranslator =
		new OpenSearchQueryTranslator();

}