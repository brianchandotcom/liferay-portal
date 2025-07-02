/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.upgrade.datacleanup;

import com.liferay.portal.kernel.upgrade.UpgradeException;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;

/**
 * @author Luis Ortiz
 */
public abstract class DataCleanupPreupgradeProcess extends UpgradeProcess {

	@Override
	public void upgrade() throws DataCleanupPreupgradeException {
		try {
			super.upgrade();
		}
		catch (UpgradeException upgradeException) {
			throw new DataCleanupPreupgradeException(upgradeException);
		}
	}

	@Override
	public void upgrade(UpgradeProcess upgradeProcess)
		throws DataCleanupPreupgradeException {

		try {
			upgradeProcess.upgrade();
		}
		catch (UpgradeException upgradeException) {
			throw new DataCleanupPreupgradeException(upgradeException);
		}
	}

}