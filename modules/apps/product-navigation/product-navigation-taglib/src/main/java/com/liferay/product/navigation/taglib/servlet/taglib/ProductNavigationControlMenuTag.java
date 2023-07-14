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

package com.liferay.product.navigation.taglib.servlet.taglib;

import com.liferay.application.list.display.context.logic.PanelCategoryHelper;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.product.navigation.control.menu.manager.ProductNavigationControlMenuManager;
import com.liferay.product.navigation.taglib.internal.servlet.ServletContextUtil;
import com.liferay.taglib.util.IncludeTag;

import java.io.IOException;

import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.PageContext;

/**
 * @author Eudaldo Alonso
 */
public class ProductNavigationControlMenuTag extends IncludeTag {

	@Override
	public int doStartTag() {
		return EVAL_BODY_INCLUDE;
	}

	@Override
	public void setPageContext(PageContext pageContext) {
		super.setPageContext(pageContext);

		setServletContext(ServletContextUtil.getServletContext());
	}

	@Override
	protected void cleanUp() {
		super.cleanUp();
	}

	@Override
	protected String getPage() {
		return _PAGE;
	}

	@Override
	protected void includePage(
			String page, HttpServletResponse httpServletResponse)
		throws IOException, ServletException {

		if (_isIncludePage()) {
			super.includePage(page, httpServletResponse);
		}
	}

	@Override
	protected boolean isCleanUpSetAttributes() {
		return false;
	}

	@Override
	protected void setAttributes(HttpServletRequest httpServletRequest) {
		httpServletRequest.setAttribute(
			"liferay-product-navigation:control-menu:applicationsMenuApp",
			_isApplicationsMenuApp(httpServletRequest));
	}

	private boolean _isApplicationsMenuApp(
		HttpServletRequest httpServletRequest) {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		if (Validator.isNull(themeDisplay.getPpid())) {
			return false;
		}

		PanelCategoryHelper panelCategoryHelper = new PanelCategoryHelper(
			ServletContextUtil.getPanelAppRegistry(),
			ServletContextUtil.getPanelCategoryRegistry());

		if (!panelCategoryHelper.isApplicationsMenuApp(
				themeDisplay.getPpid())) {

			return false;
		}

		Layout layout = themeDisplay.getLayout();

		if ((layout != null) && !layout.isTypeControlPanel()) {
			return false;
		}

		return true;
	}

	private boolean _isIncludePage() {
		String layoutMode = ParamUtil.getString(
			getOriginalServletRequest(), "p_l_mode", Constants.VIEW);

		if (layoutMode.equals(Constants.PREVIEW)) {
			return false;
		}

		HttpServletRequest httpServletRequest = getRequest();

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		ProductNavigationControlMenuManager
			productNavigationControlMenuManager =
				ServletContextUtil.getProductNavigationControlMenuManager();

		if (!productNavigationControlMenuManager.isShowControlMenu(
				themeDisplay.getScopeGroup(), themeDisplay.getLayout(),
				themeDisplay.getUserId())) {

			return false;
		}

		// Temporary workaround for LPS-175648

		if ((_ROLE_NAMES.length == 0) || !themeDisplay.isSignedIn()) {
			return true;
		}

		User user = themeDisplay.getUser();

		List<Role> roles = user.getRoles();

		for (Role role : roles) {
			if (ArrayUtil.contains(_ROLE_NAMES, role.getName())) {
				return true;
			}
		}

		return false;
	}

	private static final String _PAGE = "/control_menu/page.jsp";

	private static final String[] _ROLE_NAMES = PropsUtil.getArray(
		"control.menu.required.authenticated.user.role.names");

}