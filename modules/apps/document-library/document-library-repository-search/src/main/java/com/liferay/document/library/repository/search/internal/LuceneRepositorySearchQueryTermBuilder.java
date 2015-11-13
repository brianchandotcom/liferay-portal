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

package com.liferay.document.library.repository.search.internal;

import com.liferay.document.library.repository.search.util.KeywordsUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.repository.search.RepositorySearchQueryTermBuilder;
import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.BooleanQuery;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.TermQuery;
import com.liferay.portal.kernel.search.WildcardQuery;
import com.liferay.portal.kernel.search.generic.BooleanQueryImpl;
import com.liferay.portal.kernel.search.generic.TermQueryImpl;
import com.liferay.portal.kernel.search.generic.WildcardQueryImpl;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;

import java.util.Map;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.core.KeywordAnalyzer;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.QueryParser;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;

/**
 * @author Michael C. Han
 */
@Component(immediate = true, service = RepositorySearchQueryTermBuilder.class)
public class LuceneRepositorySearchQueryTermBuilder
	implements RepositorySearchQueryTermBuilder {

	@Override
	public void addTerm(
		BooleanQuery booleanQuery, SearchContext searchContext, String field,
		String value) {

		if (Validator.isNull(value)) {
			return;
		}

		try {
			QueryParser queryParser = new QueryParser(field, _analyzer);

			queryParser.setAllowLeadingWildcard(true);
			queryParser.setLowercaseExpandedTerms(false);

			org.apache.lucene.search.Query query = null;

			try {
				query = queryParser.parse(value);
			}
			catch (Exception e) {
				query = queryParser.parse(KeywordsUtil.escape(value));
			}

			translateQuery(
				booleanQuery, searchContext, query,
				org.apache.lucene.search.BooleanClause.Occur.SHOULD);
		}
		catch (Exception e) {
			_log.error(e, e);
		}
	}

	@Activate
	protected void activate(Map<String, Object> properties) {
		_analyzer = new KeywordAnalyzer();
	}

	protected org.apache.lucene.search.BooleanClause.Occur
		getBooleanClauseOccur(BooleanClauseOccur occur) {

		if (occur.equals(BooleanClauseOccur.MUST)) {
			return org.apache.lucene.search.BooleanClause.Occur.MUST;
		}
		else if (occur.equals(BooleanClauseOccur.MUST_NOT)) {
			return org.apache.lucene.search.BooleanClause.Occur.MUST_NOT;
		}

		return org.apache.lucene.search.BooleanClause.Occur.SHOULD;
	}

	protected BooleanClauseOccur getBooleanClauseOccur(
		org.apache.lucene.search.BooleanClause.Occur occur) {

		if (occur.equals(org.apache.lucene.search.BooleanClause.Occur.MUST)) {
			return BooleanClauseOccur.MUST;
		}
		else if (occur.equals(
					org.apache.lucene.search.BooleanClause.Occur.MUST_NOT)) {

			return BooleanClauseOccur.MUST_NOT;
		}

		return BooleanClauseOccur.SHOULD;
	}

	protected void translateQuery(
			BooleanQuery booleanQuery, SearchContext searchContext,
			org.apache.lucene.search.Query query,
			org.apache.lucene.search.BooleanClause.Occur occur)
		throws Exception {

		BooleanClauseOccur booleanClauseOccur = getBooleanClauseOccur(occur);

		if (query instanceof org.apache.lucene.search.TermQuery) {
			org.apache.lucene.search.TermQuery luceneTermQuery =
				(org.apache.lucene.search.TermQuery)query;

			Term term = luceneTermQuery.getTerm();

			String termValue = term.text();

			TermQuery termQuery = new TermQueryImpl(term.field(), termValue);

			booleanQuery.add(termQuery, getBooleanClauseOccur(occur));
		}
		else if (query instanceof org.apache.lucene.search.BooleanQuery) {
			org.apache.lucene.search.BooleanQuery curBooleanQuery =
				(org.apache.lucene.search.BooleanQuery)query;

			BooleanQuery conjunctionQuery = new BooleanQueryImpl();

			BooleanQuery disjunctionQuery = new BooleanQueryImpl();

			for (org.apache.lucene.search.BooleanClause booleanClause :
					curBooleanQuery.getClauses()) {

				BooleanClauseOccur curBooleanClauseOccur =
					getBooleanClauseOccur(booleanClause.getOccur());

				BooleanQuery subbooleanQuery = null;

				if (curBooleanClauseOccur.equals(BooleanClauseOccur.SHOULD)) {
					subbooleanQuery = disjunctionQuery;
				}
				else {
					subbooleanQuery = conjunctionQuery;
				}

				translateQuery(
					subbooleanQuery, searchContext, booleanClause.getQuery(),
					booleanClause.getOccur());
			}

			if (conjunctionQuery.hasClauses()) {
				booleanQuery.add(conjunctionQuery, BooleanClauseOccur.MUST);
			}

			if (disjunctionQuery.hasClauses()) {
				booleanQuery.add(disjunctionQuery, BooleanClauseOccur.SHOULD);
			}
		}
		else if (query instanceof org.apache.lucene.search.FuzzyQuery) {
			org.apache.lucene.search.FuzzyQuery fuzzyQuery =
				(org.apache.lucene.search.FuzzyQuery)query;

			Term term = fuzzyQuery.getTerm();

			String termValue = term.text().concat(StringPool.STAR);

			WildcardQuery wildcardQuery = new WildcardQueryImpl(
				term.field(), termValue);

			booleanQuery.add(wildcardQuery, booleanClauseOccur);
		}
		else if (query instanceof org.apache.lucene.search.PhraseQuery) {
			org.apache.lucene.search.PhraseQuery phraseQuery =
				(org.apache.lucene.search.PhraseQuery)query;

			Term[] terms = phraseQuery.getTerms();

			StringBundler sb = new StringBundler(terms.length * 2);

			for (Term term : terms) {
				sb.append(term.text());
				sb.append(StringPool.SPACE);
			}

			TermQuery termQuery = new TermQueryImpl(
				terms[0].field(), sb.toString().trim());

			booleanQuery.add(termQuery, booleanClauseOccur);
		}
		else if (query instanceof org.apache.lucene.search.PrefixQuery) {
			org.apache.lucene.search.PrefixQuery prefixQuery =
				(org.apache.lucene.search.PrefixQuery)query;

			Term prefixTerm = prefixQuery.getPrefix();

			String termValue = prefixTerm.text().concat(StringPool.STAR);

			WildcardQuery wildcardQuery = new WildcardQueryImpl(
				prefixTerm.field(), termValue);

			booleanQuery.add(wildcardQuery, booleanClauseOccur);
		}
		else if (query instanceof org.apache.lucene.search.TermRangeQuery) {
			org.apache.lucene.search.TermRangeQuery termRangeQuery =
				(org.apache.lucene.search.TermRangeQuery)query;

			booleanQuery.addRangeTerm(
				termRangeQuery.getField(),
				termRangeQuery.getLowerTerm().utf8ToString(),
				termRangeQuery.getUpperTerm().utf8ToString());
		}
		else if (query instanceof org.apache.lucene.search.WildcardQuery) {
			org.apache.lucene.search.WildcardQuery luceneWildcardQuery =
				(org.apache.lucene.search.WildcardQuery)query;

			Term wildcardTerm = luceneWildcardQuery.getTerm();

			WildcardQuery wildcardQuery = new WildcardQueryImpl(
				wildcardTerm.field(), wildcardTerm.text());

			booleanQuery.add(wildcardQuery, booleanClauseOccur);
		}
		else {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Ignoring unknown query type " + query.getClass() +
						" with query " + query);
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		LuceneRepositorySearchQueryTermBuilder.class);

	private Analyzer _analyzer;

}