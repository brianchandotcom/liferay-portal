/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.elasticsearch7.internal.query;

import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.search.elasticsearch7.internal.geolocation.ElasticsearchShapeTranslator;
import com.liferay.portal.search.elasticsearch7.internal.geolocation.GeoLocationPointTranslator;
import com.liferay.portal.search.elasticsearch7.internal.query.geolocation.GeoExecTypeTranslator;
import com.liferay.portal.search.elasticsearch7.internal.query.geolocation.GeoValidationMethodTranslator;
import com.liferay.portal.search.elasticsearch7.internal.script.ScriptTranslator;
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
import com.liferay.portal.search.query.Operator;
import com.liferay.portal.search.query.PercolateQuery;
import com.liferay.portal.search.query.PrefixQuery;
import com.liferay.portal.search.query.Query;
import com.liferay.portal.search.query.QueryTranslator;
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
import com.liferay.portal.search.query.geolocation.ShapeRelation;
import com.liferay.portal.search.query.geolocation.SpatialStrategy;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import org.elasticsearch.common.bytes.BytesArray;
import org.elasticsearch.common.bytes.BytesReference;
import org.elasticsearch.index.query.BoostingQueryBuilder;
import org.elasticsearch.index.query.CommonTermsQueryBuilder;
import org.elasticsearch.index.query.GeoBoundingBoxQueryBuilder;
import org.elasticsearch.index.query.GeoDistanceQueryBuilder;
import org.elasticsearch.index.query.GeoPolygonQueryBuilder;
import org.elasticsearch.index.query.GeoShapeQueryBuilder;
import org.elasticsearch.index.query.MatchPhrasePrefixQueryBuilder;
import org.elasticsearch.index.query.PrefixQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.index.query.RegexpQueryBuilder;
import org.elasticsearch.index.query.SimpleQueryStringBuilder;
import org.elasticsearch.index.query.TermsSetQueryBuilder;
import org.elasticsearch.legacygeo.builders.ShapeBuilder;
import org.elasticsearch.percolator.PercolateQueryBuilder;
import org.elasticsearch.script.Script;
import org.elasticsearch.xcontent.XContentType;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Michael C. Han
 */
