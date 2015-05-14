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

package com.liferay.dynamic.data.lists.display.web.template;

import com.liferay.dynamic.data.lists.display.web.configuration.DDLDisplayWebConfigurationValues;
import com.liferay.dynamic.data.lists.display.web.constants.DDLDisplayPortletKeys;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portletdisplaytemplate.BasePortletDisplayTemplateHandler;
import com.liferay.portal.kernel.template.TemplateHandler;
import com.liferay.portal.kernel.template.TemplateVariableGroup;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.dynamicdatalists.model.DDLRecord;
import com.liferay.portlet.dynamicdatalists.model.DDLRecordSet;
import com.liferay.portlet.dynamicdatalists.service.DDLRecordLocalService;
import com.liferay.portlet.dynamicdatalists.service.DDLRecordService;
import com.liferay.portlet.dynamicdatalists.service.DDLRecordSetLocalService;
import com.liferay.portlet.dynamicdatalists.service.DDLRecordSetService;
import com.liferay.portlet.portletdisplaytemplate.util.PortletDisplayTemplateConstants;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import org.osgi.service.component.annotations.Component;

/**
 * @author Marcellus Tavares
 */
@Component(
	immediate = true,
	property = {"javax.portlet.name="+ DDLDisplayPortletKeys.DDL_DISPLAY},
	service = TemplateHandler.class
)
public class DDLDisplayPortletDisplayTemplateHandler
	extends BasePortletDisplayTemplateHandler {

	@Override
	public String getClassName() {
		return DDLRecord.class.getName();
	}

	@Override
	public String getName(Locale locale) {
		ResourceBundle resourceBundle = ResourceBundle.getBundle(
			"content.Language", locale);

		String portletTitle = PortalUtil.getPortletTitle(
			DDLDisplayPortletKeys.DDL_DISPLAY, resourceBundle);

		return portletTitle.concat(StringPool.SPACE).concat(
			LanguageUtil.get(locale, "template"));
	}

	@Override
	public String getResourceName() {
		return DDLDisplayPortletKeys.DDL_DISPLAY;
	}

	@Override
	public Map<String, TemplateVariableGroup> getTemplateVariableGroups(
			long classPK, String language, Locale locale)
		throws Exception {

		Map<String, TemplateVariableGroup> templateVariableGroups =
			super.getTemplateVariableGroups(classPK, language, locale);

		TemplateVariableGroup fieldsTemplateVariableGroup =
			templateVariableGroups.get("fields");

		populateFieldsTemplateVariableGroup(fieldsTemplateVariableGroup);

		TemplateVariableGroup ddlServicesTemplateVariableGroup =
			createDDLServicesTemplateVariableGroup(language);

		templateVariableGroups.put(
			ddlServicesTemplateVariableGroup.getLabel(),
			ddlServicesTemplateVariableGroup);

		return templateVariableGroups;
	}

	protected TemplateVariableGroup createDDLServicesTemplateVariableGroup(
		String language) {

		String[] restrictedVariables = getRestrictedVariables(language);

		TemplateVariableGroup ddlServicesTemplateVariableGroup =
			new TemplateVariableGroup("ddl-services", restrictedVariables);

		ddlServicesTemplateVariableGroup.setAutocompleteEnabled(false);

		ddlServicesTemplateVariableGroup.addServiceLocatorVariables(
			DDLRecordLocalService.class, DDLRecordService.class,
			DDLRecordSetLocalService.class, DDLRecordSetService.class);

		return ddlServicesTemplateVariableGroup;
	}

	@Override
	protected String getTemplatesConfigPath() {
		return DDLDisplayWebConfigurationValues.DISPLAY_TEMPLATES_CONFIG;
	}

	protected void populateFieldsTemplateVariableGroup(
		TemplateVariableGroup fieldsTemplateVariableGroup) {

		fieldsTemplateVariableGroup.empty();

		fieldsTemplateVariableGroup.addCollectionVariable(
			"records", List.class, PortletDisplayTemplateConstants.ENTRIES,
			"record", DDLRecord.class, "curRecord", "name");

		fieldsTemplateVariableGroup.addVariable(
			"record-set", DDLRecordSet.class, "recordSet");

		fieldsTemplateVariableGroup.addVariable(
			"total-records", Integer.class, "total");
	}

}