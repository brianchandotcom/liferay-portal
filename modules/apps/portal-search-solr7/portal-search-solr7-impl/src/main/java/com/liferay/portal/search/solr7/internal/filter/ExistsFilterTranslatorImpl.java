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

package com.liferay.portal.search.solr7.internal.filter;

import com.liferay.portal.kernel.search.filter.ExistsFilter;

import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.MatchAllDocsQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TermRangeQuery;

import org.osgi.service.component.annotations.Component;

/**
 * @author Michael C. Han
 */
@Component(immediate = true, service = ExistsFilterTranslator.class)
public class ExistsFilterTranslatorImpl implements ExistsFilterTranslator {

	@Override
	public Query translate(ExistsFilter existsFilter) {
		BooleanQuery.Builder builder = new BooleanQuery.Builder();

		builder.add(new MatchAllDocsQuery(), BooleanClause.Occur.SHOULD);

		builder.add(
			TermRangeQuery.newStringRange(
				existsFilter.getField(), "*", "*", true, true),
			BooleanClause.Occur.MUST);

		return builder.build();
	}

}