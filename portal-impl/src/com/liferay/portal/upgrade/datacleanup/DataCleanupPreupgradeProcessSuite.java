/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.upgrade.datacleanup;

import com.liferay.portal.kernel.upgrade.datacleanup.DataCleanupPreupgradeProcess;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Luis Ortiz
 */
public class DataCleanupPreupgradeProcessSuite {

	public void cleanUp() throws Exception {
		for (DataCleanupPreupgradeProcess dataCleanupPreupgradeProcess :
				_dataCleanupPreupgradeProcesses) {

			dataCleanupPreupgradeProcess.upgrade();
		}
	}

	private final List<DataCleanupPreupgradeProcess>
		_dataCleanupPreupgradeProcesses = new ArrayList<>();

}