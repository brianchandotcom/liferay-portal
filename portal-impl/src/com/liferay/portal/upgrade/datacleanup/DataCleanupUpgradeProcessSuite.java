/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.upgrade.datacleanup;

import com.liferay.portal.kernel.upgrade.datacleanup.DataCleanupUpgradeProcess;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Luis Ortiz
 */
public class DataCleanupUpgradeProcessSuite {

	public void cleanUp() throws Exception {
		for (DataCleanupUpgradeProcess dataCleanupUpgradeProcess :
				_dataCleanupUpgradeProcesses) {

			dataCleanupUpgradeProcess.upgrade();
		}
	}

	private final List<DataCleanupUpgradeProcess> _dataCleanupUpgradeProcesses =
		new ArrayList<>();

}