/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.opensearch2.internal.sort;

import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.search.query.QueryTranslator;

import org.opensearch.client.opensearch._types.query_dsl.QueryVariant;

/**
 * @author Michael C. Han
 */
public class OpenSearchSortFieldTranslatorFixture {

	public OpenSearchSortFieldTranslatorFixture(
		QueryTranslator<QueryVariant> queryTranslator) {

		ReflectionTestUtil.setFieldValue(
			_openSearchSortFieldTranslator, "_queryTranslator",
			queryTranslator);
	}

	public OpenSearchSortFieldTranslator getOpenSearchSortFieldTranslator() {
		return _openSearchSortFieldTranslator;
	}

	private final OpenSearchSortFieldTranslator _openSearchSortFieldTranslator =
		new OpenSearchSortFieldTranslator();

}