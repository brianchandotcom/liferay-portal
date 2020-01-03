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

import java.sql.Connection;
import java.sql.SQLException;

import java.util.List;

/**
 * @author Alberto Chaparro
 */
public class UpgradeCompanyIdPrimaryKey extends BaseUpgradeCompanyId {

	public UpgradeCompanyIdPrimaryKey(String... tableNames) {
		_tableNames = tableNames;
	}

	@Override
	protected void doUpgrade() throws Exception {
		setUpgradePrimaryKey(true);

		super.doUpgrade();
	}

	@Override
	protected TableUpdater[] getTableUpdaters() {
		TableUpdater[] tableUpdaters = new TableUpdater[_tableNames.length];

		for (int i = 0; i < _tableNames.length; i++) {
			tableUpdaters[i] = new PKTableUpdater(_tableNames[i]);
		}

		return tableUpdaters;
	}

	protected class PKTableUpdater extends TableUpdater {

		public PKTableUpdater(String tableName) {
			super(tableName, "", "");
		}

		protected String getSelectSQL(
				Connection connection, String foreignTableName,
				String foreignColumnName)
			throws SQLException {

			List<Long> companyIds = getCompanyIds(connection);

			if (companyIds.size() == 1) {
				return String.valueOf(companyIds.get(0));
			}

			return String.valueOf(_DEFAULT_COMPANY_ID);
		}

		protected String getUpdateSQL(String selectSQL) {
			StringBundler sb = new StringBundler(5);

			sb.append("update ");
			sb.append(getTableName());
			sb.append(" set companyId = ");
			sb.append(selectSQL);
			sb.append(" where companyId is null");

			return sb.toString();
		}

		private static final long _DEFAULT_COMPANY_ID = 0;

	}

	private final String[] _tableNames;

}