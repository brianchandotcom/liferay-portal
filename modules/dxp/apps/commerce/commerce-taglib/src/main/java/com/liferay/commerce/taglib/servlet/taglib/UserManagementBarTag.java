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

package com.liferay.commerce.taglib.servlet.taglib;

import com.liferay.application.list.constants.PanelCategoryKeys;
import com.liferay.application.list.display.context.logic.PanelCategoryHelper;
import com.liferay.commerce.taglib.servlet.taglib.internal.servlet.ServletContextUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.taglib.util.IncludeTag;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

/**
 * @author Alessio Antonio Rendina
 */
public class UserManagementBarTag extends IncludeTag {

	@Override
	public int doStartTag() throws JspException {
		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		User user = themeDisplay.getUser();

		if (!user.isDefaultUser()) {
			_notificationsCount = getNotificationsCount(themeDisplay);
		}

		return super.doStartTag();
	}

	public void setHref(String href) {
		_href = href;
	}

	@Override
	public void setPageContext(PageContext pageContext) {
		super.setPageContext(pageContext);

		servletContext = ServletContextUtil.getServletContext();
	}

	public void setShowNotifications(boolean showNotifications) {
		_showNotifications = showNotifications;
	}

	@Override
	protected void cleanUp() {
		super.cleanUp();

		_href = null;
		_notificationsCount = 0;
		_showNotifications = true;
	}

	protected int getNotificationsCount(ThemeDisplay themeDisplay) {
		PanelCategoryHelper panelCategoryHelper = new PanelCategoryHelper(
			ServletContextUtil.getPanelAppRegistry(),
			ServletContextUtil.getPanelCategoryRegistry());

		return panelCategoryHelper.getNotificationsCount(
			PanelCategoryKeys.USER, themeDisplay.getPermissionChecker(),
			themeDisplay.getScopeGroup(), themeDisplay.getUser());
	}

	@Override
	protected String getPage() {
		return _PAGE;
	}

	@Override
	protected void setAttributes(HttpServletRequest httpServletRequest) {
		request.setAttribute(
			"liferay-commerce:user-management-bar:href", _href);
		request.setAttribute(
			"liferay-commerce:user-management-bar:notificationsCount",
			_notificationsCount);
		request.setAttribute(
			"liferay-commerce:user-management-bar:showNotifications",
			_showNotifications);
	}

	private static final String _PAGE = "/user_management_bar/page.jsp";

	private String _href;
	private int _notificationsCount;
	private boolean _showNotifications = true;

}