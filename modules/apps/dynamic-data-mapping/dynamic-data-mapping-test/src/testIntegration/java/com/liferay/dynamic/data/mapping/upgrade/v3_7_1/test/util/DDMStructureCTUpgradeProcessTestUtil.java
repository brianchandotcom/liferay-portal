/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.upgrade.v3_7_1.test.util;

import com.liferay.dynamic.data.lists.model.DDLRecordSet;
import com.liferay.dynamic.data.mapping.form.field.type.constants.DDMFormFieldTypeConstants;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMFormFieldValidation;
import com.liferay.dynamic.data.mapping.test.util.DDMFormTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;

/**
 * @author Igor Costa
 */
public class DDMStructureCTUpgradeProcessTestUtil {

	public static String getClassName() {
		return DDLRecordSet.class.getName();
	}

	public static DDMForm getDDMForm() {
		DDMForm ddmForm = DDMFormTestUtil.createDDMForm();

		DDMFormField ddmFormField = DDMFormTestUtil.createDDMFormField(
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			DDMFormFieldTypeConstants.DATE, DDMFormFieldTypeConstants.DATE,
			false, false, false);

		ddmFormField.setDDMFormFieldValidation(new DDMFormFieldValidation());

		ddmForm.addDDMFormField(ddmFormField);

		return ddmForm;
	}

	public static String getUpgradeStepClassName() {
		return "com.liferay.dynamic.data.mapping.internal.upgrade.v3_7_1." +
			"DDMStructureEmptyValidationUpgradeProcess";
	}

}