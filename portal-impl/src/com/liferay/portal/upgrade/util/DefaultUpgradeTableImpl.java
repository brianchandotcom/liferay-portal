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

package com.liferay.portal.upgrade.util;

import com.liferay.portal.kernel.upgrade.StagnantRowException;
import com.liferay.portal.kernel.upgrade.util.UpgradeColumn;
import com.liferay.portal.kernel.upgrade.util.UpgradeTable;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.tools.comparator.ColumnsComparator;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Alexander Chow
 * @author Bruno Farache
 */
public class DefaultUpgradeTableImpl
	extends BaseUpgradeTableImpl implements UpgradeTable {

	@Override
	public void copyTable(
			Connection sourceConnection, Connection targetConnection)
		throws Exception {

		updateTable(sourceConnection, targetConnection, false);
	}

	@Override
	public String getExportedData(ResultSet rs) throws Exception {
		StringBuilder sb = new StringBuilder();

		Object[][] columns = getColumns();

		for (int i = 0; i < columns.length; i++) {
			boolean last = false;

			if ((i + 1) == columns.length) {
				last = true;
			}

			if (_upgradeColumns[i] == null) {
				appendColumn(
					sb, rs, (String)columns[i][0], (Integer)columns[i][1],
					last);
			}
			else {
				try {
					Integer columnType = _upgradeColumns[i].getOldColumnType(
						(Integer)columns[i][1]);

					Object oldValue = getValue(
						rs, (String)columns[i][0], columnType);

					_upgradeColumns[i].setOldValue(oldValue);

					Object newValue = _upgradeColumns[i].getNewValue(oldValue);

					_upgradeColumns[i].setNewValue(newValue);

					appendColumn(sb, newValue, last);
				}
				catch (StagnantRowException sre) {
					_upgradeColumns[i].setNewValue(null);

					throw new StagnantRowException(
						"Column " + columns[i][0] + " with value " +
							sre.getMessage(),
						sre);
				}
			}
		}

		return sb.toString();
	}

	@Override
	public void setColumn(
			PreparedStatement ps, int index, Integer type, String value)
		throws Exception {

		if (_upgradeColumns[index] != null) {
			if (getCreateSQL() == null) {
				type = _upgradeColumns[index].getOldColumnType(type);
			}
			else {
				type = _upgradeColumns[index].getNewColumnType(type);
			}
		}

		super.setColumn(ps, index, type, value);
	}

	protected DefaultUpgradeTableImpl(
		String tableName, Object[][] columns, UpgradeColumn... upgradeColumns) {

		super(tableName);

		// Sort the column names to ensure they're sorted based on the
		// constructor's list of columns to upgrade. This is needed if you use
		// TempUpgradeColumnImpl and need to ensure a column's temporary value
		// is populated in the correct order.

		columns = columns.clone();

		List<String> sortedColumnNames = new ArrayList<>();

		for (UpgradeColumn upgradeColumn : upgradeColumns) {
			getSortedColumnName(sortedColumnNames, upgradeColumn);
		}

		if (!sortedColumnNames.isEmpty()) {
			Arrays.sort(columns, new ColumnsComparator(sortedColumnNames));
		}

		setColumns(columns);

		_upgradeColumns = new UpgradeColumn[columns.length];

		for (UpgradeColumn upgradeColumn : upgradeColumns) {
			prepareUpgradeColumns(upgradeColumn);
		}
	}

	protected void getSortedColumnName(
		List<String> sortedColumnNames, UpgradeColumn upgradeColumn) {

		if (upgradeColumn == null) {
			return;
		}

		String name = upgradeColumn.getName();

		if (Validator.isNotNull(name)) {
			sortedColumnNames.add(name);
		}
	}

	protected void prepareUpgradeColumns(UpgradeColumn upgradeColumn) {
		if (upgradeColumn == null) {
			return;
		}

		Object[][] columns = getColumns();

		for (int i = 0; i < columns.length; i++) {
			String name = (String)columns[i][0];

			if (upgradeColumn.isApplicable(name)) {
				_upgradeColumns[i] = upgradeColumn;
			}
		}
	}

	private final UpgradeColumn[] _upgradeColumns;

}