/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
		HttpServletRequest httpServletRequest = getRequest();

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		User user = themeDisplay.getUser();

		if (!user.isGuestUser()) {
			_notificationsCount = getNotificationsCount(themeDisplay);
		}

		return super.doStartTag();
	}

	public String getHref() {
		return _href;
	}

	public boolean isShowNotifications() {
		return _showNotifications;
	}

	public void setHref(String href) {
		_href = href;
	}

	@Override
	public void setPageContext(PageContext pageContext) {
		super.setPageContext(pageContext);

		setServletContext(ServletContextUtil.getServletContext());
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
		httpServletRequest = getRequest();

		httpServletRequest.setAttribute(
			"liferay-commerce:user-management-bar:href", _href);
		httpServletRequest.setAttribute(
			"liferay-commerce:user-management-bar:notificationsCount",
			_notificationsCount);
		httpServletRequest.setAttribute(
			"liferay-commerce:user-management-bar:showNotifications",
			_showNotifications);
	}

	private static final String _PAGE = "/user_management_bar/page.jsp";

	private String _href;
	private int _notificationsCount;
	private boolean _showNotifications = true;

}