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

package com.liferay.users.admin.internal.search.spi.model.query.contributor;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.BooleanQuery;
import com.liferay.portal.kernel.search.ParseException;
import com.liferay.portal.kernel.search.QueryConfig;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.WildcardQuery;
import com.liferay.portal.kernel.search.generic.WildcardQueryImpl;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.search.query.QueryHelper;
import com.liferay.portal.search.spi.model.query.contributor.KeywordQueryContributor;
import com.liferay.portal.search.spi.model.query.contributor.helper.KeywordQueryContributorHelper;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Luan Maoski
 */
@Component(
	immediate = true,
	property = "indexer.class.name=com.liferay.portal.kernel.model.User",
	service = KeywordQueryContributor.class
)
public class UserKeywordQueryContributor implements KeywordQueryContributor {

	@Override
	public void contribute(
		String keywords, BooleanQuery booleanQuery,
		KeywordQueryContributorHelper keywordQueryContributorHelper) {

		SearchContext searchContext =
			keywordQueryContributorHelper.getSearchContext();

		addHighlightFieldNames(searchContext);

		queryHelper.addSearchTerm(booleanQuery, searchContext, "city", false);
		queryHelper.addSearchTerm(
			booleanQuery, searchContext, "country", false);
		queryHelper.addSearchTerm(
			booleanQuery, searchContext, "firstName", false);
		queryHelper.addSearchTerm(
			booleanQuery, searchContext, "fullName", false);
		queryHelper.addSearchTerm(
			booleanQuery, searchContext, "jobTitle", false);
		queryHelper.addSearchTerm(
			booleanQuery, searchContext, "lastName", false);
		queryHelper.addSearchTerm(
			booleanQuery, searchContext, "middleName", false);
		queryHelper.addSearchTerm(booleanQuery, searchContext, "region", false);
		queryHelper.addSearchTerm(
			booleanQuery, searchContext, "screenName", false);
		queryHelper.addSearchTerm(booleanQuery, searchContext, "street", false);
		queryHelper.addSearchTerm(booleanQuery, searchContext, "zip", false);

		if (Validator.isNotNull(keywords)) {
			try {
				keywords = StringUtil.toLowerCase(keywords);

				booleanQuery.add(
					_getTrailingWildcardQuery("emailAddress", keywords),
					BooleanClauseOccur.SHOULD);
				booleanQuery.add(
					_getTrailingWildcardQuery("emailAddressDomain", keywords),
					BooleanClauseOccur.SHOULD);
				booleanQuery.add(
					_getTrailingWildcardQuery("screenName", keywords),
					BooleanClauseOccur.SHOULD);
			}
			catch (ParseException parseException) {
				throw new SystemException(parseException);
			}
		}
	}

	protected void addHighlightFieldNames(SearchContext searchContext) {
		QueryConfig queryConfig = searchContext.getQueryConfig();

		if (!queryConfig.isHighlightEnabled()) {
			return;
		}

		queryConfig.addHighlightFieldNames("fullName");
	}

	@Reference
	protected QueryHelper queryHelper;

	private WildcardQuery _getTrailingWildcardQuery(
		String field, String value) {

		return new WildcardQueryImpl(field, value + StringPool.STAR);
	}

}