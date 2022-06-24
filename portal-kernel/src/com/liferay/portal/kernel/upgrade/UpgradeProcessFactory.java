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

		Thread thread = Thread.currentThread();

		String callerClazz = thread.getStackTrace()[2].getClassName();

		String referenceName = StringBundler.concat(
			callerClazz, " -> addColumns: ", tableName, " - ",
			Arrays.toString(columnDefinitions));

		return new UpgradeProcess(referenceName) {

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

	public static UpgradeProcess alterColumnTypes(
		String tableName, String newType, String... columnNames) {

		Thread thread = Thread.currentThread();

		String callerClazz = thread.getStackTrace()[2].getClassName();

		String referenceName = StringBundler.concat(
			callerClazz, " -> alterColumnTypes: ", tableName, " - ", newType,
			" - ", Arrays.toString(columnNames));

		return new UpgradeProcess(referenceName) {

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

		Thread thread = Thread.currentThread();

		String callerClazz = thread.getStackTrace()[2].getClassName();

		String referenceName = StringBundler.concat(
			callerClazz, " -> dropColumns: ", tableName, " - ",
			Arrays.toString(columnNames));

		return new UpgradeProcess(referenceName) {

			@Override
			protected void doUpgrade() throws Exception {
				for (String columnName : columnNames) {
					alterTableDropColumn(tableName, columnName);
				}
			}

		};
	}

	public static UpgradeProcess runSQL(String sql) {
		Thread thread = Thread.currentThread();

		String callerClazz = thread.getStackTrace()[2].getClassName();

		String referenceName = StringBundler.concat(
			callerClazz, " -> runSQL: ", sql);

		return new UpgradeProcess(referenceName) {

			@Override
			protected void doUpgrade() throws Exception {
				runSQL(sql);
			}

		};
	}

}