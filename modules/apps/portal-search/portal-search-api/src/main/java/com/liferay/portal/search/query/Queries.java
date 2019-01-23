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

package com.liferay.portal.search.query;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.search.geolocation.GeoDistance;
import com.liferay.portal.kernel.search.geolocation.GeoLocationPoint;
import com.liferay.portal.search.geolocation.ShapeBuilder;
import com.liferay.portal.search.script.Script;

import java.util.List;
import java.util.Set;

/**
 * @author Wade Cao
 */
@ProviderType
public interface Queries {

	public BooleanQuery booleanQuery();

	public BoostingQuery boosting(Query positiveQuery, Query negativeQuery);

	public CommonTermsQuery commonTerms(String field, String text);

	public ConstantScoreQuery constantScore(Query query);

	public DateRangeTermQuery dateRangeTerm(
		String field, boolean includesLower, boolean includesUpper,
		String startDate, String endDate);

	public DisMaxQuery disMax();

	public ExistsQuery exists(String field);

	public FunctionScoreQuery functionScore(Query query);

	public FuzzyQuery fuzzy(String field, String value);

	public GeoBoundingBoxQuery geoBoundingBox(
		String field, GeoLocationPoint topLeftGeoLocationPoint,
		GeoLocationPoint bottomRightGeoLocationPoint);

	public GeoDistanceQuery geoDistance(
		String field, GeoLocationPoint pinGeoLocationPoint,
		GeoDistance geoDistance);

	public GeoDistanceRangeQuery geoDistanceRange(
		String field, boolean includesLower, boolean includesUpper,
		GeoDistance lowerBoundGeoDistance, GeoLocationPoint pinGeoLocationPoint,
		GeoDistance upperBoundGeoDistance);

	public GeoPolygonQuery geoPolygon(String field);

	public GeoShapeQuery geoShape(String field, ShapeBuilder shapeBuilder);

	public GeoShapeQuery geoShape(
		String field, String indexedShapeId, String indexedShapeType);

	public IdsQuery ids();

	public MatchQuery match(String field, Object value);

	public MatchAllQuery matchAll();

	public MatchPhraseQuery matchPhrase(String field, Object value);

	public MatchPhrasePrefixQuery matchPhrasePrefix(String field, Object value);

	public MoreLikeThisQuery moreLikeThis(List<String> likeTexts);

	public MoreLikeThisQuery moreLikeThis(String... likeTexts);

	public MultiMatchQuery multiMatch(Object value, Set<String> fields);

	public MultiMatchQuery multiMatch(Object value, String... fields);

	public NestedQuery nested(String path, Query query);

	public PercolateQuery percolate(String field, List<String> documentJSONs);

	public PrefixQuery prefix(String field, String prefix);

	public RangeTermQuery rangeTerm(
		String field, boolean includesLower, boolean includesUpper);

	public RangeTermQuery rangeTerm(
		String field, boolean includesLower, boolean includesUpper,
		Object lowerBound, Object upperBound);

	public RegexQuery regex(String field, String regex);

	public ScriptQuery script(Script script);

	public SimpleStringQuery simpleString(String query);

	public StringQuery string(String query);

	public TermQuery term(String field, Object value);

	public TermsQuery terms(String field);

	public TermsSetQuery termsSet(String fieldName, List<Object> values);

	public WildcardQuery wildcard(String field, String value);

}