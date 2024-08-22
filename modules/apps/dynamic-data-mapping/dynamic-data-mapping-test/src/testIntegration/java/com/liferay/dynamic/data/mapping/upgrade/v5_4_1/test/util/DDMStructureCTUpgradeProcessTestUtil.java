/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.upgrade.v5_4_1.test.util;

import com.liferay.dynamic.data.mapping.form.field.type.constants.DDMFormFieldTypeConstants;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.test.util.DDMFormTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;

/**
 * @author Igor Costa
 */
public class DDMStructureCTUpgradeProcessTestUtil {

	public static DDMForm getDDMForm() {
		DDMForm ddmForm = DDMFormTestUtil.createDDMForm();

		DDMFormField ddmFormField1 = DDMFormTestUtil.createDDMFormField(
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			DDMFormFieldTypeConstants.NUMERIC, "integer", false, false, false);

		ddmFormField1.setProperty("confirmationErrorMessage", null);
		ddmFormField1.setProperty("confirmationLabel", null);
		ddmFormField1.setProperty("requireConfirmation", true);

		ddmForm.addDDMFormField(ddmFormField1);

		DDMFormField ddmFormField2 = DDMFormTestUtil.createDDMFormField(
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			DDMFormFieldTypeConstants.TEXT, "string", false, false, false);

		ddmFormField2.setProperty("confirmationErrorMessage", null);
		ddmFormField2.setProperty("confirmationLabel", null);
		ddmFormField2.setProperty("requireConfirmation", true);

		ddmForm.addDDMFormField(ddmFormField2);

		return ddmForm;
	}

	public static String getUpgradeStepClassName() {
		return "com.liferay.dynamic.data.mapping.internal.upgrade.v5_4_1." +
			"DDMStructureUpgradeProcess";
	}

}