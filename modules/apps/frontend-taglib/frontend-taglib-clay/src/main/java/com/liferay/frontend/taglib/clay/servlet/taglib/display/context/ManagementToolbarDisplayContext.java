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

package com.liferay.frontend.taglib.clay.servlet.taglib.display.context;

import com.liferay.frontend.taglib.clay.internal.servlet.taglib.display.context.ManagementToolbarDefaults;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.CreationMenu;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.LabelItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.ViewTypeItem;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.List;
import java.util.Map;

/**
 * @author Drew Brokke
 */
public interface ManagementToolbarDisplayContext {

	public default List<DropdownItem> getActionDropdownItems() {
		return null;
	}

	public default String getClearResultsURL() {
		return null;
	}

	public default String getComponentId() {
		return null;
	}

	public default String getContentRenderer() {
		return ManagementToolbarDefaults.getContentRenderer();
	}

	public default CreationMenu getCreationMenu() {
		return null;
	}

	public default Map<String, String> getData() {
		return null;
	}

	public default String getDefaultEventHandler() {
		return null;
	}

	public default String getElementClasses() {
		return null;
	}

	public default List<DropdownItem> getFilterDropdownItems() {
		return null;
	}

	public default List<LabelItem> getFilterLabelItems() {
		return null;
	}

	public default String getId() {
		return null;
	}

	public default String getInfoPanelId() {
		return null;
	}

	public default int getItemsTotal() {
		return 0;
	}

	public default String getNamespace() {
		return null;
	}

	public default String getSearchActionURL() {
		return null;
	}

	public default String getSearchContainerId() {
		return null;
	}

	public default String getSearchFormMethod() {
		return ManagementToolbarDefaults.getSearchFormMethod();
	}

	public default String getSearchFormName() {
		return null;
	}

	public default String getSearchInputName() {
		return ManagementToolbarDefaults.getSearchInputName();
	}

	public default String getSearchValue() {
		return null;
	}

	public default int getSelectedItems() {
		return 0;
	}

	public default String getSortingOrder() {
		return "asc";
	}

	public default String getSortingURL() {
		return null;
	}

	public default String getSpritemap() {
		return null;
	}

	public default Boolean getSupportsBulkActions() {
		return false;
	}

	public default List<ViewTypeItem> getViewTypeItems() {
		return null;
	}

	public default Boolean isDisabled() {
		return false;
	}

	public default Boolean isSelectable() {
		return true;
	}

	public default Boolean isShowAdvancedSearch() {
		return GetterUtil.DEFAULT_BOOLEAN;
	}

	public default Boolean isShowCreationMenu() {
		return ManagementToolbarDefaults.isShowCreationMenu(getCreationMenu());
	}

	public default Boolean isShowFiltersDoneButton() {
		return GetterUtil.DEFAULT_BOOLEAN;
	}

	public default Boolean isShowInfoButton() {
		return ManagementToolbarDefaults.isShowInfoButton(getInfoPanelId());
	}

	public default Boolean isShowSearch() {
		return Validator.isNotNull(getSearchActionURL());
	}

}