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

package com.liferay.site.navigation.menu.web.internal.portlet.template;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portletdisplaytemplate.BasePortletDisplayTemplateHandler;
import com.liferay.portal.kernel.template.TemplateHandler;
import com.liferay.portal.kernel.template.TemplateVariableGroup;
import com.liferay.portal.kernel.theme.NavItem;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portlet.display.template.constants.PortletDisplayTemplateConstants;
import com.liferay.site.navigation.menu.web.configuration.SiteNavigationMenuWebTemplateConfiguration;
import com.liferay.site.navigation.menu.web.internal.constants.SiteNavigationMenuPortletKeys;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Juergen Kappler
 */
@Component(
	configurationPid = "com.liferay.site.navigation.menu.web.configuration.SiteNavigationMenuWebTemplateConfiguration",
	configurationPolicy = ConfigurationPolicy.OPTIONAL, immediate = true,
	property = {"javax.portlet.name=" + SiteNavigationMenuPortletKeys.SITE_NAVIGATION_MENU},
	service = TemplateHandler.class
)
public class SiteNavigationMenuPortletDisplayTemplateHandler
	extends BasePortletDisplayTemplateHandler {

	@Override
	public String getClassName() {
		return NavItem.class.getName();
	}

	@Override
	public Map<String, Object> getCustomContextObjects() {
		Map<String, Object> contextObjects = new HashMap<>();

		contextObjects.put("navItem", NavItem.class);

		return contextObjects;
	}

	@Override
	public String getDefaultTemplateKey() {
		return _siteNavigationMenuWebTemplateConfiguration.
			ddmTemplateKeyDefault();
	}

	@Override
	public String getName(Locale locale) {
		String portletTitle = _portal.getPortletTitle(
			SiteNavigationMenuPortletKeys.SITE_NAVIGATION_MENU, locale);

		return portletTitle.concat(StringPool.SPACE).concat(
			LanguageUtil.get(locale, "template"));
	}

	@Override
	public String getResourceName() {
		return SiteNavigationMenuPortletKeys.SITE_NAVIGATION_MENU;
	}

	@Override
	public Map<String, TemplateVariableGroup> getTemplateVariableGroups(
			long classPK, String language, Locale locale)
		throws Exception {

		Map<String, TemplateVariableGroup> templateVariableGroups =
			super.getTemplateVariableGroups(classPK, language, locale);

		TemplateVariableGroup templateVariableGroup =
			templateVariableGroups.get("fields");

		templateVariableGroup.empty();

		templateVariableGroup.addVariable(
			"header-type", String.class, "headerType");
		templateVariableGroup.addVariable(
			"included-layouts", String.class, "includedLayouts");
		templateVariableGroup.addVariable(
			"nested-children", String.class, "nestedChildren");
		templateVariableGroup.addVariable(
			"root-layout-level", Integer.class, "rootLayoutLevel");
		templateVariableGroup.addVariable(
			"root-layout-type", String.class, "rootLayoutType");
		templateVariableGroup.addCollectionVariable(
			"navigation-items", List.class,
			PortletDisplayTemplateConstants.ENTRIES, "navigation-item",
			NavItem.class, "navigationEntry", "getName()");

		templateVariableGroups.put(
			"navigation-util", getUtilTemplateVariableGroup());

		return templateVariableGroups;
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		_siteNavigationMenuWebTemplateConfiguration =
			ConfigurableUtil.createConfigurable(
				SiteNavigationMenuWebTemplateConfiguration.class, properties);
	}

	@Override
	protected String getTemplatesConfigPath() {
		return "com/liferay/site/navigation/menu/web/portlet/template" +
			"/dependencies/portlet-display-templates.xml";
	}

	protected TemplateVariableGroup getUtilTemplateVariableGroup() {
		TemplateVariableGroup templateVariableGroup = new TemplateVariableGroup(
			"navigation-util");

		templateVariableGroup.addVariable(
			"navigation-item", NavItem.class, "navItem");

		return templateVariableGroup;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		SiteNavigationMenuPortletDisplayTemplateHandler.class);

	@Reference
	private Portal _portal;

	private volatile SiteNavigationMenuWebTemplateConfiguration
		_siteNavigationMenuWebTemplateConfiguration;

}