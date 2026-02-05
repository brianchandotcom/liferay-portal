/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.elasticsearch8.internal.query;

import co.elastic.clients.elasticsearch._types.FieldValue;
import co.elastic.clients.elasticsearch._types.GeoLocation;
import co.elastic.clients.elasticsearch._types.GeoShapeRelation;
import co.elastic.clients.elasticsearch._types.query_dsl.BoolQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.ChildScoreMode;
import co.elastic.clients.elasticsearch._types.query_dsl.DateRangeQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.FieldLookup;
import co.elastic.clients.elasticsearch._types.query_dsl.FunctionBoostMode;
import co.elastic.clients.elasticsearch._types.query_dsl.FunctionScore.Builder.ContainerBuilder;
import co.elastic.clients.elasticsearch._types.query_dsl.FunctionScoreMode;
import co.elastic.clients.elasticsearch._types.query_dsl.GeoPolygonPoints;
import co.elastic.clients.elasticsearch._types.query_dsl.GeoShapeFieldQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.Like;
import co.elastic.clients.elasticsearch._types.query_dsl.LikeDocument;
import co.elastic.clients.elasticsearch._types.query_dsl.QueryBuilders;
import co.elastic.clients.elasticsearch._types.query_dsl.QueryStringQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.QueryVariant;
import co.elastic.clients.elasticsearch._types.query_dsl.RangeQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.RangeRelation;
import co.elastic.clients.elasticsearch._types.query_dsl.RegexpQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.SimpleQueryStringQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.TermRangeQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.TextQueryType;
import co.elastic.clients.elasticsearch._types.query_dsl.UntypedRangeQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.ZeroTermsQuery;
import co.elastic.clients.json.JsonData;

import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.util.Base64;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.search.elasticsearch8.internal.geolocation.GeoTranslator;
import com.liferay.portal.search.elasticsearch8.internal.query.function.score.ElasticsearchScoreFunctionTranslator;
import com.liferay.portal.search.elasticsearch8.internal.script.ScriptTranslator;
import com.liferay.portal.search.elasticsearch8.internal.util.ConversionUtil;
import com.liferay.portal.search.elasticsearch8.internal.util.QueryUtil;
import com.liferay.portal.search.elasticsearch8.internal.util.SetterUtil;
import com.liferay.portal.search.geolocation.Coordinate;
import com.liferay.portal.search.geolocation.GeoDistance;
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
import com.liferay.portal.search.query.Operator;
import com.liferay.portal.search.query.PercolateQuery;
import com.liferay.portal.search.query.PrefixQuery;
import com.liferay.portal.search.query.Query;
import com.liferay.portal.search.query.QueryVisitor;
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
import com.liferay.portal.search.query.function.CombineFunction;
import com.liferay.portal.search.query.geolocation.ShapeRelation;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TimeZone;
import java.util.function.Consumer;

/**
 * @author Michael C. Han
 */
public class ElasticsearchQueryVisitor implements QueryVisitor<QueryVariant> {

	public static final ElasticsearchQueryVisitor INSTANCE =
		new ElasticsearchQueryVisitor();

	public QueryVariant translate(Query query) {
		QueryVariant queryVariant = query.accept(this);

		if (queryVariant != null) {
			return queryVariant;
		}

		return QueryBuilders.queryString(
		).query(
			query.toString()
		).build();
	}

	@Override
	public QueryVariant visit(BooleanQuery booleanQuery) {
		BoolQuery.Builder builder = QueryBuilders.bool();

		builder.queryName(booleanQuery.getQueryName());

		SetterUtil.setNotNullFloat(builder::boost, booleanQuery.getBoost());

		_processBooleanQueryClauses(
			builder::filter, booleanQuery.getFilterQueryClauses());

		_processBooleanQueryClauses(
			builder::must, booleanQuery.getMustQueryClauses());

		_processBooleanQueryClauses(
			builder::mustNot, booleanQuery.getMustNotQueryClauses());

		_processBooleanQueryClauses(
			builder::should, booleanQuery.getShouldQueryClauses());

		if (booleanQuery.getMinimumShouldMatch() != null) {
			builder.minimumShouldMatch(
				String.valueOf(booleanQuery.getMinimumShouldMatch()));
		}

		return builder.build();
	}

	@Override
	public QueryVariant visit(BoostingQuery boostingQuery) {
		co.elastic.clients.elasticsearch._types.query_dsl.BoostingQuery.Builder
			builder = QueryBuilders.boosting();

		SetterUtil.setNotNullFloat(builder::boost, boostingQuery.getBoost());

		Query negativeQuery = boostingQuery.getNegativeQuery();

		builder.negative(
			new co.elastic.clients.elasticsearch._types.query_dsl.Query(
				negativeQuery.accept(this)));

		SetterUtil.setNotNullFloatAsDouble(
			builder::negativeBoost, boostingQuery.getNegativeBoost());

		Query positiveQuery = boostingQuery.getPositiveQuery();

		builder.positive(
			new co.elastic.clients.elasticsearch._types.query_dsl.Query(
				positiveQuery.accept(this)));

		return builder.build();
	}

	@Override
	public QueryVariant visit(CommonTermsQuery commonTermsQuery) {
		co.elastic.clients.elasticsearch._types.query_dsl.CommonTermsQuery.
			Builder builder =
				new co.elastic.clients.elasticsearch._types.query_dsl.
					CommonTermsQuery.Builder();

		SetterUtil.setNotBlankString(
			builder::analyzer, commonTermsQuery.getAnalyzer());
		SetterUtil.setNotNullFloat(builder::boost, commonTermsQuery.getBoost());
		SetterUtil.setNotNullFloatAsDouble(
			builder::cutoffFrequency, commonTermsQuery.getCutoffFrequency());

		builder.field(commonTermsQuery.getField());

		if (commonTermsQuery.getHighFreqOperator() != null) {
			builder.highFreqOperator(
				_translateOperator(commonTermsQuery.getHighFreqOperator()));
		}

		if (commonTermsQuery.getLowFreqOperator() != null) {
			builder.lowFreqOperator(
				_translateOperator(commonTermsQuery.getLowFreqOperator()));
		}

		builder.query(commonTermsQuery.getText());

		return builder.build();
	}

