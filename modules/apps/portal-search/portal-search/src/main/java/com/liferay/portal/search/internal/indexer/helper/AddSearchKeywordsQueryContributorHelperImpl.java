/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.internal.indexer.helper;

import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.BooleanQuery;
import com.liferay.portal.kernel.search.ParseException;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.generic.StringQuery;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.search.constants.SearchContextAttributes;
import com.liferay.portal.search.internal.indexer.IndexerProvidedClausesUtil;
import com.liferay.portal.search.internal.indexer.KeywordQueryContributorsRegistry;
import com.liferay.portal.search.internal.util.SearchStringUtil;
import com.liferay.portal.search.spi.model.query.contributor.KeywordQueryContributor;
import com.liferay.portal.search.spi.model.query.contributor.helper.KeywordQueryContributorHelper;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author André de Oliveira
 */
@Component(service = AddSearchKeywordsQueryContributorHelper.class)
public class AddSearchKeywordsQueryContributorHelperImpl
	implements AddSearchKeywordsQueryContributorHelper {

	@Override
	public void contribute(
		BooleanQuery booleanQuery, SearchContext searchContext) {

		if (IndexerProvidedClausesUtil.shouldSuppress(searchContext)) {
			return;
		}

		_addKeywordQueryContributorClauses(booleanQuery, searchContext);
	}

	protected Collection<String> getStrings(
		String string, SearchContext searchContext) {

		return Arrays.asList(
			SearchStringUtil.splitAndUnquote(
				(String)searchContext.getAttribute(string)));
	}

	@Reference
	protected KeywordQueryContributorsRegistry keywordQueryContributorsRegistry;

	private void _addKeywordQueryContributorClauses(
		BooleanQuery booleanQuery, SearchContext searchContext) {

		boolean luceneSyntax = GetterUtil.getBoolean(
			searchContext.getAttribute(
				SearchContextAttributes.ATTRIBUTE_KEY_LUCENE_SYNTAX));

		String keywords = searchContext.getKeywords();

		if (luceneSyntax) {
			_addStringQuery(booleanQuery, keywords);

			return;
		}

		List<KeywordQueryContributor> filteredKeywordQueryContributors =
			keywordQueryContributorsRegistry.filterKeywordQueryContributors(
				getStrings(
					"search.full.query.clause.contributors.excludes",
					searchContext),
				getStrings(
					"search.full.query.clause.contributors.includes",
					searchContext));

		for (KeywordQueryContributor keywordQueryContributor :
				filteredKeywordQueryContributors) {

			keywordQueryContributor.contribute(
				keywords, booleanQuery,
				new KeywordQueryContributorHelper() {

					@Override
					public String getClassName() {
						return null;
					}

					@Override
					public String[] getSearchClassNames() {
						throw new UnsupportedOperationException();
					}

					@Override
					public SearchContext getSearchContext() {
						return searchContext;
					}

				});
		}
	}

	private void _addStringQuery(BooleanQuery booleanQuery, String keywords) {
		if (Validator.isBlank(keywords)) {
			return;
		}

		try {
			booleanQuery.add(
				new StringQuery(keywords), BooleanClauseOccur.MUST);
		}
		catch (ParseException parseException) {
			throw new RuntimeException(parseException);
		}
	}

}