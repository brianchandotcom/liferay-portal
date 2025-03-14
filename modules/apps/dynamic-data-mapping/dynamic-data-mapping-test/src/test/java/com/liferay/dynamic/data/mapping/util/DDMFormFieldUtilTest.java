/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.util;

import com.liferay.dynamic.data.mapping.form.field.type.constants.DDMFormFieldTypeConstants;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.test.util.DDMFormTestUtil;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.test.AssertUtils;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Nathaly Gomes
 */
public class DDMFormFieldUtilTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testSortNestedDDMFormFields() throws Exception {
		DDMForm ddmForm = DDMFormTestUtil.createDDMForm();

		DDMFormField ddmFormField = new DDMFormField(
			DDMFormFieldTypeConstants.FIELDSET,
			DDMFormFieldTypeConstants.FIELDSET);

		DDMFormTestUtil.addNestedTextDDMFormFields(
			ddmFormField, "Field2", "Field3", "Field1");

		ddmFormField.setProperty(
			"rows",
			JSONUtil.putAll(
				JSONUtil.put(
					"columns",
					JSONUtil.putAll(
						JSONUtil.put(
							"fields", JSONUtil.putAll("Field1")
						).put(
							"size", 12
						))),
				JSONUtil.put(
					"columns",
					JSONUtil.putAll(
						JSONUtil.put(
							"fields", JSONUtil.putAll("Field2")
						).put(
							"size", 12
						))),
				JSONUtil.put(
					"columns",
					JSONUtil.putAll(
						JSONUtil.put(
							"fields", JSONUtil.putAll("Field3")
						).put(
							"size", 12
						)))));

		DDMFormTestUtil.addDDMFormFields(ddmForm, ddmFormField);

		DDMFormField expectedDDMFormField = new DDMFormField(
			DDMFormFieldTypeConstants.FIELDSET,
			DDMFormFieldTypeConstants.FIELDSET);

		DDMFormTestUtil.addNestedTextDDMFormFields(
			expectedDDMFormField, "Field2", "Field3", "Field1");

		AssertUtils.assertEquals(
			expectedDDMFormField.getNestedDDMFormFields(),
			ddmFormField.getNestedDDMFormFields());

		DDMFormFieldUtil.sortNestedDDMFormFields(ddmForm.getDDMFormFields());

		expectedDDMFormField = new DDMFormField(
			DDMFormFieldTypeConstants.FIELDSET,
			DDMFormFieldTypeConstants.FIELDSET);

		DDMFormTestUtil.addNestedTextDDMFormFields(
			expectedDDMFormField, "Field1", "Field2", "Field3");

		AssertUtils.assertEquals(
			expectedDDMFormField.getNestedDDMFormFields(),
			ddmFormField.getNestedDDMFormFields());
	}

}