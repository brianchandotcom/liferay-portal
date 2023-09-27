/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.upgrade;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;

import java.util.Arrays;

/**
 * @author Luis Ortiz Fuentes
 */
public class UpgradeProcessFactory {

	public static UpgradeProcess addColumns(
		String tableName, String... columnDefinitions) {

		return new UpgradeProcess(
			_getUpgradeInfo(
				tableName,
				"add the columns " + Arrays.toString(columnDefinitions))) {

			@Override
			protected void doUpgrade() throws Exception {
				for (String columnDefinition : columnDefinitions) {
					alterTableAddColumn(
						tableName,
						columnDefinition.substring(
							0, columnDefinition.indexOf(StringPool.SPACE)),
						columnDefinition.substring(
							columnDefinition.indexOf(StringPool.SPACE) + 1));
				}
			}

		};
	}

	public static UpgradeProcess alterColumnName(
		String tableName, String oldColumnName, String newColumnDefinition) {

		return new UpgradeProcess(
			_getUpgradeInfo(
				tableName,
				StringBundler.concat(
					"alter the name of the column ", oldColumnName, " to ",
					newColumnDefinition))) {

			@Override
			protected void doUpgrade() throws Exception {
				alterColumnName(tableName, oldColumnName, newColumnDefinition);
			}

		};
	}

	public static UpgradeProcess alterColumnType(
		String tableName, String columnName, String newColumnType) {

		return new UpgradeProcess(
			_getUpgradeInfo(
				tableName,
				StringBundler.concat(
					"alter the type of the column ", columnName, " to ",
					newColumnType))) {

			@Override
			protected void doUpgrade() throws Exception {
				alterColumnType(tableName, columnName, newColumnType);
			}

		};
	}

	public static UpgradeProcess dropColumns(
		String tableName, String... columnNames) {

		return new UpgradeProcess(
			_getUpgradeInfo(
				tableName,
				"drop the columns " + Arrays.toString(columnNames))) {

			@Override
			protected void doUpgrade() throws Exception {
				for (String columnName : columnNames) {
					alterTableDropColumn(tableName, columnName);
				}
			}

		};
	}

	public static UpgradeProcess dropTables(String... tableNames) {
		return new UpgradeProcess(
			_getUpgradeInfo("drop tables " + Arrays.toString(tableNames))) {

			@Override
			protected void doUpgrade() throws Exception {
				for (String tableName : tableNames) {
					dropTable(tableName);
				}
			}

		};
	}

	private static String _getUpgradeInfo(String message) {
		return _getUpgradeInfo(null, "Modifying schema to " + message);
	}

	private static String _getUpgradeInfo(String tableName, String message) {
		Thread thread = Thread.currentThread();

		String callerClassName = thread.getStackTrace()[3].getClassName();

		if (tableName == null) {
			return callerClassName + " - " + message;
		}

		return StringBundler.concat(
			callerClassName, " - Modifying table ", tableName, " to ", message);
	}

}