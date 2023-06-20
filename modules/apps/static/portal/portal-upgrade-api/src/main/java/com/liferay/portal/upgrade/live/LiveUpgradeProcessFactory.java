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

package com.liferay.portal.upgrade.live;

import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.upgrade.UpgradeProcessFactory;

/**
 * @author Kevin Lee
 */
public class LiveUpgradeProcessFactory {

	public static LiveUpgradeProcess addColumns(String... columnDefinitions) {
		return (tableName, liveUpgradeSchemaDiff) -> {
			UpgradeProcess upgradeProcess = UpgradeProcessFactory.addColumns(
				tableName, columnDefinitions);

			upgradeProcess.upgrade();

			liveUpgradeSchemaDiff.recordAddColumns(columnDefinitions);
		};
	}

	public static LiveUpgradeProcess alterColumnName(
		String oldColumnName, String newColumnDefinition) {

		return (tableName, liveUpgradeSchemaDiff) -> {
			UpgradeProcess upgradeProcess =
				UpgradeProcessFactory.alterColumnName(
					tableName, oldColumnName, newColumnDefinition);

			upgradeProcess.upgrade();

			liveUpgradeSchemaDiff.recordAlterColumnName(
				oldColumnName, newColumnDefinition);
		};
	}

	public static LiveUpgradeProcess alterColumnType(
		String columnName, String newColumnType) {

		return (tableName, liveUpgradeSchemaDiff) -> {
			UpgradeProcess upgradeProcess =
				UpgradeProcessFactory.alterColumnType(
					tableName, columnName, newColumnType);

			upgradeProcess.upgrade();

			liveUpgradeSchemaDiff.recordAlterColumnType(
				columnName, newColumnType);
		};
	}

	public static LiveUpgradeProcess dropColumns(String... columnNames) {
		return (tableName, liveUpgradeSchemaDiff) -> {
			UpgradeProcess upgradeProcess = UpgradeProcessFactory.dropColumns(
				tableName, columnNames);

			upgradeProcess.upgrade();

			liveUpgradeSchemaDiff.recordDropColumns(columnNames);
		};
	}

}