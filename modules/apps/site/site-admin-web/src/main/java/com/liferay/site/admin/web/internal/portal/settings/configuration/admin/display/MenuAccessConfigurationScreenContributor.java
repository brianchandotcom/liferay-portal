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

package com.liferay.site.admin.web.internal.portal.settings.configuration.admin.display;

import com.liferay.item.selector.ItemSelector;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.site.admin.web.internal.display.context.MenuAccessConfigurationDisplayContext;
import com.liferay.site.configuration.manager.MenuAccessConfigurationManager;
import com.liferay.site.settings.configuration.admin.display.SiteSettingsConfigurationScreenContributor;

import java.util.Locale;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Mikel Lorza
 */
@Component(service = SiteSettingsConfigurationScreenContributor.class)
public class MenuAccessConfigurationScreenContributor
	implements SiteSettingsConfigurationScreenContributor {

	@Override
	public String getCategoryKey() {
		return "site-configuration";
	}

	@Override
	public String getJspPath() {
		return "/site_settings/menu_access.jsp";
	}

	@Override
	public String getKey() {
		return "site-configuration-menu-access";
	}

	@Override
	public String getName(Locale locale) {
		return _language.get(locale, "menu-access");
	}

	@Override
	public String getSaveMVCActionCommandName() {
		return "/site_settings/edit_menu_access_configuration";
	}

	@Override
	public ServletContext getServletContext() {
		return _servletContext;
	}

	@Override
	public boolean isVisible(Group group) {
		if (group.isCompany()) {
			return false;
		}

		return true;
	}

	@Override
	public void setAttributes(
		HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse) {

		httpServletRequest.setAttribute(
			MenuAccessConfigurationDisplayContext.class.getName(),
			new MenuAccessConfigurationDisplayContext(
				httpServletRequest, _itemSelector,
				_menuAccessConfigurationManager, _portal, _roleLocalService));
	}

	@Reference
	private ItemSelector _itemSelector;

	@Reference
	private Language _language;

	@Reference
	private MenuAccessConfigurationManager _menuAccessConfigurationManager;

	@Reference
	private Portal _portal;

	@Reference
	private RoleLocalService _roleLocalService;

	@Reference(target = "(osgi.web.symbolicname=com.liferay.site.admin.web)")
	private ServletContext _servletContext;

}