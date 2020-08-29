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

import javax.portlet.RenderURL;
import java.util.List;
import java.util.Map;

/**
 * @author Brian Wing Shun Chan
 * @generated
 */
public interface ManagementToolbarViewState {

	public List<DropdownItem> getActionDropdownItems();

	public String getAddEntryMessage();

	public RenderURL getAddEntryURL();

	public RenderURL getClearResultsURL();

	public String getComponentId();

	public String getContentRenderer();

	public CreationMenu getCreationMenu();

	public Map<String, String> getData();

	public String getDefaultEventHandler();

	public String getDisplayStyle();

	public RenderURL getDisplayStyleURL();

	public String getElementClasses();

	public List<DropdownItem> getFilterDropdownItems();

	public List<LabelItem> getFilterLabelItems();

	public String getId();

	public String getInfoPanelId();

	public int getItemsTotal();

	public String getNamespace();

	public RenderURL getSearchActionURL();

	public String getSearchContainerId();

	public String getSearchFormMethod();

	public String getSearchFormName();

	public String getSearchInputName();

	public RenderURL getSearchURL();

	public String getSearchValue();

	public int getSelectedItems();

	public String getSortingOrder();

	public RenderURL getSortingURL();

	public RenderURL getSortingURLCurrent();

	public RenderURL getSortingURLReverse();

	public String getSpritemap();

	public List<ViewTypeItem> getViewTypeItems();

	public boolean isDisabled();

	public boolean isSelectable();

	public boolean isShowAdvancedSearch();

	public boolean isShowCreationMenu();

	public boolean isShowDisplayStyleCard();

	public boolean isShowDisplayStyleList();

	public boolean isShowDisplayStyleTable();

	public boolean isShowFiltersDoneButton();

	public boolean isShowInfoButton();

	public boolean isShowSearch();

	public boolean isSupportsBulkActions();

}