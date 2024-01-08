/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.internal.upgrade.v3_2_9;

import com.liferay.document.library.internal.upgrade.helper.DLConfigurationUpgradeHelper;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;

/**
 * @author Adolfo Pérez
 */
public class DLSizeLimitConfigurationUpgradeProcess extends UpgradeProcess {

	public DLSizeLimitConfigurationUpgradeProcess(
		DLConfigurationUpgradeHelper dlConfigurationUpgradeHelper) {

		_dlConfigurationUpgradeHelper = dlConfigurationUpgradeHelper;
	}

	@Override
	protected void doUpgrade() throws Exception {
		if (!_dlConfigurationUpgradeHelper.hasLegacyProps() ||
			!_dlConfigurationUpgradeHelper.
				hasDLSizeLimitConfigurationChanges() ||
			!_dlConfigurationUpgradeHelper.
				hasDLFileEntryConfigurationChanges()) {

			return;
		}

		_dlConfigurationUpgradeHelper.updateDLSizeLimitConfiguration();
	}

	private final DLConfigurationUpgradeHelper _dlConfigurationUpgradeHelper;

}