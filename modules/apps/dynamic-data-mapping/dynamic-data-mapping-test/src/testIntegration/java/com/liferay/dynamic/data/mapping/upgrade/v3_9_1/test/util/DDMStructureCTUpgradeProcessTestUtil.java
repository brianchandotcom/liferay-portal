/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.upgrade.v3_9_1.test.util;

import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.test.util.DDMFormTestUtil;

/**
 * @author Igor Costa
 */
public class DDMStructureCTUpgradeProcessTestUtil {

	public static DDMForm getDDMForm() {
		DDMForm ddmForm = DDMFormTestUtil.createDDMForm();

		DDMFormField ddmFormField = DDMFormTestUtil.createDDMFormField(
			"text", "Text", "text", "string", false, false, false);

		ddmFormField.setFieldReference(null);

		ddmForm.addDDMFormField(ddmFormField);

		return ddmForm;
	}

	public static String getUpgradeStepClassName() {
		return "com.liferay.dynamic.data.mapping.internal.upgrade.v3_9_1." +
			"DDMStructureUpgradeProcess";
	}

}