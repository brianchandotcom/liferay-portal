/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.redirect.internal.search.spi.model.query.contributor;

import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.BooleanQuery;
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
	property = "indexer.class.name=com.liferay.redirect.model.RedirectEntry",
	service = KeywordQueryContributor.class
)
public class RedirectEntryKeywordQueryContributor
	implements KeywordQueryContributor {

	@Override
	public void contribute(
		String keywords, BooleanQuery booleanQuery,
		KeywordQueryContributorHelper keywordQueryContributorHelper) {

		try {
			SearchContext searchContext =
				keywordQueryContributorHelper.getSearchContext();

			_queryHelper.addSearchTerm(
				booleanQuery, searchContext, "destinationURL", false);
			_queryHelper.addSearchTerm(
				booleanQuery, searchContext, "sourceURL", false);

			if (Validator.isNotNull(keywords)) {
				booleanQuery.add(
					_getMatchQuery("destinationURLParts", keywords),
					BooleanClauseOccur.SHOULD);
				booleanQuery.add(
					new MatchQuery("destinationURLParts", keywords),
					BooleanClauseOccur.SHOULD);
				booleanQuery.add(
					_getMatchQuery("sourceURLParts", keywords),
					BooleanClauseOccur.SHOULD);
				booleanQuery.add(
					new MatchQuery("sourceURLParts", keywords),
					BooleanClauseOccur.SHOULD);
			}

			String groupBaseURL = (String)searchContext.getAttribute(
				"groupBaseURL");

			if (Validator.isNotNull(groupBaseURL) &&
				Validator.isNotNull(keywords) &&
				keywords.startsWith(groupBaseURL)) {

				Query query = fieldQueryFactory.createQuery(
					"sourceURL",
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