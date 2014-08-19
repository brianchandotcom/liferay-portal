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

package com.liferay.portal.repository.search;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.repository.search.RepositorySearchQueryBuilder;
import com.liferay.portal.kernel.search.BooleanClause;
import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.BooleanQuery;
import com.liferay.portal.kernel.search.BooleanQueryFactoryUtil;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Query;
import com.liferay.portal.kernel.search.QueryTerm;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.SearchException;
import com.liferay.portal.kernel.search.TermQuery;
import com.liferay.portal.kernel.search.TermRangeQuery;
import com.liferay.portal.kernel.search.WildcardQuery;
import com.liferay.portal.kernel.security.pacl.DoPrivileged;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.search.lucene.LuceneHelperUtil;
import com.liferay.portlet.documentlibrary.model.DLFolderConstants;
import com.liferay.portlet.documentlibrary.service.DLAppServiceUtil;
import com.liferay.util.lucene.KeywordsUtil;

import java.util.HashSet;
import java.util.Set;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryParser.QueryParser;

/**
 * @author Mika Koivisto
 */
@DoPrivileged
public class RepositorySearchQueryBuilderImpl
	implements RepositorySearchQueryBuilder {

	@Override
	public BooleanQuery getFullQuery(SearchContext searchContext)
		throws SearchException {

		try {
			BooleanQuery contextQuery = BooleanQueryFactoryUtil.create(
				searchContext);

			addContext(contextQuery, searchContext);

			BooleanQuery searchQuery = BooleanQueryFactoryUtil.create(
				searchContext);

			addSearchKeywords(searchQuery, searchContext);

			BooleanQuery fullQuery = BooleanQueryFactoryUtil.create(
				searchContext);

			if (contextQuery.hasClauses()) {
				fullQuery.add(contextQuery, BooleanClauseOccur.MUST);
			}

			if (searchQuery.hasClauses()) {
				fullQuery.add(searchQuery, BooleanClauseOccur.MUST);
			}

			BooleanClause[] booleanClauses = searchContext.getBooleanClauses();

			if (booleanClauses != null) {
				for (BooleanClause booleanClause : booleanClauses) {
					fullQuery.add(
						booleanClause.getQuery(),
						booleanClause.getBooleanClauseOccur());
				}
			}

			fullQuery.setQueryConfig(searchContext.getQueryConfig());

			return fullQuery;
		}
		catch (Exception e) {
			throw new SearchException(e);
		}
	}

	public void setAnalyzer(Analyzer analyzer) {
		_analyzer = analyzer;
	}

	protected void addContext(
			BooleanQuery contextQuery, SearchContext searchContext)
		throws Exception {

		long[] folderIds = searchContext.getFolderIds();

		if (ArrayUtil.isEmpty(folderIds)) {
			return;
		}

		if (folderIds[0] == DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) {
			return;
		}

		BooleanQuery folderIdsQuery = BooleanQueryFactoryUtil.create(
			searchContext);

		for (long folderId : folderIds) {
			try {
				DLAppServiceUtil.getFolder(folderId);
			}
			catch (Exception e) {
				continue;
			}

			folderIdsQuery.addTerm(Field.FOLDER_ID, folderId);
		}

		contextQuery.add(folderIdsQuery, BooleanClauseOccur.MUST);
	}

	protected void addSearchKeywords(
			BooleanQuery searchQuery, SearchContext searchContext)
		throws Exception {

		String keywords = searchContext.getKeywords();

		if (Validator.isNull(keywords)) {
			return;
		}

		BooleanQuery titleQuery = BooleanQueryFactoryUtil.create(searchContext);

		addTerm(titleQuery, searchContext, Field.TITLE, keywords);

		if (titleQuery.hasClauses() && !contains(searchQuery, titleQuery)) {
			searchQuery.add(titleQuery, BooleanClauseOccur.SHOULD);
		}

		BooleanQuery userNameQuery = BooleanQueryFactoryUtil.create(
			searchContext);

		addTerm(userNameQuery, searchContext, Field.USER_NAME, keywords);

		if (userNameQuery.hasClauses() &&
			!contains(searchQuery, userNameQuery)) {

			searchQuery.add(userNameQuery, BooleanClauseOccur.SHOULD);
		}

		BooleanQuery contentQuery = BooleanQueryFactoryUtil.create(
			searchContext);

		addTerm(contentQuery, searchContext, Field.CONTENT, keywords);

		if (contentQuery.hasClauses() && !contains(searchQuery, contentQuery)) {
			searchQuery.add(contentQuery, BooleanClauseOccur.SHOULD);
		}
	}

	protected void addTerm(
		BooleanQuery booleanQuery, SearchContext searchContext, String field,
		String value) {

		if (Validator.isNull(value)) {
			return;
		}

		try {
			QueryParser queryParser = new QueryParser(
				LuceneHelperUtil.getVersion(), field, _analyzer);

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

	protected boolean contains(Query query1, Query query2) {
		if (query1 instanceof BooleanQuery) {
			BooleanQuery booleanQuery = (BooleanQuery)query1;

			for (com.liferay.portal.kernel.search.BooleanClause booleanClause :
					booleanQuery.clauses()) {

				if (contains(booleanClause.getQuery(), query2)) {
					return true;
				}
			}

			return false;
		}
		else if (query2 instanceof BooleanQuery) {
			BooleanQuery booleanQuery = (BooleanQuery)query2;

			for (com.liferay.portal.kernel.search.BooleanClause booleanClause :
					booleanQuery.clauses()) {

				if (contains(query1, booleanClause.getQuery())) {
					return true;
				}
			}

			return false;
		}
		else if ((query1 instanceof TermQuery) &&
				 (query2 instanceof TermQuery)) {

			TermQuery termQuery1 = (TermQuery)query1;

			QueryTerm queryTerm1 = termQuery1.getQueryTerm();

			String field1 = queryTerm1.getField();
			String value1 = queryTerm1.getValue();

			TermQuery termQuery2 = (TermQuery)query2;

			QueryTerm queryTerm2 = termQuery2.getQueryTerm();

			String field2 = queryTerm2.getField();
			String value2 = queryTerm2.getValue();

			if (field1.equals(field2) && value1.equals(value2)) {
				return true;
			}
		}
		else if ((query1 instanceof TermRangeQuery) &&
				 (query2 instanceof TermRangeQuery)) {

			TermRangeQuery termRangeQuery1 = (TermRangeQuery)query1;

			boolean includesLower1 = termRangeQuery1.includesLower();
			boolean includesUpper1 = termRangeQuery1.includesUpper();
			String lowerTerm1 = termRangeQuery1.getLowerTerm();
			String upperTerm1 = termRangeQuery1.getUpperTerm();

			TermRangeQuery termRangeQuery2 = (TermRangeQuery)query2;

			boolean includesLower2 = termRangeQuery2.includesLower();
			boolean includesUpper2 = termRangeQuery2.includesUpper();
			String lowerTerm2 = termRangeQuery2.getLowerTerm();
			String upperTerm2 = termRangeQuery2.getUpperTerm();

			if ((includesLower1 == includesLower2) &&
				(includesUpper1 == includesUpper2) &&
				lowerTerm1.equals(lowerTerm2) &&
				upperTerm1.equals(upperTerm2)) {

				return true;
			}
		}
		else if ((query1 instanceof WildcardQuery) &&
				 (query2 instanceof WildcardQuery)) {

			WildcardQuery wildcardQuery1 = (WildcardQuery)query1;

			QueryTerm queryTerm1 = wildcardQuery1.getQueryTerm();

			String field1 = queryTerm1.getField();
			String value1 = queryTerm1.getValue();

			WildcardQuery wildcardQuery2 = (WildcardQuery)query2;

			QueryTerm queryTerm2 = wildcardQuery2.getQueryTerm();

			String field2 = queryTerm2.getField();
			String value2 = queryTerm2.getValue();

			if (field1.equals(field2) && value1.equals(value2)) {
				return true;
			}
		}

		return false;
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
			Set<Term> terms = new HashSet<Term>();

			query.extractTerms(terms);

			for (Term term : terms) {
				String termValue = term.text();

				booleanQuery.addTerm(
					term.field(), termValue, false,
					getBooleanClauseOccur(occur));
			}
		}
		else if (query instanceof org.apache.lucene.search.BooleanQuery) {
			org.apache.lucene.search.BooleanQuery curBooleanQuery =
				(org.apache.lucene.search.BooleanQuery)query;

			BooleanQuery conjunctionQuery = BooleanQueryFactoryUtil.create(
				searchContext);
			BooleanQuery disjunctionQuery = BooleanQueryFactoryUtil.create(
				searchContext);

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

			booleanQuery.addTerm(
				term.field(), termValue, true, booleanClauseOccur);
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

			booleanQuery.addTerm(
				terms[0].field(), sb.toString().trim(), false,
				booleanClauseOccur);
		}
		else if (query instanceof org.apache.lucene.search.PrefixQuery) {
			org.apache.lucene.search.PrefixQuery prefixQuery =
				(org.apache.lucene.search.PrefixQuery)query;

			Term prefixTerm = prefixQuery.getPrefix();

			String termValue = prefixTerm.text().concat(StringPool.STAR);

			booleanQuery.addTerm(
				prefixTerm.field(), termValue, true, booleanClauseOccur);
		}
		else if (query instanceof org.apache.lucene.search.TermRangeQuery) {
			org.apache.lucene.search.TermRangeQuery termRangeQuery =
				(org.apache.lucene.search.TermRangeQuery)query;

			booleanQuery.addRangeTerm(
				termRangeQuery.getField(), termRangeQuery.getLowerTerm(),
				termRangeQuery.getUpperTerm());
		}
		else if (query instanceof org.apache.lucene.search.WildcardQuery) {
			org.apache.lucene.search.WildcardQuery wildcardQuery =
				(org.apache.lucene.search.WildcardQuery)query;

			Term wildcardTerm = wildcardQuery.getTerm();

			booleanQuery.addTerm(
				wildcardTerm.field(), wildcardTerm.text(), true,
				booleanClauseOccur);
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
		RepositorySearchQueryBuilderImpl.class);

	private Analyzer _analyzer;

}