/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.elasticsearch8.internal.legacy.query;

import co.elastic.clients.elasticsearch._types.query_dsl.QueryVariant;

import com.liferay.portal.kernel.module.service.Snapshot;
import com.liferay.portal.kernel.search.filter.FilterTranslator;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.search.elasticsearch8.internal.filter.ElasticsearchFilterTranslator;

/**
 * @author Michael C. Han
 */
public class ElasticsearchQueryTranslatorFixture {

	public ElasticsearchQueryTranslatorFixture() {
		ReflectionTestUtil.setFieldValue(
			_elasticsearchQueryTranslator, "_filterTranslatorSnapshot",
			new Snapshot<FilterTranslator<QueryVariant>>(null, null) {

				@Override
				public FilterTranslator<QueryVariant> get() {
					return new ElasticsearchFilterTranslator();
				}

			});
	}

	public ElasticsearchQueryTranslator getElasticsearchQueryTranslator() {
		return _elasticsearchQueryTranslator;
	}

	private final ElasticsearchQueryTranslator _elasticsearchQueryTranslator =
		new ElasticsearchQueryTranslator();

}