	@Override
	public QueryVariant visit(ConstantScoreQuery constantScoreQuery) {
		co.elastic.clients.elasticsearch._types.query_dsl.ConstantScoreQuery.
			Builder builder =
				new co.elastic.clients.elasticsearch._types.query_dsl.
					ConstantScoreQuery.Builder();

		SetterUtil.setNotNullFloat(
			builder::boost, constantScoreQuery.getBoost());

		Query query = constantScoreQuery.getQuery();

		builder.filter(
			new co.elastic.clients.elasticsearch._types.query_dsl.Query(
				query.accept(this)));

		return builder.build();
	}

	@Override
	public QueryVariant visit(DateRangeTermQuery dateRangeTermQuery) {
		RangeQuery.Builder builder = new RangeQuery.Builder();

		DateRangeQuery.Builder dateRangeQueryBuilder =
			new DateRangeQuery.Builder();

		SetterUtil.setNotNullFloat(
			dateRangeQueryBuilder::boost, dateRangeTermQuery.getBoost());

		dateRangeQueryBuilder.field(dateRangeTermQuery.getField());

		SetterUtil.setNotBlankString(
			dateRangeQueryBuilder::format, dateRangeTermQuery.getDateFormat());

		QueryUtil.setRanges(
			dateRangeQueryBuilder, dateRangeTermQuery.isIncludesLower(),
			dateRangeTermQuery.isIncludesUpper(),
			dateRangeTermQuery.getLowerBound(),
			dateRangeTermQuery.getUpperBound());

		TimeZone timeZone = dateRangeTermQuery.getTimeZone();

		if (timeZone != null) {
			dateRangeQueryBuilder.timeZone(timeZone.getID());
		}

		builder.date(dateRangeQueryBuilder.build());

		return builder.build();
	}

	@Override
	public QueryVariant visit(DisMaxQuery disMaxQuery) {
		co.elastic.clients.elasticsearch._types.query_dsl.DisMaxQuery.Builder
			builder = QueryBuilders.disMax();

		SetterUtil.setNotNullFloat(builder::boost, disMaxQuery.getBoost());

		for (Query query : disMaxQuery.getQueries()) {
			builder.queries(
				new co.elastic.clients.elasticsearch._types.query_dsl.Query(
					query.accept(this)));
		}

		SetterUtil.setNotNullFloatAsDouble(
			builder::tieBreaker, disMaxQuery.getTieBreaker());

		return builder.build();
	}

	@Override
	public QueryVariant visit(ExistsQuery existsQuery) {
		co.elastic.clients.elasticsearch._types.query_dsl.ExistsQuery.Builder
			builder = QueryBuilders.exists();

		SetterUtil.setNotNullFloat(builder::boost, existsQuery.getBoost());

		builder.field(existsQuery.getField());

		return builder.build();
	}

	@Override
	public QueryVariant visit(FunctionScoreQuery functionScoreQuery) {
		co.elastic.clients.elasticsearch._types.query_dsl.FunctionScoreQuery.
			Builder builder = QueryBuilders.functionScore();

		SetterUtil.setNotNullFloat(
			builder::boost, functionScoreQuery.getBoost());

		if (functionScoreQuery.getCombineFunction() != null) {
			builder.boostMode(
				_translateCombineFunction(
					functionScoreQuery.getCombineFunction()));
		}

		ListUtil.isNotEmptyForEach(
			functionScoreQuery.getFilterQueryScoreFunctionHolders(),
			filterQueryScoreFunctionHolder -> {
				ContainerBuilder containerBuilder =
					_elasticsearchScoreFunctionTranslator.translate(
						filterQueryScoreFunctionHolder.getScoreFunction());

				if (containerBuilder == null) {
					return;
				}

				if (filterQueryScoreFunctionHolder.getFilterQuery() != null) {
					containerBuilder.filter(
						new co.elastic.clients.elasticsearch._types.query_dsl.
							Query(
								translate(
									filterQueryScoreFunctionHolder.
										getFilterQuery())));
				}

				builder.functions(containerBuilder.build());
			});

		SetterUtil.setNotNullFloatAsDouble(
			builder::maxBoost, functionScoreQuery.getMaxBoost());
		SetterUtil.setNotNullFloatAsDouble(
			builder::minScore, functionScoreQuery.getMinScore());

		builder.query(
			new co.elastic.clients.elasticsearch._types.query_dsl.Query(
				translate(functionScoreQuery.getQuery())));

		if (functionScoreQuery.getScoreMode() != null) {
			builder.scoreMode(
				_translateScoreMore(functionScoreQuery.getScoreMode()));
		}

		return builder.build();
	}

	@Override
	public QueryVariant visit(FuzzyQuery fuzzyQuery) {
		co.elastic.clients.elasticsearch._types.query_dsl.FuzzyQuery.Builder
			builder = QueryBuilders.fuzzy();

		SetterUtil.setNotNullFloat(builder::boost, fuzzyQuery.getBoost());

		builder.field(fuzzyQuery.getField());

		SetterUtil.setNotNullValueAsString(
			builder::fuzziness, fuzzyQuery.getFuzziness());
		SetterUtil.setNotNullInteger(
			builder::maxExpansions, fuzzyQuery.getMaxExpansions());
		SetterUtil.setNotNullInteger(
			builder::prefixLength, fuzzyQuery.getPrefixLength());
		SetterUtil.setNotBlankString(builder::rewrite, fuzzyQuery.getRewrite());
		SetterUtil.setNotNullBoolean(
			builder::transpositions, fuzzyQuery.getTranspositions());

		builder.value(FieldValue.of(fuzzyQuery.getValue()));

		return builder.build();
	}

