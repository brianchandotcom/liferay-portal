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

package com.liferay.portal.search.web.internal.custom.facet.template;

import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.portletdisplaytemplate.BasePortletDisplayTemplateHandler;
import com.liferay.portal.kernel.template.TemplateHandler;
import com.liferay.portal.kernel.template.TemplateVariableGroup;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.search.web.internal.custom.facet.constants.CustomFacetPortletKeys;
import com.liferay.portal.search.web.internal.custom.facet.display.context.CustomFacetDisplayContext;
import com.liferay.portal.search.web.internal.custom.facet.display.context.CustomFacetTermDisplayContext;
import com.liferay.portlet.display.template.constants.PortletDisplayTemplateConstants;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Kevin Tan
 */
@Component(
	configurationPid = "com.liferay.portal.search.web.internal.custom.facet.configuration.SearchFacetsWebTemplateConfiguration",
	configurationPolicy = ConfigurationPolicy.OPTIONAL, immediate = true,
	property = "javax.portlet.name=" + CustomFacetPortletKeys.CUSTOM_FACET,
	service = TemplateHandler.class
)
public class CustomFacetPortletDisplayTemplateHandler
	extends BasePortletDisplayTemplateHandler {

	@Override
	public String getClassName() {
		return CustomFacetTermDisplayContext.class.getName();
	}

	@Override
	public String getName(Locale locale) {
		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			"content.Language", locale, getClass());

		String portletTitle = _portal.getPortletTitle(
			CustomFacetPortletKeys.CUSTOM_FACET, resourceBundle);

		return _language.format(locale, "x-template", portletTitle, false);
	}

	@Override
	public String getResourceName() {
		return CustomFacetPortletKeys.CUSTOM_FACET;
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
			"custom-facet-display-context", CustomFacetDisplayContext.class,
			"customFacetDisplayContext");
		templateVariableGroup.addVariable(
			"term-field-name", String.class,
			PortletDisplayTemplateConstants.ENTRY, "getFieldName()");
		templateVariableGroup.addVariable(
			"term-frequency", Integer.class,
			PortletDisplayTemplateConstants.ENTRY, "getFrequency()");
		templateVariableGroup.addCollectionVariable(
			"terms", List.class, PortletDisplayTemplateConstants.ENTRIES,
			"term", CustomFacetTermDisplayContext.class,
			PortletDisplayTemplateConstants.ENTRY, "getFieldName()");

		TemplateVariableGroup customFacetServicesTemplateVariableGroup =
			new TemplateVariableGroup(
				"custom-services", getRestrictedVariables(language));

		customFacetServicesTemplateVariableGroup.setAutocompleteEnabled(false);

		templateVariableGroups.put(
			customFacetServicesTemplateVariableGroup.getLabel(),
			customFacetServicesTemplateVariableGroup);

		return templateVariableGroups;
	}

	@Override
	protected String getTemplatesConfigPath() {
		return "com/liferay/portal/search/web/internal/custom/facet/template" +
			"/dependencies/portlet-display-templates.xml";
	}

	@Reference
	private Language _language;

	@Reference
	private Portal _portal;

}