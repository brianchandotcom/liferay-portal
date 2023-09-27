/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.tuning.rankings.web.internal.display.context;

import com.liferay.frontend.taglib.clay.servlet.taglib.util.CreationMenu;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;

import java.util.List;

/**
 * @author Kevin Tan
 */
public class RankingPortletDisplayContext {

	public List<DropdownItem> getActionDropdownItems() {
		return _actionDropdownItems;
	}

	public String getClearResultsURL() {
		return _clearResultsURL;
	}

	public CreationMenu getCreationMenu() {
		return _creationMenu;
	}

	public String getDisplayStyle() {
		return _displayStyle;
	}

	public List<DropdownItem> getFilterItemsDropdownItems() {
		return _filterItemsDropdownItems;
	}

	public String getOrderByType() {
		return _orderByType;
	}

	public String getSearchActionURL() {
		return _searchActionURL;
	}

	public SearchContainer<RankingEntryDisplayContext> getSearchContainer() {
		return _searchContainer;
	}

	public String getSortingURL() {
		return _sortingURL;
	}

	public int getTotalItems() throws PortalException {
		return _totalItems;
	}

	public boolean isDisabledManagementBar() throws PortalException {
		return _disabledManagementBar;
	}

	public Boolean isShowCreationMenu() {
		return true;
	}

	public void setActionDropdownItems(List<DropdownItem> actionDropdownItems) {
		_actionDropdownItems = actionDropdownItems;
	}

	public void setClearResultsURL(String clearResultsURL) {
		_clearResultsURL = clearResultsURL;
	}

	public void setCreationMenu(CreationMenu creationMenu) {
		_creationMenu = creationMenu;
	}

	public void setDisabledManagementBar(boolean disabledManagementBar) {
		_disabledManagementBar = disabledManagementBar;
	}

	public void setDisplayStyle(String displayStyle) {
		_displayStyle = displayStyle;
	}

	public void setFilterItemsDropdownItems(
		List<DropdownItem> filterItemsDropdownItems) {

		_filterItemsDropdownItems = filterItemsDropdownItems;
	}

	public void setOrderByType(String orderByType) {
		_orderByType = orderByType;
	}

	public void setSearchActionURL(String searchActionURL) {
		_searchActionURL = searchActionURL;
	}

	public void setSearchContainer(
		SearchContainer<RankingEntryDisplayContext> searchContainer) {

		_searchContainer = searchContainer;
	}

	public void setSortingURL(String sortingURL) {
		_sortingURL = sortingURL;
	}

	public void setTotalItems(int totalItems) {
		_totalItems = totalItems;
	}

	private List<DropdownItem> _actionDropdownItems;
	private String _clearResultsURL;
	private CreationMenu _creationMenu;
	private boolean _disabledManagementBar;
	private String _displayStyle;
	private List<DropdownItem> _filterItemsDropdownItems;
	private String _orderByType;
	private String _searchActionURL;
	private SearchContainer<RankingEntryDisplayContext> _searchContainer;
	private String _sortingURL;
	private int _totalItems;

}