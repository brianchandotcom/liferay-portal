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

package com.liferay.journal.internal.search.spi.model.query.contributor;

import com.liferay.portal.kernel.search.BooleanQuery;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.search.spi.model.query.contributor.KeywordQueryContributor;
import com.liferay.portal.search.spi.model.query.contributor.helper.KeywordQueryContributorHelper;

import java.io.Serializable;

import org.osgi.service.component.annotations.Component;

/**
 * @author Lourdes Fernández Besada
 */
@Component(
	immediate = true,
	property = "indexer.class.name=com.liferay.journal.model.JournalFolder",
	service = KeywordQueryContributor.class
)
public class JournalFolderKeywordQueryContributor
	implements KeywordQueryContributor {

	@Override
	public void contribute(
		String keywords, BooleanQuery booleanQuery,
		KeywordQueryContributorHelper keywordQueryContributorHelper) {

		addSearchLocalizedTerm(
			booleanQuery, keywordQueryContributorHelper.getSearchContext(),
			Field.DESCRIPTION, false);
		addSearchLocalizedTerm(
			booleanQuery, keywordQueryContributorHelper.getSearchContext(),
			Field.TITLE, false);
	}

	protected void addSearchLocalizedTerm(
			BooleanQuery searchQuery, SearchContext searchContext, String field,
			boolean like)
		throws Exception {

		addSearchTerm(searchQuery, searchContext, field, like);

		String localizedFieldName = Field.getLocalizedName(
			searchContext.getLocale(), field);

		addSearchTerm(searchQuery, searchContext, localizedFieldName, like);
	}

	protected void addSearchTerm(
			BooleanQuery searchQuery, SearchContext searchContext, String field,
			boolean like)
		throws Exception {

		String value = null;

		Serializable serializable = searchContext.getAttribute(field);

		if (serializable != null) {
			Class<?> clazz = serializable.getClass();

			if (clazz.isArray()) {
				value = StringUtil.merge((Object[])serializable);
			}
			else {
				value = GetterUtil.getString(serializable);
			}
		}
		else {
			value = GetterUtil.getString(serializable);
		}

		if (Validator.isNotNull(value) &&
			(searchContext.getFacet(field) != null)) {

			return;
		}

		if (Validator.isNull(value)) {
			value = searchContext.getKeywords();
		}

		if (Validator.isNull(value)) {
			return;
		}

		if (searchContext.isAndSearch()) {
			searchQuery.addRequiredTerm(field, value, like);
		}
		else {
			searchQuery.addTerm(field, value, like);
		}
	}

}