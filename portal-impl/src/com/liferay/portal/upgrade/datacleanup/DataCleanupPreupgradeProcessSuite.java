/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.upgrade.datacleanup;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.upgrade.datacleanup.DataCleanupPreupgradeProcess;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Luis Ortiz
 */
public class DataCleanupPreupgradeProcessSuite {

	public void cleanUp() throws Exception {
		if (_log.isInfoEnabled()) {
			_log.info(
				"Starting " +
					DataCleanupPreupgradeProcessSuite.class.getName());
		}

		for (DataCleanupPreupgradeProcess dataCleanupPreupgradeProcess :
				_dataCleanupPreupgradeProcesses) {

			dataCleanupPreupgradeProcess.upgrade();
		}

		if (_log.isInfoEnabled()) {
			_log.info(
				"Finished " +
					DataCleanupPreupgradeProcessSuite.class.getName());
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DataCleanupPreupgradeProcessSuite.class);

	private final List<DataCleanupPreupgradeProcess>
		_dataCleanupPreupgradeProcesses = new ArrayList<>();

}