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

import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.upgrade.UpgradeStep;

import java.util.Arrays;
import java.util.function.Function;

/**
 * @author Carlos Sierra Andrés
 */
public class UpgradeStepFactory {

	public static UpgradeStep addColumns(
		Class<?> tableClass, String... columnDefinitions) {

		return new UpgradeProcess() {

			@Override
			protected void doUpgrade() throws Exception {
				alter(
					tableClass,
					_getAlterables(
						AlterTableAddColumn::new, columnDefinitions));
			}

		};
	}

	public static UpgradeStep dropColumns(
		Class<?> tableClass, String... tableNames) {

		return new UpgradeProcess() {

			@Override
			protected void doUpgrade() throws Exception {
				alter(
					tableClass,
					_getAlterables(AlterTableDropColumn::new, tableNames));
			}

		};
	}

	public static UpgradeProcess runSql(String sql) {
		return new UpgradeProcess() {

			@Override
			protected void doUpgrade() throws Exception {
				runSQL(sql);
			}

		};
	}

	private static UpgradeProcess.Alterable[] _getAlterables(
		Function<String, UpgradeProcess.Alterable> alterableFunction,
		String... alterableStrings) {

		return Arrays.stream(
			alterableStrings
		).map(
			alterableFunction
		).toArray(
			UpgradeProcess.Alterable[]::new
		);
	}

}