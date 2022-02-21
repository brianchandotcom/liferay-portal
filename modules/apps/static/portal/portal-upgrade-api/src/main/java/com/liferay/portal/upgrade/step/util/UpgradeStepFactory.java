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

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.upgrade.UpgradeStep;

import java.util.Arrays;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Stream;

/**
 * @author Carlos Sierra Andrés
 */
public class UpgradeStepFactory {

	/**
	 * @deprecated As of Cavanaugh (7.4.x), replaced by {@link
	 * 				#addColumns(String, String...)}
	 */
	@Deprecated
	public static UpgradeStep addColumns(
		Class<?> tableClass, String... columnDefinitions) {

		return new UpgradeProcess() {

			@Override
			protected void doUpgrade() throws Exception {
				alter(
					tableClass,
					_getAddColumnAlterables(
						AlterTableAddColumn::new, columnDefinitions));
			}

		};
	}

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

	/**
	 * @deprecated As of Cavanaugh (7.4.x), replaced by {@link
	 * 				#alterColumnTypes(String, String, String...)}
	 */
	@Deprecated
	public static UpgradeStep alterColumnTypes(
		Class<?> tableClass, String newType, String... columnNames) {

		return new UpgradeProcess() {

			@Override
			protected void doUpgrade() throws Exception {
				alter(
					tableClass,
					_getAlterables(AlterColumnType::new, newType, columnNames));
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

	/**
	 * @deprecated As of Cavanaugh (7.4.x), replaced by {@link
	 * 				#dropColumns(String, String...)}
	 */
	@Deprecated
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

	private static UpgradeProcess.Alterable[] _getAddColumnAlterables(
		BiFunction<String, String, UpgradeProcess.Alterable>
			alterableBiFunction,
		String... columnDefinitions) {

		Stream<String> stream = Arrays.stream(columnDefinitions);

		return stream.map(
			columnDefinition -> {
				int index = columnDefinition.indexOf(CharPool.SPACE);

				return alterableBiFunction.apply(
					columnDefinition.substring(0, index),
					columnDefinition.substring(index + 1));
			}
		).toArray(
			UpgradeProcess.Alterable[]::new
		);
	}

	private static UpgradeProcess.Alterable[] _getAlterables(
		BiFunction<String, String, UpgradeProcess.Alterable>
			alterableBiFunction,
		String newType, String... columnNames) {

		Stream<String> stream = Arrays.stream(columnNames);

		return stream.map(
			columnName -> alterableBiFunction.apply(columnName, newType)
		).toArray(
			UpgradeProcess.Alterable[]::new
		);
	}

	private static UpgradeProcess.Alterable[] _getAlterables(
		Function<String, UpgradeProcess.Alterable> alterableFunction,
		String... alterableStrings) {

		Stream<String> stream = Arrays.stream(alterableStrings);

		return stream.map(
			alterableFunction
		).toArray(
			UpgradeProcess.Alterable[]::new
		);
	}

}