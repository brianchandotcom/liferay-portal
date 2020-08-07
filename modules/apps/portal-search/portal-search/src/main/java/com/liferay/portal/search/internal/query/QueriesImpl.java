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

package com.liferay.portal.search.internal.query;

import com.liferay.portal.search.geolocation.GeoDistance;
import com.liferay.portal.search.geolocation.GeoLocationPoint;
import com.liferay.portal.search.geolocation.Shape;
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

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.osgi.service.component.annotations.Component;

/**
 * @author Wade Cao
 */
@Component(immediate = true, service = Queries.class)
public class QueriesImpl implements Queries {

	@Override
	public BooleanQuery booleanQuery() {
		return new BooleanQueryImpl();
	}

	@Override
	public BoostingQuery boosting(Query positiveQuery, Query negativeQuery) {
		return new BoostingQueryImpl(positiveQuery, negativeQuery);
	}

	@Override
	public CommonTermsQuery commonTerms(String field, String text) {
		return new CommonTermsQueryImpl(field, text);
	}

	@Override
	public ConstantScoreQuery constantScore(Query query) {
		return new ConstantScoreQueryImpl(query);
	}

	@Override
	public DateRangeTermQuery dateRangeTerm(
		String field, boolean includesLower, boolean includesUpper,
		String startDate, String endDate) {

		return new DateRangeTermQueryImpl(
			field, includesLower, includesUpper, startDate, endDate);
	}

	@Override
	public DisMaxQuery disMax() {
		return new DisMaxQueryImpl();
	}

	@Override
	public MoreLikeThisQuery.DocumentIdentifier documentIdentifier(
		String index, String type, String id) {

		return new MoreLikeThisQueryImpl.DocumentIdentifierImpl(
			index, type, id);
	}

	@Override
	public ExistsQuery exists(String field) {
		return new ExistsQueryImpl(field);
	}

	@Override
	public FunctionScoreQuery functionScore(Query query) {
		return new FunctionScoreQueryImpl(query);
	}

	@Override
	public FuzzyQuery fuzzy(String field, String value) {
		return new FuzzyQueryImpl(field, value);
	}

	@Override
	public GeoBoundingBoxQuery geoBoundingBox(
		String field, GeoLocationPoint topLeftGeoLocationPoint,
		GeoLocationPoint bottomRightGeoLocationPoint) {

		return new GeoBoundingBoxQueryImpl(
			field, topLeftGeoLocationPoint, bottomRightGeoLocationPoint);
	}

	@Override
	public GeoDistanceQuery geoDistance(
		String field, GeoLocationPoint pinGeoLocationPoint,
		GeoDistance geoDistance) {

		return new GeoDistanceQueryImpl(
			field, pinGeoLocationPoint, geoDistance);
	}

	@Override
	public GeoDistanceRangeQuery geoDistanceRange(
		String field, boolean includesLower, boolean includesUpper,
		GeoDistance lowerBoundGeoDistance, GeoLocationPoint pinGeoLocationPoint,
		GeoDistance upperBoundGeoDistance) {

		return new GeoDistanceRangeQueryImpl(
			field, includesLower, includesUpper, lowerBoundGeoDistance,
			pinGeoLocationPoint, upperBoundGeoDistance);
	}

	@Override
	public GeoPolygonQuery geoPolygon(String field) {
		return new GeoPolygonQueryImpl(field);
	}

	@Override
	public GeoShapeQuery geoShape(String field, Shape shape) {
		return new GeoShapeQueryImpl(field, shape);
	}

	@Override
	public GeoShapeQuery geoShape(
		String field, String indexedShapeId, String indexedShapeType) {

		return new GeoShapeQueryImpl(field, indexedShapeId, indexedShapeType);
	}

	@Override
	public IdsQuery ids() {
		return new IdsQueryImpl();
	}

	@Override
	public MatchQuery match(String field, Object value) {
		return new MatchQueryImpl(field, value);
	}

	@Override
	public MatchAllQuery matchAll() {
		return new MatchAllQueryImpl();
	}

