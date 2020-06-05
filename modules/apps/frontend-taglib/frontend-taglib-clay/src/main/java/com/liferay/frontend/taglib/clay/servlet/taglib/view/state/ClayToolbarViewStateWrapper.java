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

package com.liferay.frontend.taglib.clay.servlet.taglib.view.state;

import com.liferay.frontend.taglib.clay.servlet.taglib.util.CreationMenu;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.LabelItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.ViewTypeItem;

import java.util.List;
import java.util.Map;

import javax.portlet.RenderURL;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author  Neil Griffin
 */
@ProviderType
public abstract class ClayToolbarViewStateWrapper
	implements ClayToolbarViewState {

	public ClayToolbarViewStateWrapper(
		ClayToolbarViewState clayToolbarViewState) {

		_wrapped = clayToolbarViewState;
	}

	@Override
	public List<DropdownItem> getActionDropdownItems() {
		return _wrapped.getActionDropdownItems();
	}

	@Override
	public String getAddEntryMessage() {
		return _wrapped.getAddEntryMessage();
	}

	@Override
	public RenderURL getAddEntryURL() {
		return _wrapped.getAddEntryURL();
	}

	@Override
	public String getClearResultsURL() {
		return _wrapped.getClearResultsURL();
	}

	@Override
	public String getComponentId() {
		return _wrapped.getComponentId();
	}

	@Override
	public String getContentRenderer() {
		return _wrapped.getContentRenderer();
	}

	@Override
	public CreationMenu getCreationMenu() {
		return _wrapped.getCreationMenu();
	}

	@Override
	public Map<String, String> getData() {
		return _wrapped.getData();
	}

	@Override
	public String getDefaultEventHandler() {
		return _wrapped.getDefaultEventHandler();
	}

	@Override
	public String getDisplayStyle() {
		return _wrapped.getDisplayStyle();
	}

	@Override
	public RenderURL getDisplayStyleURL() {
		return _wrapped.getDisplayStyleURL();
	}

	@Override
	public String getElementClasses() {
		return _wrapped.getElementClasses();
	}

	@Override
	public List<DropdownItem> getFilterDropdownItems() {
		return _wrapped.getFilterDropdownItems();
	}

	@Override
	public List<LabelItem> getFilterLabelItems() {
		return _wrapped.getFilterLabelItems();
	}

	@Override
	public String getId() {
		return _wrapped.getId();
	}

	@Override
	public String getInfoPanelId() {
		return _wrapped.getInfoPanelId();
	}

	@Override
	public int getItemsTotal() {
		return _wrapped.getItemsTotal();
	}

	@Override
	public String getNamespace() {
		return _wrapped.getNamespace();
	}

	@Override
	public String getSearchActionURL() {
		return _wrapped.getSearchActionURL();
	}

	@Override
	public String getSearchContainerId() {
		return _wrapped.getSearchContainerId();
	}

	@Override
	public String getSearchFormMethod() {
		return _wrapped.getSearchFormMethod();
	}

	@Override
	public String getSearchFormName() {
		return _wrapped.getSearchFormName();
	}

	@Override
	public String getSearchInputName() {
		return _wrapped.getSearchInputName();
	}

	@Override
	public RenderURL getSearchURL() {
		return _wrapped.getSearchURL();
	}

	@Override
	public String getSearchValue() {
		return _wrapped.getSearchValue();
	}

	@Override
	public int getSelectedItems() {
		return _wrapped.getSelectedItems();
	}

	@Override
	public String getSortingOrder() {
		return _wrapped.getSortingOrder();
	}

	@Override
	public String getSortingURL() {
		return _wrapped.getSortingURL();
	}

	@Override
	public RenderURL getSortingURLCurrent() {
		return _wrapped.getSortingURLCurrent();
	}

	@Override
	public RenderURL getSortingURLReverse() {
		return _wrapped.getSortingURLReverse();
	}

	@Override
	public String getSpritemap() {
		return _wrapped.getSpritemap();
	}

	@Override
	public Boolean getSupportsBulkActions() {
		return _wrapped.getSupportsBulkActions();
	}

	@Override
	public List<ViewTypeItem> getViewTypeItems() {
		return _wrapped.getViewTypeItems();
	}

	public ClayToolbarViewState getWrapped() {
		return _wrapped;
	}

	@Override
	public Boolean isDisabled() {
		return _wrapped.isDisabled();
	}

	@Override
	public Boolean isSelectable() {
		return _wrapped.isSelectable();
	}

	@Override
	public Boolean isShowAdvancedSearch() {
		return _wrapped.isShowAdvancedSearch();
	}

	@Override
	public Boolean isShowCreationMenu() {
		return _wrapped.isShowCreationMenu();
	}

	@Override
	public boolean isShowDisplayStyleCard() {
		return _wrapped.isShowDisplayStyleCard();
	}

	@Override
	public boolean isShowDisplayStyleList() {
		return _wrapped.isShowDisplayStyleList();
	}

	@Override
	public boolean isShowDisplayStyleTable() {
		return _wrapped.isShowDisplayStyleTable();
	}

	@Override
	public Boolean isShowFiltersDoneButton() {
		return _wrapped.isShowFiltersDoneButton();
	}

	@Override
	public Boolean isShowInfoButton() {
		return _wrapped.isShowInfoButton();
	}

	@Override
	public Boolean isShowSearch() {
		return _wrapped.isShowSearch();
	}

	private final ClayToolbarViewState _wrapped;

}