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

package com.liferay.portal.search.web.internal.category.facet.portlet.display.template;

import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portletdisplaytemplate.BasePortletDisplayTemplateHandler;
import com.liferay.portal.kernel.template.TemplateHandler;
import com.liferay.portal.kernel.template.TemplateVariableGroup;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.search.web.internal.category.facet.constants.CategoryFacetPortletKeys;
import com.liferay.portal.search.web.internal.facet.display.context.AssetCategoriesSearchFacetDisplayContext;
import com.liferay.portal.search.web.internal.facet.display.context.AssetCategoriesSearchFacetTermDisplayContext;
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
	configurationPid = "com.liferay.portal.search.web.internal.category.facet.configuration.SearchFacetsWebTemplateConfiguration",
	configurationPolicy = ConfigurationPolicy.OPTIONAL, immediate = true,
	property = "javax.portlet.name=" + CategoryFacetPortletKeys.CATEGORY_FACET,
	service = TemplateHandler.class
)
public class CategoryFacetPortletDisplayTemplateHandler
	extends BasePortletDisplayTemplateHandler {

	@Override
	public String getClassName() {
		return AssetCategoriesSearchFacetTermDisplayContext.class.getName();
	}

	@Override
	public String getName(Locale locale) {
		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			"content.Language", locale, getClass());

		String portletTitle = _portal.getPortletTitle(
			CategoryFacetPortletKeys.CATEGORY_FACET, resourceBundle);

		return LanguageUtil.format(locale, "x-template", portletTitle, false);
	}

	@Override
	public String getResourceName() {
		return CategoryFacetPortletKeys.CATEGORY_FACET;
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
			"category-facet-display-context",
			AssetCategoriesSearchFacetDisplayContext.class,
			"assetCategoriesSearchFacetDisplayContext");
		templateVariableGroup.addVariable(
			"term-frequency", Integer.class,
			PortletDisplayTemplateConstants.ENTRY, "getFrequency()");
		templateVariableGroup.addVariable(
			"term-name", String.class, PortletDisplayTemplateConstants.ENTRY,
			"getDisplayName()");
		templateVariableGroup.addCollectionVariable(
			"terms", List.class, PortletDisplayTemplateConstants.ENTRIES,
			"term", AssetCategoriesSearchFacetTermDisplayContext.class,
			PortletDisplayTemplateConstants.ENTRY, "getDisplayName()");

		TemplateVariableGroup categoriesServicesTemplateVariableGroup =
			new TemplateVariableGroup(
				"category-services", getRestrictedVariables(language));

		categoriesServicesTemplateVariableGroup.setAutocompleteEnabled(false);

		templateVariableGroups.put(
			categoriesServicesTemplateVariableGroup.getLabel(),
			categoriesServicesTemplateVariableGroup);

		return templateVariableGroups;
	}

	@Override
	protected String getTemplatesConfigPath() {
		return "com/liferay/portal/search/web/internal/category/facet/portlet" +
			"/display/template/dependencies/portlet-display-templates.xml";
	}

	@Reference
	private Portal _portal;

}