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

package com.liferay.portlet.dynamicdatalists.template;

import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.template.TemplateVariableCodeHandler;
import com.liferay.portal.kernel.template.TemplateVariableGroup;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portlet.dynamicdatalists.model.DDLRecord;
import com.liferay.portlet.dynamicdatalists.model.DDLRecordSet;
import com.liferay.portlet.dynamicdatalists.service.DDLRecordLocalService;
import com.liferay.portlet.dynamicdatalists.service.DDLRecordService;
import com.liferay.portlet.dynamicdatalists.service.DDLRecordSetLocalService;
import com.liferay.portlet.dynamicdatalists.service.DDLRecordSetService;
import com.liferay.portlet.dynamicdatalists.util.DDLConstants;
import com.liferay.portlet.dynamicdatamapping.service.DDMStructureLocalService;
import com.liferay.portlet.dynamicdatamapping.service.DDMStructureService;
import com.liferay.portlet.dynamicdatamapping.service.DDMTemplateLocalService;
import com.liferay.portlet.dynamicdatamapping.service.DDMTemplateService;
import com.liferay.portlet.dynamicdatamapping.storage.Field;
import com.liferay.portlet.dynamicdatamapping.template.BaseDDMTemplateHandler;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @author Jorge Ferrer
 * @author Marcellus Tavares
 */
public class DDLTemplateHandler extends BaseDDMTemplateHandler {

	@Override
	public String getClassName() {
		return DDLRecordSet.class.getName();
	}

	@Override
	public String getName(Locale locale) {
		String portletTitle = PortalUtil.getPortletTitle(
			PortletKeys.DYNAMIC_DATA_LISTS, locale);

		return portletTitle.concat(StringPool.SPACE).concat(
			LanguageUtil.get(locale, "template"));
	}

	@Override
	public String getResourceName() {
		return "com.liferay.portlet.dynamicdatalists";
	}

	@Override
	public Map<String, TemplateVariableGroup> getTemplateVariableGroups(
			long classPK, String language, Locale locale)
		throws Exception {

		Map<String, TemplateVariableGroup> templateVariableGroups =
			new LinkedHashMap<String, TemplateVariableGroup>();

		addTemplateVariableGroup(
			templateVariableGroups, getDDLVariablesTemplateVariableGroups());
		addTemplateVariableGroup(
			templateVariableGroups, getGeneralVariablesTemplateVariableGroup());

		TemplateVariableGroup structureFieldsTemplateVariableGroup =
			getStructureFieldsTemplateVariableGroup(classPK, locale);

		structureFieldsTemplateVariableGroup.setLabel(
			"data-list-record-fields");

		addTemplateVariableGroup(
			templateVariableGroups, structureFieldsTemplateVariableGroup);

		addTemplateVariableGroup(
			templateVariableGroups, getUtilTemplateVariableGroup());

		TemplateVariableGroup ddlServicesTemplateVariableGroup =
			new TemplateVariableGroup("data-list-services");

		ddlServicesTemplateVariableGroup.setAutocompleteEnabled(false);

		ddlServicesTemplateVariableGroup.addServiceLocatorVariables(
			DDLRecordLocalService.class, DDLRecordService.class,
			DDLRecordSetLocalService.class, DDLRecordSetService.class,
			DDMStructureLocalService.class, DDMStructureService.class,
			DDMTemplateLocalService.class, DDMTemplateService.class);

		templateVariableGroups.put(
			ddlServicesTemplateVariableGroup.getLabel(),
			ddlServicesTemplateVariableGroup);

		return templateVariableGroups;
	}

	protected TemplateVariableGroup getDDLVariablesTemplateVariableGroups() {
		TemplateVariableGroup templateVariableGroup = new TemplateVariableGroup(
			"data-list-variables");

		templateVariableGroup.addVariable(
			"data-definition-id", null, DDLConstants.RESERVED_DDM_STRUCTURE_ID);
		templateVariableGroup.addVariable(
			"data-list-description", String.class,
			DDLConstants.RESERVED_RECORD_SET_DESCRIPTION);
		templateVariableGroup.addVariable(
			"data-list-id", null, DDLConstants.RESERVED_RECORD_SET_ID);
		templateVariableGroup.addVariable(
			"data-list-name", String.class,
			DDLConstants.RESERVED_RECORD_SET_NAME);
		templateVariableGroup.addCollectionVariable(
			"data-list-records", List.class, "records", "record",
			DDLRecord.class, "curRecord");
		templateVariableGroup.addVariable(
			"template-id", null, DDLConstants.RESERVED_DDM_TEMPLATE_ID);

		return templateVariableGroup;
	}

	@Override
	protected Class<?> getFieldVariableClass() {
		return Field.class;
	}

	@Override
	protected TemplateVariableCodeHandler getTemplateVariableCodeHandler() {
		return null;
	}

}