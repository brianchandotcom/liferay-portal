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

package com.liferay.portal.search.elasticsearch.internal.filter;

import com.liferay.portal.kernel.search.BooleanClause;
import com.liferay.portal.kernel.search.filter.BooleanFilter;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.search.filter.FilterVisitor;
import com.liferay.portal.search.elasticsearch.filter.BooleanFilterTranslator;

import org.elasticsearch.index.query.BoolFilterBuilder;
import org.elasticsearch.index.query.FilterBuilder;
import org.elasticsearch.index.query.FilterBuilders;

import org.osgi.service.component.annotations.Component;

/**
 * @author Michael C. Han
 */
@Component(immediate = true, service = BooleanFilterTranslator.class)
public class BooleanFilterTranslatorImpl implements BooleanFilterTranslator {

	@Override
	public FilterBuilder translate(
		BooleanFilter booleanFilter,
		FilterVisitor<FilterBuilder> filterVisitor) {

		BoolFilterBuilder boolFilterBuilder = FilterBuilders.boolFilter();

		if (booleanFilter.isCached() != null) {
			boolFilterBuilder.cache(booleanFilter.isCached());
		}

		for (BooleanClause<Filter> booleanClause :
				booleanFilter.getMustBooleanClauses()) {

			FilterBuilder filterBuilder = translate(
				booleanClause, filterVisitor);

			boolFilterBuilder.must(filterBuilder);
		}

		for (BooleanClause<Filter> booleanClause :
				booleanFilter.getMustNotBooleanClauses()) {

			FilterBuilder filterBuilder = translate(
				booleanClause, filterVisitor);

			boolFilterBuilder.mustNot(filterBuilder);
		}

		for (BooleanClause<Filter> booleanClause :
				booleanFilter.getShouldBooleanClauses()) {

			FilterBuilder filterBuilder = translate(
				booleanClause, filterVisitor);

			boolFilterBuilder.should(filterBuilder);
		}

		return boolFilterBuilder;
	}

	protected FilterBuilder translate(
		BooleanClause<Filter> booleanClause,
		FilterVisitor<FilterBuilder> filterVisitor) {

		Filter filter = booleanClause.getClause();

		return filter.accept(filterVisitor);
	}

}