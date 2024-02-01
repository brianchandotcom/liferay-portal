/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.opensearch2.internal.sort;

import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Query;
import com.liferay.portal.kernel.search.generic.MatchQuery;
import com.liferay.portal.search.internal.geolocation.GeoLocationPointImpl;
import com.liferay.portal.search.internal.script.ScriptsImpl;
import com.liferay.portal.search.internal.sort.SortsImpl;
import com.liferay.portal.search.opensearch2.internal.OpenSearchTestRule;
import com.liferay.portal.search.opensearch2.internal.indexing.LiferayOpenSearchIndexingFixtureFactory;
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

import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

/**
 * @author Wade Cao
 * @author Petteri Karttunen
 */
public class OpenSearchSortFieldTranslatorTest
	extends BaseNestedFieldsSortTestCase {

	@ClassRule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@ClassRule
	public static final OpenSearchTestRule openSearchTestRule =
		OpenSearchTestRule.INSTANCE;

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
		addDocuments(
			value -> DocumentCreationHelpers.singleKeyword(
				Field.USER_NAME, value),
			"beta", "alpha beta", "beta gamma");

		_assertOrder(
			"[beta gamma, beta, alpha beta]", Field.USER_NAME, null,
			new Sort[] {_sorts.field(Field.USER_NAME, SortOrder.DESC)});
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

		_assertOrder(
			"[3.0, 2.0, 1.0]", Field.PRIORITY, null,
			new Sort[] {_sorts.field(Field.PRIORITY, SortOrder.DESC)});
	}

	@Test
	public void testFieldSortWithMissing() {
		String fieldName = "fieldSortWithMissing";

		addDocuments(
			value -> DocumentCreationHelpers.singleText(fieldName, value),
			"delta", "alpha delta", "delta gamma");

		FieldSort fieldSort = _sorts.field(fieldName + "_String");

		fieldSort.setMissing("_first");

		_assertOrder(
			"[delta, alpha delta, delta gamma]", fieldName,
			new MatchQuery(fieldName, "delta"), new Sort[] {fieldSort});
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

		GeoDistanceSort geoDistanceSort = _sorts.geoDistance(fieldName);

		geoDistanceSort.addGeoLocationPoints(
			GeoLocationPointImpl.fromLatitudeLongitude(1.0, 2.0));
		geoDistanceSort.setSortMode(SortMode.MIN);
		geoDistanceSort.setSortOrder(SortOrder.DESC);

		_assertOrder(
			"[lat: 90.0, lon: 98.0, lat: 40.0, lon: 20.0, lat: 3.0, lon: 9.0]",
			fieldName, null, new Sort[] {geoDistanceSort});
	}

	@Test
	public void testScoreSort() throws Exception {
		String fieldNameForScoreSort = "fieldNameForScoreSort";

		addDocuments(
			value -> DocumentCreationHelpers.singleText(
				fieldNameForScoreSort, value),
			"beta", "alpha beta", "beta gamma", "gamma");

		ScoreSort scoreSort = _sorts.score();

		scoreSort.setSortOrder(SortOrder.ASC);

		_assertOrder(
			"[gamma, alpha beta, beta, beta gamma]", fieldNameForScoreSort,
			new MatchQuery(fieldNameForScoreSort, "beta beta beta gamma"),
			new Sort[] {scoreSort});
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
			"[3.0, 2.0, 1.0]", Field.PRIORITY, null, new Sort[] {scriptSort});
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
		return LiferayOpenSearchIndexingFixtureFactory.getInstance();
	}

	private void _addDocuments(
			Function<Double, DocumentCreationHelper> function, double... values)
		throws Exception {

		for (double value : values) {
			addDocument(function.apply(value));
		}
	}

	private void _assertOrder(
		String expected, String fieldName, Query query, Sort[] sorts) {

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