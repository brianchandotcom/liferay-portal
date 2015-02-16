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

package com.liferay.language.web.portlet.template;

import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portletdisplaytemplate.BasePortletDisplayTemplateHandler;
import com.liferay.portal.kernel.template.TemplateVariableGroup;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.portletdisplaytemplate.util.PortletDisplayTemplateConstants;

import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @author Eduardo Garcia
 */
public class LanguagePortletDisplayTemplateHandler
	extends BasePortletDisplayTemplateHandler {

	@Override
	public String getClassName() {
		return Locale.class.getName();
	}

	@Override
	public String getName(Locale locale) {
		String portletTitle = PortalUtil.getPortletTitle(
			PortletKeys.LANGUAGE, locale);

		return portletTitle.concat(StringPool.SPACE).concat(
			LanguageUtil.get(locale, "template"));
	}

	@Override
	public String getResourceName() {
		return PortletKeys.LANGUAGE;
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
			"language", Locale.class, "curLanguage", "displayName");

		String[] restrictedVariables = getRestrictedVariables(language);

		TemplateVariableGroup documentServicesTemplateVariableGroup =
			new TemplateVariableGroup("document-services", restrictedVariables);

		documentServicesTemplateVariableGroup.setAutocompleteEnabled(false);

		templateVariableGroups.put(
			documentServicesTemplateVariableGroup.getLabel(),
			documentServicesTemplateVariableGroup);

		return templateVariableGroups;
	}

	@Override
	protected String getTemplatesConfigPath() {
		return PropsValues.LANGUAGE_DISPLAY_TEMPLATES_CONFIG;
	}

}