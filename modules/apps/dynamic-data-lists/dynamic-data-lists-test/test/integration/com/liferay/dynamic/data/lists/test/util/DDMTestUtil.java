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

package com.liferay.dynamic.data.lists.test.util;

import com.liferay.portal.kernel.template.TemplateConstants;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.dynamicdatamapping.model.DDMForm;
import com.liferay.portlet.dynamicdatamapping.model.DDMFormField;
import com.liferay.portlet.dynamicdatamapping.model.DDMFormLayout;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructure;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructureConstants;
import com.liferay.portlet.dynamicdatamapping.model.DDMTemplate;
import com.liferay.portlet.dynamicdatamapping.model.DDMTemplateConstants;
import com.liferay.portlet.dynamicdatamapping.model.LocalizedValue;
import com.liferay.portlet.dynamicdatamapping.model.UnlocalizedValue;
import com.liferay.portlet.dynamicdatamapping.model.Value;
import com.liferay.portlet.dynamicdatamapping.service.DDMStructureLocalServiceUtil;
import com.liferay.portlet.dynamicdatamapping.service.DDMTemplateLocalServiceUtil;
import com.liferay.portlet.dynamicdatamapping.storage.DDMFormFieldValue;
import com.liferay.portlet.dynamicdatamapping.storage.DDMFormValues;
import com.liferay.portlet.dynamicdatamapping.storage.StorageType;
import com.liferay.portlet.dynamicdatamapping.util.DDMUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

/**
 * @author Marcellus Tavares
 */
public class DDMTestUtil {

	public static DDMStructure addDDMStructure(
			long groupId, DDMForm ddmForm, String storageType)
		throws Exception {

		return addDDMStructure(
			groupId, PortalUtil.getClassNameId(StringUtil.randomString()),
			DDMStructureConstants.DEFAULT_PARENT_STRUCTURE_ID, ddmForm,
			storageType, ServiceContextTestUtil.getServiceContext());
	}

	public static DDMStructure addDDMStructure(
			long groupId, long classNameId, long parentStructureId,
			DDMForm ddmForm, String storageType, ServiceContext serviceContext)
		throws Exception {

		Map<Locale, String> nameMap = new HashMap<>();

		nameMap.put(ddmForm.getDefaultLocale(), "Test Structure");

		DDMFormLayout ddmFormLayout = DDMUtil.getDefaultDDMFormLayout(ddmForm);

		serviceContext.setAddGroupPermissions(true);
		serviceContext.setAddGuestPermissions(true);

		return DDMStructureLocalServiceUtil.addStructure(
			TestPropsValues.getUserId(), groupId, parentStructureId,
			classNameId, null, nameMap, null, ddmForm, ddmFormLayout,
			storageType, DDMStructureConstants.TYPE_DEFAULT, serviceContext);
	}

	public static DDMStructure addDDMStructure(long groupId, String className)
		throws Exception {

		return addDDMStructure(groupId, className, getSampleDDMForm());
	}

	public static DDMStructure addDDMStructure(
			long groupId, String className, DDMForm ddmForm)
		throws Exception {

		return addDDMStructure(
			groupId, PortalUtil.getClassNameId(className),
			DDMStructureConstants.DEFAULT_PARENT_STRUCTURE_ID, ddmForm,
			StorageType.JSON.toString(),
			ServiceContextTestUtil.getServiceContext());
	}

	public static DDMTemplate addDDMTemplate(long groupId, long structureId)
		throws Exception {

		return addDDMTemplate(
			groupId, structureId, TemplateConstants.LANG_TYPE_VM,
			_SAMPLE_VM_TEMPLATE_SCRIPT);
	}

	public static DDMTemplate addDDMTemplate(
			long groupId, long structureId, String language, String script)
		throws Exception {

		Map<Locale, String> nameMap = new HashMap<>();

		nameMap.put(LocaleUtil.getSiteDefault(), "Test Template");

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setAddGroupPermissions(true);
		serviceContext.setAddGuestPermissions(true);

		return DDMTemplateLocalServiceUtil.addTemplate(
			TestPropsValues.getUserId(), groupId,
			PortalUtil.getClassNameId(DDMStructure.class), structureId, 0,
			nameMap, null, DDMTemplateConstants.TEMPLATE_TYPE_DISPLAY, null,
			language, script, serviceContext);
	}

