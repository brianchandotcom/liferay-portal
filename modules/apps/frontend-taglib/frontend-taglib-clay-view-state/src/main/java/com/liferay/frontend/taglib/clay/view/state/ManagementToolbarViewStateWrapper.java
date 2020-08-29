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

import com.liferay.frontend.taglib.clay.servlet.taglib.util.CreationMenu;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.LabelItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.ViewTypeItem;

import java.util.List;
import java.util.Map;

import javax.portlet.RenderURL;

import org.osgi.annotation.versioning.ConsumerType;

/**
 * @author Brian Wing Shun Chan
 * @generated
 */
@ConsumerType
public abstract class ManagementToolbarViewStateWrapper
	implements ManagementToolbarViewState {

	public ManagementToolbarViewStateWrapper(
		ManagementToolbarViewState managementToolbarViewState) {

		_managementToolbarViewState = managementToolbarViewState;
	}

	@Override
	public List<DropdownItem> getActionDropdownItems() {
		return _managementToolbarViewState.getActionDropdownItems();
	}

	@Override
	public String getAddEntryMessage() {
		return _managementToolbarViewState.getAddEntryMessage();
	}

	@Override
	public RenderURL getAddEntryURL() {
		return _managementToolbarViewState.getAddEntryURL();
	}

	@Override
	public RenderURL getClearResultsURL() {
		return _managementToolbarViewState.getClearResultsURL();
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
	public String getDisplayStyle() {
		return _managementToolbarViewState.getDisplayStyle();
	}

	@Override
	public RenderURL getDisplayStyleURL() {
		return _managementToolbarViewState.getDisplayStyleURL();
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
	public RenderURL getSearchActionURL() {
		return _managementToolbarViewState.getSearchActionURL();
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
	public RenderURL getSearchURL() {
		return _managementToolbarViewState.getSearchURL();
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
	public RenderURL getSortingURL() {
		return _managementToolbarViewState.getSortingURL();
	}

	@Override
	public RenderURL getSortingURLCurrent() {
		return _managementToolbarViewState.getSortingURLCurrent();
	}

	@Override
	public RenderURL getSortingURLReverse() {
		return _managementToolbarViewState.getSortingURLReverse();
	}

	@Override
	public String getSpritemap() {
		return _managementToolbarViewState.getSpritemap();
	}

	@Override
	public List<ViewTypeItem> getViewTypeItems() {
		return _managementToolbarViewState.getViewTypeItems();
	}

	public ManagementToolbarViewState getWrapped() {
		return _managementToolbarViewState;
	}

	@Override
	public boolean isDisabled() {
		return _managementToolbarViewState.isDisabled();
	}

	@Override
	public boolean isSelectable() {
		return _managementToolbarViewState.isSelectable();
	}

	@Override
	public boolean isShowAdvancedSearch() {
		return _managementToolbarViewState.isShowAdvancedSearch();
	}

	@Override
	public boolean isShowCreationMenu() {
		return _managementToolbarViewState.isShowCreationMenu();
	}

	@Override
	public boolean isShowDisplayStyleCard() {
		return _managementToolbarViewState.isShowDisplayStyleCard();
	}

	@Override
	public boolean isShowDisplayStyleList() {
		return _managementToolbarViewState.isShowDisplayStyleList();
	}

	@Override
	public boolean isShowDisplayStyleTable() {
		return _managementToolbarViewState.isShowDisplayStyleTable();
	}

	@Override
	public boolean isShowFiltersDoneButton() {
		return _managementToolbarViewState.isShowFiltersDoneButton();
	}

	@Override
	public boolean isShowInfoButton() {
		return _managementToolbarViewState.isShowInfoButton();
	}

	@Override
	public boolean isShowSearch() {
		return _managementToolbarViewState.isShowSearch();
	}

	@Override
	public boolean isSupportsBulkActions() {
		return _managementToolbarViewState.isSupportsBulkActions();
	}

	private final ManagementToolbarViewState _managementToolbarViewState;

}