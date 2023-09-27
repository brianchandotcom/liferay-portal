/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
	public String getFieldsToDisplay() {
		return _searchResultsPortletPreferences.getFieldsToDisplay();
	}

	@Override
	public boolean isDisplayResultsInDocumentForm() {
		if (!_searchResultsPortletPreferences.isDisplayInDocumentForm() ||
			!_documentFormPermissionChecker.hasPermission()) {

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