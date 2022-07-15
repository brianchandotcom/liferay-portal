/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
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
				"Adding the columns " + Arrays.toString(columnDefinitions))) {

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
					"Altering the name of the column ", oldColumnName, " to ",
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
					"Altering the type of the column ", columnName, " to ",
					newColumnType))) {

			@Override
			protected void doUpgrade() throws Exception {
				alterColumnType(tableName, columnName, newColumnType);
			}

		};
	}

	/**
	 * @deprecated As of Cavanaugh (7.4.x), replaced by {@link #alterColumnType(String, String, String)}
	 */
	@Deprecated
	public static UpgradeProcess alterColumnTypes(
		String tableName, String newType, String... columnNames) {

		return new UpgradeProcess(
			_getUpgradeInfo(
				tableName,
				StringBundler.concat(
					"Altering the type of the columns ",
					Arrays.toString(columnNames), " to ", newType))) {

			@Override
			protected void doUpgrade() throws Exception {
				for (String columnName : columnNames) {
					alterColumnType(tableName, columnName, newType);
				}
			}

		};
	}

	public static UpgradeProcess dropColumns(
		String tableName, String... columnNames) {

		return new UpgradeProcess(
			_getUpgradeInfo(
				tableName,
				"Dropping the columns " + Arrays.toString(columnNames))) {

			@Override
			protected void doUpgrade() throws Exception {
				for (String columnName : columnNames) {
					alterTableDropColumn(tableName, columnName);
				}
			}

		};
	}

	public static UpgradeProcess runSQL(String sql) {
		return new UpgradeProcess(
			_getUpgradeInfo(null, "Running the query " + sql)) {

			@Override
			protected void doUpgrade() throws Exception {
				runSQL(sql);
			}

		};
	}

	private static String _getUpgradeInfo(String tableName, String message) {
		Thread thread = Thread.currentThread();

		String callerClassName = thread.getStackTrace()[3].getClassName();

		String tableInfo = StringPool.BLANK;

		if (tableName != null) {
			tableInfo = "Modifying table " + tableName + " to ";
		}

		return StringBundler.concat(callerClassName, " - ", tableInfo, message);
	}

}