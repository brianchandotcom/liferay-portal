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

package com.liferay.site.navigation.breadcrumb.web.portlet.template;

import aQute.bnd.annotation.metatype.Configurable;

import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portletdisplaytemplate.BasePortletDisplayTemplateHandler;
import com.liferay.portal.kernel.servlet.taglib.ui.BreadcrumbEntry;
import com.liferay.portal.kernel.servlet.taglib.ui.BreadcrumbUtil;
import com.liferay.portal.kernel.template.TemplateHandler;
import com.liferay.portal.kernel.template.TemplateVariableGroup;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.portletdisplaytemplate.util.PortletDisplayTemplateConstants;
import com.liferay.site.navigation.breadcrumb.web.configuration.BreadcrumbConfigurationValues;
import com.liferay.site.navigation.breadcrumb.web.configuration.BreadcrumbWebConfiguration;
import com.liferay.site.navigation.breadcrumb.web.constants.BreadcrumbPortletKeys;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;
import org.osgi.service.component.annotations.Modified;

/**
 * @author José Manuel Navarro
 */
@Component(
	configurationPid = "com.liferay.site.navigation.breadcrumb.web.configuration.BreadcrumbWebConfiguration",
	configurationPolicy = ConfigurationPolicy.OPTIONAL, immediate = true,
	property = {"javax.portlet.name=" + BreadcrumbPortletKeys.BREADCRUMB},
	service = TemplateHandler.class
)
public class BreadcrumbPortletDisplayTemplateHandler
	extends BasePortletDisplayTemplateHandler {

	@Override
	public String getClassName() {
		return BreadcrumbEntry.class.getName();
	}

	@Override
	public Map<String, Object> getCustomContextObjects() {
		Map<String, Object> customContextObjects = new HashMap<>(1);

		customContextObjects.put("breadcrumbUtil", BreadcrumbUtil.class);

		return customContextObjects;
	}

	@Override
	public String getDefaultTemplateKey() {
		return _breadcrumbWebConfiguration.ddmTemplateKeyDefault();
	}

	@Override
	public String getName(Locale locale) {
		ResourceBundle resourceBundle = ResourceBundle.getBundle(
			"content.Language", locale);

		String portletTitle = PortalUtil.getPortletTitle(
			BreadcrumbPortletKeys.BREADCRUMB, resourceBundle);

		return portletTitle.concat(StringPool.SPACE).concat(
			LanguageUtil.get(locale, "template"));
	}

	@Override
	public String getResourceName() {
		return BreadcrumbPortletKeys.BREADCRUMB;
	}

	@Override
	public Map<String, TemplateVariableGroup> getTemplateVariableGroups(
			long classPK, String language, Locale locale)
		throws Exception {

		Map<String, TemplateVariableGroup> templateVariableGroups =
			super.getTemplateVariableGroups(classPK, language, locale);

		TemplateVariableGroup fieldsTemplateVariableGroup =
			templateVariableGroups.get("fields");

		fieldsTemplateVariableGroup.addCollectionVariable(
			"breadcrumb-entries", List.class,
			PortletDisplayTemplateConstants.ENTRIES, "breadcrumb-entry",
			BreadcrumbEntry.class, "curEntry", "getTitle()");
		fieldsTemplateVariableGroup.addVariable(
			"breadcrumb-entry", BreadcrumbEntry.class,
			PortletDisplayTemplateConstants.ENTRY, "getTitle()");
		fieldsTemplateVariableGroup.addVariable(
			"breadcrumb-util", BreadcrumbUtil.class, "breadcrumbUtil");

		return templateVariableGroups;
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		_breadcrumbWebConfiguration = Configurable.createConfigurable(
			BreadcrumbWebConfiguration.class, properties);
	}

	@Override
	protected String getTemplatesConfigPath() {
		return BreadcrumbConfigurationValues.DISPLAY_TEMPLATES_CONFIG;
	}

	private volatile BreadcrumbWebConfiguration _breadcrumbWebConfiguration;

}