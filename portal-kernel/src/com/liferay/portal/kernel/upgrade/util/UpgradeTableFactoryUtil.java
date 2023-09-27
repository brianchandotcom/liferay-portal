/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.upgrade.util;

/**
 * @author Brian Wing Shun Chan
 */
public class UpgradeTableFactoryUtil {

	public static UpgradeTable getUpgradeTable(
		String tableName, Object[][] columns, UpgradeColumn... upgradeColumns) {

		return _upgradeTableFactory.getUpgradeTable(
			tableName, columns, upgradeColumns);
	}

	public static UpgradeTableFactory getUpgradeTableFactory() {
		return _upgradeTableFactory;
	}

	public void setUpgradeTableFactory(
		UpgradeTableFactory upgradeTableFactory) {

		_upgradeTableFactory = upgradeTableFactory;
	}

	private static UpgradeTableFactory _upgradeTableFactory;

}