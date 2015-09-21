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

package com.liferay.application.list.taglib.servlet.taglib;

import com.liferay.application.list.PanelCategory;
import com.liferay.application.list.PanelCategoryRegistry;
import com.liferay.application.list.RootPanelCategory;
import com.liferay.application.list.constants.ApplicationListWebKeys;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.theme.ThemeDisplay;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Adolfo Pérez
 */
public class PanelTag extends BasePanelTag {

	public void setPanelCategory(PanelCategory panelCategory) {
		_panelCategory = panelCategory;
	}

	@Override
	protected void cleanUp() {
		super.cleanUp();

		_panelCategory = null;
	}

	@Override
	protected String getPage() {
		return "/panel/page.jsp";
	}

	@Override
	protected void setAttributes(HttpServletRequest request) {
		if (_panelCategory == null) {
			_panelCategory = RootPanelCategory.getInstance();
		}

		PanelCategoryRegistry panelCategoryRegistry =
			(PanelCategoryRegistry)request.getAttribute(
				ApplicationListWebKeys.PANEL_CATEGORY_REGISTRY);

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		List<PanelCategory> childPanelCategories =
			panelCategoryRegistry.getChildPanelCategories(
				_panelCategory, themeDisplay.getPermissionChecker(),
				themeDisplay.getScopeGroup());

		request.setAttribute(
			"liferay-application-list:panel:childPanelCategories",
			childPanelCategories);

		request.setAttribute(
			"liferay-application-list:panel:panelCategory", _panelCategory);
	}

	private PanelCategory _panelCategory;

}