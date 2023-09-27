/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.language.override.web.internal.display.context;

import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.language.override.web.internal.display.LanguageItemDisplay;

import java.util.List;

/**
 * @author Drew Brokke
 */
public class ViewDisplayContext {

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

	private String _displayStyle;
	private boolean _hasManageLanguageOverridesPermission;
	private SearchContainer<LanguageItemDisplay> _searchContainer;
	private String _selectedLanguageId;
	private List<DropdownItem> _translationLanguageDropdownItems;

}