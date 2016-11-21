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

package com.liferay.portal.kernel.search;

import com.liferay.portal.kernel.search.result.SearchResultTranslator;
import com.liferay.portal.kernel.util.ServiceProxyFactory;

import java.util.List;
import java.util.Locale;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

/**
 * @author Eudaldo Alonso
 */
public class SearchResultUtil {

	public static List<SearchResult> getSearchResults(
		Hits hits, Locale locale) {

		return getSearchResults(hits, locale, null, null);
	}

	public static List<SearchResult> getSearchResults(
		Hits hits, Locale locale, PortletRequest portletRequest,
		PortletResponse portletResponse) {

		return _searchResultTranslator.translate(
			hits, locale, portletRequest, portletResponse);
	}

	private static volatile SearchResultTranslator _searchResultTranslator =
		ServiceProxyFactory.newServiceTrackedInstance(
			SearchResultTranslator.class, SearchResultUtil.class,
			"_searchResultTranslator", false);

}