	@Override
	public QueryVariant visit(GeoBoundingBoxQuery geoBoundingBoxQuery) {
		co.elastic.clients.elasticsearch._types.query_dsl.GeoBoundingBoxQuery.
			Builder builder = QueryBuilders.geoBoundingBox();

		SetterUtil.setNotNullFloat(
			builder::boost, geoBoundingBoxQuery.getBoost());

		builder.boundingBox(
			_geoTranslator.toGeoBounds(
				geoBoundingBoxQuery.getTopLeftGeoLocationPoint(),
				geoBoundingBoxQuery.getBottomRightGeoLocationPoint()));

		builder.field(geoBoundingBoxQuery.getField());

		SetterUtil.setNotNullBoolean(
			builder::ignoreUnmapped, geoBoundingBoxQuery.getIgnoreUnmapped());

		if (geoBoundingBoxQuery.getGeoExecType() != null) {
			builder.type(
				_geoTranslator.translateGeoExecType(
					geoBoundingBoxQuery.getGeoExecType()));
		}

		if (geoBoundingBoxQuery.getGeoValidationMethod() != null) {
			builder.validationMethod(
				_geoTranslator.translateGeoValidationMethod(
					geoBoundingBoxQuery.getGeoValidationMethod()));
		}

		return builder.build();
	}

	@Override
	public QueryVariant visit(GeoDistanceQuery geoDistanceQuery) {
		co.elastic.clients.elasticsearch._types.query_dsl.GeoDistanceQuery.
			Builder builder = QueryBuilders.geoDistance();

		SetterUtil.setNotNullFloat(builder::boost, geoDistanceQuery.getBoost());

		if (geoDistanceQuery.getGeoDistance() != null) {
			builder.distance(
				_geoTranslator.toStringWithUnit(
					geoDistanceQuery.getGeoDistance()));
		}

		builder.field(geoDistanceQuery.getField());
		builder.location(
			_geoTranslator.translateGeoLocationPoint(
				geoDistanceQuery.getPinGeoLocationPoint()));

		SetterUtil.setNotBlankString(
			builder::queryName, geoDistanceQuery.getQueryName());

		if (geoDistanceQuery.getGeoValidationMethod() != null) {
			builder.validationMethod(
				_geoTranslator.translateGeoValidationMethod(
					geoDistanceQuery.getGeoValidationMethod()));
		}

		return builder.build();
	}

	@Override
	public QueryVariant visit(GeoDistanceRangeQuery geoDistanceRangeQuery) {
		RangeQuery.Builder builder = new RangeQuery.Builder();

		UntypedRangeQuery.Builder untypedRangeQueryBuilder =
			new UntypedRangeQuery.Builder();

		SetterUtil.setNotNullFloat(
			untypedRangeQueryBuilder::boost, geoDistanceRangeQuery.getBoost());

		GeoDistance geoDistanceLowerBound =
			geoDistanceRangeQuery.getLowerBoundGeoDistance();

		GeoDistance geoDistanceUpperBound =
			geoDistanceRangeQuery.getUpperBoundGeoDistance();

		QueryUtil.setRanges(
			untypedRangeQueryBuilder, geoDistanceRangeQuery.isIncludesLower(),
			geoDistanceRangeQuery.isIncludesUpper(),
			geoDistanceLowerBound.toString(), geoDistanceUpperBound.toString());

		if (geoDistanceRangeQuery.getShapeRelation() != null) {
			ShapeRelation shapeRelation =
				geoDistanceRangeQuery.getShapeRelation();

			untypedRangeQueryBuilder.relation(
				RangeRelation.valueOf(
					StringUtil.toLowerCase(shapeRelation.name())));
		}

		builder.untyped(untypedRangeQueryBuilder.build());

		return builder.build();
	}

	@Override
	public QueryVariant visit(GeoPolygonQuery geoPolygonQuery) {
		co.elastic.clients.elasticsearch._types.query_dsl.GeoPolygonQuery.
			Builder builder = QueryBuilders.geoPolygon();

		SetterUtil.setNotNullFloat(builder::boost, geoPolygonQuery.getBoost());

		builder.field(geoPolygonQuery.getField());

		SetterUtil.setNotNullBoolean(
			builder::ignoreUnmapped, geoPolygonQuery.getIgnoreUnmapped());

		List<GeoLocation> geoLocations = TransformUtil.transform(
			geoPolygonQuery.getGeoLocationPoints(),
			_geoTranslator::translateGeoLocationPoint);

		builder.polygon(
			GeoPolygonPoints.of(
				geoPolygonPoints -> geoPolygonPoints.points(geoLocations)));

		if (geoPolygonQuery.getGeoValidationMethod() != null) {
			builder.validationMethod(
				_geoTranslator.translateGeoValidationMethod(
					geoPolygonQuery.getGeoValidationMethod()));
		}

		return builder.build();
	}

	@Override
	public QueryVariant visit(GeoShapeQuery geoShapeQuery) {
		co.elastic.clients.elasticsearch._types.query_dsl.GeoShapeQuery.Builder
			geoShapeQueryBuilder = QueryBuilders.geoShape();

		SetterUtil.setNotNullFloat(
			geoShapeQueryBuilder::boost, geoShapeQuery.getBoost());

		geoShapeQueryBuilder.field(geoShapeQuery.getField());

		SetterUtil.setNotNullBoolean(
			geoShapeQueryBuilder::ignoreUnmapped,
			geoShapeQuery.getIgnoreUnmapped());

		GeoShapeFieldQuery.Builder geoShapeFieldQueryBuilder =
			new GeoShapeFieldQuery.Builder();

		if (geoShapeQuery.getIndexedShapeId() != null) {
			FieldLookup.Builder fieldLookupBuilder = new FieldLookup.Builder();

			SetterUtil.setNotBlankString(
				fieldLookupBuilder::id, geoShapeQuery.getIndexedShapeId());

			SetterUtil.setNotNullBoolean(
				geoShapeQueryBuilder::ignoreUnmapped,
				geoShapeQuery.getIgnoreUnmapped());
			SetterUtil.setNotBlankString(
				fieldLookupBuilder::index,
				geoShapeQuery.getIndexedShapeIndex());
			SetterUtil.setNotBlankString(
				fieldLookupBuilder::path, geoShapeQuery.getIndexedShapePath());
			SetterUtil.setNotBlankString(
				fieldLookupBuilder::routing,
				geoShapeQuery.getIndexedShapeRouting());

			geoShapeFieldQueryBuilder.indexedShape(fieldLookupBuilder.build());
		}
		else {
			Shape shape = geoShapeQuery.getShape();

			List<Coordinate> coordinates = shape.getCoordinates();

			geoShapeFieldQueryBuilder.shape(
				JsonData.of(
					JSONUtil.put(
						"coordinates", JSONUtil.putAll(coordinates.toArray())
					).put(
						"type", "point"
					)));
		}

		if (geoShapeQuery.getShapeRelation() != null) {
			geoShapeFieldQueryBuilder.relation(
				_translateShapeRelation(geoShapeQuery.getShapeRelation()));
		}

		geoShapeQueryBuilder.shape(geoShapeFieldQueryBuilder.build());

		return geoShapeQueryBuilder.build();
	}

