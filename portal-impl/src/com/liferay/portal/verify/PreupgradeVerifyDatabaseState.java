/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.verify;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.db.DBResourceUtil;
import com.liferay.portal.db.partition.util.DBPartitionUtil;
import com.liferay.portal.events.StartupHelperUtil;
import com.liferay.portal.kernel.dao.db.DBInspector;
import com.liferay.portal.kernel.instance.PortalInstancePool;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.ReleaseConstants;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.util.PropsValues;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.upgrade.PortalUpgradeProcess;

import java.sql.Connection;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Jorge Avalos
 */
public class PreupgradeVerifyDatabaseState extends PreupgradeVerifyProcess {

	public void verify() throws VerifyException {
		try {
			try (Connection connection = getConnection()) {
				if (StartupHelperUtil.isDBNew() ||
					PortalUpgradeProcess.isInLatestSchemaVersion(connection) ||
					(PortalUpgradeProcess.getCurrentState(connection) !=
						ReleaseConstants.STATE_GOOD)) {

					return;
				}
			}
		}
		catch (Exception exception) {
			throw new VerifyException(exception);
		}

		super.verify();
	}

	@Override
	protected void doVerify() throws Exception {
		Set<String> serviceComponentPortalTableNames =
			DBResourceUtil.getServiceComponentPortalTableNames(connection);

		Set<String> serviceComponentTableNames =
			DBResourceUtil.getServiceComponentModuleTableNames(connection);

		serviceComponentTableNames.addAll(serviceComponentPortalTableNames);

		if (serviceComponentTableNames.isEmpty()) {
			return;
		}

		DBInspector dbInspector = new DBInspector(connection);

		Set<String> databaseTableNames = new TreeSet<>(
			String.CASE_INSENSITIVE_ORDER);

		databaseTableNames.addAll(dbInspector.getTableNames(null));

		if (!databaseTableNames.containsAll(serviceComponentTableNames)) {
			Set<String> missingTableNames = new TreeSet<>(
				String.CASE_INSENSITIVE_ORDER);

			missingTableNames.addAll(serviceComponentTableNames);

			missingTableNames.removeAll(databaseTableNames);

			Set<String> viewNames = _removeViewNames(
				dbInspector, missingTableNames);

			if (!missingTableNames.isEmpty()) {
				throw new VerifyException(
					"Missing tables detected: " +
						new TreeSet<>(missingTableNames));
			}

			viewNames.removeAll(dbInspector.getViewNames(null));

			if (!viewNames.isEmpty()) {
				throw new VerifyException(
					StringBundler.concat(
						"Missing views detected: ",
						new TreeSet<>(
							viewNames
						).toString(),
						" in company ",
						String.valueOf(
							CompanyThreadLocal.getNonsystemCompanyId())));
			}

			if (!missingTableNames.isEmpty()) {
				throw new VerifyException(
					"Missing tables detected: " +
						new TreeSet<>(missingTableNames));
			}
		}

		if (serviceComponentPortalTableNames.isEmpty()) {
			return;
		}

		Set<String> targetVersionNewTableNames =
			DBResourceUtil.getModuleTableNames(connection);

		targetVersionNewTableNames.addAll(
			DBResourceUtil.getPortalTableNames(connection));

		targetVersionNewTableNames.removeAll(serviceComponentTableNames);

		Set<String> previousUpgradeStaleTableNames = new HashSet<>(
			databaseTableNames);

		previousUpgradeStaleTableNames.retainAll(targetVersionNewTableNames);

		if (!previousUpgradeStaleTableNames.isEmpty()) {
			throw new VerifyException(
				"Stale tables from a previous upgrade detected: " +
					new TreeSet<>(previousUpgradeStaleTableNames));
		}

		_verifyTableColumns(dbInspector);
	}

