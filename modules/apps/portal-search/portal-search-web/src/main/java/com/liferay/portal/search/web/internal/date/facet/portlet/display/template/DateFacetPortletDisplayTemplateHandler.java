/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.web.internal.date.facet.portlet.display.template;

import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.template.TemplateHandler;
import com.liferay.portal.kernel.template.TemplateVariableGroup;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.search.web.internal.date.facet.configuration.DateFacetWebTemplateConfiguration;
import com.liferay.portal.search.web.internal.date.facet.constants.DateFacetPortletKeys;
import com.liferay.portal.search.web.internal.date.facet.display.context.DateFacetDisplayContext;
import com.liferay.portal.search.web.internal.date.facet.portlet.DateFacetPortlet;
import com.liferay.portlet.display.template.BasePortletDisplayTemplateHandler;
import com.liferay.portlet.display.template.constants.PortletDisplayTemplateConstants;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Petteri Karttunen
 */
@Component(
	configurationPid = "com.liferay.portal.search.web.internal.date.facet.configuration.DateFacetWebTemplateConfiguration",
	property = "javax.portlet.name=" + DateFacetPortletKeys.DATE_FACET,
	service = TemplateHandler.class
)
public class DateFacetPortletDisplayTemplateHandler
	extends BasePortletDisplayTemplateHandler {

	@Override
	public String getClassName() {
		return DateFacetPortlet.class.getName();
	}

	@Override
	public String getDefaultTemplateKey() {
		return _dateFacetWebTemplateConfiguration.dateFacetTemplateKeyDefault();
	}

	@Override
	public String getName(Locale locale) {
		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			"content.Language", locale, getClass());

		return _language.format(
			locale, "x-template",
			_portal.getPortletTitle(
				DateFacetPortletKeys.DATE_FACET, resourceBundle),
			false);
	}

	@Override
	public String getResourceName() {
		return DateFacetPortletKeys.DATE_FACET;
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
			"date-facet-display-context", DateFacetDisplayContext.class,
			"dateFacetDisplayContext");
		templateVariableGroup.addVariable(
			"term-field-name", String.class,
			PortletDisplayTemplateConstants.ENTRY, "getBucketText()");
		templateVariableGroup.addVariable(
			"term-frequency", Integer.class,
			PortletDisplayTemplateConstants.ENTRY, "getFrequency()");
		templateVariableGroup.addVariable(
			"term-name", String.class, PortletDisplayTemplateConstants.ENTRY,
			"getLabel()");
		templateVariableGroup.addCollectionVariable(
			"terms", List.class, PortletDisplayTemplateConstants.ENTRIES,
			"term", DateFacetDisplayContext.class,
			PortletDisplayTemplateConstants.ENTRY, "getLabel()");

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
		_dateFacetWebTemplateConfiguration =
			ConfigurableUtil.createConfigurable(
				DateFacetWebTemplateConfiguration.class, properties);
	}

	@Override
	protected String getTemplatesConfigPath() {
		return "com/liferay/portal/search/web/internal/date/facet/portlet" +
			"/display/template/dependencies/portlet-display-templates.xml";
	}

	private volatile DateFacetWebTemplateConfiguration
		_dateFacetWebTemplateConfiguration;

	@Reference
	private Language _language;

	@Reference
	private Portal _portal;

}