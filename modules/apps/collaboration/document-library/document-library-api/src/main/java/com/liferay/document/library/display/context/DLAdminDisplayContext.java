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

import java.util.List;

import javax.portlet.PortletURL;

/**
 * @author Alejandro Tardín
 */
public interface DLAdminDisplayContext {

	public String getClearResultsURL() throws Exception;

	public CreationMenu getCreationMenu();

	public List<DropdownItem> getFilterDropdownItems();

	public List<NavigationItem> getNavigationItems();

	public PortletURL getPortletURL() throws Exception;

	public SearchContainer getSearchContainer() throws Exception;

	public PortletURL getSearchURL();

	public int getTotalItems() throws Exception;

	public ViewTypeItemList getViewTypes() throws Exception;

}