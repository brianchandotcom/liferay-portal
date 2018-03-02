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

package com.liferay.frontend.taglib.clay.servlet.taglib.soy;

import com.liferay.frontend.js.loader.modules.extender.npm.NPMResolver;
import com.liferay.frontend.taglib.clay.internal.js.loader.modules.extender.npm.NPMResolverProvider;
import com.liferay.frontend.taglib.clay.servlet.taglib.soy.base.BaseClayTag;
import com.liferay.petra.string.StringPool;

/**
 * @author Carlos Lancha
 */
public class ManagementToolbarTag extends BaseClayTag {

	public ManagementToolbarTag() {
		super("management-toolbar", "ClayManagementToolbar", true);
	}

	@Override
	public String getModule() {
		NPMResolver npmResolver = NPMResolverProvider.getNPMResolver();

		if (npmResolver == null) {
			return StringPool.BLANK;
		}

		return npmResolver.resolveModuleName(
			"frontend-taglib-clay/management_toolbar/ManagementToolbar.es");
	}

	public void setActionItems(Object actionItems) {
		putValue("actionItems", actionItems);
	}

	public void setContentRenderer(String contentRenderer) {
		putValue("contentRenderer", contentRenderer);
	}

	public void setCreationMenu(Object creationMenu) {
		putValue("creationMenu", creationMenu);
	}

	public void setFilterItems(Object filterItems) {
		putValue("filterItems", filterItems);
	}

	public void setSearchActionURL(String searchActionURL) {
		putValue("searchActionURL", searchActionURL);
	}

	public void setSearchContainerId(String searchContainerId) {
		putValue("searchContainerId", searchContainerId);
	}

	public void setSearchFormName(String searchFormName) {
		putValue("searchFormName", searchFormName);
	}

	public void setSearchInputName(String searchInputName) {
		putValue("searchInputName", searchInputName);
	}

	public void setSelectable(Boolean selectable) {
		putValue("selectable", selectable);
	}

	public void setSelectedItems(int selectedItems) {
		putValue("selectedItems", selectedItems);
	}

	public void setShowAdvancedSearch(Boolean showAdvancedSearch) {
		putValue("showAdvancedSearch", showAdvancedSearch);
	}

	public void setShowFiltersDoneButton(Boolean showFiltersDoneButton) {
		putValue("showFiltersDoneButton", showFiltersDoneButton);
	}

	public void setShowInfoButton(Boolean showInfoButton) {
		putValue("showInfoButton", showInfoButton);
	}

	public void setShowSearch(Boolean showSearch) {
		putValue("showSearch", showSearch);
	}

	public void setSortingOrder(String sortingOrder) {
		putValue("sortingOrder", sortingOrder);
	}

	public void setTotalItems(int totalItems) {
		putValue("totalItems", totalItems);
	}

	public void setViewTypes(Object viewTypes) {
		putValue("viewTypes", viewTypes);
	}

}