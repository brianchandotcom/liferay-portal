/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.form.renderer.internal.helper;

import com.liferay.dynamic.data.mapping.model.DDMDataProviderInstance;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMFormFieldValidation;
import com.liferay.dynamic.data.mapping.model.DDMFormFieldValidationExpression;
import com.liferay.dynamic.data.mapping.model.DDMFormLayout;
import com.liferay.dynamic.data.mapping.service.DDMDataProviderInstanceService;
import com.liferay.dynamic.data.mapping.test.util.DDMFormTestUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.Set;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Rafael Praxedes
 */
public class DDMFormTemplateContextFactoryHelperTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@BeforeClass
	public static void setUpClass() throws Exception {
		_setUpDDMFormTemplateContextFactoryHelper();
	}

	@Test
	public void testGetEvaluableFieldNames() {
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

		ddmFormFieldValidation.setDDMFormFieldValidationExpression(
			new DDMFormFieldValidationExpression() {
				{
					setValue("isEmailAddress(Field4)");
				}
			});

		ddmFormField4.setDDMFormFieldValidation(ddmFormFieldValidation);

		ddmForm.addDDMFormField(ddmFormField4);

		DDMFormField ddmFormField5 = DDMFormTestUtil.createTextDDMFormField(
			"Field5", false, false, false);

		ddmFormField5.setProperty("requireConfirmation", true);

		ddmForm.addDDMFormField(ddmFormField5);

		Set<String> expectedEvaluableFieldNames = SetUtil.fromArray(
			"Field0", "Field2", "Field4", "Field5");

		Set<String> actualEvaluableFieldNames =
			_ddmFormTemplateContextFactoryHelper.getEvaluableDDMFormFieldNames(
				ddmForm, new DDMFormLayout());

		Assert.assertEquals(
			expectedEvaluableFieldNames, actualEvaluableFieldNames);
	}

	private static void _setUpDDMFormTemplateContextFactoryHelper()
		throws Exception {

		DDMDataProviderInstance ddmDataProviderInstance = Mockito.mock(
			DDMDataProviderInstance.class);

		Mockito.when(
			ddmDataProviderInstance.getUuid()
		).thenReturn(
			_DATA_PROVIDER_INSTANCE_UUID
		);

		DDMDataProviderInstanceService ddmDataProviderInstanceService =
			Mockito.mock(DDMDataProviderInstanceService.class);

		Mockito.when(
			ddmDataProviderInstanceService.getDataProviderInstance(
				Mockito.anyLong())
		).thenReturn(
			ddmDataProviderInstance
		);

		_ddmFormTemplateContextFactoryHelper =
			new DDMFormTemplateContextFactoryHelper();
	}

	private static final String _DATA_PROVIDER_INSTANCE_UUID =
		"ea3464d6-71e2-5202-964a-f53d6cc0ee39";

	private static DDMFormTemplateContextFactoryHelper
		_ddmFormTemplateContextFactoryHelper;

}