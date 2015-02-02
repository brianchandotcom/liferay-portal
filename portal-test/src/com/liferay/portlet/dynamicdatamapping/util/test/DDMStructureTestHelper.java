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

package com.liferay.portlet.dynamicdatamapping.util.test;

import com.liferay.portal.kernel.locale.test.LocaleTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.Group;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.dynamicdatalists.model.DDLRecordSet;
import com.liferay.portlet.dynamicdatalists.util.test.DDLRecordTestUtil;
import com.liferay.portlet.dynamicdatamapping.io.DDMFormXSDDeserializerUtil;
import com.liferay.portlet.dynamicdatamapping.model.DDMForm;
import com.liferay.portlet.dynamicdatamapping.model.DDMFormField;
import com.liferay.portlet.dynamicdatamapping.model.DDMFormLayout;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructure;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructureConstants;
import com.liferay.portlet.dynamicdatamapping.model.LocalizedValue;
import com.liferay.portlet.dynamicdatamapping.service.DDMStructureLocalServiceUtil;
import com.liferay.portlet.dynamicdatamapping.storage.StorageType;
import com.liferay.portlet.dynamicdatamapping.util.DDMUtil;
import com.liferay.portlet.dynamicdatamapping.util.DDMXMLUtil;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

/**
 * @author Eduardo Garcia
 * @author André de Oliveira
 * @author Marcellus Tavares
 */
public class DDMStructureTestHelper {

	public DDMStructureTestHelper(Group group) throws Exception {
		_group = group;
	}

	public DDMStructure addStructure(
			DDMForm ddmForm, DDMFormLayout ddmFormLayout)
		throws Exception {

		return addStructure(
			DDMStructureConstants.DEFAULT_PARENT_STRUCTURE_ID,
			PortalUtil.getClassNameId(DDLRecordSet.class), null,
			"Test Structure", StringPool.BLANK, ddmForm, ddmFormLayout,
			StorageType.JSON.toString(), DDMStructureConstants.TYPE_DEFAULT);
	}

	public DDMStructure addStructure(
			long parentStructureId, long classNameId, String structureKey,
			String name, String description, DDMForm ddmForm,
			DDMFormLayout ddmFormLayout, String storageType, int type)
		throws Exception {

		return DDMStructureLocalServiceUtil.addStructure(
			TestPropsValues.getUserId(), _group.getGroupId(), parentStructureId,
			classNameId, structureKey, LocaleTestUtil.getDefaultLocaleMap(name),
			LocaleTestUtil.getDefaultLocaleMap(description), ddmForm,
			ddmFormLayout, storageType, type,
			ServiceContextTestUtil.getServiceContext(_group.getGroupId()));
	}

	public DDMStructure addStructure(
			long classNameId, String structureKey, String name,
			String definition, String storageType, int type)
		throws Exception {

		DDMForm ddmForm = toDDMForm(definition);

		DDMFormLayout ddmFormLayout = DDMUtil.getDefaultDDMFormLayout(ddmForm);

		return addStructure(
			DDMStructureConstants.DEFAULT_PARENT_STRUCTURE_ID, classNameId,
			structureKey, name, StringPool.BLANK, ddmForm, ddmFormLayout,
			storageType, type);
	}

	public DDMStructure addStructure(String definition, String storageType)
		throws Exception {

		return addStructure(
			PortalUtil.getClassNameId(DDLRecordSet.class), null,
			"Test Structure", definition, storageType,
			DDMStructureConstants.TYPE_DEFAULT);
	}

	public DDMStructure addStructureXsd(Class<?> testClass) throws Exception {
		String definition = DDLRecordTestUtil.read(
			testClass, "test-structure.xsd");

		return addStructure(definition, StorageType.JSON.toString());
	}

	public void addTextDDMFormFields(DDMForm ddmForm, String... fieldNames) {
		List<DDMFormField> ddmFormFields = ddmForm.getDDMFormFields();

		for (String fieldName : fieldNames) {
			ddmFormFields.add(createTextDDMFormField(fieldName));
		}
	}

	public Set<Locale> createAvailableLocales(Locale... locales) {
		Set<Locale> availableLocales = new LinkedHashSet<>();

		for (Locale locale : locales) {
			availableLocales.add(locale);
		}

		return availableLocales;
	}

	public DDMForm createDDMForm(
		Set<Locale> availableLocales, Locale defaultLocale) {

		DDMForm ddmForm = new DDMForm();

		ddmForm.setAvailableLocales(availableLocales);
		ddmForm.setDefaultLocale(defaultLocale);

		return ddmForm;
	}

	public DDMForm createDDMForm(String... fieldNames) {
		DDMForm ddmForm = createDDMForm(
			createAvailableLocales(LocaleUtil.US), LocaleUtil.US);

		addTextDDMFormFields(ddmForm, fieldNames);

		return ddmForm;
	}

	public DDMFormField createTextDDMFormField(String name) {
		return createTextDDMFormField(name, true, false, false);
	}

	public DDMFormField createTextDDMFormField(
		String name, boolean localizable, boolean repeatable,
		boolean required) {

		DDMFormField ddmFormField = new DDMFormField(name, "text");

		ddmFormField.setDataType("string");
		ddmFormField.setLocalizable(localizable);
		ddmFormField.setRepeatable(repeatable);
		ddmFormField.setRequired(required);

		LocalizedValue localizedValue = ddmFormField.getLabel();

		localizedValue.addString(LocaleUtil.US, name);

		return ddmFormField;
	}

	public DDMForm toDDMForm(String definition) throws Exception {
		DDMXMLUtil.validateXML(definition);

		return DDMFormXSDDeserializerUtil.deserialize(definition);
	}

	private final Group _group;

}