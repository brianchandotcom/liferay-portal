/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.query;

import com.liferay.portal.search.geolocation.GeoDistance;
import com.liferay.portal.search.geolocation.GeoLocationPoint;
import com.liferay.portal.search.geolocation.Shape;
import com.liferay.portal.search.script.Script;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Shuyang Zhou
 */
public class QueriesUtil {

	public static BooleanQuery booleanQuery() {
		return new BooleanQuery();
	}

	public static BoostingQuery boosting(
		Query positiveQuery, Query negativeQuery) {

		return new BoostingQuery(positiveQuery, negativeQuery);
	}

	public static CommonTermsQuery commonTerms(String field, String text) {
		return new CommonTermsQuery(field, text);
	}

	public static ConstantScoreQuery constantScore(Query query) {
		return new ConstantScoreQuery(query);
	}

	public static DateRangeTermQuery dateRangeTerm(
		String field, boolean includesLower, boolean includesUpper,
		String startDate, String endDate) {

		return new DateRangeTermQuery(
			field, includesLower, includesUpper, startDate, endDate);
	}

	public static DisMaxQuery disMax() {
		return new DisMaxQuery();
	}

	public static MoreLikeThisQuery.DocumentIdentifier documentIdentifier(
		String index, String type, String id) {

		return new MoreLikeThisQuery.DocumentIdentifierImpl(index, type, id);
	}

	public static ExistsQuery exists(String field) {
		return new ExistsQuery(field);
	}

	public static FunctionScoreQuery functionScore(Query query) {
		return new FunctionScoreQuery(query);
	}

	public static FuzzyQuery fuzzy(String field, String value) {
		return new FuzzyQuery(field, value);
	}

	public static GeoBoundingBoxQuery geoBoundingBox(
		String field, GeoLocationPoint topLeftGeoLocationPoint,
		GeoLocationPoint bottomRightGeoLocationPoint) {

		return new GeoBoundingBoxQuery(
			field, topLeftGeoLocationPoint, bottomRightGeoLocationPoint);
	}

	public static GeoDistanceQuery geoDistance(
		String field, GeoLocationPoint pinGeoLocationPoint,
		GeoDistance geoDistance) {

		return new GeoDistanceQuery(field, pinGeoLocationPoint, geoDistance);
	}

	public static GeoDistanceRangeQuery geoDistanceRange(
		String field, boolean includesLower, boolean includesUpper,
		GeoDistance lowerBoundGeoDistance, GeoLocationPoint pinGeoLocationPoint,
		GeoDistance upperBoundGeoDistance) {

		return new GeoDistanceRangeQuery(
			field, includesLower, includesUpper, lowerBoundGeoDistance,
			pinGeoLocationPoint, upperBoundGeoDistance);
	}

	public static GeoPolygonQuery geoPolygon(String field) {
		return new GeoPolygonQuery(field);
	}

	public static GeoShapeQuery geoShape(String field, Shape shape) {
		return new GeoShapeQuery(field, shape);
	}

	public static GeoShapeQuery geoShape(
		String field, String indexedShapeId, String indexedShapeType) {

		return new GeoShapeQuery(field, indexedShapeId, indexedShapeType);
	}

	public static IdsQuery ids() {
		return new IdsQuery();
	}

	public static MatchQuery match(String field, Object value) {
		return new MatchQuery(field, value);
	}

	public static MatchAllQuery matchAll() {
		return new MatchAllQuery();
	}

	public static MatchPhraseQuery matchPhrase(String field, Object value) {
		return new MatchPhraseQuery(field, value);
	}

	public static MatchPhrasePrefixQuery matchPhrasePrefix(
		String field, Object value) {

		return new MatchPhrasePrefixQuery(field, value);
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 *             #moreLikeThis(List, String...)}
	 */
	@Deprecated
	public static MoreLikeThisQuery moreLikeThis(List<String> likeTexts) {
		return new MoreLikeThisQuery(
			Collections.emptyList(), likeTexts.toArray(new String[0]));
	}

	public static MoreLikeThisQuery moreLikeThis(
		List<String> fields, String... likeTexts) {

		return new MoreLikeThisQuery(fields, likeTexts);
	}

	public static MoreLikeThisQuery moreLikeThis(
		Set<MoreLikeThisQuery.DocumentIdentifier> documentIdentifiers) {

		return new MoreLikeThisQuery(documentIdentifiers);
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 *             #moreLikeThis(List, String...)}
	 */
	@Deprecated
	public static MoreLikeThisQuery moreLikeThis(String... likeTexts) {
		return new MoreLikeThisQuery(Collections.emptyList(), likeTexts);
	}

	public static MoreLikeThisQuery moreLikeThis(
		String[] fields, String... likeTexts) {

		return new MoreLikeThisQuery(fields, likeTexts);
	}

	public static MultiMatchQuery multiMatch(
		Object value, Map<String, Float> fieldsBoosts) {

		return new MultiMatchQuery(value, fieldsBoosts);
	}

	public static MultiMatchQuery multiMatch(Object value, Set<String> fields) {
		return new MultiMatchQuery(value, fields);
	}

	public static MultiMatchQuery multiMatch(Object value, String... fields) {
		return new MultiMatchQuery(value, fields);
	}

	public static NestedQuery nested(String path, Query query) {
		return new NestedQuery(path, query);
	}

	public static PercolateQuery percolate(
		String field, List<String> documentJSONs) {

		return new PercolateQuery(field, documentJSONs);
	}

	public static PrefixQuery prefix(String field, String prefix) {
		return new PrefixQuery(field, prefix);
	}

	public static RangeTermQuery rangeTerm(
		String field, boolean includesLower, boolean includesUpper) {

		return new RangeTermQuery(field, includesLower, includesUpper);
	}

	public static RangeTermQuery rangeTerm(
		String field, boolean includesLower, boolean includesUpper,
		Object lowerBound, Object upperBound) {

		return new RangeTermQuery(
			field, includesLower, includesUpper, lowerBound, upperBound);
	}

	public static RegexQuery regex(String field, String regex) {
		return new RegexQuery(field, regex);
	}

	public static ScriptQuery script(Script script) {
		return new ScriptQuery(script);
	}

	public static SimpleStringQuery simpleString(String query) {
		return new SimpleStringQuery(query);
	}

	public static StringQuery string(String query) {
		return new StringQuery(query);
	}

	public static TermQuery term(String field, Object value) {
		return new TermQuery(field, value);
	}

	public static TermsQuery terms(String field) {
		return new TermsQuery(field);
	}

	public static TermsSetQuery termsSet(
		String fieldName, List<Object> values) {

		return new TermsSetQuery(fieldName, values);
	}

	public static WildcardQuery wildcard(String field, String value) {
		return new WildcardQuery(field, value);
	}

	public static WrapperQuery wrapper(byte[] source) {
		return new WrapperQuery(source);
	}

	public static WrapperQuery wrapper(String source) {
		return new WrapperQuery(source);
	}

}