/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.knowledge.base.internal.upgrade.v2_0_0;

import com.liferay.portal.upgrade.v7_0_0.UpgradeKernelPackage;

/**
 * @author Adolfo Pérez
 */
public class UpgradeClassNames extends UpgradeKernelPackage {

	@Override
	protected String[][] getClassNames() {
		return new String[][] {
			{"com.liferay.knowledgebase.", "com.liferay.knowledge.base."}
		};
	}

	@Override
	protected String[][] getResourceNames() {
		return new String[][] {
			{
				"com.liferay.knowledgebase.admin",
				"com.liferay.knowledge.base.admin"
			},
			{
				"com.liferay.knowledgebase.display",
				"com.liferay.knowledge.base.display"
			}
		};
	}

}