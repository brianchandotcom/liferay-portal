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
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.upgrade.PortalUpgradeProcess;
import com.liferay.portal.util.PropsValues;

import java.sql.Connection;

import java.util.ArrayList;
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

		if (_log.isInfoEnabled()) {
			_log.info(
				"Starting " +
					DataCleanupPreupgradeProcessSuite.class.getName());
		}

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

		if (_log.isInfoEnabled()) {
			_log.info(
				"Finished " +
					DataCleanupPreupgradeProcessSuite.class.getName());
		}
	}

	public List<DataCleanupPreupgradeProcess>
		getSortedDataCleanupPreupgradeProcesses() {

		List<DataCleanupPreupgradeProcess>
			sortedDataCleanupPreupgradeProcesses = new ArrayList<>();

		while (sortedDataCleanupPreupgradeProcesses.size() !=
					_dataCleanupPreupgradeProcessesMap.size()) {

			int size = sortedDataCleanupPreupgradeProcesses.size();

			for (Map.Entry
					<DataCleanupPreupgradeProcess,
					 List<DataCleanupPreupgradeProcess>> entry :
						_dataCleanupPreupgradeProcessesMap.entrySet()) {

				DataCleanupPreupgradeProcess dataCleanupPreupgradeProcess =
					entry.getKey();

				if (sortedDataCleanupPreupgradeProcesses.contains(
						dataCleanupPreupgradeProcess) ||
					!sortedDataCleanupPreupgradeProcesses.containsAll(
						entry.getValue())) {

					continue;
				}

				sortedDataCleanupPreupgradeProcesses.add(
					dataCleanupPreupgradeProcess);
			}

			if (size == sortedDataCleanupPreupgradeProcesses.size()) {
				throw new RuntimeException("Circular dependency");
			}
		}

		return sortedDataCleanupPreupgradeProcesses;
	}

	private static List<DataCleanupPreupgradeProcess> _dependsOn(
		DataCleanupPreupgradeProcess... dataCleanupPreupgradeProcesses) {

		return ListUtil.fromArray(dataCleanupPreupgradeProcesses);
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
			dataCleanupPreupgradeProcessestMap =
				HashMapBuilder.
					<DataCleanupPreupgradeProcess,
					 List<DataCleanupPreupgradeProcess>>put(
						updateAllPrimaryKeysDataCleanupPreupgradeProcess,
						_dependsOn()
					).put(
						companyDataCleanupPreupgradeProcess,
						_dependsOn(
							updateAllPrimaryKeysDataCleanupPreupgradeProcess)
					).put(
						userDataCleanupPreupgradeProcess,
						_dependsOn(companyDataCleanupPreupgradeProcess)
					).put(
						groupDataCleanupPreupgradeProcess,
						_dependsOn(userDataCleanupPreupgradeProcess)
					).put(
						analyticsMessageDataCleanupPreupgradeProcess,
						_dependsOn()
					).put(
						configurationDataCleanupPreupgradeProcess,
						_dependsOn(userDataCleanupPreupgradeProcess)
					).put(
						ddmStructureDataCleanupPreupgradeProcess,
						_dependsOn(groupDataCleanupPreupgradeProcess)
					).put(
						dlFileEntryDataCleanupPreupgradeProcess,
						_dependsOn(groupDataCleanupPreupgradeProcess)
					).put(
						nullUnicodeContentDataCleanupPreupgradeProcess,
						_dependsOn(ddmStructureDataCleanupPreupgradeProcess)
					).put(
						quartzJobDetailsDataCleanupPreupgradeProcess,
						_dependsOn()
					).put(
						journalDataCleanupPreupgradeProcess,
						_dependsOn(ddmStructureDataCleanupPreupgradeProcess)
					).put(
						counterDataCleanupPreupgradeProcess,
						_dependsOn(
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
			dataCleanupPreupgradeProcessestMap);
	}

}