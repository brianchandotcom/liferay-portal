/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.query.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.search.geolocation.GeoDistance;
import com.liferay.portal.search.geolocation.GeoLocationPoint;
import com.liferay.portal.search.query.BooleanQuery;
import com.liferay.portal.search.query.BoostingQuery;
import com.liferay.portal.search.query.CommonTermsQuery;
import com.liferay.portal.search.query.ConstantScoreQuery;
import com.liferay.portal.search.query.DateRangeTermQuery;
import com.liferay.portal.search.query.DisMaxQuery;
import com.liferay.portal.search.query.ExistsQuery;
import com.liferay.portal.search.query.FunctionScoreQuery;
import com.liferay.portal.search.query.FuzzyQuery;
import com.liferay.portal.search.query.GeoBoundingBoxQuery;
import com.liferay.portal.search.query.GeoDistanceQuery;
import com.liferay.portal.search.query.GeoDistanceRangeQuery;
import com.liferay.portal.search.query.GeoPolygonQuery;
import com.liferay.portal.search.query.GeoShapeQuery;
import com.liferay.portal.search.query.IdsQuery;
import com.liferay.portal.search.query.MatchAllQuery;
import com.liferay.portal.search.query.MatchPhrasePrefixQuery;
import com.liferay.portal.search.query.MatchPhraseQuery;
import com.liferay.portal.search.query.MatchQuery;
import com.liferay.portal.search.query.MoreLikeThisQuery;
import com.liferay.portal.search.query.MultiMatchQuery;
import com.liferay.portal.search.query.NestedQuery;
import com.liferay.portal.search.query.PercolateQuery;
import com.liferay.portal.search.query.PrefixQuery;
import com.liferay.portal.search.query.Queries;
import com.liferay.portal.search.query.Query;
import com.liferay.portal.search.query.RangeTermQuery;
import com.liferay.portal.search.query.RegexQuery;
import com.liferay.portal.search.query.ScriptQuery;
import com.liferay.portal.search.query.SimpleStringQuery;
import com.liferay.portal.search.query.StringQuery;
import com.liferay.portal.search.query.TermQuery;
import com.liferay.portal.search.query.TermsQuery;
import com.liferay.portal.search.query.TermsSetQuery;
import com.liferay.portal.search.query.WildcardQuery;
import com.liferay.portal.search.query.WrapperQuery;
import com.liferay.portal.search.script.Script;
import com.liferay.portal.search.test.util.SearchTestRule;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Bryan Engler
 */
@RunWith(Arquillian.class)
public class QueriesInstantiationTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testBooleanQuery() {
		BooleanQuery booleanQuery = _queries.booleanQuery();

