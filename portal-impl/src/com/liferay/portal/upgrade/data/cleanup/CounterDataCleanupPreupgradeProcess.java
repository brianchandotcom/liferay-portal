/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.upgrade.data.cleanup;

import com.liferay.counter.kernel.model.Counter;
import com.liferay.counter.kernel.service.CounterLocalServiceUtil;
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.dao.orm.common.SQLTransformer;
import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBInspector;
import com.liferay.portal.kernel.dao.db.DBManagerUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.upgrade.data.cleanup.DataCleanupPreupgradeProcess;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PortalClassLoaderUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Luis Ortiz
 */
public class CounterDataCleanupPreupgradeProcess
	extends DataCleanupPreupgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		try (PreparedStatement preparedStatement = connection.prepareStatement(
				"select name, currentId from Counter");
			ResultSet resultSet = preparedStatement.executeQuery()) {

			ClassLoader classLoader = PortalClassLoaderUtil.getClassLoader();
			DBInspector dbInspector = new DBInspector(connection);
			List<String> excludedTableNames = new ArrayList<>();
			long kernelCounterValue = 0;

			while (resultSet.next()) {
				String counterName = resultSet.getString(1);
				long counterValue = resultSet.getLong(2);

				if (counterName.equals(Counter.class.getName())) {
					kernelCounterValue = counterValue;

					continue;
				}

				Matcher matcher = _layoutPattern.matcher(counterName);

				if (matcher.matches()) {
					long groupId = GetterUtil.getLong(matcher.group(2));
					boolean privateLayout = matcher.group(
						3
					).equalsIgnoreCase(
						"true"
					);

					_checkLayoutCounter(
						counterName, counterValue, dbInspector, groupId,
						privateLayout);

					continue;
				}

				String tableName;

				try {
					Class<?> clazz = classLoader.loadClass(counterName);

					ImplementationClassName implementationClassName =
						clazz.getAnnotation(ImplementationClassName.class);

					if (implementationClassName == null) {
						if (_log.isWarnEnabled()) {
							_log.warn("Unable find model for " + clazz);
						}

						continue;
					}

					clazz = classLoader.loadClass(
						implementationClassName.value());

					tableName = (String)clazz.getField(
						"TABLE_NAME"
					).get(
						null
					);
				}
				catch (ClassNotFoundException classNotFoundException) {
					if (_log.isDebugEnabled()) {
						_log.debug(classNotFoundException);
					}

					tableName = StringUtil.extractLast(counterName, '.');
				}

				if (!dbInspector.hasTable(tableName)) {
					continue;
				}

				_checkTableCounter(
					counterName, counterValue, dbInspector, tableName);

				if (!counterName.equals(DLFileEntry.class.getName())) {
					excludedTableNames.add(
						dbInspector.normalizeName(tableName));
				}
			}

			_checkKernelCounter(
				kernelCounterValue, dbInspector, excludedTableNames);
		}
	}

	private void _checkKernelCounter(
			long counterValue, DBInspector dbInspector,
			List<String> excludedTableNames)
		throws Exception {

		List<String> tableNames = dbInspector.getTableNames(null);

		tableNames.remove("Company");
		tableNames.remove("Counter");
		tableNames.removeAll(excludedTableNames);

		long latestCounterValue = 0L;

		for (String tableName : tableNames) {
			String primaryKeyColumnName = _getPrimaryKeyColumnName(
				dbInspector, tableName);

			if ((primaryKeyColumnName == null) ||
				!dbInspector.isNumeric(tableName, primaryKeyColumnName)) {

				continue;
			}

			long maxCounterValue = _getMaxCounterValue(
				primaryKeyColumnName, tableName);

			if (maxCounterValue > latestCounterValue) {
				latestCounterValue = maxCounterValue;
			}
		}

		if (counterValue < latestCounterValue) {
			CounterLocalServiceUtil.reset(
				Counter.class.getName(), latestCounterValue);

			if (_log.isInfoEnabled()) {
				_log.info(
					StringBundler.concat(
						"Counter ", Counter.class.getName(),
						" has been reset to value ", latestCounterValue));
			}
		}
	}

	private void _checkLayoutCounter(
			String counterName, long counterValue, DBInspector dbInspector,
			long groupId, boolean privateLayout)
		throws Exception {

		if (!dbInspector.hasTable("Layout")) {
			_log.error("Table Layout does not exist");

			return;
		}

		try (PreparedStatement preparedStatement1 = connection.prepareStatement(
				SQLTransformer.transform(
					StringBundler.concat(
						"select max(layoutId) from Layout where groupId = ",
						groupId, " and privateLayout = ",
						privateLayout ? "[$TRUE$]" : "[$FALSE$]")));
			ResultSet resultSet = preparedStatement1.executeQuery()) {

			if (resultSet.next()) {
				long maxValue = resultSet.getLong(1);

				if (resultSet.wasNull()) {
					try (PreparedStatement preparedStatement2 =
							connection.prepareStatement(
								"delete from Counter where name = '" +
									counterName + "'")) {

						preparedStatement2.executeUpdate();

						if (_log.isInfoEnabled()) {
							_log.info(
								"Deleted counter " + counterName +
									" because it is unused");
						}
					}

					return;
				}

				if (counterValue >= maxValue) {
					return;
				}

				CounterLocalServiceUtil.reset(counterName, maxValue);

				if (_log.isInfoEnabled()) {
					_log.info(
						StringBundler.concat(
							"Counter ", counterName,
							" has been reset to value ", maxValue));
				}
			}
		}
	}

	private void _checkTableCounter(
			String counterName, long counterValue, DBInspector dbInspector,
			String tableName)
		throws Exception {

		String primaryKeyColumnName = _getPrimaryKeyColumnName(
			dbInspector, tableName);

		if (counterName.equals(DLFileEntry.class.getName())) {
			primaryKeyColumnName = "name";
		}

		if (primaryKeyColumnName == null) {
			return;
		}

		long maxCounterValue = _getMaxCounterValue(
			primaryKeyColumnName, tableName);

		if (counterValue >= maxCounterValue) {
			return;
		}

		CounterLocalServiceUtil.reset(counterName, maxCounterValue);

		if (_log.isInfoEnabled()) {
			_log.info(
				StringBundler.concat(
					"Counter ", counterName, " has been reset to value ",
					maxCounterValue));
		}
	}

	private long _getMaxCounterValue(
			String primaryKeyColumnName, String tableName)
		throws Exception {

		try (PreparedStatement preparedStatement1 = connection.prepareStatement(
				StringBundler.concat(
					"select max(", primaryKeyColumnName, ") from ", tableName));
			ResultSet resultSet = preparedStatement1.executeQuery()) {

			if (resultSet.next()) {
				return resultSet.getLong(1);
			}
		}

		return 0L;
	}

	private String _getPrimaryKeyColumnName(
			DBInspector dbInspector, String tableName)
		throws Exception {

		DB db = DBManagerUtil.getDB();

		List<String> primaryKeyColumnNames = new ArrayList<>(
			Arrays.asList(db.getPrimaryKeyColumnNames(connection, tableName)));

		primaryKeyColumnNames.remove(
			dbInspector.normalizeName("ctCollectionId"));

		if (primaryKeyColumnNames.size() != 1) {
			return null;
		}

		return primaryKeyColumnNames.get(0);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CounterDataCleanupPreupgradeProcess.class);

	private static final Pattern _layoutPattern = Pattern.compile(
		"^([a-zA-Z0-9_.]+)#(\\d+)#(true|false)$");

}