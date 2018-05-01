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

package com.liferay.dynamic.data.mapping.form.renderer.internal;

import com.liferay.dynamic.data.mapping.model.DDMDataProviderInstance;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMFormFieldValidation;
import com.liferay.dynamic.data.mapping.model.DDMFormRule;
import com.liferay.dynamic.data.mapping.service.DDMDataProviderInstanceService;
import com.liferay.dynamic.data.mapping.test.util.DDMFormTestUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.SetUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.Matchers;

import org.powermock.api.mockito.PowerMockito;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 * @author Rafael Praxedes
 */
@RunWith(PowerMockRunner.class)
public class DDMFormTemplateContextFactoryHelperTest extends PowerMockito {

	@BeforeClass
	public static void setUpClass() throws Exception {
		setUpDDMFormTemplateContextFactoryHelper();
	}

	@Test
	public void testGetDataProviderSettingsFromAutoFillActions()
		throws Exception {

		DDMForm ddmForm = DDMFormTestUtil.createDDMForm();

		List<DDMFormRule> ddmFormRules = new ArrayList<>();

		ddmFormRules.add(createAutoFillDDMFormRule());

		ddmForm.setDDMFormRules(ddmFormRules);

		DDMFormField ddmFormField1 = DDMFormTestUtil.createDDMFormField(
			"Field1", "Field1", "select", "string", false, false, false);

		ddmForm.addDDMFormField(ddmFormField1);

		DDMFormField ddmFormField2 = DDMFormTestUtil.createDDMFormField(
			"Field2", "Field2", "select", "string", false, false, false);

		ddmForm.addDDMFormField(ddmFormField2);

		Map<String, Map<String, Object>> dataProviderSettings =
			_ddmFormTemplateContextFactoryHelper.getDataProviderSettings(
				ddmForm);

		Assert.assertNotNull(dataProviderSettings);

		Map<String, Object> fieldDataProviderSettingsMap =
			dataProviderSettings.get(ddmFormField2.getName());

		Assert.assertNotNull(fieldDataProviderSettingsMap);

		Assert.assertEquals(
			_DATA_PROVIDER_INSTANCE_UUID,
			fieldDataProviderSettingsMap.get("dataProviderInstanceUUID"));

		Assert.assertEquals(
			"output", fieldDataProviderSettingsMap.get("outputParameterName"));

		Map<String, String> inputParametersMap = new HashMap<>();

		inputParametersMap.put("input", ddmFormField1.getName());

		Assert.assertEquals(
			inputParametersMap,
			fieldDataProviderSettingsMap.get("inputParameters"));
	}

	@Test
	public void testGetDataProviderSettingsFromFieldSettings()
		throws Exception {

		DDMForm ddmForm = DDMFormTestUtil.createDDMForm();

		DDMFormField ddmFormField = DDMFormTestUtil.createDDMFormField(
			"Field1", "Field1", "select", "string", false, false, false);

		ddmFormField.setProperty("ddmDataProviderInstanceId", 1);
		ddmFormField.setProperty("ddmDataProviderInstanceOutput", "output");

		ddmForm.addDDMFormField(ddmFormField);

		Map<String, Map<String, Object>> dataProviderSettings =
			_ddmFormTemplateContextFactoryHelper.getDataProviderSettings(
				ddmForm);

		Assert.assertNotNull(dataProviderSettings);

		Map<String, Object> fieldDataProviderSettingsMap =
			dataProviderSettings.get(ddmFormField.getName());

		Assert.assertNotNull(fieldDataProviderSettingsMap);

		Assert.assertEquals(
			_DATA_PROVIDER_INSTANCE_UUID,
			fieldDataProviderSettingsMap.get("dataProviderInstanceUUID"));

		Assert.assertEquals(
			"output", fieldDataProviderSettingsMap.get("outputParameterName"));

		Assert.assertEquals(
			Collections.emptyMap(),
			fieldDataProviderSettingsMap.get("inputParameters"));
	}

	@Test
	public void testGetEvaluableFieldNames() throws Exception {
		DDMForm ddmForm = DDMFormTestUtil.createDDMForm();

		ddmForm.addDDMFormField(
			DDMFormTestUtil.createTextDDMFormField(
				"Field0", false, false, false));
		ddmForm.addDDMFormField(
			DDMFormTestUtil.createTextDDMFormField(
				"Field1", false, false, false));
		ddmForm.addDDMFormField(
			DDMFormTestUtil.createTextDDMFormField(
				"Field2", false, false, true));

		DDMFormField ddmFormField3 = DDMFormTestUtil.createTextDDMFormField(
			"Field3", false, false, false);

		ddmFormField3.setVisibilityExpression("equals(Field0, 'Joe')");

		ddmForm.addDDMFormField(ddmFormField3);

		DDMFormField ddmFormField4 = DDMFormTestUtil.createTextDDMFormField(
			"Field4", false, false, false);

		DDMFormFieldValidation ddmFormFieldValidation =
			new DDMFormFieldValidation();

		ddmFormFieldValidation.setExpression("isEmailAddress(Field4)");

		ddmFormField4.setDDMFormFieldValidation(ddmFormFieldValidation);

		ddmForm.addDDMFormField(ddmFormField4);

		Set<String> expectedEvaluableFieldNames = SetUtil.fromArray(
			new String[] {"Field0", "Field2", "Field4"});

		Set<String> actualEvaluableFieldNames =
			_ddmFormTemplateContextFactoryHelper.getEvaluableDDMFormFieldNames(
				ddmForm);

		Assert.assertEquals(
			expectedEvaluableFieldNames, actualEvaluableFieldNames);
	}

	protected static void setUpDDMFormTemplateContextFactoryHelper()
		throws Exception {

		DDMDataProviderInstance ddmDataProviderInstance = mock(
			DDMDataProviderInstance.class);

		when(
			ddmDataProviderInstance.getUuid()
		).thenReturn(
			_DATA_PROVIDER_INSTANCE_UUID
		);

		DDMDataProviderInstanceService ddmDataProviderInstanceService = mock(
			DDMDataProviderInstanceService.class);

		when(
			ddmDataProviderInstanceService.getDataProviderInstance(
				Matchers.anyLong())
		).thenReturn(
			ddmDataProviderInstance
		);

		_ddmFormTemplateContextFactoryHelper =
			new DDMFormTemplateContextFactoryHelper(
				ddmDataProviderInstanceService);
	}

	protected DDMFormRule createAutoFillDDMFormRule() {
		StringBuilder sb = new StringBuilder();

		sb.append("call(");
		sb.append(StringPool.APOSTROPHE);
		sb.append(_DATA_PROVIDER_INSTANCE_UUID);
		sb.append(StringPool.APOSTROPHE);
		sb.append(", 'input=Field1', 'Field2=output')");

		return new DDMFormRule(
			"not(equals(getValue('Field1'), 'Option'))", sb.toString());
	}

	private static final String _DATA_PROVIDER_INSTANCE_UUID =
		"ea3464d6-71e2-5202-964a-f53d6cc0ee39";

	private static DDMFormTemplateContextFactoryHelper
		_ddmFormTemplateContextFactoryHelper;

}