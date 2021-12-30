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

package com.liferay.dynamic.data.lists.internal.search.spi.model.query.contributor;

import com.liferay.portal.kernel.search.BooleanQuery;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.search.query.QueryHelper;
import com.liferay.portal.search.spi.model.query.contributor.KeywordQueryContributor;
import com.liferay.portal.search.spi.model.query.contributor.helper.KeywordQueryContributorHelper;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marcela Cunha
 */
@Component(
	immediate = true,
	property = "indexer.class.name=com.liferay.dynamic.data.lists.model.DDLRecordSet",
	service = KeywordQueryContributor.class
)
public class DDLRecordSetKeywordQueryContributor
	implements KeywordQueryContributor {

	@Override
	public void contribute(
		String keywords, BooleanQuery booleanQuery,
		KeywordQueryContributorHelper keywordQueryContributorHelper) {

		_addSearchLocalizedTerm(
			booleanQuery, keywordQueryContributorHelper, Field.DESCRIPTION);
		_addSearchLocalizedTerm(
			booleanQuery, keywordQueryContributorHelper, Field.NAME);
	}

	@Reference
	protected QueryHelper queryHelper;

	private void _addSearchLocalizedTerm(
		BooleanQuery booleanQuery,
		KeywordQueryContributorHelper keywordQueryContributorHelper,
		String fieldName) {

		SearchContext searchContext =
			keywordQueryContributorHelper.getSearchContext();

		if (Validator.isNull(searchContext.getAttribute(fieldName))) {
			return;
		}

		searchContext.setAttribute(
			LocalizationUtil.getLocalizedName(
				fieldName, searchContext.getLanguageId()),
			searchContext.getAttribute(fieldName));

		queryHelper.addSearchLocalizedTerm(
			booleanQuery, keywordQueryContributorHelper.getSearchContext(),
			fieldName, false);
	}

}