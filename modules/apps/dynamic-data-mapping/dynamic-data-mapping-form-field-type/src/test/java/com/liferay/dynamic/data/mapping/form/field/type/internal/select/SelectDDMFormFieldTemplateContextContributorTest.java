/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.form.field.type.internal.select;

import com.liferay.dynamic.data.mapping.form.field.type.BaseDDMFormFieldTypeSettingsTestCase;
import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldOptionsFactory;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMFormFieldOptions;
import com.liferay.dynamic.data.mapping.model.LocalizedValue;
import com.liferay.dynamic.data.mapping.render.DDMFormFieldRenderingContext;
import com.liferay.dynamic.data.mapping.service.DDMFormInstanceLocalService;
import com.liferay.dynamic.data.mapping.test.util.DDMFormFieldOptionsTestUtil;
import com.liferay.portal.json.JSONFactoryImpl;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.util.PropsTestUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleThreadLocal;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Marcellus Tavares
 */
public class SelectDDMFormFieldTemplateContextContributorTest
	extends BaseDDMFormFieldTypeSettingsTestCase {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_setUpDDMFormInstanceLocalService();

		PropsTestUtil.setProps("collator.rules", "<<<");

		_setUpJSONFactory();
		_setUpLocaleThreadLocal();

		ReflectionTestUtil.setFieldValue(
			_selectDDMFormFieldTemplateContextContributor, "_language",
			Mockito.mock(Language.class));
	}

	@Test
	public void testGetMultiple1() {
		String fieldName = "field";

		DDMFormField ddmFormField = new DDMFormField(fieldName, "select");

		ddmFormField.setProperty("multiple", "true");

		DDMFormFieldRenderingContext ddmFormFieldRenderingContext =
			new DDMFormFieldRenderingContext();

		ddmFormFieldRenderingContext.setProperty("changedProperties", null);

		Assert.assertTrue(
			_selectDDMFormFieldTemplateContextContributor.getMultiple(
				ddmFormField, ddmFormFieldRenderingContext));
	}

	@Test
	public void testGetMultiple2() {
		DDMFormField ddmFormField = new DDMFormField("field", "select");

		ddmFormField.setProperty("multiple", "true");

		DDMFormFieldRenderingContext ddmFormFieldRenderingContext =
			new DDMFormFieldRenderingContext();

		Map<String, Object> changedProperties = new HashMap<>();

		ddmFormFieldRenderingContext.setProperty(
			"changedProperties", changedProperties);

		Assert.assertTrue(
			_selectDDMFormFieldTemplateContextContributor.getMultiple(
				ddmFormField, ddmFormFieldRenderingContext));
	}

	@Test
	public void testGetMultiple3() {
		DDMFormField ddmFormField = new DDMFormField("field", "select");

		ddmFormField.setProperty("multiple", "false");

		DDMFormFieldRenderingContext ddmFormFieldRenderingContext =
			new DDMFormFieldRenderingContext();

		ddmFormFieldRenderingContext.setProperty(
			"changedProperties",
			HashMapBuilder.<String, Object>put(
				"multiple", true
			).build());

		Assert.assertTrue(
			_selectDDMFormFieldTemplateContextContributor.getMultiple(
				ddmFormField, ddmFormFieldRenderingContext));
	}

	@Test
	public void testGetOptions() {
		List<Object> expectedOptions = new ArrayList<>();

		expectedOptions.add(
			DDMFormFieldOptionsTestUtil.createOption(
				"Label 1", "Reference 1", "value 1"));
		expectedOptions.add(
			DDMFormFieldOptionsTestUtil.createOption(
				"Label 2", "Reference 2", "value 2"));
		expectedOptions.add(
			DDMFormFieldOptionsTestUtil.createOption(
				"Label 3", "Reference 3", "value 3"));

		DDMFormFieldOptions ddmFormFieldOptions =
			DDMFormFieldOptionsTestUtil.createDDMFormFieldOptions();

		Assert.assertEquals(
			expectedOptions,
			_getActualOptions(
				new DDMFormField("field", "select"), ddmFormFieldOptions,
				LocaleUtil.US));
	}

	@Test
	public void testGetOptionsAlphabeticallyOrdered() {
		List<Object> expectedOptions = new ArrayList<>();

		expectedOptions.add(
			DDMFormFieldOptionsTestUtil.createOption(
				"Label 1", "Reference 1", "value 1"));
		expectedOptions.add(
			DDMFormFieldOptionsTestUtil.createOption(
				"Label 2", "Reference 2", "value 2"));
		expectedOptions.add(
			DDMFormFieldOptionsTestUtil.createOption(
				"Label 3", "Reference 3", "value 3"));

		DDMFormField ddmFormField = new DDMFormField("field", "select");

		DDMFormFieldOptions ddmFormFieldOptions = new DDMFormFieldOptions();

		for (int i = 3; i > 0; i--) {
			ddmFormFieldOptions.addOptionLabel(
				"value " + i, LocaleUtil.US, "Label " + i);
			ddmFormFieldOptions.addOptionReference(
				"value " + i, "Reference " + i);
		}

		Assert.assertNotEquals(
			expectedOptions,
			_getActualOptions(
				ddmFormField, ddmFormFieldOptions, LocaleUtil.US));

		ddmFormField.setProperty("alphabeticalOrder", "true");

		Assert.assertEquals(
			expectedOptions,
			_getActualOptions(
				ddmFormField, ddmFormFieldOptions, LocaleUtil.US));
	}

	@Test
	public void testGetParameters1() throws Exception {
		DDMFormField ddmFormField = new DDMFormField("field", "select");

		ddmFormField.setProperty("dataSourceType", "data-provider");

		DDMFormFieldRenderingContext ddmFormFieldRenderingContext =
			new DDMFormFieldRenderingContext();

		ddmFormFieldRenderingContext.setLocale(LocaleUtil.US);
		ddmFormFieldRenderingContext.setValue("[\"value 1\"]");

		_setUpDDMFormFieldOptionsFactory(
			ddmFormField, ddmFormFieldRenderingContext);

		SelectDDMFormFieldTemplateContextContributor
			selectDDMFormFieldTemplateContextContributor = _createSpy();

		Map<String, Object> parameters =
			selectDDMFormFieldTemplateContextContributor.getParameters(
				ddmFormField, ddmFormFieldRenderingContext);

		Assert.assertTrue(parameters.containsKey("dataSourceType"));
		Assert.assertEquals("data-provider", parameters.get("dataSourceType"));

		Assert.assertTrue(parameters.containsKey("multiple"));
		Assert.assertFalse((boolean)parameters.get("multiple"));

		Assert.assertTrue(parameters.containsKey("options"));

		List<Object> options = (List<Object>)parameters.get("options");

		Assert.assertEquals(options.toString(), 3, options.size());

		Map<String, String> optionMap = (Map<String, String>)options.get(0);

		Assert.assertEquals("Label 1", optionMap.get("label"));
		Assert.assertEquals("value 1", optionMap.get("value"));

		optionMap = (Map<String, String>)options.get(1);

		Assert.assertEquals("Label 2", optionMap.get("label"));
		Assert.assertEquals("value 2", optionMap.get("value"));

		optionMap = (Map<String, String>)options.get(2);

		Assert.assertEquals("Label 3", optionMap.get("label"));
		Assert.assertEquals("value 3", optionMap.get("value"));

		Assert.assertTrue((boolean)parameters.get("showEmptyOption"));

		List<String> value = (List<String>)parameters.get("value");

		Assert.assertEquals(value.toString(), 1, value.size());
		Assert.assertTrue(value.toString(), value.contains("value 1"));
	}

	@Test
	public void testGetParameters2() throws Exception {
		DDMFormField ddmFormField = new DDMFormField("field", "select");

		ddmFormField.setMultiple(true);
		ddmFormField.setProperty("dataSourceType", "manual");
		ddmFormField.setProperty("showEmptyOption", false);

		DDMFormFieldRenderingContext ddmFormFieldRenderingContext =
			new DDMFormFieldRenderingContext();

		ddmFormFieldRenderingContext.setLocale(LocaleUtil.US);

		LocalizedValue predefinedValue = new LocalizedValue();

		predefinedValue.setDefaultLocale(LocaleUtil.US);
		predefinedValue.addString(LocaleUtil.US, "[\"value 2\",\"value 3\"]");

		ddmFormField.setPredefinedValue(predefinedValue);

		_setUpDDMFormFieldOptionsFactory(
			ddmFormField, ddmFormFieldRenderingContext);

		SelectDDMFormFieldTemplateContextContributor
			selectDDMFormFieldTemplateContextContributor = _createSpy();

		Map<String, Object> parameters =
			selectDDMFormFieldTemplateContextContributor.getParameters(
				ddmFormField, ddmFormFieldRenderingContext);

		Assert.assertTrue(parameters.containsKey("dataSourceType"));
		Assert.assertEquals("manual", parameters.get("dataSourceType"));

		Assert.assertTrue(parameters.containsKey("multiple"));
		Assert.assertTrue((boolean)parameters.get("multiple"));

		Assert.assertTrue(parameters.containsKey("options"));

		List<Object> options = (List<Object>)parameters.get("options");

		Assert.assertEquals(options.toString(), 3, options.size());

		Map<String, String> optionMap = (Map<String, String>)options.get(0);

		Assert.assertEquals("Label 1", optionMap.get("label"));
		Assert.assertEquals("value 1", optionMap.get("value"));

		optionMap = (Map<String, String>)options.get(1);

		Assert.assertEquals("Label 2", optionMap.get("label"));
		Assert.assertEquals("value 2", optionMap.get("value"));

		optionMap = (Map<String, String>)options.get(2);

		Assert.assertEquals("Label 3", optionMap.get("label"));
		Assert.assertEquals("value 3", optionMap.get("value"));

		List<String> predefinedValueParameter = (List<String>)parameters.get(
			"predefinedValue");

		Assert.assertEquals(
			predefinedValueParameter.toString(), 2,
			predefinedValueParameter.size());
		Assert.assertTrue(
			predefinedValueParameter.toString(),
			predefinedValueParameter.contains("value 2"));
		Assert.assertTrue(
			predefinedValueParameter.toString(),
			predefinedValueParameter.contains("value 3"));

		Assert.assertFalse((boolean)parameters.get("showEmptyOption"));
	}

	@Test
	public void testGetValue1() {
		List<String> values =
			_selectDDMFormFieldTemplateContextContributor.getValue(
				"[\"a\",\"b\"]");

		Assert.assertTrue(values.toString(), values.contains("a"));
		Assert.assertTrue(values.toString(), values.contains("b"));
	}

	@Test
	public void testGetValue2() {
		List<String> values =
			_selectDDMFormFieldTemplateContextContributor.getValue("value");

		Assert.assertTrue(values.toString(), values.contains("value"));
	}

	private SelectDDMFormFieldTemplateContextContributor _createSpy() {
		SelectDDMFormFieldTemplateContextContributor
			selectDDMFormFieldTemplateContextContributor = Mockito.spy(
				_selectDDMFormFieldTemplateContextContributor);

		Mockito.doReturn(
			_resourceBundle
		).when(
			selectDDMFormFieldTemplateContextContributor
		).getResourceBundle(
			Mockito.any(Locale.class)
		);

		return selectDDMFormFieldTemplateContextContributor;
	}

	private List<Map<String, String>> _getActualOptions(
		DDMFormField ddmFormField, DDMFormFieldOptions ddmFormFieldOptions,
		Locale locale) {

		return _selectDDMFormFieldTemplateContextContributor.getOptions(
			ddmFormField, ddmFormFieldOptions, locale,
			new DDMFormFieldRenderingContext());
	}

	private void _setUpDDMFormFieldOptionsFactory(
			DDMFormField ddmFormField,
			DDMFormFieldRenderingContext ddmFormFieldRenderingContext)
		throws Exception {

		ReflectionTestUtil.setFieldValue(
			_selectDDMFormFieldTemplateContextContributor,
			"ddmFormFieldOptionsFactory", _ddmFormFieldOptionsFactory);

		DDMFormFieldOptions ddmFormFieldOptions =
			DDMFormFieldOptionsTestUtil.createDDMFormFieldOptions();

		Mockito.when(
			_ddmFormFieldOptionsFactory.create(
				ddmFormField, ddmFormFieldRenderingContext)
		).thenReturn(
			ddmFormFieldOptions
		);
	}

	private void _setUpDDMFormInstanceLocalService() throws Exception {
		DDMFormInstanceLocalService ddmFormInstanceLocalService = Mockito.mock(
			DDMFormInstanceLocalService.class);

		Mockito.when(
			ddmFormInstanceLocalService.fetchDDMFormInstance(0)
		).thenReturn(
			null
		);

		ReflectionTestUtil.setFieldValue(
			_selectDDMFormFieldTemplateContextContributor,
			"_ddmFormInstanceLocalService", ddmFormInstanceLocalService);
	}

	private void _setUpJSONFactory() throws Exception {
		ReflectionTestUtil.setFieldValue(
			_selectDDMFormFieldTemplateContextContributor, "jsonFactory",
			_jsonFactory);
	}

	private void _setUpLocaleThreadLocal() {
		LocaleThreadLocal.setThemeDisplayLocale(LocaleUtil.US);
	}

	private final DDMFormFieldOptionsFactory _ddmFormFieldOptionsFactory =
		Mockito.mock(DDMFormFieldOptionsFactory.class);
	private final JSONFactory _jsonFactory = new JSONFactoryImpl();
	private final ResourceBundle _resourceBundle = Mockito.mock(
		ResourceBundle.class);
	private final SelectDDMFormFieldTemplateContextContributor
		_selectDDMFormFieldTemplateContextContributor =
			new SelectDDMFormFieldTemplateContextContributor();

}