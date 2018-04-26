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
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.repository.model.Folder;

import java.util.List;

import javax.portlet.PortletURL;

/**
 * @author Alejandro Tardín
 */
public interface DLAdminDisplayContext {

	public List<DropdownItem> getActionDropdownItems();

	public String getClearResultsURL() throws PortalException;

	public CreationMenu getCreationMenu();

	public String getDisplayStyle();

	public List<DropdownItem> getFilterDropdownItems();

	public Folder getFolder() throws PortalException;

	public long getFolderId() throws PortalException;

	public List<NavigationItem> getNavigationItems();

	public String getOrderByCol();

	public String getOrderByType();

	public PortletURL getPortletURL() throws PortalException;

	public long getRepositoryId() throws PortalException;

	public SearchContainer getSearchContainer() throws PortalException;

	public PortletURL getSearchURL() throws PortalException;

	public PortletURL getSortingURL() throws PortalException;

	public int getTotalItems() throws PortalException;

	public ViewTypeItemList getViewTypes() throws PortalException;

	public boolean isDefaultFolderView();

}