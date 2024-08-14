/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.upgrade.v5_1_5.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;

import org.junit.runner.RunWith;

/**
 * @author Paulo Albuquerque
 */
@RunWith(Arquillian.class)
public class DDMStructureLayoutCTUpgradeProcessTest
	extends com.liferay.dynamic.data.mapping.upgrade.v4_3_3.test.
				DDMStructureLayoutCTUpgradeProcessTest {

	@Override
	protected String getPaginationMode() {
		return "paginated";
	}

	@Override
	protected String getSchemaVersion() {
		return "v5_1_5";
	}

}