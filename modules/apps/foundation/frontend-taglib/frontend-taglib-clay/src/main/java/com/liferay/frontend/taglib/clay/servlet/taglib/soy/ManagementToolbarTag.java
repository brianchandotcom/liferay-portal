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

import com.liferay.frontend.taglib.clay.servlet.taglib.soy.base.BaseClayTag;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

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

		if (Validator.isNull(context.get("spritemap"))) {
			ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
				WebKeys.THEME_DISPLAY);

			putValue(
				"spritemap",
				themeDisplay.getPathThemeImages().concat("/clay/icons.svg"));
		}

		return super.doStartTag();
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

	public void setElementClasses(String elementClasses) {
		putValue("elementClasses", elementClasses);
	}

	public void setFilterItems(Object filterItems) {
		putValue("filterItems", filterItems);
	}

	public void setHideFiltersDoneButton(Boolean hideFiltersDoneButton) {
		putValue("hideFiltersDoneButton", hideFiltersDoneButton);
	}

	public void setId(String id) {
		putValue("id", id);
	}

	public void setSearchActionURL(String searchActionURL) {
		putValue("searchActionURL", searchActionURL);
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

	public void setSortingOrder(String sortingOrder) {
		putValue("sortingOrder", sortingOrder);
	}

	public void setSpritemap(String spritemap) {
		putValue("spritemap", spritemap);
	}

	public void setTotalItems(int totalItems) {
		putValue("totalItems", totalItems);
	}

	public void setViewTypes(Object viewTypes) {
		putValue("viewTypes", viewTypes);
	}

}