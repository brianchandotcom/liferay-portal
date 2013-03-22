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

package com.liferay.portlet.journal.template;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.mobile.device.Device;
import com.liferay.portal.kernel.template.BaseTemplateHandler;
import com.liferay.portal.kernel.template.TemplateVariableGroup;
import com.liferay.portal.kernel.templateparser.TemplateNode;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.Company;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructure;
import com.liferay.portlet.dynamicdatamapping.service.DDMStructureLocalServiceUtil;
import com.liferay.portlet.journal.model.JournalArticle;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @author Jorge Ferrer
 */
public class JournalTemplateHandler extends BaseTemplateHandler {

	public String getClassName() {
		return JournalArticle.class.getName();
	}

	public String getName(Locale locale) {
		String portletTitle = PortalUtil.getPortletTitle(
			PortletKeys.JOURNAL, locale);

		return portletTitle.concat(StringPool.SPACE).concat(
			LanguageUtil.get(locale, "template"));
	}

	public String getResourceName() {
		return "com.liferay.portlet.journal";
	}

	public Map<String, TemplateVariableGroup> getTemplateVariableGroups(
			long classPK, Locale locale)
		throws Exception {

		Map<String, TemplateVariableGroup> templateVariableGroups =
			new LinkedHashMap<String, TemplateVariableGroup>();

		templateVariableGroups.put(
			"fields", getStructureFieldsTemplateVariableGroup(classPK, locale));

		templateVariableGroups.put(
			"general-variables", getGeneralVariablesTemplateVariableGroup());

		templateVariableGroups.put("util", getUtilTemplateVariableGroup());

		return templateVariableGroups;

	}

	protected TemplateVariableGroup getGeneralVariablesTemplateVariableGroup() {
		TemplateVariableGroup generalVariablesTemplateVariableGroup =
			new TemplateVariableGroup("general-variables");

		generalVariablesTemplateVariableGroup.addVariable(
			"portal-instance", Company.class, "company");
		generalVariablesTemplateVariableGroup.addVariable(
			"portal-instance-id", null, "companyId");
		generalVariablesTemplateVariableGroup.addVariable(
			"device", Device.class, "device");
		generalVariablesTemplateVariableGroup.addVariable(
			"site-id", null, "groupId");
		generalVariablesTemplateVariableGroup.addVariable(
			"view-mode", String.class, "viewMode");

		return generalVariablesTemplateVariableGroup;
	}

	protected TemplateVariableGroup getStructureFieldsTemplateVariableGroup(
			long ddmStructureId, Locale locale)
		throws PortalException, SystemException {

		TemplateVariableGroup fieldsTemplateVariableGroup =
			new TemplateVariableGroup("fields");

		DDMStructure ddmStructure = DDMStructureLocalServiceUtil.getStructure(
			ddmStructureId);

		List<String> fieldNames = ddmStructure.getRootFieldNames();

		for (String fieldName : fieldNames) {
			if (fieldName.startsWith(StringPool.UNDERLINE)) {
				continue;
			}

			String label = ddmStructure.getFieldLabel(fieldName, locale);
			String dataType = ddmStructure.getFieldDataType(fieldName);
			String tip = ddmStructure.getFieldTip(fieldName, locale);
			boolean repeatable = ddmStructure.getFieldRepeatable(fieldName);

			fieldsTemplateVariableGroup.addFieldVariable(
				label, TemplateNode.class, fieldName, tip, dataType,
				repeatable);
		}

		return fieldsTemplateVariableGroup;
	}

	protected TemplateVariableGroup getUtilTemplateVariableGroup() {
		TemplateVariableGroup utilTemplateVariableGroup =
			new TemplateVariableGroup("util");

		utilTemplateVariableGroup.addVariable(
			"permission-checker", PermissionChecker.class, "permissionChecker");
		utilTemplateVariableGroup.addVariable(
			"random-namespace", String.class, "randomNamespace");
		utilTemplateVariableGroup.addVariable(
			"templates-path", String.class, "templatesPath");
		utilTemplateVariableGroup.addVariable(
			"xml-request", String.class, "xmlRequest");

		return utilTemplateVariableGroup;
	}

}