/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.upgrade.v7_2_x;

import com.liferay.portal.kernel.upgrade.BaseBadColumnNamesUpgradeProcess;
import com.liferay.portal.upgrade.v7_2_x.util.CompanyTable;
import com.liferay.portal.upgrade.v7_2_x.util.LayoutTable;

/**
 * @author Tina Tian
 */
public class UpgradeBadColumnNames extends BaseBadColumnNamesUpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		upgradeBadColumnNames(CompanyTable.class, "system");
		upgradeBadColumnNames(LayoutTable.class, "system");
	}

}