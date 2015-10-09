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

package com.liferay.wiki.web.wiki.portlet.template;

import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portletdisplaytemplate.BasePortletDisplayTemplateHandler;
import com.liferay.portal.kernel.template.TemplateVariableGroup;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.asset.model.AssetEntry;
import com.liferay.portlet.display.template.PortletDisplayTemplateConstants;
import com.liferay.wiki.constants.WikiPortletKeys;
import com.liferay.wiki.model.WikiPage;
import com.liferay.wiki.service.WikiNodeLocalService;
import com.liferay.wiki.service.WikiNodeService;
import com.liferay.wiki.service.WikiPageLocalService;
import com.liferay.wiki.service.WikiPageService;

import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * @author Juan Fernández
 */
public class WikiPortletDisplayTemplateHandler
	extends BasePortletDisplayTemplateHandler {

	@Override
	public String getClassName() {
		return WikiPage.class.getName();
	}

	@Override
	public String getName(Locale locale) {
		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			"content.Language", locale, getClass());

		String portletTitle = PortalUtil.getPortletTitle(
			WikiPortletKeys.WIKI, resourceBundle);

		return portletTitle.concat(StringPool.SPACE).concat(
			LanguageUtil.get(locale, "template"));
	}

	@Override
	public String getResourceName() {
		return WikiPortletKeys.WIKI;
	}

	@Override
	public Map<String, TemplateVariableGroup> getTemplateVariableGroups(
			long classPK, String language, Locale locale)
		throws Exception {

		Map<String, TemplateVariableGroup> templateVariableGroups =
			super.getTemplateVariableGroups(classPK, language, locale);

		TemplateVariableGroup fieldsTemplateVariableGroup =
			templateVariableGroups.get("fields");

		fieldsTemplateVariableGroup.empty();

		fieldsTemplateVariableGroup.addVariable(
			"asset-entry", AssetEntry.class, "assetEntry");
		fieldsTemplateVariableGroup.addVariable(
			"wiki-page", WikiPage.class, PortletDisplayTemplateConstants.ENTRY);
		fieldsTemplateVariableGroup.addVariable(
			"wiki-page-content", String.class, "formattedContent");

		String[] restrictedVariables = getRestrictedVariables(language);

		TemplateVariableGroup wikiServicesTemplateVariableGroup =
			new TemplateVariableGroup("wiki-services", restrictedVariables);

		wikiServicesTemplateVariableGroup.setAutocompleteEnabled(false);

		wikiServicesTemplateVariableGroup.addServiceLocatorVariables(
			WikiPageLocalService.class, WikiPageService.class,
			WikiNodeLocalService.class, WikiNodeService.class);

		templateVariableGroups.put(
			wikiServicesTemplateVariableGroup.getLabel(),
			wikiServicesTemplateVariableGroup);

		return templateVariableGroups;
	}

	@Override
	protected String getTemplatesConfigPath() {
		return "com/liferay/wiki/dependencies/portlet-display-templates.xml";
	}

}