	@Override
	public QueryVariant visit(IdsQuery idsQuery) {
		co.elastic.clients.elasticsearch._types.query_dsl.IdsQuery.Builder
			builder = QueryBuilders.ids();

		SetterUtil.setNotNullFloat(builder::boost, idsQuery.getBoost());

		builder.queryName(idsQuery.getQueryName());
		builder.values(ListUtil.fromCollection(idsQuery.getIds()));

		return builder.build();
	}

	@Override
	public QueryVariant visit(MatchAllQuery matchAllQuery) {
		co.elastic.clients.elasticsearch._types.query_dsl.MatchAllQuery.Builder
			builder = QueryBuilders.matchAll();

		SetterUtil.setNotNullFloat(builder::boost, matchAllQuery.getBoost());

		return builder.build();
	}

	@Override
	public QueryVariant visit(MatchPhrasePrefixQuery matchPhrasePrefixQuery) {
		co.elastic.clients.elasticsearch._types.query_dsl.
			MatchPhrasePrefixQuery.Builder builder =
				QueryBuilders.matchPhrasePrefix();

		SetterUtil.setNotBlankString(
			builder::analyzer, matchPhrasePrefixQuery.getAnalyzer());
		SetterUtil.setNotNullFloat(
			builder::boost, matchPhrasePrefixQuery.getBoost());

		builder.field(matchPhrasePrefixQuery.getField());

		SetterUtil.setNotNullInteger(
			builder::maxExpansions, matchPhrasePrefixQuery.getMaxExpansions());

		builder.query(String.valueOf(matchPhrasePrefixQuery.getValue()));

		SetterUtil.setNotNullInteger(
			builder::slop, matchPhrasePrefixQuery.getSlop());

		return builder.build();
	}

	@Override
	public QueryVariant visit(MatchPhraseQuery matchPhraseQuery) {
		co.elastic.clients.elasticsearch._types.query_dsl.MatchPhraseQuery.
			Builder builder = QueryBuilders.matchPhrase();

		SetterUtil.setNotBlankString(
			builder::analyzer, matchPhraseQuery.getAnalyzer());
		SetterUtil.setNotNullFloat(builder::boost, matchPhraseQuery.getBoost());

		builder.field(matchPhraseQuery.getField());
		builder.query(String.valueOf(matchPhraseQuery.getValue()));

		SetterUtil.setNotNullInteger(builder::slop, matchPhraseQuery.getSlop());

		return builder.build();
	}

	@Override
	public QueryVariant visit(MatchQuery matchQuery) {
		MatchQuery.Type type = matchQuery.getType();
		Object value = matchQuery.getValue();

		if (value instanceof String) {
			String stringValue = (String)value;

			if (stringValue.startsWith(StringPool.QUOTE) &&
				stringValue.endsWith(StringPool.QUOTE)) {

				type = MatchQuery.Type.PHRASE;

				stringValue = StringUtil.unquote(stringValue);

				if (stringValue.endsWith(StringPool.STAR)) {
					type = MatchQuery.Type.PHRASE_PREFIX;
				}
			}

			if (type == MatchQuery.Type.PHRASE) {
				return _translateMatchPhraseQuery(matchQuery, stringValue);
			}
			else if (type == MatchQuery.Type.PHRASE_PREFIX) {
				return _translateMatchPhrasePrefixQuery(
					matchQuery, stringValue);
			}
		}

		if ((type == null) || (type == MatchQuery.Type.BOOLEAN)) {
			return _translateMatchQuery(matchQuery, value);
		}

		throw new IllegalArgumentException("Invalid match query type " + type);
	}

	@Override
	public QueryVariant visit(MoreLikeThisQuery moreLikeThisQuery) {
		co.elastic.clients.elasticsearch._types.query_dsl.MoreLikeThisQuery.
			Builder builder = QueryBuilders.moreLikeThis();

		SetterUtil.setNotBlankString(
			builder::analyzer, moreLikeThisQuery.getAnalyzer());
		SetterUtil.setNotNullFloat(
			builder::boost, moreLikeThisQuery.getBoost());
		SetterUtil.setNotNullFloatAsDouble(
			builder::boostTerms, moreLikeThisQuery.getTermBoost());

		if (ListUtil.isNotEmpty(moreLikeThisQuery.getFields())) {
			builder.fields(moreLikeThisQuery.getFields());
		}

		SetterUtil.setNotNullBoolean(
			builder::include, moreLikeThisQuery.isIncludeInput());

		List<Like> likes = _translateDocumentIdentifiers(
			moreLikeThisQuery.getDocumentIdentifiers());

		ListUtil.isNotEmptyForEach(
			moreLikeThisQuery.getLikeTexts(),
			likeText -> likes.add(Like.of(like -> like.text(likeText))));

		builder.like(likes);

		SetterUtil.setNotNullInteger(
			builder::maxDocFreq, moreLikeThisQuery.getMaxDocFrequency());
		SetterUtil.setNotNullInteger(
			builder::maxQueryTerms, moreLikeThisQuery.getMaxQueryTerms());
		SetterUtil.setNotNullInteger(
			builder::maxWordLength, moreLikeThisQuery.getMaxWordLength());
		SetterUtil.setNotNullInteger(
			builder::minDocFreq, moreLikeThisQuery.getMinDocFrequency());
		SetterUtil.setNotBlankString(
			builder::minimumShouldMatch, moreLikeThisQuery.getMinShouldMatch());
		SetterUtil.setNotNullInteger(
			builder::minTermFreq, moreLikeThisQuery.getMinTermFrequency());
		SetterUtil.setNotNullInteger(
			builder::minWordLength, moreLikeThisQuery.getMinWordLength());

		if (SetUtil.isNotEmpty(moreLikeThisQuery.getStopWords())) {
			builder.stopWords(
				ListUtil.fromCollection(moreLikeThisQuery.getStopWords()));
		}

		return builder.build();
	}

