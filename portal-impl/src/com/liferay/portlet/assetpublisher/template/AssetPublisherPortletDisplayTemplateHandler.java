/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.assetpublisher.template;

import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portletdisplaytemplate.BasePortletDisplayTemplateHandler;
import com.liferay.portal.kernel.template.TemplateVariableGroup;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.asset.model.AssetEntry;
import com.liferay.portlet.asset.service.AssetCategoryLocalService;
import com.liferay.portlet.asset.service.AssetCategoryService;
import com.liferay.portlet.asset.service.AssetEntryLocalService;
import com.liferay.portlet.asset.service.AssetEntryService;
import com.liferay.portlet.asset.service.AssetTagLocalService;
import com.liferay.portlet.asset.service.AssetTagService;
import com.liferay.portlet.asset.service.AssetTagStatsLocalService;
import com.liferay.portlet.asset.service.AssetVocabularyLocalService;
import com.liferay.portlet.asset.service.AssetVocabularyService;
import com.liferay.portlet.assetpublisher.util.AssetPublisherHelper;
import com.liferay.portlet.portletdisplaytemplate.util.PortletDisplayTemplateConstants;

import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @author Juan Fernández
 */
public class AssetPublisherPortletDisplayTemplateHandler
	extends BasePortletDisplayTemplateHandler {

	public String getClassName() {
		return AssetEntry.class.getName();
	}

	public String getName(Locale locale) {
		String portletTitle = PortalUtil.getPortletTitle(
			PortletKeys.ASSET_PUBLISHER, locale);

		return portletTitle.concat(StringPool.SPACE).concat(
			LanguageUtil.get(locale, "template"));
	}

	public String getResourceName() {
		return "com.liferay.portlet.assetpublisher";
	}

	@Override
	public Map<String, TemplateVariableGroup> getTemplateVariableGroups(
			long classPK, String language, Locale locale)
		throws Exception {

		Map<String, TemplateVariableGroup> templateVariableGroups =
			super.getTemplateVariableGroups(classPK, language, locale);

		TemplateVariableGroup assetPublisherUtilTemplateVariableGroup =
			new TemplateVariableGroup("asset-publisher-util");

		assetPublisherUtilTemplateVariableGroup.addVariable(
			"asset-publisher-helper", AssetPublisherHelper.class,
			PortletDisplayTemplateConstants.ASSET_PUBLISHER_HELPER);

		templateVariableGroups.put(
			"asset-publisher-util", assetPublisherUtilTemplateVariableGroup);

		TemplateVariableGroup fieldsTemplateVariableGroup =
			templateVariableGroups.get("fields");

		fieldsTemplateVariableGroup.empty();

		fieldsTemplateVariableGroup.addCollectionVariable(
			"asset-entries", List.class,
			PortletDisplayTemplateConstants.ENTRIES, "asset-entry",
			AssetEntry.class, "curEntry");
		fieldsTemplateVariableGroup.addVariable(
			"asset-entry", AssetEntry.class,
			PortletDisplayTemplateConstants.ENTRY);

		TemplateVariableGroup assetServicesTemplateVariableGroup =
			new TemplateVariableGroup("asset-services");

		assetServicesTemplateVariableGroup.setAutocompleteEnabled(false);

		assetServicesTemplateVariableGroup.addServiceLocatorVariables(
			AssetEntryLocalService.class, AssetEntryService.class,
			AssetVocabularyLocalService.class, AssetVocabularyService.class,
			AssetCategoryLocalService.class, AssetCategoryService.class,
			AssetTagLocalService.class, AssetTagService.class,
			AssetTagStatsLocalService.class);

		templateVariableGroups.put(
			assetServicesTemplateVariableGroup.getLabel(),
			assetServicesTemplateVariableGroup);

		return templateVariableGroups;
	}

	@Override
	protected String getTemplatesConfigPath() {
		return PropsValues.ASSET_PUBLISHER_DISPLAY_TEMPLATES_CONFIG;
	}

}