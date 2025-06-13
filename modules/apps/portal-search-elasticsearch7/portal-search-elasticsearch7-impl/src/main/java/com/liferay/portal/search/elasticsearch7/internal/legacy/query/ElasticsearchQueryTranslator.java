/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.elasticsearch7.internal.legacy.query;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.search.BooleanQuery;
import com.liferay.portal.kernel.search.Query;
import com.liferay.portal.kernel.search.QueryTerm;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.TermQuery;
import com.liferay.portal.kernel.search.TermRangeQuery;
import com.liferay.portal.kernel.search.WildcardQuery;
import com.liferay.portal.kernel.search.generic.DisMaxQuery;
import com.liferay.portal.kernel.search.generic.FuzzyQuery;
import com.liferay.portal.kernel.search.generic.MatchAllQuery;
import com.liferay.portal.kernel.search.generic.MatchQuery;
import com.liferay.portal.kernel.search.generic.MoreLikeThisQuery;
import com.liferay.portal.kernel.search.generic.MultiMatchQuery;
import com.liferay.portal.kernel.search.generic.NestedQuery;
import com.liferay.portal.kernel.search.generic.StringQuery;
import com.liferay.portal.kernel.search.query.QueryTranslator;
import com.liferay.portal.kernel.search.query.QueryVisitor;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.index.query.MatchPhrasePrefixQueryBuilder;
import org.elasticsearch.index.query.MatchPhraseQueryBuilder;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.Operator;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.index.query.WildcardQueryBuilder;
import org.elasticsearch.index.query.ZeroTermsQueryOption;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author André de Oliveira
 * @author Miguel Angelo Caldas Gallindo
 */
