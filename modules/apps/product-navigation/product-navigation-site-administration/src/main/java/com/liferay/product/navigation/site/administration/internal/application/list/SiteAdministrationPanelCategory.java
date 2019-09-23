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

package com.liferay.product.navigation.site.administration.internal.application.list;

import com.liferay.application.list.BaseJSPPanelCategory;
import com.liferay.application.list.PanelCategory;
import com.liferay.application.list.constants.ApplicationListWebKeys;
import com.liferay.application.list.constants.PanelCategoryKeys;
import com.liferay.item.selector.ItemSelector;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.service.permission.GroupPermissionUtil;
import com.liferay.product.navigation.site.administration.internal.constants.SiteAdministrationWebKeys;
import com.liferay.site.util.GroupURLProvider;
import com.liferay.site.util.RecentGroupManager;

import java.io.IOException;

import java.util.Locale;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eudaldo Alonso
 */
@Component(
	immediate = true,
	property = {
		"panel.category.key=" + PanelCategoryKeys.ROOT,
		"panel.category.order:Integer=300"
	},
	service = PanelCategory.class
)
public class SiteAdministrationPanelCategory extends BaseJSPPanelCategory {

	@Override
	public String getHeaderJspPath() {
		return "/sites/site_administration_header.jsp";
	}

	@Override
	public String getJspPath() {
		return "/sites/site_administration_body.jsp";
	}

	@Override
	public String getKey() {
		return PanelCategoryKeys.SITE_ADMINISTRATION;
	}

	@Override
	public String getLabel(Locale locale) {
		return LanguageUtil.get(locale, "site-administration");
	}

	@Override
	public boolean include(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws IOException {

		httpServletRequest.setAttribute(
			ApplicationListWebKeys.PANEL_CATEGORY, this);

		return super.include(httpServletRequest, httpServletResponse);
	}

	@Override
	public boolean includeHeader(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws IOException {

		httpServletRequest.setAttribute(
			ApplicationListWebKeys.PANEL_CATEGORY, this);
		httpServletRequest.setAttribute(
			SiteAdministrationWebKeys.GROUP_URL_PROVIDER, _groupURLProvider);
		httpServletRequest.setAttribute(
			SiteAdministrationWebKeys.ITEM_SELECTOR, _itemSelector);
		httpServletRequest.setAttribute(
			SiteAdministrationWebKeys.RECENT_GROUP_MANAGER,
			_recentGroupManager);

		return super.includeHeader(httpServletRequest, httpServletResponse);
	}

	@Override
	public boolean isShow(PermissionChecker permissionChecker, Group group)
		throws PortalException {

		if (group == null) {
			return false;
		}

		if (GroupPermissionUtil.contains(
				permissionChecker, group,
				ActionKeys.VIEW_SITE_ADMINISTRATION)) {

			return true;
		}

		return false;
	}

	@Override
	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.product.navigation.site.administration)",
		unbind = "-"
	)
	public void setServletContext(ServletContext servletContext) {
		super.setServletContext(servletContext);
	}

	@Reference
	private GroupURLProvider _groupURLProvider;

	@Reference
	private ItemSelector _itemSelector;

	@Reference
	private RecentGroupManager _recentGroupManager;

}