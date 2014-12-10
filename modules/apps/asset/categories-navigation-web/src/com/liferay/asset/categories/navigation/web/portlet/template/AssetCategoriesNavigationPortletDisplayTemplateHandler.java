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

package com.liferay.asset.categories.navigation.web.portlet.template;

import com.liferay.asset.categories.navigation.web.configuration.CategoriesNavigationWebConfigurationValues;
import com.liferay.asset.categories.navigation.web.constants.CategoriesNavigationPortletKeys;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portletdisplaytemplate.BasePortletDisplayTemplateHandler;
import com.liferay.portal.kernel.template.TemplateHandler;
import com.liferay.portal.kernel.template.TemplateVariableGroup;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.PortletConstants;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.asset.model.AssetCategory;
import com.liferay.portlet.asset.model.AssetVocabulary;
import com.liferay.portlet.asset.service.AssetCategoryLocalService;
import com.liferay.portlet.asset.service.AssetCategoryService;
import com.liferay.portlet.asset.service.AssetVocabularyLocalService;
import com.liferay.portlet.asset.service.AssetVocabularyService;
import com.liferay.portlet.portletdisplaytemplate.util.PortletDisplayTemplateConstants;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleReference;
import org.osgi.service.component.annotations.Component;

/**
 * @author Juan Fernández
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=com_liferay_asset_categories_navigation_web_portlet_CategoriesNavigationPortlet"
	},
	service = TemplateHandler.class
)
public class AssetCategoriesNavigationPortletDisplayTemplateHandler
	extends BasePortletDisplayTemplateHandler {

	@Override
	public String getClassName() {
		return AssetCategory.class.getName();
	}

	@Override
	public String getName(Locale locale) {
		ResourceBundle resourceBundle = ResourceBundle.getBundle(
			"content.Language");

		String portletTitle = PortalUtil.getPortletTitle(
			CategoriesNavigationPortletKeys.CATEGORIES_NAVIGATION,
			resourceBundle);

		return portletTitle.concat(StringPool.SPACE).concat(
			LanguageUtil.get(locale, "template"));
	}

	@Override
	public String getResourceName() {
		Class<?> clazz = getClass();

		BundleReference bundleReference =
			(BundleReference)clazz.getClassLoader();

		Bundle bundle = bundleReference.getBundle();

		String symbolicName = bundle.getSymbolicName();

		symbolicName = symbolicName.replaceAll("[^a-zA-Z0-9]", "");

		return CategoriesNavigationPortletKeys.CATEGORIES_NAVIGATION.concat(
			PortletConstants.WAR_SEPARATOR).concat(
				String.valueOf(symbolicName));
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
			"vocabularies", List.class, PortletDisplayTemplateConstants.ENTRIES,
			"vocabulary", AssetVocabulary.class, "curVocabulary", "name");

		String[] restrictedVariables = getRestrictedVariables(language);

		TemplateVariableGroup categoriesServicesTemplateVariableGroup =
			new TemplateVariableGroup("category-services", restrictedVariables);

		categoriesServicesTemplateVariableGroup.setAutocompleteEnabled(false);

		categoriesServicesTemplateVariableGroup.addServiceLocatorVariables(
			AssetVocabularyLocalService.class, AssetVocabularyService.class,
			AssetCategoryLocalService.class, AssetCategoryService.class);

		templateVariableGroups.put(
			categoriesServicesTemplateVariableGroup.getLabel(),
			categoriesServicesTemplateVariableGroup);

		return templateVariableGroups;
	}

	@Override
	protected String getTemplatesConfigPath() {
		return CategoriesNavigationWebConfigurationValues.
			DISPLAY_TEMPLATES_CONFIG;
	}

}