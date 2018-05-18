/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
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