@Component(
	property = "search.engine.impl=Elasticsearch",
	service = QueryTranslator.class
)
public class ElasticsearchQueryTranslator
	implements QueryTranslator<QueryBuilder>, QueryVisitor<QueryBuilder> {

	@Override
	public QueryBuilder translate(Query query) {
		QueryBuilder queryBuilder = query.accept(this);

		if (queryBuilder == null) {
			queryBuilder = QueryBuilders.queryStringQuery(query.toString());
		}

		return queryBuilder;
	}

	@Override
	public QueryBuilder visit(BooleanQuery booleanQuery) {
		return _addBoost(
			booleanQuery,
			_booleanQueryTranslator.translate(booleanQuery, this));
	}

	@Override
	public QueryBuilder visit(BoostingQuery boostingQuery) {
		Query positiveQuery = boostingQuery.getPositiveQuery();

		QueryBuilder positiveQueryBuilder = positiveQuery.accept(this);

		Query negativeQuery = boostingQuery.getNegativeQuery();

		QueryBuilder negativeQueryBuilder = negativeQuery.accept(this);

		BoostingQueryBuilder boostingQueryBuilder = QueryBuilders.boostingQuery(
			positiveQueryBuilder, negativeQueryBuilder);

		Float negativeBoost = boostingQuery.getNegativeBoost();

		if (negativeBoost != null) {
			boostingQueryBuilder.negativeBoost(negativeBoost);
		}

		return _addBoost(boostingQuery, boostingQueryBuilder);
	}

	@Override
	public QueryBuilder visit(CommonTermsQuery commonTermsQuery) {
		CommonTermsQueryBuilder commonTermsQueryBuilder =
			QueryBuilders.commonTermsQuery(
				commonTermsQuery.getField(), commonTermsQuery.getText());

		if (commonTermsQuery.getAnalyzer() != null) {
			commonTermsQueryBuilder.analyzer(commonTermsQuery.getAnalyzer());
		}

		if (commonTermsQuery.getCutoffFrequency() != null) {
			commonTermsQueryBuilder.cutoffFrequency(
				commonTermsQuery.getCutoffFrequency());
		}

		if (commonTermsQuery.getHighFreqMinimumShouldMatch() != null) {
			commonTermsQueryBuilder.highFreqMinimumShouldMatch(
				commonTermsQuery.getHighFreqMinimumShouldMatch());
		}

		if (commonTermsQuery.getHighFreqOperator() != null) {
			commonTermsQueryBuilder.highFreqOperator(
				_translate(commonTermsQuery.getHighFreqOperator()));
		}

		if (commonTermsQuery.getLowFreqMinimumShouldMatch() != null) {
			commonTermsQueryBuilder.highFreqMinimumShouldMatch(
				commonTermsQuery.getLowFreqMinimumShouldMatch());
		}

		if (commonTermsQuery.getLowFreqOperator() != null) {
			commonTermsQueryBuilder.lowFreqOperator(
				_translate(commonTermsQuery.getLowFreqOperator()));
		}

		return _addBoost(commonTermsQuery, commonTermsQueryBuilder);
	}

	@Override
	public QueryBuilder visit(ConstantScoreQuery constantScoreQuery) {
		return _addBoost(
			constantScoreQuery,
			_constantScoreQueryTranslator.translate(constantScoreQuery, this));
	}

	@Override
	public QueryBuilder visit(DateRangeTermQuery dateRangeTermQuery) {
		RangeQueryBuilder rangeQueryBuilder = QueryBuilders.rangeQuery(
			dateRangeTermQuery.getField());

		if (dateRangeTermQuery.getDateFormat() != null) {
			rangeQueryBuilder.format(dateRangeTermQuery.getDateFormat());
		}

		rangeQueryBuilder.from(dateRangeTermQuery.getLowerBound());
		rangeQueryBuilder.includeLower(dateRangeTermQuery.isIncludesLower());
		rangeQueryBuilder.includeUpper(dateRangeTermQuery.isIncludesUpper());

		TimeZone timeZone = dateRangeTermQuery.getTimeZone();

		if (timeZone != null) {
			rangeQueryBuilder.timeZone(timeZone.getID());
		}

		rangeQueryBuilder.to(dateRangeTermQuery.getUpperBound());

		return _addBoost(dateRangeTermQuery, rangeQueryBuilder);
	}

	@Override
	public QueryBuilder visit(DisMaxQuery disMaxQuery) {
		return _addBoost(
			disMaxQuery, _disMaxQueryTranslator.translate(disMaxQuery, this));
	}

	@Override
	public QueryBuilder visit(ExistsQuery existsQuery) {
		return _addBoost(
			existsQuery, QueryBuilders.existsQuery(existsQuery.getField()));
	}

	@Override
	public QueryBuilder visit(FunctionScoreQuery functionScoreQuery) {
		return _addBoost(
			functionScoreQuery,
			_functionScoreQueryTranslator.translate(functionScoreQuery, this));
	}

	@Override
	public QueryBuilder visit(FuzzyQuery fuzzyQuery) {
		return _addBoost(
			fuzzyQuery, _fuzzyQueryTranslator.translate(fuzzyQuery));
	}

	@Override
	public QueryBuilder visit(GeoBoundingBoxQuery geoBoundingBoxQuery) {
		GeoBoundingBoxQueryBuilder geoBoundingBoxQueryBuilder =
			QueryBuilders.geoBoundingBoxQuery(geoBoundingBoxQuery.getField());

		geoBoundingBoxQueryBuilder.setCorners(
			GeoLocationPointTranslator.translate(
				geoBoundingBoxQuery.getTopLeftGeoLocationPoint()),
			GeoLocationPointTranslator.translate(
				geoBoundingBoxQuery.getBottomRightGeoLocationPoint()));

		if (geoBoundingBoxQuery.getGeoExecType() != null) {
			geoBoundingBoxQueryBuilder.type(
				_geoExecTypeTranslator.translate(
					geoBoundingBoxQuery.getGeoExecType()));
		}

		if (geoBoundingBoxQuery.getGeoValidationMethod() != null) {
			geoBoundingBoxQueryBuilder.setValidationMethod(
				_geoValidationMethodTranslator.translate(
					geoBoundingBoxQuery.getGeoValidationMethod()));
		}

		if (geoBoundingBoxQuery.getIgnoreUnmapped() != null) {
			geoBoundingBoxQueryBuilder.ignoreUnmapped(
				geoBoundingBoxQuery.getIgnoreUnmapped());
		}

		return _addBoost(geoBoundingBoxQuery, geoBoundingBoxQueryBuilder);
	}

	@Override
	public QueryBuilder visit(GeoDistanceQuery geoDistanceQuery) {
		GeoDistanceQueryBuilder geoDistanceQueryBuilder =
			QueryBuilders.geoDistanceQuery(geoDistanceQuery.getField());

		geoDistanceQueryBuilder.distance(
			String.valueOf(geoDistanceQuery.getGeoDistance()));

		GeoLocationPoint pinGeoLocationPoint =
			geoDistanceQuery.getPinGeoLocationPoint();

		geoDistanceQueryBuilder.point(
			pinGeoLocationPoint.getLatitude(),
			pinGeoLocationPoint.getLongitude());

		if (geoDistanceQuery.getGeoValidationMethod() != null) {
			geoDistanceQueryBuilder.setValidationMethod(
				_geoValidationMethodTranslator.translate(
					geoDistanceQuery.getGeoValidationMethod()));
		}

		if (geoDistanceQuery.getIgnoreUnmapped() != null) {
			geoDistanceQueryBuilder.ignoreUnmapped(
				geoDistanceQuery.getIgnoreUnmapped());
		}

		return _addBoost(geoDistanceQuery, geoDistanceQueryBuilder);
	}

	@Override
	public QueryBuilder visit(GeoDistanceRangeQuery geoDistanceRangeQuery) {
		return _addBoost(
			geoDistanceRangeQuery,
			_geoDistanceRangeQueryTranslator.translate(geoDistanceRangeQuery));
	}

	@Override
	public QueryBuilder visit(GeoPolygonQuery geoPolygonQuery) {
		GeoPolygonQueryBuilder geoPolygonQueryBuilder =
			QueryBuilders.geoPolygonQuery(
				geoPolygonQuery.getField(),
				TransformUtil.transform(
					geoPolygonQuery.getGeoLocationPoints(),
					GeoLocationPointTranslator::translate));

		if (geoPolygonQuery.getGeoValidationMethod() != null) {
			geoPolygonQueryBuilder.setValidationMethod(
				_geoValidationMethodTranslator.translate(
					geoPolygonQuery.getGeoValidationMethod()));
		}

		if (geoPolygonQuery.getIgnoreUnmapped() != null) {
			geoPolygonQueryBuilder.ignoreUnmapped(
				geoPolygonQuery.getIgnoreUnmapped());
		}

		return _addBoost(geoPolygonQuery, geoPolygonQueryBuilder);
	}

	@Override
	public QueryBuilder visit(GeoShapeQuery geoShapeQuery) {
		GeoShapeQueryBuilder geoShapeQueryBuilder = _translateQuery(
			geoShapeQuery);

		if (geoShapeQuery.getIgnoreUnmapped() != null) {
			geoShapeQueryBuilder.ignoreUnmapped(
				geoShapeQuery.getIgnoreUnmapped());
		}

		if (geoShapeQuery.getShapeRelation() != null) {
			geoShapeQueryBuilder.relation(
				_translate(geoShapeQuery.getShapeRelation()));
		}

		if (geoShapeQuery.getSpatialStrategy() != null) {
			geoShapeQueryBuilder.strategy(
				_translate(geoShapeQuery.getSpatialStrategy()));
		}

		return _addBoost(geoShapeQuery, geoShapeQueryBuilder);
	}

	@Override
	public QueryBuilder visit(IdsQuery idsQuery) {
		return _addBoost(idsQuery, _idsQueryTranslator.translate(idsQuery));
	}

	@Override
	public QueryBuilder visit(MatchAllQuery matchAllQuery) {
		return _addBoost(
			matchAllQuery, _matchAllQueryTranslator.translate(matchAllQuery));
	}

	@Override
	public QueryBuilder visit(MatchPhrasePrefixQuery matchPhrasePrefixQuery) {
		MatchPhrasePrefixQueryBuilder matchPhrasePrefixQueryBuilder =
			QueryBuilders.matchPhrasePrefixQuery(
				matchPhrasePrefixQuery.getField(),
				matchPhrasePrefixQuery.getValue());

		if (matchPhrasePrefixQuery.getAnalyzer() != null) {
			matchPhrasePrefixQueryBuilder.analyzer(
				matchPhrasePrefixQuery.getAnalyzer());
		}

		if (matchPhrasePrefixQuery.getSlop() != null) {
			matchPhrasePrefixQueryBuilder.slop(
				matchPhrasePrefixQuery.getSlop());
		}

		if (matchPhrasePrefixQuery.getMaxExpansions() != null) {
			matchPhrasePrefixQueryBuilder.maxExpansions(
				matchPhrasePrefixQuery.getMaxExpansions());
		}

		return _addBoost(matchPhrasePrefixQuery, matchPhrasePrefixQueryBuilder);
	}

	@Override
	public QueryBuilder visit(MatchPhraseQuery matchPhraseQuery) {
		return _addBoost(
			matchPhraseQuery,
			_matchPhraseQueryTranslator.translate(matchPhraseQuery));
	}

	@Override
	public QueryBuilder visit(MatchQuery matchQuery) {
		return _addBoost(
			matchQuery, _matchQueryTranslator.translate(matchQuery));
	}

	@Override
	public QueryBuilder visit(MoreLikeThisQuery moreLikeThisQuery) {
		return _addBoost(
			moreLikeThisQuery,
			_moreLikeThisQueryTranslator.translate(moreLikeThisQuery));
	}

	@Override
	public QueryBuilder visit(MultiMatchQuery multiMatchQuery) {
		return _addBoost(
			multiMatchQuery,
			_multiMatchQueryTranslator.translate(multiMatchQuery));
	}

	@Override
	public QueryBuilder visit(NestedQuery nestedQuery) {
		return _addBoost(
			nestedQuery, _nestedQueryTranslator.translate(nestedQuery, this));
	}

	@Override
	public QueryBuilder visit(PercolateQuery percolateQuery) {
		List<String> documentJSONs = percolateQuery.getDocumentJSONs();

		List<BytesReference> bytesArrays = new ArrayList<>();

		documentJSONs.forEach(
			documentJSON -> bytesArrays.add(new BytesArray(documentJSON)));

		return _addBoost(
			percolateQuery,
			new PercolateQueryBuilder(
				percolateQuery.getField(), bytesArrays, XContentType.JSON));
	}

	@Override
	public QueryBuilder visit(PrefixQuery prefixQuery) {
		PrefixQueryBuilder prefixQueryBuilder = QueryBuilders.prefixQuery(
			prefixQuery.getField(), prefixQuery.getPrefix());

		if (prefixQuery.getRewrite() != null) {
			prefixQueryBuilder.rewrite(prefixQuery.getRewrite());
		}

		return _addBoost(prefixQuery, prefixQueryBuilder);
	}

	@Override
	public QueryBuilder visit(RangeTermQuery rangeTermQuery) {
		return _addBoost(
			rangeTermQuery,
			_rangeTermQueryTranslator.translate(rangeTermQuery));
	}

	@Override
	public QueryBuilder visit(RegexQuery regexQuery) {
		RegexpQueryBuilder regexpQueryBuilder = QueryBuilders.regexpQuery(
			regexQuery.getField(), regexQuery.getRegex());

		if (regexQuery.getMaxDeterminedStates() != null) {
			regexpQueryBuilder.maxDeterminizedStates(
				regexQuery.getMaxDeterminedStates());
		}

		if (regexQuery.getRegexFlags() != null) {
			regexpQueryBuilder.flags(regexQuery.getRegexFlags());
		}

		if (regexQuery.getRewrite() != null) {
			regexpQueryBuilder.rewrite(regexQuery.getRewrite());
		}

		return _addBoost(regexQuery, regexpQueryBuilder);
	}

	@Override
	public QueryBuilder visit(ScriptQuery scriptQuery) {
		return _addBoost(
			scriptQuery,
			QueryBuilders.scriptQuery(
				_scriptTranslator.translate(scriptQuery.getScript())));
	}

	@Override
	public QueryBuilder visit(SimpleStringQuery simpleStringQuery) {
		SimpleQueryStringBuilder simpleQueryStringBuilder =
			QueryBuilders.simpleQueryStringQuery(simpleStringQuery.getQuery());

		if (simpleStringQuery.getAnalyzer() != null) {
			simpleQueryStringBuilder.analyzer(simpleStringQuery.getAnalyzer());
		}

		if (simpleStringQuery.getAnalyzeWildcard() != null) {
			simpleQueryStringBuilder.analyzeWildcard(
				simpleStringQuery.getAnalyzeWildcard());
		}

		if (simpleStringQuery.getAutoGenerateSynonymsPhraseQuery() != null) {
			simpleQueryStringBuilder.autoGenerateSynonymsPhraseQuery(
				simpleStringQuery.getAutoGenerateSynonymsPhraseQuery());
		}

		Map<String, Float> fieldBoostMap = simpleStringQuery.getFieldBoostMap();

		if (MapUtil.isNotEmpty(fieldBoostMap)) {
			for (Map.Entry<String, Float> entry : fieldBoostMap.entrySet()) {
				Float value = entry.getValue();

				if (value != null) {
					simpleQueryStringBuilder.field(entry.getKey(), value);
				}
				else {
					simpleQueryStringBuilder.field(entry.getKey());
				}
			}
		}

		if (simpleStringQuery.getDefaultOperator() != null) {
			Operator operator = simpleStringQuery.getDefaultOperator();

			if (operator == Operator.OR) {
				simpleQueryStringBuilder.defaultOperator(
					org.elasticsearch.index.query.Operator.OR);
			}
			else if (operator == Operator.AND) {
				simpleQueryStringBuilder.defaultOperator(
					org.elasticsearch.index.query.Operator.AND);
			}
			else {
				throw new IllegalArgumentException(
					"Invalid operator: " + operator);
			}
		}

		if (simpleStringQuery.getFuzzyMaxExpansions() != null) {
			simpleQueryStringBuilder.fuzzyMaxExpansions(
				simpleStringQuery.getFuzzyMaxExpansions());
		}

		if (simpleStringQuery.getFuzzyPrefixLength() != null) {
			simpleQueryStringBuilder.fuzzyPrefixLength(
				simpleStringQuery.getFuzzyPrefixLength());
		}

		if (simpleStringQuery.getFuzzyTranspositions() != null) {
			simpleQueryStringBuilder.fuzzyTranspositions(
				simpleStringQuery.getFuzzyTranspositions());
		}

		if (simpleStringQuery.getLenient() != null) {
			simpleQueryStringBuilder.lenient(simpleStringQuery.getLenient());
		}

		if (simpleStringQuery.getQuoteFieldSuffix() != null) {
			simpleQueryStringBuilder.quoteFieldSuffix(
				simpleStringQuery.getQuoteFieldSuffix());
		}

		return _addBoost(simpleStringQuery, simpleQueryStringBuilder);
	}

	@Override
	public QueryBuilder visit(StringQuery stringQuery) {
		return _addBoost(
			stringQuery, _stringQueryTranslator.translate(stringQuery));
	}

	@Override
	public QueryBuilder visit(TermQuery termQuery) {
		return _addBoost(termQuery, _termQueryTranslator.translate(termQuery));
	}

	@Override
	public QueryBuilder visit(TermsQuery termsQuery) {
		return _addBoost(
			termsQuery, _termsQueryTranslator.translate(termsQuery));
	}

	@Override
	public QueryBuilder visit(TermsSetQuery termsSetQuery) {
		TermsSetQueryBuilder termsSetQueryBuilder = new TermsSetQueryBuilder(
			termsSetQuery.getFieldName(),
			ListUtil.toList(termsSetQuery.getValues()));

		if (!Validator.isBlank(termsSetQuery.getMinimumShouldMatchField())) {
			termsSetQueryBuilder.setMinimumShouldMatchField(
				termsSetQuery.getMinimumShouldMatchField());
		}

		if (termsSetQuery.getMinimumShouldMatchScript() != null) {
			Script script = _scriptTranslator.translate(
				termsSetQuery.getMinimumShouldMatchScript());

			termsSetQueryBuilder.setMinimumShouldMatchScript(script);
		}

		return _addBoost(termsSetQuery, termsSetQueryBuilder);
	}

	@Override
	public QueryBuilder visit(WildcardQuery wildcardQuery) {
		return _addBoost(
			wildcardQuery, _wildcardQueryTranslator.translate(wildcardQuery));
	}

	@Override
	public QueryBuilder visit(WrapperQuery wrapperQuery) {
		return _addBoost(
			wrapperQuery, QueryBuilders.wrapperQuery(wrapperQuery.getSource()));
	}

	private QueryBuilder _addBoost(Query query, QueryBuilder queryBuilder) {
		if (query.getBoost() != null) {
			queryBuilder.boost(query.getBoost());
		}

		return queryBuilder;
	}

	private org.elasticsearch.index.query.Operator _translate(
		Operator matchQueryOperator) {

		if (matchQueryOperator == Operator.AND) {
			return org.elasticsearch.index.query.Operator.AND;
		}
		else if (matchQueryOperator == Operator.OR) {
			return org.elasticsearch.index.query.Operator.AND;
		}

		throw new IllegalArgumentException(
			"Invalid operator: " + matchQueryOperator);
	}

	private org.elasticsearch.common.geo.ShapeRelation _translate(
		ShapeRelation shapeRelation) {

		if (shapeRelation == ShapeRelation.CONTAINS) {
			return org.elasticsearch.common.geo.ShapeRelation.CONTAINS;
		}

		if (shapeRelation == ShapeRelation.DISJOINT) {
			return org.elasticsearch.common.geo.ShapeRelation.DISJOINT;
		}

		if (shapeRelation == ShapeRelation.INTERSECTS) {
			return org.elasticsearch.common.geo.ShapeRelation.INTERSECTS;
		}

		if (shapeRelation == ShapeRelation.WITHIN) {
			return org.elasticsearch.common.geo.ShapeRelation.WITHIN;
		}

		throw new IllegalArgumentException(
			"Invalid ShapeRelation: " + shapeRelation);
	}

	private org.elasticsearch.common.geo.SpatialStrategy _translate(
		SpatialStrategy spatialStrategy) {

		if (spatialStrategy == SpatialStrategy.RECURSIVE) {
			return org.elasticsearch.common.geo.SpatialStrategy.RECURSIVE;
		}

		if (spatialStrategy == SpatialStrategy.TERM) {
			return org.elasticsearch.common.geo.SpatialStrategy.TERM;
		}

		throw new IllegalArgumentException(
			"Invalid SpatialStrategy: " + spatialStrategy);
	}

	private GeoShapeQueryBuilder _translateQuery(GeoShapeQuery geoShapeQuery) {
		if (geoShapeQuery.getIndexedShapeId() != null) {
			GeoShapeQueryBuilder geoShapeQueryBuilder =
				QueryBuilders.geoShapeQuery(
					geoShapeQuery.getField(), geoShapeQuery.getIndexedShapeId(),
					geoShapeQuery.getIndexedShapeType());

			if (geoShapeQuery.getIndexedShapeIndex() != null) {
				geoShapeQueryBuilder.indexedShapeIndex(
					geoShapeQuery.getIndexedShapeIndex());
			}

			if (geoShapeQuery.getIndexedShapePath() != null) {
				geoShapeQueryBuilder.indexedShapePath(
					geoShapeQuery.getIndexedShapePath());
			}

			if (geoShapeQuery.getIndexedShapeRouting() != null) {
				geoShapeQueryBuilder.indexedShapeRouting(
					geoShapeQuery.getIndexedShapeRouting());
			}

			return geoShapeQueryBuilder;
		}

		Shape shape = geoShapeQuery.getShape();

		ShapeBuilder shapeBuilder = shape.accept(_elasticsearchShapeTranslator);

		return new GeoShapeQueryBuilder(
			geoShapeQuery.getField(), shapeBuilder.buildGeometry());
	}

	@Reference
	private BooleanQueryTranslator _booleanQueryTranslator;

	@Reference
	private ConstantScoreQueryTranslator _constantScoreQueryTranslator;

	@Reference
	private DisMaxQueryTranslator _disMaxQueryTranslator;

	private final ElasticsearchShapeTranslator _elasticsearchShapeTranslator =
		new ElasticsearchShapeTranslator();

	@Reference
	private FunctionScoreQueryTranslator _functionScoreQueryTranslator;

	@Reference
	private FuzzyQueryTranslator _fuzzyQueryTranslator;

	@Reference
	private GeoDistanceRangeQueryTranslator _geoDistanceRangeQueryTranslator;

	private final GeoExecTypeTranslator _geoExecTypeTranslator =
		new GeoExecTypeTranslator();
	private final GeoValidationMethodTranslator _geoValidationMethodTranslator =
		new GeoValidationMethodTranslator();

	@Reference
	private IdsQueryTranslator _idsQueryTranslator;

	@Reference
	private MatchAllQueryTranslator _matchAllQueryTranslator;

	@Reference
	private MatchPhraseQueryTranslator _matchPhraseQueryTranslator;

	@Reference
	private MatchQueryTranslator _matchQueryTranslator;

	@Reference
	private MoreLikeThisQueryTranslator _moreLikeThisQueryTranslator;

	@Reference
	private MultiMatchQueryTranslator _multiMatchQueryTranslator;

	@Reference
	private NestedQueryTranslator _nestedQueryTranslator;

	@Reference
	private RangeTermQueryTranslator _rangeTermQueryTranslator;

	private final ScriptTranslator _scriptTranslator = new ScriptTranslator();

	@Reference
	private StringQueryTranslator _stringQueryTranslator;

	@Reference
	private TermQueryTranslator _termQueryTranslator;

	@Reference
	private TermsQueryTranslator _termsQueryTranslator;

	@Reference
	private WildcardQueryTranslator _wildcardQueryTranslator;

}