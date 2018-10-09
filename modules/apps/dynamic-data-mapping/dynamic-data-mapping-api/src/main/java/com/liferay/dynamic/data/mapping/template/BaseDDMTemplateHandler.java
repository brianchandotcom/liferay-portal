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

package com.liferay.dynamic.data.mapping.template;

import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalServiceUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.mobile.device.Device;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.template.BaseTemplateHandler;
import com.liferay.portal.kernel.template.TemplateVariableCodeHandler;
import com.liferay.portal.kernel.template.TemplateVariableGroup;
import com.liferay.portal.kernel.templateparser.TemplateNode;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @author Jorge Ferrer
 * @author Marcellus Tavares
 */
public abstract class BaseDDMTemplateHandler extends BaseTemplateHandler {

	@Override
	public Map<String, TemplateVariableGroup> getTemplateVariableGroups(
			long classPK, String language, Locale locale)
		throws Exception {

		Map<String, TemplateVariableGroup> templateVariableGroups =
			new LinkedHashMap<>();

		addTemplateVariableGroup(
			templateVariableGroups, getGeneralVariablesTemplateVariableGroup());
		addTemplateVariableGroup(
			templateVariableGroups,
			getStructureFieldsTemplateVariableGroup(classPK, locale));
		addTemplateVariableGroup(
			templateVariableGroups, getUtilTemplateVariableGroup());

		return templateVariableGroups;
	}

	@Override
	public boolean isDisplayTemplateHandler() {
		return false;
	}

	protected void addTemplateVariableGroup(
		Map<String, TemplateVariableGroup> templateVariableGroups,
		TemplateVariableGroup templateVariableGroup) {

		if (templateVariableGroup == null) {
			return;
		}

		templateVariableGroups.put(
			templateVariableGroup.getLabel(), templateVariableGroup);
	}

	protected void collectNestedFieldNameVariableName(
			DDMFormField parentDDMFormField,
			Map<String, String> fieldNameVariableNameMap)
		throws PortalException {

		List<DDMFormField> childrenDDMFormFields =
			parentDDMFormField.getNestedDDMFormFields();

		if (ListUtil.isEmpty(childrenDDMFormFields)) {
			return;
		}

		String parentVariableName = fieldNameVariableNameMap.get(
			parentDDMFormField.getName());

		for (DDMFormField childDDMFormField : childrenDDMFormFields) {
			fieldNameVariableNameMap.put(
				childDDMFormField.getName(),
				parentVariableName + StringPool.PERIOD +
					childDDMFormField.getName());

			collectNestedFieldNameVariableName(
				childDDMFormField, fieldNameVariableNameMap);
		}
	}

	protected Class<?> getFieldVariableClass() {
		return TemplateNode.class;
	}

	protected TemplateVariableGroup getGeneralVariablesTemplateVariableGroup() {
		TemplateVariableGroup templateVariableGroup = new TemplateVariableGroup(
			"general-variables");

		templateVariableGroup.addVariable("device", Device.class, "device");
		templateVariableGroup.addVariable(
			"portal-instance", Company.class, "company");
		templateVariableGroup.addVariable(
			"portal-instance-id", null, "companyId");
		templateVariableGroup.addVariable("site-id", null, "groupId");
		templateVariableGroup.addVariable(
			"view-mode", String.class, "viewMode");

		return templateVariableGroup;
	}

	protected TemplateVariableGroup getStructureFieldsTemplateVariableGroup(
			long ddmStructureId, Locale locale)
		throws PortalException {

		if (ddmStructureId <= 0) {
			return null;
		}

		TemplateVariableGroup templateVariableGroup = new TemplateVariableGroup(
			"fields");

		DDMStructure ddmStructure = DDMStructureLocalServiceUtil.getStructure(
			ddmStructureId);

		DDMForm fullHierarchyDDMForm = ddmStructure.getFullHierarchyDDMForm();

		Map<String, String> fieldNameVariableNameMap = new LinkedHashMap<>();

		for (DDMFormField ddmFormField :
				fullHierarchyDDMForm.getDDMFormFields()) {

			fieldNameVariableNameMap.put(
				ddmFormField.getName(), ddmFormField.getName());

			collectNestedFieldNameVariableName(
				ddmFormField, fieldNameVariableNameMap);
		}

		for (Map.Entry<String, String> fieldNameVariableName :
				fieldNameVariableNameMap.entrySet()) {

			String fieldName = fieldNameVariableName.getKey();

			String dataType = ddmStructure.getFieldDataType(fieldName);

			if (Validator.isNull(dataType)) {
				continue;
			}

			String label = ddmStructure.getFieldLabel(fieldName, locale);
			String tip = ddmStructure.getFieldTip(fieldName, locale);
			boolean repeatable = ddmStructure.getFieldRepeatable(fieldName);

			templateVariableGroup.addFieldVariable(
				label, getFieldVariableClass(),
				fieldNameVariableName.getValue(), tip, dataType, repeatable,
				getTemplateVariableCodeHandler());
		}

		return templateVariableGroup;
	}

	protected abstract TemplateVariableCodeHandler
		getTemplateVariableCodeHandler();

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