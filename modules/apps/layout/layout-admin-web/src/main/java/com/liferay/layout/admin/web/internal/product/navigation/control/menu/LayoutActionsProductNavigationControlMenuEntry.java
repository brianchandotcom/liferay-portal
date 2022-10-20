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

package com.liferay.layout.admin.web.internal.product.navigation.control.menu;

import com.liferay.layout.admin.web.internal.constants.LayoutAdminWebKeys;
import com.liferay.layout.admin.web.internal.display.context.LayoutActionsDisplayContext;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.permission.LayoutPermission;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.product.navigation.control.menu.BaseJSPProductNavigationControlMenuEntry;
import com.liferay.product.navigation.control.menu.ProductNavigationControlMenuEntry;
import com.liferay.product.navigation.control.menu.constants.ProductNavigationControlMenuCategoryKeys;
import com.liferay.segments.service.SegmentsExperienceLocalService;

import java.io.IOException;

import java.util.Locale;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jürgen Kappler
 */
@Component(
	immediate = true,
	property = {
		"product.navigation.control.menu.category.key=" + ProductNavigationControlMenuCategoryKeys.USER,
		"product.navigation.control.menu.entry.order:Integer=100"
	},
	service = ProductNavigationControlMenuEntry.class
)
public class LayoutActionsProductNavigationControlMenuEntry
	extends BaseJSPProductNavigationControlMenuEntry
	implements ProductNavigationControlMenuEntry {

	@Override
	public String getIconJspPath() {
		return "/control/menu/layout_actions.jsp";
	}

	@Override
	public String getLabel(Locale locale) {
		return null;
	}

	@Override
	public String getURL(HttpServletRequest httpServletRequest) {
		return null;
	}

	@Override
	public boolean includeIcon(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws IOException {

		httpServletRequest.setAttribute(
			LayoutAdminWebKeys.LAYOUT_ACTIONS_DISPLAY_CONTEXT,
			new LayoutActionsDisplayContext(
				httpServletRequest, _segmentsExperienceLocalService));

		return super.includeIcon(httpServletRequest, httpServletResponse);
	}

	@Override
	public boolean isShow(HttpServletRequest httpServletRequest)
		throws PortalException {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		Layout layout = themeDisplay.getLayout();

		if (!layout.isDraftLayout()) {
			return false;
		}

		String layoutMode = ParamUtil.getString(
			httpServletRequest, "p_l_mode", Constants.VIEW);

		if (!layoutMode.equals(Constants.EDIT) || layout.isTypeControlPanel() ||
			isEmbeddedPersonalApplicationLayout(layout) ||
			!(themeDisplay.isShowLayoutTemplatesIcon() ||
			  themeDisplay.isShowPageSettingsIcon())) {

			return false;
		}

		if (layout.isSystem()) {
			return _layoutPermission.contains(
				themeDisplay.getPermissionChecker(),
				_layoutLocalService.getLayout(layout.getClassPK()),
				ActionKeys.UPDATE);
		}

		return super.isShow(httpServletRequest);
	}

	@Override
	protected ServletContext getServletContext() {
		return _servletContext;
	}

	@Reference
	private LayoutLocalService _layoutLocalService;

	@Reference
	private LayoutPermission _layoutPermission;

	@Reference
	private SegmentsExperienceLocalService _segmentsExperienceLocalService;

	@Reference(target = "(osgi.web.symbolicname=com.liferay.layout.admin.web)")
	private ServletContext _servletContext;

}