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

package com.liferay.users.admin.web.internal.display.context;

import com.liferay.frontend.taglib.clay.servlet.taglib.display.context.ManagementToolbarDisplayContext;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.model.User;

/**
 * @author Pei-Jung Lan
 */
public class ViewFlatUsersDisplayContext {

	public String getDisplayStyle() {
		return _displayStyle;
	}

	public ManagementToolbarDisplayContext
		getManagementToolbarDisplayContext() {

		return _managementToolbarDisplayContext;
	}

	public SearchContainer<User> getSearchContainer() {
		return _searchContainer;
	}

	public int getStatus() {
		return _status;
	}

	public String getToolbarItem() {
		return _toolbarItem;
	}

	public String getUsersListView() {
		return _usersListView;
	}

	public String getViewUsersRedirect() {
		return _viewUsersRedirect;
	}

	public void setDisplayStyle(String displayStyle) {
		_displayStyle = displayStyle;
	}

	public void setManagementToolbarDisplayContext(
		ManagementToolbarDisplayContext managementToolbarDisplayContext) {

		_managementToolbarDisplayContext = managementToolbarDisplayContext;
	}

	public void setSearchContainer(SearchContainer<User> searchContainer) {
		_searchContainer = searchContainer;
	}

	public void setStatus(int status) {
		_status = status;
	}

	public void setToolbarItem(String toolbarItem) {
		_toolbarItem = toolbarItem;
	}

	public void setUsersListView(String usersListView) {
		_usersListView = usersListView;
	}

	public void setViewUsersRedirect(String viewUsersRedirect) {
		_viewUsersRedirect = viewUsersRedirect;
	}

	private String _displayStyle;
	private ManagementToolbarDisplayContext _managementToolbarDisplayContext;
	private SearchContainer<User> _searchContainer;
	private int _status;
	private String _toolbarItem;
	private String _usersListView;
	private String _viewUsersRedirect;

}