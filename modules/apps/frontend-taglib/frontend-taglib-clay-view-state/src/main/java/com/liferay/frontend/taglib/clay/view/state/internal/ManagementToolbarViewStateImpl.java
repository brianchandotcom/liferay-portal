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

package com.liferay.frontend.taglib.clay.view.state.internal;

import com.liferay.frontend.taglib.clay.servlet.taglib.util.CreationMenu;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.CreationMenuBuilder;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.LabelItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.ViewTypeItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.ViewTypeItemList;
import com.liferay.frontend.taglib.clay.view.state.ManagementToolbarViewState;
import com.liferay.portal.kernel.dao.search.DisplayTerms;
import com.liferay.portal.kernel.util.Validator;

import java.util.List;
import java.util.Map;

import javax.portlet.RenderURL;

/**
 * @author  Neil Griffin
 */
public class ManagementToolbarViewStateImpl
	implements ManagementToolbarViewState {

	public ManagementToolbarViewStateImpl(
		String addEntryMessage, RenderURL addEntryURL,
		RenderURL clearResultsURL, String displayStyle,
		RenderURL displayStyleURL, String searchFormMethod,
		String searchFormName, String searchInputName, RenderURL searchURL,
		String searchValue, boolean showCreationMenu,
		boolean showDisplayStyleCard, boolean showDisplayStyleList,
		boolean showDisplayStyleTable, String sortingOrder,
		RenderURL sortingURLCurrent, RenderURL sortingURLReverse) {

		_addEntryMessage = addEntryMessage;
		_addEntryURL = addEntryURL;
		_clearResultsURL = clearResultsURL;
		_displayStyle = displayStyle;
		_displayStyleURL = displayStyleURL;
		_searchFormMethod = searchFormMethod;
		_searchFormName = searchFormName;
		_searchInputName = searchInputName;
		_searchURL = searchURL;
		_searchValue = searchValue;
		_showCreationMenu = showCreationMenu;
		_showDisplayStyleCard = showDisplayStyleCard;
		_showDisplayStyleList = showDisplayStyleList;
		_showDisplayStyleTable = showDisplayStyleTable;
		_sortingOrder = sortingOrder;
		_sortingURLCurrent = sortingURLCurrent;
		_sortingURLReverse = sortingURLReverse;
	}

	@Override
	public List<DropdownItem> getActionDropdownItems() {
		return null;
	}

	@Override
	public String getAddEntryMessage() {
		return _addEntryMessage;
	}

	@Override
	public RenderURL getAddEntryURL() {
		return _addEntryURL;
	}

	@Override
	public RenderURL getClearResultsURL() {
		return _clearResultsURL;
	}

	@Override
	public String getComponentId() {
		return null;
	}

	@Override
	public String getContentRenderer() {
		return null;
	}

	@Override
	public CreationMenu getCreationMenu() {
		if (_creationMenu == null) {
			_creationMenu = CreationMenuBuilder.addDropdownItem(
				dropdownItem -> {
					dropdownItem.setHref(_addEntryURL);
					dropdownItem.setLabel(_addEntryMessage);
				}
			).build();
		}

		return _creationMenu;
	}

	@Override
	public Map<String, String> getData() {
		return null;
	}

	@Override
	public String getDefaultEventHandler() {
		return null;
	}

	@Override
	public String getDisplayStyle() {
		return _displayStyle;
	}

	@Override
	public RenderURL getDisplayStyleURL() {
		return _displayStyleURL;
	}

	@Override
	public String getElementClasses() {
		return null;
	}

	@Override
	public List<DropdownItem> getFilterDropdownItems() {
		return null;
	}

	@Override
	public List<LabelItem> getFilterLabelItems() {
		return null;
	}

	@Override
	public String getId() {
		return null;
	}

	@Override
	public String getInfoPanelId() {
		return null;
	}

	@Override
	public int getItemsTotal() {
		return 0;
	}

	@Override
	public String getNamespace() {
		return null;
	}

	@Override
	public RenderURL getSearchActionURL() {
		return _searchURL;
	}

	@Override
	public String getSearchContainerId() {
		return null;
	}

	@Override
	public String getSearchFormMethod() {
		if (_searchFormMethod == null) {
			_searchFormMethod = "GET";
		}

		return _searchFormMethod;
	}

	@Override
	public String getSearchFormName() {
		return _searchFormName;
	}

	@Override
	public String getSearchInputName() {
		if (_searchInputName == null) {
			_searchInputName = DisplayTerms.KEYWORDS;
		}

		return _searchInputName;
	}

	@Override
	public RenderURL getSearchURL() {
		return _searchURL;
	}

	@Override
	public String getSearchValue() {
		return _searchValue;
	}

	@Override
	public int getSelectedItems() {
		return 0;
	}

	@Override
	public String getSortingOrder() {
		return _sortingOrder;
	}

	@Override
	public RenderURL getSortingURL() {
		return _sortingURLReverse;
	}

	@Override
	public RenderURL getSortingURLCurrent() {
		return _sortingURLCurrent;
	}

	@Override
	public RenderURL getSortingURLReverse() {
		return _sortingURLReverse;
	}

	@Override
	public String getSpritemap() {
		return null;
	}

	@Override
	public List<ViewTypeItem> getViewTypeItems() {
		if (_viewTypeItems == null) {
			_viewTypeItems = new ViewTypeItemList(
				_displayStyleURL, _displayStyle);

			if (isShowDisplayStyleCard()) {
				_viewTypeItems.addCardViewTypeItem();
			}

			if (isShowDisplayStyleList()) {
				_viewTypeItems.addListViewTypeItem();
			}

			if (isShowDisplayStyleTable()) {
				_viewTypeItems.addTableViewTypeItem();
			}
		}

		return _viewTypeItems;
	}

	@Override
	public boolean isDisabled() {
		return false;
	}

	@Override
	public boolean isSelectable() {
		return true;
	}

	@Override
	public boolean isShowAdvancedSearch() {
		return false;
	}

	@Override
	public boolean isShowCreationMenu() {
		return _showCreationMenu;
	}

	@Override
	public boolean isShowDisplayStyleCard() {
		return _showDisplayStyleCard;
	}

	@Override
	public boolean isShowDisplayStyleList() {
		return _showDisplayStyleList;
	}

	@Override
	public boolean isShowDisplayStyleTable() {
		return _showDisplayStyleTable;
	}

	@Override
	public boolean isShowFiltersDoneButton() {
		return false;
	}

	@Override
	public boolean isShowInfoButton() {
		return Validator.isNotNull(getInfoPanelId());
	}

	@Override
	public boolean isShowSearch() {
		return Validator.isNotNull(getSearchActionURL());
	}

	@Override
	public boolean isSupportsBulkActions() {
		return false;
	}

	private final String _addEntryMessage;
	private final RenderURL _addEntryURL;
	private final RenderURL _clearResultsURL;
	private CreationMenu _creationMenu;
	private final String _displayStyle;
	private final RenderURL _displayStyleURL;
	private String _searchFormMethod;
	private final String _searchFormName;
	private String _searchInputName;
	private final RenderURL _searchURL;
	private final String _searchValue;
	private final boolean _showCreationMenu;
	private final boolean _showDisplayStyleCard;
	private final boolean _showDisplayStyleList;
	private final boolean _showDisplayStyleTable;
	private final String _sortingOrder;
	private final RenderURL _sortingURLCurrent;
	private final RenderURL _sortingURLReverse;
	private ViewTypeItemList _viewTypeItems;

}