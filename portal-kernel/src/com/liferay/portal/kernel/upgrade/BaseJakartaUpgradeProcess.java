/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.upgrade;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.upgrade.util.JakartaUpgradeProcessUtil;
import com.liferay.portal.kernel.util.StringBundler;

import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * @author Luis Ortiz
 */
public abstract class BaseJakartaUpgradeProcess extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		for (String[] tableAndColumnNames : getTableAndColumnNames()) {
			Set<String> modifiedKeys = new CopyOnWriteArraySet<>();

			String columnName = tableAndColumnNames[1];

			String tableName = tableAndColumnNames[0];

			String[] primaryKeyColumnNames = getPrimaryKeyColumnNames(
				connection, tableName);

			processConcurrently(
				JakartaUpgradeProcessUtil.getSelectSQL(
					columnName, primaryKeyColumnNames, tableName),
				JakartaUpgradeProcessUtil.getUpdateSQL(
					columnName, primaryKeyColumnNames, tableName),
				resultSet -> JakartaUpgradeProcessUtil.getSelectResultSetData(
					columnName, primaryKeyColumnNames, resultSet),
				(values, preparedStatement) -> {
					String modifiedKey =
						JakartaUpgradeProcessUtil.updateJakartaValue(
							getCustomSeparators(), preparedStatement,
							primaryKeyColumnNames, values);

					if (modifiedKey == null) {
						return;
					}

					modifiedKeys.add(modifiedKey);
				},
				StringBundler.concat(
					"Unable to update javax references in table ", tableName,
					" column ", columnName, " for company ",
					CompanyThreadLocal.getCompanyId(
					).toString()));

			if (_log.isInfoEnabled()) {
				StringBundler sb = new StringBundler();

				for (String key : modifiedKeys) {
					sb.append(key);
					sb.append(", ");
				}

				sb.setIndex(sb.index() - 1);

				_log.info(
					StringBundler.concat(
						"Table/column ", tableName, "/", columnName,
						" for company ",
						CompanyThreadLocal.getCompanyId(
						).toString(),
						" has been upgraded for next IDs: ", sb.toString()));
			}
		}
	}

	protected char[] getCustomSeparators() {
		return new char[0];
	}

	protected abstract String[][] getTableAndColumnNames();

	private static final Log _log = LogFactoryUtil.getLog(
		BaseJakartaUpgradeProcess.class);

}