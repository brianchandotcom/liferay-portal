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

package com.liferay.portal.search.elasticsearch7.internal.query;

/**
 * @author Michael C. Han
 */
public class ElasticsearchQueryTranslatorFixture {

	public ElasticsearchQueryTranslatorFixture() {
		_elasticsearchQueryTranslator = new ElasticsearchQueryTranslator() {
			{
				setBooleanQueryTranslator(new BooleanQueryTranslatorImpl());
				setBoostingQueryTranslator(new BoostingQueryTranslatorImpl());
				setCommonTermsQueryTranslator(
					new CommonTermsQueryTranslatorImpl());
				setConstantScoreQueryTranslator(
					new ConstantScoreQueryTranslatorImpl());
				setDateRangeTermQueryTranslator(
					new DateRangeTermQueryTranslatorImpl());
				setDisMaxQueryTranslator(new DisMaxQueryTranslatorImpl());
				setExistsQueryTranslator(new ExistsQueryTranslatorImpl());
				setFunctionScoreQueryTranslator(
					new FunctionScoreQueryTranslatorImpl());
				setFuzzyQueryTranslator(new FuzzyQueryTranslatorImpl());
				setGeoBoundingBoxQueryTranslator(
					new GeoBoundingBoxQueryTranslatorImpl());
				setGeoDistanceQueryTranslator(
					new GeoDistanceQueryTranslatorImpl());
				setGeoDistanceRangeQueryTranslator(
					new GeoDistanceRangeQueryTranslatorImpl());
				setGeoPolygonQueryTranslator(
					new GeoPolygonQueryTranslatorImpl());
				setGeoShapeQueryTranslator(new GeoShapeQueryTranslatorImpl());
				setIdsQueryTranslator(new IdsQueryTranslatorImpl());
				setMatchAllQueryTranslator(new MatchAllQueryTranslatorImpl());
				setMatchPhraseQueryTranslator(
					new MatchPhraseQueryTranslatorImpl());
				setMatchPhrasePrefixQueryTranslator(
					new MatchPhrasePrefixQueryTranslatorImpl());
				setMatchQueryTranslator(new MatchQueryTranslatorImpl());
				setMoreLikeThisQueryTranslator(
					new MoreLikeThisQueryTranslatorImpl());
				setMultiMatchQueryTranslator(
					new MultiMatchQueryTranslatorImpl());
				setNestedQueryTranslator(new NestedQueryTranslatorImpl());
				setPercolateQueryTranslator(new PercolateQueryTranslatorImpl());
				setPrefixQueryTranslator(new PrefixQueryTranslatorImpl());
				setRangeTermQueryTranslator(new RangeTermQueryTranslatorImpl());
				setRegexQueryTranslator(new RegexQueryTranslatorImpl());
				setScriptQueryTranslator(new ScriptQueryTranslatorImpl());
				setSimpleQueryStringQueryTranslator(
					new SimpleStringQueryTranslatorImpl());
				setStringQueryTranslator(new StringQueryTranslatorImpl());
				setTermQueryTranslator(new TermQueryTranslatorImpl());
				setTermsQueryTranslator(new TermsQueryTranslatorImpl());
				setTermsSetQueryTranslator(new TermsSetQueryTranslatorImpl());
				setWildcardQueryTranslator(new WildcardQueryTranslatorImpl());
			}
		};
	}

	public ElasticsearchQueryTranslator getElasticsearchQueryTranslator() {
		return _elasticsearchQueryTranslator;
	}

	private final ElasticsearchQueryTranslator _elasticsearchQueryTranslator;

}