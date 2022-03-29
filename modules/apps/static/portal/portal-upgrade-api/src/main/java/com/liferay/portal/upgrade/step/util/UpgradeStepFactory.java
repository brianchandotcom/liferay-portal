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

package com.liferay.portal.upgrade.step.util;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.upgrade.UpgradeStep;

/**
 * @author Carlos Sierra Andrés
 */
public class UpgradeStepFactory {

	public static UpgradeStep addColumns(
		String tableName, String... columnDefinitions) {

		return new UpgradeProcess() {

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

	public static UpgradeStep alterColumnTypes(
		String tableName, String newType, String... columnNames) {

		return new UpgradeProcess() {

			@Override
			protected void doUpgrade() throws Exception {
				for (String columnName : columnNames) {
					alterColumnType(tableName, columnName, newType);
				}
			}

		};
	}

	public static UpgradeStep dropColumns(
		String tableName, String... columnNames) {

		return new UpgradeProcess() {

			@Override
			protected void doUpgrade() throws Exception {
				for (String columnName : columnNames) {
					alterTableDropColumn(tableName, columnName);
				}
			}

		};
	}

	public static UpgradeProcess runSQL(String sql) {
		return new UpgradeProcess() {

			@Override
			protected void doUpgrade() throws Exception {
				runSQL(sql);
			}

		};
	}

}