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

package com.liferay.product.navigation.site.administration.application.list;

import com.liferay.application.list.BaseJSPPanelCategory;
import com.liferay.application.list.PanelCategory;
import com.liferay.application.list.constants.PanelCategoryKeys;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Organization;
import com.liferay.portal.model.User;
import com.liferay.portal.security.permission.PermissionChecker;

import java.util.List;
import java.util.Locale;

import javax.servlet.ServletContext;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eudaldo Alonso
 */
@Component(
	immediate = true,
	property = {
		"panel.category.key=" + PanelCategoryKeys.SITES_ALL_SITES,
		"service.ranking:Integer=200"
	},
	service = PanelCategory.class
)
public class MySitesPanelCategory extends BaseJSPPanelCategory {

	@Override
	public String getIconCssClass() {
		return "icon-sitemap";
	}

	@Override
	public String getJspPath() {
		return "/META-INF/resources/my_sites/my_sites.jsp";
	}

	@Override
	public String getKey() {
		return PanelCategoryKeys.SITES_ADMINISTRATION_MY_SITES;
	}

	@Override
	public String getLabel(Locale locale) {
		return LanguageUtil.get(locale, "my-sites");
	}

	@Override
	public boolean hasAccessPermission(
			PermissionChecker permissionChecker, Group group)
		throws PortalException {

		User user = permissionChecker.getUser();

		List<Group> siteGroups = user.getMySiteGroups(
			new String[] {Group.class.getName(), Organization.class.getName()},
			false, 1);

		if (siteGroups.isEmpty()) {
			return false;
		}

		return super.hasAccessPermission(permissionChecker, group);
	}

	@Override
	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.product.menu.site.administration.service)",
		unbind = "-"
	)
	public void setServletContext(ServletContext servletContext) {
		super.setServletContext(servletContext);
	}

}