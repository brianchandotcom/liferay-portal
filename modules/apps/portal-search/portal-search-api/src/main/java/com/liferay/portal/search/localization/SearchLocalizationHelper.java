/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.localization;

import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.SearchContext;

import java.util.Locale;
import java.util.Map;

/**
 * @author Adam Brandizzi
 */
public interface SearchLocalizationHelper {

	public void addLocalizedField(
		Document document, String field, Locale defaultLocale,
		Map<Locale, String> map);

	public Locale[] getLocales(SearchContext searchContext);

	public String[] getLocalizedFieldNames(
		String[] prefixes, SearchContext searchContext);

}