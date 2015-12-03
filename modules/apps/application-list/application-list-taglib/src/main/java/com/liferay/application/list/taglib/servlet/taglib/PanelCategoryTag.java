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

import com.liferay.application.list.PanelApp;
import com.liferay.application.list.PanelAppRegistry;
import com.liferay.application.list.PanelCategory;
import com.liferay.application.list.PanelCategoryRegistry;
import com.liferay.application.list.constants.ApplicationListWebKeys;
import com.liferay.application.list.display.context.logic.PanelCategoryHelper;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.theme.ThemeDisplay;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Adolfo Pérez
 */
public class PanelCategoryTag extends BasePanelTag {

	public void setPanelCategory(PanelCategory panelCategory) {
		_panelCategory = panelCategory;
	}

	public void setShowHeader(boolean showHeader) {
		_showHeader = showHeader;
	}

	public void setShowOpen(boolean showOpen) {
		_showOpen = showOpen;
	}

	@Override
	protected void cleanUp() {
		super.cleanUp();

		_panelCategory = null;
		_showHeader = true;
		_showOpen = false;
	}

	@Override
	protected String getEndPage() {
		return "/panel_category/end.jsp";
	}

	@Override
	protected String getStartPage() {
		return "/panel_category/start.jsp";
	}

	@Override
	protected void setAttributes(HttpServletRequest request) {
		boolean active = _showOpen;

		PanelAppRegistry panelAppRegistry =
			(PanelAppRegistry)request.getAttribute(
				ApplicationListWebKeys.PANEL_APP_REGISTRY);

		PanelCategoryRegistry panelCategoryRegistry =
			(PanelCategoryRegistry)request.getAttribute(
				ApplicationListWebKeys.PANEL_CATEGORY_REGISTRY);

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		List<PanelApp> panelApps = panelAppRegistry.getPanelApps(
			_panelCategory, themeDisplay.getPermissionChecker(),
			themeDisplay.getScopeGroup());

		PanelCategoryHelper panelCategoryHelper = new PanelCategoryHelper(
			panelAppRegistry, panelCategoryRegistry);

		if (!_showOpen && !panelApps.isEmpty()) {
			active = panelCategoryHelper.containsPortlet(
				themeDisplay.getPpid(), _panelCategory);
		}

		request.setAttribute(
			"liferay-application-list:panel-category:active", active);

		String id = StringUtil.replace(
			_panelCategory.getKey(), StringPool.PERIOD, StringPool.UNDERLINE);

		id = "panel-manage-" + id;

		request.setAttribute("liferay-application-list:panel-category:id", id);

		int notificationsCount = panelCategoryHelper.getNotificationsCount(
			_panelCategory.getKey(), themeDisplay.getPermissionChecker(),
			themeDisplay.getScopeGroup(), themeDisplay.getUser());

		request.setAttribute(
			"liferay-application-list:panel-category:notificationsCount",
			notificationsCount);

		request.setAttribute(
			"liferay-application-list:panel-category:panelApps", panelApps);
		request.setAttribute(
			"liferay-application-list:panel-category:panelCategory",
			_panelCategory);
		request.setAttribute(
			"liferay-application-list:panel-category:showHeader", _showHeader);
		request.setAttribute(
			"liferay-application-list:panel-category:showOpen", _showOpen);
	}

	private PanelCategory _panelCategory;
	private boolean _showHeader = true;
	private boolean _showOpen;

}