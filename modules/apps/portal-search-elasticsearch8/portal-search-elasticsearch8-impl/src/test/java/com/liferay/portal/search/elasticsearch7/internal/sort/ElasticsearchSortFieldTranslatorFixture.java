/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.elasticsearch7.internal.sort;

import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.search.query.QueryTranslator;

import org.elasticsearch.index.query.QueryBuilder;

/**
 * @author Michael C. Han
 */
public class ElasticsearchSortFieldTranslatorFixture {

	public ElasticsearchSortFieldTranslatorFixture(
		QueryTranslator<QueryBuilder> queryTranslator) {

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