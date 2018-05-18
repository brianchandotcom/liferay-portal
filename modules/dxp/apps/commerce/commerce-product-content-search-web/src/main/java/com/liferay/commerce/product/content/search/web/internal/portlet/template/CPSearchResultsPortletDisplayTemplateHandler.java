/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.commerce.product.content.search.web.internal.portlet.template;

import com.liferay.commerce.product.constants.CPPortletKeys;
import com.liferay.commerce.product.content.search.web.internal.display.context.CPSearchResultsDisplayContext;
import com.liferay.commerce.product.content.search.web.internal.portlet.CPSearchResultsPortlet;
import com.liferay.commerce.product.service.CPDefinitionLocalService;
import com.liferay.commerce.product.service.CPDefinitionService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portletdisplaytemplate.BasePortletDisplayTemplateHandler;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.template.TemplateHandler;
import com.liferay.portal.kernel.template.TemplateVariableGroup;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portlet.display.template.PortletDisplayTemplateConstants;

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
	immediate = true,
	property = "javax.portlet.name=" + CPPortletKeys.CP_SEARCH_RESULTS,
	service = TemplateHandler.class
)
public class CPSearchResultsPortletDisplayTemplateHandler
	extends BasePortletDisplayTemplateHandler {

	@Override
	public String getClassName() {
		return CPSearchResultsPortlet.class.getName();
	}

	@Override
	public String getName(Locale locale) {
		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			"content.Language", locale, getClass());

		String portletTitle = _portal.getPortletTitle(
			CPPortletKeys.CP_SEARCH_RESULTS, resourceBundle);

		return portletTitle.concat(StringPool.SPACE).concat(
			LanguageUtil.get(locale, "template"));
	}

	@Override
	public String getResourceName() {
		return CPPortletKeys.CP_SEARCH_RESULTS;
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
			"cp-search-results-display-context",
			CPSearchResultsDisplayContext.class,
			"cpSearchResultsDisplayContext");
		templateVariableGroup.addCollectionVariable(
			"documents", List.class, PortletDisplayTemplateConstants.ENTRIES,
			"document", Document.class, "curDocument", "title");

		String[] restrictedVariables = getRestrictedVariables(language);

		TemplateVariableGroup cpDefinitionsServicesTemplateVariableGroup =
			new TemplateVariableGroup(
				"cp-definition-services", restrictedVariables);

		cpDefinitionsServicesTemplateVariableGroup.setAutocompleteEnabled(
			false);

		cpDefinitionsServicesTemplateVariableGroup.addServiceLocatorVariables(
			CPDefinitionLocalService.class, CPDefinitionService.class);

		templateVariableGroups.put(
			cpDefinitionsServicesTemplateVariableGroup.getLabel(),
			cpDefinitionsServicesTemplateVariableGroup);

		return templateVariableGroups;
	}

	@Override
	protected String getTemplatesConfigPath() {
		return "com/liferay/commerce/product/content/search/web/portlet" +
			"/template/dependencies/portlet-display-templates.xml";
	}

	@Reference
	private Portal _portal;

}