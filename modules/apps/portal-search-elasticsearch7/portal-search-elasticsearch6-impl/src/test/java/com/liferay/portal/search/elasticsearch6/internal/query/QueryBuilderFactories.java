/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portal.search.elasticsearch6.internal.query;

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