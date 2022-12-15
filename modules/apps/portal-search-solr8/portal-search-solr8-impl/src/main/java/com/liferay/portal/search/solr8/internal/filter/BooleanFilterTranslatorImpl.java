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

package com.liferay.portal.search.solr8.internal.filter;

import com.liferay.portal.kernel.search.BooleanClause;
import com.liferay.portal.kernel.search.filter.BooleanFilter;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.search.filter.FilterVisitor;

import java.util.List;

import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.MatchAllDocsQuery;
import org.apache.lucene.search.Query;

import org.osgi.service.component.annotations.Component;

/**
 * @author Michael C. Han
 */
@Component(service = BooleanFilterTranslator.class)
public class BooleanFilterTranslatorImpl implements BooleanFilterTranslator {

	@Override
	public Query translate(
		BooleanFilter booleanFilter, FilterVisitor<Query> filterVisitor) {

		BooleanQuery.Builder builder = new BooleanQuery.Builder();

		for (BooleanClause<Filter> booleanClause :
				booleanFilter.getMustBooleanClauses()) {

			builder.add(
				translate(booleanClause, filterVisitor),
				org.apache.lucene.search.BooleanClause.Occur.MUST);
		}

		for (BooleanClause<Filter> booleanClause :
				booleanFilter.getMustNotBooleanClauses()) {

			builder.add(
				translate(booleanClause, filterVisitor),
				org.apache.lucene.search.BooleanClause.Occur.MUST_NOT);
		}

		if (_isOnlyMustNotClauses(booleanFilter)) {
			builder.add(
				new MatchAllDocsQuery(),
				org.apache.lucene.search.BooleanClause.Occur.SHOULD);
		}

		for (BooleanClause<Filter> booleanClause :
				booleanFilter.getShouldBooleanClauses()) {

			builder.add(
				translate(booleanClause, filterVisitor),
				org.apache.lucene.search.BooleanClause.Occur.SHOULD);
		}

		return builder.build();
	}

	protected Query translate(
		BooleanClause<Filter> booleanClause,
		FilterVisitor<Query> filterVisitor) {

		Filter filter = booleanClause.getClause();

		return filter.accept(filterVisitor);
	}

	private boolean _isOnlyMustNotClauses(BooleanFilter booleanFilter) {
		List<BooleanClause<Filter>> booleanClauses =
			booleanFilter.getMustBooleanClauses();

		if (!booleanClauses.isEmpty()) {
			return false;
		}

		booleanClauses = booleanFilter.getShouldBooleanClauses();

		if (!booleanClauses.isEmpty()) {
			return false;
		}

		booleanClauses = booleanFilter.getMustNotBooleanClauses();

		if (!booleanClauses.isEmpty()) {
			return true;
		}

		return false;
	}

}