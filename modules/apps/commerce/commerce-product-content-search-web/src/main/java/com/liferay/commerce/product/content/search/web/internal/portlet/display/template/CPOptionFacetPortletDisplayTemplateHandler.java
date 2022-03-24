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

package com.liferay.commerce.product.content.search.web.internal.portlet.display.template;

import com.liferay.commerce.product.constants.CPPortletKeys;
import com.liferay.commerce.product.content.search.web.internal.display.context.CPOptionsSearchFacetDisplayContext;
import com.liferay.commerce.product.content.search.web.internal.display.context.CPOptionsSearchFacetTermDisplayContext;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portletdisplaytemplate.BasePortletDisplayTemplateHandler;
import com.liferay.portal.kernel.template.TemplateHandler;
import com.liferay.portal.kernel.template.TemplateVariableGroup;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portlet.display.template.constants.PortletDisplayTemplateConstants;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Andrea Sbarra
 */
@Component(
	configurationPid = "com.liferay.commerce.product.content.search.web.internal.configuration.CPOptionFacetsPortletInstanceConfiguration",
	configurationPolicy = ConfigurationPolicy.OPTIONAL, enabled = false,
	immediate = true,
	property = "javax.portlet.name=" + CPPortletKeys.CP_OPTION_FACETS,
	service = TemplateHandler.class
)
public class CPOptionFacetPortletDisplayTemplateHandler
	extends BasePortletDisplayTemplateHandler {

	@Override
	public String getClassName() {
		return CPOptionsSearchFacetDisplayContext.class.getName();
	}

	@Override
	public String getName(Locale locale) {
		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			"content.Language", locale, getClass());

		String portletTitle = _portal.getPortletTitle(
			CPPortletKeys.CP_OPTION_FACETS, resourceBundle);

		return LanguageUtil.format(locale, "x-template", portletTitle, false);
	}

	@Override
	public String getResourceName() {
		return CPPortletKeys.CP_OPTION_FACETS;
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
			"cpoption-facet-display-context",
			CPOptionsSearchFacetDisplayContext.class,
			"cpOptionsSearchFacetDisplayContext");
		templateVariableGroup.addVariable(
			"term-frequency", Integer.class,
			PortletDisplayTemplateConstants.ENTRY, "getFrequency()");
		templateVariableGroup.addVariable(
			"term-name", String.class, PortletDisplayTemplateConstants.ENTRY,
			"getDisplayName()");
		templateVariableGroup.addCollectionVariable(
			"terms", List.class, PortletDisplayTemplateConstants.ENTRIES,
			"term", CPOptionsSearchFacetTermDisplayContext.class,
			PortletDisplayTemplateConstants.ENTRY, "getDisplayName()");

		TemplateVariableGroup cpOptionsServicesTemplateVariableGroup =
			new TemplateVariableGroup(
				"cpoption-services", getRestrictedVariables(language));

		cpOptionsServicesTemplateVariableGroup.setAutocompleteEnabled(false);

		templateVariableGroups.put(
			cpOptionsServicesTemplateVariableGroup.getLabel(),
			cpOptionsServicesTemplateVariableGroup);

		return templateVariableGroups;
	}

	@Override
	protected String getTemplatesConfigPath() {
		return "com/liferay/commerce/product/content/search/web/internal" +
			"/portlet/display/template/dependencies/option_facets" +
				"/portlet-display-templates.xml";
	}

	@Reference
	private Portal _portal;

}