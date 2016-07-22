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

package com.liferay.rss.web.internal.portlet.template;

import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portletdisplaytemplate.BasePortletDisplayTemplateHandler;
import com.liferay.portal.kernel.template.TemplateHandler;
import com.liferay.portal.kernel.template.TemplateVariableGroup;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portlet.display.template.PortletDisplayTemplateConstants;
import com.liferay.rss.web.configuration.RSSWebConfigurationValues;
import com.liferay.rss.web.constants.RSSPortletKeys;
import com.liferay.rss.web.internal.display.context.RSSDisplayContext;
import com.liferay.rss.web.internal.util.RSSFeed;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import org.osgi.service.component.annotations.Component;

/**
 * @author Eudaldo Alonso
 */
@Component(
	immediate = true, property = {"javax.portlet.name=" + RSSPortletKeys.RSS},
	service = TemplateHandler.class
)
public class RSSPortletDisplayTemplateHandler
	extends BasePortletDisplayTemplateHandler {

	@Override
	public String getClassName() {
		return RSSFeed.class.getName();
	}

	@Override
	public String getName(Locale locale) {
		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			"content.Language", getClass());

		String portletTitle = PortalUtil.getPortletTitle(
			RSSPortletKeys.RSS, resourceBundle);

		return portletTitle.concat(StringPool.SPACE).concat(
			LanguageUtil.get(locale, "template"));
	}

	@Override
	public String getResourceName() {
		return RSSPortletKeys.RSS;
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
			"rss-display-context", RSSDisplayContext.class,
			"rssDisplayContext");
		templateVariableGroup.addCollectionVariable(
			"rss-feeds", List.class, PortletDisplayTemplateConstants.ENTRIES,
			"rss-feed", RSSFeed.class, "curEntry", "getSyndFeed().getTitle()");

		return templateVariableGroups;
	}

	@Override
	protected String getTemplatesConfigPath() {
		return RSSWebConfigurationValues.DISPLAY_TEMPLATES_CONFIG;
	}

}