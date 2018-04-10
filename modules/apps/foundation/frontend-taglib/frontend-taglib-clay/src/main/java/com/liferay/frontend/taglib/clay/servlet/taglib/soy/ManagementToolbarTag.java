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
import com.liferay.frontend.taglib.clay.servlet.taglib.util.CreationMenu;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemList;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.ViewTypeItemList;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.search.DisplayTerms;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.Map;

/**
 * @author Carlos Lancha
 */
public class ManagementToolbarTag extends BaseClayTag {

	public ManagementToolbarTag() {
		super("management-toolbar", "ClayManagementToolbar", true);
	}

	@Override
	public int doStartTag() {
		Map<String, Object> context = getContext();

		String searchInputName = (String)context.get("searchInputName");

		if (Validator.isNull(searchInputName)) {
			searchInputName = DisplayTerms.KEYWORDS;

			setSearchInputName(searchInputName);
		}

		String searchValue = (String)context.get("searchValue");

		if (Validator.isNull(searchValue) &&
			Validator.isNotNull(searchInputName)) {

			setSearchValue(ParamUtil.getString(request, searchInputName));
		}

		Boolean selectable = (Boolean)context.get("selectable");

		if (selectable == null) {
			setSelectable(true);
		}

		Boolean showFiltersDoneButton = (Boolean)context.get(
			"showFiltersDoneButton");

		if (showFiltersDoneButton == null) {
			setShowFiltersDoneButton(false);
		}

		return super.doStartTag();
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

	public void setActionItems(DropdownItemList actionItems) {
		putValue("actionItems", actionItems);
	}

	public void setClearResultsURL(String clearResultsURL) {
		putValue("clearResultsURL", clearResultsURL);
	}

	public void setContentRenderer(String contentRenderer) {
		putValue("contentRenderer", contentRenderer);
	}

	public void setCreationMenu(CreationMenu creationMenu) {
		putValue("creationMenu", creationMenu);
	}

	public void setDisabled(Boolean disabled) {
		putValue("disabled", disabled);
	}

	public void setFilterItems(DropdownItemList filterItems) {
		putValue("filterItems", filterItems);
	}

	public void setInfoPanelId(String infoPanelId) {
		putValue("infoPanelId", infoPanelId);
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

	public void setSearchValue(String searchValue) {
		putValue("searchValue", searchValue);
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

	public void setShowCreationMenu(Boolean showCreationMenu) {
		putValue("showCreationMenu", showCreationMenu);
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

	public void setSortingURL(String sortingURL) {
		putValue("sortingURL", sortingURL);
	}

	public void setTotalItems(int totalItems) {
		putValue("totalItems", totalItems);
	}

	public void setViewTypes(ViewTypeItemList viewTypes) {
		putValue("viewTypes", viewTypes);
	}

	@Override
	protected String[] getNamespacedParams() {
		return _NAMESPACED_PARAMS;
	}

	private static final String[] _NAMESPACED_PARAMS = {
		"infoPanelId", "searchContainerId", "searchFormName", "searchInputName"
	};

}