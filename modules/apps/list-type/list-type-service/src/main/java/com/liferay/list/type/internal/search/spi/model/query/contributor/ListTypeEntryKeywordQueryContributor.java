/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.list.type.internal.search.spi.model.query.contributor;

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
 * @author Carolina Barbosa
 */
@Component(
	property = "indexer.class.name=com.liferay.list.type.model.ListTypeEntry",
	service = KeywordQueryContributor.class
)
public class ListTypeEntryKeywordQueryContributor
	implements KeywordQueryContributor {

	@Override
	public void contribute(
		String keywords, BooleanQuery booleanQuery,
		KeywordQueryContributorHelper keywordQueryContributorHelper) {

		_addSearchLocalizedTerm(
			booleanQuery, keywordQueryContributorHelper.getSearchContext(),
			Field.NAME);
		_addSearchTerm(
			booleanQuery, keywordQueryContributorHelper.getSearchContext(),
			"key");
	}

	private void _addSearchLocalizedTerm(
		BooleanQuery booleanQuery, SearchContext searchContext,
		String fieldName) {

		if (Validator.isNull(searchContext.getAttribute(fieldName))) {
			return;
		}

		searchContext.setAttribute(
			_localization.getLocalizedName(
				fieldName, searchContext.getLanguageId()),
			searchContext.getAttribute(fieldName));

		_queryHelper.addSearchLocalizedTerm(
			booleanQuery, searchContext, fieldName, false);
	}

	private void _addSearchTerm(
		BooleanQuery booleanQuery, SearchContext searchContext,
		String fieldName) {

		if (Validator.isNull(searchContext.getAttribute(fieldName))) {
			return;
		}

		_queryHelper.addSearchTerm(
			booleanQuery, searchContext, fieldName, false);
	}

	@Reference
	private Localization _localization;

	@Reference
	private QueryHelper _queryHelper;

}