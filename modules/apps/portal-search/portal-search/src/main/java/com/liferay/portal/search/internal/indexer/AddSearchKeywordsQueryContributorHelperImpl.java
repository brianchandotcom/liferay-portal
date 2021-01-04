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

package com.liferay.portal.search.internal.indexer;

import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.BooleanQuery;
import com.liferay.portal.kernel.search.ParseException;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.generic.StringQuery;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.search.constants.SearchContextAttributes;
import com.liferay.portal.search.spi.model.query.contributor.KeywordQueryContributor;
import com.liferay.portal.search.spi.model.query.contributor.helper.KeywordQueryContributorHelper;

import java.util.stream.Stream;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author André de Oliveira
 */
@Component(
	immediate = true, service = AddSearchKeywordsQueryContributorHelper.class
)
public class AddSearchKeywordsQueryContributorHelperImpl
	implements AddSearchKeywordsQueryContributorHelper {

	@Override
	public void contribute(
		BooleanQuery booleanQuery, SearchContext searchContext) {

		if (shouldSuppressIndexerProvidedClauses(searchContext)) {
			return;
		}

		addKeywordQueryContributorClauses(booleanQuery, searchContext);
	}

	protected void addKeywordQueryContributorClauses(
		BooleanQuery booleanQuery, SearchContext searchContext) {

		boolean luceneSyntax = GetterUtil.getBoolean(
			searchContext.getAttribute(
				SearchContextAttributes.ATTRIBUTE_KEY_LUCENE_SYNTAX));

		String keywords = searchContext.getKeywords();

		if (luceneSyntax) {
			addStringQuery(booleanQuery, keywords);

			return;
		}

		Stream<KeywordQueryContributor> stream =
			keywordQueryContributorsHolder.getAll();

		stream.forEach(
			keywordQueryContributor -> keywordQueryContributor.contribute(
				keywords, booleanQuery,
				new KeywordQueryContributorHelper() {

					@Override
					public String getClassName() {
						return null;
					}

					@Override
					public Stream<String> getSearchClassNamesStream() {
						throw new UnsupportedOperationException();
					}

					@Override
					public SearchContext getSearchContext() {
						return searchContext;
					}

				}));
	}

	protected void addStringQuery(BooleanQuery booleanQuery, String keywords) {
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

	protected boolean shouldSuppressIndexerProvidedClauses(
		SearchContext searchContext) {

		return GetterUtil.getBoolean(
			searchContext.getAttribute(
				"search.full.query.suppress.indexer.provided.clauses"));
	}

	@Reference
	protected KeywordQueryContributorsHolder keywordQueryContributorsHolder;

}