	@Override
	public QueryVariant visit(MultiMatchQuery multiMatchQuery) {
		co.elastic.clients.elasticsearch._types.query_dsl.MultiMatchQuery.
			Builder builder = QueryBuilders.multiMatch();

		SetterUtil.setNotBlankString(
			builder::analyzer, multiMatchQuery.getAnalyzer());
		SetterUtil.setNotNullFloat(builder::boost, multiMatchQuery.getBoost());
		SetterUtil.setNotNullFloatAsDouble(
			builder::cutoffFrequency, multiMatchQuery.getCutOffFrequency());

		if (!multiMatchQuery.isFieldBoostsEmpty()) {
			builder.fields(
				QueryUtil.fieldsBoostsToFieldsWithBoosts(
					multiMatchQuery.getFieldsBoosts()));
		}

		SetterUtil.setNotBlankString(
			builder::fuzziness, multiMatchQuery.getFuzziness());

		if (multiMatchQuery.getFuzzyRewriteMethod() != null) {
			builder.fuzzyRewrite(
				translateMatchQueryRewriteMethod(
					multiMatchQuery.getFuzzyRewriteMethod()));
		}

		SetterUtil.setNotNullBoolean(
			builder::lenient, multiMatchQuery.isLenient());
		SetterUtil.setNotNullInteger(
			builder::maxExpansions, multiMatchQuery.getMaxExpansions());
		SetterUtil.setNotBlankString(
			builder::minimumShouldMatch, multiMatchQuery.getMinShouldMatch());

		if (multiMatchQuery.getOperator() != null) {
			builder.operator(_translateOperator(multiMatchQuery.getOperator()));
		}

		SetterUtil.setNotNullInteger(
			builder::prefixLength, multiMatchQuery.getPrefixLength());

		builder.query(String.valueOf(multiMatchQuery.getValue()));

		SetterUtil.setNotNullInteger(builder::slop, multiMatchQuery.getSlop());

		if (multiMatchQuery.getType() != null) {
			builder.type(
				translateMultiMatchQueryType(multiMatchQuery.getType()));
		}

		if (multiMatchQuery.getZeroTermsQuery() != null) {
			builder.zeroTermsQuery(
				translateMatchQueryZeroTermsQuery(
					multiMatchQuery.getZeroTermsQuery()));
		}

		return builder.build();
	}

	@Override
	public QueryVariant visit(NestedQuery nestedQuery) {
		co.elastic.clients.elasticsearch._types.query_dsl.NestedQuery.Builder
			builder = QueryBuilders.nested();

		SetterUtil.setNotNullFloat(builder::boost, nestedQuery.getBoost());

		builder.path(nestedQuery.getPath());

		Query query = nestedQuery.getQuery();

		builder.query(
			new co.elastic.clients.elasticsearch._types.query_dsl.Query(
				query.accept(this)));

		if (nestedQuery.getQueryName() != null) {
			builder.queryName(nestedQuery.getQueryName());
		}

		builder.scoreMode(ChildScoreMode.Sum);

		return builder.build();
	}

	@Override
	public QueryVariant visit(PercolateQuery percolateQuery) {
		co.elastic.clients.elasticsearch._types.query_dsl.PercolateQuery.Builder
			builder = QueryBuilders.percolate();

		SetterUtil.setNotNullFloat(builder::boost, percolateQuery.getBoost());

		ListUtil.isNotEmptyForEach(
			percolateQuery.getDocumentJSONs(),
			documentJSON -> builder.document(JsonData.of(documentJSON)));

		builder.field(percolateQuery.getField());

		return builder.build();
	}

	@Override
	public QueryVariant visit(PrefixQuery prefixQuery) {
		co.elastic.clients.elasticsearch._types.query_dsl.PrefixQuery.Builder
			builder = QueryBuilders.prefix();

		SetterUtil.setNotNullFloat(builder::boost, prefixQuery.getBoost());

		builder.field(prefixQuery.getField());

		SetterUtil.setNotBlankString(
			builder::rewrite, prefixQuery.getRewrite());

		builder.value(prefixQuery.getPrefix());

		return builder.build();
	}

	@Override
	public QueryVariant visit(RangeTermQuery rangeTermQuery) {
		RangeQuery.Builder builder = QueryBuilders.range();

		TermRangeQuery.Builder termRangeQueryBuilder =
			new TermRangeQuery.Builder();

		SetterUtil.setNotNullFloat(
			termRangeQueryBuilder::boost, rangeTermQuery.getBoost());

		termRangeQueryBuilder.field(rangeTermQuery.getField());

		QueryUtil.setRanges(
			termRangeQueryBuilder, rangeTermQuery.isIncludesLower(),
			rangeTermQuery.isIncludesUpper(), rangeTermQuery.getLowerBound(),
			rangeTermQuery.getUpperBound());

		builder.term(termRangeQueryBuilder.build());

		return builder.build();
	}

	@Override
	public QueryVariant visit(RegexQuery regexQuery) {
		RegexpQuery.Builder builder = QueryBuilders.regexp();

		SetterUtil.setNotNullFloat(builder::boost, regexQuery.getBoost());

		builder.field(regexQuery.getField());

		if (regexQuery.getRegexFlags() != null) {
			builder.flags(_translateRegexFlags(regexQuery.getRegexFlags()));
		}

		SetterUtil.setNotNullInteger(
			builder::maxDeterminizedStates,
			regexQuery.getMaxDeterminedStates());
		SetterUtil.setNotBlankString(builder::rewrite, regexQuery.getRewrite());

		builder.value(regexQuery.getRegex());

		return builder.build();
	}

	@Override
	public QueryVariant visit(ScriptQuery scriptQuery) {
		co.elastic.clients.elasticsearch._types.query_dsl.ScriptQuery.Builder
			builder = QueryBuilders.script();

		SetterUtil.setNotNullFloat(builder::boost, scriptQuery.getBoost());

		builder.script(_scriptTranslator.translate(scriptQuery.getScript()));

		return builder.build();
	}

