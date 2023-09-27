/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.solr8.internal.query;

import com.liferay.portal.kernel.search.BooleanQuery;
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
import com.liferay.portal.kernel.search.query.QueryVisitor;

import org.apache.lucene.search.Query;

import org.osgi.service.component.annotations.Reference;

/**
 * @author Joao Victor Alves
 */
public abstract class BaseQueryVisitor implements QueryVisitor<Query> {

	@Override
	public Query visitQuery(BooleanQuery booleanQuery) {
		return booleanQueryTranslator.translate(booleanQuery, this);
	}

	@Override
	public Query visitQuery(DisMaxQuery disMaxQuery) {
		return disMaxQueryTranslator.translate(disMaxQuery, this);
	}

	@Override
	public Query visitQuery(FuzzyQuery fuzzyQuery) {
		return fuzzyQueryTranslator.translate(fuzzyQuery);
	}

	@Override
	public Query visitQuery(MatchAllQuery matchAllQuery) {
		return matchAllQueryTranslator.translate(matchAllQuery);
	}

	@Override
	public Query visitQuery(MatchQuery matchQuery) {
		return matchQueryTranslator.translate(matchQuery);
	}

	@Override
	public Query visitQuery(MoreLikeThisQuery moreLikeThisQuery) {
		return moreLikeThisQueryTranslator.translate(moreLikeThisQuery);
	}

	@Override
	public Query visitQuery(MultiMatchQuery multiMatchQuery) {
		return multiMatchQueryTranslator.translate(multiMatchQuery);
	}

	@Override
	public Query visitQuery(NestedQuery nestedQuery) {
		return nestedQueryTranslator.translate(nestedQuery, this);
	}

	@Override
	public Query visitQuery(StringQuery stringQuery) {
		return stringQueryTranslator.translate(stringQuery);
	}

	@Override
	public Query visitQuery(TermQuery termQuery) {
		return termQueryTranslator.translate(termQuery);
	}

	@Override
	public Query visitQuery(TermRangeQuery termRangeQuery) {
		return termRangeQueryTranslator.translate(termRangeQuery);
	}

	@Override
	public Query visitQuery(WildcardQuery wildcardQuery) {
		return wildcardQueryTranslator.translate(wildcardQuery);
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
	protected MatchQueryTranslator matchQueryTranslator;

	@Reference
	protected MoreLikeThisQueryTranslator moreLikeThisQueryTranslator;

	@Reference
	protected MultiMatchQueryTranslator multiMatchQueryTranslator;

	@Reference
	protected NestedQueryTranslator nestedQueryTranslator;

	@Reference
	protected StringQueryTranslator stringQueryTranslator;

	@Reference
	protected TermQueryTranslator termQueryTranslator;

	@Reference
	protected TermRangeQueryTranslator termRangeQueryTranslator;

	@Reference
	protected WildcardQueryTranslator wildcardQueryTranslator;

}