/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.data.cleanup.internal.verify;

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
import com.liferay.portal.verify.VerifyProcess;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;

/**
 * @author Luis Ortiz
 */
public class ClassNameDataCleanupVerifyProcess extends VerifyProcess {

	public ClassNameDataCleanupVerifyProcess(
		ClassNameLocalService classNameLocalService) {

		_classNameLocalService = classNameLocalService;
	}

	@Override
	protected void doVerify() throws Exception {
		BundleContext bundleContext = SystemBundleUtil.getBundleContext();

		for (Bundle bundle : bundleContext.getBundles()) {
			String bundleSymbolicName = bundle.getSymbolicName();

			if (!bundleSymbolicName.startsWith("com.liferay.")) {
				continue;
			}

			if (bundle.getState() == Bundle.INSTALLED) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"ClassNameDataCleanupVerifyProcess cannot be " +
							"executed because there are modules with " +
								"unsatisfied references");
				}

				return;
			}
		}

		List<ClassName> classNames = _classNameLocalService.getClassNames(
			QueryUtil.ALL_POS, QueryUtil.ALL_POS);
		DBInspector dbInspector = new DBInspector(connection);
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
			List<String> usedTableNames = new ArrayList<>();

			tableNames.remove(dbInspector.normalizeName("ClassName_"));

			for (String tableName : tableNames) {
				if (!dbInspector.hasColumn(tableName, "classNameId")) {
					continue;
				}

				try (PreparedStatement preparedStatement =
						connection.prepareStatement(
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

				if (_log.isInfoEnabled()) {
					_log.info(
						"ClassName " + value +
							" has been deleted because it is not in use");
				}
			}
			else if (_log.isWarnEnabled()) {
				_log.warn(
					StringBundler.concat(
						"ClassName ", value,
						" has not been found but is referenced in the next ",
						"tables: ", String.join(", ", usedTableNames)));
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ClassNameDataCleanupVerifyProcess.class);

	private final ClassNameLocalService _classNameLocalService;

}