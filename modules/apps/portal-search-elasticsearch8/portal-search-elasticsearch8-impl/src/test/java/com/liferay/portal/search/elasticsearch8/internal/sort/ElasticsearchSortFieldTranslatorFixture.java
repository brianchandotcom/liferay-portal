/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.elasticsearch8.internal.sort;

import co.elastic.clients.elasticsearch._types.query_dsl.QueryVariant;

import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.search.query.QueryTranslator;

/**
 * @author Michael C. Han
 */
public class ElasticsearchSortFieldTranslatorFixture {

	public ElasticsearchSortFieldTranslatorFixture(
		QueryTranslator<QueryVariant> queryTranslator) {

		ReflectionTestUtil.setFieldValue(
			_elasticsearchSortFieldTranslator, "_queryTranslator",
			queryTranslator);
	}

	public ElasticsearchSortFieldTranslator
		getElasticsearchSortFieldTranslator() {

		return _elasticsearchSortFieldTranslator;
	}

	private final ElasticsearchSortFieldTranslator
		_elasticsearchSortFieldTranslator =
			new ElasticsearchSortFieldTranslator();

}