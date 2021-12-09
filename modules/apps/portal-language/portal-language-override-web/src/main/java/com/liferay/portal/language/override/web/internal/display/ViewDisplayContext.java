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

package com.liferay.portal.language.override.web.internal.display;

import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.language.override.web.internal.dto.PLOItemDTO;

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

	public SearchContainer<PLOItemDTO> getSearchContainer() {
		return _searchContainer;
	}

	public String getSelectedLanguage() {
		return _selectedLanguage;
	}

	public void setAvailableLocales(Locale[] availableLocales) {
		_availableLocales = availableLocales;
	}

	public void setDisplayStyle(String displayStyle) {
		_displayStyle = displayStyle;
	}

	public void setSearchContainer(
		SearchContainer<PLOItemDTO> searchContainer) {

		_searchContainer = searchContainer;
	}

	public void setSelectedLanguage(String selectedLanguage) {
		_selectedLanguage = selectedLanguage;
	}

	private Locale[] _availableLocales;
	private String _displayStyle;
	private SearchContainer<PLOItemDTO> _searchContainer;
	private String _selectedLanguage;

}