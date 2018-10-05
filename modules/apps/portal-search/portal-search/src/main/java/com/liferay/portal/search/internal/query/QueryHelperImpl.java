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

package com.liferay.portal.search.internal.query;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.search.BooleanQuery;
import com.liferay.portal.kernel.search.ParseException;
import com.liferay.portal.kernel.search.Query;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Localization;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.search.query.QueryHelper;

import java.io.Serializable;

import java.util.Locale;

import org.osgi.service.component.annotations.Component;

/**
 * @author Michael C. Han
 */
@Component(immediate = true, service = QueryHelper.class)
public class QueryHelperImpl implements QueryHelper {

	@Override
	public void addSearchLocalizedTerm(
		BooleanQuery searchQuery, SearchContext searchContext, String field,
		boolean like) {

		addSearchTerm(
			searchQuery, searchContext,
			getLocalizedName(field, searchContext.getLocale()), like);
	}

	@Override
	public Query addSearchTerm(
		BooleanQuery searchQuery, SearchContext searchContext, String field,
		boolean like) {

		if (Validator.isBlank(field)) {
			return null;
		}

		String value = StringPool.BLANK;

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

		if (!Validator.isBlank(value) &&
			(searchContext.getFacet(field) != null)) {

			return null;
		}

		if (Validator.isBlank(value)) {
			value = searchContext.getKeywords();
		}

		if (Validator.isBlank(value)) {
			return null;
		}

		Query query = null;

		if (searchContext.isAndSearch()) {
			query = searchQuery.addRequiredTerm(field, value, like);
		}
		else {
			try {
				query = searchQuery.addTerm(field, value, like);
			}
			catch (ParseException pe) {
				throw new SystemException(pe);
			}
		}

		return query;
	}

	protected Localization getLocalization() {

		// See LPS-72507

		if (localization != null) {
			return localization;
		}

		return LocalizationUtil.getLocalization();
	}

	protected String getLocalizedName(String name, Locale locale) {
		Localization localization = getLocalization();

		return localization.getLocalizedName(
			name, LocaleUtil.toLanguageId(locale));
	}

	protected Localization localization;

}