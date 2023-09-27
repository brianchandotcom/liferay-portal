/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.internal.search.spi.model.query.contributor;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.BooleanQuery;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.ParseException;
import com.liferay.portal.kernel.search.QueryConfig;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.generic.BooleanQueryImpl;
import com.liferay.portal.kernel.search.generic.MatchQuery;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.search.localization.SearchLocalizationHelper;
import com.liferay.portal.search.query.QueryHelper;
import com.liferay.portal.search.spi.model.query.contributor.KeywordQueryContributor;
import com.liferay.portal.search.spi.model.query.contributor.helper.KeywordQueryContributorHelper;

import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Bryan Engler
 */
@Component(
	property = "indexer.class.name=com.liferay.document.library.kernel.model.DLFileEntry",
	service = KeywordQueryContributor.class
)
public class DLFileEntryKeywordQueryContributor
	implements KeywordQueryContributor {

	@Override
	public void contribute(
		String keywords, BooleanQuery booleanQuery,
		KeywordQueryContributorHelper keywordQueryContributorHelper) {

		SearchContext searchContext =
			keywordQueryContributorHelper.getSearchContext();

		if (Validator.isNull(keywords)) {
			_queryHelper.addSearchLocalizedTerm(
				booleanQuery, searchContext, Field.DESCRIPTION, false);
			_queryHelper.addSearchTerm(
				booleanQuery, searchContext, Field.USER_NAME, false);
		}

		_queryHelper.addSearchTerm(
			booleanQuery, searchContext, "ddmContent", false);
		_queryHelper.addSearchTerm(
			booleanQuery, searchContext, "extension", false);
		_queryHelper.addSearchTerm(
			booleanQuery, searchContext, "fileEntryTypeId", false);
		_queryHelper.addSearchLocalizedTerm(
			booleanQuery, searchContext, Field.CONTENT, false);
		_queryHelper.addSearchLocalizedTerm(
			booleanQuery, searchContext, Field.TITLE, false);

		if (Validator.isNotNull(keywords)) {
			try {
				BooleanQuery fileNameBooleanQuery = new BooleanQueryImpl();

				_addKeywordsToFileNameBooleanQuery(
					fileNameBooleanQuery, keywords);

				booleanQuery.add(
					_getMatchQuery(
						"fileExtension", keywords,
						MatchQuery.Type.PHRASE_PREFIX),
					BooleanClauseOccur.SHOULD);
				fileNameBooleanQuery.add(
					_getMatchQuery(
						"fileName", keywords, MatchQuery.Type.PHRASE),
					BooleanClauseOccur.SHOULD);

				booleanQuery.add(
					fileNameBooleanQuery, BooleanClauseOccur.SHOULD);
			}
			catch (ParseException parseException) {
				throw new SystemException(parseException);
			}
		}

		QueryConfig queryConfig = searchContext.getQueryConfig();

		queryConfig.addHighlightFieldNames(
			_searchLocalizationHelper.getLocalizedFieldNames(
				new String[] {Field.CONTENT, Field.TITLE}, searchContext));
	}

	private void _addKeywordsToFileNameBooleanQuery(
			BooleanQuery fileNameBooleanQuery, String keywords)
		throws ParseException {

		String exactMatch = StringUtils.substringBetween(
			keywords, StringPool.QUOTE);

		if (Validator.isNull(exactMatch)) {
			fileNameBooleanQuery.add(
				_getShouldBooleanQuery(StringUtil.trim(keywords)),
				BooleanClauseOccur.MUST);
		}
		else {
			fileNameBooleanQuery.add(
				_getMatchQuery("fileName", exactMatch, MatchQuery.Type.PHRASE),
				BooleanClauseOccur.MUST);

			String remainingKeywords = keywords.replaceFirst(
				Pattern.quote(StringPool.QUOTE + exactMatch + StringPool.QUOTE),
				"");

			if (Validator.isNotNull(remainingKeywords)) {
				_addKeywordsToFileNameBooleanQuery(
					fileNameBooleanQuery, remainingKeywords);
			}
		}
	}

	private MatchQuery _getMatchQuery(
		String field, String keywords, MatchQuery.Type phrase) {

		MatchQuery matchPhraseQuery = new MatchQuery(field, keywords);

		matchPhraseQuery.setType(phrase);

		return matchPhraseQuery;
	}

	private BooleanQuery _getShouldBooleanQuery(String keyword)
		throws ParseException {

		BooleanQuery booleanQuery = new BooleanQueryImpl();

		booleanQuery.add(
			new MatchQuery("fileName", keyword), BooleanClauseOccur.SHOULD);
		booleanQuery.add(
			_getMatchQuery("fileName", keyword, MatchQuery.Type.PHRASE_PREFIX),
			BooleanClauseOccur.SHOULD);

		return booleanQuery;
	}

	@Reference
	private QueryHelper _queryHelper;

	@Reference
	private SearchLocalizationHelper _searchLocalizationHelper;

}