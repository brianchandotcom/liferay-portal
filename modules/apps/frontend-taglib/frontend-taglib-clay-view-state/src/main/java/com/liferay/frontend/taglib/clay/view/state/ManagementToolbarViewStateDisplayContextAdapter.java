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

package com.liferay.frontend.taglib.clay.view.state;

import com.liferay.frontend.taglib.clay.servlet.taglib.display.context.ManagementToolbarDisplayContext;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.CreationMenu;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.LabelItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.ViewTypeItem;

import java.util.List;
import java.util.Map;

import javax.portlet.RenderURL;

/**
 * @author Neil Griffin
 */
public class ManagementToolbarViewStateDisplayContextAdapter
	implements ManagementToolbarDisplayContext {

	public ManagementToolbarViewStateDisplayContextAdapter(
		ManagementToolbarViewState managementToolbarViewState) {

		_managementToolbarViewState = managementToolbarViewState;
	}

	@Override
	public List<DropdownItem> getActionDropdownItems() {
		return _managementToolbarViewState.getActionDropdownItems();
	}

	@Override
	public String getClearResultsURL() {
		RenderURL clearResultsURL =
			_managementToolbarViewState.getClearResultsURL();

		if (clearResultsURL == null) {
			return null;
		}

		return clearResultsURL.toString();
	}

	@Override
	public String getComponentId() {
		return _managementToolbarViewState.getComponentId();
	}

	@Override
	public String getContentRenderer() {
		return _managementToolbarViewState.getContentRenderer();
	}

	@Override
	public CreationMenu getCreationMenu() {
		return _managementToolbarViewState.getCreationMenu();
	}

	@Override
	public Map<String, String> getData() {
		return _managementToolbarViewState.getData();
	}

	@Override
	public String getDefaultEventHandler() {
		return _managementToolbarViewState.getDefaultEventHandler();
	}

	@Override
	public String getElementClasses() {
		return _managementToolbarViewState.getElementClasses();
	}

	@Override
	public List<DropdownItem> getFilterDropdownItems() {
		return _managementToolbarViewState.getFilterDropdownItems();
	}

	@Override
	public List<LabelItem> getFilterLabelItems() {
		return _managementToolbarViewState.getFilterLabelItems();
	}

	@Override
	public String getId() {
		return _managementToolbarViewState.getId();
	}

	@Override
	public String getInfoPanelId() {
		return _managementToolbarViewState.getInfoPanelId();
	}

	@Override
	public int getItemsTotal() {
		return _managementToolbarViewState.getItemsTotal();
	}

	@Override
	public String getNamespace() {
		return _managementToolbarViewState.getNamespace();
	}

	@Override
	public String getSearchActionURL() {
		RenderURL searchActionURL =
			_managementToolbarViewState.getSearchActionURL();

		if (searchActionURL == null) {
			return null;
		}

		return searchActionURL.toString();
	}

	@Override
	public String getSearchContainerId() {
		return _managementToolbarViewState.getSearchContainerId();
	}

	@Override
	public String getSearchFormMethod() {
		return _managementToolbarViewState.getSearchFormMethod();
	}

	@Override
	public String getSearchFormName() {
		return _managementToolbarViewState.getSearchFormName();
	}

	@Override
	public String getSearchInputName() {
		return _managementToolbarViewState.getSearchInputName();
	}

	@Override
	public String getSearchValue() {
		return _managementToolbarViewState.getSearchValue();
	}

	@Override
	public int getSelectedItems() {
		return _managementToolbarViewState.getSelectedItems();
	}

	@Override
	public String getSortingOrder() {
		return _managementToolbarViewState.getSortingOrder();
	}

	@Override
	public String getSortingURL() {
		RenderURL sortingURL = _managementToolbarViewState.getSortingURL();

		if (sortingURL == null) {
			return null;
		}

		return sortingURL.toString();
	}

	@Override
	public String getSpritemap() {
		return _managementToolbarViewState.getSpritemap();
	}

	@Override
	public Boolean getSupportsBulkActions() {
		return _managementToolbarViewState.isSupportsBulkActions();
	}

	@Override
	public List<ViewTypeItem> getViewTypeItems() {
		return _managementToolbarViewState.getViewTypeItems();
	}

	@Override
	public Boolean isDisabled() {
		return _managementToolbarViewState.isDisabled();
	}

	@Override
	public Boolean isSelectable() {
		return _managementToolbarViewState.isSelectable();
	}

	@Override
	public Boolean isShowAdvancedSearch() {
		return _managementToolbarViewState.isShowAdvancedSearch();
	}

	@Override
	public Boolean isShowCreationMenu() {
		return _managementToolbarViewState.isShowCreationMenu();
	}

	@Override
	public Boolean isShowFiltersDoneButton() {
		return _managementToolbarViewState.isShowFiltersDoneButton();
	}

	@Override
	public Boolean isShowInfoButton() {
		return _managementToolbarViewState.isShowInfoButton();
	}

	@Override
	public Boolean isShowSearch() {
		return _managementToolbarViewState.isShowSearch();
	}

	private final ManagementToolbarViewState _managementToolbarViewState;

}