	private Set<String> _removeViewNames(
			DBInspector dbInspector, Set<String> missingTableNames)
		throws Exception {

		Set<String> viewNames = new TreeSet<>(String.CASE_INSENSITIVE_ORDER);

		if (CompanyThreadLocal.getNonsystemCompanyId() ==
				PortalInstancePool.getDefaultCompanyId()) {

			return viewNames;
		}

		for (String missingTableName : missingTableNames) {
			if (dbInspector.isControlTable(missingTableName)) {
				viewNames.add(missingTableName);
			}
		}

		missingTableNames.removeAll(viewNames);

		return viewNames;
	}

	private void _verifyTableColumns(DBInspector dbInspector) throws Exception {
		Map<String, List<String>> tableColumnDefinitions =
			DBResourceUtil.getServiceComponentPortalTableColumnDefinitions(
				connection);

		if (tableColumnDefinitions.isEmpty()) {
			return;
		}

		tableColumnDefinitions.putAll(
			DBResourceUtil.getServiceComponentModuleTableColumnDefinitions(
				connection));

		Map<String, List<String>> mismatchedTableColumnDefinitions =
			new ConcurrentHashMap<>();
		Map<String, List<String>> missingTableColumnNames =
			new ConcurrentHashMap<>();

		processConcurrently(
			tableColumnDefinitions,
			tableColumnDefinitionsEntry -> {
				for (String columnDefinition :
						tableColumnDefinitionsEntry.getValue()) {

					int indexOf = columnDefinition.indexOf(StringPool.SPACE);

					String columnName = columnDefinition.substring(0, indexOf);
					String columnType = columnDefinition.substring(indexOf + 1);

					if (!dbInspector.hasColumn(
							tableColumnDefinitionsEntry.getKey(), columnName)) {

						missingTableColumnNames.computeIfAbsent(
							tableColumnDefinitionsEntry.getKey(),
							tableName -> new ArrayList<>()
						).add(
							columnName
						);
					}
					else if (!dbInspector.hasColumnType(
								tableColumnDefinitionsEntry.getKey(),
								columnName, columnType)) {

						mismatchedTableColumnDefinitions.computeIfAbsent(
							tableColumnDefinitionsEntry.getKey(),
							tableName -> new ArrayList<>()
						).add(
							columnDefinition
						);
					}
				}
			},
			null);

		String partitionSuffix = StringPool.BLANK;

		if (PropsValues.DATABASE_PARTITION_ENABLED) {
			String partitionName = DBPartitionUtil.getPartitionName(
				CompanyThreadLocal.getNonsystemCompanyId());

			partitionSuffix = " in " + partitionName;
		}

		if (_log.isWarnEnabled()) {
			for (Map.Entry<String, List<String>>
					mismatchedTableColumnDefinitionsEntry :
						new TreeMap<>(mismatchedTableColumnDefinitions).
							entrySet()) {

				if (dbInspector.hasView(
						mismatchedTableColumnDefinitionsEntry.getKey())) {

					continue;
				}

				for (String columnDefinition :
						mismatchedTableColumnDefinitionsEntry.getValue()) {

					int indexOf = columnDefinition.indexOf(StringPool.SPACE);

					String columnName = columnDefinition.substring(0, indexOf);
					String columnType = columnDefinition.substring(indexOf + 1);

					_log.warn(
						StringBundler.concat(
							"Column ", dbInspector.normalizeName(columnName),
							" is not defined as ", columnType, " for ",
							dbInspector.normalizeName(
								mismatchedTableColumnDefinitionsEntry.getKey()),
							partitionSuffix));
				}
			}
		}

		StringBundler sb = new StringBundler();

		for (Map.Entry<String, List<String>> missingTableColumnNamesEntry :
				new TreeMap<>(missingTableColumnNames).entrySet()) {

			for (String columnName : missingTableColumnNamesEntry.getValue()) {
				sb.append(
					StringBundler.concat(
						"Column ", dbInspector.normalizeName(columnName),
						" is missing for ",
						dbInspector.normalizeName(
							missingTableColumnNamesEntry.getKey()),
						partitionSuffix));
				sb.append(StringPool.NEW_LINE);
			}
		}

		if (sb.length() != 0) {
			throw new VerifyException(sb.toString());
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		PreupgradeVerifyDatabaseState.class);

}