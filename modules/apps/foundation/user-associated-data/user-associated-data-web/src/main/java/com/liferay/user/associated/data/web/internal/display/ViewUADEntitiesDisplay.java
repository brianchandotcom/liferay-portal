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

package com.liferay.user.associated.data.web.internal.display;

import com.liferay.frontend.taglib.clay.servlet.taglib.util.NavigationItem;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.user.associated.data.display.UADEntityDisplay;

import java.util.List;

/**
 * @author Drew Brokke
 */
public class ViewUADEntitiesDisplay {

	public String getApplicationName() {
		return _applicationName;
	}

	public List<NavigationItem> getNavigationItems() {
		return _navigationItems;
	}

	public SearchContainer<UADEntity> getSearchContainer() {
		return _searchContainer;
	}

	public UADEntityDisplay getUADEntityDisplay() {
		return _uadEntityDisplay;
	}

	public String getUADRegistryKey() {
		return _uadRegistryKey;
	}

	public void setApplicationName(String applicationName) {
		_applicationName = applicationName;
	}

	public void setNavigationItems(List<NavigationItem> navigationItems) {
		_navigationItems = navigationItems;
	}

	public void setSearchContainer(SearchContainer<UADEntity> searchContainer) {
		_searchContainer = searchContainer;
	}

	public void setUADEntityDisplay(UADEntityDisplay uadEntityDisplay) {
		_uadEntityDisplay = uadEntityDisplay;
	}

	public void setUADRegistryKey(String uadRegistryKey) {
		_uadRegistryKey = uadRegistryKey;
	}

	private String _applicationName;
	private List<NavigationItem> _navigationItems;
	private SearchContainer<UADEntity> _searchContainer;
	private UADEntityDisplay _uadEntityDisplay;
	private String _uadRegistryKey;

}