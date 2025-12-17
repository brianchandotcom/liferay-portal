/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.data.cleanup.internal.verify;

import com.liferay.data.cleanup.internal.verify.util.PostUpgradeDataCleanupProcessUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.db.DBInspector;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.ClassName;
import com.liferay.portal.kernel.model.ModelHintsUtil;
import com.liferay.portal.kernel.module.util.SystemBundleUtil;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.upgrade.data.cleanup.util.DataCleanupLoggingUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;

/**
 * @author Luis Ortiz
 */
public class ClassNamePostUpgradeDataCleanupProcess
	implements PostUpgradeDataCleanupProcess {

	public ClassNamePostUpgradeDataCleanupProcess(
		ClassNameLocalService classNameLocalService, Connection connection) {

		_classNameLocalService = classNameLocalService;

		_connection = connection;

		_dbInspector = new DBInspector(_connection);
	}

	@Override
	public void cleanUp() throws Exception {
		if (!PostUpgradeDataCleanupProcessUtil.isEveryLiferayBundleResolved()) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					StringBundler.concat(
						ClassNamePostUpgradeDataCleanupProcess.class.
							getSimpleName(),
						" cannot be executed because there are modules with ",
						"unsatisfied references"));
			}

			return;
		}

		BundleContext bundleContext = SystemBundleUtil.getBundleContext();
		List<ClassName> classNames = _classNameLocalService.getClassNames(
			QueryUtil.ALL_POS, QueryUtil.ALL_POS);
		DBInspector dbInspector = new DBInspector(_connection);
		Set<String> models = new HashSet<>(ModelHintsUtil.getModels());

		for (ClassName className : classNames) {
			String value = className.getValue();

			if (!value.startsWith("com.liferay.")) {
				continue;
			}

			int dashIndex = value.indexOf(StringPool.DASH);
			int poundIndex = value.indexOf(StringPool.POUND);

			if (dashIndex != -1) {
				value = value.substring(0, dashIndex);
			}

			if (poundIndex != -1) {
				value = value.substring(0, poundIndex);
			}

			if (models.contains(value)) {
				continue;
			}

			Class<?> clazz = null;

			for (Bundle bundle : bundleContext.getBundles()) {
				try {
					clazz = bundle.loadClass(value);

					break;
				}
				catch (ClassNotFoundException classNotFoundException) {
					if (_log.isDebugEnabled()) {
						_log.debug(classNotFoundException);
					}
				}
			}

			if (clazz != null) {
				continue;
			}

			List<String> tableNames = dbInspector.getTableNames(null);
			Set<String> usedTableNames = new HashSet<>();

			tableNames.remove(dbInspector.normalizeName("ClassName_"));

			for (String tableName : tableNames) {
				if (!dbInspector.hasColumn(tableName, "classNameId")) {
					continue;
				}

				try (PreparedStatement preparedStatement =
						_connection.prepareStatement(
							"select 1 from " + tableName +
								" where classNameId = ?")) {

					preparedStatement.setLong(1, className.getClassNameId());

					try (ResultSet resultSet =
							preparedStatement.executeQuery()) {

						if (resultSet.next()) {
							usedTableNames.add(tableName);
						}
					}
				}
			}

			if (usedTableNames.isEmpty()) {
				_classNameLocalService.deleteClassName(className);

				DataCleanupLoggingUtil.logDelete(
					_log, 1, _dbInspector.normalizeName("ClassName_"),
					StringBundler.concat(
						"\"", value,
						"\" is not defined in any deployed module and is not ",
						"in use"));
			}
			else if (_log.isWarnEnabled()) {
				_log.warn(
					StringBundler.concat(
						"Class name ", value,
						" is not defined in any deployed module but is ",
						"referenced in the next tables: ",
						String.join(", ", new TreeSet<>(usedTableNames))));
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ClassNamePostUpgradeDataCleanupProcess.class);

	private final ClassNameLocalService _classNameLocalService;
	private final Connection _connection;
	private final DBInspector _dbInspector;

}