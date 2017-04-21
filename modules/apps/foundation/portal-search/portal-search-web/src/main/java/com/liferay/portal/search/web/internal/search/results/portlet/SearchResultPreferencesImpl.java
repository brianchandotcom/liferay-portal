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

package com.liferay.portal.search.web.internal.search.results.portlet;

import com.liferay.portal.search.web.internal.display.context.SearchResultPreferences;
import com.liferay.portal.search.web.internal.document.DocumentFormPermissionChecker;

/**
 * @author André de Oliveira
 */
public class SearchResultPreferencesImpl implements SearchResultPreferences {

	public SearchResultPreferencesImpl(
		SearchResultsPortletPreferences searchResultsPortletPreferences,
		DocumentFormPermissionChecker documentFormPermissionChecker) {

		_searchResultsPortletPreferences = searchResultsPortletPreferences;
		_documentFormPermissionChecker = documentFormPermissionChecker;
	}

	@Override
	public boolean isDisplayResultsInDocumentForm() {
		if (!_searchResultsPortletPreferences.isDisplayInDocumentForm()) {
			return false;
		}

		if (!_documentFormPermissionChecker.hasPermission()) {
			return false;
		}

		return true;
	}

	@Override
	public boolean isHighlightEnabled() {
		return _searchResultsPortletPreferences.isHighlightEnabled();
	}

	@Override
	public boolean isViewInContext() {
		return _searchResultsPortletPreferences.isViewInContext();
	}

	private final DocumentFormPermissionChecker _documentFormPermissionChecker;
	private final SearchResultsPortletPreferences
		_searchResultsPortletPreferences;

}