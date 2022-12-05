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

package com.liferay.feature.flag.web.internal.display;

import com.liferay.feature.flag.web.internal.model.FeatureFlagDisplay;
import com.liferay.frontend.taglib.clay.servlet.taglib.display.context.ManagementToolbarDisplayContext;
import com.liferay.portal.kernel.dao.search.SearchContainer;

/**
 * @author Drew Brokke
 */
public class FeatureFlagsDisplayContext {

	public String getDescription() {
		return _description;
	}

	public String getDisplayStyle() {
		return _displayStyle;
	}

	public ManagementToolbarDisplayContext
		getManagementToolbarDisplayContext() {

		return _managementToolbarDisplayContext;
	}

	public SearchContainer<FeatureFlagDisplay> getSearchContainer() {
		return _searchContainer;
	}

	public String getSearchResultCssClass() {
		return _searchResultCssClass;
	}

	public String getTitle() {
		return _title;
	}

	public void setDescription(String description) {
		_description = description;
	}

	public void setDisplayStyle(String displayStyle) {
		_displayStyle = displayStyle;
	}

	public void setManagementToolbarDisplayContext(
		ManagementToolbarDisplayContext managementToolbarDisplayContext) {

		_managementToolbarDisplayContext = managementToolbarDisplayContext;
	}

	public void setSearchContainer(
		SearchContainer<FeatureFlagDisplay> searchContainer) {

		_searchContainer = searchContainer;
	}

	public void setSearchResultCssClass(String searchResultCssClass) {
		_searchResultCssClass = searchResultCssClass;
	}

	public void setTitle(String title) {
		_title = title;
	}

	private String _description;
	private String _displayStyle;
	private ManagementToolbarDisplayContext _managementToolbarDisplayContext;
	private SearchContainer<FeatureFlagDisplay> _searchContainer;
	private String _searchResultCssClass;
	private String _title;

}