	@Override
	public QueryVariant visit(SimpleStringQuery simpleStringQuery) {
		SimpleQueryStringQuery.Builder builder =
			QueryBuilders.simpleQueryString();

		SetterUtil.setNotNullFloat(
			builder::boost, simpleStringQuery.getBoost());

		if (MapUtil.isNotEmpty(simpleStringQuery.getFieldBoostMap())) {
			builder.fields(
				QueryUtil.fieldsBoostsToFieldsWithBoosts(
					simpleStringQuery.getFieldBoostMap()));
		}

		SetterUtil.setNotBlankString(
			builder::analyzer, simpleStringQuery.getAnalyzer());
		SetterUtil.setNotNullBoolean(
			builder::analyzeWildcard, simpleStringQuery.getAnalyzeWildcard());
		SetterUtil.setNotNullBoolean(
			builder::autoGenerateSynonymsPhraseQuery,
			simpleStringQuery.getAutoGenerateSynonymsPhraseQuery());

		if (simpleStringQuery.getDefaultOperator() != null) {
			builder.defaultOperator(
				_translateOperator(simpleStringQuery.getDefaultOperator()));
		}

		SetterUtil.setNotNullInteger(
			builder::fuzzyMaxExpansions,
			simpleStringQuery.getFuzzyMaxExpansions());
		SetterUtil.setNotNullInteger(
			builder::fuzzyPrefixLength,
			simpleStringQuery.getFuzzyPrefixLength());
		SetterUtil.setNotNullBoolean(
			builder::fuzzyTranspositions,
			simpleStringQuery.getFuzzyTranspositions());
		SetterUtil.setNotNullBoolean(
			builder::lenient, simpleStringQuery.getLenient());

		builder.query(simpleStringQuery.getQuery());

		SetterUtil.setNotBlankString(
			builder::quoteFieldSuffix, simpleStringQuery.getQuoteFieldSuffix());

		return builder.build();
	}

	@Override
	public QueryVariant visit(StringQuery stringQuery) {
		QueryStringQuery.Builder builder = QueryBuilders.queryString();

		SetterUtil.setNotNullBoolean(
			builder::allowLeadingWildcard,
			stringQuery.getAllowLeadingWildcard());
		SetterUtil.setNotBlankString(
			builder::analyzer, stringQuery.getAnalyzer());
		SetterUtil.setNotNullBoolean(
			builder::analyzeWildcard, stringQuery.getAnalyzeWildcard());
		SetterUtil.setNotNullBoolean(
			builder::autoGenerateSynonymsPhraseQuery,
			stringQuery.getAutoGenerateSynonymsPhraseQuery());
		SetterUtil.setNotNullFloat(builder::boost, stringQuery.getBoost());
		SetterUtil.setNotBlankString(
			builder::defaultField, stringQuery.getDefaultField());

		if (stringQuery.getDefaultOperator() != null) {
			builder.defaultOperator(
				_translateOperator(stringQuery.getDefaultOperator()));
		}

		SetterUtil.setNotNullBoolean(
			builder::enablePositionIncrements,
			stringQuery.getEnablePositionIncrements());
		SetterUtil.setNotNullBoolean(builder::escape, stringQuery.getEscape());

		if (MapUtil.isNotEmpty(stringQuery.getFieldsBoosts())) {
			builder.fields(
				QueryUtil.fieldsBoostsToFieldsWithBoosts(
					stringQuery.getFieldsBoosts()));
		}

		SetterUtil.setNotNullValueAsString(
			builder::fuzziness, stringQuery.getFuzziness());
		SetterUtil.setNotNullInteger(
			builder::fuzzyMaxExpansions, stringQuery.getFuzzyMaxExpansions());
		SetterUtil.setNotNullInteger(
			builder::fuzzyPrefixLength, stringQuery.getFuzzyPrefixLength());
		SetterUtil.setNotBlankString(
			builder::fuzzyRewrite, stringQuery.getFuzzyRewrite());
		SetterUtil.setNotNullBoolean(
			builder::fuzzyTranspositions, stringQuery.getFuzzyTranspositions());
		SetterUtil.setNotNullBoolean(
			builder::lenient, stringQuery.getLenient());
		SetterUtil.setNotNullInteger(
			builder::maxDeterminizedStates,
			stringQuery.getMaxDeterminedStates());
		SetterUtil.setNotBlankString(
			builder::minimumShouldMatch, stringQuery.getMinimumShouldMatch());
		SetterUtil.setNotNullIntegerAsDouble(
			builder::phraseSlop, stringQuery.getPhraseSlop());

		builder.query(stringQuery.getQuery());

		SetterUtil.setNotBlankString(
			builder::quoteAnalyzer, stringQuery.getQuoteAnalyzer());
		SetterUtil.setNotBlankString(
			builder::quoteFieldSuffix, stringQuery.getQuoteFieldSuffix());
		SetterUtil.setNotBlankString(
			builder::rewrite, stringQuery.getRewrite());
		SetterUtil.setNotNullFloatAsDouble(
			builder::tieBreaker, stringQuery.getTieBreaker());
		SetterUtil.setNotBlankString(
			builder::timeZone, stringQuery.getTimeZone());

		return builder.build();
	}

	@Override
	public QueryVariant visit(TermQuery termQuery) {
		co.elastic.clients.elasticsearch._types.query_dsl.TermQuery.Builder
			builder = QueryBuilders.term();

		SetterUtil.setNotNullFloat(builder::boost, termQuery.getBoost());

		builder.field(termQuery.getField());
		builder.value(ConversionUtil.toFieldValue(termQuery.getValue()));

		return builder.build();
	}

	@Override
	public QueryVariant visit(TermsQuery termsQuery) {
		return QueryUtil.translateTerms(
			termsQuery.getBoost(), termsQuery.getField(),
			termsQuery.getValues());
	}

