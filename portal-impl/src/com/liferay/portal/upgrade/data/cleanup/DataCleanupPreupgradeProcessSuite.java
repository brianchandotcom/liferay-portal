/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.upgrade.data.cleanup;

import com.liferay.portal.db.index.PrimaryKeyUpdaterUtil;
import com.liferay.portal.events.StartupHelperUtil;
import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.ReleaseConstants;
import com.liferay.portal.kernel.upgrade.data.cleanup.DataCleanupPreupgradeProcess;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LoggingTimer;
import com.liferay.portal.upgrade.PortalUpgradeProcess;
import com.liferay.portal.util.PropsValues;

import java.sql.Connection;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @author Luis Ortiz
 */
public class DataCleanupPreupgradeProcessSuite {

	public void cleanUp() throws Exception {
		try (Connection connection = DataAccess.getConnection()) {
			if (StartupHelperUtil.isDBNew() ||
				PortalUpgradeProcess.isInLatestSchemaVersion(connection) ||
				(PortalUpgradeProcess.getCurrentState(connection) !=
					ReleaseConstants.STATE_GOOD)) {

				return;
			}
		}

		try (LoggingTimer loggingTimer = new LoggingTimer()) {
			List<DataCleanupPreupgradeProcess> dataCleanupPreupgradeProcesses =
				getSortedDataCleanupPreupgradeProcesses();

			for (DataCleanupPreupgradeProcess dataCleanupPreupgradeProcess :
					dataCleanupPreupgradeProcesses) {

				Class<?> clazz = dataCleanupPreupgradeProcess.getClass();

				if (ArrayUtil.contains(
						PropsValues.
							UPGRADE_DATABASE_PREUPGRADE_DATA_CLEANUP_BLACKLIST,
						clazz.getName())) {

					if (_log.isInfoEnabled()) {
						_log.info(
							"Skipping blacklisted data cleanup process: " +
								clazz.getName());
					}

					continue;
				}

				dataCleanupPreupgradeProcess.upgrade();
			}
		}
	}

	public List<DataCleanupPreupgradeProcess>
		getSortedDataCleanupPreupgradeProcesses() {

		return DataCleanupPreupgradeProcess.
			getSortedDataCleanupPreupgradeProcesses(
				_dataCleanupPreupgradeProcessesMap);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DataCleanupPreupgradeProcessSuite.class);

	private static final Map
		<DataCleanupPreupgradeProcess, List<DataCleanupPreupgradeProcess>>
			_dataCleanupPreupgradeProcessesMap;

	static {
		DataCleanupPreupgradeProcess
			analyticsMessageDataCleanupPreupgradeProcess =
				new AnalyticsMessageDataCleanupPreupgradeProcess();
		DataCleanupPreupgradeProcess companyDataCleanupPreupgradeProcess =
			new CompanyDataCleanupPreupgradeProcess();
		DataCleanupPreupgradeProcess configurationDataCleanupPreupgradeProcess =
			new ConfigurationDataCleanupPreupgradeProcess();
		DataCleanupPreupgradeProcess counterDataCleanupPreupgradeProcess =
			new CounterDataCleanupPreupgradeProcess();
		DataCleanupPreupgradeProcess ddmStructureDataCleanupPreupgradeProcess =
			new DDMStructureDataCleanupPreupgradeProcess();
		DataCleanupPreupgradeProcess dlFileEntryDataCleanupPreupgradeProcess =
			new DLFileEntryDataCleanupPreupgradeProcess();
		DataCleanupPreupgradeProcess groupDataCleanupPreupgradeProcess =
			new GroupDataCleanupPreupgradeProcess();
		DataCleanupPreupgradeProcess journalDataCleanupPreupgradeProcess =
			new JournalDataCleanupPreupgradeProcess();
		DataCleanupPreupgradeProcess
			nullUnicodeContentDataCleanupPreupgradeProcess =
				new NullUnicodeContentDataCleanupPreupgradeProcess();
		DataCleanupPreupgradeProcess
			quartzJobDetailsDataCleanupPreupgradeProcess =
				new QuartzJobDetailsDataCleanupPreupgradeProcess();
		DataCleanupPreupgradeProcess
			updateAllPrimaryKeysDataCleanupPreupgradeProcess =
				new DataCleanupPreupgradeProcess() {

					@Override
					protected void doUpgrade() throws Exception {
						PrimaryKeyUpdaterUtil.updateAllPrimaryKeys();
					}

				};
		DataCleanupPreupgradeProcess userDataCleanupPreupgradeProcess =
			new UserDataCleanupPreupgradeProcess();

		Map<DataCleanupPreupgradeProcess, List<DataCleanupPreupgradeProcess>>
			dataCleanupPreupgradeProcessesMap =
				HashMapBuilder.
					<DataCleanupPreupgradeProcess,
					 List<DataCleanupPreupgradeProcess>>put(
						updateAllPrimaryKeysDataCleanupPreupgradeProcess,
						DataCleanupPreupgradeProcess.dependsOn()
					).put(
						companyDataCleanupPreupgradeProcess,
						DataCleanupPreupgradeProcess.dependsOn(
							updateAllPrimaryKeysDataCleanupPreupgradeProcess)
					).put(
						userDataCleanupPreupgradeProcess,
						DataCleanupPreupgradeProcess.dependsOn(
							companyDataCleanupPreupgradeProcess)
					).put(
						groupDataCleanupPreupgradeProcess,
						DataCleanupPreupgradeProcess.dependsOn(
							userDataCleanupPreupgradeProcess)
					).put(
						analyticsMessageDataCleanupPreupgradeProcess,
						DataCleanupPreupgradeProcess.dependsOn()
					).put(
						configurationDataCleanupPreupgradeProcess,
						DataCleanupPreupgradeProcess.dependsOn(
							userDataCleanupPreupgradeProcess)
					).put(
						ddmStructureDataCleanupPreupgradeProcess,
						DataCleanupPreupgradeProcess.dependsOn(
							groupDataCleanupPreupgradeProcess)
					).put(
						dlFileEntryDataCleanupPreupgradeProcess,
						DataCleanupPreupgradeProcess.dependsOn(
							groupDataCleanupPreupgradeProcess)
					).put(
						nullUnicodeContentDataCleanupPreupgradeProcess,
						DataCleanupPreupgradeProcess.dependsOn(
							ddmStructureDataCleanupPreupgradeProcess)
					).put(
						quartzJobDetailsDataCleanupPreupgradeProcess,
						DataCleanupPreupgradeProcess.dependsOn()
					).put(
						journalDataCleanupPreupgradeProcess,
						DataCleanupPreupgradeProcess.dependsOn(
							ddmStructureDataCleanupPreupgradeProcess)
					).put(
						counterDataCleanupPreupgradeProcess,
						DataCleanupPreupgradeProcess.dependsOn(
							analyticsMessageDataCleanupPreupgradeProcess,
							companyDataCleanupPreupgradeProcess,
							configurationDataCleanupPreupgradeProcess,
							ddmStructureDataCleanupPreupgradeProcess,
							dlFileEntryDataCleanupPreupgradeProcess,
							groupDataCleanupPreupgradeProcess,
							journalDataCleanupPreupgradeProcess,
							nullUnicodeContentDataCleanupPreupgradeProcess,
							quartzJobDetailsDataCleanupPreupgradeProcess,
							updateAllPrimaryKeysDataCleanupPreupgradeProcess,
							userDataCleanupPreupgradeProcess)
					).build();

		_dataCleanupPreupgradeProcessesMap = Collections.unmodifiableMap(
			dataCleanupPreupgradeProcessesMap);
	}

}