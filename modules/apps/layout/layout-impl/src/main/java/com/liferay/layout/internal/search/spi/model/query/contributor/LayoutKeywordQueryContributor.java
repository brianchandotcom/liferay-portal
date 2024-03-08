/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.internal.search.spi.model.query.contributor;

import com.liferay.portal.kernel.search.BooleanQuery;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.QueryConfig;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.search.localization.SearchLocalizationHelper;
import com.liferay.portal.search.query.QueryHelper;
import com.liferay.portal.search.spi.model.query.contributor.KeywordQueryContributor;
import com.liferay.portal.search.spi.model.query.contributor.helper.KeywordQueryContributorHelper;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Vagner B.C
 */
@Component(
	property = "indexer.class.name=com.liferay.portal.kernel.model.Layout",
	service = KeywordQueryContributor.class
)
public class LayoutKeywordQueryContributor implements KeywordQueryContributor {

	@Override
	public void contribute(
		String keywords, BooleanQuery booleanQuery,
		KeywordQueryContributorHelper keywordQueryContributorHelper) {

		SearchContext searchContext =
			keywordQueryContributorHelper.getSearchContext();

		List<String> prefixes = new ArrayList<>(
			Arrays.asList(Field.NAME, Field.TITLE));

		Serializable searchOnlyByTitle = searchContext.getAttribute(
			"searchOnlyByTitle");

		if (Validator.isNull(searchOnlyByTitle)) {
			searchOnlyByTitle = false;
		}

		if (searchOnlyByTitle.equals(false)) {
			_queryHelper.addSearchLocalizedTerm(
				booleanQuery, searchContext, Field.CONTENT, false);

			prefixes.add(Field.CONTENT);
		}

		_queryHelper.addSearchTerm(
			booleanQuery, searchContext, Field.NAME, false);
		_queryHelper.addSearchLocalizedTerm(
			booleanQuery, searchContext, Field.TITLE, false);

		QueryConfig queryConfig = searchContext.getQueryConfig();

		queryConfig.addHighlightFieldNames(
			_searchLocalizationHelper.getLocalizedFieldNames(
				prefixes.toArray(new String[0]), searchContext));
	}

	@Reference
	private QueryHelper _queryHelper;

	@Reference
	private SearchLocalizationHelper _searchLocalizationHelper;

}