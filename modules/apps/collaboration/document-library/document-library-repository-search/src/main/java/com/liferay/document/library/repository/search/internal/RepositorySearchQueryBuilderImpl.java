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

import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.service.DLAppService;
import com.liferay.portal.kernel.repository.search.RepositorySearchQueryBuilder;
import com.liferay.portal.kernel.repository.search.RepositorySearchQueryTermBuilder;
import com.liferay.portal.kernel.search.BooleanClause;
import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.BooleanQuery;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Query;
import com.liferay.portal.kernel.search.QueryTerm;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.SearchException;
import com.liferay.portal.kernel.search.TermQuery;
import com.liferay.portal.kernel.search.TermRangeQuery;
import com.liferay.portal.kernel.search.WildcardQuery;
import com.liferay.portal.kernel.search.generic.BooleanQueryImpl;
import com.liferay.portal.kernel.search.generic.TermQueryImpl;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.Validator;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Mika Koivisto
 */
@Component(immediate = true, service = RepositorySearchQueryBuilder.class)
public class RepositorySearchQueryBuilderImpl
	implements RepositorySearchQueryBuilder {

	@Override
	public BooleanQuery getFullQuery(SearchContext searchContext)
		throws SearchException {

		try {
			BooleanQuery contextQuery = new BooleanQueryImpl();

			addContext(contextQuery, searchContext);

			BooleanQuery searchQuery = new BooleanQueryImpl();

			addSearchKeywords(searchQuery, searchContext);

			BooleanQuery fullQuery = new BooleanQueryImpl();

			if (contextQuery.hasClauses()) {
				fullQuery.add(contextQuery, BooleanClauseOccur.MUST);
			}

			if (searchQuery.hasClauses()) {
				fullQuery.add(searchQuery, BooleanClauseOccur.MUST);
			}

			BooleanClause<Query>[] booleanClauses =
				searchContext.getBooleanClauses();

			if (booleanClauses != null) {
				for (BooleanClause<Query> booleanClause : booleanClauses) {
					fullQuery.add(
						booleanClause.getClause(),
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

		BooleanQuery folderIdsQuery = new BooleanQueryImpl();

		for (long folderId : folderIds) {
			try {
				_dlAppService.getFolder(folderId);
			}
			catch (Exception e) {
				continue;
			}

			folderIdsQuery.add(
				new TermQueryImpl(Field.FOLDER_ID, String.valueOf(folderId)),
				BooleanClauseOccur.SHOULD);
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

		BooleanQuery titleQuery = new BooleanQueryImpl();

		_repositorySearchQueryTermBuilder.addTerm(
			titleQuery, searchContext, Field.TITLE, keywords);

		if (titleQuery.hasClauses() && !contains(searchQuery, titleQuery)) {
			searchQuery.add(titleQuery, BooleanClauseOccur.SHOULD);
		}

		BooleanQuery userNameQuery = new BooleanQueryImpl();

		_repositorySearchQueryTermBuilder.addTerm(
			userNameQuery, searchContext, Field.USER_NAME, keywords);

		if (userNameQuery.hasClauses() &&
			!contains(searchQuery, userNameQuery)) {

			searchQuery.add(userNameQuery, BooleanClauseOccur.SHOULD);
		}

		BooleanQuery contentQuery = new BooleanQueryImpl();

		_repositorySearchQueryTermBuilder.addTerm(
			contentQuery, searchContext, Field.CONTENT, keywords);

		if (contentQuery.hasClauses() && !contains(searchQuery, contentQuery)) {
			searchQuery.add(contentQuery, BooleanClauseOccur.SHOULD);
		}
	}

	protected boolean contains(Query query1, Query query2) {
		if (query1 instanceof BooleanQuery) {
			BooleanQuery booleanQuery = (BooleanQuery)query1;

			for (BooleanClause<Query> booleanClause : booleanQuery.clauses()) {
				if (contains(booleanClause.getClause(), query2)) {
					return true;
				}
			}

			return false;
		}
		else if (query2 instanceof BooleanQuery) {
			BooleanQuery booleanQuery = (BooleanQuery)query2;

			for (BooleanClause<Query> booleanClause : booleanQuery.clauses()) {
				if (contains(query1, booleanClause.getClause())) {
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

	@Reference(unbind = "-")
	protected void setDLAppService(DLAppService dlAppService) {
		_dlAppService = dlAppService;
	}

	@Reference(unbind = "-")
	protected void setRepositorySearchQueryTermBuilder(
		RepositorySearchQueryTermBuilder repositorySearchQueryTermBuilder) {

		_repositorySearchQueryTermBuilder = repositorySearchQueryTermBuilder;
	}

	private DLAppService _dlAppService;
	private RepositorySearchQueryTermBuilder _repositorySearchQueryTermBuilder;

}