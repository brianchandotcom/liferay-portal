/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.upgrade.v3_9_1.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.upgrade.v3_9_1.test.util.DDMStructureCTUpgradeProcessTestUtil;

import org.junit.runner.RunWith;

/**
 * @author Pedro Leite
 */
@RunWith(Arquillian.class)
public class DDMStructureCTUpgradeProcessTest
	extends com.liferay.dynamic.data.mapping.upgrade.v5_1_1.test.
				DDMStructureCTUpgradeProcessTest {

	@Override
	protected DDMForm getDDMForm() {
		return DDMStructureCTUpgradeProcessTestUtil.getDDMForm();
	}

	@Override
	protected String getUpgradeStepClassName() {
		return DDMStructureCTUpgradeProcessTestUtil.getUpgradeStepClassName();
	}

}