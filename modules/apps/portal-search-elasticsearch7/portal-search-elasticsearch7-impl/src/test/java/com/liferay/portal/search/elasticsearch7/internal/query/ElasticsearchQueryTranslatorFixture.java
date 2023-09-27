/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.elasticsearch7.internal.query;

import com.liferay.portal.kernel.test.ReflectionTestUtil;

/**
 * @author Michael C. Han
 */
public class ElasticsearchQueryTranslatorFixture {

	public ElasticsearchQueryTranslatorFixture() {
		ReflectionTestUtil.setFieldValue(
			_elasticsearchQueryTranslator, "_booleanQueryTranslator",
			new BooleanQueryTranslatorImpl());
		ReflectionTestUtil.setFieldValue(
			_elasticsearchQueryTranslator, "_boostingQueryTranslator",
			new BoostingQueryTranslatorImpl());
		ReflectionTestUtil.setFieldValue(
			_elasticsearchQueryTranslator, "_commonTermsQueryTranslator",
			new CommonTermsQueryTranslatorImpl());
		ReflectionTestUtil.setFieldValue(
			_elasticsearchQueryTranslator, "_constantScoreQueryTranslator",
			new ConstantScoreQueryTranslatorImpl());
		ReflectionTestUtil.setFieldValue(
			_elasticsearchQueryTranslator, "_dateRangeTermQueryTranslator",
			new DateRangeTermQueryTranslatorImpl());
		ReflectionTestUtil.setFieldValue(
			_elasticsearchQueryTranslator, "_disMaxQueryTranslator",
			new DisMaxQueryTranslatorImpl());
		ReflectionTestUtil.setFieldValue(
			_elasticsearchQueryTranslator, "_existsQueryTranslator",
			new ExistsQueryTranslatorImpl());
		ReflectionTestUtil.setFieldValue(
			_elasticsearchQueryTranslator, "_functionScoreQueryTranslator",
			new FunctionScoreQueryTranslatorImpl());
		ReflectionTestUtil.setFieldValue(
			_elasticsearchQueryTranslator, "_fuzzyQueryTranslator",
			new FuzzyQueryTranslatorImpl());
		ReflectionTestUtil.setFieldValue(
			_elasticsearchQueryTranslator, "_geoBoundingBoxQueryTranslator",
			new GeoBoundingBoxQueryTranslatorImpl());
		ReflectionTestUtil.setFieldValue(
			_elasticsearchQueryTranslator, "_geoDistanceQueryTranslator",
			new GeoDistanceQueryTranslatorImpl());
		ReflectionTestUtil.setFieldValue(
			_elasticsearchQueryTranslator, "_geoDistanceRangeQueryTranslator",
			new GeoDistanceRangeQueryTranslatorImpl());
		ReflectionTestUtil.setFieldValue(
			_elasticsearchQueryTranslator, "_geoPolygonQueryTranslator",
			new GeoPolygonQueryTranslatorImpl());
		ReflectionTestUtil.setFieldValue(
			_elasticsearchQueryTranslator, "_geoShapeQueryTranslator",
			new GeoShapeQueryTranslatorImpl());
		ReflectionTestUtil.setFieldValue(
			_elasticsearchQueryTranslator, "_idsQueryTranslator",
			new IdsQueryTranslatorImpl());
		ReflectionTestUtil.setFieldValue(
			_elasticsearchQueryTranslator, "_matchAllQueryTranslator",
			new MatchAllQueryTranslatorImpl());
		ReflectionTestUtil.setFieldValue(
			_elasticsearchQueryTranslator, "_matchPhrasePrefixQueryTranslator",
			new MatchPhrasePrefixQueryTranslatorImpl());
		ReflectionTestUtil.setFieldValue(
			_elasticsearchQueryTranslator, "_matchPhraseQueryTranslator",
			new MatchPhraseQueryTranslatorImpl());
		ReflectionTestUtil.setFieldValue(
			_elasticsearchQueryTranslator, "_matchQueryTranslator",
			new MatchQueryTranslatorImpl());
		ReflectionTestUtil.setFieldValue(
			_elasticsearchQueryTranslator, "_moreLikeThisQueryTranslator",
			new MoreLikeThisQueryTranslatorImpl());
		ReflectionTestUtil.setFieldValue(
			_elasticsearchQueryTranslator, "_multiMatchQueryTranslator",
			new MultiMatchQueryTranslatorImpl());
		ReflectionTestUtil.setFieldValue(
			_elasticsearchQueryTranslator, "_nestedQueryTranslator",
			new NestedQueryTranslatorImpl());
		ReflectionTestUtil.setFieldValue(
			_elasticsearchQueryTranslator, "_percolateQueryTranslator",
			new PercolateQueryTranslatorImpl());
		ReflectionTestUtil.setFieldValue(
			_elasticsearchQueryTranslator, "_prefixQueryTranslator",
			new PrefixQueryTranslatorImpl());
		ReflectionTestUtil.setFieldValue(
			_elasticsearchQueryTranslator, "_rangeTermQueryTranslator",
			new RangeTermQueryTranslatorImpl());
		ReflectionTestUtil.setFieldValue(
			_elasticsearchQueryTranslator, "_regexQueryTranslator",
			new RegexQueryTranslatorImpl());
		ReflectionTestUtil.setFieldValue(
			_elasticsearchQueryTranslator, "_scriptQueryTranslator",
			new ScriptQueryTranslatorImpl());
		ReflectionTestUtil.setFieldValue(
			_elasticsearchQueryTranslator, "_simpleQueryStringQueryTranslator",
			new SimpleStringQueryTranslatorImpl());
		ReflectionTestUtil.setFieldValue(
			_elasticsearchQueryTranslator, "_stringQueryTranslator",
			new StringQueryTranslatorImpl());
		ReflectionTestUtil.setFieldValue(
			_elasticsearchQueryTranslator, "_termQueryTranslator",
			new TermQueryTranslatorImpl());
		ReflectionTestUtil.setFieldValue(
			_elasticsearchQueryTranslator, "_termsQueryTranslator",
			new TermsQueryTranslatorImpl());
		ReflectionTestUtil.setFieldValue(
			_elasticsearchQueryTranslator, "_termsSetQueryTranslator",
			new TermsSetQueryTranslatorImpl());
		ReflectionTestUtil.setFieldValue(
			_elasticsearchQueryTranslator, "_wildcardQueryTranslator",
			new WildcardQueryTranslatorImpl());
		ReflectionTestUtil.setFieldValue(
			_elasticsearchQueryTranslator, "_wrapperQueryTranslator",
			new WrapperQueryTranslatorImpl());
	}

	public ElasticsearchQueryTranslator getElasticsearchQueryTranslator() {
		return _elasticsearchQueryTranslator;
	}

	private final ElasticsearchQueryTranslator _elasticsearchQueryTranslator =
		new ElasticsearchQueryTranslator();

}