	@Override
	public QueryVariant visit(TermsSetQuery termsSetQuery) {
		co.elastic.clients.elasticsearch._types.query_dsl.TermsSetQuery.Builder
			builder = QueryBuilders.termsSet();

		SetterUtil.setNotNullFloat(builder::boost, termsSetQuery.getBoost());

		builder.field(termsSetQuery.getFieldName());

		SetterUtil.setNotBlankString(
			builder::minimumShouldMatchField,
			termsSetQuery.getMinimumShouldMatchField());
		SetterUtil.setNotNullScript(
			builder::minimumShouldMatchScript,
			termsSetQuery.getMinimumShouldMatchScript());

		ListUtil.isNotEmptyForEach(
			termsSetQuery.getValues(),
			value -> builder.terms(GetterUtil.getString(value)));

		return builder.build();
	}

	@Override
	public QueryVariant visit(WildcardQuery wildcardQuery) {
		co.elastic.clients.elasticsearch._types.query_dsl.WildcardQuery.Builder
			builder = QueryBuilders.wildcard();

		SetterUtil.setNotNullFloat(builder::boost, wildcardQuery.getBoost());

		builder.field(wildcardQuery.getField());

		SetterUtil.setNotBlankString(
			builder::rewrite, wildcardQuery.getRewrite());

		builder.value(wildcardQuery.getValue());

		return builder.build();
	}

	@Override
	public QueryVariant visit(WrapperQuery wrapperQuery) {
		co.elastic.clients.elasticsearch._types.query_dsl.WrapperQuery.Builder
			builder = QueryBuilders.wrapper();

		SetterUtil.setNotNullFloat(builder::boost, wrapperQuery.getBoost());

		builder.query(Base64.encode(wrapperQuery.getSource()));

		return builder.build();
	}

	protected String translateMatchQueryRewriteMethod(
		MatchQuery.RewriteMethod rewriteMethod) {

		if (rewriteMethod == MatchQuery.RewriteMethod.CONSTANT_SCORE_AUTO) {
			return "constant_score_auto";
		}
		else if (rewriteMethod ==
					MatchQuery.RewriteMethod.CONSTANT_SCORE_BOOLEAN) {

			return "constant_score_boolean";
		}
		else if (rewriteMethod ==
					MatchQuery.RewriteMethod.CONSTANT_SCORE_FILTER) {

			return "constant_score_filter";
		}
		else if (rewriteMethod == MatchQuery.RewriteMethod.SCORING_BOOLEAN) {
			return "scoring_boolean";
		}
		else if (rewriteMethod == MatchQuery.RewriteMethod.TOP_TERMS_BOOST_N) {
			return "top_terms_boost_N";
		}
		else if (rewriteMethod == MatchQuery.RewriteMethod.TOP_TERMS_N) {
			return "top_terms_N";
		}

		throw new IllegalArgumentException(
			"Invalid rewrite method " + rewriteMethod);
	}

	protected ZeroTermsQuery translateMatchQueryZeroTermsQuery(
		MatchQuery.ZeroTermsQuery zeroTermsQuery) {

		if (zeroTermsQuery == MatchQuery.ZeroTermsQuery.ALL) {
			return ZeroTermsQuery.All;
		}
		else if (zeroTermsQuery == MatchQuery.ZeroTermsQuery.NONE) {
			return ZeroTermsQuery.None;
		}

		throw new IllegalArgumentException(
			"Invalid zero terms query " + zeroTermsQuery);
	}

	protected TextQueryType translateMultiMatchQueryType(
		MultiMatchQuery.Type type) {

		if (type == MultiMatchQuery.Type.BEST_FIELDS) {
			return TextQueryType.BestFields;
		}
		else if (type == MultiMatchQuery.Type.BOOL_PREFIX) {
			return TextQueryType.BoolPrefix;
		}
		else if (type == MultiMatchQuery.Type.CROSS_FIELDS) {
			return TextQueryType.CrossFields;
		}
		else if (type == MultiMatchQuery.Type.MOST_FIELDS) {
			return TextQueryType.MostFields;
		}
		else if (type == MultiMatchQuery.Type.PHRASE) {
			return TextQueryType.Phrase;
		}
		else if (type == MultiMatchQuery.Type.PHRASE_PREFIX) {
			return TextQueryType.PhrasePrefix;
		}

		throw new IllegalArgumentException(
			"Invalid multi match query type " + type);
	}

	private ElasticsearchQueryVisitor() {
	}

	private void _processBooleanQueryClauses(
		Consumer<co.elastic.clients.elasticsearch._types.query_dsl.Query>
			consumer,
		List<Query> queryClauses) {

		for (Query query : queryClauses) {
			consumer.accept(
				new co.elastic.clients.elasticsearch._types.query_dsl.Query(
					query.accept(this)));
		}
	}

	private FunctionBoostMode _translateCombineFunction(
		CombineFunction combineFunction) {

		if (combineFunction == CombineFunction.AVG) {
			return FunctionBoostMode.Avg;
		}
		else if (combineFunction == CombineFunction.MAX) {
			return FunctionBoostMode.Max;
		}
		else if (combineFunction == CombineFunction.MIN) {
			return FunctionBoostMode.Min;
		}
		else if (combineFunction == CombineFunction.MULTIPLY) {
			return FunctionBoostMode.Multiply;
		}
		else if (combineFunction == CombineFunction.REPLACE) {
			return FunctionBoostMode.Replace;
		}
		else if (combineFunction == CombineFunction.SUM) {
			return FunctionBoostMode.Sum;
		}

		throw new IllegalArgumentException(
			"Invalid combine function " + combineFunction);
	}

	private List<Like> _translateDocumentIdentifiers(
		Set<MoreLikeThisQuery.DocumentIdentifier> documentIdentifiers) {

		List<Like> likes = new ArrayList<>();

		if (SetUtil.isEmpty(documentIdentifiers)) {
			return likes;
		}

		documentIdentifiers.forEach(
			documentIdentifier -> {
				LikeDocument.Builder builder = new LikeDocument.Builder();

				builder.id(documentIdentifier.getId());
				builder.index(documentIdentifier.getIndex());

				likes.add(Like.of(l -> l.document(builder.build())));
			});

		return likes;
	}

