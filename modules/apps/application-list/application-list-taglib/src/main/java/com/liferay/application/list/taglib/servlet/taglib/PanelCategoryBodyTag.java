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
import com.liferay.application.list.constants.ApplicationListWebKeys;
import com.liferay.petra.string.CharPool;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Julio Camarero
 */
public class PanelCategoryBodyTag extends BasePanelTag {

	public PanelCategory getPanelCategory() {
		return _panelCategory;
	}

	public void setPanelApps(List<PanelApp> panelApps) {
		_panelApps = panelApps;
	}

	public void setPanelCategory(PanelCategory panelCategory) {
		_panelCategory = panelCategory;
	}

	@Override
	protected void cleanUp() {
		super.cleanUp();

		_panelApps = null;
		_panelCategory = null;
	}

	@Override
	protected String getPage() {
		return "/panel_category_body/page.jsp";
	}

	protected List<PanelApp> getPanelApps() {
		PanelAppRegistry panelAppRegistry =
			(PanelAppRegistry)request.getAttribute(
				ApplicationListWebKeys.PANEL_APP_REGISTRY);

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		return panelAppRegistry.getPanelApps(
			_panelCategory, themeDisplay.getPermissionChecker(), getGroup());
	}

	@Override
	protected void setAttributes(HttpServletRequest httpServletRequest) {
		String id = StringUtil.replace(
			_panelCategory.getKey(), CharPool.PERIOD, CharPool.UNDERLINE);

		id = "panel-manage-" + id;

		httpServletRequest.setAttribute(
			"liferay-application-list:panel-category-body:id", id);

		List<PanelApp> panelApps = _panelApps;

		if (panelApps == null) {
			panelApps = getPanelApps();
		}

		httpServletRequest.setAttribute(
			"liferay-application-list:panel-category-body:panelApps",
			panelApps);

		httpServletRequest.setAttribute(
			"liferay-application-list:panel-category-body:panelCategory",
			_panelCategory);
	}

	private List<PanelApp> _panelApps;
	private PanelCategory _panelCategory;

}