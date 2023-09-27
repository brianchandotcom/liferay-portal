/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.query;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author André de Oliveira
 * @author Miguel Angelo Caldas Gallindo
 */
@ProviderType
public interface QueryVisitor<T> {

	public T visit(BooleanQuery booleanQuery);

	public T visit(BoostingQuery boostingQuery);

	public T visit(CommonTermsQuery commonTermsQuery);

	public T visit(ConstantScoreQuery constantScoreQuery);

	public T visit(DateRangeTermQuery dateRangeTermQuery);

	public T visit(DisMaxQuery disMaxQuery);

	public T visit(ExistsQuery existsQuery);

	public T visit(FunctionScoreQuery functionScoreQuery);

	public T visit(FuzzyQuery fuzzyQuery);

	public T visit(GeoBoundingBoxQuery geoBoundingBoxQuery);

	public T visit(GeoDistanceQuery geoDistanceQuery);

	public T visit(GeoDistanceRangeQuery geoDistanceRangeQuery);

	public T visit(GeoPolygonQuery geoPolygonQuery);

	public T visit(GeoShapeQuery geoShapeQuery);

	public T visit(IdsQuery idsQuery);

	public T visit(MatchAllQuery matchAllQuery);

	public T visit(MatchPhrasePrefixQuery matchPhrasePrefixQuery);

	public T visit(MatchPhraseQuery matchPhraseQuery);

	public T visit(MatchQuery matchQuery);

	public T visit(MoreLikeThisQuery moreLikeThisQuery);

	public T visit(MultiMatchQuery multiMatchQuery);

	public T visit(NestedQuery nestedQuery);

	public T visit(PercolateQuery percolateQuery);

	public T visit(PrefixQuery prefixQuery);

	public T visit(RangeTermQuery rangeTermQuery);

	public T visit(RegexQuery regexQuery);

	public T visit(ScriptQuery scriptQuery);

	public T visit(SimpleStringQuery simpleStringQuery);

	public T visit(StringQuery stringQuery);

	public T visit(TermQuery termQuery);

	public T visit(TermsQuery termsQuery);

	public T visit(TermsSetQuery termsQuery);

	public T visit(WildcardQuery wildcardQuery);

	public T visit(WrapperQuery wrapperQuery);

}