@Component(
	property = "search.engine.impl=Elasticsearch",
	service = QueryTranslator.class
)
public class ElasticsearchQueryTranslator
	implements QueryTranslator<QueryBuilder>, QueryVisitor<QueryBuilder> {

	@Override
	public QueryBuilder translate(Query query, SearchContext searchContext) {
		QueryBuilder queryBuilder = query.accept(this);

		if (queryBuilder == null) {
			queryBuilder = QueryBuilders.queryStringQuery(query.toString());
		}

		return queryBuilder;
	}

	@Override
	public QueryBuilder visitQuery(BooleanQuery booleanQuery) {
		return booleanQueryTranslator.translate(booleanQuery, this);
	}

	@Override
	public QueryBuilder visitQuery(DisMaxQuery disMaxQuery) {
		return disMaxQueryTranslator.translate(disMaxQuery, this);
	}

	@Override
	public QueryBuilder visitQuery(FuzzyQuery fuzzyQuery) {
		return fuzzyQueryTranslator.translate(fuzzyQuery);
	}

	@Override
	public QueryBuilder visitQuery(MatchAllQuery matchAllQuery) {
		return matchAllQueryTranslator.translate(matchAllQuery);
	}

	@Override
	public QueryBuilder visitQuery(MatchQuery matchQuery) {
		String field = matchQuery.getField();

		MatchQuery.Type type = matchQuery.getType();
		String value = matchQuery.getValue();

		if (value.startsWith(StringPool.QUOTE) &&
			value.endsWith(StringPool.QUOTE)) {

			type = MatchQuery.Type.PHRASE;

			value = StringUtil.unquote(value);

			if (value.endsWith(StringPool.STAR)) {
				type = MatchQuery.Type.PHRASE_PREFIX;
			}
		}

		if ((type == null) || (type == MatchQuery.Type.BOOLEAN)) {
			return _translateMatchQuery(field, value, matchQuery);
		}
		else if (type == MatchQuery.Type.PHRASE) {
			return _translateMatchPhraseQuery(field, value, matchQuery);
		}
		else if (type == MatchQuery.Type.PHRASE_PREFIX) {
			return _translateMatchPhrasePrefixQuery(field, value, matchQuery);
		}

		throw new IllegalArgumentException("Invalid match query type: " + type);
	}

	@Override
	public QueryBuilder visitQuery(MoreLikeThisQuery moreLikeThisQuery) {
		return moreLikeThisQueryTranslator.translate(moreLikeThisQuery);
	}

	@Override
	public QueryBuilder visitQuery(MultiMatchQuery multiMatchQuery) {
		return multiMatchQueryTranslator.translate(multiMatchQuery);
	}

	@Override
	public QueryBuilder visitQuery(NestedQuery nestedQuery) {
		return nestedQueryTranslator.translate(nestedQuery, this);
	}

	@Override
	public QueryBuilder visitQuery(StringQuery stringQuery) {
		return stringQueryTranslator.translate(stringQuery);
	}

	@Override
	public QueryBuilder visitQuery(TermQuery termQuery) {
		QueryTerm queryTerm = termQuery.getQueryTerm();

		TermQueryBuilder termQueryBuilder = QueryBuilders.termQuery(
			queryTerm.getField(), queryTerm.getValue());

		if (!termQuery.isDefaultBoost()) {
			termQueryBuilder.boost(termQuery.getBoost());
		}

		return termQueryBuilder;
	}

	@Override
	public QueryBuilder visitQuery(TermRangeQuery termRangeQuery) {
		return termRangeQueryTranslator.translate(termRangeQuery);
	}

	@Override
	public QueryBuilder visitQuery(WildcardQuery wildcardQuery) {
		QueryTerm queryTerm = wildcardQuery.getQueryTerm();

		WildcardQueryBuilder wildcardQueryBuilder = QueryBuilders.wildcardQuery(
			queryTerm.getField(), queryTerm.getValue());

		if (!wildcardQuery.isDefaultBoost()) {
			wildcardQueryBuilder.boost(wildcardQuery.getBoost());
		}

		return wildcardQueryBuilder;
	}

	@Reference
	protected BooleanQueryTranslator booleanQueryTranslator;

	@Reference
	protected DisMaxQueryTranslator disMaxQueryTranslator;

	@Reference
	protected FuzzyQueryTranslator fuzzyQueryTranslator;

	@Reference
	protected MatchAllQueryTranslator matchAllQueryTranslator;

	@Reference
	protected MoreLikeThisQueryTranslator moreLikeThisQueryTranslator;

	@Reference
	protected MultiMatchQueryTranslator multiMatchQueryTranslator;

	@Reference
	protected NestedQueryTranslator nestedQueryTranslator;

	@Reference
	protected StringQueryTranslator stringQueryTranslator;

	@Reference
	protected TermRangeQueryTranslator termRangeQueryTranslator;

	private QueryBuilder _translateMatchPhrasePrefixQuery(
		String field, String value, MatchQuery matchQuery) {

		MatchPhrasePrefixQueryBuilder matchPhrasePrefixQueryBuilder =
			QueryBuilders.matchPhrasePrefixQuery(field, value);

		if (Validator.isNotNull(matchQuery.getAnalyzer())) {
			matchPhrasePrefixQueryBuilder.analyzer(matchQuery.getAnalyzer());
		}

		if (matchQuery.getMaxExpansions() != null) {
			matchPhrasePrefixQueryBuilder.maxExpansions(
				matchQuery.getMaxExpansions());
		}

		if (matchQuery.getSlop() != null) {
			matchPhrasePrefixQueryBuilder.slop(matchQuery.getSlop());
		}

		if (!matchQuery.isDefaultBoost()) {
			matchPhrasePrefixQueryBuilder.boost(matchQuery.getBoost());
		}

		return matchPhrasePrefixQueryBuilder;
	}

	private QueryBuilder _translateMatchPhraseQuery(
		String field, String value, MatchQuery matchQuery) {

		MatchPhraseQueryBuilder matchPhraseQueryBuilder =
			QueryBuilders.matchPhraseQuery(field, value);

		if (Validator.isNotNull(matchQuery.getAnalyzer())) {
			matchPhraseQueryBuilder.analyzer(matchQuery.getAnalyzer());
		}

		if (matchQuery.getSlop() != null) {
			matchPhraseQueryBuilder.slop(matchQuery.getSlop());
		}

		if (!matchQuery.isDefaultBoost()) {
			matchPhraseQueryBuilder.boost(matchQuery.getBoost());
		}

		return matchPhraseQueryBuilder;
	}

	private QueryBuilder _translateMatchQuery(
		String field, String value, MatchQuery matchQuery) {

		MatchQueryBuilder matchQueryBuilder = QueryBuilders.matchQuery(
			field, value);

		if (Validator.isNotNull(matchQuery.getAnalyzer())) {
			matchQueryBuilder.analyzer(matchQuery.getAnalyzer());
		}

		if (matchQuery.getCutOffFrequency() != null) {
			matchQueryBuilder.cutoffFrequency(matchQuery.getCutOffFrequency());
		}

		if (matchQuery.getFuzziness() != null) {
			matchQueryBuilder.fuzziness(
				Fuzziness.build(matchQuery.getFuzziness()));
		}

		if (matchQuery.getFuzzyRewriteMethod() != null) {
			String matchQueryFuzzyRewrite = MatchQueryTranslatorUtil.translate(
				matchQuery.getFuzzyRewriteMethod());

			matchQueryBuilder.fuzzyRewrite(matchQueryFuzzyRewrite);
		}

		if (matchQuery.getMaxExpansions() != null) {
			matchQueryBuilder.maxExpansions(matchQuery.getMaxExpansions());
		}

		if (Validator.isNotNull(matchQuery.getMinShouldMatch())) {
			matchQueryBuilder.minimumShouldMatch(
				matchQuery.getMinShouldMatch());
		}

		if (matchQuery.getOperator() != null) {
			Operator operator = MatchQueryTranslatorUtil.translate(
				matchQuery.getOperator());

			matchQueryBuilder.operator(operator);
		}

		if (matchQuery.getPrefixLength() != null) {
			matchQueryBuilder.prefixLength(matchQuery.getPrefixLength());
		}

		if (matchQuery.getZeroTermsQuery() != null) {
			ZeroTermsQueryOption zeroTermsQueryOption =
				MatchQueryTranslatorUtil.translate(
					matchQuery.getZeroTermsQuery());

			matchQueryBuilder.zeroTermsQuery(zeroTermsQueryOption);
		}

		if (!matchQuery.isDefaultBoost()) {
			matchQueryBuilder.boost(matchQuery.getBoost());
		}

		if (matchQuery.isFuzzyTranspositions() != null) {
			matchQueryBuilder.fuzzyTranspositions(
				matchQuery.isFuzzyTranspositions());
		}

		if (matchQuery.isLenient() != null) {
			matchQueryBuilder.lenient(matchQuery.isLenient());
		}

		return matchQueryBuilder;
	}

}