	public static DDMForm createDDMForm(
		Locale defaultLocale, Locale... availableLocales) {

		DDMForm ddmForm = new DDMForm();

		ddmForm.setDefaultLocale(defaultLocale);

		for (Locale availableLocale : availableLocales) {
			ddmForm.addAvailableLocale(availableLocale);
		}

		return ddmForm;
	}

	public static DDMForm createDDMForm(
		Set<Locale> availableLocales, Locale defaultLocale) {

		DDMForm ddmForm = new DDMForm();

		ddmForm.setAvailableLocales(availableLocales);
		ddmForm.setDefaultLocale(defaultLocale);

		return ddmForm;
	}

	public static DDMForm createDDMForm(String... fieldNames) {
		DDMForm ddmForm = createDDMForm(LocaleUtil.US, LocaleUtil.US);

		List<DDMFormField> ddmFormFields = ddmForm.getDDMFormFields();

		for (String fieldName : fieldNames) {
			DDMFormField ddmFormField = createTextDDMFormField(
				fieldName, true, false, false);

			ddmFormFields.add(ddmFormField);
		}

		return ddmForm;
	}

	public static DDMFormField createDDMFormField(
		String name, String label, String type, String dataType,
		boolean localizable, boolean repeatable, boolean required) {

		DDMFormField ddmFormField = new DDMFormField(name, type);

		ddmFormField.setDataType(dataType);
		ddmFormField.setLocalizable(localizable);
		ddmFormField.setRepeatable(repeatable);
		ddmFormField.setRequired(required);

		LocalizedValue localizedValue = ddmFormField.getLabel();

		localizedValue.addString(LocaleUtil.US, label);

		return ddmFormField;
	}

	public static DDMFormFieldValue createDDMFormFieldValue(
		String name, Value value) {

		DDMFormFieldValue ddmFormFieldValue = new DDMFormFieldValue();

		ddmFormFieldValue.setInstanceId(StringUtil.randomString());
		ddmFormFieldValue.setName(name);
		ddmFormFieldValue.setValue(value);

		return ddmFormFieldValue;
	}

	public static DDMFormValues createDDMFormValues(
		DDMForm ddmForm, Locale defaultLocale, Locale... availableLocales) {

		DDMFormValues ddmFormValues = new DDMFormValues(ddmForm);

		ddmFormValues.setDefaultLocale(defaultLocale);

		for (Locale availableLocale : availableLocales) {
			ddmFormValues.addAvailableLocale(availableLocale);
		}

		return ddmFormValues;
	}

	public static DDMFormFieldValue createLocalizedDDMFormFieldValue(
		String name, String enValue) {

		Value localizedValue = new LocalizedValue(LocaleUtil.US);

		localizedValue.addString(LocaleUtil.US, enValue);

		return createDDMFormFieldValue(name, localizedValue);
	}

	public static DDMFormField createTextDDMFormField(
		String name, boolean localizable, boolean repeatable,
		boolean required) {

		return createDDMFormField(
			name, name, "text", "string", localizable, repeatable, required);
	}

	public static DDMFormFieldValue createUnlocalizedDDMFormFieldValue(
		String name, String value) {

		return createDDMFormFieldValue(name, new UnlocalizedValue(value));
	}

	public static DDMForm getSampleDDMForm() {
		DDMForm ddmForm = new DDMForm();

		ddmForm.addAvailableLocale(LocaleUtil.US);
		ddmForm.setDefaultLocale(LocaleUtil.US);

		DDMFormField ddmFormField = new DDMFormField("name", "text");

		ddmFormField.setDataType("string");
		ddmFormField.setIndexType("text");
		ddmFormField.setLocalizable(true);
		ddmFormField.setRepeatable(true);

		LocalizedValue label = new LocalizedValue(LocaleUtil.US);

		label.addString(LocaleUtil.US, "Field");

		ddmFormField.setLabel(label);

		ddmForm.addDDMFormField(ddmFormField);

		return ddmForm;
	}

	private static final String _SAMPLE_VM_TEMPLATE_SCRIPT = "$name.getData()";

}