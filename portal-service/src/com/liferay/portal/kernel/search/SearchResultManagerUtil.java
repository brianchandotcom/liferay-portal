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

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.pacl.permission.PortalRuntimePermission;

import java.util.Locale;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

/**
 * @author Adolfo Pérez
 */
public class SearchResultManagerUtil {

	public static SearchResult createSearchResult(Document document)
		throws PortalException {

		return getSearchResultManager().createSearchResult(document);
	}

	public static SearchResultManager getSearchResultManager() {
		PortalRuntimePermission.checkGetBeanProperty(
			SearchResultManagerUtil.class);

		return _searchResultManager;
	}

	public static void updateSearchResult(
			SearchResult searchResult, Document document, Locale locale,
			PortletRequest portletRequest, PortletResponse portletResponse)
		throws PortalException {

		getSearchResultManager().updateSearchResult(
			searchResult, document, locale, portletRequest, portletResponse);
	}

	public void setSearchResultManager(
		SearchResultManager searchResultManager) {

		PortalRuntimePermission.checkSetBeanProperty(getClass());

		_searchResultManager = searchResultManager;
	}

	private static SearchResultManager _searchResultManager;

}