/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.elasticsearch7.internal.query;

import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;

/**
 * @author André de Oliveira
 */
public class QueryBuilderFactories {

	public static final QueryBuilderFactory MATCH = new QueryBuilderFactory() {

		@Override
		public QueryBuilder create(String field, String text) {
			return QueryBuilders.matchQuery(field, text);
		}

	};

	public static final QueryBuilderFactory MATCH_PHRASE_PREFIX =
		new QueryBuilderFactory() {

			@Override
			public QueryBuilder create(String name, String text) {
				return QueryBuilders.matchPhrasePrefixQuery(name, text);
			}

		};

}