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

package com.liferay.portal.search.internal.localization;

import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.search.localization.SearchLocalizationHelper;

import java.util.HashSet;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Adam Brandizzi
 */
@Component(immediate = true, service = SearchLocalizationHelper.class)
public class SearchLocalizationHelperImpl implements SearchLocalizationHelper {

	@Override
	public void addLocalizedField(
		Document document, String field, Locale defaultLocale,
		Map<Locale, String> map) {

		for (Map.Entry<Locale, String> entry : map.entrySet()) {
			Locale locale = entry.getKey();

			if (locale.equals(defaultLocale)) {
				document.addText(field, entry.getValue());
			}

			document.addText(
				Field.getLocalizedName(locale, field), entry.getValue());
		}
	}

	@Override
	public Locale[] getLocales(SearchContext searchContext) {
		long[] groupIds = searchContext.getGroupIds();

		if (ArrayUtil.isEmpty(groupIds)) {
			Set<Locale> locales = _language.getCompanyAvailableLocales(
				searchContext.getCompanyId());

			return locales.toArray(new Locale[0]);
		}

		if (groupIds.length == 1) {
			Set<Locale> locales = _language.getAvailableLocales(groupIds[0]);

			return locales.toArray(new Locale[0]);
		}

		Set<Locale> locales = new HashSet<>();

		for (long groupId : groupIds) {
			locales.addAll(_language.getAvailableLocales(groupId));
		}

		return locales.toArray(new Locale[0]);
	}

	@Override
	public String[] getLocalizedFieldNames(
		String[] prefixes, SearchContext searchContext) {

		return getLocalizedFieldNames(prefixes, getLocales(searchContext));
	}

	protected String[] getLocalizedFieldNames(
		String[] prefixes, Locale[] locales) {

		Set<String> fieldNames = new HashSet<>();

		for (String prefix : prefixes) {
			for (Locale locale : locales) {
				fieldNames.add(Field.getLocalizedName(locale, prefix));
			}
		}

		return fieldNames.toArray(new String[0]);
	}

	@Reference
	private Language _language;

}