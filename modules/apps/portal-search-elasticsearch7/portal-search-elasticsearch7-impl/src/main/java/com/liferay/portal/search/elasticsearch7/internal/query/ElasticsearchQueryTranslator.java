/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.elasticsearch7.internal.query;

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

import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;

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
		QueryBuilder queryBuilder = _booleanQueryTranslator.translate(
			booleanQuery, this);

		if (booleanQuery.getBoost() != null) {
			queryBuilder.boost(booleanQuery.getBoost());
		}

		return queryBuilder;
	}

	@Override
	public QueryBuilder visit(BoostingQuery boostingQuery) {
		QueryBuilder queryBuilder = _boostingQueryTranslator.translate(
			boostingQuery, this);

		if (boostingQuery.getBoost() != null) {
			queryBuilder.boost(boostingQuery.getBoost());
		}

		return queryBuilder;
	}

	@Override
	public QueryBuilder visit(CommonTermsQuery commonTermsQuery) {
		QueryBuilder queryBuilder = _commonTermsQueryTranslator.translate(
			commonTermsQuery);

		if (commonTermsQuery.getBoost() != null) {
			queryBuilder.boost(commonTermsQuery.getBoost());
		}

		return queryBuilder;
	}

	@Override
	public QueryBuilder visit(ConstantScoreQuery constantScoreQuery) {
		QueryBuilder queryBuilder = _constantScoreQueryTranslator.translate(
			constantScoreQuery, this);

		if (constantScoreQuery.getBoost() != null) {
			queryBuilder.boost(constantScoreQuery.getBoost());
		}

		return queryBuilder;
	}

	@Override
	public QueryBuilder visit(DateRangeTermQuery dateRangeTermQuery) {
		QueryBuilder queryBuilder = _dateRangeTermQueryTranslator.translate(
			dateRangeTermQuery);

		if (dateRangeTermQuery.getBoost() != null) {
			queryBuilder.boost(dateRangeTermQuery.getBoost());
		}

		return queryBuilder;
	}

	@Override
	public QueryBuilder visit(DisMaxQuery disMaxQuery) {
		QueryBuilder queryBuilder = _disMaxQueryTranslator.translate(
			disMaxQuery, this);

		if (disMaxQuery.getBoost() != null) {
			queryBuilder.boost(disMaxQuery.getBoost());
		}

		return queryBuilder;
	}

	@Override
	public QueryBuilder visit(ExistsQuery existsQuery) {
		QueryBuilder queryBuilder = _existsQueryTranslator.translate(
			existsQuery);

		if (existsQuery.getBoost() != null) {
			queryBuilder.boost(existsQuery.getBoost());
		}

		return queryBuilder;
	}

	@Override
	public QueryBuilder visit(FunctionScoreQuery functionScoreQuery) {
		QueryBuilder queryBuilder = _functionScoreQueryTranslator.translate(
			functionScoreQuery, this);

		if (functionScoreQuery.getBoost() != null) {
			queryBuilder.boost(functionScoreQuery.getBoost());
		}

		return queryBuilder;
	}

	@Override
	public QueryBuilder visit(FuzzyQuery fuzzyQuery) {
		QueryBuilder queryBuilder = _fuzzyQueryTranslator.translate(fuzzyQuery);

		if (fuzzyQuery.getBoost() != null) {
			queryBuilder.boost(fuzzyQuery.getBoost());
		}

		return queryBuilder;
	}

	@Override
	public QueryBuilder visit(GeoBoundingBoxQuery geoBoundingBoxQuery) {
		QueryBuilder queryBuilder = _geoBoundingBoxQueryTranslator.translate(
			geoBoundingBoxQuery);

		if (geoBoundingBoxQuery.getBoost() != null) {
			queryBuilder.boost(geoBoundingBoxQuery.getBoost());
		}

		return queryBuilder;
	}

	@Override
	public QueryBuilder visit(GeoDistanceQuery geoDistanceQuery) {
		QueryBuilder queryBuilder = _geoDistanceQueryTranslator.translate(
			geoDistanceQuery);

		if (geoDistanceQuery.getBoost() != null) {
			queryBuilder.boost(geoDistanceQuery.getBoost());
		}

		return queryBuilder;
	}

	@Override
	public QueryBuilder visit(GeoDistanceRangeQuery geoDistanceRangeQuery) {
		QueryBuilder queryBuilder = _geoDistanceRangeQueryTranslator.translate(
			geoDistanceRangeQuery);

		if (geoDistanceRangeQuery.getBoost() != null) {
			queryBuilder.boost(geoDistanceRangeQuery.getBoost());
		}

		return queryBuilder;
	}

	@Override
	public QueryBuilder visit(GeoPolygonQuery geoPolygonQuery) {
		QueryBuilder queryBuilder = _geoPolygonQueryTranslator.translate(
			geoPolygonQuery);

		if (geoPolygonQuery.getBoost() != null) {
			queryBuilder.boost(geoPolygonQuery.getBoost());
		}

		return queryBuilder;
	}

	@Override
	public QueryBuilder visit(GeoShapeQuery geoShapeQuery) {
		QueryBuilder queryBuilder = _geoShapeQueryTranslator.translate(
			geoShapeQuery);

		if (geoShapeQuery.getBoost() != null) {
			queryBuilder.boost(geoShapeQuery.getBoost());
		}

		return queryBuilder;
	}

	@Override
	public QueryBuilder visit(IdsQuery idsQuery) {
		QueryBuilder queryBuilder = _idsQueryTranslator.translate(idsQuery);

		if (idsQuery.getBoost() != null) {
			queryBuilder.boost(idsQuery.getBoost());
		}

		return queryBuilder;
	}

	@Override
	public QueryBuilder visit(MatchAllQuery matchAllQuery) {
		QueryBuilder queryBuilder = _matchAllQueryTranslator.translate(
			matchAllQuery);

		if (matchAllQuery.getBoost() != null) {
			queryBuilder.boost(matchAllQuery.getBoost());
		}

		return queryBuilder;
	}

	@Override
	public QueryBuilder visit(MatchPhrasePrefixQuery matchPhrasePrefixQuery) {
		QueryBuilder queryBuilder = _matchPhrasePrefixQueryTranslator.translate(
			matchPhrasePrefixQuery);

		if (matchPhrasePrefixQuery.getBoost() != null) {
			queryBuilder.boost(matchPhrasePrefixQuery.getBoost());
		}

		return queryBuilder;
	}

	@Override
	public QueryBuilder visit(MatchPhraseQuery matchPhraseQuery) {
		QueryBuilder queryBuilder = _matchPhraseQueryTranslator.translate(
			matchPhraseQuery);

		if (matchPhraseQuery.getBoost() != null) {
			queryBuilder.boost(matchPhraseQuery.getBoost());
		}

		return queryBuilder;
	}

	@Override
	public QueryBuilder visit(MatchQuery matchQuery) {
		QueryBuilder queryBuilder = _matchQueryTranslator.translate(matchQuery);

		if (matchQuery.getBoost() != null) {
			queryBuilder.boost(matchQuery.getBoost());
		}

		return queryBuilder;
	}

	@Override
	public QueryBuilder visit(MoreLikeThisQuery moreLikeThisQuery) {
		QueryBuilder queryBuilder = _moreLikeThisQueryTranslator.translate(
			moreLikeThisQuery);

		if (moreLikeThisQuery.getBoost() != null) {
			queryBuilder.boost(moreLikeThisQuery.getBoost());
		}

		return queryBuilder;
	}

	@Override
	public QueryBuilder visit(MultiMatchQuery multiMatchQuery) {
		QueryBuilder queryBuilder = _multiMatchQueryTranslator.translate(
			multiMatchQuery);

		if (multiMatchQuery.getBoost() != null) {
			queryBuilder.boost(multiMatchQuery.getBoost());
		}

		return queryBuilder;
	}

	@Override
	public QueryBuilder visit(NestedQuery nestedQuery) {
		QueryBuilder queryBuilder = _nestedQueryTranslator.translate(
			nestedQuery, this);

		if (nestedQuery.getBoost() != null) {
			queryBuilder.boost(nestedQuery.getBoost());
		}

		return queryBuilder;
	}

	@Override
	public QueryBuilder visit(PercolateQuery percolateQuery) {
		QueryBuilder queryBuilder = _percolateQueryTranslator.translate(
			percolateQuery);

		if (percolateQuery.getBoost() != null) {
			queryBuilder.boost(percolateQuery.getBoost());
		}

		return queryBuilder;
	}

	@Override
	public QueryBuilder visit(PrefixQuery prefixQuery) {
		QueryBuilder queryBuilder = _prefixQueryTranslator.translate(
			prefixQuery);

		if (prefixQuery.getBoost() != null) {
			queryBuilder.boost(prefixQuery.getBoost());
		}

		return queryBuilder;
	}

	@Override
	public QueryBuilder visit(RangeTermQuery rangeTermQuery) {
		QueryBuilder queryBuilder = _rangeTermQueryTranslator.translate(
			rangeTermQuery);

		if (rangeTermQuery.getBoost() != null) {
			queryBuilder.boost(rangeTermQuery.getBoost());
		}

		return queryBuilder;
	}

	@Override
	public QueryBuilder visit(RegexQuery regexQuery) {
		QueryBuilder queryBuilder = _regexQueryTranslator.translate(regexQuery);

		if (regexQuery.getBoost() != null) {
			queryBuilder.boost(regexQuery.getBoost());
		}

		return queryBuilder;
	}

	@Override
	public QueryBuilder visit(ScriptQuery scriptQuery) {
		QueryBuilder queryBuilder = _scriptQueryTranslator.translate(
			scriptQuery);

		if (scriptQuery.getBoost() != null) {
			queryBuilder.boost(scriptQuery.getBoost());
		}

		return queryBuilder;
	}

	@Override
	public QueryBuilder visit(SimpleStringQuery simpleStringQuery) {
		QueryBuilder queryBuilder = _simpleQueryStringQueryTranslator.translate(
			simpleStringQuery);

		if (simpleStringQuery.getBoost() != null) {
			queryBuilder.boost(simpleStringQuery.getBoost());
		}

		return queryBuilder;
	}

	@Override
	public QueryBuilder visit(StringQuery stringQuery) {
		QueryBuilder queryBuilder = _stringQueryTranslator.translate(
			stringQuery);

		if (stringQuery.getBoost() != null) {
			queryBuilder.boost(stringQuery.getBoost());
		}

		return queryBuilder;
	}

	@Override
	public QueryBuilder visit(TermQuery termQuery) {
		QueryBuilder queryBuilder = _termQueryTranslator.translate(termQuery);

		if (termQuery.getBoost() != null) {
			queryBuilder.boost(termQuery.getBoost());
		}

		return queryBuilder;
	}

	@Override
	public QueryBuilder visit(TermsQuery termsQuery) {
		QueryBuilder queryBuilder = _termsQueryTranslator.translate(termsQuery);

		if (termsQuery.getBoost() != null) {
			queryBuilder.boost(termsQuery.getBoost());
		}

		return queryBuilder;
	}

	@Override
	public QueryBuilder visit(TermsSetQuery termsSetQuery) {
		QueryBuilder queryBuilder = _termsSetQueryTranslator.translate(
			termsSetQuery);

		if (termsSetQuery.getBoost() != null) {
			queryBuilder.boost(termsSetQuery.getBoost());
		}

		return queryBuilder;
	}

	@Override
	public QueryBuilder visit(WildcardQuery wildcardQuery) {
		QueryBuilder queryBuilder = _wildcardQueryTranslator.translate(
			wildcardQuery);

		if (wildcardQuery.getBoost() != null) {
			queryBuilder.boost(wildcardQuery.getBoost());
		}

		return queryBuilder;
	}

	@Override
	public QueryBuilder visit(WrapperQuery wrapperQuery) {
		QueryBuilder queryBuilder = _wrapperQueryTranslator.translate(
			wrapperQuery);

		if (wrapperQuery.getBoost() != null) {
			queryBuilder.boost(wrapperQuery.getBoost());
		}

		return queryBuilder;
	}

	@Reference
	private BooleanQueryTranslator _booleanQueryTranslator;

	@Reference
	private BoostingQueryTranslator _boostingQueryTranslator;

	@Reference
	private CommonTermsQueryTranslator _commonTermsQueryTranslator;

	@Reference
	private ConstantScoreQueryTranslator _constantScoreQueryTranslator;

	@Reference
	private DateRangeTermQueryTranslator _dateRangeTermQueryTranslator;

	@Reference
	private DisMaxQueryTranslator _disMaxQueryTranslator;

	@Reference
	private ExistsQueryTranslator _existsQueryTranslator;

	@Reference
	private FunctionScoreQueryTranslator _functionScoreQueryTranslator;

	@Reference
	private FuzzyQueryTranslator _fuzzyQueryTranslator;

	@Reference
	private GeoBoundingBoxQueryTranslator _geoBoundingBoxQueryTranslator;

	@Reference
	private GeoDistanceQueryTranslator _geoDistanceQueryTranslator;

	@Reference
	private GeoDistanceRangeQueryTranslator _geoDistanceRangeQueryTranslator;

	@Reference
	private GeoPolygonQueryTranslator _geoPolygonQueryTranslator;

	@Reference
	private GeoShapeQueryTranslator _geoShapeQueryTranslator;

	@Reference
	private IdsQueryTranslator _idsQueryTranslator;

	@Reference
	private MatchAllQueryTranslator _matchAllQueryTranslator;

	@Reference
	private MatchPhrasePrefixQueryTranslator _matchPhrasePrefixQueryTranslator;

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
	private PercolateQueryTranslator _percolateQueryTranslator;

	@Reference
	private PrefixQueryTranslator _prefixQueryTranslator;

	@Reference
	private RangeTermQueryTranslator _rangeTermQueryTranslator;

	@Reference
	private RegexQueryTranslator _regexQueryTranslator;

	@Reference
	private ScriptQueryTranslator _scriptQueryTranslator;

	@Reference
	private SimpleStringQueryTranslator _simpleQueryStringQueryTranslator;

	@Reference
	private StringQueryTranslator _stringQueryTranslator;

	@Reference
	private TermQueryTranslator _termQueryTranslator;

	@Reference
	private TermsQueryTranslator _termsQueryTranslator;

	@Reference
	private TermsSetQueryTranslator _termsSetQueryTranslator;

	@Reference
	private WildcardQueryTranslator _wildcardQueryTranslator;

	@Reference
	private WrapperQueryTranslator _wrapperQueryTranslator;

}