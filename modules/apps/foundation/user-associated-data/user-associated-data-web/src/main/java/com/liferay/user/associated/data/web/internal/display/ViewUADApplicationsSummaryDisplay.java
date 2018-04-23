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

import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemList;
import com.liferay.portal.kernel.dao.search.SearchContainer;

/**
 * @author Drew Brokke
 */
public class ViewUADApplicationsSummaryDisplay {

	public DropdownItemList getManagementBarFilterItems() {
		return _managementBarFilterItems;
	}

	public SearchContainer<UADApplicationSummaryDisplay> getSearchContainer() {
		return _searchContainer;
	}

	public String getSortingURL() {
		return _sortingURL;
	}

	public int getTotalCount() {
		return _totalCount;
	}

	public void setManagementBarFilterItems(
		DropdownItemList managementBarFilterItems) {

		_managementBarFilterItems = managementBarFilterItems;
	}

	public void setSearchContainer(
		SearchContainer<UADApplicationSummaryDisplay> searchContainer) {

		_searchContainer = searchContainer;
	}

	public void setSortingURL(String sortingURL) {
		_sortingURL = sortingURL;
	}

	public void setTotalCount(int totalCount) {
		_totalCount = totalCount;
	}

	private DropdownItemList _managementBarFilterItems;
	private SearchContainer<UADApplicationSummaryDisplay> _searchContainer;
	private String _sortingURL;
	private int _totalCount;

}