/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
import com.liferay.portal.search.query.QueriesUtil;
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

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.osgi.service.component.annotations.Component;

/**
 * @author Wade Cao
 */
@Component(service = Queries.class)
public class QueriesImpl implements Queries {

	@Override
	public BooleanQuery booleanQuery() {
		return QueriesUtil.booleanQuery();
	}

	@Override
	public BoostingQuery boosting(Query positiveQuery, Query negativeQuery) {
		return QueriesUtil.boosting(positiveQuery, negativeQuery);
	}

	@Override
	public CommonTermsQuery commonTerms(String field, String text) {
		return QueriesUtil.commonTerms(field, text);
	}

	@Override
	public ConstantScoreQuery constantScore(Query query) {
		return QueriesUtil.constantScore(query);
	}

	@Override
	public DateRangeTermQuery dateRangeTerm(
		String field, boolean includesLower, boolean includesUpper,
		String startDate, String endDate) {

		return QueriesUtil.dateRangeTerm(
			field, includesLower, includesUpper, startDate, endDate);
	}

	@Override
	public DisMaxQuery disMax() {
		return QueriesUtil.disMax();
	}

	@Override
	public MoreLikeThisQuery.DocumentIdentifier documentIdentifier(
		String index, String type, String id) {

		return QueriesUtil.documentIdentifier(index, type, id);
	}

	@Override
	public ExistsQuery exists(String field) {
		return QueriesUtil.exists(field);
	}

	@Override
	public FunctionScoreQuery functionScore(Query query) {
		return QueriesUtil.functionScore(query);
	}

	@Override
	public FuzzyQuery fuzzy(String field, String value) {
		return QueriesUtil.fuzzy(field, value);
	}

	@Override
	public GeoBoundingBoxQuery geoBoundingBox(
		String field, GeoLocationPoint topLeftGeoLocationPoint,
		GeoLocationPoint bottomRightGeoLocationPoint) {

		return QueriesUtil.geoBoundingBox(
			field, topLeftGeoLocationPoint, bottomRightGeoLocationPoint);
	}

	@Override
	public GeoDistanceQuery geoDistance(
		String field, GeoLocationPoint pinGeoLocationPoint,
		GeoDistance geoDistance) {

		return QueriesUtil.geoDistance(field, pinGeoLocationPoint, geoDistance);
	}

	@Override
	public GeoDistanceRangeQuery geoDistanceRange(
		String field, boolean includesLower, boolean includesUpper,
		GeoDistance lowerBoundGeoDistance, GeoLocationPoint pinGeoLocationPoint,
		GeoDistance upperBoundGeoDistance) {

		return QueriesUtil.geoDistanceRange(
			field, includesLower, includesUpper, lowerBoundGeoDistance,
			pinGeoLocationPoint, upperBoundGeoDistance);
	}

	@Override
	public GeoPolygonQuery geoPolygon(String field) {
		return QueriesUtil.geoPolygon(field);
	}

	@Override
	public GeoShapeQuery geoShape(String field, Shape shape) {
		return QueriesUtil.geoShape(field, shape);
	}

	@Override
	public GeoShapeQuery geoShape(
		String field, String indexedShapeId, String indexedShapeType) {

		return QueriesUtil.geoShape(field, indexedShapeId, indexedShapeType);
	}

	@Override
	public IdsQuery ids() {
		return QueriesUtil.ids();
	}

	@Override
	public MatchQuery match(String field, Object value) {
		return QueriesUtil.match(field, value);
	}

	@Override
	public MatchAllQuery matchAll() {
		return QueriesUtil.matchAll();
	}

	@Override
	public MatchPhraseQuery matchPhrase(String field, Object value) {
		return QueriesUtil.matchPhrase(field, value);
	}

	@Override
	public MatchPhrasePrefixQuery matchPhrasePrefix(
		String field, Object value) {

		return QueriesUtil.matchPhrasePrefix(field, value);
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 *             #moreLikeThis(List, String...)}
	 */
	@Deprecated
	@Override
	public MoreLikeThisQuery moreLikeThis(List<String> likeTexts) {
		return QueriesUtil.moreLikeThis(likeTexts);
	}

	@Override
	public MoreLikeThisQuery moreLikeThis(
		List<String> fields, String... likeTexts) {

		return QueriesUtil.moreLikeThis(fields, likeTexts);
	}

	@Override
	public MoreLikeThisQuery moreLikeThis(
		Set<MoreLikeThisQuery.DocumentIdentifier> documentIdentifiers) {

		return QueriesUtil.moreLikeThis(documentIdentifiers);
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 *             #moreLikeThis(List, String...)}
	 */
	@Deprecated
	@Override
	public MoreLikeThisQuery moreLikeThis(String... likeTexts) {
		return QueriesUtil.moreLikeThis(likeTexts);
	}

	@Override
	public MoreLikeThisQuery moreLikeThis(
		String[] fields, String... likeTexts) {

		return QueriesUtil.moreLikeThis(fields, likeTexts);
	}

	@Override
	public MultiMatchQuery multiMatch(
		Object value, Map<String, Float> fieldsBoosts) {

		return QueriesUtil.multiMatch(value, fieldsBoosts);
	}

	@Override
	public MultiMatchQuery multiMatch(Object value, Set<String> fields) {
		return QueriesUtil.multiMatch(value, fields);
	}

	@Override
	public MultiMatchQuery multiMatch(Object value, String... fields) {
		return QueriesUtil.multiMatch(value, fields);
	}

	@Override
	public NestedQuery nested(String path, Query query) {
		return QueriesUtil.nested(path, query);
	}

	@Override
	public PercolateQuery percolate(String field, List<String> documentJSONs) {
		return QueriesUtil.percolate(field, documentJSONs);
	}

	@Override
	public PrefixQuery prefix(String field, String prefix) {
		return QueriesUtil.prefix(field, prefix);
	}

	@Override
	public RangeTermQuery rangeTerm(
		String field, boolean includesLower, boolean includesUpper) {

		return QueriesUtil.rangeTerm(field, includesLower, includesUpper);
	}

	@Override
	public RangeTermQuery rangeTerm(
		String field, boolean includesLower, boolean includesUpper,
		Object lowerBound, Object upperBound) {

		return QueriesUtil.rangeTerm(
			field, includesLower, includesUpper, lowerBound, upperBound);
	}

	@Override
	public RegexQuery regex(String field, String regex) {
		return QueriesUtil.regex(field, regex);
	}

	@Override
	public ScriptQuery script(Script script) {
		return QueriesUtil.script(script);
	}

	@Override
	public SimpleStringQuery simpleString(String query) {
		return QueriesUtil.simpleString(query);
	}

	@Override
	public StringQuery string(String query) {
		return QueriesUtil.string(query);
	}

	@Override
	public TermQuery term(String field, Object value) {
		return QueriesUtil.term(field, value);
	}

	@Override
	public TermsQuery terms(String field) {
		return QueriesUtil.terms(field);
	}

	@Override
	public TermsSetQuery termsSet(String fieldName, List<Object> values) {
		return QueriesUtil.termsSet(fieldName, values);
	}

	@Override
	public WildcardQuery wildcard(String field, String value) {
		return QueriesUtil.wildcard(field, value);
	}

	@Override
	public WrapperQuery wrapper(byte[] source) {
		return QueriesUtil.wrapper(source);
	}

	@Override
	public WrapperQuery wrapper(String source) {
		return QueriesUtil.wrapper(source);
	}

}