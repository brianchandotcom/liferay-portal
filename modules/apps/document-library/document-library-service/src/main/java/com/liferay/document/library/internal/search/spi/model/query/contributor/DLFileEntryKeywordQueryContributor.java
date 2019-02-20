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

package com.liferay.document.library.internal.search.spi.model.query.contributor;

import com.liferay.portal.kernel.search.BooleanQuery;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.search.query.QueryHelper;
import com.liferay.portal.search.spi.model.query.contributor.KeywordQueryContributor;
import com.liferay.portal.search.spi.model.query.contributor.helper.KeywordQueryContributorHelper;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Bryan Engler
 */
@Component(
	immediate = true,
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
			queryHelper.addSearchTerm(
				booleanQuery, searchContext, Field.DESCRIPTION, false);
			queryHelper.addSearchTerm(
				booleanQuery, searchContext, Field.TITLE, false);
			queryHelper.addSearchTerm(
				booleanQuery, searchContext, Field.USER_NAME, false);
		}

		queryHelper.addSearchTerm(
			booleanQuery, searchContext, "ddmContent", false);
		queryHelper.addSearchTerm(
			booleanQuery, searchContext, "extension", false);
		queryHelper.addSearchTerm(
			booleanQuery, searchContext, "fileEntryTypeId", false);
		queryHelper.addSearchTerm(booleanQuery, searchContext, "path", false);
		queryHelper.addSearchLocalizedTerm(
			booleanQuery, searchContext, Field.CONTENT, false);
	}

	@Reference
	protected QueryHelper queryHelper;

}