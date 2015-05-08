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

package com.liferay.site.navigation.language.web.portlet.template;

import aQute.bnd.annotation.metatype.Configurable;

import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portletdisplaytemplate.BasePortletDisplayTemplateHandler;
import com.liferay.portal.kernel.servlet.taglib.ui.LanguageEntry;
import com.liferay.portal.kernel.template.TemplateHandler;
import com.liferay.portal.kernel.template.TemplateVariableGroup;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.portletdisplaytemplate.util.PortletDisplayTemplateConstants;
import com.liferay.site.navigation.language.web.configuration.LanguageWebConfiguration;
import com.liferay.site.navigation.language.web.configuration.LanguageWebConfigurationValues;
import com.liferay.site.navigation.language.web.constants.LanguagePortletKeys;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;
import org.osgi.service.component.annotations.Modified;

/**
 * @author Eduardo Garcia
 */
@Component(
	configurationPid = "com.liferay.site.navigation.language.web.configuration.LanguageWebConfiguration",
	configurationPolicy = ConfigurationPolicy.OPTIONAL, immediate = true,
	property = {"javax.portlet.name="+ LanguagePortletKeys.LANGUAGE},
	service = TemplateHandler.class
)
public class LanguagePortletDisplayTemplateHandler
	extends BasePortletDisplayTemplateHandler {

	@Override
	public String getClassName() {
		return LanguageEntry.class.getName();
	}

	@Override
	public String getDefaultTemplateKey() {
		return _languageWebConfiguration.ddmTemplateKey();
	}

	@Override
	public String getName(Locale locale) {
		ResourceBundle resourceBundle = ResourceBundle.getBundle(
			"content.Language", locale);

		String portletTitle = PortalUtil.getPortletTitle(
			LanguagePortletKeys.LANGUAGE, resourceBundle);

		return portletTitle.concat(StringPool.SPACE).concat(
			LanguageUtil.get(locale, "template"));
	}

	@Override
	public String getResourceName() {
		return LanguagePortletKeys.LANGUAGE;
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

		templateVariableGroup.addCollectionVariable(
			"languages", List.class, PortletDisplayTemplateConstants.ENTRIES,
			"language", LanguageEntry.class, "curLanguage", "longDisplayName");

		return templateVariableGroups;
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		_languageWebConfiguration = Configurable.createConfigurable(
			LanguageWebConfiguration.class, properties);
	}

	@Override
	protected String getTemplatesConfigPath() {
		return LanguageWebConfigurationValues.DISPLAY_TEMPLATES_CONFIG;
	}

	private volatile LanguageWebConfiguration _languageWebConfiguration;

}