	@Override
	public MatchPhraseQuery matchPhrase(String field, Object value) {
		return new MatchPhraseQueryImpl(field, value);
	}

	@Override
	public MatchPhrasePrefixQuery matchPhrasePrefix(
		String field, Object value) {

		return new MatchPhrasePrefixQueryImpl(field, value);
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 *             #moreLikeThis(List, String...)}
	 */
	@Deprecated
	@Override
	public MoreLikeThisQuery moreLikeThis(List<String> likeTexts) {
		return new MoreLikeThisQueryImpl(
			Collections.emptyList(), likeTexts.toArray(new String[0]));
	}

	@Override
	public MoreLikeThisQuery moreLikeThis(
		List<String> fields, String... likeTexts) {

		return new MoreLikeThisQueryImpl(fields, likeTexts);
	}

	@Override
	public MoreLikeThisQuery moreLikeThis(
		Set<MoreLikeThisQuery.DocumentIdentifier> documentIdentifiers) {

		return new MoreLikeThisQueryImpl(documentIdentifiers);
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 *             #moreLikeThis(List, String...)}
	 */
	@Deprecated
	@Override
	public MoreLikeThisQuery moreLikeThis(String... likeTexts) {
		return new MoreLikeThisQueryImpl(Collections.emptyList(), likeTexts);
	}

	@Override
	public MoreLikeThisQuery moreLikeThis(
		String[] fields, String... likeTexts) {

		return new MoreLikeThisQueryImpl(fields, likeTexts);
	}

	@Override
	public MultiMatchQuery multiMatch(
		Object value, Map<String, Float> fieldsBoosts) {

		return new MultiMatchQueryImpl(value, fieldsBoosts);
	}

	@Override
	public MultiMatchQuery multiMatch(Object value, Set<String> fields) {
		return new MultiMatchQueryImpl(value, fields);
	}

	@Override
	public MultiMatchQuery multiMatch(Object value, String... fields) {
		return new MultiMatchQueryImpl(value, fields);
	}

	@Override
	public NestedQuery nested(String path, Query query) {
		return new NestedQueryImpl(path, query);
	}

	@Override
	public PercolateQuery percolate(String field, List<String> documentJSONs) {
		return new PercolateQueryImpl(field, documentJSONs);
	}

	@Override
	public PrefixQuery prefix(String field, String prefix) {
		return new PrefixQueryImpl(field, prefix);
	}

	@Override
	public RangeTermQuery rangeTerm(
		String field, boolean includesLower, boolean includesUpper) {

		return new RangeTermQueryImpl(field, includesLower, includesUpper);
	}

	@Override
	public RangeTermQuery rangeTerm(
		String field, boolean includesLower, boolean includesUpper,
		Object lowerBound, Object upperBound) {

		return new RangeTermQueryImpl(
			field, includesLower, includesUpper, lowerBound, upperBound);
	}

	@Override
	public RegexQuery regex(String field, String regex) {
		return new RegexQueryImpl(field, regex);
	}

	@Override
	public ScriptQuery script(Script script) {
		return new ScriptQueryImpl(script);
	}

	@Override
	public SimpleStringQuery simpleString(String query) {
		return new SimpleStringQueryImpl(query);
	}

	@Override
	public StringQuery string(String query) {
		return new StringQueryImpl(query);
	}

	@Override
	public TermQuery term(String field, Object value) {
		return new TermQueryImpl(field, value);
	}

	@Override
	public TermsQuery terms(String field) {
		return new TermsQueryImpl(field);
	}

	@Override
	public TermsSetQuery termsSet(String fieldName, List<Object> values) {
		return new TermsSetQueryImpl(fieldName, values);
	}

	@Override
	public WildcardQuery wildcard(String field, String value) {
		return new WildcardQueryImpl(field, value);
	}

	@Override
	public WrapperQuery wrapper(byte[] source) {
		return new WrapperQueryImpl(source);
	}

	@Override
	public WrapperQuery wrapper(String source) {
		return new WrapperQueryImpl(source);
	}

}