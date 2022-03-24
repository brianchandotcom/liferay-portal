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

package com.liferay.commerce.product.asset.categories.navigation.web.internal.portlet.display.template;

import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.asset.kernel.service.AssetCategoryLocalService;
import com.liferay.asset.kernel.service.AssetCategoryService;
import com.liferay.commerce.product.asset.categories.navigation.web.internal.display.context.CPAssetCategoriesNavigationDisplayContext;
import com.liferay.commerce.product.asset.categories.navigation.web.internal.portlet.CPAssetCategoriesNavigationPortlet;
import com.liferay.commerce.product.constants.CPPortletKeys;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
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
 * @author Alessio Antonio Rendina
 */
@Component(
	enabled = false, immediate = true,
	property = "javax.portlet.name=" + CPPortletKeys.CP_ASSET_CATEGORIES_NAVIGATION,
	service = TemplateHandler.class
)
public class CPAssetCategoriesNavigationPortletDisplayTemplateHandler
	extends BasePortletDisplayTemplateHandler {

	@Override
	public String getClassName() {
		return CPAssetCategoriesNavigationPortlet.class.getName();
	}

	@Override
	public String getName(Locale locale) {
		StringBundler sb = new StringBundler(3);

		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			"content.Language", locale, getClass());

		sb.append(
			_portal.getPortletTitle(
				CPPortletKeys.CP_ASSET_CATEGORIES_NAVIGATION, resourceBundle));

		sb.append(StringPool.SPACE);
		sb.append(LanguageUtil.get(locale, "template"));

		return sb.toString();
	}

	@Override
	public String getResourceName() {
		return CPPortletKeys.CP_ASSET_CATEGORIES_NAVIGATION;
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
			"cp-asset-categories-navigation-display-context",
			CPAssetCategoriesNavigationDisplayContext.class,
			"cpAssetCategoriesNavigationDisplayContext");
		templateVariableGroup.addCollectionVariable(
			"asset-categories", List.class,
			PortletDisplayTemplateConstants.ENTRIES, "assetCategory",
			AssetCategory.class, "curAssetCategory", "title");

		TemplateVariableGroup assetCategoriesServicesTemplateVariableGroup =
			new TemplateVariableGroup(
				"asset-categories-services", getRestrictedVariables(language));

		assetCategoriesServicesTemplateVariableGroup.setAutocompleteEnabled(
			false);

		assetCategoriesServicesTemplateVariableGroup.addServiceLocatorVariables(
			AssetCategoryLocalService.class, AssetCategoryService.class);

		templateVariableGroups.put(
			assetCategoriesServicesTemplateVariableGroup.getLabel(),
			assetCategoriesServicesTemplateVariableGroup);

		return templateVariableGroups;
	}

	@Reference
	private Portal _portal;

}