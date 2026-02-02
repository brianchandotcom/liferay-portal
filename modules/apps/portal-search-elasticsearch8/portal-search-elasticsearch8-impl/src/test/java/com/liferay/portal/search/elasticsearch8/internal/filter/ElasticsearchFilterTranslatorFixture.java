/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.elasticsearch8.internal.filter;

import co.elastic.clients.elasticsearch._types.query_dsl.QueryVariant;

import com.liferay.portal.kernel.search.query.QueryTranslator;
import com.liferay.portal.search.index.IndexNameBuilder;

import org.mockito.Mockito;

/**
 * @author Michael C. Han
 */
public class ElasticsearchFilterTranslatorFixture {

	public ElasticsearchFilterTranslatorFixture(
		QueryTranslator<QueryVariant> queryTranslator) {

		_elasticsearchFilterTranslator = new ElasticsearchFilterTranslator() {
			{
				indexNameBuilder = _createIndexNameBuilder();

				activate();
			}
		};
	}

	public ElasticsearchFilterTranslator getElasticsearchFilterTranslator() {
		return _elasticsearchFilterTranslator;
	}

	private IndexNameBuilder _createIndexNameBuilder() {
		IndexNameBuilder indexNameBuilder = Mockito.mock(
			IndexNameBuilder.class);

		Mockito.when(
			indexNameBuilder.getIndexName(Mockito.anyLong())
		).then(
			invocation -> String.valueOf(invocation.getArgument(0, Long.class))
		);

		return indexNameBuilder;
	}

	private final ElasticsearchFilterTranslator _elasticsearchFilterTranslator;

}