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
import com.liferay.petra.string.CharPool;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.SessionClicks;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.List;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Adolfo Pérez
 */
public class PanelCategoryTag extends BasePanelTag {

	public void setPanelCategory(PanelCategory panelCategory) {
		_panelCategory = panelCategory;
	}

	public void setPersistState(boolean persistState) {
		_persistState = persistState;
	}

	public void setShowBody(boolean showBody) {
		_showBody = showBody;
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
		_persistState = false;
		_showBody = true;
		_showHeader = true;
		_showOpen = false;
	}

	@Override
	protected String getEndPage() {
		return "/panel_category/end.jsp";
	}

	protected String getId() {
		String id = StringUtil.replace(
			_panelCategory.getKey(), CharPool.PERIOD, CharPool.UNDERLINE);

		return "panel-manage-" + id;
	}

	@Override
	protected String getStartPage() {
		return "/panel_category/start.jsp";
	}

	protected boolean isActive(
		List<PanelApp> panelApps, PanelCategoryHelper panelCategoryHelper,
		Group group) {

		if (_showOpen) {
			return true;
		}

		if (isPersistState()) {
			String state = SessionClicks.get(
				request, PanelCategory.class.getName() + getId(), "closed");

			if (Objects.equals(state, "open")) {
				return true;
			}
		}

		if (panelApps.isEmpty()) {
			return false;
		}

		return _panelCategory.isActive(request, panelCategoryHelper, group);
	}

	protected boolean isPersistState() {
		if (_persistState || _panelCategory.isPersistState()) {
			return true;
		}

		return false;
	}

	@Override
	protected void setAttributes(HttpServletRequest request) {
		PanelAppRegistry panelAppRegistry =
			(PanelAppRegistry)request.getAttribute(
				ApplicationListWebKeys.PANEL_APP_REGISTRY);

		PanelCategoryRegistry panelCategoryRegistry =
			(PanelCategoryRegistry)request.getAttribute(
				ApplicationListWebKeys.PANEL_CATEGORY_REGISTRY);

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		Group group = getGroup();

		List<PanelApp> panelApps = panelAppRegistry.getPanelApps(
			_panelCategory, themeDisplay.getPermissionChecker(), group);

		PanelCategoryHelper panelCategoryHelper = new PanelCategoryHelper(
			panelAppRegistry, panelCategoryRegistry);

		request.setAttribute(
			"liferay-application-list:panel-category:active",
			isActive(panelApps, panelCategoryHelper, group));

		request.setAttribute(
			"liferay-application-list:panel-category:id", getId());

		int notificationsCount = panelCategoryHelper.getNotificationsCount(
			_panelCategory.getKey(), themeDisplay.getPermissionChecker(), group,
			themeDisplay.getUser());

		String portletId = themeDisplay.getPpid();

		if (Validator.isNotNull(portletId) &&
			panelCategoryHelper.containsPortlet(
				portletId, _panelCategory.getKey())) {

			request.setAttribute(
				"liferay-application-list:panel-category:headerActive",
				Boolean.TRUE);
		}
		else {
			request.setAttribute(
				"liferay-application-list:panel-category:headerActive",
				Boolean.FALSE);
		}

		request.setAttribute(
			"liferay-application-list:panel-category:notificationsCount",
			notificationsCount);

		request.setAttribute(
			"liferay-application-list:panel-category:panelApps", panelApps);
		request.setAttribute(
			"liferay-application-list:panel-category:panelCategory",
			_panelCategory);
		request.setAttribute(
			"liferay-application-list:panel-category:persistState",
			isPersistState());
		request.setAttribute(
			"liferay-application-list:panel-category:showBody", _showBody);
		request.setAttribute(
			"liferay-application-list:panel-category:showHeader", _showHeader);
		request.setAttribute(
			"liferay-application-list:panel-category:showOpen", _showOpen);
	}

	private PanelCategory _panelCategory;
	private boolean _persistState;
	private boolean _showBody = true;
	private boolean _showHeader = true;
	private boolean _showOpen;

}