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

package com.liferay.dynamic.data.mapping.util;

import com.liferay.dynamic.data.mapping.BaseDDMTestCase;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMTemplate;
import com.liferay.dynamic.data.mapping.model.DDMTemplateConstants;
import com.liferay.dynamic.data.mapping.model.LocalizedValue;
import com.liferay.dynamic.data.mapping.model.impl.DDMTemplateImpl;
import com.liferay.dynamic.data.mapping.service.impl.DDMTemplateLocalServiceImpl;
import com.liferay.dynamic.data.mapping.util.impl.DDMFormTemplateSynchonizer;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import org.powermock.core.classloader.annotations.PrepareForTest;

/**
 * @author Marcellus Tavares
 */
@PrepareForTest(LocaleUtil.class)
public class DDMFormTemplateSynchonizerTest extends BaseDDMTestCase {

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		setUpConfigurationFactoryUtil();
		setUpDDMFormJSONSerializer();
		setUpDDMFormJSONDeserializer();
		setUpJSONFactoryUtil();
		setUpLanguageUtil();
		setUpLocaleUtil();
		setUpHtmlUtil();
		setUpPropsUtil();
		setUpSAXReaderUtil();
	}

	@Test
	public void testAddRequiredFieldsIfTheyWereAddedInStructureAndModeIsCreate()
		throws Exception {

		DDMForm structureDDMForm = createDDMForm();

		addDDMFormField(
			structureDDMForm, createTextDDMFormField("name1", false));

		createFormTemplates(structureDDMForm);

		addDDMFormField(
			structureDDMForm, createTextDDMFormField("name2", true));

		DDMFormTemplateSynchonizer ddmFormTemplateSynchonizer =
			new MockDDMFormTemplateSynchronizer(structureDDMForm);

		ddmFormTemplateSynchonizer.synchronize();

		testFormTemplatesAfterAddRequiredFields();
	}

	@Test
	public void testRemoveTemplateFieldsIfTheyWereRemovedFromStructure()
		throws Exception {

		DDMForm structureDDMForm = createDDMForm();

		addDDMFormField(
			structureDDMForm, createTextDDMFormField("name1", false));
		addDDMFormField(
			structureDDMForm, createTextDDMFormField("name2", false));

		createFormTemplates(structureDDMForm);

		removeDDMFormField(structureDDMForm, "name1");

		DDMFormTemplateSynchonizer ddmFormTemplateSynchonizer =
			new MockDDMFormTemplateSynchronizer(structureDDMForm);

		ddmFormTemplateSynchonizer.synchronize();

		testFormTemplatesAfterRemoveFields();
	}

	@Test
	public void testUpdateRequiredPropertyIfTemplateModeIsCreate()
		throws Exception {

		DDMForm structureDDMForm = createDDMForm();

		addDDMFormField(
			structureDDMForm, createTextDDMFormField("name1", false));
		addDDMFormField(
			structureDDMForm, createTextDDMFormField("name2", true));

		createFormTemplates(structureDDMForm);

		updateDDMFormFieldRequiredProperty(structureDDMForm, "name1", true);
		updateDDMFormFieldRequiredProperty(structureDDMForm, "name2", false);

		DDMFormTemplateSynchonizer ddmFormTemplateSynchonizer =
			new MockDDMFormTemplateSynchronizer(structureDDMForm);

		ddmFormTemplateSynchonizer.synchronize();

		testFormTemplatesAfterUpdateRequiredFieldProperties();
	}

	protected void addDDMFormField(DDMForm ddmForm, DDMFormField ddmFormField) {
		List<DDMFormField> ddmFormFields = ddmForm.getDDMFormFields();

		ddmFormFields.add(ddmFormField);
	}

	protected DDMForm createDDMForm() {
		DDMForm ddmForm = new DDMForm();

		ddmForm.setAvailableLocales(createAvailableLocales(LocaleUtil.US));
		ddmForm.setDefaultLocale(LocaleUtil.US);

		return ddmForm;
	}

	protected void createFormTemplates(DDMForm ddmForm) {
		_createDDMTemplate = createTemplate(
			RandomTestUtil.randomLong(), "Test Create Mode Form Template",
			DDMTemplateConstants.TEMPLATE_MODE_CREATE,
			ddmFormJSONSerializer.serialize(ddmForm));

		_editDDMTemplate = createTemplate(
			RandomTestUtil.randomLong(), "Test Edit Mode Form Template",
			DDMTemplateConstants.TEMPLATE_MODE_EDIT,
			ddmFormJSONSerializer.serialize(ddmForm));
	}

	@Override
	protected DDMTemplate createTemplate(
		long templateId, String name, String mode, String script) {

		DDMTemplate template = new DDMTemplateImpl();

		template.setTemplateId(templateId);
		template.setName(name);
		template.setMode(mode);
		template.setScript(script);

		return template;
	}

	protected DDMFormField createTextDDMFormField(
		String name, boolean required) {

		DDMFormField ddmFormField = new DDMFormField(name, "text");

		ddmFormField.setDataType("string");
		ddmFormField.setRequired(required);

		LocalizedValue label = ddmFormField.getLabel();

		label.addString(LocaleUtil.US, StringUtil.randomString());

		return ddmFormField;
	}

	protected Map<String, DDMFormField> getDDMFormFieldsMap(
			DDMTemplate ddmTemplate)
		throws Exception {

		DDMForm ddmForm = ddmFormJSONDeserializer.deserialize(
			ddmTemplate.getScript());

		return ddmForm.getDDMFormFieldsMap(true);
	}

	protected void removeDDMFormField(DDMForm ddmForm, String fieldName) {
		List<DDMFormField> ddmFormFields = ddmForm.getDDMFormFields();

		Iterator<DDMFormField> itr = ddmFormFields.iterator();

		while (itr.hasNext()) {
			DDMFormField ddmFormField = itr.next();

			if (fieldName.equals(ddmFormField.getName())) {
				itr.remove();
			}
		}
	}

	protected void testFormTemplatesAfterAddRequiredFields() throws Exception {

		// Edit

		Map<String, DDMFormField> ddmFormFieldsMap = getDDMFormFieldsMap(
			_editDDMTemplate);

		DDMFormField ddmFormField = ddmFormFieldsMap.get("name2");

		Assert.assertNull(ddmFormField);

		// Create

		ddmFormFieldsMap = getDDMFormFieldsMap(_createDDMTemplate);

		ddmFormField = ddmFormFieldsMap.get("name2");

		Assert.assertNotNull(ddmFormField);
		Assert.assertTrue(ddmFormField.isRequired());
	}

	protected void testFormTemplatesAfterRemoveFields() throws Exception {

		// Edit

		Map<String, DDMFormField> ddmFormFieldsMap = getDDMFormFieldsMap(
			_editDDMTemplate);

		DDMFormField ddmFormField = ddmFormFieldsMap.get("name1");

		Assert.assertNull(ddmFormField);

		// Create

		ddmFormFieldsMap = getDDMFormFieldsMap(_createDDMTemplate);

		ddmFormField = ddmFormFieldsMap.get("name1");

		Assert.assertNull(ddmFormField);
	}

	protected void testFormTemplatesAfterUpdateRequiredFieldProperties()
		throws Exception {

		// Edit

		Map<String, DDMFormField> ddmFormFieldsMap = getDDMFormFieldsMap(
			_editDDMTemplate);

		DDMFormField name1DDMFormField = ddmFormFieldsMap.get("name1");
		DDMFormField name2DDMFormField = ddmFormFieldsMap.get("name2");

		Assert.assertFalse(name1DDMFormField.isRequired());
		Assert.assertTrue(name2DDMFormField.isRequired());

		// Create

		ddmFormFieldsMap = getDDMFormFieldsMap(_createDDMTemplate);

		name1DDMFormField = ddmFormFieldsMap.get("name1");
		name2DDMFormField = ddmFormFieldsMap.get("name2");

		Assert.assertTrue(name1DDMFormField.isRequired());
		Assert.assertFalse(name2DDMFormField.isRequired());
	}

	protected void updateDDMFormFieldRequiredProperty(
		DDMForm ddmForm, String fieldName, boolean required) {

		List<DDMFormField> ddmFormFields = ddmForm.getDDMFormFields();

		for (DDMFormField ddmFormField : ddmFormFields) {
			if (fieldName.equals(ddmFormField.getName())) {
				ddmFormField.setRequired(required);
			}
		}
	}

	private DDMTemplate _createDDMTemplate;
	private DDMTemplate _editDDMTemplate;

	private class MockDDMFormTemplateSynchronizer
		extends DDMFormTemplateSynchonizer {

		public MockDDMFormTemplateSynchronizer(DDMForm structureDDMForm) {
			super(
				structureDDMForm, ddmFormJSONDeserializer,
				ddmFormJSONSerializer, new DDMTemplateLocalServiceImpl());
		}

		@Override
		protected List<DDMTemplate> getDDMFormTemplates() {
			List<DDMTemplate> ddmFormTemplates = new ArrayList<>();

			ddmFormTemplates.add(_createDDMTemplate);
			ddmFormTemplates.add(_editDDMTemplate);

			return ddmFormTemplates;
		}

		@Override
		protected void updateDDMTemplate(
			DDMTemplate ddmTemplate, DDMForm templateDDMForm) {

			String script = ddmFormJSONSerializer.serialize(templateDDMForm);

			ddmTemplate.setScript(script);

			if (ddmTemplate.getMode().equals(
					DDMTemplateConstants.TEMPLATE_MODE_CREATE)) {

				_createDDMTemplate = ddmTemplate;
			}
			else {
				_editDDMTemplate = ddmTemplate;
			}
		}

	}

}