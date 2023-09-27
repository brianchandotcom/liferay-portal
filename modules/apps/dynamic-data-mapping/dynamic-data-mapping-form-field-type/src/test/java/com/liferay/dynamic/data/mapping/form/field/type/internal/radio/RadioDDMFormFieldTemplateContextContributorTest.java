/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.form.field.type.internal.radio;

import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.LocalizedValue;
import com.liferay.dynamic.data.mapping.render.DDMFormFieldRenderingContext;
import com.liferay.dynamic.data.mapping.test.util.DDMFormTestUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.json.JSONFactoryImpl;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Leonardo Barros
 */
public class RadioDDMFormFieldTemplateContextContributorTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@BeforeClass
	public static void setUpClass() {
		_radioDDMFormFieldTemplateContextContributor =
			new RadioDDMFormFieldTemplateContextContributor();

		_setUpJSONFactory();
	}

	@Test
	public void testGetInline() {
		DDMFormField ddmFormField = createDDMFormField();

		DDMFormFieldRenderingContext ddmFormFieldRenderingContext =
			new DDMFormFieldRenderingContext();

		ddmFormField.setProperty("inline", true);
		ddmFormField.setProperty("dataSourceType", "data-provider");

		Map<String, Object> parameters =
			_radioDDMFormFieldTemplateContextContributor.getParameters(
				ddmFormField, ddmFormFieldRenderingContext);

		Assert.assertTrue((boolean)parameters.get("inline"));
	}

	@Test
	public void testGetNotDefinedPredefinedValue() {
		DDMFormField ddmFormField = createDDMFormField();

		ddmFormField.setProperty("dataSourceType", "manual");

		DDMFormFieldRenderingContext ddmFormFieldRenderingContext =
			new DDMFormFieldRenderingContext();

		ddmFormFieldRenderingContext.setLocale(LocaleUtil.US);
		ddmFormFieldRenderingContext.setProperty(
			"options", _createDDMFormOptions());

		Map<String, Object> parameters =
			_radioDDMFormFieldTemplateContextContributor.getParameters(
				ddmFormField, ddmFormFieldRenderingContext);

		Assert.assertEquals(
			StringPool.BLANK, parameters.get("predefinedValue"));
	}

	@Test
	public void testGetOptions() {
		DDMFormField ddmFormField = createDDMFormField();

		ddmFormField.setProperty("dataSourceType", "manual");

		DDMFormFieldRenderingContext ddmFormFieldRenderingContext =
			new DDMFormFieldRenderingContext();

		ddmFormFieldRenderingContext.setLocale(LocaleUtil.US);
		ddmFormFieldRenderingContext.setProperty(
			"options", _createDDMFormOptions());

		Map<String, Object> parameters =
			_radioDDMFormFieldTemplateContextContributor.getParameters(
				ddmFormField, ddmFormFieldRenderingContext);

		Assert.assertTrue(parameters.containsKey("options"));

		List<Object> options = (List<Object>)parameters.get("options");

		Assert.assertEquals(options.toString(), 2, options.size());

		Map<String, String> option0 = (Map<String, String>)options.get(0);

		Assert.assertEquals("Label 0", option0.get("label"));
		Assert.assertEquals("Value 0", option0.get("value"));

		Map<String, String> option1 = (Map<String, String>)options.get(1);

		Assert.assertEquals("Label 1", option1.get("label"));
		Assert.assertEquals("Value 1", option1.get("value"));
	}

	@Test
	public void testGetPredefinedValue() {
		DDMFormField ddmFormField = createDDMFormField();

		ddmFormField.setProperty("dataSourceType", "manual");

		DDMFormFieldRenderingContext ddmFormFieldRenderingContext =
			new DDMFormFieldRenderingContext();

		ddmFormFieldRenderingContext.setLocale(LocaleUtil.US);
		ddmFormFieldRenderingContext.setProperty(
			"options", _createDDMFormOptions());

		LocalizedValue predefinedValue = new LocalizedValue(LocaleUtil.US);

		predefinedValue.addString(LocaleUtil.US, "value");

		ddmFormField.setProperty("predefinedValue", predefinedValue);

		Map<String, Object> parameters =
			_radioDDMFormFieldTemplateContextContributor.getParameters(
				ddmFormField, ddmFormFieldRenderingContext);

		Assert.assertEquals("value", parameters.get("predefinedValue"));
	}

	@Test
	public void testGetPredefinedValueInJSONArrayFormat() {
		DDMFormField ddmFormField = createDDMFormField();

		ddmFormField.setProperty("dataSourceType", "manual");

		DDMFormFieldRenderingContext ddmFormFieldRenderingContext =
			new DDMFormFieldRenderingContext();

		ddmFormFieldRenderingContext.setLocale(LocaleUtil.US);

		List<Map<String, String>> keyValuePairs = new ArrayList<>();

		ddmFormFieldRenderingContext.setProperty("options", keyValuePairs);

		LocalizedValue predefinedValue = new LocalizedValue(LocaleUtil.US);

		predefinedValue.addString(LocaleUtil.US, "[\"value\"]");

		ddmFormField.setProperty("predefinedValue", predefinedValue);

		Map<String, Object> parameters =
			_radioDDMFormFieldTemplateContextContributor.getParameters(
				ddmFormField, ddmFormFieldRenderingContext);

		Assert.assertEquals("value", parameters.get("predefinedValue"));
	}

	@Test
	public void testGetValue() {
		DDMFormField ddmFormField = createDDMFormField();

		ddmFormField.setProperty("dataSourceType", "manual");

		DDMFormFieldRenderingContext ddmFormFieldRenderingContext =
			new DDMFormFieldRenderingContext();

		ddmFormFieldRenderingContext.setLocale(LocaleUtil.US);
		ddmFormFieldRenderingContext.setProperty(
			"options", _createDDMFormOptions());
		ddmFormFieldRenderingContext.setValue("value");

		Map<String, Object> parameters =
			_radioDDMFormFieldTemplateContextContributor.getParameters(
				ddmFormField, ddmFormFieldRenderingContext);

		Assert.assertEquals("value", parameters.get("value"));
	}

	@Test
	public void testGetValueInJSONArrayFormat() {
		DDMFormField ddmFormField = createDDMFormField();

		ddmFormField.setProperty("dataSourceType", "manual");

		DDMFormFieldRenderingContext ddmFormFieldRenderingContext =
			new DDMFormFieldRenderingContext();

		ddmFormFieldRenderingContext.setLocale(LocaleUtil.US);
		ddmFormFieldRenderingContext.setProperty(
			"options", _createDDMFormOptions());
		ddmFormFieldRenderingContext.setValue("[\"value\"]");

		Map<String, Object> parameters =
			_radioDDMFormFieldTemplateContextContributor.getParameters(
				ddmFormField, ddmFormFieldRenderingContext);

		Assert.assertEquals("value", parameters.get("value"));
	}

	protected DDMFormField createDDMFormField() {
		return DDMFormTestUtil.createTextDDMFormField(
			"name", false, false, false);
	}

	private static void _setUpJSONFactory() {
		ReflectionTestUtil.setFieldValue(
			_radioDDMFormFieldTemplateContextContributor, "jsonFactory",
			new JSONFactoryImpl());
	}

	private List<Map<String, String>> _createDDMFormOptions() {
		return Arrays.asList(
			HashMapBuilder.put(
				"label", "Label 0"
			).put(
				"value", "Value 0"
			).build(),
			HashMapBuilder.put(
				"label", "Label 1"
			).put(
				"value", "Value 1"
			).build());
	}

	private static RadioDDMFormFieldTemplateContextContributor
		_radioDDMFormFieldTemplateContextContributor;

}