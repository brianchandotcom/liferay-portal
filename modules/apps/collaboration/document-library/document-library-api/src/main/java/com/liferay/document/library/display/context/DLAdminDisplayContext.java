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

package com.liferay.document.library.display.context;

import com.liferay.frontend.taglib.clay.servlet.taglib.util.CreationMenu;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.NavigationItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.ViewTypeItemList;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.repository.model.Folder;

import java.util.List;

import javax.portlet.PortletURL;

/**
 * @author Alejandro Tardín
 */
public interface DLAdminDisplayContext {

	public List<DropdownItem> getActionDropdownItems();

	public String getClearResultsURL();

	public CreationMenu getCreationMenu();

	public String getDisplayStyle();

	public List<DropdownItem> getFilterDropdownItems();

	public Folder getFolder();

	public long getFolderId();

	public List<NavigationItem> getNavigationItems();

	public String getOrderByCol();

	public String getOrderByType();

	public long getRepositoryId();

	public long getRootFolderId();

	public String getRootFolderName();

	public SearchContainer getSearchContainer();

	public PortletURL getSearchURL();

	public PortletURL getSortingURL();

	public int getTotalItems();

	public ViewTypeItemList getViewTypes();

	public boolean isDefaultFolderView();

	public boolean isDisabled();

	public boolean isSelectable();

	public boolean isShowSearch();

	public boolean isShowSearchInfo();

}