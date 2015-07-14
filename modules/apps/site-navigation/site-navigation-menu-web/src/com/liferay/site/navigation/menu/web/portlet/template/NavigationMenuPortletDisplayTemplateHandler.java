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

package com.liferay.site.navigation.menu.web.portlet.template;

import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portletdisplaytemplate.BasePortletDisplayTemplateHandler;
import com.liferay.portal.kernel.template.TemplateHandler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.theme.NavItem;
import com.liferay.portal.util.PortalUtil;
import com.liferay.site.navigation.menu.web.configuration.NavigationMenuConfigurationValues;
import com.liferay.site.navigation.menu.web.constants.NavigationMenuPortletKeys;

import java.util.Locale;
import java.util.ResourceBundle;

import org.osgi.service.component.annotations.Component;

/**
 * @author Juergen Kappler
 */
@Component(
	immediate = true,
	property = {"javax.portlet.name=" + NavigationMenuPortletKeys.NAVIGATION},
	service = TemplateHandler.class
)
public class NavigationMenuPortletDisplayTemplateHandler
	extends BasePortletDisplayTemplateHandler {

	@Override
	public String getClassName() {
		return NavItem.class.getName();
	}

	@Override
	public String getName(Locale locale) {
		ResourceBundle resourceBundle = ResourceBundle.getBundle(
			"content.Language", locale);

		String portletTitle = PortalUtil.getPortletTitle(
			NavigationMenuPortletKeys.NAVIGATION, resourceBundle);

		return portletTitle.concat(StringPool.SPACE).concat(
			LanguageUtil.get(locale, "template"));
	}

	@Override
	public String getResourceName() {
		return NavigationMenuPortletKeys.NAVIGATION;
	}

	@Override
	protected String getTemplatesConfigPath() {
		return NavigationMenuConfigurationValues.DISPLAY_TEMPLATES_CONFIG;
	}

}