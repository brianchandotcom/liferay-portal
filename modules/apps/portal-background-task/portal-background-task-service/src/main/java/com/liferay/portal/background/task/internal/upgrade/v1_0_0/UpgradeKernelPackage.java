/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.background.task.internal.upgrade.v1_0_0;

import com.liferay.portal.kernel.dao.orm.WildcardMode;
import com.liferay.portal.kernel.upgrade.UpgradeException;

/**
 * @author Tina Tian
 */
public class UpgradeKernelPackage
	extends com.liferay.portal.upgrade.v7_0_0.UpgradeKernelPackage {

	@Override
	protected void doUpgrade() throws UpgradeException {
		try {
			upgradeLongTextTable(
				"BackgroundTask", "taskContext", "backgroundTaskId",
				_CLASS_NAMES, WildcardMode.SURROUND);
		}
		catch (Exception exception) {
			throw new UpgradeException(exception);
		}
	}

	private static final String[][] _CLASS_NAMES = {
		{
			"com.liferay.portal.security.auth.HttpPrincipal",
			"com.liferay.portal.kernel.security.auth.HttpPrincipal"
		}
	};

}