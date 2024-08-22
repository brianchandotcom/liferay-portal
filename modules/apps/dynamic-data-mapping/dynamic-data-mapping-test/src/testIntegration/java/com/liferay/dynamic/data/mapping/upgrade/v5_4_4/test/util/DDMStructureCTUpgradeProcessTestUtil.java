/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.upgrade.v5_4_4.test.util;

import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormRule;
import com.liferay.dynamic.data.mapping.test.util.DDMFormTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;

import java.util.Arrays;

/**
 * @author Igor Costa
 */
public class DDMStructureCTUpgradeProcessTestUtil {

	public static DDMForm getDDMForm() {
		DDMForm ddmForm = DDMFormTestUtil.createDDMForm(
			RandomTestUtil.randomString());

		ddmForm.addDDMFormRule(
			new DDMFormRule(Arrays.asList("jumpPage(0, 2)"), "TRUE"));

		return ddmForm;
	}

	public static String getUpgradeStepClassName() {
		return "com.liferay.dynamic.data.mapping.internal.upgrade.v5_4_4." +
			"DDMStructureUpgradeProcess";
	}

}