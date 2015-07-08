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
import com.liferay.portal.bean.BeanPropertiesImpl;
import com.liferay.portal.kernel.bean.BeanPropertiesUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portlet.dynamicdatamapping.model.DDMForm;
import com.liferay.portlet.dynamicdatamapping.model.DDMFormField;
import com.liferay.portlet.dynamicdatamapping.model.DDMFormFieldOptions;
import com.liferay.portlet.dynamicdatamapping.model.LocalizedValue;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Leonardo Barros
 */
public class DDMFormDeepCopyTest extends BaseDDMTestCase {

	@Before
	public void setUp() {
		setUpBeanPropertiesUtil();
	}

	@Test
	public void testNestedFields() throws Exception {
		DDMForm ddmForm = createDDMForm();

		DDMFormField nameDDMFormField = new DDMFormField("Name", "textarea");

		ddmForm.addDDMFormField(nameDDMFormField);

		DDMFormField phoneDDMFormField = new DDMFormField("Phone", "text");
		DDMFormField contactDDMFormField = new DDMFormField("Name", "text");

		nameDDMFormField.addNestedDDMFormField(phoneDDMFormField);
		nameDDMFormField.addNestedDDMFormField(contactDDMFormField);

		DDMFormField addressDDMFormField = new DDMFormField("ZipCode", "text");

		contactDDMFormField.addNestedDDMFormField(addressDDMFormField);

		com.liferay.portlet.dynamicdatamapping.DDMForm ddmFormCopy =
			BeanPropertiesUtil.deepCopyProperties(
				ddmForm, com.liferay.portlet.dynamicdatamapping.DDMForm.class);

		List<com.liferay.portlet.dynamicdatamapping.DDMFormField>
			ddmFormFieldsCopy = ddmFormCopy.getDDMFormFields();

		Assert.assertEquals(1, ddmFormFieldsCopy.size());

		com.liferay.portlet.dynamicdatamapping.DDMFormField ddmFormFieldCopy =
			ddmFormFieldsCopy.get(0);

		Assert.assertEquals("Name", ddmFormFieldCopy.getName());
		Assert.assertEquals("textarea", ddmFormFieldCopy.getType());

		List<com.liferay.portlet.dynamicdatamapping.DDMFormField>
			nestedDDMFormFieldsCopy = ddmFormFieldCopy.getNestedDDMFormFields();

		Assert.assertEquals(2, nestedDDMFormFieldsCopy.size());

		com.liferay.portlet.dynamicdatamapping.DDMFormField
			nestedDDMFormFieldCopy = nestedDDMFormFieldsCopy.get(0);

		Assert.assertEquals("Phone", nestedDDMFormFieldCopy.getName());
		Assert.assertEquals("text", nestedDDMFormFieldCopy.getType());

		nestedDDMFormFieldCopy = nestedDDMFormFieldsCopy.get(1);

		Assert.assertEquals("Name", nestedDDMFormFieldCopy.getName());
		Assert.assertEquals("text", nestedDDMFormFieldCopy.getType());

		Assert.assertEquals(
			1, nestedDDMFormFieldCopy.getNestedDDMFormFields().size());

		nestedDDMFormFieldCopy =
			nestedDDMFormFieldCopy.getNestedDDMFormFields().get(0);

		Assert.assertEquals("ZipCode", nestedDDMFormFieldCopy.getName());
		Assert.assertEquals("text", nestedDDMFormFieldCopy.getType());
	}

