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

package com.liferay.asset.categories.navigation.web.internal.portlet.display.template;

import com.liferay.asset.categories.navigation.constants.AssetCategoriesNavigationPortletKeys;
import com.liferay.asset.categories.navigation.web.internal.display.context.AssetCategoriesNavigationDisplayContext;
import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.asset.kernel.model.AssetVocabulary;
import com.liferay.asset.kernel.service.AssetCategoryLocalService;
import com.liferay.asset.kernel.service.AssetCategoryService;
import com.liferay.asset.kernel.service.AssetVocabularyLocalService;
import com.liferay.asset.kernel.service.AssetVocabularyService;
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
import org.osgi.service.component.annotations.Reference;

/**
 * @author Juan Fernández
 */
@Component(
	immediate = true,
	property = "javax.portlet.name=" + AssetCategoriesNavigationPortletKeys.ASSET_CATEGORIES_NAVIGATION,
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
		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			"content.Language", locale, getClass());

		String portletTitle = _portal.getPortletTitle(
			AssetCategoriesNavigationPortletKeys.ASSET_CATEGORIES_NAVIGATION,
			resourceBundle);

		return LanguageUtil.format(locale, "x-template", portletTitle, false);
	}

	@Override
	public String getResourceName() {
		return AssetCategoriesNavigationPortletKeys.ASSET_CATEGORIES_NAVIGATION;
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
			"asset-categories-navigation-display-context",
			AssetCategoriesNavigationDisplayContext.class,
			"assetCategoriesNavigationDisplayContext");
		templateVariableGroup.addCollectionVariable(
			"vocabularies", List.class, PortletDisplayTemplateConstants.ENTRIES,
			"vocabulary", AssetVocabulary.class, "curVocabulary", "name");

		TemplateVariableGroup categoriesServicesTemplateVariableGroup =
			new TemplateVariableGroup(
				"category-services", getRestrictedVariables(language));

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
		return "com/liferay/asset/categories/navigation/web/portlet/template" +
			"/dependencies/portlet-display-templates.xml";
	}

	@Reference
	private Portal _portal;

}