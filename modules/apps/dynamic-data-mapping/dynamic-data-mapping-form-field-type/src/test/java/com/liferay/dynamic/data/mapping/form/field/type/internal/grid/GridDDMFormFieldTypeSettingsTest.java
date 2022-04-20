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

package com.liferay.dynamic.data.mapping.form.field.type.internal.grid;

import com.liferay.dynamic.data.mapping.form.field.type.BaseDDMFormFieldTypeSettingsTestCase;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMFormLayout;
import com.liferay.dynamic.data.mapping.model.DDMFormRule;
import com.liferay.dynamic.data.mapping.test.util.DDMFormLayoutTestUtil;
import com.liferay.dynamic.data.mapping.util.DDMFormFactory;
import com.liferay.dynamic.data.mapping.util.DDMFormLayoutFactory;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.resource.bundle.ResourceBundleLoader;
import com.liferay.portal.kernel.resource.bundle.ResourceBundleLoaderUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Matchers;
import org.mockito.Mockito;

/**
 * @author Pedro Queiroz
 */
public class GridDDMFormFieldTypeSettingsTest
	extends BaseDDMFormFieldTypeSettingsTestCase {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	@Override
	public void setUp() {
		setUpLanguageUtil();
		setUpPortalUtil();
		setUpResourceBundleUtil();
	}

	@Test
	public void testCreateGridDDMFormFieldTypeSettingsDDMForm() {
		DDMForm ddmForm = DDMFormFactory.create(
			GridDDMFormFieldTypeSettings.class);

		Map<String, DDMFormField> ddmFormFieldsMap =
			ddmForm.getDDMFormFieldsMap(false);

		DDMFormField rowsDDMFormField = ddmFormFieldsMap.get("rows");

		Assert.assertNotNull(rowsDDMFormField);
		Assert.assertEquals("ddm-options", rowsDDMFormField.getDataType());
		Assert.assertNotNull(rowsDDMFormField.getLabel());
		Assert.assertTrue(rowsDDMFormField.isRequired());
		Assert.assertEquals("options", rowsDDMFormField.getType());

		DDMFormField columnsDDMFormField = ddmFormFieldsMap.get("columns");

		Assert.assertNotNull(columnsDDMFormField);
		Assert.assertEquals("ddm-options", columnsDDMFormField.getDataType());
		Assert.assertNotNull(columnsDDMFormField.getLabel());
		Assert.assertTrue(columnsDDMFormField.isRequired());
		Assert.assertEquals("options", columnsDDMFormField.getType());

		DDMFormField predefinedValueDDMFormField = ddmFormFieldsMap.get(
			"predefinedValue");

		Assert.assertNotNull(predefinedValueDDMFormField);

		DDMFormField repeatableDDMFormField = ddmFormFieldsMap.get(
			"repeatable");

		Assert.assertNotNull(repeatableDDMFormField);

		DDMFormField requiredErrorMessage = ddmFormFieldsMap.get(
			"requiredErrorMessage");

		Assert.assertNotNull(requiredErrorMessage);

		List<DDMFormRule> ddmFormRules = ddmForm.getDDMFormRules();

		Assert.assertEquals(ddmFormRules.toString(), 1, ddmFormRules.size());

		DDMFormRule ddmFormRule = ddmFormRules.get(0);

		List<String> actions = ddmFormRule.getActions();

		Assert.assertEquals("TRUE", ddmFormRule.getCondition());

		Assert.assertEquals(actions.toString(), 4, actions.size());

		Assert.assertEquals("setVisible('indexType', false)", actions.get(0));
		Assert.assertEquals(
			"setVisible('predefinedValue', false)", actions.get(1));
		Assert.assertEquals("setVisible('repeatable', false)", actions.get(2));
		Assert.assertEquals(
			"setVisible('requiredErrorMessage', getValue('required'))",
			actions.get(3));
	}

	@Test
	public void testCreateGridDDMFormFieldTypeSettingsDDMFormLayout() {
		assertDDMFormLayout(
			DDMFormLayoutFactory.create(GridDDMFormFieldTypeSettings.class),
			DDMFormLayoutTestUtil.createDDMFormLayout(
				DDMFormLayout.TABBED_MODE,
				DDMFormLayoutTestUtil.createDDMFormLayoutPage(
					"label", "tip", "required", "requiredErrorMessage",
					"predefinedValue", "rows", "columns"),
				DDMFormLayoutTestUtil.createDDMFormLayoutPage(
					"name", "fieldReference", "objectFieldName",
					"visibilityExpression", "showLabel", "repeatable",
					"fieldNamespace", "indexType", "localizable", "readOnly",
					"dataType", "type")));
	}

	@Override
	protected void setUpLanguageUtil() {
		LanguageUtil languageUtil = new LanguageUtil();

		languageUtil.setLanguage(Mockito.mock(Language.class));
	}

	@Override
	protected void setUpPortalUtil() {
		PortalUtil portalUtil = new PortalUtil();

		Portal portal = Mockito.mock(Portal.class);

		ResourceBundle resourceBundle = Mockito.mock(ResourceBundle.class);

		Mockito.when(
			portal.getResourceBundle(Matchers.any(Locale.class))
		).thenReturn(
			resourceBundle
		);

		portalUtil.setPortal(portal);
	}

	@Override
	protected void setUpResourceBundleUtil() {
		ResourceBundleLoader resourceBundleLoader = Mockito.mock(
			ResourceBundleLoader.class);

		ResourceBundleLoaderUtil.setPortalResourceBundleLoader(
			resourceBundleLoader);

		Mockito.when(
			resourceBundleLoader.loadResourceBundle(Matchers.any(Locale.class))
		).thenReturn(
			ResourceBundleUtil.EMPTY_RESOURCE_BUNDLE
		);
	}

}