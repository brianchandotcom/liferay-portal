/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.client.extension.internal.upgrade.v3_0_0;

import com.liferay.portal.kernel.dao.orm.WildcardMode;
import com.liferay.portal.kernel.upgrade.UpgradeException;
import com.liferay.portal.upgrade.v7_0_0.UpgradeKernelPackage;

/**
 * @author Dante Wang
 */
public class ClassNamesUpgradeProcess extends UpgradeKernelPackage {

	@Override
	protected void doUpgrade() throws UpgradeException {
		super.doUpgrade();

		try {
			upgradeTable(
				"ResourcePermission", "primKey", getClassNames(),
				WildcardMode.LEADING);
			upgradeTable(
				"ResourcePermission", "primKey", getResourceNames(),
				WildcardMode.LEADING);
		}
		catch (Exception exception) {
			throw new UpgradeException(exception);
		}
	}

	@Override
	protected String[][] getClassNames() {
		return _CLASS_NAMES;
	}

	@Override
	protected String[][] getResourceNames() {
		return _RESOURCE_NAMES;
	}

	private static final String[][] _CLASS_NAMES = {
		{
			"com.liferay.remote.app.model.RemoteAppEntry",
			"com.liferay.client.extension.model.ClientExtensionEntry"
		}
	};

	private static final String[][] _RESOURCE_NAMES = {
		{"com.liferay.remote.app", "com.liferay.client.extension"}
	};

}