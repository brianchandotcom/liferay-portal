/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.upgrade.v4_3_3.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.dynamic.data.mapping.model.DDMFormLayout;
import com.liferay.dynamic.data.mapping.upgrade.BaseDDMStructureLayoutCTUpgradeProcessTestCase;

import org.junit.runner.RunWith;

/**
 * @author Paulo Albuquerque
 */
@RunWith(Arquillian.class)
public class DDMStructureLayoutUpgradeProcessTest
	extends BaseDDMStructureLayoutCTUpgradeProcessTestCase {

	@Override
	protected String getPaginationMode() {
		return DDMFormLayout.WIZARD_MODE;
	}

	@Override
	protected String getSchemaVersion() {
		return "v4_3_3";
	}

}