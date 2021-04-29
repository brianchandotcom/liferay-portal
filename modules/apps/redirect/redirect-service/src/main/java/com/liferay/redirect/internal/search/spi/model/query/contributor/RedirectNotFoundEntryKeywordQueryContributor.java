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

package com.liferay.redirect.internal.search.spi.model.query.contributor;

import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.BooleanQuery;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.ParseException;
import com.liferay.portal.kernel.search.Query;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.generic.MatchQuery;
import com.liferay.portal.kernel.search.query.FieldQueryFactory;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.search.query.QueryHelper;
import com.liferay.portal.search.spi.model.query.contributor.KeywordQueryContributor;
import com.liferay.portal.search.spi.model.query.contributor.helper.KeywordQueryContributorHelper;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alejandro Tardín
 */
@Component(
	immediate = true,
	property = "indexer.class.name=com.liferay.redirect.model.RedirectNotFoundEntry",
	service = KeywordQueryContributor.class
)
public class RedirectNotFoundEntryKeywordQueryContributor
	implements KeywordQueryContributor {

	@Override
	public void contribute(
		String keywords, BooleanQuery booleanQuery,
		KeywordQueryContributorHelper keywordQueryContributorHelper) {

		try {
			SearchContext searchContext =
				keywordQueryContributorHelper.getSearchContext();

			_queryHelper.addSearchTerm(
				booleanQuery, searchContext, Field.URL, false);

			String groupBaseURL = (String)searchContext.getAttribute(
				"groupBaseURL");

			if (Validator.isNotNull(keywords)) {
				booleanQuery.add(
					_getMatchQuery("urlParts", keywords),
					BooleanClauseOccur.SHOULD);
				booleanQuery.add(
					new MatchQuery("urlParts", keywords),
					BooleanClauseOccur.SHOULD);
			}

			if (Validator.isNotNull(groupBaseURL) &&
				Validator.isNotNull(keywords) &&
				keywords.startsWith(groupBaseURL)) {

				Query query = fieldQueryFactory.createQuery(
					Field.URL,
					StringUtil.removeSubstring(keywords, groupBaseURL), false,
					false);

				booleanQuery.add(query, BooleanClauseOccur.SHOULD);
			}
		}
		catch (ParseException parseException) {
			throw new SystemException(parseException);
		}
	}

	@Reference
	protected FieldQueryFactory fieldQueryFactory;

	private MatchQuery _getMatchQuery(String field, String keywords) {
		MatchQuery sourceURLPartsMatchQuery = new MatchQuery(field, keywords);

		sourceURLPartsMatchQuery.setType(MatchQuery.Type.PHRASE_PREFIX);

		return sourceURLPartsMatchQuery;
	}

	@Reference
	private QueryHelper _queryHelper;

}