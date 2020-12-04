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

package com.liferay.portal.search.internal.filter;

import com.liferay.portal.search.filter.ComplexQueryPart;
import com.liferay.portal.search.filter.ComplexQueryPartBuilderFactory;
import com.liferay.portal.search.internal.query.BooleanQueryImpl;
import com.liferay.portal.search.internal.query.QueriesImpl;
import com.liferay.portal.search.query.BooleanQuery;
import com.liferay.portal.search.query.DateRangeTermQuery;
import com.liferay.portal.search.query.FuzzyQuery;
import com.liferay.portal.search.query.MatchQuery;
import com.liferay.portal.search.query.NestedQuery;
import com.liferay.portal.search.query.Queries;
import com.liferay.portal.search.query.Query;
import com.liferay.portal.search.query.RangeTermQuery;
import com.liferay.portal.search.script.Scripts;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

/**
 * @author Wade Cao
 */
public class ComplexQueryBuilderImplTest {

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testFilterDateRangeTermQuery() {
		ComplexQueryBuilderImpl complexQueryBuilderImpl =
			new ComplexQueryBuilderImpl(_queries, _scripts);

		Query query = _getQuery(
			complexQueryBuilderImpl, "date_range", "[now/d now+1d/d[");

		Assert.assertTrue(query instanceof DateRangeTermQuery);
	}

	@Test
	public void testFilterDateRangeTermQueryInvalidValue() {
		ComplexQueryBuilderImpl complexQueryBuilderImpl =
			new ComplexQueryBuilderImpl(_queries, _scripts);

		List<Query> queries = _getQueries(
			complexQueryBuilderImpl, "date_range", "now/d now+1d/d[");

		Assert.assertTrue(queries.isEmpty());
	}

	@Test
	public void testFilterFuzzyQuery() {
		ComplexQueryBuilderImpl complexQueryBuilderImpl =
			new ComplexQueryBuilderImpl(_queries, _scripts);

		Query query = _getQuery(
			complexQueryBuilderImpl, "fuzzy", "it is fuzzyyyyy");

		Assert.assertTrue(query instanceof FuzzyQuery);
	}

	@Test
	public void testFilterMatchQuery() {
		ComplexQueryBuilderImpl complexQueryBuilderImpl =
			new ComplexQueryBuilderImpl(_queries, _scripts);

		Query query = _getQuery(complexQueryBuilderImpl, "match", "match-me");

		Assert.assertTrue(query instanceof MatchQuery);
	}

	@Test
	public void testFilterNestedQuery() {
		ComplexQueryBuilderImpl complexQueryBuilderImpl =
			new ComplexQueryBuilderImpl(_queries, _scripts);

		Query query = _getQuery(complexQueryBuilderImpl, "nested", "path");

		Assert.assertTrue(query instanceof NestedQuery);
	}

	@Test
	public void testFilterRangeTermQuery() {
		ComplexQueryBuilderImpl complexQueryBuilderImpl =
			new ComplexQueryBuilderImpl(_queries, _scripts);

		Query query = _getQuery(complexQueryBuilderImpl, "range", "]10 20]");

		Assert.assertTrue(query instanceof RangeTermQuery);
	}

	@Test
	public void testFilterRangeTermQueryInvalidValue() {
		ComplexQueryBuilderImpl complexQueryBuilderImpl =
			new ComplexQueryBuilderImpl(_queries, _scripts);

		List<Query> queries = _getQueries(
			complexQueryBuilderImpl, "range", "10 20]");

		Assert.assertTrue(queries.isEmpty());
	}

	@Test
	public void testInvalidType() {
		ComplexQueryBuilderImpl complexQueryBuilderImpl =
			new ComplexQueryBuilderImpl(_queries, _scripts);

		List<Query> queries = _getQueries(
			complexQueryBuilderImpl, "whatever", "[now/d now+1d/d[");

		Assert.assertTrue(queries.isEmpty());
	}

	@Test
	public void testInvalidType2() {
		ComplexQueryBuilderImpl complexQueryBuilderImpl =
			new ComplexQueryBuilderImpl(_queries, _scripts);

		List<Query> queries = _getQueries(
			complexQueryBuilderImpl, "whatever", "]10 20]");

		Assert.assertTrue(queries.isEmpty());
	}

	private List<Query> _getQueries(
		ComplexQueryBuilderImpl complexQueryBuilderImpl, String type,
		String value) {

		BooleanQuery booleanQuery = (BooleanQuery)complexQueryBuilderImpl.root(
			new BooleanQueryImpl()
		).addParts(
			new ArrayList<ComplexQueryPart>() {

				private static final long serialVersionUID = 1L;

				{
					add(
						_complexQueryPartBuilderFactory.builder(
						).boost(
							Float.valueOf(1.0F)
						).disabled(
							false
						).field(
							"modified"
						).name(
							"myTerm"
						).occur(
							"filter"
						).parent(
							"no parent"
						).type(
							type
						).value(
							value
						).build());
				}
			}
		).build();

		return booleanQuery.getFilterQueryClauses();
	}

	private Query _getQuery(
		ComplexQueryBuilderImpl complexQueryBuilderImpl, String type,
		String value) {

		List<Query> queries = _getQueries(complexQueryBuilderImpl, type, value);

		return queries.get(0);
	}

	private final ComplexQueryPartBuilderFactory
		_complexQueryPartBuilderFactory =
			new ComplexQueryPartBuilderFactoryImpl();
	private final Queries _queries = new QueriesImpl();

	@Mock
	private Scripts _scripts;

}