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

package com.liferay.portal.search.elasticsearch7.internal.sort;

import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Query;
import com.liferay.portal.kernel.search.generic.MatchQuery;
import com.liferay.portal.search.elasticsearch7.internal.LiferayElasticsearchIndexingFixtureFactory;
import com.liferay.portal.search.geolocation.GeoLocationPoint;
import com.liferay.portal.search.internal.geolocation.GeoLocationPointImpl;
import com.liferay.portal.search.internal.script.ScriptsImpl;
import com.liferay.portal.search.internal.sort.SortsImpl;
import com.liferay.portal.search.script.Script;
import com.liferay.portal.search.script.ScriptBuilder;
import com.liferay.portal.search.script.Scripts;
import com.liferay.portal.search.sort.FieldSort;
import com.liferay.portal.search.sort.GeoDistanceSort;
import com.liferay.portal.search.sort.ScoreSort;
import com.liferay.portal.search.sort.ScriptSort;
import com.liferay.portal.search.sort.Sort;
import com.liferay.portal.search.sort.SortMode;
import com.liferay.portal.search.sort.SortOrder;
import com.liferay.portal.search.sort.Sorts;
import com.liferay.portal.search.test.util.DocumentsAssert;
import com.liferay.portal.search.test.util.indexing.DocumentCreationHelper;
import com.liferay.portal.search.test.util.indexing.DocumentCreationHelpers;
import com.liferay.portal.search.test.util.indexing.IndexingFixture;
import com.liferay.portal.search.test.util.sort.BaseNestedFieldsSortTestCase;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.Date;
import java.util.function.Function;
import java.util.stream.Stream;

import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Wade Cao
 */
public class ElasticsearchSortFieldTranslatorTest
	extends BaseNestedFieldsSortTestCase {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();
	}

	@After
	@Override
	public void tearDown() throws Exception {
		super.tearDown();
	}

	@Test
	public void testFieldSortKeyword() throws Exception {
		String fieldName = "userName";

		addDocuments(
			value -> DocumentCreationHelpers.singleKeyword(fieldName, value),
			Stream.of("beta", "alpha beta", "beta gamma"));

		FieldSort fieldSort = _sorts.field(fieldName, SortOrder.DESC);

		_assertOrder(
			new Sort[] {fieldSort}, fieldName, "[beta gamma, beta, alpha beta]",
			null);
	}

	@Test
	public void testFieldSortNumber() throws Exception {
		_addDocuments(
			value -> document -> {
				document.addDate(
					Field.MODIFIED_DATE, new Date(value.longValue()));
				document.addNumber(Field.PRIORITY, value);
			},
			new double[] {1, 2, 3});

		FieldSort fieldSort = _sorts.field(Field.PRIORITY, SortOrder.DESC);

		_assertOrder(
			new Sort[] {fieldSort}, Field.PRIORITY, "[3.0, 2.0, 1.0]", null);
	}

	@Test
	public void testFieldSortWithMissing() {
		String fieldName = "fieldSortWithMissing";

		addDocuments(
			value -> DocumentCreationHelpers.singleText(fieldName, value),
			Stream.of("delta", "alpha delta", "delta gamma"));

		FieldSort fieldSortMissing = _sorts.field(fieldName + "_String");

		fieldSortMissing.setMissing("_first");

		Query query = new MatchQuery(fieldName, "delta");

		_assertOrder(
			new Sort[] {fieldSortMissing}, fieldName,
			"[delta, alpha delta, delta gamma]", query);
	}

	@Test
	public void testFieldSortWithNestedSort() throws Exception {
		assertSort("nestedSortWithFieldSort");
	}

	@Test
	public void testGeoDistanceSortWithSortMode() throws Exception {
		String fieldName = Field.GEO_LOCATION;

		addDocument(
			DocumentCreationHelpers.singleGeoLocation(fieldName, 3.0, 9.0));
		addDocument(
			DocumentCreationHelpers.singleGeoLocation(fieldName, 40.0, 20.0));
		addDocument(
			DocumentCreationHelpers.singleGeoLocation(fieldName, 90.0, 98.0));

		GeoLocationPoint geoLocationPoint =
			GeoLocationPointImpl.fromLatitudeLongitude(1.0, 2.0);

		GeoDistanceSort geoDistanceSort = _sorts.geoDistance(fieldName);

		geoDistanceSort.addGeoLocationPoints(geoLocationPoint);
		geoDistanceSort.setSortMode(SortMode.MIN);
		geoDistanceSort.setSortOrder(SortOrder.DESC);

		_assertOrder(
			new Sort[] {geoDistanceSort}, fieldName,
			"[lat: 90.0, lon: 98.0, lat: 40.0, lon: 20.0, lat: 3.0, lon: 9.0]",
			null);
	}

	@Test
	public void testScoreSort() throws Exception {
		String fieldNameForScoreSort = "fieldNameForScoreSort";

		addDocuments(
			value -> DocumentCreationHelpers.singleText(
				fieldNameForScoreSort, value),
			Stream.of("beta", "alpha beta", "beta gamma", "gamma"));

		ScoreSort scoreSort = _sorts.score();

		scoreSort.setSortOrder(SortOrder.ASC);

		Query query = new MatchQuery(
			fieldNameForScoreSort, "beta beta beta gamma");

		_assertOrder(
			new Sort[] {scoreSort}, fieldNameForScoreSort,
			"[gamma, alpha beta, beta, beta gamma]", query);
	}

	@Test
	public void testScriptSort() throws Exception {
		_addDocuments(
			value -> document -> {
				document.addDate(
					Field.MODIFIED_DATE, new Date(value.longValue()));
				document.addNumber(Field.PRIORITY, value);
			},
			new double[] {1, 2, 3});

		ScriptBuilder scriptBuilder = _scripts.builder();

		Script script = scriptBuilder.idOrCode(
			"doc['priority'].value * 1.1"
		).language(
			"painless"
		).build();

		ScriptSort scriptSort = _sorts.script(
			script, ScriptSort.ScriptSortType.NUMBER);

		scriptSort.setSortOrder(SortOrder.DESC);

		_assertOrder(
			new Sort[] {scriptSort}, Field.PRIORITY, "[3.0, 2.0, 1.0]", null);
	}

	@Override
	@Test
	public void testSort1() throws Exception {
	}

	@Override
	@Test
	public void testSort2() throws Exception {
	}

	@Override
	@Test
	public void testSort3() throws Exception {
	}

	@Override
	protected IndexingFixture createIndexingFixture() throws Exception {
		return LiferayElasticsearchIndexingFixtureFactory.getInstance();
	}

	private void _addDocuments(
			Function<Double, DocumentCreationHelper> function, double... values)
		throws Exception {

		for (double value : values) {
			addDocument(function.apply(value));
		}
	}

	private void _assertOrder(Sort[] sorts, String fieldName, String expected) {
		_assertOrder(sorts, fieldName, expected, null);
	}

	private void _assertOrder(
		Sort[] sorts, String fieldName, String expected, Query query) {

		assertSearch(
			indexingTestHelper -> {
				indexingTestHelper.defineRequest(
					searchRequestBuilder -> searchRequestBuilder.sorts(sorts));

				if (query != null) {
					indexingTestHelper.setQuery(query);
				}

				indexingTestHelper.search();

				indexingTestHelper.verify(
					hits -> DocumentsAssert.assertValues(
						indexingTestHelper.getRequestString(), hits.getDocs(),
						fieldName, expected));
			});
	}

	private static final Scripts _scripts = new ScriptsImpl();
	private static final Sorts _sorts = new SortsImpl();

}