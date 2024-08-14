/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.upgrade.v3_8_1.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.upgrade.v3_8_1.test.util.DDMFormFieldUpgradeProcessTestUtil;
import com.liferay.dynamic.data.mapping.upgrade.v5_1_1.test.DDMStructureCTUpgradeProcessTest;

import org.junit.runner.RunWith;

/**
 * @author Carolina Barbosa
 */
@RunWith(Arquillian.class)
public class DDMFormFieldDDMStructureCTUpgradeProcessTest
	extends DDMStructureCTUpgradeProcessTest {

	@Override
	protected String getClassName() {
		return DDMFormFieldUpgradeProcessTestUtil.getClassName();
	}

	@Override
	protected DDMForm getDDMForm() {
		return DDMFormFieldUpgradeProcessTestUtil.getDDMForm();
	}

}