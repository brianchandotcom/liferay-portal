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

package com.liferay.portal.search.web.internal.search.results.portlet.display.template;

import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portletdisplaytemplate.BasePortletDisplayTemplateHandler;
import com.liferay.portal.kernel.template.TemplateHandler;
import com.liferay.portal.kernel.template.TemplateVariableGroup;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.search.web.internal.result.display.context.SearchResultSummaryDisplayContext;
import com.liferay.portal.search.web.internal.search.results.configuration.SearchResultsWebTemplateConfiguration;
import com.liferay.portal.search.web.internal.search.results.constants.SearchResultsPortletKeys;
import com.liferay.portal.search.web.internal.search.results.portlet.SearchResultsPortletDisplayContext;
import com.liferay.portlet.display.template.constants.PortletDisplayTemplateConstants;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Kevin Tan
 */
@Component(
	configurationPid = "com.liferay.portal.search.web.internal.search.results.configuration.SearchResultsWebTemplateConfiguration",
	configurationPolicy = ConfigurationPolicy.OPTIONAL, immediate = true,
	property = "javax.portlet.name=" + SearchResultsPortletKeys.SEARCH_RESULTS,
	service = TemplateHandler.class
)
public class SearchResultsPortletDisplayTemplateHandler
	extends BasePortletDisplayTemplateHandler {

	@Override
	public String getClassName() {
		return SearchResultSummaryDisplayContext.class.getName();
	}

	@Override
	public String getDefaultTemplateKey() {
		return _searchResultsWebTemplateConfiguration.
			searchResultsTemplateKeyDefault();
	}

	@Override
	public String getName(Locale locale) {
		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			"content.Language", locale, getClass());

		String portletTitle = _portal.getPortletTitle(
			SearchResultsPortletKeys.SEARCH_RESULTS, resourceBundle);

		return LanguageUtil.format(locale, "x-template", portletTitle, false);
	}

	@Override
	public String getResourceName() {
		return SearchResultsPortletKeys.SEARCH_RESULTS;
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
			"document-content", SearchResultSummaryDisplayContext.class,
			PortletDisplayTemplateConstants.ENTRY, "getContent()");
		templateVariableGroup.addVariable(
			"document-creation-date", SearchResultSummaryDisplayContext.class,
			PortletDisplayTemplateConstants.ENTRY, "getCreationDateString()");
		templateVariableGroup.addVariable(
			"document-creator-user-name",
			SearchResultSummaryDisplayContext.class,
			PortletDisplayTemplateConstants.ENTRY, "getCreatorUserName()");
		templateVariableGroup.addVariable(
			"document-title", SearchResultSummaryDisplayContext.class,
			PortletDisplayTemplateConstants.ENTRY, "getHighlightedTitle()");
		templateVariableGroup.addCollectionVariable(
			"documents", List.class, PortletDisplayTemplateConstants.ENTRIES,
			"document", SearchResultSummaryDisplayContext.class,
			PortletDisplayTemplateConstants.ENTRY, "getHighlightedTitle()");
		templateVariableGroup.addVariable(
			"search-container", SearchContainer.class, "searchContainer");
		templateVariableGroup.addVariable(
			"search-results-display-context",
			SearchResultsPortletDisplayContext.class,
			"searchResultsPortletDisplayContext");

		TemplateVariableGroup categoriesServicesTemplateVariableGroup =
			new TemplateVariableGroup(
				"category-services", getRestrictedVariables(language));

		categoriesServicesTemplateVariableGroup.setAutocompleteEnabled(false);

		templateVariableGroups.put(
			categoriesServicesTemplateVariableGroup.getLabel(),
			categoriesServicesTemplateVariableGroup);

		return templateVariableGroups;
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		_searchResultsWebTemplateConfiguration =
			ConfigurableUtil.createConfigurable(
				SearchResultsWebTemplateConfiguration.class, properties);
	}

	@Override
	protected String getTemplatesConfigPath() {
		return "com/liferay/portal/search/web/internal/search/results/web" +
			"/portlet/display/template/dependencies/portlet-display-" +
				"templates.xml";
	}

	@Reference
	private Portal _portal;

	private volatile SearchResultsWebTemplateConfiguration
		_searchResultsWebTemplateConfiguration;

}