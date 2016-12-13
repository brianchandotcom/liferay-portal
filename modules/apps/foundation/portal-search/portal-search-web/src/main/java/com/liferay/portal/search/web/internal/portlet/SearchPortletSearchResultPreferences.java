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

package com.liferay.portal.search.web.internal.portlet;

import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.search.web.internal.display.context.SearchResultPreferences;
import com.liferay.portal.search.web.internal.display.context.ThemeDisplaySupplier;

import javax.portlet.PortletPreferences;

/**
 * @author André de Oliveira
 */
public class SearchPortletSearchResultPreferences
	implements SearchResultPreferences {

	public SearchPortletSearchResultPreferences(
		PortletPreferences portletPreferences,
		ThemeDisplaySupplier themeDisplaySupplier) {

		_portletPreferences = portletPreferences;
		_themeDisplaySupplier = themeDisplaySupplier;
	}

	@Override
	public boolean isDisplayResultsInDocumentForm() {
		if (_displayResultsInDocumentForm != null) {
			return _displayResultsInDocumentForm;
		}

		_displayResultsInDocumentForm = GetterUtil.getBoolean(
			_portletPreferences.getValue("displayResultsInDocumentForm", null));

		ThemeDisplay themeDisplay = getThemeDisplay();

		PermissionChecker permissionChecker =
			themeDisplay.getPermissionChecker();

		if (!permissionChecker.isCompanyAdmin()) {
			_displayResultsInDocumentForm = false;
		}

		return _displayResultsInDocumentForm;
	}

	@Override
	public boolean isViewInContext() {
		if (_viewInContext != null) {
			return _viewInContext;
		}

		_viewInContext = GetterUtil.getBoolean(
			_portletPreferences.getValue("viewInContext", null), true);

		return _viewInContext;
	}

	protected ThemeDisplay getThemeDisplay() {
		return _themeDisplaySupplier.getThemeDisplay();
	}

	private Boolean _displayResultsInDocumentForm;
	private final PortletPreferences _portletPreferences;
	private final ThemeDisplaySupplier _themeDisplaySupplier;
	private Boolean _viewInContext;

}