	@Test
	public void testSomeFieldProperties() throws Exception {
		DDMForm ddmForm = createDDMForm();

		DDMFormField nameDDMFormField = new DDMFormField("Name", "textarea");

		ddmForm.addDDMFormField(nameDDMFormField);

		nameDDMFormField.setFieldNamespace("namespace");
		nameDDMFormField.setIndexType("indexType");

		LocalizedValue label = new LocalizedValue(LocaleUtil.BRAZIL);

		label.addString(LocaleUtil.BRAZIL, "teste");

		nameDDMFormField.setLabel(label);

		nameDDMFormField.setLocalizable(true);
		nameDDMFormField.setMultiple(true);
		nameDDMFormField.setReadOnly(true);
		nameDDMFormField.setRepeatable(true);
		nameDDMFormField.setRequired(true);
		nameDDMFormField.setShowLabel(true);

		LocalizedValue style = new LocalizedValue(LocaleUtil.ENGLISH);

		style.addString(LocaleUtil.ENGLISH, "style");

		nameDDMFormField.setStyle(style);

		LocalizedValue tip = new LocalizedValue(LocaleUtil.FRANCE);

		tip.addString(LocaleUtil.FRANCE, "tip");

		nameDDMFormField.setTip(tip);
		nameDDMFormField.setVisibilityExpression("expression");

		DDMFormFieldOptions ddmFormFieldOptions = new DDMFormFieldOptions();

		ddmFormFieldOptions.setDefaultLocale(LocaleUtil.BRAZIL);

		ddmFormFieldOptions.addOptionLabel(
			"teste", LocaleUtil.ENGLISH, "label");

		nameDDMFormField.setDDMFormFieldOptions(ddmFormFieldOptions);

		com.liferay.portlet.dynamicdatamapping.DDMForm ddmFormCopy =
			BeanPropertiesUtil.deepCopyProperties(
				ddmForm, com.liferay.portlet.dynamicdatamapping.DDMForm.class);

		com.liferay.portlet.dynamicdatamapping.DDMFormField ddmFormFieldCopy =
			ddmFormCopy.getDDMFormFields().get(0);

		Assert.assertEquals("Name", ddmFormFieldCopy.getName());
		Assert.assertEquals("textarea", ddmFormFieldCopy.getType());
		Assert.assertEquals("namespace", ddmFormFieldCopy.getFieldNamespace());
		Assert.assertEquals("indexType", ddmFormFieldCopy.getIndexType());

		com.liferay.portlet.dynamicdatamapping.LocalizedValue labelCopy =
			ddmFormFieldCopy.getLabel();

		Assert.assertEquals(LocaleUtil.BRAZIL, labelCopy.getDefaultLocale());
		Assert.assertEquals("teste", labelCopy.getString(LocaleUtil.BRAZIL));

		Assert.assertTrue(ddmFormFieldCopy.isLocalizable());
		Assert.assertTrue(ddmFormFieldCopy.isMultiple());
		Assert.assertTrue(ddmFormFieldCopy.isReadOnly());
		Assert.assertTrue(ddmFormFieldCopy.isRepeatable());
		Assert.assertTrue(ddmFormFieldCopy.isRequired());
		Assert.assertTrue(ddmFormFieldCopy.isShowLabel());

		com.liferay.portlet.dynamicdatamapping.LocalizedValue styleCopy =
			ddmFormFieldCopy.getStyle();

		Assert.assertEquals(LocaleUtil.ENGLISH, styleCopy.getDefaultLocale());
		Assert.assertEquals("style", styleCopy.getString(LocaleUtil.ENGLISH));

		com.liferay.portlet.dynamicdatamapping.LocalizedValue tipCopy =
			ddmFormFieldCopy.getTip();

		Assert.assertEquals(LocaleUtil.FRANCE, tipCopy.getDefaultLocale());
		Assert.assertEquals("tip", tipCopy.getString(LocaleUtil.FRANCE));

		Assert.assertEquals(
			"expression", ddmFormFieldCopy.getVisibilityExpression());

		com.liferay.portlet.dynamicdatamapping.DDMFormFieldOptions
			ddmFormFieldOptionsCopy = ddmFormFieldCopy.getDDMFormFieldOptions();

		Assert.assertEquals(
			LocaleUtil.BRAZIL, ddmFormFieldOptionsCopy.getDefaultLocale());

		Map<String, com.liferay.portlet.dynamicdatamapping.LocalizedValue>
			optionsCopy = ddmFormFieldOptionsCopy.getOptions();

		Assert.assertTrue(optionsCopy.containsKey("teste"));

		com.liferay.portlet.dynamicdatamapping.LocalizedValue
			testeOptionLabelCopy = optionsCopy.get("teste");

		Assert.assertEquals(
			"label", testeOptionLabelCopy.getString(LocaleUtil.ENGLISH));
	}

	@Test
	public void testWithFields() throws Exception {
		DDMForm ddmForm = createDDMForm();

		DDMFormField nameDDMFormField = new DDMFormField("Name", "textarea");

		DDMFormField phoneDDMFormField = new DDMFormField("Phone", "text");

		ddmForm.addDDMFormField(nameDDMFormField);
		ddmForm.addDDMFormField(phoneDDMFormField);

		com.liferay.portlet.dynamicdatamapping.DDMForm ddmFormCopy =
			BeanPropertiesUtil.deepCopyProperties(
				ddmForm, com.liferay.portlet.dynamicdatamapping.DDMForm.class);

		List<com.liferay.portlet.dynamicdatamapping.DDMFormField>
			ddmFormFieldsCopy = ddmFormCopy.getDDMFormFields();

		Assert.assertEquals(2, ddmFormFieldsCopy.size());

		com.liferay.portlet.dynamicdatamapping.DDMFormField ddmFormFieldCopy =
			ddmFormFieldsCopy.get(0);

		Assert.assertEquals("Name", ddmFormFieldCopy.getName());
		Assert.assertEquals("textarea", ddmFormFieldCopy.getType());

		ddmFormFieldCopy = ddmFormFieldsCopy.get(1);

		Assert.assertEquals("Phone", ddmFormFieldCopy.getName());
		Assert.assertEquals("text", ddmFormFieldCopy.getType());
	}

	@Test
	public void testWithoutFields() throws Exception {
		DDMForm ddmForm = createDDMForm();

		com.liferay.portlet.dynamicdatamapping.DDMForm ddmFormCopy =
			BeanPropertiesUtil.deepCopyProperties(
				ddmForm, com.liferay.portlet.dynamicdatamapping.DDMForm.class);

		Set<Locale> availableLocales = ddmFormCopy.getAvailableLocales();

		Assert.assertFalse(availableLocales.isEmpty());
		Assert.assertEquals(availableLocales.toArray()[0], LocaleUtil.US);
	}

	protected void setUpBeanPropertiesUtil() {
		BeanPropertiesUtil beanPropertiesUtil = new BeanPropertiesUtil();

		beanPropertiesUtil.setBeanProperties(new BeanPropertiesImpl());
	}

}