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

package com.liferay.portal.search.elasticsearch.util;

import com.liferay.util.lucene.KeywordsUtil;

import java.util.List;

import org.apache.lucene.index.Term;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.FuzzyQuery;
import org.apache.lucene.search.PhraseQuery;
import org.apache.lucene.search.PrefixQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TermRangeQuery;
import org.apache.lucene.search.WildcardQuery;
import org.apache.lucene.util.BytesRef;

/**
 * @author Tibor Lipusz
 */
public class ElasticsearchHelperUtil {

	public static String toEscapedQueryString(
		com.liferay.portal.kernel.search.Query query) {

		return getEscapedQuery(query.getWrappedQuery()).toString();
	}

	protected static Query getEscapedQuery(Object originalQuery) {
		Query escapedQuery = (Query)originalQuery;

		if (originalQuery instanceof TermQuery) {
			TermQuery termQuery = (TermQuery)originalQuery;

			Term term = termQuery.getTerm();

			term = new Term(term.field(), KeywordsUtil.escape(term.text()));

			escapedQuery = new TermQuery(term);

			escapedQuery.setBoost(termQuery.getBoost());
		}
		else if (originalQuery instanceof BooleanQuery) {
			BooleanQuery booleanQuery = (BooleanQuery)originalQuery;

			BooleanQuery escapedBooleanQuery =
				new BooleanQuery(booleanQuery.isCoordDisabled());

			processClauses(escapedBooleanQuery, booleanQuery.clauses());

			escapedBooleanQuery.setBoost(booleanQuery.getBoost());
			escapedBooleanQuery.setMinimumNumberShouldMatch(
				booleanQuery.getMinimumNumberShouldMatch());

			escapedQuery = escapedBooleanQuery;
		}
		else if (originalQuery instanceof FuzzyQuery) {
			FuzzyQuery fuzzyQuery = (FuzzyQuery)originalQuery;

			Term term = fuzzyQuery.getTerm();

			term = new Term(term.field(), KeywordsUtil.escape(term.text()));

			FuzzyQuery escapedFuzzyQuery = new FuzzyQuery(
				term, fuzzyQuery.getMaxEdits(), fuzzyQuery.getPrefixLength());

			escapedFuzzyQuery.setBoost(fuzzyQuery.getBoost());
			escapedFuzzyQuery.setRewriteMethod(fuzzyQuery.getRewriteMethod());

			escapedQuery = escapedFuzzyQuery;
		}
		else if (originalQuery instanceof PhraseQuery) {
			PhraseQuery phraseQuery = (PhraseQuery)originalQuery;

			PhraseQuery escapedPhraseQuery = new PhraseQuery();

			Term[] terms = phraseQuery.getTerms();

			for (org.apache.lucene.index.Term term : terms) {
				term = new Term(term.field(), KeywordsUtil.escape(term.text()));

				escapedPhraseQuery.add(term);
			}

			escapedPhraseQuery.setBoost(phraseQuery.getBoost());
			escapedPhraseQuery.setSlop(phraseQuery.getSlop());

			escapedQuery = escapedPhraseQuery;
		}
		else if (originalQuery instanceof PrefixQuery) {
			PrefixQuery prefixQuery = (PrefixQuery)originalQuery;

			Term term = prefixQuery.getPrefix();

			term = new Term(term.field(), KeywordsUtil.escape(term.text()));

			PrefixQuery escapedPrefixQuery = new PrefixQuery(term);

			escapedPrefixQuery.setBoost(prefixQuery.getBoost());
			escapedPrefixQuery.setRewriteMethod(prefixQuery.getRewriteMethod());

			escapedQuery = escapedPrefixQuery;
		}
		else if (originalQuery instanceof TermRangeQuery) {
			TermRangeQuery termRangeQuery = (TermRangeQuery)originalQuery;

			BytesRef lowerTerm = termRangeQuery.getLowerTerm();
			BytesRef upperTerm = termRangeQuery.getUpperTerm();

			BytesRef escapedLowerTerm = new BytesRef(
				KeywordsUtil.escape(lowerTerm.toString()));
			BytesRef escapedUpperTerm = new BytesRef(
				KeywordsUtil.escape(upperTerm.toString()));

			TermRangeQuery escapedTermRangeQuery =
				new TermRangeQuery(
					termRangeQuery.getField(), escapedLowerTerm,
					escapedUpperTerm, termRangeQuery.includesLower(),
					termRangeQuery.includesUpper());

			escapedTermRangeQuery.setBoost(termRangeQuery.getBoost());
			escapedTermRangeQuery.setRewriteMethod(
				termRangeQuery.getRewriteMethod());

			escapedQuery = escapedTermRangeQuery;
		}
		else if (originalQuery instanceof WildcardQuery) {
			WildcardQuery wildcardQuery = (WildcardQuery)originalQuery;

			Term term = wildcardQuery.getTerm();

			term = new Term(term.field(), KeywordsUtil.escape(term.text()));

			WildcardQuery escapedWildcardQuery = new WildcardQuery(term);

			escapedWildcardQuery.setBoost(wildcardQuery.getBoost());
			escapedWildcardQuery.setRewriteMethod(
				wildcardQuery.getRewriteMethod());

			escapedQuery = escapedWildcardQuery;
		}

		return escapedQuery;
	}

	protected static void processClauses(
		BooleanQuery escapedBoolanQuery,
		List<BooleanClause> clauses) {

		for (BooleanClause booleanClause : clauses) {
			BooleanClause escapedClause = booleanClause;

			Query query = booleanClause.getQuery();

			Query escapedSubQuery = (Query)getEscapedQuery(query);

			escapedClause = new BooleanClause(
				escapedSubQuery, booleanClause.getOccur());

			escapedBoolanQuery.add(escapedClause);
		}
	}

}