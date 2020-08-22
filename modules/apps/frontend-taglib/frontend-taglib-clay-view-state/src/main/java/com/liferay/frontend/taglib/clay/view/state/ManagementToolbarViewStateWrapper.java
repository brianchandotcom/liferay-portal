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

		_wrappedManagementToolbarViewState = managementToolbarViewState;
	}

	@Override
	public List<DropdownItem> getActionDropdownItems() {
		return _wrappedManagementToolbarViewState.getActionDropdownItems();
	}

	@Override
	public String getAddEntryMessage() {
		return _wrappedManagementToolbarViewState.getAddEntryMessage();
	}

	@Override
	public RenderURL getAddEntryURL() {
		return _wrappedManagementToolbarViewState.getAddEntryURL();
	}

	@Override
	public RenderURL getClearResultsURL() {
		return _wrappedManagementToolbarViewState.getClearResultsURL();
	}

	@Override
	public String getComponentId() {
		return _wrappedManagementToolbarViewState.getComponentId();
	}

	@Override
	public String getContentRenderer() {
		return _wrappedManagementToolbarViewState.getContentRenderer();
	}

	@Override
	public CreationMenu getCreationMenu() {
		return _wrappedManagementToolbarViewState.getCreationMenu();
	}

	@Override
	public Map<String, String> getData() {
		return _wrappedManagementToolbarViewState.getData();
	}

	@Override
	public String getDefaultEventHandler() {
		return _wrappedManagementToolbarViewState.getDefaultEventHandler();
	}

	@Override
	public String getDisplayStyle() {
		return _wrappedManagementToolbarViewState.getDisplayStyle();
	}

	@Override
	public RenderURL getDisplayStyleURL() {
		return _wrappedManagementToolbarViewState.getDisplayStyleURL();
	}

	@Override
	public String getElementClasses() {
		return _wrappedManagementToolbarViewState.getElementClasses();
	}

	@Override
	public List<DropdownItem> getFilterDropdownItems() {
		return _wrappedManagementToolbarViewState.getFilterDropdownItems();
	}

	@Override
	public List<LabelItem> getFilterLabelItems() {
		return _wrappedManagementToolbarViewState.getFilterLabelItems();
	}

	@Override
	public String getId() {
		return _wrappedManagementToolbarViewState.getId();
	}

	@Override
	public String getInfoPanelId() {
		return _wrappedManagementToolbarViewState.getInfoPanelId();
	}

	@Override
	public int getItemsTotal() {
		return _wrappedManagementToolbarViewState.getItemsTotal();
	}

	@Override
	public String getNamespace() {
		return _wrappedManagementToolbarViewState.getNamespace();
	}

	@Override
	public RenderURL getSearchActionURL() {
		return _wrappedManagementToolbarViewState.getSearchActionURL();
	}

	@Override
	public String getSearchContainerId() {
		return _wrappedManagementToolbarViewState.getSearchContainerId();
	}

	@Override
	public String getSearchFormMethod() {
		return _wrappedManagementToolbarViewState.getSearchFormMethod();
	}

	@Override
	public String getSearchFormName() {
		return _wrappedManagementToolbarViewState.getSearchFormName();
	}

	@Override
	public String getSearchInputName() {
		return _wrappedManagementToolbarViewState.getSearchInputName();
	}

	@Override
	public RenderURL getSearchURL() {
		return _wrappedManagementToolbarViewState.getSearchURL();
	}

	@Override
	public String getSearchValue() {
		return _wrappedManagementToolbarViewState.getSearchValue();
	}

	@Override
	public int getSelectedItems() {
		return _wrappedManagementToolbarViewState.getSelectedItems();
	}

	@Override
	public String getSortingOrder() {
		return _wrappedManagementToolbarViewState.getSortingOrder();
	}

	@Override
	public RenderURL getSortingURL() {
		return _wrappedManagementToolbarViewState.getSortingURL();
	}

	@Override
	public RenderURL getSortingURLCurrent() {
		return _wrappedManagementToolbarViewState.getSortingURLCurrent();
	}

	@Override
	public RenderURL getSortingURLReverse() {
		return _wrappedManagementToolbarViewState.getSortingURLReverse();
	}

	@Override
	public String getSpritemap() {
		return _wrappedManagementToolbarViewState.getSpritemap();
	}

	@Override
	public List<ViewTypeItem> getViewTypeItems() {
		return _wrappedManagementToolbarViewState.getViewTypeItems();
	}

	public ManagementToolbarViewState getWrapped() {
		return _wrappedManagementToolbarViewState;
	}

	@Override
	public boolean isDisabled() {
		return _wrappedManagementToolbarViewState.isDisabled();
	}

	@Override
	public boolean isSelectable() {
		return _wrappedManagementToolbarViewState.isSelectable();
	}

	@Override
	public boolean isShowAdvancedSearch() {
		return _wrappedManagementToolbarViewState.isShowAdvancedSearch();
	}

	@Override
	public boolean isShowCreationMenu() {
		return _wrappedManagementToolbarViewState.isShowCreationMenu();
	}

	@Override
	public boolean isShowDisplayStyleCard() {
		return _wrappedManagementToolbarViewState.isShowDisplayStyleCard();
	}

	@Override
	public boolean isShowDisplayStyleList() {
		return _wrappedManagementToolbarViewState.isShowDisplayStyleList();
	}

	@Override
	public boolean isShowDisplayStyleTable() {
		return _wrappedManagementToolbarViewState.isShowDisplayStyleTable();
	}

	@Override
	public boolean isShowFiltersDoneButton() {
		return _wrappedManagementToolbarViewState.isShowFiltersDoneButton();
	}

	@Override
	public boolean isShowInfoButton() {
		return _wrappedManagementToolbarViewState.isShowInfoButton();
	}

	@Override
	public boolean isShowSearch() {
		return _wrappedManagementToolbarViewState.isShowSearch();
	}

	@Override
	public boolean isSupportsBulkActions() {
		return _wrappedManagementToolbarViewState.isSupportsBulkActions();
	}

	private final ManagementToolbarViewState _wrappedManagementToolbarViewState;

}