	private QueryVariant _translateMatchPhrasePrefixQuery(
		MatchQuery matchQuery, String value) {

		co.elastic.clients.elasticsearch._types.query_dsl.
			MatchPhrasePrefixQuery.Builder builder =
				QueryBuilders.matchPhrasePrefix();

		SetterUtil.setNotBlankString(
			builder::analyzer, matchQuery.getAnalyzer());
		SetterUtil.setNotNullFloat(builder::boost, matchQuery.getBoost());

		builder.field(matchQuery.getField());

		SetterUtil.setNotNullInteger(
			builder::maxExpansions, matchQuery.getMaxExpansions());

		builder.query(value);

		SetterUtil.setNotNullInteger(builder::slop, matchQuery.getSlop());

		return builder.build();
	}

	private QueryVariant _translateMatchPhraseQuery(
		MatchQuery matchQuery, String value) {

		co.elastic.clients.elasticsearch._types.query_dsl.MatchPhraseQuery.
			Builder builder = QueryBuilders.matchPhrase();

		SetterUtil.setNotBlankString(
			builder::analyzer, matchQuery.getAnalyzer());
		SetterUtil.setNotNullFloat(builder::boost, matchQuery.getBoost());

		builder.field(matchQuery.getField());
		builder.query(value);

		SetterUtil.setNotNullInteger(builder::slop, matchQuery.getSlop());

		return builder.build();
	}

	private QueryVariant _translateMatchQuery(
		MatchQuery matchQuery, Object value) {

		co.elastic.clients.elasticsearch._types.query_dsl.MatchQuery.Builder
			builder = QueryBuilders.match();

		SetterUtil.setNotBlankString(
			builder::analyzer, matchQuery.getAnalyzer());
		SetterUtil.setNotNullFloat(builder::boost, matchQuery.getBoost());
		SetterUtil.setNotNullFloatAsDouble(
			builder::cutoffFrequency, matchQuery.getCutOffFrequency());

		builder.field(matchQuery.getField());

		SetterUtil.setNotNullValueAsString(
			builder::fuzziness, matchQuery.getFuzziness());

		if (matchQuery.getFuzzyRewriteMethod() != null) {
			builder.fuzzyRewrite(
				translateMatchQueryRewriteMethod(
					matchQuery.getFuzzyRewriteMethod()));
		}

		SetterUtil.setNotNullBoolean(
			builder::fuzzyTranspositions, matchQuery.isFuzzyTranspositions());
		SetterUtil.setNotNullBoolean(builder::lenient, matchQuery.isLenient());
		SetterUtil.setNotNullInteger(
			builder::maxExpansions, matchQuery.getMaxExpansions());
		SetterUtil.setNotBlankString(
			builder::minimumShouldMatch, matchQuery.getMinShouldMatch());

		if (matchQuery.getOperator() != null) {
			builder.operator(_translateOperator(matchQuery.getOperator()));
		}

		SetterUtil.setNotNullInteger(
			builder::prefixLength, matchQuery.getPrefixLength());

		builder.query(ConversionUtil.toFieldValue(value));

		if (matchQuery.getZeroTermsQuery() != null) {
			builder.zeroTermsQuery(
				translateMatchQueryZeroTermsQuery(
					matchQuery.getZeroTermsQuery()));
		}

		return builder.build();
	}

	private co.elastic.clients.elasticsearch._types.query_dsl.Operator
		_translateOperator(Operator operator) {

		if (operator == Operator.AND) {
			return co.elastic.clients.elasticsearch._types.query_dsl.Operator.
				And;
		}
		else if (operator == Operator.OR) {
			return co.elastic.clients.elasticsearch._types.query_dsl.Operator.
				Or;
		}

		throw new IllegalArgumentException("Invalid operator " + operator);
	}

	private String _translateRegexFlags(int regexFlag) {
		if (regexFlag == 0xffff) {
			return "ALL";
		}

		if (regexFlag == 0x0008) {
			return "ANYSTRING";
		}

		if (regexFlag == 0x0010) {
			return "AUTOMATON";
		}

		if (regexFlag == 0x0002) {
			return "COMPLEMENT";
		}

		if (regexFlag == 0x0004) {
			return "EMPTY";
		}

		if (regexFlag == 0x0001) {
			return "INTERSECTION";
		}

		if (regexFlag == 0x0020) {
			return "INTERVAL";
		}

		if (regexFlag == 0) {
			return "NONE";
		}

		throw new IllegalArgumentException(
			"Invalid regex flag value " + regexFlag);
	}

	private FunctionScoreMode _translateScoreMore(
		FunctionScoreQuery.ScoreMode scoreMode) {

		if (scoreMode == FunctionScoreQuery.ScoreMode.AVG) {
			return FunctionScoreMode.Avg;
		}
		else if (scoreMode == FunctionScoreQuery.ScoreMode.FIRST) {
			return FunctionScoreMode.First;
		}
		else if (scoreMode == FunctionScoreQuery.ScoreMode.MAX) {
			return FunctionScoreMode.Max;
		}
		else if (scoreMode == FunctionScoreQuery.ScoreMode.MIN) {
			return FunctionScoreMode.Min;
		}
		else if (scoreMode == FunctionScoreQuery.ScoreMode.MULTIPLY) {
			return FunctionScoreMode.Multiply;
		}
		else if (scoreMode == FunctionScoreQuery.ScoreMode.SUM) {
			return FunctionScoreMode.Sum;
		}

		throw new IllegalArgumentException(
			"Invalid function score query score mode " + scoreMode);
	}

	private GeoShapeRelation _translateShapeRelation(
		ShapeRelation shapeRelation) {

		if (shapeRelation == ShapeRelation.CONTAINS) {
			return GeoShapeRelation.Contains;
		}

		if (shapeRelation == ShapeRelation.DISJOINT) {
			return GeoShapeRelation.Disjoint;
		}

		if (shapeRelation == ShapeRelation.INTERSECTS) {
			return GeoShapeRelation.Intersects;
		}

		if (shapeRelation == ShapeRelation.WITHIN) {
			return GeoShapeRelation.Within;
		}

		throw new IllegalArgumentException(
			"Invalid shape relation " + shapeRelation);
	}

	private final ElasticsearchScoreFunctionTranslator
		_elasticsearchScoreFunctionTranslator =
			new ElasticsearchScoreFunctionTranslator();
	private final GeoTranslator _geoTranslator = new GeoTranslator();
	private final ScriptTranslator _scriptTranslator = new ScriptTranslator();

}