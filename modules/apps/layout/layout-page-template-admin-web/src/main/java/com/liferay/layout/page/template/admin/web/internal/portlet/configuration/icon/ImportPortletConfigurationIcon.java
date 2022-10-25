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

package com.liferay.layout.page.template.admin.web.internal.portlet.configuration.icon;

import com.liferay.layout.page.template.admin.constants.LayoutPageTemplateAdminPortletKeys;
import com.liferay.layout.page.template.constants.LayoutPageTemplateActionKeys;
import com.liferay.layout.page.template.constants.LayoutPageTemplateConstants;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.portlet.configuration.icon.BaseJSPPortletConfigurationIcon;
import com.liferay.portal.kernel.portlet.configuration.icon.PortletConfigurationIcon;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;

import javax.portlet.PortletRequest;

import javax.servlet.ServletContext;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Rubén Pulido
 */
@Component(
	immediate = true,
	property = "javax.portlet.name=" + LayoutPageTemplateAdminPortletKeys.LAYOUT_PAGE_TEMPLATES,
	service = PortletConfigurationIcon.class
)
public class ImportPortletConfigurationIcon
	extends BaseJSPPortletConfigurationIcon {

	@Override
	public String getIconCssClass() {
		return "download";
	}

	@Override
	public String getJspPath() {
		return "/configuration/icon/import.jsp";
	}

	@Override
	public String getMessage(PortletRequest portletRequest) {
		return _language.get(getLocale(portletRequest), "import");
	}

	@Override
	public double getWeight() {
		return 100;
	}

	@Override
	public boolean isShow(PortletRequest portletRequest) {
		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		Group scopeGroup = themeDisplay.getScopeGroup();

		if (_portletResourcePermission.contains(
				themeDisplay.getPermissionChecker(), scopeGroup.getGroupId(),
				LayoutPageTemplateActionKeys.ADD_LAYOUT_PAGE_TEMPLATE_ENTRY)) {

			return true;
		}

		return false;
	}

	@Override
	protected ServletContext getServletContext() {
		return _servletContext;
	}

	@Reference
	private Language _language;

	@Reference
	private Portal _portal;

	@Reference(
		target = "(resource.name=" + LayoutPageTemplateConstants.RESOURCE_NAME + ")"
	)
	private PortletResourcePermission _portletResourcePermission;

	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.layout.page.template.admin.web)"
	)
	private ServletContext _servletContext;

}