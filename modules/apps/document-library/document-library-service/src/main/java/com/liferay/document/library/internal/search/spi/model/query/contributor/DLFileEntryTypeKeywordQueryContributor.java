/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.internal.search.spi.model.query.contributor;

import com.liferay.portal.kernel.search.BooleanQuery;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.util.Localization;
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
	property = "indexer.class.name=com.liferay.document.library.kernel.model.DLFileEntryType",
	service = KeywordQueryContributor.class
)
public class DLFileEntryTypeKeywordQueryContributor
	implements KeywordQueryContributor {

	@Override
	public void contribute(
		String keywords, BooleanQuery booleanQuery,
		KeywordQueryContributorHelper keywordQueryContributorHelper) {

		addSearchLocalizedTerm(
			booleanQuery, keywordQueryContributorHelper, Field.DESCRIPTION);
		addSearchLocalizedTerm(
			booleanQuery, keywordQueryContributorHelper, Field.NAME);
	}

	protected void addSearchLocalizedTerm(
		BooleanQuery booleanQuery,
		KeywordQueryContributorHelper keywordQueryContributorHelper,
		String fieldName) {

		SearchContext searchContext =
			keywordQueryContributorHelper.getSearchContext();

		if (Validator.isNull(searchContext.getAttribute(fieldName))) {
			return;
		}

		String fieldNameLocalizedName = _localization.getLocalizedName(
			fieldName, searchContext.getLanguageId());

		searchContext.setAttribute(
			fieldNameLocalizedName, searchContext.getAttribute(fieldName));

		_queryHelper.addSearchLocalizedTerm(
			booleanQuery, keywordQueryContributorHelper.getSearchContext(),
			fieldName, false);
	}

	@Reference
	private Localization _localization;

	@Reference
	private QueryHelper _queryHelper;

}