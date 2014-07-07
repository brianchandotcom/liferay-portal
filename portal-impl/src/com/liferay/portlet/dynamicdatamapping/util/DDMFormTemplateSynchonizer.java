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

package com.liferay.portlet.dynamicdatamapping.util;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portlet.dynamicdatamapping.io.DDMFormXSDDeserializerUtil;
import com.liferay.portlet.dynamicdatamapping.io.DDMFormXSDSerializerUtil;
import com.liferay.portlet.dynamicdatamapping.model.DDMForm;
import com.liferay.portlet.dynamicdatamapping.model.DDMFormField;
import com.liferay.portlet.dynamicdatamapping.model.DDMTemplate;
import com.liferay.portlet.dynamicdatamapping.model.DDMTemplateConstants;
import com.liferay.portlet.dynamicdatamapping.service.DDMTemplateLocalServiceUtil;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @author Marcellus Tavares
 */
public class DDMFormTemplateSynchonizer {

	public DDMFormTemplateSynchonizer(DDMForm structureDDMForm) {
		_structureDDMForm = structureDDMForm;
	}

	public void setDDMFormTemplates(List<DDMTemplate> ddmFormTemplates) {
		_ddmFormTemplates = ddmFormTemplates;
	}

	public void synchronize() throws Exception {
		syncStructureTemplatesFields();
	}

	protected void appendNewStructureRequiredFields(
		List<DDMFormField> structureDDMFormFields,
		List<DDMFormField> templateDDMFormFields) {

		for (int i = 0; i < structureDDMFormFields.size(); i++) {
			DDMFormField structureDDMFormField = structureDDMFormFields.get(i);

			List<String> templateDDMFormFieldNames = getDDMFormFieldNames(
				templateDDMFormFields);

			if (!templateDDMFormFieldNames.contains(
					structureDDMFormField.getName())) {

				if (structureDDMFormField.isRequired()) {
					templateDDMFormFields.add(i, structureDDMFormField);
				}
			}
			else {
				DDMFormField templateDDMFormField = templateDDMFormFields.get(
					i);

				appendNewStructureRequiredFields(
					structureDDMFormField.getNestedDDMFormFields(),
					templateDDMFormField.getNestedDDMFormFields());
			}
		}
	}

	protected List<String> getDDMFormFieldNames(
		List<DDMFormField> ddmFormFields) {

		List<String> ddmFormFieldNames = new ArrayList<String>();

		for (DDMFormField ddmFormField : ddmFormFields) {
			ddmFormFieldNames.add(ddmFormField.getName());
		}

		return ddmFormFieldNames;
	}

	protected List<DDMTemplate> getDDMFormTemplates() {
		return _ddmFormTemplates;
	}

	protected String getDDMFormTemplateScript(DDMForm templateDDMForm) {
		String script = DDMFormXSDSerializerUtil.serialize(templateDDMForm);

		return DDMXMLUtil.formatXML(script);
	}

	protected void syncStructureTemplatesFields() throws PortalException {
		List<DDMTemplate> ddmTemplates = getDDMFormTemplates();

		for (DDMTemplate ddmTemplate : ddmTemplates) {
			DDMForm templateDDMForm = DDMFormXSDDeserializerUtil.deserialize(
				ddmTemplate.getScript());

			syncStructureTemplatesFields(
				_structureDDMForm, templateDDMForm.getDDMFormFields(),
				ddmTemplate.getMode());

			if (ddmTemplate.getMode().equals(
					DDMTemplateConstants.TEMPLATE_MODE_CREATE)) {

				appendNewStructureRequiredFields(
						_structureDDMForm.getDDMFormFields(),
						templateDDMForm.getDDMFormFields());
			}

			updateDDMTemplate(ddmTemplate, templateDDMForm);
		}
	}

	protected void syncStructureTemplatesFields(
		DDMForm structureDDMForm, List<DDMFormField> templateDDMFormFields,
		String templateMode) {

		Map<String, DDMFormField> structureDDMFormFieldsMap =
			structureDDMForm.getDDMFormFieldsMap(false);

		Iterator<DDMFormField> itr = templateDDMFormFields.iterator();

		while (itr.hasNext()) {
			DDMFormField templateDDMFormField = itr.next();

			String name = templateDDMFormField.getName();

			if (!structureDDMFormFieldsMap.containsKey(name)) {
				itr.remove();

				continue;
			}

			if (templateMode.equals(
					DDMTemplateConstants.TEMPLATE_MODE_CREATE)) {

				DDMFormField structureDDMFormField =
					structureDDMFormFieldsMap.get(name);

				templateDDMFormField.setRequired(
					structureDDMFormField.isRequired());
			}

			syncStructureTemplatesFields(
				structureDDMForm, templateDDMFormField.getNestedDDMFormFields(),
				templateMode);
		}
	}

	protected void updateDDMTemplate(
		DDMTemplate ddmTemplate, DDMForm templateDDMForm) {

		String script = getDDMFormTemplateScript(templateDDMForm);

		ddmTemplate.setScript(script);

		DDMTemplateLocalServiceUtil.updateDDMTemplate(ddmTemplate);
	}

	private List<DDMTemplate> _ddmFormTemplates;
	private DDMForm _structureDDMForm;

}