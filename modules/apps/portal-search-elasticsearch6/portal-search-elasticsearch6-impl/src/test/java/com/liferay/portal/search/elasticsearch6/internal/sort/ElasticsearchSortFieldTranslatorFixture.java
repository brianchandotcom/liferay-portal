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

package com.liferay.portal.search.elasticsearch6.internal.sort;

import com.liferay.portal.search.query.QueryTranslator;

import org.elasticsearch.index.query.QueryBuilder;

/**
 * @author Michael C. Han
 */
public class ElasticsearchSortFieldTranslatorFixture {

	public ElasticsearchSortFieldTranslatorFixture(
		QueryTranslator<QueryBuilder> queryTranslator) {

		_elasticsearchQueryTranslator = new ElasticsearchSortFieldTranslator() {
			{
				setQueryTranslator(queryTranslator);
			}
		};
	}

	public ElasticsearchSortFieldTranslator getElasticsearchQueryTranslator() {
		return _elasticsearchQueryTranslator;
	}

	private final ElasticsearchSortFieldTranslator
		_elasticsearchQueryTranslator;

}