		Assert.assertNotNull(booleanQuery);
	}

	@Test
	public void testBoostingQuery() {
		Query positiveQuery = null;
		Query negativeQuery = null;

		BoostingQuery boostingQuery = _queries.boosting(
			positiveQuery, negativeQuery);

		Assert.assertNotNull(boostingQuery);
	}

	@Test
	public void testCommonTermsQuery() {
		CommonTermsQuery commonTermsQuery = _queries.commonTerms(
			"field", "text");

		Assert.assertNotNull(commonTermsQuery);
	}

	@Test
	public void testConstantScoreQuery() {
		Query query = null;

		ConstantScoreQuery constantScoreQuery = _queries.constantScore(query);

		Assert.assertNotNull(constantScoreQuery);
	}

	@Test
	public void testDateRangeTermQuery() {
		boolean includesLower = true;
		boolean includesUpper = true;

		DateRangeTermQuery dateRangeTermQuery = _queries.dateRangeTerm(
			"field", includesLower, includesUpper, "startDate", "endDate");

		Assert.assertNotNull(dateRangeTermQuery);
	}

	@Test
	public void testDisMaxQuery() {
		DisMaxQuery disMaxQuery = _queries.disMax();

		Assert.assertNotNull(disMaxQuery);
	}

	@Test
	public void testDocumentIdentifier() {
		MoreLikeThisQuery.DocumentIdentifier documentIdentifier =
			_queries.documentIdentifier("index", "type", "id");

		Assert.assertNotNull(documentIdentifier);
	}

	@Test
	public void testExistsQuery() {
		ExistsQuery existsQuery = _queries.exists("field");

		Assert.assertNotNull(existsQuery);
	}

	@Test
	public void testFunctionScoreQuery() {
		Query query = null;

		FunctionScoreQuery functionScoreQuery = _queries.functionScore(query);

		Assert.assertNotNull(functionScoreQuery);
	}

	@Test
	public void testFuzzyQuery() {
		FuzzyQuery fuzzyQuery = _queries.fuzzy("field", "value");

		Assert.assertNotNull(fuzzyQuery);
	}

	@Test
	public void testGeoBoundingBoxQuery() {
		GeoLocationPoint topLeftGeoLocationPoint = null;
		GeoLocationPoint bottomRightGeoLocationPoint = null;

		GeoBoundingBoxQuery geoBoundingBoxQuery = _queries.geoBoundingBox(
			"field", topLeftGeoLocationPoint, bottomRightGeoLocationPoint);

		Assert.assertNotNull(geoBoundingBoxQuery);
	}

	@Test
	public void testGeoDistanceQuery() {
		GeoLocationPoint pinGeoLocationPoint = null;
		GeoDistance geoDistance = null;

		GeoDistanceQuery geoDistanceQuery = _queries.geoDistance(
			"field", pinGeoLocationPoint, geoDistance);

		Assert.assertNotNull(geoDistanceQuery);
	}

	@Test
	public void testGeoDistanceRangeQuery() {
		boolean includesLower = true;
		boolean includesUpper = true;
		GeoDistance lowerBoundGeoDistance = null;
		GeoLocationPoint pinGeoLocationPoint = null;
		GeoDistance upperBoundGeoDistance = null;

		GeoDistanceRangeQuery geoDistanceRangeQuery = _queries.geoDistanceRange(
			"field", includesLower, includesUpper, lowerBoundGeoDistance,
			pinGeoLocationPoint, upperBoundGeoDistance);

		Assert.assertNotNull(geoDistanceRangeQuery);
	}

	@Test
	public void testGeoPolygonQuery() {
		GeoPolygonQuery geoPolygonQuery = _queries.geoPolygon("field");

		Assert.assertNotNull(geoPolygonQuery);
	}

	@Test
	public void testGeoShapeQuery1() {
		Assert.assertNotNull(_queries.geoShape("field", null));
	}

	@Test
	public void testGeoShapeQuery2() {
		GeoShapeQuery geoShapeQuery = _queries.geoShape(
			"field", "indexedShapeId", "indexedShapeType");

		Assert.assertNotNull(geoShapeQuery);
	}

	@Test
	public void testIdsQuery() {
		IdsQuery idsQuery = _queries.ids();

		Assert.assertNotNull(idsQuery);
	}

	@Test
	public void testMatchAllQuery() {
		MatchAllQuery matchAllQuery = _queries.matchAll();

		Assert.assertNotNull(matchAllQuery);
	}

	@Test
	public void testMatchPhrasePrefixQuery() {
		Object value = null;

		MatchPhrasePrefixQuery matchPhrasePrefixQuery =
			_queries.matchPhrasePrefix("field", value);

		Assert.assertNotNull(matchPhrasePrefixQuery);
	}

	@Test
	public void testMatchPhraseQuery() {
		Object value = null;

		MatchPhraseQuery matchPhraseQuery = _queries.matchPhrase(
			"field", value);

		Assert.assertNotNull(matchPhraseQuery);
	}

	@Test
	public void testMatchQuery() {
		Object value = null;

		MatchQuery matchQuery = _queries.match("field", value);

		Assert.assertNotNull(matchQuery);
	}

	@Test
	public void testMoreLikeThisQuery1() {
		MoreLikeThisQuery.DocumentIdentifier documentIdentifier =
			_queries.documentIdentifier("index", "type", "id");

		MoreLikeThisQuery moreLikeThisQuery = _queries.moreLikeThis(
			Collections.singleton(documentIdentifier));

		Assert.assertNotNull(moreLikeThisQuery);
	}

	@Test
	public void testMoreLikeThisQuery2() {
		String[] fields = new String[0];

		MoreLikeThisQuery moreLikeThisQuery = _queries.moreLikeThis(
			fields, "likeTexts");

		Assert.assertNotNull(moreLikeThisQuery);
	}

	@Test
	public void testMoreLikeThisQuery3() {
		List<String> fields = new ArrayList<>();

		MoreLikeThisQuery moreLikeThisQuery = _queries.moreLikeThis(
			fields, "likeTexts");

		Assert.assertNotNull(moreLikeThisQuery);
	}

	@Test
	public void testMultiMatchQuery1() {
		Object value = null;
		Set<String> fields = new HashSet<>();

		MultiMatchQuery multiMatchQuery = _queries.multiMatch(value, fields);

		Assert.assertNotNull(multiMatchQuery);
	}

	@Test
	public void testMultiMatchQuery2() {
		Object value = null;

		MultiMatchQuery multiMatchQuery = _queries.multiMatch(value, "fields");

		Assert.assertNotNull(multiMatchQuery);
	}

	@Test
	public void testNestedQuery() {
		Query query = null;

		NestedQuery nestedQuery = _queries.nested("path", query);

		Assert.assertNotNull(nestedQuery);
	}

	@Test
	public void testPercolateQuery() {
		List<String> documentJSONs = new ArrayList<>();

		PercolateQuery percolateQuery = _queries.percolate(
			"field", documentJSONs);

		Assert.assertNotNull(percolateQuery);
	}

	@Test
	public void testPrefixQuery() {
		PrefixQuery prefixQuery = _queries.prefix("field", "prefix");

		Assert.assertNotNull(prefixQuery);
	}

	@Test
	public void testRangeTermQuery1() {
		boolean includesLower = true;
		boolean includesUpper = true;

		RangeTermQuery rangeTermQuery = _queries.rangeTerm(
			"field", includesLower, includesUpper);

		Assert.assertNotNull(rangeTermQuery);
	}

	@Test
	public void testRangeTermQuery2() {
		boolean includesLower = true;
		boolean includesUpper = true;
		Object lowerBound = null;
		Object upperBound = null;

		RangeTermQuery rangeTermQuery = _queries.rangeTerm(
			"field", includesLower, includesUpper, lowerBound, upperBound);

		Assert.assertNotNull(rangeTermQuery);
	}

	@Test
	public void testRegexQuery() {
		RegexQuery regexQuery = _queries.regex("field", "regex");

		Assert.assertNotNull(regexQuery);
	}

	@Test
	public void testScriptQuery() {
		Script script = null;

		ScriptQuery scriptQuery = _queries.script(script);

		Assert.assertNotNull(scriptQuery);
	}

	@Test
	public void testSimpleStringQuery() {
		SimpleStringQuery simpleStringQuery = _queries.simpleString("query");

		Assert.assertNotNull(simpleStringQuery);
	}

	@Test
	public void testStringQuery() {
		StringQuery stringQuery = _queries.string("query");

		Assert.assertNotNull(stringQuery);
	}

	@Test
	public void testTermQuery() {
		Object value = null;

		TermQuery termQuery = _queries.term("field", value);

		Assert.assertNotNull(termQuery);
	}

	@Test
	public void testTermsQuery() {
		TermsQuery termsQuery = _queries.terms("field");

		Assert.assertNotNull(termsQuery);
	}

	@Test
	public void testTermsSetQuery() {
		List<Object> values = null;

		TermsSetQuery termsSetQuery = _queries.termsSet("fieldName", values);

		Assert.assertNotNull(termsSetQuery);
	}

	@Test
	public void testWildcardQuery() {
		WildcardQuery wildcardQuery = _queries.wildcard("field", "value");

		Assert.assertNotNull(wildcardQuery);
	}

	@Test
	public void testWrapperQuery() {
		WrapperQuery wrapperQuery = _queries.wrapper(
			"{\"query\":\"match_all\":{}}");

		Assert.assertNotNull(wrapperQuery);
	}

	@Rule
	public SearchTestRule searchTestRule = new SearchTestRule();

	@Inject
	private static Queries _queries;

}