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

package com.liferay.wiki.web.internal.portlet.display.template;

import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Release;
import com.liferay.portal.kernel.portletdisplaytemplate.BasePortletDisplayTemplateHandler;
import com.liferay.portal.kernel.template.TemplateHandler;
import com.liferay.portal.kernel.template.TemplateVariableGroup;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portlet.display.template.constants.PortletDisplayTemplateConstants;
import com.liferay.wiki.constants.WikiPortletKeys;
import com.liferay.wiki.model.WikiPage;
import com.liferay.wiki.service.WikiNodeLocalService;
import com.liferay.wiki.service.WikiNodeService;
import com.liferay.wiki.service.WikiPageLocalService;
import com.liferay.wiki.service.WikiPageService;

import java.util.Locale;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Juan Fernández
 */
@Component(
	immediate = true, property = "javax.portlet.name=" + WikiPortletKeys.WIKI,
	service = TemplateHandler.class
)
public class WikiPortletDisplayTemplateHandler
	extends BasePortletDisplayTemplateHandler {

	@Override
	public String getClassName() {
		return WikiPage.class.getName();
	}

	@Override
	public String getName(Locale locale) {
		String portletTitle = _portal.getPortletTitle(
			WikiPortletKeys.WIKI,
			ResourceBundleUtil.getBundle(
				"content.Language", locale, getClass()));

		return LanguageUtil.format(locale, "x-template", portletTitle, false);
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

		TemplateVariableGroup wikiServicesTemplateVariableGroup =
			new TemplateVariableGroup(
				"wiki-services", getRestrictedVariables(language));

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
		return "com/liferay/wiki/web/portlet/display/template/dependencies" +
			"/portlet-display-templates.xml";
	}

	@Reference
	private Portal _portal;

	@Reference(
		target = "(&(release.bundle.symbolic.name=com.liferay.wiki.service)(release.schema.version>=0.0.3))"
	)
	private Release _release;

}