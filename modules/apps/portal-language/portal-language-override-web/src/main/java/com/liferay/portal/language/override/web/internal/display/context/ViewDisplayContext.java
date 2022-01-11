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

package com.liferay.portal.language.override.web.internal.display.context;

import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.language.override.web.internal.display.LanguageItemDisplay;

import java.util.List;
import java.util.Locale;

/**
 * @author Drew Brokke
 */
public class ViewDisplayContext {

	public Locale[] getAvailableLocales() {
		return _availableLocales;
	}

	public String getDisplayStyle() {
		return _displayStyle;
	}

	public SearchContainer<LanguageItemDisplay> getSearchContainer() {
		return _searchContainer;
	}

	public String getSelectedLanguageId() {
		return _selectedLanguageId;
	}

	public List<DropdownItem> getTranslationLanguageDropdownItems() {
		return _translationLanguageDropdownItems;
	}

	public boolean isHasManageLanguageOverridesPermission() {
		return _hasManageLanguageOverridesPermission;
	}

	public void setAvailableLocales(Locale[] availableLocales) {
		_availableLocales = availableLocales;
	}

	public void setDisplayStyle(String displayStyle) {
		_displayStyle = displayStyle;
	}

	public void setHasManageLanguageOverridesPermission(
		boolean hasManageLanguageOverridesPermission) {

		_hasManageLanguageOverridesPermission =
			hasManageLanguageOverridesPermission;
	}

	public void setSearchContainer(
		SearchContainer<LanguageItemDisplay> searchContainer) {

		_searchContainer = searchContainer;
	}

	public void setSelectedLanguageId(String selectedLanguageId) {
		_selectedLanguageId = selectedLanguageId;
	}

	public void setTranslationLanguageDropdownItems(
		List<DropdownItem> translationLanguageDropdownItems) {

		_translationLanguageDropdownItems = translationLanguageDropdownItems;
	}

	private Locale[] _availableLocales;
	private String _displayStyle;
	private boolean _hasManageLanguageOverridesPermission;
	private SearchContainer<LanguageItemDisplay> _searchContainer;
	private String _selectedLanguageId;
	private List<DropdownItem> _translationLanguageDropdownItems;

}