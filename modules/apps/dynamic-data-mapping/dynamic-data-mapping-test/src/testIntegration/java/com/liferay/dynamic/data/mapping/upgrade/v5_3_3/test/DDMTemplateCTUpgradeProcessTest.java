/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.upgrade.v5_3_3.test;

/**
 * @author Pedro Leite
 */
public class DDMTemplateCTUpgradeProcessTest
	extends com.liferay.dynamic.data.mapping.upgrade.v4_3_2.test.
				DDMTemplateCTUpgradeProcessTest {

	@Override
	protected String getUpgradeStepClassName() {
		return "com.liferay.dynamic.data.mapping.internal.upgrade.v5_3_3." +
			"BrowserSnifferTemplateUpgradeProcess";
	}

}