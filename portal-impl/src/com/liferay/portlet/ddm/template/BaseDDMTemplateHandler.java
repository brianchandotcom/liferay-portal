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

package com.liferay.portlet.ddm.template;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.mobile.device.Device;
import com.liferay.portal.kernel.template.BaseTemplateHandler;
import com.liferay.portal.kernel.template.TemplateVariableGroup;
import com.liferay.portal.kernel.templateparser.TemplateNode;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.Company;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructure;
import com.liferay.portlet.dynamicdatamapping.service.DDMStructureLocalServiceUtil;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @author Jorge Ferrer
 */
public abstract class BaseDDMTemplateHandler extends BaseTemplateHandler {

	@Override
	public Map<String, TemplateVariableGroup> getTemplateVariableGroups(
			long classPK, String language, Locale locale)
		throws Exception {

		Map<String, TemplateVariableGroup> templateVariableGroups =
			new LinkedHashMap<String, TemplateVariableGroup>();

		if (classPK > 0) {
			templateVariableGroups.put(
				"fields",
				getStructureFieldsTemplateVariableGroup(classPK, locale));
		}

		templateVariableGroups.put(
			"general-variables", getGeneralVariablesTemplateVariableGroup());

		templateVariableGroups.put("util", getUtilTemplateVariableGroup());

		return templateVariableGroups;
	}

	protected TemplateVariableGroup getGeneralVariablesTemplateVariableGroup() {
		TemplateVariableGroup templateVariableGroup = new TemplateVariableGroup(
			"general-variables");

		templateVariableGroup.addVariable(
			"portal-instance", Company.class, "company");
		templateVariableGroup.addVariable(
			"portal-instance-id", null, "companyId");
		templateVariableGroup.addVariable("device", Device.class, "device");
		templateVariableGroup.addVariable("site-id", null, "groupId");
		templateVariableGroup.addVariable(
			"view-mode", String.class, "viewMode");

		return templateVariableGroup;
	}

	protected TemplateVariableGroup getStructureFieldsTemplateVariableGroup(
			long ddmStructureId, Locale locale)
		throws PortalException, SystemException {

		TemplateVariableGroup templateVariableGroup = new TemplateVariableGroup(
			"fields");

		DDMStructure ddmStructure = DDMStructureLocalServiceUtil.getStructure(
			ddmStructureId);

		List<String> fieldNames = ddmStructure.getRootFieldNames();

		for (String fieldName : fieldNames) {
			if (fieldName.startsWith(StringPool.UNDERLINE)) {
				continue;
			}

			String label = ddmStructure.getFieldLabel(fieldName, locale);
			String tip = ddmStructure.getFieldTip(fieldName, locale);
			String dataType = ddmStructure.getFieldDataType(fieldName);
			boolean repeatable = ddmStructure.getFieldRepeatable(fieldName);

			templateVariableGroup.addFieldVariable(
				label, TemplateNode.class, fieldName, tip, dataType,
				repeatable);
		}

		return templateVariableGroup;
	}

	protected TemplateVariableGroup getUtilTemplateVariableGroup() {
		TemplateVariableGroup templateVariableGroup = new TemplateVariableGroup(
			"util");

		templateVariableGroup.addVariable(
			"permission-checker", PermissionChecker.class, "permissionChecker");
		templateVariableGroup.addVariable(
			"random-namespace", String.class, "randomNamespace");
		templateVariableGroup.addVariable(
			"templates-path", String.class, "templatesPath");

		return templateVariableGroup;
	}

}